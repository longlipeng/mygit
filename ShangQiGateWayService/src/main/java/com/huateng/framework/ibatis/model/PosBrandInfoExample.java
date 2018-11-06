package com.huateng.framework.ibatis.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PosBrandInfoExample {
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to
	 * the database table TB_POS_BRAND_INFO
	 * 
	 * @abatorgenerated Mon Nov 08 16:52:40 CST 2010
	 */
	protected String orderByClause;

	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to
	 * the database table TB_POS_BRAND_INFO
	 * 
	 * @abatorgenerated Mon Nov 08 16:52:40 CST 2010
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_POS_BRAND_INFO
	 * 
	 * @abatorgenerated Mon Nov 08 16:52:40 CST 2010
	 */
	public PosBrandInfoExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_POS_BRAND_INFO
	 * 
	 * @abatorgenerated Mon Nov 08 16:52:40 CST 2010
	 */
	protected PosBrandInfoExample(PosBrandInfoExample example) {
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_POS_BRAND_INFO
	 * 
	 * @abatorgenerated Mon Nov 08 16:52:40 CST 2010
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_POS_BRAND_INFO
	 * 
	 * @abatorgenerated Mon Nov 08 16:52:40 CST 2010
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_POS_BRAND_INFO
	 * 
	 * @abatorgenerated Mon Nov 08 16:52:40 CST 2010
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_POS_BRAND_INFO
	 * 
	 * @abatorgenerated Mon Nov 08 16:52:40 CST 2010
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_POS_BRAND_INFO
	 * 
	 * @abatorgenerated Mon Nov 08 16:52:40 CST 2010
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
	 * to the database table TB_POS_BRAND_INFO
	 * 
	 * @abatorgenerated Mon Nov 08 16:52:40 CST 2010
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_POS_BRAND_INFO
	 * 
	 * @abatorgenerated Mon Nov 08 16:52:40 CST 2010
	 */
	public void clear() {
		oredCriteria.clear();
	}

	/**
	 * This class was generated by Abator for iBATIS. This class corresponds to
	 * the database table TB_POS_BRAND_INFO
	 * 
	 * @abatorgenerated Mon Nov 08 16:52:40 CST 2010
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

		public Criteria andTermBrandIdIsNull() {
			addCriterion("TERM_BRAND_ID is null");
			return this;
		}

		public Criteria andTermBrandIdIsNotNull() {
			addCriterion("TERM_BRAND_ID is not null");
			return this;
		}

		public Criteria andTermBrandIdEqualTo(Integer value) {
			addCriterion("TERM_BRAND_ID =", value, "termBrandId");
			return this;
		}

		public Criteria andTermBrandIdNotEqualTo(Integer value) {
			addCriterion("TERM_BRAND_ID <>", value, "termBrandId");
			return this;
		}

		public Criteria andTermBrandIdGreaterThan(Integer value) {
			addCriterion("TERM_BRAND_ID >", value, "termBrandId");
			return this;
		}

		public Criteria andTermBrandIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("TERM_BRAND_ID >=", value, "termBrandId");
			return this;
		}

		public Criteria andTermBrandIdLessThan(Integer value) {
			addCriterion("TERM_BRAND_ID <", value, "termBrandId");
			return this;
		}

		public Criteria andTermBrandIdLessThanOrEqualTo(Integer value) {
			addCriterion("TERM_BRAND_ID <=", value, "termBrandId");
			return this;
		}

		public Criteria andTermBrandIdLike(Integer value) {
			addCriterion("TERM_BRAND_ID like", value, "termBrandId");
			return this;
		}

		public Criteria andTermBrandIdNotLike(Integer value) {
			addCriterion("TERM_BRAND_ID not like", value, "termBrandId");
			return this;
		}

		public Criteria andTermBrandIdIn(List<Integer> values) {
			addCriterion("TERM_BRAND_ID in", values, "termBrandId");
			return this;
		}

		public Criteria andTermBrandIdNotIn(List<Integer> values) {
			addCriterion("TERM_BRAND_ID not in", values, "termBrandId");
			return this;
		}

		public Criteria andTermBrandIdBetween(Integer value1, Integer value2) {
			addCriterion("TERM_BRAND_ID between", value1, value2, "termBrandId");
			return this;
		}

		public Criteria andTermBrandIdNotBetween(Integer value1, Integer value2) {
			addCriterion("TERM_BRAND_ID not between", value1, value2,
					"termBrandId");
			return this;
		}

		public Criteria andBrandNameIsNull() {
			addCriterion("BRAND_NAME is null");
			return this;
		}

		public Criteria andBrandNameIsNotNull() {
			addCriterion("BRAND_NAME is not null");
			return this;
		}

		public Criteria andBrandNameEqualTo(String value) {
			addCriterion("BRAND_NAME =", value, "brandName");
			return this;
		}

		public Criteria andBrandNameNotEqualTo(String value) {
			addCriterion("BRAND_NAME <>", value, "brandName");
			return this;
		}

		public Criteria andBrandNameGreaterThan(String value) {
			addCriterion("BRAND_NAME >", value, "brandName");
			return this;
		}

		public Criteria andBrandNameGreaterThanOrEqualTo(String value) {
			addCriterion("BRAND_NAME >=", value, "brandName");
			return this;
		}

		public Criteria andBrandNameLessThan(String value) {
			addCriterion("BRAND_NAME <", value, "brandName");
			return this;
		}

		public Criteria andBrandNameLessThanOrEqualTo(String value) {
			addCriterion("BRAND_NAME <=", value, "brandName");
			return this;
		}

		public Criteria andBrandNameLike(String value) {
			addCriterion("BRAND_NAME like", value, "brandName");
			return this;
		}

		public Criteria andBrandNameNotLike(String value) {
			addCriterion("BRAND_NAME not like", value, "brandName");
			return this;
		}

		public Criteria andBrandNameIn(List<String> values) {
			addCriterion("BRAND_NAME in", values, "brandName");
			return this;
		}

		public Criteria andBrandNameNotIn(List<String> values) {
			addCriterion("BRAND_NAME not in", values, "brandName");
			return this;
		}

		public Criteria andBrandNameBetween(String value1, String value2) {
			addCriterion("BRAND_NAME between", value1, value2, "brandName");
			return this;
		}

		public Criteria andBrandNameNotBetween(String value1, String value2) {
			addCriterion("BRAND_NAME not between", value1, value2, "brandName");
			return this;
		}

		public Criteria andTmkIndexIsNull() {
			addCriterion("TMK_INDEX is null");
			return this;
		}

		public Criteria andTmkIndexIsNotNull() {
			addCriterion("TMK_INDEX is not null");
			return this;
		}

		public Criteria andTmkIndexEqualTo(String value) {
			addCriterion("TMK_INDEX =", value, "tmkIndex");
			return this;
		}

		public Criteria andTmkIndexNotEqualTo(String value) {
			addCriterion("TMK_INDEX <>", value, "tmkIndex");
			return this;
		}

		public Criteria andTmkIndexGreaterThan(String value) {
			addCriterion("TMK_INDEX >", value, "tmkIndex");
			return this;
		}

		public Criteria andTmkIndexGreaterThanOrEqualTo(String value) {
			addCriterion("TMK_INDEX >=", value, "tmkIndex");
			return this;
		}

		public Criteria andTmkIndexLessThan(String value) {
			addCriterion("TMK_INDEX <", value, "tmkIndex");
			return this;
		}

		public Criteria andTmkIndexLessThanOrEqualTo(String value) {
			addCriterion("TMK_INDEX <=", value, "tmkIndex");
			return this;
		}

		public Criteria andTmkIndexLike(String value) {
			addCriterion("TMK_INDEX like", value, "tmkIndex");
			return this;
		}

		public Criteria andTmkIndexNotLike(String value) {
			addCriterion("TMK_INDEX not like", value, "tmkIndex");
			return this;
		}

		public Criteria andTmkIndexIn(List<String> values) {
			addCriterion("TMK_INDEX in", values, "tmkIndex");
			return this;
		}

		public Criteria andTmkIndexNotIn(List<String> values) {
			addCriterion("TMK_INDEX not in", values, "tmkIndex");
			return this;
		}

		public Criteria andTmkIndexBetween(String value1, String value2) {
			addCriterion("TMK_INDEX between", value1, value2, "tmkIndex");
			return this;
		}

		public Criteria andTmkIndexNotBetween(String value1, String value2) {
			addCriterion("TMK_INDEX not between", value1, value2, "tmkIndex");
			return this;
		}

		public Criteria andTmkKeyIsNull() {
			addCriterion("TMK_KEY is null");
			return this;
		}

		public Criteria andTmkKeyIsNotNull() {
			addCriterion("TMK_KEY is not null");
			return this;
		}

		public Criteria andTmkKeyEqualTo(String value) {
			addCriterion("TMK_KEY =", value, "tmkKey");
			return this;
		}

		public Criteria andTmkKeyNotEqualTo(String value) {
			addCriterion("TMK_KEY <>", value, "tmkKey");
			return this;
		}

		public Criteria andTmkKeyGreaterThan(String value) {
			addCriterion("TMK_KEY >", value, "tmkKey");
			return this;
		}

		public Criteria andTmkKeyGreaterThanOrEqualTo(String value) {
			addCriterion("TMK_KEY >=", value, "tmkKey");
			return this;
		}

		public Criteria andTmkKeyLessThan(String value) {
			addCriterion("TMK_KEY <", value, "tmkKey");
			return this;
		}

		public Criteria andTmkKeyLessThanOrEqualTo(String value) {
			addCriterion("TMK_KEY <=", value, "tmkKey");
			return this;
		}

		public Criteria andTmkKeyLike(String value) {
			addCriterion("TMK_KEY like", value, "tmkKey");
			return this;
		}

		public Criteria andTmkKeyNotLike(String value) {
			addCriterion("TMK_KEY not like", value, "tmkKey");
			return this;
		}

		public Criteria andTmkKeyIn(List<String> values) {
			addCriterion("TMK_KEY in", values, "tmkKey");
			return this;
		}

		public Criteria andTmkKeyNotIn(List<String> values) {
			addCriterion("TMK_KEY not in", values, "tmkKey");
			return this;
		}

		public Criteria andTmkKeyBetween(String value1, String value2) {
			addCriterion("TMK_KEY between", value1, value2, "tmkKey");
			return this;
		}

		public Criteria andTmkKeyNotBetween(String value1, String value2) {
			addCriterion("TMK_KEY not between", value1, value2, "tmkKey");
			return this;
		}
	}
}