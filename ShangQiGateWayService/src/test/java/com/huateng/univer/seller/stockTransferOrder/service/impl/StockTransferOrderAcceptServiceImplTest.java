/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: StockTransferOrderAcceptServiceImplTest.java
 * Author:   13010154
 * Date:     2013-11-22 下午02:29:40
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.huateng.univer.seller.stockTransferOrder.service.impl;

import java.util.List;

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
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderQueryDTO;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.dataset.FlatXmlDataSetLoader;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.SellOrderDAO;
import com.huateng.framework.ibatis.model.SellOrder;
import com.huateng.univer.order.business.bo.OrderBO;
import com.huateng.univer.seller.stockTransferOrder.service.StockTransferOrderAcceptService;
import com.suning.svc.coupon.db.DbScriptExecutionListener;
import com.suning.svc.coupon.db.annotation.DbScriptSetup;

/**
 * 
 * 库存调拨
 *
 * @author 13010154
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext-test.xml" })
@TestExecutionListeners(value = { DependencyInjectionTestExecutionListener.class, DbScriptExecutionListener.class, DbUnitTestExecutionListener.class })
@DbUnitConfiguration(databaseConnection = "dataSourceConnection", dataSetLoader = FlatXmlDataSetLoader.class)
public class StockTransferOrderAcceptServiceImplTest {
    Logger logger = Logger.getLogger(StockTransferOrderAcceptServiceImplTest.class);
    @Autowired
    private StockTransferOrderAcceptService stockTransferOrderAcceptService;
    @Autowired
    private OrderBO orderBO;
    @Autowired
    private SellOrderDAO sellOrderDAO;
    @Autowired
    private CommonsDAO commonsDAO;
    
    @Test
    public void testQueryStockTransferOrderAtAccept() {
        SellOrderQueryDTO sellOrderQueryDTO = new SellOrderQueryDTO();
        sellOrderQueryDTO.setEntityId("5101");
        try {
            PageDataDTO pageDataDTO = stockTransferOrderAcceptService.queryStockTransferOrderAtAccept(sellOrderQueryDTO);
            if(null == pageDataDTO) {
                Assert.assertNull(pageDataDTO);
            }
            else {
                int size = pageDataDTO.getData().size();
                logger.info("查询库存调拨订单的数量为：" + size);
            }
        } catch (BizServiceException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    @DbScriptSetup(value="/db/stockTransferOrderAcceptService.sql", datasource="dataSource")
    public void testQueryOrderCardList() {
        SellOrderDTO sellOrderDTO = new SellOrderDTO();
        sellOrderDTO.setOrderId("1554");
        try {
            List<String> queryOrderCardList = stockTransferOrderAcceptService.queryOrderCardList(sellOrderDTO);
            
            if(queryOrderCardList != null && queryOrderCardList.size() > 0) {
                Assert.assertEquals(queryOrderCardList.size(), 1);
                String cardNo = queryOrderCardList.get(0);
                Assert.assertEquals("2100000000000217253", cardNo);
                logger.info("查询到的卡号为： " + cardNo);
            }
        } catch (BizServiceException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 
     *订单入库-退回订单
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @Test
    @DbScriptSetup(value="/db/sendBackAtAccept.sql", datasource="dataSource")
    public void testSendBackAtAccept() {
        SellOrderDTO sellOrderDTO = new SellOrderDTO();
        sellOrderDTO.setOrderId("155");
        sellOrderDTO.setLoginUserId("1");
        sellOrderDTO.setDefaultEntityId("5101");
        sellOrderDTO.setOperationMemo("432");
        try {
            SellOrder sellOrder = sellOrderDAO.selectByPrimaryKey(sellOrderDTO.getOrderId());
            Assert.assertEquals("155", sellOrder.getOrderId());
            if (sellOrder.getOrderState().equals(OrderConst.STOCK_TRANSFER_ORDER_NOTALL_IN)) {
                logger.info("部分入库状态不能退回");
                throw new BizServiceException("部分入库状态不能退回");
            }
            try {
                orderBO.orderFlowNexNode(sellOrderDTO.getOrderId(), OrderConst.STOCK_TRANSFER_ORDER_READY, sellOrderDTO
                        .getLoginUserId(), sellOrderDTO.getDefaultEntityId(), OrderConst.ORDER_FLOW_OPRATION_BACK,
                        sellOrderDTO.getOperationMemo(), OrderConst.ORDER_FLOW_BRANCH_ACCEPT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (BizServiceException e) {
            e.printStackTrace();
        }
        logger.info("订单入库-退回订单单元测试完成！");
    }
    
    /**
     * 
     * 判断部分入库或完全入库
     * 
     *
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @Test
    @DbScriptSetup(value="/db/sendBackAtAccept.sql", datasource="dataSource")
    public void testCheckAccept() {
        SellOrderDTO sellOrderDTO = new SellOrderDTO();
        sellOrderDTO.setOrderId("155");
        sellOrderDTO.setLoginUserId("1");
        sellOrderDTO.setDefaultEntityId("5101");
        sellOrderDTO.setOperationMemo("432");
        sellOrderDTO.setOrderId(sellOrderDTO.getOrderId());
        List<String> result = (List<String>) commonsDAO.queryForList("STOCKTRANSFERORDER.getCardNos", sellOrderDTO);
        if (result.size() > 0) {
            Assert.assertNotNull(result);
            try {
                orderBO.orderFlowNexNode(sellOrderDTO.getOrderId(), OrderConst.STOCK_TRANSFER_ORDER_NOTALL_IN,
                        sellOrderDTO.getLoginUserId(), sellOrderDTO.getDefaultEntityId(),
                        OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION, "", OrderConst.ORDER_FLOW_BRANCH_ACCEPT);
            } catch (Exception e) {
               
            }
        } else {
            try {
                orderBO.orderFlowNexNode(sellOrderDTO.getOrderId(), OrderConst.STOCK_TRANSFER_ORDER_ALL_IN,
                        sellOrderDTO.getLoginUserId(), sellOrderDTO.getDefaultEntityId(),
                        OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION, "", OrderConst.ORDER_FLOW_BRANCH_ACCEPT);
            } catch (Exception e) {
           
            }
        }
        logger.info("判断部分入库或完全入库结束！！");
    }
    
    
    
}
