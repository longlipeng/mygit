/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: CardCurrentTimeStockQueryServiceImpl.java
 * Author:   lbb
 * Date:     2013-7-22 下午11:17:39
 * Description:       
 * History: 
 * <author>      <time>      <version>    <desc>
 * lbb             2013-7-22 下午11:17:39
 */
package com.huateng.univer.issuer.cardCurrentTimeStockQuery.service.impl;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.service.issueOperation.cardCurrentTimeStockQuery.dto.CardCurrentTimeStockQueryDTO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.univer.issuer.cardCurrentTimeStockQuery.service.CardCurrentTimeStockQueryService;

/**
 * 卡实时库存查询实现类<br> 
 * 
 *
 * @author zqs
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class CardCurrentTimeStockQueryServiceImpl implements CardCurrentTimeStockQueryService {
    
    /**
     * 日志
     */
    Logger logger = Logger.getLogger(CardCurrentTimeStockQueryServiceImpl.class);
    /**
     * 分页查询DAO
     */
    private PageQueryDAO pageQueryDAO;
  
   
    /**
     * 查询卡实时库存
     * 
     * @param CardCurrentTimeStockQueryDTO
     * @return PageDataDTO
     * @throws BizServiceException
     * */
    public PageDataDTO inqueryCardCurrentTimeStock(
            CardCurrentTimeStockQueryDTO cardCurrentTimeStockQueryDTO) throws BizServiceException {
        try {
            PageDataDTO pdd = pageQueryDAO.query( 
                    "CARDCURRENTTIMESTOCKQUERY.cardCurrentTimeStockQuery",cardCurrentTimeStockQueryDTO) ;
            return pdd;
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("查询卡实时库存失败~！");
        }
    }


    public PageQueryDAO getPageQueryDAO() {
        return pageQueryDAO;
    }


    public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
        this.pageQueryDAO = pageQueryDAO;
    }

}
