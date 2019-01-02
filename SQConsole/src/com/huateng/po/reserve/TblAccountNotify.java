package com.huateng.po.reserve;

import java.io.Serializable;

/**
 * 来账通知表
 * @author 
 *
 */
public class TblAccountNotify implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tanNo;//交易流水号
	private String tanDate;//交易日期
	private String tanPayerBankNo;//付款方开户行行号
	private String tanPayerAcctNo;//付款方账号
	private String tanPayerAcctName;//付款方账户名称
	private String tanPayeeAcctNo;//收款方账号
	private String tanPayeeAcctName;//收款方账户名称
	private String tanTxnAmt;//金额
	private String tanPayeeAcctBal;//收款方虚拟记账余额
	private String tanAcctDate;//银联记账日期
	
	
	
	
	
	
	
	
	public String getTanNo() {
		return tanNo;
	}
	public void setTanNo(String tanNo) {
		this.tanNo = tanNo;
	}
	public String getTanDate() {
		return tanDate;
	}
	public void setTanDate(String tanDate) {
		this.tanDate = tanDate;
	}
	public String getTanPayerBankNo() {
		return tanPayerBankNo;
	}
	public void setTanPayerBankNo(String tanPayerBankNo) {
		this.tanPayerBankNo = tanPayerBankNo;
	}
	public String getTanPayerAcctNo() {
		return tanPayerAcctNo;
	}
	public void setTanPayerAcctNo(String tanPayerAcctNo) {
		this.tanPayerAcctNo = tanPayerAcctNo;
	}
	public String getTanPayerAcctName() {
		return tanPayerAcctName;
	}
	public void setTanPayerAcctName(String tanPayerAcctName) {
		this.tanPayerAcctName = tanPayerAcctName;
	}
	public String getTanPayeeAcctNo() {
		return tanPayeeAcctNo;
	}
	public void setTanPayeeAcctNo(String tanPayeeAcctNo) {
		this.tanPayeeAcctNo = tanPayeeAcctNo;
	}
	public String getTanPayeeAcctName() {
		return tanPayeeAcctName;
	}
	public void setTanPayeeAcctName(String tanPayeeAcctName) {
		this.tanPayeeAcctName = tanPayeeAcctName;
	}
	public String getTanTxnAmt() {
		return tanTxnAmt;
	}
	public void setTanTxnAmt(String tanTxnAmt) {
		this.tanTxnAmt = tanTxnAmt;
	}
	public String getTanPayeeAcctBal() {
		return tanPayeeAcctBal;
	}
	public void setTanPayeeAcctBal(String tanPayeeAcctBal) {
		this.tanPayeeAcctBal = tanPayeeAcctBal;
	}
	public String getTanAcctDate() {
		return tanAcctDate;
	}
	public void setTanAcctDate(String tanAcctDate) {
		this.tanAcctDate = tanAcctDate;
	}
	
	
	
	
	
}
