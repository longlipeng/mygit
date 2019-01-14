/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: AccCardInfoExample.java
 * Author:   12073942
 * Date:     2013-8-6 上午11:45:49
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.huateng.framework.ibatis.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author LEO
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class AccCardInfoExample {
    /**
     * This field was generated by Abator for iBATIS. This field corresponds to the database table TBL_SAP_INFO
     * 
     * @abatorgenerated Tue Aug 06 10:52:07 CST 2013
     */
    protected String orderByClause;

    /**
     * This field was generated by Abator for iBATIS. This field corresponds to the database table TBL_SAP_INFO
     * 
     * @abatorgenerated Tue Aug 06 10:52:07 CST 2013
     */
    protected List oredCriteria;

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table TBL_SAP_INFO
     * 
     * @abatorgenerated Tue Aug 06 10:52:07 CST 2013
     */
    public AccCardInfoExample() {
        oredCriteria = new ArrayList();
    }

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table TBL_SAP_INFO
     * 
     * @abatorgenerated Tue Aug 06 10:52:07 CST 2013
     */
    protected AccCardInfoExample(AccCardInfoExample example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
    }

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table TBL_SAP_INFO
     * 
     * @abatorgenerated Tue Aug 06 10:52:07 CST 2013
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table TBL_SAP_INFO
     * 
     * @abatorgenerated Tue Aug 06 10:52:07 CST 2013
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table TBL_SAP_INFO
     * 
     * @abatorgenerated Tue Aug 06 10:52:07 CST 2013
     */
    public List getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table TBL_SAP_INFO
     * 
     * @abatorgenerated Tue Aug 06 10:52:07 CST 2013
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table TBL_SAP_INFO
     * 
     * @abatorgenerated Tue Aug 06 10:52:07 CST 2013
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table TBL_SAP_INFO
     * 
     * @abatorgenerated Tue Aug 06 10:52:07 CST 2013
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table TBL_SAP_INFO
     * 
     * @abatorgenerated Tue Aug 06 10:52:07 CST 2013
     */
    public void clear() {
        oredCriteria.clear();
    }

    /**
     * This class was generated by Abator for iBATIS. This class corresponds to the database table TBL_SAP_INFO
     * 
     * @abatorgenerated Tue Aug 06 10:52:07 CST 2013
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

       

        public Criteria andIssuerIdEqualTo(String value) {
            addCriterion("ISSUER_ID =", value, "issuerId");
            return this;
        }


        public Criteria andCardNoGreaterThanOrEqualTo(String value) {
            addCriterion("CARD_NO >=", value, "cardNo");
            return this;
        }


        public Criteria andCardNoLessThanOrEqualTo(String value) {
            addCriterion("CARD_NO <=", value, "cardNo");
            return this;
        }

        public Criteria andCardStatIn(List values) {
            addCriterion("CARD_STAT IN", values, "cardStat");
            return this;
        }

       
    }
}