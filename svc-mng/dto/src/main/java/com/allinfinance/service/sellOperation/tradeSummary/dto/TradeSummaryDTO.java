/**
 * Classname TradeSummaryDTO.java
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
 *		htd033		2012-11-12
 * =============================================================================
 */

package com.allinfinance.service.sellOperation.tradeSummary.dto;

import java.math.BigDecimal;

import com.allinfinance.univer.report.IreportDTO;

/**
 * @author wpf
 * 
 */
public class TradeSummaryDTO extends IreportDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String startDate;
	private String endDate;

	private String entityId;
	private String customerName;
	private BigDecimal talCredit;
	private BigDecimal creditNum;
	private BigDecimal talAmt;
	private BigDecimal talNum;
	private BigDecimal tallAmt;
	private BigDecimal cardNum;

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

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public BigDecimal getTalCredit() {
		return talCredit;
	}

	public void setTalCredit(BigDecimal talCredit) {
		this.talCredit = talCredit;
	}

	public BigDecimal getCreditNum() {
		return creditNum;
	}

	public void setCreditNum(BigDecimal creditNum) {
		this.creditNum = creditNum;
	}

	public BigDecimal getTalAmt() {
		return talAmt;
	}

	public void setTalAmt(BigDecimal talAmt) {
		this.talAmt = talAmt;
	}

	public BigDecimal getTalNum() {
		return talNum;
	}

	public void setTalNum(BigDecimal talNum) {
		this.talNum = talNum;
	}

	public BigDecimal getTallAmt() {
		return tallAmt;
	}

	public void setTallAmt(BigDecimal tallAmt) {
		this.tallAmt = tallAmt;
	}

	public BigDecimal getCardNum() {
		return cardNum;
	}

	public void setCardNum(BigDecimal cardNum) {
		this.cardNum = cardNum;
	}

}
