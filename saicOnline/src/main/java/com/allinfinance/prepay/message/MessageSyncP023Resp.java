package com.allinfinance.prepay.message;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;

@XStreamAlias("DATA")
public class MessageSyncP023Resp extends BasicMessage {
	
	private String ERROR_MSG;//不通过时，返回错误信息            

	public String getERROR_MSG() {
		return ERROR_MSG;
	}

	public void setERROR_MSG(String eRROR_MSG) {
		ERROR_MSG = eRROR_MSG;
	}

	public static XStream getXs() {
		return xs;
	}

	public static void setXs(XStream xs) {
		MessageSyncP023Resp.xs = xs;
	}

	private static XStream xs = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));

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
