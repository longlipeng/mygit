package com.huateng.framework.util;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
/**
 * 
 * @author dawn
 */
public class RechargeConfig {

	
	private static Logger logger = Logger.getLogger(RechargeConfig.class);
	private static String configFileName = "recharge.properties";
	private static String ip;
	private static String port;
	private static String userName;
	private static String password;
	private static String uploadFilePath;

	public static void loadConfig() {
		Properties properties = new Properties();
		try {
			FileInputStream fis = new FileInputStream(configFileName);
			properties.load(fis);

		} catch (Exception e) {
			logger.error("Open makeCard.properties config file fail!"
					+ e.toString());
			logger.error(e.getMessage());
		}

		ip = properties.getProperty("ip");
		port = properties.getProperty("port");
		userName = properties.getProperty("userName");
		password = properties.getProperty("password");
		uploadFilePath = properties.getProperty("uploadFilePath");
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

	public static String getUserName() {
		if (null == userName) {
			loadConfig();
		}
		return userName;
	}

	public static String getPassword() {
		if (null == password) {
			loadConfig();
		}
		return password;
	}

	public static String getUploadFilePath() {
		if (null == uploadFilePath) {
			loadConfig();	
		}
		return uploadFilePath;
	}
}
