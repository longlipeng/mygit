/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: DepositProcesserTest.java
 * Author:   13040443
 * Date:     2013-10-31 下午07:38:25
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.service.impl;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.allinfinance.svc.coupon.dto.TradeItemTempDto;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.dataset.FlatXmlDataSetLoader;
import com.huateng.framework.util.StringUtil;
import com.suning.svc.coupon.constants.DepositConstants;
import com.suning.svc.ibatis.dao.DepositOrderDAO;
import com.suning.svc.ibatis.dao.DepositRefundOrderDAO;
import com.suning.svc.ibatis.dao.InvoiceRequirementDAO;
import com.suning.svc.ibatis.dao.TradeItemDealedDAO;
import com.suning.svc.ibatis.dao.VirtualCardDAO;
import com.suning.svc.ibatis.model.DepositOrder;
import com.suning.svc.ibatis.model.DepositRefundOrder;
import com.suning.svc.ibatis.model.DepositRefundOrderExample;
import com.suning.svc.ibatis.model.InvoiceRequirement;
import com.suning.svc.ibatis.model.InvoiceRequirementExample;
import com.suning.svc.ibatis.model.TradeItemDealed;
import com.suning.svc.ibatis.model.TradeItemDealedExample;
import com.suning.svc.ibatis.model.VirtualCard;
import com.suning.svc.ibatis.model.VirtualCardExample;

/**
 * 测试类
 * 
 * @author yanbin
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext-test.xml" })
@TestExecutionListeners(value = { DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
@DbUnitConfiguration(databaseConnection = "dataSourceConnection", dataSetLoader = FlatXmlDataSetLoader.class)
@DatabaseSetup(type = DatabaseOperation.DELETE, value = { "/dbunit/deposit_refund_processer.xml" })
public class DepositRefundProcesserTest {

    @Autowired
    private DepositRefundProcessor depositRefundProcessor;
    @Autowired
    private DepositRefundOrderDAO depositRefundOrderDAO;
    @Autowired
    private DepositOrderDAO depositOrderDAO;
    @Autowired
    private InvoiceRequirementDAO invoiceRequirementDAO;
    @Autowired
    private VirtualCardDAO virtualCardDAO;
    @Autowired
    private TradeItemDealedDAO tradeItemDealedDAO;

    @Test
    @DatabaseSetup(type = DatabaseOperation.INSERT, value = { "/dbunit/deposit_refund_processer.xml" })
    public void testGetBatchNO() {
        long batchId = depositRefundProcessor.getBatchNO();
        Assert.assertNotNull(batchId);
    }

    @Test
    @DatabaseSetup(type = DatabaseOperation.INSERT, value = { "/dbunit/deposit_refund_processer.xml" })
    public void testGetTradeList() {
        long batchId = depositRefundProcessor.getBatchNO();
        List<TradeItemTempDto> datas = depositRefundProcessor.getTradeList(batchId);
        if (null == datas || datas.size() == 0) {
            Assert.fail("没有获取到数据！");
        }
    }

    @Test
    @DatabaseSetup(type = DatabaseOperation.INSERT, value = { "/dbunit/deposit_refund_processer.xml" })
    public void testBatchProcessTrade() {

        depositRefundProcessor.batchProcessTrade();

        TradeItemDealedExample dealedExample = new TradeItemDealedExample();
        dealedExample.createCriteria().andItemOrderNoEqualTo("00000001");
        List<TradeItemDealed> tradeItemDealeds = tradeItemDealedDAO.selectByExample(dealedExample);
        TradeItemDealed tradeItemDealed = tradeItemDealeds.get(0);

        DepositOrder depositOrder = depositOrderDAO.selectByPrimaryKey(tradeItemDealed.getRefOrderId());

        // check 充退订单
        DepositRefundOrderExample refundOrderExample = new DepositRefundOrderExample();
        refundOrderExample.createCriteria().andOriginalOrderNoEqualTo(depositOrder.getOrderNo());

        List<DepositRefundOrder> depositRefundOrders = depositRefundOrderDAO.selectByExample(refundOrderExample);

        if (null == depositRefundOrders) {
            Assert.fail("refundOrderExample ; 没有得到充退订单！");
        }

        Assert.assertEquals(depositRefundOrders.size(), 1);

        DepositRefundOrder depositRefundOrder = depositRefundOrders.get(0);

        if (StringUtil.isEmpty(depositRefundOrder.getCardNo())) {
            Assert.fail(depositRefundOrder.getId() + "没有卡号！");
        }
        Assert.assertEquals(depositRefundOrder.getCustomerEntityId(), "RE1160");
        Assert.assertEquals(depositRefundOrder.getAmount().longValue(), 8000l);
        Assert.assertEquals(depositRefundOrder.getBalanceAmount().longValue(), 37000l);

        // check 卡内金额
        VirtualCardExample cardExample = new VirtualCardExample();
        cardExample.createCriteria().andCardNoEqualTo(depositOrder.getCardNo());

        List<VirtualCard> virtualCards = virtualCardDAO.selectByExample(cardExample);
        if (null == virtualCards || virtualCards.size() == 0) {
            Assert.fail(depositOrder.getId() + "没有找到对应的卡！");
        }

        VirtualCard virtualCard = virtualCards.get(0);
        Assert.assertEquals(virtualCard.getAvailableBalance().longValue(), 37000l);

        // check 开票
        InvoiceRequirementExample invoiceRequirementExample = new InvoiceRequirementExample();
        invoiceRequirementExample.createCriteria().andRefOrderIdEqualTo(depositOrder.getId())
                .andRefOrderTypeEqualTo(DepositConstants.INVOICE_REF_TYPE_DEPOSIT);
        List<InvoiceRequirement> invoiceRequirements = invoiceRequirementDAO.selectByExample(invoiceRequirementExample);

        if (null == invoiceRequirements || invoiceRequirements.size() == 0) {
            Assert.fail(depositOrder.getId() + "没有开票信息！");
        }

        InvoiceRequirement requirement = invoiceRequirements.get(0);

        Assert.assertEquals(requirement.getYetAmount().longValue(), 0l);
        Assert.assertEquals(requirement.getWaitAmount().longValue(), 37000l);

    }
}
