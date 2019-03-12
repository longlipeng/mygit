package com;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * Zuul提供了过滤功能,继承ZuulFilter类即可对请求先进行筛选和过滤之后再路由到具体服务
 * 在 SpringCloud 体系中，Zuul 担任着网关的角色，对发送到服务端的请求进行一些预处理，
 * 比如安全验证、动态路由、负载分配等
 * @author 
 *
 */
public class DemoFilter extends ZuulFilter {

	private static Logger logger = LoggerFactory.getLogger(DemoFilter.class);
	
	/**
	 * 这里可以写逻辑判断，是否要过滤，true为需要过滤
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
		
		logger.info("--->>> TokenFilter {},{}", request.getMethod(), request.getRequestURL().toString());
		 
        String token = request.getParameter("token");//获取请求的参数
 
        //请求中含有token便让请求继续往下走，如果请求不带token就直接返回并给出提示
        if (StringUtils.isNotBlank(token)) {
            ctx.setSendZuulResponse(true); //对请求进行路由
            ctx.setResponseStatusCode(200);
            ctx.set("isSuccess", true);
            return null;
        } else {
            ctx.setSendZuulResponse(false); //不对其进行路由
            ctx.setResponseStatusCode(400);
            ctx.setResponseBody("token is empty");
            ctx.set("isSuccess", false);
            return null;
        }

//		return null;
	}
	
	
}
