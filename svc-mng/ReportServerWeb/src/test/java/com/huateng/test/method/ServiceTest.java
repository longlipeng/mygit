/**
 * Classname ServiceTest.java
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
 *		htd033		2012-11-28
 * =============================================================================
 */

package com.huateng.test.method;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.huateng.framework.util.MakeTestType;
import com.huateng.univer.utils.SystemInfoTest;

/**
 * @author wpf
 * 
 *         针对service测试
 */
@RunWith(Suite.class)
@Suite.SuiteClasses( { SystemInfoTest.class })
@MakeTestType(testType = { "MethodTest" })
public class ServiceTest {

}
