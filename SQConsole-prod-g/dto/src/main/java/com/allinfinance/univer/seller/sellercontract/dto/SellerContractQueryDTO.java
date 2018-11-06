package com.allinfinance.univer.seller.sellercontract.dto;

import java.util.Date;

import com.allinfinance.framework.dto.PageQueryDTO;

public class SellerContractQueryDTO extends PageQueryDTO {
	private static final long serialVersionUID = -5410395443113784244L;

	private String sellContractId;
	private String contractBuyer;
	private String contractSeller;
	private String deliveryFee;
	private String settlePeriodRule;
	private String smsSvcStat;
	private String emailSvcStat;
	private String monstmtSvcStat;
	private String webPayStat;
	private String webSmsSvcStat;
	private String webEmailSvcStat;
	private String contractState;
	private String expiryDate;
	private Date expiryDateDate;
	private String createUser;
	private String modifyUser;
	private String dataState;
	private String contractType;
	private String customerName;//客户姓名
	
	private String sellerName;//营销机构名称
	private String ruleName;//结算规则名称
	private String ruleType;//结算规则类型
	private String clearTp;
	private String endDate;
	private String startDate;
	
	

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getClearTp() {
		return clearTp;
	}

	public void setClearTp(String clearTp) {
		this.clearTp = clearTp;
	}
	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
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

	public Date getExpiryDateDate() {
		return expiryDateDate;
	}

	public void setExpiryDateDate(Date expiryDateDate) {
		this.expiryDateDate = expiryDateDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public String getDataState() {
		return dataState;
	}

	public void setDataState(String dataState) {
		this.dataState = dataState;
	}

}