package com.huateng.framework.db.ibatis.dao;

import com.huateng.framework.db.ibatis.model.SellOrderPayment;
import com.huateng.framework.db.ibatis.model.SellOrderPaymentExample;
import com.huateng.framework.db.ibatis.model.SellOrderPaymentKey;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class SellOrderPaymentDAOImpl extends SqlMapClientDaoSupport implements
		SellOrderPaymentDAO {

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SELL_ORDER_PAYMENT
	 * 
	 * @abatorgenerated Thu Mar 29 16:35:26 CST 2012
	 */
	public SellOrderPaymentDAOImpl() {
		super();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SELL_ORDER_PAYMENT
	 * 
	 * @abatorgenerated Thu Mar 29 16:35:26 CST 2012
	 */
	public void insert(SellOrderPayment record) {
		getSqlMapClientTemplate().insert(
				"TB_SELL_ORDER_PAYMENT.abatorgenerated_insert", record);
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SELL_ORDER_PAYMENT
	 * 
	 * @abatorgenerated Thu Mar 29 16:35:26 CST 2012
	 */
	public int updateByPrimaryKey(SellOrderPayment record) {
		int rows = getSqlMapClientTemplate().update(
				"TB_SELL_ORDER_PAYMENT.abatorgenerated_updateByPrimaryKey",
				record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SELL_ORDER_PAYMENT
	 * 
	 * @abatorgenerated Thu Mar 29 16:35:26 CST 2012
	 */
	public int updateByPrimaryKeySelective(SellOrderPayment record) {
		int rows = getSqlMapClientTemplate()
				.update(
						"TB_SELL_ORDER_PAYMENT.abatorgenerated_updateByPrimaryKeySelective",
						record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SELL_ORDER_PAYMENT
	 * 
	 * @abatorgenerated Thu Mar 29 16:35:26 CST 2012
	 */
	@SuppressWarnings("unchecked")
	public List<SellOrderPayment> selectByExample(
			SellOrderPaymentExample example) {
		List<SellOrderPayment> list = (List<SellOrderPayment>) getSqlMapClientTemplate()
				.queryForList(
						"TB_SELL_ORDER_PAYMENT.abatorgenerated_selectByExample",
						example);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SELL_ORDER_PAYMENT
	 * 
	 * @abatorgenerated Thu Mar 29 16:35:26 CST 2012
	 */
	public SellOrderPayment selectByPrimaryKey(SellOrderPaymentKey key) {
		SellOrderPayment record = (SellOrderPayment) getSqlMapClientTemplate()
				.queryForObject(
						"TB_SELL_ORDER_PAYMENT.abatorgenerated_selectByPrimaryKey",
						key);
		return record;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SELL_ORDER_PAYMENT
	 * 
	 * @abatorgenerated Thu Mar 29 16:35:26 CST 2012
	 */
	public int deleteByExample(SellOrderPaymentExample example) {
		int rows = getSqlMapClientTemplate().delete(
				"TB_SELL_ORDER_PAYMENT.abatorgenerated_deleteByExample",
				example);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SELL_ORDER_PAYMENT
	 * 
	 * @abatorgenerated Thu Mar 29 16:35:26 CST 2012
	 */
	public int deleteByPrimaryKey(SellOrderPaymentKey key) {
		int rows = getSqlMapClientTemplate()
				.delete(
						"TB_SELL_ORDER_PAYMENT.abatorgenerated_deleteByPrimaryKey",
						key);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SELL_ORDER_PAYMENT
	 * 
	 * @abatorgenerated Thu Mar 29 16:35:26 CST 2012
	 */
	public int countByExample(SellOrderPaymentExample example) {
		Integer count = (Integer) getSqlMapClientTemplate()
				.queryForObject(
						"TB_SELL_ORDER_PAYMENT.abatorgenerated_countByExample",
						example);
		return count;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SELL_ORDER_PAYMENT
	 * 
	 * @abatorgenerated Thu Mar 29 16:35:26 CST 2012
	 */
	public int updateByExampleSelective(SellOrderPayment record,
			SellOrderPaymentExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate()
				.update(
						"TB_SELL_ORDER_PAYMENT.abatorgenerated_updateByExampleSelective",
						parms);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SELL_ORDER_PAYMENT
	 * 
	 * @abatorgenerated Thu Mar 29 16:35:26 CST 2012
	 */
	public int updateByExample(SellOrderPayment record,
			SellOrderPaymentExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate().update(
				"TB_SELL_ORDER_PAYMENT.abatorgenerated_updateByExample", parms);
		return rows;
	}

	/**
	 * This class was generated by Abator for iBATIS. This class corresponds to
	 * the database table TB_SELL_ORDER_PAYMENT
	 * 
	 * @abatorgenerated Thu Mar 29 16:35:26 CST 2012
	 */
	private static class UpdateByExampleParms extends SellOrderPaymentExample {
		private Object record;

		public UpdateByExampleParms(Object record,
				SellOrderPaymentExample example) {
			super(example);
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}
}