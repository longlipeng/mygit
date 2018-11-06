package com.huateng.test;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.huateng.framework.util.MakeTestType;

/**
 * 测试基类 提供整体的前置后置方法 事务前后方法
 * 
 * @author wpf
 * 
 */
// 使用junit4进行测试
@RunWith(Suite.class)
// 加载配置文件
// @ContextConfiguration(locations = { "/spring/applicationContext-test.xml" })
@MakeTestType(testType = { "MakeTestType" })
public class BaseTest {

	/**
	 * 测试开始前
	 */
	@Before
	public void before() {
		// set up test data within the transaction
	}

	/**
	 * 测试结束时
	 */
	@After
	public void after() {
		// execute "tear down" logic within the transaction

	}

}
