package com.allinfinance.prepay.message;

import java.util.List;

import com.allinfinance.prepay.dto.Product;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;

@XStreamAlias("DATA")
public class MessageSyncP027Resp extends BasicMessage{
	
	@XStreamImplicit(itemFieldName="product")
	private List<Product> list;
	private String MAC;//报文校验码
	private String resMsg;
	
	private static XStream xs = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));

	
	

	public String getResMsg() {
		return resMsg;
	}

	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
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
		MessageSyncP027Resp.xs = xs;
	}


	
	
	public List<Product> getList() {
		return list;
	}

	public void setList(List<Product> list) {
		this.list = list;
	}

	@Override
	public String toXml()
	{
		xs.alias("DATA", this.getClass());
		xs.alias("product", Product.class);
		xs.processAnnotations(this.getClass());
		return xs.toXML(this);
	}
	
	public static Object parseXml(String xml)
	{
		xs.alias("DATA", MessageSyncP022Resp.class);
		return xs.fromXML(xml);
		
	}
	
}
