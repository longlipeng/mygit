package com.allinfinance.prepay.message;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;


public class MessageMinResp extends BasicMessage {

	private static XStream xs = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
	public MessageMinResp()
	{
//		this.REQTYPE = "02";
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
