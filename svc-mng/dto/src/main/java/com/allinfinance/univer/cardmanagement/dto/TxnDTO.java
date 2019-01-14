package com.allinfinance.univer.cardmanagement.dto;

import java.io.Serializable;

public class TxnDTO  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String instDate;
	private String sysSeqNum;
	
	private String txnNum;
	private String transType;
	private String pan;
	private String retrivlRef;
	private String acctId1;
	private String amtTrans;
	public String getSysSeqNum() {
		return sysSeqNum;
	}
	public void setSysSeqNum(String sysSeqNum) {
		this.sysSeqNum = sysSeqNum;
	}

	public String getInstDate() {
		return instDate;
	}
	public void setInstDate(String instDate) {
		this.instDate = instDate;
	}
	public String getTxnNum() {
		return txnNum;
	}
	public void setTxnNum(String txnNum) {
		this.txnNum = txnNum;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public String getRetrivlRef() {
		return retrivlRef;
	}
	public void setRetrivlRef(String retrivlRef) {
		this.retrivlRef = retrivlRef;
	}
	public String getAcctId1() {
		return acctId1;
	}
	public void setAcctId1(String acctId1) {
		this.acctId1 = acctId1;
	}
	public String getAmtTrans() {
		return amtTrans;
	}
	public void setAmtTrans(String amtTrans) {
		this.amtTrans = amtTrans;
	}
}
