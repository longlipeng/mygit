package com.huateng.framework.ibatis.dao;

import com.huateng.framework.ibatis.model.TableSerialNumber;
import com.huateng.framework.ibatis.model.TableSerialNumberExample;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class TableSerialNumberDAOImpl extends SqlMapClientDaoSupport implements
		TableSerialNumberDAO {

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_TABLE_SERIAL_NUMBER
	 * 
	 * @abatorgenerated Sun Sep 26 15:57:08 CST 2010
	 */
	public TableSerialNumberDAOImpl() {
		super();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_TABLE_SERIAL_NUMBER
	 * 
	 * @abatorgenerated Sun Sep 26 15:57:08 CST 2010
	 */
	public void insert(TableSerialNumber record) {
		getSqlMapClientTemplate().insert(
				"TB_TABLE_SERIAL_NUMBER.abatorgenerated_insert", record);
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_TABLE_SERIAL_NUMBER
	 * 
	 * @abatorgenerated Sun Sep 26 15:57:08 CST 2010
	 */
	public int updateByPrimaryKey(TableSerialNumber record) {
		int rows = getSqlMapClientTemplate().update(
				"TB_TABLE_SERIAL_NUMBER.abatorgenerated_updateByPrimaryKey",
				record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_TABLE_SERIAL_NUMBER
	 * 
	 * @abatorgenerated Sun Sep 26 15:57:08 CST 2010
	 */
	public int updateByPrimaryKeySelective(TableSerialNumber record) {
		int rows = getSqlMapClientTemplate()
				.update(
						"TB_TABLE_SERIAL_NUMBER.abatorgenerated_updateByPrimaryKeySelective",
						record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_TABLE_SERIAL_NUMBER
	 * 
	 * @abatorgenerated Sun Sep 26 15:57:08 CST 2010
	 */
	@SuppressWarnings("unchecked")
	public List<TableSerialNumber> selectByExample(
			TableSerialNumberExample example) {
		List<TableSerialNumber> list = (List<TableSerialNumber>) getSqlMapClientTemplate()
				.queryForList(
						"TB_TABLE_SERIAL_NUMBER.abatorgenerated_selectByExample",
						example);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_TABLE_SERIAL_NUMBER
	 * 
	 * @abatorgenerated Sun Sep 26 15:57:08 CST 2010
	 */
	public TableSerialNumber selectByPrimaryKey(String tableName) {
		TableSerialNumber key = new TableSerialNumber();
		key.setTableName(tableName);
		TableSerialNumber record = (TableSerialNumber) getSqlMapClientTemplate()
				.queryForObject(
						"TB_TABLE_SERIAL_NUMBER.abatorgenerated_selectByPrimaryKey",
						key);
		return record;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_TABLE_SERIAL_NUMBER
	 * 
	 * @abatorgenerated Sun Sep 26 15:57:08 CST 2010
	 */
	public int deleteByExample(TableSerialNumberExample example) {
		int rows = getSqlMapClientTemplate().delete(
				"TB_TABLE_SERIAL_NUMBER.abatorgenerated_deleteByExample",
				example);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_TABLE_SERIAL_NUMBER
	 * 
	 * @abatorgenerated Sun Sep 26 15:57:08 CST 2010
	 */
	public int deleteByPrimaryKey(String tableName) {
		TableSerialNumber key = new TableSerialNumber();
		key.setTableName(tableName);
		int rows = getSqlMapClientTemplate().delete(
				"TB_TABLE_SERIAL_NUMBER.abatorgenerated_deleteByPrimaryKey",
				key);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_TABLE_SERIAL_NUMBER
	 * 
	 * @abatorgenerated Sun Sep 26 15:57:08 CST 2010
	 */
	public int countByExample(TableSerialNumberExample example) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(
				"TB_TABLE_SERIAL_NUMBER.abatorgenerated_countByExample",
				example);
		return count;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_TABLE_SERIAL_NUMBER
	 * 
	 * @abatorgenerated Sun Sep 26 15:57:08 CST 2010
	 */
	public int updateByExampleSelective(TableSerialNumber record,
			TableSerialNumberExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate()
				.update(
						"TB_TABLE_SERIAL_NUMBER.abatorgenerated_updateByExampleSelective",
						parms);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_TABLE_SERIAL_NUMBER
	 * 
	 * @abatorgenerated Sun Sep 26 15:57:08 CST 2010
	 */
	public int updateByExample(TableSerialNumber record,
			TableSerialNumberExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate()
				.update(
						"TB_TABLE_SERIAL_NUMBER.abatorgenerated_updateByExample",
						parms);
		return rows;
	}

	/**
	 * This class was generated by Abator for iBATIS. This class corresponds to
	 * the database table TB_TABLE_SERIAL_NUMBER
	 * 
	 * @abatorgenerated Sun Sep 26 15:57:08 CST 2010
	 */
	private static class UpdateByExampleParms extends TableSerialNumberExample {
		private Object record;

		public UpdateByExampleParms(Object record,
				TableSerialNumberExample example) {
			super(example);
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}
}