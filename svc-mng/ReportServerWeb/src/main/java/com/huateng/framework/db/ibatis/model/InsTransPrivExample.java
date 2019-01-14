package com.huateng.framework.db.ibatis.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class InsTransPrivExample {
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to
	 * the database table TBL_INS_TRANS_PRIV
	 * 
	 * @abatorgenerated Wed Dec 28 14:51:12 CST 2011
	 */
	protected String orderByClause;

	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to
	 * the database table TBL_INS_TRANS_PRIV
	 * 
	 * @abatorgenerated Wed Dec 28 14:51:12 CST 2011
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TBL_INS_TRANS_PRIV
	 * 
	 * @abatorgenerated Wed Dec 28 14:51:12 CST 2011
	 */
	public InsTransPrivExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TBL_INS_TRANS_PRIV
	 * 
	 * @abatorgenerated Wed Dec 28 14:51:12 CST 2011
	 */
	protected InsTransPrivExample(InsTransPrivExample example) {
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TBL_INS_TRANS_PRIV
	 * 
	 * @abatorgenerated Wed Dec 28 14:51:12 CST 2011
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TBL_INS_TRANS_PRIV
	 * 
	 * @abatorgenerated Wed Dec 28 14:51:12 CST 2011
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TBL_INS_TRANS_PRIV
	 * 
	 * @abatorgenerated Wed Dec 28 14:51:12 CST 2011
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TBL_INS_TRANS_PRIV
	 * 
	 * @abatorgenerated Wed Dec 28 14:51:12 CST 2011
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TBL_INS_TRANS_PRIV
	 * 
	 * @abatorgenerated Wed Dec 28 14:51:12 CST 2011
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
	 * to the database table TBL_INS_TRANS_PRIV
	 * 
	 * @abatorgenerated Wed Dec 28 14:51:12 CST 2011
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TBL_INS_TRANS_PRIV
	 * 
	 * @abatorgenerated Wed Dec 28 14:51:12 CST 2011
	 */
	public void clear() {
		oredCriteria.clear();
	}

	/**
	 * This class was generated by Abator for iBATIS. This class corresponds to
	 * the database table TBL_INS_TRANS_PRIV
	 * 
	 * @abatorgenerated Wed Dec 28 14:51:12 CST 2011
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

		protected void addCriterionForJDBCDate(String condition, Date value,
				String property) {
			addCriterion(condition, new java.sql.Date(value.getTime()),
					property);
		}

		protected void addCriterionForJDBCDate(String condition,
				List<Date> values, String property) {
			if (values == null || values.size() == 0) {
				throw new RuntimeException("Value list for " + property
						+ " cannot be null or empty");
			}
			List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
			Iterator<Date> iter = values.iterator();
			while (iter.hasNext()) {
				dateList.add(new java.sql.Date(iter.next().getTime()));
			}
			addCriterion(condition, dateList, property);
		}

		protected void addCriterionForJDBCDate(String condition, Date value1,
				Date value2, String property) {
			if (value1 == null || value2 == null) {
				throw new RuntimeException("Between values for " + property
						+ " cannot be null");
			}
			addCriterion(condition, new java.sql.Date(value1.getTime()),
					new java.sql.Date(value2.getTime()), property);
		}

		public Criteria andInsIdCdIsNull() {
			addCriterion("INS_ID_CD is null");
			return this;
		}

		public Criteria andInsIdCdIsNotNull() {
			addCriterion("INS_ID_CD is not null");
			return this;
		}

		public Criteria andInsIdCdEqualTo(String value) {
			addCriterion("INS_ID_CD =", value, "insIdCd");
			return this;
		}

		public Criteria andInsIdCdNotEqualTo(String value) {
			addCriterion("INS_ID_CD <>", value, "insIdCd");
			return this;
		}

		public Criteria andInsIdCdGreaterThan(String value) {
			addCriterion("INS_ID_CD >", value, "insIdCd");
			return this;
		}

		public Criteria andInsIdCdGreaterThanOrEqualTo(String value) {
			addCriterion("INS_ID_CD >=", value, "insIdCd");
			return this;
		}

		public Criteria andInsIdCdLessThan(String value) {
			addCriterion("INS_ID_CD <", value, "insIdCd");
			return this;
		}

		public Criteria andInsIdCdLessThanOrEqualTo(String value) {
			addCriterion("INS_ID_CD <=", value, "insIdCd");
			return this;
		}

		public Criteria andInsIdCdLike(String value) {
			addCriterion("INS_ID_CD like", value, "insIdCd");
			return this;
		}

		public Criteria andInsIdCdNotLike(String value) {
			addCriterion("INS_ID_CD not like", value, "insIdCd");
			return this;
		}

		public Criteria andInsIdCdIn(List<String> values) {
			addCriterion("INS_ID_CD in", values, "insIdCd");
			return this;
		}

		public Criteria andInsIdCdNotIn(List<String> values) {
			addCriterion("INS_ID_CD not in", values, "insIdCd");
			return this;
		}

		public Criteria andInsIdCdBetween(String value1, String value2) {
			addCriterion("INS_ID_CD between", value1, value2, "insIdCd");
			return this;
		}

		public Criteria andInsIdCdNotBetween(String value1, String value2) {
			addCriterion("INS_ID_CD not between", value1, value2, "insIdCd");
			return this;
		}

		public Criteria andTransIdIsNull() {
			addCriterion("TRANS_ID is null");
			return this;
		}

		public Criteria andTransIdIsNotNull() {
			addCriterion("TRANS_ID is not null");
			return this;
		}

		public Criteria andTransIdEqualTo(String value) {
			addCriterion("TRANS_ID =", value, "transId");
			return this;
		}

		public Criteria andTransIdNotEqualTo(String value) {
			addCriterion("TRANS_ID <>", value, "transId");
			return this;
		}

		public Criteria andTransIdGreaterThan(String value) {
			addCriterion("TRANS_ID >", value, "transId");
			return this;
		}

		public Criteria andTransIdGreaterThanOrEqualTo(String value) {
			addCriterion("TRANS_ID >=", value, "transId");
			return this;
		}

		public Criteria andTransIdLessThan(String value) {
			addCriterion("TRANS_ID <", value, "transId");
			return this;
		}

		public Criteria andTransIdLessThanOrEqualTo(String value) {
			addCriterion("TRANS_ID <=", value, "transId");
			return this;
		}

		public Criteria andTransIdLike(String value) {
			addCriterion("TRANS_ID like", value, "transId");
			return this;
		}

		public Criteria andTransIdNotLike(String value) {
			addCriterion("TRANS_ID not like", value, "transId");
			return this;
		}

		public Criteria andTransIdIn(List<String> values) {
			addCriterion("TRANS_ID in", values, "transId");
			return this;
		}

		public Criteria andTransIdNotIn(List<String> values) {
			addCriterion("TRANS_ID not in", values, "transId");
			return this;
		}

		public Criteria andTransIdBetween(String value1, String value2) {
			addCriterion("TRANS_ID between", value1, value2, "transId");
			return this;
		}

		public Criteria andTransIdNotBetween(String value1, String value2) {
			addCriterion("TRANS_ID not between", value1, value2, "transId");
			return this;
		}

		public Criteria andFwdPrivIsNull() {
			addCriterion("FWD_PRIV is null");
			return this;
		}

		public Criteria andFwdPrivIsNotNull() {
			addCriterion("FWD_PRIV is not null");
			return this;
		}

		public Criteria andFwdPrivEqualTo(String value) {
			addCriterion("FWD_PRIV =", value, "fwdPriv");
			return this;
		}

		public Criteria andFwdPrivNotEqualTo(String value) {
			addCriterion("FWD_PRIV <>", value, "fwdPriv");
			return this;
		}

		public Criteria andFwdPrivGreaterThan(String value) {
			addCriterion("FWD_PRIV >", value, "fwdPriv");
			return this;
		}

		public Criteria andFwdPrivGreaterThanOrEqualTo(String value) {
			addCriterion("FWD_PRIV >=", value, "fwdPriv");
			return this;
		}

		public Criteria andFwdPrivLessThan(String value) {
			addCriterion("FWD_PRIV <", value, "fwdPriv");
			return this;
		}

		public Criteria andFwdPrivLessThanOrEqualTo(String value) {
			addCriterion("FWD_PRIV <=", value, "fwdPriv");
			return this;
		}

		public Criteria andFwdPrivLike(String value) {
			addCriterion("FWD_PRIV like", value, "fwdPriv");
			return this;
		}

		public Criteria andFwdPrivNotLike(String value) {
			addCriterion("FWD_PRIV not like", value, "fwdPriv");
			return this;
		}

		public Criteria andFwdPrivIn(List<String> values) {
			addCriterion("FWD_PRIV in", values, "fwdPriv");
			return this;
		}

		public Criteria andFwdPrivNotIn(List<String> values) {
			addCriterion("FWD_PRIV not in", values, "fwdPriv");
			return this;
		}

		public Criteria andFwdPrivBetween(String value1, String value2) {
			addCriterion("FWD_PRIV between", value1, value2, "fwdPriv");
			return this;
		}

		public Criteria andFwdPrivNotBetween(String value1, String value2) {
			addCriterion("FWD_PRIV not between", value1, value2, "fwdPriv");
			return this;
		}

		public Criteria andRcvPrivIsNull() {
			addCriterion("RCV_PRIV is null");
			return this;
		}

		public Criteria andRcvPrivIsNotNull() {
			addCriterion("RCV_PRIV is not null");
			return this;
		}

		public Criteria andRcvPrivEqualTo(String value) {
			addCriterion("RCV_PRIV =", value, "rcvPriv");
			return this;
		}

		public Criteria andRcvPrivNotEqualTo(String value) {
			addCriterion("RCV_PRIV <>", value, "rcvPriv");
			return this;
		}

		public Criteria andRcvPrivGreaterThan(String value) {
			addCriterion("RCV_PRIV >", value, "rcvPriv");
			return this;
		}

		public Criteria andRcvPrivGreaterThanOrEqualTo(String value) {
			addCriterion("RCV_PRIV >=", value, "rcvPriv");
			return this;
		}

		public Criteria andRcvPrivLessThan(String value) {
			addCriterion("RCV_PRIV <", value, "rcvPriv");
			return this;
		}

		public Criteria andRcvPrivLessThanOrEqualTo(String value) {
			addCriterion("RCV_PRIV <=", value, "rcvPriv");
			return this;
		}

		public Criteria andRcvPrivLike(String value) {
			addCriterion("RCV_PRIV like", value, "rcvPriv");
			return this;
		}

		public Criteria andRcvPrivNotLike(String value) {
			addCriterion("RCV_PRIV not like", value, "rcvPriv");
			return this;
		}

		public Criteria andRcvPrivIn(List<String> values) {
			addCriterion("RCV_PRIV in", values, "rcvPriv");
			return this;
		}

		public Criteria andRcvPrivNotIn(List<String> values) {
			addCriterion("RCV_PRIV not in", values, "rcvPriv");
			return this;
		}

		public Criteria andRcvPrivBetween(String value1, String value2) {
			addCriterion("RCV_PRIV between", value1, value2, "rcvPriv");
			return this;
		}

		public Criteria andRcvPrivNotBetween(String value1, String value2) {
			addCriterion("RCV_PRIV not between", value1, value2, "rcvPriv");
			return this;
		}

		public Criteria andRecUpdUsrIdIsNull() {
			addCriterion("REC_UPD_USR_ID is null");
			return this;
		}

		public Criteria andRecUpdUsrIdIsNotNull() {
			addCriterion("REC_UPD_USR_ID is not null");
			return this;
		}

		public Criteria andRecUpdUsrIdEqualTo(String value) {
			addCriterion("REC_UPD_USR_ID =", value, "recUpdUsrId");
			return this;
		}

		public Criteria andRecUpdUsrIdNotEqualTo(String value) {
			addCriterion("REC_UPD_USR_ID <>", value, "recUpdUsrId");
			return this;
		}

		public Criteria andRecUpdUsrIdGreaterThan(String value) {
			addCriterion("REC_UPD_USR_ID >", value, "recUpdUsrId");
			return this;
		}

		public Criteria andRecUpdUsrIdGreaterThanOrEqualTo(String value) {
			addCriterion("REC_UPD_USR_ID >=", value, "recUpdUsrId");
			return this;
		}

		public Criteria andRecUpdUsrIdLessThan(String value) {
			addCriterion("REC_UPD_USR_ID <", value, "recUpdUsrId");
			return this;
		}

		public Criteria andRecUpdUsrIdLessThanOrEqualTo(String value) {
			addCriterion("REC_UPD_USR_ID <=", value, "recUpdUsrId");
			return this;
		}

		public Criteria andRecUpdUsrIdLike(String value) {
			addCriterion("REC_UPD_USR_ID like", value, "recUpdUsrId");
			return this;
		}

		public Criteria andRecUpdUsrIdNotLike(String value) {
			addCriterion("REC_UPD_USR_ID not like", value, "recUpdUsrId");
			return this;
		}

		public Criteria andRecUpdUsrIdIn(List<String> values) {
			addCriterion("REC_UPD_USR_ID in", values, "recUpdUsrId");
			return this;
		}

		public Criteria andRecUpdUsrIdNotIn(List<String> values) {
			addCriterion("REC_UPD_USR_ID not in", values, "recUpdUsrId");
			return this;
		}

		public Criteria andRecUpdUsrIdBetween(String value1, String value2) {
			addCriterion("REC_UPD_USR_ID between", value1, value2,
					"recUpdUsrId");
			return this;
		}

		public Criteria andRecUpdUsrIdNotBetween(String value1, String value2) {
			addCriterion("REC_UPD_USR_ID not between", value1, value2,
					"recUpdUsrId");
			return this;
		}

		public Criteria andRecUpdTsIsNull() {
			addCriterion("REC_UPD_TS is null");
			return this;
		}

		public Criteria andRecUpdTsIsNotNull() {
			addCriterion("REC_UPD_TS is not null");
			return this;
		}

		public Criteria andRecUpdTsEqualTo(Date value) {
			addCriterionForJDBCDate("REC_UPD_TS =", value, "recUpdTs");
			return this;
		}

		public Criteria andRecUpdTsNotEqualTo(Date value) {
			addCriterionForJDBCDate("REC_UPD_TS <>", value, "recUpdTs");
			return this;
		}

		public Criteria andRecUpdTsGreaterThan(Date value) {
			addCriterionForJDBCDate("REC_UPD_TS >", value, "recUpdTs");
			return this;
		}

		public Criteria andRecUpdTsGreaterThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("REC_UPD_TS >=", value, "recUpdTs");
			return this;
		}

		public Criteria andRecUpdTsLessThan(Date value) {
			addCriterionForJDBCDate("REC_UPD_TS <", value, "recUpdTs");
			return this;
		}

		public Criteria andRecUpdTsLessThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("REC_UPD_TS <=", value, "recUpdTs");
			return this;
		}

		public Criteria andRecUpdTsIn(List<Date> values) {
			addCriterionForJDBCDate("REC_UPD_TS in", values, "recUpdTs");
			return this;
		}

		public Criteria andRecUpdTsNotIn(List<Date> values) {
			addCriterionForJDBCDate("REC_UPD_TS not in", values, "recUpdTs");
			return this;
		}

		public Criteria andRecUpdTsBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("REC_UPD_TS between", value1, value2,
					"recUpdTs");
			return this;
		}

		public Criteria andRecUpdTsNotBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("REC_UPD_TS not between", value1, value2,
					"recUpdTs");
			return this;
		}

		public Criteria andRecCrtTsIsNull() {
			addCriterion("REC_CRT_TS is null");
			return this;
		}

		public Criteria andRecCrtTsIsNotNull() {
			addCriterion("REC_CRT_TS is not null");
			return this;
		}

		public Criteria andRecCrtTsEqualTo(Date value) {
			addCriterionForJDBCDate("REC_CRT_TS =", value, "recCrtTs");
			return this;
		}

		public Criteria andRecCrtTsNotEqualTo(Date value) {
			addCriterionForJDBCDate("REC_CRT_TS <>", value, "recCrtTs");
			return this;
		}

		public Criteria andRecCrtTsGreaterThan(Date value) {
			addCriterionForJDBCDate("REC_CRT_TS >", value, "recCrtTs");
			return this;
		}

		public Criteria andRecCrtTsGreaterThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("REC_CRT_TS >=", value, "recCrtTs");
			return this;
		}

		public Criteria andRecCrtTsLessThan(Date value) {
			addCriterionForJDBCDate("REC_CRT_TS <", value, "recCrtTs");
			return this;
		}

		public Criteria andRecCrtTsLessThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("REC_CRT_TS <=", value, "recCrtTs");
			return this;
		}

		public Criteria andRecCrtTsIn(List<Date> values) {
			addCriterionForJDBCDate("REC_CRT_TS in", values, "recCrtTs");
			return this;
		}

		public Criteria andRecCrtTsNotIn(List<Date> values) {
			addCriterionForJDBCDate("REC_CRT_TS not in", values, "recCrtTs");
			return this;
		}

		public Criteria andRecCrtTsBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("REC_CRT_TS between", value1, value2,
					"recCrtTs");
			return this;
		}

		public Criteria andRecCrtTsNotBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("REC_CRT_TS not between", value1, value2,
					"recCrtTs");
			return this;
		}
	}
}