/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: InvoiceMatchingService.java
 * Author:   13040446
 * Date:     2013-10-30 下午03:10:47
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.service;

import java.util.List;
import java.util.Map;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.svc.coupon.dto.InvoiceRequireMentDto;
import com.allinfinance.svc.coupon.dto.InvoiceRequireMentQueryDto;
import com.allinfinance.svc.coupon.dto.InvoiceRequireTempQueryDto;
import com.allinfinance.svc.coupon.dto.InvoiceTempQueryDto;
import com.allinfinance.svc.coupon.dto.SettlementInfoDto;
import com.huateng.framework.exception.BizServiceException;

/**
 * 发票匹配Service
 * 
 * @author 13040446
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface InvoiceMatchingService {

    /**
     * 根据结算单查询可开发票需求
     * 
     * @param dto
     * @return PageDataDTO
     */
    PageDataDTO queryInvoiceRequire(InvoiceRequireMentQueryDto dto) throws BizServiceException;

    /**
     * 查询发票需求临时表
     * 
     * @param dto
     * @return PageDataDTO
     */
    PageDataDTO queryInvoiceRequireTemp(InvoiceRequireTempQueryDto dto) throws BizServiceException;

    /**
     * 查询发票临时表
     * 
     * @param dto
     * @return PageDataDTO
     */
    PageDataDTO queryInvoiceTemp(InvoiceTempQueryDto dto) throws BizServiceException;

    /**
     * 发票临时表入库
     * 
     * @param dto
     * @return
     */
    void insertTemp(InvoiceRequireMentQueryDto dto) throws BizServiceException;

    /**
     * 发票入库、插入关联表
     * 
     * @param dto
     * @return
     */
    void insertInvoice(InvoiceRequireMentQueryDto dto) throws BizServiceException;

    /**
     * 删除发票临时表数据
     * 
     * @param dto
     * @return
     */
    void deleteTemp(InvoiceRequireMentQueryDto dto) throws BizServiceException;

    /**
     * 根据结算单ID 查询
     * 
     * @param dto
     * @return SettlementInfoDto
     */
    SettlementInfoDto querySettlementById(SettlementInfoDto dto) throws BizServiceException;

    /**
     * 增加普通发票
     * 
     * @param dto
     * @return
     */
    void insertCommonInvoice(InvoiceRequireMentQueryDto dto) throws BizServiceException;

    /**
     * 根据结算单查询结算单发票详情
     * 
     * @param dto
     * @return SettlementInfoDto
     */
    PageDataDTO queryInvoiceSettleView(InvoiceRequireMentQueryDto dto) throws BizServiceException;

    /**
     * 
     * 根据客户编码查询开票项目
     * 
     * @param dto
     * @return
     * @throws BizServiceException
     */
    List<Map<String, String>> queryInvoiceProject(InvoiceRequireMentDto dto) throws BizServiceException;
    /**
     * 
     * 查询金额小计
     *
     * @param dto
     * @return
     * @throws BizServiceException
     */
    long querySumAmount(InvoiceRequireMentQueryDto dto) throws BizServiceException;

}
