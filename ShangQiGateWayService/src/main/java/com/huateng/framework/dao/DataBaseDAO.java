package com.huateng.framework.dao;

import java.util.List;

import com.huateng.framework.exception.BizServiceException;

public interface DataBaseDAO {

	String getNextValueOfSequence(String tableName) throws BizServiceException;

	String getNextValueOfSequenceBySequence(String sequence)
			throws BizServiceException;

	@SuppressWarnings("unchecked")
	int batchInsert(final String statementId, final List parameters);
	
	@SuppressWarnings("unchecked")
	public int batchUpdate(final String statementId, final List parameters);
	
	public Object queryForObject(final String nameSpace,
			final String statementName, final Object parameterObject);
	
	/**
	 * 通用查询方法
	 */
	public List<?> queryForList(final String statementName, final Object parameterObject);
}
