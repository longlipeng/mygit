package com.suning.svc.ibatis.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.suning.svc.ibatis.model.ConsumeOrder;
import com.suning.svc.ibatis.model.ConsumeOrderExample;

public class ConsumeOrderDAOImpl extends SqlMapClientDaoSupport implements ConsumeOrderDAO {

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table CP_CONSUME_ORDER
     * 
     * @abatorgenerated Mon Nov 04 20:17:09 CST 2013
     */
    public ConsumeOrderDAOImpl() {
        super();
    }

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table CP_CONSUME_ORDER
     * 
     * @abatorgenerated Mon Nov 04 20:17:09 CST 2013
     */
    public void insert(ConsumeOrder record) {
        getSqlMapClientTemplate().insert("CP_CONSUME_ORDER.abatorgenerated_insert", record);
    }

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table CP_CONSUME_ORDER
     * 
     * @abatorgenerated Mon Nov 04 20:17:09 CST 2013
     */
    public int updateByPrimaryKey(ConsumeOrder record) {
        int rows = getSqlMapClientTemplate().update("CP_CONSUME_ORDER.abatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table CP_CONSUME_ORDER
     * 
     * @abatorgenerated Mon Nov 04 20:17:09 CST 2013
     */
    public int updateByPrimaryKeySelective(ConsumeOrder record) {
        int rows = getSqlMapClientTemplate().update("CP_CONSUME_ORDER.abatorgenerated_updateByPrimaryKeySelective",
                record);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table CP_CONSUME_ORDER
     * 
     * @abatorgenerated Mon Nov 04 20:17:09 CST 2013
     */
    public List selectByExample(ConsumeOrderExample example) {
        List list = getSqlMapClientTemplate().queryForList("CP_CONSUME_ORDER.abatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table CP_CONSUME_ORDER
     * 
     * @abatorgenerated Mon Nov 04 20:17:09 CST 2013
     */
    public ConsumeOrder selectByPrimaryKey(Long id) {
        ConsumeOrder key = new ConsumeOrder();
        key.setId(id);
        ConsumeOrder record = (ConsumeOrder) getSqlMapClientTemplate().queryForObject(
                "CP_CONSUME_ORDER.abatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table CP_CONSUME_ORDER
     * 
     * @abatorgenerated Mon Nov 04 20:17:09 CST 2013
     */
    public int deleteByExample(ConsumeOrderExample example) {
        int rows = getSqlMapClientTemplate().delete("CP_CONSUME_ORDER.abatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table CP_CONSUME_ORDER
     * 
     * @abatorgenerated Mon Nov 04 20:17:09 CST 2013
     */
    public int deleteByPrimaryKey(Long id) {
        ConsumeOrder key = new ConsumeOrder();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("CP_CONSUME_ORDER.abatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table CP_CONSUME_ORDER
     * 
     * @abatorgenerated Mon Nov 04 20:17:09 CST 2013
     */
    public int countByExample(ConsumeOrderExample example) {
        Integer count = (Integer) getSqlMapClientTemplate().queryForObject(
                "CP_CONSUME_ORDER.abatorgenerated_countByExample", example);
        return count.intValue();
    }

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table CP_CONSUME_ORDER
     * 
     * @abatorgenerated Mon Nov 04 20:17:09 CST 2013
     */
    public int updateByExampleSelective(ConsumeOrder record, ConsumeOrderExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("CP_CONSUME_ORDER.abatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table CP_CONSUME_ORDER
     * 
     * @abatorgenerated Mon Nov 04 20:17:09 CST 2013
     */
    public int updateByExample(ConsumeOrder record, ConsumeOrderExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("CP_CONSUME_ORDER.abatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This class was generated by Abator for iBATIS. This class corresponds to the database table CP_CONSUME_ORDER
     * 
     * @abatorgenerated Mon Nov 04 20:17:09 CST 2013
     */
    private static class UpdateByExampleParms extends ConsumeOrderExample {
        private Object record;

        public UpdateByExampleParms(Object record, ConsumeOrderExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}