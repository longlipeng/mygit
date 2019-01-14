package com.allinfinance.univer.issuer.dto.product;

import java.util.List;

import com.allinfinance.framework.dto.PageQueryDTO;

public class ValidPeriodRuleQueryDTO extends PageQueryDTO{
    private String ruleId;
    private String ruleName;
    private String ruleDsp;
    private String entityId;
    private String ruleState;
    private String transType;
    //延期基准
    private String branchMark;
    private String deadLine;
    private String createTime;
    private String createUser;
    private String modifyTime;
    private String modifyUser;
    private ValidPeriodRuleDspDTO validPeriodRuleDspDTO=new ValidPeriodRuleDspDTO();
    private List<ValidPeriodRuleInfDTO> list;
  
   
    public ValidPeriodRuleDspDTO getValidPeriodRuleDspDTO() {
		return validPeriodRuleDspDTO;
	}


	public void setValidPeriodRuleDspDTO(ValidPeriodRuleDspDTO validPeriodRuleDspDTO) {
		this.validPeriodRuleDspDTO = validPeriodRuleDspDTO;
	}


	public List<ValidPeriodRuleInfDTO> getList() {
		return list;
	}


	public void setList(List<ValidPeriodRuleInfDTO> list) {
		this.list = list;
	}


	public String getRuleId() {
		return ruleId;
	}


	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
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
    

}