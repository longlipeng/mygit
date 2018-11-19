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
import com.suning.svc.coupon.constants.TradeConstants.TradeOrderType;
import com.suning.svc.ibatis.dao.DepositOrderDAO;
import com.suning.svc.ibatis.dao.InvoiceRequirementDAO;
import com.suning.svc.ibatis.dao.TradeItemDealedDAO;
import com.suning.svc.ibatis.dao.TradeItemTempDAO;
import com.suning.svc.ibatis.dao.VirtualCardDAO;
import com.suning.svc.ibatis.model.DepositOrder;
import com.suning.svc.ibatis.model.DepositOrderExample;
import com.suning.svc.ibatis.model.InvoiceRequirement;
import com.suning.svc.ibatis.model.InvoiceRequirementExample;
import com.suning.svc.ibatis.model.TradeItemDealed;
import com.suning.svc.ibatis.model.TradeItemDealedExample;
import com.suning.svc.ibatis.model.TradeItemTemp;
import com.suning.svc.ibatis.model.TradeItemTempExample;
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
@DatabaseSetup(type = DatabaseOperation.DELETE, value = { "/dbunit/share_deposit_processer.xml" })
public class ShareDepositProcesserTest {

    @Autowired
    private ShareDepositProcessor shareDepositProcessor;
    @Autowired
    private DepositOrderDAO depositOrderDAO;
    @Autowired
    private InvoiceRequirementDAO invoiceRequirementDAO;
    @Autowired
    private VirtualCardDAO virtualCardDAO;
    @Autowired
    private TradeItemTempDAO tradeItemTempDAO;
    @Autowired
    private TradeItemDealedDAO tradeItemDealedDAO;

    @Test
    @DatabaseSetup(type = DatabaseOperation.INSERT, value = { "/dbunit/share_deposit_processer.xml" })
    public void testGetBatchNO() {
        long batchId = shareDepositProcessor.getBatchNO();
        Assert.assertNotNull(batchId);
    }

    @Test
    @DatabaseSetup(type = DatabaseOperation.INSERT, value = { "/dbunit/share_deposit_processer.xml" })
    public void testGetTradeList() {
        long batchId = shareDepositProcessor.getBatchNO();
        List<TradeItemTempDto> datas = shareDepositProcessor.getTradeList(batchId);
        if (null == datas || datas.size() == 0) {
            Assert.fail("没有获取到数据！");
        }
    }

    @Test
    @DatabaseSetup(type = DatabaseOperation.INSERT, value = { "/dbunit/share_deposit_processer.xml" })
    public void testBatchProcessTrade() {

        shareDepositProcessor.batchProcessTrade();

        // check 充值订单
        DepositOrderExample depositExample = new DepositOrderExample();
        depositExample.createCriteria().andCouponNoEqualTo("00000000000001").andAmountEqualTo(12000l);
        List<DepositOrder> depositOrders = depositOrderDAO.selectByExample(depositExample);

        if (null == depositOrders) {
            Assert.fail("depositExample ; 没有得到充值订单！");
        }

        Assert.assertEquals(depositOrders.size(), 1);

        DepositOrder depositOrder = depositOrders.get(0);

        if (StringUtil.isEmpty(depositOrder.getCardNo())) {
            Assert.fail(depositOrder.getId() + "没有卡号！");
        }
        Assert.assertEquals(depositOrder.getCustomerEntityId(), "RE1160");
        Assert.assertEquals(depositOrder.getAmount().longValue(), 12000l);
        Assert.assertEquals(depositOrder.getBalanceAmount().longValue(), 12000l);

        // check 卡内金额
        VirtualCardExample cardExample = new VirtualCardExample();
        cardExample.createCriteria().andCardNoEqualTo(depositOrder.getCardNo());

        List<VirtualCard> virtualCards = virtualCardDAO.selectByExample(cardExample);
        if (null == virtualCards || virtualCards.size() == 0) {
            Assert.fail(depositOrder.getId() + "没有找到对应的卡！");
        }

        VirtualCard virtualCard = virtualCards.get(0);
        Assert.assertEquals(virtualCard.getAvailableBalance().longValue(), 57000l);

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
        Assert.assertEquals(requirement.getWaitAmount().longValue(), 12000l);
        Assert.assertEquals(requirement.getCustomerEntityId(), "RE1160");
        Assert.assertEquals(requirement.getAspect(), DepositConstants.INVOICE_REQUIRE_ASPECT_NORMAL);
        Assert.assertEquals(requirement.getStatus(), DepositConstants.INVOICE_REQUIRE_STATUS_NO);

        // check 数据转移
        TradeItemTempExample temExample = new TradeItemTempExample();
        temExample.createCriteria().andIdEqualTo(2000001l);
        List<TradeItemTemp> tradeItemTemps = tradeItemTempDAO.selectByExample(temExample);
        if (null != tradeItemTemps && tradeItemTemps.size() > 0) {
            Assert.fail("数据临时表没有被删除！");
        }

        TradeItemDealedExample dealedExample = new TradeItemDealedExample();
        dealedExample.createCriteria().andIdEqualTo(2000001l);
        List<TradeItemDealed> tradeItemDealeds = tradeItemDealedDAO.selectByExample(dealedExample);

        if (null == tradeItemDealeds && tradeItemDealeds.size() == 0) {
            Assert.fail("已处理表中没有转入进去！");
        }

        for (TradeItemDealed tradeItemDealed : tradeItemDealeds) {
            Assert.assertEquals(tradeItemDealed.getCouponNo(), "00000000000001");
            Assert.assertEquals(tradeItemDealed.getTradeType(), "0010");
            Assert.assertEquals(tradeItemDealed.getMchtCode(), "RE1160");
            Assert.assertEquals(tradeItemDealed.getAmount().longValue(), 12000);
            Assert.assertEquals(tradeItemDealed.getDirection(), "1");
            Assert.assertEquals(tradeItemDealed.getRefOrderId(), depositOrder.getId());
            Assert.assertEquals(tradeItemDealed.getRefOrderType(), TradeOrderType.ORDER_TYPE_DEPOSIT);
        }
    }

}
