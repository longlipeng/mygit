/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: OrderBaseQueryBOTest.java
 * Author:   Administrator
 * Date:     2013-11-25 下午12:32:14
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.huateng.univer.order.bo;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.seller.order.dto.OrderReceiveCardListQueryDTO;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.dataset.FlatXmlDataSetLoader;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.univer.order.business.bo.OrderBaseQueryBO;
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
 * 〈营销机构采购订单详细测试类〉<br> 
 * 〈测试详细信息中的卡接收列表功能〉
 *
 * @author Administrator
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext-test.xml" })
@TestExecutionListeners(value = { DependencyInjectionTestExecutionListener.class, DbScriptExecutionListener.class, DbUnitTestExecutionListener.class })
@DbUnitConfiguration(databaseConnection = "dataSourceConnection", dataSetLoader = FlatXmlDataSetLoader.class)
@DbScriptSetup(value="/dbunit/cardReceive.sql", datasource="dataSource")
public class OrderBaseQueryBOTest {
    
    @Autowired
    private OrderBaseQueryBO bo;
    
    @Test
    public void testGetOrderReceiveListPageData(){
        OrderReceiveCardListQueryDTO QueryDTO = new OrderReceiveCardListQueryDTO();
        QueryDTO.setOrderId("1992");
        try {
            PageDataDTO pageDataDTO = bo.getOrderReceiveListPageData(QueryDTO);
            if(null == pageDataDTO){
                Assert.assertNull(pageDataDTO);
            }else{
                Assert.assertEquals(2, pageDataDTO.getTotalRecord());
            }
            
        } catch (BizServiceException e) {
            Assert.fail("查询信息失败");
            e.printStackTrace();
        }
    }
    
    
}
