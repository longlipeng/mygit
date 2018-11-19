package com.huateng.framework.db.ibatis.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityStockExample {
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to
	 * the database table TB_ENTITY_STOCK
	 * 
	 * @abatorgenerated Thu Jan 13 14:19:16 CST 2011
	 */
	protected String orderByClause;

	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to
	 * the database table TB_ENTITY_STOCK
	 * 
	 * @abatorgenerated Thu Jan 13 14:19:16 CST 2011
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_STOCK
	 * 
	 * @abatorgenerated Thu Jan 13 14:19:16 CST 2011
	 */
	public EntityStockExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_STOCK
	 * 
	 * @abatorgenerated Thu Jan 13 14:19:16 CST 2011
	 */
	protected EntityStockExample(EntityStockExample example) {
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_STOCK
	 * 
	 * @abatorgenerated Thu Jan 13 14:19:16 CST 2011
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_STOCK
	 * 
	 * @abatorgenerated Thu Jan 13 14:19:16 CST 2011
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_STOCK
	 * 
	 * @abatorgenerated Thu Jan 13 14:19:16 CST 2011
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_STOCK
	 * 
	 * @abatorgenerated Thu Jan 13 14:19:16 CST 2011
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_STOCK
	 * 
	 * @abatorgenerated Thu Jan 13 14:19:16 CST 2011
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
	 * to the database table TB_ENTITY_STOCK
	 * 
	 * @abatorgenerated Thu Jan 13 14:19:16 CST 2011
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_STOCK
	 * 
	 * @abatorgenerated Thu Jan 13 14:19:16 CST 2011
	 */
	public void clear() {
		oredCriteria.clear();
	}

	/**
	 * This class was generated by Abator for iBATIS. This class corresponds to
	 * the database table TB_ENTITY_STOCK
	 * 
	 * @abatorgenerated Thu Jan 13 14:19:16 CST 2011
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

		public Criteria andCardNoIsNull() {
			addCriterion("CARD_NO is null");
			return this;
		}

		public Criteria andCardNoIsNotNull() {
			addCriterion("CARD_NO is not null");
			return this;
		}

		public Criteria andCardNoEqualTo(String value) {
			addCriterion("CARD_NO =", value, "cardNo");
			return this;
		}

		public Criteria andCardNoNotEqualTo(String value) {
			addCriterion("CARD_NO <>", value, "cardNo");
			return this;
		}

		public Criteria andCardNoGreaterThan(String value) {
			addCriterion("CARD_NO >", value, "cardNo");
			return this;
		}

		public Criteria andCardNoGreaterThanOrEqualTo(String value) {
			addCriterion("CARD_NO >=", value, "cardNo");
			return this;
		}

		public Criteria andCardNoLessThan(String value) {
			addCriterion("CARD_NO <", value, "cardNo");
			return this;
		}

		public Criteria andCardNoLessThanOrEqualTo(String value) {
			addCriterion("CARD_NO <=", value, "cardNo");
			return this;
		}

		public Criteria andCardNoLike(String value) {
			addCriterion("CARD_NO like", value, "cardNo");
			return this;
		}

		public Criteria andCardNoNotLike(String value) {
			addCriterion("CARD_NO not like", value, "cardNo");
			return this;
		}

		public Criteria andCardNoIn(List<String> values) {
			addCriterion("CARD_NO in", values, "cardNo");
			return this;
		}

		public Criteria andCardNoNotIn(List<String> values) {
			addCriterion("CARD_NO not in", values, "cardNo");
			return this;
		}

		public Criteria andCardNoBetween(String value1, String value2) {
			addCriterion("CARD_NO between", value1, value2, "cardNo");
			return this;
		}

		public Criteria andCardNoNotBetween(String value1, String value2) {
			addCriterion("CARD_NO not between", value1, value2, "cardNo");
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

		public Criteria andFunctionRoleIdIsNull() {
			addCriterion("FUNCTION_ROLE_ID is null");
			return this;
		}

		public Criteria andFunctionRoleIdIsNotNull() {
			addCriterion("FUNCTION_ROLE_ID is not null");
			return this;
		}

		public Criteria andFunctionRoleIdEqualTo(String value) {
			addCriterion("FUNCTION_ROLE_ID =", value, "functionRoleId");
			return this;
		}

		public Criteria andFunctionRoleIdNotEqualTo(String value) {
			addCriterion("FUNCTION_ROLE_ID <>", value, "functionRoleId");
			return this;
		}

		public Criteria andFunctionRoleIdGreaterThan(String value) {
			addCriterion("FUNCTION_ROLE_ID >", value, "functionRoleId");
			return this;
		}

		public Criteria andFunctionRoleIdGreaterThanOrEqualTo(String value) {
			addCriterion("FUNCTION_ROLE_ID >=", value, "functionRoleId");
			return this;
		}

		public Criteria andFunctionRoleIdLessThan(String value) {
			addCriterion("FUNCTION_ROLE_ID <", value, "functionRoleId");
			return this;
		}

		public Criteria andFunctionRoleIdLessThanOrEqualTo(String value) {
			addCriterion("FUNCTION_ROLE_ID <=", value, "functionRoleId");
			return this;
		}

		public Criteria andFunctionRoleIdLike(String value) {
			addCriterion("FUNCTION_ROLE_ID like", value, "functionRoleId");
			return this;
		}

		public Criteria andFunctionRoleIdNotLike(String value) {
			addCriterion("FUNCTION_ROLE_ID not like", value, "functionRoleId");
			return this;
		}

		public Criteria andFunctionRoleIdIn(List<String> values) {
			addCriterion("FUNCTION_ROLE_ID in", values, "functionRoleId");
			return this;
		}

		public Criteria andFunctionRoleIdNotIn(List<String> values) {
			addCriterion("FUNCTION_ROLE_ID not in", values, "functionRoleId");
			return this;
		}

		public Criteria andFunctionRoleIdBetween(String value1, String value2) {
			addCriterion("FUNCTION_ROLE_ID between", value1, value2,
					"functionRoleId");
			return this;
		}

		public Criteria andFunctionRoleIdNotBetween(String value1, String value2) {
			addCriterion("FUNCTION_ROLE_ID not between", value1, value2,
					"functionRoleId");
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

		public Criteria andCardLayoutIdIsNull() {
			addCriterion("CARD_LAYOUT_ID is null");
			return this;
		}

		public Criteria andCardLayoutIdIsNotNull() {
			addCriterion("CARD_LAYOUT_ID is not null");
			return this;
		}

		public Criteria andCardLayoutIdEqualTo(String value) {
			addCriterion("CARD_LAYOUT_ID =", value, "cardLayoutId");
			return this;
		}

		public Criteria andCardLayoutIdNotEqualTo(String value) {
			addCriterion("CARD_LAYOUT_ID <>", value, "cardLayoutId");
			return this;
		}

		public Criteria andCardLayoutIdGreaterThan(String value) {
			addCriterion("CARD_LAYOUT_ID >", value, "cardLayoutId");
			return this;
		}

		public Criteria andCardLayoutIdGreaterThanOrEqualTo(String value) {
			addCriterion("CARD_LAYOUT_ID >=", value, "cardLayoutId");
			return this;
		}

		public Criteria andCardLayoutIdLessThan(String value) {
			addCriterion("CARD_LAYOUT_ID <", value, "cardLayoutId");
			return this;
		}

		public Criteria andCardLayoutIdLessThanOrEqualTo(String value) {
			addCriterion("CARD_LAYOUT_ID <=", value, "cardLayoutId");
			return this;
		}

		public Criteria andCardLayoutIdLike(String value) {
			addCriterion("CARD_LAYOUT_ID like", value, "cardLayoutId");
			return this;
		}

		public Criteria andCardLayoutIdNotLike(String value) {
			addCriterion("CARD_LAYOUT_ID not like", value, "cardLayoutId");
			return this;
		}

		public Criteria andCardLayoutIdIn(List<String> values) {
			addCriterion("CARD_LAYOUT_ID in", values, "cardLayoutId");
			return this;
		}

		public Criteria andCardLayoutIdNotIn(List<String> values) {
			addCriterion("CARD_LAYOUT_ID not in", values, "cardLayoutId");
			return this;
		}

		public Criteria andCardLayoutIdBetween(String value1, String value2) {
			addCriterion("CARD_LAYOUT_ID between", value1, value2,
					"cardLayoutId");
			return this;
		}

		public Criteria andCardLayoutIdNotBetween(String value1, String value2) {
			addCriterion("CARD_LAYOUT_ID not between", value1, value2,
					"cardLayoutId");
			return this;
		}

		public Criteria andFaceValueTypeIsNull() {
			addCriterion("FACE_VALUE_TYPE is null");
			return this;
		}

		public Criteria andFaceValueTypeIsNotNull() {
			addCriterion("FACE_VALUE_TYPE is not null");
			return this;
		}

		public Criteria andFaceValueTypeEqualTo(String value) {
			addCriterion("FACE_VALUE_TYPE =", value, "faceValueType");
			return this;
		}

		public Criteria andFaceValueTypeNotEqualTo(String value) {
			addCriterion("FACE_VALUE_TYPE <>", value, "faceValueType");
			return this;
		}

		public Criteria andFaceValueTypeGreaterThan(String value) {
			addCriterion("FACE_VALUE_TYPE >", value, "faceValueType");
			return this;
		}

		public Criteria andFaceValueTypeGreaterThanOrEqualTo(String value) {
			addCriterion("FACE_VALUE_TYPE >=", value, "faceValueType");
			return this;
		}

		public Criteria andFaceValueTypeLessThan(String value) {
			addCriterion("FACE_VALUE_TYPE <", value, "faceValueType");
			return this;
		}

		public Criteria andFaceValueTypeLessThanOrEqualTo(String value) {
			addCriterion("FACE_VALUE_TYPE <=", value, "faceValueType");
			return this;
		}

		public Criteria andFaceValueTypeLike(String value) {
			addCriterion("FACE_VALUE_TYPE like", value, "faceValueType");
			return this;
		}

		public Criteria andFaceValueTypeNotLike(String value) {
			addCriterion("FACE_VALUE_TYPE not like", value, "faceValueType");
			return this;
		}

		public Criteria andFaceValueTypeIn(List<String> values) {
			addCriterion("FACE_VALUE_TYPE in", values, "faceValueType");
			return this;
		}

		public Criteria andFaceValueTypeNotIn(List<String> values) {
			addCriterion("FACE_VALUE_TYPE not in", values, "faceValueType");
			return this;
		}

		public Criteria andFaceValueTypeBetween(String value1, String value2) {
			addCriterion("FACE_VALUE_TYPE between", value1, value2,
					"faceValueType");
			return this;
		}

		public Criteria andFaceValueTypeNotBetween(String value1, String value2) {
			addCriterion("FACE_VALUE_TYPE not between", value1, value2,
					"faceValueType");
			return this;
		}

		public Criteria andFaceValueIsNull() {
			addCriterion("FACE_VALUE is null");
			return this;
		}

		public Criteria andFaceValueIsNotNull() {
			addCriterion("FACE_VALUE is not null");
			return this;
		}

		public Criteria andFaceValueEqualTo(String value) {
			addCriterion("FACE_VALUE =", value, "faceValue");
			return this;
		}

		public Criteria andFaceValueNotEqualTo(String value) {
			addCriterion("FACE_VALUE <>", value, "faceValue");
			return this;
		}

		public Criteria andFaceValueGreaterThan(String value) {
			addCriterion("FACE_VALUE >", value, "faceValue");
			return this;
		}

		public Criteria andFaceValueGreaterThanOrEqualTo(String value) {
			addCriterion("FACE_VALUE >=", value, "faceValue");
			return this;
		}

		public Criteria andFaceValueLessThan(String value) {
			addCriterion("FACE_VALUE <", value, "faceValue");
			return this;
		}

		public Criteria andFaceValueLessThanOrEqualTo(String value) {
			addCriterion("FACE_VALUE <=", value, "faceValue");
			return this;
		}

		public Criteria andFaceValueLike(String value) {
			addCriterion("FACE_VALUE like", value, "faceValue");
			return this;
		}

		public Criteria andFaceValueNotLike(String value) {
			addCriterion("FACE_VALUE not like", value, "faceValue");
			return this;
		}

		public Criteria andFaceValueIn(List<String> values) {
			addCriterion("FACE_VALUE in", values, "faceValue");
			return this;
		}

		public Criteria andFaceValueNotIn(List<String> values) {
			addCriterion("FACE_VALUE not in", values, "faceValue");
			return this;
		}

		public Criteria andFaceValueBetween(String value1, String value2) {
			addCriterion("FACE_VALUE between", value1, value2, "faceValue");
			return this;
		}

		public Criteria andFaceValueNotBetween(String value1, String value2) {
			addCriterion("FACE_VALUE not between", value1, value2, "faceValue");
			return this;
		}

		public Criteria andCardValidDateIsNull() {
			addCriterion("CARD_VALID_DATE is null");
			return this;
		}

		public Criteria andCardValidDateIsNotNull() {
			addCriterion("CARD_VALID_DATE is not null");
			return this;
		}

		public Criteria andCardValidDateEqualTo(String value) {
			addCriterion("CARD_VALID_DATE =", value, "cardValidDate");
			return this;
		}

		public Criteria andCardValidDateNotEqualTo(String value) {
			addCriterion("CARD_VALID_DATE <>", value, "cardValidDate");
			return this;
		}

		public Criteria andCardValidDateGreaterThan(String value) {
			addCriterion("CARD_VALID_DATE >", value, "cardValidDate");
			return this;
		}

		public Criteria andCardValidDateGreaterThanOrEqualTo(String value) {
			addCriterion("CARD_VALID_DATE >=", value, "cardValidDate");
			return this;
		}

		public Criteria andCardValidDateLessThan(String value) {
			addCriterion("CARD_VALID_DATE <", value, "cardValidDate");
			return this;
		}

		public Criteria andCardValidDateLessThanOrEqualTo(String value) {
			addCriterion("CARD_VALID_DATE <=", value, "cardValidDate");
			return this;
		}

		public Criteria andCardValidDateLike(String value) {
			addCriterion("CARD_VALID_DATE like", value, "cardValidDate");
			return this;
		}

		public Criteria andCardValidDateNotLike(String value) {
			addCriterion("CARD_VALID_DATE not like", value, "cardValidDate");
			return this;
		}

		public Criteria andCardValidDateIn(List<String> values) {
			addCriterion("CARD_VALID_DATE in", values, "cardValidDate");
			return this;
		}

		public Criteria andCardValidDateNotIn(List<String> values) {
			addCriterion("CARD_VALID_DATE not in", values, "cardValidDate");
			return this;
		}

		public Criteria andCardValidDateBetween(String value1, String value2) {
			addCriterion("CARD_VALID_DATE between", value1, value2,
					"cardValidDate");
			return this;
		}

		public Criteria andCardValidDateNotBetween(String value1, String value2) {
			addCriterion("CARD_VALID_DATE not between", value1, value2,
					"cardValidDate");
			return this;
		}

		public Criteria andStockStateIsNull() {
			addCriterion("STOCK_STATE is null");
			return this;
		}

		public Criteria andStockStateIsNotNull() {
			addCriterion("STOCK_STATE is not null");
			return this;
		}

		public Criteria andStockStateEqualTo(String value) {
			addCriterion("STOCK_STATE =", value, "stockState");
			return this;
		}

		public Criteria andStockStateNotEqualTo(String value) {
			addCriterion("STOCK_STATE <>", value, "stockState");
			return this;
		}

		public Criteria andStockStateGreaterThan(String value) {
			addCriterion("STOCK_STATE >", value, "stockState");
			return this;
		}

		public Criteria andStockStateGreaterThanOrEqualTo(String value) {
			addCriterion("STOCK_STATE >=", value, "stockState");
			return this;
		}

		public Criteria andStockStateLessThan(String value) {
			addCriterion("STOCK_STATE <", value, "stockState");
			return this;
		}

		public Criteria andStockStateLessThanOrEqualTo(String value) {
			addCriterion("STOCK_STATE <=", value, "stockState");
			return this;
		}

		public Criteria andStockStateLike(String value) {
			addCriterion("STOCK_STATE like", value, "stockState");
			return this;
		}

		public Criteria andStockStateNotLike(String value) {
			addCriterion("STOCK_STATE not like", value, "stockState");
			return this;
		}

		public Criteria andStockStateIn(List<String> values) {
			addCriterion("STOCK_STATE in", values, "stockState");
			return this;
		}

		public Criteria andStockStateNotIn(List<String> values) {
			addCriterion("STOCK_STATE not in", values, "stockState");
			return this;
		}

		public Criteria andStockStateBetween(String value1, String value2) {
			addCriterion("STOCK_STATE between", value1, value2, "stockState");
			return this;
		}

		public Criteria andStockStateNotBetween(String value1, String value2) {
			addCriterion("STOCK_STATE not between", value1, value2,
					"stockState");
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