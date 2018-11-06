package com.huateng.framework.ibatis.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityDepartmentExample {
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to
	 * the database table TB_ENTITY_DEPARTMENT
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:40 CST 2010
	 */
	protected String orderByClause;

	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to
	 * the database table TB_ENTITY_DEPARTMENT
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:40 CST 2010
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_DEPARTMENT
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:40 CST 2010
	 */
	public EntityDepartmentExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_DEPARTMENT
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:40 CST 2010
	 */
	protected EntityDepartmentExample(EntityDepartmentExample example) {
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_DEPARTMENT
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:40 CST 2010
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_DEPARTMENT
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:40 CST 2010
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_DEPARTMENT
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:40 CST 2010
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_DEPARTMENT
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:40 CST 2010
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_DEPARTMENT
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:40 CST 2010
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
	 * to the database table TB_ENTITY_DEPARTMENT
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:40 CST 2010
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_DEPARTMENT
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:40 CST 2010
	 */
	public void clear() {
		oredCriteria.clear();
	}

	/**
	 * This class was generated by Abator for iBATIS. This class corresponds to
	 * the database table TB_ENTITY_DEPARTMENT
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:40 CST 2010
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

		public Criteria andDepartmentIdIsNull() {
			addCriterion("DEPARTMENT_ID is null");
			return this;
		}

		public Criteria andDepartmentIdIsNotNull() {
			addCriterion("DEPARTMENT_ID is not null");
			return this;
		}

		public Criteria andDepartmentIdEqualTo(String value) {
			addCriterion("DEPARTMENT_ID =", value, "departmentId");
			return this;
		}

		public Criteria andDepartmentIdNotEqualTo(String value) {
			addCriterion("DEPARTMENT_ID <>", value, "departmentId");
			return this;
		}

		public Criteria andDepartmentIdGreaterThan(String value) {
			addCriterion("DEPARTMENT_ID >", value, "departmentId");
			return this;
		}

		public Criteria andDepartmentIdGreaterThanOrEqualTo(String value) {
			addCriterion("DEPARTMENT_ID >=", value, "departmentId");
			return this;
		}

		public Criteria andDepartmentIdLessThan(String value) {
			addCriterion("DEPARTMENT_ID <", value, "departmentId");
			return this;
		}

		public Criteria andDepartmentIdLessThanOrEqualTo(String value) {
			addCriterion("DEPARTMENT_ID <=", value, "departmentId");
			return this;
		}

		public Criteria andDepartmentIdLike(String value) {
			addCriterion("DEPARTMENT_ID like", value, "departmentId");
			return this;
		}

		public Criteria andDepartmentIdNotLike(String value) {
			addCriterion("DEPARTMENT_ID not like", value, "departmentId");
			return this;
		}

		public Criteria andDepartmentIdIn(List<String> values) {
			addCriterion("DEPARTMENT_ID in", values, "departmentId");
			return this;
		}

		public Criteria andDepartmentIdNotIn(List<String> values) {
			addCriterion("DEPARTMENT_ID not in", values, "departmentId");
			return this;
		}

		public Criteria andDepartmentIdBetween(String value1, String value2) {
			addCriterion("DEPARTMENT_ID between", value1, value2,
					"departmentId");
			return this;
		}

		public Criteria andDepartmentIdNotBetween(String value1, String value2) {
			addCriterion("DEPARTMENT_ID not between", value1, value2,
					"departmentId");
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

		public Criteria andDepartmentNameIsNull() {
			addCriterion("DEPARTMENT_NAME is null");
			return this;
		}

		public Criteria andDepartmentNameIsNotNull() {
			addCriterion("DEPARTMENT_NAME is not null");
			return this;
		}

		public Criteria andDepartmentNameEqualTo(String value) {
			addCriterion("DEPARTMENT_NAME =", value, "departmentName");
			return this;
		}

		public Criteria andDepartmentNameNotEqualTo(String value) {
			addCriterion("DEPARTMENT_NAME <>", value, "departmentName");
			return this;
		}

		public Criteria andDepartmentNameGreaterThan(String value) {
			addCriterion("DEPARTMENT_NAME >", value, "departmentName");
			return this;
		}

		public Criteria andDepartmentNameGreaterThanOrEqualTo(String value) {
			addCriterion("DEPARTMENT_NAME >=", value, "departmentName");
			return this;
		}

		public Criteria andDepartmentNameLessThan(String value) {
			addCriterion("DEPARTMENT_NAME <", value, "departmentName");
			return this;
		}

		public Criteria andDepartmentNameLessThanOrEqualTo(String value) {
			addCriterion("DEPARTMENT_NAME <=", value, "departmentName");
			return this;
		}

		public Criteria andDepartmentNameLike(String value) {
			addCriterion("DEPARTMENT_NAME like", value, "departmentName");
			return this;
		}

		public Criteria andDepartmentNameNotLike(String value) {
			addCriterion("DEPARTMENT_NAME not like", value, "departmentName");
			return this;
		}

		public Criteria andDepartmentNameIn(List<String> values) {
			addCriterion("DEPARTMENT_NAME in", values, "departmentName");
			return this;
		}

		public Criteria andDepartmentNameNotIn(List<String> values) {
			addCriterion("DEPARTMENT_NAME not in", values, "departmentName");
			return this;
		}

		public Criteria andDepartmentNameBetween(String value1, String value2) {
			addCriterion("DEPARTMENT_NAME between", value1, value2,
					"departmentName");
			return this;
		}

		public Criteria andDepartmentNameNotBetween(String value1, String value2) {
			addCriterion("DEPARTMENT_NAME not between", value1, value2,
					"departmentName");
			return this;
		}

		public Criteria andDefaultFlagIsNull() {
			addCriterion("DEFAULT_FLAG is null");
			return this;
		}

		public Criteria andDefaultFlagIsNotNull() {
			addCriterion("DEFAULT_FLAG is not null");
			return this;
		}

		public Criteria andDefaultFlagEqualTo(String value) {
			addCriterion("DEFAULT_FLAG =", value, "defaultFlag");
			return this;
		}

		public Criteria andDefaultFlagNotEqualTo(String value) {
			addCriterion("DEFAULT_FLAG <>", value, "defaultFlag");
			return this;
		}

		public Criteria andDefaultFlagGreaterThan(String value) {
			addCriterion("DEFAULT_FLAG >", value, "defaultFlag");
			return this;
		}

		public Criteria andDefaultFlagGreaterThanOrEqualTo(String value) {
			addCriterion("DEFAULT_FLAG >=", value, "defaultFlag");
			return this;
		}

		public Criteria andDefaultFlagLessThan(String value) {
			addCriterion("DEFAULT_FLAG <", value, "defaultFlag");
			return this;
		}

		public Criteria andDefaultFlagLessThanOrEqualTo(String value) {
			addCriterion("DEFAULT_FLAG <=", value, "defaultFlag");
			return this;
		}

		public Criteria andDefaultFlagLike(String value) {
			addCriterion("DEFAULT_FLAG like", value, "defaultFlag");
			return this;
		}

		public Criteria andDefaultFlagNotLike(String value) {
			addCriterion("DEFAULT_FLAG not like", value, "defaultFlag");
			return this;
		}

		public Criteria andDefaultFlagIn(List<String> values) {
			addCriterion("DEFAULT_FLAG in", values, "defaultFlag");
			return this;
		}

		public Criteria andDefaultFlagNotIn(List<String> values) {
			addCriterion("DEFAULT_FLAG not in", values, "defaultFlag");
			return this;
		}

		public Criteria andDefaultFlagBetween(String value1, String value2) {
			addCriterion("DEFAULT_FLAG between", value1, value2, "defaultFlag");
			return this;
		}

		public Criteria andDefaultFlagNotBetween(String value1, String value2) {
			addCriterion("DEFAULT_FLAG not between", value1, value2,
					"defaultFlag");
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