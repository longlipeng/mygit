/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: StockTransferOrderInputServiceImplTest.java
 * Author:   13071598
 * Date:     2013-11-22 上午10:35:20
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.huateng.univer.seller.stockTransferOrder.service.impl;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.issuer.dto.product.ProductQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderCompositeDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderInputDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderListDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderQueryDTO;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.dataset.FlatXmlDataSetLoader;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.dao.BaseDAO;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.SellOrderDAO;
import com.huateng.framework.ibatis.dao.SellOrderListDAO;
import com.huateng.framework.ibatis.model.SellOrder;
import com.huateng.framework.ibatis.model.SellOrderList;
import com.huateng.framework.ibatis.model.SellOrderListExample;
import com.huateng.univer.issuer.order.biz.bo.StockOrderCommonService;
import com.huateng.univer.order.business.bo.OrderBO;
import com.huateng.univer.order.business.bo.OrderBaseQueryBO;
import com.huateng.univer.seller.stockTransferOrder.service.StockTransferOrderInputService;
import com.huateng.univer.system.sysparam.biz.service.SystemParameterService;
import com.suning.svc.coupon.db.DbScriptExecutionListener;
import com.suning.svc.coupon.db.annotation.DbScriptSetup;

import java.util.List;

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
public class StockTransferOrderInputServiceImplTest {
    
    @Autowired
    private OrderBO orderBO;
    @Autowired
    private PageQueryDAO pageQueryDAO;
    @Autowired
    private CommonsDAO commonsDAO;
    @Autowired
    private BaseDAO baseDAO;
    @Autowired
    private SellOrderDAO sellOrderDAO;
    @Autowired
    private SellOrderListDAO sellOrderListDAO;
    @Autowired
    private OrderBaseQueryBO orderBaseQueryBO;
    @Autowired
    private StockOrderCommonService stockOrderCommonService;
    @Autowired
    private SystemParameterService systemParameterService;
    @Autowired
    private StockTransferOrderInputService stockTransferOrderInputService;
    
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
            result = stockTransferOrderInputService.queryStockTransferOrderAtInput(sellOrderQueryDTO);
        } catch (BizServiceException e) {
            e.printStackTrace();
        }
        if(result.getData().size() <= 0){
            Assert.fail("查询失败");
        }
        Assert.assertEquals(1, result.getData().size());
    }
    
    @Test
    @DbScriptSetup(value="/dbunit/testQueryFirstProcessProducts.sql", datasource="dataSource")
    @DatabaseSetup(type = DatabaseOperation.INSERT, value = {"/dbunit/tb_sell_contract.xml","/dbunit/tb_sell_prod_contract.xml"})
    public void testQueryFirstProcessProducts(){
        SellOrderDTO sellOrderDTO = new SellOrderDTO();
        sellOrderDTO.setFirstEntityId("-1111");
        sellOrderDTO.setProcessEntityId("-2222");
        List<ProductQueryDTO> list = null;
        try {
            list = stockTransferOrderInputService.queryFirstProcessProducts(sellOrderDTO);
        } catch (BizServiceException e) {
            e.printStackTrace();
        }
        if(list.size() <= 0){
            Assert.fail("查询调出机构产品无记录");
        }
        Assert.assertEquals(1, list.size());
    }
    
    @Test
    @DbScriptSetup(value="/dbunit/queryFirstEntityStock.sql", datasource="dataSource")
    @DatabaseSetup(type = DatabaseOperation.INSERT, value = {"/dbunit/tb_entity_stock.xml","/dbunit/tb_card_layout.xml"})
    public void testQueryFirstEntityStock(){
        SellOrderQueryDTO sellOrderQueryDTO = new SellOrderQueryDTO();
        sellOrderQueryDTO.setFirstEntityId("-1111");
        sellOrderQueryDTO.setProductId("-2222");
        PageDataDTO result = null;
        try {
            result = stockTransferOrderInputService.queryFirstEntityStock(sellOrderQueryDTO);
        } catch (BizServiceException e) {
            e.printStackTrace();
        }
        if(result.getData().size() <= 0){
            Assert.fail("查询调出机构产品库存无记录");
        }
        Assert.assertEquals(1,result.getData().size());
    }
    
    @Test
    public void insertStockTransferOrder(){
        SellOrderDTO sellOrderDTO = new SellOrderDTO();
        sellOrderDTO.setFirstEntityId("-3333");
        sellOrderDTO.setProcessEntityId("-4444");
        sellOrderDTO.setOrderDate("2013-11-10");
        sellOrderDTO.setOrderType("60000006");
        sellOrderDTO.setOrderState("7");
        sellOrderDTO.setLoginUserId("1");
        try {
            stockTransferOrderInputService.insertStockTransferOrder(sellOrderDTO);
        } catch (BizServiceException e) {
            e.printStackTrace();
        }
        SellOrder sellOrder = sellOrderDAO.selectByPrimaryKey(sellOrderDTO.getOrderId());
        
        if(sellOrder == null || !sellOrder.getFirstEntityId().equals("-3333") 
                || !sellOrder.getProcessEntityId().equals("-4444")){
            Assert.fail("订单新增失败");
        }
        sellOrderDAO.deleteByPrimaryKey(sellOrderDTO.getOrderId());
    }
    @Test
    @DbScriptSetup(value="/dbunit/testQueryStockTransferOrderForEdit.sql", datasource="dataSource")
    @DatabaseSetup(type = DatabaseOperation.INSERT, value = {"/dbunit/stock_transfer_order_input.xml"})
    public void testQueryStockTransferOrderForEdit(){
        SellOrderDTO sellOrderDTO = new SellOrderDTO();
        sellOrderDTO.setOrderId("-2222");
        SellOrderCompositeDTO sellOrderCompositeDTO = null;
        try {
            sellOrderCompositeDTO = stockTransferOrderInputService.queryStockTransferOrderForEdit(sellOrderDTO);
        } catch (BizServiceException e) {
            e.printStackTrace();
        }
        if(sellOrderCompositeDTO == null){
            Assert.fail("无该订单相关记录");
        }
    }
    
    @Test
    @DbScriptSetup(value="/dbunit/testQueryStockTransferOrderForEdit.sql", datasource="dataSource")
    @DatabaseSetup(type = DatabaseOperation.INSERT, value = {"/dbunit/stock_transfer_order_input.xml"})
    public void testInsertStockTransferOrderList(){
        SellOrderListDTO sellOrderListDTO = new SellOrderListDTO();
        sellOrderListDTO.setOrderId("-2222");
        sellOrderListDTO.setProductId("-1001");
        sellOrderListDTO.setCardLayoutId("-x9x9");
        sellOrderListDTO.setCardAmount("10");
        sellOrderListDTO.setRealAmount("0");
        sellOrderListDTO.setLoginUserId("1");
        sellOrderListDTO.setFaceValueType("0");
        sellOrderListDTO.setFaceValue("1000");
        try {
            stockTransferOrderInputService.insertStockTransferOrderList(sellOrderListDTO);
        } catch (BizServiceException e) {
            e.printStackTrace();
        }
        SellOrderListExample example = new SellOrderListExample();
        example.createCriteria().andCardLayoutIdEqualTo("-x9x9");
        List<SellOrderList> sellOrderList = sellOrderListDAO.selectByExample(example);
        
        if(sellOrderList.size() == 0){
            Assert.fail("订单明细插入失败");
        }
        sellOrderListDAO.deleteByExample(example);
    }
    @Test
    @DbScriptSetup(value="/dbunit/stock_transfer_order_input.sql", datasource="dataSource")
    @DatabaseSetup(type = DatabaseOperation.INSERT, value = { "/dbunit/stock_transfer_order_input.xml" })
    public void testUpdateStockTransferOrder(){
        SellOrderDTO sellOrderDTO = new SellOrderDTO();
        sellOrderDTO.setOrderId("-2222");
        sellOrderDTO.setOrderDate("9999-12-15");
        sellOrderDTO.setLoginUserId("1");
        try {
            stockTransferOrderInputService.updateStockTransferOrder(sellOrderDTO);
        } catch (BizServiceException e) {
            e.printStackTrace();
        }
        SellOrder sellOrder = sellOrderDAO.selectByPrimaryKey(sellOrderDTO.getOrderId());
        if(!sellOrder.getOrderDate().equals("99991215")){
            Assert.fail("订单更新失败");
        }
    }
    
    @Test
    @DbScriptSetup(value="/dbunit/stock_transfer_order_input.sql", datasource="dataSource")
    @DatabaseSetup(type = DatabaseOperation.INSERT, value = { "/dbunit/stock_transfer_order_input.xml" })
    public void testSubmitStockTransferOrderAtInput(){
        SellOrderInputDTO sellOrderInputDTO = new SellOrderInputDTO();
        String[] choose = new String[]{"-2222"};
        sellOrderInputDTO.setEc_choose(choose);
        sellOrderInputDTO.setLoginUserId("-1");
        sellOrderInputDTO.setDefaultEntityId("-1212");
        try {
            stockTransferOrderInputService.submitStockTransferOrderAtInput(sellOrderInputDTO);
        } catch (BizServiceException e) {
            e.printStackTrace();
        }
        SellOrder sellOrder = sellOrderDAO.selectByPrimaryKey("-2222");
        
        if(!sellOrder.getOrderState().equals("8")){
            Assert.fail("订单提交失败");
        }
    }
    
    @Test
    @DbScriptSetup(value="/dbunit/testQueryStockTransferOrderForEdit.sql", datasource="dataSource")
    @DatabaseSetup(type = DatabaseOperation.INSERT, value = { "/dbunit/stock_transfer_order_input.xml","/dbunit/stock_transfer_order_list.xml" })
    public void testDeleteRecord(){
        SellOrderListDTO sellOrderListDTO = new SellOrderListDTO();
        sellOrderListDTO.setOrderListId("-4444");
        sellOrderListDTO.setOrderId("-2222");
        try {
            stockTransferOrderInputService.deleteRecord(sellOrderListDTO);
        } catch (BizServiceException e) {
            e.printStackTrace();
        }
        SellOrderList sellOrderList = sellOrderListDAO.selectByPrimaryKey(sellOrderListDTO.getOrderListId());
        
        if(sellOrderList != null){
            Assert.fail("失败");
        }
    }
    @Test
    @DbScriptSetup(value="/dbunit/stock_transfer_order_input.sql", datasource="dataSource")
    @DatabaseSetup(type = DatabaseOperation.INSERT, value = { "/dbunit/stock_transfer_order_input.xml"})
    public void testCancelStockTransferOrderAtInput(){
        SellOrderInputDTO sellOrderInputDTO = new SellOrderInputDTO();
        String[] choose = new String[]{"-2222"};
        sellOrderInputDTO.setEc_choose(choose);
        sellOrderInputDTO.setLoginUserId("-1");
        sellOrderInputDTO.setDefaultEntityId("-1212");
        try {
            stockTransferOrderInputService.cancelStockTransferOrderAtInput(sellOrderInputDTO);
        } catch (BizServiceException e) {
            e.printStackTrace();
        }
        SellOrder sellOrder = sellOrderDAO.selectByPrimaryKey("-2222");
        if(!sellOrder.getOrderState().equals("12")){
            Assert.fail("订单取消失败");
        }
    }
    
}
