/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: TradeItemDaoImpl.java
 * Author:   秦伟
 * Date:     2013-10-31 下午5:31:00
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.suning.svc.coupon.constants.TradeItemConstants;
import com.suning.svc.coupon.dao.TradeItemDao;
import com.suning.svc.ibatis.model.TradeItemDealed;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author 11051612
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class TradeItemDaoImpl extends SqlMapClientDaoSupport implements TradeItemDao {

    private final String NAMESPACE = TradeItemDealed.class.getName();
    
    /* (non-Jsdoc)
     * @see com.suning.svc.coupon.dao.TradeItemDao#moveData()
     */
    @Override
    public void moveData(String merchantCode, String couponNO, long batchNO, String itemOrderNO, String orderType, long orderId) {
        Map<String , Object> params = new HashMap<String , Object>();
        if(!StringUtils.isBlank(merchantCode)){
            params.put("merchantCode", merchantCode);
        }
        params.put("couponNO", couponNO);
        params.put("batchNO", batchNO);
        params.put("status", TradeItemConstants.DEAL_SUCCESS);
        params.put("orderId", orderId);
        params.put("orderType", orderType);
        if(!StringUtils.isBlank(itemOrderNO)){
            params.put("itemOrderNO", itemOrderNO);
        }
        
        getSqlMapClientTemplate().update(NAMESPACE + ".moveData", params);
    }


}
