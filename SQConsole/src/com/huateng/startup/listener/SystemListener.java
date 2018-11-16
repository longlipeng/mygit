/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-3-8       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2010 Huateng Software, Inc. All rights reserved.
 *
 *       This software is the confidential and proprietary information of
 *       Shanghai HUATENG Software Co., Ltd. ("Confidential Information").
 *       You shall not disclose such Confidential Information and shall use it
 *       only in accordance with the terms of the license agreement you entered
 *       into with Huateng.
 *
 * Warning:
 * =============================================================================
 *
 */
package com.huateng.startup.listener;

import java.io.IOException;
import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.dom4j.DocumentException;
import com.huateng.common.Constants;
import com.huateng.common.SysParamConstants;
import com.huateng.common.grid.GridConfigUtil;
import com.huateng.common.select.SelectOptionUnit;
import com.huateng.startup.init.MenuInfoUtil;
import com.huateng.system.util.SysParamUtil;
import com.huateng.system.util.SystemDictionaryUnit;

/**
 * Title: 系统服务监听
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-3-8
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class SystemListener implements ServletContextListener {
	
	private static Logger log = Logger.getLogger(SystemListener.class);
	public void contextDestroyed(ServletContextEvent contextEvent) {

	}
	
	/**
	 * 初始化系统资源
	 */
	public void contextInitialized(ServletContextEvent contextEvent) {
		ServletContext context = contextEvent.getServletContext();
		init(context);
	}
	
	/**
	 * 系统初始化
	 * @param context
	 */
	protected void init(ServletContext context) {
		log.info("系统启动时间");
		try {
			System.setProperty(SysParamConstants.PRODUCTION_MODE,
					SysParamUtil.getParam(SysParamConstants.PRODUCTION_MODE));
			//初始化上下文路径
			Constants.CONTEXTPATH = context.getRealPath("");
			//初始化日志
			initLog4J();
			//初始化菜单
			MenuInfoUtil.init();
			
			SystemDictionaryUnit.initSysDic(context);
			SelectOptionUnit.initSelectOptions(context);
			GridConfigUtil.initGirdConfig(context);
		} catch (DocumentException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}
	
	/**
	 * 初始化系统日志
	 */
	private void initLog4J() {
		String logConfigFile = "/log4j.properties";
		Properties properties = new Properties();
		try {
			properties.load(this.getClass().getResourceAsStream(logConfigFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		PropertyConfigurator.configure(properties);
	}
	
}
