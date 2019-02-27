package com.huateng.system.util;

import java.util.Enumeration;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.util.HtmlUtils;


/**
 * 该类继承HttpServletRequestWrapper，
 * 是HttpServletRequest的装饰类，
 * 用来改变HttpServletRequest的状态，
 * 从而达到对请求内容的过滤的功能
 */
public class XssFilterWrapper extends HttpServletRequestWrapper {
	
	public XssFilterWrapper(HttpServletRequest request) {
		super(request);
		// TODO Auto-generated constructor stub
	}
	
//	@Override
//    public String getHeader(String arg0) {
//        System.out.println("getHeader");
//        return processXss(super.getHeader(arg0));
//    }
//
//    @Override
//    public String getParameter(String arg0) {
//        System.out.println("getParameter");
//        return processXss(super.getParameter(arg0));
//    }

	/**
	 * 对数组参数进行特殊字符过滤
	 */
	@Override
	public String[] getParameterValues(String arg0){
		String[] Values = super.getParameterValues(arg0);
		String[] newValues = new String[Values.length];
		
		for (int i = 0; i < Values.length; i++) {
			newValues[i] = HtmlUtils.htmlEscape(Values[i]);//spring的HtmlUtils进行转义
//			newValues[i] = Values[i];
		}
		return newValues;
	}
	
	/**
     * 处理字符转义
     * @param value
     * @return
     */
//	private String  processXss(String value){
//        if(value == null || StringUtils.equals("", value)){
//            return value;
//        }
//        value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
//        return value;
//    }
	
	
//	public static void main(String[] args){
//		
//		String s = "<alert>(123)(*&^%$#@!)</alert>";
//		//<转义&lt;,>转义&gt;,^转义
//        s = StringEscapeUtils.escapeHtml(s);
//		
//		System.out.println(s);
//		
//		
//		String inj_str = "\" ) \' * % < > &";
//		String inj_stra[] = inj_str.split(" ");
//		
//		for (int i = 0; i < inj_stra.length; i++) {
//			if(s.indexOf(inj_stra[i]) >= 0){
//				//spring的HtmlUtils进行转义
////				s = HtmlUtils.htmlEscape(s);
//				s = StringEscapeUtils.escapeHtml(s);
//			}
//		}
//		
//		System.out.println(s.trim());
//	}
	
	
}
