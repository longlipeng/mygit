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

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import com.huateng.common.Thread.Server;

/**
 * Title:6.0查询交易流水服务监听
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-3-8
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author zou
 * 
 * @version 1.0
 */
public class MyServerListener implements ServletContextListener {
	
	 private Server myServer;

	public void contextInitialized(ServletContextEvent event) {
	  String str = null;  
         if (str == null && myServer == null) {  
        	 myServer = new Server();  
        	 myServer.start(); // servlet 上下文初始化时启动 socket   
         }  
	
  }

	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		if (myServer != null ) {  
			myServer.serverStop();
		   }  

	}
}