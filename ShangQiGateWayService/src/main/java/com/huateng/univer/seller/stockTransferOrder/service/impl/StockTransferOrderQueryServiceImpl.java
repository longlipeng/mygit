/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: StockTransferOrderQueryServiceImpl.java
 * Author:   13071598
 * Date:     2013-11-14 下午04:25:06
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.huateng.univer.seller.stockTransferOrder.service.impl;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderQueryDTO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.univer.seller.stockTransferOrder.service.StockTransferOrderQueryService;

import org.apache.log4j.Logger;

/**
 * 〈一句话功能简述〉<br>
 * 调拨订单查询service实现类
 * 
 * @author 13071598
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class StockTransferOrderQueryServiceImpl implements StockTransferOrderQueryService {

    /**
     * 日志
     * */
    Logger logger = Logger.getLogger(StockTransferOrderQueryServiceImpl.class);

    private PageQueryDAO pageQueryDAO;

    /**
     * 
     * 功能描述: <br>
     * 查询当前机构发起的调拨订单
     *
     * @param sellOrderQueryDTO
     * @return
     * @throws BizServiceException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public PageDataDTO queryAllOrder(SellOrderQueryDTO sellOrderQueryDTO) throws BizServiceException {
        try {
            return pageQueryDAO.query("STOCKTRANSFERORDER.queryStockTransferOrderByCreater", sellOrderQueryDTO);
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("查询库存调拨订单失败~！");
        }
    }

    public PageQueryDAO getPageQueryDAO() {
        return pageQueryDAO;
    }

    public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
        this.pageQueryDAO = pageQueryDAO;
    }

}
