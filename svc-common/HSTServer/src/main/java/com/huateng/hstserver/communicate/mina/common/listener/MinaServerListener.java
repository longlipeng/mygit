package com.huateng.hstserver.communicate.mina.common.listener;

/**
 * MinaServerListener.java - 2012-07-11
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
import java.io.IOException;
import java.net.InetSocketAddress;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.quartz.impl.StdScheduler;
import org.apache.log4j.Logger;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.huateng.hstserver.communicate.mina.comm.common.connection.ConnectionPool;
import com.huateng.hstserver.config.HSTProperties;

/**
 * Description: MINA服务器启停监听器，用于在服务器启停时启停MINA监听
 *
 * (C) Copyright of HuaTeng Data Co., Ltd. 2012.
 *
 */
public class MinaServerListener implements ServletContextListener {
	
	// 记录日志
	final Logger logger = Logger.getLogger(MinaServerListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		logger.info("开始停止心跳请求");
		StdScheduler stdScheduler = (StdScheduler)WebApplicationContextUtils.getWebApplicationContext(arg0.getServletContext()).getBean("quartzScheduler");
		stdScheduler.shutdown(false);
		logger.info("停止心跳请求结束");
		
		logger.info("开始关闭MINA链路");
		ConnectionPool.getInstance().destroy();
		logger.info("关闭MINA链路完成");	
		
		logger.info("开始停止MINA服务器监听");
		NioSocketAcceptor acceptor = (NioSocketAcceptor)WebApplicationContextUtils.getWebApplicationContext(arg0.getServletContext()).getBean("ioAcceptor");
		acceptor.setCloseOnDeactivation(true);
		acceptor.unbind();
		logger.info("停止MINA服务器监听完成");	
	}
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		logger.info("开始启动MINA服务器监听");
		
		String ip = HSTProperties.getString("LISTEN_IP");
		int port = Integer.parseInt(HSTProperties.getString("LISTEN_PORT").trim());
		InetSocketAddress localAddress = new InetSocketAddress(ip, port);	
		NioSocketAcceptor acceptor = (NioSocketAcceptor)WebApplicationContextUtils.getWebApplicationContext(arg0.getServletContext()).getBean("ioAcceptor");
		try {
			acceptor.setDefaultLocalAddress(localAddress);			
			logger.info("LISTEN PORT:"+acceptor.getDefaultLocalAddress().getPort());			
			acceptor.bind();
		} catch (IOException e) {			
			logger.error(e.getMessage());
		}
		logger.info("启动MINA服务器监听完成");	
		
	}




}
