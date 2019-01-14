package com.allinfinance.univer.servicefeerule.dto;

import java.util.List;

import com.allinfinance.framework.dto.BaseDTO;

public class ServiceFeeRuleDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;
    private String ruleNo;
    private String ruleStep;
    private String result;
    // 操作符
    private String operand1;
    // 运算符
    private String operator1;
    private String operand2;
    private String operator2;
    private String operand3;

    private String ruleName;
    private String ruleDesc;
    private String entityId;
    private String state;
    private String amountStart;
    private String amountEnd;
    private String rateValue;
    private String feeMin;
    private String feeMax;
    private String calcType;

    private List<ServiceFeeRuleDTO> serviceFeeRuleDTOList;
    private int serviceFeeRuleDTOListSize;

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRuleDesc() {
        return ruleDesc;
    }

    public void setRuleDesc(String ruleDesc) {
        this.ruleDesc = ruleDesc;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAmountStart() {
        return amountStart;
    }

    public void setAmountStart(String amountStart) {
        this.amountStart = amountStart;
    }

    public String getAmountEnd() {
        return amountEnd;
    }

    public void setAmountEnd(String amountEnd) {
        this.amountEnd = amountEnd;
    }

    public String getRateValue() {
        return rateValue;
    }

    public void setRateValue(String rateValue) {
        this.rateValue = rateValue;
    }

    public String getFeeMin() {
        return feeMin;
    }

    public void setFeeMin(String feeMin) {
        this.feeMin = feeMin;
    }

    public String getFeeMax() {
        return feeMax;
    }

    public void setFeeMax(String feeMax) {
        this.feeMax = feeMax;
    }

    public String getCalcType() {
        return calcType;
    }

    public void setCalcType(String calcType) {
        this.calcType = calcType;
    }

    public String getRuleNo() {
        return ruleNo;
    }

    public void setRuleNo(String ruleNo) {
        this.ruleNo = ruleNo;
    }

    public String getRuleStep() {
        return ruleStep;
    }

    public void setRuleStep(String ruleStep) {
        this.ruleStep = ruleStep;
    }

    public List<ServiceFeeRuleDTO> getServiceFeeRuleDTOList() {
        return serviceFeeRuleDTOList;
    }

    public void setServiceFeeRuleDTOList(List<ServiceFeeRuleDTO> serviceFeeRuleDTOList) {
        this.serviceFeeRuleDTOList = serviceFeeRuleDTOList;
    }

    public int getServiceFeeRuleDTOListSize() {
        return serviceFeeRuleDTOListSize;
    }

    public void setServiceFeeRuleDTOListSize(int serviceFeeRuleDTOListSize) {
        this.serviceFeeRuleDTOListSize = serviceFeeRuleDTOListSize;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getOperand1() {
        return operand1;
    }

    public void setOperand1(String operand1) {
        this.operand1 = operand1;
    }

    public String getOperator1() {
        return operator1;
    }

    public void setOperator1(String operator1) {
        this.operator1 = operator1;
    }

    public String getOperand2() {
        return operand2;
    }

    public void setOperand2(String operand2) {
        this.operand2 = operand2;
    }

    public String getOperator2() {
        return operator2;
    }

    public void setOperator2(String operator2) {
        this.operator2 = operator2;
    }

    public String getOperand3() {
        return operand3;
    }

    public void setOperand3(String operand3) {
        this.operand3 = operand3;
    }

}
