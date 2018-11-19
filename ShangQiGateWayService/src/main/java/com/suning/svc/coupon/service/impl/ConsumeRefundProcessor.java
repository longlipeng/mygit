/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: ConsumeRefundProcessor.java
 * Author:   11051612
 * Date:     2013-10-28 下午7:33:39
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.service.impl;

import java.util.Date;
import java.util.List;

import com.allinfinance.svc.coupon.dto.TradeItemTempDto;
import com.suning.svc.core.common.BaseException;
import com.suning.svc.coupon.constants.OrgnizationConstants;
import com.suning.svc.coupon.constants.SequenceContansts;
import com.suning.svc.coupon.constants.TradeConstants;
import com.suning.svc.coupon.dao.ConsumeOrderDao;
import com.suning.svc.coupon.service.BatchTradeProcessTemplate;
import com.suning.svc.coupon.service.TradeDealResult;
import com.suning.svc.coupon.util.SerialNumberUtil;
import com.suning.svc.ibatis.dao.ConsumeOrderDAO;
import com.suning.svc.ibatis.dao.TradeItemDealedDAO;
import com.suning.svc.ibatis.model.ConsumeOrder;
import com.suning.svc.ibatis.model.TradeItemDealed;
import com.suning.svc.ibatis.model.TradeItemDealedExample;

/**
 * 消费退货交易处理
 * 
 * @author 11051612
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ConsumeRefundProcessor extends BatchTradeProcessTemplate {

    private ConsumeOrderDao consumeOrderDao;

    private ConsumeOrderDAO consumeOrderDAO;

    private TradeItemDealedDAO tradeItemDealedDAO;

    @Override
    protected long getBatchNO() {
        return commonSupportService.getBatchNO(TradeConstants.TradeType.CONSUME, TradeConstants.TradeDirection.OPPOSIT);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected TradeDealResult handleTrade(TradeItemTempDto data, long batchNO) throws BaseException {
        TradeDealResult result = new TradeDealResult();

        // 获取原交易订单行项号，根据行项号查找原始消费交易
        String itemOrderNO = data.getItemOrderNo();
        logger.info("开始对交易{}退货{}分", data.getItemOrderNo(), data.getAmount());
        // 根据退货的原交易行项号，获取原消费订单列表
        TradeItemDealedExample tradeItemDealedExample = new TradeItemDealedExample();
        TradeItemDealedExample.Criteria criteria = tradeItemDealedExample.createCriteria();
        criteria.andItemOrderNoEqualTo(itemOrderNO);
        criteria.andCouponNoEqualTo(data.getCouponNo());
        criteria.andMchtCodeEqualTo(data.getMchtCode());
        criteria.andTradeTypeEqualTo(TradeConstants.TradeType.CONSUME);
        List<TradeItemDealed> tradeItemDealeds = tradeItemDealedDAO.selectByExample(tradeItemDealedExample);
        if (tradeItemDealeds.size() == 0) {
            result.fail("未找到原交易[单号:" + data.getItemOrderNo() + ", 券号:" + data.getCouponNo() + "]");
            return result;
        }

        ConsumeOrder originalOrder = consumeOrderDAO.selectByPrimaryKey(tradeItemDealeds.get(0).getRefOrderId());

        // 如果不够退货，则退出
        if (originalOrder.getRefundableAmount() < data.getAmount()) {
            result.fail("可退金额[" + originalOrder.getRefundableAmount() + "]<退货金额[" + data.getAmount() + "] 退货失败");
            return result;
        }

        // 执行退货，并生成退货订单，修改消费订单的可退金额
        long refundOrderId = refund(originalOrder.getCardNo(), data, originalOrder);
        result.setOrderId(refundOrderId);
        result.setOrderType(TradeConstants.TradeOrderType.ORDER_TYPE_CONSUME);
        return result;

    }

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     * 
     * @param cardNO 退货的卡号
     * @param refundAmount 退货金额
     * @param b2cRefundOrder 原易购消费交易
     * @param order 退货的原交易订单
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private long refund(String cardNO, TradeItemTempDto data, ConsumeOrder originalOrder) {
        Date now = new Date();
        long refundAmount = data.getAmount();
        // 增加卡余额
        long balance = cardAmountManager.addAmount(cardNO, refundAmount);
        logger.info("向卡[{}]增加金额[{}]分", cardNO, refundAmount);
        // 插消费表记录退货记录
        long id = SerialNumberUtil.getSequence(SequenceContansts.CONSUMER_ORDER_ID_SEQ);
        ConsumeOrder consumeOrder = new ConsumeOrder();
        consumeOrder.setId(id);
        consumeOrder.setAmount(refundAmount);
        consumeOrder.setCardNo(cardNO);
        consumeOrder.setCouponNo(originalOrder.getCouponNo());
        consumeOrder.setCreatedTime(now);
        consumeOrder.setMchtEntityId(originalOrder.getMchtEntityId());
        consumeOrder.setFatherEntityId(OrgnizationConstants.HXT_ENTITY_ID);
        consumeOrder.setOriginalSeriaNo(originalOrder.getSerialNo());
        consumeOrder.setSerialNo(SerialNumberUtil.getSerialNumberOf16(id));
        consumeOrder.setTradeTime(data.getTradeTime());
        consumeOrder.setBalanceAmount(balance);
        consumeOrder.setType(TradeConstants.TradeDirection.OPPOSIT);
        consumeOrder.setUpdatedTime(now);
        consumeOrderDAO.insert(consumeOrder);
        logger.info("消费订单[{}]退货{}分", originalOrder.getSerialNo(), refundAmount);

        // 扣减消费的可退货金额 更新退货状态
        long refundableAmount = originalOrder.getRefundableAmount() - refundAmount;
        String refundType = null;
        if (refundableAmount > 0) {
            refundType = TradeConstants.RefundStatus.PARTLY_REFUND;
        } else {
            refundType = TradeConstants.RefundStatus.COMPLETELY_REFUND;
        }
        consumeOrderDao.minusRefundableMoney(originalOrder.getId(), refundAmount, refundType);
        logger.info("扣减订单[{}]的可退金额{}分", originalOrder.getSerialNo(), refundAmount);
        return id;
    }

    /*
     * (non-Jsdoc)
     * @see com.suning.svc.coupon.service.BatchTradeProcessTemplate#getTradeList(long)
     */
    @Override
    protected List<TradeItemTempDto> getTradeList(long batchNO) {
        return commonSupportService.getTradesByBatchNo(batchNO);
    }

    /**
     * @return the consumeOrderDao
     */
    public ConsumeOrderDao getConsumeOrderDao() {
        return consumeOrderDao;
    }

    /**
     * @param consumeOrderDao the consumeOrderDao to set
     */
    public void setConsumeOrderDao(ConsumeOrderDao consumeOrderDao) {
        this.consumeOrderDao = consumeOrderDao;
    }

    /**
     * @return the consumeOrderDAO
     */
    public ConsumeOrderDAO getconsumeOrderDAO() {
        return consumeOrderDAO;
    }

    /**
     * @param consumeOrderDAO the consumeOrderDAO to set
     */
    public void setconsumeOrderDAO(ConsumeOrderDAO consumeOrderDAO) {
        this.consumeOrderDAO = consumeOrderDAO;
    }

    /**
     * @return the consumeOrderDAO
     */
    public ConsumeOrderDAO getConsumeOrderDAO() {
        return consumeOrderDAO;
    }

    /**
     * @param consumeOrderDAO the consumeOrderDAO to set
     */
    public void setConsumeOrderDAO(ConsumeOrderDAO consumeOrderDAO) {
        this.consumeOrderDAO = consumeOrderDAO;
    }

    /**
     * @return the tradeItemDealedDAO
     */
    public TradeItemDealedDAO getTradeItemDealedDAO() {
        return tradeItemDealedDAO;
    }

    /**
     * @param tradeItemDealedDAO the tradeItemDealedDAO to set
     */
    public void setTradeItemDealedDAO(TradeItemDealedDAO tradeItemDealedDAO) {
        this.tradeItemDealedDAO = tradeItemDealedDAO;
    }

}
