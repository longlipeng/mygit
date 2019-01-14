package com.allinfinance.univer.report;

import java.io.Serializable;

public class IreportDTO extends Object implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String reportName;
	private String reportFileName;
	private String reportType;
	private String IssuerId;
	private String IssuerName;
	private String[] parValue;
	public String[] getParValue() {
		return parValue;
	}
	public void setParValue(String[] parValue) {
		this.parValue = parValue;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public String getIssuerId() {
		return IssuerId;
	}
	public void setIssuerId(String issuerId) {
		IssuerId = issuerId;
	}
	public String getIssuerName() {
		return IssuerName;
	}
	public void setIssuerName(String issuerName) {
		IssuerName = issuerName;
	}
	public String getReportFileName() {
		return reportFileName;
	}
	public void setReportFileName(String reportFileName) {
		this.reportFileName = reportFileName;
	}
}