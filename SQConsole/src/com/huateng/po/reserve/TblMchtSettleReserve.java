package com.huateng.po.reserve;

import java.io.Serializable;

public class TblMchtSettleReserve implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String reserveId;  //商户结算备款编号
	private String reserveTime; //日期
	private String redemptionMoney;  //赎回金额
	private String reserveSettleMoney;  //结算金额
	private String reserveMoney;  //备款金额(可修改)
	private String reserveStatus;  //审核状态(0.正常, 1.修改待审核, 2.备款待审核)
	private String reserveSettleStatus;  //备款状态(0.成功, 1.失败)
	private String reservePayStatus;  //支付状态
	private String reserveLaunchTime;  //发起日期
	private String reserveLaunchName;  //发起人员
	private String reserveAuditTime;  //审核日期
	private String reserveAuditName;  //审核人员
	private String reserveBatch;  //交易流水号
	
	
	public String getReserveBatch() {
		return reserveBatch;
	}
	public void setReserveBatch(String reserveBatch) {
		this.reserveBatch = reserveBatch;
	}
	public String getReservePayStatus() {
		return reservePayStatus;
	}
	public void setReservePayStatus(String reservePayStatus) {
		this.reservePayStatus = reservePayStatus;
	}
	public String getReserveId() {
		return reserveId;
	}
	public void setReserveId(String reserveId) {
		this.reserveId = reserveId;
	}
	public String getReserveTime() {
		return reserveTime;
	}
	public void setReserveTime(String reserveTime) {
		this.reserveTime = reserveTime;
	}
	public String getRedemptionMoney() {
		return redemptionMoney;
	}
	public void setRedemptionMoney(String redemptionMoney) {
		this.redemptionMoney = redemptionMoney;
	}
	public String getReserveSettleMoney() {
		return reserveSettleMoney;
	}
	public void setReserveSettleMoney(String reserveSettleMoney) {
		this.reserveSettleMoney = reserveSettleMoney;
	}
	public String getReserveMoney() {
		return reserveMoney;
	}
	public void setReserveMoney(String reserveMoney) {
		this.reserveMoney = reserveMoney;
	}
	public String getReserveStatus() {
		return reserveStatus;
	}
	public void setReserveStatus(String reserveStatus) {
		this.reserveStatus = reserveStatus;
	}
	public String getReserveSettleStatus() {
		return reserveSettleStatus;
	}
	public void setReserveSettleStatus(String reserveSettleStatus) {
		this.reserveSettleStatus = reserveSettleStatus;
	}
	public String getReserveLaunchTime() {
		return reserveLaunchTime;
	}
	public void setReserveLaunchTime(String reserveLaunchTime) {
		this.reserveLaunchTime = reserveLaunchTime;
	}
	public String getReserveLaunchName() {
		return reserveLaunchName;
	}
	public void setReserveLaunchName(String reserveLaunchName) {
		this.reserveLaunchName = reserveLaunchName;
	}
	public String getReserveAuditTime() {
		return reserveAuditTime;
	}
	public void setReserveAuditTime(String reserveAuditTime) {
		this.reserveAuditTime = reserveAuditTime;
	}
	public String getReserveAuditName() {
		return reserveAuditName;
	}
	public void setReserveAuditName(String reserveAuditName) {
		this.reserveAuditName = reserveAuditName;
	}

	
}
