/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: OrderDaySumBatch.java
 * Author:   13010154
 * Date:     2013-10-31 下午07:13:55
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.service;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author 13010154
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface OrderDaySumBatchService {
    /**
     * 
     * 消费，退货 <br>
     */
    public void sumCpConsumeOrder(String txnType, String sumTxnTypeCode);
    /**
     * 
     * 充值 <br>
     */
    public void sumCpDepositOrder(String sumTxnTypeCode);
    /**
     * 
     * 充退 <br>
     */
    public void sumCpDepositRefundOrder(String sumTxnTypeCode);
}
