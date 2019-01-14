package com.huateng.univer.issuer.issuercontract.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.issuer.dto.issuerContract.LoyaltyAcctypeContractDTO;
import com.allinfinance.univer.issuer.dto.issuerContract.LoyaltyContractDTO;
import com.allinfinance.univer.issuer.dto.issuerContract.LoyaltyContractQueryDTO;
import com.allinfinance.univer.issuer.dto.issuerContract.LoyaltyProdContractDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.LoyaltyAcctypeContractDAO;
import com.huateng.framework.ibatis.dao.LoyaltyContractDAO;
import com.huateng.framework.ibatis.dao.LoyaltyProdContractDAO;
import com.huateng.framework.ibatis.model.LoyaltyAcctypeContract;
import com.huateng.framework.ibatis.model.LoyaltyContract;
import com.huateng.framework.ibatis.model.LoyaltyContractExample;
import com.huateng.framework.ibatis.model.LoyaltyProdContract;
import com.huateng.framework.ibatis.model.LoyaltyProdContractExample;
import com.huateng.framework.util.Amount;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.framework.util.StringUtil;
import com.huateng.univer.issuer.issuer.service.impl.IssuerServiceImpl;
import com.huateng.univer.issuer.issuercontract.dao.LoyaltyContractServiceDAO;
import com.huateng.univer.issuer.issuercontract.service.LoyaltyContractService;
import com.huateng.univer.issuer.issuercontract.service.LoyaltyProdContractService;

public class LoyaltyContractServiceImpl implements LoyaltyContractService {

	Logger logger = Logger.getLogger(IssuerServiceImpl.class);
	/**
	 * 分页查询DAO
	 */
	private PageQueryDAO pageQueryDAO;
	/**
	 * 公共工具类DAO
	 */
	private CommonsDAO commonsDAO;

	private LoyaltyContractDAO loyaltyContractDAO;

	private LoyaltyProdContractService loyaltyProdContractService;

	private LoyaltyContractServiceDAO loyaltyContractServiceDAO;

	private LoyaltyProdContractDAO loyaltyProdContractDAO;

	private LoyaltyAcctypeContractDAO loyaltyAcctypeContractDAO;

	public LoyaltyAcctypeContractDAO getLoyaltyAcctypeContractDAO() {
		return loyaltyAcctypeContractDAO;
	}

	public void setLoyaltyAcctypeContractDAO(
			LoyaltyAcctypeContractDAO loyaltyAcctypeContractDAO) {
		this.loyaltyAcctypeContractDAO = loyaltyAcctypeContractDAO;
	}

	public LoyaltyProdContractDAO getLoyaltyProdContractDAO() {
		return loyaltyProdContractDAO;
	}

	public void setLoyaltyProdContractDAO(
			LoyaltyProdContractDAO loyaltyProdContractDAO) {
		this.loyaltyProdContractDAO = loyaltyProdContractDAO;
	}

	public LoyaltyContractServiceDAO getLoyaltyContractServiceDAO() {
		return loyaltyContractServiceDAO;
	}

	public void setLoyaltyContractServiceDAO(
			LoyaltyContractServiceDAO loyaltyContractServiceDAO) {
		this.loyaltyContractServiceDAO = loyaltyContractServiceDAO;
	}

	public LoyaltyProdContractService getLoyaltyProdContractService() {
		return loyaltyProdContractService;
	}

	public void setLoyaltyProdContractService(
			LoyaltyProdContractService loyaltyProdContractService) {
		this.loyaltyProdContractService = loyaltyProdContractService;
	}

	public LoyaltyContractDAO getLoyaltyContractDAO() {
		return loyaltyContractDAO;
	}

	public void setLoyaltyContractDAO(LoyaltyContractDAO loyaltyContractDAO) {
		this.loyaltyContractDAO = loyaltyContractDAO;
	}

	public PageQueryDAO getPageQueryDAO() {
		return pageQueryDAO;
	}

	public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
		this.pageQueryDAO = pageQueryDAO;
	}

	public CommonsDAO getCommonsDAO() {
		return commonsDAO;
	}

	public void setCommonsDAO(CommonsDAO commonsDAO) {
		this.commonsDAO = commonsDAO;
	}

	public PageDataDTO inqueryIssuerContract(
			LoyaltyContractQueryDTO loyaltyContractQueryDTO)
			throws BizServiceException {
		try {
			loyaltyContractQueryDTO.setSort("desc");
			loyaltyContractQueryDTO.setSortFieldName("loyaltyContractId");
			if (null != loyaltyContractQueryDTO.getLoyaltyContractId()) {
				loyaltyContractQueryDTO
						.setLoyaltyContractId(loyaltyContractQueryDTO
								.getLoyaltyContractId().trim());
				if ("".equals(loyaltyContractQueryDTO.getLoyaltyContractId())) {
					loyaltyContractQueryDTO.setLoyaltyContractId(null);
				}
			}

			if (null != loyaltyContractQueryDTO.getCustomerName()) {
				loyaltyContractQueryDTO.setCustomerName(loyaltyContractQueryDTO
						.getCustomerName().trim());
				if ("".equals(loyaltyContractQueryDTO.getCustomerName())) {
					loyaltyContractQueryDTO.setCustomerName(null);
				}
			}
			if (null != loyaltyContractQueryDTO.getContractState()) {
				loyaltyContractQueryDTO
						.setContractState(loyaltyContractQueryDTO
								.getContractState().trim());
				if ("".equals(loyaltyContractQueryDTO.getContractState())) {
					loyaltyContractQueryDTO.setContractState(null);
				}
			}
			// if(loyaltyContractQueryDTO.getWebPayStat().trim()==""){
			// loyaltyContractQueryDTO.setWebPayStat(null);
			// }
			PageDataDTO pdd = pageQueryDAO.query(
					"LOYALTYCONTRACT.selectContract", loyaltyContractQueryDTO);
			return pdd;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询发行机构合同信息失败~！");
		}
	}

	public LoyaltyContractDTO insertIssuerContract(
			LoyaltyContractDTO loyaltyContractDTO) throws BizServiceException {
		try {
			// 合同双方只能签一个发行机构合同
			LoyaltyContractExample example = new LoyaltyContractExample();
			example.createCriteria().andContractBuyerEqualTo(
					loyaltyContractDTO.getContractBuyer())
					.andContractSellerEqualTo(
							loyaltyContractDTO.getDefaultEntityId())
					.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL)
					.andContractStateEqualTo("1")
					.andExpiryDateGreaterThanOrEqualTo(
							DateUtil.getCurrentDateStr());
			if (loyaltyContractDAO.countByExample(example) > 0) {
				throw new BizServiceException("双方已签过合同，不能重复签合同！");
			}

			LoyaltyContract loyaltyContract = new LoyaltyContract();
			ReflectionUtil.copyProperties(loyaltyContractDTO, loyaltyContract);

			loyaltyContract.setLoyaltyContractId(commonsDAO
					.getNextValueOfSequence("TB_LOYALTY_CONTRACT"));
			if (StringUtil.isNotEmpty(loyaltyContractDTO.getExpiryDate())) {
				loyaltyContract.setExpiryDate(DateUtil
						.getFormatTime(loyaltyContract.getExpiryDate()));
			}
			loyaltyContract.setContractSeller(loyaltyContractDTO
					.getDefaultEntityId());
			loyaltyContract.setCreateTime(DateUtil.getCurrentTime());
			loyaltyContract.setCreateUser(loyaltyContractDTO.getLoginUserId());
			loyaltyContract.setModifyTime(DateUtil.getCurrentTime());
			loyaltyContract.setModifyUser(loyaltyContractDTO.getLoginUserId());
			loyaltyContract.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			loyaltyContract.setClearTp(loyaltyContractDTO.getClearTp());
			loyaltyContractDAO.insert(loyaltyContract);
			ReflectionUtil.copyProperties(loyaltyContract, loyaltyContractDTO);

			return loyaltyContractDTO;
		} catch (BizServiceException be) {
			throw be;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加发行机构合同失败！");
		}
	}

	public LoyaltyContractDTO editIssuerContract(
			LoyaltyContractDTO loyaltyContractDTO) throws BizServiceException {
		try {
			LoyaltyContract loyaltyContract = new LoyaltyContract();
			loyaltyContract = loyaltyContractDAO
					.selectByPrimaryKey(loyaltyContractDTO
							.getLoyaltyContractId());
			ReflectionUtil.copyProperties(loyaltyContract, loyaltyContractDTO);
			// loyaltyContractDTO.setExpiryDate(DateUtil.getStringDate());
			if (StringUtil.isNotEmpty(loyaltyContractDTO.getExpiryDate())) {
				loyaltyContractDTO.setExpiryDate(DateUtil
						.dbFormatToDateFormat(loyaltyContract.getExpiryDate()));
			}
			LoyaltyContractDTO contractinfo = loyaltyContractServiceDAO
					.inqueryLoyaltyContractInfo(loyaltyContractDTO
							.getLoyaltyContractId());
			loyaltyContractDTO.setContractBuyerName(contractinfo
					.getContractBuyerName());
			loyaltyContractDTO.setRuleName(contractinfo.getRuleName());
			// 关联发行机构合同产品明细
			loyaltyContractDTO
					.setLoyaltyProdContractDTO(getProductContractDTOs(loyaltyContractDTO
							.getLoyaltyContractId()));

			return loyaltyContractDTO;
		} catch (BizServiceException be) {
			throw be;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查看发行机构合同失败！");
		}
	}

	private List<LoyaltyProdContractDTO> getProductContractDTOs(
			String issuerContractId) throws BizServiceException {
		List<LoyaltyProdContractDTO> proDTOs = loyaltyProdContractService
				.inquery(issuerContractId);
		return proDTOs;
	}

	public void insetProductAndService(
			LoyaltyProdContractDTO loyaltyProdContractDTO)
			throws BizServiceException {
		try {
			// 判断产品在合同中是否已存在
			LoyaltyProdContractExample example = new LoyaltyProdContractExample();
			example.createCriteria().andProductIdEqualTo(
					loyaltyProdContractDTO.getProductId())
					.andLoyaltyContractIdEqualTo(
							loyaltyProdContractDTO.getLoyaltyContractId())
					.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);

			if (loyaltyProdContractDAO.countByExample(example) > 0) {
				throw new BizServiceException("产品："
						+ loyaltyProdContractDTO.getProductId()
						+ " 在合同中已存在，不能重复添加！");
			}
			LoyaltyProdContract productContract = new LoyaltyProdContract();
			ReflectionUtil.copyProperties(loyaltyProdContractDTO,
					productContract);
			productContract.setId(commonsDAO
					.getNextValueOfSequence("TB_LOYALTY_PROD_CONTRACT"));
			productContract.setCardFee(Amount.getDataBaseAmount(productContract
					.getCardFee()));
			productContract.setAnnualFee(Amount
					.getDataBaseAmount(productContract.getAnnualFee()));
			productContract.setCreateUser(loyaltyProdContractDTO
					.getLoginUserId());
			productContract.setCreateTime(DateUtil.getCurrentTime());
			productContract.setModifyUser(loyaltyProdContractDTO
					.getLoginUserId());
			productContract.setModifyTime(DateUtil.getCurrentTime());
			productContract.setDataState(DataBaseConstant.DATA_STATE_NORMAL);

			// 添加营销合同产品明细
			loyaltyProdContractDAO.insert(productContract);
			// 添加营销合同服务明细
			insertSellerAcctypeContract(loyaltyProdContractDTO);
		} catch (BizServiceException be) {
			throw be;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加发行机构合同产品明细失败！");
		}

	}

	/** 添加营销合同服务明细 */
	private void insertSellerAcctypeContract(
			LoyaltyProdContractDTO loyaltyProdContractDTO)
			throws BizServiceException {
		try {
			List<LoyaltyAcctypeContractDTO> accDTOs = loyaltyProdContractDTO
					.getAccDTOs();
			List<LoyaltyAcctypeContract> accList = new ArrayList<LoyaltyAcctypeContract>();
			for (LoyaltyAcctypeContractDTO accDTO : accDTOs) {
				LoyaltyAcctypeContract acc = new LoyaltyAcctypeContract();
				ReflectionUtil.copyProperties(accDTO, acc);

				acc.setId(commonsDAO
						.getNextValueOfSequence("TB_LOYALTY_ACCTYPE_CONTRACT"));
				acc.setCreateUser(loyaltyProdContractDTO.getLoginUserId());
				acc.setCreateTime(DateUtil.getCurrentTime());
				acc.setModifyUser(loyaltyProdContractDTO.getLoginUserId());
				acc.setModifyTime(DateUtil.getCurrentTime());
				accList.add(acc);
			}
			commonsDAO.batchUpdate(
					"TB_LOYALTY_ACCTYPE_CONTRACT.abatorgenerated_insert",
					accList);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加发行机构合同帐户明细失败！");
		}
	}

	public void editIssuerContractProduct(
			LoyaltyProdContractDTO loyaltyProdContractDTO)
			throws BizServiceException {
		try {
			LoyaltyProdContract loyaltyProdContract = loyaltyProdContractDAO
					.selectByPrimaryKey(loyaltyProdContractDTO.getId());
			loyaltyProdContract.setCardFee(Amount
					.getDataBaseAmount(loyaltyProdContractDTO.getCardFee()));
			loyaltyProdContract.setAnnualFee(Amount
					.getDataBaseAmount(loyaltyProdContractDTO.getAnnualFee()));
			loyaltyProdContract.setModifyTime(DateUtil.getCurrentTime());
			loyaltyProdContract.setModifyUser(loyaltyProdContractDTO
					.getLoginUserId());
			loyaltyProdContractDAO.updateByPrimaryKey(loyaltyProdContract);

		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("修改合同产品明细，卡费、年费失败！");
		}

	}

	public LoyaltyProdContractDTO viewIssuerContractProduct(
			LoyaltyProdContractDTO loyaltyProdContractDTO)
			throws BizServiceException {
		try {
			LoyaltyProdContract loyaltyProdContract = loyaltyProdContractDAO
					.selectByPrimaryKey(loyaltyProdContractDTO.getId());
			LoyaltyProdContractDTO prodContractDTO = new LoyaltyProdContractDTO();
			ReflectionUtil.copyProperties(loyaltyProdContract, prodContractDTO);
			prodContractDTO.setCardFee(Amount
					.getReallyAmount(loyaltyProdContract.getCardFee()));
			prodContractDTO.setAnnualFee(Amount
					.getReallyAmount(loyaltyProdContract.getAnnualFee()));
			return prodContractDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查看合同产品明细，卡费、年费失败！");
		}
	}

	public void editIssuerContractService(
			LoyaltyAcctypeContractDTO loyaltyAcctypeContractDTO)
			throws BizServiceException {
		try {
			LoyaltyAcctypeContract loyaltyAcctypeContract = loyaltyAcctypeContractDAO
					.selectByPrimaryKey(loyaltyAcctypeContractDTO.getId());
			loyaltyAcctypeContract.setRuleNo(loyaltyAcctypeContractDTO
					.getRuleNo());
			loyaltyAcctypeContract.setModifyTime(DateUtil.getCurrentTime());
			loyaltyAcctypeContract.setModifyUser(loyaltyAcctypeContractDTO
					.getModifyUser());
			loyaltyAcctypeContractDAO
					.updateByPrimaryKey(loyaltyAcctypeContract);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("修改合同帐户明细失败！");
		}

	}

	public void updateIssuerContractService(
			LoyaltyContractDTO loyaltyContractDTO) throws BizServiceException {
		try {
			LoyaltyContract loyaltyContract = new LoyaltyContract();
			ReflectionUtil.copyProperties(loyaltyContractDTO, loyaltyContract);
			if (StringUtil.isNotEmpty(loyaltyContractDTO.getExpiryDate())) {
				loyaltyContract.setExpiryDate(DateUtil
						.getFormatTime(loyaltyContract.getExpiryDate()));
			}
			loyaltyContract.setModifyTime(DateUtil.getCurrentTime());
			loyaltyContract.setModifyUser(loyaltyContractDTO.getModifyUser());
			loyaltyContract.setClearTp(loyaltyContractDTO.getClearTp());
			loyaltyContractDAO.updateByPrimaryKeySelective(loyaltyContract);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("修改合同帐户明细失败！");
		}

	}

}
