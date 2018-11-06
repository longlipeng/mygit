package com.allinfinance.univer.issuer.dto.product;

import com.allinfinance.framework.dto.PageQueryDTO;

public class ValidPeriodRuleInfDTO extends PageQueryDTO{
    
    private String ruleInfId;
    private String ruleDspId;
    private String minAmount;
    private String maxAmount;
    private String delayMonth;
    private String dataState;
    //类型
    private String transType;
    
    //延期手续费
    private String validPoundage;
	public String getRuleInfId() {
		return ruleInfId;
	}
	public void setRuleInfId(String ruleInfId) {
		this.ruleInfId = ruleInfId;
	}
	public String getRuleDspId() {
		return ruleDspId;
	}
	public void setRuleDspId(String ruleDspId) {
		this.ruleDspId = ruleDspId;
	}
	public String getMinAmount() {
		return minAmount;
	}
	public void setMinAmount(String minAmount) {
		this.minAmount = minAmount;
	}
	public String getMaxAmount() {
		return maxAmount;
	}
	public void setMaxAmount(String maxAmount) {
		this.maxAmount = maxAmount;
	}
	public String getDelayMonth() {
		return delayMonth;
	}
	public void setDelayMonth(String delayMonth) {
		this.delayMonth = delayMonth;
	}
	public String getDataState() {
		return dataState;
	}
	public void setDataState(String dataState) {
		this.dataState = dataState;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public String getValidPoundage() {
		return validPoundage;
	}
	public void setValidPoundage(String validPoundage) {
		this.validPoundage = validPoundage;
	}
    
}