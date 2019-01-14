package com.huateng.framework.msg;

import java.io.InputStream;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import com.huateng.framework.constant.Const;
import com.huateng.framework.exception.BizServiceException;

/**
 * <p>
 * Title: Accor
 * </p>
 * 
 * <p>
 * Description:Accor Project 1nd Edition
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author YY
 * @version 1.0
 */

public class PacketConfig {
	static Logger logger = Logger.getLogger(PacketConfig.class);
	protected static HashMap hashDoc = new HashMap();
	private static PacketConfig config = new PacketConfig();

	PacketConfig() {

	}

	public PacketConfig getPacketConfig() {
		return config;
	}

	public static Document getDocument(String xmlFile)
			throws BizServiceException {
		try {
			Document xmlDoc = null;
			if (hashDoc.containsKey(xmlFile)) {
				xmlDoc = (Document) hashDoc.get(xmlFile);
			} else {
				logger.debug("this is :" + xmlFile);
				SAXReader reader = new SAXReader();
				logger.debug(PacketConfig.class.getClassLoader().getResource(
						xmlFile));

				InputStream is = PacketConfig.class.getClassLoader()
						.getResourceAsStream(xmlFile);

				xmlDoc = reader.read(is);
				hashDoc.put(xmlFile, xmlDoc);
			}
			return xmlDoc;
		} catch (Exception ex) {
			throw new BizServiceException("Error Get XML File" + ex.toString());
		}
	}

}
