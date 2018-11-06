/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: CardDao.java
 * Author:   sunchao
 * Date:     2013-10-28 下午07:52:48
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.service;

import java.util.List;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.svc.coupon.dto.VirtualCardQueryDto;
import com.huateng.framework.ibatis.model.EntitySystemParameter;
import com.suning.svc.ibatis.model.VirtualCard;

public interface VirtualCardService {

    /**
     * 可以使用的卡数量
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    List<VirtualCard> canCardCanUsed(long productId) throws Exception;

    /**
     * 制num张卡
     * 
     * @param num
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    void makeCards(long productId, int num) throws Exception;

    /**
     * 分页查询虚拟卡信息
     * 
     * @param dto
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    PageDataDTO queryVirtualCardByPage(VirtualCardQueryDto dto);

    /**
     * 
     * 更新卡状态
     * 
     * @param id
     * @param cardState
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    void updateVirtualCardState(long id, String cardState);

    /**
     * 根据券号获取卡
     *
     * @param couponNo
     * @param orgCode
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    VirtualCard queryCardByCouponNo(String couponNo ,String orgCode);

    /**
     * 根据华夏通的券号获取华夏通的卡
     * 
     * @param couponNo
     * @return
     */
    VirtualCard queryHXTCardByCouponNo(String couponNo);

    EntitySystemParameter queryByCardKey();

    /**
     * 获取使用的卡
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    List<VirtualCard> getCardToUse(long productId) throws Exception;

    /**
     * 批量更新卡状态
     * 
     * @param virtualCardList
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    int batchUpdateCardStatus(List<VirtualCard> virtualCardList);

}
