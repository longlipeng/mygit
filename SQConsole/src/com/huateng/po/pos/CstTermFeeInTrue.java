package com.huateng.po.pos;

import java.io.Serializable;

public class CstTermFeeInTrue implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	   private CstTermFeePK id;
	   private String txnnum;
//	   private String cardtype;
	   private String channel;
	   private String daynum;
	   private double dayamt;
	   private double daysingle;
	   private String monnum;
	   private double monamt;
	   private String reccrtts;
	   private String recupdts;
	   private String upid;
	   private String creid;
	   private String sastatue;
	   private String saaction;
	   public String getSaaction() {
		return saaction;
	}
	public void setSaaction(String saaction) {
		this.saaction = saaction;
	}
	public double getDayamt() {
		return dayamt;
	}
	public void setDayamt(double dayamt) {
		this.dayamt = dayamt;
	}
	public double getDaysingle() {
		return daysingle;
	}
	public void setDaysingle(double daysingle) {
		this.daysingle = daysingle;
	}
	public double getMonamt() {
		return monamt;
	}
	public void setMonamt(double monamt) {
		this.monamt = monamt;
	}
	
   public CstTermFeePK getId() {
		return id;
	}
	public void setId(CstTermFeePK id) {
		this.id = id;
	}
	public String getTxnnum() {
		return txnnum;
	}
	public void setTxnnum(String txnnum) {
		this.txnnum = txnnum;
	}
//	public String getCardtype() {
//		return cardtype;
//	}
//	public void setCardtype(String cardtype) {
//		this.cardtype = cardtype;
//	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getDaynum() {
		return daynum;
	}
	public void setDaynum(String daynum) {
		this.daynum = daynum;
	}

	public String getMonnum() {
		return monnum;
	}
	public void setMonnum(String monnum) {
		this.monnum = monnum;
	}
	public String getReccrtts() {
		return reccrtts;
	}
	public void setReccrtts(String reccrtts) {
		this.reccrtts = reccrtts;
	}
	public String getRecupdts() {
		return recupdts;
	}
	public void setRecupdts(String recupdts) {
		this.recupdts = recupdts;
	}
	public String getUpid() {
		return upid;
	}
	public void setUpid(String upid) {
		this.upid = upid;
	}
	public String getCreid() {
		return creid;
	}
	public void setCreid(String creid) {
		this.creid = creid;
	}
	public String getSastatue() {
		return sastatue;
	}
	public void setSastatue(String sastatue) {
		this.sastatue = sastatue;
	}

}
