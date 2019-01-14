package com.huateng.test;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.NotTransactional;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.huateng.framework.util.MakeTestType;
/**
 * 测试基类 提供整体的前置后置方法 事务前后方法
 * @author wpf
 *
 */
// 使用junit4进行测试
@RunWith(SpringJUnit4ClassRunner.class)
// 加载配置文件
@ContextConfiguration(locations = { "/spring/applicationContext-test.xml" })
// 事务配置
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
// 启用事务
@Transactional
@MakeTestType(testType={"MakeTestType"})
public class BaseTest {

	/**
	 * 事务开始前
	 */
	@BeforeTransaction
	public void beforeTransaction() {
		// logic to verify the initial state before a transaction is started
	}

	/**
	 * 事务结束后
	 */
	@AfterTransaction
	public void afterTransaction() {
		// logic to verify the final state after transaction has rolled back
	}

	/**
	 * 无事务测试
	 */
	@NotTransactional
	public void notTransactional() {
		// logic which does not modify database state
	}

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
