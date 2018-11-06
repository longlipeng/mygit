/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: ConsumeProcessor.java
 * Author:   11051612
 * Date:     2013-10-28 下午7:33:20
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.allinfinance.svc.coupon.dto.TradeItemTempDto;
import com.suning.svc.core.common.BaseException;
import com.suning.svc.coupon.constants.OrgnizationConstants;
import com.suning.svc.coupon.constants.SequenceContansts;
import com.suning.svc.coupon.constants.TradeConstants;
import com.suning.svc.coupon.service.BatchTradeProcessTemplate;
import com.suning.svc.coupon.service.TradeDealResult;
import com.suning.svc.coupon.service.VirtualCardService;
import com.suning.svc.coupon.util.SerialNumberUtil;
import com.suning.svc.ibatis.dao.ConsumeOrderDAO;
import com.suning.svc.ibatis.model.ConsumeOrder;
import com.suning.svc.ibatis.model.TradeItemTemp;
import com.suning.svc.ibatis.model.TradeItemTempExample;
import com.suning.svc.ibatis.model.VirtualCard;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author 11051612
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ConsumeProcessor extends BatchTradeProcessTemplate {

    private ConsumeOrderDAO consumeOrderDAO;

    private VirtualCardService virtualCardService;

    @Override
    protected long getBatchNO() {
        return commonSupportService.getBatchNO(TradeConstants.TradeType.CONSUME, TradeConstants.TradeDirection.FORWARD);
    }

    /*
     * (non-Jsdoc)
     * @see com.suning.svc.coupon.service.BatchTradeProcessTemplate#getTradeList(long)
     */
    @Override
    protected List<TradeItemTempDto> getTradeList(long batchNO) {
        return commonSupportService.getGroupTradesByBatchNo(batchNO);
    }

    @Override
    protected TradeDealResult handleTrade(TradeItemTempDto data, long usedBatchNO) throws BaseException {
        TradeDealResult result = new TradeDealResult();

        String couponNo = data.getCouponNo();
        // 根据券号查询出对应的卡信息
        VirtualCard card = virtualCardService.queryHXTCardByCouponNo(couponNo);

        if(card == null){
            result.fail("没有找到券号对应的卡号，消费失败");
            return result;
        }
        long cardAvailableBalance = card.getAvailableBalance();

        long consumeAmount = data.getAmount();
        if (cardAvailableBalance + TradeConstants.MONEY_TOLERANCE < consumeAmount) {
            logger.info("卡总金额[{}]分 不够消费[{}]分", cardAvailableBalance, consumeAmount);
            result.fail("交易失败,余额不足");
            return result;
        }

        // 扣减卡余额
        logger.info("对卡[{}]扣减可用余额[{}]", card.getCardNo(), consumeAmount);
        cardAvailableBalance = cardAmountManager.minusAmount(card.getCardNo(), consumeAmount);

        // 创建消费订单
        logger.info("创建消费订单[券号：{}, 金额:{}, 卡号:{}, 商户 :{}, 余额:{}]",
                new Object[] { couponNo, consumeAmount, card.getCardNo(), data.getMchtCode(), cardAvailableBalance });
        long orderId = createConsumeOrder(data, card, cardAvailableBalance);

        result.setOrderId(orderId);
        result.setOrderType(TradeConstants.TradeOrderType.ORDER_TYPE_CONSUME);
        return result;
    }

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     * 
     * @param data
     * @param couponNo
     * @param cardConsumeAmount
     * @param card
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private long createConsumeOrder(TradeItemTempDto data, VirtualCard card, Long cardAvailableBalance) {
        // 将卡消费信息记录到消费订单表中
        Date now = new Date();
        ConsumeOrder consumeOrder = new ConsumeOrder();
        Long id = SerialNumberUtil.getSequence(SequenceContansts.CONSUMER_ORDER_ID_SEQ);
        consumeOrder.setId(id);
        // 流水号
        consumeOrder.setSerialNo(SerialNumberUtil.getSerialNumberOf16(id));
        // 券号
        consumeOrder.setCouponNo(data.getCouponNo());
        // 类型为消费交易
        consumeOrder.setType(TradeConstants.TradeDirection.FORWARD);
        // 卡号
        consumeOrder.setCardNo(card.getCardNo());
        // 商户号
        consumeOrder.setMchtEntityId(data.getMchtCode());
        consumeOrder.setFatherEntityId(OrgnizationConstants.HXT_ENTITY_ID);
        // 可退金额
        consumeOrder.setRefundableAmount(data.getAmount());
        // 消费金额
        consumeOrder.setAmount(data.getAmount());
        // 正常状态
        consumeOrder.setStatus(TradeConstants.RefundStatus.NO_REFUND);
        // 创建时间
        consumeOrder.setTradeTime(data.getTradeTime());
        consumeOrder.setBalanceAmount(cardAvailableBalance);
        consumeOrder.setCreatedTime(now);
        consumeOrder.setUpdatedTime(now);
        consumeOrderDAO.insert(consumeOrder);
        return id;
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
     * @return the virtualCardService
     */
    public VirtualCardService getVirtualCardService() {
        return virtualCardService;
    }

    /**
     * @param virtualCardService the virtualCardService to set
     */
    public void setVirtualCardService(VirtualCardService virtualCardService) {
        this.virtualCardService = virtualCardService;
    }

}
