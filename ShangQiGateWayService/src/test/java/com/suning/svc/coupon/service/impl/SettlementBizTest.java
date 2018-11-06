/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: DepositProcesserTest.java
 * Author:   sunchao
 * Date:     2013-10-31 下午07:38:25
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.allinfinance.svc.coupon.dto.DoSettleDto;
import com.allinfinance.svc.coupon.dto.SettlementQueryDto;
import com.huateng.framework.exception.BizServiceException;
import com.suning.svc.coupon.service.SettlementBiz;

/**
 * 
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author 孙超
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext-test.xml" })
public class SettlementBizTest {

    @Autowired
    private SettlementBiz settleBiz;

    @Test
    public void settleTest() {
        DoSettleDto dto = new DoSettleDto();
        dto.setEndDateStr("2013-11-14");
        dto.setStartDateStr("2013-11-14");
        dto.setFatherEntityId("5101");
        try {
            settleBiz.settle(dto);
        } catch (BizServiceException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送选中的结算单到SOP
     * 
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @Test
    public void sendCheckToSOPTest() {
        SettlementQueryDto dto = new SettlementQueryDto();
        dto.setSettlementIds(new Long[] { 63L });
        settleBiz.sendCheckToSOP(dto);
    }

    /**
     * 发送查询到的结算单到SOP
     * 
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @Test
    public void sendAllToSOPTest() {
        SettlementQueryDto dto = new SettlementQueryDto();
        dto.setMchtCode("");
        dto.setSendStatus("");
        dto.setInvoiceSendStatus("");
        settleBiz.sendAllToSOP(dto);
    }

    /**
     * 发送选中的开票信息到SOP
     * 
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @Test
    public void sendCheckedInvoiceToSOPTest() {
        SettlementQueryDto dto = new SettlementQueryDto();
        dto.setSettlementIds(new Long[] { 63L });
        settleBiz.sendCheckedInvoiceToSOP(dto);
    }

    /**
     * 发送查询到的开票信息到SOP
     * 
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @Test
    public void sendInvoiceAllToSOPTest() {
        SettlementQueryDto dto = new SettlementQueryDto();
        dto.setMchtCode("");
        dto.setSendStatus("");
        dto.setInvoiceSendStatus("");
        settleBiz.sendInvoiceAllToSOP(dto);
    }

    public SettlementBiz getSettleBiz() {
        return settleBiz;
    }

    public void setSettleBiz(SettlementBiz settleBiz) {
        this.settleBiz = settleBiz;
    }

}
