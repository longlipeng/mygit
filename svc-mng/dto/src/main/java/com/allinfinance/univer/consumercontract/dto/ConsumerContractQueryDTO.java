package com.allinfinance.univer.consumercontract.dto;

import com.allinfinance.framework.dto.PageQueryDTO;

public class ConsumerContractQueryDTO extends PageQueryDTO{
	private String consumerContractId;
	private String ruleNo;
	private String ruleName;
	private String contractBuyer;
	private String contractSeller;
	private String merchantName;
	private String merchantCode;
	private String state;
	public String getConsumerContractId() {
		return consumerContractId;
	}
	public void setConsumerContractId(String consumerContractId) {
		this.consumerContractId = consumerContractId;
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
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	
	
	
}
