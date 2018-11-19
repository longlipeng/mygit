package com.suning.svc.ibatis.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DealBatchExample {

    /**
     * This field was generated by Abator for iBATIS. This field corresponds to the database table CP_DEAL_BATCH
     * 
     * @abatorgenerated Tue Oct 29 19:11:44 CST 2013
     */
    protected String orderByClause;
    /**
     * This field was generated by Abator for iBATIS. This field corresponds to the database table CP_DEAL_BATCH
     * 
     * @abatorgenerated Tue Oct 29 19:11:44 CST 2013
     */
    protected List oredCriteria;

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table CP_DEAL_BATCH
     * 
     * @abatorgenerated Tue Oct 29 19:11:44 CST 2013
     */
    public DealBatchExample() {
        oredCriteria = new ArrayList();
    }

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table CP_DEAL_BATCH
     * 
     * @abatorgenerated Tue Oct 29 19:11:44 CST 2013
     */
    protected DealBatchExample(DealBatchExample example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
    }

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table CP_DEAL_BATCH
     * 
     * @abatorgenerated Tue Oct 29 19:11:44 CST 2013
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table CP_DEAL_BATCH
     * 
     * @abatorgenerated Tue Oct 29 19:11:44 CST 2013
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table CP_DEAL_BATCH
     * 
     * @abatorgenerated Tue Oct 29 19:11:44 CST 2013
     */
    public List getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table CP_DEAL_BATCH
     * 
     * @abatorgenerated Tue Oct 29 19:11:44 CST 2013
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table CP_DEAL_BATCH
     * 
     * @abatorgenerated Tue Oct 29 19:11:44 CST 2013
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table CP_DEAL_BATCH
     * 
     * @abatorgenerated Tue Oct 29 19:11:44 CST 2013
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table CP_DEAL_BATCH
     * 
     * @abatorgenerated Tue Oct 29 19:11:44 CST 2013
     */
    public void clear() {
        oredCriteria.clear();
    }

    /**
     * This class was generated by Abator for iBATIS. This class corresponds to the database table CP_DEAL_BATCH
     * 
     * @abatorgenerated Tue Oct 29 19:11:44 CST 2013
     */
    public static class Criteria {
        protected List criteriaWithoutValue;
        protected List criteriaWithSingleValue;
        protected List criteriaWithListValue;
        protected List criteriaWithBetweenValue;

        protected Criteria() {
            super();
            criteriaWithoutValue = new ArrayList();
            criteriaWithSingleValue = new ArrayList();
            criteriaWithListValue = new ArrayList();
            criteriaWithBetweenValue = new ArrayList();
        }

        public boolean isValid() {
            return criteriaWithoutValue.size() > 0 || criteriaWithSingleValue.size() > 0
                    || criteriaWithListValue.size() > 0 || criteriaWithBetweenValue.size() > 0;
        }

        public List getCriteriaWithoutValue() {
            return criteriaWithoutValue;
        }

        public List getCriteriaWithSingleValue() {
            return criteriaWithSingleValue;
        }

        public List getCriteriaWithListValue() {
            return criteriaWithListValue;
        }

        public List getCriteriaWithBetweenValue() {
            return criteriaWithBetweenValue;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteriaWithoutValue.add(condition);
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            Map map = new HashMap();
            map.put("condition", condition);
            map.put("value", value);
            criteriaWithSingleValue.add(map);
        }

        protected void addCriterion(String condition, List values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            Map map = new HashMap();
            map.put("condition", condition);
            map.put("values", values);
            criteriaWithListValue.add(map);
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            List list = new ArrayList();
            list.add(value1);
            list.add(value2);
            Map map = new HashMap();
            map.put("condition", condition);
            map.put("values", list);
            criteriaWithBetweenValue.add(map);
        }

        public Criteria andIdIsNull() {
            addCriterion("ID is null");
            return this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ID is not null");
            return this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("ID =", value, "id");
            return this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("ID <>", value, "id");
            return this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("ID >", value, "id");
            return this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("ID >=", value, "id");
            return this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("ID <", value, "id");
            return this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("ID <=", value, "id");
            return this;
        }

        public Criteria andIdIn(List values) {
            addCriterion("ID in", values, "id");
            return this;
        }

        public Criteria andIdNotIn(List values) {
            addCriterion("ID not in", values, "id");
            return this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("ID between", value1, value2, "id");
            return this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("ID not between", value1, value2, "id");
            return this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("TYPE is null");
            return this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("TYPE is not null");
            return this;
        }

        public Criteria andTypeEqualTo(String value) {
            addCriterion("TYPE =", value, "type");
            return this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("TYPE <>", value, "type");
            return this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("TYPE >", value, "type");
            return this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("TYPE >=", value, "type");
            return this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("TYPE <", value, "type");
            return this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("TYPE <=", value, "type");
            return this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("TYPE like", value, "type");
            return this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("TYPE not like", value, "type");
            return this;
        }

        public Criteria andTypeIn(List values) {
            addCriterion("TYPE in", values, "type");
            return this;
        }

        public Criteria andTypeNotIn(List values) {
            addCriterion("TYPE not in", values, "type");
            return this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("TYPE between", value1, value2, "type");
            return this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("TYPE not between", value1, value2, "type");
            return this;
        }

        public Criteria andBeginTimeIsNull() {
            addCriterion("BEGIN_TIME is null");
            return this;
        }

        public Criteria andBeginTimeIsNotNull() {
            addCriterion("BEGIN_TIME is not null");
            return this;
        }

        public Criteria andBeginTimeEqualTo(Date value) {
            addCriterion("BEGIN_TIME =", value, "beginTime");
            return this;
        }

        public Criteria andBeginTimeNotEqualTo(Date value) {
            addCriterion("BEGIN_TIME <>", value, "beginTime");
            return this;
        }

        public Criteria andBeginTimeGreaterThan(Date value) {
            addCriterion("BEGIN_TIME >", value, "beginTime");
            return this;
        }

        public Criteria andBeginTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("BEGIN_TIME >=", value, "beginTime");
            return this;
        }

        public Criteria andBeginTimeLessThan(Date value) {
            addCriterion("BEGIN_TIME <", value, "beginTime");
            return this;
        }

        public Criteria andBeginTimeLessThanOrEqualTo(Date value) {
            addCriterion("BEGIN_TIME <=", value, "beginTime");
            return this;
        }

        public Criteria andBeginTimeIn(List values) {
            addCriterion("BEGIN_TIME in", values, "beginTime");
            return this;
        }

        public Criteria andBeginTimeNotIn(List values) {
            addCriterion("BEGIN_TIME not in", values, "beginTime");
            return this;
        }

        public Criteria andBeginTimeBetween(Date value1, Date value2) {
            addCriterion("BEGIN_TIME between", value1, value2, "beginTime");
            return this;
        }

        public Criteria andBeginTimeNotBetween(Date value1, Date value2) {
            addCriterion("BEGIN_TIME not between", value1, value2, "beginTime");
            return this;
        }

        public Criteria andEndTimeIsNull() {
            addCriterion("END_TIME is null");
            return this;
        }

        public Criteria andEndTimeIsNotNull() {
            addCriterion("END_TIME is not null");
            return this;
        }

        public Criteria andEndTimeEqualTo(Date value) {
            addCriterion("END_TIME =", value, "endTime");
            return this;
        }

        public Criteria andEndTimeNotEqualTo(Date value) {
            addCriterion("END_TIME <>", value, "endTime");
            return this;
        }

        public Criteria andEndTimeGreaterThan(Date value) {
            addCriterion("END_TIME >", value, "endTime");
            return this;
        }

        public Criteria andEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("END_TIME >=", value, "endTime");
            return this;
        }

        public Criteria andEndTimeLessThan(Date value) {
            addCriterion("END_TIME <", value, "endTime");
            return this;
        }

        public Criteria andEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("END_TIME <=", value, "endTime");
            return this;
        }

        public Criteria andEndTimeIn(List values) {
            addCriterion("END_TIME in", values, "endTime");
            return this;
        }

        public Criteria andEndTimeNotIn(List values) {
            addCriterion("END_TIME not in", values, "endTime");
            return this;
        }

        public Criteria andEndTimeBetween(Date value1, Date value2) {
            addCriterion("END_TIME between", value1, value2, "endTime");
            return this;
        }

        public Criteria andEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("END_TIME not between", value1, value2, "endTime");
            return this;
        }
    }
}