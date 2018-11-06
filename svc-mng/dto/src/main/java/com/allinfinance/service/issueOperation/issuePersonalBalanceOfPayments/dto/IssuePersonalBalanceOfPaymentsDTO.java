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

package com.allinfinance.service.issueOperation.issuePersonalBalanceOfPayments.dto;

import java.math.BigDecimal;

import com.allinfinance.univer.report.IreportDTO;

public class IssuePersonalBalanceOfPaymentsDTO extends IreportDTO {
	private static final long serialVersionUID = 1L;
	private String startDate;
	private String endDate;
	private String userId;

	private String userName;
	private BigDecimal num1;
	private BigDecimal num2;
	private BigDecimal num3;
	private BigDecimal num4;
	private BigDecimal num5;
	private BigDecimal sum1;
	
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public BigDecimal getNum1() {
		return num1;
	}
	public void setNum1(BigDecimal num1) {
		this.num1 = num1;
	}
	public BigDecimal getNum2() {
		return num2;
	}
	public void setNum2(BigDecimal num2) {
		this.num2 = num2;
	}
	public BigDecimal getNum3() {
		return num3;
	}
	public void setNum3(BigDecimal num3) {
		this.num3 = num3;
	}
	public BigDecimal getNum4() {
		return num4;
	}
	public void setNum4(BigDecimal num4) {
		this.num4 = num4;
	}
	public BigDecimal getNum5() {
		return num5;
	}
	public void setNum5(BigDecimal num5) {
		this.num5 = num5;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public BigDecimal getSum1() {
		return sum1;
	}
	public void setSum1(BigDecimal sum1) {
		this.sum1 = sum1;
	}
	
	
	
}