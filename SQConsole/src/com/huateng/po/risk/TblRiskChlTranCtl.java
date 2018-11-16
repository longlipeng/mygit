package com.huateng.po.risk;

import java.io.Serializable;

public class TblRiskChlTranCtl implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String channel;
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
public String getSastatue() {
	return sastatue;
}
public void setSastatue(String sastatue) {
	this.sastatue = sastatue;
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

public String getUptime() {
	return uptime;
}
public void setUptime(String uptime) {
	this.uptime = uptime;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
private String cardbin;
private String txnnum;
private String sastatue;
private String creid;
private String cretime;
private String upid;
private String cardbinold;
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
private String txnnumold;
public String getUpid() {
	return upid;
}
public void setUpid(String upid) {
	this.upid = upid;
}
private String uptime;
private String id;


}
