package com.huateng.po.reserve;

import java.io.Serializable;

public class TblHistoryQuery implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String historyNo;  //交易流水号
	private String historyDate; //交易日期
	private String historyAcctNo; //虚拟账号
	private String historyAcctName; //虚拟账户名称
	private String historyOpenBal; //期初虚拟记账余额
	private String historyEncBal; //期末虚拟记账余额
	private String historyTotalDbtrQty; //借记总笔数
	private String historyTotalCdtrQty; //贷记总笔数
	private String historyTotalDbtrAmt; //借记总金额
	private String historyTotalCdtrAmt; //贷记总金额
	
	
	
	
	public String getHistoryNo() {
		return historyNo;
	}
	public void setHistoryNo(String historyNo) {
		this.historyNo = historyNo;
	}
	public String getHistoryDate() {
		return historyDate;
	}
	public void setHistoryDate(String historyDate) {
		this.historyDate = historyDate;
	}
	public String getHistoryAcctNo() {
		return historyAcctNo;
	}
	public void setHistoryAcctNo(String historyAcctNo) {
		this.historyAcctNo = historyAcctNo;
	}
	public String getHistoryAcctName() {
		return historyAcctName;
	}
	public void setHistoryAcctName(String historyAcctName) {
		this.historyAcctName = historyAcctName;
	}
	public String getHistoryOpenBal() {
		return historyOpenBal;
	}
	public void setHistoryOpenBal(String historyOpenBal) {
		this.historyOpenBal = historyOpenBal;
	}
	public String getHistoryEncBal() {
		return historyEncBal;
	}
	public void setHistoryEncBal(String historyEncBal) {
		this.historyEncBal = historyEncBal;
	}
	public String getHistoryTotalDbtrQty() {
		return historyTotalDbtrQty;
	}
	public void setHistoryTotalDbtrQty(String historyTotalDbtrQty) {
		this.historyTotalDbtrQty = historyTotalDbtrQty;
	}
	public String getHistoryTotalCdtrQty() {
		return historyTotalCdtrQty;
	}
	public void setHistoryTotalCdtrQty(String historyTotalCdtrQty) {
		this.historyTotalCdtrQty = historyTotalCdtrQty;
	}
	public String getHistoryTotalDbtrAmt() {
		return historyTotalDbtrAmt;
	}
	public void setHistoryTotalDbtrAmt(String historyTotalDbtrAmt) {
		this.historyTotalDbtrAmt = historyTotalDbtrAmt;
	}
	public String getHistoryTotalCdtrAmt() {
		return historyTotalCdtrAmt;
	}
	public void setHistoryTotalCdtrAmt(String historyTotalCdtrAmt) {
		this.historyTotalCdtrAmt = historyTotalCdtrAmt;
	}
	
	
	
	
	
}
