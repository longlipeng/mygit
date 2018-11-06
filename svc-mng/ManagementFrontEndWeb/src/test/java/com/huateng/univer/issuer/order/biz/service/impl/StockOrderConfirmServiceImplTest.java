/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: StockOrderConfirmServiceImplTest.java
 * Author:   Administrator
 * Date:     2013-11-25 上午09:29:19
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.huateng.univer.issuer.order.biz.service.impl;

import com.allinfinance.univer.seller.order.dto.AcceptOrderDTO;
import com.huateng.test.BaseTest;
import com.huateng.univer.issuer.order.biz.service.StockOrderConfirmService;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 *订单优化卡号段接收、删除、全部删除
 * @author xw
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext-test.xml")
//@Transactional
public class StockOrderConfirmServiceImplTest {
    private Logger logger = Logger.getLogger(StockOrderConfirmServiceImplTest.class);
    @Autowired
    private StockOrderConfirmService  stockOrderConfirmService;
   /* @Test
    public void testDelete() throws Exception{
        AcceptOrderDTO acceptOrderDTO=new AcceptOrderDTO();
        acceptOrderDTO.setEntity("5101");
        acceptOrderDTO.setStartCardNo("6699669901000010433");
        acceptOrderDTO.setEndCardNo("6699669901000010433");
        acceptOrderDTO.setCardNum("1");
        acceptOrderDTO.setOrderId("1533");
        acceptOrderDTO.setOrderType("30000002");
        stockOrderConfirmService.delete(acceptOrderDTO);
    }
    @Test
    public void testDeleteAll() throws Exception{
        AcceptOrderDTO acceptOrderDTO=new AcceptOrderDTO();
        acceptOrderDTO.setEntity("5101");
        acceptOrderDTO.setStartCardNo("6699669901000010433");
        acceptOrderDTO.setEndCardNo("6699669901000010433");
        acceptOrderDTO.setCardNum("1");
        acceptOrderDTO.setOrderId("1533");
        acceptOrderDTO.setOrderType("30000002");
        stockOrderConfirmService.deleteAll(acceptOrderDTO);
    }*/
    @Test
    public void testSummitAcceptOrder() throws Exception{
        AcceptOrderDTO acceptOrderDTO=new AcceptOrderDTO();
        acceptOrderDTO.setEntity("5101");
        acceptOrderDTO.setStartCardNo("6699669901000010433");
        acceptOrderDTO.setEndCardNo("6699669901000010433");
        acceptOrderDTO.setCardNum("1");
        acceptOrderDTO.setOrderId("1533");
        acceptOrderDTO.setOrderType("30000002");
        stockOrderConfirmService.submitAcceptOrder(acceptOrderDTO);
    }
    /**
     * @return the stockOrderConfirmService
     */
    public StockOrderConfirmService getStockOrderConfirmService() {
        return stockOrderConfirmService;
    }

    /**
     * @param stockOrderConfirmService the stockOrderConfirmService to set
     */
    public void setStockOrderConfirmService(StockOrderConfirmService stockOrderConfirmService) {
        this.stockOrderConfirmService = stockOrderConfirmService;
    }
    /**
     * @return the logger
     */
    public Logger getLogger() {
        return logger;
    }
    /**
     * @param logger the logger to set
     */
    public void setLogger(Logger logger) {
        this.logger = logger;
    } 
    
}
