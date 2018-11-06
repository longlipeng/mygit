package com.huateng.framework.ibatis.dao;

import com.huateng.framework.ibatis.model.CardholderEnterPrise;
import com.huateng.framework.ibatis.model.CardholderEnterPriseExample;
import com.huateng.framework.ibatis.model.CardholderEnterPriseKey;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class CardholderEnterPriseDAOImpl extends SqlMapClientDaoSupport implements CardholderEnterPriseDAO {

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table TB_CARDHOLDER_ENTERPRISE
     * @ibatorgenerated  Thu Aug 03 18:20:15 CST 2017
     */
    public CardholderEnterPriseDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table TB_CARDHOLDER_ENTERPRISE
     * @ibatorgenerated  Thu Aug 03 18:20:15 CST 2017
     */
    public int countByExample(CardholderEnterPriseExample example) {
        Integer count = (Integer) getSqlMapClientTemplate().queryForObject(
                "TB_CARDHOLDER_ENTERPRISE.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table TB_CARDHOLDER_ENTERPRISE
     * @ibatorgenerated  Thu Aug 03 18:20:15 CST 2017
     */
    public int deleteByExample(CardholderEnterPriseExample example) {
        int rows = getSqlMapClientTemplate().delete(
                "TB_CARDHOLDER_ENTERPRISE.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table TB_CARDHOLDER_ENTERPRISE
     * @ibatorgenerated  Thu Aug 03 18:20:15 CST 2017
     */
    public int deleteByPrimaryKey(CardholderEnterPriseKey key) {
        int rows = getSqlMapClientTemplate().delete(
                "TB_CARDHOLDER_ENTERPRISE.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table TB_CARDHOLDER_ENTERPRISE
     * @ibatorgenerated  Thu Aug 03 18:20:15 CST 2017
     */
    public void insert(CardholderEnterPrise record) {
        getSqlMapClientTemplate().insert("TB_CARDHOLDER_ENTERPRISE.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table TB_CARDHOLDER_ENTERPRISE
     * @ibatorgenerated  Thu Aug 03 18:20:15 CST 2017
     */
    public void insertSelective(CardholderEnterPrise record) {
        getSqlMapClientTemplate().insert("TB_CARDHOLDER_ENTERPRISE.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table TB_CARDHOLDER_ENTERPRISE
     * @ibatorgenerated  Thu Aug 03 18:20:15 CST 2017
     */
    public List selectByExample(CardholderEnterPriseExample example) {
        List list = getSqlMapClientTemplate().queryForList(
                "TB_CARDHOLDER_ENTERPRISE.ibatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table TB_CARDHOLDER_ENTERPRISE
     * @ibatorgenerated  Thu Aug 03 18:20:15 CST 2017
     */
    public CardholderEnterPrise selectByPrimaryKey(CardholderEnterPriseKey key) {
        CardholderEnterPrise record = (CardholderEnterPrise) getSqlMapClientTemplate().queryForObject(
                "TB_CARDHOLDER_ENTERPRISE.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table TB_CARDHOLDER_ENTERPRISE
     * @ibatorgenerated  Thu Aug 03 18:20:15 CST 2017
     */
    public int updateByExampleSelective(CardholderEnterPrise record, CardholderEnterPriseExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update(
                "TB_CARDHOLDER_ENTERPRISE.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table TB_CARDHOLDER_ENTERPRISE
     * @ibatorgenerated  Thu Aug 03 18:20:15 CST 2017
     */
    public int updateByExample(CardholderEnterPrise record, CardholderEnterPriseExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update(
                "TB_CARDHOLDER_ENTERPRISE.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table TB_CARDHOLDER_ENTERPRISE
     * @ibatorgenerated  Thu Aug 03 18:20:15 CST 2017
     */
    public int updateByPrimaryKeySelective(CardholderEnterPrise record) {
        int rows = getSqlMapClientTemplate().update(
                "TB_CARDHOLDER_ENTERPRISE.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table TB_CARDHOLDER_ENTERPRISE
     * @ibatorgenerated  Thu Aug 03 18:20:15 CST 2017
     */
    public int updateByPrimaryKey(CardholderEnterPrise record) {
        int rows = getSqlMapClientTemplate().update(
                "TB_CARDHOLDER_ENTERPRISE.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This class was generated by Apache iBATIS ibator. This class corresponds to the database table TB_CARDHOLDER_ENTERPRISE
     * @ibatorgenerated  Thu Aug 03 18:20:15 CST 2017
     */
    private static class UpdateByExampleParms extends CardholderEnterPriseExample {
        private Object record;

        public UpdateByExampleParms(Object record, CardholderEnterPriseExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}