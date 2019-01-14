/**
 * Classname IssuePersonalBalanceOfPaymentsDTO.java
 *
 * Version information
 *
 * Licensed Property to HuaTeng Data Co.; Ltd.
 * 
 * (C) Copyright of HuaTeng Data Co.; Ltd. 2010
 *     All Rights Reserved.
 *
 * Project Info: HuaTeng Data Internet Acquiring  Project
 * 
 * Modification History:
 * =============================================================================
 *		Author		Date		Description
 *   ------------ ---------- ---------------------------------------------------
 *		zqs		2013-04-09
 * =============================================================================
 */

package com.allinfinance.univer.report.dto;

import com.allinfinance.univer.report.IreportDTO;

public class IssuePersonalBalanceOfPaymentsDTO extends IreportDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String startDate;
	private String endDate;
	private String userId;
	
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	
	
	
	

}
