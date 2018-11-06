package com.allinfinance.univer.cardmanagement.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class CardBalanceDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String accountType;
	private String balance;
	private String congeal;
	private String posSingleAmount;
	private String posDailyAmount;
	private String webSingleAmount;
	private String webDailyAmount;
	private String withoutPinAmount;
	private String serviceFee;
	private String total;
	private String accountId;
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getTotal() {
		return new BigDecimal(total).divide(new BigDecimal(100)).toString();
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getCongeal() {
//		String b=new Long(congeal).toString();
//		if(b.length()<3&&b.length()>1){
//            b= "0."+b;
//        }else if(b.length()<2){
//            b="0.0"+b;
//        }else{
//            b=b.substring(0, b.length()-2)+"."+b.substring(b.length()-2);
//        }
//			return b;
		return new BigDecimal(congeal).divide(new BigDecimal(100)).toString();
	}
	public void setCongeal(String congeal) {
		this.congeal = congeal;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getBalance() {
//		String b=new Long(balance).toString();
//		if(b.length()<3&&b.length()>1){
//            b= "0."+b;
//        }else if(b.length()<2){
//            b="0.0"+b;
//        }else{
//            b=b.substring(0, b.length()-2)+"."+b.substring(b.length()-2);
//        }
//		return b;
		return new BigDecimal(balance).divide(new BigDecimal(100)).toString();
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getPosSingleAmount() {
		return posSingleAmount;
	}
	public void setPosSingleAmount(String posSingleAmount) {
		this.posSingleAmount = posSingleAmount;
	}
	public String getPosDailyAmount() {
		return posDailyAmount;
	}
	public void setPosDailyAmount(String posDailyAmount) {
		this.posDailyAmount = posDailyAmount;
	}
	public String getWebSingleAmount() {
		return webSingleAmount;
	}
	public void setWebSingleAmount(String webSingleAmount) {
		this.webSingleAmount = webSingleAmount;
	}
	public String getWebDailyAmount() {
		return webDailyAmount;
	}
	public void setWebDailyAmount(String webDailyAmount) {
		this.webDailyAmount = webDailyAmount;
	}
	public String getWithoutPinAmount() {
		return withoutPinAmount;
	}
	public void setWithoutPinAmount(String withoutPinAmount) {
		this.withoutPinAmount = withoutPinAmount;
	}
	public String getServiceFee() {
		return serviceFee;
	}
	public void setServiceFee(String serviceFee) {
		this.serviceFee = serviceFee;
	}
    
}	
