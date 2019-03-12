package com;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * Zuul提供了过滤功能,继承ZuulFilter类即可对请求先进行筛选和过滤之后再路由到具体服务
 * @author 
 *
 */
public class DemoFilter extends ZuulFilter {

	private static Logger logger = LoggerFactory.getLogger(DemoFilter.class);
	
	/**
	 * 这里可以写逻辑判断，是否要过滤，true为永远过滤
	 */
	public boolean shouldFilter() {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * 过滤的优先级，数字越大，优先级越低
	 */
	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 返回一个字符串代表过滤器的类型
	 * pre：路由之前
	 * routing：路由之时
	 * post： 路由之后
	 * error：发送错误调用
	 */
	@Override
	public String filterType() {
		// TODO Auto-generated method stub
		return "pre";
	}

	
	/**
	 * 过滤器的具体逻辑
	 */
	public Object run() {
		// TODO Auto-generated method stub
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		String s = String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString());
		logger.info(s);
		return null;
	}
	
	
}
