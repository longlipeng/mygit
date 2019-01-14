package com.huateng.univer.consumer.acctypecontract.biz.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.univer.consumercontract.dto.AccTypeContractDTO;
import com.allinfinance.univer.issuer.dto.service.ServiceDTO;
import com.allinfinance.univer.servicefeerule.dto.CaclDspDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.AcctypeContractDAO;
import com.huateng.framework.ibatis.dao.CaclDspDAO;
import com.huateng.framework.ibatis.dao.LoyaltyAcctypeContractDAO;
import com.huateng.framework.ibatis.dao.LoyaltyContractDAO;
import com.huateng.framework.ibatis.dao.SerivceFeeRuleDAO;
import com.huateng.framework.ibatis.dao.ServiceDAO;
import com.huateng.framework.ibatis.model.AcctypeContract;
import com.huateng.framework.ibatis.model.AcctypeContractExample;
import com.huateng.framework.ibatis.model.CaclDsp;
import com.huateng.framework.ibatis.model.CaclDspExample;
import com.huateng.framework.ibatis.model.LoyaltyAcctypeContract;
import com.huateng.framework.ibatis.model.LoyaltyAcctypeContractExample;
import com.huateng.framework.ibatis.model.LoyaltyContract;
import com.huateng.framework.ibatis.model.LoyaltyContractExample;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.framework.util.StringUtils;
import com.huateng.univer.consumer.acctypecontract.biz.service.AccTypeContractService;
import com.huateng.univer.consumer.acctypecontract.integration.dao.AccTypeContractServiceDAO;

public class AccTypeContractServiceImpl implements AccTypeContractService {
	Logger logger = Logger.getLogger(AccTypeContractServiceImpl.class);
	private SerivceFeeRuleDAO serviceFeeRuleDAO;
	private AcctypeContractDAO acctypeContractDAO;
	private PageQueryDAO pageQueryDAO;
	private AccTypeContractServiceDAO accTypeContractServiceDAO;
	private LoyaltyAcctypeContractDAO loyaltyAcctypeContractDAO;
	private LoyaltyContractDAO loyaltyContractDAO;

	private CommonsDAO commonsDAO;
	private ServiceDAO serviceDAO;
	private CaclDspDAO caclDspDAO;

	public CaclDspDAO getCaclDspDAO() {
		return caclDspDAO;
	}

	public void setCaclDspDAO(CaclDspDAO caclDspDAO) {
		this.caclDspDAO = caclDspDAO;
	}

	public LoyaltyAcctypeContractDAO getLoyaltyAcctypeContractDAO() {
		return loyaltyAcctypeContractDAO;
	}

	public void setLoyaltyAcctypeContractDAO(
			LoyaltyAcctypeContractDAO loyaltyAcctypeContractDAO) {
		this.loyaltyAcctypeContractDAO = loyaltyAcctypeContractDAO;
	}

	public LoyaltyContractDAO getLoyaltyContractDAO() {
		return loyaltyContractDAO;
	}

	public void setLoyaltyContractDAO(LoyaltyContractDAO loyaltyContractDAO) {
		this.loyaltyContractDAO = loyaltyContractDAO;
	}

	public ServiceDAO getServiceDAO() {
		return serviceDAO;
	}

	public void setServiceDAO(ServiceDAO serviceDAO) {
		this.serviceDAO = serviceDAO;
	}

	public AccTypeContractDTO addAccTypeContractDTO(AccTypeContractDTO dto)
			throws BizServiceException {
		getServiceFeeRuleList(dto);
		getServiceDTOList(dto);
		return dto;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public SerivceFeeRuleDAO getServiceFeeRuleDAO() {
		return serviceFeeRuleDAO;
	}

	public void setServiceFeeRuleDAO(SerivceFeeRuleDAO serviceFeeRuleDAO) {
		this.serviceFeeRuleDAO = serviceFeeRuleDAO;
	}

	public AcctypeContractDAO getAcctypeContractDAO() {
		return acctypeContractDAO;
	}

	public void setAcctypeContractDAO(AcctypeContractDAO acctypeContractDAO) {
		this.acctypeContractDAO = acctypeContractDAO;
	}

	public PageQueryDAO getPageQueryDAO() {
		return pageQueryDAO;
	}

	public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
		this.pageQueryDAO = pageQueryDAO;
	}

	public AccTypeContractServiceDAO getAccTypeContractServiceDAO() {
		return accTypeContractServiceDAO;
	}

	public void setAccTypeContractServiceDAO(
			AccTypeContractServiceDAO accTypeContractServiceDAO) {
		this.accTypeContractServiceDAO = accTypeContractServiceDAO;
	}

	public void getServiceFeeRuleList(AccTypeContractDTO dto)
			throws BizServiceException {
		try {

			CaclDspExample caclDspExample = new CaclDspExample();
			//机构号不足八位，补足八位
			String entityId = StringUtils.rpad(dto.getContractSeller(),8," ");
			caclDspExample.createCriteria().andRecUpdUsrIdEqualTo(
					entityId).andDataStatEqualTo(
					DataBaseConstant.RULE_STATE_ENABLE);
			List<CaclDsp> lstCaclDsp = caclDspDAO
					.selectByExample(caclDspExample);
			List<CaclDspDTO> caclDspDTOList = new ArrayList<CaclDspDTO>();
			for (CaclDsp obj : lstCaclDsp) {
				CaclDspDTO dtoObj = new CaclDspDTO();
				ReflectionUtil.copyProperties(obj, dtoObj);
				caclDspDTOList.add(dtoObj);
			}

			// ServiceFeeRuleQueryDTO queryDTO=new ServiceFeeRuleQueryDTO();
			// queryDTO.setEntityId(dto.getContractSeller());
			// queryDTO.setState(DataBaseConstant.RULE_STATE_ENABLE);
			// List<ServiceFeeRuleDTO>
			// ruleDTOList=accTypeContractServiceDAO.queryDTOList(queryDTO);
			// dto.setServiceFeeRuleDTOList(ruleDTOList);
			dto.setCaclDspDTOList(caclDspDTOList);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询帐户计算规则失败！");
		}
	}

	public void getServiceDTOList(AccTypeContractDTO dto)
			throws BizServiceException {
		try {
			List<String> contractServiceIdList = new ArrayList<String>();
			List<ServiceDTO> serviceDTOList = new ArrayList<ServiceDTO>();
			ServiceDTO serviceDTO = new ServiceDTO();
			// 当前机构(发卡机构或收单机构)与下级机构(收单机构或商户)之间的合同已经定义过的服务
			AcctypeContractExample accExample = new AcctypeContractExample();
			accExample.createCriteria().andConsumerContractIdEqualTo(
					dto.getConsumerContractId());
			List<AcctypeContract> acctypeContractList = acctypeContractDAO
					.selectByExample(accExample);
			if (acctypeContractList != null && acctypeContractList.size() > 0) {
				for (AcctypeContract a : acctypeContractList) {
					contractServiceIdList.add(a.getAcctypeId());
				}
			}
			// 判断合同类型 CONTRACT_MERCHANT 商户合同 else 收单合同
			if (dto.getConstractType().equals(
					DataBaseConstant.CONTRACT_MERCHANT)) {
				AcctypeContractExample aExample = new AcctypeContractExample();
				aExample.createCriteria().andContractBuyerEqualTo(
						dto.getContractSeller());
				List<AcctypeContract> aContractList = acctypeContractDAO
						.selectByExample(aExample);
				if (aContractList != null && aContractList.size() > 0) {
					for (AcctypeContract t : aContractList) {
						if (!contractServiceIdList.contains(t.getAcctypeId())) {
							serviceDTO = new ServiceDTO();
							serviceDTO.setServiceId(t.getAcctypeId());
							serviceDTOList.add(serviceDTO);
						}
					}
				}
			} else {
				// 自己的服务
//				ServiceExample serviceExample = new ServiceExample();
//				serviceExample.createCriteria().andEntityIdEqualTo(
//						dto.getContractSeller()).andDataStateEqualTo(
//						DataBaseConstant.DATA_STATE_NORMAL);
//				List<Service> serviceList = new ArrayList<Service>();
//
//				serviceList = serviceDAO.selectByExample(serviceExample);
				
				//获取已在该机构产品中定义的服务，未在产品中定义的服务不出现在合同可选服务中
				List<?> serviceList = commonsDAO.queryForList("PRODUCT.selectServiceIdRelWithProductByIssuerId", dto.getContractSeller());
				
				
				if (serviceList != null && serviceList.size() > 0) {
					for (Object s : serviceList) {
						String serviceId = (String)((HashMap)s).get("serviceId");
						if (!contractServiceIdList.contains(serviceId)){
							serviceDTO = new ServiceDTO();
							serviceDTO.setServiceId(serviceId);
							serviceDTOList.add(serviceDTO);
							contractServiceIdList.add(serviceId);
						}
					}
				}

				// 代发卡合同签订的服务
				LoyaltyContractExample loyaltyExample = new LoyaltyContractExample();
				loyaltyExample.createCriteria().andContractBuyerEqualTo(
						dto.getContractSeller()).andDataStateEqualTo(
						DataBaseConstant.DATA_STATE_NORMAL)
						//.andExpiryDateGreaterThan(new Date().toLocaleString())
						.andExpiryDateGreaterThan(DateUtil.getCurrentDateStr())
						.andContractStateEqualTo(DataBaseConstant.STATE_ACTIVE);
				List<LoyaltyContract> loyaltyContractList = loyaltyContractDAO
						.selectByExample(loyaltyExample);

				LoyaltyAcctypeContractExample laccExample = new LoyaltyAcctypeContractExample();

				List lcIdList = new ArrayList();
				if (loyaltyContractList != null
						&& loyaltyContractList.size() > 0) {
					for (LoyaltyContract lc : loyaltyContractList) {
						String lcId = lc.getLoyaltyContractId();
						lcIdList.add(lcId);
					}
				}
				if (null != lcIdList && lcIdList.size() > 0) {
					laccExample.createCriteria().andLoyaltyContractIdIn(
							lcIdList);
					List<LoyaltyAcctypeContract> laAcctypeContractList = loyaltyAcctypeContractDAO
							.selectByExample(laccExample);
					if (laAcctypeContractList != null
							&& laAcctypeContractList.size() > 0) {
						for (LoyaltyAcctypeContract lac : laAcctypeContractList) {
							if (!contractServiceIdList.contains(lac
									.getAcctypeId())) {
								serviceDTO = new ServiceDTO();
								serviceDTO.setServiceId(lac.getAcctypeId());
								serviceDTOList.add(serviceDTO);
							}
						}
					}
				}

			}
			dto.setServiceDTOList(serviceDTOList);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询服务失败");
		}
	}

	public List<AccTypeContractDTO> inquery(String Id)
			throws BizServiceException {
		List<AccTypeContractDTO> accDTOList = new ArrayList<AccTypeContractDTO>();
		try {
			AcctypeContractExample aExample = new AcctypeContractExample();
			aExample.createCriteria().andConsumerContractIdEqualTo(Id);
			List<AcctypeContract> aContractList = acctypeContractDAO
					.selectByExample(aExample);
			if (aContractList != null && aContractList.size() > 0) {
				for (AcctypeContract t : aContractList) {
					AccTypeContractDTO accTypeContractDTO = new AccTypeContractDTO();
					ReflectionUtil.copyProperties(t, accTypeContractDTO);
					accDTOList.add(accTypeContractDTO);
				}
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询合同服务信息失败");
		}
		return accDTOList;
	}

	public void insertAccTypeContractDTO(AccTypeContractDTO dto)
			throws BizServiceException {
		try {
			AcctypeContractExample accTypeContractExample = new AcctypeContractExample();
			accTypeContractExample.createCriteria()
					.andConsumerContractIdEqualTo(dto.getConsumerContractId())
					.andAcctypeIdEqualTo(dto.getAcctypeId());
			AcctypeContract contract = new AcctypeContract();
			ReflectionUtil.copyProperties(dto, contract);
			if (null == dto.getAcctypeContractId()
					|| "".equals(dto.getAcctypeContractId())) {
				contract.setAcctypeContractId(commonsDAO
						.getNextValueOfSequence("TB_ACCTYPE_CONTRACT"));
				contract.setCreateTime(DateUtil.getCurrentTime());
				contract.setCreateUser(dto.getLoginUserId());
			}
			contract.setModifyTime(DateUtil.getCurrentTime());

			if (acctypeContractDAO.countByExample(accTypeContractExample) > 0) {

				acctypeContractDAO.updateByExampleSelective(contract,
						accTypeContractExample);

			}else{
				if("1".equals(dto.getConstractType())){
					//根据服务ID获取相关产品的卡BIN，并添加到合同服务表
					List<?> cardBinList = commonsDAO.queryForList("PRODUCT.selectCardBinByServiceId", contract.getAcctypeId());
					for(int i=0; i<cardBinList.size(); i++){
						HashMap cardBin = (HashMap)cardBinList.get(i);
						contract.setCardBin((String)cardBin.get("cardBin"));					
						acctypeContractDAO.insert(contract);
					}				
				}else{
					acctypeContractDAO.insert(contract);
				}
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("新增服务合同失败！");
		}
	}

	public CommonsDAO getCommonsDAO() {
		return commonsDAO;
	}

	public void setCommonsDAO(CommonsDAO commonsDAO) {
		this.commonsDAO = commonsDAO;
	}

	public AccTypeContractDTO viewAccTypeContractDTO(AccTypeContractDTO dto)
			throws BizServiceException {
		try {
			// AcctypeContract contract = acctypeContractDAO
			// .selectByPrimaryKey(dto.getAcctypeContractId());
			AcctypeContractExample accTypeContractExample = new AcctypeContractExample();
			accTypeContractExample.createCriteria()
					.andConsumerContractIdEqualTo(dto.getConsumerContractId())
					.andAcctypeContractIdEqualTo(dto.getAcctypeContractId());
			AcctypeContract contract = acctypeContractDAO.selectByExample(
					accTypeContractExample).get(0);
			ReflectionUtil.copyProperties(contract, dto);
			getServiceFeeRuleList(dto);
			return dto;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询服务合同失败！");
		}

	}

	public void updateAccTypeContract(AccTypeContractDTO dto)
			throws BizServiceException {
		try {
			AcctypeContractExample accTypeContractExample = new AcctypeContractExample();
			accTypeContractExample.createCriteria()
					.andAcctypeContractIdEqualTo(dto.getAcctypeContractId())
					.andConsumerContractIdEqualTo(dto.getConsumerContractId());
			AcctypeContract contract = acctypeContractDAO.selectByExample(
					accTypeContractExample).get(0);
			ReflectionUtil.copyProperties(dto, contract);
			contract.setModifyTime(DateUtil.getCurrentTime());
			acctypeContractDAO
					.updateByExample(contract, accTypeContractExample);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("更新服务合同失败！");
		}
	}

	public void deleteAccTypeContract(AccTypeContractDTO dto)
			throws BizServiceException {
		List<AcctypeContract> deleteList = new ArrayList<AcctypeContract>();
		List<AccTypeContractDTO> dtoList = dto.getAccTypeContractDTOList();
		for (AccTypeContractDTO o : dtoList) {
			AcctypeContract contract = new AcctypeContract();
			contract.setAcctypeContractId(o.getAcctypeContractId());
			deleteList.add(contract);
		}
		try {
			commonsDAO.batchDelete(
					"TB_ACCTYPE_CONTRACT.abatorgenerated_deleteByPrimaryKey",
					deleteList);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("删除服务合同失败！");
		}
	}

}
