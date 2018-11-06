package com.huateng.framework.db.ibatis.dao;

import com.huateng.framework.db.ibatis.model.SettlePeriodRule;
import com.huateng.framework.db.ibatis.model.SettlePeriodRuleExample;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class SettlePeriodRuleDAOImpl extends SqlMapClientDaoSupport implements
		SettlePeriodRuleDAO {

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SETTLE_PERIOD_RULE
	 * 
	 * @abatorgenerated Sun Sep 26 15:57:01 CST 2010
	 */
	public SettlePeriodRuleDAOImpl() {
		super();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SETTLE_PERIOD_RULE
	 * 
	 * @abatorgenerated Sun Sep 26 15:57:01 CST 2010
	 */
	public void insert(SettlePeriodRule record) {
		getSqlMapClientTemplate().insert(
				"TB_SETTLE_PERIOD_RULE.abatorgenerated_insert", record);
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SETTLE_PERIOD_RULE
	 * 
	 * @abatorgenerated Sun Sep 26 15:57:01 CST 2010
	 */
	public int updateByPrimaryKey(SettlePeriodRule record) {
		int rows = getSqlMapClientTemplate().update(
				"TB_SETTLE_PERIOD_RULE.abatorgenerated_updateByPrimaryKey",
				record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SETTLE_PERIOD_RULE
	 * 
	 * @abatorgenerated Sun Sep 26 15:57:01 CST 2010
	 */
	public int updateByPrimaryKeySelective(SettlePeriodRule record) {
		int rows = getSqlMapClientTemplate()
				.update(
						"TB_SETTLE_PERIOD_RULE.abatorgenerated_updateByPrimaryKeySelective",
						record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SETTLE_PERIOD_RULE
	 * 
	 * @abatorgenerated Sun Sep 26 15:57:01 CST 2010
	 */
	@SuppressWarnings("unchecked")
	public List<SettlePeriodRule> selectByExample(
			SettlePeriodRuleExample example) {
		List<SettlePeriodRule> list = (List<SettlePeriodRule>) getSqlMapClientTemplate()
				.queryForList(
						"TB_SETTLE_PERIOD_RULE.abatorgenerated_selectByExample",
						example);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SETTLE_PERIOD_RULE
	 * 
	 * @abatorgenerated Sun Sep 26 15:57:01 CST 2010
	 */
	public SettlePeriodRule selectByPrimaryKey(String ruleNo) {
		SettlePeriodRule key = new SettlePeriodRule();
		key.setRuleNo(ruleNo);
		SettlePeriodRule record = (SettlePeriodRule) getSqlMapClientTemplate()
				.queryForObject(
						"TB_SETTLE_PERIOD_RULE.abatorgenerated_selectByPrimaryKey",
						key);
		return record;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SETTLE_PERIOD_RULE
	 * 
	 * @abatorgenerated Sun Sep 26 15:57:01 CST 2010
	 */
	public int deleteByExample(SettlePeriodRuleExample example) {
		int rows = getSqlMapClientTemplate().delete(
				"TB_SETTLE_PERIOD_RULE.abatorgenerated_deleteByExample",
				example);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SETTLE_PERIOD_RULE
	 * 
	 * @abatorgenerated Sun Sep 26 15:57:01 CST 2010
	 */
	public int deleteByPrimaryKey(String ruleNo) {
		SettlePeriodRule key = new SettlePeriodRule();
		key.setRuleNo(ruleNo);
		int rows = getSqlMapClientTemplate()
				.delete(
						"TB_SETTLE_PERIOD_RULE.abatorgenerated_deleteByPrimaryKey",
						key);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SETTLE_PERIOD_RULE
	 * 
	 * @abatorgenerated Sun Sep 26 15:57:01 CST 2010
	 */
	public int countByExample(SettlePeriodRuleExample example) {
		Integer count = (Integer) getSqlMapClientTemplate()
				.queryForObject(
						"TB_SETTLE_PERIOD_RULE.abatorgenerated_countByExample",
						example);
		return count;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SETTLE_PERIOD_RULE
	 * 
	 * @abatorgenerated Sun Sep 26 15:57:01 CST 2010
	 */
	public int updateByExampleSelective(SettlePeriodRule record,
			SettlePeriodRuleExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate()
				.update(
						"TB_SETTLE_PERIOD_RULE.abatorgenerated_updateByExampleSelective",
						parms);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SETTLE_PERIOD_RULE
	 * 
	 * @abatorgenerated Sun Sep 26 15:57:01 CST 2010
	 */
	public int updateByExample(SettlePeriodRule record,
			SettlePeriodRuleExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate().update(
				"TB_SETTLE_PERIOD_RULE.abatorgenerated_updateByExample", parms);
		return rows;
	}

	/**
	 * This class was generated by Abator for iBATIS. This class corresponds to
	 * the database table TB_SETTLE_PERIOD_RULE
	 * 
	 * @abatorgenerated Sun Sep 26 15:57:01 CST 2010
	 */
	private static class UpdateByExampleParms extends SettlePeriodRuleExample {
		private Object record;

		public UpdateByExampleParms(Object record,
				SettlePeriodRuleExample example) {
			super(example);
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}
}