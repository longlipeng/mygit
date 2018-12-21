package com.huateng.po.reserve;

import java.io.Serializable;

public class TblFocusReserve implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String focusId;//交易流水号
	private String focusAccount;//备款账户
	private String focusAccountName;//备款账户名称
	private String focusMoney;//备款金额
	private String focusStatus;//备款状态
	private String focusDate;//备款时间
	
	private String focusAuditStatus;//审核状态
	private String focusPayStatus;//支付状态
	private String focusLaunchTime;//发起日期
	private String focusLaunchName;//发起人员
	private String focusAuditTime;//审核日期
	private String focusAuditName;//审核人员
	
	private String focusBatch;//交易流水号
	
	 
	
	public String getFocusAuditStatus() {
		return focusAuditStatus;
	}
	public void setFocusAuditStatus(String focusAuditStatus) {
		this.focusAuditStatus = focusAuditStatus;
	}
	public String getFocusPayStatus() {
		return focusPayStatus;
	}
	public void setFocusPayStatus(String focusPayStatus) {
		this.focusPayStatus = focusPayStatus;
	}
	public String getFocusLaunchTime() {
		return focusLaunchTime;
	}
	public void setFocusLaunchTime(String focusLaunchTime) {
		this.focusLaunchTime = focusLaunchTime;
	}
	public String getFocusLaunchName() {
		return focusLaunchName;
	}
	public void setFocusLaunchName(String focusLaunchName) {
		this.focusLaunchName = focusLaunchName;
	}
	public String getFocusAuditTime() {
		return focusAuditTime;
	}
	public void setFocusAuditTime(String focusAuditTime) {
		this.focusAuditTime = focusAuditTime;
	}
	public String getFocusAuditName() {
		return focusAuditName;
	}
	public void setFocusAuditName(String focusAuditName) {
		this.focusAuditName = focusAuditName;
	}
	public String getFocusBatch() {
		return focusBatch;
	}
	public void setFocusBatch(String focusBatch) {
		this.focusBatch = focusBatch;
	}
	public String getFocusAccount() {
		return focusAccount;
	}
	public void setFocusAccount(String focusAccount) {
		this.focusAccount = focusAccount;
	}
	public String getFocusAccountName() {
		return focusAccountName;
	}
	public void setFocusAccountName(String focusAccountName) {
		this.focusAccountName = focusAccountName;
	}
	public String getFocusId() {
		return focusId;
	}
	public void setFocusId(String focusId) {
		this.focusId = focusId;
	}
	public String getFocusMoney() {
		return focusMoney;
	}
	public void setFocusMoney(String focusMoney) {
		this.focusMoney = focusMoney;
	}
	public String getFocusStatus() {
		return focusStatus;
	}
	public void setFocusStatus(String focusStatus) {
		this.focusStatus = focusStatus;
	}
	public String getFocusDate() {
		return focusDate;
	}
	public void setFocusDate(String focusDate) {
		this.focusDate = focusDate;
	}
	
	
	
}
