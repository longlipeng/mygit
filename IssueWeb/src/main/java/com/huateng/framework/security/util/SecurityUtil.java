/**
 * 
 */
package com.huateng.framework.security.util;

import javax.servlet.ServletContext;

import org.springframework.security.context.SecurityContextHolder;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.huateng.framework.security.model.User;

/**
 * @author Downpour
 * 
 */
public class SecurityUtil {

	/**
	 * Returns the current user
	 * 
	 * @return
	 */
	public static User getCurrentUser() {
		return (User) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
	}

	public Object getSpringBean(ServletContext context, String beanName) {
		return WebApplicationContextUtils.getWebApplicationContext(context)
				.getBean(beanName);
	}
}
