package com.allinfinance.prepay.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("cardInfoTemplate")
public class CardInfoTemplate {
	@XStreamAlias("cardNo")
	private String cardNo;//卡号
	@XStreamAlias("cardStatus")
	private String cardStatus;//卡状态
	@XStreamAlias("cardType")
	private String cardType;//卡产品类型
	@XStreamAlias("cardMedium")
	private String cardMedium;//卡介质类型
	@XStreamAlias("cardBalance")
	private String cardBalance;//卡余额
	
	
	
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getCardStatus() {
		return cardStatus;
	}
	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getCardMedium() {
		return cardMedium;
	}
	public void setCardMedium(String cardMedium) {
		this.cardMedium = cardMedium;
	}
	public String getCardBalance() {
		return cardBalance;
	}
	public void setCardBalance(String cardBalance) {
		this.cardBalance = cardBalance;
	}
	
	
	
}
