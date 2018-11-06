package com.allinfinance.univer.manager.transactionQuery.dto;

import com.allinfinance.framework.dto.PageQueryDTO;

public class TransactionQueryDTO extends PageQueryDTO {
	private String cardAccptrId;//商户号
	private String cardAccptrTermnlId;//终端号
	private String txnType;//交易类型
	private String cardNo;
	private String amt;//交易金额
	private String fee;//服务费
	private String startDate;
	private String endDate;
	private String transactionState;//交易状态
	private String accBal;//卡余额
	private String transTime;//交易时间
	private String seqNo;//流水
	
	
	
	
	
	
	
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public String getTransTime() {
		return transTime;
	}
	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}
	public String getAccBal() {
		return accBal;
	}
	public void setAccBal(String accBal) {
		this.accBal = accBal;
	}
	public String getCardAccptrId() {
		return cardAccptrId;
	}
	public void setCardAccptrId(String cardAccptrId) {
		this.cardAccptrId = cardAccptrId;
	}
	public String getCardAccptrTermnlId() {
		return cardAccptrTermnlId;
	}
	public void setCardAccptrTermnlId(String cardAccptrTermnlId) {
		this.cardAccptrTermnlId = cardAccptrTermnlId;
	}
	public String getTxnType() {
		return txnType;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getAmt() {
		return amt;
	}
	public void setAmt(String amt) {
		this.amt = amt;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getTransactionState() {
		return transactionState;
	}
	public void setTransactionState(String transactionState) {
		this.transactionState = transactionState;
	}
	
	
	
	
	

}
