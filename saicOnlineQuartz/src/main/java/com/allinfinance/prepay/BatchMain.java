package com.allinfinance.prepay;

import java.io.File;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.allinfinance.prepay.utils.Config;

public class BatchMain
{

	private ApplicationContext ctx;
	
	private Logger logger = Logger.getLogger(getClass());
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		new BatchMain();
	}
	
	public BatchMain()
	{
		Properties sp = System.getProperties();
		String iposHome = sp.getProperty("quartz_home");
//		System.out.println("IPOS_HOME: " + iposHome);
		String logPF;
		if(!StringUtils.isEmpty(iposHome))
		{
			logPF = iposHome + "/log4j.properties";
			System.out.println("log4j property file: " + logPF);	
			File f = new File(logPF);
			if (!f.exists())
			{
				System.out.println("请先正确配置log4j");
				System.exit(0);
			}
			
			PropertyConfigurator.configureAndWatch(logPF, 6000);
		}
		
		logger.info("开始启动通联saicOnlineQuartz");
		ctx = new ClassPathXmlApplicationContext("application-context.xml");
		logger.info("saicOnlineQuartz启动成功");
		logger.info("quartz_home: " + iposHome);
		logger.info("CoreIp:"+Config.getCoreIp()+",CorePort:"+
		Config.getCorePort()+",UserId:"+
		Config.getUserId()+",checkIp:"+Config.getCheckedIp()+",checkPort:"+Config.getCheckedPort()+
		",orgId:"+Config.getOrgId()+",checkPort:"+Config.getTransCode()+",checkPort:"+Config.getProvider());
//		SyncP001Processor p=(SyncP001Processor) ctx.getBean("syncP001Processor");
//		BasicMessage b = null;
//		try {
//			p.process(b);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
}
