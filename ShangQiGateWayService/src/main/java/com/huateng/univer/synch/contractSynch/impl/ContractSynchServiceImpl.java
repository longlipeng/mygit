/**
 * Classname ContractSynchServiceImpl.java
 *
 * Version information
 *
 * Licensed Property to HuaTeng Data Co., Ltd.
 * 
 * (C) Copyright of HuaTeng Data Co., Ltd. 2010
 *     All Rights Reserved.
 *
 * Project Info: HuaTeng Data Internet Acquiring  Project
 * 
 * Modification History:
 * =============================================================================
 *		Author		Date		Description
 *   ------------ ---------- ---------------------------------------------------
 *		lfr		2013-4-15
 * =============================================================================
 */

package com.huateng.univer.synch.contractSynch.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.AcctypeContractDAO;
import com.huateng.framework.ibatis.dao.CaclDspDAO;
import com.huateng.framework.ibatis.dao.ConsumerContractDAO;
import com.huateng.framework.ibatis.dao.ServiceDAO;
import com.huateng.framework.ibatis.dao.SettlePeriodRuleDAO;
import com.huateng.framework.ibatis.model.AcctypeContract;
import com.huateng.framework.ibatis.model.CaclDsp;
import com.huateng.framework.ibatis.model.CaclDspKey;
import com.huateng.framework.ibatis.model.ConsumerContract;
import com.huateng.framework.ibatis.model.ConsumerContractExample;
import com.huateng.framework.ibatis.model.Merchant;
import com.huateng.framework.ibatis.model.MerchantKey;
import com.huateng.framework.ibatis.model.Service;
import com.huateng.framework.ibatis.model.SettlePeriodRule;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.StringUtils;
import com.huateng.univer.consumer.merchant.integration.dao.MerchantServiceDAO;
import com.huateng.univer.settleperiodrule.biz.service.SettlePeriodRuleService;
import com.huateng.univer.synch.contractSynch.ContractSynchService;
import com.huateng.univer.synch.dto.AcctypeContractSynchDTO;
import com.huateng.univer.synch.dto.MerchantContractSynchDTO;

public class ContractSynchServiceImpl implements ContractSynchService {
	Logger logger = Logger.getLogger(ContractSynchServiceImpl.class);

	private ConsumerContractDAO consumerContractDAO;
	private MerchantServiceDAO merchantServiceDAO;
	private SettlePeriodRuleDAO settlePeriodRuleDAO;
	private CaclDspDAO caclDspDAO;
	// 合同是否符合要求标志
	private Boolean flag = true;
	private SettlePeriodRuleService settlePeriodRuleService;
	private CommonsDAO commonsDAO;
	private AcctypeContractDAO acctypeContractDAO;
	private ServiceDAO serviceDAO;

	/**
	 * 合同信息同步
	 * 
	 * @param MerchantContractSynchDTO
	 * @return
	 * @throws BizServiceException
	 */
	public String contractInfoSynch(
			MerchantContractSynchDTO merchantContractSynchDTO)
			throws BizServiceException {
		try {
			// 检查数据
			merchantContractSynchDTO = checkContractData(merchantContractSynchDTO);

			ConsumerContract contract = consumerContractDAO
					.selectByPrimaryKey(merchantContractSynchDTO
							.getConsumerContractId());

			if (contract == null) {
				// 新增

				contract = new ConsumerContract();
				contract.setConsumerContractId(merchantContractSynchDTO
						.getConsumerContractId());
				contract.setContractStartDate(merchantContractSynchDTO
						.getContractStartDate());
				contract.setContractEndDate(merchantContractSynchDTO
						.getContractEndDate());
				contract.setContractBuyer(merchantContractSynchDTO
						.getContractBuyer());
				//合同创建日期
				String contractCreateDate = merchantContractSynchDTO.getContractCreateDate();
				contract.setContractCreateDate(contractCreateDate);
				
				contract.setRuleNo(merchantContractSynchDTO.getPeriodRuleNo());
				// ContractType=1为收单机构合同，2为商户合同。
				contract.setContractType(DataBaseConstant.CONTRACT_MERCHANT);
				
				//发起公司名称(多收单的情况下，可以用于判断发起公司是否与系统收单相同)
				// 20130822 从DTO取而不是从配置文件取
				String fatherEntityId = merchantContractSynchDTO.getInitiatorCompanyCode();

				// contract.setContractSeller(merchants.get(0).getFatherEntityId());
				// 收单机构号
//				String fatherEntityId = Config.getConsumerEntityId();
				contract.setContractSeller(fatherEntityId);

				// 查询有效商户
				MerchantKey merchantKey = new MerchantKey();
				merchantKey.setEntityId(merchantContractSynchDTO
						.getContractBuyer());
				merchantKey.setFatherEntityId(fatherEntityId);
				Merchant merchant = merchantServiceDAO
						.selectByPrimaryKey(merchantKey);
				if (merchant == null
						|| !DataBaseConstant.DATA_STATE_NORMAL.equals(merchant
								.getDataState())) {
					throw new BizServiceException("合同号："+merchantContractSynchDTO
							.getConsumerContractId()+"异常原因："+"商户"
							+ merchantContractSynchDTO.getContractBuyer()
							+ "不存在或无效！");
				}

				// 新增合同
				contract = insertContract(contract);

				// 新增服务合同
				List<AcctypeContractSynchDTO> acctypeContractSynchDTOs = merchantContractSynchDTO
						.getAcctypeContractSynchDTOs();
				for (AcctypeContractSynchDTO acctypeContractSynchDTO : acctypeContractSynchDTOs) {
					AcctypeContract acctypeContract = new AcctypeContract();
					// 合同id
					acctypeContract.setConsumerContractId(contract
							.getConsumerContractId());
					// 合同买方
					acctypeContract.setContractBuyer(contract
							.getContractBuyer());
					// 手续费计算规则号
					String calculationRuleNo = acctypeContractSynchDTO
							.getCalculationRuleNo();

					acctypeContract.setRuleNo(calculationRuleNo);
					// 服务类型
					acctypeContract.setAcctypeId(acctypeContractSynchDTO
							.getAcctypeId());

					//检查手续费计算规则号
					CaclDspKey key = new CaclDspKey();		
					key.setDiscCd(acctypeContract.getRuleNo());
					String recUpdUsrId = StringUtils.rpad(fatherEntityId,8," ");
					key.setRecUpdUsrId(recUpdUsrId);
					CaclDsp caclDsp = caclDspDAO.selectByPrimaryKey(key);
					if (caclDsp == null
							|| DataBaseConstant.RULE_STATE_DELETE.equals(caclDsp
									.getDataStat())) {
						throw new BizServiceException("合同号："
								+ contract.getConsumerContractId() + "异常原因：在收单机构"
								+ contract.getContractSeller() + "下未找到计算规则"
								+ acctypeContract.getRuleNo() + "！");
						
					}
					if(DataBaseConstant.RULE_STATE_NEW.equals(caclDsp.getDataStat())){
						throw new BizServiceException("合同号："
								+ contract.getConsumerContractId() + "异常原因：在收单机构"
								+ contract.getContractSeller() + "下计算规则"
								+ acctypeContract.getRuleNo() + "未启用！");
					}
					
					//检查账户类型
					Service acctype = serviceDAO.selectByPrimaryKey(acctypeContract.getAcctypeId());

					if (acctype == null
							|| DataBaseConstant.DATA_TYPE_NO.equals(acctype.getDataState())) {
						throw new BizServiceException("合同号："
								+ contract.getConsumerContractId() + "异常原因：未找到账户类型"
								+ acctypeContract.getAcctypeId() + "！");
						
					}
					insertAccTypeContract(acctypeContract);
				}

			} else {
				// 合同只能新增不能更新
				throw new BizServiceException("合同号："+merchantContractSynchDTO
						.getConsumerContractId()+"异常原因："+"合同 ："
						+ contract.getConsumerContractId() + " 已经存在，并且不允许更新！");
			}

			return "succ";

		} catch (BizServiceException ex) {
			throw ex;
		} catch (Exception e) {
			logger.error(e);
			throw new BizServiceException("合同号："+merchantContractSynchDTO
					.getConsumerContractId()+"异常原因："+"新增商户合同失败！"
					+ e.toString());
		}
	}

	/**
	 * 新增合同信息
	 * 
	 * @param ConsumerContract
	 * @return
	 * @throws BizServiceException
	 */
	public ConsumerContract insertContract(ConsumerContract contract)
			throws BizServiceException {
		try {

			// 检查周期规则号
//			SettlePeriodRuleQueryDTO dto = new SettlePeriodRuleQueryDTO();
//			dto.setEntityId(contract.getContractSeller());
//			dto.setRuleNo(contract.getRuleNo());
//			settlePeriodRuleService.query(dto);
			SettlePeriodRule rule = settlePeriodRuleDAO
					.selectByPrimaryKey(contract.getRuleNo());
			if (rule == null
					|| !contract.getContractSeller().equals(rule.getEntityId())
					|| DataBaseConstant.RULE_STATE_DELETE.equals(rule
							.getState())) {
				throw new BizServiceException("合同号："
						+ contract.getConsumerContractId() + "异常原因：在收单机构"
						+ contract.getContractSeller() + "下未找到结算周期规则"
						+ contract.getRuleNo() + "！");
				
			}
			if(DataBaseConstant.RULE_STATE_NEW.equals(rule.getState())){
				throw new BizServiceException("合同号："
						+ contract.getConsumerContractId() + "异常原因：在收单机构"
						+ contract.getContractSeller() + "下结算周期规则"
						+ contract.getRuleNo() + "未启用！");
			}

			checkMchntContract(contract);
			if (!flag) {
				throw new BizServiceException("合同号："+contract
						.getConsumerContractId()+"异常原因："+"更新后的合同的开始日期必须大于上一合同的开始日期");
			}

			String settleDateNext = "";
			// 计算首次结算日
			settleDateNext = settlePeriodRuleService.getSettleDateNext(contract
					.getContractStartDate(), contract.getRuleNo());
			if (null != settleDateNext && !"".equals(settleDateNext)
					&& null != contract.getContractEndDate()
					&& !"".equals(contract.getContractEndDate().trim())) {
				if (Integer.parseInt(settleDateNext) > Integer
						.parseInt(contract.getContractEndDate())) {
					throw new BizServiceException("合同号："+contract
							.getConsumerContractId()+"异常原因："+"添加结算周期规则的首次结算日大于合同的截止日期");
				}
			}

			contract.setSettlDtFirst(settleDateNext);
			contract.setSettlDtPre(DateUtil.date2String(DateUtil.addDay(
					DateUtil.string2Dateyyyymmdd(contract
							.getContractStartDate()), -1)));
			contract.setSettlDtNext(settleDateNext);
			
			//汇总结算
			contract.setClearTp("1");
			contract.setCreateTime(DateUtil.getCurrentTime());
			contract.setCreateUser("0");
			contract.setModifyTime(DateUtil.getCurrentTime());
			contract.setModifyUser("0");
			contract.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			consumerContractDAO.insert(contract);

			return contract;

		} catch (BizServiceException e1) {
			throw e1;
		} catch (Exception e) {
			logger.error(e);
			throw new BizServiceException("合同号："+contract
					.getConsumerContractId()+"异常原因："+"新增合同失败！" + e.toString());
		}
	}

	
	/**
	 * 新增服务合同
	 * 
	 * @param ConsumerContract
	 * @return
	 * @throws BizServiceException
	 */
	public void insertAccTypeContract(AcctypeContract acctypeContract)
			throws BizServiceException {
		try {
			
			acctypeContract.setAcctypeContractId(commonsDAO
					.getNextValueOfSequence("TB_ACCTYPE_CONTRACT"));
			acctypeContract.setCreateTime(DateUtil.getCurrentTime());
			//使用默认用户admin
			acctypeContract.setCreateUser("0");
			acctypeContract.setModifyTime(DateUtil.getCurrentTime());
			acctypeContractDAO.insert(acctypeContract);

		} catch (Exception e) {
			logger.error(e);		
			throw new BizServiceException("合同号："+acctypeContract
					.getConsumerContractId()+"异常原因："+"新增服务合同失败！" + e.toString());
		}
	}

	// 检查数据
	public MerchantContractSynchDTO checkContractData(
			MerchantContractSynchDTO merchantContractSynchDTO)
			throws BizServiceException {

		// 合同编号
		String consumerContractId = merchantContractSynchDTO
				.getConsumerContractId();
		if (consumerContractId == null || "".equals(consumerContractId.trim())) {
			throw new BizServiceException("合同号："+merchantContractSynchDTO
					.getConsumerContractId()+"异常原因："+"合同编号为空。");
		} else {
			merchantContractSynchDTO.setConsumerContractId(consumerContractId
					.trim());
		}

		// 商户编号
		String contractBuyer = merchantContractSynchDTO.getContractBuyer();
		if (contractBuyer == null || "".equals(contractBuyer.trim())) {
			throw new BizServiceException("合同号："+merchantContractSynchDTO
					.getConsumerContractId()+"异常原因："+"商户编号为空。");
		} else {
			merchantContractSynchDTO.setContractBuyer(contractBuyer.trim());
		}

		// 商户名称
		String merchantName = merchantContractSynchDTO.getMerchantName();
		merchantContractSynchDTO.setMerchantName(merchantName.trim());

		// 结算周期规则号
		String periodRuleNo = merchantContractSynchDTO.getPeriodRuleNo();
		if (periodRuleNo == null || "".equals(periodRuleNo.trim())) {
			throw new BizServiceException("合同号："+merchantContractSynchDTO
					.getConsumerContractId()+"异常原因："+"结算周期规则号为空。");
		} else {
			merchantContractSynchDTO.setPeriodRuleNo(periodRuleNo.trim());
		}
		
		//合同创建日期
		String contractCreateDate = merchantContractSynchDTO.getContractCreateDate();
		if(contractCreateDate == null || "".equals(contractCreateDate.trim())){
			throw new BizServiceException("合同号："+merchantContractSynchDTO
					.getConsumerContractId()+"异常原因："+"合同创建日期为空。");
		}
		contractCreateDate = DateUtil.getFormatTime(contractCreateDate.trim());
		merchantContractSynchDTO.setContractCreateDate(contractCreateDate);

		// 合同开始日期
		String contractStartDate = merchantContractSynchDTO
				.getContractStartDate();
		Date contractStartDateDate;
		if (contractStartDate == null || "".equals(contractStartDate.trim())) {
			throw new BizServiceException("合同号："+merchantContractSynchDTO
					.getConsumerContractId()+"异常原因："+"合同开始日期为空。");
		} else {
			contractStartDate = contractStartDate.trim();
			contractStartDate = DateUtil.getFormatTime(contractStartDate);
			// 合同开始日期必需大于等于当前日期
			contractStartDateDate = DateUtil
					.string2Dateyyyymmdd(contractStartDate);
			if (contractStartDateDate.compareTo(DateUtil.getCurrentDate()) < 0) {
				throw new BizServiceException("合同号："+merchantContractSynchDTO
						.getConsumerContractId()+"异常原因："+"合同有效期开始日期不能小于系统日期！");
			}
			merchantContractSynchDTO.setContractStartDate(contractStartDate);
		}

		// 合同结束日期
		String contractEndDate = merchantContractSynchDTO.getContractEndDate();
		if (contractEndDate == null || "".equals(contractEndDate.trim())) {
			contractEndDate = null;
		} else {
			contractEndDate = contractEndDate.trim();
			contractEndDate = DateUtil.getFormatTime(contractEndDate);
			// 合同开始日期不大于结束日期
			Date contractEndDateDate = DateUtil
					.string2Dateyyyymmdd(contractEndDate);
			if (contractStartDateDate.compareTo(contractEndDateDate) > 0) {
				throw new BizServiceException("合同号："+merchantContractSynchDTO
						.getConsumerContractId()+"异常原因："+"合同有效期结束日期不能小于开始日期！");
			}
		}
		merchantContractSynchDTO.setContractEndDate(contractEndDate);

		// 账户合同
		List<AcctypeContractSynchDTO> acctypeContractSynchDTOs = merchantContractSynchDTO
				.getAcctypeContractSynchDTOs();
		if (acctypeContractSynchDTOs == null
				|| acctypeContractSynchDTOs.size() == 0) {
			throw new BizServiceException("合同号："+merchantContractSynchDTO
					.getConsumerContractId()+"异常原因："+"账户合同信息为空。");
		}
		for (AcctypeContractSynchDTO acctypeContractSynchDTO : acctypeContractSynchDTOs) {
			// 服务类型
			String AcctypeId = acctypeContractSynchDTO.getAcctypeId();
			if (AcctypeId == null || "".equals(AcctypeId.trim())) {
				throw new BizServiceException("合同号："+merchantContractSynchDTO
						.getConsumerContractId()+"异常原因："+"账户类型为空。");
			} else {
				acctypeContractSynchDTO.setAcctypeId(AcctypeId.trim());
			}
			// 手续费计算规则
			String calculationRuleNo = acctypeContractSynchDTO
					.getCalculationRuleNo();
			if (calculationRuleNo == null
					|| "".equals(calculationRuleNo.trim())) {
				throw new BizServiceException("合同号："+merchantContractSynchDTO
						.getConsumerContractId()+"异常原因："+"手续费计算规则为空。");
			} else {
				acctypeContractSynchDTO.setCalculationRuleNo(calculationRuleNo
						.trim());
			}
		}
		merchantContractSynchDTO
				.setAcctypeContractSynchDTOs(acctypeContractSynchDTOs);

		return merchantContractSynchDTO;
	}

	// 检查商户合同
	public void checkMchntContract(ConsumerContract contract)
			throws BizServiceException {
		List<ConsumerContract> consumerContractList;

		consumerContractList = getConsumerContractListForAdd(contract);

		// 商户是否已经拥有合同
		if (consumerContractList != null && consumerContractList.size() > 0) {
			ConsumerContract consumerContract = new ConsumerContract();
			consumerContract = consumerContractList.get(consumerContractList
					.size() - 1);
			String startDateStr = contract.getContractStartDate();
			Date dtoStartDate = DateUtil.string2Dateyyyymmdd(startDateStr);
			Date contractStartDate = DateUtil
					.string2Dateyyyymmdd(consumerContract
							.getContractStartDate());
			if (dtoStartDate.compareTo(contractStartDate) <= 0) {
				// 小于等于上一个合同开始日期
				flag = false;
			} else {
				// 大于上一个合同开始日期
				flag = true;
			}
			// 符合条件的情况
			if (flag) {
				// 上一个合同的结束日期大于当前要添加合同的开始日期时,则把上一个合同的结束日期置为新添加合同的前一天
				if (consumerContract.getContractEndDate() == null
						|| (consumerContract.getContractEndDate() != null && DateUtil
								.string2Dateyyyymmdd(
										consumerContract.getContractEndDate())
								.compareTo(dtoStartDate) >= 0)) {
					/* 修改上一个合同的下个结算日为合同开始日 */
					consumerContract.setSettlDtNext(consumerContract
							.getContractStartDate());
					consumerContract.setContractEndDate(DateUtil
							.date2String(DateUtil.addDay(dtoStartDate, -1)));
					consumerContract.setModifyUser("0");
					consumerContract.setModifyTime(DateUtil.getCurrentTime());
					try {
						consumerContractDAO
								.updateByPrimaryKey(consumerContract);
					} catch (Exception e) {
						logger.error(e);
						throw new BizServiceException("合同号："+contract
								.getConsumerContractId()+"修改上一个合同的下个结算日和合同结算日期失败！"
								+ e.toString());
					}
				}
			}
		} else {
			flag = true;
		}
	}

	// 在新增合同时，查询合同买方的所有合同
	public List<ConsumerContract> getConsumerContractListForAdd(
			ConsumerContract contract) throws BizServiceException {
		try {
			ConsumerContractExample example = new ConsumerContractExample();
			example.createCriteria().andContractBuyerEqualTo(
					contract.getContractBuyer()).andContractSellerEqualTo(
					contract.getContractSeller()).andDataStateEqualTo(
					DataBaseConstant.DATA_STATE_NORMAL).andContractTypeEqualTo(
					contract.getContractType());
			example.setOrderByClause("CONSUMER_CONTRACT_ID");
			List<ConsumerContract> contractList = consumerContractDAO
					.selectByExample(example);
			return contractList;
		} catch (Exception e) {
			logger.error(e);
			throw new BizServiceException("合同号："+contract
					.getConsumerContractId()+"查询合同买方的所有合同失败！" + e.toString());
		}
	}

	public ConsumerContractDAO getConsumerContractDAO() {
		return consumerContractDAO;
	}

	public void setConsumerContractDAO(ConsumerContractDAO consumerContractDAO) {
		this.consumerContractDAO = consumerContractDAO;
	}

	public MerchantServiceDAO getMerchantServiceDAO() {
		return merchantServiceDAO;
	}

	public void setMerchantServiceDAO(MerchantServiceDAO merchantServiceDAO) {
		this.merchantServiceDAO = merchantServiceDAO;
	}

	public SettlePeriodRuleDAO getSettlePeriodRuleDAO() {
		return settlePeriodRuleDAO;
	}

	public void setSettlePeriodRuleDAO(SettlePeriodRuleDAO settlePeriodRuleDAO) {
		this.settlePeriodRuleDAO = settlePeriodRuleDAO;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public SettlePeriodRuleService getSettlePeriodRuleService() {
		return settlePeriodRuleService;
	}

	public void setSettlePeriodRuleService(
			SettlePeriodRuleService settlePeriodRuleService) {
		this.settlePeriodRuleService = settlePeriodRuleService;
	}

	public CommonsDAO getCommonsDAO() {
		return commonsDAO;
	}

	public void setCommonsDAO(CommonsDAO commonsDAO) {
		this.commonsDAO = commonsDAO;
	}

	public AcctypeContractDAO getAcctypeContractDAO() {
		return acctypeContractDAO;
	}

	public void setAcctypeContractDAO(AcctypeContractDAO acctypeContractDAO) {
		this.acctypeContractDAO = acctypeContractDAO;
	}

	public CaclDspDAO getCaclDspDAO() {
		return caclDspDAO;
	}

	public void setCaclDspDAO(CaclDspDAO caclDspDAO) {
		this.caclDspDAO = caclDspDAO;
	}

	public ServiceDAO getServiceDAO() {
		return serviceDAO;
	}

	public void setServiceDAO(ServiceDAO serviceDAO) {
		this.serviceDAO = serviceDAO;
	}
    
	
	
}
