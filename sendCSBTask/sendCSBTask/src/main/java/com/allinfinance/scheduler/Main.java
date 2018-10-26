package com.allinfinance.scheduler;



import java.io.File;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import service.CopProPertiesUtil;




public class Main {
	private static Logger logger = Logger.getLogger(Main.class);
public static void main(String[] args) { 
	Properties sp = System.getProperties();		
	String home = sp.getProperty("IPOS_OPTS_QUAR");
	String logPF=home+"/log4j.properties";	
	File f = new File(logPF);
	if (!f.exists())
	{
		System.out.println("������ȷ����log4j");
		System.exit(0);
	}
	PropertyConfigurator.configureAndWatch(logPF, 6000);// log4j ���������ļ�
	CopProPertiesUtil.init();//���������ļ�
	logger.info("��ʼ������ʱ�����ļ�����");
	new ClassPathXmlApplicationContext("application-context.xml");
	logger.info("��ʱ�����ļ����������ɹ�!");
}

}
