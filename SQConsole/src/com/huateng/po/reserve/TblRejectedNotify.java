package com.huateng.po.reserve;

import java.io.Serializable;

/**
 * 退汇通知表
 * @author 
 *
 */
public class TblRejectedNotify implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String trnNo;//交易流水号
	private String trnDate;//交易日期
	private String trnPayerBankNo;//付款方开户行行号
	private String trnPayerAcctNo;//付款方账号
	private String trnPayerAcctName;//付款方账户名称
	private String trnPayeeAcctNo;//收款方账号
	private String trnPayeeAcctName;//收款方账户名称
	private String trnTxnAmt;//金额
	private String trnPayeeAcctBal;//收款方虚拟记账余额
	private String trnAcctDate;//银联记账日期
	private String trnOrigTxnNo;//原交易流水号
	private String trnOrigTxnDate;//原交易日期
	private String trnMerId;//客户代码
	private String trnMerName;//客户名称
	
	
	
	
	
	
	
	public String getTrnNo() {
		return trnNo;
	}
	public void setTrnNo(String trnNo) {
		this.trnNo = trnNo;
	}
	public String getTrnDate() {
		return trnDate;
	}
	public void setTrnDate(String trnDate) {
		this.trnDate = trnDate;
	}
	public String getTrnPayerBankNo() {
		return trnPayerBankNo;
	}
	public void setTrnPayerBankNo(String trnPayerBankNo) {
		this.trnPayerBankNo = trnPayerBankNo;
	}
	public String getTrnPayerAcctNo() {
		return trnPayerAcctNo;
	}
	public void setTrnPayerAcctNo(String trnPayerAcctNo) {
		this.trnPayerAcctNo = trnPayerAcctNo;
	}
	public String getTrnPayerAcctName() {
		return trnPayerAcctName;
	}
	public void setTrnPayerAcctName(String trnPayerAcctName) {
		this.trnPayerAcctName = trnPayerAcctName;
	}
	public String getTrnPayeeAcctNo() {
		return trnPayeeAcctNo;
	}
	public void setTrnPayeeAcctNo(String trnPayeeAcctNo) {
		this.trnPayeeAcctNo = trnPayeeAcctNo;
	}
	public String getTrnPayeeAcctName() {
		return trnPayeeAcctName;
	}
	public void setTrnPayeeAcctName(String trnPayeeAcctName) {
		this.trnPayeeAcctName = trnPayeeAcctName;
	}
	public String getTrnTxnAmt() {
		return trnTxnAmt;
	}
	public void setTrnTxnAmt(String trnTxnAmt) {
		this.trnTxnAmt = trnTxnAmt;
	}
	public String getTrnPayeeAcctBal() {
		return trnPayeeAcctBal;
	}
	public void setTrnPayeeAcctBal(String trnPayeeAcctBal) {
		this.trnPayeeAcctBal = trnPayeeAcctBal;
	}
	public String getTrnAcctDate() {
		return trnAcctDate;
	}
	public void setTrnAcctDate(String trnAcctDate) {
		this.trnAcctDate = trnAcctDate;
	}
	public String getTrnOrigTxnNo() {
		return trnOrigTxnNo;
	}
	public void setTrnOrigTxnNo(String trnOrigTxnNo) {
		this.trnOrigTxnNo = trnOrigTxnNo;
	}
	public String getTrnOrigTxnDate() {
		return trnOrigTxnDate;
	}
	public void setTrnOrigTxnDate(String trnOrigTxnDate) {
		this.trnOrigTxnDate = trnOrigTxnDate;
	}
	public String getTrnMerId() {
		return trnMerId;
	}
	public void setTrnMerId(String trnMerId) {
		this.trnMerId = trnMerId;
	}
	public String getTrnMerName() {
		return trnMerName;
	}
	public void setTrnMerName(String trnMerName) {
		this.trnMerName = trnMerName;
	}
	
	
	
	
	
	
}
