package com.allinfinance.univer.seller.order.dto;

import java.util.List;

import com.allinfinance.framework.dto.BaseDTO;
import com.allinfinance.univer.entity.dto.BankDTO;

/**
 * 订单付款信息DTO
 * 2012-02-25
 * */
public class SellOrderPaymentDTO extends BaseDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -455838297220703317L;
	
	private String paymentId;
	private String orderId;
	private String[] orderIds;
	private String orderType;
	private String paymentState;
	private String paymentType;
	private String paymentAmount;
	private String remark;
	private String createUser;
	private String createTime;
	private String modifyUser;
	private String modifyTime;
	private String dataState;
	private String paymentIds;
	private String totalPrice;
	private String firstEntityId;
	private String orderState;

	private List<BankDTO> lstBankDTO;
	private String intoBankId;
	
	public List<BankDTO> getLstBankDTO() {
		return lstBankDTO;
	}
	public void setLstBankDTO(List<BankDTO> lstBankDTO) {
		this.lstBankDTO = lstBankDTO;
	}
	public String getIntoBankId() {
		return intoBankId;
	}
	public void setIntoBankId(String intoBankId) {
		this.intoBankId = intoBankId;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getPaymentState() {
		return paymentState;
	}
	public void setPaymentState(String paymentState) {
		this.paymentState = paymentState;
	}
	public String getPaymentIds() {
		return paymentIds;
	}
	public void setPaymentIds(String paymentIds) {
		this.paymentIds = paymentIds;
	}
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getModifyUser() {
		return modifyUser;
	}
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
	public String getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getDataState() {
		return dataState;
	}
	public void setDataState(String dataState) {
		this.dataState = dataState;
	}
	public String[] getOrderIds() {
		return orderIds;
	}
	public void setOrderIds(String[] orderIds) {
		this.orderIds = orderIds;
	}
	public String getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getFirstEntityId() {
		return firstEntityId;
	}
	public void setFirstEntityId(String firstEntityId) {
		this.firstEntityId = firstEntityId;
	}
    public String getOrderState() {
        return orderState;
    }
    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }
	
}