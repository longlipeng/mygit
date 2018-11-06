/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: OrderActUpdateTaskTest.java
 * Author:   13091704
 * Date:     2013-11-22 下午04:18:29
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
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.dataset.FlatXmlDataSetLoader;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.BatchSumDAO;
import com.huateng.framework.ibatis.dao.SellOrderDAO;
import com.huateng.framework.ibatis.model.BatchSum;
import com.huateng.framework.ibatis.model.BatchSumExample;
import com.huateng.framework.ibatis.model.SellOrder;
import com.huateng.framework.ibatis.model.SellOrderExample;
import com.huateng.univer.order.business.bo.OrderBaseQueryBO;
import com.suning.svc.coupon.db.DbScriptExecutionListener;

/**
 * 〈一句话功能简述〉<br>
 * 订单激活状态更新测试类
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
public class OrderActUpdateTaskTest {

    @Autowired
    private OrderActUpdateTask orderActUpdateTask;
    @Autowired
    private OrderBaseQueryBO orderBaseQueryBO;
    @Autowired
    private BatchSumDAO batchSumDAO;
    @Autowired
    private SellOrderDAO sellOrderDAO;
    
    @Test
    public void testGetOrderActUpdate() {
        try {
            List<SellOrderDTO> sellOrderDTOList = orderActUpdateTask.getOrderActUpdate();
            Assert.assertEquals(1, sellOrderDTOList.size());
        } catch (BizServiceException e) {
            Assert.fail();
            e.printStackTrace();
        }
    }

    @Test
    public void testOrderActUpdate() {
        try {
            List<SellOrderDTO> sellOrderDTOs = orderBaseQueryBO.getOrderActUpdate();
            String orderId = sellOrderDTOs.get(0).getOrderId();
            String batchNo = sellOrderDTOs.get(0).getBatchNo();
            BatchSumExample example = new BatchSumExample();
            example.createCriteria().andOrderIdEqualTo(orderId).andBatchNoEqualTo(batchNo);
            BatchSum batchSum = batchSumDAO.selectByExample(example).get(0);
            batchSum.setTotCnt("1");
            batchSum.setSucCnt("0000000001");
            batchSum.setFailCnt("0000000000");
            batchSum.setBatchState("03");
            batchSumDAO.updateByPrimaryKey(batchSum);

            
            orderActUpdateTask.orderActUpdate("businessAct");
            
            SellOrderExample ex= new SellOrderExample();
            example.createCriteria().andOrderIdEqualTo("-0001");
            SellOrder sellOrder = sellOrderDAO.selectByExample(ex).get(0);
            Assert.assertEquals("1", sellOrder.getInitActStat());

        } catch (BizServiceException e) {
            Assert.fail();
            e.printStackTrace();
        }
    }
}
