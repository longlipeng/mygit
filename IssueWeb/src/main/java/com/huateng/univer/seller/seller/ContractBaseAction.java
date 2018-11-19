package com.huateng.univer.seller.seller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;
import com.allinfinance.univer.issuer.dto.product.ProductQueryDTO;
import com.allinfinance.univer.issuer.dto.service.ServiceDTO;
import com.allinfinance.univer.seller.sellercontract.dto.SellerAcctypeContractDTO;
import com.allinfinance.univer.seller.sellercontract.dto.SellerContractDTO;
import com.allinfinance.univer.seller.sellercontract.dto.SellerContractQueryDTO;
import com.allinfinance.univer.seller.sellercontract.dto.SellerProductContractDTO;
import com.allinfinance.univer.servicefeerule.dto.CaclInfQueryDTO;
import com.allinfinance.univer.servicefeerule.dto.ServiceFeeRuleQueryDTO;
import com.allinfinance.univer.settleperiodrule.dto.SettlePeriodRuleQueryDTO;
import com.huateng.framework.action.BaseAction;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.constant.SystemInfoConstants;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.SystemInfo;

/**
 * 合同(发行机构合同、营销机构合同、客户合同)baseAction
 * 
 * @author xxl
 * 
 */
public abstract class ContractBaseAction extends BaseAction {

	private static final long serialVersionUID = 6567225110315471645L;
	private Logger logger = Logger.getLogger(ContractBaseAction.class);
	protected PageDataDTO pageDataDTO = new PageDataDTO();
	protected CaclInfQueryDTO caclInfQueryDTO =new CaclInfQueryDTO();
	protected ProductDTO productDTO = new ProductDTO();

	protected SellerContractDTO sellerContractDTO = new SellerContractDTO();
	protected SellerProductContractDTO sellerProductContractDTO = new SellerProductContractDTO();
	protected SellerAcctypeContractDTO sellerAcctypeContractDTO = new SellerAcctypeContractDTO();

	protected ServiceFeeRuleQueryDTO serviceFeeRuleQueryDTO = new ServiceFeeRuleQueryDTO();
	protected SettlePeriodRuleQueryDTO settlePeriodRuleQueryDTO = new SettlePeriodRuleQueryDTO();
	protected SellerContractQueryDTO sellerContractQueryDTO = new SellerContractQueryDTO();
	protected ProductQueryDTO productQueryDTO = new ProductQueryDTO();

	protected List<ServiceDTO> productServices = new ArrayList<ServiceDTO>();
	//默认账户计算规则
	protected String defaultRuleNo;
	protected List<SellerProductContractDTO> productContractDTOs = new ArrayList<SellerProductContractDTO>();
	protected List<SellerAcctypeContractDTO> acctypeContractDTOs = new ArrayList<SellerAcctypeContractDTO>();
	
	private BigDecimal temp = new BigDecimal("100");

	protected String[] sellAcctypeContractIdList;
	protected String[] sellerContractIds;
	protected String[] serviceIdList;
	protected String[] ruleNoList;
	protected String[] feeList;
	protected Integer totalRows;
	protected String nameSpace;
	// 发卡机构标示
	protected String issuerFlag;

	protected abstract void initNameSpace();

	protected abstract void inqueryInit();

	/** 初始化合同信息 */
	protected abstract void insertInit();

	public String list() throws Exception {
		// 查询初始化
		this.inqueryInit();

		ListPageInit(null, sellerContractQueryDTO);

		if (sellerContractQueryDTO.isQueryAll()) {
			sellerContractQueryDTO.setQueryAll(false);
			sellerContractQueryDTO
					.setRowsDisplayed(Integer
							.parseInt(SystemInfo
									.getParameterValue(SystemInfoConstants.EXPORT_DATA_MAXIMUM)));
		}

		pageDataDTO = (PageDataDTO) sendService(
				ConstCode.SELLER_CONTRACT_SERVICE_INQUERY,
				sellerContractQueryDTO).getDetailvo();
		if (null != pageDataDTO && pageDataDTO.getData().size() > 0) {
			totalRows = pageDataDTO.getTotalRecord();
		} else {
			totalRows = 0;
		}
		return "list";
	}
	


	/**
	 * 产品选择
	 * 
	 * @return
	 * @throws Exception
	 */
	public String productChoice() throws Exception {
		ListPageInit(null, sellerContractQueryDTO);
		if ("1".equals(issuerFlag)) {
			pageDataDTO = (PageDataDTO) sendService(
					ConstCode.SELLER_CONTRACT_SERVICE_PRODUCT_CHOICE_FOR_ISSUER,
					sellerContractQueryDTO).getDetailvo();
		} else {
			pageDataDTO = (PageDataDTO) sendService(
					ConstCode.SELLER_CONTRACT_SERVICE_PRODUCT_CHOICE,
					sellerContractQueryDTO).getDetailvo();
		}

		if (null != pageDataDTO && pageDataDTO.getTotalRecord() > 0) {
			totalRows = pageDataDTO.getTotalRecord();
		} else {
			totalRows = 0;
		}
		return "productChoice";
	}

	public String productView() throws Exception {
		//productDTO = (ProductDTO) sendService(ConstCode.PRODUCT_SERVICE_VIEW,productDTO).getDetailvo();
		productDTO = (ProductDTO) sendService(ConstCode.PRODUCT_SERVICE_VIEW_FOR_CONTRACT,productDTO).getDetailvo();
		// 加载产品服务信息
		sellerProductContractDTO.setProductId(productDTO.getProductId());
		sellerProductContractDTO.setProductDTO(productDTO);
		productServices = productDTO.getServices();
		sellerProductContractDTO.setCardFee((new BigDecimal(productDTO.getCardFee())
		.divide(temp)).toString());
		sellerProductContractDTO.setAnnualFee((new BigDecimal(productDTO.getAnnualFee())
		.divide(temp)).toString());
		
		defaultRuleNo = productDTO.getDefaultRuleNo();
		return "productView";
	}

	/**
	 * 服务计算规则选择
	 */
	public String serviceRuleChoice() throws Exception {
		caclInfQueryDTO.setRecUpdUsrId(getUser().getEntityId());
		caclInfQueryDTO.setDataStat(DataBaseConstant.RULE_STATE_ENABLE);
		//serviceFeeRuleQueryDTO.setEntityId(getUser().getEntityId());
		//serviceFeeRuleQueryDTO.setState(DataBaseConstant.RULE_STATE_ENABLE);
		this.pageDataDTO = (PageDataDTO) sendService("7777100001",
				caclInfQueryDTO).getDetailvo();
		if (null != pageDataDTO && pageDataDTO.getData().size() > 0) {
			totalRows = pageDataDTO.getTotalRecord();
		} else {
			totalRows = 0;
		}
		return "serviceRuleChoice";
	}

	/**
	 * 结算周期规则
	 */
	public String settleRuleChoice() throws Exception {

		try {
			settlePeriodRuleQueryDTO.setEntityId(getUser().getEntityId());
			settlePeriodRuleQueryDTO.setState(DataBaseConstant.RULE_STATE_ENABLE);
			this.pageDataDTO = (PageDataDTO) sendService("7777000002",
					settlePeriodRuleQueryDTO).getDetailvo();
			if (null != pageDataDTO && pageDataDTO.getData().size() > 0) {
				totalRows = pageDataDTO.getTotalRecord();
			} else {
				totalRows = 0;
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}

		return "settleRuleChoice";
	}

	public String view() throws Exception {
		try {
			this.initNameSpace();
			sellerContractDTO = (SellerContractDTO) sendService(
					ConstCode.SELLER_CONTRACT_SERVICE_VIEW, sellerContractDTO)
					.getDetailvo();

			if (null != sellerContractDTO) {
				productContractDTOs = sellerContractDTO.getProDTOs();
				// acctypeContractDTOs = sellerContractDTO.getAccDTOs();
			}

			if (hasActionErrors()) {
				return this.list();
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "view";
	}

	public String add() throws Exception {
		return "add";
	}

	public String insert() throws Exception {

		try {
			//判断失效日期格式 并且不能小于当前日期
			if (!DateUtil.isValidDate(sellerContractDTO.getExpiryDate()) &&
					DateUtil.string2date(sellerContractDTO.getExpiryDate()).compareTo(DateUtil.getCurrentDate())< 0) {
				this.addActionMessage("日期格式不对");		
				return INPUT;
			}
			
			insertInit();

			sellerContractDTO = (SellerContractDTO) this
					.sendService(ConstCode.SELLER_CONTRACT_SERVICE_INSERT,
							sellerContractDTO).getDetailvo();
			if (hasActionErrors()) {
				return INPUT;
			}
			this.addActionMessage("添加合同成功！");
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return this.edit();
	}

	public String addProductService() throws Exception {

		return "addProductService";
	}

	public String insertProductService() throws Exception {
		try {
			// insertInit();
			// 添加合同服务明细
			if (null != serviceIdList && serviceIdList.length > 0) {
				List<SellerAcctypeContractDTO> accDTOs = new ArrayList<SellerAcctypeContractDTO>();
				for (int i = 0; i < serviceIdList.length; i++) {
					SellerAcctypeContractDTO accDTO = new SellerAcctypeContractDTO();
					accDTO.setSellContractId(sellerContractDTO
							.getSellContractId());
					accDTO.setProductId(sellerProductContractDTO
									.getProductId());
					accDTO.setAcctypeId(serviceIdList[i]);
					accDTO.setRuleNo(ruleNoList[i]);
					// 交易类型暂定为0000
					accDTO.setTxnNum("0000");
					accDTO.setFee(feeList[i]);
					accDTOs.add(accDTO);
				}
				sellerProductContractDTO.setAccDTOs(accDTOs);
			}
			sellerProductContractDTO.setSellContractId(sellerContractDTO
					.getSellContractId());

			this.sendService(ConstCode.SELLER_PRODUCT_CONTRACT_SERVICE_INSERT,
					sellerProductContractDTO);
			if (hasActionErrors()) {
				return INPUT;
			}
			this.addActionMessage("添加合同明细成功！");

		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "blank";
	}

	public String insertCustomerProductService() throws Exception {

		try {
			// insertInit();
			// 添加合同服务明细
			if (null != serviceIdList && serviceIdList.length > 0) {
				List<SellerAcctypeContractDTO> accDTOs = new ArrayList<SellerAcctypeContractDTO>();
				for (int i = 0; i < serviceIdList.length; i++) {
					SellerAcctypeContractDTO accDTO = new SellerAcctypeContractDTO();
					accDTO.setSellContractId(sellerContractDTO
							.getSellContractId());
					accDTO
							.setProductId(sellerProductContractDTO
									.getProductId());
					accDTO.setAcctypeId(serviceIdList[i]);
					accDTO.setFee(feeList[i]);
					// accDTO.setRuleNo(ruleNoList[i]);
					// 交易类型暂定为0000
					accDTO.setTxnNum("0000");
					accDTOs.add(accDTO);
				}
				sellerProductContractDTO.setAccDTOs(accDTOs);
			}
			sellerProductContractDTO.setSellContractId(sellerContractDTO
					.getSellContractId());

			this.sendService(ConstCode.SELLER_PRODUCT_CONTRACT_SERVICE_INSERT,
					sellerProductContractDTO);
			if (hasActionErrors()) {
				return INPUT;
			}
			this.addActionMessage("添加合同明细成功！");
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "blank";
	}

	public String edit() throws Exception {
		try {
			if (null != sellerContractIds && sellerContractIds.length == 1) {
				sellerContractDTO.setSellContractId(sellerContractIds[0]);
			}
			this.view();
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "edit";
	}

	public String update() throws Exception {

		// 修改营销机构服务合同
		/*
		 * if (null != sellAcctypeContractIdList &&
		 * sellAcctypeContractIdList.length > 0) {
		 * List<SellerAcctypeContractDTO> accDTOs = new
		 * ArrayList<SellerAcctypeContractDTO>(); for (int i = 0; i <
		 * sellAcctypeContractIdList.length; i++) { SellerAcctypeContractDTO
		 * accDTO = new SellerAcctypeContractDTO();
		 * accDTO.setId(sellAcctypeContractIdList[i]);
		 * accDTO.setRuleNo(ruleNoList[i]); accDTOs.add(accDTO); }
		 * sellerContractDTO.setAccDTOs(accDTOs); }
		 */

		try {
			this.sendService(ConstCode.SELLER_CONTRACT_SERVICE_UPDATE,
					sellerContractDTO);
			if (hasActionErrors()) {
				return INPUT;
			}
			this.addActionMessage("更新合同成功！");
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return this.list();
	}

	public String editProduct() throws Exception {
		try {
			sellerProductContractDTO = (SellerProductContractDTO) sendService(
					ConstCode.SELLER_PRODUCT_CONTRACT_SERVICE_VIEW,
					sellerProductContractDTO).getDetailvo();
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}

		return "editProduct";
	}

	public String updateProduct() throws Exception {

		try {
			sendService(ConstCode.SELLER_PRODUCT_CONTRACT_SERVICE_UPDATE,
					sellerProductContractDTO);

			if (hasActionErrors()) {
//				return this.list();
				return "input";
			}
			this.addActionMessage("更新合同产品明细成功！");
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "blank";
	}

	public String deleteProduct() throws Exception {

		try {
			sendService(ConstCode.SELLER_PRODUCT_CONTRACT_SERVICE_DEL,
					sellerProductContractDTO);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "blank";
	}

	public String editService() throws Exception {
		try {
			sellerAcctypeContractDTO = (SellerAcctypeContractDTO) sendService(
					ConstCode.SELLER_ACCOUNT_CONTRACT_SERVICE_VIEW,
					sellerAcctypeContractDTO).getDetailvo();
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}

		return "editService";
	}

	public String updateService() throws Exception {

		sendService(ConstCode.SELLER_ACCOUNT_CONTRACT_SERVICE_UPDATE,
				sellerAcctypeContractDTO);
		if (hasActionErrors()) {
			return INPUT;

		}
		this.addActionMessage("更新合同明细成功！");
		return "blank";
	}

	public String delete() throws Exception {
		try {
			if (sellerContractIds != null && sellerContractIds.length > 0) {
				sellerContractDTO.setSellerContractIds(sellerContractIds);
				this.sendService(ConstCode.SELLER_CONTRACT_SERVICE_DEL,
						sellerContractDTO);
				if (!hasActionErrors()) {
					this.addActionMessage("删除合同成功！");
				}
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return this.list();
	}
	
	////////////////////////////////////////////////////////////////////////
	
	/**
	 * 合同模版列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String masterPlatelist() throws Exception {
		// 查询初始化
		this.inqueryInit();

		ListPageInit(null, sellerContractQueryDTO);

		if (sellerContractQueryDTO.isQueryAll()) {
			sellerContractQueryDTO.setQueryAll(false);
			sellerContractQueryDTO
					.setRowsDisplayed(Integer
							.parseInt(SystemInfo
									.getParameterValue(SystemInfoConstants.EXPORT_DATA_MAXIMUM)));
		}

		pageDataDTO = (PageDataDTO) sendService(
				ConstCode.SELLER_CONTRACT_SERVICE_INQUERY_MASTERPALTE,
				sellerContractQueryDTO).getDetailvo();
		if (null != pageDataDTO && pageDataDTO.getData().size() > 0) {
			totalRows = pageDataDTO.getTotalRecord();
		} else {
			totalRows = 0;
		}
		return "masterPlateList";
	}
	
	/**
	 * 查看合同模板
	 * 
	 * @return
	 * @throws Exception
	 */
	public String viewMasterplate() throws Exception {
		try {
			this.initNameSpace();
			sellerContractDTO.setDefaultEntityId(getUser().getEntityId());
			sellerContractDTO = (SellerContractDTO) sendService(
					ConstCode.SELLER_CONTRACT_SERVICE_VIEW, sellerContractDTO)
					.getDetailvo();

			if (null != sellerContractDTO) {
				productContractDTOs = sellerContractDTO.getProDTOs();
			}

			if (hasActionErrors()) {
				return this.list();
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "viewMasterplate";
	}
	
	/**
	 * 跳转到合同模板编辑页面
	 * @return
	 * @throws Exception
	 */
	public String editMasterplate() throws Exception {
		try {
			if (null != sellerContractIds && sellerContractIds.length == 1) {
				sellerContractDTO.setSellContractId(sellerContractIds[0]);
			}
			this.viewMasterplate();
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "editMasterplate";
	}
	
	/**
	 * 合同模板中添加服务明细
	 * 
	 * @return
	 * @throws Exception
	 */
	public String insertCustomerMasterplateProductService() throws Exception {

		try {
			// insertInit();
			// 添加合同服务明细
			if (null != serviceIdList && serviceIdList.length > 0) {
				List<SellerAcctypeContractDTO> accDTOs = new ArrayList<SellerAcctypeContractDTO>();
				for (int i = 0; i < serviceIdList.length; i++) {
					SellerAcctypeContractDTO accDTO = new SellerAcctypeContractDTO();
					accDTO.setSellContractId(sellerContractDTO
							.getSellContractId());
					accDTO.setProductId(sellerProductContractDTO
									.getProductId());
					accDTO.setAcctypeId(serviceIdList[i]);
					accDTO.setFee(feeList[i]);
					// accDTO.setRuleNo(ruleNoList[i]);
					// 交易类型暂定为0000
					accDTO.setTxnNum("0000");
					accDTOs.add(accDTO);
				}
				sellerProductContractDTO.setAccDTOs(accDTOs);
			}
			sellerProductContractDTO.setSellContractId(sellerContractDTO
					.getSellContractId());

			this.sendService(ConstCode.SELLER_PRODUCT_CONTRACT_SERVICE_INSERT,
					sellerProductContractDTO);
			if (hasActionErrors()) {
				return INPUT;
			}
			this.addActionMessage("添加合同明细成功！");
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "blankMasterplate";
	}
	
	/**
	 * 更新合同模板
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateMasterplate() throws Exception {
		try {
			this.sendService(ConstCode.SELLER_CONTRACT_SERVICE_UPDATE,
					sellerContractDTO);
			if (hasActionErrors()) {
				return INPUT;
			}
			this.addActionMessage("更新合同成功！");
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return this.masterPlatelist();
	}
	 
	/**
	 * 合同模板添加产品
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addProductMasterplateService() throws Exception {

		return "addProductMasterplateService";
	}
	
	
	////////////////////////////////////////////////////////////////////////

	public SellerContractQueryDTO getSellerContractQueryDTO() {
		return sellerContractQueryDTO;
	}

	public void setSellerContractQueryDTO(
			SellerContractQueryDTO sellerContractQueryDTO) {
		this.sellerContractQueryDTO = sellerContractQueryDTO;
	}

	public PageDataDTO getPageDataDTO() {
		return pageDataDTO;
	}

	public void setPageDataDTO(PageDataDTO pageDataDTO) {
		this.pageDataDTO = pageDataDTO;
	}

	public SellerContractDTO getSellerContractDTO() {
		return sellerContractDTO;
	}

	public void setSellerContractDTO(SellerContractDTO sellerContractDTO) {
		this.sellerContractDTO = sellerContractDTO;
	}

	public ProductQueryDTO getProductQueryDTO() {
		return productQueryDTO;
	}

	public void setProductQueryDTO(ProductQueryDTO productQueryDTO) {
		this.productQueryDTO = productQueryDTO;
	}

	public ProductDTO getProductDTO() {
		return productDTO;
	}

	public void setProductDTO(ProductDTO productDTO) {
		this.productDTO = productDTO;
	}

	public List<ServiceDTO> getProductServices() {
		return productServices;
	}

	public void setProductServices(List<ServiceDTO> productServices) {
		this.productServices = productServices;
	}

	public List<SellerAcctypeContractDTO> getAcctypeContractDTOs() {
		return acctypeContractDTOs;
	}

	public void setAcctypeContractDTOs(
			List<SellerAcctypeContractDTO> acctypeContractDTOs) {
		this.acctypeContractDTOs = acctypeContractDTOs;
	}

	public String[] getSellAcctypeContractIdList() {
		return sellAcctypeContractIdList;
	}

	public void setSellAcctypeContractIdList(String[] sellAcctypeContractIdList) {
		this.sellAcctypeContractIdList = sellAcctypeContractIdList;
	}

	public String[] getSellerContractIds() {
		return sellerContractIds;
	}

	public void setSellerContractIds(String[] sellerContractIds) {
		this.sellerContractIds = sellerContractIds;
	}

	public String[] getServiceIdList() {
		return serviceIdList;
	}

	public void setServiceIdList(String[] serviceIdList) {
		this.serviceIdList = serviceIdList;
	}

	public String[] getRuleNoList() {
		return ruleNoList;
	}

	public void setRuleNoList(String[] ruleNoList) {
		this.ruleNoList = ruleNoList;
	}

	public Integer getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}

	public ServiceFeeRuleQueryDTO getServiceFeeRuleQueryDTO() {
		return serviceFeeRuleQueryDTO;
	}

	public void setServiceFeeRuleQueryDTO(
			ServiceFeeRuleQueryDTO serviceFeeRuleQueryDTO) {
		this.serviceFeeRuleQueryDTO = serviceFeeRuleQueryDTO;
	}

	public SettlePeriodRuleQueryDTO getSettlePeriodRuleQueryDTO() {
		return settlePeriodRuleQueryDTO;
	}

	public void setSettlePeriodRuleQueryDTO(
			SettlePeriodRuleQueryDTO settlePeriodRuleQueryDTO) {
		this.settlePeriodRuleQueryDTO = settlePeriodRuleQueryDTO;
	}

	public SellerProductContractDTO getSellerProductContractDTO() {
		return sellerProductContractDTO;
	}

	public void setSellerProductContractDTO(
			SellerProductContractDTO sellerProductContractDTO) {
		this.sellerProductContractDTO = sellerProductContractDTO;
	}

	public List<SellerProductContractDTO> getProductContractDTOs() {
		return productContractDTOs;
	}

	public void setProductContractDTOs(
			List<SellerProductContractDTO> productContractDTOs) {
		this.productContractDTOs = productContractDTOs;
	}

	public SellerAcctypeContractDTO getSellerAcctypeContractDTO() {
		return sellerAcctypeContractDTO;
	}

	public void setSellerAcctypeContractDTO(
			SellerAcctypeContractDTO sellerAcctypeContractDTO) {
		this.sellerAcctypeContractDTO = sellerAcctypeContractDTO;
	}

	public String getNameSpace() {
		return nameSpace;
	}

	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}

	public String[] getFeeList() {
		return feeList;
	}

	public void setFeeList(String[] feeList) {
		this.feeList = feeList;
	}

	public String getIssuerFlag() {
		return issuerFlag;
	}

	public void setIssuerFlag(String issuerFlag) {
		this.issuerFlag = issuerFlag;
	}

	public String getDefaultRuleNo() {
		return defaultRuleNo;
	}

	public void setDefaultRuleNo(String defaultRuleNo) {
		this.defaultRuleNo = defaultRuleNo;
	}

	public CaclInfQueryDTO getCaclInfQueryDTO() {
		return caclInfQueryDTO;
	}

	public void setCaclInfQueryDTO(CaclInfQueryDTO caclInfQueryDTO) {
		this.caclInfQueryDTO = caclInfQueryDTO;
	}
	
}
