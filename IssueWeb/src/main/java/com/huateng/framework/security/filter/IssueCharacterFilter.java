package com.huateng.framework.security.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import com.allinfinance.batchImport.IDCardCheck;
import com.huateng.framework.action.BaseAction;
import com.huateng.framework.exception.BizServiceException;

public class IssueCharacterFilter implements Filter  {

	
	  
//	 public void init(FilterConfig filterConfig) throws ServletException {
//	 }
//	  
//	 public void destroy() {}
//	 
//	 public void doFilter(ServletRequest request, ServletResponse response,
//			 FilterChain chain) throws IOException, ServletException {
	  
//	 chain.doFilter(new XssHttpServletRequestWrapper( (HttpServletRequest)
//	  request), response); 
//		 XSSRequestWrapper  wrapper = new XSSRequestWrapper((HttpServletRequest) request);
		/* Enumeration params=request.getParameterNames();
		 String param = "";
		
		 while (params.hasMoreElements()) {
			 param = (String) params.nextElement();
//			 String[] values = request.getParameterValues(param);
			 System.out.println(request.getParameter(param));
			 IDCardCheck.isNumeric(param);
			 String p=EscapeUtil.stripXSS((String)request.getParameter(param));
			 
//			 Map map=request.getParameterMap();
//			 String[] pp={p};
//			 map.put(param, pp);
			 request.getParameter(param);
			 System.out.println(request.getParameter(param));
//			 for (int i = 0; i < values.length; i++) {
//				 String paramValue =null;
//				 paramValue= EscapeUtil.stripXSS(values[i]);
//				0.4624671777246799 
//			}
			 
			 
		 }*/
//	     chain.doFilter(wrapper, response);
//	 }
	

	private String[] characterParams = null;
	private boolean OK = true;
	private String encoding = null;

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void init(FilterConfig config) throws ServletException {
		if (config.getInitParameter("characterParams").length() < 1)
			OK = false;
		else
			this.characterParams = config.getInitParameter("characterParams")
					.split(",");

		// his.encoding = config.getInitParameter("encoding");
	}

	/**
	 * 此程序块主要用来解决参数带非法字符等过滤功能
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain arg2) throws IOException, ServletException {

		HttpServletRequest servletrequest = (HttpServletRequest) request;
		HttpServletResponse servletresponse = (HttpServletResponse) response;
		boolean status = false;
		servletresponse.setContentType("text/html");
		servletresponse.setCharacterEncoding("utf-8");
		servletrequest.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("UTF-8");
		java.util.Enumeration params = request.getParameterNames();
		String param = "";
		String paramValue = "";

		while (params.hasMoreElements()) {
			param = (String) params.nextElement();
			String[] values = request.getParameterValues(param);
			paramValue = "";
			if (OK) {// 过滤字符串为0个时 不对字符过滤
				for (int i = 0; i < values.length; i++)
					paramValue = paramValue + values[i];
				for (int i = 0; i < characterParams.length; i++)
					if (paramValue.indexOf(characterParams[i]) >= 0) {
						status = true;
						break;
					}
				if (status)
					break;
			}
		}
		// System.out.println(param+"="+paramValue+";");
		if (status) {
			
			PrintWriter out = servletresponse.getWriter();
//			out.print("<script language='javascript'>alert(\"您提交的相关表单数据字符含有非法字符。如\\/-\\//-=-<->-javascript:-script\");location.reload('"+ servletrequest.getRequestURL() + "');</script>");
			// return;
			out.print("<script language='javascript'>alert(\"您提交的相关表单数据字符含有非法字符。如\\/-<->-javascript:-script\");history.go(-1);this.window.close(); </script>");
			
		} else
			arg2.doFilter(request, response);

	}

}
