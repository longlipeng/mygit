/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: CustomerInvoiceInfoService.java
 * Author:   12073942
 * Date:     2013-10-30 下午7:46:26
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.svc.coupon.dto.CustomerInvoiceInfoDTO;
import com.allinfinance.svc.coupon.dto.CustomerInvoiceInfoQueryDTO;
import com.huateng.framework.dao.BaseDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.util.ReflectionUtil;
import com.suning.svc.ibatis.dao.CustomerInvoiceInfoDAO;
import com.suning.svc.ibatis.model.CustomerInvoiceInfo;

/**
 * 客户发票信息后台服务
 * 
 * @author LEO
 */
public class CustomerInvoiceInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerInvoiceInfoService.class);

    /**
     * 分页查询DAO
     */
    private PageQueryDAO pageQueryDAO;

    /**
     * 客户发票信息DAO
     */
    private CustomerInvoiceInfoDAO customerInvoiceInfoDAO;

    /**
     * 基础DAO
     */
    private BaseDAO baseDAO;

    /**
     * 
     * 查询符合条件的列表
     * 
     * @param queryDTO 查询条件
     * @return 分页数据
     * @throws BizServiceException
     */
    public PageDataDTO inquiryCustomerInvoiceInfoList(CustomerInvoiceInfoQueryDTO queryDTO) throws BizServiceException {
        try {
            return pageQueryDAO.query("CUSTOMER_INVOICE_INFO.selectCustomerInvoiceInfo", queryDTO);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new BizServiceException("查询客户发票信息失败！");
        }
    }

    /**
     * 
     * 按主键获取客户发票信息
     * 
     * @param id 主键
     * @return 对象
     * @throws BizServiceException
     */
    public CustomerInvoiceInfoDTO getCustomerInvoiceInfo(Long id) throws BizServiceException {
        try {
            return (CustomerInvoiceInfoDTO) baseDAO.queryForObject(
                    "CUSTOMER_INVOICE_INFO.selectCustomerInvoiceInfoById", id);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new BizServiceException("获取客户发票信息失败！");
        }
    }

    /**
     * 
     * 更新客户发票信息
     * 
     * @param dto 对象DTO
     * @throws BizServiceException
     */
    public void updateCustomerInvoiceInfo(CustomerInvoiceInfoDTO dto) throws BizServiceException {
        try {
            // 更新客户发票信息表
            CustomerInvoiceInfo info = new CustomerInvoiceInfo();
            ReflectionUtil.copyPropertiesNotNull(dto, info);
            info.setUpdatedTime(new Date());
            customerInvoiceInfoDAO.updateByPrimaryKeySelective(info);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new BizServiceException("更新客户发票信息失败！");
        }
    }

    /**
     * @param pageQueryDAO the pageQueryDAO to set
     */
    public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
        this.pageQueryDAO = pageQueryDAO;
    }

    /**
     * @param customerInvoiceInfoDAO the customerInvoiceInfoDAO to set
     */
    public void setCustomerInvoiceInfoDAO(CustomerInvoiceInfoDAO customerInvoiceInfoDAO) {
        this.customerInvoiceInfoDAO = customerInvoiceInfoDAO;
    }

    /**
     * @param baseDAO the baseDAO to set
     */
    public void setBaseDAO(BaseDAO baseDAO) {
        this.baseDAO = baseDAO;
    }

}
