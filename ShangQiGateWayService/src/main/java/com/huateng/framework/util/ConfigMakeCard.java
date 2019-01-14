package com.huateng.framework.util;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 制卡文件上传下载配置文件
 */
public class ConfigMakeCard {
	private static Logger logger = Logger.getLogger(ConfigMakeCard.class);
	private static String configFileName = "makeCard.properties";
	private static String ip;
	private static String port;
	private static String userName;
	private static String password;
	private static String uploadFilePath;
	private static String downloadFilePath;
	private static String txnCodeFile;
	private static String txnCodeBackFile;
	private static String platformNo;
	private static String txnTypeFile;
	private static String txnTypeBackFile;
	private static String filePath;
	private static String rmotePath;
	private static String cardBin;
	public static void loadConfig() {
		Properties properties = new Properties();
		try {
//			FileInputStream fis = new FileInputStream(configFileName);
			InputStream fis = ConfigMakeCard.class.getClassLoader().
                    getResourceAsStream(configFileName);
			properties.load(fis);
			logger.info("makeCard.properties is loaded successfully!");
		} catch (Exception e) {
			logger.error("Fail to load makeCard.properties!"
					+ e.toString());
			logger.error(e.getMessage());
		}

		ip = properties.getProperty("makeCard.ip");
		port = properties.getProperty("makeCard.port");
		userName = properties.getProperty("makeCard.userName");
		password = properties.getProperty("makeCard.password");
		uploadFilePath = properties.getProperty("makeCard.uploadFilePath");
		downloadFilePath = properties.getProperty("makeCard.downloadFilePath");
		txnCodeFile = properties.getProperty("makeCard.txnCodeFile");
		txnCodeBackFile = properties.getProperty("makeCard.txnCodeBackFile");
		platformNo = properties.getProperty("makeCard.platformNo");
		txnTypeFile = properties.getProperty("makeCard.txnTypeFile");
		txnTypeBackFile = properties.getProperty("makeCard.txnTypeBackFile");
		filePath=properties.getProperty("settle.filePah");
		rmotePath=properties.getProperty("makeCard.remoteFilePath");
		
		cardBin=properties.getProperty("cardBin");
	}
	

	public static String getCardBin() {
		if (null == cardBin) {
			loadConfig();
		}
		return cardBin;
	}


	public static void setCardBin(String cardBin) {
		ConfigMakeCard.cardBin = cardBin;
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

	public static String getDownloadFilePath() {
		if (null == downloadFilePath) {
			loadConfig();
		}
		return downloadFilePath;
	}

	public static String getTxnCodeFile() {
		if (null == txnCodeFile) {
			loadConfig();
		}
		return txnCodeFile;
	}

	public static String getTxnCodeBackFile() {
		if (null == txnCodeBackFile) {
			loadConfig();
		}
		return txnCodeBackFile;
	}
	
	public static String getPlatfromNo() {
		if (null == platformNo) {
			loadConfig();
		}
		return platformNo;
	}
	
	public static String getTxnTypeFile(){
		if(null==txnTypeFile){
			loadConfig();
		}
		return txnTypeFile;
	}
	
	public static String getTxnTypeBackFile(){
		if(null==txnTypeBackFile){
			loadConfig();
		}
		return txnTypeBackFile;
	}

	public static String getFilePath() {
		if(null==filePath){
			loadConfig();
		}
		return filePath;
	}
	public static String getRomtePath(){
		if(null == rmotePath){
			loadConfig();
		}
		return rmotePath;
	}
}
