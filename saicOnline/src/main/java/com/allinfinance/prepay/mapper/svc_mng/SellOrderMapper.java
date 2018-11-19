package com.allinfinance.prepay.mapper.svc_mng;

import com.allinfinance.prepay.model.SellOrder;
import com.allinfinance.prepay.model.SellOrderExample;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SellOrderMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVC_MNG.TB_SELL_ORDER
     *
     * @mbggenerated Tue Apr 26 16:41:37 CST 2016
     */
    int countByExample(SellOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVC_MNG.TB_SELL_ORDER
     *
     * @mbggenerated Tue Apr 26 16:41:37 CST 2016
     */
    int deleteByExample(SellOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVC_MNG.TB_SELL_ORDER
     *
     * @mbggenerated Tue Apr 26 16:41:37 CST 2016
     */
    int insert(SellOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVC_MNG.TB_SELL_ORDER
     *
     * @mbggenerated Tue Apr 26 16:41:37 CST 2016
     */
    int insertSelective(SellOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVC_MNG.TB_SELL_ORDER
     *
     * @mbggenerated Tue Apr 26 16:41:37 CST 2016
     */
    List<SellOrder> selectByExample(SellOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVC_MNG.TB_SELL_ORDER
     *
     * @mbggenerated Tue Apr 26 16:41:37 CST 2016
     */
    int updateByExampleSelective(@Param("record") SellOrder record, @Param("example") SellOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVC_MNG.TB_SELL_ORDER
     *
     * @mbggenerated Tue Apr 26 16:41:37 CST 2016
     */
    int updateByExample(@Param("record") SellOrder record, @Param("example") SellOrderExample example);
    
    SellOrderDTO getCustomerOrderById(String orderId);
    
    SellOrderDTO getChangeCardOrderById(String orderId);
}