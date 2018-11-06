package com.allinfinance.prepay.message;

import com.allinfinance.prepay.annotation.MessageField;
import com.allinfinance.prepay.annotation.MessageField.FieldType;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;

@XStreamAlias("DATA")
public class MessageSyncP011Resp extends BasicMessage {
	
	
	
	
	private static XStream xs = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));

	

	

	public static XStream getXs() {
		return xs;
	}

	public static void setXs(XStream xs) {
		MessageSyncP011Resp.xs = xs;
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
		xs.alias("DATA", MessageSyncP001Resp.class);
		return xs.fromXML(xml);
		
	}
}
