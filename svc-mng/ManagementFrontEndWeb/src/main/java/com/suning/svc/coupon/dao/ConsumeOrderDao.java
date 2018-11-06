/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: ConsumeOrderDao.java
 * Author:   11051612
 * Date:     2013-10-29 上午10:17:33
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
 * @author 11051612
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface ConsumeOrderDao {

    /**
     * 扣减退货订单的可退货金额
     * 
     * @param consumerOrderId 消费订单ID
     * @param minusAmount 扣减金额(即退货金额)
     * @param refundType 退货类型 部分退货 全部退货
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    void minusRefundableMoney(long consumerOrderId, long minusAmount, String refundType);
}
