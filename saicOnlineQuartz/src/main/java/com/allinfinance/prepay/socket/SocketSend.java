package com.allinfinance.prepay.socket;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.allinfinance.prepay.utils.CommonFunction;
import com.allinfinance.prepay.utils.Config;
import com.allinfinance.prepay.utils.Constants;


public class SocketSend {
	private static Logger log = Logger.getLogger(SocketSend.class);
	
	
	
	//往核心发送
	public static String SendToCore(String acceptContentFormOut) {
		String sendXmlToSd = acceptContentFormOut.toString();
		String sendXmlByteLengthToSd;
		SocketConnect s =null;
		try {
			sendXmlByteLengthToSd = CommonFunction.fillString(
					sendXmlToSd.getBytes(Constants.XMLENCODING).length + "",
					'0', 4, false);
		
		String sendContentToSd = sendXmlByteLengthToSd + sendXmlToSd;
		log.info("长度:" + sendXmlByteLengthToSd + ",发送的报文:"
				+ sendContentToSd);

		// socket通信
		String ip =  Config.getCoreIp() ;
		String port=Config.getCorePort();
		s = new SocketConnect(sendContentToSd,port,ip);
		
		s.run(Constants.XMLENCODING);
		
		// 接收收单应答的报文
		String acceptXmlFormSd = s.getRsp().trim();
		    String acceptXmlLengthFormSd = acceptXmlFormSd.substring(0, 4);
		String acceptContentFormSd = acceptXmlFormSd.substring(4);
		log.info("长度:" + acceptXmlLengthFormSd + ",接收到响应的报文:"
				+ acceptContentFormSd);
		s.close();

		// 如果收单返回的报文中包含有XML报文头,需要将XML报文头剔除
		if (acceptContentFormSd.indexOf(Constants.XMLBAOWENHEADER) == 0) {
				acceptContentFormSd = acceptContentFormSd
				.substring(Constants.XMLBAOWENHEADER.length());
				log.info("发送给外部的报文:" + acceptContentFormSd);
		}
		return acceptContentFormSd;
		}catch (Exception e) {
			log.error("webservice解析,组装,通信异常:" + e.getMessage());
			if(s!=null){
				try {
					s.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
			return Constants.SYSERR;
		}
		
		
	}

}
