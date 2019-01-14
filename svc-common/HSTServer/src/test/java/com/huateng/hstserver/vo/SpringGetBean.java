/**
 * Classname SpringGetBean.java
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

import com.huateng.framework.util.MakeTestType;

/**
 * @author wpf
 * 
 */
@MakeTestType(testType = { "SpringGetBean" })
public class SpringGetBean {
	private Logger logger = Logger.getLogger(SpringGetBean.class);

	public SpringGetBean() {
		logger.debug("确实走入SpringGetBean构造函数");
	}

	public String getName() {
		return "springGetBean";
	}
}
