package com.huateng.secure.util.log;

import org.apache.log4j.Logger;
/**
 * 
 * <p>
 * <strong>
 * This class name was LogWriter
 * </strong>
 * </p>
 * @author Lay
 * @date 2010-5-12 11:27:11
 * @version 1.0
 */
public class LogWriter  {
	
	private static Logger logger = Logger.getLogger("HSM Encrypt");
	public LogWriter(){}

	public static void info(String s){
		if(logger.isInfoEnabled()){
			logger.info(s);
		}
	}	

	public static void error(String s){
			logger.error(s);
	}	

	public static void debug(String s){
		if(logger.isDebugEnabled()){
			logger.debug(s);
		}
	}	
}
