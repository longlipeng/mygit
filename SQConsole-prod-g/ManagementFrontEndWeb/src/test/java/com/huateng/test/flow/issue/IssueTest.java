/**
 * Classname IssueTest.java
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
 *		htd033		2012-11-27
 * =============================================================================
 */

package com.huateng.test.flow.issue;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.huateng.framework.util.MakeTestType;
import com.huateng.univer.system.user.biz.service.impl.UserServiceImplTest;

/**
 * @author wpf
 * 
 *         IssueTest: 发行机构流程测试
 * 
 *         1.UserServiceImplTest :
 * 
 */
@RunWith(Suite.class)
@Suite.SuiteClasses( { UserServiceImplTest.class })
@MakeTestType(testType = { "TestAll" })
public class IssueTest {
}
