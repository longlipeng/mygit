/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: ConsumeRefundProcessorTest.java
 * Author:   秦伟
 * Date:     2013-11-1 上午10:29:54
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.github.springtestdbunit.dataset.FlatXmlDataSetLoader;
import com.suning.svc.coupon.db.DbScriptExecutionListener;
import com.suning.svc.coupon.db.annotation.DbScriptSetup;
import com.suning.svc.coupon.service.TradeProcessor;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author 11051612
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/applicationContext-test.xml"})
@TestExecutionListeners(value = { DependencyInjectionTestExecutionListener.class, DbScriptExecutionListener.class, DbUnitTestExecutionListener.class })
@DbUnitConfiguration(databaseConnection = "dataSourceConnection", dataSetLoader = FlatXmlDataSetLoader.class)
public class ConsumeProcessorTest{

    /**
     * Test method for {@link com.suning.svc.coupon.service.BatchTradeProcessTemplate#batchProcessTrade()}.
     */
    @Autowired
    @Qualifier("consumeProcessor")
    TradeProcessor processor;
    
    @Test
    @DbScriptSetup(value="/dbunit/consumeProcessor.sql", datasource="dataSource")
    @DatabaseSetup(type = DatabaseOperation.INSERT, value = { "/dbunit/consumeProcessor.xml" })
    @ExpectedDatabase(value="/dbunit/consumeOrder.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
    public void testBatchProcessTrade() {
        processor.batchProcessTrade();
    }

}
