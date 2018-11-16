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
