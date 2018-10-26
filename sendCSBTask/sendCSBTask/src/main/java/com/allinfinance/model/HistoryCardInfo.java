package com.allinfinance.model;

public class HistoryCardInfo {
	private String cardNo;
	private String orderType;
	private String createTime;
	private String validDate;
	private String accBal;
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getValidDate() {
		return validDate;
	}
	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}
	public String getAccBal() {
		return accBal;
	}
	public void setAccBal(String accBal) {
		this.accBal = accBal;
	}
	@Override
	public String toString() {
		return "HistoryCardInfo [cardNo=" + cardNo + ", orderType=" + orderType + ", createTime=" + createTime
				+ ", validDate=" + validDate + ", accBal=" + accBal + "]";
	}
	

}
