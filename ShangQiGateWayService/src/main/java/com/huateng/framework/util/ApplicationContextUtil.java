package com.huateng.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 获取bean
 * 
 * @author sunchao
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ApplicationContextUtil implements ApplicationContextAware {

    private static final Logger log = LoggerFactory.getLogger(ApplicationContextUtil.class);
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static Object getBean(String beanName) {
        Object obj = applicationContext.getBean(beanName);
        if (obj == null) {
            log.error("bean named: " + beanName + " not found");
        }
        return obj;
    }

}
