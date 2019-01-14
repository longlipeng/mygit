package com.allinfinance.prepay.message;

import com.allinfinance.prepay.annotation.MessageField;
import com.allinfinance.prepay.annotation.MessageField.FieldType;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class MessageSyncP004Req extends BasicMessage {
	@MessageField(mandetory=true,fieldType=FieldType.CHARACTER,maxLength=19)
	protected  String CARD_NO;//新卡号
	@MessageField(mandetory=true,fieldType=FieldType.CHARACTER,maxLength=19)
	protected  String OLD_CARD_NO;//老卡号
	@MessageField(mandetory=true,fieldType=FieldType.CHARACTER)
	protected  String CARD_PWD;//密码
	
	@MessageField(mandetory=true,fieldType=FieldType.CHARACTER)
	protected  String ID_NO;//证件ID
	@MessageField(mandetory=true,fieldType=FieldType.CHARACTER)
	protected  String ID_TYPE;//证件类别
	
	protected  String MAC;//报文校验码


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

	public String getOLD_CARD_NO() {
		return OLD_CARD_NO;
	}

	public void setOLD_CARD_NO(String oLD_CARD_NO) {
		OLD_CARD_NO = oLD_CARD_NO;
	}

	public String getCARD_PWD() {
		return CARD_PWD;
	}

	public void setCARD_PWD(String cARD_PWD) {
		CARD_PWD = cARD_PWD;
	}

	public String getCARD_NO() {
		return CARD_NO;
	}

	public void setCARD_NO(String cARD_NO) {
		CARD_NO = cARD_NO;
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
		MessageSyncP004Req.xs = xs;
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
