package com.allinfinance.model;
/**
 * 充值交易信息类
 * @author taojiajun
 *
 */
public class RechargeTxnInfo {
	private String serialNo;
	private String cardID;
	private String cardNo;
	private String cardMon;
	private String cardBalance;
	private String chargeTime;
	private String chargeMon;
	private String chargeBalance;
	private String isOpenAcc;
	private String isRegister;
	private String beginDate;
	private String expiryDate;
	private String txnType;//附加字段 交易类型
	private String onymoumState;//是否是记名卡2不记名 3 记名
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

	public String getChargeTime() {
		return chargeTime;
	}

	public void setChargeTime(String chargeTime) {
		this.chargeTime = chargeTime;
	}

	public String getChargeMon() {
		return chargeMon;
	}

	public void setChargeMon(String chargeMon) {
		this.chargeMon = chargeMon;
	}

	public String getChargeBalance() {
		return chargeBalance;
	}

	public void setChargeBalance(String chargeBalance) {
		this.chargeBalance = chargeBalance;
	}

	public String getIsOpenAcc() {
		return isOpenAcc;
	}

	public void setIsOpenAcc(String isOpenAcc) {
		this.isOpenAcc = isOpenAcc;
	}

	public String getIsRegister() {
		return isRegister;
	}

	public void setIsRegister(String isRegister) {
		this.isRegister = isRegister;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getOnymoumState() {
		return onymoumState;
	}

	public void setOnymoumState(String onymoumState) {
		this.onymoumState = onymoumState;
	}

}
