package com.huateng.framework.util;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ConfigPosp {
	private static Logger logger = Logger.getLogger(ConfigPosp.class);
	private static String configFileName = "webservice.properties";
	private static String ip;
	private static String port;
	private static String timeout;
	private static String listenerPort;
	private static String webserviceIp;
	private static String sendPort;
	private static String cardAccptrTermnlId;//终端号
	private static String cardAccptrId;//商户号
	private static String recordConsumePort;//商户号
	private static String dataSourceId;//中石油第三方编号
	private static String coreIp;//发往核心的IP
	private static String corePort;//发往核心的端口
	private static String mallAccptrTermnlId;//商城消费的终端号
	private static String mallAccptrId;//商城消费的商户号
	
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

		ip = properties.getProperty("socket.ip");
		port = properties.getProperty("socket.port");
		timeout = properties.getProperty("socket.timeout");
		listenerPort = properties.getProperty("socket.listener_port");
		webserviceIp=properties.getProperty("webserviceIp");
		sendPort=properties.getProperty("socket.sendport");
		cardAccptrTermnlId=properties.getProperty("TermCode");
		cardAccptrId=properties.getProperty("MechtCode");
		recordConsumePort=properties.getProperty("socket.recordConsumePort");
		dataSourceId=properties.getProperty("DataSource_id");
		coreIp=properties.getProperty("core_ip");
		corePort=properties.getProperty("core_port");
		mallAccptrTermnlId=properties.getProperty("TermCode1");
		mallAccptrId=properties.getProperty("MechtCode1");
	}

	
	
	public static String getMallAccptrTermnlId() {
		if (null == mallAccptrTermnlId) {
			loadConfig();
		}
		return mallAccptrTermnlId;
	}



	public static void setMallAccptrTermnlId(String mallAccptrTermnlId) {
		ConfigPosp.mallAccptrTermnlId = mallAccptrTermnlId;
	}



	public static String getMallAccptrId() {
		if (null == mallAccptrId) {
			loadConfig();
		}
		return mallAccptrId;
	}



	public static void setMallAccptrId(String mallAccptrId) {
		ConfigPosp.mallAccptrId = mallAccptrId;
	}



	public static String getCoreIp() {
		if (null == coreIp) {
			loadConfig();
		}
		return coreIp;
	}
	

	public static String getCorePort() {
		if (null == corePort) {
			loadConfig();
		}
		return corePort;
	}

	public static void setCorePort(String corePort) {
		ConfigPosp.corePort = corePort;
	}

	public static void setCoreIp(String coreIp) {
		ConfigPosp.coreIp = coreIp;
	}
	
	public static String getDataSourceId() {
		if (null == dataSourceId) {
			loadConfig();
		}
		return dataSourceId;
	}

	public static void setDataSourceId(String dataSourceId) {
		ConfigPosp.dataSourceId = dataSourceId;
	}



	public static String getRecordConsumePort() {
		if (null == recordConsumePort) {
			loadConfig();
		}
		return recordConsumePort;
	}
	public static void setRecordConsumePort(String recordConsumePort) {
		ConfigPosp.recordConsumePort = recordConsumePort;
	}

	public static String getCardAccptrTermnlId() {
		if (null == cardAccptrTermnlId) {
			loadConfig();
		}
		return cardAccptrTermnlId;
	}


	public static void setCardAccptrTermnlId(String cardAccptrTermnlId) {
		ConfigPosp.cardAccptrTermnlId = cardAccptrTermnlId;
	}


	public static String getCardAccptrId() {
		if (null == cardAccptrId) {
			loadConfig();
		}
		return cardAccptrId;
	}


	public static void setCardAccptrId(String cardAccptrId) {
		ConfigPosp.cardAccptrId = cardAccptrId;
	}
	
	
	public static String getSendPort() {
		if (null == sendPort) {
			loadConfig();
		}
		return sendPort;
	}





	public static void setSendPort(String sendPort) {
		ConfigPosp.sendPort = sendPort;
	}





	public static String getWebserviceIp() {
		if (null == webserviceIp) {
			loadConfig();
		}
		return webserviceIp;
	}


	public static void setWebserviceIp(String webserviceIp) {
		ConfigPosp.webserviceIp = webserviceIp;
	}


	public static Logger getLogger() {
		if (null == configFileName) {
			loadConfig();
		}
		return logger;
	}
	public static void setLogger(Logger logger) {
		ConfigPosp.logger = logger;
	}
	public static String getConfigFileName() {
		if (null == configFileName) {
			loadConfig();
		}
		return configFileName;
	}
	public static void setConfigFileName(String configFileName) {
		ConfigPosp.configFileName = configFileName;
	}
	public static String getIp() {
		if (null == ip) {
			loadConfig();
		}
		return ip;
	}
	public static void setIp(String ip) {
		ConfigPosp.ip = ip;
	}
	public static String getPort() {
		if (null == port) {
			loadConfig();
		}
		return port;
	}
	public static void setPort(String port) {
		ConfigPosp.port = port;
	}
	public static String getTimeout() {
		if (null == timeout) {
			loadConfig();
		}
		return timeout;
	}
	public static void setTimeout(String timeout) {
		ConfigPosp.timeout = timeout;
	}
	public static String getListenerPort() {
		if (null == listenerPort) {
			loadConfig();
		}
		return listenerPort;
	}
	public static void setListenerPort(String listenerPort) {
		ConfigPosp.listenerPort = listenerPort;
	}
	
	

}
