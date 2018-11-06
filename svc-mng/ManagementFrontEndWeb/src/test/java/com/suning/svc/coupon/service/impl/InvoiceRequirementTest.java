/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: InvoiceRequirementTest.java
 * Author:   luwanchuan
 * Date:     2013-11-13 下午03:13:49
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.service.impl;

import com.allinfinance.svc.coupon.dto.InvoiceRequirementsDTO;
import com.huateng.framework.exception.BizServiceException;
import com.suning.svc.coupon.service.InvoiceRequirementService;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author wangpeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext-test.xml" })
public class InvoiceRequirementTest {
    @Autowired
    InvoiceRequirementService invoiceRequirementService;
    InvoiceRequirementsDTO invoiceRequirementsDTO;

    @Test
    public void testInitInvoiceRequirement() throws BizServiceException {
        invoiceRequirementsDTO.setAspect("0");
        invoiceRequirementsDTO.setRefOrderId(001l);
        invoiceRequirementsDTO.setRefOrderType("1");
        invoiceRequirementsDTO.setCreatedTime(new Date());
        invoiceRequirementsDTO.setCustomerEntityId("5101");
        invoiceRequirementsDTO.setFatherEntityId("5101");
        invoiceRequirementsDTO.setInvoiceName("苏宁电器");
        invoiceRequirementsDTO.setStatus("1");
        invoiceRequirementsDTO.setUpdatedTime(new Date());
        invoiceRequirementsDTO.setType("1");
        invoiceRequirementsDTO.setWaitAmount(200l);
        invoiceRequirementsDTO.setYetAmount(0l);
        invoiceRequirementService.initInvoiceRequirement(invoiceRequirementsDTO);
    }

    public void testModifyInvoice() throws BizServiceException {
        invoiceRequirementsDTO.setInvoiceIds(",1,2,3");
        invoiceRequirementService.initInvoiceRequirement(invoiceRequirementsDTO);

    }

}
