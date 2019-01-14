package com.huateng.framework.ibatis.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityDictInfoExample {
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to
	 * the database table TB_ENTITY_DICT_INFO
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:41 CST 2010
	 */
	protected String orderByClause;

	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to
	 * the database table TB_ENTITY_DICT_INFO
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:41 CST 2010
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_DICT_INFO
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:41 CST 2010
	 */
	public EntityDictInfoExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_DICT_INFO
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:41 CST 2010
	 */
	protected EntityDictInfoExample(EntityDictInfoExample example) {
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_DICT_INFO
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:41 CST 2010
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_DICT_INFO
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:41 CST 2010
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_DICT_INFO
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:41 CST 2010
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_DICT_INFO
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:41 CST 2010
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_DICT_INFO
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:41 CST 2010
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
	 * to the database table TB_ENTITY_DICT_INFO
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:41 CST 2010
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_DICT_INFO
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:41 CST 2010
	 */
	public void clear() {
		oredCriteria.clear();
	}

	/**
	 * This class was generated by Abator for iBATIS. This class corresponds to
	 * the database table TB_ENTITY_DICT_INFO
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:41 CST 2010
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

		public Criteria andDictIdIsNull() {
			addCriterion("DICT_ID is null");
			return this;
		}

		public Criteria andDictIdIsNotNull() {
			addCriterion("DICT_ID is not null");
			return this;
		}

		public Criteria andDictIdEqualTo(String value) {
			addCriterion("DICT_ID =", value, "dictId");
			return this;
		}

		public Criteria andDictIdNotEqualTo(String value) {
			addCriterion("DICT_ID <>", value, "dictId");
			return this;
		}

		public Criteria andDictIdGreaterThan(String value) {
			addCriterion("DICT_ID >", value, "dictId");
			return this;
		}

		public Criteria andDictIdGreaterThanOrEqualTo(String value) {
			addCriterion("DICT_ID >=", value, "dictId");
			return this;
		}

		public Criteria andDictIdLessThan(String value) {
			addCriterion("DICT_ID <", value, "dictId");
			return this;
		}

		public Criteria andDictIdLessThanOrEqualTo(String value) {
			addCriterion("DICT_ID <=", value, "dictId");
			return this;
		}

		public Criteria andDictIdLike(String value) {
			addCriterion("DICT_ID like", value, "dictId");
			return this;
		}

		public Criteria andDictIdNotLike(String value) {
			addCriterion("DICT_ID not like", value, "dictId");
			return this;
		}

		public Criteria andDictIdIn(List<String> values) {
			addCriterion("DICT_ID in", values, "dictId");
			return this;
		}

		public Criteria andDictIdNotIn(List<String> values) {
			addCriterion("DICT_ID not in", values, "dictId");
			return this;
		}

		public Criteria andDictIdBetween(String value1, String value2) {
			addCriterion("DICT_ID between", value1, value2, "dictId");
			return this;
		}

		public Criteria andDictIdNotBetween(String value1, String value2) {
			addCriterion("DICT_ID not between", value1, value2, "dictId");
			return this;
		}

		public Criteria andDictCodeIsNull() {
			addCriterion("DICT_CODE is null");
			return this;
		}

		public Criteria andDictCodeIsNotNull() {
			addCriterion("DICT_CODE is not null");
			return this;
		}

		public Criteria andDictCodeEqualTo(String value) {
			addCriterion("DICT_CODE =", value, "dictCode");
			return this;
		}

		public Criteria andDictCodeNotEqualTo(String value) {
			addCriterion("DICT_CODE <>", value, "dictCode");
			return this;
		}

		public Criteria andDictCodeGreaterThan(String value) {
			addCriterion("DICT_CODE >", value, "dictCode");
			return this;
		}

		public Criteria andDictCodeGreaterThanOrEqualTo(String value) {
			addCriterion("DICT_CODE >=", value, "dictCode");
			return this;
		}

		public Criteria andDictCodeLessThan(String value) {
			addCriterion("DICT_CODE <", value, "dictCode");
			return this;
		}

		public Criteria andDictCodeLessThanOrEqualTo(String value) {
			addCriterion("DICT_CODE <=", value, "dictCode");
			return this;
		}

		public Criteria andDictCodeLike(String value) {
			addCriterion("DICT_CODE like", value, "dictCode");
			return this;
		}

		public Criteria andDictCodeNotLike(String value) {
			addCriterion("DICT_CODE not like", value, "dictCode");
			return this;
		}

		public Criteria andDictCodeIn(List<String> values) {
			addCriterion("DICT_CODE in", values, "dictCode");
			return this;
		}

		public Criteria andDictCodeNotIn(List<String> values) {
			addCriterion("DICT_CODE not in", values, "dictCode");
			return this;
		}

		public Criteria andDictCodeBetween(String value1, String value2) {
			addCriterion("DICT_CODE between", value1, value2, "dictCode");
			return this;
		}

		public Criteria andDictCodeNotBetween(String value1, String value2) {
			addCriterion("DICT_CODE not between", value1, value2, "dictCode");
			return this;
		}

		public Criteria andDictTypeCodeIsNull() {
			addCriterion("DICT_TYPE_CODE is null");
			return this;
		}

		public Criteria andDictTypeCodeIsNotNull() {
			addCriterion("DICT_TYPE_CODE is not null");
			return this;
		}

		public Criteria andDictTypeCodeEqualTo(String value) {
			addCriterion("DICT_TYPE_CODE =", value, "dictTypeCode");
			return this;
		}

		public Criteria andDictTypeCodeNotEqualTo(String value) {
			addCriterion("DICT_TYPE_CODE <>", value, "dictTypeCode");
			return this;
		}

		public Criteria andDictTypeCodeGreaterThan(String value) {
			addCriterion("DICT_TYPE_CODE >", value, "dictTypeCode");
			return this;
		}

		public Criteria andDictTypeCodeGreaterThanOrEqualTo(String value) {
			addCriterion("DICT_TYPE_CODE >=", value, "dictTypeCode");
			return this;
		}

		public Criteria andDictTypeCodeLessThan(String value) {
			addCriterion("DICT_TYPE_CODE <", value, "dictTypeCode");
			return this;
		}

		public Criteria andDictTypeCodeLessThanOrEqualTo(String value) {
			addCriterion("DICT_TYPE_CODE <=", value, "dictTypeCode");
			return this;
		}

		public Criteria andDictTypeCodeLike(String value) {
			addCriterion("DICT_TYPE_CODE like", value, "dictTypeCode");
			return this;
		}

		public Criteria andDictTypeCodeNotLike(String value) {
			addCriterion("DICT_TYPE_CODE not like", value, "dictTypeCode");
			return this;
		}

		public Criteria andDictTypeCodeIn(List<String> values) {
			addCriterion("DICT_TYPE_CODE in", values, "dictTypeCode");
			return this;
		}

		public Criteria andDictTypeCodeNotIn(List<String> values) {
			addCriterion("DICT_TYPE_CODE not in", values, "dictTypeCode");
			return this;
		}

		public Criteria andDictTypeCodeBetween(String value1, String value2) {
			addCriterion("DICT_TYPE_CODE between", value1, value2,
					"dictTypeCode");
			return this;
		}

		public Criteria andDictTypeCodeNotBetween(String value1, String value2) {
			addCriterion("DICT_TYPE_CODE not between", value1, value2,
					"dictTypeCode");
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

		public Criteria andDictNameIsNull() {
			addCriterion("DICT_NAME is null");
			return this;
		}

		public Criteria andDictNameIsNotNull() {
			addCriterion("DICT_NAME is not null");
			return this;
		}

		public Criteria andDictNameEqualTo(String value) {
			addCriterion("DICT_NAME =", value, "dictName");
			return this;
		}

		public Criteria andDictNameNotEqualTo(String value) {
			addCriterion("DICT_NAME <>", value, "dictName");
			return this;
		}

		public Criteria andDictNameGreaterThan(String value) {
			addCriterion("DICT_NAME >", value, "dictName");
			return this;
		}

		public Criteria andDictNameGreaterThanOrEqualTo(String value) {
			addCriterion("DICT_NAME >=", value, "dictName");
			return this;
		}

		public Criteria andDictNameLessThan(String value) {
			addCriterion("DICT_NAME <", value, "dictName");
			return this;
		}

		public Criteria andDictNameLessThanOrEqualTo(String value) {
			addCriterion("DICT_NAME <=", value, "dictName");
			return this;
		}

		public Criteria andDictNameLike(String value) {
			addCriterion("DICT_NAME like", value, "dictName");
			return this;
		}

		public Criteria andDictNameNotLike(String value) {
			addCriterion("DICT_NAME not like", value, "dictName");
			return this;
		}

		public Criteria andDictNameIn(List<String> values) {
			addCriterion("DICT_NAME in", values, "dictName");
			return this;
		}

		public Criteria andDictNameNotIn(List<String> values) {
			addCriterion("DICT_NAME not in", values, "dictName");
			return this;
		}

		public Criteria andDictNameBetween(String value1, String value2) {
			addCriterion("DICT_NAME between", value1, value2, "dictName");
			return this;
		}

		public Criteria andDictNameNotBetween(String value1, String value2) {
			addCriterion("DICT_NAME not between", value1, value2, "dictName");
			return this;
		}

		public Criteria andDictShortNameIsNull() {
			addCriterion("DICT_SHORT_NAME is null");
			return this;
		}

		public Criteria andDictShortNameIsNotNull() {
			addCriterion("DICT_SHORT_NAME is not null");
			return this;
		}

		public Criteria andDictShortNameEqualTo(String value) {
			addCriterion("DICT_SHORT_NAME =", value, "dictShortName");
			return this;
		}

		public Criteria andDictShortNameNotEqualTo(String value) {
			addCriterion("DICT_SHORT_NAME <>", value, "dictShortName");
			return this;
		}

		public Criteria andDictShortNameGreaterThan(String value) {
			addCriterion("DICT_SHORT_NAME >", value, "dictShortName");
			return this;
		}

		public Criteria andDictShortNameGreaterThanOrEqualTo(String value) {
			addCriterion("DICT_SHORT_NAME >=", value, "dictShortName");
			return this;
		}

		public Criteria andDictShortNameLessThan(String value) {
			addCriterion("DICT_SHORT_NAME <", value, "dictShortName");
			return this;
		}

		public Criteria andDictShortNameLessThanOrEqualTo(String value) {
			addCriterion("DICT_SHORT_NAME <=", value, "dictShortName");
			return this;
		}

		public Criteria andDictShortNameLike(String value) {
			addCriterion("DICT_SHORT_NAME like", value, "dictShortName");
			return this;
		}

		public Criteria andDictShortNameNotLike(String value) {
			addCriterion("DICT_SHORT_NAME not like", value, "dictShortName");
			return this;
		}

		public Criteria andDictShortNameIn(List<String> values) {
			addCriterion("DICT_SHORT_NAME in", values, "dictShortName");
			return this;
		}

		public Criteria andDictShortNameNotIn(List<String> values) {
			addCriterion("DICT_SHORT_NAME not in", values, "dictShortName");
			return this;
		}

		public Criteria andDictShortNameBetween(String value1, String value2) {
			addCriterion("DICT_SHORT_NAME between", value1, value2,
					"dictShortName");
			return this;
		}

		public Criteria andDictShortNameNotBetween(String value1, String value2) {
			addCriterion("DICT_SHORT_NAME not between", value1, value2,
					"dictShortName");
			return this;
		}

		public Criteria andDictEnglishNameIsNull() {
			addCriterion("DICT_ENGLISH_NAME is null");
			return this;
		}

		public Criteria andDictEnglishNameIsNotNull() {
			addCriterion("DICT_ENGLISH_NAME is not null");
			return this;
		}

		public Criteria andDictEnglishNameEqualTo(String value) {
			addCriterion("DICT_ENGLISH_NAME =", value, "dictEnglishName");
			return this;
		}

		public Criteria andDictEnglishNameNotEqualTo(String value) {
			addCriterion("DICT_ENGLISH_NAME <>", value, "dictEnglishName");
			return this;
		}

		public Criteria andDictEnglishNameGreaterThan(String value) {
			addCriterion("DICT_ENGLISH_NAME >", value, "dictEnglishName");
			return this;
		}

		public Criteria andDictEnglishNameGreaterThanOrEqualTo(String value) {
			addCriterion("DICT_ENGLISH_NAME >=", value, "dictEnglishName");
			return this;
		}

		public Criteria andDictEnglishNameLessThan(String value) {
			addCriterion("DICT_ENGLISH_NAME <", value, "dictEnglishName");
			return this;
		}

		public Criteria andDictEnglishNameLessThanOrEqualTo(String value) {
			addCriterion("DICT_ENGLISH_NAME <=", value, "dictEnglishName");
			return this;
		}

		public Criteria andDictEnglishNameLike(String value) {
			addCriterion("DICT_ENGLISH_NAME like", value, "dictEnglishName");
			return this;
		}

		public Criteria andDictEnglishNameNotLike(String value) {
			addCriterion("DICT_ENGLISH_NAME not like", value, "dictEnglishName");
			return this;
		}

		public Criteria andDictEnglishNameIn(List<String> values) {
			addCriterion("DICT_ENGLISH_NAME in", values, "dictEnglishName");
			return this;
		}

		public Criteria andDictEnglishNameNotIn(List<String> values) {
			addCriterion("DICT_ENGLISH_NAME not in", values, "dictEnglishName");
			return this;
		}

		public Criteria andDictEnglishNameBetween(String value1, String value2) {
			addCriterion("DICT_ENGLISH_NAME between", value1, value2,
					"dictEnglishName");
			return this;
		}

		public Criteria andDictEnglishNameNotBetween(String value1,
				String value2) {
			addCriterion("DICT_ENGLISH_NAME not between", value1, value2,
					"dictEnglishName");
			return this;
		}

		public Criteria andDictStateIsNull() {
			addCriterion("DICT_STATE is null");
			return this;
		}

		public Criteria andDictStateIsNotNull() {
			addCriterion("DICT_STATE is not null");
			return this;
		}

		public Criteria andDictStateEqualTo(String value) {
			addCriterion("DICT_STATE =", value, "dictState");
			return this;
		}

		public Criteria andDictStateNotEqualTo(String value) {
			addCriterion("DICT_STATE <>", value, "dictState");
			return this;
		}

		public Criteria andDictStateGreaterThan(String value) {
			addCriterion("DICT_STATE >", value, "dictState");
			return this;
		}

		public Criteria andDictStateGreaterThanOrEqualTo(String value) {
			addCriterion("DICT_STATE >=", value, "dictState");
			return this;
		}

		public Criteria andDictStateLessThan(String value) {
			addCriterion("DICT_STATE <", value, "dictState");
			return this;
		}

		public Criteria andDictStateLessThanOrEqualTo(String value) {
			addCriterion("DICT_STATE <=", value, "dictState");
			return this;
		}

		public Criteria andDictStateLike(String value) {
			addCriterion("DICT_STATE like", value, "dictState");
			return this;
		}

		public Criteria andDictStateNotLike(String value) {
			addCriterion("DICT_STATE not like", value, "dictState");
			return this;
		}

		public Criteria andDictStateIn(List<String> values) {
			addCriterion("DICT_STATE in", values, "dictState");
			return this;
		}

		public Criteria andDictStateNotIn(List<String> values) {
			addCriterion("DICT_STATE not in", values, "dictState");
			return this;
		}

		public Criteria andDictStateBetween(String value1, String value2) {
			addCriterion("DICT_STATE between", value1, value2, "dictState");
			return this;
		}

		public Criteria andDictStateNotBetween(String value1, String value2) {
			addCriterion("DICT_STATE not between", value1, value2, "dictState");
			return this;
		}

		public Criteria andFatherDictCodeIsNull() {
			addCriterion("FATHER_DICT_CODE is null");
			return this;
		}

		public Criteria andFatherDictCodeIsNotNull() {
			addCriterion("FATHER_DICT_CODE is not null");
			return this;
		}

		public Criteria andFatherDictCodeEqualTo(String value) {
			addCriterion("FATHER_DICT_CODE =", value, "fatherDictCode");
			return this;
		}

		public Criteria andFatherDictCodeNotEqualTo(String value) {
			addCriterion("FATHER_DICT_CODE <>", value, "fatherDictCode");
			return this;
		}

		public Criteria andFatherDictCodeGreaterThan(String value) {
			addCriterion("FATHER_DICT_CODE >", value, "fatherDictCode");
			return this;
		}

		public Criteria andFatherDictCodeGreaterThanOrEqualTo(String value) {
			addCriterion("FATHER_DICT_CODE >=", value, "fatherDictCode");
			return this;
		}

		public Criteria andFatherDictCodeLessThan(String value) {
			addCriterion("FATHER_DICT_CODE <", value, "fatherDictCode");
			return this;
		}

		public Criteria andFatherDictCodeLessThanOrEqualTo(String value) {
			addCriterion("FATHER_DICT_CODE <=", value, "fatherDictCode");
			return this;
		}

		public Criteria andFatherDictCodeLike(String value) {
			addCriterion("FATHER_DICT_CODE like", value, "fatherDictCode");
			return this;
		}

		public Criteria andFatherDictCodeNotLike(String value) {
			addCriterion("FATHER_DICT_CODE not like", value, "fatherDictCode");
			return this;
		}

		public Criteria andFatherDictCodeIn(List<String> values) {
			addCriterion("FATHER_DICT_CODE in", values, "fatherDictCode");
			return this;
		}

		public Criteria andFatherDictCodeNotIn(List<String> values) {
			addCriterion("FATHER_DICT_CODE not in", values, "fatherDictCode");
			return this;
		}

		public Criteria andFatherDictCodeBetween(String value1, String value2) {
			addCriterion("FATHER_DICT_CODE between", value1, value2,
					"fatherDictCode");
			return this;
		}

		public Criteria andFatherDictCodeNotBetween(String value1, String value2) {
			addCriterion("FATHER_DICT_CODE not between", value1, value2,
					"fatherDictCode");
			return this;
		}
	}
}