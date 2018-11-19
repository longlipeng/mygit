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
	protected  String ID_NO;//֤��ID
	@MessageField(mandetory=true,fieldType=FieldType.CHARACTER)
	protected  String ID_TYPE;//֤�����
	@MessageField(mandetory=true,fieldType=FieldType.CHARACTER)
	protected  String TXN_TYPE;//��������
	@MessageField(mandetory=true,fieldType=FieldType.CHARACTER)
	protected  String CARD_NO;//����
	@MessageField(mandetory=true,fieldType=FieldType.CHARACTER)
	protected  String CARD_PWD;//��Ƭ��������
	@MessageField(mandetory=true,fieldType=FieldType.CHARACTER)
	protected  String FIRST_NAME;//�ֿ�������
	@MessageField(mandetory=true,fieldType=FieldType.CHARACTER)
	protected  String MAC;//����У����
	@MessageField(mandetory=false,fieldType=FieldType.CHARACTER,maxLength=15)
	protected  String PLATE_NUMBER;//���ƺ�
	@MessageField(mandetory=false,fieldType=FieldType.CHARACTER,maxLength=30)
	protected  String DRIVER_LICENCE;//���պ�
	@MessageField(mandetory=false,fieldType=FieldType.CHARACTER,maxLength=64)
	protected  String CARDHOLDER_MOBILE;//�ֻ���
	@MessageField(mandetory=false,fieldType=FieldType.CHARACTER,maxLength=1)
	protected  String CUSTOMER_TPYE;//����������
	
	
	protected  String CUSTOMER_NAME;//������������ҵ����
	protected  String ACTIVITY_SECTOR;//ְҵ
	protected  String DELIVERY_ADDRESS;//�ʼĵ�ַ
	protected  String CARDHOLDER_GENDER;//�Ա�
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
