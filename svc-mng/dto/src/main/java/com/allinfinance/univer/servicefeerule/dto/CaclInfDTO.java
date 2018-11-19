package com.allinfinance.univer.servicefeerule.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.allinfinance.framework.dto.BaseDTO;

public class CaclInfDTO extends BaseDTO {
	private String discCd;
	private int stepNo;
	//操作结果
	private int operRslt;
	//操作数1
	private String operand1;
	//运算符
	private String operator1;
	private Long operand2;
	private String operator2;
	private String operand3;
	//修改的用户标识
	private String recUpdUsrId;
	//修改日期
	private Date recUpdTs;
	//创建日期
	private Date recCrtTs;
	private List<String> serviceFeeRuleDTORuleStep;
	private List<String> serviceFeeRuleDTORateType;
	private List<String> serviceFeeRuleDTORateValue;
	private List<String> serviceFeeRuleDTOFeeMin;
	private List<String> serviceFeeRuleDTOFeeMax;
	private List<String> serviceFeeRuleDTOAmountStart;
	private List<String> serviceFeeRuleDTOAmountEnd;

	public List<String> getServiceFeeRuleDTORateType() {
		return serviceFeeRuleDTORateType;
	}

	public void setServiceFeeRuleDTORateType(List<String> serviceFeeRuleDTORateType) {
		this.serviceFeeRuleDTORateType = serviceFeeRuleDTORateType;
	}

	public List<String> getServiceFeeRuleDTORateValue() {
		return serviceFeeRuleDTORateValue;
	}

	public void setServiceFeeRuleDTORateValue(
			List<String> serviceFeeRuleDTORateValue) {
		this.serviceFeeRuleDTORateValue = serviceFeeRuleDTORateValue;
	}

	public List<String> getServiceFeeRuleDTOFeeMin() {
		return serviceFeeRuleDTOFeeMin;
	}

	public void setServiceFeeRuleDTOFeeMin(List<String> serviceFeeRuleDTOFeeMin) {
		this.serviceFeeRuleDTOFeeMin = serviceFeeRuleDTOFeeMin;
	}

	public List<String> getServiceFeeRuleDTOFeeMax() {
		return serviceFeeRuleDTOFeeMax;
	}

	public void setServiceFeeRuleDTOFeeMax(List<String> serviceFeeRuleDTOFeeMax) {
		this.serviceFeeRuleDTOFeeMax = serviceFeeRuleDTOFeeMax;
	}

	public List<String> getServiceFeeRuleDTOAmountStart() {
		return serviceFeeRuleDTOAmountStart;
	}

	public void setServiceFeeRuleDTOAmountStart(
			List<String> serviceFeeRuleDTOAmountStart) {
		this.serviceFeeRuleDTOAmountStart = serviceFeeRuleDTOAmountStart;
	}

	public List<String> getServiceFeeRuleDTOAmountEnd() {
		return serviceFeeRuleDTOAmountEnd;
	}

	public void setServiceFeeRuleDTOAmountEnd(
			List<String> serviceFeeRuleDTOAmountEnd) {
		this.serviceFeeRuleDTOAmountEnd = serviceFeeRuleDTOAmountEnd;
	}



	List<CaclInfDTO> caclInfList =new ArrayList<CaclInfDTO>();
	private long splitProfit;
	
	

	public List<String> getServiceFeeRuleDTORuleStep() {
		return serviceFeeRuleDTORuleStep;
	}

	public void setServiceFeeRuleDTORuleStep(List<String> serviceFeeRuleDTORuleStep) {
		this.serviceFeeRuleDTORuleStep = serviceFeeRuleDTORuleStep;
	}

	public long getSplitProfit() {
		return splitProfit;
	}

	public void setSplitProfit(long splitProfit) {
		this.splitProfit = splitProfit;
	}

	public String getDiscCd() {
		return discCd;
	}

	public void setDiscCd(String discCd) {
		this.discCd = discCd;
	}

	public int getStepNo() {
		return stepNo;
	}

	public void setStepNo(int stepNo) {
		this.stepNo = stepNo;
	}

	public int getOperRslt() {
		return operRslt;
	}

	public void setOperRslt(int operRslt) {
		this.operRslt = operRslt;
	}

	

	public String getOperator1() {
		return operator1;
	}

	public void setOperator1(String operator1) {
		this.operator1 = operator1;
	}

	


	public Long getOperand2() {
		return operand2;
	}

	public void setOperand2(Long operand2) {
		this.operand2 = operand2;
	}

	public String getOperator2() {
		return operator2;
	}

	public void setOperator2(String operator2) {
		this.operator2 = operator2;
	}

	

	public String getRecUpdUsrId() {
		return recUpdUsrId;
	}

	public void setRecUpdUsrId(String recUpdUsrId) {
		this.recUpdUsrId = recUpdUsrId;
	}



	public Date getRecUpdTs() {
		return recUpdTs;
	}

	public void setRecUpdTs(Date recUpdTs) {
		this.recUpdTs = recUpdTs;
	}

	public Date getRecCrtTs() {
		return recCrtTs;
	}

	public void setRecCrtTs(Date recCrtTs) {
		this.recCrtTs = recCrtTs;
	}

	public List<CaclInfDTO> getCaclInfList() {
		return caclInfList;
	}

	public void setCaclInfList(List<CaclInfDTO> caclInfList) {
		this.caclInfList = caclInfList;
	}

	public String getOperand1() {
		return operand1;
	}

	public void setOperand1(String operand1) {
		this.operand1 = operand1;
	}

	public String getOperand3() {
		return operand3;
	}

	public void setOperand3(String operand3) {
		this.operand3 = operand3;
	}

	
	

}
