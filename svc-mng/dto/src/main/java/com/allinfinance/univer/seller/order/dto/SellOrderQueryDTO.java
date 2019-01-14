package com.allinfinance.univer.seller.order.dto;

import java.util.ArrayList;
import java.util.List;

import com.allinfinance.framework.dto.PageQueryDTO;

/***
 * 
 * @author dawn 订单查询DTO
 */
public class SellOrderQueryDTO extends PageQueryDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String orderId;

	private String orderType;

	// 订单状态列表
	private String orderStateArray;

	// 订单类型列表
	private String orderTypeArray;
	private String orderStateWithoutArray;

	private String orderDate;

	private String entityId;

	private String entityName;

	private String firstEntityId;

	private String firstEntityName;

	private String processEntityId;

	private String orderState;

	private String validityPeriod;

	private String createUserName;

	private String createTime;

	private String modifyUser;

	private String modifyTime;

	private String isImportCardFile;

	private String isCreateCardFile;

	private String isCreatePinFile;

	private String productId;

	private String createUser;
	
	private List<String> orderIdList = new ArrayList<String>();
	
	private String paymentTerm;
	
	private String paymentState;
	
	private String saleManName;
	
	private String processEntityName;
	
	private String orderDateStart;
	
	private String orderDateEnd;
	private String validFlag;
	
	private String oldCardNo;
	
	
    //无效合同则不允许录入订单
	private String contractState;
	
	/**
	 * 增加按照卡号查找相关联的订单
	 */
	private String cardNo;
	
	private String batchNo;
	
	private String invoiceState;

	
	private String[] newOrderList;
	
	
	private String channel;//渠道号
	private String branchId;//网点
	
	private String phoneNum;//手机号
	private String idNo;//证件号
	private String idType;//证件类型
	
	

    public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String[] getNewOrderList() {
        return newOrderList;
    }

    public void setNewOrderList(String[] newOrderList) {
        this.newOrderList = newOrderList;
    }

    public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	private String paymentType;
	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getValidFlag() {
		return validFlag;
	}

	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}

	public String getPaymentTerm() {
		return paymentTerm;
	}

	public void setPaymentTerm(String paymentTerm) {
		this.paymentTerm = paymentTerm;
	}

	public String getPaymentState() {
		return paymentState;
	}

	public void setPaymentState(String paymentState) {
		this.paymentState = paymentState;
	}

	public String getSaleManName() {
		return saleManName;
	}

	public void setSaleManName(String saleManName) {
		this.saleManName = saleManName;
	}

	public String getProcessEntityName() {
		return processEntityName;
	}

	public void setProcessEntityName(String processEntityName) {
		this.processEntityName = processEntityName;
	}

	public String getOrderDateStart() {
		return orderDateStart;
	}

	public void setOrderDateStart(String orderDateStart) {
		this.orderDateStart = orderDateStart;
	}

	public String getOrderDateEnd() {
		return orderDateEnd;
	}

	public void setOrderDateEnd(String orderDateEnd) {
		this.orderDateEnd = orderDateEnd;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
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

	public String getOrderState() {
		return orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	public String getValidityPeriod() {
		return validityPeriod;
	}

	public void setValidityPeriod(String validityPeriod) {
		this.validityPeriod = validityPeriod;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
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

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getOrderStateArray() {
		return orderStateArray;
	}

	public void setOrderStateArray(String orderStateArray) {
		this.orderStateArray = orderStateArray;
	}

	public String getOrderTypeArray() {
		return orderTypeArray;
	}

	public void setOrderTypeArray(String orderTypeArray) {
		this.orderTypeArray = orderTypeArray;
	}

	public String getIsImportCardFile() {
		return isImportCardFile;
	}

	public void setIsImportCardFile(String isImportCardFile) {
		this.isImportCardFile = isImportCardFile;
	}

	public String getIsCreateCardFile() {
		return isCreateCardFile;
	}

	public void setIsCreateCardFile(String isCreateCardFile) {
		this.isCreateCardFile = isCreateCardFile;
	}

	public String getIsCreatePinFile() {
		return isCreatePinFile;
	}

	public void setIsCreatePinFile(String isCreatePinFile) {
		this.isCreatePinFile = isCreatePinFile;
	}

	public String getOrderStateWithoutArray() {
		return orderStateWithoutArray;
	}

	public void setOrderStateWithoutArray(String orderStateWithoutArray) {
		this.orderStateWithoutArray = orderStateWithoutArray;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public List<String> getOrderIdList() {
		return orderIdList;
	}

	public void setOrderIdList(List<String> orderIdList) {
		this.orderIdList = orderIdList;
	}

	public String getFirstEntityName() {
		return firstEntityName;
	}

	public void setFirstEntityName(String firstEntityName) {
		this.firstEntityName = firstEntityName;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getContractState() {
		return contractState;
	}

	public void setContractState(String contractState) {
		this.contractState = contractState;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

    public String getInvoiceState() {
        return invoiceState;
    }

    public void setInvoiceState(String invoiceState) {
        this.invoiceState = invoiceState;
    }

	public String getOldCardNo() {
		return oldCardNo;
	}

	public void setOldCardNo(String oldCardNo) {
		this.oldCardNo = oldCardNo;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	
	
}
