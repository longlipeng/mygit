package com.huateng.framework.ibatis.dao;

import com.huateng.framework.ibatis.model.TransType;
import com.huateng.framework.ibatis.model.TransTypeExample;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class TransTypeDAOImpl extends SqlMapClientDaoSupport implements TransTypeDAO {

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MNGDB.TB_TRANS_TYPE
     *
     * @abatorgenerated Mon Aug 06 14:47:24 CST 2012
     */
    public TransTypeDAOImpl() {
        super();
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MNGDB.TB_TRANS_TYPE
     *
     * @abatorgenerated Mon Aug 06 14:47:24 CST 2012
     */
    public void insert(TransType record) {
        getSqlMapClientTemplate().insert("TB_TRANS_TYPE.abatorgenerated_insert", record);
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MNGDB.TB_TRANS_TYPE
     *
     * @abatorgenerated Mon Aug 06 14:47:24 CST 2012
     */
    @SuppressWarnings("unchecked")
    public List<TransType> selectByExample(TransTypeExample example) {
        List<TransType> list = (List<TransType>) getSqlMapClientTemplate().queryForList("TB_TRANS_TYPE.abatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MNGDB.TB_TRANS_TYPE
     *
     * @abatorgenerated Mon Aug 06 14:47:24 CST 2012
     */
    public int deleteByExample(TransTypeExample example) {
        int rows = getSqlMapClientTemplate().delete("TB_TRANS_TYPE.abatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MNGDB.TB_TRANS_TYPE
     *
     * @abatorgenerated Mon Aug 06 14:47:24 CST 2012
     */
    public int countByExample(TransTypeExample example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("TB_TRANS_TYPE.abatorgenerated_countByExample", example);
        return count;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MNGDB.TB_TRANS_TYPE
     *
     * @abatorgenerated Mon Aug 06 14:47:24 CST 2012
     */
    public int updateByExampleSelective(TransType record, TransTypeExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("TB_TRANS_TYPE.abatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MNGDB.TB_TRANS_TYPE
     *
     * @abatorgenerated Mon Aug 06 14:47:24 CST 2012
     */
    public int updateByExample(TransType record, TransTypeExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("TB_TRANS_TYPE.abatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This class was generated by Abator for iBATIS.
     * This class corresponds to the database table MNGDB.TB_TRANS_TYPE
     *
     * @abatorgenerated Mon Aug 06 14:47:24 CST 2012
     */
    private static class UpdateByExampleParms extends TransTypeExample {
        private Object record;

        public UpdateByExampleParms(Object record, TransTypeExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}