/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: InvoiceMatchingDaoImpl.java
 * Author:   13040446
 * Date:     2013-11-1 下午04:41:02
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.suning.svc.coupon.dao.InvoiceMatchingDao;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author 13040446
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class InvoiceMatchingDaoImpl extends SqlMapClientDaoSupport implements InvoiceMatchingDao {

    public void insert(String statementName, Object parameterObject) {
        getSqlMapClientTemplate().insert(statementName, parameterObject);

    }

    public void delete(String statementName, Object parameterObject) {
        getSqlMapClientTemplate().delete(statementName, parameterObject);
    }

    public void update(String statementName, Object parameterObject) {
        getSqlMapClientTemplate().update(statementName, parameterObject);
    }

    public void insertInvoiceMatching(Long invoiceRequirementId, Long invoiceId, Long amount) {
        Map<String, Object> conditions = new HashMap<String, Object>();
        conditions.put("requireId", invoiceRequirementId);
        conditions.put("invoiceId", invoiceId);
        conditions.put("amount", amount);
        getSqlMapClientTemplate().insert("INVOICE_MATCHING.invoiceMatching_insert", conditions);

    }

}
