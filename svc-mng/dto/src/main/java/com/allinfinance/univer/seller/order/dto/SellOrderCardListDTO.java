package com.allinfinance.univer.seller.order.dto;

import com.allinfinance.framework.dto.BaseDTO;
/***
 * 
 * @author dawn
 *
 */
public class SellOrderCardListDTO extends BaseDTO{

	/**
	 *订单卡明细 
	 */
	private static final long serialVersionUID = 1L;

	private String orderCardListId;
	
	private String orderListId;
	
	private String orderId;
	
	private String cardholderId;
	
	private String lastName;
	
	private String firstName;
	
	private String externalId;
	
	private String cardNo;
	
	private String cardPinState;
	
	private String cardState;
	
	private String legCardNo;
	
	private String creditAmount;
	
	private String cardNoArray[];
	
	private String startCardNo;
	
	private String endCardNo;

	private String createTime;
	    
	private String createUser;
	    
	    
	private String modifyTime;
	    
	private String modifyUser;
	    
	private String firstEntityId;
	
	private String productId;
	
	private String processEntityId;
	
	private String certType;
	
	private String certNo;
	
	private String isCurCustomer;
	
	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getOrderListId() {
		return orderListId;
	}

	public void setOrderListId(String orderListId) {
		this.orderListId = orderListId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCardholderId() {
		return cardholderId;
	}

	public void setCardholderId(String cardholderId) {
		this.cardholderId = cardholderId;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardPinState() {
		return cardPinState;
	}

	public void setCardPinState(String cardPinState) {
		this.cardPinState = cardPinState;
	}

	public String getCardState() {
		return cardState;
	}

	public void setCardState(String cardState) {
		this.cardState = cardState;
	}

	public String getLegCardNo() {
		return legCardNo;
	}

	public void setLegCardNo(String legCardNo) {
		this.legCardNo = legCardNo;
	}

	public String getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(String creditAmount) {
		this.creditAmount = creditAmount;
	}

	public String getOrderCardListId() {
		return orderCardListId;
	}

	public void setOrderCardListId(String orderCardListId) {
		this.orderCardListId = orderCardListId;
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

	

	public String[] getCardNoArray() {
		return cardNoArray;
	}

	public void setCardNoArray(String[] cardNoArray) {
		this.cardNoArray = cardNoArray;
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

	public String getFirstEntityId() {
		return firstEntityId;
	}

	public void setFirstEntityId(String firstEntityId) {
		this.firstEntityId = firstEntityId;
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

	public String getIsCurCustomer() {
		return isCurCustomer;
	}

	public void setIsCurCustomer(String isCurCustomer) {
		this.isCurCustomer = isCurCustomer;
	}

	
	
	
}
