package com.allinfinance.framework.dto;

import java.util.List;
import java.util.Map;

public class BatchRechargeDTO extends PageQueryDTO {
	private static final long serialVersionUID = 1L;
	private String cardNo;
	private String rechargeSum;
	private String rechargeFee;
	private String rechargeState;
	private String createTime;
	private String modifyTime;
	private String createUser;
	private String countNum;
	private List<String> list;
	private String bacthNo;
	private String smltDate;
	private String smltTime;
	private String rechargeType;
	private String rechargeTxnSeqNo;
	
	public String getRechargeFee() {
		return rechargeFee;
	}
	public void setRechargeFee(String rechargeFee) {
		this.rechargeFee = rechargeFee;
	}
	public String getBacthNo() {
		return bacthNo;
	}
	public void setBacthNo(String bacthNo) {
		this.bacthNo = bacthNo;
	}
	public List<String> getList() {
		return list;
	}
	public void setList(List<String> list) {
		this.list = list;
	}
	public String getCountNum() {
		return countNum;
	}
	public void setCountNum(String countNum) {
		this.countNum = countNum;
	}
	public String getRechargeState() {
		return rechargeState;
	}
	public void setRechargeState(String rechargeState) {
		this.rechargeState = rechargeState;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getRechargeSum() {
		return rechargeSum;
	}
	public void setRechargeSum(String rechargeSum) {
		this.rechargeSum = rechargeSum;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getSmltDate() {
		return smltDate;
	}
	public void setSmltDate(String smltDate) {
		this.smltDate = smltDate;
	}
	public String getSmltTime() {
		return smltTime;
	}
	public void setSmltTime(String smltTime) {
		this.smltTime = smltTime;
	}
	public String getRechargeType() {
		return rechargeType;
	}
	public void setRechargeType(String rechargeType) {
		this.rechargeType = rechargeType;
	}
	public String getRechargeTxnSeqNo() {
		return rechargeTxnSeqNo;
	}
	public void setRechargeTxnSeqNo(String rechargeTxnSeqNo) {
		this.rechargeTxnSeqNo = rechargeTxnSeqNo;
	}
	
}
	
