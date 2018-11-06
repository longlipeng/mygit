package com.allinfinance.univer.cardmanagement.dto;

import java.io.Serializable;

public class RiskDTO  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cardNo;
	private String txnDate;
	private String txnTime;
	private String sysSeq;
	private String monReason;
	private String rsvd1;
	private String rsvd2;
	private String count;
	private String mon;
	private String amt;
	private String maId;
	private String posId;
	public String getMaId() {
		return maId;
	}
	public void setMaId(String maId) {
		this.maId = maId;
	}
	public String getPosId() {
		return posId;
	}
	public void setPosId(String posId) {
		this.posId = posId;
	}
	public String getAmt() {
		String b=new Long(amt).toString();
		if(b.length()<3){
			return "0."+b;
		}
			b=b.substring(0, b.length()-2)+"."+b.substring(b.length()-2);
			return b;
	}
	public void setAmt(String amt) {
		this.amt = amt;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getMon() {
		return mon;
	}
	public void setMon(String mon) {
		this.mon = mon;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getTxnDate() {
		return txnDate;
	}
	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}
	public String getTxnTime() {
		return txnTime;
	}
	public void setTxnTime(String txnTime) {
		this.txnTime = txnTime;
	}
	public String getSysSeq() {
		return sysSeq;
	}
	public void setSysSeq(String sysSeq) {
		this.sysSeq = sysSeq;
	}
	public String getMonReason() {
		return monReason;
	}
	public void setMonReason(String monReason) {
		this.monReason = monReason;
	}
	public String getRsvd1() {
		return rsvd1;
	}
	public void setRsvd1(String rsvd1) {
		this.rsvd1 = rsvd1;
	}
	public String getRsvd2() {
		return rsvd2;
	}
	public void setRsvd2(String rsvd2) {
		this.rsvd2 = rsvd2;
	}
}
