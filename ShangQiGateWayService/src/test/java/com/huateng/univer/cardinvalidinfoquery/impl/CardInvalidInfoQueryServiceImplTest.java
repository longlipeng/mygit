/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: CardInvalidInfoQueryServiceImplTest.java
 * Author:   Administrator
 * Date:     2013-11-22 下午03:31:06
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 苟昊                    2013-11-22            版本号                  描述
 */
package com.huateng.univer.cardinvalidinfoquery.impl;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderQueryDTO;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.dataset.FlatXmlDataSetLoader;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.univer.cardinvalidinfoquery.CardInvalidInfoQueryService;
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
 * 卡作废查询测试类<br> 
 * 〈功能详细描述〉
 *
 * @author Administrator
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext-test.xml" })
@TestExecutionListeners(value = { DependencyInjectionTestExecutionListener.class, DbScriptExecutionListener.class, DbUnitTestExecutionListener.class })
@DbUnitConfiguration(databaseConnection = "dataSourceConnection", dataSetLoader = FlatXmlDataSetLoader.class)
@DbScriptSetup(value="/dbunit/cardInvalidQuery.sql", datasource="dataSource")
public class CardInvalidInfoQueryServiceImplTest {
    
    
    @Autowired
    private CardInvalidInfoQueryService cardInvalidInfoQueryService;
    
    @Test
    public void testQuery(){
        SellOrderQueryDTO sellOrderQueryDTO = new SellOrderQueryDTO();
        sellOrderQueryDTO.setOrderId("1992");
        try {
            PageDataDTO pageDataDTO = cardInvalidInfoQueryService.query(sellOrderQueryDTO);
            if(pageDataDTO==null){
                Assert.assertNull(pageDataDTO);   
            }
            Assert.assertEquals(1, pageDataDTO.getTotalRecord());
        } catch (BizServiceException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testView(){
        SellOrderQueryDTO sellOrderQueryDTO = new SellOrderQueryDTO();
        sellOrderQueryDTO.setOrderId("1992");
        try {
            SellOrderDTO sellOrderDTO = cardInvalidInfoQueryService.view(sellOrderQueryDTO);
            if(sellOrderDTO==null){
                Assert.assertNull(sellOrderDTO);
            }
            Assert.assertEquals("1992", sellOrderDTO.getOrderId());
        } catch (BizServiceException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testViewCardList(){
        SellOrderQueryDTO sellOrderQueryDTO = new SellOrderQueryDTO();
        sellOrderQueryDTO.setOrderId("1992");
        try {
            PageDataDTO pageDataDTO = cardInvalidInfoQueryService.viewCardList(sellOrderQueryDTO);
            if(pageDataDTO==null){
                Assert.assertNull(pageDataDTO);  
            }
            int  i = pageDataDTO.getTotalRecord();
            if(pageDataDTO.getTotalRecord()!=2){
                Assert.fail("获取数据不正确！");
                Assert.assertEquals("1992", pageDataDTO.getTotalRecord());
            }
        } catch (BizServiceException e) {
            e.printStackTrace();
        }
    }
 


}
