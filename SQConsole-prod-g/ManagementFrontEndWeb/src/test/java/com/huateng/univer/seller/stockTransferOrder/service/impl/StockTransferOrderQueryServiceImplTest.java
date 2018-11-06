/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: StockTransferOrderQueryServiceImplTest.java
 * Author:   13071598
 * Date:     2013-11-25 下午02:42:00
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.huateng.univer.seller.stockTransferOrder.service.impl;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderQueryDTO;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.dataset.FlatXmlDataSetLoader;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.univer.seller.stockTransferOrder.service.StockTransferOrderQueryService;
import com.suning.svc.coupon.db.DbScriptExecutionListener;
import com.suning.svc.coupon.db.annotation.DbScriptSetup;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

/**
 * 测试类
 *
 * @author 13071598
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext-test.xml" })
@TestExecutionListeners(value = { DependencyInjectionTestExecutionListener.class, DbScriptExecutionListener.class, DbUnitTestExecutionListener.class })
@DbUnitConfiguration(databaseConnection = "dataSourceConnection", dataSetLoader = FlatXmlDataSetLoader.class)
public class StockTransferOrderQueryServiceImplTest {

    @Autowired
    private StockTransferOrderQueryService stockTransferOrderQueryService;
    
    
    @Test
    @DbScriptSetup(value="/dbunit/stock_transfer_order_input.sql", datasource="dataSource")
    @DatabaseSetup(type = DatabaseOperation.INSERT, value = { "/dbunit/stock_transfer_order_input.xml" })
    public void testQueryStockTransferOrderAtInput(){
        
        SellOrderQueryDTO sellOrderQueryDTO = new SellOrderQueryDTO();
        sellOrderQueryDTO.setOrderState("7");
        sellOrderQueryDTO.setCreateUser("1");
        sellOrderQueryDTO.setEntityId("5101");
        sellOrderQueryDTO.setOrderId("-2222");
        PageDataDTO result = null;
        try {
            result = stockTransferOrderQueryService.queryAllOrder(sellOrderQueryDTO);
        } catch (BizServiceException e) {
            e.printStackTrace();
        }
        if(result.getData().size() <= 0){
            Assert.fail("查询失败");
        }
        Assert.assertEquals(1, result.getData().size());
    }
    
}
