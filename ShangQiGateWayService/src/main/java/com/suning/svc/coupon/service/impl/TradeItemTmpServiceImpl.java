/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: TradeDealResult.java
 * Author:   孙超
 * Date:     2013-11-5 下午10:30:01
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.service.impl;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.svc.coupon.dto.TradeItemTempQueryDTO;
import com.huateng.framework.dao.PageQueryDAO;
import com.suning.svc.coupon.service.TradeItemTmpService;
import com.suning.svc.ibatis.dao.TradeItemTempDAO;

/**
 * 交易临时表
 * 
 * @author 孙超
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class TradeItemTmpServiceImpl implements TradeItemTmpService {

    private PageQueryDAO pageQueryDAO;

    private TradeItemTempDAO tradeItemTempDAO;

    @Override
    public PageDataDTO getTradeItemTmpByPage(TradeItemTempQueryDTO tradeItemTempQueryDTO) {
        return pageQueryDAO.query("com.suning.svc.ibatis.model.TradeItemDealed.getTradeItemTmpByPage",
                tradeItemTempQueryDTO);
    }

    public PageQueryDAO getPageQueryDAO() {
        return pageQueryDAO;
    }

    public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
        this.pageQueryDAO = pageQueryDAO;
    }

    @Override
    public void delete(Long id) {
        tradeItemTempDAO.deleteByPrimaryKey(id);

    }

    /**
     * @return the tradeItemTempDAO
     */
    public TradeItemTempDAO getTradeItemTempDAO() {
        return tradeItemTempDAO;
    }

    /**
     * @param tradeItemTempDAO the tradeItemTempDAO to set
     */
    public void setTradeItemTempDAO(TradeItemTempDAO tradeItemTempDAO) {
        this.tradeItemTempDAO = tradeItemTempDAO;
    }

}
