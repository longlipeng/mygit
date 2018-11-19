package com.huateng.framework.util;

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
	private static String configFileName="accor.properties";
	private static String serverURL;
	private static String mgmtServiceName;
	private static String txnServiceName;
	private static String tmKey;
	private static String wkKeyC;
	private static String pinKey;
	private static String macKey;
	private static String actionURL;
	private static String saSMTPHost;
	private static String mailFrom;
	
	private static String activeTime;
	
	private static String checkTime;
	
	private static String fileDataPath;
	
	private static String SMServerURL;
	
	private static String commFileUploadPath;
	
	private static String clientName;
	
	private static String ireportURL;
	
	private static String certURL;
	private static String logoURL;
	private static String domainURL;
	
	//收单机构号
	private static String consumerEntityId;
  	


	public Config() {
  		
  	}

  	public static void loadConfig(){

  		Properties properties = new Properties();
  		try{
//  		FileInputStream inputStream = new FileInputStream(configFileName);
  			InputStream inputStream = Config.class.getClassLoader().
                    getResourceAsStream(configFileName);
  			properties.load(inputStream);
  			logger.info("accor.properties is loaded successfully!");
  		}catch(Exception e){
  		    logger.error("Fail to load accor.properties!" + e.toString());
  		}
  		serverURL = properties.getProperty("serverURL");
  		mgmtServiceName = properties.getProperty("mgmtServiceName");
  		txnServiceName = properties.getProperty("txnServiceName");
  		tmKey = properties.getProperty("tmKey");
  		wkKeyC = properties.getProperty("wkKeyC");
  		pinKey = properties.getProperty("pinKey");
  		macKey = properties.getProperty("macKey");
  		actionURL=properties.getProperty("actionURL");
  		saSMTPHost=properties.getProperty("saSMTPHost");
  		mailFrom=properties.getProperty("mailFrom");
  		activeTime=properties.getProperty("activeTime");
  		checkTime=properties.getProperty("checkTime");
  		fileDataPath=properties.getProperty("fileDataPath");
  		SMServerURL=properties.getProperty("SMServerURL");
  		commFileUploadPath=properties.getProperty("commFileUploadPath");
  		ireportURL=properties.getProperty("ireportURL");
  		certURL=properties.getProperty("certURL");
  		logoURL=properties.getProperty("logoURL");
  		domainURL=properties.getProperty("domainURL");
  		consumerEntityId=properties.getProperty("consumerEntityId");
  		
  	}

  	public static String getServerURL() {
  		if(serverURL == null){
  			loadConfig();
  		}
  		return serverURL;
  	}

  	public static String getMgmtServiceName() {
  		if(mgmtServiceName == null){
  			loadConfig();
  		}
  		return mgmtServiceName;
  	}

  	public static String getTxnServiceName() {
  		if(txnServiceName == null){
  			loadConfig();
  		}
  		return txnServiceName;
  	}

  	public static String getTmKey() {
  		if(tmKey == null){
  			loadConfig();
  		}
  		return tmKey;
  	}

  	public static String getWkKeyC() {
  		if(wkKeyC == null){
  			loadConfig();
  		}
  		return wkKeyC;
  	}

  	public static String getPinKey() {
  		if(pinKey == null){
  			loadConfig();
  		}
  		return pinKey;
  	}

  	public static String getMacKey() {
  		if(macKey == null){
  			loadConfig();
  		}
  		return macKey;
  	}
  	public static String getActionURL(){
  		if(actionURL==null){
  			loadConfig();
  		}
  		return actionURL;
  	}
  	public static String getSaSMTPHost(){
  		if(saSMTPHost==null){
  			loadConfig();
  		}
  		return saSMTPHost;
  	}
  	public static String getMailFrom(){
  		if(mailFrom==null){
  			loadConfig();
  		}
  		return mailFrom;
  	}
  	public static String getActiveTime(){
  		if(activeTime==null){
  			loadConfig();
  		}
  		return activeTime;
  	}
  	
  	public static String getCheckTime(){
  		if(checkTime==null){
  			loadConfig();	
  		}
  		return checkTime;
  	}
  	
  	public static String getFileDataPath(){
  		if(fileDataPath==null){
  			loadConfig();
  		}
  		return fileDataPath;
  	}
  	
  	public static String getSMServerURL(){
  		if(SMServerURL==null){
  			loadConfig();	
  		}
  		return SMServerURL;
  	}
  	
  	public static String getCommFileUploadPath() {
  		if (commFileUploadPath == null){
  			loadConfig();
  		}
  		return commFileUploadPath;
  	}
  	public static String getIreportURL() {
  		if (ireportURL == null){
  			loadConfig();
  		}
		return ireportURL;
	}
  	public static String getCertURL() {
  		if (certURL == null){
  			loadConfig();
  		}
		return certURL;
	}
  	public static String getLogoURL() {
  		if (logoURL == null){
  			loadConfig();
  		}
		return logoURL;
	}
  	public static String getDomainURL() {
  		if (domainURL == null){
  			loadConfig();
  		}
		return domainURL;
	}
  	public static String getConsumerEntityId() {
  		if (consumerEntityId == null){
  			loadConfig();
  		}
		return consumerEntityId;
	}
}
