package com.allinfinance.prepay.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * ∂Ã–≈≈‰÷√Œƒº˛
 */
public class ConfigMessage {
	private static Logger logger = Logger.getLogger(ConfigMessage.class);
	private static String configFileName = "shortMessage.properties";
	private static String ip;
	private static String port;
	private static String templet1_No;
	private static String templet2_No;
	//…Û∫À∂Ã–≈ƒ£∞Â∫≈
	private static String templet3_No;
	public static void loadConfig() {
		Properties properties = new Properties();
		
		try {
			Properties sp = System.getProperties();
			String iposHome = sp.getProperty("IPOS_HOME");
			FileInputStream fis = new FileInputStream(iposHome+"/"+configFileName);
//			InputStream fis = ConfigMessage.class.getClassLoader().
//                    getResourceAsStream(configFileName);
			properties.load(fis);
			logger.info("shortMessage.properties is loaded successfully!");
		} catch (Exception e) {
			logger.error("Fail to load shortMessage.properties!"
					+ e.toString());
			logger.error(e.getMessage());
		}
		
		ip = properties.getProperty("Host_IP");
		port = properties.getProperty("Host_Port");
		templet1_No = properties.getProperty("Templet1_No");
		templet2_No = properties.getProperty("Templet2_No");
		
		//…Û∫À∂Ã–≈ƒ£∞Â∫≈
		templet3_No = properties.getProperty("Templet3_No");
	}
	
	
	
	
	public static String getConfigFileName() {
		if (null == configFileName) {
			loadConfig();
		}
		return configFileName;
	}
	
	
	
	public static String getIp() {
		if (null == ip) {
			loadConfig();
		}
		return ip;
	}

	public static String getPort() {
		if (null == port) {
			loadConfig();
		}
		return port;
	}
	
	public static String getTemplet1_No() {
		if (null == templet1_No) {
			loadConfig();
		}
		return templet1_No;
	}
	public static String getTemplet2_No() {
		if (null == templet2_No) {
			loadConfig();
		}
		return templet2_No;
	}
	
	public static String getTemplet3_No() {
		if (null == templet3_No) {
			loadConfig();
		}
		return templet3_No;
	}
}