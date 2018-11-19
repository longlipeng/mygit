package com.huateng.framework.struts2;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/***
 * 
 * @author wpf
 * 
 */
public class CommonExceptionThrowsInterceptor extends AbstractInterceptor {

	private static final Logger logger = Logger.getLogger(CommonExceptionThrowsInterceptor.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String result;
		try {
			result = invocation.invoke();
		} catch (Exception e) {
			try {
				ActionSupport action = (ActionSupport) invocation.getAction();
				action.addActionError("系统异常,请联系管理员");
			} catch (Exception ex) {
				logger.error(e.getMessage(), e);
				logger.error(ex.getMessage(), ex);
				throw e;
			}
			logger.error(e.getMessage(), e);
			throw e;
		}
		return result;
	}
}
