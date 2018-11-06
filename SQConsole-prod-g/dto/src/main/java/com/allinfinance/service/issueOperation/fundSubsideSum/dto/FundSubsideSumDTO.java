/**
 * Classname FundSubsideSumDTO.java
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
 *		yaoxin		2012-10-26
 * =============================================================================
 */

package com.allinfinance.service.issueOperation.fundSubsideSum.dto;


import java.math.BigDecimal;

import com.allinfinance.univer.report.IreportDTO;

/**
 * @author yaoxin
 *
 */
public class FundSubsideSumDTO extends IreportDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String queryDate;
	
	private String accountName;
	
	private String productName;
	
	private BigDecimal cardNumber;
	
	private BigDecimal amt;
	
	private BigDecimal sumAmt;

	public String getQueryDate() {
		return queryDate;
	}

	public void setQueryDate(String queryDate) {
		this.queryDate = queryDate;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(BigDecimal cardNumber) {
		this.cardNumber = cardNumber;
	}

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public BigDecimal getSumAmt() {
		return sumAmt;
	}

	public void setSumAmt(BigDecimal sumAmt) {
		this.sumAmt = sumAmt;
	}
	
}
