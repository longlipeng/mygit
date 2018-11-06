/**
 * Classname IssuerSellSummaryDTO.java
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
 *		administrator		2012-10-25
 * =============================================================================
 */

package com.allinfinance.service.seller.warehouseSummary.dto;

import com.allinfinance.univer.report.IreportDTO;


/**
 * @author administrator
 * 
 */
public class WarehouseSummaryDTO extends IreportDTO {
	
	private static final long serialVersionUID = 1L;

	private String productId;
	
	private String productName;
	/**开始日期*/
	private String startDate;	
	/**结束日期*/
	private String endDate;
	
	
	private String dateTime;
	private String onymousStat;
	private String cardType;
	private String incard;
	private String outcard;
	private String stockNumEnd;
	private String faceValue;
	private String stockNumStart;
	
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
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
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
	public String getIncard() {
		return incard;
	}
	public void setIncard(String incard) {
		this.incard = incard;
	}
	public String getOutcard() {
		return outcard;
	}
	public void setOutcard(String outcard) {
		this.outcard = outcard;
	}
	public String getStockNumEnd() {
		return stockNumEnd;
	}
	public void setStockNumEnd(String stockNumEnd) {
		this.stockNumEnd = stockNumEnd;
	}
	public String getFaceValue() {
		return faceValue;
	}
	public void setFaceValue(String faceValue) {
		this.faceValue = faceValue;
	}
	public String getStockNumStart() {
		return stockNumStart;
	}
	public void setStockNumStart(String stockNumStart) {
		this.stockNumStart = stockNumStart;
	}
	
	

}
