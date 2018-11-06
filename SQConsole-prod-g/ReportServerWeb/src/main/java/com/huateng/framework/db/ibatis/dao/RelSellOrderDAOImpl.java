package com.huateng.framework.db.ibatis.dao;

import com.huateng.framework.db.ibatis.model.RelSellOrder;
import com.huateng.framework.db.ibatis.model.RelSellOrderExample;
import com.huateng.framework.db.ibatis.model.RelSellOrderKey;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class RelSellOrderDAOImpl extends SqlMapClientDaoSupport implements
		RelSellOrderDAO {

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_SELL_ORDER
	 * 
	 * @abatorgenerated Sun Sep 26 16:12:41 CST 2010
	 */
	public RelSellOrderDAOImpl() {
		super();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_SELL_ORDER
	 * 
	 * @abatorgenerated Sun Sep 26 16:12:41 CST 2010
	 */
	public void insert(RelSellOrder record) {
		getSqlMapClientTemplate().insert(
				"TB_REL_SELL_ORDER.abatorgenerated_insert", record);
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_SELL_ORDER
	 * 
	 * @abatorgenerated Sun Sep 26 16:12:41 CST 2010
	 */
	public int updateByPrimaryKey(RelSellOrder record) {
		int rows = getSqlMapClientTemplate().update(
				"TB_REL_SELL_ORDER.abatorgenerated_updateByPrimaryKey", record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_SELL_ORDER
	 * 
	 * @abatorgenerated Sun Sep 26 16:12:41 CST 2010
	 */
	public int updateByPrimaryKeySelective(RelSellOrder record) {
		int rows = getSqlMapClientTemplate()
				.update(
						"TB_REL_SELL_ORDER.abatorgenerated_updateByPrimaryKeySelective",
						record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_SELL_ORDER
	 * 
	 * @abatorgenerated Sun Sep 26 16:12:41 CST 2010
	 */
	@SuppressWarnings("unchecked")
	public List<RelSellOrder> selectByExample(RelSellOrderExample example) {
		List<RelSellOrder> list = (List<RelSellOrder>) getSqlMapClientTemplate()
				.queryForList(
						"TB_REL_SELL_ORDER.abatorgenerated_selectByExample",
						example);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_SELL_ORDER
	 * 
	 * @abatorgenerated Sun Sep 26 16:12:41 CST 2010
	 */
	public RelSellOrder selectByPrimaryKey(RelSellOrderKey key) {
		RelSellOrder record = (RelSellOrder) getSqlMapClientTemplate()
				.queryForObject(
						"TB_REL_SELL_ORDER.abatorgenerated_selectByPrimaryKey",
						key);
		return record;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_SELL_ORDER
	 * 
	 * @abatorgenerated Sun Sep 26 16:12:41 CST 2010
	 */
	public int deleteByExample(RelSellOrderExample example) {
		int rows = getSqlMapClientTemplate().delete(
				"TB_REL_SELL_ORDER.abatorgenerated_deleteByExample", example);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_SELL_ORDER
	 * 
	 * @abatorgenerated Sun Sep 26 16:12:41 CST 2010
	 */
	public int deleteByPrimaryKey(RelSellOrderKey key) {
		int rows = getSqlMapClientTemplate().delete(
				"TB_REL_SELL_ORDER.abatorgenerated_deleteByPrimaryKey", key);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_SELL_ORDER
	 * 
	 * @abatorgenerated Sun Sep 26 16:12:41 CST 2010
	 */
	public int countByExample(RelSellOrderExample example) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(
				"TB_REL_SELL_ORDER.abatorgenerated_countByExample", example);
		return count;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_SELL_ORDER
	 * 
	 * @abatorgenerated Sun Sep 26 16:12:41 CST 2010
	 */
	public int updateByExampleSelective(RelSellOrder record,
			RelSellOrderExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate().update(
				"TB_REL_SELL_ORDER.abatorgenerated_updateByExampleSelective",
				parms);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_SELL_ORDER
	 * 
	 * @abatorgenerated Sun Sep 26 16:12:41 CST 2010
	 */
	public int updateByExample(RelSellOrder record, RelSellOrderExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate().update(
				"TB_REL_SELL_ORDER.abatorgenerated_updateByExample", parms);
		return rows;
	}

	/**
	 * This class was generated by Abator for iBATIS. This class corresponds to
	 * the database table TB_REL_SELL_ORDER
	 * 
	 * @abatorgenerated Sun Sep 26 16:12:41 CST 2010
	 */
	private static class UpdateByExampleParms extends RelSellOrderExample {
		private Object record;

		public UpdateByExampleParms(Object record, RelSellOrderExample example) {
			super(example);
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}
}