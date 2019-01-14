package com.huateng.framework.db.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.huateng.framework.db.dao.BaseDAO;

public class BaseDAOIbatisImpl extends SqlMapClientDaoSupport implements
		BaseDAO {

	protected String namespace = "";

	protected Logger logger = Logger.getLogger(getClass());

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace + ".";
	}

	public int countByExample(Object example) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(
				namespace + "abatorgenerated_countByExample", example);
		return count;
	}

	@SuppressWarnings("unchecked")
	public List selectByExample(Object example) {
		List list = getSqlMapClientTemplate().queryForList(
				namespace + "abatorgenerated_selectByExample", example);
		return list;
	}

	@SuppressWarnings("unchecked")
	public List selectByExample(final String namespace,
			final String statementName, Object example) {
		List list = getSqlMapClientTemplate().queryForList(
				namespace + "." + statementName, example);
		return list;
	}

	public Object selectByPrimaryKey(Object key) {
		Object record = getSqlMapClientTemplate().queryForObject(
				namespace + "abatorgenerated_selectByPrimaryKey", key);
		return record;
	}


	@SuppressWarnings("unchecked")
	public List queryForList(final String statementName,
			final Object parameterObject) {
		List result = getSqlMapClientTemplate().queryForList(
				namespace + statementName, parameterObject);
		return result;
	}

	public Object queryForObject(final String statementName,
			final Object parameterObject) {
		Object result = getSqlMapClientTemplate().queryForObject(
				namespace + statementName, parameterObject);
		return result;
	}

	public Object queryForObject(final String nameSpace,
			final String statementName, final Object parameterObject) {
		Object result = getSqlMapClientTemplate().queryForObject(
				nameSpace + "." + statementName, parameterObject);
		return result;
	}

	@SuppressWarnings("unchecked")
	public List queryForList(final String namespace,
			final String statementName, final Object parameterObject) {
		List result = getSqlMapClientTemplate().queryForList(
				namespace + "." + statementName, parameterObject);
		return result;
	}

	@SuppressWarnings("unchecked")
	public List queryForList(final String statementName,
			final Object parameterObject, final int skipResults,
			final int maxResults) {
		List result = getSqlMapClientTemplate().queryForList(
				namespace + statementName, parameterObject, skipResults,
				maxResults);
		return result;
	}

	@SuppressWarnings("unchecked")
	public Map queryForMap(final String statementName,
			final Object parameterObject, final String keyProperty) {
		return getSqlMapClientTemplate().queryForMap(namespace + statementName,
				parameterObject, keyProperty);
	}

}
