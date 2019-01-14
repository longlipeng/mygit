package com.allinfinance.univer.seller.order.dto;

import com.allinfinance.framework.dto.PageQueryDTO;


public class OrderReadyDTO extends PageQueryDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public String[] cardNoArray;
	
	public String cardNo;
	
	public String startCardNo;
	
	public String endCardNo;

	public String orderId;
	
	public String processEntityId;

	public String productId;
	
	public String orderType;
	public String orderListId;
	
	public String orderCardListId;
	public String firstEntityId;
	
	private String lastRowNum;
	
	private String realAmount;
	private String cardLayoutId;
	private String faceValueType;
	private String faceValue;
	private String cardValidDate;
	
	
	
	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getLastRowNum() {
		return lastRowNum;
	}

	public void setLastRowNum(String lastRowNum) {
		this.lastRowNum = lastRowNum;
	}

	public String[] getCardNoArray() {
		return cardNoArray;
	}

	public void setCardNoArray(String[] cardNoArray) {
		this.cardNoArray = cardNoArray;
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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getProcessEntityId() {
		return processEntityId;
	}

	public void setProcessEntityId(String processEntityId) {
		this.processEntityId = processEntityId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getOrderListId() {
		return orderListId;
	}

	public void setOrderListId(String orderListId) {
		this.orderListId = orderListId;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderCardListId() {
		return orderCardListId;
	}

	public void setOrderCardListId(String orderCardListId) {
		this.orderCardListId = orderCardListId;
	}

	public String getFirstEntityId() {
		return firstEntityId;
	}

	public void setFirstEntityId(String firstEntityId) {
		this.firstEntityId = firstEntityId;
	}

	public String getRealAmount() {
		return realAmount;
	}

	public void setRealAmount(String realAmount) {
		this.realAmount = realAmount;
	}

	public String getCardLayoutId() {
		return cardLayoutId;
	}

	public void setCardLayoutId(String cardLayoutId) {
		this.cardLayoutId = cardLayoutId;
	}

	public String getFaceValueType() {
		return faceValueType;
	}

	public void setFaceValueType(String faceValueType) {
		this.faceValueType = faceValueType;
	}

	public String getFaceValue() {
		return faceValue;
	}

	public void setFaceValue(String faceValue) {
		this.faceValue = faceValue;
	}

	public String getCardValidDate() {
		return cardValidDate;
	}

	public void setCardValidDate(String cardValidDate) {
		this.cardValidDate = cardValidDate;
	}
	
	
	
}
