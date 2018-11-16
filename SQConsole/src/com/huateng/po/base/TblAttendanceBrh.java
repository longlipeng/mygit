package com.huateng.po.base;

public class TblAttendanceBrh {
private String brhid;//机构号
private String mchtid;//商户号
private String brhtype;//银行类型
private String upddate;//日期
private String descinfo;//签到描述
private String status;//签到状态
private String termid;//终端号
public String getBrhid() {
	return brhid;
}
public void setBrhid(String brhid) {
	this.brhid = brhid;
}
public String getMchtid() {
	return mchtid;
}
public void setMchtid(String mchtid) {
	this.mchtid = mchtid;
}
public String getBrhtype() {
	return brhtype;
}
public void setBrhtype(String brhtype) {
	this.brhtype = brhtype;
}
public String getUpddate() {
	return upddate;
}
public void setUpddate(String upddate) {
	this.upddate = upddate;
}
public String getDescinfo() {
	return descinfo;
}
public void setDescinfo(String descinfo) {
	this.descinfo = descinfo;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getTermid() {
	return termid;
}
public void setTermid(String termid) {
	this.termid = termid;
}


}
