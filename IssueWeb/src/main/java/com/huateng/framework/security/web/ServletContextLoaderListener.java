package com.huateng.framework.security.web;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.allinfinance.univer.system.role.dto.ResourceDTO;
import com.huateng.framework.security.service.SecurityService;

/**
 * 容器启动时初始化
 * 
 * @author liming.feng
 * 
 */
public class ServletContextLoaderListener implements ServletContextListener {

	/**
	 * 容器启动时
	 * 取得所有受保护的资源列表，存在context中，key值是urlAuthorities，供所有用户使用
	 */
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ServletContext servletContext = servletContextEvent.getServletContext();
		SecurityService securityManager = this
				.getSecurityManager(servletContext);

		List<ResourceDTO> urlAuthorities = securityManager
				.loadUrlAuthorities();
		servletContext.setAttribute("urlAuthorities", urlAuthorities);
	}

	/**
	 * 容器关闭时
	 * 清除context中的urlAuthorities
	 */
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		servletContextEvent.getServletContext().removeAttribute(
				"urlAuthorities");
	}

	/**
	 * 获得取数据的service
	 * @param servletContext
	 * @return
	 */
	protected SecurityService getSecurityManager(ServletContext servletContext) {
		return (SecurityService) WebApplicationContextUtils
				.getWebApplicationContext(servletContext).getBean(
						"securityService");
	}

}
