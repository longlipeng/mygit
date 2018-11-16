package com.huateng.po.risk;

import java.io.Serializable;

public class TblRiskChlTranLimit implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String channel;
	 private String cardbin;
	 private String txnnum;
	 private String limit;
	 private String cardbinold;
	 private String txnnumold;
	 private String limitold;
	 private String creid;
	 private String cretime;
	 private String upid;
	 private String uptime;
	 public String getStatue() {
		return statue;
	}
	public void setStatue(String statue) {
		this.statue = statue;
	}
	private String statue;
	 public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	private String id;
 public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getCardbin() {
		return cardbin;
	}
	public void setCardbin(String cardbin) {
		this.cardbin = cardbin;
	}
	public String getTxnnum() {
		return txnnum;
	}
	public void setTxnnum(String txnnum) {
		this.txnnum = txnnum;
	}
	public String getLimit() {
		return limit;
	}
	public void setLimit(String limit) {
		this.limit = limit;
	}
	public String getCardbinold() {
		return cardbinold;
	}
	public void setCardbinold(String cardbinold) {
		this.cardbinold = cardbinold;
	}
	public String getTxnnumold() {
		return txnnumold;
	}
	public void setTxnnumold(String txnnumold) {
		this.txnnumold = txnnumold;
	}
	public String getLimitold() {
		return limitold;
	}
	public void setLimitold(String limitold) {
		this.limitold = limitold;
	}
	public String getCreid() {
		return creid;
	}
	public void setCreid(String creid) {
		this.creid = creid;
	}
	public String getCretime() {
		return cretime;
	}
	public void setCretime(String cretime) {
		this.cretime = cretime;
	}
	public String getUpid() {
		return upid;
	}
	public void setUpid(String upid) {
		this.upid = upid;
	}
	public String getUptime() {
		return uptime;
	}
	public void setUptime(String uptime) {
		this.uptime = uptime;
	}

}
