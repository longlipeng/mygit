/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: BatchCardServiceImpl.java
 * Author:   xuwei
 * Date:     2013-11-6 下午06:24:15
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.huateng.univer.cardmanage.biz.service.impl;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.cardmanagement.dto.BatchCardActionDTO;
import com.allinfinance.univer.cardmanagement.dto.BatchCardInfoDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.AccCardInfoDao;
import com.huateng.framework.ibatis.dao.EntityStockDAO;
import com.huateng.framework.ibatis.dao.SellOrderCardListDAO;
import com.huateng.framework.ibatis.dao.SellOrderDAO;
import com.huateng.framework.ibatis.dao.SellOrderListDAO;
import com.huateng.framework.ibatis.model.EntityStock;
import com.huateng.framework.ibatis.model.EntityStockExample;
import com.huateng.framework.ibatis.model.SellOrder;
import com.huateng.framework.ibatis.model.SellOrderCardList;
import com.huateng.framework.ibatis.model.SellOrderList;
import com.huateng.framework.util.DateUtil;
import com.huateng.univer.cardmanage.biz.service.BatchCardService;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * 发行机构批量卡作废
 * 
 * @author xuwei
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class BatchCardServiceImpl implements BatchCardService {
    /**
     * Log操作
     */
    Logger logger = Logger.getLogger(BatchCardServiceImpl.class);
    /** 分页DAO */
    private PageQueryDAO pageQueryDAO;
    private CommonsDAO commonsDAO;
    /** 卡信息表DAO */
    private AccCardInfoDao accCardInfoDao;
    /** 订单DAO */
    private SellOrderDAO sellOrderDAO;
    /** 订单卡详细DAO */
    private SellOrderCardListDAO sellOrderCardListDAO;
    /** 订单详细DAO */
    private SellOrderListDAO sellOrderListDAO;
    /** 库存DAO */
    private EntityStockDAO entityStockDAO;

    /*
     * (non-Javadoc)
     * @seecom.huateng.univer.cardmanage.biz.service.BatchCardService#query(com.huateng.univer.cardmanagement.dto.
     * BatchCardActionDTO)
     */
    /**
     * 查询方法
     * */
    @Override
    public PageDataDTO query(BatchCardActionDTO batchCardDTO) throws BizServiceException {
        try {
            return pageQueryDAO.query("TB_ACC_CARD_INFO.selectBatchCardDTO", batchCardDTO);
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("查询卡批量作废信息失败！");
        }
    }

    /*
     * (non-Javadoc)
     * @see com.huateng.univer.cardmanage.biz.service.BatchCardService#checkStatus(java.lang.String[])
     */
    /**
     * 检查卡片状态
     * */
    @Override
    public long checkStatus(BatchCardActionDTO batchCardDTO) throws BizServiceException {
        Long i = accCardInfoDao.checkCardStatus(batchCardDTO);
        return i.longValue();
    }

    /*
     * (non-Javadoc)
     * @see com.huateng.univer.cardmanage.biz.service.BatchCardService#invalid()
     */
    /**
     * 作废入口
     * */
    @Override
    public void submitInvalid(BatchCardActionDTO batchCardDTO) throws BizServiceException {
        long countStatus = checkStatus(batchCardDTO);
        int countProductId = checkProductId(batchCardDTO);
        if (countStatus > 0) {
            throw new BizServiceException("作废的卡中有已激活的卡！");
        }
        if (countProductId > 1) {
            throw new BizServiceException("请检查起始和结束卡号！");
        } else {
            doOprater(batchCardDTO);
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.huateng.univer.cardmanage.biz.service.BatchCardService#checkProductId(com.huateng.univer.cardmanagement.dto
     * .BatchCardActionDTO)
     */
    /**
     *检查做废卡产品号
     * */
    @Override
    public int checkProductId(BatchCardActionDTO batchCardDTO) throws BizServiceException {
        int i = accCardInfoDao.checkCardProductId(batchCardDTO);
        return i;
    }
    /**
     * 作废逻辑
     * */
    public void doOprater(BatchCardActionDTO batchCardDTO) throws BizServiceException {
        // 在卡片状态未更新前取出来。
        List<BatchCardInfoDTO> list = new ArrayList<BatchCardInfoDTO>();
        if(batchCardDTO!=null){
            list = accCardInfoDao.getAccCardInfo(batchCardDTO);
        }else{
            throw new BizServiceException("参数传递失败！");
        }  
        Integer cardTotalInteger=Integer.valueOf(batchCardDTO.getMaxNum());
        if (cardTotalInteger.intValue() < list.size()) {
            throw new BizServiceException("每次作废的卡数不能超过" + cardTotalInteger);
        }
        logger.info("共有" + list.size() + "条记录需要处理!");
        try {
            accCardInfoDao.updateStatus(batchCardDTO);
        } catch (Exception e) {
            logger.error("更新卡状态失败！!");
            logger.info(e.getStackTrace());
            logger.info(e.getMessage());
            throw new BizServiceException("更新卡状态失败！");
        }
        BatchCardInfoDTO bean = new BatchCardInfoDTO();
        if (list.size() > 0) {
            bean = list.get(0);
        }
        // 得到产品号和机构号
        SellOrder sellOrder = new SellOrder();
        sellOrder.setOrderId(commonsDAO.getNextValueOfSequence("TB_SELL_ORDER"));
        sellOrder.setOrderType(OrderConst.ORDER_TYPE_CARD_INVOICE);
        sellOrder.setProductId(bean.getProductId().trim());
        // 将机构号设置成订单发起方
        sellOrder.setFirstEntityId(bean.getIssuerId().trim());
        sellOrder.setCardQuantity(String.valueOf(list.size()));
        sellOrder.setRealCardQuantity(String.valueOf(list.size()));
        String date = DateUtil.getCurrentTime();
        sellOrder.setOrderDate(date.substring(0, 8));
        sellOrder.setCreateTime(date);
        sellOrder.setModifyTime(date);
        sellOrder.setCreateUser(batchCardDTO.getUser());
        sellOrder.setModifyUser(batchCardDTO.getUser());
        sellOrder.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
        sellOrder.setMemo(batchCardDTO.getMemo());
        logger.info("开始插入作废订单信息！");
        try {
            sellOrderDAO.insert(sellOrder);
        } catch (Exception e) {
            logger.error("插入作废订单失败！");
            logger.error(e.getMessage());
        }
        logger.info("结束插入作废订单信息！");
        SellOrderList sellOrderList = new SellOrderList();
        sellOrderList.setOrderListId(commonsDAO.getNextValueOfSequence("TB_SELL_ORDER_LIST"));
        sellOrderList.setOrderId(sellOrder.getOrderId());
        sellOrderList.setProductId(bean.getProductId().trim());
        sellOrderList.setCreateTime(date);
        sellOrderList.setModifyTime(date);
        sellOrderList.setCreateUser(batchCardDTO.getUser());
        sellOrderList.setModifyUser(batchCardDTO.getUser());
        sellOrderList.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
        logger.info("开始插入作废订单详细信息！");
        try {
            sellOrderListDAO.insert(sellOrderList);
        } catch (Exception e) {
            logger.error("插入订单信息失败");
            logger.error(e.getMessage());
        }
        logger.info("结束插入作废订单详细信息！");
        logger.info("开始插入作废订单卡信息！");
        for (BatchCardInfoDTO record : list) {
            SellOrderCardList sellOrderCardList = new SellOrderCardList();
            sellOrderCardList.setOrderCardListId(commonsDAO
                    .getNextValueOfSequenceBySequence("SEQ_SELL_ORDER_CARD_LIST"));
            sellOrderCardList.setOrderId(sellOrder.getOrderId());
            sellOrderCardList.setOrderListId(sellOrderList.getOrderListId());
            sellOrderCardList.setCardNo(record.getCardNo());
            sellOrderCardList.setCreateTime(date);
            sellOrderCardList.setModifyTime(date);
            sellOrderCardList.setCreateUser(batchCardDTO.getUser());
            sellOrderCardList.setModifyUser(batchCardDTO.getUser());
            sellOrderCardList.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
            sellOrderCardListDAO.insert(sellOrderCardList);
            EntityStock entityStock = new EntityStock();
            entityStock = entityStockDAO.selectByPrimaryKey(record.getCardNo());
            if (entityStock != null && entityStock.getCardNo() != null) {
                EntityStockExample example = new EntityStockExample();
                example.createCriteria().andCardNoEqualTo(record.getCardNo());
                entityStock.setFldResData(OrderConst.CARD_STOCK_INVALID);
                entityStock.setModifyTime(date);
                entityStock.setModifyUser(batchCardDTO.getUser());
                entityStockDAO.updateByExample(entityStock, example);
            }
        }
        logger.info("结束插入作废订单卡信息！");
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

    /**
     * @return the accCardInfoDao
     */
    public AccCardInfoDao getAccCardInfoDao() {
        return accCardInfoDao;
    }

    /**
     * @param accCardInfoDao the accCardInfoDao to set
     */
    public void setAccCardInfoDao(AccCardInfoDao accCardInfoDao) {
        this.accCardInfoDao = accCardInfoDao;
    }

    /**
     * @return the sellOrderDAO
     */
    public SellOrderDAO getSellOrderDAO() {
        return sellOrderDAO;
    }

    /**
     * @param sellOrderDAO the sellOrderDAO to set
     */
    public void setSellOrderDAO(SellOrderDAO sellOrderDAO) {
        this.sellOrderDAO = sellOrderDAO;
    }

    /**
     * @return the sellOrderCardListDAO
     */
    public SellOrderCardListDAO getSellOrderCardListDAO() {
        return sellOrderCardListDAO;
    }

    /**
     * @param sellOrderCardListDAO the sellOrderCardListDAO to set
     */
    public void setSellOrderCardListDAO(SellOrderCardListDAO sellOrderCardListDAO) {
        this.sellOrderCardListDAO = sellOrderCardListDAO;
    }

    /**
     * @return the sellOrderListDAO
     */
    public SellOrderListDAO getSellOrderListDAO() {
        return sellOrderListDAO;
    }

    /**
     * @param sellOrderListDAO the sellOrderListDAO to set
     */
    public void setSellOrderListDAO(SellOrderListDAO sellOrderListDAO) {
        this.sellOrderListDAO = sellOrderListDAO;
    }

    /**
     * @return the entityStockDAO
     */
    public EntityStockDAO getEntityStockDAO() {
        return entityStockDAO;
    }

    /**
     * @param entityStockDAO the entityStockDAO to set
     */
    public void setEntityStockDAO(EntityStockDAO entityStockDAO) {
        this.entityStockDAO = entityStockDAO;
    }

}
