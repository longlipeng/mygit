/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: CommonSupportServiceDaoImpl.java
 * Author:   13040443
 * Date:     2013-10-29 下午07:48:44
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.allinfinance.svc.coupon.dto.TradeItemTempDto;
import com.suning.svc.ibatis.dao.CommonSupportServiceDao;

/**
 * 批量处理业务dao
 * 
 * @author yanbin
 */
public class CommonSupportServiceDaoImpl extends SqlMapClientDaoSupport implements CommonSupportServiceDao {

    @Override
    public List<TradeItemTempDto> selectGroupByBatchNo(long batchNo) {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("batchNo", batchNo);
        @SuppressWarnings("unchecked")
        List<TradeItemTempDto> results = getSqlMapClientTemplate().queryForList(
                "CommonSupportService.selectGroupByBatchNo", condition);
        return results;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TradeItemTempDto> selectByBatchNo(long batchNo) {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("batchNo", batchNo);
        return getSqlMapClientTemplate().queryForList("CommonSupportService.selectByBatchNo", condition);
    }

    @Override
    public TradeItemTempDto selectMinIdAndCount(String tradeType, String direction, Date spaceTime, Long counts) {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("tradeType", tradeType);
        condition.put("direction", direction);
        condition.put("spaceTime", spaceTime);
        condition.put("counts", counts);
        return (TradeItemTempDto) getSqlMapClientTemplate()
                .queryForObject("CommonSupportService.countTrade", condition);
    }

    @Override
    public void updateBatch(long batchNo, String tradeType, String direction, Date spaceTime, Long counts) {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("batchNo", batchNo);
        condition.put("tradeType", tradeType);
        condition.put("direction", direction);
        condition.put("spaceTime", spaceTime);
        condition.put("counts", counts);
        getSqlMapClientTemplate().update("CommonSupportService.updateBatch", condition);
    }

    @Override
    public void updateBatch(long batchNo, long minId, long maxId, String tradeType, String direction, Date spaceTime,
            Long counts) {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("batchNo", batchNo);
        condition.put("minId", minId);
        condition.put("maxId", maxId);
        condition.put("tradeType", tradeType);
        condition.put("direction", direction);
        condition.put("spaceTime", spaceTime);
        condition.put("counts", counts);
        getSqlMapClientTemplate().update("CommonSupportService.updateBatchPage", condition);
    }

}
