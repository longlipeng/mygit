/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: DepositProcessorDAO.java
 * Author:   13040443
 * Date:     2013-10-30 上午11:16:34
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.ibatis.dao;

/**
 * 充值充退用到的自定义SQL
 * 
 * @author yanbin
 */
public interface DepositProcessorDAO {

    /**
     * 更新可退开票的金额
     * 
     * @param invoiceId
     * @param amount
     */
    void updateMinusInvoiceMoney(Long invoiceId, Long amount);

    /**
     * 更新可退开票的金额，金额扣完，更新状态
     * 
     * @param invoiceId
     * @param amount
     * @param status
     */
    void updateMinusInvoiceMoney(Long invoiceId, Long amount, String status);

}
