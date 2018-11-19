package com.huateng.univer.seller.sellercontract.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.seller.customer.CustomerDTO;
import com.allinfinance.univer.seller.seller.dto.SellerQueryDTO;
import com.allinfinance.univer.seller.sellercontract.dto.SellerAcctypeContractDTO;
import com.allinfinance.univer.seller.sellercontract.dto.SellerContractDTO;
import com.allinfinance.univer.seller.sellercontract.dto.SellerContractQueryDTO;
import com.allinfinance.univer.seller.sellercontract.dto.SellerProductContractDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.dao.BaseDAO;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.CustomerDAO;
import com.huateng.framework.ibatis.dao.SellAcctypeContractDAO;
import com.huateng.framework.ibatis.dao.SellContractDAO;
import com.huateng.framework.ibatis.model.Customer;
import com.huateng.framework.ibatis.model.CustomerExample;
import com.huateng.framework.ibatis.model.SellAcctypeContract;
import com.huateng.framework.ibatis.model.SellAcctypeContractExample;
import com.huateng.framework.ibatis.model.SellContract;
import com.huateng.framework.ibatis.model.SellContractExample;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.framework.util.StringUtil;
import com.huateng.hstserver.frameworkUtil.Amount;
import com.huateng.univer.issuer.issuercontract.dao.LoyaltyContractServiceDAO;
import com.huateng.univer.seller.sellercontract.biz.service.SellerContractService;
import com.huateng.univer.seller.sellercontract.biz.service.SellerProductContractService;

/**
 * 营销机构合同service
 * 
 * @author xxl
 * 
 */
public class SellerContractServiceImpl implements SellerContractService {

	Logger logger = Logger.getLogger(this.getClass());
	// 营销合同DAO
	private SellContractDAO sellerContractDAO;
	// 营销服务合同DAO
	private SellAcctypeContractDAO sellAcctypeContractDAO;
	private SellerProductContractService sellerProductContractService;
	private LoyaltyContractServiceDAO loyaltyContractServiceDAO;
	private PageQueryDAO pageQueryDAO;
	private CommonsDAO commonsDAO;
	private BaseDAO baseDAO;
	private CustomerDAO customerDAO;

	public CustomerDAO getCustomerDAO() {
		return customerDAO;
	}

	public void setCustomerDAO(CustomerDAO customerDAO) {
		this.customerDAO = customerDAO;
	}

	public LoyaltyContractServiceDAO getLoyaltyContractServiceDAO() {
		return loyaltyContractServiceDAO;
	}

	public void setLoyaltyContractServiceDAO(
			LoyaltyContractServiceDAO loyaltyContractServiceDAO) {
		this.loyaltyContractServiceDAO = loyaltyContractServiceDAO;
	}

	public PageDataDTO inquery(SellerContractQueryDTO sellerContractQueryDTO)
			throws BizServiceException {
		try {
			if (sellerContractQueryDTO.getDefaultEntityId() != null
					&& !"".equals(sellerContractQueryDTO.getDefaultEntityId())) {
				sellerContractQueryDTO.setContractSeller(sellerContractQueryDTO
						.getDefaultEntityId());
			}
			if (null != sellerContractQueryDTO.getSellContractId()) {
				sellerContractQueryDTO.setSellContractId(sellerContractQueryDTO
						.getSellContractId().trim());
			}
			if (sellerContractQueryDTO.getContractState() != null
					&& sellerContractQueryDTO.getContractState().trim().equals("")) {
				sellerContractQueryDTO.setContractState(null);
			}
			if (sellerContractQueryDTO.getWebPayStat() != null
					&& sellerContractQueryDTO.getWebPayStat().trim().equals("")) {
				sellerContractQueryDTO.setWebPayStat(null);
			}
			PageDataDTO pageDataDTO = pageQueryDAO.query(
					"SELLERCONTRACT.selectSellerContract",
					sellerContractQueryDTO);
			return pageDataDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询营销机构合同失败！");
		}
	}

	/**
	 * 合同模板
	 * 
	 * @param sellerContractQueryDTO
	 * @return
	 * @throws BizServiceException
	 */
	public PageDataDTO inqueryMasterPlate(
			SellerContractQueryDTO sellerContractQueryDTO)
			throws BizServiceException {
		try {			
			PageDataDTO pageDataDTO = pageQueryDAO.query(
					"SELLERCONTRACT.selectMasterPlate", sellerContractQueryDTO);
			return pageDataDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询营销机构合同失败！");
		}
	}

	/**
	 * 查找发卡机构产品:自己产品+代发卡合同产品
	 */
	public PageDataDTO inqueryProductForIssuer(
			SellerContractQueryDTO sellerContractQueryDTO)
			throws BizServiceException {
		try {

			return pageQueryDAO.query("SELLERCONTRACT.inqueryProductForOrder",
					sellerContractQueryDTO);

		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询产品失败！");
		}
	}

	/**
	 * 查找代发卡产品
	 */
	public PageDataDTO inqueryProductForIssuerContract(
			SellerContractQueryDTO sellerContractQueryDTO)
			throws BizServiceException {
		try {

			return pageQueryDAO.query(
					"SELLERCONTRACT.inqueryProductForIssuerContract",
					sellerContractQueryDTO);

		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询产品失败！");
		}
	}

	/**
	 * 查询营销机构产品
	 */
	public PageDataDTO inqueryProduct(
			SellerContractQueryDTO sellerContractQueryDTO)
			throws BizServiceException {
		try {
			return pageQueryDAO.query(
					"SELLERCONTRACT.inqueryProductFromContract",
					sellerContractQueryDTO);

		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询产品失败！");
		}
	}

	public PageDataDTO insertProduct(
			SellerContractQueryDTO sellerContractQueryDTO)
			throws BizServiceException {
		try {
			return pageQueryDAO.query("SELLERCONTRACT.inqueryProduct",
					sellerContractQueryDTO);

		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询产品失败！");
		}
	}

	// 查询普通的发行机构
	public PageDataDTO insertIssuer(SellerQueryDTO sellerQueryDTO)

	throws BizServiceException {
		try {
			return pageQueryDAO.query("SELLER.selectIssuerUpSeller",
					sellerQueryDTO);

		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询发行机构失败！");
		}
	}

	@SuppressWarnings("unchecked")
	public SellerContractDTO view(SellerContractDTO sellerContractDTO)
			throws BizServiceException {
		try {
			SellContract sellContract = sellerContractDAO
					.selectByPrimaryKey(sellerContractDTO.getSellContractId());

			// ReflectionUtil.copyProperties(sellContract, sellerContractDTO);

			// 动态表
			if (DataBaseConstant.SELL_CONTRACT_ISSUER.equals(sellContract
					.getContractType())) {
				// sellerContractDTO.setDynamicContractBuyerTable("TB_ISSUER");
				// sellerContractDTO.setDynamicContractBuyerName("ISSUER_NAME");
				sellerContractDTO.setDynamicContractBuyerTable("TB_SELLER");
				sellerContractDTO.setDynamicContractBuyerName("SELLER_NAME");
				sellerContractDTO = (SellerContractDTO) baseDAO.queryForObject(
						"SELLERCONTRACT.selectSellContractForView",
						sellerContractDTO);

				// 关联营销合同产品明细
				sellerContractDTO.setProDTOs(this
						.getProductContractDTOs(sellerContractDTO
								.getSellContractId()));

				// 关联营销合同服务明细
				// sellerContractDTO.setAccDTOs(this.getAcctypeContractDTOs(sellerContractDTO));
			} else if (DataBaseConstant.SELL_CONTRACT_SELLER
					.equals(sellContract.getContractType())) {
				sellerContractDTO.setDynamicContractBuyerTable("TB_SELLER");
				sellerContractDTO.setDynamicContractBuyerName("SELLER_NAME");
				sellerContractDTO = (SellerContractDTO) baseDAO.queryForObject(
						"SELLERCONTRACT.selectSellContractForView",
						sellerContractDTO);

				// 关联营销合同产品明细
				sellerContractDTO.setProDTOs(this
						.getProductContractDTOs(sellerContractDTO
								.getSellContractId()));

				// 关联营销合同服务明细
				// sellerContractDTO.setAccDTOs(this.getAcctypeContractDTOs(sellerContractDTO));
			} else {

				sellerContractDTO.setDynamicContractBuyerTable("TB_CUSTOMER");
				sellerContractDTO.setDynamicContractBuyerName("CUSTOMER_NAME");

				List<String> spcds = (List<String>) baseDAO.queryForList(
						"SELLERCONTRACT.selectSellProduct", sellerContractDTO
								.getDefaultEntityId());
				// List<String> spcds=new ArrayList<String>();
				// for(HashMap<?,?> hm:spcdm){
				// spcds.add((String)hm.get("productId"));
				// }
				sellerContractDTO = (SellerContractDTO) baseDAO.queryForObject(
						"SELLERCONTRACT.selectSellContractForView",
						sellerContractDTO);
				List<SellerProductContractDTO> spcdc = this
						.getProductContractDTOs(sellerContractDTO
								.getSellContractId());
				List<SellerProductContractDTO> sellerProductContractDTO = new ArrayList<SellerProductContractDTO>();
				for (SellerProductContractDTO spcd : spcdc) {
					if (spcds.contains(spcd.getProductId())) {
						sellerProductContractDTO.add(spcd);
					}
				}
				// 关联营销合同产品明细
				sellerContractDTO.setProDTOs(sellerProductContractDTO);

				// 关联营销合同服务明细
				// sellerContractDTO.setAccDTOs(this.getAcctypeContractDTOs(sellerContractDTO));
			}

			return sellerContractDTO;
		} catch (BizServiceException be) {
			throw be;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查看营销合同失败！");
		}
	}

	private List<SellerProductContractDTO> getProductContractDTOs(
			String sellerContractId) throws BizServiceException {
		List<SellerProductContractDTO> proDTOs = sellerProductContractService
				.inquery(sellerContractId);

		return proDTOs;
	}

	@SuppressWarnings("unused")
	private List<SellerAcctypeContractDTO> getAcctypeContractDTOs(
			SellerContractDTO sellerContractDTO) throws Exception {
		SellAcctypeContractExample example = new SellAcctypeContractExample();
		example.createCriteria().andSellContractIdEqualTo(
				sellerContractDTO.getSellContractId());

		List<SellAcctypeContract> acctypeContracts = sellAcctypeContractDAO
				.selectByExample(example);
		List<SellerAcctypeContractDTO> acctypeContractDTOs = new ArrayList<SellerAcctypeContractDTO>();
		if (null != acctypeContracts && acctypeContracts.size() > 0) {
			for (SellAcctypeContract acc : acctypeContracts) {
				SellerAcctypeContractDTO dto = new SellerAcctypeContractDTO();
				ReflectionUtil.copyProperties(acc, dto);
				acctypeContractDTOs.add(dto);
			}

		}
		return acctypeContractDTOs;
	}

	public SellerContractDTO insert(SellerContractDTO sellerContractDTO)
			throws BizServiceException {
		try {
			// 合同双方只能签一个营销合同
			SellContractExample example = new SellContractExample();
			example.createCriteria().andContractBuyerEqualTo(
					sellerContractDTO.getContractBuyer())
					.andContractSellerEqualTo(
							sellerContractDTO.getContractSeller())
					.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL)
					.andContractStateEqualTo("1")
					.andExpiryDateGreaterThanOrEqualTo(
							DateUtil.getCurrentDateStr());
			if (sellerContractDAO.countByExample(example) > 0) {
				throw new BizServiceException("合同双方已签过合同，不能重复签合同！");
			}

			SellContract sellContract = new SellContract();
			ReflectionUtil.copyProperties(sellerContractDTO, sellContract);

			sellContract.setSellContractId(commonsDAO
					.getNextValueOfSequence("TB_SELL_CONTRACT"));

			if (StringUtil.isNotEmpty(sellerContractDTO.getExpiryDate())) {
				sellContract.setExpiryDate(DateUtil
						.getFormatTime(sellerContractDTO.getExpiryDate()));
			} else {
				sellContract.setExpiryDate("29991231");
			}
			sellContract.setCreateTime(DateUtil.getCurrentTime());
			sellContract.setCreateUser(sellerContractDTO.getLoginUserId());
			sellContract.setModifyTime(DateUtil.getCurrentTime());
			sellContract.setModifyUser(sellerContractDTO.getLoginUserId());
			sellContract.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			sellContract.setClearTp(sellerContractDTO.getClearTp());
			sellerContractDAO.insert(sellContract);
			//如果客户状态不是无效，修改客户状态为4
			if (DataBaseConstant.CONTRACT_CUSTOMER.equals(sellContract
					.getContractType())) {
				CustomerExample ce = new CustomerExample();
				ce.createCriteria().andEntityIdEqualTo(
						sellContract.getContractBuyer());
				Customer customer = customerDAO.selectByExample(ce).get(0);
				if(!customer.getCusState().equals("0"))
					customer.setCusState("4");
				customerDAO.updateByPrimaryKeySelective(customer);
			}
			ReflectionUtil.copyProperties(sellContract, sellerContractDTO);
			/*
			 * // 添加服务营销机构服务合同 sellerContractDTO.setSellContractId(sellContract
			 * .getSellContractId());
			 * insertSellerAcctypeContract(sellerContractDTO);
			 */
			return sellerContractDTO;
		} catch (BizServiceException be) {
			throw be;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加合同失败！");
		}
	}

	public void update(SellerContractDTO sellerContractDTO)
			throws BizServiceException {
		try {
			/** 判断该合同是否和订单相关联*/
//			if (checkContractInOrder() > 0) {
//				throw new BizServiceException("合同： "
//						+ sellerContractDTO.getSellContractId()
//						+ " 和订单相关联，不能编辑！");
//			}
			/** 如果为合同模板 则跳过判断  */
			if(null==sellerContractDTO.getContractType()||"".equals(sellerContractDTO.getContractType())||!"0".equals(sellerContractDTO.getContractType())){			
				/** 判断是否已有有效合同*/
				// 合同到期日
				String expireDate = DateUtil.getFormatTime(sellerContractDTO.getExpiryDate());
				// 合同状态
				String contractState = sellerContractDTO.getContractState();
				// 合同ID
				String contractId = sellerContractDTO.getSellContractId(); 
				// 合同买家
				String contractBuyer = sellerContractDTO.getContractBuyer();
				// 当前日期
				String currDate = DateUtil.date2String(DateUtil.getCurrentDate());
				if(Long.parseLong(expireDate) >= Long.parseLong(currDate) && contractState.equals(DataBaseConstant.CONTRACT_STATE_ACTIVE)){
					SellContractExample example=new SellContractExample();
					example.createCriteria().andContractBuyerEqualTo(contractBuyer)
							.andContractStateEqualTo(DataBaseConstant.CONTRACT_STATE_ACTIVE)
							.andExpiryDateGreaterThanOrEqualTo(currDate)
							.andSellContractIdNotEqualTo(contractId)
							.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
					List<SellContract> contracts=sellerContractDAO.selectByExample(example);
					if(contracts!=null && contracts.size()!=0){
						throw new BizServiceException("已有有效合同，不能编辑该合同为有效合同！");
					}
				}
			}
			
			SellContract sellContract = new SellContract();
			ReflectionUtil.copyProperties(sellerContractDTO, sellContract);
			if (StringUtil.isNotEmpty(sellerContractDTO.getExpiryDate())) {
				sellContract.setExpiryDate(DateUtil
						.getFormatTime(sellerContractDTO.getExpiryDate()));
			}
			sellContract.setModifyTime(DateUtil.getCurrentTime());
			sellContract.setModifyUser(sellerContractDTO.getLoginUserId());
			sellContract.setClearTp(sellerContractDTO.getClearTp());
			sellerContractDAO.updateByPrimaryKeySelective(sellContract);
			// 更新服务营销机构服务合同
			// updateSellerAcctypeContract(sellerContractDTO);
		} catch (BizServiceException be) {
			throw be;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("更新合同失败！");
		}
	}

	public void delete(SellerContractDTO sellerContractDTO)
			throws BizServiceException {
		try {
			String[] sellerContractIds = sellerContractDTO
					.getSellerContractIds();
			for (String contractId : sellerContractIds) {
				SellContract sellContract = sellerContractDAO
						.selectByPrimaryKey(contractId);

				// 判断该合同是否有订单
				if (checkContractInOrder() > 0) {
					throw new BizServiceException("合同： " + contractId
							+ " 和订单相关联，不能删除！");
				}
				sellContract.setModifyTime(DateUtil.getCurrentTime());
				sellContract.setModifyUser(sellerContractDTO.getLoginUserId());
				sellContract.setDataState(DataBaseConstant.DATA_STATE_DELETE);

				sellerContractDAO.updateByPrimaryKeySelective(sellContract);

				// 删除营销合同管理的产品及服务
				List<SellerProductContractDTO> productContractDTOs = this
						.getProductContractDTOs(contractId);
				if (null != productContractDTOs
						&& productContractDTOs.size() > 0) {
					for (SellerProductContractDTO proDTO : productContractDTOs) {
						sellerProductContractService.delete(proDTO);
					}
				}

				/*
				 * // 删除该合同对应的服务合同 SellAcctypeContractExample example = new
				 * SellAcctypeContractExample();
				 * example.createCriteria().andSellContractIdEqualTo
				 * (contractId);
				 * 
				 * List<SellAcctypeContract> accContracts =
				 * sellAcctypeContractDAO .selectByExample(example); if (null !=
				 * accContracts && accContracts.size() > 0) { commonsDAO
				 * .batchDelete(
				 * "TB_SELL_ACCTYPE_CONTRACT.abatorgenerated_deleteByPrimaryKey"
				 * , accContracts); }
				 */
			}

		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("删除营销机构合同失败！");
		}
	}

	/**
	 * 根据合同模板插入新合同（同时将合同模板具有的产品明细同样添加到新合同上）
	 * 
	 * @return
	 * @throws BizServiceException
	 */
	public void insertByMasterplate(CustomerDTO customerDTO)
			throws BizServiceException {

		// 查询合同模板
		SellerContractDTO scDTO = new SellerContractDTO();
		SellContractExample example=new SellContractExample();
		example.createCriteria().andContractSellerEqualTo(customerDTO.getDefaultEntityId()).andContractTypeEqualTo(DataBaseConstant.CONTRACT_TEMPLATE);
		List<SellContract> sellContracts=sellerContractDAO.selectByExample(example);		
		if (null ==sellContracts || sellContracts.size()<1) {
			throw new BizServiceException("查询合同模板失败！");
		} else {
			ReflectionUtil.copyProperties(sellContracts.get(0), scDTO);
		}

		// 新增合同
		scDTO.setContractBuyer(customerDTO.getEntityId());// 绑定到客户上
		scDTO.setCreateUser(customerDTO.getLoginUserId());

		SellerContractDTO sellercontractDTO = new SellerContractDTO();
		try {
			// 合同双方只能签一个营销合同
			SellContractExample sellContractExample = new SellContractExample();
			sellContractExample.createCriteria().andContractBuyerEqualTo(
					scDTO.getContractBuyer()).andContractSellerEqualTo(
					scDTO.getContractSeller()).andDataStateEqualTo(
					DataBaseConstant.DATA_STATE_NORMAL)
					.andContractStateEqualTo("1")
					.andExpiryDateGreaterThanOrEqualTo(
							DateUtil.getCurrentDateStr());
			if (sellerContractDAO.countByExample(sellContractExample) > 0) {
				throw new BizServiceException("合同双方已签过合同，不能重复签合同！");
			}

			SellContract sellContract1 = new SellContract();
			ReflectionUtil.copyProperties(scDTO, sellContract1);

			sellContract1.setSellContractId(commonsDAO
					.getNextValueOfSequence("TB_SELL_CONTRACT"));

			if (StringUtil.isNotEmpty(scDTO.getExpiryDate())) {
				sellContract1.setExpiryDate(scDTO.getExpiryDate());
			} else {
				sellContract1.setExpiryDate("29991231");
			}
			sellContract1.setContractState(DataBaseConstant.DATA_STATE_NORMAL);
			sellContract1.setCreateTime(DateUtil.getCurrentTime());
			sellContract1.setCreateUser(customerDTO.getLoginUserId());
			sellContract1.setModifyTime(DateUtil.getCurrentTime());
			sellContract1.setModifyUser(customerDTO.getLoginUserId());
			sellContract1.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			sellContract1.setClearTp(scDTO.getClearTp());
			sellContract1.setContractType("3");
			sellerContractDAO.insert(sellContract1);
			//
			if (DataBaseConstant.CONTRACT_CUSTOMER.equals(sellContract1
					.getContractType())) {
				CustomerExample ce = new CustomerExample();
				ce.createCriteria().andEntityIdEqualTo(
						sellContract1.getContractBuyer());
				Customer customer = customerDAO.selectByExample(ce).get(0);
				customerDAO.updateByPrimaryKeySelective(customer);
			}
			ReflectionUtil.copyProperties(sellContract1, sellercontractDTO);
		} catch (BizServiceException be) {
			throw be;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加合同失败！");
		}

		// 查询合同模板具有的产品明细
		List<SellerProductContractDTO> proDTOs = this
				.getProductContractDTOs(scDTO.getSellContractId());

		// 在新增的合同上添加产品明细
		if (proDTOs.size() > 0) {
			for (int i = 0; i < proDTOs.size(); i++) {
				SellerProductContractDTO sellerProductContractDTO = proDTOs
						.get(i);
				sellerProductContractDTO.setSellContractId(sellercontractDTO
						.getSellContractId());
				sellerProductContractDTO.setAnnualFee(Amount
						.getReallyAmount(sellerProductContractDTO
								.getAnnualFee()));
				sellerProductContractDTO
						.setCardFee(Amount
								.getReallyAmount(sellerProductContractDTO
										.getCardFee()));
				sellerProductContractDTO.setCreateUser(customerDTO
						.getLoginUserId());
				sellerProductContractDTO.setCreateTime(DateUtil
						.getCurrentTime());
				sellerProductContractDTO.setModifyTime(DateUtil
						.getCurrentTime());
				sellerProductContractDTO.setModifyUser(customerDTO
						.getLoginUserId());
				sellerProductContractDTO.setLoginUserId(customerDTO
						.getLoginUserId());
				sellerProductContractService.insert(sellerProductContractDTO);
			}
		}
	}

	public List<SellerContractDTO> inqueryContract(
			SellerContractQueryDTO sellerContractQueryDTO)
			throws BizServiceException {
		try {
			if (sellerContractQueryDTO.getDefaultEntityId() != null
					&& !"".equals(sellerContractQueryDTO.getDefaultEntityId())) {
				sellerContractQueryDTO.setContractSeller(sellerContractQueryDTO
						.getDefaultEntityId());
			}
			if (null != sellerContractQueryDTO.getSellContractId()) {
				sellerContractQueryDTO.setSellContractId(sellerContractQueryDTO
						.getSellContractId().trim());
			}
			if (sellerContractQueryDTO.getContractState() != null
					&& sellerContractQueryDTO.getContractState().trim().equals("")) {
				sellerContractQueryDTO.setContractState(null);
			}
			if (sellerContractQueryDTO.getWebPayStat() != null
					&& sellerContractQueryDTO.getWebPayStat().trim().equals("")) {
				sellerContractQueryDTO.setWebPayStat(null);
			}

			SellContractExample sellContractExample = new SellContractExample();
			sellContractExample.createCriteria().andContractBuyerEqualTo(
					sellerContractQueryDTO.getContractBuyer());

			List<SellContract> sellContractList = sellerContractDAO
					.selectByExample(sellContractExample);
			List<SellerContractDTO> sellerContractDTOList = new ArrayList<SellerContractDTO>();
			SellerContractDTO sellerContractDTOTmp = new SellerContractDTO();

			for (SellContract sellContract : sellContractList) {
				sellerContractDTOTmp = new SellerContractDTO();
				BeanUtils.copyProperties(sellerContractDTOTmp, sellContract);
				sellerContractDTOList.add(sellerContractDTOTmp);
			}
			return sellerContractDTOList;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询营销机构合同失败！");
		}
	}

	// //////////////////////////////////////////////////////////////////////////////

	private int checkContractInOrder() throws Exception {

		return 0;
	}

	public SellContractDAO getSellerContractDAO() {
		return sellerContractDAO;
	}

	public void setSellerContractDAO(SellContractDAO sellerContractDAO) {
		this.sellerContractDAO = sellerContractDAO;
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

	public SellAcctypeContractDAO getSellAcctypeContractDAO() {
		return sellAcctypeContractDAO;
	}

	public void setSellAcctypeContractDAO(
			SellAcctypeContractDAO sellAcctypeContractDAO) {
		this.sellAcctypeContractDAO = sellAcctypeContractDAO;
	}

	public SellerProductContractService getSellerProductContractService() {
		return sellerProductContractService;
	}

	public void setSellerProductContractService(
			SellerProductContractService sellerProductContractService) {
		this.sellerProductContractService = sellerProductContractService;
	}

	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

}
