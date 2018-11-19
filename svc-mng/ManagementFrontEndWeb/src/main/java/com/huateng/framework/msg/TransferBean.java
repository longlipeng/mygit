package com.huateng.framework.msg;

public class TransferBean {
	// 报文长度
	private String msgLen;

	// 消息类型
	private String txnType;

	// 发送日期
	private String sndDate;

	// 发送时间
	private String sndTime;

	// 转出卡号
	private String cardNoOut;

	// 转出服务号
	private String accountNoOut;

	// 卡片密码
	private String password;

	// 转出金额
	private String amountOut;

	// 转入金额
	private String amountIn;

	// 转入手续费
	private String amountFeeIn;

	// 转出手续费
	private String amountFeeOut;

	// 转入卡号
	private String cardNoIn;

	// 转入服务号
	private String accountNoIn;

	// 应答码
	private String rspCode;

	// 卡片CVV2
	private String cvv2;

	public String getCvv2() {
		return cvv2;
	}

	public void setCvv2(String cvv2) {
		this.cvv2 = cvv2;
	}

	public String getMsgLen() {
		return msgLen;
	}

	public void setMsgLen(String msgLen) {
		this.msgLen = msgLen;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getSndDate() {
		return sndDate;
	}

	public void setSndDate(String sndDate) {
		this.sndDate = sndDate;
	}

	public String getSndTime() {
		return sndTime;
	}

	public void setSndTime(String sndTime) {
		this.sndTime = sndTime;
	}

	public String getCardNoOut() {
		return cardNoOut;
	}

	public void setCardNoOut(String cardNoOut) {
		this.cardNoOut = cardNoOut;
	}

	public String getAccountNoOut() {
		return accountNoOut;
	}

	public void setAccountNoOut(String accountNoOut) {
		this.accountNoOut = accountNoOut;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAmountOut() {
		return amountOut;
	}

	public void setAmountOut(String amountOut) {
		this.amountOut = amountOut;
	}

	public String getAmountIn() {
		return amountIn;
	}

	public void setAmountIn(String amountIn) {
		this.amountIn = amountIn;
	}

	public String getAmountFeeIn() {
		return amountFeeIn;
	}

	public void setAmountFeeIn(String amountFeeIn) {
		this.amountFeeIn = amountFeeIn;
	}

	public String getAmountFeeOut() {
		return amountFeeOut;
	}

	public void setAmountFeeOut(String amountFeeOut) {
		this.amountFeeOut = amountFeeOut;
	}

	public String getCardNoIn() {
		return cardNoIn;
	}

	public void setCardNoIn(String cardNoIn) {
		this.cardNoIn = cardNoIn;
	}

	public String getAccountNoIn() {
		return accountNoIn;
	}

	public void setAccountNoIn(String accountNoIn) {
		this.accountNoIn = accountNoIn;
	}

	public String getRspCode() {
		return rspCode;
	}

	public void setRspCode(String rspCode) {
		this.rspCode = rspCode;
	}

}
