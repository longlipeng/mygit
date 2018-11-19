/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: TradeItemDealedServiceImpl.java
 * Author:   13040443
 * Date:     2013-11-6 下午02:43:28
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.service.impl;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.svc.coupon.dto.TradeItemDealedQueryDto;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.suning.svc.coupon.service.TradeItemDealedService;

/**
 * 已处理数据实现类
 * 
 * @author yanbin
 */
public class TradeItemDealedServiceImpl implements TradeItemDealedService {

    private Logger logger = Logger.getLogger(this.getClass());

    private PageQueryDAO pageQueryDAO;

    @Override
    public PageDataDTO query(TradeItemDealedQueryDto tradeItemDealedQueryDto) throws BizServiceException {
        try {
            return pageQueryDAO.query(
                    "com.suning.svc.ibatis.model.TradeItemDealed.abatorgenerated_selectTradeItemDealed",
                    tradeItemDealedQueryDto);
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("查询记录失败!");
        }
    }

    /**
     * @return the pageQueryDAO
     */
    public PageQueryDAO getPageQueryDAO() {
        return pageQueryDAO;
    }

    /**
     * @param pageQueryDAO the pageQueryDAO to set
     */
    public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
        this.pageQueryDAO = pageQueryDAO;
    }

}
