package com.huateng.framework.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;



public class Mail {
	private static Logger logger = Logger.getLogger(Mail.class);
    private static String resetMobileMail;
    private static String registerMail;
    private static String resetCardPinMail;
    	
	public String getResetCardPinMail() {
			if(resetCardPinMail==null){
				resetCardPinMail=getMail("resetCardPinMail");
			}
			
			return resetCardPinMail;
		}
	public static String getMail(String fileName){
		StringBuffer strBuf=new StringBuffer("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp");	
		try {
			FileInputStream inputStream = new FileInputStream(fileName);
			InputStreamReader isr=new InputStreamReader(inputStream);
			BufferedReader br=new BufferedReader(isr);
			
			String temp=br.readLine();
			while(temp!=null){
				strBuf.append(temp).append("</br>");
				temp=br.readLine();
			}
			
			   strBuf.append("<center><h4 style=\'color:red\'>此邮件为系统自动发送，请勿回复!</h4></center>");
			
			
			strBuf.append("<div style=\'text-align: right;\'><h4 style=\'color:red\'>华夏通服务</h4></div>");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return strBuf.toString();
	}
	/**
	 * 获取邮件的最后标准格
	 * @return
	 */
	
	
	public static String getLastFormat(String text,String warmTips,String accorService){
	    StringBuffer strBuffer=new StringBuffer(text);
	    strBuffer.append("<center><h4 style=\'color:blue\'>").append(warmTips).append("</h4></center>")
	    .append("<center><h4 style=\'color:red\'>此邮件为系统自动发送，请勿回复</h4></center>")
	    .append("<div style=\'text-align: right;\'><h4 style=\'color:red\'>").append(accorService).append("</h4></div>");
	    return strBuffer.toString();
	}
	
	
	


	public  String getResetMobileMail() {
	    if(resetMobileMail==null){
		resetMobileMail= getMail("resetMobileMail");
	    }
	    
	    return resetMobileMail;
	}




	public  String getRegisterMail() {
	    if(registerMail==null){
		registerMail=getMail("registerMail");
	    }
	    return registerMail;
	}




	
}
