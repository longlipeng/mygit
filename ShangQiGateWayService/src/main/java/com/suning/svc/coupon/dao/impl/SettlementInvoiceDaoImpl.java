/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: SettlementInvoiceDaoImpl.java
 * Author:   Administrator
 * Date:     2013-11-4 下午01:42:43
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>gouhao      <time>2013-11-04      <version>    <desc>
 */
package com.suning.svc.coupon.dao.impl;

import com.suning.svc.coupon.dao.SettlementInvoiceDao;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

/**
 * 查询发票和结算单关联信息接口实现<br> 
 * 〈功能详细描述〉
 *
 * @author gouhao
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class SettlementInvoiceDaoImpl extends SqlMapClientDaoSupport implements SettlementInvoiceDao{

    
    @Override
    public List<?> selectSettlement(String statementName, Object parameterObject) {
        return getSqlMapClientTemplate().queryForList(statementName, parameterObject);
    }

    
    @Override
    public int checkInvoiceAll(String statementName, Object parameterObject) {
        return  (Integer)getSqlMapClientTemplate().queryForObject(statementName, parameterObject);
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, String>> selectSettlementInvoice(long id) {
		
		return getSqlMapClientTemplate().queryForList("INVOICE_MATCHING.selectSettlementInvoice", id);
	}

}
