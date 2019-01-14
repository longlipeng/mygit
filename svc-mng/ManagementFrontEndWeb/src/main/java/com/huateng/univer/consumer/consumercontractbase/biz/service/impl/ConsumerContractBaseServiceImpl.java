package com.huateng.univer.consumer.consumercontractbase.biz.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.consumer.merchant.dto.MerchantDTO;
import com.allinfinance.univer.consumer.merchant.dto.MerchantTxnQueryDTO;
import com.allinfinance.univer.consumercontract.dto.AccTypeContractDTO;
import com.allinfinance.univer.consumercontract.dto.ConsumerContractDTO;
import com.allinfinance.univer.consumercontract.dto.ConsumerContractQueryDTO;
import com.allinfinance.univer.servicefeerule.dto.CaclDspDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.AcctypeContractDAO;
import com.huateng.framework.ibatis.dao.CaclDspDAO;
import com.huateng.framework.ibatis.dao.ConsumerContractDAO;
import com.huateng.framework.ibatis.dao.ConsumerDAO;
import com.huateng.framework.ibatis.dao.InsTransPrivDAO;
import com.huateng.framework.ibatis.dao.PosParamenterDAO;
import com.huateng.framework.ibatis.dao.SerivceFeeRuleDAO;
import com.huateng.framework.ibatis.dao.SettlePeriodRuleDAO;
import com.huateng.framework.ibatis.model.CaclDsp;
import com.huateng.framework.ibatis.model.CaclDspExample;
import com.huateng.framework.ibatis.model.Consumer;
import com.huateng.framework.ibatis.model.ConsumerContract;
import com.huateng.framework.ibatis.model.ConsumerContractExample;
import com.huateng.framework.ibatis.model.ConsumerKey;
import com.huateng.framework.ibatis.model.InsTransPriv;
import com.huateng.framework.ibatis.model.InsTransPrivExample;
import com.huateng.framework.ibatis.model.Merchant;
import com.huateng.framework.ibatis.model.MerchantExample;
import com.huateng.framework.ibatis.model.MerchantKey;
import com.huateng.framework.ibatis.model.SettlePeriodRule;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.framework.util.StringUtils;
import com.huateng.univer.consumer.consumercontractbase.biz.service.ConsumerContractBaseService;
import com.huateng.univer.consumer.merchant.biz.service.MerchantService;
import com.huateng.univer.consumer.merchant.integration.dao.MerchantServiceDAO;
import com.huateng.univer.settleperiodrule.biz.service.SettlePeriodRuleService;

public class ConsumerContractBaseServiceImpl implements
		ConsumerContractBaseService {
	Logger logger = Logger.getLogger(ConsumerContractBaseServiceImpl.class);
	private CommonsDAO commonsDAO;
	private ConsumerContractDAO consumerContractDAO;
	private AcctypeContractDAO acctypeContractDAO;
	private SerivceFeeRuleDAO serviceFeeRuleDAO;
	private Boolean flag = true;
	private MerchantServiceDAO merchantServiceDAO;
	private ConsumerDAO consumerDAO;
	private MerchantService merchantService;
	private CaclDspDAO caclDspDAO;
	private SettlePeriodRuleService settlePeriodRuleService;
	private SettlePeriodRuleDAO settlePeriodRuleDAO;
	private InsTransPrivDAO insTransPrivDAO;
	private PosParamenterDAO terParameterDAO;

	public SettlePeriodRuleDAO getSettlePeriodRuleDAO() {
		return settlePeriodRuleDAO;
	}

	public void setSettlePeriodRuleDAO(SettlePeriodRuleDAO settlePeriodRuleDAO) {
		this.settlePeriodRuleDAO = settlePeriodRuleDAO;
	}

	public PosParamenterDAO getTerParameterDAO() {
		return terParameterDAO;
	}

	public void setTerParameterDAO(PosParamenterDAO terParameterDAO) {
		this.terParameterDAO = terParameterDAO;
	}


	public InsTransPrivDAO getInsTransPrivDAO() {
		return insTransPrivDAO;
	}

	public void setInsTransPrivDAO(InsTransPrivDAO insTransPrivDAO) {
		this.insTransPrivDAO = insTransPrivDAO;
	}

	public CaclDspDAO getCaclDspDAO() {
		return caclDspDAO;
	}

	public void setCaclDspDAO(CaclDspDAO caclDspDAO) {
		this.caclDspDAO = caclDspDAO;
	}

	public MerchantService getMerchantService() {
		return merchantService;
	}

	public void setMerchantService(MerchantService merchantService) {
		this.merchantService = merchantService;
	}

	public ConsumerDAO getConsumerDAO() {
		return consumerDAO;
	}

	public void setConsumerDAO(ConsumerDAO consumerDAO) {
		this.consumerDAO = consumerDAO;
	}

	public SettlePeriodRuleService getSettlePeriodRuleService() {
		return settlePeriodRuleService;
	}

	public void setSettlePeriodRuleService(
			SettlePeriodRuleService settlePeriodRuleService) {
		this.settlePeriodRuleService = settlePeriodRuleService;
	}

	private PageQueryDAO pageQueryDAO;

	public PageDataDTO selectContract(ConsumerContractQueryDTO dto)
			throws BizServiceException {
		try {
			if (null != dto.getConsumerContractId()) {
				dto.setConsumerContractId(dto.getConsumerContractId().trim());
			}
			PageDataDTO pdd = pageQueryDAO.query(
					"CONSUMER_CONTRACT.selectContract", dto);
			return pdd;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询发行机构合同信息失败~！");
		}
	}

	public ConsumerContractDTO insertContract(ConsumerContractDTO dto)
			throws BizServiceException {
		try {
			chkMchntContract(dto);
			if (!flag) {
				throw new BizServiceException("新增合同的开始日期必须大于上一合同的开始日期");
			}
			String settleDateNext = "";
			// 如果是第一个合同, 合同起始日前一天为商户上一结算日
			//List<ConsumerContract> contractList = getConsumerContractList(dto);
			//if (contractList == null || contractList.size() == 0) {
				// 判断合同是收单机构还是商户，ContractType=1为收单机构，2为商户。
				// 计算首次结算日
				settleDateNext = settlePeriodRuleService.getSettleDateNext(dto
						.getContractStartDate(), dto.getRuleNo());
				if (null != settleDateNext && !"".equals(settleDateNext)
						&& null != dto.getContractEndDate()
						&& !"".equals(dto.getContractEndDate().trim())) {
					if (Integer.parseInt(settleDateNext) > Integer.parseInt(dto
							.getContractEndDate())) {
						throw new BizServiceException("添加结算周期规则的首次结算日大于合同的截止日期");
					}
				}
				if (dto.getContractType().equals("2")) {
					MerchantDTO mDto = new MerchantDTO();
					mDto.setEntityId(dto.getContractBuyer());
					mDto.setFatherEntityId(dto.getContractSeller());
					mDto.setLoginUserId(dto.getLoginUserId());
					merchantService.updateConMerchant(mDto);

					dto.setSettlDtFirst(settleDateNext);
					dto.setSettlDtPre(DateUtil
							.date2String(DateUtil.addDay(dto.getContractStartDateDate(), -1)));
					dto.setSettlDtNext(settleDateNext);
					dto.setSettleDateNext(settleDateNext);
				}
			//}
			ConsumerContract contract = new ConsumerContract();
			ReflectionUtil.copyProperties(dto, contract);
			String id = commonsDAO
					.getNextValueOfSequence("TB_CONSUMER_CONTRACT");
			contract.setConsumerContractId(id);
			contract.setContractCreateDate(DateUtil.date2String(new Date()));
			contract.setCreateTime(DateUtil.getCurrentTime());
			contract.setCreateUser(dto.getLoginUserId());
			contract.setModifyTime(DateUtil.getCurrentTime());
			contract.setModifyUser(dto.getLoginUserId());
			contract.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			consumerContractDAO.insert(contract);
			dto.setConsumerContractId(id);

			return dto;
		} catch (BizServiceException e1) {
			throw e1;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("新增合同失败！");
		}
	}

	public CommonsDAO getCommonsDAO() {
		return commonsDAO;
	}

	public void setCommonsDAO(CommonsDAO commonsDAO) {
		this.commonsDAO = commonsDAO;
	}

	public ConsumerContractDAO getConsumerContractDAO() {
		return consumerContractDAO;
	}

	public void setConsumerContractDAO(ConsumerContractDAO consumerContractDAO) {
		this.consumerContractDAO = consumerContractDAO;
	}

	public ConsumerContractDTO viewMchntConsumerContractDTO(
			ConsumerContractDTO dto) throws BizServiceException {
		try {
			ConsumerContract consumerContract = consumerContractDAO
					.selectByPrimaryKey(dto.getConsumerContractId());
			ReflectionUtil.copyProperties(consumerContract, dto);
			SettlePeriodRule settlePeriodRule = settlePeriodRuleDAO
					.selectByPrimaryKey(dto.getRuleNo());
			dto.setRuleName(settlePeriodRule.getRuleName());
			if (!dto.getContractType().equals("")
					&& dto.getContractType().equals(
							DataBaseConstant.CONTRACT_CONSUMER)) {
				ConsumerKey consumerKey = new ConsumerKey();
				consumerKey.setEntityId(dto.getContractBuyer());
				consumerKey.setFatherEntityId(dto.getContractSeller());
				Consumer consor = consumerDAO.selectByPrimaryKey(consumerKey);
				if (consor != null)
					dto.setMerchantName(consor.getConsumerName());
			} else {
				MerchantKey key = new MerchantKey();
				key.setEntityId(dto.getContractBuyer());
				key.setFatherEntityId(dto.getContractSeller());
				Merchant merchant = merchantServiceDAO.selectByPrimaryKey(key);
				if (merchant != null) {
					dto.setMerchantName(merchant.getMerchantName());
					dto.setMerchantCode(merchant.getMerchantCode());
				}
			}
			CaclDspExample caclDspExample = new CaclDspExample();

			//机构号不足八位，补足八位
	        String entityId = StringUtils.rpad(dto.getContractSeller(),8," ");
			caclDspExample.createCriteria().andRecUpdUsrIdEqualTo(entityId).andDataStatNotEqualTo(
					DataBaseConstant.RULE_STATE_DELETE);
			List<CaclDsp> lstCaclDsp = caclDspDAO
					.selectByExample(caclDspExample);
			List<CaclDspDTO> caclDspDTOList = new ArrayList<CaclDspDTO>();
			for (CaclDsp obj : lstCaclDsp) {
				CaclDspDTO dtoObj = new CaclDspDTO();
				ReflectionUtil.copyProperties(obj, dtoObj);
				dtoObj.setRecCrtTsString(DateUtil.dateToSting_(dtoObj
						.getRecCrtTs()));
				if (dtoObj.getDataStat().equals("0")) {
					dtoObj.setDataStat("删除");

				}
				if (dtoObj.getDataStat().equals("1")) {
					dtoObj.setDataStat("新建");

				}
				if (dtoObj.getDataStat().equals("2")) {
					dtoObj.setDataStat("启用");

				}

				caclDspDTOList.add(dtoObj);
			}

			dto.setCaclDspDTOs(caclDspDTOList);

			return dto;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询合同失败！");
		}

	}

	@SuppressWarnings("unchecked")
	public ConsumerContractDTO viewConsumerContractDTO(ConsumerContractDTO dto)
			throws BizServiceException {
		try {
			ConsumerContract consumerContract = consumerContractDAO
					.selectByPrimaryKey(dto.getConsumerContractId());
			ReflectionUtil.copyProperties(consumerContract, dto);
			SettlePeriodRule settlePeriodRule = settlePeriodRuleDAO
					.selectByPrimaryKey(dto.getRuleNo());
			dto.setRuleName(settlePeriodRule.getRuleName());
			if (!dto.getContractType().equals("")
					&& dto.getContractType().equals(
							DataBaseConstant.CONTRACT_CONSUMER)) {
				ConsumerKey consumerKey = new ConsumerKey();
				consumerKey.setEntityId(dto.getContractBuyer());
				consumerKey.setFatherEntityId(dto.getContractSeller());
				Consumer consor = consumerDAO.selectByPrimaryKey(consumerKey);
				if (consor != null)
					dto.setMerchantName(consor.getConsumerName());
			} else {
				MerchantKey key = new MerchantKey();
				key.setEntityId(dto.getContractBuyer());
				key.setFatherEntityId(dto.getContractSeller());
				Merchant merchant = merchantServiceDAO.selectByPrimaryKey(key);
				if (merchant != null) {
					dto.setMerchantName(merchant.getMerchantName());
					dto.setMerchantCode(merchant.getMerchantCode());
				}
			}
			
			
//			AcctypeContractExample example = new AcctypeContractExample();
//			example.createCriteria().andConsumerContractIdEqualTo(
//					dto.getConsumerContractId());
//			List<AcctypeContract> conList = acctypeContractDAO.selectByExample(example);
			
			List<AccTypeContractDTO> conList = (List<AccTypeContractDTO>) commonsDAO
					.queryForList(
							"ACCTYPE_CONTRACT.selectAccTypeByConsumerContraId",
							dto.getConsumerContractId());
			
			List<AccTypeContractDTO> accDTOList = new ArrayList<AccTypeContractDTO>();
			if (null != conList) {
				if (conList.size() > 0) {
					for (AccTypeContractDTO a : conList) {
						AccTypeContractDTO o = new AccTypeContractDTO();
						ReflectionUtil.copyProperties(a, o);
						CaclDspExample caclDspExample = new CaclDspExample();
						caclDspExample.createCriteria().andDiscCdEqualTo(
								o.getRuleNo());
						List<CaclDsp> caclDsps = caclDspDAO
								.selectByExample(caclDspExample);
						if (null != caclDsps) {
							if (caclDsps.size() > 0) {

								CaclDsp caclDsp = caclDspDAO.selectByExample(
										caclDspExample).get(0);
								// SerivceFeeRuleExample sExample=new
								// SerivceFeeRuleExample();
								// sExample.createCriteria().andRuleNoEqualTo(o.getRuleNo());
								// SerivceFeeRule
								// rule=serviceFeeRuleDAO.selectByExample(sExample).get(0);
								o.setRuleName(caclDsp.getCaclName());
							}
						}

						accDTOList.add(o);
					}
				}
			}
			dto.setAccTypeContractDTOList(accDTOList);

			/** 查询收单机构的交易权限 **/
			InsTransPrivExample insTransPrivExample = new InsTransPrivExample();
			if (dto.getContractBuyer().length() < 10) {
				insTransPrivExample.createCriteria().andInsIdCdEqualTo(
						"0" + dto.getContractBuyer().length()
								+ dto.getContractBuyer());

			} else {
				insTransPrivExample.createCriteria().andInsIdCdEqualTo(
						dto.getContractBuyer().length()
								+ dto.getContractBuyer());

			}

			List<InsTransPriv> insTransPrivs = insTransPrivDAO
					.selectByExample(insTransPrivExample);
			List<String> trandIds = new ArrayList<String>();
			for (InsTransPriv insTransPriv : insTransPrivs) {
				trandIds.add(insTransPriv.getTransId());

			}
			if (trandIds.size() <= 0) {
				trandIds.add(" ");
			}
			dto.setTransId(trandIds);
			return dto;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询收单合同失败");
		}
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public AcctypeContractDAO getAcctypeContractDAO() {
		return acctypeContractDAO;
	}

	public void setAcctypeContractDAO(AcctypeContractDAO acctypeContractDAO) {
		this.acctypeContractDAO = acctypeContractDAO;
	}

	public SerivceFeeRuleDAO getServiceFeeRuleDAO() {
		return serviceFeeRuleDAO;
	}

	public void setServiceFeeRuleDAO(SerivceFeeRuleDAO serviceFeeRuleDAO) {
		this.serviceFeeRuleDAO = serviceFeeRuleDAO;
	}

	public void updateConsumerContract(ConsumerContractDTO dto)
			throws BizServiceException {
		try {
			chkMchntContract(dto);
			if (!flag) {
				throw new BizServiceException("更新后的合同的开始日期必须大于上一合同的开始日期");
			}
			ConsumerContract contract = consumerContractDAO
					.selectByPrimaryKey(dto.getConsumerContractId());
			contract.setContractStartDate(dto.getContractStartDate());
			contract.setClearTp(dto.getClearTp());
			contract.setContractEndDate(dto.getContractEndDate());
			contract.setModifyTime(DateUtil.getCurrentTime());
			consumerContractDAO.updateByPrimaryKey(contract);
			if (dto.getContractType()
					.equals(DataBaseConstant.CONTRACT_CONSUMER)) {
				
				/* 收单机构交易权限的控制 */

				InsTransPrivExample insTransPrivExample = new InsTransPrivExample();
				if (dto.getContractBuyer().length() < 10) {
					insTransPrivExample.createCriteria().andInsIdCdEqualTo(
							"0"+ dto.getContractBuyer().length()
									+ dto.getContractBuyer());

				} else {
					insTransPrivExample.createCriteria().andInsIdCdEqualTo(
							dto.getContractBuyer().length()
									+ dto.getContractBuyer());

				}
				List<InsTransPriv> insTransPrivs = insTransPrivDAO
						.selectByExample(insTransPrivExample);

				/* 往c后台那边的机构交易权限中添加信息 */
				if (null != dto.getTransId()) {

					if (null != insTransPrivs && insTransPrivs.size() > 0) {
						insTransPrivDAO.deleteByExample(insTransPrivExample);
						InsTransPriv insTransPriv;

						for (String transId : dto.getTransId()) {
							insTransPriv = new InsTransPriv();
							if (dto.getContractBuyer().length() < 10) {
								insTransPriv.setInsIdCd("0"
										+ dto.getContractBuyer().length()
										+ dto.getContractBuyer());
							} else {
								insTransPriv.setInsIdCd(dto.getContractBuyer()
										.length()
										+ dto.getContractBuyer());
							}
							insTransPriv.setTransId(transId);
							insTransPriv.setFwdPriv("1");
							insTransPriv.setRcvPriv("1");
							insTransPriv.setRecUpdUsrId(dto.getLoginUserId());
							insTransPriv.setRecUpdTs(DateUtil
									.getCurrentDateAndTime());
							insTransPriv.setRecCrtTs(DateUtil
									.getCurrentDateAndTime());
							insTransPrivDAO.insert(insTransPriv);
						}
					} else {
						InsTransPriv insTransPriv;
						for (String transId : dto.getTransId()) {
							insTransPriv = new InsTransPriv();
							if (dto.getContractBuyer().length() < 10) {
								insTransPriv.setInsIdCd("0"
										+ dto.getContractBuyer().length()
										+ dto.getContractBuyer());
							} else {
								insTransPriv.setInsIdCd(dto.getContractBuyer()
										.length()
										+ dto.getContractBuyer());
							}
							insTransPriv.setTransId(transId);
							insTransPriv.setFwdPriv("1");
							insTransPriv.setRcvPriv("1");
							insTransPriv.setRecUpdUsrId(dto.getLoginUserId());
							insTransPriv.setRecUpdTs(DateUtil
									.getCurrentDateAndTime());
							insTransPriv.setRecCrtTs(DateUtil
									.getCurrentDateAndTime());
							insTransPrivDAO.insert(insTransPriv);

						}

					}
					
				}
			}
		} catch (BizServiceException e1) {
			throw e1;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("更新合同失败！");
		}
	}

	// 在新增合同时，查询合同买方的所有合同
	public List<ConsumerContract> getConsumerContractList(
			ConsumerContractDTO dto) throws BizServiceException {
		try {
			ConsumerContractExample example = new ConsumerContractExample();
			example.createCriteria().andContractBuyerEqualTo(
					dto.getContractBuyer()).andContractSellerEqualTo(
					dto.getContractSeller()).andDataStateEqualTo(
					DataBaseConstant.DATA_STATE_NORMAL).andContractTypeEqualTo(
					dto.getContractType());
			example.setOrderByClause("CONSUMER_CONTRACT_ID");
			List<ConsumerContract> contractList = consumerContractDAO
					.selectByExample(example);
			return contractList;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询合同失败！");
		}
	}

	// 在更新合同时，查询合同买方的所有合同（去除自己）
	public List<ConsumerContract> getConsumerContractUpdateList(
			ConsumerContractDTO dto) throws BizServiceException {
		try {
			ConsumerContractExample example = new ConsumerContractExample();
			example.createCriteria().andContractBuyerEqualTo(
					dto.getContractBuyer()).andContractSellerEqualTo(
					dto.getContractSeller()).andConsumerContractIdNotEqualTo(
					dto.getConsumerContractId()).andContractTypeEqualTo(
					dto.getContractType()).andDataStateEqualTo(
					DataBaseConstant.DATA_STATE_NORMAL);
			example.setOrderByClause("CONSUMER_CONTRACT_ID");
			List<ConsumerContract> contractList = consumerContractDAO
					.selectByExample(example);
			return contractList;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询合同失败！");
		}
	}

	public void chkMchntContract(ConsumerContractDTO dto)
			throws BizServiceException {
		List<ConsumerContract> consumerContractList;
		// 新增
		if (dto.getConsumerContractId() == null
				|| "".equals(dto.getConsumerContractId().trim())) {
			consumerContractList = getConsumerContractList(dto);
		} else {
			// 更新
			consumerContractList = getConsumerContractUpdateList(dto);
		}
		if (consumerContractList != null && consumerContractList.size() > 0) {
			ConsumerContract consumerContract = new ConsumerContract();
			consumerContract = consumerContractList.get(consumerContractList
					.size() - 1);
			Date dtoStartDate = dto.getContractStartDateDate();
			Date contractStartDate = DateUtil
					.string2Dateyyyymmdd(consumerContract
							.getContractStartDate());
			if (dtoStartDate.compareTo(contractStartDate) <= 0) {
				flag = false;
			} else {
				flag = true;
			}
			// 符合条件的情况
			if (flag) {
				// 上一个合同的结束日期大于当前要添加合同的开始日期时,则把上一个合同的结束日期置为新添加合同的前一天
				if (consumerContract.getContractEndDate() == null
						|| consumerContract.getContractEndDate().equals("")
						        ||(consumerContract.getContractEndDate() != null 
						                && !consumerContract.getContractEndDate().equals("") 
						                        && DateUtil.string2Dateyyyymmdd(
						                                consumerContract.getContractEndDate())
						                                        .compareTo(dtoStartDate) >= 0)) {
					/*修改上一个合同的下个结算日为合同开始日*/
					consumerContract.setSettlDtNext(consumerContract.getContractStartDate());
					consumerContract.setContractEndDate(DateUtil
							.date2String(DateUtil.addDay(dtoStartDate, -1)));
					consumerContract.setModifyUser(dto.getLoginUserId());
					consumerContract.setModifyTime(DateUtil.getCurrentTime());
					try {
						consumerContractDAO
								.updateByPrimaryKey(consumerContract);
					} catch (Exception e) {
						this.logger.error(e.getMessage());
						throw new BizServiceException("更新合同失败！");
					}
				}
			}
		} else {
			flag = true;
		}
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public MerchantServiceDAO getMerchantServiceDAO() {
		return merchantServiceDAO;
	}

	public void setMerchantServiceDAO(MerchantServiceDAO merchantServiceDAO) {
		this.merchantServiceDAO = merchantServiceDAO;
	}

	public PageQueryDAO getPageQueryDAO() {
		return pageQueryDAO;
	}

	public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
		this.pageQueryDAO = pageQueryDAO;
	}

	/*
	 * 删除商户合同 2011-03-24 By Yifeng.Shi
	 */
	public void deleteConsumerContract(ConsumerContractDTO dto)
			throws BizServiceException {
		try {
			// 在合同的起止日期内存在交易，则合同不能删除

			// 获取商户合同具体信息
			ConsumerContractExample example = new ConsumerContractExample();
			example.createCriteria().andContractBuyerEqualTo(
					dto.getContractBuyer()).andContractSellerEqualTo(
					dto.getDefaultEntityId());
			example.setOrderByClause("CONTRACT_START_DATE");
			List<ConsumerContract> lstConsumerContractDTOs = consumerContractDAO
					.selectByExample(example);

			// 最新的一个合同
			ConsumerContract lastMerchantContract = lstConsumerContractDTOs
					.get(lstConsumerContractDTOs.size() - 1);

			// 获取商户编号
			MerchantExample merchantExample = new MerchantExample();
			merchantExample.createCriteria().andEntityIdEqualTo(
					dto.getContractBuyer());
			List<Merchant> lstMerchants = merchantServiceDAO
					.selectByExample(merchantExample);

			// 封装查询报文对象
			MerchantTxnQueryDTO merchantTxnQueryDTO = new MerchantTxnQueryDTO();
			ConsumerContract currentConsumerContract = new ConsumerContract();
			for (ConsumerContract consumerContract : lstConsumerContractDTOs) {
				if (consumerContract.getConsumerContractId().equals(
						dto.getConsumerContractId())) {
					currentConsumerContract = consumerContract;
					merchantTxnQueryDTO.setStartDate(consumerContract
							.getContractStartDate());
					merchantTxnQueryDTO.setEndDate(consumerContract
							.getContractEndDate());
					merchantTxnQueryDTO.setMerchantCode(lstMerchants.get(0)
							.getMerchantCode());
					break;
				}
			}
			// 查询交易
			PageDataDTO pageDataDTO = merchantService
					.merchantTxnQuery(merchantTxnQueryDTO);
			if (pageDataDTO != null && pageDataDTO.getData() != null) {
				throw new BizServiceException("该商户合同在有效期内存在交易,不能删除!");
			}

			if (!dto.getConsumerContractId().equals(
					lastMerchantContract.getConsumerContractId())) {
				throw new BizServiceException("该合同不是最新合同,不能删除!");
			}
			if (checkValidDate(currentConsumerContract.getContractEndDate())) {
				throw new BizServiceException("该合同还没有过期,不能删除!");
			}

			// 删除当前合同
			consumerContractDAO.deleteByPrimaryKey(currentConsumerContract
					.getConsumerContractId());

			// 如果他的上一合同未过期，更新上一合同的结束日期为null
			if (lstConsumerContractDTOs.size() > 1) {
				ConsumerContract operateConsumerContract = lstConsumerContractDTOs
						.get(lstConsumerContractDTOs.size() - 2);
				operateConsumerContract.setContractEndDate(null);
				consumerContractDAO.updateByPrimaryKey(operateConsumerContract);
			}
		} catch (BizServiceException b) {
			throw b;
		}

		catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("删除合同失败！");
		}
	}

	private boolean checkValidDate(String contractValidDate) {
		boolean result = false;
		String todayDate = new SimpleDateFormat("yyyy-MM-dd")
				.format(new Date());
		todayDate = todayDate.substring(0, 4) + todayDate.substring(5, 7)
				+ todayDate.substring(8, 10);

		if (Integer.valueOf(todayDate) < Integer.valueOf(contractValidDate)) {
			result = true;
		}
		return result;
	}
}
