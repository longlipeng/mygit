package com.allinfinance.model;

/**
 * 单笔交易信息类
 * 
 * @author taojiajun
 *
 */
public class SingleTransactionInfo {
	private String serialNo;
	private String cardID;
	private String cardNo;
	private String cardMon;
	private String cardBalance;
	private String tranTime;
	private String tranMon;
	private String tranBalance;
	private String tranType;
	private String txnType;

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getCardID() {
		return cardID;
	}

	public void setCardID(String cardID) {
		this.cardID = cardID;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardMon() {
		return cardMon;
	}

	public void setCardMon(String cardMon) {
		this.cardMon = cardMon;
	}

	public String getCardBalance() {
		return cardBalance;
	}

	public void setCardBalance(String cardBalance) {
		this.cardBalance = cardBalance;
	}

	public String getTranTime() {
		return tranTime;
	}

	public void setTranTime(String tranTime) {
		this.tranTime = tranTime;
	}

	public String getTranMon() {
		return tranMon;
	}

	public void setTranMon(String tranMon) {
		this.tranMon = tranMon;
	}

	public String getTranBalance() {
		return tranBalance;
	}

	public void setTranBalance(String tranBalance) {
		this.tranBalance = tranBalance;
	}

	public String getTranType() {
		return tranType;
	}

	public void setTranType(String tranType) {
		this.tranType = tranType;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	
}
