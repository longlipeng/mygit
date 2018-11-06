package com.allinfinance.univer.report.dto;

import java.math.BigDecimal;

import com.allinfinance.univer.report.IreportDTO;
/**
 * 卡余额报表
 */
public class CardBalanceDTO extends IreportDTO{
	
	private static final long serialVersionUID = 8033831574444979351L;
	
	private String dataDate;
	
	private String accNo;
	
	private BigDecimal accBal;
	
	private BigDecimal accTotalBal;
	
	private String accBalInfo;
	
	

	public String getAccBalInfo() {
		return accBalInfo;
	}

	public void setAccBalInfo(String accBalInfo) {
		this.accBalInfo = accBalInfo;
	}

	public String getDataDate() {
		return dataDate;
	}

	public void setDataDate(String dataDate) {
		this.dataDate = dataDate;
	}

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public BigDecimal getAccBal() {
		return accBal;
	}

	public void setAccBal(BigDecimal accBal) {
		this.accBal = accBal;
	}

	public BigDecimal getAccTotalBal() {
		return accTotalBal;
	}

	public void setAccTotalBal(BigDecimal accTotalBal) {
		this.accTotalBal = accTotalBal;
	}
	
	
}
