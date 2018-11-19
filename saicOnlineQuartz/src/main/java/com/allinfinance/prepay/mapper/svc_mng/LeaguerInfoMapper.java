package com.allinfinance.prepay.mapper.svc_mng;

import com.allinfinance.prepay.model.LeaguerInfo;
import com.allinfinance.prepay.model.LeaguerInfoExample;
import com.allinfinance.prepay.model.LeaguerInfoKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LeaguerInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVC_MNG.TB_LEAGUER_INFO
     *
     * @mbggenerated Mon Jun 12 18:10:01 CST 2017
     */
    int countByExample(LeaguerInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVC_MNG.TB_LEAGUER_INFO
     *
     * @mbggenerated Mon Jun 12 18:10:01 CST 2017
     */
    int deleteByExample(LeaguerInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVC_MNG.TB_LEAGUER_INFO
     *
     * @mbggenerated Mon Jun 12 18:10:01 CST 2017
     */
    int deleteByPrimaryKey(LeaguerInfoKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVC_MNG.TB_LEAGUER_INFO
     *
     * @mbggenerated Mon Jun 12 18:10:01 CST 2017
     */
    int insert(LeaguerInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVC_MNG.TB_LEAGUER_INFO
     *
     * @mbggenerated Mon Jun 12 18:10:01 CST 2017
     */
    int insertSelective(LeaguerInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVC_MNG.TB_LEAGUER_INFO
     *
     * @mbggenerated Mon Jun 12 18:10:01 CST 2017
     */
    List<LeaguerInfo> selectByExample(LeaguerInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVC_MNG.TB_LEAGUER_INFO
     *
     * @mbggenerated Mon Jun 12 18:10:01 CST 2017
     */
    LeaguerInfo selectByPrimaryKey(LeaguerInfoKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVC_MNG.TB_LEAGUER_INFO
     *
     * @mbggenerated Mon Jun 12 18:10:01 CST 2017
     */
    int updateByExampleSelective(@Param("record") LeaguerInfo record, @Param("example") LeaguerInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVC_MNG.TB_LEAGUER_INFO
     *
     * @mbggenerated Mon Jun 12 18:10:01 CST 2017
     */
    int updateByExample(@Param("record") LeaguerInfo record, @Param("example") LeaguerInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVC_MNG.TB_LEAGUER_INFO
     *
     * @mbggenerated Mon Jun 12 18:10:01 CST 2017
     */
    int updateByPrimaryKeySelective(LeaguerInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVC_MNG.TB_LEAGUER_INFO
     *
     * @mbggenerated Mon Jun 12 18:10:01 CST 2017
     */
    int updateByPrimaryKey(LeaguerInfo record);
}