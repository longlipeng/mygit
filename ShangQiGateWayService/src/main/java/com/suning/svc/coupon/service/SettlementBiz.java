/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: SettlementBiz.java
 * Author:   11051612
 * Date:     2013-10-29 上午10:54:37
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.service;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.svc.coupon.dto.DoSettleDto;
import com.allinfinance.svc.coupon.dto.SettlementQueryDto;
import com.huateng.framework.exception.BizServiceException;

/**
 * 零元购电子卡结算
 * 
 * @author 孙超
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface SettlementBiz {

    void settle(DoSettleDto dto) throws BizServiceException;

    /**
     * 
     * 分页查询结算单
     * 
     * @param dto
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    PageDataDTO querySettlementByPage(SettlementQueryDto dto);

    /**
     * 发送选中的数据到开放平台
     * 
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    void sendCheckToSOP(SettlementQueryDto settlementQueryDto);

    /**
     * 发送所有数据到开放平台
     * 
     * @param settlementQueryDto
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    void sendAllToSOP(SettlementQueryDto settlementQueryDto);

    /**
     * 发送所有查询到的开票信息到开放平台
     * 
     * @param settlementQueryDto
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    void sendInvoiceAllToSOP(SettlementQueryDto settlementQueryDto);

    /**
     * 发送选中的发票到开放平台
     * 
     * @param settlementQueryDto
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    void sendCheckedInvoiceToSOP(SettlementQueryDto settlementQueryDto);

}
