package com.huateng.framework.db.ibatis.dao;

import com.huateng.framework.db.ibatis.model.SellOrderList;
import com.huateng.framework.db.ibatis.model.SellOrderListExample;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class SellOrderListDAOImpl extends SqlMapClientDaoSupport implements
		SellOrderListDAO {

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SELL_ORDER_LIST
	 * 
	 * @abatorgenerated Tue Dec 14 10:30:41 CST 2010
	 */
	public SellOrderListDAOImpl() {
		super();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SELL_ORDER_LIST
	 * 
	 * @abatorgenerated Tue Dec 14 10:30:41 CST 2010
	 */
	public void insert(SellOrderList record) {
		getSqlMapClientTemplate().insert(
				"TB_SELL_ORDER_LIST.abatorgenerated_insert", record);
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SELL_ORDER_LIST
	 * 
	 * @abatorgenerated Tue Dec 14 10:30:41 CST 2010
	 */
	public int updateByPrimaryKey(SellOrderList record) {
		int rows = getSqlMapClientTemplate()
				.update(
						"TB_SELL_ORDER_LIST.abatorgenerated_updateByPrimaryKey",
						record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SELL_ORDER_LIST
	 * 
	 * @abatorgenerated Tue Dec 14 10:30:41 CST 2010
	 */
	public int updateByPrimaryKeySelective(SellOrderList record) {
		int rows = getSqlMapClientTemplate()
				.update(
						"TB_SELL_ORDER_LIST.abatorgenerated_updateByPrimaryKeySelective",
						record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SELL_ORDER_LIST
	 * 
	 * @abatorgenerated Tue Dec 14 10:30:41 CST 2010
	 */
	@SuppressWarnings("unchecked")
	public List<SellOrderList> selectByExample(SellOrderListExample example) {
		List<SellOrderList> list = (List<SellOrderList>) getSqlMapClientTemplate()
				.queryForList(
						"TB_SELL_ORDER_LIST.abatorgenerated_selectByExample",
						example);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SELL_ORDER_LIST
	 * 
	 * @abatorgenerated Tue Dec 14 10:30:41 CST 2010
	 */
	public SellOrderList selectByPrimaryKey(String orderListId) {
		SellOrderList key = new SellOrderList();
		key.setOrderListId(orderListId);
		SellOrderList record = (SellOrderList) getSqlMapClientTemplate()
				.queryForObject(
						"TB_SELL_ORDER_LIST.abatorgenerated_selectByPrimaryKey",
						key);
		return record;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SELL_ORDER_LIST
	 * 
	 * @abatorgenerated Tue Dec 14 10:30:41 CST 2010
	 */
	public int deleteByExample(SellOrderListExample example) {
		int rows = getSqlMapClientTemplate().delete(
				"TB_SELL_ORDER_LIST.abatorgenerated_deleteByExample", example);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SELL_ORDER_LIST
	 * 
	 * @abatorgenerated Tue Dec 14 10:30:41 CST 2010
	 */
	public int deleteByPrimaryKey(String orderListId) {
		SellOrderList key = new SellOrderList();
		key.setOrderListId(orderListId);
		int rows = getSqlMapClientTemplate().delete(
				"TB_SELL_ORDER_LIST.abatorgenerated_deleteByPrimaryKey", key);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SELL_ORDER_LIST
	 * 
	 * @abatorgenerated Tue Dec 14 10:30:41 CST 2010
	 */
	public int countByExample(SellOrderListExample example) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(
				"TB_SELL_ORDER_LIST.abatorgenerated_countByExample", example);
		return count;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SELL_ORDER_LIST
	 * 
	 * @abatorgenerated Tue Dec 14 10:30:41 CST 2010
	 */
	public int updateByExampleSelective(SellOrderList record,
			SellOrderListExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate().update(
				"TB_SELL_ORDER_LIST.abatorgenerated_updateByExampleSelective",
				parms);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SELL_ORDER_LIST
	 * 
	 * @abatorgenerated Tue Dec 14 10:30:41 CST 2010
	 */
	public int updateByExample(SellOrderList record,
			SellOrderListExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate().update(
				"TB_SELL_ORDER_LIST.abatorgenerated_updateByExample", parms);
		return rows;
	}

	/**
	 * This class was generated by Abator for iBATIS. This class corresponds to
	 * the database table TB_SELL_ORDER_LIST
	 * 
	 * @abatorgenerated Tue Dec 14 10:30:41 CST 2010
	 */
	private static class UpdateByExampleParms extends SellOrderListExample {
		private Object record;

		public UpdateByExampleParms(Object record, SellOrderListExample example) {
			super(example);
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}
}