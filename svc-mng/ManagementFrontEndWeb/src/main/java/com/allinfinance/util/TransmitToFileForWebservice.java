package com.allinfinance.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.namespace.QName;

import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.log4j.Logger;

import com.huateng.framework.util.Config;

public class TransmitToFileForWebservice {
	public static Logger logger = Logger.getLogger(TransmitToFileForWebservice.class);
	public static String toFileService(String str){
		String respMessage = null;
		try {
			logger.debug("转发往文件服务报文长度："+str.length());
//			logger.debug("文件服务器配置信息url【"+Config.getFileServiceUrl()+"】");
//			logger.debug("文件服务器配置信息namespace【"+Config.getFileServiceNamespace()+"】");
//			logger.debug("文件服务器配置信息function【"+Config.getfileServiceFunction()+"】");
			RPCServiceClient serviceClient = new RPCServiceClient();
			Options options = serviceClient.getOptions();
			EndpointReference targetEPR = new EndpointReference
					(Config.getImgFileWebserviceUrl());
			options.setTo(targetEPR);
			Object[] opAddEntryArgs = new Object[]{str};
			Class[] classes = new Class[]{String.class};
			QName opAddEntry = new QName(Config.getImgFileWebserviceNamespace(),Config.getImgFileWebserviceFunction());
			Object[] invokeBlocking = serviceClient.invokeBlocking(opAddEntry, opAddEntryArgs, classes);
			respMessage = (String) invokeBlocking[0];
			logger.debug("收到文件服务返回报文长度："+respMessage.length());
		} catch (AxisFault e) {
			logger.error("请求文件服务器错");
			e.printStackTrace();
		}
		
		
		return respMessage;
	}
	
	public static void main(String[] args) {
		TransmitToFileForWebservice  tt=new TransmitToFileForWebservice();
		String message2 = "<?xml version='1.0' encoding='UTF-8' ?><DATA><TXN_CODE>I002</TXN_CODE>"
				+ "<PATHF>/home/fileServer/Data/IDPictures/20161213/132624750305501_1.txt</PATHF>"
				+ "<PATHB>/home/fileServer/Data/IDPictures/20161213/132624750305501_2.txt</PATHB>"
				+"<IMGTYPE>01</IMGTYPE>"
				+ "<IDNO>132624750305501</IDNO></DATA>";
//		System.out.println(tt.toFileService(message2));
	}
	

}
