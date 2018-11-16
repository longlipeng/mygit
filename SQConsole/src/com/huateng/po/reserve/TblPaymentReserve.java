package com.huateng.po.reserve;

import java.io.Serializable;

public class TblPaymentReserve implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String paymentId;//交易流水号
	private String paymentAccount;//出款账户
	private String paymentAccountName;//出款账户名称
	private String paymentMoney;//出款金额
	private String paymentStatus;//出款状态
	private String paymentDate;//出款时间
	
	
	
	public String getPaymentAccount() {
		return paymentAccount;
	}
	public void setPaymentAccount(String paymentAccount) {
		this.paymentAccount = paymentAccount;
	}
	public String getPaymentAccountName() {
		return paymentAccountName;
	}
	public void setPaymentAccountName(String paymentAccountName) {
		this.paymentAccountName = paymentAccountName;
	}
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	public String getPaymentMoney() {
		return paymentMoney;
	}
	public void setPaymentMoney(String paymentMoney) {
		this.paymentMoney = paymentMoney;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	
	
	
	
}
