package com.allinfinance.prepay.mapper.svc_mng;

import com.allinfinance.prepay.model.ProdFaceValue;
import com.allinfinance.prepay.model.ProdFaceValueExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProdFaceValueMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVC_MNG.TB_PROD_FACE_VALUE
     *
     * @mbggenerated Fri Jul 01 15:50:52 CST 2016
     */
    int countByExample(ProdFaceValueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVC_MNG.TB_PROD_FACE_VALUE
     *
     * @mbggenerated Fri Jul 01 15:50:52 CST 2016
     */
    int deleteByExample(ProdFaceValueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVC_MNG.TB_PROD_FACE_VALUE
     *
     * @mbggenerated Fri Jul 01 15:50:52 CST 2016
     */
    int insert(ProdFaceValue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVC_MNG.TB_PROD_FACE_VALUE
     *
     * @mbggenerated Fri Jul 01 15:50:52 CST 2016
     */
    int insertSelective(ProdFaceValue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVC_MNG.TB_PROD_FACE_VALUE
     *
     * @mbggenerated Fri Jul 01 15:50:52 CST 2016
     */
    List<ProdFaceValue> selectByExample(ProdFaceValueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVC_MNG.TB_PROD_FACE_VALUE
     *
     * @mbggenerated Fri Jul 01 15:50:52 CST 2016
     */
    int updateByExampleSelective(@Param("record") ProdFaceValue record, @Param("example") ProdFaceValueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVC_MNG.TB_PROD_FACE_VALUE
     *
     * @mbggenerated Fri Jul 01 15:50:52 CST 2016
     */
    int updateByExample(@Param("record") ProdFaceValue record, @Param("example") ProdFaceValueExample example);
}