package com.allinfinance.prepay;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.allinfinance.prepay.message.BasicMessage;
import com.allinfinance.prepay.processor.ipos.SyncP001Processor;
import com.allinfinance.prepay.utils.Config;

public class IposMain
{

	private ApplicationContext ctx;
	
	private Logger logger = Logger.getLogger(getClass());
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		new IposMain();
	}
	
	public IposMain()
	{
		Properties sp = System.getProperties();
		String iposHome = sp.getProperty("IPOS_HOME");
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
		
		logger.info("开始启动通联saicSyncOnline");
		ctx = new ClassPathXmlApplicationContext("application-context.xml");
		RabbitAdmin admin = ctx.getBean(RabbitAdmin.class);
		Map<String, Queue> queues = ctx.getBeansOfType(Queue.class);
		//初始化，清理所有队列
		logger.info("开始清理MQ");
		for(Entry<String, Queue> entry :queues.entrySet())
			admin.purgeQueue(entry.getValue().getName(), true);
//		SimpleMessageListenerContainer container = ctx.getBean(SimpleMessageListenerContainer.class);
//		container.start();
		logger.info("saicSyncOnline启动成功");
		logger.info("IPOS_HOME: " + iposHome);
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
