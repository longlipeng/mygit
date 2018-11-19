/**
 * Classname DatabaseCommonTest.java
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
import com.huateng.univer.method.sqlcharsettest.SqlCharsetTest;

/**
 * @author htd033
 * 
 *         SqlCharsetTest 针对数据长度测试
 */
@RunWith(Suite.class)
@Suite.SuiteClasses( { SqlCharsetTest.class })
@MakeTestType(testType = { "MethodTest" })
public class DatabaseCommonTest {

}
