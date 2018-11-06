package com.huateng.framework.ibatis.dao;

import com.huateng.framework.ibatis.model.LoyaltyAcctypeContract;
import com.huateng.framework.ibatis.model.LoyaltyAcctypeContractExample;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class LoyaltyAcctypeContractDAOImpl extends SqlMapClientDaoSupport
		implements LoyaltyAcctypeContractDAO {

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_LOYALTY_ACCTYPE_CONTRACT
	 * 
	 * @abatorgenerated Thu Nov 18 15:31:30 CST 2010
	 */
	public LoyaltyAcctypeContractDAOImpl() {
		super();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_LOYALTY_ACCTYPE_CONTRACT
	 * 
	 * @abatorgenerated Thu Nov 18 15:31:30 CST 2010
	 */
	public void insert(LoyaltyAcctypeContract record) {
		getSqlMapClientTemplate().insert(
				"TB_LOYALTY_ACCTYPE_CONTRACT.abatorgenerated_insert", record);
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_LOYALTY_ACCTYPE_CONTRACT
	 * 
	 * @abatorgenerated Thu Nov 18 15:31:30 CST 2010
	 */
	public int updateByPrimaryKey(LoyaltyAcctypeContract record) {
		int rows = getSqlMapClientTemplate()
				.update(
						"TB_LOYALTY_ACCTYPE_CONTRACT.abatorgenerated_updateByPrimaryKey",
						record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_LOYALTY_ACCTYPE_CONTRACT
	 * 
	 * @abatorgenerated Thu Nov 18 15:31:30 CST 2010
	 */
	public int updateByPrimaryKeySelective(LoyaltyAcctypeContract record) {
		int rows = getSqlMapClientTemplate()
				.update(
						"TB_LOYALTY_ACCTYPE_CONTRACT.abatorgenerated_updateByPrimaryKeySelective",
						record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_LOYALTY_ACCTYPE_CONTRACT
	 * 
	 * @abatorgenerated Thu Nov 18 15:31:30 CST 2010
	 */
	@SuppressWarnings("unchecked")
	public List<LoyaltyAcctypeContract> selectByExample(
			LoyaltyAcctypeContractExample example) {
		List<LoyaltyAcctypeContract> list = (List<LoyaltyAcctypeContract>) getSqlMapClientTemplate()
				.queryForList(
						"TB_LOYALTY_ACCTYPE_CONTRACT.abatorgenerated_selectByExample",
						example);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_LOYALTY_ACCTYPE_CONTRACT
	 * 
	 * @abatorgenerated Thu Nov 18 15:31:30 CST 2010
	 */
	public LoyaltyAcctypeContract selectByPrimaryKey(String id) {
		LoyaltyAcctypeContract key = new LoyaltyAcctypeContract();
		key.setId(id);
		LoyaltyAcctypeContract record = (LoyaltyAcctypeContract) getSqlMapClientTemplate()
				.queryForObject(
						"TB_LOYALTY_ACCTYPE_CONTRACT.abatorgenerated_selectByPrimaryKey",
						key);
		return record;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_LOYALTY_ACCTYPE_CONTRACT
	 * 
	 * @abatorgenerated Thu Nov 18 15:31:30 CST 2010
	 */
	public int deleteByExample(LoyaltyAcctypeContractExample example) {
		int rows = getSqlMapClientTemplate().delete(
				"TB_LOYALTY_ACCTYPE_CONTRACT.abatorgenerated_deleteByExample",
				example);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_LOYALTY_ACCTYPE_CONTRACT
	 * 
	 * @abatorgenerated Thu Nov 18 15:31:30 CST 2010
	 */
	public int deleteByPrimaryKey(String id) {
		LoyaltyAcctypeContract key = new LoyaltyAcctypeContract();
		key.setId(id);
		int rows = getSqlMapClientTemplate()
				.delete(
						"TB_LOYALTY_ACCTYPE_CONTRACT.abatorgenerated_deleteByPrimaryKey",
						key);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_LOYALTY_ACCTYPE_CONTRACT
	 * 
	 * @abatorgenerated Thu Nov 18 15:31:30 CST 2010
	 */
	public int countByExample(LoyaltyAcctypeContractExample example) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(
				"TB_LOYALTY_ACCTYPE_CONTRACT.abatorgenerated_countByExample",
				example);
		return count;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_LOYALTY_ACCTYPE_CONTRACT
	 * 
	 * @abatorgenerated Thu Nov 18 15:31:30 CST 2010
	 */
	public int updateByExampleSelective(LoyaltyAcctypeContract record,
			LoyaltyAcctypeContractExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate()
				.update(
						"TB_LOYALTY_ACCTYPE_CONTRACT.abatorgenerated_updateByExampleSelective",
						parms);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_LOYALTY_ACCTYPE_CONTRACT
	 * 
	 * @abatorgenerated Thu Nov 18 15:31:30 CST 2010
	 */
	public int updateByExample(LoyaltyAcctypeContract record,
			LoyaltyAcctypeContractExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate().update(
				"TB_LOYALTY_ACCTYPE_CONTRACT.abatorgenerated_updateByExample",
				parms);
		return rows;
	}

	/**
	 * This class was generated by Abator for iBATIS. This class corresponds to
	 * the database table TB_LOYALTY_ACCTYPE_CONTRACT
	 * 
	 * @abatorgenerated Thu Nov 18 15:31:30 CST 2010
	 */
	private static class UpdateByExampleParms extends
			LoyaltyAcctypeContractExample {
		private Object record;

		public UpdateByExampleParms(Object record,
				LoyaltyAcctypeContractExample example) {
			super(example);
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}
}