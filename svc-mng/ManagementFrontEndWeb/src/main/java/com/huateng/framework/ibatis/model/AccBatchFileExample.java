package com.huateng.framework.ibatis.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccBatchFileExample {

	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database table TBL_ACC_BATCH_FILE
	 * @abatorgenerated  Tue Dec 04 19:16:11 CST 2012
	 */
	protected String orderByClause;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database table TBL_ACC_BATCH_FILE
	 * @abatorgenerated  Tue Dec 04 19:16:11 CST 2012
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TBL_ACC_BATCH_FILE
	 * @abatorgenerated  Tue Dec 04 19:16:11 CST 2012
	 */
	public AccBatchFileExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TBL_ACC_BATCH_FILE
	 * @abatorgenerated  Tue Dec 04 19:16:11 CST 2012
	 */
	protected AccBatchFileExample(AccBatchFileExample example) {
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TBL_ACC_BATCH_FILE
	 * @abatorgenerated  Tue Dec 04 19:16:11 CST 2012
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TBL_ACC_BATCH_FILE
	 * @abatorgenerated  Tue Dec 04 19:16:11 CST 2012
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TBL_ACC_BATCH_FILE
	 * @abatorgenerated  Tue Dec 04 19:16:11 CST 2012
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TBL_ACC_BATCH_FILE
	 * @abatorgenerated  Tue Dec 04 19:16:11 CST 2012
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TBL_ACC_BATCH_FILE
	 * @abatorgenerated  Tue Dec 04 19:16:11 CST 2012
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TBL_ACC_BATCH_FILE
	 * @abatorgenerated  Tue Dec 04 19:16:11 CST 2012
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TBL_ACC_BATCH_FILE
	 * @abatorgenerated  Tue Dec 04 19:16:11 CST 2012
	 */
	public void clear() {
		oredCriteria.clear();
	}

	/**
	 * This class was generated by Abator for iBATIS. This class corresponds to the database table TBL_ACC_BATCH_FILE
	 * @abatorgenerated  Tue Dec 04 19:16:11 CST 2012
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

		public Criteria andBatchNoIsNull() {
			addCriterion("BATCH_NO is null");
			return this;
		}

		public Criteria andBatchNoIsNotNull() {
			addCriterion("BATCH_NO is not null");
			return this;
		}

		public Criteria andBatchNoEqualTo(String value) {
			addCriterion("BATCH_NO =", value, "batchNo");
			return this;
		}

		public Criteria andBatchNoNotEqualTo(String value) {
			addCriterion("BATCH_NO <>", value, "batchNo");
			return this;
		}

		public Criteria andBatchNoGreaterThan(String value) {
			addCriterion("BATCH_NO >", value, "batchNo");
			return this;
		}

		public Criteria andBatchNoGreaterThanOrEqualTo(String value) {
			addCriterion("BATCH_NO >=", value, "batchNo");
			return this;
		}

		public Criteria andBatchNoLessThan(String value) {
			addCriterion("BATCH_NO <", value, "batchNo");
			return this;
		}

		public Criteria andBatchNoLessThanOrEqualTo(String value) {
			addCriterion("BATCH_NO <=", value, "batchNo");
			return this;
		}

		public Criteria andBatchNoLike(String value) {
			addCriterion("BATCH_NO like", value, "batchNo");
			return this;
		}

		public Criteria andBatchNoNotLike(String value) {
			addCriterion("BATCH_NO not like", value, "batchNo");
			return this;
		}

		public Criteria andBatchNoIn(List<String> values) {
			addCriterion("BATCH_NO in", values, "batchNo");
			return this;
		}

		public Criteria andBatchNoNotIn(List<String> values) {
			addCriterion("BATCH_NO not in", values, "batchNo");
			return this;
		}

		public Criteria andBatchNoBetween(String value1, String value2) {
			addCriterion("BATCH_NO between", value1, value2, "batchNo");
			return this;
		}

		public Criteria andBatchNoNotBetween(String value1, String value2) {
			addCriterion("BATCH_NO not between", value1, value2, "batchNo");
			return this;
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

		public Criteria andFileTypeIsNull() {
			addCriterion("FILE_TYPE is null");
			return this;
		}

		public Criteria andFileTypeIsNotNull() {
			addCriterion("FILE_TYPE is not null");
			return this;
		}

		public Criteria andFileTypeEqualTo(String value) {
			addCriterion("FILE_TYPE =", value, "fileType");
			return this;
		}

		public Criteria andFileTypeNotEqualTo(String value) {
			addCriterion("FILE_TYPE <>", value, "fileType");
			return this;
		}

		public Criteria andFileTypeGreaterThan(String value) {
			addCriterion("FILE_TYPE >", value, "fileType");
			return this;
		}

		public Criteria andFileTypeGreaterThanOrEqualTo(String value) {
			addCriterion("FILE_TYPE >=", value, "fileType");
			return this;
		}

		public Criteria andFileTypeLessThan(String value) {
			addCriterion("FILE_TYPE <", value, "fileType");
			return this;
		}

		public Criteria andFileTypeLessThanOrEqualTo(String value) {
			addCriterion("FILE_TYPE <=", value, "fileType");
			return this;
		}

		public Criteria andFileTypeLike(String value) {
			addCriterion("FILE_TYPE like", value, "fileType");
			return this;
		}

		public Criteria andFileTypeNotLike(String value) {
			addCriterion("FILE_TYPE not like", value, "fileType");
			return this;
		}

		public Criteria andFileTypeIn(List<String> values) {
			addCriterion("FILE_TYPE in", values, "fileType");
			return this;
		}

		public Criteria andFileTypeNotIn(List<String> values) {
			addCriterion("FILE_TYPE not in", values, "fileType");
			return this;
		}

		public Criteria andFileTypeBetween(String value1, String value2) {
			addCriterion("FILE_TYPE between", value1, value2, "fileType");
			return this;
		}

		public Criteria andFileTypeNotBetween(String value1, String value2) {
			addCriterion("FILE_TYPE not between", value1, value2, "fileType");
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

		public Criteria andFileStatIsNull() {
			addCriterion("FILE_STAT is null");
			return this;
		}

		public Criteria andFileStatIsNotNull() {
			addCriterion("FILE_STAT is not null");
			return this;
		}

		public Criteria andFileStatEqualTo(String value) {
			addCriterion("FILE_STAT =", value, "fileStat");
			return this;
		}

		public Criteria andFileStatNotEqualTo(String value) {
			addCriterion("FILE_STAT <>", value, "fileStat");
			return this;
		}

		public Criteria andFileStatGreaterThan(String value) {
			addCriterion("FILE_STAT >", value, "fileStat");
			return this;
		}

		public Criteria andFileStatGreaterThanOrEqualTo(String value) {
			addCriterion("FILE_STAT >=", value, "fileStat");
			return this;
		}

		public Criteria andFileStatLessThan(String value) {
			addCriterion("FILE_STAT <", value, "fileStat");
			return this;
		}

		public Criteria andFileStatLessThanOrEqualTo(String value) {
			addCriterion("FILE_STAT <=", value, "fileStat");
			return this;
		}

		public Criteria andFileStatLike(String value) {
			addCriterion("FILE_STAT like", value, "fileStat");
			return this;
		}

		public Criteria andFileStatNotLike(String value) {
			addCriterion("FILE_STAT not like", value, "fileStat");
			return this;
		}

		public Criteria andFileStatIn(List<String> values) {
			addCriterion("FILE_STAT in", values, "fileStat");
			return this;
		}

		public Criteria andFileStatNotIn(List<String> values) {
			addCriterion("FILE_STAT not in", values, "fileStat");
			return this;
		}

		public Criteria andFileStatBetween(String value1, String value2) {
			addCriterion("FILE_STAT between", value1, value2, "fileStat");
			return this;
		}

		public Criteria andFileStatNotBetween(String value1, String value2) {
			addCriterion("FILE_STAT not between", value1, value2, "fileStat");
			return this;
		}

		public Criteria andFileNameIsNull() {
			addCriterion("FILE_NAME is null");
			return this;
		}

		public Criteria andFileNameIsNotNull() {
			addCriterion("FILE_NAME is not null");
			return this;
		}

		public Criteria andFileNameEqualTo(String value) {
			addCriterion("FILE_NAME =", value, "fileName");
			return this;
		}

		public Criteria andFileNameNotEqualTo(String value) {
			addCriterion("FILE_NAME <>", value, "fileName");
			return this;
		}

		public Criteria andFileNameGreaterThan(String value) {
			addCriterion("FILE_NAME >", value, "fileName");
			return this;
		}

		public Criteria andFileNameGreaterThanOrEqualTo(String value) {
			addCriterion("FILE_NAME >=", value, "fileName");
			return this;
		}

		public Criteria andFileNameLessThan(String value) {
			addCriterion("FILE_NAME <", value, "fileName");
			return this;
		}

		public Criteria andFileNameLessThanOrEqualTo(String value) {
			addCriterion("FILE_NAME <=", value, "fileName");
			return this;
		}

		public Criteria andFileNameLike(String value) {
			addCriterion("FILE_NAME like", value, "fileName");
			return this;
		}

		public Criteria andFileNameNotLike(String value) {
			addCriterion("FILE_NAME not like", value, "fileName");
			return this;
		}

		public Criteria andFileNameIn(List<String> values) {
			addCriterion("FILE_NAME in", values, "fileName");
			return this;
		}

		public Criteria andFileNameNotIn(List<String> values) {
			addCriterion("FILE_NAME not in", values, "fileName");
			return this;
		}

		public Criteria andFileNameBetween(String value1, String value2) {
			addCriterion("FILE_NAME between", value1, value2, "fileName");
			return this;
		}

		public Criteria andFileNameNotBetween(String value1, String value2) {
			addCriterion("FILE_NAME not between", value1, value2, "fileName");
			return this;
		}

		public Criteria andTotCntIsNull() {
			addCriterion("TOT_CNT is null");
			return this;
		}

		public Criteria andTotCntIsNotNull() {
			addCriterion("TOT_CNT is not null");
			return this;
		}

		public Criteria andTotCntEqualTo(String value) {
			addCriterion("TOT_CNT =", value, "totCnt");
			return this;
		}

		public Criteria andTotCntNotEqualTo(String value) {
			addCriterion("TOT_CNT <>", value, "totCnt");
			return this;
		}

		public Criteria andTotCntGreaterThan(String value) {
			addCriterion("TOT_CNT >", value, "totCnt");
			return this;
		}

		public Criteria andTotCntGreaterThanOrEqualTo(String value) {
			addCriterion("TOT_CNT >=", value, "totCnt");
			return this;
		}

		public Criteria andTotCntLessThan(String value) {
			addCriterion("TOT_CNT <", value, "totCnt");
			return this;
		}

		public Criteria andTotCntLessThanOrEqualTo(String value) {
			addCriterion("TOT_CNT <=", value, "totCnt");
			return this;
		}

		public Criteria andTotCntLike(String value) {
			addCriterion("TOT_CNT like", value, "totCnt");
			return this;
		}

		public Criteria andTotCntNotLike(String value) {
			addCriterion("TOT_CNT not like", value, "totCnt");
			return this;
		}

		public Criteria andTotCntIn(List<String> values) {
			addCriterion("TOT_CNT in", values, "totCnt");
			return this;
		}

		public Criteria andTotCntNotIn(List<String> values) {
			addCriterion("TOT_CNT not in", values, "totCnt");
			return this;
		}

		public Criteria andTotCntBetween(String value1, String value2) {
			addCriterion("TOT_CNT between", value1, value2, "totCnt");
			return this;
		}

		public Criteria andTotCntNotBetween(String value1, String value2) {
			addCriterion("TOT_CNT not between", value1, value2, "totCnt");
			return this;
		}

		public Criteria andSucCntIsNull() {
			addCriterion("SUC_CNT is null");
			return this;
		}

		public Criteria andSucCntIsNotNull() {
			addCriterion("SUC_CNT is not null");
			return this;
		}

		public Criteria andSucCntEqualTo(String value) {
			addCriterion("SUC_CNT =", value, "sucCnt");
			return this;
		}

		public Criteria andSucCntNotEqualTo(String value) {
			addCriterion("SUC_CNT <>", value, "sucCnt");
			return this;
		}

		public Criteria andSucCntGreaterThan(String value) {
			addCriterion("SUC_CNT >", value, "sucCnt");
			return this;
		}

		public Criteria andSucCntGreaterThanOrEqualTo(String value) {
			addCriterion("SUC_CNT >=", value, "sucCnt");
			return this;
		}

		public Criteria andSucCntLessThan(String value) {
			addCriterion("SUC_CNT <", value, "sucCnt");
			return this;
		}

		public Criteria andSucCntLessThanOrEqualTo(String value) {
			addCriterion("SUC_CNT <=", value, "sucCnt");
			return this;
		}

		public Criteria andSucCntLike(String value) {
			addCriterion("SUC_CNT like", value, "sucCnt");
			return this;
		}

		public Criteria andSucCntNotLike(String value) {
			addCriterion("SUC_CNT not like", value, "sucCnt");
			return this;
		}

		public Criteria andSucCntIn(List<String> values) {
			addCriterion("SUC_CNT in", values, "sucCnt");
			return this;
		}

		public Criteria andSucCntNotIn(List<String> values) {
			addCriterion("SUC_CNT not in", values, "sucCnt");
			return this;
		}

		public Criteria andSucCntBetween(String value1, String value2) {
			addCriterion("SUC_CNT between", value1, value2, "sucCnt");
			return this;
		}

		public Criteria andSucCntNotBetween(String value1, String value2) {
			addCriterion("SUC_CNT not between", value1, value2, "sucCnt");
			return this;
		}

		public Criteria andRsvd1IsNull() {
			addCriterion("RSVD1 is null");
			return this;
		}

		public Criteria andRsvd1IsNotNull() {
			addCriterion("RSVD1 is not null");
			return this;
		}

		public Criteria andRsvd1EqualTo(String value) {
			addCriterion("RSVD1 =", value, "rsvd1");
			return this;
		}

		public Criteria andRsvd1NotEqualTo(String value) {
			addCriterion("RSVD1 <>", value, "rsvd1");
			return this;
		}

		public Criteria andRsvd1GreaterThan(String value) {
			addCriterion("RSVD1 >", value, "rsvd1");
			return this;
		}

		public Criteria andRsvd1GreaterThanOrEqualTo(String value) {
			addCriterion("RSVD1 >=", value, "rsvd1");
			return this;
		}

		public Criteria andRsvd1LessThan(String value) {
			addCriterion("RSVD1 <", value, "rsvd1");
			return this;
		}

		public Criteria andRsvd1LessThanOrEqualTo(String value) {
			addCriterion("RSVD1 <=", value, "rsvd1");
			return this;
		}

		public Criteria andRsvd1Like(String value) {
			addCriterion("RSVD1 like", value, "rsvd1");
			return this;
		}

		public Criteria andRsvd1NotLike(String value) {
			addCriterion("RSVD1 not like", value, "rsvd1");
			return this;
		}

		public Criteria andRsvd1In(List<String> values) {
			addCriterion("RSVD1 in", values, "rsvd1");
			return this;
		}

		public Criteria andRsvd1NotIn(List<String> values) {
			addCriterion("RSVD1 not in", values, "rsvd1");
			return this;
		}

		public Criteria andRsvd1Between(String value1, String value2) {
			addCriterion("RSVD1 between", value1, value2, "rsvd1");
			return this;
		}

		public Criteria andRsvd1NotBetween(String value1, String value2) {
			addCriterion("RSVD1 not between", value1, value2, "rsvd1");
			return this;
		}

		public Criteria andRsvd2IsNull() {
			addCriterion("RSVD2 is null");
			return this;
		}

		public Criteria andRsvd2IsNotNull() {
			addCriterion("RSVD2 is not null");
			return this;
		}

		public Criteria andRsvd2EqualTo(String value) {
			addCriterion("RSVD2 =", value, "rsvd2");
			return this;
		}

		public Criteria andRsvd2NotEqualTo(String value) {
			addCriterion("RSVD2 <>", value, "rsvd2");
			return this;
		}

		public Criteria andRsvd2GreaterThan(String value) {
			addCriterion("RSVD2 >", value, "rsvd2");
			return this;
		}

		public Criteria andRsvd2GreaterThanOrEqualTo(String value) {
			addCriterion("RSVD2 >=", value, "rsvd2");
			return this;
		}

		public Criteria andRsvd2LessThan(String value) {
			addCriterion("RSVD2 <", value, "rsvd2");
			return this;
		}

		public Criteria andRsvd2LessThanOrEqualTo(String value) {
			addCriterion("RSVD2 <=", value, "rsvd2");
			return this;
		}

		public Criteria andRsvd2Like(String value) {
			addCriterion("RSVD2 like", value, "rsvd2");
			return this;
		}

		public Criteria andRsvd2NotLike(String value) {
			addCriterion("RSVD2 not like", value, "rsvd2");
			return this;
		}

		public Criteria andRsvd2In(List<String> values) {
			addCriterion("RSVD2 in", values, "rsvd2");
			return this;
		}

		public Criteria andRsvd2NotIn(List<String> values) {
			addCriterion("RSVD2 not in", values, "rsvd2");
			return this;
		}

		public Criteria andRsvd2Between(String value1, String value2) {
			addCriterion("RSVD2 between", value1, value2, "rsvd2");
			return this;
		}

		public Criteria andRsvd2NotBetween(String value1, String value2) {
			addCriterion("RSVD2 not between", value1, value2, "rsvd2");
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

		public Criteria andRecCrtTsEqualTo(String value) {
			addCriterion("REC_CRT_TS =", value, "recCrtTs");
			return this;
		}

		public Criteria andRecCrtTsNotEqualTo(String value) {
			addCriterion("REC_CRT_TS <>", value, "recCrtTs");
			return this;
		}

		public Criteria andRecCrtTsGreaterThan(String value) {
			addCriterion("REC_CRT_TS >", value, "recCrtTs");
			return this;
		}

		public Criteria andRecCrtTsGreaterThanOrEqualTo(String value) {
			addCriterion("REC_CRT_TS >=", value, "recCrtTs");
			return this;
		}

		public Criteria andRecCrtTsLessThan(String value) {
			addCriterion("REC_CRT_TS <", value, "recCrtTs");
			return this;
		}

		public Criteria andRecCrtTsLessThanOrEqualTo(String value) {
			addCriterion("REC_CRT_TS <=", value, "recCrtTs");
			return this;
		}

		public Criteria andRecCrtTsLike(String value) {
			addCriterion("REC_CRT_TS like", value, "recCrtTs");
			return this;
		}

		public Criteria andRecCrtTsNotLike(String value) {
			addCriterion("REC_CRT_TS not like", value, "recCrtTs");
			return this;
		}

		public Criteria andRecCrtTsIn(List<String> values) {
			addCriterion("REC_CRT_TS in", values, "recCrtTs");
			return this;
		}

		public Criteria andRecCrtTsNotIn(List<String> values) {
			addCriterion("REC_CRT_TS not in", values, "recCrtTs");
			return this;
		}

		public Criteria andRecCrtTsBetween(String value1, String value2) {
			addCriterion("REC_CRT_TS between", value1, value2, "recCrtTs");
			return this;
		}

		public Criteria andRecCrtTsNotBetween(String value1, String value2) {
			addCriterion("REC_CRT_TS not between", value1, value2, "recCrtTs");
			return this;
		}

		public Criteria andUpdCrtTsIsNull() {
			addCriterion("UPD_CRT_TS is null");
			return this;
		}

		public Criteria andUpdCrtTsIsNotNull() {
			addCriterion("UPD_CRT_TS is not null");
			return this;
		}

		public Criteria andUpdCrtTsEqualTo(String value) {
			addCriterion("UPD_CRT_TS =", value, "updCrtTs");
			return this;
		}

		public Criteria andUpdCrtTsNotEqualTo(String value) {
			addCriterion("UPD_CRT_TS <>", value, "updCrtTs");
			return this;
		}

		public Criteria andUpdCrtTsGreaterThan(String value) {
			addCriterion("UPD_CRT_TS >", value, "updCrtTs");
			return this;
		}

		public Criteria andUpdCrtTsGreaterThanOrEqualTo(String value) {
			addCriterion("UPD_CRT_TS >=", value, "updCrtTs");
			return this;
		}

		public Criteria andUpdCrtTsLessThan(String value) {
			addCriterion("UPD_CRT_TS <", value, "updCrtTs");
			return this;
		}

		public Criteria andUpdCrtTsLessThanOrEqualTo(String value) {
			addCriterion("UPD_CRT_TS <=", value, "updCrtTs");
			return this;
		}

		public Criteria andUpdCrtTsLike(String value) {
			addCriterion("UPD_CRT_TS like", value, "updCrtTs");
			return this;
		}

		public Criteria andUpdCrtTsNotLike(String value) {
			addCriterion("UPD_CRT_TS not like", value, "updCrtTs");
			return this;
		}

		public Criteria andUpdCrtTsIn(List<String> values) {
			addCriterion("UPD_CRT_TS in", values, "updCrtTs");
			return this;
		}

		public Criteria andUpdCrtTsNotIn(List<String> values) {
			addCriterion("UPD_CRT_TS not in", values, "updCrtTs");
			return this;
		}

		public Criteria andUpdCrtTsBetween(String value1, String value2) {
			addCriterion("UPD_CRT_TS between", value1, value2, "updCrtTs");
			return this;
		}

		public Criteria andUpdCrtTsNotBetween(String value1, String value2) {
			addCriterion("UPD_CRT_TS not between", value1, value2, "updCrtTs");
			return this;
		}
	}
}