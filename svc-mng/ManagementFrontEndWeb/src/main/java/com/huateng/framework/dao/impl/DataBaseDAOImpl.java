package com.huateng.framework.dao.impl;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.huateng.framework.dao.DataBaseDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.TableSerialNumberDAO;
import com.huateng.framework.ibatis.model.TableSerialNumber;
import com.ibatis.sqlmap.client.SqlMapExecutor;

public class DataBaseDAOImpl extends SqlMapClientDaoSupport implements
		DataBaseDAO {
	private TableSerialNumberDAO tableSerialNumberDAO;

	public TableSerialNumberDAO getTableSerialNumberDAO() {
		return tableSerialNumberDAO;
	}

	public void setTableSerialNumberDAO(
			TableSerialNumberDAO tableSerialNumberDAO) {
		this.tableSerialNumberDAO = tableSerialNumberDAO;
	}

	@Override
	public String getNextValueOfSequence(String tableName)
			throws BizServiceException {
		Long serialNumber = 0L;
		try {
			serialNumber = (Long) this.getSqlMapClientTemplate()
					.queryForObject("tableSerialNumber.selectSerialNumber",
							tableName);
			/***
			 * 获取当前流水ID
			 */
			serialNumber++;
			TableSerialNumber tableSerialNumber = new TableSerialNumber();
			tableSerialNumber.setSerialNumber(serialNumber + "");
			tableSerialNumber.setTableName(tableName.toUpperCase());
			tableSerialNumberDAO.updateByPrimaryKeySelective(tableSerialNumber);
			return (serialNumber - 1) + "";
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("获取流水号失败!");
		}
	}

	@Override
	public int batchInsert(final String statementId, final List parameters) {
		Integer result = (Integer) this.getSqlMapClientTemplate().execute(
				new SqlMapClientCallback() {
					public Object doInSqlMapClient(SqlMapExecutor executor)
							throws SQLException {
						executor.startBatch();
						for (Iterator it = parameters.iterator(); it.hasNext();) {
							executor.insert(statementId, it.next());
						}
						int result = executor.executeBatch();
						return new Integer(result);
					}
				});
		return result.intValue();
	}

	@Override
	public String getNextValueOfSequenceBySequence(String sequence)
			throws BizServiceException {
		Long serialNumber = 0L;
		try {
			serialNumber = (Long) this.getSqlMapClientTemplate()
					.queryForObject("Commons.getNextValueOfSequence", sequence);
			return serialNumber.toString();
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("获取流水号失败!");
		}
	}

	@Override
	public int batchUpdate(final String statementId, final List parameters) {
		Integer result = (Integer) this.getSqlMapClientTemplate().execute(
				new SqlMapClientCallback() {
					public Object doInSqlMapClient(SqlMapExecutor executor)
							throws SQLException {
						executor.startBatch();
						for (Iterator it = parameters.iterator(); it.hasNext();) {
							executor.update(statementId, it.next());
						}
						int result = executor.executeBatch();
						return new Integer(result);
					}
				});
		return result.intValue();
	}

	@Override
	public Object queryForObject(final String nameSpace,
			final String statementName, final Object parameterObject) {
		Object result = getSqlMapClientTemplate().queryForObject(
				nameSpace + "." + statementName, parameterObject);
		return result;
	}
	
	/**
	 * 通用查询方法
	 */
	public List<?> queryForList(final String statementName, final Object parameterObject){
		List<?> result = this.getSqlMapClientTemplate().queryForList(statementName,parameterObject);
		return result;	
	}
}
