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

package com.allinfinance.service.issueOperation.issuerCardValSum.dto;

import com.allinfinance.univer.report.IreportDTO;


/**
 * @author administrator
 * 
 */
public class IssuerCardValSumDTO extends IreportDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String startDate;
	private String endDate;
	
	private String entityId;
	private String issuerName;
	private String accBal;
	private String actAmt;
	private String txnAmt;
	private String endAccBal;

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

	public String getIssuerName() {
		return issuerName;
	}

	public void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
	}

	public String getAccBal() {
		return accBal;
	}

	public void setAccBal(String accBal) {
		this.accBal = accBal;
	}

	public String getActAmt() {
		return actAmt;
	}

	public void setActAmt(String actAmt) {
		this.actAmt = actAmt;
	}

	public String getTxnAmt() {
		return txnAmt;
	}

	public void setTxnAmt(String txnAmt) {
		this.txnAmt = txnAmt;
	}

	public String getEndAccBal() {
		return endAccBal;
	}

	public void setEndAccBal(String endAccBal) {
		this.endAccBal = endAccBal;
	}

}
