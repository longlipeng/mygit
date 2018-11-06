package com.huateng.framework.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.huateng.framework.dao.BaseDAO;
import com.ibatis.sqlmap.client.SqlMapExecutor;

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

	public int deleteByExample(Object example) {
		int rows = getSqlMapClientTemplate().delete(
				namespace + "abatorgenerated_deleteByExample", example);
		return rows;
	}

	public int deleteByExample(String namespace, Object example) {
		int rows = getSqlMapClientTemplate().delete(
				namespace + ".abatorgenerated_deleteByExample", example);
		return rows;
	}

	public int deleteByPrimaryKey(Object key) {
		int rows = getSqlMapClientTemplate().delete(
				namespace + "abatorgenerated_deleteByPrimaryKey", key);
		return rows;
	}

	public void insert(Object record) {
		getSqlMapClientTemplate().insert(namespace + "abatorgenerated_insert",
				record);
	}

	public void insert(String statement, Object record) {
		getSqlMapClientTemplate().insert(statement, record);
	}

	public int update(String namespace, Object record) {
		return getSqlMapClientTemplate().update(namespace, record);
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

	public int updateByPrimaryKey(Object record) {
		int rows = getSqlMapClientTemplate().update(
				namespace + "abatorgenerated_updateByPrimaryKey", record);
		return rows;
	}

	public int updateByPrimaryKeySelective(Object record) {
		int rows = getSqlMapClientTemplate().update(
				namespace + "abatorgenerated_updateByPrimaryKeySelective",
				record);
		return rows;
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
		Object result =null;
		try{
			result = getSqlMapClientTemplate().queryForObject(
					namespace + statementName, parameterObject);
		}catch(Exception e){
			e.printStackTrace();
		}
		
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

	@SuppressWarnings("unchecked")
	public int batchInsert(final String namespace, final List parameters) {
		Integer result = (Integer) this.getSqlMapClientTemplate().execute(
				new SqlMapClientCallback() {
					public Object doInSqlMapClient(SqlMapExecutor executor)
							throws SQLException {
						executor.startBatch();
						for (Iterator it = parameters.iterator(); it.hasNext();) {
							executor.insert(namespace
									+ ".abatorgenerated_insert", it.next());
						}
						int result = executor.executeBatch();
						return Integer.valueOf(result);
					}
				});
		return result.intValue();
	}

	@SuppressWarnings("unchecked")
	public int batchInsert(final List parameters) {
		Integer result = (Integer) this.getSqlMapClientTemplate().execute(
				new SqlMapClientCallback() {
					public Object doInSqlMapClient(SqlMapExecutor executor)
							throws SQLException {
						executor.startBatch();
						for (Iterator it = parameters.iterator(); it.hasNext();) {
							executor.insert(namespace
									+ "abatorgenerated_insert", it.next());
						}
						int result = executor.executeBatch();
						return Integer.valueOf(result);
					}
				});
		return result.intValue();
	}

	public int batchInsert(final String namespace, final String statementName,
			final List<?> parameters) {

		Integer result = (Integer) this.getSqlMapClientTemplate().execute(
				new SqlMapClientCallback() {
					public Object doInSqlMapClient(SqlMapExecutor executor)
							throws SQLException {
						executor.startBatch();
						for (Iterator<?> it = parameters.iterator(); it
								.hasNext();) {
							executor.insert(namespace + "." + statementName, it
									.next());
						}
						int result = executor.executeBatch();
						return Integer.valueOf(result);
					}
				});
		return result.intValue();

	}

	public int batchUpdate(final List<?> parameters) {
		Integer result = (Integer) this.getSqlMapClientTemplate().execute(
				new SqlMapClientCallback() {
					public Object doInSqlMapClient(SqlMapExecutor executor)
							throws SQLException {
						executor.startBatch();
						for (Iterator<?> it = parameters.iterator(); it
								.hasNext();) {
							executor
									.update(
											namespace
													+ "abatorgenerated_updateByPrimaryKeySelective",
											it.next());
						}
						int result = executor.executeBatch();
						return Integer.valueOf(result);
					}
				});
		return result.intValue();
	}

	public int batchUpdate(final String namespace, final List<?> parameters) {
		Integer result = (Integer) this.getSqlMapClientTemplate().execute(
				new SqlMapClientCallback() {
					public Object doInSqlMapClient(SqlMapExecutor executor)
							throws SQLException {
						executor.startBatch();
						for (Iterator<?> it = parameters.iterator(); it
								.hasNext();) {
							executor
									.update(
											namespace
													+ ".abatorgenerated_updateByPrimaryKeySelective",
											it.next());
						}
						int result = executor.executeBatch();
						return Integer.valueOf(result);
					}
				});
		return result.intValue();
	}

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
    
	public Long getNextValueOfSequence(String sequenceName) {
		return (Long) getSqlMapClientTemplate().queryForObject(
				"Commons.getNextValueOfSequence", sequenceName);
	}

	public int batchUpdate(final String namespace, final String statementName,
			final List<?> parameters) {
		Integer result = (Integer) this.getSqlMapClientTemplate().execute(
				new SqlMapClientCallback() {
					public Object doInSqlMapClient(SqlMapExecutor executor)
							throws SQLException {
						executor.startBatch();
						for (Iterator<?> it = parameters.iterator(); it
								.hasNext();) {
							executor.update(namespace + "." + statementName, it
									.next());
						}
						int result = executor.executeBatch();
						return Integer.valueOf(result);
					}
				});
		return result.intValue();
	}

	public int batchDelete(final List<?> parameters) {
		Integer result = (Integer) this.getSqlMapClientTemplate().execute(
				new SqlMapClientCallback() {
					public Object doInSqlMapClient(SqlMapExecutor executor)
							throws SQLException {
						executor.startBatch();
						for (Iterator<?> it = parameters.iterator(); it
								.hasNext();) {
							executor.delete(namespace
									+ "abatorgenerated_deleteByPrimaryKey", it
									.next());
						}
						int result = executor.executeBatch();
						return Integer.valueOf(result);
					}
				});
		return result.intValue();
	}

	@SuppressWarnings("unchecked")
	public int batchDelete(final String namespace, final String statementName,
			final List parameters) {
		Integer result = (Integer) this.getSqlMapClientTemplate().execute(
				new SqlMapClientCallback() {
					public Object doInSqlMapClient(SqlMapExecutor executor)
							throws SQLException {
						executor.startBatch();
						for (Iterator<?> it = parameters.iterator(); it
								.hasNext();) {
							executor.delete(namespace + "." + statementName, it
									.next());
						}
						int result = executor.executeBatch();
						return Integer.valueOf(result);
					}
				});
		return result.intValue();
	}

}
