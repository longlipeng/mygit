/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: TransactionTemplate.java
 * Author:   11051612
 * Date:     2013-10-28 下午4:23:52
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.suning.svc.coupon.dao.SerialNumberDao;

/**
 * 生成序列单号工具类
 * 
 */
public class SerialNumberDaoImpl extends SqlMapClientDaoSupport implements SerialNumberDao {

    /**
     * 根据序列名称生成16位序列单号 (8位日期+8位序列)如：2011091500000052
     * 
     * @param seqName 序列名称
     * @return
     * @throws SQLException
     */
    public String getSerialNumberOf16BySeqName(String seqName) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("sequenceName", seqName);
        params.put("length", 8);
        return (String) getSqlMapClientTemplate().queryForObject("SerialNumber.selectSerialNumber", params);
    }
    
    public Long getSequence(String seqName){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("sequenceName", seqName);
        return (Long) getSqlMapClientTemplate().queryForObject("SerialNumber.getSequence", params);
    }

}
