package com.allinfinance.univer.seller.sellercontract.dto;

import java.util.Date;
import java.util.List;

import com.allinfinance.framework.dto.BaseDTO;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;
import com.allinfinance.univer.seller.seller.dto.SellerDTO;

public class SellerContractDTO extends BaseDTO {
	private static final long serialVersionUID = -1762731632007801103L;

	private String sellContractId;
	private String contractBuyer;   //营销机构号
	private String contractBuyerName;  //营销机构名称
	private String contractSeller;
	private String contractSellerName;
	private String deliveryFee;      //快递费
	private String settlePeriodRule;   //结算周期规则
	private String settlePeriodRuleName;  //结算周期规则名称
	private String smsSvcStat;
	private String emailSvcStat;
	private String monstmtSvcStat;
	private String webPayStat;
	private String webSmsSvcStat;
	private String webEmailSvcStat;
	private String contractState;   //合同状态
	private String expiryDate;    //失效时间
	private String createUser;
	private String createTime;
	private String modifyUser;
	private String modifyTime;
	private String dataState;

	private String contractType;

	private SellerDTO sellerDTO;

	private ProductDTO productDTO;

	private String[] sellerContractIds;

	private List<SellerProductContractDTO> proDTOs;

	private List<SellerAcctypeContractDTO> accDTOs;

	private String cardFee;

	private String annualFee;
	private String clearTp;

	// 动态表
	private String dynamicContractBuyerName;
	private String dynamicContractBuyerTable;

	
	public String getClearTp() {
		return clearTp;
	}

	public void setClearTp(String clearTp) {
		this.clearTp = clearTp;
	}

	public String getDynamicContractBuyerName() {
		return dynamicContractBuyerName;
	}

	public void setDynamicContractBuyerName(String dynamicContractBuyerName) {
		this.dynamicContractBuyerName = dynamicContractBuyerName;
	}

	public String getDynamicContractBuyerTable() {
		return dynamicContractBuyerTable;
	}

	public void setDynamicContractBuyerTable(String dynamicContractBuyerTable) {
		this.dynamicContractBuyerTable = dynamicContractBuyerTable;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public String getSellContractId() {
		return sellContractId;
	}

	public void setSellContractId(String sellContractId) {
		this.sellContractId = sellContractId;
	}

	public String getContractBuyer() {
		return contractBuyer;
	}

	public void setContractBuyer(String contractBuyer) {
		this.contractBuyer = contractBuyer;
	}

	public String getContractSeller() {
		return contractSeller;
	}

	public void setContractSeller(String contractSeller) {
		this.contractSeller = contractSeller;
	}

	public String getDeliveryFee() {
		return deliveryFee;
	}

	public void setDeliveryFee(String deliveryFee) {
		this.deliveryFee = deliveryFee;
	}

	public String getSettlePeriodRule() {
		return settlePeriodRule;
	}

	public void setSettlePeriodRule(String settlePeriodRule) {
		this.settlePeriodRule = settlePeriodRule;
	}

	public String getSmsSvcStat() {
		return smsSvcStat;
	}

	public void setSmsSvcStat(String smsSvcStat) {
		this.smsSvcStat = smsSvcStat;
	}

	public String getEmailSvcStat() {
		return emailSvcStat;
	}

	public void setEmailSvcStat(String emailSvcStat) {
		this.emailSvcStat = emailSvcStat;
	}

	public String getMonstmtSvcStat() {
		return monstmtSvcStat;
	}

	public void setMonstmtSvcStat(String monstmtSvcStat) {
		this.monstmtSvcStat = monstmtSvcStat;
	}

	public String getWebPayStat() {
		return webPayStat;
	}

	public void setWebPayStat(String webPayStat) {
		this.webPayStat = webPayStat;
	}

	public String getWebSmsSvcStat() {
		return webSmsSvcStat;
	}

	public void setWebSmsSvcStat(String webSmsSvcStat) {
		this.webSmsSvcStat = webSmsSvcStat;
	}

	public String getWebEmailSvcStat() {
		return webEmailSvcStat;
	}

	public void setWebEmailSvcStat(String webEmailSvcStat) {
		this.webEmailSvcStat = webEmailSvcStat;
	}

	public String getContractState() {
		return contractState;
	}

	public void setContractState(String contractState) {
		this.contractState = contractState;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getDataState() {
		return dataState;
	}

	public void setDataState(String dataState) {
		this.dataState = dataState;
	}

	public SellerDTO getSellerDTO() {
		return sellerDTO;
	}

	public void setSellerDTO(SellerDTO sellerDTO) {
		this.sellerDTO = sellerDTO;
	}

	public ProductDTO getProductDTO() {
		return productDTO;
	}

	public void setProductDTO(ProductDTO productDTO) {
		this.productDTO = productDTO;
	}

	public String[] getSellerContractIds() {
		return sellerContractIds;
	}

	public void setSellerContractIds(String[] sellerContractIds) {
		this.sellerContractIds = sellerContractIds;
	}

	public List<SellerAcctypeContractDTO> getAccDTOs() {
		return accDTOs;
	}

	public void setAccDTOs(List<SellerAcctypeContractDTO> accDTOs) {
		this.accDTOs = accDTOs;
	}

	public List<SellerProductContractDTO> getProDTOs() {
		return proDTOs;
	}

	public void setProDTOs(List<SellerProductContractDTO> proDTOs) {
		this.proDTOs = proDTOs;
	}

	public String getContractBuyerName() {
		return contractBuyerName;
	}

	public void setContractBuyerName(String contractBuyerName) {
		this.contractBuyerName = contractBuyerName;
	}

	public String getSettlePeriodRuleName() {
		return settlePeriodRuleName;
	}

	public void setSettlePeriodRuleName(String settlePeriodRuleName) {
		this.settlePeriodRuleName = settlePeriodRuleName;
	}

	public String getContractSellerName() {
		return contractSellerName;
	}

	public void setContractSellerName(String contractSellerName) {
		this.contractSellerName = contractSellerName;
	}

	public String getCardFee() {
		return cardFee;
	}

	public void setCardFee(String cardFee) {
		this.cardFee = cardFee;
	}

	public String getAnnualFee() {
		return annualFee;
	}

	public void setAnnualFee(String annualFee) {
		this.annualFee = annualFee;
	}

}