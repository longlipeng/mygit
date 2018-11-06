package com.allinfinance.univer.servicefeerule.dto;

public class CaclInfView {
	private String amountStart;
	private String amountEnd;
	private String feeMin;
	private String feeMax;
	private Long rateValue;
	private double rateType;
	private double splitProfit;
	
	
	public double getSplitProfit() {
		return splitProfit;
	}
	public void setSplitProfit(double splitProfit) {
		this.splitProfit = splitProfit;
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
	public Long getRateValue() {
		return rateValue;
	}
	public void setRateValue(Long rateValue) {
		this.rateValue = rateValue;
	}
	public double getRateType() {
		return rateType;
	}
	public void setRateType(double rateType) {
		this.rateType = rateType;
	}
	
	
}