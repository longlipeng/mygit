package com.huateng.framework.ibatis.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.allinfinance.univer.seller.order.dto.SellOrderCardListDTO;
import com.huateng.framework.ibatis.model.SellOrderCardList;
import com.huateng.framework.ibatis.model.SellOrderCardListExample;

public class SellOrderCardListDAOImpl extends SqlMapClientDaoSupport implements
		SellOrderCardListDAO {

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SELL_ORDER_CARD_LIST
	 * 
	 * @abatorgenerated Thu Dec 23 17:11:41 CST 2010
	 */
	public SellOrderCardListDAOImpl() {
		super();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SELL_ORDER_CARD_LIST
	 * 
	 * @abatorgenerated Thu Dec 23 17:11:41 CST 2010
	 */
	public void insert(SellOrderCardList record) {
		getSqlMapClientTemplate().insert(
				"TB_SELL_ORDER_CARD_LIST.abatorgenerated_insert", record);
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SELL_ORDER_CARD_LIST
	 * 
	 * @abatorgenerated Thu Dec 23 17:11:41 CST 2010
	 */
	public int updateByPrimaryKey(SellOrderCardList record) {
		int rows = getSqlMapClientTemplate().update(
				"TB_SELL_ORDER_CARD_LIST.abatorgenerated_updateByPrimaryKey",
				record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SELL_ORDER_CARD_LIST
	 * 
	 * @abatorgenerated Thu Dec 23 17:11:41 CST 2010
	 */
	public int updateByPrimaryKeySelective(SellOrderCardList record) {
		int rows = getSqlMapClientTemplate()
				.update(
						"TB_SELL_ORDER_CARD_LIST.abatorgenerated_updateByPrimaryKeySelective",
						record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SELL_ORDER_CARD_LIST
	 * 
	 * @abatorgenerated Thu Dec 23 17:11:41 CST 2010
	 */
	@SuppressWarnings("unchecked")
	public List<SellOrderCardList> selectByExample(
			SellOrderCardListExample example) {
		List<SellOrderCardList> list = (List<SellOrderCardList>) getSqlMapClientTemplate()
				.queryForList(
						"TB_SELL_ORDER_CARD_LIST.abatorgenerated_selectByExample",
						example);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SELL_ORDER_CARD_LIST
	 * 
	 * @abatorgenerated Thu Dec 23 17:11:41 CST 2010
	 */
	public SellOrderCardList selectByPrimaryKey(String orderCardListId) {
		SellOrderCardList key = new SellOrderCardList();
		key.setOrderCardListId(orderCardListId);
		SellOrderCardList record = (SellOrderCardList) getSqlMapClientTemplate()
				.queryForObject(
						"TB_SELL_ORDER_CARD_LIST.abatorgenerated_selectByPrimaryKey",
						key);
		return record;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SELL_ORDER_CARD_LIST
	 * 
	 * @abatorgenerated Thu Dec 23 17:11:41 CST 2010
	 */
	public int deleteByExample(SellOrderCardListExample example) {
		int rows = getSqlMapClientTemplate().delete(
				"TB_SELL_ORDER_CARD_LIST.abatorgenerated_deleteByExample",
				example);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SELL_ORDER_CARD_LIST
	 * 
	 * @abatorgenerated Thu Dec 23 17:11:41 CST 2010
	 */
	public int deleteByPrimaryKey(String orderCardListId) {
		SellOrderCardList key = new SellOrderCardList();
		key.setOrderCardListId(orderCardListId);
		int rows = getSqlMapClientTemplate().delete(
				"TB_SELL_ORDER_CARD_LIST.abatorgenerated_deleteByPrimaryKey",
				key);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SELL_ORDER_CARD_LIST
	 * 
	 * @abatorgenerated Thu Dec 23 17:11:41 CST 2010
	 */
	public int countByExample(SellOrderCardListExample example) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(
				"TB_SELL_ORDER_CARD_LIST.abatorgenerated_countByExample",
				example);
		return count;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SELL_ORDER_CARD_LIST
	 * 
	 * @abatorgenerated Thu Dec 23 17:11:41 CST 2010
	 */
	public int updateByExampleSelective(SellOrderCardList record,
			SellOrderCardListExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate()
				.update(
						"TB_SELL_ORDER_CARD_LIST.abatorgenerated_updateByExampleSelective",
						parms);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SELL_ORDER_CARD_LIST
	 * 
	 * @abatorgenerated Thu Dec 23 17:11:41 CST 2010
	 */
	public int updateByExample(SellOrderCardList record,
			SellOrderCardListExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate().update(
				"TB_SELL_ORDER_CARD_LIST.abatorgenerated_updateByExample",
				parms);
		return rows;
	}

	public List<SellOrderCardListDTO> selectCardDetailForSignOrderByOrderId(
			String orderId) {
		List<SellOrderCardListDTO> tempList = (List<SellOrderCardListDTO>) getSqlMapClientTemplate()
				.queryForList(
						"TB_SELL_ORDER_CARD_LIST.selectCardDetailForSignOrder",
						orderId);
		return tempList;
	}

	/**
	 * This class was generated by Abator for iBATIS. This class corresponds to
	 * the database table TB_SELL_ORDER_CARD_LIST
	 * 
	 * @abatorgenerated Thu Dec 23 17:11:41 CST 2010
	 */
	private static class UpdateByExampleParms extends SellOrderCardListExample {
		private Object record;

		public UpdateByExampleParms(Object record,
				SellOrderCardListExample example) {
			super(example);
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}
}