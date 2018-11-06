/**
 *
 */
package com.huateng.framework.util;

import javax.servlet.ServletContext;

import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author Downpour
 *
 */
public class SpringUtil {

    public static Object getSpringBean(ServletContext context, String beanName) {
        return WebApplicationContextUtils.getWebApplicationContext(context)
                .getBean(beanName);
    }
}
