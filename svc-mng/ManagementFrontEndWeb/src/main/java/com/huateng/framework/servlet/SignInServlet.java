package com.huateng.framework.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.huateng.hstserver.constants.HSTConstants;
import com.huateng.hstserver.exception.BizServiceException;
import com.huateng.hstserver.gatewayService.Java2CCommonServiceImpl;
import com.huateng.hstserver.gatewayService.Java2STLBusinessServiceImpl;
import com.huateng.hstserver.model.StlPackageDTO;

public class SignInServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(SignInServlet.class);
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		 logger.info("#################### CHECK IN ####################");
		Java2CCommonServiceImpl java2CBusinessService =(Java2CCommonServiceImpl)WebApplicationContextUtils.getWebApplicationContext(this.getServletContext()).getBean("java2CCommonService");
		//账户系统签到
		try {
			java2CBusinessService.signIn(HSTConstants.SYS_ACC);
		} catch (BizServiceException e) {
			this.logger.error(e.getMessage());
		}
		//交易系统签到
		try {
			java2CBusinessService.signIn(HSTConstants.SYS_TXN);
		} catch (BizServiceException e) {
			this.logger.error(e.getMessage());
		}
		//触发结算系统发起连接
		Java2STLBusinessServiceImpl java2STLBusinessService =(Java2STLBusinessServiceImpl)WebApplicationContextUtils.getWebApplicationContext(this.getServletContext()).getBean("java2STLBusinessService");
		StlPackageDTO stlPackageDTO = new StlPackageDTO();
		try {
			java2STLBusinessService.querySettle(stlPackageDTO);
		} catch (BizServiceException e) {
			this.logger.error(e.getMessage());
		}
		
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		doGet(req, resp);
	}

}
