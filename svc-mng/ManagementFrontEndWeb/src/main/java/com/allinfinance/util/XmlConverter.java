package com.allinfinance.util;

import com.thoughtworks.xstream.XStream;

public class XmlConverter {
	private static XStream xs = new XStream();
	private String IMGF;
	private String IMGB;
	private String TXN_CODE;
	private String RESPCODE;
	private String MARK;
	
	
	
	public static Object parseXml(String xmlMessage){
		xs.alias("DATA", XmlConverter.class);
		return xs.fromXML(xmlMessage);
	}
	
	public String getTXN_CODE() {
		return TXN_CODE;
	}


	public void setTXN_CODE(String tXN_CODE) {
		TXN_CODE = tXN_CODE;
	}


	public String getRESPCODE() {
		return RESPCODE;
	}


	public void setRESPCODE(String rESPCODE) {
		RESPCODE = rESPCODE;
	}


	public String getMARK() {
		return MARK;
	}


	public void setMARK(String mARK) {
		MARK = mARK;
	}


	public String getIMGF() {
		return IMGF;
	}


	public void setIMGF(String iMGF) {
		IMGF = iMGF;
	}


	public String getIMGB() {
		return IMGB;
	}


	public void setIMGB(String iMGB) {
		IMGB = iMGB;
	}



}
