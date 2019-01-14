package com.huateng.framework.db.dao;

import java.util.List;
import java.util.Map;

public interface BaseDAO {


	/**
	 * 根据example查询数据库数据
	 * 
	 * @param example
	 * @return
	 */
	@SuppressWarnings("unchecked")
	List selectByExample(Object example);

	@SuppressWarnings("unchecked")
	List selectByExample(final String namespace, final String statementName,
			Object example);

	Object selectByPrimaryKey(Object key);

	int countByExample(Object example);

	@SuppressWarnings("unchecked")
	public List queryForList(final String statementName,
			final Object parameterObject);

	@SuppressWarnings("unchecked")
	public List queryForList(final String namespace,
			final String statementName, final Object parameterObject);

	public Object queryForObject(final String statementName,
			final Object parameterObject);

	public Object queryForObject(final String nameSpace,
			final String statementName, final Object parameterObject);

	@SuppressWarnings("unchecked")
	public List queryForList(final String statementName,
			final Object parameterObject, final int skipResults,
			final int maxResults);

	@SuppressWarnings("unchecked")
	public Map queryForMap(final String statementName,
			final Object parameterObject, final String keyProperty);

}
