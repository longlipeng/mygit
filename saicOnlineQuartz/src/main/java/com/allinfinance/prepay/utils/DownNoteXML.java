package com.allinfinance.prepay.utils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)//��ʾʹ��������е� private �Ǿ�̬�ֶ���Ϊ XML ������
@XmlRootElement(name="DATA")
@XmlType(propOrder={"orgId","smsNo","smsType","mobile","cv","cardNo","amounts","balance","sms","respFlag","respDetail","requestTime"})//xml��ʽ���ݵ���ʾ��˳������Ҫ�Ͷ��������һ����������@XmlElement�е�name
public class DownNoteXML {
	//����xml����ʾ������
    @XmlElement(name="ORG_ID",required=true)//������
    private  String orgId;
    @XmlElement(name="SMS_NO",required=true)//��ˮ��
    private String smsNo;
    @XmlElement(name="SMS_TYPE",required=true)//��������
    private String smsType;
    @XmlElement(name="MOBILE",required=true)//����
    private String mobile;
    @XmlElement(name="CV",required=true)//У����
    private String cv;
    @XmlElement(name="CARDNO",required=true)//����
    private String cardNo;
    @XmlElement(name="AMOUNTS",required=true)//���׽��
    private String amounts;
    @XmlElement(name="BALANCE",required=true)//���
    private String balance;
    @XmlElement(name="SMS",required=true)//����ģ���
    private String sms;
    @XmlElement(name="RESP_FLAG",required=true)//���ر�־
    private String respFlag;
    @XmlElement(name="RESP_DETAIL",required=true)//��������
    private String respDetail;
    @XmlElement(name="REQUEST_TIME",required=true)//��������
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