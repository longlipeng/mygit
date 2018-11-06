package com.huateng.framework.ibatis.dao;

import com.huateng.framework.ibatis.model.OrderBatchFile;
import com.huateng.framework.ibatis.model.OrderBatchFileExample;
import com.huateng.framework.ibatis.model.OrderBatchFileKey;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class OrderBatchFileDAOImpl extends SqlMapClientDaoSupport implements
		OrderBatchFileDAO {

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ORDER_BATCH_FILE
	 * 
	 * @abatorgenerated Fri Jan 07 21:49:50 CST 2011
	 */
	public OrderBatchFileDAOImpl() {
		super();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ORDER_BATCH_FILE
	 * 
	 * @abatorgenerated Fri Jan 07 21:49:50 CST 2011
	 */
	public void insert(OrderBatchFile record) {
		getSqlMapClientTemplate().insert(
				"TB_ORDER_BATCH_FILE.abatorgenerated_insert", record);
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ORDER_BATCH_FILE
	 * 
	 * @abatorgenerated Fri Jan 07 21:49:50 CST 2011
	 */
	public int updateByPrimaryKey(OrderBatchFile record) {
		int rows = getSqlMapClientTemplate().update(
				"TB_ORDER_BATCH_FILE.abatorgenerated_updateByPrimaryKey",
				record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ORDER_BATCH_FILE
	 * 
	 * @abatorgenerated Fri Jan 07 21:49:50 CST 2011
	 */
	public int updateByPrimaryKeySelective(OrderBatchFile record) {
		int rows = getSqlMapClientTemplate()
				.update(
						"TB_ORDER_BATCH_FILE.abatorgenerated_updateByPrimaryKeySelective",
						record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ORDER_BATCH_FILE
	 * 
	 * @abatorgenerated Fri Jan 07 21:49:50 CST 2011
	 */
	@SuppressWarnings("unchecked")
	public List<OrderBatchFile> selectByExample(OrderBatchFileExample example) {
		List<OrderBatchFile> list = (List<OrderBatchFile>) getSqlMapClientTemplate()
				.queryForList(
						"TB_ORDER_BATCH_FILE.abatorgenerated_selectByExample",
						example);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ORDER_BATCH_FILE
	 * 
	 * @abatorgenerated Fri Jan 07 21:49:50 CST 2011
	 */
	public OrderBatchFile selectByPrimaryKey(OrderBatchFileKey key) {
		OrderBatchFile record = (OrderBatchFile) getSqlMapClientTemplate()
				.queryForObject(
						"TB_ORDER_BATCH_FILE.abatorgenerated_selectByPrimaryKey",
						key);
		return record;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ORDER_BATCH_FILE
	 * 
	 * @abatorgenerated Fri Jan 07 21:49:50 CST 2011
	 */
	public int deleteByExample(OrderBatchFileExample example) {
		int rows = getSqlMapClientTemplate().delete(
				"TB_ORDER_BATCH_FILE.abatorgenerated_deleteByExample", example);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ORDER_BATCH_FILE
	 * 
	 * @abatorgenerated Fri Jan 07 21:49:50 CST 2011
	 */
	public int deleteByPrimaryKey(OrderBatchFileKey key) {
		int rows = getSqlMapClientTemplate().delete(
				"TB_ORDER_BATCH_FILE.abatorgenerated_deleteByPrimaryKey", key);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ORDER_BATCH_FILE
	 * 
	 * @abatorgenerated Fri Jan 07 21:49:50 CST 2011
	 */
	public int countByExample(OrderBatchFileExample example) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(
				"TB_ORDER_BATCH_FILE.abatorgenerated_countByExample", example);
		return count;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ORDER_BATCH_FILE
	 * 
	 * @abatorgenerated Fri Jan 07 21:49:50 CST 2011
	 */
	public int updateByExampleSelective(OrderBatchFile record,
			OrderBatchFileExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate().update(
				"TB_ORDER_BATCH_FILE.abatorgenerated_updateByExampleSelective",
				parms);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ORDER_BATCH_FILE
	 * 
	 * @abatorgenerated Fri Jan 07 21:49:50 CST 2011
	 */
	public int updateByExample(OrderBatchFile record,
			OrderBatchFileExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate().update(
				"TB_ORDER_BATCH_FILE.abatorgenerated_updateByExample", parms);
		return rows;
	}

	/**
	 * This class was generated by Abator for iBATIS. This class corresponds to
	 * the database table TB_ORDER_BATCH_FILE
	 * 
	 * @abatorgenerated Fri Jan 07 21:49:50 CST 2011
	 */
	private static class UpdateByExampleParms extends OrderBatchFileExample {
		private Object record;

		public UpdateByExampleParms(Object record, OrderBatchFileExample example) {
			super(example);
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}
}