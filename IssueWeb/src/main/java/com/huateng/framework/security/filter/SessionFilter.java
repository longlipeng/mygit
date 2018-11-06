package com.huateng.framework.security.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.ui.SpringSecurityFilter;

import com.huateng.framework.security.exception.AuthCodeValidationException;


/**
 *
 * @description
 * @author Shoru
 * @date 2009-8-21
 * @version 1.0.0
 * @since 1.0
 */
public class SessionFilter extends SpringSecurityFilter {

    /**
     * Context.
     */
   // ServletContext context;
    
	/* (non-Javadoc)
	 * @see org.springframework.security.ui.SpringSecurityFilter#getOrder()
	 */
	public int getOrder() {
		return 0;
	}

    /**
     * debug开关
     */
    static boolean debug = false;
    
    /**
     * 过滤birt请求，转发到对应的servlet，以绕过其他过滤器，e.g. struts2
     *
     * @description
     * @author Shoru
     * @date 2009-8-21
     * @version 1.0.0
     * @param request
     * @param response
     * @param fc
     * @throws IOException
     * @throws ServletException
     */
    public void doFilterHttp(HttpServletRequest request, HttpServletResponse response, FilterChain fc)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse rep = (HttpServletResponse) response;
        logger.info("this is sessionFilter");
        
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        if (request.getParameter("flag") == null){
	        if (httpRequest.getSession().getAttribute("userLoginMark") == null){
	        	logger.info("not logged in");
	        }else{
	            HttpSession session = request.getSession(false);
	            // 错误处理：输出错误消息、用户名显示、密码清除、重定向到登录页面
	            session.setAttribute("SPRING_SECURITY_LAST_EXCEPTION", new AuthCodeValidationException("请勿重复登录，请登出后再登录，或者打开新的浏览器窗口再登录！"));
	            String username = request.getParameter("j_username");
	            session.setAttribute(
	    				"SPRING_SECURITY_LAST_USERNAME", username);
	    		response.sendRedirect(request.getContextPath()+"/login.jsp?error=true");
	            return;
	        }
        } else {
        if((request.getParameter("flag").equals("true"))){
        	request.getSession().setAttribute("callCenter", true);
//        	request.getSession().setAttribute("SPRING_SECURITY_LAST_USERNAME", "he");
//        	request.getSession().setAttribute("j_username", "he");
//        	request.getSession().setAttribute("j_password", "a333333");
//        	Map map = request.getParameterMap();
//        	map.put("j_username", "he");
//        	map.put("j_password", "a333333");
//        	request.setAttribute("j_username", "he");
//        	request.setAttribute("j_password", "a333333");
        }
        }
       /* if(request.getRequestURI().endsWith("j_spring_security_check_ext")){
        	
        }*/
        // 将请求交给下一个过滤器
        fc.doFilter(request, response);
    }

}
