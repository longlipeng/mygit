/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: CommonServiceSupport.java
 * Author:   13040443
 * Date:     2013-10-29 下午02:33:04
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.service;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.svc.coupon.dto.InvoiceRequirementsDTO;
import com.huateng.framework.exception.BizServiceException;
import com.suning.svc.core.common.BaseException;
import com.suning.svc.ibatis.model.DepositOrder;
import com.suning.svc.ibatis.model.DepositRefundOrder;

/**
 * 开票需求service
 * 
 * @author yanbin
 */
public interface InvoiceRequirementService {
    /**
     * 初始化开票记录
     * 
     * @param depositOrder
     * @return
     */
    PageDataDTO initInvoiceRequirement(InvoiceRequirementsDTO invoiceRequirementDTO) throws BizServiceException;

    /**
     * 按商户统计未开票金额
     * 
     * @param invoiceRequirementDTO
     * @return
     * @throws BizServiceException
     */
    PageDataDTO sumMchtInvoiceRequirement(InvoiceRequirementsDTO invoiceRequirementDTO) throws BizServiceException;

    /**
     * 开票
     * 
     * @param depositOrder
     * @return
     */
    void modifyInvoice(InvoiceRequirementsDTO invoiceRequirementsDTO) throws BizServiceException;

    /**
     * 根据depositOrder 增加开票需求（正向充值）
     * 
     * @param depositOrder
     * @return
     */
    void addInvoiceRequirement(DepositOrder depositOrder);

    /**
     * 根据depositRefundOrder 增加开票需求 （反向充退）
     * 
     * @param depositRefundOrder
     * @param depositOrderId
     * @return
     */
    void addInvoiceRequirement(DepositRefundOrder depositRefundOrder, Long depositOrderId) throws BaseException;

}
