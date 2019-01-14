/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: CustomerSynchServiceImpl.java
 * Author:   12073942
 * Date:     2013-10-25 下午3:29:51
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.huateng.univer.synch.customer.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.CustomerDAO;
import com.huateng.framework.ibatis.model.Customer;
import com.huateng.framework.ibatis.model.CustomerKey;
import com.huateng.univer.synch.customer.CustomerSynchService;
import com.huateng.univer.synch.dto.CustomerSynchDTO;
import com.suning.svc.coupon.constants.OrgnizationConstants;

/**
 * 客户下发同步服务实现
 * 
 * @author LEO
 */
public class CustomerSynchServiceImpl implements CustomerSynchService {

    Logger logger = Logger.getLogger(this.getClass());

    /**
     * 客户表DAO
     */
    private CustomerDAO customerDAO;

    /**
     * 事务化DAO操作
     */
    private CustomerSynchDAOService customerSynchDAOService;

    @Override
    public void customerInfoSynch(CustomerSynchDTO customerSynchDTO) throws BizServiceException {
        try {

            String entityId = customerSynchDTO.getEntityId();
            if (StringUtils.isBlank(entityId)) {
                logger.error("客户实体ID（供应商编码）为空。");
                throw new BizServiceException("客户实体ID（供应商编码）为空。");
            }

            // 双11活动公司为华夏通
            String fatherEntityId = OrgnizationConstants.HXT_ENTITY_ID;
            customerSynchDTO.setFatherEntityId(fatherEntityId);

            CustomerKey customerKey = new CustomerKey();
            customerKey.setEntityId(entityId);
            customerKey.setFatherEntityId(fatherEntityId);
            // 主键查询
            Customer record = customerDAO.selectByPrimaryKey(customerKey);
            if (record != null) {
                // 更新
                customerSynchDAOService.updateCustomer(customerSynchDTO);
            } else {
                // 新增
                customerSynchDAOService.insertCustomer(customerSynchDTO);
            }

        } catch (BizServiceException be) {
            throw be;
        } catch (Exception e) {
            logger.error("客户" + customerSynchDTO.getEntityId() + "同步失败：" + e.getMessage());
            throw new BizServiceException("客户" + customerSynchDTO.getEntityId() + "同步失败！");
        }
    }

    /**
     * @param customerDAO the customerDAO to set
     */
    public void setCustomerDAO(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    /**
     * @param customerSynchDAOService the customerSynchDAOService to set
     */
    public void setCustomerSynchDAOService(CustomerSynchDAOService customerSynchDAOService) {
        this.customerSynchDAOService = customerSynchDAOService;
    }

}
