package com.allinfinance.univer.seller.order.dto;

import com.allinfinance.framework.dto.PageQueryDTO;

public class SellOrderInputCardListDTO extends PageQueryDTO{
	/**
     *日志
     */
    private static final long serialVersionUID = 1L;
    /**
     * 录入卡号
     */
    private String cardNo;
    /**
     * 订单ID
     */
    private String orderId;
    
    
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
