/**
 * Classname SellerIssueSellDetailDTO.java
 *
 * Version information
 *
 * Licensed Property to HuaTeng Data Co., Ltd.
 * 
 * (C) Copyright of HuaTeng Data Co., Ltd. 2010
 *     All Rights Reserved.
 *
 * Project Info: HuaTeng Data Internet Acquiring  Project
 * 
 * Modification History:
 * =============================================================================
 *		Author		Date		Description
 *   ------------ ---------- ---------------------------------------------------
 *		htd033		2012-11-8
 * =============================================================================
 */

package com.allinfinance.service.sellOperation.sellerIssueSellDetail.dto;

import java.sql.Date;
import java.sql.Timestamp;

import com.allinfinance.univer.report.IreportDTO;

/**
 * @author wpf
 * 
 */
public class SellerIssueSellDetailDTO extends IreportDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private String sellerId;
	private String sellerName;
	private String startDate;
	private String endDate;
	private String queryType;
	private String nsellerId;//下级营销机构ID
	private String nsellerName;//下级营销机构名称


	/*订单日期*/
	private Date orderDate;
	/*激活日期*/
	private Date actDate;
	/*订单ID*/
	private String orderId;
	/*订单类型*/
	private String orderType;
	/*客户ID*/
	private String customerId;
	/*客户名称*/
	private String customerName;
	/*订单卡片数量*/
	private String cardQuantity;
	/*订单金额*/
	private String amount;
	/*产品名称*/
	private String prouctName;
	/*面额值*/
	private String faceValue;
	/*面额类型*/
	private String faceType;
	/*卡费*/
	private String cardIssueFee;
	/*年费*/
	private String annualFee;
	/*包装费*/
	private String packageFee;
	/*快递费*/
	private String deliveryFee;
	/*附加费*/
	private String additionalFee;
	/*折扣费*/
	private String discountFee;
	/*订单总金额*/
	private String totalPrice;
	/*单卡操作时间*/
	private Timestamp operationTime;
	/*卡号*/
	private String cardNo;
	/*卡片操作序号*/
	private String cardManagementId;
	/*操作卡服务费*/
	private String serviceFee;
	/*卡片操作类型*/
	private String transType;
	private String sellChannel;//售卡方式
	private String termChannel;//售卡渠道号
	private String branchId;//售卡服务点
//	private String cardholderComment;//备注
	

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	
	

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getActDate() {
		return actDate;
	}

	public void setActDate(Date actDate) {
		this.actDate = actDate;
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

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCardQuantity() {
		return cardQuantity;
	}

	public void setCardQuantity(String cardQuantity) {
		this.cardQuantity = cardQuantity;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getProuctName() {
		return prouctName;
	}

	public void setProuctName(String prouctName) {
		this.prouctName = prouctName;
	}

	public String getFaceValue() {
		return faceValue;
	}

	public void setFaceValue(String faceValue) {
		this.faceValue = faceValue;
	}

	public String getFaceType() {
		return faceType;
	}

	public void setFaceType(String faceType) {
		this.faceType = faceType;
	}

	public String getCardIssueFee() {
		return cardIssueFee;
	}

	public void setCardIssueFee(String cardIssueFee) {
		this.cardIssueFee = cardIssueFee;
	}

	public String getAnnualFee() {
		return annualFee;
	}

	public void setAnnualFee(String annualFee) {
		this.annualFee = annualFee;
	}

	public String getPackageFee() {
		return packageFee;
	}

	public void setPackageFee(String packageFee) {
		this.packageFee = packageFee;
	}

	public String getDeliveryFee() {
		return deliveryFee;
	}

	public void setDeliveryFee(String deliveryFee) {
		this.deliveryFee = deliveryFee;
	}

	public String getAdditionalFee() {
		return additionalFee;
	}

	public void setAdditionalFee(String additionalFee) {
		this.additionalFee = additionalFee;
	}

	public String getDiscountFee() {
		return discountFee;
	}

	public void setDiscountFee(String discountFee) {
		this.discountFee = discountFee;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Timestamp getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(Timestamp operationTime) {
		this.operationTime = operationTime;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardManagementId() {
		return cardManagementId;
	}

	public void setCardManagementId(String cardManagementId) {
		this.cardManagementId = cardManagementId;
	}

	public String getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(String serviceFee) {
		this.serviceFee = serviceFee;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getNsellerId() {
		return nsellerId;
	}

	public void setNsellerId(String nsellerId) {
		this.nsellerId = nsellerId;
	}

	public String getNsellerName() {
		return nsellerName;
	}

	public void setNsellerName(String nsellerName) {
		this.nsellerName = nsellerName;
	}

	public String getSellChannel() {
		return sellChannel;
	}

	public void setSellChannel(String sellChannel) {
		this.sellChannel = sellChannel;
	}

	public String getTermChannel() {
		return termChannel;
	}

	public void setTermChannel(String termChannel) {
		this.termChannel = termChannel;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

//	public String getCardholderComment() {
//		return cardholderComment;
//	}
//
//	public void setCardholderComment(String cardholderComment) {
//		this.cardholderComment = cardholderComment;
//	}

}
