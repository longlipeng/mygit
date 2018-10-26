package com.allinfinance.model;

import java.util.ArrayList;
import java.util.List;

public class TbDictInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TbDictInfoExample() {
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

        public Criteria andDictIdIsNull() {
            addCriterion("DICT_ID is null");
            return (Criteria) this;
        }

        public Criteria andDictIdIsNotNull() {
            addCriterion("DICT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andDictIdEqualTo(String value) {
            addCriterion("DICT_ID =", value, "dictId");
            return (Criteria) this;
        }

        public Criteria andDictIdNotEqualTo(String value) {
            addCriterion("DICT_ID <>", value, "dictId");
            return (Criteria) this;
        }

        public Criteria andDictIdGreaterThan(String value) {
            addCriterion("DICT_ID >", value, "dictId");
            return (Criteria) this;
        }

        public Criteria andDictIdGreaterThanOrEqualTo(String value) {
            addCriterion("DICT_ID >=", value, "dictId");
            return (Criteria) this;
        }

        public Criteria andDictIdLessThan(String value) {
            addCriterion("DICT_ID <", value, "dictId");
            return (Criteria) this;
        }

        public Criteria andDictIdLessThanOrEqualTo(String value) {
            addCriterion("DICT_ID <=", value, "dictId");
            return (Criteria) this;
        }

        public Criteria andDictIdLike(String value) {
            addCriterion("DICT_ID like", value, "dictId");
            return (Criteria) this;
        }

        public Criteria andDictIdNotLike(String value) {
            addCriterion("DICT_ID not like", value, "dictId");
            return (Criteria) this;
        }

        public Criteria andDictIdIn(List<String> values) {
            addCriterion("DICT_ID in", values, "dictId");
            return (Criteria) this;
        }

        public Criteria andDictIdNotIn(List<String> values) {
            addCriterion("DICT_ID not in", values, "dictId");
            return (Criteria) this;
        }

        public Criteria andDictIdBetween(String value1, String value2) {
            addCriterion("DICT_ID between", value1, value2, "dictId");
            return (Criteria) this;
        }

        public Criteria andDictIdNotBetween(String value1, String value2) {
            addCriterion("DICT_ID not between", value1, value2, "dictId");
            return (Criteria) this;
        }

        public Criteria andDictCodeIsNull() {
            addCriterion("DICT_CODE is null");
            return (Criteria) this;
        }

        public Criteria andDictCodeIsNotNull() {
            addCriterion("DICT_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andDictCodeEqualTo(String value) {
            addCriterion("DICT_CODE =", value, "dictCode");
            return (Criteria) this;
        }

        public Criteria andDictCodeNotEqualTo(String value) {
            addCriterion("DICT_CODE <>", value, "dictCode");
            return (Criteria) this;
        }

        public Criteria andDictCodeGreaterThan(String value) {
            addCriterion("DICT_CODE >", value, "dictCode");
            return (Criteria) this;
        }

        public Criteria andDictCodeGreaterThanOrEqualTo(String value) {
            addCriterion("DICT_CODE >=", value, "dictCode");
            return (Criteria) this;
        }

        public Criteria andDictCodeLessThan(String value) {
            addCriterion("DICT_CODE <", value, "dictCode");
            return (Criteria) this;
        }

        public Criteria andDictCodeLessThanOrEqualTo(String value) {
            addCriterion("DICT_CODE <=", value, "dictCode");
            return (Criteria) this;
        }

        public Criteria andDictCodeLike(String value) {
            addCriterion("DICT_CODE like", value, "dictCode");
            return (Criteria) this;
        }

        public Criteria andDictCodeNotLike(String value) {
            addCriterion("DICT_CODE not like", value, "dictCode");
            return (Criteria) this;
        }

        public Criteria andDictCodeIn(List<String> values) {
            addCriterion("DICT_CODE in", values, "dictCode");
            return (Criteria) this;
        }

        public Criteria andDictCodeNotIn(List<String> values) {
            addCriterion("DICT_CODE not in", values, "dictCode");
            return (Criteria) this;
        }

        public Criteria andDictCodeBetween(String value1, String value2) {
            addCriterion("DICT_CODE between", value1, value2, "dictCode");
            return (Criteria) this;
        }

        public Criteria andDictCodeNotBetween(String value1, String value2) {
            addCriterion("DICT_CODE not between", value1, value2, "dictCode");
            return (Criteria) this;
        }

        public Criteria andDictTypeCodeIsNull() {
            addCriterion("DICT_TYPE_CODE is null");
            return (Criteria) this;
        }

        public Criteria andDictTypeCodeIsNotNull() {
            addCriterion("DICT_TYPE_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andDictTypeCodeEqualTo(String value) {
            addCriterion("DICT_TYPE_CODE =", value, "dictTypeCode");
            return (Criteria) this;
        }

        public Criteria andDictTypeCodeNotEqualTo(String value) {
            addCriterion("DICT_TYPE_CODE <>", value, "dictTypeCode");
            return (Criteria) this;
        }

        public Criteria andDictTypeCodeGreaterThan(String value) {
            addCriterion("DICT_TYPE_CODE >", value, "dictTypeCode");
            return (Criteria) this;
        }

        public Criteria andDictTypeCodeGreaterThanOrEqualTo(String value) {
            addCriterion("DICT_TYPE_CODE >=", value, "dictTypeCode");
            return (Criteria) this;
        }

        public Criteria andDictTypeCodeLessThan(String value) {
            addCriterion("DICT_TYPE_CODE <", value, "dictTypeCode");
            return (Criteria) this;
        }

        public Criteria andDictTypeCodeLessThanOrEqualTo(String value) {
            addCriterion("DICT_TYPE_CODE <=", value, "dictTypeCode");
            return (Criteria) this;
        }

        public Criteria andDictTypeCodeLike(String value) {
            addCriterion("DICT_TYPE_CODE like", value, "dictTypeCode");
            return (Criteria) this;
        }

        public Criteria andDictTypeCodeNotLike(String value) {
            addCriterion("DICT_TYPE_CODE not like", value, "dictTypeCode");
            return (Criteria) this;
        }

        public Criteria andDictTypeCodeIn(List<String> values) {
            addCriterion("DICT_TYPE_CODE in", values, "dictTypeCode");
            return (Criteria) this;
        }

        public Criteria andDictTypeCodeNotIn(List<String> values) {
            addCriterion("DICT_TYPE_CODE not in", values, "dictTypeCode");
            return (Criteria) this;
        }

        public Criteria andDictTypeCodeBetween(String value1, String value2) {
            addCriterion("DICT_TYPE_CODE between", value1, value2, "dictTypeCode");
            return (Criteria) this;
        }

        public Criteria andDictTypeCodeNotBetween(String value1, String value2) {
            addCriterion("DICT_TYPE_CODE not between", value1, value2, "dictTypeCode");
            return (Criteria) this;
        }

        public Criteria andDictNameIsNull() {
            addCriterion("DICT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andDictNameIsNotNull() {
            addCriterion("DICT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andDictNameEqualTo(String value) {
            addCriterion("DICT_NAME =", value, "dictName");
            return (Criteria) this;
        }

        public Criteria andDictNameNotEqualTo(String value) {
            addCriterion("DICT_NAME <>", value, "dictName");
            return (Criteria) this;
        }

        public Criteria andDictNameGreaterThan(String value) {
            addCriterion("DICT_NAME >", value, "dictName");
            return (Criteria) this;
        }

        public Criteria andDictNameGreaterThanOrEqualTo(String value) {
            addCriterion("DICT_NAME >=", value, "dictName");
            return (Criteria) this;
        }

        public Criteria andDictNameLessThan(String value) {
            addCriterion("DICT_NAME <", value, "dictName");
            return (Criteria) this;
        }

        public Criteria andDictNameLessThanOrEqualTo(String value) {
            addCriterion("DICT_NAME <=", value, "dictName");
            return (Criteria) this;
        }

        public Criteria andDictNameLike(String value) {
            addCriterion("DICT_NAME like", value, "dictName");
            return (Criteria) this;
        }

        public Criteria andDictNameNotLike(String value) {
            addCriterion("DICT_NAME not like", value, "dictName");
            return (Criteria) this;
        }

        public Criteria andDictNameIn(List<String> values) {
            addCriterion("DICT_NAME in", values, "dictName");
            return (Criteria) this;
        }

        public Criteria andDictNameNotIn(List<String> values) {
            addCriterion("DICT_NAME not in", values, "dictName");
            return (Criteria) this;
        }

        public Criteria andDictNameBetween(String value1, String value2) {
            addCriterion("DICT_NAME between", value1, value2, "dictName");
            return (Criteria) this;
        }

        public Criteria andDictNameNotBetween(String value1, String value2) {
            addCriterion("DICT_NAME not between", value1, value2, "dictName");
            return (Criteria) this;
        }

        public Criteria andDictShortNameIsNull() {
            addCriterion("DICT_SHORT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andDictShortNameIsNotNull() {
            addCriterion("DICT_SHORT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andDictShortNameEqualTo(String value) {
            addCriterion("DICT_SHORT_NAME =", value, "dictShortName");
            return (Criteria) this;
        }

        public Criteria andDictShortNameNotEqualTo(String value) {
            addCriterion("DICT_SHORT_NAME <>", value, "dictShortName");
            return (Criteria) this;
        }

        public Criteria andDictShortNameGreaterThan(String value) {
            addCriterion("DICT_SHORT_NAME >", value, "dictShortName");
            return (Criteria) this;
        }

        public Criteria andDictShortNameGreaterThanOrEqualTo(String value) {
            addCriterion("DICT_SHORT_NAME >=", value, "dictShortName");
            return (Criteria) this;
        }

        public Criteria andDictShortNameLessThan(String value) {
            addCriterion("DICT_SHORT_NAME <", value, "dictShortName");
            return (Criteria) this;
        }

        public Criteria andDictShortNameLessThanOrEqualTo(String value) {
            addCriterion("DICT_SHORT_NAME <=", value, "dictShortName");
            return (Criteria) this;
        }

        public Criteria andDictShortNameLike(String value) {
            addCriterion("DICT_SHORT_NAME like", value, "dictShortName");
            return (Criteria) this;
        }

        public Criteria andDictShortNameNotLike(String value) {
            addCriterion("DICT_SHORT_NAME not like", value, "dictShortName");
            return (Criteria) this;
        }

        public Criteria andDictShortNameIn(List<String> values) {
            addCriterion("DICT_SHORT_NAME in", values, "dictShortName");
            return (Criteria) this;
        }

        public Criteria andDictShortNameNotIn(List<String> values) {
            addCriterion("DICT_SHORT_NAME not in", values, "dictShortName");
            return (Criteria) this;
        }

        public Criteria andDictShortNameBetween(String value1, String value2) {
            addCriterion("DICT_SHORT_NAME between", value1, value2, "dictShortName");
            return (Criteria) this;
        }

        public Criteria andDictShortNameNotBetween(String value1, String value2) {
            addCriterion("DICT_SHORT_NAME not between", value1, value2, "dictShortName");
            return (Criteria) this;
        }

        public Criteria andDictEnglishNameIsNull() {
            addCriterion("DICT_ENGLISH_NAME is null");
            return (Criteria) this;
        }

        public Criteria andDictEnglishNameIsNotNull() {
            addCriterion("DICT_ENGLISH_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andDictEnglishNameEqualTo(String value) {
            addCriterion("DICT_ENGLISH_NAME =", value, "dictEnglishName");
            return (Criteria) this;
        }

        public Criteria andDictEnglishNameNotEqualTo(String value) {
            addCriterion("DICT_ENGLISH_NAME <>", value, "dictEnglishName");
            return (Criteria) this;
        }

        public Criteria andDictEnglishNameGreaterThan(String value) {
            addCriterion("DICT_ENGLISH_NAME >", value, "dictEnglishName");
            return (Criteria) this;
        }

        public Criteria andDictEnglishNameGreaterThanOrEqualTo(String value) {
            addCriterion("DICT_ENGLISH_NAME >=", value, "dictEnglishName");
            return (Criteria) this;
        }

        public Criteria andDictEnglishNameLessThan(String value) {
            addCriterion("DICT_ENGLISH_NAME <", value, "dictEnglishName");
            return (Criteria) this;
        }

        public Criteria andDictEnglishNameLessThanOrEqualTo(String value) {
            addCriterion("DICT_ENGLISH_NAME <=", value, "dictEnglishName");
            return (Criteria) this;
        }

        public Criteria andDictEnglishNameLike(String value) {
            addCriterion("DICT_ENGLISH_NAME like", value, "dictEnglishName");
            return (Criteria) this;
        }

        public Criteria andDictEnglishNameNotLike(String value) {
            addCriterion("DICT_ENGLISH_NAME not like", value, "dictEnglishName");
            return (Criteria) this;
        }

        public Criteria andDictEnglishNameIn(List<String> values) {
            addCriterion("DICT_ENGLISH_NAME in", values, "dictEnglishName");
            return (Criteria) this;
        }

        public Criteria andDictEnglishNameNotIn(List<String> values) {
            addCriterion("DICT_ENGLISH_NAME not in", values, "dictEnglishName");
            return (Criteria) this;
        }

        public Criteria andDictEnglishNameBetween(String value1, String value2) {
            addCriterion("DICT_ENGLISH_NAME between", value1, value2, "dictEnglishName");
            return (Criteria) this;
        }

        public Criteria andDictEnglishNameNotBetween(String value1, String value2) {
            addCriterion("DICT_ENGLISH_NAME not between", value1, value2, "dictEnglishName");
            return (Criteria) this;
        }

        public Criteria andDictStateIsNull() {
            addCriterion("DICT_STATE is null");
            return (Criteria) this;
        }

        public Criteria andDictStateIsNotNull() {
            addCriterion("DICT_STATE is not null");
            return (Criteria) this;
        }

        public Criteria andDictStateEqualTo(String value) {
            addCriterion("DICT_STATE =", value, "dictState");
            return (Criteria) this;
        }

        public Criteria andDictStateNotEqualTo(String value) {
            addCriterion("DICT_STATE <>", value, "dictState");
            return (Criteria) this;
        }

        public Criteria andDictStateGreaterThan(String value) {
            addCriterion("DICT_STATE >", value, "dictState");
            return (Criteria) this;
        }

        public Criteria andDictStateGreaterThanOrEqualTo(String value) {
            addCriterion("DICT_STATE >=", value, "dictState");
            return (Criteria) this;
        }

        public Criteria andDictStateLessThan(String value) {
            addCriterion("DICT_STATE <", value, "dictState");
            return (Criteria) this;
        }

        public Criteria andDictStateLessThanOrEqualTo(String value) {
            addCriterion("DICT_STATE <=", value, "dictState");
            return (Criteria) this;
        }

        public Criteria andDictStateLike(String value) {
            addCriterion("DICT_STATE like", value, "dictState");
            return (Criteria) this;
        }

        public Criteria andDictStateNotLike(String value) {
            addCriterion("DICT_STATE not like", value, "dictState");
            return (Criteria) this;
        }

        public Criteria andDictStateIn(List<String> values) {
            addCriterion("DICT_STATE in", values, "dictState");
            return (Criteria) this;
        }

        public Criteria andDictStateNotIn(List<String> values) {
            addCriterion("DICT_STATE not in", values, "dictState");
            return (Criteria) this;
        }

        public Criteria andDictStateBetween(String value1, String value2) {
            addCriterion("DICT_STATE between", value1, value2, "dictState");
            return (Criteria) this;
        }

        public Criteria andDictStateNotBetween(String value1, String value2) {
            addCriterion("DICT_STATE not between", value1, value2, "dictState");
            return (Criteria) this;
        }

        public Criteria andFatherDictCodeIsNull() {
            addCriterion("FATHER_DICT_CODE is null");
            return (Criteria) this;
        }

        public Criteria andFatherDictCodeIsNotNull() {
            addCriterion("FATHER_DICT_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andFatherDictCodeEqualTo(String value) {
            addCriterion("FATHER_DICT_CODE =", value, "fatherDictCode");
            return (Criteria) this;
        }

        public Criteria andFatherDictCodeNotEqualTo(String value) {
            addCriterion("FATHER_DICT_CODE <>", value, "fatherDictCode");
            return (Criteria) this;
        }

        public Criteria andFatherDictCodeGreaterThan(String value) {
            addCriterion("FATHER_DICT_CODE >", value, "fatherDictCode");
            return (Criteria) this;
        }

        public Criteria andFatherDictCodeGreaterThanOrEqualTo(String value) {
            addCriterion("FATHER_DICT_CODE >=", value, "fatherDictCode");
            return (Criteria) this;
        }

        public Criteria andFatherDictCodeLessThan(String value) {
            addCriterion("FATHER_DICT_CODE <", value, "fatherDictCode");
            return (Criteria) this;
        }

        public Criteria andFatherDictCodeLessThanOrEqualTo(String value) {
            addCriterion("FATHER_DICT_CODE <=", value, "fatherDictCode");
            return (Criteria) this;
        }

        public Criteria andFatherDictCodeLike(String value) {
            addCriterion("FATHER_DICT_CODE like", value, "fatherDictCode");
            return (Criteria) this;
        }

        public Criteria andFatherDictCodeNotLike(String value) {
            addCriterion("FATHER_DICT_CODE not like", value, "fatherDictCode");
            return (Criteria) this;
        }

        public Criteria andFatherDictCodeIn(List<String> values) {
            addCriterion("FATHER_DICT_CODE in", values, "fatherDictCode");
            return (Criteria) this;
        }

        public Criteria andFatherDictCodeNotIn(List<String> values) {
            addCriterion("FATHER_DICT_CODE not in", values, "fatherDictCode");
            return (Criteria) this;
        }

        public Criteria andFatherDictCodeBetween(String value1, String value2) {
            addCriterion("FATHER_DICT_CODE between", value1, value2, "fatherDictCode");
            return (Criteria) this;
        }

        public Criteria andFatherDictCodeNotBetween(String value1, String value2) {
            addCriterion("FATHER_DICT_CODE not between", value1, value2, "fatherDictCode");
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