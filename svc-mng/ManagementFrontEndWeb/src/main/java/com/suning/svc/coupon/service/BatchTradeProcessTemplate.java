/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: TransactionTemplate.java
 * Author:   11051612
 * Date:     2013-10-28 下午4:23:52
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.allinfinance.svc.coupon.dto.TradeItemTempDto;
import com.suning.svc.core.common.BaseException;
import com.suning.svc.coupon.constants.TradeItemConstants;
import com.suning.svc.coupon.dao.TradeItemDao;
import com.suning.svc.ibatis.dao.DealBatchDAO;
import com.suning.svc.ibatis.dao.TradeItemTempDAO;
import com.suning.svc.ibatis.model.DealBatch;
import com.suning.svc.ibatis.model.DealBatchExample;
import com.suning.svc.ibatis.model.TradeItemTemp;
import com.suning.svc.ibatis.model.TradeItemTempExample;

/**
 * 双十一联合0元购交易处理模板
 * 
 * @author 11051612
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public abstract class BatchTradeProcessTemplate implements TradeProcessor {

    private final String CLASSNAME = getClass().getSimpleName();

    protected static final Logger logger = LoggerFactory.getLogger(BatchTradeProcessTemplate.class);

    @Autowired
    @Qualifier("transactionTemplate")
    private TransactionTemplate transactionTemate;

    @Autowired
    protected CommonSupportService commonSupportService;

    @Autowired
    protected DealBatchDAO dealBatchDAO;

    @Autowired
    protected TradeItemTempDAO tradeItemTempDAO;

    @Autowired
    protected TradeItemDao tradeItemDao;

    @Autowired
    protected CardAmountManager cardAmountManager;

    public void batchProcessTrade() {
        init();
        // 获取批次号
        long batchNO;
        while (true) {
            batchNO = getBatchNO();
            if (batchNO == -1) {
                logger.info("批次号[{}]退出处理", batchNO);
                break;
            }
            logger.info("开始处理批次[{}]", batchNO);
            final long usedBatchNO = batchNO;
            // 根据批次号获取这一批次需要处理的交易列表
            List<TradeItemTempDto> datas = getTradeList(batchNO);

            for (final TradeItemTempDto data : datas) {
                try {
                    transactionTemate.execute(new TransactionCallback() {

                        @Override
                        public Object doInTransaction(TransactionStatus status) {
                            // 处理交易(消费，退货，充值 ，充退)
                            logger.info(
                                    "{}开始处理交易:[券号:{},商户:{},批次:{},金额:{}]",
                                    new Object[] { CLASSNAME, data.getCouponNo(), data.getMchtCode(), usedBatchNO,
                                            data.getAmount() });
                            TradeDealResult result = handleTrade(data, usedBatchNO);
                            if (result.isSuccess()) {
                                // 处理成功，将数据从临时表移到正式表 将交易状态改为失败 更新b2c交易的更新时间
                                moveData(data, usedBatchNO, result.getOrderType(), result.getOrderId());
                            } else {
                                // 处理失败 ，将交易状态改为失败 更新b2c交易的更新时间
                                handleTradeFail(data, usedBatchNO, result.getMessage());
                            }
                            return null;
                        }

                    });
                } catch (Exception e) {
                    logger.warn("交易处理失败", e);
                    // 处理失败 ，将交易状态改为失败 更新b2c交易的更新时间
                    handleTradeFail(data, usedBatchNO, e.getMessage());
                    // throw new BaseException("交易处理失败", e);
                }
            }
            // 对这一批次处理更新结束时间
            updateBatch(batchNO);
        }

    }

    /**
     * 在处理交易之前，做一些初始化的准备工作，由子类实现
     * 
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    protected void init() {
    }

    /**
     * 
     * 功能描述: <br>
     * 〈功能详细描述〉
     * 
     * @param data
     * @param batchNO
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    protected void handleTradeFail(TradeItemTempDto data, long batchNO, String message) {
        // 更新状态为处理失败
        logger.info("{}处理交易[券号:{},商户:{},批次:{},金额:{}]:失败, 错误描述:{}",
                new Object[] { CLASSNAME, data.getCouponNo(), data.getMchtCode(), batchNO, data.getAmount(), message });
        TradeItemTempExample example = new TradeItemTempExample();
        TradeItemTempExample.Criteria criteria = example.createCriteria();
        criteria.andBatchIdEqualTo(batchNO);
        if (!StringUtils.isBlank(data.getCouponNo())) {
            criteria.andCouponNoEqualTo(data.getCouponNo());
        }
        if (!StringUtils.isBlank(data.getMchtCode())) {
            criteria.andMchtCodeEqualTo(data.getMchtCode());
        }
        if (!StringUtils.isBlank(data.getItemOrderNo())) {
            criteria.andItemOrderNoEqualTo(data.getItemOrderNo());
        }
        TradeItemTemp record = new TradeItemTemp();
        record.setStatus(TradeItemConstants.DEAL_FAIL);
        record.setUpdatedTime(new Date());
        // 记录错误信息
        record.setErrMsg(StringUtils.substring(message, 0, 150));
        tradeItemTempDAO.updateByExampleSelective(record, example);

    }

    protected void updateBatch(long batchNO) {
        logger.info("批次[{}]处理结束", batchNO);
        DealBatchExample example = new DealBatchExample();
        DealBatchExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(batchNO);

        DealBatch record = new DealBatch();
        record.setEndTime(new Date());
        record.setId(batchNO);

        dealBatchDAO.updateByExampleSelective(record, example);
    }

    /**
     * 对每一条请求数据做业务处理
     * 
     * @param usedBatchNO
     * @return 处理交易成功 返回订单ID，失败 返回　FAIL＿ID
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    protected abstract TradeDealResult handleTrade(TradeItemTempDto data, long usedBatchNO) throws BaseException;

    /**
     * 根据批次号获取所有对应未处理的数据
     * 
     * @param batchNO
     * @return
     */
    protected abstract List<TradeItemTempDto> getTradeList(long batchNO);

    /**
     * 将数据从临时表删除，并保存到正式表中
     * 
     * @param data
     * @param batchNO
     * @param orderType
     * @param id
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    protected void moveData(TradeItemTempDto data, long batchNO, String orderType, long orderId) {
        logger.info("{}成功处理交易{}，开始转移临时表数据到正式表", getClass(),
                ToStringBuilder.reflectionToString(data, ToStringStyle.SHORT_PREFIX_STYLE));
        // 更新临时表
        TradeItemTempExample example = new TradeItemTempExample();
        TradeItemTempExample.Criteria criteria = example.createCriteria();
        criteria.andBatchIdEqualTo(batchNO);
        if (!StringUtils.isBlank(data.getCouponNo())) {
            criteria.andCouponNoEqualTo(data.getCouponNo());
        }
        if (!StringUtils.isBlank(data.getMchtCode())) {
            criteria.andMchtCodeEqualTo(data.getMchtCode());
        }
        if (!StringUtils.isBlank(data.getItemOrderNo())) {
            criteria.andItemOrderNoEqualTo(data.getItemOrderNo());
        }
        TradeItemTemp record = new TradeItemTemp();
        record.setStatus(TradeItemConstants.DEAL_SUCCESS);
        record.setUpdatedTime(new Date());
        record.setErrMsg(null);
        tradeItemTempDAO.updateByExampleSelective(record, example);
        // 将记录插入正式表
        tradeItemDao.moveData(data.getMchtCode(), data.getCouponNo(), batchNO, data.getItemOrderNo(), orderType,
                orderId);
        // 删除临时表记录
        example = new TradeItemTempExample();
        criteria = example.createCriteria();
        criteria.andBatchIdEqualTo(batchNO);
        if (!StringUtils.isBlank(data.getCouponNo())) {
            criteria.andCouponNoEqualTo(data.getCouponNo());
        }
        if (!StringUtils.isBlank(data.getMchtCode())) {
            criteria.andMchtCodeEqualTo(data.getMchtCode());
        }
        if (!StringUtils.isBlank(data.getItemOrderNo())) {
            criteria.andItemOrderNoEqualTo(data.getItemOrderNo());
        }
        tradeItemTempDAO.deleteByExample(example);
    }

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    protected abstract long getBatchNO();

    /**
     * @return the transactionTemate
     */
    public TransactionTemplate getTransactionTemate() {
        return transactionTemate;
    }

    /**
     * @param transactionTemate the transactionTemate to set
     */
    public void setTransactionTemate(TransactionTemplate transactionTemate) {
        this.transactionTemate = transactionTemate;
    }

    /**
     * @return the commonSupportService
     */
    public CommonSupportService getCommonSupportService() {
        return commonSupportService;
    }

    /**
     * @param commonSupportService the commonSupportService to set
     */
    public void setCommonSupportService(CommonSupportService commonSupportService) {
        this.commonSupportService = commonSupportService;
    }

    /**
     * @return the dealBatchDAO
     */
    public DealBatchDAO getDealBatchDAO() {
        return dealBatchDAO;
    }

    /**
     * @param dealBatchDAO the dealBatchDAO to set
     */
    public void setDealBatchDAO(DealBatchDAO dealBatchDAO) {
        this.dealBatchDAO = dealBatchDAO;
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

    /**
     * @return the tradeItemDao
     */
    public TradeItemDao getTradeItemDao() {
        return tradeItemDao;
    }

    /**
     * @param tradeItemDao the tradeItemDao to set
     */
    public void setTradeItemDao(TradeItemDao tradeItemDao) {
        this.tradeItemDao = tradeItemDao;
    }

    /**
     * @return the cardAmountManager
     */
    public CardAmountManager getCardAmountManager() {
        return cardAmountManager;
    }

    /**
     * @param cardAmountManager the cardAmountManager to set
     */
    public void setCardAmountManager(CardAmountManager cardAmountManager) {
        this.cardAmountManager = cardAmountManager;
    }

}
