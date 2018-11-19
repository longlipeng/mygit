/**
 * Classname UserServiceImplTest.java
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

package com.huateng.univer.system.user.biz.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.system.user.dto.UserDTO;
import com.allinfinance.univer.system.user.dto.UserQueryDTO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.util.MakeTestType;
import com.huateng.framework.util.ReDTOs;
import com.huateng.test.BaseTest;
import com.huateng.univer.system.user.biz.service.UserService;

/**
 * @author wpf
 * 
 */
@MakeTestType(testType = { "IssueTest" })
public class UserServiceImplTest extends BaseTest {
	private Logger logger = Logger.getLogger(UserServiceImplTest.class);
	@Resource
	private UserService userService;

	@Test
	public void testFindUser() {
		UserDTO user = null;

		try {
			user = userService.findUser("admin");
		} catch (BizServiceException e) {
			fail("FindUser error");
		}

		assertNotNull(user);

		try {
			user = userService.findUser("sadasdfwq;eirqpmvc;qwnepqf");
		} catch (BizServiceException e) {
			user = null;
		}
		assertNull(user);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testInquery() {
		UserQueryDTO userQueryDTO = (UserQueryDTO) ReDTOs
				.getDTO(UserQueryDTO.class);

		PageDataDTO pdd = null;

		userQueryDTO.setDefaultEntityId("00000000");

		/**
		 * 测试1. 无参数时:
		 */
		userQueryDTO.setUserId(null);
		userQueryDTO.setUserName(null);
		userQueryDTO.setEntityId(null);
		try {
			pdd = userService.inquery(userQueryDTO);
		} catch (BizServiceException e) {
			this.logger.error(e.getMessage());
			fail("无参数测试用户查询错误 ,  测试失败 ");
		}
		try {
			if (pdd.getData().size() < 1) {
				throw new Exception("数据不正常");
			}

		} catch (Exception e) {
			this.logger.error(e.getMessage());
			fail("无参数测试用户查询错误 ,  测试失败 ");
		}
		/**
		 * 测试2. 通过ID(admin默认id为0)获取admin用户.
		 */
		userQueryDTO.setUserId("0");
		userQueryDTO.setUserName("ADmin");
		userQueryDTO.setDoCount(true);
		try {
			pdd = userService.inquery(userQueryDTO);

			List list = pdd.getData();
			Map map = (Map) list.get(0);
			assertNotNull(map);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			fail("查询ADMIN用户错误");
		}

	}
	//
	// @Test
	// public void testViewUser() {
	// fail("Not yet implemented");
	// }
	//
	//	
	// @Test
	// public void testGetUserById() {
	// fail("Not yet implemented");
	// }
	//
	//	
	// @Test
	// public void testCheckUserName() {
	// fail("Not yet implemented");
	// }
	//
	//	
	// @Test
	// public void testInsertUser() {
	// fail("Not yet implemented");
	// }
	//
	//	
	// @Test
	// public void testInsertUserForEntity() {
	// fail("Not yet implemented");
	// }
	//
	//	
	// @Test
	// public void testUpdateUser() {
	// fail("Not yet implemented");
	// }
	//
	//	
	// @Test
	// public void testDeleteUser() {
	// fail("Not yet implemented");
	// }
	//
	//	
	// @Test
	// public void testModifyPassword() {
	// fail("Not yet implemented");
	// }
	//
	//	
	// @Test
	// public void testAddRole() {
	// fail("Not yet implemented");
	// }
	//
	//	
	// @Test
	// public void testDeleteRole() {
	// fail("Not yet implemented");
	// }

}
