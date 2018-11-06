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

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;

import com.huateng.framework.util.MakeTestType;
import com.huateng.test.BaseTest;

/**
 * @author wpf
 * 
 */
@MakeTestType(testType = { "MethodTest" })
public class SpringFactoryBeanTest extends BaseTest{

	@Resource
	private SpringGetBean springGetBeanTest;
	

	@Test
	public void springBeanGetTest(){
		Assert.assertEquals("springGetBean",springGetBeanTest.getName());
	}
	
}
