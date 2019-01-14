package com.allinfinance.univer.report.dto;

import java.math.BigDecimal;

import com.allinfinance.univer.report.IreportDTO;

public class CardBalanceDatailDTO extends IreportDTO  {
	private static final long serialVersionUID = -5983787858988526509L;
	
	private String startDate;
	
	private String endDate;
	
	private String txnDate;
	
	private String cardNo;
	
	private String account;
	
	private String txnType;
	
	private BigDecimal txnAmt;
	
	private BigDecimal cardTotalBal;
	
	private String mchntCd;
	
	private String mchntName;
	
	private String termId;
	
	private String DebtOrCredit;
	
	private String origCardNo;
	
	

	public String getOrigCardNo() {
		return origCardNo;
	}

	public void setOrigCardNo(String origCardNo) {
		this.origCardNo = origCardNo;
	}

	public String getMchntName() {
		return mchntName;
	}

	public void setMchntName(String mchntName) {
		this.mchntName = mchntName;
	}

	public String getDebtOrCredit() {
		return DebtOrCredit;
	}

	public void setDebtOrCredit(String debtOrCredit) {
		DebtOrCredit = debtOrCredit;
	}

	public String getMchntCd() {
		return mchntCd;
	}

	public void setMchntCd(String mchntCd) {
		this.mchntCd = mchntCd;
	}

	public String getTermId() {
		return termId;
	}

	public void setTermId(String termId) {
		this.termId = termId;
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

	public String getTxnDate() {
		return txnDate;
	}

	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public BigDecimal getTxnAmt() {
		return txnAmt;
	}

	public void setTxnAmt(BigDecimal txnAmt) {
		this.txnAmt = txnAmt;
	}

	public BigDecimal getCardTotalBal() {
		return cardTotalBal;
	}

	public void setCardTotalBal(BigDecimal cardTotalBal) {
		this.cardTotalBal = cardTotalBal;
	}
	
	
}
