/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: DepositProcessor.java
 * Author:   11051612
 * Date:     2013-10-28 下午7:30:08
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.allinfinance.svc.coupon.dto.TradeItemTempDto;
import com.suning.svc.core.common.BaseException;
import com.suning.svc.coupon.card.CardSelector;
import com.suning.svc.coupon.constants.DepositConstants;
import com.suning.svc.coupon.constants.OrgnizationConstants;
import com.suning.svc.coupon.constants.SequenceContansts;
import com.suning.svc.coupon.constants.TradeConstants.TradeDirection;
import com.suning.svc.coupon.constants.TradeConstants.TradeOrderType;
import com.suning.svc.coupon.constants.TradeConstants.TradeType;
import com.suning.svc.coupon.service.BatchTradeProcessTemplate;
import com.suning.svc.coupon.service.CardAmountManager;
import com.suning.svc.coupon.service.InvoiceRequirementService;
import com.suning.svc.coupon.service.TradeDealResult;
import com.suning.svc.coupon.service.VirtualCardService;
import com.suning.svc.coupon.util.SerialNumberUtil;
import com.suning.svc.ibatis.dao.DepositOrderDAO;
import com.suning.svc.ibatis.dao.DepositProcessorDAO;
import com.suning.svc.ibatis.dao.VirtualCardDAO;
import com.suning.svc.ibatis.model.DepositOrder;
import com.suning.svc.ibatis.model.VirtualCard;

/**
 * 充值
 * 
 * @author yanbin
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class DepositProcessor extends BatchTradeProcessTemplate {

    private static final Logger log = LoggerFactory.getLogger(DepositProcessor.class);

    private VirtualCardDAO virtualCardDAO;
    private VirtualCardService virtualCardService;
    private DepositProcessorDAO depositProcessorDAO;
    private DepositOrderDAO depositOrderDAO;
    private InvoiceRequirementService invoiceRequirementService;
    private CardAmountManager cardAmountManager;

    @Override
    protected TradeDealResult handleTrade(TradeItemTempDto data, long batchNo) throws BaseException {
        TradeDealResult result = new TradeDealResult();

        String mchtCode = data.getMchtCode();
        String couponNo = data.getCouponNo();
        Long amount = data.getAmount();
        String tradeTime = data.getTradeTime();
        log.debug("1、0007 +1 : 处理充值订单  券号为 :", data.getCouponNo() + "金额为：" + data.getAmount());
        VirtualCard virtualCard = dealVirtualCard(couponNo, amount);
        // 更新卡内金额
        Long balanceAmount = cardAmountManager.addAmount(virtualCard.getCardNo(), amount);
        DepositOrder depositOrder = addDepositOrder(virtualCard, mchtCode, couponNo, amount, balanceAmount, tradeTime);
        invoiceRequirementService.addInvoiceRequirement(depositOrder);
        log.debug("完成对充值交易的业务处理");
        result.setOrderId(depositOrder.getId());
        result.setOrderType(TradeOrderType.ORDER_TYPE_DEPOSIT);

        return result;
    }

    @Override
    protected long getBatchNO() {
        return commonSupportService.getBatchNO(TradeType.DEPOSIT, TradeDirection.FORWARD);
    }

    /**
     * 获取充值的虚拟卡
     * 
     * @param couponNo
     * @param amount
     * @return
     */
    private VirtualCard dealVirtualCard(String couponNo, Long amount) {

        // 充值卡
        VirtualCard virtualCard = virtualCardService.queryHXTCardByCouponNo(couponNo);

        if (null == virtualCard) {
            virtualCard = CardSelector.getCard();
            virtualCard.setCouponNo(couponNo);
            virtualCard.setFatherEntityId(OrgnizationConstants.HXT_ENTITY_ID);
            virtualCardDAO.updateByPrimaryKey(virtualCard);
        }

        log.debug("拿到卡，卡号为：{}", virtualCard.getCardNo());
        return virtualCard;
    }

    /**
     * 添加充值订单
     * 
     * @param virtualCard
     * @param mchtCode
     * @param couponNo
     * @param amount
     * @return
     */
    private DepositOrder addDepositOrder(VirtualCard virtualCard, String mchtCode, String couponNo, Long amount,
            Long balanceAmount, String tradeTime) {
        // 记录订单信息
        DepositOrder depositOrder = new DepositOrder();
        depositOrder.setId(SerialNumberUtil.getSequence(SequenceContansts.SEQ_DEPOSIT_ORDER));
        depositOrder.setOrderNo(SerialNumberUtil.getSerialNumberOf16BySeqName(SequenceContansts.SEQ_DEPOSIT_ORDER));
        depositOrder.setCardNo(virtualCard.getCardNo());

        depositOrder.setCustomerEntityId(mchtCode);
        depositOrder.setFatherEntityId(OrgnizationConstants.HXT_ENTITY_ID);
        depositOrder.setAmount(amount);
        depositOrder.setTradeTime(tradeTime);
        depositOrder.setCouponNo(couponNo);
        depositOrder.setRefundableAmount(amount);
        depositOrder.setBalanceAmount(balanceAmount);
        depositOrder.setStatus(DepositConstants.DEPOSIT_ORDER_STATUS_NORMAL);
        depositOrder.setCreatedTime(new Date());
        depositOrder.setUpdatedTime(new Date());
        depositOrderDAO.insert(depositOrder);
        log.debug("添加充值订单完成；订单号为：{}", depositOrder.getOrderNo());
        return depositOrder;
    }

    @Override
    protected List<TradeItemTempDto> getTradeList(long batchNO) {
        return commonSupportService.getGroupTradesByBatchNo(batchNO);
    }

    /**
     * @return the virtualCardDAO
     */
    public VirtualCardDAO getVirtualCardDAO() {
        return virtualCardDAO;
    }

    /**
     * @param virtualCardDAO the virtualCardDAO to set
     */
    public void setVirtualCardDAO(VirtualCardDAO virtualCardDAO) {
        this.virtualCardDAO = virtualCardDAO;
    }

    /**
     * @return the depositProcessorDAO
     */
    public DepositProcessorDAO getDepositProcessorDAO() {
        return depositProcessorDAO;
    }

    /**
     * @param depositProcessorDAO the depositProcessorDAO to set
     */
    public void setDepositProcessorDAO(DepositProcessorDAO depositProcessorDAO) {
        this.depositProcessorDAO = depositProcessorDAO;
    }

    /**
     * @return the depositOrderDAO
     */
    public DepositOrderDAO getDepositOrderDAO() {
        return depositOrderDAO;
    }

    /**
     * @param depositOrderDAO the depositOrderDAO to set
     */
    public void setDepositOrderDAO(DepositOrderDAO depositOrderDAO) {
        this.depositOrderDAO = depositOrderDAO;
    }

    /**
     * @return the invoiceRequirementService
     */
    public InvoiceRequirementService getInvoiceRequirementService() {
        return invoiceRequirementService;
    }

    /**
     * @param invoiceRequirementService the invoiceRequirementService to set
     */
    public void setInvoiceRequirementService(InvoiceRequirementService invoiceRequirementService) {
        this.invoiceRequirementService = invoiceRequirementService;
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
