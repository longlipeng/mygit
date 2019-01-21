package com.huateng.system.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class XssFilter extends HttpServletRequestWrapper {

	public XssFilter(HttpServletRequest request) {
		super(request);
		// TODO Auto-generated constructor stub
	}

	
}
