package com.suning.svc.ibatis.dao;

import java.util.List;

import com.suning.svc.ibatis.model.DealBatch;
import com.suning.svc.ibatis.model.DealBatchExample;

public interface DealBatchDAO {

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table CP_DEAL_BATCH
     * 
     * @abatorgenerated Tue Oct 29 19:11:44 CST 2013
     */
    void insert(DealBatch record);

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table CP_DEAL_BATCH
     * 
     * @abatorgenerated Tue Oct 29 19:11:44 CST 2013
     */
    int updateByPrimaryKey(DealBatch record);

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table CP_DEAL_BATCH
     * 
     * @abatorgenerated Tue Oct 29 19:11:44 CST 2013
     */
    int updateByPrimaryKeySelective(DealBatch record);

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table CP_DEAL_BATCH
     * 
     * @abatorgenerated Tue Oct 29 19:11:44 CST 2013
     */
    List selectByExample(DealBatchExample example);

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table CP_DEAL_BATCH
     * 
     * @abatorgenerated Tue Oct 29 19:11:44 CST 2013
     */
    DealBatch selectByPrimaryKey(Long id);

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table CP_DEAL_BATCH
     * 
     * @abatorgenerated Tue Oct 29 19:11:44 CST 2013
     */
    int deleteByExample(DealBatchExample example);

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table CP_DEAL_BATCH
     * 
     * @abatorgenerated Tue Oct 29 19:11:44 CST 2013
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table CP_DEAL_BATCH
     * 
     * @abatorgenerated Tue Oct 29 19:11:44 CST 2013
     */
    int countByExample(DealBatchExample example);

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table CP_DEAL_BATCH
     * 
     * @abatorgenerated Tue Oct 29 19:11:44 CST 2013
     */
    int updateByExampleSelective(DealBatch record, DealBatchExample example);

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table CP_DEAL_BATCH
     * 
     * @abatorgenerated Tue Oct 29 19:11:44 CST 2013
     */
    int updateByExample(DealBatch record, DealBatchExample example);
}