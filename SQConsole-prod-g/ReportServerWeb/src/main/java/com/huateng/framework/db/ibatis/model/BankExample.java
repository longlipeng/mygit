package com.huateng.framework.db.ibatis.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BankExample {

	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to
	 * the database table TB_BANK
	 * 
	 * @abatorgenerated Thu Oct 27 16:38:36 CST 2011
	 */
	protected String orderByClause;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to
	 * the database table TB_BANK
	 * 
	 * @abatorgenerated Thu Oct 27 16:38:36 CST 2011
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_BANK
	 * 
	 * @abatorgenerated Thu Oct 27 16:38:36 CST 2011
	 */
	public BankExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_BANK
	 * 
	 * @abatorgenerated Thu Oct 27 16:38:36 CST 2011
	 */
	protected BankExample(BankExample example) {
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_BANK
	 * 
	 * @abatorgenerated Thu Oct 27 16:38:36 CST 2011
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_BANK
	 * 
	 * @abatorgenerated Thu Oct 27 16:38:36 CST 2011
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_BANK
	 * 
	 * @abatorgenerated Thu Oct 27 16:38:36 CST 2011
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_BANK
	 * 
	 * @abatorgenerated Thu Oct 27 16:38:36 CST 2011
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_BANK
	 * 
	 * @abatorgenerated Thu Oct 27 16:38:36 CST 2011
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_BANK
	 * 
	 * @abatorgenerated Thu Oct 27 16:38:36 CST 2011
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_BANK
	 * 
	 * @abatorgenerated Thu Oct 27 16:38:36 CST 2011
	 */
	public void clear() {
		oredCriteria.clear();
	}

	/**
	 * This class was generated by Abator for iBATIS. This class corresponds to
	 * the database table TB_BANK
	 * 
	 * @abatorgenerated Thu Oct 27 16:38:36 CST 2011
	 */
	public static class Criteria {
		protected List<String> criteriaWithoutValue;
		protected List<Map<String, Object>> criteriaWithSingleValue;
		protected List<Map<String, Object>> criteriaWithListValue;
		protected List<Map<String, Object>> criteriaWithBetweenValue;

		protected Criteria() {
			super();
			criteriaWithoutValue = new ArrayList<String>();
			criteriaWithSingleValue = new ArrayList<Map<String, Object>>();
			criteriaWithListValue = new ArrayList<Map<String, Object>>();
			criteriaWithBetweenValue = new ArrayList<Map<String, Object>>();
		}

		public boolean isValid() {
			return criteriaWithoutValue.size() > 0
					|| criteriaWithSingleValue.size() > 0
					|| criteriaWithListValue.size() > 0
					|| criteriaWithBetweenValue.size() > 0;
		}

		public List<String> getCriteriaWithoutValue() {
			return criteriaWithoutValue;
		}

		public List<Map<String, Object>> getCriteriaWithSingleValue() {
			return criteriaWithSingleValue;
		}

		public List<Map<String, Object>> getCriteriaWithListValue() {
			return criteriaWithListValue;
		}

		public List<Map<String, Object>> getCriteriaWithBetweenValue() {
			return criteriaWithBetweenValue;
		}

		protected void addCriterion(String condition) {
			if (condition == null) {
				throw new RuntimeException("Value for condition cannot be null");
			}
			criteriaWithoutValue.add(condition);
		}

		protected void addCriterion(String condition, Object value,
				String property) {
			if (value == null) {
				throw new RuntimeException("Value for " + property
						+ " cannot be null");
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("condition", condition);
			map.put("value", value);
			criteriaWithSingleValue.add(map);
		}

		protected void addCriterion(String condition,
				List<? extends Object> values, String property) {
			if (values == null || values.size() == 0) {
				throw new RuntimeException("Value list for " + property
						+ " cannot be null or empty");
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("condition", condition);
			map.put("values", values);
			criteriaWithListValue.add(map);
		}

		protected void addCriterion(String condition, Object value1,
				Object value2, String property) {
			if (value1 == null || value2 == null) {
				throw new RuntimeException("Between values for " + property
						+ " cannot be null");
			}
			List<Object> list = new ArrayList<Object>();
			list.add(value1);
			list.add(value2);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("condition", condition);
			map.put("values", list);
			criteriaWithBetweenValue.add(map);
		}

		public Criteria andBankIdIsNull() {
			addCriterion("BANK_ID is null");
			return this;
		}

		public Criteria andBankIdIsNotNull() {
			addCriterion("BANK_ID is not null");
			return this;
		}

		public Criteria andBankIdEqualTo(String value) {
			addCriterion("BANK_ID =", value, "bankId");
			return this;
		}

		public Criteria andBankIdNotEqualTo(String value) {
			addCriterion("BANK_ID <>", value, "bankId");
			return this;
		}

		public Criteria andBankIdGreaterThan(String value) {
			addCriterion("BANK_ID >", value, "bankId");
			return this;
		}

		public Criteria andBankIdGreaterThanOrEqualTo(String value) {
			addCriterion("BANK_ID >=", value, "bankId");
			return this;
		}

		public Criteria andBankIdLessThan(String value) {
			addCriterion("BANK_ID <", value, "bankId");
			return this;
		}

		public Criteria andBankIdLessThanOrEqualTo(String value) {
			addCriterion("BANK_ID <=", value, "bankId");
			return this;
		}

		public Criteria andBankIdLike(String value) {
			addCriterion("BANK_ID like", value, "bankId");
			return this;
		}

		public Criteria andBankIdNotLike(String value) {
			addCriterion("BANK_ID not like", value, "bankId");
			return this;
		}

		public Criteria andBankIdIn(List<String> values) {
			addCriterion("BANK_ID in", values, "bankId");
			return this;
		}

		public Criteria andBankIdNotIn(List<String> values) {
			addCriterion("BANK_ID not in", values, "bankId");
			return this;
		}

		public Criteria andBankIdBetween(String value1, String value2) {
			addCriterion("BANK_ID between", value1, value2, "bankId");
			return this;
		}

		public Criteria andBankIdNotBetween(String value1, String value2) {
			addCriterion("BANK_ID not between", value1, value2, "bankId");
			return this;
		}

		public Criteria andEntityIdIsNull() {
			addCriterion("ENTITY_ID is null");
			return this;
		}

		public Criteria andEntityIdIsNotNull() {
			addCriterion("ENTITY_ID is not null");
			return this;
		}

		public Criteria andEntityIdEqualTo(String value) {
			addCriterion("ENTITY_ID =", value, "entityId");
			return this;
		}

		public Criteria andEntityIdNotEqualTo(String value) {
			addCriterion("ENTITY_ID <>", value, "entityId");
			return this;
		}

		public Criteria andEntityIdGreaterThan(String value) {
			addCriterion("ENTITY_ID >", value, "entityId");
			return this;
		}

		public Criteria andEntityIdGreaterThanOrEqualTo(String value) {
			addCriterion("ENTITY_ID >=", value, "entityId");
			return this;
		}

		public Criteria andEntityIdLessThan(String value) {
			addCriterion("ENTITY_ID <", value, "entityId");
			return this;
		}

		public Criteria andEntityIdLessThanOrEqualTo(String value) {
			addCriterion("ENTITY_ID <=", value, "entityId");
			return this;
		}

		public Criteria andEntityIdLike(String value) {
			addCriterion("ENTITY_ID like", value, "entityId");
			return this;
		}

		public Criteria andEntityIdNotLike(String value) {
			addCriterion("ENTITY_ID not like", value, "entityId");
			return this;
		}

		public Criteria andEntityIdIn(List<String> values) {
			addCriterion("ENTITY_ID in", values, "entityId");
			return this;
		}

		public Criteria andEntityIdNotIn(List<String> values) {
			addCriterion("ENTITY_ID not in", values, "entityId");
			return this;
		}

		public Criteria andEntityIdBetween(String value1, String value2) {
			addCriterion("ENTITY_ID between", value1, value2, "entityId");
			return this;
		}

		public Criteria andEntityIdNotBetween(String value1, String value2) {
			addCriterion("ENTITY_ID not between", value1, value2, "entityId");
			return this;
		}

		public Criteria andBankNameIsNull() {
			addCriterion("BANK_NAME is null");
			return this;
		}

		public Criteria andBankNameIsNotNull() {
			addCriterion("BANK_NAME is not null");
			return this;
		}

		public Criteria andBankNameEqualTo(String value) {
			addCriterion("BANK_NAME =", value, "bankName");
			return this;
		}

		public Criteria andBankNameNotEqualTo(String value) {
			addCriterion("BANK_NAME <>", value, "bankName");
			return this;
		}

		public Criteria andBankNameGreaterThan(String value) {
			addCriterion("BANK_NAME >", value, "bankName");
			return this;
		}

		public Criteria andBankNameGreaterThanOrEqualTo(String value) {
			addCriterion("BANK_NAME >=", value, "bankName");
			return this;
		}

		public Criteria andBankNameLessThan(String value) {
			addCriterion("BANK_NAME <", value, "bankName");
			return this;
		}

		public Criteria andBankNameLessThanOrEqualTo(String value) {
			addCriterion("BANK_NAME <=", value, "bankName");
			return this;
		}

		public Criteria andBankNameLike(String value) {
			addCriterion("BANK_NAME like", value, "bankName");
			return this;
		}

		public Criteria andBankNameNotLike(String value) {
			addCriterion("BANK_NAME not like", value, "bankName");
			return this;
		}

		public Criteria andBankNameIn(List<String> values) {
			addCriterion("BANK_NAME in", values, "bankName");
			return this;
		}

		public Criteria andBankNameNotIn(List<String> values) {
			addCriterion("BANK_NAME not in", values, "bankName");
			return this;
		}

		public Criteria andBankNameBetween(String value1, String value2) {
			addCriterion("BANK_NAME between", value1, value2, "bankName");
			return this;
		}

		public Criteria andBankNameNotBetween(String value1, String value2) {
			addCriterion("BANK_NAME not between", value1, value2, "bankName");
			return this;
		}

		public Criteria andBankAccountIsNull() {
			addCriterion("BANK_ACCOUNT is null");
			return this;
		}

		public Criteria andBankAccountIsNotNull() {
			addCriterion("BANK_ACCOUNT is not null");
			return this;
		}

		public Criteria andBankAccountEqualTo(String value) {
			addCriterion("BANK_ACCOUNT =", value, "bankAccount");
			return this;
		}

		public Criteria andBankAccountNotEqualTo(String value) {
			addCriterion("BANK_ACCOUNT <>", value, "bankAccount");
			return this;
		}

		public Criteria andBankAccountGreaterThan(String value) {
			addCriterion("BANK_ACCOUNT >", value, "bankAccount");
			return this;
		}

		public Criteria andBankAccountGreaterThanOrEqualTo(String value) {
			addCriterion("BANK_ACCOUNT >=", value, "bankAccount");
			return this;
		}

		public Criteria andBankAccountLessThan(String value) {
			addCriterion("BANK_ACCOUNT <", value, "bankAccount");
			return this;
		}

		public Criteria andBankAccountLessThanOrEqualTo(String value) {
			addCriterion("BANK_ACCOUNT <=", value, "bankAccount");
			return this;
		}

		public Criteria andBankAccountLike(String value) {
			addCriterion("BANK_ACCOUNT like", value, "bankAccount");
			return this;
		}

		public Criteria andBankAccountNotLike(String value) {
			addCriterion("BANK_ACCOUNT not like", value, "bankAccount");
			return this;
		}

		public Criteria andBankAccountIn(List<String> values) {
			addCriterion("BANK_ACCOUNT in", values, "bankAccount");
			return this;
		}

		public Criteria andBankAccountNotIn(List<String> values) {
			addCriterion("BANK_ACCOUNT not in", values, "bankAccount");
			return this;
		}

		public Criteria andBankAccountBetween(String value1, String value2) {
			addCriterion("BANK_ACCOUNT between", value1, value2, "bankAccount");
			return this;
		}

		public Criteria andBankAccountNotBetween(String value1, String value2) {
			addCriterion("BANK_ACCOUNT not between", value1, value2,
					"bankAccount");
			return this;
		}

		public Criteria andBankAccountNameIsNull() {
			addCriterion("BANK_ACCOUNT_NAME is null");
			return this;
		}

		public Criteria andBankAccountNameIsNotNull() {
			addCriterion("BANK_ACCOUNT_NAME is not null");
			return this;
		}

		public Criteria andBankAccountNameEqualTo(String value) {
			addCriterion("BANK_ACCOUNT_NAME =", value, "bankAccountName");
			return this;
		}

		public Criteria andBankAccountNameNotEqualTo(String value) {
			addCriterion("BANK_ACCOUNT_NAME <>", value, "bankAccountName");
			return this;
		}

		public Criteria andBankAccountNameGreaterThan(String value) {
			addCriterion("BANK_ACCOUNT_NAME >", value, "bankAccountName");
			return this;
		}

		public Criteria andBankAccountNameGreaterThanOrEqualTo(String value) {
			addCriterion("BANK_ACCOUNT_NAME >=", value, "bankAccountName");
			return this;
		}

		public Criteria andBankAccountNameLessThan(String value) {
			addCriterion("BANK_ACCOUNT_NAME <", value, "bankAccountName");
			return this;
		}

		public Criteria andBankAccountNameLessThanOrEqualTo(String value) {
			addCriterion("BANK_ACCOUNT_NAME <=", value, "bankAccountName");
			return this;
		}

		public Criteria andBankAccountNameLike(String value) {
			addCriterion("BANK_ACCOUNT_NAME like", value, "bankAccountName");
			return this;
		}

		public Criteria andBankAccountNameNotLike(String value) {
			addCriterion("BANK_ACCOUNT_NAME not like", value, "bankAccountName");
			return this;
		}

		public Criteria andBankAccountNameIn(List<String> values) {
			addCriterion("BANK_ACCOUNT_NAME in", values, "bankAccountName");
			return this;
		}

		public Criteria andBankAccountNameNotIn(List<String> values) {
			addCriterion("BANK_ACCOUNT_NAME not in", values, "bankAccountName");
			return this;
		}

		public Criteria andBankAccountNameBetween(String value1, String value2) {
			addCriterion("BANK_ACCOUNT_NAME between", value1, value2,
					"bankAccountName");
			return this;
		}

		public Criteria andBankAccountNameNotBetween(String value1,
				String value2) {
			addCriterion("BANK_ACCOUNT_NAME not between", value1, value2,
					"bankAccountName");
			return this;
		}

		public Criteria andBankAccountCodeIsNull() {
			addCriterion("BANK_ACCOUNT_CODE is null");
			return this;
		}

		public Criteria andBankAccountCodeIsNotNull() {
			addCriterion("BANK_ACCOUNT_CODE is not null");
			return this;
		}

		public Criteria andBankAccountCodeEqualTo(String value) {
			addCriterion("BANK_ACCOUNT_CODE =", value, "bankAccountCode");
			return this;
		}

		public Criteria andBankAccountCodeNotEqualTo(String value) {
			addCriterion("BANK_ACCOUNT_CODE <>", value, "bankAccountCode");
			return this;
		}

		public Criteria andBankAccountCodeGreaterThan(String value) {
			addCriterion("BANK_ACCOUNT_CODE >", value, "bankAccountCode");
			return this;
		}

		public Criteria andBankAccountCodeGreaterThanOrEqualTo(String value) {
			addCriterion("BANK_ACCOUNT_CODE >=", value, "bankAccountCode");
			return this;
		}

		public Criteria andBankAccountCodeLessThan(String value) {
			addCriterion("BANK_ACCOUNT_CODE <", value, "bankAccountCode");
			return this;
		}

		public Criteria andBankAccountCodeLessThanOrEqualTo(String value) {
			addCriterion("BANK_ACCOUNT_CODE <=", value, "bankAccountCode");
			return this;
		}

		public Criteria andBankAccountCodeLike(String value) {
			addCriterion("BANK_ACCOUNT_CODE like", value, "bankAccountCode");
			return this;
		}

		public Criteria andBankAccountCodeNotLike(String value) {
			addCriterion("BANK_ACCOUNT_CODE not like", value, "bankAccountCode");
			return this;
		}

		public Criteria andBankAccountCodeIn(List<String> values) {
			addCriterion("BANK_ACCOUNT_CODE in", values, "bankAccountCode");
			return this;
		}

		public Criteria andBankAccountCodeNotIn(List<String> values) {
			addCriterion("BANK_ACCOUNT_CODE not in", values, "bankAccountCode");
			return this;
		}

		public Criteria andBankAccountCodeBetween(String value1, String value2) {
			addCriterion("BANK_ACCOUNT_CODE between", value1, value2,
					"bankAccountCode");
			return this;
		}

		public Criteria andBankAccountCodeNotBetween(String value1,
				String value2) {
			addCriterion("BANK_ACCOUNT_CODE not between", value1, value2,
					"bankAccountCode");
			return this;
		}

		public Criteria andAccountFlagIsNull() {
			addCriterion("ACCOUNT_FLAG is null");
			return this;
		}

		public Criteria andAccountFlagIsNotNull() {
			addCriterion("ACCOUNT_FLAG is not null");
			return this;
		}

		public Criteria andAccountFlagEqualTo(String value) {
			addCriterion("ACCOUNT_FLAG =", value, "accountFlag");
			return this;
		}

		public Criteria andAccountFlagNotEqualTo(String value) {
			addCriterion("ACCOUNT_FLAG <>", value, "accountFlag");
			return this;
		}

		public Criteria andAccountFlagGreaterThan(String value) {
			addCriterion("ACCOUNT_FLAG >", value, "accountFlag");
			return this;
		}

		public Criteria andAccountFlagGreaterThanOrEqualTo(String value) {
			addCriterion("ACCOUNT_FLAG >=", value, "accountFlag");
			return this;
		}

		public Criteria andAccountFlagLessThan(String value) {
			addCriterion("ACCOUNT_FLAG <", value, "accountFlag");
			return this;
		}

		public Criteria andAccountFlagLessThanOrEqualTo(String value) {
			addCriterion("ACCOUNT_FLAG <=", value, "accountFlag");
			return this;
		}

		public Criteria andAccountFlagLike(String value) {
			addCriterion("ACCOUNT_FLAG like", value, "accountFlag");
			return this;
		}

		public Criteria andAccountFlagNotLike(String value) {
			addCriterion("ACCOUNT_FLAG not like", value, "accountFlag");
			return this;
		}

		public Criteria andAccountFlagIn(List<String> values) {
			addCriterion("ACCOUNT_FLAG in", values, "accountFlag");
			return this;
		}

		public Criteria andAccountFlagNotIn(List<String> values) {
			addCriterion("ACCOUNT_FLAG not in", values, "accountFlag");
			return this;
		}

		public Criteria andAccountFlagBetween(String value1, String value2) {
			addCriterion("ACCOUNT_FLAG between", value1, value2, "accountFlag");
			return this;
		}

		public Criteria andAccountFlagNotBetween(String value1, String value2) {
			addCriterion("ACCOUNT_FLAG not between", value1, value2,
					"accountFlag");
			return this;
		}

		public Criteria andBankTypeIsNull() {
			addCriterion("BANK_TYPE is null");
			return this;
		}

		public Criteria andBankTypeIsNotNull() {
			addCriterion("BANK_TYPE is not null");
			return this;
		}

		public Criteria andBankTypeEqualTo(String value) {
			addCriterion("BANK_TYPE =", value, "bankType");
			return this;
		}

		public Criteria andBankTypeNotEqualTo(String value) {
			addCriterion("BANK_TYPE <>", value, "bankType");
			return this;
		}

		public Criteria andBankTypeGreaterThan(String value) {
			addCriterion("BANK_TYPE >", value, "bankType");
			return this;
		}

		public Criteria andBankTypeGreaterThanOrEqualTo(String value) {
			addCriterion("BANK_TYPE >=", value, "bankType");
			return this;
		}

		public Criteria andBankTypeLessThan(String value) {
			addCriterion("BANK_TYPE <", value, "bankType");
			return this;
		}

		public Criteria andBankTypeLessThanOrEqualTo(String value) {
			addCriterion("BANK_TYPE <=", value, "bankType");
			return this;
		}

		public Criteria andBankTypeLike(String value) {
			addCriterion("BANK_TYPE like", value, "bankType");
			return this;
		}

		public Criteria andBankTypeNotLike(String value) {
			addCriterion("BANK_TYPE not like", value, "bankType");
			return this;
		}

		public Criteria andBankTypeIn(List<String> values) {
			addCriterion("BANK_TYPE in", values, "bankType");
			return this;
		}

		public Criteria andBankTypeNotIn(List<String> values) {
			addCriterion("BANK_TYPE not in", values, "bankType");
			return this;
		}

		public Criteria andBankTypeBetween(String value1, String value2) {
			addCriterion("BANK_TYPE between", value1, value2, "bankType");
			return this;
		}

		public Criteria andBankTypeNotBetween(String value1, String value2) {
			addCriterion("BANK_TYPE not between", value1, value2, "bankType");
			return this;
		}

		public Criteria andCreateUserIsNull() {
			addCriterion("CREATE_USER is null");
			return this;
		}

		public Criteria andCreateUserIsNotNull() {
			addCriterion("CREATE_USER is not null");
			return this;
		}

		public Criteria andCreateUserEqualTo(String value) {
			addCriterion("CREATE_USER =", value, "createUser");
			return this;
		}

		public Criteria andCreateUserNotEqualTo(String value) {
			addCriterion("CREATE_USER <>", value, "createUser");
			return this;
		}

		public Criteria andCreateUserGreaterThan(String value) {
			addCriterion("CREATE_USER >", value, "createUser");
			return this;
		}

		public Criteria andCreateUserGreaterThanOrEqualTo(String value) {
			addCriterion("CREATE_USER >=", value, "createUser");
			return this;
		}

		public Criteria andCreateUserLessThan(String value) {
			addCriterion("CREATE_USER <", value, "createUser");
			return this;
		}

		public Criteria andCreateUserLessThanOrEqualTo(String value) {
			addCriterion("CREATE_USER <=", value, "createUser");
			return this;
		}

		public Criteria andCreateUserLike(String value) {
			addCriterion("CREATE_USER like", value, "createUser");
			return this;
		}

		public Criteria andCreateUserNotLike(String value) {
			addCriterion("CREATE_USER not like", value, "createUser");
			return this;
		}

		public Criteria andCreateUserIn(List<String> values) {
			addCriterion("CREATE_USER in", values, "createUser");
			return this;
		}

		public Criteria andCreateUserNotIn(List<String> values) {
			addCriterion("CREATE_USER not in", values, "createUser");
			return this;
		}

		public Criteria andCreateUserBetween(String value1, String value2) {
			addCriterion("CREATE_USER between", value1, value2, "createUser");
			return this;
		}

		public Criteria andCreateUserNotBetween(String value1, String value2) {
			addCriterion("CREATE_USER not between", value1, value2,
					"createUser");
			return this;
		}

		public Criteria andCreateTimeIsNull() {
			addCriterion("CREATE_TIME is null");
			return this;
		}

		public Criteria andCreateTimeIsNotNull() {
			addCriterion("CREATE_TIME is not null");
			return this;
		}

		public Criteria andCreateTimeEqualTo(String value) {
			addCriterion("CREATE_TIME =", value, "createTime");
			return this;
		}

		public Criteria andCreateTimeNotEqualTo(String value) {
			addCriterion("CREATE_TIME <>", value, "createTime");
			return this;
		}

		public Criteria andCreateTimeGreaterThan(String value) {
			addCriterion("CREATE_TIME >", value, "createTime");
			return this;
		}

		public Criteria andCreateTimeGreaterThanOrEqualTo(String value) {
			addCriterion("CREATE_TIME >=", value, "createTime");
			return this;
		}

		public Criteria andCreateTimeLessThan(String value) {
			addCriterion("CREATE_TIME <", value, "createTime");
			return this;
		}

		public Criteria andCreateTimeLessThanOrEqualTo(String value) {
			addCriterion("CREATE_TIME <=", value, "createTime");
			return this;
		}

		public Criteria andCreateTimeLike(String value) {
			addCriterion("CREATE_TIME like", value, "createTime");
			return this;
		}

		public Criteria andCreateTimeNotLike(String value) {
			addCriterion("CREATE_TIME not like", value, "createTime");
			return this;
		}

		public Criteria andCreateTimeIn(List<String> values) {
			addCriterion("CREATE_TIME in", values, "createTime");
			return this;
		}

		public Criteria andCreateTimeNotIn(List<String> values) {
			addCriterion("CREATE_TIME not in", values, "createTime");
			return this;
		}

		public Criteria andCreateTimeBetween(String value1, String value2) {
			addCriterion("CREATE_TIME between", value1, value2, "createTime");
			return this;
		}

		public Criteria andCreateTimeNotBetween(String value1, String value2) {
			addCriterion("CREATE_TIME not between", value1, value2,
					"createTime");
			return this;
		}

		public Criteria andModifyUserIsNull() {
			addCriterion("MODIFY_USER is null");
			return this;
		}

		public Criteria andModifyUserIsNotNull() {
			addCriterion("MODIFY_USER is not null");
			return this;
		}

		public Criteria andModifyUserEqualTo(String value) {
			addCriterion("MODIFY_USER =", value, "modifyUser");
			return this;
		}

		public Criteria andModifyUserNotEqualTo(String value) {
			addCriterion("MODIFY_USER <>", value, "modifyUser");
			return this;
		}

		public Criteria andModifyUserGreaterThan(String value) {
			addCriterion("MODIFY_USER >", value, "modifyUser");
			return this;
		}

		public Criteria andModifyUserGreaterThanOrEqualTo(String value) {
			addCriterion("MODIFY_USER >=", value, "modifyUser");
			return this;
		}

		public Criteria andModifyUserLessThan(String value) {
			addCriterion("MODIFY_USER <", value, "modifyUser");
			return this;
		}

		public Criteria andModifyUserLessThanOrEqualTo(String value) {
			addCriterion("MODIFY_USER <=", value, "modifyUser");
			return this;
		}

		public Criteria andModifyUserLike(String value) {
			addCriterion("MODIFY_USER like", value, "modifyUser");
			return this;
		}

		public Criteria andModifyUserNotLike(String value) {
			addCriterion("MODIFY_USER not like", value, "modifyUser");
			return this;
		}

		public Criteria andModifyUserIn(List<String> values) {
			addCriterion("MODIFY_USER in", values, "modifyUser");
			return this;
		}

		public Criteria andModifyUserNotIn(List<String> values) {
			addCriterion("MODIFY_USER not in", values, "modifyUser");
			return this;
		}

		public Criteria andModifyUserBetween(String value1, String value2) {
			addCriterion("MODIFY_USER between", value1, value2, "modifyUser");
			return this;
		}

		public Criteria andModifyUserNotBetween(String value1, String value2) {
			addCriterion("MODIFY_USER not between", value1, value2,
					"modifyUser");
			return this;
		}

		public Criteria andModifyTimeIsNull() {
			addCriterion("MODIFY_TIME is null");
			return this;
		}

		public Criteria andModifyTimeIsNotNull() {
			addCriterion("MODIFY_TIME is not null");
			return this;
		}

		public Criteria andModifyTimeEqualTo(String value) {
			addCriterion("MODIFY_TIME =", value, "modifyTime");
			return this;
		}

		public Criteria andModifyTimeNotEqualTo(String value) {
			addCriterion("MODIFY_TIME <>", value, "modifyTime");
			return this;
		}

		public Criteria andModifyTimeGreaterThan(String value) {
			addCriterion("MODIFY_TIME >", value, "modifyTime");
			return this;
		}

		public Criteria andModifyTimeGreaterThanOrEqualTo(String value) {
			addCriterion("MODIFY_TIME >=", value, "modifyTime");
			return this;
		}

		public Criteria andModifyTimeLessThan(String value) {
			addCriterion("MODIFY_TIME <", value, "modifyTime");
			return this;
		}

		public Criteria andModifyTimeLessThanOrEqualTo(String value) {
			addCriterion("MODIFY_TIME <=", value, "modifyTime");
			return this;
		}

		public Criteria andModifyTimeLike(String value) {
			addCriterion("MODIFY_TIME like", value, "modifyTime");
			return this;
		}

		public Criteria andModifyTimeNotLike(String value) {
			addCriterion("MODIFY_TIME not like", value, "modifyTime");
			return this;
		}

		public Criteria andModifyTimeIn(List<String> values) {
			addCriterion("MODIFY_TIME in", values, "modifyTime");
			return this;
		}

		public Criteria andModifyTimeNotIn(List<String> values) {
			addCriterion("MODIFY_TIME not in", values, "modifyTime");
			return this;
		}

		public Criteria andModifyTimeBetween(String value1, String value2) {
			addCriterion("MODIFY_TIME between", value1, value2, "modifyTime");
			return this;
		}

		public Criteria andModifyTimeNotBetween(String value1, String value2) {
			addCriterion("MODIFY_TIME not between", value1, value2,
					"modifyTime");
			return this;
		}

		public Criteria andDataStateIsNull() {
			addCriterion("DATA_STATE is null");
			return this;
		}

		public Criteria andDataStateIsNotNull() {
			addCriterion("DATA_STATE is not null");
			return this;
		}

		public Criteria andDataStateEqualTo(String value) {
			addCriterion("DATA_STATE =", value, "dataState");
			return this;
		}

		public Criteria andDataStateNotEqualTo(String value) {
			addCriterion("DATA_STATE <>", value, "dataState");
			return this;
		}

		public Criteria andDataStateGreaterThan(String value) {
			addCriterion("DATA_STATE >", value, "dataState");
			return this;
		}

		public Criteria andDataStateGreaterThanOrEqualTo(String value) {
			addCriterion("DATA_STATE >=", value, "dataState");
			return this;
		}

		public Criteria andDataStateLessThan(String value) {
			addCriterion("DATA_STATE <", value, "dataState");
			return this;
		}

		public Criteria andDataStateLessThanOrEqualTo(String value) {
			addCriterion("DATA_STATE <=", value, "dataState");
			return this;
		}

		public Criteria andDataStateLike(String value) {
			addCriterion("DATA_STATE like", value, "dataState");
			return this;
		}

		public Criteria andDataStateNotLike(String value) {
			addCriterion("DATA_STATE not like", value, "dataState");
			return this;
		}

		public Criteria andDataStateIn(List<String> values) {
			addCriterion("DATA_STATE in", values, "dataState");
			return this;
		}

		public Criteria andDataStateNotIn(List<String> values) {
			addCriterion("DATA_STATE not in", values, "dataState");
			return this;
		}

		public Criteria andDataStateBetween(String value1, String value2) {
			addCriterion("DATA_STATE between", value1, value2, "dataState");
			return this;
		}

		public Criteria andDataStateNotBetween(String value1, String value2) {
			addCriterion("DATA_STATE not between", value1, value2, "dataState");
			return this;
		}
	}
}