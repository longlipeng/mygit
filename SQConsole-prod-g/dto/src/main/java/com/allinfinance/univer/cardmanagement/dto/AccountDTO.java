package com.allinfinance.univer.cardmanagement.dto;

import java.io.Serializable;

public class AccountDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String CardNo;
	private String accountNo;
	private String accountType;
	private Integer posSingleAmount;
	private Integer posDailyAmount;
	private Integer webSingleAmount;
	private Integer webDailyAmount;
	private Integer withoutPinAmount;
	private String accountType1;
	private Integer accBal;
	private Integer congeal;
	private String accBal1;
	private String congeal1;
	public String getAccBal1() {
		String b = Long.valueOf(accBal).toString();
		if(b.length()<3&&b.length()>1){
            b= "0."+b;
        }else if(b.length()<2){
            b="0.0"+b;
        }else{
            b=b.substring(0, b.length()-2)+"."+b.substring(b.length()-2);
        }
		return b;
	}
	public void setAccBal1(String accBal1) {
		this.accBal1 = accBal1;
	}
	public String getCongeal1() {
		String b = Long.valueOf(congeal).toString();
		if(b.length()<3&&b.length()>1){
            b= "0."+b;
        }else if(b.length()<2){
            b="0.0"+b;
        }else{
            b=b.substring(0, b.length()-2)+"."+b.substring(b.length()-2);
        }
		return b;
	}
	public void setCongeal1(String congeal1) {
		this.congeal1 = congeal1;
	}
	public Integer getCongeal() {
		return congeal;
	}
	public void setCongeal(Integer congeal) {
		this.congeal = congeal;
	}
	public Integer getAccBal() {
		return accBal;
	}
	public void setAccBal(Integer accBal) {
		this.accBal = accBal;
	}
	public String getAccountType1() {
		return accountType1;
	}
	public void setAccountType1(String accountType1) {
		this.accountType1 = accountType1;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public Integer getPosSingleAmount() {
		return posSingleAmount;
	}
	public void setPosSingleAmount(Integer posSingleAmount) {
		this.posSingleAmount = posSingleAmount;
	}
	public Integer getPosDailyAmount() {
		return posDailyAmount;
	}
	public void setPosDailyAmount(Integer posDailyAmount) {
		this.posDailyAmount = posDailyAmount;
	}
	public Integer getWebSingleAmount() {
		return webSingleAmount;
	}
	public void setWebSingleAmount(Integer webSingleAmount) {
		this.webSingleAmount = webSingleAmount;
	}
	public Integer getWebDailyAmount() {
		return webDailyAmount;
	}
	public void setWebDailyAmount(Integer webDailyAmount) {
		this.webDailyAmount = webDailyAmount;
	}
	public Integer getWithoutPinAmount() {
		return withoutPinAmount;
	}
	public void setWithoutPinAmount(Integer withoutPinAmount) {
		this.withoutPinAmount = withoutPinAmount;
	}
	public String getCardNo() {
		return CardNo;
	}
	public void setCardNo(String cardNo) {
		CardNo = cardNo;
	}
	
}
