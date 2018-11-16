package com.huateng.po.reserve;

import java.io.Serializable;

public class TblBalanceReserveQuery implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String balanceNo;//交易流水号
	private String balanceDate;//交易日期
	private String balanceAcsBankNo;//ACS备付金存管账号
	private String balanceAcctBal;//备付金虚拟记账余额
	private String balanceAvlbBal;//备付金虚拟记账可用余额
	private String balanceAcsAcctBal;//ACS备付金存管账户可用余额
	private String balanceAcsAcctName;//ACS备付金存管账户名称
	private String balanceAvlbQuotaAmt;//可支取额度
	
	
	
	
	public String getBalanceNo() {
		return balanceNo;
	}
	public void setBalanceNo(String balanceNo) {
		this.balanceNo = balanceNo;
	}
	public String getBalanceDate() {
		return balanceDate;
	}
	public void setBalanceDate(String balanceDate) {
		this.balanceDate = balanceDate;
	}
	public String getBalanceAcsBankNo() {
		return balanceAcsBankNo;
	}
	public void setBalanceAcsBankNo(String balanceAcsBankNo) {
		this.balanceAcsBankNo = balanceAcsBankNo;
	}
	public String getBalanceAcctBal() {
		return balanceAcctBal;
	}
	public void setBalanceAcctBal(String balanceAcctBal) {
		this.balanceAcctBal = balanceAcctBal;
	}
	public String getBalanceAvlbBal() {
		return balanceAvlbBal;
	}
	public void setBalanceAvlbBal(String balanceAvlbBal) {
		this.balanceAvlbBal = balanceAvlbBal;
	}
	public String getBalanceAcsAcctBal() {
		return balanceAcsAcctBal;
	}
	public void setBalanceAcsAcctBal(String balanceAcsAcctBal) {
		this.balanceAcsAcctBal = balanceAcsAcctBal;
	}
	public String getBalanceAcsAcctName() {
		return balanceAcsAcctName;
	}
	public void setBalanceAcsAcctName(String balanceAcsAcctName) {
		this.balanceAcsAcctName = balanceAcsAcctName;
	}
	public String getBalanceAvlbQuotaAmt() {
		return balanceAvlbQuotaAmt;
	}
	public void setBalanceAvlbQuotaAmt(String balanceAvlbQuotaAmt) {
		this.balanceAvlbQuotaAmt = balanceAvlbQuotaAmt;
	}
	
	
	
}
