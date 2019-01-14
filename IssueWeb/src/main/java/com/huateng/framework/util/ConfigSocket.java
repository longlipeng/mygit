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

public class ConfigSocket {

	private static Logger logger = Logger.getLogger(ConfigSocket.class);
	private static String configFileName="accorSocket.properties";
	private static String socketIPPOSP;
	private static String socketPortPOSP;
	private static String socketIPACCT;
	private static String socketPortACCT;
	private static String socketTimeOut;
	private static String socketIPRecharge;
	private static String socketPortRecharge;
	private static String socketIPPublicService;
	private static String socketPortPublicService;
	private static String socketIPOldCard;
	private static String socketPortOldCard;
  	public ConfigSocket() {
  		
  	}

  	public static void loadConfig(){

  		Properties properties = new Properties();
  		try{
  			FileInputStream inputStream = new FileInputStream(configFileName);
/*  			InputStream inputStream = Config.class.getClassLoader().
                    getResourceAsStream(configFileName);*/
  			properties.load(inputStream);
  		}catch(Exception e){
  			logger.error("Open config file fail!" + e.toString());
  		}
  		socketIPPOSP = properties.getProperty("socketIPPOSP");
  		socketPortPOSP = properties.getProperty("socketPortPOSP");
  		socketIPACCT = properties.getProperty("socketIPACCT");
  		socketPortACCT = properties.getProperty("socketPortACCT");
  		socketTimeOut = properties.getProperty("socketTimeOut");
  		socketIPRecharge = properties.getProperty("socketIPRecharge");
  		socketPortRecharge = properties.getProperty("socketPortRecharge");
  		socketIPPublicService = properties.getProperty("socketIPPublicService");
  		socketPortPublicService = properties.getProperty("socketPortPublicService");
  		socketIPOldCard = properties.getProperty("socketIPOldCard");
  		socketPortOldCard = properties.getProperty("socketPortOldCard");
  	}

  	public static String getSocketIPPOSP() {
  		if(socketIPPOSP == null){
  			loadConfig();
  		}
  		return socketIPPOSP;
  	}

  	public static String getSocketPortPOSP() {
  		if(socketPortPOSP == null){
  			loadConfig();
  		}
  		return socketPortPOSP;
  	}
  	
  	public static String getSocketIPACCT() {
  		if(socketIPACCT == null){
  			loadConfig();
  		}
  		return socketIPACCT;
  	}

  	public static String getSocketPortACCT() {
  		if(socketPortACCT == null){
  			loadConfig();
  		}
  		return socketPortACCT;
  	}

  	public static String getSocketTimeOut() {
  		if(socketTimeOut == null){
  			loadConfig();
  		}
  		return socketTimeOut;
  	}

  	public static String getSocketIPRecharge() {
  		if(socketIPRecharge == null){
  			loadConfig();
  		}
  		return socketIPRecharge;
  	}

  	public static String getSocketPortRecharge() {
  		if(socketPortRecharge == null){
  			loadConfig();
  		}
  		return socketPortRecharge;
  	}
  	
  	public static String getSocketIPPublicService() {
  		if(socketIPPublicService == null){
  			loadConfig();
  		}
  		return socketIPPublicService;
  	}

  	public static String getSocketPortPublicService() {
  		if(socketPortPublicService == null){
  			loadConfig();
  		}
  		return socketPortPublicService;
  	}

	public static String getSocketIPOldCard() {
		if(socketIPOldCard==null){
			loadConfig();
		}
		return socketIPOldCard;
	}

	public static String getSocketPortOldCard() {
		if(socketPortOldCard==null){
			loadConfig();
		}
		return socketPortOldCard;
	}
  	
}
