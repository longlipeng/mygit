/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: DepositProcessorDAOImpl.java
 * Author:   13040443
 * Date:     2013-10-30 上午11:18:48
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.suning.svc.ibatis.dao.DepositProcessorDAO;

/**
 * 充值充退业务层的dao
 * 
 * @author yanbin
 */
public class DepositProcessorDAOImpl extends SqlMapClientDaoSupport implements DepositProcessorDAO {

    @Override
    public void updateMinusInvoiceMoney(Long invoiceId, Long amount) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("invoiceId", invoiceId);
        param.put("amount", amount);
        getSqlMapClientTemplate().update("DepositProcessor.updateMinusInvoiceMoney", param);
    }

    @Override
    public void updateMinusInvoiceMoney(Long invoiceId, Long amount, String status) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("invoiceId", invoiceId);
        param.put("amount", amount);
        param.put("status", status);
        getSqlMapClientTemplate().update("DepositProcessor.updateMinusInvoiceMoneyAndStatus", param);
    }

}
