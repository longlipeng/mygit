/**
 * Classname sellerSellSummaryDTO.java
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
 *		administrator		2012-10-22
 * =============================================================================
 */

package com.allinfinance.service.issueOperation.issuerStockSum.dto;

import com.allinfinance.univer.report.IreportDTO;


/**
 * @author administrator
 * 
 */
public class IssuerStockSumDTO extends IreportDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	private String productId;
	private String productName;
	private String startDate;
	private String endDate;
	
	
	private String dateTime;
	private String onymousStat;
	private String cardType;
	private Long incard;
	private Long outcard;
	private Long stockNumStart;
	private Long stockNumEnd;
	private Long faceValue;
	

	public Long getFaceValue() {
		return faceValue;
	}

	public void setFaceValue(Long faceValue) {
		this.faceValue = faceValue;
	}

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

	

	public Long getIncard() {
		return incard;
	}

	public void setIncard(Long incard) {
		this.incard = incard;
	}

	public Long getOutcard() {
		return outcard;
	}

	public void setOutcard(Long outcard) {
		this.outcard = outcard;
	}
	

	public Long getStockNumStart() {
		return stockNumStart;
	}

	public void setStockNumStart(Long stockNumStart) {
		this.stockNumStart = stockNumStart;
	}

	public Long getStockNumEnd() {
		return stockNumEnd;
	}

	public void setStockNumEnd(Long stockNumEnd) {
		this.stockNumEnd = stockNumEnd;
	}
	
}
