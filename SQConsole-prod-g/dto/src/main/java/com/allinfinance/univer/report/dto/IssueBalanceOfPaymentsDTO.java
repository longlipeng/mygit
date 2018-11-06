package com.allinfinance.univer.report.dto;

import com.allinfinance.univer.report.IreportDTO;

public class IssueBalanceOfPaymentsDTO extends IreportDTO  {
	private static final long serialVersionUID = -3728689235364535098L;

	/**
	 * 营销机构收支平衡表DTO
	 */
	private String startDate;
	
	private String endDate;
	
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
	
}
