/**
 * Classname SpringGetBeanTest.java
 *
 * Version information
 *
 * Licensed Property to HuaTeng Data Co., Ltd.
 * 
 * (C) Copyright of HuaTeng Data Co., Ltd. 2010
 *     All Rights Reserved.
 *
 * Project Info: HuaTeng Data Internet Acquiring  Project
 * 
 * Modification History:
 * =============================================================================
 *		Author		Date		Description
 *   ------------ ---------- ---------------------------------------------------
 *		wpf		2013-1-7
 * =============================================================================
 */

package com.huateng.hstserver.vo;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import com.huateng.framework.util.MakeTestType;
import com.huateng.test.BaseTest;

/**
 * @author wpf
 * 
 */
@MakeTestType(testType = { "SpringFactoryBean" })
public class SpringFactoryBean extends BaseTest implements FactoryBean,
		InitializingBean {
	private Logger logger = Logger.getLogger(SpringFactoryBean.class);

	private SpringGetBean springGetBean;

	@Override
	public Object getObject() throws Exception {
		logger
				.debug("=========    SpringGetBeanTest #  getObject ============");
		return this.springGetBean;
	}

	@Override
	public Class<?> getObjectType() {
		logger
				.debug("=========    SpringGetBeanTest #  getObjectType ============");
		return springGetBean != null ? springGetBean.getClass()
				: SpringGetBean.class;
	}

	@Override
	public boolean isSingleton() {
		logger
				.debug("=========    SpringGetBeanTest #  isSingleton ============");
		return false;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		logger
				.debug("=========    SpringGetBeanTest #  afterPropertiesSet ============");
		springGetBean = new SpringGetBean();
	}

}
