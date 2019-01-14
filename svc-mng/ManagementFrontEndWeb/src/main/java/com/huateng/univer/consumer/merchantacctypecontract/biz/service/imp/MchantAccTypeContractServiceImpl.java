package com.huateng.univer.consumer.merchantacctypecontract.biz.service.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.allinfinance.univer.consumercontract.dto.AccTypeContractDTO;
import com.allinfinance.univer.consumercontract.dto.ConsumerContractDTO;
import com.allinfinance.univer.issuer.dto.service.ServiceDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.AcctypeContractDAO;
import com.huateng.framework.ibatis.dao.CaclDspDAO;
import com.huateng.framework.ibatis.dao.LoyaltyAcctypeContractDAO;
import com.huateng.framework.ibatis.dao.LoyaltyContractDAO;
import com.huateng.framework.ibatis.dao.ServiceDAO;
import com.huateng.framework.ibatis.model.AcctypeContract;
import com.huateng.framework.ibatis.model.AcctypeContractExample;
import com.huateng.framework.ibatis.model.CaclDsp;
import com.huateng.framework.ibatis.model.CaclDspExample;
import com.huateng.framework.ibatis.model.CaclDspKey;
import com.huateng.framework.ibatis.model.LoyaltyAcctypeContract;
import com.huateng.framework.ibatis.model.LoyaltyAcctypeContractExample;
import com.huateng.framework.ibatis.model.LoyaltyContract;
import com.huateng.framework.ibatis.model.LoyaltyContractExample;
import com.huateng.framework.ibatis.model.Service;
import com.huateng.framework.ibatis.model.ServiceExample;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.univer.consumer.merchantacctypecontract.biz.service.MchantAccTypeContractService;

public class MchantAccTypeContractServiceImpl implements
		MchantAccTypeContractService {
	Logger logger = Logger.getLogger(MchantAccTypeContractServiceImpl.class);

	private AcctypeContractDAO acctypeContractDAO;
	private LoyaltyAcctypeContractDAO loyaltyAcctypeContractDAO;
	private LoyaltyContractDAO loyaltyContractDAO;
	private ServiceDAO serviceDAO;
	private CaclDspDAO caclDspDAO;
	private CommonsDAO commonsDAO;

	public AcctypeContractDAO getAcctypeContractDAO() {
		return acctypeContractDAO;
	}

	public void setAcctypeContractDAO(AcctypeContractDAO acctypeContractDAO) {
		this.acctypeContractDAO = acctypeContractDAO;
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

	public CaclDspDAO getCaclDspDAO() {
		return caclDspDAO;
	}

	public void setCaclDspDAO(CaclDspDAO caclDspDAO) {
		this.caclDspDAO = caclDspDAO;
	}	

	public CommonsDAO getCommonsDAO() {
		return commonsDAO;
	}

	public void setCommonsDAO(CommonsDAO commonsDAO) {
		this.commonsDAO = commonsDAO;
	}

	public ConsumerContractDTO selectAccTypeContractDTO(ConsumerContractDTO dto)
			throws BizServiceException {
		getServiceDTOList(dto);
		return dto;
	}

	public void getServiceDTOList(ConsumerContractDTO dto)
			throws BizServiceException {
		try {
			List<String> contractServiceIdList = new ArrayList<String>();
			List<ServiceDTO> serviceDTOList = new ArrayList<ServiceDTO>();
			ServiceDTO serviceDTO = new ServiceDTO();
			// 当前机构(发卡机构或收单机构)与下级机构(收单机构或商户)之间的当签合同已经定义过的服务
			
//			AcctypeContractExample accExample = new AcctypeContractExample();
//			accExample.createCriteria().andConsumerContractIdEqualTo(dto.getConsumerContractId());
//			List<AcctypeContract> acctypeContractList = acctypeContractDAO.selectByExample(accExample);
			
			List<AccTypeContractDTO> acctypeContractList = (List<AccTypeContractDTO>)commonsDAO.queryForList("ACCTYPE_CONTRACT.selectAccTypeByConsumerContraId", dto.getConsumerContractId());
			
			List<AccTypeContractDTO> accDTOList = new ArrayList<AccTypeContractDTO>();
			if (acctypeContractList != null && acctypeContractList.size() > 0) {
				for (AccTypeContractDTO a : acctypeContractList) {
					contractServiceIdList.add(a.getAcctypeId());
					CaclDspExample caclDspExample = new CaclDspExample();
					AccTypeContractDTO o = new AccTypeContractDTO();
					ReflectionUtil.copyProperties(a, o);
					caclDspExample.createCriteria().andDiscCdEqualTo(
							o.getRuleNo());
					CaclDsp caclDsp = caclDspDAO
							.selectByExample(caclDspExample).get(0);
					o.setRuleName(caclDsp.getCaclName());
					accDTOList.add(o);

				}

			}
			// 判断合同类型 CONTRACT_MERCHANT 商户合同 else 收单合同
			if (dto.getContractType()
					.equals(DataBaseConstant.CONTRACT_MERCHANT)) {
				AcctypeContractExample aExample = new AcctypeContractExample();
				aExample.createCriteria().andContractBuyerEqualTo(
						dto.getContractSeller());
				List<AcctypeContract> aContractList = acctypeContractDAO
						.selectByExample(aExample);
				Map a = new HashMap();
				if (aContractList != null && aContractList.size() > 0) {
					for (AcctypeContract t : aContractList) {
						String result = (String) a.put(t.getAcctypeId(), "0");
						if (null == result) {
							if (!contractServiceIdList.contains(t
									.getAcctypeId())) {
								AccTypeContractDTO accTypeContractDTO = new AccTypeContractDTO();
								ReflectionUtil.copyProperties(t,
										accTypeContractDTO);
								accTypeContractDTO.setRuleNo("");
								accTypeContractDTO.setAcctypeContractId("");
								accDTOList.add(accTypeContractDTO);

								/*
								 * AccTypeContractDTO accTypeContractDTO=new
								 * AccTypeContractDTO();
								 * ReflectionUtil.copyProperties
								 * (t,accTypeContractDTO);
								 * accTypeContractDTO.setRuleName(caclDspDAO
								 * .selectByPrimaryKey(
								 * accTypeContractDTO.getRuleNo())
								 * .getCaclName());
								 * accDTOList.add(accTypeContractDTO);
								 */
							}
						}

					}
				}
			} else {
				// 自己的服务
				ServiceExample serviceExample = new ServiceExample();
				serviceExample.createCriteria().andEntityIdEqualTo(
						dto.getContractSeller()).andDataStateEqualTo(
						DataBaseConstant.DATA_STATE_NORMAL);
				List<Service> serviceList = new ArrayList<Service>();

				serviceList = serviceDAO.selectByExample(serviceExample);

				if (serviceList != null && serviceList.size() > 0) {
					for (Service s : serviceList) {
						if (!contractServiceIdList.contains(s.getServiceId())) {
							serviceDTO = new ServiceDTO();
							serviceDTO.setServiceId(s.getServiceId());
							serviceDTOList.add(serviceDTO);
							contractServiceIdList.add(s.getServiceId());
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

			dto.setAccTypeContractDTOList(accDTOList);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询服务失败");
		}
	}

	public ConsumerContractDTO selectAccTypeContDTOInVer(ConsumerContractDTO dto)
			throws BizServiceException {
		ConsumerContractDTO consumerContractDTO = getServiceDTOListInVerifier(dto);
		return consumerContractDTO;
	}

	public ConsumerContractDTO getServiceDTOListInVerifier(
			ConsumerContractDTO dto) throws BizServiceException {
		try {
			List<String> contractServiceIdList = new ArrayList<String>();
			// 当前机构(发卡机构或收单机构)与下级机构(收单机构或商户)之间的当签合同已经定义过的服务
/*			AcctypeContractExample accExample = new AcctypeContractExample();
			accExample.createCriteria().andConsumerContractIdEqualTo(
					dto.getConsumerContractId());
			List<AcctypeContract> acctypeContractList = acctypeContractDAO
					.selectByExample(accExample);*/
			List<AccTypeContractDTO> acctypeContractList =
				(List<AccTypeContractDTO>)commonsDAO.queryForList("ACCTYPE_CONTRACT.selectAccTypeByConsumerContraId", dto.getConsumerContractId());
			List<AccTypeContractDTO> accDTOList = new ArrayList<AccTypeContractDTO>();
			if (acctypeContractList != null && acctypeContractList.size() > 0) {
				for (AccTypeContractDTO a : acctypeContractList) {
					contractServiceIdList.add(a.getAcctypeId());
					CaclDspExample caclDspExample = new CaclDspExample();
					AccTypeContractDTO o = new AccTypeContractDTO();
					ReflectionUtil.copyProperties(a, o);
					caclDspExample.createCriteria().andDiscCdEqualTo(
							o.getRuleNo());
					CaclDsp caclDsp = caclDspDAO
							.selectByExample(caclDspExample).get(0);
					o.setRuleName(caclDsp.getCaclName());
					accDTOList.add(o);

				}
			}
			// 判断合同类型 CONTRACT_MERCHANT 商户合同 else 收单合同
			if (dto.getContractType()
					.equals(DataBaseConstant.CONTRACT_MERCHANT)) {
				/*AcctypeContractExample aExample = new AcctypeContractExample();
				aExample.createCriteria().andContractBuyerEqualTo(
						dto.getContractSeller());
				List<AcctypeContract> aContractList = acctypeContractDAO
						.selectByExample(aExample);*/
				List<AcctypeContract> aContractList = 
					(List<AcctypeContract>)commonsDAO.queryForList("ACCTYPE_CONTRACT.selectAccTypeByContractBuyer", dto.getContractSeller());
				Map a = new HashMap();
				if (aContractList != null && aContractList.size() > 0) {
					logger.debug("acctypecontract size:" + aContractList.size());
					for (AcctypeContract t : aContractList) {
						String result = (String) a.put(t.getAcctypeId(), "0");
						if (null == result) {

							if (!contractServiceIdList.contains(t
									.getAcctypeId())) {
								AccTypeContractDTO accTypeContractDTO = new AccTypeContractDTO();
								ReflectionUtil.copyProperties(t,
										accTypeContractDTO);
								CaclDspKey caclDspKey=new CaclDspKey();
								caclDspKey.setDiscCd(accTypeContractDTO.getRuleNo());
								caclDspKey.setRecUpdUsrId(dto.getContractSeller());
								accTypeContractDTO.setRuleName(caclDspDAO.selectByPrimaryKey(caclDspKey).getCaclName());
								accDTOList.add(accTypeContractDTO);
							}
						}

					}
				}
			}
			dto.setAccTypeContractDTOList(accDTOList);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询服务失败");
		}
		return dto;
	}

}
