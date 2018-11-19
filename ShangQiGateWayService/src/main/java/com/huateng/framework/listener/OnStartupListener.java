package com.huateng.framework.listener;



/**
 * OnStartupListener.java - 2012-07-17
 *
 * Licensed Property to HuaTeng Data Co., Ltd.
 * 
 * (C) Copyright of HuaTeng Data Co., Ltd. 2010
 *     All Rights Reserved.
 *
 * Project Info: HuaTeng Data Internet Acquiring Project
 * 
 * Modification History:
 * =============================================================================
 *   Author         Date          Description
 *   ------------ ---------- ---------------------------------------------------
 *        
 * =============================================================================
 */




import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.huateng.hstserver.config.HSTProperties;




/**
 * Description: 配置文件初始化，用于全局配置文件的初始化
 * 因为应用启动时加载顺序为Listener > Filter > Servlet
 * 而类似MinaServerListener需要先初始化配置文件，考虑将全局配置文件放入Listener初始化
 *
 * (C) Copyright of HuaTeng Data Co., Ltd. 2012.
 *
 */
public class OnStartupListener implements ServletContextListener {
	
	// 记录日志
	final Logger logger = Logger.getLogger(OnStartupListener.class);

	public void contextDestroyed(ServletContextEvent arg0) {
	
	}

	public void contextInitialized(ServletContextEvent arg0) {
		//logger.info("配置文件初始化开始");
		logger.info("配置文件初始化开始");
		HSTProperties.setHSTproperties("HST.properties");
		//HSTProperties.reloadByExtConf();
		HSTProperties.reloadByLocalConf();
//		logger.info("domainId:"+HSTProperties.getString("DOMAN_ID"));
//		logger.info("配置文件初始化结束");	
//		logger.info("domainId:"+HSTProperties.getString("DOMAN_ID"));
		logger.info("ip:"+HSTProperties.getString("LISTEN_IP"));
		logger.info("port:"+HSTProperties.getString("LISTEN_PORT"));
		HSTProperties.baseDir="";
		logger.info("配置文件初始化结束");
	}




}
