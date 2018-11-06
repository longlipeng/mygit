package com.allinfinance.prepay.dao;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;

@Service
public class BatchInfoDAOImpl extends SqlSessionDaoSupport implements BatchInfoDAO   {

	@Override
	public int batchInsertInfo(String method, Object entity) {
		return this.getSqlSession().insert(method, entity);  
	}
	
	@Override
	public int batchUpdateInfo(String method, Object entity) {
		return this.getSqlSession().update(method, entity);  
	}

}
