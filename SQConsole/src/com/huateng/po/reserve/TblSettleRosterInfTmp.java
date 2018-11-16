package com.huateng.po.reserve;

import java.io.Serializable;

public class TblSettleRosterInfTmp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String rosterId;  //账户白名单id
	private String rosterBankCard;  //收款方开户行行号
	private String rosterAccount;  //收款方账号
	private String rosterAccountName;  //收款方账户名称
	private String rosterStatus;  //审核状态
	private String rosterLaunchTime;  //发起日期
	private String rosterLaunchName;  //发起人员
	private String rosterAuditTime;  //审核日期
	private String rosterAuditName;  //审核人员
	
	
	public String getRosterStatus() {
		return rosterStatus;
	}
	public void setRosterStatus(String rosterStatus) {
		this.rosterStatus = rosterStatus;
	}
	public String getRosterId() {
		return rosterId;
	}
	public void setRosterId(String rosterId) {
		this.rosterId = rosterId;
	}
	public String getRosterBankCard() {
		return rosterBankCard;
	}
	public void setRosterBankCard(String rosterBankCard) {
		this.rosterBankCard = rosterBankCard;
	}
	public String getRosterAccount() {
		return rosterAccount;
	}
	public void setRosterAccount(String rosterAccount) {
		this.rosterAccount = rosterAccount;
	}
	public String getRosterAccountName() {
		return rosterAccountName;
	}
	public void setRosterAccountName(String rosterAccountName) {
		this.rosterAccountName = rosterAccountName;
	}
	public String getRosterLaunchTime() {
		return rosterLaunchTime;
	}
	public void setRosterLaunchTime(String rosterLaunchTime) {
		this.rosterLaunchTime = rosterLaunchTime;
	}
	public String getRosterLaunchName() {
		return rosterLaunchName;
	}
	public void setRosterLaunchName(String rosterLaunchName) {
		this.rosterLaunchName = rosterLaunchName;
	}
	public String getRosterAuditTime() {
		return rosterAuditTime;
	}
	public void setRosterAuditTime(String rosterAuditTime) {
		this.rosterAuditTime = rosterAuditTime;
	}
	public String getRosterAuditName() {
		return rosterAuditName;
	}
	public void setRosterAuditName(String rosterAuditName) {
		this.rosterAuditName = rosterAuditName;
	}
	
	
	
	
}
