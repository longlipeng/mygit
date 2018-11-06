package com.allinfinance.shangqi.gateway.dto;

public class ConsumDTO {
	
	private String cardNo;
	private String amount;
	private String orderNo;
	private String channel;
	private String serviceFee;
	private String txnNo;
	private String innerMerchantNo;
	private String balance;
	private String respCode;
	
	
	
	
	
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getInnerMerchantNo() {
		return innerMerchantNo;
	}
	public void setInnerMerchantNo(String innerMerchantNo) {
		this.innerMerchantNo = innerMerchantNo;
	}
	public String getTxnNo() {
		return txnNo;
	}
	public void setTxnNo(String txnNo) {
		this.txnNo = txnNo;
	}
	public String getServiceFee() {
		return serviceFee;
	}
	public void setServiceFee(String serviceFee) {
		this.serviceFee = serviceFee;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	

}
