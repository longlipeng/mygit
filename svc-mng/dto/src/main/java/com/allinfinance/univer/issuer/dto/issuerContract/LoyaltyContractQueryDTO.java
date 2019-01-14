package com.allinfinance.univer.issuer.dto.issuerContract;

import com.allinfinance.framework.dto.PageQueryDTO;

public class LoyaltyContractQueryDTO extends PageQueryDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String loyaltyContractId;

	private String ruleNo;

	private String contractBuyer;

	private String contractSeller;

	private String deliveryFee;
	
	private String smsSvcStat;

	private String emailSvcStat;

	private String monstmtSvcStat;

	private String webPayStat;

	private String webSmsSvcStat;

	private String webEmailSvcStat;

	private String contractState;

	private String expiryDate;

	private String createUser;

	private String createTime;

	private String modifyUser;

	private String modifyTime;

	private String dataState;
	
	private String customerName;
	
	private String ruleName;//结算周期名称
	private String ruleType;//结算周期类型
	private String clearTp;

	public String getClearTp() {
		return clearTp;
	}

	public void setClearTp(String clearTp) {
		this.clearTp = clearTp;
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

	public String getDeliveryFee() {
		return deliveryFee;
	}

	public void setDeliveryFee(String deliveryFee) {
		this.deliveryFee = deliveryFee;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getLoyaltyContractId() {
		return loyaltyContractId;
	}

	public void setLoyaltyContractId(String loyaltyContractId) {
		this.loyaltyContractId = loyaltyContractId;
	}

	public String getRuleNo() {
		return ruleNo;
	}

	public void setRuleNo(String ruleNo) {
		this.ruleNo = ruleNo;
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
	
	
	
	
	
}
