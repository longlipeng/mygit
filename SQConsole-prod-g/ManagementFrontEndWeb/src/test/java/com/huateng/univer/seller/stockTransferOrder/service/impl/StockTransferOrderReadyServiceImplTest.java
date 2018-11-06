/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: StockTransferOrderReadyServiceImplTest.java
 * Author:   13010154
 * Date:     2013-11-22 下午05:00:04
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.huateng.univer.seller.stockTransferOrder.service.impl;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.seller.order.dto.OrderReadyDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderQueryDTO;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.dataset.FlatXmlDataSetLoader;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.univer.seller.stockTransferOrder.service.StockTransferOrderReadyService;
import com.suning.svc.coupon.db.DbScriptExecutionListener;
import com.suning.svc.coupon.db.annotation.DbScriptSetup;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author 13010154
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext-test.xml" })
@TestExecutionListeners(value = { DependencyInjectionTestExecutionListener.class, DbScriptExecutionListener.class, DbUnitTestExecutionListener.class })
@DbUnitConfiguration(databaseConnection = "dataSourceConnection", dataSetLoader = FlatXmlDataSetLoader.class)
public class StockTransferOrderReadyServiceImplTest {
    
    Logger logger = Logger.getLogger(StockTransferOrderReadyServiceImplTest.class);
    @Autowired
    private StockTransferOrderReadyService stockTransferOrderReadyService;
    
    /**
     * 
     * 查询待出库状态的调拨订单
     *
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @Test
    @DbScriptSetup(value="/db/QueryStockTransferOrderAtReady.sql", datasource="dataSource")
    public void testQueryStockTransferOrderAtReady() {
        SellOrderQueryDTO sellOrderQueryDTO = new SellOrderQueryDTO();
        sellOrderQueryDTO.setEntityId("5101");
        try {
            PageDataDTO pageDataDTO = stockTransferOrderReadyService.queryStockTransferOrderAtReady(sellOrderQueryDTO);
            if(null == pageDataDTO) {
                Assert.assertNull(pageDataDTO);
            }
            else {
                int size = pageDataDTO.getData().size();
                logger.info("查询待出库状态的调拨订单数量为：" + size);
            }
        } catch (BizServiceException e) { 
            e.printStackTrace();
        } 
    }
    
    @Test
    public void testQueryCardForReady() {
        OrderReadyDTO orderReadyDTO = new OrderReadyDTO();
        orderReadyDTO.setDefaultEntityId("5101");
        orderReadyDTO.setOrderId("1554");
        orderReadyDTO.setOrderCardListId("600580");
        try {
            PageDataDTO pageDataDTO = stockTransferOrderReadyService.queryCardForReady(orderReadyDTO);
            if(null == pageDataDTO) {
                Assert.assertNull(pageDataDTO);
            }
            else {
                int size = pageDataDTO.getData().size();
                logger.info("准备卡数量为：" + size);
            }
        } catch (BizServiceException e) {
            e.printStackTrace();
        }
    }
    
    
    

}
