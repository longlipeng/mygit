package com.huateng.framework.util;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class BlackListAndRiskGradeConfig {
	private static Logger logger = Logger.getLogger(Config.class);
	private static String configFileName = "blackListandriskgrade.properties";
	private static String riskGradeURL ;
	private static String blackListRUL ;

	public static void loadConfig() {
		Properties properties = new Properties();
		try {
			// FileInputStream inputStream = new FileInputStream(configFileName);
			InputStream inputStream = BlackListAndRiskGradeConfig.class.getClassLoader().getResourceAsStream(configFileName);
			properties.load(inputStream);
			logger.info("accor.properties is loaded successfully!");
		} catch (Exception e) {
			logger.error("Fail to load accor.properties!" + e.toString());
		}
		riskGradeURL = properties.getProperty("riskGradeURL");
		blackListRUL = properties.getProperty("blackListRUL");
	}


	public static String getRiskGradeURL() {
		if(riskGradeURL==null) {
			loadConfig();
		}
		return riskGradeURL;
	}

	public static String getBlackListRUL() {
		if(blackListRUL==null) {
			loadConfig();
		}
		return blackListRUL;
	}

}
