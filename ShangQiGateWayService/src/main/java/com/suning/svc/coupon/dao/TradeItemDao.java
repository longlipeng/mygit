/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: TradeItemDao.java
 * Author:   秦伟
 * Date:     2013-10-31 下午5:29:23
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
public interface TradeItemDao {

    /**
     * 将临时表数据转移到正式表中
     *
     * @param merchantCode
     * @param couponNO
     * @param batchNO
     * @param itemOrderNO
     * @param orderType
     * @param orderId
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    void moveData(String merchantCode, String couponNO, long batchNO, String itemOrderNO, String orderType, long orderId);
}
