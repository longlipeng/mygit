package com.allinfinance.prepay.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("cardInfoTemplate")
public class CardInfoTemplate {
	@XStreamAlias("cardNo")
	private String cardNo;//����
	@XStreamAlias("cardStatus")
	private String cardStatus;//��״̬
	@XStreamAlias("cardType")
	private String cardType;//����Ʒ����
	@XStreamAlias("cardMedium")
	private String cardMedium;//����������
	@XStreamAlias("cardBalance")
	private String cardBalance;//�����
	
	
	
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
