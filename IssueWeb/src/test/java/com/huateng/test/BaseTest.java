package com.huateng.test;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.StrutsSpringTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.test.annotation.NotTransactional;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import com.huateng.framework.security.model.User;
import com.huateng.framework.util.ReDTOs;
import com.huateng.framework.webservice.ManageService;
/**
 * 测试基类 提供整体的前置后置方法 事务前后方法
 * @author wpf
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 使用junit4进行测试
@ContextConfiguration(locations = { "/spring/applicationContext-test.xml" })
// 加载配置文件
public class BaseTest extends StrutsSpringTestCase {
	Logger logger = Logger.getLogger(BaseTest.class);
	/**
	 * 这个感觉有点难受.上面的spring需要配置.这个又是struts2的配置(居然要配spring的配置文件).
	 */
	protected String getContextLocations() {
		return "/spring/applicationContext-test.xml";
	}

	@Resource
	protected ManageService manageService;

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
		/**
		 * FIXME 这是让我最纠结的地方. struts2用的是testcase的setup 但死活不会在测试前执行.
		 * 
		 * 由此我想到未来 可能发生的问题.是不是有可能.after也不是自动调用的.
		 */
		try {
			super.setUp();
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}

		// spring security 加入用户
		User user = (User) ReDTOs.getDTO(User.class, false);
		user.setUserId("10000");
		user.setUserName("testUser");
		user.setUserName("testPass");

		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken(user, "testPass"));
	}

	/**
	 * 测试结束时
	 */
	@After
	public void after() {
		// execute "tear down" logic within the transaction

	}

}
