package com.huateng.framework.dao;

import java.util.List;
import java.util.Map;

public interface BaseDAO {

	/**
	 * 往数据库中添加一条记录
	 * 
	 * @param record
	 */
	void insert(Object record);

	/**
	 * 通过sql往数据库插入记录
	 */
	public void insert(String statement, Object record);

	/**
	 * 更新数据库一条记录
	 * 
	 * @param record
	 * @return
	 */
	int updateByPrimaryKey(Object record);

	/**
	 * 有选择的更新数据库一条记录
	 * 
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeySelective(Object record);

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

	/**
	 * 更新数据库记录
	 * 
	 * @param namespace
	 * @param record
	 * @return
	 */
	int update(String namespace, Object record);

	Object selectByPrimaryKey(Object key);

	int deleteByExample(Object example);

	int deleteByPrimaryKey(Object key);

	int countByExample(Object example);

	public String getSerialNumberOf16BySeqName(String seqName);
	
	public Long getNextValueOfSequence(String sequenceName);

	public int batchInsert(final List<?> parameters);

	public int batchUpdate(final List<?> parameters);

	public int batchInsert(final String namespace, final List<?> parameters);

	public int batchInsert(final String namespace, final String statementName,
			final List<?> parameters);

	public int batchUpdate(final String namespace, final List<?> parameters);

	public int batchUpdate(final String namespace, final String statementName,
			final List<?> parameters);

	public int batchDelete(final List<?> parameters);

	@SuppressWarnings("unchecked")
	public int batchDelete(final String namespace, final String statementName,
			final List parameters);

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

	@SuppressWarnings("unchecked")
	public int deleteByExample(String namespace, Object example);

}
