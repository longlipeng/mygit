/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: CustomerInvoiceInfoServiceTest.java
 * Author:   Administrator
 * Date:     2013-11-1 上午10:37:32
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.allinfinance.svc.coupon.dto.CustomerInvoiceInfoDTO;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.dataset.FlatXmlDataSetLoader;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.test.BaseTest;
import com.suning.svc.ibatis.dao.CustomerInvoiceInfoDAO;

/**
 * 
 * CustomerInvoiceInfoService单元测试
 * 
 * @author Tester
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext-test.xml" })
@TestExecutionListeners(value = { DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
@DbUnitConfiguration(databaseConnection = "dataSourceConnection", dataSetLoader = FlatXmlDataSetLoader.class)
@DatabaseSetup(type = DatabaseOperation.DELETE, value = { "/dbunit/customer_invoice_info.xml" })
public class CustomerInvoiceInfoServiceTest extends BaseTest {

    @Autowired
    private CustomerInvoiceInfoDAO dao;

    @Autowired
    private CustomerInvoiceInfoService service;

    @Test
    @DatabaseSetup(type = DatabaseOperation.INSERT, value = { "/dbunit/customer_invoice_info.xml" })
    @DatabaseTearDown(type = DatabaseOperation.DELETE, value = { "/dbunit/customer_invoice_info.xml" })
    public void testUpdateCustomerInvoiceInfo() throws BizServiceException {
        CustomerInvoiceInfoDTO dto = new CustomerInvoiceInfoDTO();
        dto.setId("-1");
        dto.setCompanyName("Test Company Name");
        service.updateCustomerInvoiceInfo(dto);
        String val = dao.selectByPrimaryKey(-1L).getCompanyName();
        Assert.assertEquals("Test Company Name", val);
    }
}
