package com.huateng.framework.util;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)//表示使用这个类中的 private 非静态字段作为 XML 的序列
@XmlRootElement(name="DATA")
@XmlType(propOrder={"orgId","smsNo","smsType","mobile","cv","cardNo","amounts","balance","sms","respFlag","respDetail","requestTime"})//xml格式数据的显示的顺序名字要和定义变量的一样，而不是@XmlElement中的name
public class DownNoteXML {
	//定义xml中显示的数据
    @XmlElement(name="ORG_ID",required=true)//机构号
    private  String orgId;
    @XmlElement(name="SMS_NO",required=true)//流水号
    private String smsNo;
    @XmlElement(name="SMS_TYPE",required=true)//短信类型
    private String smsType;
    @XmlElement(name="MOBILE",required=true)//号码
    private String mobile;
    @XmlElement(name="CV",required=true)//校验码
    private String cv;
    @XmlElement(name="CARDNO",required=true)//卡号
    private String cardNo;
    @XmlElement(name="AMOUNTS",required=true)//交易金额
    private String amounts;
    @XmlElement(name="BALANCE",required=true)//余额
    private String balance;
    @XmlElement(name="SMS",required=true)//短信模版号
    private String sms;
    @XmlElement(name="RESP_FLAG",required=true)//返回标志
    private String respFlag;
    @XmlElement(name="RESP_DETAIL",required=true)//返回描述
    private String respDetail;
    @XmlElement(name="REQUEST_TIME",required=true)//返回描述
    private String requestTime;
    
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getSmsNo() {
		return smsNo;
	}
	public void setSmsNo(String smsNo) {
		this.smsNo = smsNo;
	}
	public String getSmsType() {
		return smsType;
	}
	public void setSmsType(String smsType) {
		this.smsType = smsType;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCv() {
		return cv;
	}
	public void setCv(String cv) {
		this.cv = cv;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getAmounts() {
		return amounts;
	}
	public void setAmounts(String amounts) {
		this.amounts = amounts;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getSms() {
		return sms;
	}
	public void setSms(String sms) {
		this.sms = sms;
	}
	public String getRespFlag() {
		return respFlag;
	}
	public void setRespFlag(String respFlag) {
		this.respFlag = respFlag;
	}
	public String getRespDetail() {
		return respDetail;
	}
	public void setRespDetail(String respDetail) {
		this.respDetail = respDetail;
	}
	public String getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}
	@Override
	public String toString() {
		return "DownNoteXML [orgId=" + orgId + ", smsNo=" + smsNo
				+ ", smsType=" + smsType + ", mobile=" + mobile + ", cv=" + cv
				+ ", cardNo=" + cardNo + ", amounts=" + amounts + ", balance="
				+ balance + ", sms=" + sms + ", respFlag=" + respFlag
				+ ", respDetail=" + respDetail + ", requestTime=" + requestTime
				+ "]";
	}
	
	
}