package com.huateng.framework.ibatis.dao;

import com.huateng.framework.ibatis.model.OrderPackage;
import com.huateng.framework.ibatis.model.OrderPackageExample;
import com.huateng.framework.ibatis.model.OrderPackageKey;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class OrderPackageDAOImpl extends SqlMapClientDaoSupport implements
		OrderPackageDAO {

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_ORDER_PACKAGE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:18 CST 2010
	 */
	public OrderPackageDAOImpl() {
		super();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_ORDER_PACKAGE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:18 CST 2010
	 */
	public void insert(OrderPackage record) {
		getSqlMapClientTemplate().insert(
				"TB_REL_ORDER_PACKAGE.abatorgenerated_insert", record);
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_ORDER_PACKAGE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:18 CST 2010
	 */
	public int updateByPrimaryKey(OrderPackage record) {
		int rows = getSqlMapClientTemplate().update(
				"TB_REL_ORDER_PACKAGE.abatorgenerated_updateByPrimaryKey",
				record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_ORDER_PACKAGE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:18 CST 2010
	 */
	public int updateByPrimaryKeySelective(OrderPackage record) {
		int rows = getSqlMapClientTemplate()
				.update(
						"TB_REL_ORDER_PACKAGE.abatorgenerated_updateByPrimaryKeySelective",
						record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_ORDER_PACKAGE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:18 CST 2010
	 */
	@SuppressWarnings("unchecked")
	public List<OrderPackage> selectByExample(OrderPackageExample example) {
		List<OrderPackage> list = (List<OrderPackage>) getSqlMapClientTemplate()
				.queryForList(
						"TB_REL_ORDER_PACKAGE.abatorgenerated_selectByExample",
						example);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_ORDER_PACKAGE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:18 CST 2010
	 */
	public OrderPackage selectByPrimaryKey(OrderPackageKey key) {
		OrderPackage record = (OrderPackage) getSqlMapClientTemplate()
				.queryForObject(
						"TB_REL_ORDER_PACKAGE.abatorgenerated_selectByPrimaryKey",
						key);
		return record;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_ORDER_PACKAGE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:18 CST 2010
	 */
	public int deleteByExample(OrderPackageExample example) {
		int rows = getSqlMapClientTemplate()
				.delete("TB_REL_ORDER_PACKAGE.abatorgenerated_deleteByExample",
						example);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_ORDER_PACKAGE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:18 CST 2010
	 */
	public int deleteByPrimaryKey(OrderPackageKey key) {
		int rows = getSqlMapClientTemplate().delete(
				"TB_REL_ORDER_PACKAGE.abatorgenerated_deleteByPrimaryKey", key);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_ORDER_PACKAGE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:18 CST 2010
	 */
	public int countByExample(OrderPackageExample example) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(
				"TB_REL_ORDER_PACKAGE.abatorgenerated_countByExample", example);
		return count;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_ORDER_PACKAGE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:18 CST 2010
	 */
	public int updateByExampleSelective(OrderPackage record,
			OrderPackageExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate()
				.update(
						"TB_REL_ORDER_PACKAGE.abatorgenerated_updateByExampleSelective",
						parms);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_ORDER_PACKAGE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:18 CST 2010
	 */
	public int updateByExample(OrderPackage record, OrderPackageExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate().update(
				"TB_REL_ORDER_PACKAGE.abatorgenerated_updateByExample", parms);
		return rows;
	}

	/**
	 * This class was generated by Abator for iBATIS. This class corresponds to
	 * the database table TB_REL_ORDER_PACKAGE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:18 CST 2010
	 */
	private static class UpdateByExampleParms extends OrderPackageExample {
		private Object record;

		public UpdateByExampleParms(Object record, OrderPackageExample example) {
			super(example);
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}
}