package com.huateng.system.util;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.springframework.web.util.HtmlUtils;

/**
 * Title:防止注入攻击拦截
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-9-16
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @version 1.0
 */
public class XssFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		
//		injectInput(request);
		
//		if(!inje.equals("")){
//			request.getRequestDispatcher(request.getContextPath()).forward(request, response);
//			response.sendRedirect(request.getContextPath());
//			arg2.doFilter(request, response);
//		}else{
//			arg2.doFilter(request, response);
//		}
		
		//使用装饰类
//        XssFilterWrapper xssFilterWrapper = new XssFilterWrapper(request);
////		request = new XssFilterWrapper(request);
//        
//        Enumeration enu = request.getParameterNames();
//        //当此枚举对象至少还包含一个可提供的元素时，才返回 true；否则返回 false。
//        while(enu.hasMoreElements()){
//        	String enuName = (String) enu.nextElement();
//        	String[] xss = xssFilterWrapper.getParameterValues(enuName);
//        }
		
        arg2.doFilter(new XssFilterWrapper(request), response);
	}
	
	
	/**
     * 判断request中是否含有注入攻击字符
     * @param request
     * @return
     */
	@SuppressWarnings("rawtypes")
	public String injectInput(HttpServletRequest request){
		
		Enumeration e = request.getParameterNames();
		
		String inj = "";
		while(e.hasMoreElements()){
			String attributeName = (String) e.nextElement();
			
			String[] attributeValues = request.getParameterValues(attributeName);
			for (int i = 0; i < attributeValues.length; i++) {
				
				if(attributeValues[i] == null || attributeValues[i].equals(""))
					continue;
				inj = injectChar(attributeValues[i]);
				
//				if(!inj.equals("")){
//					return inj;
//				}
				
			}
			
		}
		return inj;
	}
	
	/**
	 * 判断字符串中是否含有注入攻击字符
	 * @param attributeValues
	 * @return
	 */
	public String injectChar(String attributeValues){
		
		String inj_str = "\" ) \' * % < > &";
		String inj_stra[] = inj_str.split(" ");
		
		for (int i = 0; i < inj_stra.length; i++) {
			if(attributeValues.indexOf(inj_stra[i]) >= 0){
				//spring的HtmlUtils进行转义
				return HtmlUtils.htmlEscape(attributeValues);
			}
		}
		return "";
	}
	

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	
	
}
