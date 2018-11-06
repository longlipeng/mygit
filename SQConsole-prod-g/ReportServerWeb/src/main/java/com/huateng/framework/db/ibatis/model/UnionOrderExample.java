package com.huateng.framework.db.ibatis.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnionOrderExample {

	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to
	 * the database table TB_UNION_ORDER
	 * 
	 * @abatorgenerated Mon Dec 13 17:12:34 CST 2010
	 */
	protected String orderByClause;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to
	 * the database table TB_UNION_ORDER
	 * 
	 * @abatorgenerated Mon Dec 13 17:12:34 CST 2010
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_UNION_ORDER
	 * 
	 * @abatorgenerated Mon Dec 13 17:12:34 CST 2010
	 */
	public UnionOrderExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_UNION_ORDER
	 * 
	 * @abatorgenerated Mon Dec 13 17:12:34 CST 2010
	 */
	protected UnionOrderExample(UnionOrderExample example) {
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_UNION_ORDER
	 * 
	 * @abatorgenerated Mon Dec 13 17:12:34 CST 2010
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_UNION_ORDER
	 * 
	 * @abatorgenerated Mon Dec 13 17:12:34 CST 2010
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_UNION_ORDER
	 * 
	 * @abatorgenerated Mon Dec 13 17:12:34 CST 2010
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_UNION_ORDER
	 * 
	 * @abatorgenerated Mon Dec 13 17:12:34 CST 2010
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_UNION_ORDER
	 * 
	 * @abatorgenerated Mon Dec 13 17:12:34 CST 2010
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
	 * to the database table TB_UNION_ORDER
	 * 
	 * @abatorgenerated Mon Dec 13 17:12:34 CST 2010
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_UNION_ORDER
	 * 
	 * @abatorgenerated Mon Dec 13 17:12:34 CST 2010
	 */
	public void clear() {
		oredCriteria.clear();
	}

	/**
	 * This class was generated by Abator for iBATIS. This class corresponds to
	 * the database table TB_UNION_ORDER
	 * 
	 * @abatorgenerated Mon Dec 13 17:12:34 CST 2010
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

		public Criteria andNewOrderIsNull() {
			addCriterion("NEW_ORDER is null");
			return this;
		}

		public Criteria andNewOrderIsNotNull() {
			addCriterion("NEW_ORDER is not null");
			return this;
		}

		public Criteria andNewOrderEqualTo(String value) {
			addCriterion("NEW_ORDER =", value, "newOrder");
			return this;
		}

		public Criteria andNewOrderNotEqualTo(String value) {
			addCriterion("NEW_ORDER <>", value, "newOrder");
			return this;
		}

		public Criteria andNewOrderGreaterThan(String value) {
			addCriterion("NEW_ORDER >", value, "newOrder");
			return this;
		}

		public Criteria andNewOrderGreaterThanOrEqualTo(String value) {
			addCriterion("NEW_ORDER >=", value, "newOrder");
			return this;
		}

		public Criteria andNewOrderLessThan(String value) {
			addCriterion("NEW_ORDER <", value, "newOrder");
			return this;
		}

		public Criteria andNewOrderLessThanOrEqualTo(String value) {
			addCriterion("NEW_ORDER <=", value, "newOrder");
			return this;
		}

		public Criteria andNewOrderLike(String value) {
			addCriterion("NEW_ORDER like", value, "newOrder");
			return this;
		}

		public Criteria andNewOrderNotLike(String value) {
			addCriterion("NEW_ORDER not like", value, "newOrder");
			return this;
		}

		public Criteria andNewOrderIn(List<String> values) {
			addCriterion("NEW_ORDER in", values, "newOrder");
			return this;
		}

		public Criteria andNewOrderNotIn(List<String> values) {
			addCriterion("NEW_ORDER not in", values, "newOrder");
			return this;
		}

		public Criteria andNewOrderBetween(String value1, String value2) {
			addCriterion("NEW_ORDER between", value1, value2, "newOrder");
			return this;
		}

		public Criteria andNewOrderNotBetween(String value1, String value2) {
			addCriterion("NEW_ORDER not between", value1, value2, "newOrder");
			return this;
		}

		public Criteria andOldOrderIsNull() {
			addCriterion("OLD_ORDER is null");
			return this;
		}

		public Criteria andOldOrderIsNotNull() {
			addCriterion("OLD_ORDER is not null");
			return this;
		}

		public Criteria andOldOrderEqualTo(String value) {
			addCriterion("OLD_ORDER =", value, "oldOrder");
			return this;
		}

		public Criteria andOldOrderNotEqualTo(String value) {
			addCriterion("OLD_ORDER <>", value, "oldOrder");
			return this;
		}

		public Criteria andOldOrderGreaterThan(String value) {
			addCriterion("OLD_ORDER >", value, "oldOrder");
			return this;
		}

		public Criteria andOldOrderGreaterThanOrEqualTo(String value) {
			addCriterion("OLD_ORDER >=", value, "oldOrder");
			return this;
		}

		public Criteria andOldOrderLessThan(String value) {
			addCriterion("OLD_ORDER <", value, "oldOrder");
			return this;
		}

		public Criteria andOldOrderLessThanOrEqualTo(String value) {
			addCriterion("OLD_ORDER <=", value, "oldOrder");
			return this;
		}

		public Criteria andOldOrderLike(String value) {
			addCriterion("OLD_ORDER like", value, "oldOrder");
			return this;
		}

		public Criteria andOldOrderNotLike(String value) {
			addCriterion("OLD_ORDER not like", value, "oldOrder");
			return this;
		}

		public Criteria andOldOrderIn(List<String> values) {
			addCriterion("OLD_ORDER in", values, "oldOrder");
			return this;
		}

		public Criteria andOldOrderNotIn(List<String> values) {
			addCriterion("OLD_ORDER not in", values, "oldOrder");
			return this;
		}

		public Criteria andOldOrderBetween(String value1, String value2) {
			addCriterion("OLD_ORDER between", value1, value2, "oldOrder");
			return this;
		}

		public Criteria andOldOrderNotBetween(String value1, String value2) {
			addCriterion("OLD_ORDER not between", value1, value2, "oldOrder");
			return this;
		}

		public Criteria andLeafNodeIsNull() {
			addCriterion("LEAF_NODE is null");
			return this;
		}

		public Criteria andLeafNodeIsNotNull() {
			addCriterion("LEAF_NODE is not null");
			return this;
		}

		public Criteria andLeafNodeEqualTo(String value) {
			addCriterion("LEAF_NODE =", value, "leafNode");
			return this;
		}

		public Criteria andLeafNodeNotEqualTo(String value) {
			addCriterion("LEAF_NODE <>", value, "leafNode");
			return this;
		}

		public Criteria andLeafNodeGreaterThan(String value) {
			addCriterion("LEAF_NODE >", value, "leafNode");
			return this;
		}

		public Criteria andLeafNodeGreaterThanOrEqualTo(String value) {
			addCriterion("LEAF_NODE >=", value, "leafNode");
			return this;
		}

		public Criteria andLeafNodeLessThan(String value) {
			addCriterion("LEAF_NODE <", value, "leafNode");
			return this;
		}

		public Criteria andLeafNodeLessThanOrEqualTo(String value) {
			addCriterion("LEAF_NODE <=", value, "leafNode");
			return this;
		}

		public Criteria andLeafNodeLike(String value) {
			addCriterion("LEAF_NODE like", value, "leafNode");
			return this;
		}

		public Criteria andLeafNodeNotLike(String value) {
			addCriterion("LEAF_NODE not like", value, "leafNode");
			return this;
		}

		public Criteria andLeafNodeIn(List<String> values) {
			addCriterion("LEAF_NODE in", values, "leafNode");
			return this;
		}

		public Criteria andLeafNodeNotIn(List<String> values) {
			addCriterion("LEAF_NODE not in", values, "leafNode");
			return this;
		}

		public Criteria andLeafNodeBetween(String value1, String value2) {
			addCriterion("LEAF_NODE between", value1, value2, "leafNode");
			return this;
		}

		public Criteria andLeafNodeNotBetween(String value1, String value2) {
			addCriterion("LEAF_NODE not between", value1, value2, "leafNode");
			return this;
		}
	}
}