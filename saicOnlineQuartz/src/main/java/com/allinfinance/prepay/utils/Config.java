package com.allinfinance.prepay.utils;

/**
 * <p>Title: Accor</p>
 *
 * <p>Description:Accor Project 1nd Edition </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: </p>
 * @author YY
 * @version 1.0
 */

import java.io.*;
import java.util.*;

import org.apache.log4j.*;

/**
 * <p>Title: Accor</p>
 *
 * <p>Description:Accor Project 1nd Edition </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: </p>
 * @author YY
 * @version 1.0
 */

public class Config {

	private static Logger logger = Logger.getLogger(Config.class);
	private static String configFileName="config.properties";
	
	private static String coreIp;
	private static String corePort;
	private static String userId;
	private static String channel;
	private static String checkedIp;
	private static String checkedPort;
	private static String orgId;
	private static String transCode;
	private static String provider;
	

	public Config() {
  		
  	}

  	public static void loadConfig(){

  		Properties properties = new Properties();
  		try{
  		
  			Properties sp = System.getProperties();
  			String iposHome = sp.getProperty("quartz_home");
//  			InputStream inputStream = Config.class.getClassLoader().
//                    getResourceAsStream(iposHome+"/"+configFileName);
  			FileInputStream inputStream = new FileInputStream(iposHome+"/"+configFileName);
  			logger.info("iposHomepath:"+iposHome+"/"+configFileName);
  			properties.load(inputStream);
  			logger.info("config.properties is loaded successfully!");
  		}catch(Exception e){
  			logger.error("Fail to load config.properties!" + e.toString());
  		}
  		coreIp = properties.getProperty("coreIp");
  		corePort = properties.getProperty("corePort");
  		userId = properties.getProperty("userId");
  		channel= properties.getProperty("channel");
  		checkedIp=properties.getProperty("checkedIp");
  		checkedPort=properties.getProperty("checkedPort");
  		orgId=properties.getProperty("orgId");
  		transCode=properties.getProperty("transCode");
  		provider=properties.getProperty("provider");
  		
  	}
  	
  	
  	
  	
  	
  	
	public static String getCheckedIp() {
		if(checkedIp == null){
			loadConfig();
		}
		return checkedIp;
	}

	public static void setCheckedIp(String checkedIp) {
		Config.checkedIp = checkedIp;
	}

	public static String getCheckedPort() {
		if(checkedPort == null){
			loadConfig();
		}
		return checkedPort;
	}

	public static void setCheckedPort(String checkedPort) {
		Config.checkedPort = checkedPort;
	}

	public static String getChannel() {
		if(channel == null){
			loadConfig();
		}
		return channel;
	}

	public static void setChannel(String channel) {
		Config.channel = channel;
	}

	public static String getConfigFileName() {
		if(configFileName == null){
			loadConfig();
		}
		return configFileName;
	}

	public static void setConfigFileName(String configFileName) {
		Config.configFileName = configFileName;
	}
	
	
	public static String getCoreIp() {
		if(coreIp == null){
			loadConfig();
		}
		logger.info("config.properties coreIp:"+coreIp);
		return coreIp;
	}

	public static void setCoreIp(String coreIp) {
		Config.coreIp = coreIp;
	}

	public static String getCorePort() {
		if(corePort == null){
			loadConfig();
		}
		logger.info("config.propertiescorePort:"+corePort);
		return corePort;
	}

	public static void setCorePort(String corePort) {
		Config.corePort = corePort;
	}

	public static String getUserId() {
		if(userId == null){
			loadConfig();
		}
//		logger.info("config.properties===> userId:"+userId);
		return userId;
	}

	public static void setUserId(String userId) {
		Config.userId = userId;
	}

	public static String getOrgId() {
		if(orgId == null){
			loadConfig();
		}
		logger.info("config.properties===> orgId:"+orgId);
		return orgId;
	}

	public static void setOrgId(String orgId) {
		Config.orgId = orgId;
	}

	public static String getTransCode() {
		if(transCode == null){
			loadConfig();
		}
		logger.info("config.properties===> transCode:"+transCode);
		return transCode;
	}

	public static void setTransCode(String transCode) {
		Config.transCode = transCode;
	}

	public static String getProvider() {
		if(provider == null){
			loadConfig();
		}
		logger.info("config.properties===> provider:"+provider);
		return provider;
	}

	public static void setProvider(String provider) {
		Config.provider = provider;
	}
	
	
  	
}
