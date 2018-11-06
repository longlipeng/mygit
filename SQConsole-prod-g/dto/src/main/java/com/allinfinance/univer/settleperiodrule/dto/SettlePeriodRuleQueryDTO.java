package com.allinfinance.univer.settleperiodrule.dto;

import com.allinfinance.framework.dto.PageQueryDTO;

public class SettlePeriodRuleQueryDTO extends PageQueryDTO{
	 private String ruleNo;
	 private String ruleName;
	 private String state;
	 private String periodType;
	 private String ruleType;
	 private String entityId;
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	public String getRuleNo() {
		return ruleNo;
	}
	public void setRuleNo(String ruleNo) {
		this.ruleNo = ruleNo;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPeriodType() {
		return periodType;
	}
	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}
	public String getRuleType() {
		return ruleType;
	}
	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}
	 
}
