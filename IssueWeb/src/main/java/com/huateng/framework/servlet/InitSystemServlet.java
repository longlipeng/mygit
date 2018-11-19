package com.huateng.framework.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.apache.log4j.Logger;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.huateng.framework.webservice.service.SystemInfoService;

public class InitSystemServlet extends HttpServlet {
	
	private static final Logger logger = Logger.getLogger(InitSystemServlet.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 7383330395840866359L;

	@Override
	public void init() throws ServletException {

		try {
			
			SystemInfoService systemInfoService = (SystemInfoService) WebApplicationContextUtils
					.getWebApplicationContext(this.getServletContext())
					.getBean("dictInfoService");
			systemInfoService.initSystemInfo();
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
	}
}
