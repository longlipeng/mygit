/**
 * Classname WarehouseSummaryDTO.java
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
 *		htd033		2012-11-9
 * =============================================================================
 */

package com.allinfinance.service.sellOperation.warehouseSummary.dto;

import java.math.BigDecimal;

import com.allinfinance.univer.report.IreportDTO;

/**
 * @author wpf
 * 
 */
public class WarehouseSummaryDTO extends IreportDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String startDate;
	private String endDate;
	private String productId;
	private String productName;

	private String stockDate;
	private String onymousStat;
	private String cardType;
	private BigDecimal incard;
	private BigDecimal outcard;
	private BigDecimal endStockNum;
	private BigDecimal faceValue;
	private BigDecimal startStockNum;

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

	public String getStockDate() {
		return stockDate;
	}

	public void setStockDate(String stockDate) {
		this.stockDate = stockDate;
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

	public BigDecimal getIncard() {
		return incard;
	}

	public void setIncard(BigDecimal incard) {
		this.incard = incard;
	}

	public BigDecimal getOutcard() {
		return outcard;
	}

	public void setOutcard(BigDecimal outcard) {
		this.outcard = outcard;
	}

	public BigDecimal getEndStockNum() {
		return endStockNum;
	}

	public void setEndStockNum(BigDecimal endStockNum) {
		this.endStockNum = endStockNum;
	}

	public BigDecimal getFaceValue() {
		return faceValue;
	}

	public void setFaceValue(BigDecimal faceValue) {
		this.faceValue = faceValue;
	}

	public BigDecimal getStartStockNum() {
		return startStockNum;
	}

	public void setStartStockNum(BigDecimal startStockNum) {
		this.startStockNum = startStockNum;
	}

}
