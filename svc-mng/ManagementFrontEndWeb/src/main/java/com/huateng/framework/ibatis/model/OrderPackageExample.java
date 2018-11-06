package com.huateng.framework.ibatis.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderPackageExample {
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to
	 * the database table TB_REL_ORDER_PACKAGE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:18 CST 2010
	 */
	protected String orderByClause;

	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to
	 * the database table TB_REL_ORDER_PACKAGE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:18 CST 2010
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_ORDER_PACKAGE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:18 CST 2010
	 */
	public OrderPackageExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_ORDER_PACKAGE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:18 CST 2010
	 */
	protected OrderPackageExample(OrderPackageExample example) {
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_ORDER_PACKAGE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:18 CST 2010
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_ORDER_PACKAGE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:18 CST 2010
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_ORDER_PACKAGE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:18 CST 2010
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_ORDER_PACKAGE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:18 CST 2010
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_ORDER_PACKAGE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:18 CST 2010
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
	 * to the database table TB_REL_ORDER_PACKAGE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:18 CST 2010
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_ORDER_PACKAGE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:18 CST 2010
	 */
	public void clear() {
		oredCriteria.clear();
	}

	/**
	 * This class was generated by Abator for iBATIS. This class corresponds to
	 * the database table TB_REL_ORDER_PACKAGE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:18 CST 2010
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

		public Criteria andOrderIdIsNull() {
			addCriterion("ORDER_ID is null");
			return this;
		}

		public Criteria andOrderIdIsNotNull() {
			addCriterion("ORDER_ID is not null");
			return this;
		}

		public Criteria andOrderIdEqualTo(String value) {
			addCriterion("ORDER_ID =", value, "orderId");
			return this;
		}

		public Criteria andOrderIdNotEqualTo(String value) {
			addCriterion("ORDER_ID <>", value, "orderId");
			return this;
		}

		public Criteria andOrderIdGreaterThan(String value) {
			addCriterion("ORDER_ID >", value, "orderId");
			return this;
		}

		public Criteria andOrderIdGreaterThanOrEqualTo(String value) {
			addCriterion("ORDER_ID >=", value, "orderId");
			return this;
		}

		public Criteria andOrderIdLessThan(String value) {
			addCriterion("ORDER_ID <", value, "orderId");
			return this;
		}

		public Criteria andOrderIdLessThanOrEqualTo(Long value) {
			addCriterion("ORDER_ID <=", value, "orderId");
			return this;
		}

		public Criteria andOrderIdIn(List<Long> values) {
			addCriterion("ORDER_ID in", values, "orderId");
			return this;
		}

		public Criteria andOrderIdNotIn(List<Long> values) {
			addCriterion("ORDER_ID not in", values, "orderId");
			return this;
		}

		public Criteria andOrderIdBetween(String value1, String value2) {
			addCriterion("ORDER_ID between", value1, value2, "orderId");
			return this;
		}

		public Criteria andOrderIdNotBetween(String value1, String value2) {
			addCriterion("ORDER_ID not between", value1, value2, "orderId");
			return this;
		}

		public Criteria andPackageBarcodeIsNull() {
			addCriterion("PACKAGE_BARCODE is null");
			return this;
		}

		public Criteria andPackageBarcodeIsNotNull() {
			addCriterion("PACKAGE_BARCODE is not null");
			return this;
		}

		public Criteria andPackageBarcodeEqualTo(String value) {
			addCriterion("PACKAGE_BARCODE =", value, "packageBarcode");
			return this;
		}

		public Criteria andPackageBarcodeNotEqualTo(String value) {
			addCriterion("PACKAGE_BARCODE <>", value, "packageBarcode");
			return this;
		}

		public Criteria andPackageBarcodeGreaterThan(String value) {
			addCriterion("PACKAGE_BARCODE >", value, "packageBarcode");
			return this;
		}

		public Criteria andPackageBarcodeGreaterThanOrEqualTo(String value) {
			addCriterion("PACKAGE_BARCODE >=", value, "packageBarcode");
			return this;
		}

		public Criteria andPackageBarcodeLessThan(String value) {
			addCriterion("PACKAGE_BARCODE <", value, "packageBarcode");
			return this;
		}

		public Criteria andPackageBarcodeLessThanOrEqualTo(String value) {
			addCriterion("PACKAGE_BARCODE <=", value, "packageBarcode");
			return this;
		}

		public Criteria andPackageBarcodeLike(String value) {
			addCriterion("PACKAGE_BARCODE like", value, "packageBarcode");
			return this;
		}

		public Criteria andPackageBarcodeNotLike(String value) {
			addCriterion("PACKAGE_BARCODE not like", value, "packageBarcode");
			return this;
		}

		public Criteria andPackageBarcodeIn(List<String> values) {
			addCriterion("PACKAGE_BARCODE in", values, "packageBarcode");
			return this;
		}

		public Criteria andPackageBarcodeNotIn(List<String> values) {
			addCriterion("PACKAGE_BARCODE not in", values, "packageBarcode");
			return this;
		}

		public Criteria andPackageBarcodeBetween(String value1, String value2) {
			addCriterion("PACKAGE_BARCODE between", value1, value2,
					"packageBarcode");
			return this;
		}

		public Criteria andPackageBarcodeNotBetween(String value1, String value2) {
			addCriterion("PACKAGE_BARCODE not between", value1, value2,
					"packageBarcode");
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

		public Criteria andCreateTimeIn(List<String> values) {
		    addCriterion("CREATE_TIME in", values, "createTime");
			return this;
		}

		public Criteria andCreateTimeNotIn(List<String> values) {
		    addCriterion("CREATE_TIME not in", values, "createTime");
			return this;
		}

		public Criteria andCreateTimeBetween(String value1, String value2) {
		    addCriterion("CREATE_TIME between", value1, value2,
					"createTime");
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

		public Criteria andModifyTimeIn(List<String> values) {
		    addCriterion("MODIFY_TIME in", values, "modifyTime");
			return this;
		}

		public Criteria andModifyTimeNotIn(List<String> values) {
		    addCriterion("MODIFY_TIME not in", values, "modifyTime");
			return this;
		}

		public Criteria andModifyTimeBetween(String value1, String value2) {
		    addCriterion("MODIFY_TIME between", value1, value2,
					"modifyTime");
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

		public Criteria andDataStateNotBetween(Short value1, Short value2) {
			addCriterion("DATA_STATE not between", value1, value2, "dataState");
			return this;
		}
	}
}