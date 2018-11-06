package com.huateng.hstserver.config;

/**
 * HSTServerProperties.java - 2012-07-11
 *
 * Licensed Property to HuaTeng Data Co., Ltd.
 *
 * (C) Copyright of HuaTeng Data Co., Ltd. 2012
 *     All Rights Reserved.
 *
 * Project Info: HuaTeng Data Internet Acquiring Project
 *
 * Modification History:
 * =============================================================================
 *   Author         Date          Description
 *   ------------ ---------- ---------------------------------------------------
 *   胡万里
 * =============================================================================
 */


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.huateng.hstserver.communicate.mina.main.SendThread;

/**
 * 
 * Description:系统属性类
 * 
 * Copyright: Copyright (c) 2010
 * 
 * Company: HuaTeng Data Co., Ltd.
 * 
 * 
 * @author wanli.hu
 * 
 * @version 1.0, 2012-9-27
 */
public class HSTProperties {
	private static Logger logger = Logger.getLogger(HSTProperties.class);
	public static String baseDir;
	private static final Properties props = new Properties();
	private static String fileName;
	private static Map<String,String> HST_CONIFG;

	public HSTProperties() {
	}

	public static String getString(String key) {
		if (null == HST_CONIFG) {
			if(fileName != null)
				HSTProperties.reloadByExtConf();
			else
				HSTProperties.reloadByLocalConf();			
		}	
		return (String) HST_CONIFG.get(key);
	}

	public static boolean isExits(String key) {
		if (null == HST_CONIFG) {
			if(fileName != null)
				HSTProperties.reloadByExtConf();
			else
				HSTProperties.reloadByLocalConf();	
		}
		return HST_CONIFG.containsKey(key);
	}

	public static void reloadByLocalConf() {
		Map<String,String> newHSTConfig = new HashMap<String,String>();
		ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("HST");

		newHSTConfig.put("LISTEN_IP",getValue(RESOURCE_BUNDLE, "LISTEN_IP",""));
		newHSTConfig.put("LISTEN_PORT",getValue(RESOURCE_BUNDLE, "LISTEN_PORT",""));
		
		newHSTConfig.put("DOMAN1_ID",getValue(RESOURCE_BUNDLE, "DOMAN1_ID",""));
		newHSTConfig.put("TXN_IP_1",getValue(RESOURCE_BUNDLE, "TXN_IP_1",""));
		newHSTConfig.put("TXN_PORT_1",getValue(RESOURCE_BUNDLE, "TXN_PORT_1",""));
		newHSTConfig.put("ACC_IP_1",getValue(RESOURCE_BUNDLE, "ACC_IP_1",""));
		newHSTConfig.put("ACC_PORT_1",getValue(RESOURCE_BUNDLE, "ACC_PORT_1",""));
		newHSTConfig.put("STL_IP_1",getValue(RESOURCE_BUNDLE, "STL_IP_1","") );
		newHSTConfig.put("STL_PORT_1",getValue(RESOURCE_BUNDLE, "STL_PORT_1",""));

		newHSTConfig.put("DOMAN2_ID",getValue(RESOURCE_BUNDLE, "DOMAN2_ID",""));
		newHSTConfig.put("TXN_IP_2",getValue(RESOURCE_BUNDLE, "TXN_IP_2",""));
		newHSTConfig.put("TXN_PORT_2",getValue(RESOURCE_BUNDLE, "TXN_PORT_2",""));
		newHSTConfig.put("ACC_IP_2",getValue(RESOURCE_BUNDLE, "ACC_IP_2",""));
		newHSTConfig.put("ACC_PORT_2",getValue(RESOURCE_BUNDLE, "ACC_PORT_2",""));
		newHSTConfig.put("STL_IP_2",getValue(RESOURCE_BUNDLE, "STL_IP_2","") );
		newHSTConfig.put("STL_PORT_2",getValue(RESOURCE_BUNDLE, "STL_PORT_2",""));
		
		newHSTConfig.put("SERVICE_NM",getValue(RESOURCE_BUNDLE, "SERVICE_NM",""));
		
		newHSTConfig.put("ACC_CHNL_ID",getValue(RESOURCE_BUNDLE, "ACC_CHNL_ID",""));
		newHSTConfig.put("ACC_HEARTBEAT_IN",getValue(RESOURCE_BUNDLE, "ACC_HEARTBEAT_IN",""));		
		newHSTConfig.put("TXN_CHNL_ID",getValue(RESOURCE_BUNDLE, "TXN_CHNL_ID",""));
		newHSTConfig.put("TXN_HEARTBEAT_IN",getValue(RESOURCE_BUNDLE, "TXN_HEARTBEAT_IN",""));
		newHSTConfig.put("STL_CHNL_ID",getValue(RESOURCE_BUNDLE, "STL_CHNL_ID",""));
		newHSTConfig.put("STL_HEARTBEAT_IN",getValue(RESOURCE_BUNDLE, "STL_HEARTBEAT_IN",""));
		
		newHSTConfig.put("TIME_OUT_SEC",getValue(RESOURCE_BUNDLE, "TIME_OUT_SEC",""));
		
		HST_CONIFG = newHSTConfig;
	}

	private static String getValue(ResourceBundle RESOURCE_BUNDLE, String key,String defuat) {
		String value = "";
		try {
			value = RESOURCE_BUNDLE.getString(key);
		} catch (java.util.MissingResourceException e) {
			value = "";
		}
		return null == value || "".equals(value) ? defuat : value;
	}

	public static void reloadByExtConf() {
		if(fileName != null){
			InputStream in;
			Map<String,String> newHSTConfig = new HashMap<String,String>();
			try {
				in = new BufferedInputStream(new FileInputStream(fileName));
				props.load(in);
				in.close();
			} catch (Exception e) {
				logger.error(e.getMessage());
				reloadByLocalConf();
				return;//不在执行以下代码 2014-12-16
			}
			Enumeration e = props.keys();
			while (e.hasMoreElements()) {
				// 获取属性文件的属性值
				String str = (String) e.nextElement();
				if (str == null) {
					continue;
				}
				Object obj = props.get(str);
				if (obj == null) {
					continue;
				}
				// 属性名：属性值
				newHSTConfig.put(str.trim(), obj.toString().trim());
			}
			HST_CONIFG = newHSTConfig;
		}
	}
	
	public static void setHSTproperties(String fileNamePath) {
		fileName = fileNamePath;
	}

	
/*	public static void main(String[] args) throws IOException {

		HSTProperties.setHSTproperties("D:/workspace/hsdata/HSTServer/resources/HST.properties");
		String ss = HSTProperties.getString("ACC_IP_1");
		logger.info(ss);
		boolean s = HSTProperties.isExits("ACC_PORT_1");
		logger.info(s);
	}*/
}
