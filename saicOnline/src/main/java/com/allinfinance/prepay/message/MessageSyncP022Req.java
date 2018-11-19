package com.allinfinance.prepay.message;

import com.allinfinance.prepay.annotation.MessageField;
import com.allinfinance.prepay.annotation.MessageField.FieldType;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;

@XStreamAlias("DATA")
public class MessageSyncP022Req extends BasicMessage {
	@MessageField(mandetory=true,fieldType=FieldType.CHARACTER)
	protected  String ID_NO;//证件ID
	@MessageField(mandetory=true,fieldType=FieldType.CHARACTER)
	protected  String ID_TYPE;//证件类别
	protected  String TXN_TYPE;
	protected  String KEY;
	protected  String MAC;//报文校验码
	
	private static XStream xs = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
	
	public static XStream getXs() {
		return xs;
	}

	public static void setXs(XStream xs) {
		MessageSyncP022Req.xs = xs;
	}
	
	

	public String getTXN_TYPE() {
		return TXN_TYPE;
	}

	public void setTXN_TYPE(String tXN_TYPE) {
		TXN_TYPE = tXN_TYPE;
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
