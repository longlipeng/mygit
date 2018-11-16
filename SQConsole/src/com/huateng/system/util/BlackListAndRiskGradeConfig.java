package com.huateng.system.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;
/**
 * 
 * @author Li
 *
 */
public class BlackListAndRiskGradeConfig {
	private static Logger logger = Logger.getLogger(BlackListAndRiskGradeConfig.class);
	private static String configFileName = "blackListandriskgrade.properties";
	private static String riskGradeURL ;
	private static String blackListRUL ;

	public static void loadConfig() {
		Properties properties = new Properties();
		try {
		    // 使用ClassLoader加载properties配置文件生成对应的输入流
		    InputStream in = BlackListAndRiskGradeConfig.class.getClassLoader().getResourceAsStream(configFileName);
		    // 使用properties对象加载输入流
		    properties.load(in);

			logger.info("accor.properties is loaded successfully!");
		} catch (Exception e) {
			logger.error("Fail to load accor.properties!" + e.toString());
		}
		//获取key对应的value值
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
