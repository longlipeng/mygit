package com.allinfinance.prepay.model;

import java.util.ArrayList;
import java.util.List;

public class TbOrderResourceExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TbOrderResourceExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andOrderIdIsNull() {
            addCriterion("ORDER_ID is null");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNotNull() {
            addCriterion("ORDER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andOrderIdEqualTo(String value) {
            addCriterion("ORDER_ID =", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotEqualTo(String value) {
            addCriterion("ORDER_ID <>", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThan(String value) {
            addCriterion("ORDER_ID >", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("ORDER_ID >=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThan(String value) {
            addCriterion("ORDER_ID <", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThanOrEqualTo(String value) {
            addCriterion("ORDER_ID <=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLike(String value) {
            addCriterion("ORDER_ID like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotLike(String value) {
            addCriterion("ORDER_ID not like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIn(List<String> values) {
            addCriterion("ORDER_ID in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotIn(List<String> values) {
            addCriterion("ORDER_ID not in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdBetween(String value1, String value2) {
            addCriterion("ORDER_ID between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotBetween(String value1, String value2) {
            addCriterion("ORDER_ID not between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andResourceIsNull() {
            addCriterion("RESOURCE is null");
            return (Criteria) this;
        }

        public Criteria andResourceIsNotNull() {
            addCriterion("RESOURCE is not null");
            return (Criteria) this;
        }

        public Criteria andResourceEqualTo(String value) {
            addCriterion("RESOURCE =", value, "resource");
            return (Criteria) this;
        }

        public Criteria andResourceNotEqualTo(String value) {
            addCriterion("RESOURCE <>", value, "resource");
            return (Criteria) this;
        }

        public Criteria andResourceGreaterThan(String value) {
            addCriterion("RESOURCE >", value, "resource");
            return (Criteria) this;
        }

        public Criteria andResourceGreaterThanOrEqualTo(String value) {
            addCriterion("RESOURCE >=", value, "resource");
            return (Criteria) this;
        }

        public Criteria andResourceLessThan(String value) {
            addCriterion("RESOURCE <", value, "resource");
            return (Criteria) this;
        }

        public Criteria andResourceLessThanOrEqualTo(String value) {
            addCriterion("RESOURCE <=", value, "resource");
            return (Criteria) this;
        }

        public Criteria andResourceLike(String value) {
            addCriterion("RESOURCE like", value, "resource");
            return (Criteria) this;
        }

        public Criteria andResourceNotLike(String value) {
            addCriterion("RESOURCE not like", value, "resource");
            return (Criteria) this;
        }

        public Criteria andResourceIn(List<String> values) {
            addCriterion("RESOURCE in", values, "resource");
            return (Criteria) this;
        }

        public Criteria andResourceNotIn(List<String> values) {
            addCriterion("RESOURCE not in", values, "resource");
            return (Criteria) this;
        }

        public Criteria andResourceBetween(String value1, String value2) {
            addCriterion("RESOURCE between", value1, value2, "resource");
            return (Criteria) this;
        }

        public Criteria andResourceNotBetween(String value1, String value2) {
            addCriterion("RESOURCE not between", value1, value2, "resource");
            return (Criteria) this;
        }

        public Criteria andRes1IsNull() {
            addCriterion("RES1 is null");
            return (Criteria) this;
        }

        public Criteria andRes1IsNotNull() {
            addCriterion("RES1 is not null");
            return (Criteria) this;
        }

        public Criteria andRes1EqualTo(String value) {
            addCriterion("RES1 =", value, "res1");
            return (Criteria) this;
        }

        public Criteria andRes1NotEqualTo(String value) {
            addCriterion("RES1 <>", value, "res1");
            return (Criteria) this;
        }

        public Criteria andRes1GreaterThan(String value) {
            addCriterion("RES1 >", value, "res1");
            return (Criteria) this;
        }

        public Criteria andRes1GreaterThanOrEqualTo(String value) {
            addCriterion("RES1 >=", value, "res1");
            return (Criteria) this;
        }

        public Criteria andRes1LessThan(String value) {
            addCriterion("RES1 <", value, "res1");
            return (Criteria) this;
        }

        public Criteria andRes1LessThanOrEqualTo(String value) {
            addCriterion("RES1 <=", value, "res1");
            return (Criteria) this;
        }

        public Criteria andRes1Like(String value) {
            addCriterion("RES1 like", value, "res1");
            return (Criteria) this;
        }

        public Criteria andRes1NotLike(String value) {
            addCriterion("RES1 not like", value, "res1");
            return (Criteria) this;
        }

        public Criteria andRes1In(List<String> values) {
            addCriterion("RES1 in", values, "res1");
            return (Criteria) this;
        }

        public Criteria andRes1NotIn(List<String> values) {
            addCriterion("RES1 not in", values, "res1");
            return (Criteria) this;
        }

        public Criteria andRes1Between(String value1, String value2) {
            addCriterion("RES1 between", value1, value2, "res1");
            return (Criteria) this;
        }

        public Criteria andRes1NotBetween(String value1, String value2) {
            addCriterion("RES1 not between", value1, value2, "res1");
            return (Criteria) this;
        }

        public Criteria andRes2IsNull() {
            addCriterion("RES2 is null");
            return (Criteria) this;
        }

        public Criteria andRes2IsNotNull() {
            addCriterion("RES2 is not null");
            return (Criteria) this;
        }

        public Criteria andRes2EqualTo(String value) {
            addCriterion("RES2 =", value, "res2");
            return (Criteria) this;
        }

        public Criteria andRes2NotEqualTo(String value) {
            addCriterion("RES2 <>", value, "res2");
            return (Criteria) this;
        }

        public Criteria andRes2GreaterThan(String value) {
            addCriterion("RES2 >", value, "res2");
            return (Criteria) this;
        }

        public Criteria andRes2GreaterThanOrEqualTo(String value) {
            addCriterion("RES2 >=", value, "res2");
            return (Criteria) this;
        }

        public Criteria andRes2LessThan(String value) {
            addCriterion("RES2 <", value, "res2");
            return (Criteria) this;
        }

        public Criteria andRes2LessThanOrEqualTo(String value) {
            addCriterion("RES2 <=", value, "res2");
            return (Criteria) this;
        }

        public Criteria andRes2Like(String value) {
            addCriterion("RES2 like", value, "res2");
            return (Criteria) this;
        }

        public Criteria andRes2NotLike(String value) {
            addCriterion("RES2 not like", value, "res2");
            return (Criteria) this;
        }

        public Criteria andRes2In(List<String> values) {
            addCriterion("RES2 in", values, "res2");
            return (Criteria) this;
        }

        public Criteria andRes2NotIn(List<String> values) {
            addCriterion("RES2 not in", values, "res2");
            return (Criteria) this;
        }

        public Criteria andRes2Between(String value1, String value2) {
            addCriterion("RES2 between", value1, value2, "res2");
            return (Criteria) this;
        }

        public Criteria andRes2NotBetween(String value1, String value2) {
            addCriterion("RES2 not between", value1, value2, "res2");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}