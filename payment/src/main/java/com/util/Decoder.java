package com.util;


import com.thoughtworks.xstream.XStream;

public class Decoder {

	public static Object parseXml(XStream xs, String xml, String alias, Class<?> clazz) {
		xs.alias(alias, clazz);
		return xs.fromXML(xml);
	}

}
