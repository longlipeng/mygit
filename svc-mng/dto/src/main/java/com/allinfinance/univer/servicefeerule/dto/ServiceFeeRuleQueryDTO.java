package com.allinfinance.univer.servicefeerule.dto;

import com.allinfinance.framework.dto.PageQueryDTO;

public class ServiceFeeRuleQueryDTO extends PageQueryDTO{
	 private String ruleName;
	 private String ruleNo;
	 private String state;
	 private String ruleType;
	 private String entityId;
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public String getRuleNo() {
		return ruleNo;
	}
	public void setRuleNo(String ruleNo) {
		this.ruleNo = ruleNo;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getRuleType() {
		return ruleType;
	}
	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	 
}
