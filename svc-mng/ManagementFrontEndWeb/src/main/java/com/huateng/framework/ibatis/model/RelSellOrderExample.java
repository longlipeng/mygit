package com.huateng.framework.ibatis.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RelSellOrderExample {
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to
	 * the database table TB_REL_SELL_ORDER
	 * 
	 * @abatorgenerated Sun Sep 26 16:12:41 CST 2010
	 */
	protected String orderByClause;

	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to
	 * the database table TB_REL_SELL_ORDER
	 * 
	 * @abatorgenerated Sun Sep 26 16:12:41 CST 2010
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_SELL_ORDER
	 * 
	 * @abatorgenerated Sun Sep 26 16:12:41 CST 2010
	 */
	public RelSellOrderExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_SELL_ORDER
	 * 
	 * @abatorgenerated Sun Sep 26 16:12:41 CST 2010
	 */
	protected RelSellOrderExample(RelSellOrderExample example) {
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_SELL_ORDER
	 * 
	 * @abatorgenerated Sun Sep 26 16:12:41 CST 2010
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_SELL_ORDER
	 * 
	 * @abatorgenerated Sun Sep 26 16:12:41 CST 2010
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_SELL_ORDER
	 * 
	 * @abatorgenerated Sun Sep 26 16:12:41 CST 2010
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_SELL_ORDER
	 * 
	 * @abatorgenerated Sun Sep 26 16:12:41 CST 2010
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_SELL_ORDER
	 * 
	 * @abatorgenerated Sun Sep 26 16:12:41 CST 2010
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
	 * to the database table TB_REL_SELL_ORDER
	 * 
	 * @abatorgenerated Sun Sep 26 16:12:41 CST 2010
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_SELL_ORDER
	 * 
	 * @abatorgenerated Sun Sep 26 16:12:41 CST 2010
	 */
	public void clear() {
		oredCriteria.clear();
	}

	/**
	 * This class was generated by Abator for iBATIS. This class corresponds to
	 * the database table TB_REL_SELL_ORDER
	 * 
	 * @abatorgenerated Sun Sep 26 16:12:41 CST 2010
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

		public Criteria andOrderIdLessThanOrEqualTo(String value) {
			addCriterion("ORDER_ID <=", value, "orderId");
			return this;
		}

		public Criteria andOrderIdLike(String value) {
			addCriterion("ORDER_ID like", value, "orderId");
			return this;
		}

		public Criteria andOrderIdNotLike(String value) {
			addCriterion("ORDER_ID not like", value, "orderId");
			return this;
		}

		public Criteria andOrderIdIn(List<String> values) {
			addCriterion("ORDER_ID in", values, "orderId");
			return this;
		}

		public Criteria andOrderIdNotIn(List<String> values) {
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

		public Criteria andRelOrderIdIsNull() {
			addCriterion("REL_ORDER_ID is null");
			return this;
		}

		public Criteria andRelOrderIdIsNotNull() {
			addCriterion("REL_ORDER_ID is not null");
			return this;
		}

		public Criteria andRelOrderIdEqualTo(String value) {
			addCriterion("REL_ORDER_ID =", value, "relOrderId");
			return this;
		}

		public Criteria andRelOrderIdNotEqualTo(String value) {
			addCriterion("REL_ORDER_ID <>", value, "relOrderId");
			return this;
		}

		public Criteria andRelOrderIdGreaterThan(String value) {
			addCriterion("REL_ORDER_ID >", value, "relOrderId");
			return this;
		}

		public Criteria andRelOrderIdGreaterThanOrEqualTo(String value) {
			addCriterion("REL_ORDER_ID >=", value, "relOrderId");
			return this;
		}

		public Criteria andRelOrderIdLessThan(String value) {
			addCriterion("REL_ORDER_ID <", value, "relOrderId");
			return this;
		}

		public Criteria andRelOrderIdLessThanOrEqualTo(String value) {
			addCriterion("REL_ORDER_ID <=", value, "relOrderId");
			return this;
		}

		public Criteria andRelOrderIdLike(String value) {
			addCriterion("REL_ORDER_ID like", value, "relOrderId");
			return this;
		}

		public Criteria andRelOrderIdNotLike(String value) {
			addCriterion("REL_ORDER_ID not like", value, "relOrderId");
			return this;
		}

		public Criteria andRelOrderIdIn(List<String> values) {
			addCriterion("REL_ORDER_ID in", values, "relOrderId");
			return this;
		}

		public Criteria andRelOrderIdNotIn(List<String> values) {
			addCriterion("REL_ORDER_ID not in", values, "relOrderId");
			return this;
		}

		public Criteria andRelOrderIdBetween(String value1, String value2) {
			addCriterion("REL_ORDER_ID between", value1, value2, "relOrderId");
			return this;
		}

		public Criteria andRelOrderIdNotBetween(String value1, String value2) {
			addCriterion("REL_ORDER_ID not between", value1, value2,
					"relOrderId");
			return this;
		}

		public Criteria andIsLeafPointIsNull() {
			addCriterion("IS_LEAF_POINT is null");
			return this;
		}

		public Criteria andIsLeafPointIsNotNull() {
			addCriterion("IS_LEAF_POINT is not null");
			return this;
		}

		public Criteria andIsLeafPointEqualTo(String value) {
			addCriterion("IS_LEAF_POINT =", value, "isLeafPoint");
			return this;
		}

		public Criteria andIsLeafPointNotEqualTo(String value) {
			addCriterion("IS_LEAF_POINT <>", value, "isLeafPoint");
			return this;
		}

		public Criteria andIsLeafPointGreaterThan(String value) {
			addCriterion("IS_LEAF_POINT >", value, "isLeafPoint");
			return this;
		}

		public Criteria andIsLeafPointGreaterThanOrEqualTo(String value) {
			addCriterion("IS_LEAF_POINT >=", value, "isLeafPoint");
			return this;
		}

		public Criteria andIsLeafPointLessThan(String value) {
			addCriterion("IS_LEAF_POINT <", value, "isLeafPoint");
			return this;
		}

		public Criteria andIsLeafPointLessThanOrEqualTo(String value) {
			addCriterion("IS_LEAF_POINT <=", value, "isLeafPoint");
			return this;
		}

		public Criteria andIsLeafPointLike(String value) {
			addCriterion("IS_LEAF_POINT like", value, "isLeafPoint");
			return this;
		}

		public Criteria andIsLeafPointNotLike(String value) {
			addCriterion("IS_LEAF_POINT not like", value, "isLeafPoint");
			return this;
		}

		public Criteria andIsLeafPointIn(List<String> values) {
			addCriterion("IS_LEAF_POINT in", values, "isLeafPoint");
			return this;
		}

		public Criteria andIsLeafPointNotIn(List<String> values) {
			addCriterion("IS_LEAF_POINT not in", values, "isLeafPoint");
			return this;
		}

		public Criteria andIsLeafPointBetween(String value1, String value2) {
			addCriterion("IS_LEAF_POINT between", value1, value2, "isLeafPoint");
			return this;
		}

		public Criteria andIsLeafPointNotBetween(String value1, String value2) {
			addCriterion("IS_LEAF_POINT not between", value1, value2,
					"isLeafPoint");
			return this;
		}
	}
}