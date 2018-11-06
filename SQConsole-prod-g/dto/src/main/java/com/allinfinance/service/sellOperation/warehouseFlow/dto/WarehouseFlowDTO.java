/**
 * Classname WarehouseFlowDTO.java
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
 *		wpf		2012-10-18
 * =============================================================================
 */

package com.allinfinance.service.sellOperation.warehouseFlow.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.allinfinance.univer.report.IreportDTO;

/**
 * @author wpf
 * 
 */
public class WarehouseFlowDTO extends IreportDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -942068734166417637L;
	private String startDate;
	private String endDate;

	private String orderId;
	private String orderType;
	private Date orderDate;
	private String productName;
	private String onymousStat;
	private String cardType;
	private String faceType;
	private BigDecimal faceValue;
	private String issuerName1;
	private String issuerName2;
	private BigDecimal inNum;
	private BigDecimal outNum;
	private String userName;
	private Date stockDate;
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
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
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getOnymousStat() {
		return onymousStat;
	}
	public void setOnymousStat(String onymousStat) {
		this.onymousStat = onymousStat;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getFaceType() {
		return faceType;
	}
	public void setFaceType(String faceType) {
		this.faceType = faceType;
	}
	public BigDecimal getFaceValue() {
		return faceValue;
	}
	public void setFaceValue(BigDecimal faceValue) {
		this.faceValue = faceValue;
	}
	public String getIssuerName1() {
		return issuerName1;
	}
	public void setIssuerName1(String issuerName1) {
		this.issuerName1 = issuerName1;
	}
	public String getIssuerName2() {
		return issuerName2;
	}
	public void setIssuerName2(String issuerName2) {
		this.issuerName2 = issuerName2;
	}
	public BigDecimal getInNum() {
		return inNum;
	}
	public void setInNum(BigDecimal inNum) {
		this.inNum = inNum;
	}
	public BigDecimal getOutNum() {
		return outNum;
	}
	public void setOutNum(BigDecimal outNum) {
		this.outNum = outNum;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getStockDate() {
		return stockDate;
	}
	public void setStockDate(Date stockDate) {
		this.stockDate = stockDate;
	}

}
