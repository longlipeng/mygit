/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: CardAmountManagerImplTest.java
 * Author:   秦伟
 * Date:     2013-11-5 下午4:31:56
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

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.dataset.FlatXmlDataSetLoader;
import com.suning.svc.coupon.service.CardAmountManager;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author 11051612
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext-test.xml" })
@TestExecutionListeners(value = { DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
@DbUnitConfiguration(databaseConnection = "dataSourceConnection", dataSetLoader = FlatXmlDataSetLoader.class)
@DatabaseSetup(type = DatabaseOperation.DELETE, value = { "/dbunit/cp_virtual_card.xml" })
public class CardAmountManagerImplTest2 {

    @Autowired
    CardAmountManager cardManager;

    /**
     * Test method for
     * {@link com.suning.svc.coupon.service.impl.CardAmountManagerImpl#addAmount(java.lang.String, long)}.
     */
    @Test
    @DatabaseSetup(type = DatabaseOperation.INSERT, value = { "/dbunit/cp_virtual_card.xml" })
    public void testAddAmount() {
        long retAmount = cardManager.addAmount("6699661110001880616", 100);
        Assert.assertEquals(400, retAmount);
    }

    /**
     * Test method for
     * {@link com.suning.svc.coupon.service.impl.CardAmountManagerImpl#minusAmount(java.lang.String, long)}.
     */
    @Test
    public void testMinusAmount() {
        long retAmount = cardManager.minusAmount("6699661110001880616", 100);
        Assert.assertEquals(200, retAmount);
    }

}
