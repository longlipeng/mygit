/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: ConsumeOrderDaoImpl.java
 * Author:   秦伟
 * Date:     2013-10-30 上午10:43:10
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.suning.svc.coupon.constants.TradeConstants;
import com.suning.svc.coupon.dao.ConsumeOrderDao;
import com.suning.svc.ibatis.model.ConsumeOrder;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author 11051612
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ConsumeOrderDaoImpl extends SqlMapClientDaoSupport implements ConsumeOrderDao {

    private final String NAMESPACE = ConsumeOrder.class.getName();

    /* (non-Jsdoc)
     * @see com.suning.svc.coupon.dao.ConsumeOrderDao#minusRefundableMoney(long, long, java.lang.String)
     */
    @Override
    public void minusRefundableMoney(long consumeOrderId, long minusAmount, String refundType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("consumeOrderId", consumeOrderId);
        params.put("minusAmount", minusAmount);
        params.put("refundType", refundType);
        getSqlMapClientTemplate().update(NAMESPACE + ".minusRefundableMoney", params);
    }

}
