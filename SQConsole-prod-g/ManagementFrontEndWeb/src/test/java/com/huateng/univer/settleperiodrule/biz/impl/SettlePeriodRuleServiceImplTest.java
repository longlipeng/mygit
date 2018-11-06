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
package com.huateng.univer.settleperiodrule.biz.impl;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderQueryDTO;
import com.allinfinance.univer.settleperiodrule.dto.SettlePeriodRuleDTO;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.dataset.FlatXmlDataSetLoader;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.univer.cardinvalidinfoquery.CardInvalidInfoQueryService;
import com.huateng.univer.settleperiodrule.biz.service.SettlePeriodRuleService;
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
@DbScriptSetup(value="/dbunit/settleRule.sql", datasource="dataSource")
public class SettlePeriodRuleServiceImplTest {
    
    
    @Autowired
    private SettlePeriodRuleService SettlePeriodRuleService;
    
    @Test
    public void testInsert(){
        SettlePeriodRuleDTO settlePeriodRuleDTO = new SettlePeriodRuleDTO();
        settlePeriodRuleDTO.setRuleName("单元测试");
        settlePeriodRuleDTO.setLoginUserId("9999");
        settlePeriodRuleDTO.setAmountMin("");
        settlePeriodRuleDTO.setRuleType("3");
        settlePeriodRuleDTO.setPartAmount("4");
        settlePeriodRuleDTO.setSettleOne("04");
        settlePeriodRuleDTO.setSettleTwo("06");
        settlePeriodRuleDTO.setSettleThree("15");
        settlePeriodRuleDTO.setSettleFour("23");
        try {
            SettlePeriodRuleService.insert(settlePeriodRuleDTO);
            String setSettleDateBuf = settlePeriodRuleDTO.getSettleDateBuf();
            Assert.assertEquals("04|06|15", setSettleDateBuf);
        } catch (BizServiceException e) {
            Assert.fail("插入数据失败");
            e.printStackTrace();        
        }
    }
    
    @Test
    public void testView(){
        SettlePeriodRuleDTO dto = new SettlePeriodRuleDTO();
        dto.setRuleNo("9999");
        try {
            SettlePeriodRuleDTO dto1 = SettlePeriodRuleService.view(dto);
            Assert.assertEquals("05|15|25" +
            		"" +
            		"", dto1.getSettleDateBuf());
        } catch (BizServiceException e) {
            Assert.fail("查询数据失败");
            e.printStackTrace();
        }
    }

    
   


}
