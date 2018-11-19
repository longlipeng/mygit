package com.huateng.framework.util;

import org.apache.log4j.Logger;
import java.lang.reflect.*;

public class Log {
	static Logger logger = Logger.getLogger(Log.class);
	
	public static void printObject(Object obj){
		StringBuffer sb = new StringBuffer();
		String sep = "\r\n";
		try{
			Class objClass = obj.getClass();
			sb.append(objClass.getName()).append(sep);
			Field[] fields = objClass.getDeclaredFields();
			for (Field field : fields){
				field.setAccessible(true);
				sb.append("[").append(field.getName()).append(" = ")
				  .append(field.get(obj)).append("]").append(sep);
			}
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		logger.debug(sb.toString());
	}
	
	public static void printHexLog(byte[] bt)
    {
        for (int i = 0; i < bt.length; i++)
        {
             int hex = (int)bt[i] & 0xff;
             logger.info(Integer.toHexString(hex) + " ");
        }
        logger.info("  length = "+bt.length);
    }
}
