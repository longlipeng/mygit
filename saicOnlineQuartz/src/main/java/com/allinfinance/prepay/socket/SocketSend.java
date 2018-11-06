package com.allinfinance.prepay.socket;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.allinfinance.prepay.utils.CommonFunction;
import com.allinfinance.prepay.utils.Config;
import com.allinfinance.prepay.utils.Constants;


public class SocketSend {
	private static Logger log = Logger.getLogger(SocketSend.class);
	
	
	
	//�����ķ���
	public static String SendToCore(String acceptContentFormOut) {
		String sendXmlToSd = acceptContentFormOut.toString();
		String sendXmlByteLengthToSd;
		SocketConnect s =null;
		try {
			sendXmlByteLengthToSd = CommonFunction.fillString(
					sendXmlToSd.getBytes(Constants.XMLENCODING).length + "",
					'0', 4, false);
		
		String sendContentToSd = sendXmlByteLengthToSd + sendXmlToSd;
		log.info("����:" + sendXmlByteLengthToSd + ",���͵ı���:"
				+ sendContentToSd);

		// socketͨ��
		String ip =  Config.getCoreIp() ;
		String port=Config.getCorePort();
		s = new SocketConnect(sendContentToSd,port,ip);
		
		s.run(Constants.XMLENCODING);
		
		// �����յ�Ӧ��ı���
		String acceptXmlFormSd = s.getRsp().trim();
		    String acceptXmlLengthFormSd = acceptXmlFormSd.substring(0, 4);
		String acceptContentFormSd = acceptXmlFormSd.substring(4);
		log.info("����:" + acceptXmlLengthFormSd + ",���յ���Ӧ�ı���:"
				+ acceptContentFormSd);
		s.close();

		// ����յ����صı����а�����XML����ͷ,��Ҫ��XML����ͷ�޳�
		if (acceptContentFormSd.indexOf(Constants.XMLBAOWENHEADER) == 0) {
				acceptContentFormSd = acceptContentFormSd
				.substring(Constants.XMLBAOWENHEADER.length());
				log.info("���͸��ⲿ�ı���:" + acceptContentFormSd);
		}
		return acceptContentFormSd;
		}catch (Exception e) {
			log.error("webservice����,��װ,ͨ���쳣:" + e.getMessage());
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
