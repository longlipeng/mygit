package com.allinfinance.univer.seller.order.dto;

import com.allinfinance.framework.dto.PageQueryDTO;

public class SellOrderOrigCardListQueryDTO extends PageQueryDTO{

	private static final long serialVersionUID = -1139389074186582411L;
	
	private String orderId;
	private String startCardNo;
	private String endCardNo;
	private String cardNo;
	private String orderType;
	
	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getStartCardNo() {
		return startCardNo;
	}

	public void setStartCardNo(String startCardNo) {
		this.startCardNo = startCardNo;
	}

	public String getEndCardNo() {
		return endCardNo;
	}

	public void setEndCardNo(String endCardNo) {
		this.endCardNo = endCardNo;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
}
