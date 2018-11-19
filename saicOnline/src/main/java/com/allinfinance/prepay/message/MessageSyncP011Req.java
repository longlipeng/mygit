package com.allinfinance.prepay.message;

import com.allinfinance.prepay.annotation.MessageField;
import com.allinfinance.prepay.annotation.MessageField.FieldType;
//import com.allinfinance.prepay.encryption.ArqcRequestObject;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;

@XStreamAlias("DATA")
public class MessageSyncP011Req extends BasicMessage {
	
	@MessageField(mandetory=true,fieldType=FieldType.CHARACTER)
	protected  String ID_NO;//证件ID
	@MessageField(mandetory=true,fieldType=FieldType.CHARACTER)
	protected  String ID_TYPE;//证件类别
	@MessageField(mandetory=true,fieldType=FieldType.CHARACTER)
	protected  String TXN_TYPE;//交易类型
	@MessageField(mandetory=true,fieldType=FieldType.CHARACTER)
	protected  String CARD_NO;//卡号
	@MessageField(mandetory=true,fieldType=FieldType.CHARACTER)
	protected  String CARD_PWD;//卡片交易密码
	@MessageField(mandetory=true,fieldType=FieldType.CHARACTER)
	protected  String FIRST_NAME;//持卡人姓名
	@MessageField(mandetory=true,fieldType=FieldType.CHARACTER)
	protected  String MAC;//报文校验码
	@MessageField(mandetory=false,fieldType=FieldType.CHARACTER,maxLength=15)
	protected  String PLATE_NUMBER;//车牌号
	@MessageField(mandetory=false,fieldType=FieldType.CHARACTER,maxLength=30)
	protected  String DRIVER_LICENCE;//驾照号
	@MessageField(mandetory=false,fieldType=FieldType.CHARACTER,maxLength=64)
	protected  String CARDHOLDER_MOBILE;//手机号
	@MessageField(mandetory=false,fieldType=FieldType.CHARACTER,maxLength=1)
	protected  String CUSTOMER_TPYE;//购卡人类型
	
	
	protected  String CUSTOMER_NAME;//购卡人所属企业名称
	protected  String ACTIVITY_SECTOR;//职业
	protected  String DELIVERY_ADDRESS;//邮寄地址
	protected  String CARDHOLDER_GENDER;//性别
	protected  String KEY;//PIN key
	
	private static XStream xs = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));

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

	public String getCARD_NO() {
		return CARD_NO;
	}

	public void setCARD_NO(String cARD_NO) {
		CARD_NO = cARD_NO;
	}

	public String getCARD_PWD() {
		return CARD_PWD;
	}

	public void setCARD_PWD(String cARD_PWD) {
		CARD_PWD = cARD_PWD;
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

	public String getACTIVITY_SECTOR() {
		return ACTIVITY_SECTOR;
	}

	public void setACTIVITY_SECTOR(String aCTIVITY_SECTOR) {
		ACTIVITY_SECTOR = aCTIVITY_SECTOR;
	}

	public String getCUSTOMER_TPYE() {
		return CUSTOMER_TPYE;
	}

	public void setCUSTOMER_TPYE(String cUSTOMER_TPYE) {
		CUSTOMER_TPYE = cUSTOMER_TPYE;
	}

	public String getCUSTOMER_NAME() {
		return CUSTOMER_NAME;
	}

	public void setCUSTOMER_NAME(String cUSTOMER_NAME) {
		CUSTOMER_NAME = cUSTOMER_NAME;
	}

	public String getKEY() {
		return KEY;
	}

	public void setKEY(String kEY) {
		KEY = kEY;
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
		MessageSyncP011Req.xs = xs;
	}

	@Override
	public String toXml()
	{
		xs.alias("DATA", this.getClass());
		xs.processAnnotations(this.getClass());
		return xs.toXML(this);
	}
	
	public static Object parseXml(String xml, Class<?> clazz)
	{
		xs.alias("DATA", clazz);
		return xs.fromXML(xml);
		
	}

}
