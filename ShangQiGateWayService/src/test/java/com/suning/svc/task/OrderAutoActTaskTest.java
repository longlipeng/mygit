/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: OrderAutoActTaskTest.java
 * Author:   13091704
 * Date:     2013-11-22 上午09:40:17
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.task;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.dataset.FlatXmlDataSetLoader;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.SellOrderDAO;
import com.huateng.framework.ibatis.model.SellOrder;
import com.huateng.framework.ibatis.model.SellOrderExample;
import com.suning.svc.coupon.db.DbScriptExecutionListener;
import com.suning.svc.coupon.db.annotation.DbScriptSetup;

/**
 * 〈一句话功能简述〉<br>
 * 订单自动激活测试类
 * 
 * @author 13091704
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext-test.xml" })
@TestExecutionListeners(value = { DependencyInjectionTestExecutionListener.class, DbScriptExecutionListener.class,
        DbUnitTestExecutionListener.class })
@DbUnitConfiguration(databaseConnection = "dataSourceConnection", dataSetLoader = FlatXmlDataSetLoader.class)
public class OrderAutoActTaskTest {
    @Autowired
    private OrderAutoActTask orderAutoActTask;
    @Autowired
    private SellOrderDAO sellOrderDAO;

    @Test
    @DbScriptSetup(value = "/dbunit/tb_sell_order_delete.sql", datasource = "dataSource")
    @DatabaseSetup(type = DatabaseOperation.INSERT, value = { "/dbunit/tb_sell_order_insert.xml" })
    public void testGetAutoActOrders() {
        try {
            List<SellOrderDTO> sellOrderDTOs = orderAutoActTask.getAutoActOrders();
            Assert.assertEquals(1, sellOrderDTOs.size());
        } catch (BizServiceException e) {
            Assert.fail();
            e.printStackTrace();
        }
    }

    @Test
    @DbScriptSetup(value = "/dbunit/tb_sell_order_delete.sql", datasource = "dataSource")
    @DatabaseSetup(type = DatabaseOperation.INSERT, value = { "/dbunit/tb_sell_order_insert.xml" })
    public void testOrderAutoAct() {
        try {
            orderAutoActTask.orderAutoAct();
            SellOrderExample example = new SellOrderExample();
            example.createCriteria().andOrderIdEqualTo("-0001");
            SellOrder sellOrder = sellOrderDAO.selectByExample(example).get(0);
            Assert.assertEquals("2", sellOrder.getInitActStat());
        } catch (BizServiceException e) {
            Assert.fail();
            e.printStackTrace();
        }

    }

}
