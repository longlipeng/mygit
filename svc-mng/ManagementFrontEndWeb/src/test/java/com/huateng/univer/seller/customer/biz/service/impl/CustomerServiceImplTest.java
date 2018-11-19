/**
 * Classname CustomerServiceImplTest.java
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
 *		flypal		2012-9-10
 * =============================================================================
 */

package com.huateng.univer.seller.customer.biz.service.impl;

import static org.junit.Assert.fail;
import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.allinfinance.univer.seller.customer.CustomerDTO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.univer.seller.customer.biz.service.CustomerService;

/**
 * @author flypal
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-test.xml")
@Transactional
public class CustomerServiceImplTest {
	private Logger logger = Logger.getLogger(CustomerServiceImplTest.class);
	
	@Autowired
	private CustomerService service;

	/**
	 * Test method for {@link com.huateng.univer.seller.customer.biz.service.impl.CustomerServiceImpl#initCustomer(com.allinfinance.univer.seller.customer.CustomerDTO)}.
	 */
	@Test
	public void testInitCustomer() {
//		fail("Not yet implemented");
		try {
			CustomerDTO dto = service.initCustomer(new CustomerDTO());
			Assert.assertEquals(0, dto.getSalesmanList().size());
		} catch (BizServiceException e) {
			
			this.logger.error(e.getMessage());
			fail("error");
		}
	}

	/**
	 * Test method for {@link com.huateng.univer.seller.customer.biz.service.impl.CustomerServiceImpl#viewCustomer(com.allinfinance.univer.seller.customer.CustomerDTO)}.
	 */
	@Test
	public void testViewCustomer() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.huateng.univer.seller.customer.biz.service.impl.CustomerServiceImpl#inqueryCustomer(com.allinfinance.univer.seller.customer.CustomerQueryDTO)}.
	 */
	@Test
	public void testInqueryCustomer() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.huateng.univer.seller.customer.biz.service.impl.CustomerServiceImpl#insertCustomer(com.allinfinance.univer.seller.customer.CustomerDTO)}.
	 */
	@Test
	public void testInsertCustomer() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.huateng.univer.seller.customer.biz.service.impl.CustomerServiceImpl#modifyState(com.allinfinance.univer.seller.customer.CustomerDTO)}.
	 */
	@Test
	public void testModifyState() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.huateng.univer.seller.customer.biz.service.impl.CustomerServiceImpl#updateCustomer(com.allinfinance.univer.seller.customer.CustomerDTO)}.
	 */
	@Test
	public void testUpdateCustomer() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.huateng.univer.seller.customer.biz.service.impl.CustomerServiceImpl#deleteCustomer(com.allinfinance.univer.seller.customer.CustomerDTO)}.
	 */
	@Test
	public void testDeleteCustomer() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.huateng.univer.seller.customer.biz.service.impl.CustomerServiceImpl#isContract(java.util.List)}.
	 */
	@Test
	public void testIsContract() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.huateng.univer.seller.customer.biz.service.impl.CustomerServiceImpl#getCustomerById(java.lang.String)}.
	 */
	@Test
	public void testGetCustomerById() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.huateng.univer.seller.customer.biz.service.impl.CustomerServiceImpl#selectRetailCustomer(java.lang.String)}.
	 */
	@Test
	public void testSelectRetailCustomer() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.huateng.univer.seller.customer.biz.service.impl.CustomerServiceImpl#importFile(com.allinfinance.univer.seller.customer.CustomerDTO)}.
	 */
	@Test
	public void testImportFile() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.huateng.univer.seller.customer.biz.service.impl.CustomerServiceImpl#insertOrderCustomer(com.allinfinance.univer.seller.customer.CustomerDTO)}.
	 */
	@Test
	public void testInsertOrderCustomer() {
		fail("Not yet implemented");
	}

}
