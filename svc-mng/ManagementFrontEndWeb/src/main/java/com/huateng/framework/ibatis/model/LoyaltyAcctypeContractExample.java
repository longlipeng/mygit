package com.huateng.framework.ibatis.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoyaltyAcctypeContractExample {
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to
	 * the database table TB_LOYALTY_ACCTYPE_CONTRACT
	 * 
	 * @abatorgenerated Thu Nov 18 15:31:30 CST 2010
	 */
	protected String orderByClause;

	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to
	 * the database table TB_LOYALTY_ACCTYPE_CONTRACT
	 * 
	 * @abatorgenerated Thu Nov 18 15:31:30 CST 2010
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_LOYALTY_ACCTYPE_CONTRACT
	 * 
	 * @abatorgenerated Thu Nov 18 15:31:30 CST 2010
	 */
	public LoyaltyAcctypeContractExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_LOYALTY_ACCTYPE_CONTRACT
	 * 
	 * @abatorgenerated Thu Nov 18 15:31:30 CST 2010
	 */
	protected LoyaltyAcctypeContractExample(
			LoyaltyAcctypeContractExample example) {
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_LOYALTY_ACCTYPE_CONTRACT
	 * 
	 * @abatorgenerated Thu Nov 18 15:31:30 CST 2010
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_LOYALTY_ACCTYPE_CONTRACT
	 * 
	 * @abatorgenerated Thu Nov 18 15:31:30 CST 2010
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_LOYALTY_ACCTYPE_CONTRACT
	 * 
	 * @abatorgenerated Thu Nov 18 15:31:30 CST 2010
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_LOYALTY_ACCTYPE_CONTRACT
	 * 
	 * @abatorgenerated Thu Nov 18 15:31:30 CST 2010
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_LOYALTY_ACCTYPE_CONTRACT
	 * 
	 * @abatorgenerated Thu Nov 18 15:31:30 CST 2010
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
	 * to the database table TB_LOYALTY_ACCTYPE_CONTRACT
	 * 
	 * @abatorgenerated Thu Nov 18 15:31:30 CST 2010
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_LOYALTY_ACCTYPE_CONTRACT
	 * 
	 * @abatorgenerated Thu Nov 18 15:31:30 CST 2010
	 */
	public void clear() {
		oredCriteria.clear();
	}

	/**
	 * This class was generated by Abator for iBATIS. This class corresponds to
	 * the database table TB_LOYALTY_ACCTYPE_CONTRACT
	 * 
	 * @abatorgenerated Thu Nov 18 15:31:30 CST 2010
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

		public Criteria andIdIsNull() {
			addCriterion("ID is null");
			return this;
		}

		public Criteria andIdIsNotNull() {
			addCriterion("ID is not null");
			return this;
		}

		public Criteria andIdEqualTo(String value) {
			addCriterion("ID =", value, "id");
			return this;
		}

		public Criteria andIdNotEqualTo(String value) {
			addCriterion("ID <>", value, "id");
			return this;
		}

		public Criteria andIdGreaterThan(String value) {
			addCriterion("ID >", value, "id");
			return this;
		}

		public Criteria andIdGreaterThanOrEqualTo(String value) {
			addCriterion("ID >=", value, "id");
			return this;
		}

		public Criteria andIdLessThan(String value) {
			addCriterion("ID <", value, "id");
			return this;
		}

		public Criteria andIdLessThanOrEqualTo(String value) {
			addCriterion("ID <=", value, "id");
			return this;
		}

		public Criteria andIdLike(String value) {
			addCriterion("ID like", value, "id");
			return this;
		}

		public Criteria andIdNotLike(String value) {
			addCriterion("ID not like", value, "id");
			return this;
		}

		public Criteria andIdIn(List<String> values) {
			addCriterion("ID in", values, "id");
			return this;
		}

		public Criteria andIdNotIn(List<String> values) {
			addCriterion("ID not in", values, "id");
			return this;
		}

		public Criteria andIdBetween(String value1, String value2) {
			addCriterion("ID between", value1, value2, "id");
			return this;
		}

		public Criteria andIdNotBetween(String value1, String value2) {
			addCriterion("ID not between", value1, value2, "id");
			return this;
		}

		public Criteria andLoyaltyContractIdIsNull() {
			addCriterion("LOYALTY_CONTRACT_ID is null");
			return this;
		}

		public Criteria andLoyaltyContractIdIsNotNull() {
			addCriterion("LOYALTY_CONTRACT_ID is not null");
			return this;
		}

		public Criteria andLoyaltyContractIdEqualTo(String value) {
			addCriterion("LOYALTY_CONTRACT_ID =", value, "loyaltyContractId");
			return this;
		}

		public Criteria andLoyaltyContractIdNotEqualTo(String value) {
			addCriterion("LOYALTY_CONTRACT_ID <>", value, "loyaltyContractId");
			return this;
		}

		public Criteria andLoyaltyContractIdGreaterThan(String value) {
			addCriterion("LOYALTY_CONTRACT_ID >", value, "loyaltyContractId");
			return this;
		}

		public Criteria andLoyaltyContractIdGreaterThanOrEqualTo(String value) {
			addCriterion("LOYALTY_CONTRACT_ID >=", value, "loyaltyContractId");
			return this;
		}

		public Criteria andLoyaltyContractIdLessThan(String value) {
			addCriterion("LOYALTY_CONTRACT_ID <", value, "loyaltyContractId");
			return this;
		}

		public Criteria andLoyaltyContractIdLessThanOrEqualTo(String value) {
			addCriterion("LOYALTY_CONTRACT_ID <=", value, "loyaltyContractId");
			return this;
		}

		public Criteria andLoyaltyContractIdLike(String value) {
			addCriterion("LOYALTY_CONTRACT_ID like", value, "loyaltyContractId");
			return this;
		}

		public Criteria andLoyaltyContractIdNotLike(String value) {
			addCriterion("LOYALTY_CONTRACT_ID not like", value,
					"loyaltyContractId");
			return this;
		}

		public Criteria andLoyaltyContractIdIn(List<String> values) {
			addCriterion("LOYALTY_CONTRACT_ID in", values, "loyaltyContractId");
			return this;
		}

		public Criteria andLoyaltyContractIdNotIn(List<String> values) {
			addCriterion("LOYALTY_CONTRACT_ID not in", values,
					"loyaltyContractId");
			return this;
		}

		public Criteria andLoyaltyContractIdBetween(String value1, String value2) {
			addCriterion("LOYALTY_CONTRACT_ID between", value1, value2,
					"loyaltyContractId");
			return this;
		}

		public Criteria andLoyaltyContractIdNotBetween(String value1,
				String value2) {
			addCriterion("LOYALTY_CONTRACT_ID not between", value1, value2,
					"loyaltyContractId");
			return this;
		}

		public Criteria andRuleNoIsNull() {
			addCriterion("RULE_NO is null");
			return this;
		}

		public Criteria andRuleNoIsNotNull() {
			addCriterion("RULE_NO is not null");
			return this;
		}

		public Criteria andRuleNoEqualTo(String value) {
			addCriterion("RULE_NO =", value, "ruleNo");
			return this;
		}

		public Criteria andRuleNoNotEqualTo(String value) {
			addCriterion("RULE_NO <>", value, "ruleNo");
			return this;
		}

		public Criteria andRuleNoGreaterThan(String value) {
			addCriterion("RULE_NO >", value, "ruleNo");
			return this;
		}

		public Criteria andRuleNoGreaterThanOrEqualTo(String value) {
			addCriterion("RULE_NO >=", value, "ruleNo");
			return this;
		}

		public Criteria andRuleNoLessThan(String value) {
			addCriterion("RULE_NO <", value, "ruleNo");
			return this;
		}

		public Criteria andRuleNoLessThanOrEqualTo(String value) {
			addCriterion("RULE_NO <=", value, "ruleNo");
			return this;
		}

		public Criteria andRuleNoLike(String value) {
			addCriterion("RULE_NO like", value, "ruleNo");
			return this;
		}

		public Criteria andRuleNoNotLike(String value) {
			addCriterion("RULE_NO not like", value, "ruleNo");
			return this;
		}

		public Criteria andRuleNoIn(List<String> values) {
			addCriterion("RULE_NO in", values, "ruleNo");
			return this;
		}

		public Criteria andRuleNoNotIn(List<String> values) {
			addCriterion("RULE_NO not in", values, "ruleNo");
			return this;
		}

		public Criteria andRuleNoBetween(String value1, String value2) {
			addCriterion("RULE_NO between", value1, value2, "ruleNo");
			return this;
		}

		public Criteria andRuleNoNotBetween(String value1, String value2) {
			addCriterion("RULE_NO not between", value1, value2, "ruleNo");
			return this;
		}

		public Criteria andProductIdIsNull() {
			addCriterion("PRODUCT_ID is null");
			return this;
		}

		public Criteria andProductIdIsNotNull() {
			addCriterion("PRODUCT_ID is not null");
			return this;
		}

		public Criteria andProductIdEqualTo(String value) {
			addCriterion("PRODUCT_ID =", value, "productId");
			return this;
		}

		public Criteria andProductIdNotEqualTo(String value) {
			addCriterion("PRODUCT_ID <>", value, "productId");
			return this;
		}

		public Criteria andProductIdGreaterThan(String value) {
			addCriterion("PRODUCT_ID >", value, "productId");
			return this;
		}

		public Criteria andProductIdGreaterThanOrEqualTo(String value) {
			addCriterion("PRODUCT_ID >=", value, "productId");
			return this;
		}

		public Criteria andProductIdLessThan(String value) {
			addCriterion("PRODUCT_ID <", value, "productId");
			return this;
		}

		public Criteria andProductIdLessThanOrEqualTo(String value) {
			addCriterion("PRODUCT_ID <=", value, "productId");
			return this;
		}

		public Criteria andProductIdLike(String value) {
			addCriterion("PRODUCT_ID like", value, "productId");
			return this;
		}

		public Criteria andProductIdNotLike(String value) {
			addCriterion("PRODUCT_ID not like", value, "productId");
			return this;
		}

		public Criteria andProductIdIn(List<String> values) {
			addCriterion("PRODUCT_ID in", values, "productId");
			return this;
		}

		public Criteria andProductIdNotIn(List<String> values) {
			addCriterion("PRODUCT_ID not in", values, "productId");
			return this;
		}

		public Criteria andProductIdBetween(String value1, String value2) {
			addCriterion("PRODUCT_ID between", value1, value2, "productId");
			return this;
		}

		public Criteria andProductIdNotBetween(String value1, String value2) {
			addCriterion("PRODUCT_ID not between", value1, value2, "productId");
			return this;
		}

		public Criteria andAcctypeIdIsNull() {
			addCriterion("ACCTYPE_ID is null");
			return this;
		}

		public Criteria andAcctypeIdIsNotNull() {
			addCriterion("ACCTYPE_ID is not null");
			return this;
		}

		public Criteria andAcctypeIdEqualTo(String value) {
			addCriterion("ACCTYPE_ID =", value, "acctypeId");
			return this;
		}

		public Criteria andAcctypeIdNotEqualTo(String value) {
			addCriterion("ACCTYPE_ID <>", value, "acctypeId");
			return this;
		}

		public Criteria andAcctypeIdGreaterThan(String value) {
			addCriterion("ACCTYPE_ID >", value, "acctypeId");
			return this;
		}

		public Criteria andAcctypeIdGreaterThanOrEqualTo(String value) {
			addCriterion("ACCTYPE_ID >=", value, "acctypeId");
			return this;
		}

		public Criteria andAcctypeIdLessThan(String value) {
			addCriterion("ACCTYPE_ID <", value, "acctypeId");
			return this;
		}

		public Criteria andAcctypeIdLessThanOrEqualTo(String value) {
			addCriterion("ACCTYPE_ID <=", value, "acctypeId");
			return this;
		}

		public Criteria andAcctypeIdLike(String value) {
			addCriterion("ACCTYPE_ID like", value, "acctypeId");
			return this;
		}

		public Criteria andAcctypeIdNotLike(String value) {
			addCriterion("ACCTYPE_ID not like", value, "acctypeId");
			return this;
		}

		public Criteria andAcctypeIdIn(List<String> values) {
			addCriterion("ACCTYPE_ID in", values, "acctypeId");
			return this;
		}

		public Criteria andAcctypeIdNotIn(List<String> values) {
			addCriterion("ACCTYPE_ID not in", values, "acctypeId");
			return this;
		}

		public Criteria andAcctypeIdBetween(String value1, String value2) {
			addCriterion("ACCTYPE_ID between", value1, value2, "acctypeId");
			return this;
		}

		public Criteria andAcctypeIdNotBetween(String value1, String value2) {
			addCriterion("ACCTYPE_ID not between", value1, value2, "acctypeId");
			return this;
		}

		public Criteria andTxnNumIsNull() {
			addCriterion("TXN_NUM is null");
			return this;
		}

		public Criteria andTxnNumIsNotNull() {
			addCriterion("TXN_NUM is not null");
			return this;
		}

		public Criteria andTxnNumEqualTo(String value) {
			addCriterion("TXN_NUM =", value, "txnNum");
			return this;
		}

		public Criteria andTxnNumNotEqualTo(String value) {
			addCriterion("TXN_NUM <>", value, "txnNum");
			return this;
		}

		public Criteria andTxnNumGreaterThan(String value) {
			addCriterion("TXN_NUM >", value, "txnNum");
			return this;
		}

		public Criteria andTxnNumGreaterThanOrEqualTo(String value) {
			addCriterion("TXN_NUM >=", value, "txnNum");
			return this;
		}

		public Criteria andTxnNumLessThan(String value) {
			addCriterion("TXN_NUM <", value, "txnNum");
			return this;
		}

		public Criteria andTxnNumLessThanOrEqualTo(String value) {
			addCriterion("TXN_NUM <=", value, "txnNum");
			return this;
		}

		public Criteria andTxnNumLike(String value) {
			addCriterion("TXN_NUM like", value, "txnNum");
			return this;
		}

		public Criteria andTxnNumNotLike(String value) {
			addCriterion("TXN_NUM not like", value, "txnNum");
			return this;
		}

		public Criteria andTxnNumIn(List<String> values) {
			addCriterion("TXN_NUM in", values, "txnNum");
			return this;
		}

		public Criteria andTxnNumNotIn(List<String> values) {
			addCriterion("TXN_NUM not in", values, "txnNum");
			return this;
		}

		public Criteria andTxnNumBetween(String value1, String value2) {
			addCriterion("TXN_NUM between", value1, value2, "txnNum");
			return this;
		}

		public Criteria andTxnNumNotBetween(String value1, String value2) {
			addCriterion("TXN_NUM not between", value1, value2, "txnNum");
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
	}
}