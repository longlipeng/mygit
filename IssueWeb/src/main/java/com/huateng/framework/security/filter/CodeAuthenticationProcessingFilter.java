package com.huateng.framework.security.filter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.ui.SpringSecurityFilter;

import com.huateng.framework.security.exception.AuthCodeValidationException;
import com.huateng.framework.util.ConfigMessage;
import com.huateng.framework.util.VerificationCodeUtil;

/**
 * 登录页面动态验证码的check过滤器
 * 
 * @author liming.feng
 * 
 */
public class CodeAuthenticationProcessingFilter extends SpringSecurityFilter {
	
	/**
	 * 可以在配置文件中设置拦截的url
	 */
	private String defaultFilterProcessesUrl;

	/* (non-Javadoc)
	 * @see org.springframework.security.ui.SpringSecurityFilter#doFilterHttp(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	protected void doFilterHttp(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		 HttpSession session = request.getSession(false);
		if (requiresAuthentication(request,response)) {
		
			Object code = request.getSession().getAttribute("randCode");
			String checkCode = request.getParameter("checkCode");
			
			//TODO 2014-12-08暂时关闭校验码检查
			// 校验验证码不通过
			if (code == null || !code.equals(checkCode)) {
				try {
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
			}
			if("open".equals(ConfigMessage.getOn_off())){
				 String username = request.getParameter("j_username");
				try {
					Object verificationCode=request.getSession().getAttribute("validCode");
					String checkVerificationCode = request.getParameter("verificationCode");
					Object verificationCodeTime = request.getSession().getAttribute("validCodeTime");
					String validCodeUsername =(String) request.getSession().getAttribute("validCodeUsername");
					if(!username.equals(validCodeUsername)){
						//短信验证码失效
						// 错误处理：输出错误消息、用户名显示、密码清除、重定向到登录页面
			            session.setAttribute("SPRING_SECURITY_LAST_EXCEPTION", new AuthCodeValidationException("短信验证码失效！"));
			            session.setAttribute(
								"SPRING_SECURITY_LAST_USERNAME", username);
						response.sendRedirect(request.getContextPath()+"/login.jsp?error=true");
						 return;
					}
					if(verificationCode==null||verificationCodeTime==null){
						//短信验证码失效
						// 错误处理：输出错误消息、用户名显示、密码清除、重定向到登录页面
			            session.setAttribute("SPRING_SECURITY_LAST_EXCEPTION", new AuthCodeValidationException("短信验证码失效！"));
			            session.setAttribute(
								"SPRING_SECURITY_LAST_USERNAME", username);
						response.sendRedirect(request.getContextPath()+"/login.jsp?error=true");
						 return;
					}else{
						Date nowDate= new Date();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");       //结束时间
						Date validCodeTime = sdf.parse(verificationCodeTime.toString());
						VerificationCodeUtil timeUtil=new VerificationCodeUtil();
						long timeSum=timeUtil.getDatePoor(validCodeTime, nowDate);
						if(timeSum>2){
							//短信验证码失效
							  session.setAttribute("SPRING_SECURITY_LAST_EXCEPTION", new AuthCodeValidationException("短信验证码失效！"));
					            session.setAttribute(
										"SPRING_SECURITY_LAST_USERNAME", username);
								response.sendRedirect(request.getContextPath()+"/login.jsp?error=true");
								 return;
						}
						if(!verificationCode.toString().equals(checkVerificationCode)){
							//短信验证码错误
							  session.setAttribute("SPRING_SECURITY_LAST_EXCEPTION", new AuthCodeValidationException("短信验证码错误！"));
					          session.setAttribute(
										"SPRING_SECURITY_LAST_USERNAME", username);
							  response.sendRedirect(request.getContextPath()+"/login.jsp?error=true");
							  return;
						}
						
					}
				} catch (Exception e) {
					// TODO: handle exception
					  session.setAttribute("SPRING_SECURITY_LAST_EXCEPTION", new AuthCodeValidationException("系统异常！"));
			            session.setAttribute(
								"SPRING_SECURITY_LAST_USERNAME", username);
						response.sendRedirect(request.getContextPath()+"/login.jsp?error=true");
						return;
				}
			}
			
			
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
            return uri.endsWith(defaultFilterProcessesUrl);
        }

        return uri.endsWith(request.getContextPath() + defaultFilterProcessesUrl);
    }

	/* (non-Javadoc)
	 * @see org.springframework.security.ui.SpringSecurityFilter#getOrder()
	 */
	public int getOrder() {
		return 0;
	}

	public String getDefaultFilterProcessesUrl() {
		return defaultFilterProcessesUrl;
	}

	public void setDefaultFilterProcessesUrl(String defaultFilterProcessesUrl) {
		this.defaultFilterProcessesUrl = defaultFilterProcessesUrl;
	}

}
