/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: CardInvalidInfoQueryServiceImpl.java
 * Author:   Administrator
 * Date:     2013-11-7 下午03:06:03
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 苟昊               2013-11-07            版本号                  描述
 */
package com.huateng.univer.cardinvalidinfoquery.impl;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderQueryDTO;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.univer.cardinvalidinfoquery.CardInvalidInfoQueryService;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * 卡作废查询实现类<br>
 * . 〈功能详细描述〉
 * 
 * @author Administrator
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class CardInvalidInfoQueryServiceImpl implements CardInvalidInfoQueryService {
    private Logger logger = Logger.getLogger(this.getClass());
    /**
     * 分页查询DAO.
     */
    private PageQueryDAO pageQueryDAO;
    /**
     * 公共DAO.
     */
    private CommonsDAO commonsDAO;

    /**
     * 查询
     */
    @Override
    public PageDataDTO query(SellOrderQueryDTO sellOrderQueryDTO) throws BizServiceException {
        try {
            PageDataDTO pageDataDTO = pageQueryDAO.query("CARD_INVALID_QUERY.query", sellOrderQueryDTO);
            return pageDataDTO;

        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("查询卡作废单失败!");
        }

    }

    /*
     * (non-Javadoc)
     * @see
     * com.huateng.univer.cardinvalidinfoquery.CardInvalidInfoQueryService#view(com.huateng.univer.seller.order.dto.
     * SellOrderQueryDTO)
     */
    @SuppressWarnings("unchecked")
    @Override
    public SellOrderDTO view(SellOrderQueryDTO sellOrderQueryDTO) throws BizServiceException {
        try {
            List<SellOrderDTO> list = new ArrayList<SellOrderDTO>();
            list = (List<SellOrderDTO>) commonsDAO.queryForList("CARD_INVALID_QUERY.view", sellOrderQueryDTO);
            SellOrderDTO sellOrderDTO = (SellOrderDTO) list.get(0);
            return sellOrderDTO;
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("查询卡作废明细失败!");
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.huateng.univer.cardinvalidinfoquery.CardInvalidInfoQueryService#viewCardList(com.huateng.univer.seller.order
     * .dto.SellOrderQueryDTO)
     */
    @Override
    public PageDataDTO viewCardList(SellOrderQueryDTO sellOrderQueryDTO) throws BizServiceException {
        try {
            PageDataDTO pageDataDTO = pageQueryDAO.query("CARD_INVALID_QUERY.viewcardlist", sellOrderQueryDTO);
            return pageDataDTO;
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("查询作废卡列表明细失败!");
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

    /**
     * @return the commonsDAO
     */
    public CommonsDAO getCommonsDAO() {
        return commonsDAO;
    }

    /**
     * @param commonsDAO the commonsDAO to set
     */
    public void setCommonsDAO(CommonsDAO commonsDAO) {
        this.commonsDAO = commonsDAO;
    }

}
