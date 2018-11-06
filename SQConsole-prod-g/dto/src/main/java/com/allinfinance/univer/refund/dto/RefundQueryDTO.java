package com.allinfinance.univer.refund.dto;

import com.allinfinance.framework.dto.PageQueryDTO;

public class RefundQueryDTO extends PageQueryDTO{
	
	private String orderId;
	
	private String startDate;
	
	private String endDate;
	
	private String mchntCd;
	
	private String authRlt;
	
	//商户所属收单机构号
	private String consumerId;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getMchntCd() {
		return mchntCd;
	}

	public void setMchntCd(String mchntCd) {
		this.mchntCd = mchntCd;
	}

	public String getAuthRlt() {
		return authRlt;
	}

	public void setAuthRlt(String authRlt) {
		this.authRlt = authRlt;
	}

	public String getConsumerId() {
		return consumerId;
	}

	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}
	
	
}
