package com.allinfinance.prepay.message;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;

@XStreamAlias("DATA")
public class MessageSyncP022Resp extends BasicMessage{
	
	private String ID_NO;//证件ID
	private String ID_TYPE;//证件类别
	private String TXN_TYPE;//交易代码
	private String FIRST_NAME;//持卡人姓名
	private String CARDHOLDER_MOBILE;//手机号
	private String CARDHOLDER_GENDER;//性别
	private String DELIVERY_ADDRESS;//邮寄地址
	private String PLATE_NUMBER;//车牌号
	private String DRIVER_LICENCE;//驾照号
	private String ACTIVITY_SECTOR;//职业
	private String CUSTOMER_TYPE;//购卡人类型
	private String CUSTOMER_NAME;//购卡人所属企业名称
	private String MAC;//报文校验码
	private String REMARK;//备注
	
	private static XStream xs = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));

	

	public String getACTIVITY_SECTOR() {
		return ACTIVITY_SECTOR;
	}

	public void setACTIVITY_SECTOR(String aCTIVITY_SECTOR) {
		ACTIVITY_SECTOR = aCTIVITY_SECTOR;
	}

	public String getID_NO() {
		return ID_NO;
	}

	public void setID_NO(String iD_NO) {
		ID_NO = iD_NO;
	}

	public String getID_TYPE() {
		return ID_TYPE;
	}

	public void setID_TYPE(String iD_TYPE) {
		ID_TYPE = iD_TYPE;
	}

	public String getTXN_TYPE() {
		return TXN_TYPE;
	}

	public void setTXN_TYPE(String tXN_TYPE) {
		TXN_TYPE = tXN_TYPE;
	}

	public String getFIRST_NAME() {
		return FIRST_NAME;
	}

	public void setFIRST_NAME(String fIRST_NAME) {
		FIRST_NAME = fIRST_NAME;
	}

	public String getCARDHOLDER_MOBILE() {
		return CARDHOLDER_MOBILE;
	}

	public void setCARDHOLDER_MOBILE(String cARDHOLDER_MOBILE) {
		CARDHOLDER_MOBILE = cARDHOLDER_MOBILE;
	}

	public String getCARDHOLDER_GENDER() {
		return CARDHOLDER_GENDER;
	}

	public void setCARDHOLDER_GENDER(String cARDHOLDER_GENDER) {
		CARDHOLDER_GENDER = cARDHOLDER_GENDER;
	}

	public String getDELIVERY_ADDRESS() {
		return DELIVERY_ADDRESS;
	}

	public void setDELIVERY_ADDRESS(String dELIVERY_ADDRESS) {
		DELIVERY_ADDRESS = dELIVERY_ADDRESS;
	}

	public String getPLATE_NUMBER() {
		return PLATE_NUMBER;
	}

	public void setPLATE_NUMBER(String pLATE_NUMBER) {
		PLATE_NUMBER = pLATE_NUMBER;
	}

	public String getDRIVER_LICENCE() {
		return DRIVER_LICENCE;
	}

	public void setDRIVER_LICENCE(String dRIVER_LICENCE) {
		DRIVER_LICENCE = dRIVER_LICENCE;
	}

	

	public String getCUSTOMER_TYPE() {
		return CUSTOMER_TYPE;
	}

	public void setCUSTOMER_TYPE(String cUSTOMER_TYPE) {
		CUSTOMER_TYPE = cUSTOMER_TYPE;
	}

	public String getCUSTOMER_NAME() {
		return CUSTOMER_NAME;
	}

	public void setCUSTOMER_NAME(String cUSTOMER_NAME) {
		CUSTOMER_NAME = cUSTOMER_NAME;
	}

	public String getREMARK() {
		return REMARK;
	}

	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}

	public String getMCHNT_CD() {
		return MCHNT_CD;
	}

	public void setMCHNT_CD(String mCHNT_CD) {
		MCHNT_CD = mCHNT_CD;
	}

	public String getMAC() {
		return MAC;
	}

	public void setMAC(String mAC) {
		MAC = mAC;
	}
	
	public static XStream getXs() {
		return xs;
	}

	public static void setXs(XStream xs) {
		MessageSyncP022Resp.xs = xs;
	}

	@Override
	public String toXml()
	{
		xs.alias("DATA", this.getClass());
		xs.processAnnotations(this.getClass());
		return xs.toXML(this);
	}
	
	public static Object parseXml(String xml)
	{
		xs.alias("DATA", MessageSyncP022Resp.class);
		return xs.fromXML(xml);
		
	}
}
