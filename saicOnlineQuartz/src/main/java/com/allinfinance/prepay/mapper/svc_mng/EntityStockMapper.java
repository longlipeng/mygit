package com.allinfinance.prepay.mapper.svc_mng;

import com.allinfinance.prepay.model.EntityStock;
import com.allinfinance.prepay.model.EntityStockExample;
import com.allinfinance.prepay.model.SellOrderCardList;
import com.allinfinance.univer.seller.order.dto.OrderReadyDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface EntityStockMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVC_MNG.TB_ENTITY_STOCK
     *
     * @mbggenerated Tue Apr 26 10:50:23 CST 2016
     */
    int countByExample(EntityStockExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVC_MNG.TB_ENTITY_STOCK
     *
     * @mbggenerated Tue Apr 26 10:50:23 CST 2016
     */
    int deleteByExample(EntityStockExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVC_MNG.TB_ENTITY_STOCK
     *
     * @mbggenerated Tue Apr 26 10:50:23 CST 2016
     */
    int insert(EntityStock record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVC_MNG.TB_ENTITY_STOCK
     *
     * @mbggenerated Tue Apr 26 10:50:23 CST 2016
     */
    int insertSelective(EntityStock record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVC_MNG.TB_ENTITY_STOCK
     *
     * @mbggenerated Tue Apr 26 10:50:23 CST 2016
     */
    List<EntityStock> selectByExample(EntityStockExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVC_MNG.TB_ENTITY_STOCK
     *
     * @mbggenerated Tue Apr 26 10:50:23 CST 2016
     */
    int updateByExampleSelective(@Param("record") EntityStock record, @Param("example") EntityStockExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVC_MNG.TB_ENTITY_STOCK
     *
     * @mbggenerated Tue Apr 26 10:50:23 CST 2016
     */
    int updateByExample(@Param("record") EntityStock record, @Param("example") EntityStockExample example);
    
    
    List<HashMap<String, String>> selectCardNoforOrderReady(OrderReadyDTO orderReadyDTO);
    
    List<HashMap<String, String>> selectCardNoforOrderReadySign(OrderReadyDTO orderReadyDTO);
    
    List<SellOrderCardList> selectCardDetailFororderReadyByFixDetail(OrderReadyDTO orderReadyDTO);
    
    List<HashMap<String, Object>> selectStockByProductId(OrderReadyDTO orderReadyDTO);
}