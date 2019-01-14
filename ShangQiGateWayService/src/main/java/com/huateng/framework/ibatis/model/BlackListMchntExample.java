package com.huateng.framework.ibatis.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlackListMchntExample {
    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database table TB_BLACKLIST_MCHNT
     *
     * @abatorgenerated Wed Nov 14 14:12:57 CST 2012
     */
    protected String orderByClause;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database table TB_BLACKLIST_MCHNT
     *
     * @abatorgenerated Wed Nov 14 14:12:57 CST 2012
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table TB_BLACKLIST_MCHNT
     *
     * @abatorgenerated Wed Nov 14 14:12:57 CST 2012
     */
    public BlackListMchntExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table TB_BLACKLIST_MCHNT
     *
     * @abatorgenerated Wed Nov 14 14:12:57 CST 2012
     */
    protected BlackListMchntExample(BlackListMchntExample example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table TB_BLACKLIST_MCHNT
     *
     * @abatorgenerated Wed Nov 14 14:12:57 CST 2012
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table TB_BLACKLIST_MCHNT
     *
     * @abatorgenerated Wed Nov 14 14:12:57 CST 2012
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table TB_BLACKLIST_MCHNT
     *
     * @abatorgenerated Wed Nov 14 14:12:57 CST 2012
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table TB_BLACKLIST_MCHNT
     *
     * @abatorgenerated Wed Nov 14 14:12:57 CST 2012
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table TB_BLACKLIST_MCHNT
     *
     * @abatorgenerated Wed Nov 14 14:12:57 CST 2012
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table TB_BLACKLIST_MCHNT
     *
     * @abatorgenerated Wed Nov 14 14:12:57 CST 2012
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table TB_BLACKLIST_MCHNT
     *
     * @abatorgenerated Wed Nov 14 14:12:57 CST 2012
     */
    public void clear() {
        oredCriteria.clear();
    }

    /**
     * This class was generated by Abator for iBATIS.
     * This class corresponds to the database table TB_BLACKLIST_MCHNT
     *
     * @abatorgenerated Wed Nov 14 14:12:57 CST 2012
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

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("condition", condition);
            map.put("value", value);
            criteriaWithSingleValue.add(map);
        }

        protected void addCriterion(String condition, List<? extends Object> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("condition", condition);
            map.put("values", values);
            criteriaWithListValue.add(map);
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            List<Object> list = new ArrayList<Object>();
            list.add(value1);
            list.add(value2);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("condition", condition);
            map.put("values", list);
            criteriaWithBetweenValue.add(map);
        }

        public Criteria andMchntNoIsNull() {
            addCriterion("MCHNT_NO is null");
            return this;
        }

        public Criteria andMchntNoIsNotNull() {
            addCriterion("MCHNT_NO is not null");
            return this;
        }

        public Criteria andMchntNoEqualTo(String value) {
            addCriterion("MCHNT_NO =", value, "mchntNo");
            return this;
        }

        public Criteria andMchntNoNotEqualTo(String value) {
            addCriterion("MCHNT_NO <>", value, "mchntNo");
            return this;
        }

        public Criteria andMchntNoGreaterThan(String value) {
            addCriterion("MCHNT_NO >", value, "mchntNo");
            return this;
        }

        public Criteria andMchntNoGreaterThanOrEqualTo(String value) {
            addCriterion("MCHNT_NO >=", value, "mchntNo");
            return this;
        }

        public Criteria andMchntNoLessThan(String value) {
            addCriterion("MCHNT_NO <", value, "mchntNo");
            return this;
        }

        public Criteria andMchntNoLessThanOrEqualTo(String value) {
            addCriterion("MCHNT_NO <=", value, "mchntNo");
            return this;
        }

        public Criteria andMchntNoLike(String value) {
            addCriterion("MCHNT_NO like", value, "mchntNo");
            return this;
        }

        public Criteria andMchntNoNotLike(String value) {
            addCriterion("MCHNT_NO not like", value, "mchntNo");
            return this;
        }

        public Criteria andMchntNoIn(List<String> values) {
            addCriterion("MCHNT_NO in", values, "mchntNo");
            return this;
        }

        public Criteria andMchntNoNotIn(List<String> values) {
            addCriterion("MCHNT_NO not in", values, "mchntNo");
            return this;
        }

        public Criteria andMchntNoBetween(String value1, String value2) {
            addCriterion("MCHNT_NO between", value1, value2, "mchntNo");
            return this;
        }

        public Criteria andMchntNoNotBetween(String value1, String value2) {
            addCriterion("MCHNT_NO not between", value1, value2, "mchntNo");
            return this;
        }

        public Criteria andMenoIsNull() {
            addCriterion("MENO is null");
            return this;
        }

        public Criteria andMenoIsNotNull() {
            addCriterion("MENO is not null");
            return this;
        }

        public Criteria andMenoEqualTo(String value) {
            addCriterion("MENO =", value, "meno");
            return this;
        }

        public Criteria andMenoNotEqualTo(String value) {
            addCriterion("MENO <>", value, "meno");
            return this;
        }

        public Criteria andMenoGreaterThan(String value) {
            addCriterion("MENO >", value, "meno");
            return this;
        }

        public Criteria andMenoGreaterThanOrEqualTo(String value) {
            addCriterion("MENO >=", value, "meno");
            return this;
        }

        public Criteria andMenoLessThan(String value) {
            addCriterion("MENO <", value, "meno");
            return this;
        }

        public Criteria andMenoLessThanOrEqualTo(String value) {
            addCriterion("MENO <=", value, "meno");
            return this;
        }

        public Criteria andMenoLike(String value) {
            addCriterion("MENO like", value, "meno");
            return this;
        }

        public Criteria andMenoNotLike(String value) {
            addCriterion("MENO not like", value, "meno");
            return this;
        }

        public Criteria andMenoIn(List<String> values) {
            addCriterion("MENO in", values, "meno");
            return this;
        }

        public Criteria andMenoNotIn(List<String> values) {
            addCriterion("MENO not in", values, "meno");
            return this;
        }

        public Criteria andMenoBetween(String value1, String value2) {
            addCriterion("MENO between", value1, value2, "meno");
            return this;
        }

        public Criteria andMenoNotBetween(String value1, String value2) {
            addCriterion("MENO not between", value1, value2, "meno");
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
            addCriterion("CREATE_USER not between", value1, value2, "createUser");
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
            addCriterion("CREATE_TIME not between", value1, value2, "createTime");
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
            addCriterion("MODIFY_USER not between", value1, value2, "modifyUser");
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
            addCriterion("MODIFY_TIME not between", value1, value2, "modifyTime");
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