package com.suning.svc.ibatis.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.suning.svc.ibatis.model.TradeItemTemp;
import com.suning.svc.ibatis.model.TradeItemTempExample;

public class TradeItemTempDAOImpl extends SqlMapClientDaoSupport implements TradeItemTempDAO {

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table CP_TRADE_ITEM_TEMP
     * 
     * @abatorgenerated Wed Nov 06 15:03:28 CST 2013
     */
    public TradeItemTempDAOImpl() {
        super();
    }

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table CP_TRADE_ITEM_TEMP
     * 
     * @abatorgenerated Wed Nov 06 15:03:28 CST 2013
     */
    public void insert(TradeItemTemp record) {
        getSqlMapClientTemplate().insert("CP_TRADE_ITEM_TEMP.abatorgenerated_insert", record);
    }

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table CP_TRADE_ITEM_TEMP
     * 
     * @abatorgenerated Wed Nov 06 15:03:28 CST 2013
     */
    public int updateByPrimaryKey(TradeItemTemp record) {
        int rows = getSqlMapClientTemplate().update("CP_TRADE_ITEM_TEMP.abatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table CP_TRADE_ITEM_TEMP
     * 
     * @abatorgenerated Wed Nov 06 15:03:28 CST 2013
     */
    public int updateByPrimaryKeySelective(TradeItemTemp record) {
        int rows = getSqlMapClientTemplate().update("CP_TRADE_ITEM_TEMP.abatorgenerated_updateByPrimaryKeySelective",
                record);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table CP_TRADE_ITEM_TEMP
     * 
     * @abatorgenerated Wed Nov 06 15:03:28 CST 2013
     */
    public List selectByExample(TradeItemTempExample example) {
        List list = getSqlMapClientTemplate().queryForList("CP_TRADE_ITEM_TEMP.abatorgenerated_selectByExample",
                example);
        return list;
    }

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table CP_TRADE_ITEM_TEMP
     * 
     * @abatorgenerated Wed Nov 06 15:03:28 CST 2013
     */
    public TradeItemTemp selectByPrimaryKey(Long id) {
        TradeItemTemp key = new TradeItemTemp();
        key.setId(id);
        TradeItemTemp record = (TradeItemTemp) getSqlMapClientTemplate().queryForObject(
                "CP_TRADE_ITEM_TEMP.abatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table CP_TRADE_ITEM_TEMP
     * 
     * @abatorgenerated Wed Nov 06 15:03:28 CST 2013
     */
    public int deleteByExample(TradeItemTempExample example) {
        int rows = getSqlMapClientTemplate().delete("CP_TRADE_ITEM_TEMP.abatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table CP_TRADE_ITEM_TEMP
     * 
     * @abatorgenerated Wed Nov 06 15:03:28 CST 2013
     */
    public int deleteByPrimaryKey(Long id) {
        TradeItemTemp key = new TradeItemTemp();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("CP_TRADE_ITEM_TEMP.abatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table CP_TRADE_ITEM_TEMP
     * 
     * @abatorgenerated Wed Nov 06 15:03:28 CST 2013
     */
    public int countByExample(TradeItemTempExample example) {
        Integer count = (Integer) getSqlMapClientTemplate().queryForObject(
                "CP_TRADE_ITEM_TEMP.abatorgenerated_countByExample", example);
        return count.intValue();
    }

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table CP_TRADE_ITEM_TEMP
     * 
     * @abatorgenerated Wed Nov 06 15:03:28 CST 2013
     */
    public int updateByExampleSelective(TradeItemTemp record, TradeItemTempExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("CP_TRADE_ITEM_TEMP.abatorgenerated_updateByExampleSelective",
                parms);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table CP_TRADE_ITEM_TEMP
     * 
     * @abatorgenerated Wed Nov 06 15:03:28 CST 2013
     */
    public int updateByExample(TradeItemTemp record, TradeItemTempExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("CP_TRADE_ITEM_TEMP.abatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This class was generated by Abator for iBATIS. This class corresponds to the database table CP_TRADE_ITEM_TEMP
     * 
     * @abatorgenerated Wed Nov 06 15:03:28 CST 2013
     */
    private static class UpdateByExampleParms extends TradeItemTempExample {
        private Object record;

        public UpdateByExampleParms(Object record, TradeItemTempExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}