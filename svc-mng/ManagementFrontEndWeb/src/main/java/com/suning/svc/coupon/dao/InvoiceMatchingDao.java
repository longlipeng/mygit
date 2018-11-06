/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: InvoiceMatchingDao.java
 * Author:   13040446
 * Date:     2013-11-1 下午04:30:54
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.dao;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author 13040446
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface InvoiceMatchingDao {

    /**
     * 通过sql往数据库插入记录
     */
    public void insert(String statementName, Object parameterObject);

    /**
     * 向发票需求关系插入记录
     */
    public void insertInvoiceMatching(Long invoiceRequirementId, Long invoiceId, Long amount);

    /**
     * 通过sql删除记录
     */
    public void delete(String statementName, Object parameterObject);

    /**
     * 通过sql删除记录
     */
    public void update(String statementName, Object parameterObject);
}
