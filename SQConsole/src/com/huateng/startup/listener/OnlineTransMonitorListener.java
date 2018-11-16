package com.huateng.startup.listener;

import java.util.Timer;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;
import com.huateng.common.Thread.MonitorTask;

public class OnlineTransMonitorListener implements ServletContextListener {
	private static Logger log = Logger.getLogger(SystemListener.class);
	private MonitorTask monitorTask;
	Timer timer = new Timer();

	public void contextInitialized(ServletContextEvent arg0) {
		log.info("联机交易监控任务开始....");
		String str = null;  
        if (str == null && monitorTask == null) {  // 上下文初始化时启动 socket 
        	monitorTask = new MonitorTask();
        	try {    
				Thread.sleep(10000);//等待10秒后tomcat服务启动、上下文生成，数据库连接可获取到，无需自己创建连接
        	}catch(Exception e){}  

    		try {//此处时间参数period通过系统参数获取
				timer.scheduleAtFixedRate(monitorTask, 1000, Integer.parseInt(monitorTask.getPeriod())*60*1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
	}

	public void contextDestroyed(ServletContextEvent arg0) {
		if (monitorTask != null ) {  
			timer.cancel();
		}  
	}
}
