package com.huateng.framework.db.ibatis.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProdLayoutExample {
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to
	 * the database table TB_REL_PROD_LAYOUT
	 * 
	 * @abatorgenerated Thu Nov 11 17:44:36 CST 2010
	 */
	protected String orderByClause;

	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to
	 * the database table TB_REL_PROD_LAYOUT
	 * 
	 * @abatorgenerated Thu Nov 11 17:44:36 CST 2010
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_PROD_LAYOUT
	 * 
	 * @abatorgenerated Thu Nov 11 17:44:36 CST 2010
	 */
	public ProdLayoutExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_PROD_LAYOUT
	 * 
	 * @abatorgenerated Thu Nov 11 17:44:36 CST 2010
	 */
	protected ProdLayoutExample(ProdLayoutExample example) {
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_PROD_LAYOUT
	 * 
	 * @abatorgenerated Thu Nov 11 17:44:36 CST 2010
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_PROD_LAYOUT
	 * 
	 * @abatorgenerated Thu Nov 11 17:44:36 CST 2010
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_PROD_LAYOUT
	 * 
	 * @abatorgenerated Thu Nov 11 17:44:36 CST 2010
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_PROD_LAYOUT
	 * 
	 * @abatorgenerated Thu Nov 11 17:44:36 CST 2010
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_PROD_LAYOUT
	 * 
	 * @abatorgenerated Thu Nov 11 17:44:36 CST 2010
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
	 * to the database table TB_REL_PROD_LAYOUT
	 * 
	 * @abatorgenerated Thu Nov 11 17:44:36 CST 2010
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_PROD_LAYOUT
	 * 
	 * @abatorgenerated Thu Nov 11 17:44:36 CST 2010
	 */
	public void clear() {
		oredCriteria.clear();
	}

	/**
	 * This class was generated by Abator for iBATIS. This class corresponds to
	 * the database table TB_REL_PROD_LAYOUT
	 * 
	 * @abatorgenerated Thu Nov 11 17:44:36 CST 2010
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

		public Criteria andRelIdIsNull() {
			addCriterion("REL_ID is null");
			return this;
		}

		public Criteria andRelIdIsNotNull() {
			addCriterion("REL_ID is not null");
			return this;
		}

		public Criteria andRelIdEqualTo(String value) {
			addCriterion("REL_ID =", value, "relId");
			return this;
		}

		public Criteria andRelIdNotEqualTo(String value) {
			addCriterion("REL_ID <>", value, "relId");
			return this;
		}

		public Criteria andRelIdGreaterThan(String value) {
			addCriterion("REL_ID >", value, "relId");
			return this;
		}

		public Criteria andRelIdGreaterThanOrEqualTo(String value) {
			addCriterion("REL_ID >=", value, "relId");
			return this;
		}

		public Criteria andRelIdLessThan(String value) {
			addCriterion("REL_ID <", value, "relId");
			return this;
		}

		public Criteria andRelIdLessThanOrEqualTo(String value) {
			addCriterion("REL_ID <=", value, "relId");
			return this;
		}

		public Criteria andRelIdLike(String value) {
			addCriterion("REL_ID like", value, "relId");
			return this;
		}

		public Criteria andRelIdNotLike(String value) {
			addCriterion("REL_ID not like", value, "relId");
			return this;
		}

		public Criteria andRelIdIn(List<String> values) {
			addCriterion("REL_ID in", values, "relId");
			return this;
		}

		public Criteria andRelIdNotIn(List<String> values) {
			addCriterion("REL_ID not in", values, "relId");
			return this;
		}

		public Criteria andRelIdBetween(String value1, String value2) {
			addCriterion("REL_ID between", value1, value2, "relId");
			return this;
		}

		public Criteria andRelIdNotBetween(String value1, String value2) {
			addCriterion("REL_ID not between", value1, value2, "relId");
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
	}
}