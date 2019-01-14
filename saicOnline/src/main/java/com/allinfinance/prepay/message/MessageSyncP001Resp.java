package com.allinfinance.prepay.message;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;

@XStreamAlias("DATA")
public class MessageSyncP001Resp extends BasicMessage {
	
	
	
	private String MAC;//����У����
	
	private static XStream xs = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));

	

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
		MessageSyncP001Resp.xs = xs;
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
