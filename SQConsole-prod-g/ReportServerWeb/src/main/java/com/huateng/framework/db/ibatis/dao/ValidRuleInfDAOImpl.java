package com.huateng.framework.db.ibatis.dao;

import com.huateng.framework.db.ibatis.model.ValidRuleInf;
import com.huateng.framework.db.ibatis.model.ValidRuleInfExample;
import com.huateng.framework.db.ibatis.model.ValidRuleInfKey;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class ValidRuleInfDAOImpl extends SqlMapClientDaoSupport implements
		ValidRuleInfDAO {

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_VALID_RULE_INF
	 * 
	 * @abatorgenerated Thu Feb 23 10:40:59 CST 2012
	 */
	public ValidRuleInfDAOImpl() {
		super();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_VALID_RULE_INF
	 * 
	 * @abatorgenerated Thu Feb 23 10:40:59 CST 2012
	 */
	public void insert(ValidRuleInf record) {
		getSqlMapClientTemplate().insert(
				"TB_VALID_RULE_INF.abatorgenerated_insert", record);
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_VALID_RULE_INF
	 * 
	 * @abatorgenerated Thu Feb 23 10:40:59 CST 2012
	 */
	public int updateByPrimaryKey(ValidRuleInf record) {
		int rows = getSqlMapClientTemplate().update(
				"TB_VALID_RULE_INF.abatorgenerated_updateByPrimaryKey", record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_VALID_RULE_INF
	 * 
	 * @abatorgenerated Thu Feb 23 10:40:59 CST 2012
	 */
	public int updateByPrimaryKeySelective(ValidRuleInf record) {
		int rows = getSqlMapClientTemplate()
				.update(
						"TB_VALID_RULE_INF.abatorgenerated_updateByPrimaryKeySelective",
						record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_VALID_RULE_INF
	 * 
	 * @abatorgenerated Thu Feb 23 10:40:59 CST 2012
	 */
	@SuppressWarnings("unchecked")
	public List<ValidRuleInf> selectByExample(ValidRuleInfExample example) {
		List<ValidRuleInf> list = (List<ValidRuleInf>) getSqlMapClientTemplate()
				.queryForList(
						"TB_VALID_RULE_INF.abatorgenerated_selectByExample",
						example);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_VALID_RULE_INF
	 * 
	 * @abatorgenerated Thu Feb 23 10:40:59 CST 2012
	 */
	public ValidRuleInf selectByPrimaryKey(ValidRuleInfKey key) {
		ValidRuleInf record = (ValidRuleInf) getSqlMapClientTemplate()
				.queryForObject(
						"TB_VALID_RULE_INF.abatorgenerated_selectByPrimaryKey",
						key);
		return record;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_VALID_RULE_INF
	 * 
	 * @abatorgenerated Thu Feb 23 10:40:59 CST 2012
	 */
	public int deleteByExample(ValidRuleInfExample example) {
		int rows = getSqlMapClientTemplate().delete(
				"TB_VALID_RULE_INF.abatorgenerated_deleteByExample", example);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_VALID_RULE_INF
	 * 
	 * @abatorgenerated Thu Feb 23 10:40:59 CST 2012
	 */
	public int deleteByPrimaryKey(ValidRuleInfKey key) {
		int rows = getSqlMapClientTemplate().delete(
				"TB_VALID_RULE_INF.abatorgenerated_deleteByPrimaryKey", key);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_VALID_RULE_INF
	 * 
	 * @abatorgenerated Thu Feb 23 10:40:59 CST 2012
	 */
	public int countByExample(ValidRuleInfExample example) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(
				"TB_VALID_RULE_INF.abatorgenerated_countByExample", example);
		return count;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_VALID_RULE_INF
	 * 
	 * @abatorgenerated Thu Feb 23 10:40:59 CST 2012
	 */
	public int updateByExampleSelective(ValidRuleInf record,
			ValidRuleInfExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate().update(
				"TB_VALID_RULE_INF.abatorgenerated_updateByExampleSelective",
				parms);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_VALID_RULE_INF
	 * 
	 * @abatorgenerated Thu Feb 23 10:40:59 CST 2012
	 */
	public int updateByExample(ValidRuleInf record, ValidRuleInfExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate().update(
				"TB_VALID_RULE_INF.abatorgenerated_updateByExample", parms);
		return rows;
	}

	/**
	 * This class was generated by Abator for iBATIS. This class corresponds to
	 * the database table TB_VALID_RULE_INF
	 * 
	 * @abatorgenerated Thu Feb 23 10:40:59 CST 2012
	 */
	private static class UpdateByExampleParms extends ValidRuleInfExample {
		private Object record;

		public UpdateByExampleParms(Object record, ValidRuleInfExample example) {
			super(example);
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}
}