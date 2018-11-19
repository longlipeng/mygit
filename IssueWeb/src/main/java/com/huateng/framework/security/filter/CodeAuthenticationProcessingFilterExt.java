package com.huateng.framework.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.ui.SpringSecurityFilter;

import com.huateng.framework.security.exception.AuthCodeValidationException;

/**
 * 登录页面动态验证码的check过滤器
 * 
 * @author liming.feng
 * 
 */
public class CodeAuthenticationProcessingFilterExt extends SpringSecurityFilter {
	
	/**
	 * 可以在配置文件中设置拦截的url
	 */
	private String filterProcessesUrlExt;

	/* (non-Javadoc)
	 * @see org.springframework.security.ui.SpringSecurityFilter#doFilterHttp(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	protected void doFilterHttp(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		if (requiresAuthentication(request,response)) {
		
			Object code = request.getSession().getAttribute("randCode");
			String checkCode = request.getParameter("checkCode");
			
			//TODO 2014-12-08暂时关闭校验码检查
			// 校验验证码不通过
			/*if (code == null || !code.equals(checkCode)) {
				try {
		            HttpSession session = request.getSession(false);
		            
		            // 错误处理：输出错误消息、用户名显示、密码清除、重定向到登录页面
		            session.setAttribute("SPRING_SECURITY_LAST_EXCEPTION", new AuthCodeValidationException("验证码输入错误！"));
		            String username = request.getParameter("j_username");
		            session.setAttribute(
							"SPRING_SECURITY_LAST_USERNAME", username);
					response.sendRedirect(request.getContextPath()+"/login.jsp?error=true");
		        }
		        catch (Exception ignored) {
		        }
		        return;
			}*/
		}
		chain.doFilter(request, response);
	}
	
	/**
	 * @param request
	 * @param response
	 * @return
	 */
	protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String uri = request.getRequestURI();
        int pathParamIndex = uri.indexOf(';');

        if (pathParamIndex > 0) {
            uri = uri.substring(0, pathParamIndex);
        }

        if ("".equals(request.getContextPath())) {
            return uri.endsWith(filterProcessesUrlExt);
        }

        return uri.endsWith(request.getContextPath() + filterProcessesUrlExt);
    }

	/* (non-Javadoc)
	 * @see org.springframework.security.ui.SpringSecurityFilter#getOrder()
	 */
	public int getOrder() {
		return 0;
	}


	public String getFilterProcessesUrlExt() {
		return filterProcessesUrlExt;
	}

	public void setFilterProcessesUrlExt(String filterProcessesUrlExt) {
		this.filterProcessesUrlExt = filterProcessesUrlExt;
	}


}
