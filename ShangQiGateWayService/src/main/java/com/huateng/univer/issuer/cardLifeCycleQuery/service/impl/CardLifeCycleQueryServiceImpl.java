/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: CardLifeCycleQueryServiceImpl.java
 * Author:   zqs
 * Date:     2013-4-25 下午02:13:39
 * Description:       
 * History: 
 * <author>      <time>      <version>    <desc>
 * zqs             2013-4-25 下午02:13:39
 */
package com.huateng.univer.issuer.cardLifeCycleQuery.service.impl;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.service.issueOperation.cardLifeCycleQuery.dto.CardLifeCycleQueryDTO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.univer.issuer.cardLifeCycleQuery.service.CardLifeCycleQueryService;

/**
 * 卡生命周期查询实现类<br> 
 * 
 *
 * @author zqs
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class CardLifeCycleQueryServiceImpl implements CardLifeCycleQueryService {
    
    /**
     * 日志
     */
    Logger logger = Logger.getLogger(CardLifeCycleQueryServiceImpl.class);
    /**
     * 分页查询DAO
     */
    private PageQueryDAO pageQueryDAO;
  
   
    /**
     * 查询卡生命周期
     * 
     * @param CardLifeCycleQueryDTO
     * @return PageDataDTO
     * @throws BizServiceException
     * */
    public PageDataDTO inqueryCardLifeCycle(CardLifeCycleQueryDTO cardLifeCycleQueryDTO) throws BizServiceException {
        try {
            PageDataDTO pdd = pageQueryDAO.query("CARDLIFECYCLEQUERY.cardLifeCycleQuery",
                    cardLifeCycleQueryDTO);
            return pdd;
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("查询卡生命周期失败~！");
        }
    }


    public PageQueryDAO getPageQueryDAO() {
        return pageQueryDAO;
    }


    public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
        this.pageQueryDAO = pageQueryDAO;
    }

}
