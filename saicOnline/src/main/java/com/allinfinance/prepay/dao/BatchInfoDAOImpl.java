package com.allinfinance.prepay.dao;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.allinfinance.prepay.exception.BizServiceException;

@Service
public class BatchInfoDAOImpl extends SqlSessionDaoSupport implements BatchInfoDAO   {

	@Override
	@Transactional(propagation = Propagation.REQUIRED ,
	isolation = Isolation.READ_COMMITTED ,
	rollbackFor={Exception.class,BizServiceException.class})
	public int batchInsertInfo(String method, Object entity) {
		return this.getSqlSession().insert(method, entity);  
	}

}
