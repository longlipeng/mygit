package com.huateng.framework.db.ibatis.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceExample {
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to
	 * the database table TB_SERVICE
	 * 
	 * @abatorgenerated Sun Sep 26 15:57:00 CST 2010
	 */
	protected String orderByClause;

	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to
	 * the database table TB_SERVICE
	 * 
	 * @abatorgenerated Sun Sep 26 15:57:00 CST 2010
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SERVICE
	 * 
	 * @abatorgenerated Sun Sep 26 15:57:00 CST 2010
	 */
	public ServiceExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SERVICE
	 * 
	 * @abatorgenerated Sun Sep 26 15:57:00 CST 2010
	 */
	protected ServiceExample(ServiceExample example) {
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SERVICE
	 * 
	 * @abatorgenerated Sun Sep 26 15:57:00 CST 2010
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SERVICE
	 * 
	 * @abatorgenerated Sun Sep 26 15:57:00 CST 2010
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SERVICE
	 * 
	 * @abatorgenerated Sun Sep 26 15:57:00 CST 2010
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SERVICE
	 * 
	 * @abatorgenerated Sun Sep 26 15:57:00 CST 2010
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SERVICE
	 * 
	 * @abatorgenerated Sun Sep 26 15:57:00 CST 2010
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
	 * to the database table TB_SERVICE
	 * 
	 * @abatorgenerated Sun Sep 26 15:57:00 CST 2010
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SERVICE
	 * 
	 * @abatorgenerated Sun Sep 26 15:57:00 CST 2010
	 */
	public void clear() {
		oredCriteria.clear();
	}

	/**
	 * This class was generated by Abator for iBATIS. This class corresponds to
	 * the database table TB_SERVICE
	 * 
	 * @abatorgenerated Sun Sep 26 15:57:00 CST 2010
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

		public Criteria andServiceIdIsNull() {
			addCriterion("SERVICE_ID is null");
			return this;
		}

		public Criteria andServiceIdIsNotNull() {
			addCriterion("SERVICE_ID is not null");
			return this;
		}

		public Criteria andServiceIdEqualTo(String value) {
			addCriterion("SERVICE_ID =", value, "serviceId");
			return this;
		}

		public Criteria andServiceIdNotEqualTo(String value) {
			addCriterion("SERVICE_ID <>", value, "serviceId");
			return this;
		}

		public Criteria andServiceIdGreaterThan(String value) {
			addCriterion("SERVICE_ID >", value, "serviceId");
			return this;
		}

		public Criteria andServiceIdGreaterThanOrEqualTo(String value) {
			addCriterion("SERVICE_ID >=", value, "serviceId");
			return this;
		}

		public Criteria andServiceIdLessThan(String value) {
			addCriterion("SERVICE_ID <", value, "serviceId");
			return this;
		}

		public Criteria andServiceIdLessThanOrEqualTo(String value) {
			addCriterion("SERVICE_ID <=", value, "serviceId");
			return this;
		}

		public Criteria andServiceIdLike(String value) {
			addCriterion("SERVICE_ID like", value, "serviceId");
			return this;
		}

		public Criteria andServiceIdNotLike(String value) {
			addCriterion("SERVICE_ID not like", value, "serviceId");
			return this;
		}

		public Criteria andServiceIdIn(List<String> values) {
			addCriterion("SERVICE_ID in", values, "serviceId");
			return this;
		}

		public Criteria andServiceIdNotIn(List<String> values) {
			addCriterion("SERVICE_ID not in", values, "serviceId");
			return this;
		}

		public Criteria andServiceIdBetween(String value1, String value2) {
			addCriterion("SERVICE_ID between", value1, value2, "serviceId");
			return this;
		}

		public Criteria andServiceIdNotBetween(String value1, String value2) {
			addCriterion("SERVICE_ID not between", value1, value2, "serviceId");
			return this;
		}

		public Criteria andServiceNameIsNull() {
			addCriterion("SERVICE_NAME is null");
			return this;
		}

		public Criteria andServiceNameIsNotNull() {
			addCriterion("SERVICE_NAME is not null");
			return this;
		}

		public Criteria andServiceNameEqualTo(String value) {
			addCriterion("SERVICE_NAME =", value, "serviceName");
			return this;
		}

		public Criteria andServiceNameNotEqualTo(String value) {
			addCriterion("SERVICE_NAME <>", value, "serviceName");
			return this;
		}

		public Criteria andServiceNameGreaterThan(String value) {
			addCriterion("SERVICE_NAME >", value, "serviceName");
			return this;
		}

		public Criteria andServiceNameGreaterThanOrEqualTo(String value) {
			addCriterion("SERVICE_NAME >=", value, "serviceName");
			return this;
		}

		public Criteria andServiceNameLessThan(String value) {
			addCriterion("SERVICE_NAME <", value, "serviceName");
			return this;
		}

		public Criteria andServiceNameLessThanOrEqualTo(String value) {
			addCriterion("SERVICE_NAME <=", value, "serviceName");
			return this;
		}

		public Criteria andServiceNameLike(String value) {
			addCriterion("SERVICE_NAME like", value, "serviceName");
			return this;
		}

		public Criteria andServiceNameNotLike(String value) {
			addCriterion("SERVICE_NAME not like", value, "serviceName");
			return this;
		}

		public Criteria andServiceNameIn(List<String> values) {
			addCriterion("SERVICE_NAME in", values, "serviceName");
			return this;
		}

		public Criteria andServiceNameNotIn(List<String> values) {
			addCriterion("SERVICE_NAME not in", values, "serviceName");
			return this;
		}

		public Criteria andServiceNameBetween(String value1, String value2) {
			addCriterion("SERVICE_NAME between", value1, value2, "serviceName");
			return this;
		}

		public Criteria andServiceNameNotBetween(String value1, String value2) {
			addCriterion("SERVICE_NAME not between", value1, value2,
					"serviceName");
			return this;
		}

		public Criteria andServiceShortNameIsNull() {
			addCriterion("SERVICE_SHORT_NAME is null");
			return this;
		}

		public Criteria andServiceShortNameIsNotNull() {
			addCriterion("SERVICE_SHORT_NAME is not null");
			return this;
		}

		public Criteria andServiceShortNameEqualTo(String value) {
			addCriterion("SERVICE_SHORT_NAME =", value, "serviceShortName");
			return this;
		}

		public Criteria andServiceShortNameNotEqualTo(String value) {
			addCriterion("SERVICE_SHORT_NAME <>", value, "serviceShortName");
			return this;
		}

		public Criteria andServiceShortNameGreaterThan(String value) {
			addCriterion("SERVICE_SHORT_NAME >", value, "serviceShortName");
			return this;
		}

		public Criteria andServiceShortNameGreaterThanOrEqualTo(String value) {
			addCriterion("SERVICE_SHORT_NAME >=", value, "serviceShortName");
			return this;
		}

		public Criteria andServiceShortNameLessThan(String value) {
			addCriterion("SERVICE_SHORT_NAME <", value, "serviceShortName");
			return this;
		}

		public Criteria andServiceShortNameLessThanOrEqualTo(String value) {
			addCriterion("SERVICE_SHORT_NAME <=", value, "serviceShortName");
			return this;
		}

		public Criteria andServiceShortNameLike(String value) {
			addCriterion("SERVICE_SHORT_NAME like", value, "serviceShortName");
			return this;
		}

		public Criteria andServiceShortNameNotLike(String value) {
			addCriterion("SERVICE_SHORT_NAME not like", value,
					"serviceShortName");
			return this;
		}

		public Criteria andServiceShortNameIn(List<String> values) {
			addCriterion("SERVICE_SHORT_NAME in", values, "serviceShortName");
			return this;
		}

		public Criteria andServiceShortNameNotIn(List<String> values) {
			addCriterion("SERVICE_SHORT_NAME not in", values,
					"serviceShortName");
			return this;
		}

		public Criteria andServiceShortNameBetween(String value1, String value2) {
			addCriterion("SERVICE_SHORT_NAME between", value1, value2,
					"serviceShortName");
			return this;
		}

		public Criteria andServiceShortNameNotBetween(String value1,
				String value2) {
			addCriterion("SERVICE_SHORT_NAME not between", value1, value2,
					"serviceShortName");
			return this;
		}

		public Criteria andServiceEnglishNameIsNull() {
			addCriterion("SERVICE_ENGLISH_NAME is null");
			return this;
		}

		public Criteria andServiceEnglishNameIsNotNull() {
			addCriterion("SERVICE_ENGLISH_NAME is not null");
			return this;
		}

		public Criteria andServiceEnglishNameEqualTo(String value) {
			addCriterion("SERVICE_ENGLISH_NAME =", value, "serviceEnglishName");
			return this;
		}

		public Criteria andServiceEnglishNameNotEqualTo(String value) {
			addCriterion("SERVICE_ENGLISH_NAME <>", value, "serviceEnglishName");
			return this;
		}

		public Criteria andServiceEnglishNameGreaterThan(String value) {
			addCriterion("SERVICE_ENGLISH_NAME >", value, "serviceEnglishName");
			return this;
		}

		public Criteria andServiceEnglishNameGreaterThanOrEqualTo(String value) {
			addCriterion("SERVICE_ENGLISH_NAME >=", value, "serviceEnglishName");
			return this;
		}

		public Criteria andServiceEnglishNameLessThan(String value) {
			addCriterion("SERVICE_ENGLISH_NAME <", value, "serviceEnglishName");
			return this;
		}

		public Criteria andServiceEnglishNameLessThanOrEqualTo(String value) {
			addCriterion("SERVICE_ENGLISH_NAME <=", value, "serviceEnglishName");
			return this;
		}

		public Criteria andServiceEnglishNameLike(String value) {
			addCriterion("SERVICE_ENGLISH_NAME like", value,
					"serviceEnglishName");
			return this;
		}

		public Criteria andServiceEnglishNameNotLike(String value) {
			addCriterion("SERVICE_ENGLISH_NAME not like", value,
					"serviceEnglishName");
			return this;
		}

		public Criteria andServiceEnglishNameIn(List<String> values) {
			addCriterion("SERVICE_ENGLISH_NAME in", values,
					"serviceEnglishName");
			return this;
		}

		public Criteria andServiceEnglishNameNotIn(List<String> values) {
			addCriterion("SERVICE_ENGLISH_NAME not in", values,
					"serviceEnglishName");
			return this;
		}

		public Criteria andServiceEnglishNameBetween(String value1,
				String value2) {
			addCriterion("SERVICE_ENGLISH_NAME between", value1, value2,
					"serviceEnglishName");
			return this;
		}

		public Criteria andServiceEnglishNameNotBetween(String value1,
				String value2) {
			addCriterion("SERVICE_ENGLISH_NAME not between", value1, value2,
					"serviceEnglishName");
			return this;
		}

		public Criteria andExpiryDateIsNull() {
			addCriterion("EXPIRY_DATE is null");
			return this;
		}

		public Criteria andExpiryDateIsNotNull() {
			addCriterion("EXPIRY_DATE is not null");
			return this;
		}

		public Criteria andExpiryDateEqualTo(String value) {
			addCriterion("EXPIRY_DATE =", value, "expiryDate");
			return this;
		}

		public Criteria andExpiryDateNotEqualTo(String value) {
			addCriterion("EXPIRY_DATE <>", value, "expiryDate");
			return this;
		}

		public Criteria andExpiryDateGreaterThan(String value) {
			addCriterion("EXPIRY_DATE >", value, "expiryDate");
			return this;
		}

		public Criteria andExpiryDateGreaterThanOrEqualTo(String value) {
			addCriterion("EXPIRY_DATE >=", value, "expiryDate");
			return this;
		}

		public Criteria andExpiryDateLessThan(String value) {
			addCriterion("EXPIRY_DATE <", value, "expiryDate");
			return this;
		}

		public Criteria andExpiryDateLessThanOrEqualTo(String value) {
			addCriterion("EXPIRY_DATE <=", value, "expiryDate");
			return this;
		}

		public Criteria andExpiryDateLike(String value) {
			addCriterion("EXPIRY_DATE like", value, "expiryDate");
			return this;
		}

		public Criteria andExpiryDateNotLike(String value) {
			addCriterion("EXPIRY_DATE not like", value, "expiryDate");
			return this;
		}

		public Criteria andExpiryDateIn(List<String> values) {
			addCriterion("EXPIRY_DATE in", values, "expiryDate");
			return this;
		}

		public Criteria andExpiryDateNotIn(List<String> values) {
			addCriterion("EXPIRY_DATE not in", values, "expiryDate");
			return this;
		}

		public Criteria andExpiryDateBetween(String value1, String value2) {
			addCriterion("EXPIRY_DATE between", value1, value2, "expiryDate");
			return this;
		}

		public Criteria andExpiryDateNotBetween(String value1, String value2) {
			addCriterion("EXPIRY_DATE not between", value1, value2,
					"expiryDate");
			return this;
		}

		public Criteria andDefaultRateIsNull() {
			addCriterion("DEFAULT_RATE is null");
			return this;
		}

		public Criteria andDefaultRateIsNotNull() {
			addCriterion("DEFAULT_RATE is not null");
			return this;
		}

		public Criteria andDefaultRateEqualTo(String value) {
			addCriterion("DEFAULT_RATE =", value, "defaultRate");
			return this;
		}

		public Criteria andDefaultRateNotEqualTo(String value) {
			addCriterion("DEFAULT_RATE <>", value, "defaultRate");
			return this;
		}

		public Criteria andDefaultRateGreaterThan(String value) {
			addCriterion("DEFAULT_RATE >", value, "defaultRate");
			return this;
		}

		public Criteria andDefaultRateGreaterThanOrEqualTo(String value) {
			addCriterion("DEFAULT_RATE >=", value, "defaultRate");
			return this;
		}

		public Criteria andDefaultRateLessThan(String value) {
			addCriterion("DEFAULT_RATE <", value, "defaultRate");
			return this;
		}

		public Criteria andDefaultRateLessThanOrEqualTo(String value) {
			addCriterion("DEFAULT_RATE <=", value, "defaultRate");
			return this;
		}

		public Criteria andDefaultRateLike(String value) {
			addCriterion("DEFAULT_RATE like", value, "defaultRate");
			return this;
		}

		public Criteria andDefaultRateNotLike(String value) {
			addCriterion("DEFAULT_RATE not like", value, "defaultRate");
			return this;
		}

		public Criteria andDefaultRateIn(List<String> values) {
			addCriterion("DEFAULT_RATE in", values, "defaultRate");
			return this;
		}

		public Criteria andDefaultRateNotIn(List<String> values) {
			addCriterion("DEFAULT_RATE not in", values, "defaultRate");
			return this;
		}

		public Criteria andDefaultRateBetween(String value1, String value2) {
			addCriterion("DEFAULT_RATE between", value1, value2, "defaultRate");
			return this;
		}

		public Criteria andDefaultRateNotBetween(String value1, String value2) {
			addCriterion("DEFAULT_RATE not between", value1, value2,
					"defaultRate");
			return this;
		}

		public Criteria andIsAgentIsNull() {
			addCriterion("IS_AGENT is null");
			return this;
		}

		public Criteria andIsAgentIsNotNull() {
			addCriterion("IS_AGENT is not null");
			return this;
		}

		public Criteria andIsAgentEqualTo(String value) {
			addCriterion("IS_AGENT =", value, "isAgent");
			return this;
		}

		public Criteria andIsAgentNotEqualTo(String value) {
			addCriterion("IS_AGENT <>", value, "isAgent");
			return this;
		}

		public Criteria andIsAgentGreaterThan(String value) {
			addCriterion("IS_AGENT >", value, "isAgent");
			return this;
		}

		public Criteria andIsAgentGreaterThanOrEqualTo(String value) {
			addCriterion("IS_AGENT >=", value, "isAgent");
			return this;
		}

		public Criteria andIsAgentLessThan(String value) {
			addCriterion("IS_AGENT <", value, "isAgent");
			return this;
		}

		public Criteria andIsAgentLessThanOrEqualTo(String value) {
			addCriterion("IS_AGENT <=", value, "isAgent");
			return this;
		}

		public Criteria andIsAgentLike(String value) {
			addCriterion("IS_AGENT like", value, "isAgent");
			return this;
		}

		public Criteria andIsAgentNotLike(String value) {
			addCriterion("IS_AGENT not like", value, "isAgent");
			return this;
		}

		public Criteria andIsAgentIn(List<String> values) {
			addCriterion("IS_AGENT in", values, "isAgent");
			return this;
		}

		public Criteria andIsAgentNotIn(List<String> values) {
			addCriterion("IS_AGENT not in", values, "isAgent");
			return this;
		}

		public Criteria andIsAgentBetween(String value1, String value2) {
			addCriterion("IS_AGENT between", value1, value2, "isAgent");
			return this;
		}

		public Criteria andIsAgentNotBetween(String value1, String value2) {
			addCriterion("IS_AGENT not between", value1, value2, "isAgent");
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