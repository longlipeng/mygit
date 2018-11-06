package com.allinfinance.univer.issuer.dto.product;

import java.util.ArrayList;
import java.util.List;

import com.allinfinance.framework.dto.PageQueryDTO;

public class ValidPeriodRuleDspDTO extends PageQueryDTO {
	private static final long serialVersionUID = -2737375953682343761L;
	private String ruleDspId;
	private String ruleName;
	private String ruleDsp;
	private String entityId;
	private String ruleState;
	private String transType;
	private String oldTransType;
	private String transTypeName;
	private String branchMark;
	private String branchMarkName;
	private String deadLine;
	private String createTime;
	private String createUser;
	private String createUserName;
	private String modifyTime;
	private String modifyUser;
	private List<ValidPeriodRuleInfDTO> validPeriodRuleInfDTOs = new ArrayList<ValidPeriodRuleInfDTO>();
	private List<String> ruleDspIds;
	
	private ValidPeriodRuleInfDTO validPeriodRuleInfDTO=new ValidPeriodRuleInfDTO();
	

	public String getTransTypeName() {
		return transTypeName;
	}

	public void setTransTypeName(String transTypeName) {
		this.transTypeName = transTypeName;
	}

	public String getBranchMarkName() {
		return branchMarkName;
	}

	public void setBranchMarkName(String branchMarkName) {
		this.branchMarkName = branchMarkName;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public List<String> getRuleDspIds() {
		return ruleDspIds;
	}

	public void setRuleDspIds(List<String> ruleDspIds) {
		this.ruleDspIds = ruleDspIds;
	}

	public List<ValidPeriodRuleInfDTO> getValidPeriodRuleInfDTOs() {
		return validPeriodRuleInfDTOs;
	}

	public void setValidPeriodRuleInfDTOs(
			List<ValidPeriodRuleInfDTO> validPeriodRuleInfDTOs) {
		this.validPeriodRuleInfDTOs = validPeriodRuleInfDTOs;
	}

	public String getRuleDspId() {
		return ruleDspId;
	}

	public void setRuleDspId(String ruleDspId) {
		this.ruleDspId = ruleDspId;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getRuleDsp() {
		return ruleDsp;
	}

	public void setRuleDsp(String ruleDsp) {
		this.ruleDsp = ruleDsp;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getRuleState() {
		return ruleState;
	}

	public void setRuleState(String ruleState) {
		this.ruleState = ruleState;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getBranchMark() {
		return branchMark;
	}

	public void setBranchMark(String branchMark) {
		this.branchMark = branchMark;
	}

	public String getDeadLine() {
		return deadLine;
	}

	public void setDeadLine(String deadLine) {
		this.deadLine = deadLine;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public ValidPeriodRuleInfDTO getValidPeriodRuleInfDTO() {
		return validPeriodRuleInfDTO;
	}

	public void setValidPeriodRuleInfDTO(ValidPeriodRuleInfDTO validPeriodRuleInfDTO) {
		this.validPeriodRuleInfDTO = validPeriodRuleInfDTO;
	}

	public String getOldTransType() {
		return oldTransType;
	}

	public void setOldTransType(String oldTransType) {
		this.oldTransType = oldTransType;
	}
	

}