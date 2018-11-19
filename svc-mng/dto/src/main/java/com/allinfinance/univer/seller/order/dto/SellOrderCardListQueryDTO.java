package com.allinfinance.univer.seller.order.dto;

import com.allinfinance.framework.dto.PageQueryDTO;

/***
 * 订单卡明细
 * 
 * @author gouhao
 * 
 */
public class SellOrderCardListQueryDTO extends PageQueryDTO {

    /**
     *日志
     */
    private static final long serialVersionUID = 1L;
    /**
     * 发起机构ID
     */
    private String firstEntityId;
    /**
     * 发起机构ID
     */
    private String processEntityId;
    /**
     * orderListId
     */
    private String orderListId;
    /**
     * 订单号
     */
    private String orderId;
    /**
     * 订单状态
     */
    private String orderType;
    /**
     * cardholderId
     */
    private String cardholderId;
    /**
     * lastName
     */
    private String lastName;
    /**
     * firstName
     */
    private String firstName;
    /**
     * externalId
     */
    private String externalId;
    /**
     * 卡号
     */
    private String cardNo;
    /**
     * cardPinState
     */
    private String cardPinState;
    /**
     * 卡状态
     */
    private String cardState;
    /**
     * legCardNo
     */
    private String legCardNo;
    /**
     * creditAmount
     */
    private String creditAmount;
    /**
     * 开始卡号
     */
    private String startCardNo;
    /**
     * 结束卡号
     */
    private String endCardNo;
    /**
     * 产品ID
     */
    private String productId;
    /**
     * isCurCustomer
     */
    private String isCurCustomer;
    /**
     * isLastRowNum
     */
    private String isLastRowNum;
    /**
     * 订单列表
     */
    private String[] oldOrderList;
    
    private String checkState;
    
    

    public String getCheckState() {
		return checkState;
	}

	public void setCheckState(String checkState) {
		this.checkState = checkState;
	}

	public String[] getOldOrderList() {
        return oldOrderList;
    }

    public void setOldOrderList(String[] oldOrderList) {
        this.oldOrderList = oldOrderList;
    }

    public String getIsLastRowNum() {
        return isLastRowNum;
    }

    public void setIsLastRowNum(String isLastRowNum) {
        this.isLastRowNum = isLastRowNum;
    }

    public String getIsCurCustomer() {
        return isCurCustomer;
    }

    public void setIsCurCustomer(String isCurCustomer) {
        this.isCurCustomer = isCurCustomer;
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

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
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

}
