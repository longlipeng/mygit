/**
 * Classname SpringIocTest.java
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
 *		htd033		2013-1-7
 * =============================================================================
 */

package com.huateng.test.method.spring;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.huateng.framework.util.MakeTestType;
import com.huateng.hstserver.vo.SpringFactoryBeanTest;

/**
 * @author wpf
 * 
 *         针对spring测试
 */
@RunWith(Suite.class)
@Suite.SuiteClasses( { SpringFactoryBeanTest.class })
@MakeTestType(testType = { "MethodTest" })
public class SpringIocTest {

}
