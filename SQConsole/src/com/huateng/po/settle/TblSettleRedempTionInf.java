package com.huateng.po.settle;

import java.io.Serializable;

public class TblSettleRedempTionInf implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String redempTionId;  //客户编号
	private String redempTionAccountName;   //客户账户名称
	private String redempTionAccount;   //客户账户
	private String redempTionMoney;  //赎回金额/分
	private String redempTionBankCard;  //客户开户行行号
	private String redempTionStatus;  //审核状态(0 正常, 1 新增待审核, 2.删除待审核)
	private String redempTionAccountStatus;  //是否打款(1.是，2.否)    
	private String redempTionPayStatus;  //支付状态   
	private String redempTionAddTime;  //发起日期
	private String redempTionAddName;  //发起人员
	private String redempTionAuditDate;  //审核时间
	private String redempTionAuditName;  //审核人员
	private String redempTionEnTry;  //录入方式
	
	
	
	public String getRedempTionBankCard() {
		return redempTionBankCard;
	}
	public void setRedempTionBankCard(String redempTionBankCard) {
		this.redempTionBankCard = redempTionBankCard;
	}
	public String getRedempTionEnTry() {
		return redempTionEnTry;
	}
	public void setRedempTionEnTry(String redempTionEnTry) {
		this.redempTionEnTry = redempTionEnTry;
	}
	public String getRedempTionId() {
		return redempTionId;
	}
	public void setRedempTionId(String redempTionId) {
		this.redempTionId = redempTionId;
	}
	public String getRedempTionAccountName() {
		return redempTionAccountName;
	}
	public void setRedempTionAccountName(String redempTionAccountName) {
		this.redempTionAccountName = redempTionAccountName;
	}
	public String getRedempTionAccount() {
		return redempTionAccount;
	}
	public void setRedempTionAccount(String redempTionAccount) {
		this.redempTionAccount = redempTionAccount;
	}
	public String getRedempTionMoney() {
		return redempTionMoney;
	}
	public void setRedempTionMoney(String redempTionMoney) {
		this.redempTionMoney = redempTionMoney;
	}
	public String getRedempTionStatus() {
		return redempTionStatus;
	}
	public void setRedempTionStatus(String redempTionStatus) {
		this.redempTionStatus = redempTionStatus;
	}
	public String getRedempTionAccountStatus() {
		return redempTionAccountStatus;
	}
	public void setRedempTionAccountStatus(String redempTionAccountStatus) {
		this.redempTionAccountStatus = redempTionAccountStatus;
	}
	public String getRedempTionPayStatus() {
		return redempTionPayStatus;
	}
	public void setRedempTionPayStatus(String redempTionPayStatus) {
		this.redempTionPayStatus = redempTionPayStatus;
	}
	public String getRedempTionAddTime() {
		return redempTionAddTime;
	}
	public void setRedempTionAddTime(String redempTionAddTime) {
		this.redempTionAddTime = redempTionAddTime;
	}
	public String getRedempTionAddName() {
		return redempTionAddName;
	}
	public void setRedempTionAddName(String redempTionAddName) {
		this.redempTionAddName = redempTionAddName;
	}
	public String getRedempTionAuditDate() {
		return redempTionAuditDate;
	}
	public void setRedempTionAuditDate(String redempTionAuditDate) {
		this.redempTionAuditDate = redempTionAuditDate;
	}
	public String getRedempTionAuditName() {
		return redempTionAuditName;
	}
	public void setRedempTionAuditName(String redempTionAuditName) {
		this.redempTionAuditName = redempTionAuditName;
	}
	
	
}
