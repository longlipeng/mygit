package com.allinfinance.shangqi.gateway.dto;

import com.allinfinance.framework.dto.PageQueryDTO;



public class EquityAccountTranChinaDTO extends PageQueryDTO{
	
	
	
	private String cardNo;
	/**信息类别  100简要信息 200消费明细 300充值明细 400 积分累积明细 500 圈存明细****/
	private String infoType;
	private String startTime;
	private String endTime;
	private String cardholderMobile;
	private String idType;
	private String idNo;
	
	
	
	
	
	
	
	
	public String getCardholderMobile() {
		return cardholderMobile;
	}
	public void setCardholderMobile(String cardholderMobile) {
		this.cardholderMobile = cardholderMobile;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getInfoType() {
		return infoType;
	}
	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	
	
	
	
	

	
	
	
}
