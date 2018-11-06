/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: SettlementTradeDaoImpl.java
 * Author:   13040443
 * Date:     2013-12-13 下午06:34:13
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

import com.suning.svc.coupon.dao.SettlementTradeDao;

/**
 * 自定义结算单查询dao 实现类
 * 
 * @author yanbin
 */
public class SettlementTradeDaoImpl extends SqlMapClientDaoSupport implements SettlementTradeDao {

    @Override
    public List<Map<String, String>> selectSettlementTrade(String mchtId, Long batchId) {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("mchtId", mchtId);
        condition.put("batchId", batchId);
        @SuppressWarnings("unchecked")
        List<Map<String, String>> results = getSqlMapClientTemplate().queryForList(
                "COUPONSETTLEMENT.querySettlementTrade", condition);
        return results;
    }

}
