package com.allinfinance.prepay.message;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;

@XStreamAlias("DATA")
public class MessageSyncP027Req extends BasicMessage {
	protected  String MAC;//报文校验码
	protected  String defult;//默认参数
	
	private static XStream xs = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
	
	
	
	
	
	

	public String getDefult() {
		return defult;
	}

	public void setDefult(String defult) {
		this.defult = defult;
	}

	public static XStream getXs() {
		return xs;
	}

	public static void setXs(XStream xs) {
		MessageSyncP027Req.xs = xs;
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
