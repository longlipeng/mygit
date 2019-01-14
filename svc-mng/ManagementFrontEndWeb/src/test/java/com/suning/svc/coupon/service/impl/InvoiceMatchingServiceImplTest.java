/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: InvoiceMachingImplTest.java
 * Author:   13040446
 * Date:     2013-11-5 下午05:36:01
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.allinfinance.svc.coupon.dto.InvoiceRequireMentQueryDto;
import com.allinfinance.svc.coupon.dto.SettlementInfoDto;
import com.huateng.framework.exception.BizServiceException;
import com.suning.svc.coupon.service.InvoiceMatchingService;

/**
 * 
 * 开票测试
 *
 * @author 13040446
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/applicationContext-test.xml"})
public class InvoiceMatchingServiceImplTest{
    @Autowired
    private InvoiceMatchingService invoiceMatchingService;
    @Test
    public void testQuerySumAmount(){
        InvoiceRequireMentQueryDto dto = new InvoiceRequireMentQueryDto();
        dto.setSettlementId(64L);
        long sumAmount = 0;
        try {
          sumAmount =  invoiceMatchingService.querySumAmount(dto);
        } catch (BizServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Assert.assertEquals(0, sumAmount);
    }
    @Test
    public void testInsertTemp(){
        InvoiceRequireMentQueryDto dto = new InvoiceRequireMentQueryDto();
        dto.setSettlementId(64L);
        String[] ids = {"1","5101","0070000012","aa","5000"};
        dto.setInvoiceRequireIds(ids);
        try {
          invoiceMatchingService.insertTemp(dto);
        } catch (BizServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //Assert.assertEquals(0, sumAmount);
    }
    @Test
    public void testInsertInvoice(){
        InvoiceRequireMentQueryDto dto = new InvoiceRequireMentQueryDto();
        dto.setSettlementId(64L);
        try {
          invoiceMatchingService.insertInvoice(dto);
        } catch (BizServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        SettlementInfoDto settlementInfoDto = new SettlementInfoDto();
        settlementInfoDto.setId(64L);
            
        try {
            settlementInfoDto = invoiceMatchingService.querySettlementById(settlementInfoDto);
        } catch (BizServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        Assert.assertEquals("100", settlementInfoDto.getReceivedInvoinceAmount());
    }
    
    @Test
    public void testDeleteTemp(){
        InvoiceRequireMentQueryDto dto = new InvoiceRequireMentQueryDto();
        dto.setSettlementId(64L);
        try {
          invoiceMatchingService.deleteTemp(dto);
        } catch (BizServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //Assert.assertEquals(0, sumAmount);
    }
    
    @Test
    public void testInsertCommonInvoice(){
        InvoiceRequireMentQueryDto dto = new InvoiceRequireMentQueryDto();
        dto.setSettlementId(64L);
        try {
          invoiceMatchingService.insertCommonInvoice(dto);
        } catch (BizServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //Assert.assertEquals(0, sumAmount);
    }
}
