/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: ConsumeRefundProcessorTest.java
 * Author:   秦伟
 * Date:     2013-11-1 上午10:29:54
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.card;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.dataset.FlatXmlDataSetLoader;
import com.suning.svc.coupon.service.VirtualCardService;
import com.suning.svc.ibatis.model.VirtualCard;

/**
 * 制卡测试
 * 
 * @author 孙超
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext-test.xml" })
@TestExecutionListeners(value = { DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
@DbUnitConfiguration(databaseConnection = "dataSourceConnection", dataSetLoader = FlatXmlDataSetLoader.class)
@DatabaseSetup(type = DatabaseOperation.DELETE, value = { "/dbunit/cp_virtual_card_makecard1.xml" })
public class CardProducerTest {

    @Autowired
    VirtualCardService virtualCardService;

    @Test
    public void testMakeCards() {
        try {
            virtualCardService.makeCards(1021, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 无超时数据、无初始卡，有数据准备 */
    @Test
    public void testcanCardCanUsed1() {
        try {
            List<VirtualCard> list = virtualCardService.canCardCanUsed(1021);
            Assert.assertEquals(0, list.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 有超时数据、无初始卡，有数据准备 */
    @Test
    public void testcanCardCanUsed2() {
        try {
            List<VirtualCard> list = virtualCardService.canCardCanUsed(1021);
            Assert.assertEquals(1000, list.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 无超时数据、有初始卡，有数据准备 */
    @Test
    public void testcanCardCanUsed3() {
        try {
            List<VirtualCard> list = virtualCardService.canCardCanUsed(1021);
            Assert.assertEquals(1000, list.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DatabaseSetup(type = DatabaseOperation.INSERT, value = { "/dbunit/cp_virtual_card_makecard1.xml" })
    public void testProduceCard1() {
        VirtualCard virtualCard = CardSelector.getCard();
        VirtualCard v = new VirtualCard();
        v.setId(0L);
        v.setBatchId(165L);
        v.setProductId(1021L);
        v.setCardNo("6699661110701880616");
        v.setAvailableBalance(0L);
        v.setStatus("0");
        v.setCreatedTime(DateUtils.addHours(new Date(), -26));
        v.setUpdatedTime(DateUtils.addHours(new Date(), -26));
        Assert.assertNotEquals(v, virtualCard);
    }
}
