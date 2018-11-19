package com.huateng.framework.msg;

public class ExchangeBean {
	// 报文长度
	private String msgLen;

	// 消息类型
	private String txnType;

	// 发送日期
	private String sndDate;

	// 发送时间
	private String sndTime;

	// 卡号
	private String cardNo;

	// 服务号
	private String accountNo;

	// 邮件
	private String email;

	// 金额
	private String amount;

	// 积分
	private String integral;

	// 应答码
	private String rspCode;

	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getIntegral() {
		return integral;
	}

	public void setIntegral(String integral) {
		this.integral = integral;
	}

	public String getRspCode() {
		return rspCode;
	}

	public void setRspCode(String rspCode) {
		this.rspCode = rspCode;
	}

}
