package com.huateng.framework.ibatis.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntitySystemParameterExample {
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to
	 * the database table TB_ENTITY_SYSTEM_PARAMETER
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:52 CST 2010
	 */
	protected String orderByClause;

	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to
	 * the database table TB_ENTITY_SYSTEM_PARAMETER
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:52 CST 2010
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_SYSTEM_PARAMETER
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:52 CST 2010
	 */
	public EntitySystemParameterExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_SYSTEM_PARAMETER
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:52 CST 2010
	 */
	protected EntitySystemParameterExample(EntitySystemParameterExample example) {
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_SYSTEM_PARAMETER
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:52 CST 2010
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_SYSTEM_PARAMETER
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:52 CST 2010
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_SYSTEM_PARAMETER
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:52 CST 2010
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_SYSTEM_PARAMETER
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:52 CST 2010
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_SYSTEM_PARAMETER
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:52 CST 2010
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
	 * to the database table TB_ENTITY_SYSTEM_PARAMETER
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:52 CST 2010
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_SYSTEM_PARAMETER
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:52 CST 2010
	 */
	public void clear() {
		oredCriteria.clear();
	}

	/**
	 * This class was generated by Abator for iBATIS. This class corresponds to
	 * the database table TB_ENTITY_SYSTEM_PARAMETER
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:52 CST 2010
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

		public Criteria andFatherEntityIdIsNull() {
			addCriterion("FATHER_ENTITY_ID is null");
			return this;
		}

		public Criteria andFatherEntityIdIsNotNull() {
			addCriterion("FATHER_ENTITY_ID is not null");
			return this;
		}

		public Criteria andFatherEntityIdEqualTo(String value) {
			addCriterion("FATHER_ENTITY_ID =", value, "fatherEntityId");
			return this;
		}

		public Criteria andFatherEntityIdNotEqualTo(String value) {
			addCriterion("FATHER_ENTITY_ID <>", value, "fatherEntityId");
			return this;
		}

		public Criteria andFatherEntityIdGreaterThan(String value) {
			addCriterion("FATHER_ENTITY_ID >", value, "fatherEntityId");
			return this;
		}

		public Criteria andFatherEntityIdGreaterThanOrEqualTo(String value) {
			addCriterion("FATHER_ENTITY_ID >=", value, "fatherEntityId");
			return this;
		}

		public Criteria andFatherEntityIdLessThan(String value) {
			addCriterion("FATHER_ENTITY_ID <", value, "fatherEntityId");
			return this;
		}

		public Criteria andFatherEntityIdLessThanOrEqualTo(String value) {
			addCriterion("FATHER_ENTITY_ID <=", value, "fatherEntityId");
			return this;
		}

		public Criteria andFatherEntityIdLike(String value) {
			addCriterion("FATHER_ENTITY_ID like", value, "fatherEntityId");
			return this;
		}

		public Criteria andFatherEntityIdNotLike(String value) {
			addCriterion("FATHER_ENTITY_ID not like", value, "fatherEntityId");
			return this;
		}

		public Criteria andFatherEntityIdIn(List<String> values) {
			addCriterion("FATHER_ENTITY_ID in", values, "fatherEntityId");
			return this;
		}

		public Criteria andFatherEntityIdNotIn(List<String> values) {
			addCriterion("FATHER_ENTITY_ID not in", values, "fatherEntityId");
			return this;
		}

		public Criteria andFatherEntityIdBetween(String value1, String value2) {
			addCriterion("FATHER_ENTITY_ID between", value1, value2,
					"fatherEntityId");
			return this;
		}

		public Criteria andFatherEntityIdNotBetween(String value1, String value2) {
			addCriterion("FATHER_ENTITY_ID not between", value1, value2,
					"fatherEntityId");
			return this;
		}

		public Criteria andParameterCodeIsNull() {
			addCriterion("PARAMETER_CODE is null");
			return this;
		}

		public Criteria andParameterCodeIsNotNull() {
			addCriterion("PARAMETER_CODE is not null");
			return this;
		}

		public Criteria andParameterCodeEqualTo(String value) {
			addCriterion("PARAMETER_CODE =", value, "parameterCode");
			return this;
		}

		public Criteria andParameterCodeNotEqualTo(String value) {
			addCriterion("PARAMETER_CODE <>", value, "parameterCode");
			return this;
		}

		public Criteria andParameterCodeGreaterThan(String value) {
			addCriterion("PARAMETER_CODE >", value, "parameterCode");
			return this;
		}

		public Criteria andParameterCodeGreaterThanOrEqualTo(String value) {
			addCriterion("PARAMETER_CODE >=", value, "parameterCode");
			return this;
		}

		public Criteria andParameterCodeLessThan(String value) {
			addCriterion("PARAMETER_CODE <", value, "parameterCode");
			return this;
		}

		public Criteria andParameterCodeLessThanOrEqualTo(String value) {
			addCriterion("PARAMETER_CODE <=", value, "parameterCode");
			return this;
		}

		public Criteria andParameterCodeLike(String value) {
			addCriterion("PARAMETER_CODE like", value, "parameterCode");
			return this;
		}

		public Criteria andParameterCodeNotLike(String value) {
			addCriterion("PARAMETER_CODE not like", value, "parameterCode");
			return this;
		}

		public Criteria andParameterCodeIn(List<String> values) {
			addCriterion("PARAMETER_CODE in", values, "parameterCode");
			return this;
		}

		public Criteria andParameterCodeNotIn(List<String> values) {
			addCriterion("PARAMETER_CODE not in", values, "parameterCode");
			return this;
		}

		public Criteria andParameterCodeBetween(String value1, String value2) {
			addCriterion("PARAMETER_CODE between", value1, value2,
					"parameterCode");
			return this;
		}

		public Criteria andParameterCodeNotBetween(String value1, String value2) {
			addCriterion("PARAMETER_CODE not between", value1, value2,
					"parameterCode");
			return this;
		}

		public Criteria andParameterNameIsNull() {
			addCriterion("PARAMETER_NAME is null");
			return this;
		}

		public Criteria andParameterNameIsNotNull() {
			addCriterion("PARAMETER_NAME is not null");
			return this;
		}

		public Criteria andParameterNameEqualTo(String value) {
			addCriterion("PARAMETER_NAME =", value, "parameterName");
			return this;
		}

		public Criteria andParameterNameNotEqualTo(String value) {
			addCriterion("PARAMETER_NAME <>", value, "parameterName");
			return this;
		}

		public Criteria andParameterNameGreaterThan(String value) {
			addCriterion("PARAMETER_NAME >", value, "parameterName");
			return this;
		}

		public Criteria andParameterNameGreaterThanOrEqualTo(String value) {
			addCriterion("PARAMETER_NAME >=", value, "parameterName");
			return this;
		}

		public Criteria andParameterNameLessThan(String value) {
			addCriterion("PARAMETER_NAME <", value, "parameterName");
			return this;
		}

		public Criteria andParameterNameLessThanOrEqualTo(String value) {
			addCriterion("PARAMETER_NAME <=", value, "parameterName");
			return this;
		}

		public Criteria andParameterNameLike(String value) {
			addCriterion("PARAMETER_NAME like", value, "parameterName");
			return this;
		}

		public Criteria andParameterNameNotLike(String value) {
			addCriterion("PARAMETER_NAME not like", value, "parameterName");
			return this;
		}

		public Criteria andParameterNameIn(List<String> values) {
			addCriterion("PARAMETER_NAME in", values, "parameterName");
			return this;
		}

		public Criteria andParameterNameNotIn(List<String> values) {
			addCriterion("PARAMETER_NAME not in", values, "parameterName");
			return this;
		}

		public Criteria andParameterNameBetween(String value1, String value2) {
			addCriterion("PARAMETER_NAME between", value1, value2,
					"parameterName");
			return this;
		}

		public Criteria andParameterNameNotBetween(String value1, String value2) {
			addCriterion("PARAMETER_NAME not between", value1, value2,
					"parameterName");
			return this;
		}

		public Criteria andParameterValueIsNull() {
			addCriterion("PARAMETER_VALUE is null");
			return this;
		}

		public Criteria andParameterValueIsNotNull() {
			addCriterion("PARAMETER_VALUE is not null");
			return this;
		}

		public Criteria andParameterValueEqualTo(String value) {
			addCriterion("PARAMETER_VALUE =", value, "parameterValue");
			return this;
		}

		public Criteria andParameterValueNotEqualTo(String value) {
			addCriterion("PARAMETER_VALUE <>", value, "parameterValue");
			return this;
		}

		public Criteria andParameterValueGreaterThan(String value) {
			addCriterion("PARAMETER_VALUE >", value, "parameterValue");
			return this;
		}

		public Criteria andParameterValueGreaterThanOrEqualTo(String value) {
			addCriterion("PARAMETER_VALUE >=", value, "parameterValue");
			return this;
		}

		public Criteria andParameterValueLessThan(String value) {
			addCriterion("PARAMETER_VALUE <", value, "parameterValue");
			return this;
		}

		public Criteria andParameterValueLessThanOrEqualTo(String value) {
			addCriterion("PARAMETER_VALUE <=", value, "parameterValue");
			return this;
		}

		public Criteria andParameterValueLike(String value) {
			addCriterion("PARAMETER_VALUE like", value, "parameterValue");
			return this;
		}

		public Criteria andParameterValueNotLike(String value) {
			addCriterion("PARAMETER_VALUE not like", value, "parameterValue");
			return this;
		}

		public Criteria andParameterValueIn(List<String> values) {
			addCriterion("PARAMETER_VALUE in", values, "parameterValue");
			return this;
		}

		public Criteria andParameterValueNotIn(List<String> values) {
			addCriterion("PARAMETER_VALUE not in", values, "parameterValue");
			return this;
		}

		public Criteria andParameterValueBetween(String value1, String value2) {
			addCriterion("PARAMETER_VALUE between", value1, value2,
					"parameterValue");
			return this;
		}

		public Criteria andParameterValueNotBetween(String value1, String value2) {
			addCriterion("PARAMETER_VALUE not between", value1, value2,
					"parameterValue");
			return this;
		}

		public Criteria andParameterRoleIsNull() {
			addCriterion("PARAMETER_ROLE is null");
			return this;
		}

		public Criteria andParameterRoleIsNotNull() {
			addCriterion("PARAMETER_ROLE is not null");
			return this;
		}

		public Criteria andParameterRoleEqualTo(String value) {
			addCriterion("PARAMETER_ROLE =", value, "parameterRole");
			return this;
		}

		public Criteria andParameterRoleNotEqualTo(String value) {
			addCriterion("PARAMETER_ROLE <>", value, "parameterRole");
			return this;
		}

		public Criteria andParameterRoleGreaterThan(String value) {
			addCriterion("PARAMETER_ROLE >", value, "parameterRole");
			return this;
		}

		public Criteria andParameterRoleGreaterThanOrEqualTo(String value) {
			addCriterion("PARAMETER_ROLE >=", value, "parameterRole");
			return this;
		}

		public Criteria andParameterRoleLessThan(String value) {
			addCriterion("PARAMETER_ROLE <", value, "parameterRole");
			return this;
		}

		public Criteria andParameterRoleLessThanOrEqualTo(String value) {
			addCriterion("PARAMETER_ROLE <=", value, "parameterRole");
			return this;
		}

		public Criteria andParameterRoleLike(String value) {
			addCriterion("PARAMETER_ROLE like", value, "parameterRole");
			return this;
		}

		public Criteria andParameterRoleNotLike(String value) {
			addCriterion("PARAMETER_ROLE not like", value, "parameterRole");
			return this;
		}

		public Criteria andParameterRoleIn(List<String> values) {
			addCriterion("PARAMETER_ROLE in", values, "parameterRole");
			return this;
		}

		public Criteria andParameterRoleNotIn(List<String> values) {
			addCriterion("PARAMETER_ROLE not in", values, "parameterRole");
			return this;
		}

		public Criteria andParameterRoleBetween(String value1, String value2) {
			addCriterion("PARAMETER_ROLE between", value1, value2,
					"parameterRole");
			return this;
		}

		public Criteria andParameterRoleNotBetween(String value1, String value2) {
			addCriterion("PARAMETER_ROLE not between", value1, value2,
					"parameterRole");
			return this;
		}

		public Criteria andParameterCommentIsNull() {
			addCriterion("PARAMETER_COMMENT is null");
			return this;
		}

		public Criteria andParameterCommentIsNotNull() {
			addCriterion("PARAMETER_COMMENT is not null");
			return this;
		}

		public Criteria andParameterCommentEqualTo(String value) {
			addCriterion("PARAMETER_COMMENT =", value, "parameterComment");
			return this;
		}

		public Criteria andParameterCommentNotEqualTo(String value) {
			addCriterion("PARAMETER_COMMENT <>", value, "parameterComment");
			return this;
		}

		public Criteria andParameterCommentGreaterThan(String value) {
			addCriterion("PARAMETER_COMMENT >", value, "parameterComment");
			return this;
		}

		public Criteria andParameterCommentGreaterThanOrEqualTo(String value) {
			addCriterion("PARAMETER_COMMENT >=", value, "parameterComment");
			return this;
		}

		public Criteria andParameterCommentLessThan(String value) {
			addCriterion("PARAMETER_COMMENT <", value, "parameterComment");
			return this;
		}

		public Criteria andParameterCommentLessThanOrEqualTo(String value) {
			addCriterion("PARAMETER_COMMENT <=", value, "parameterComment");
			return this;
		}

		public Criteria andParameterCommentLike(String value) {
			addCriterion("PARAMETER_COMMENT like", value, "parameterComment");
			return this;
		}

		public Criteria andParameterCommentNotLike(String value) {
			addCriterion("PARAMETER_COMMENT not like", value,
					"parameterComment");
			return this;
		}

		public Criteria andParameterCommentIn(List<String> values) {
			addCriterion("PARAMETER_COMMENT in", values, "parameterComment");
			return this;
		}

		public Criteria andParameterCommentNotIn(List<String> values) {
			addCriterion("PARAMETER_COMMENT not in", values, "parameterComment");
			return this;
		}

		public Criteria andParameterCommentBetween(String value1, String value2) {
			addCriterion("PARAMETER_COMMENT between", value1, value2,
					"parameterComment");
			return this;
		}

		public Criteria andParameterCommentNotBetween(String value1,
				String value2) {
			addCriterion("PARAMETER_COMMENT not between", value1, value2,
					"parameterComment");
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