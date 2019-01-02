package com.huateng.po.reserve;

import java.io.Serializable;

public class TblPaymentReserve implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String paymentId;//id
	private String paymentAccount;//出款账户
	private String paymentAccountName;//出款账户名称
	private String paymentMoney;//出款金额
	private String paymentStatus;//出款状态
	private String paymentDate;//出款时间
	
	private String paymentAuditStatus;//审核状态
	private String paymentPayStatus;//支付状态
	private String paymentLaunchTime;//发起日期
	private String paymentLaunchName;//发起人员
	private String paymentAuditTime;//审核日期
	private String paymentAuditName;//审核人员
	
	private String paymentBatch;//交易流水号
	
	private String paymentInsSeq;//头寸序号
	
	
	
	
	public String getPaymentInsSeq() {
		return paymentInsSeq;
	}
	public void setPaymentInsSeq(String paymentInsSeq) {
		this.paymentInsSeq = paymentInsSeq;
	}
	public String getPaymentBatch() {
		return paymentBatch;
	}
	public void setPaymentBatch(String paymentBatch) {
		this.paymentBatch = paymentBatch;
	}
	public String getPaymentAuditStatus() {
		return paymentAuditStatus;
	}
	public void setPaymentAuditStatus(String paymentAuditStatus) {
		this.paymentAuditStatus = paymentAuditStatus;
	}
	public String getPaymentPayStatus() {
		return paymentPayStatus;
	}
	public void setPaymentPayStatus(String paymentPayStatus) {
		this.paymentPayStatus = paymentPayStatus;
	}
	public String getPaymentLaunchTime() {
		return paymentLaunchTime;
	}
	public void setPaymentLaunchTime(String paymentLaunchTime) {
		this.paymentLaunchTime = paymentLaunchTime;
	}
	public String getPaymentLaunchName() {
		return paymentLaunchName;
	}
	public void setPaymentLaunchName(String paymentLaunchName) {
		this.paymentLaunchName = paymentLaunchName;
	}
	public String getPaymentAuditTime() {
		return paymentAuditTime;
	}
	public void setPaymentAuditTime(String paymentAuditTime) {
		this.paymentAuditTime = paymentAuditTime;
	}
	public String getPaymentAuditName() {
		return paymentAuditName;
	}
	public void setPaymentAuditName(String paymentAuditName) {
		this.paymentAuditName = paymentAuditName;
	}
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
