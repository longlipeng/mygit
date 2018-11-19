package com.allinfinance.prepay.mapper.svc_mng;

import com.allinfinance.prepay.model.BatchSum;
import com.allinfinance.prepay.model.BatchSumExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BatchSumMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVC_MNG.TBL_ACC_BATCH_SUM
     *
     * @mbggenerated Fri May 06 09:14:11 CST 2016
     */
    int countByExample(BatchSumExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVC_MNG.TBL_ACC_BATCH_SUM
     *
     * @mbggenerated Fri May 06 09:14:11 CST 2016
     */
    int deleteByExample(BatchSumExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVC_MNG.TBL_ACC_BATCH_SUM
     *
     * @mbggenerated Fri May 06 09:14:11 CST 2016
     */
    int deleteByPrimaryKey(String batchNo);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVC_MNG.TBL_ACC_BATCH_SUM
     *
     * @mbggenerated Fri May 06 09:14:11 CST 2016
     */
    int insert(BatchSum record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVC_MNG.TBL_ACC_BATCH_SUM
     *
     * @mbggenerated Fri May 06 09:14:11 CST 2016
     */
    int insertSelective(BatchSum record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVC_MNG.TBL_ACC_BATCH_SUM
     *
     * @mbggenerated Fri May 06 09:14:11 CST 2016
     */
    List<BatchSum> selectByExample(BatchSumExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVC_MNG.TBL_ACC_BATCH_SUM
     *
     * @mbggenerated Fri May 06 09:14:11 CST 2016
     */
    BatchSum selectByPrimaryKey(String batchNo);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVC_MNG.TBL_ACC_BATCH_SUM
     *
     * @mbggenerated Fri May 06 09:14:11 CST 2016
     */
    int updateByExampleSelective(@Param("record") BatchSum record, @Param("example") BatchSumExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVC_MNG.TBL_ACC_BATCH_SUM
     *
     * @mbggenerated Fri May 06 09:14:11 CST 2016
     */
    int updateByExample(@Param("record") BatchSum record, @Param("example") BatchSumExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVC_MNG.TBL_ACC_BATCH_SUM
     *
     * @mbggenerated Fri May 06 09:14:11 CST 2016
     */
    int updateByPrimaryKeySelective(BatchSum record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVC_MNG.TBL_ACC_BATCH_SUM
     *
     * @mbggenerated Fri May 06 09:14:11 CST 2016
     */
    int updateByPrimaryKey(BatchSum record);
}