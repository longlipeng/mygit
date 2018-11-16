package com.huateng.po.base;

import java.io.Serializable;

public class TblAgentDivide implements Serializable{
    
	private String uuid;  //主键
    private String agentNo;
    private String divideType;
    private String discCd;
    private String profit;
    private String extend1;
    private String extend2;
    private String extend3;
    private String extend4;
    private String extend5;
    private double minValue;
    private double maxValue;
    private String state;
    private String crtPer;
    private String crtDate;
    private String upPer;
    private String upDate;


	public String getProfit() {
		return profit;
	}
	public void setProfit(String profit) {
		this.profit = profit;
	}
	public String getExtend1() {
		return extend1;
	}
	public void setExtend1(String extend1) {
		this.extend1 = extend1;
	}
	public String getExtend2() {
		return extend2;
	}
	public void setExtend2(String extend2) {
		this.extend2 = extend2;
	}
	public String getExtend3() {
		return extend3;
	}
	public void setExtend3(String extend3) {
		this.extend3 = extend3;
	}
	public String getExtend4() {
		return extend4;
	}
	public void setExtend4(String extend4) {
		this.extend4 = extend4;
	}
	public String getExtend5() {
		return extend5;
	}
	public void setExtend5(String extend5) {
		this.extend5 = extend5;
	}
	public String getAgentNo() {
		return agentNo;
	}
	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}
	public String getDivideType() {
		return divideType;
	}
	public void setDivideType(String divideType) {
		this.divideType = divideType;
	}
	public String getDiscCd() {
		return discCd;
	}
	public void setDiscCd(String discCd) {
		this.discCd = discCd;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public double getMinValue() {
		return minValue;
	}
	public void setMinValue(double minValue) {
		this.minValue = minValue;
	}
	public double getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCrtPer() {
		return crtPer;
	}
	public void setCrtPer(String crtPer) {
		this.crtPer = crtPer;
	}
	public String getCrtDate() {
		return crtDate;
	}
	public void setCrtDate(String crtDate) {
		this.crtDate = crtDate;
	}
	public String getUpPer() {
		return upPer;
	}
	public void setUpPer(String upPer) {
		this.upPer = upPer;
	}
	public String getUpDate() {
		return upDate;
	}
	public void setUpDate(String upDate) {
		this.upDate = upDate;
	}
	
}
