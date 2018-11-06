/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: ShareDepositProcessor.java
 * Author:   13040443
 * Date:     2013-10-30 下午03:45:56
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
import com.huateng.framework.ibatis.dao.EntitySystemParameterDAO;
import com.huateng.framework.ibatis.model.EntitySystemParameter;
import com.huateng.framework.ibatis.model.EntitySystemParameterExample;
import com.suning.svc.core.common.BaseException;
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
import com.suning.svc.coupon.util.SerialNumberUtil;
import com.suning.svc.ibatis.dao.DepositOrderDAO;
import com.suning.svc.ibatis.dao.DepositProcessorDAO;
import com.suning.svc.ibatis.dao.TradeItemDealedDAO;
import com.suning.svc.ibatis.dao.VirtualCardDAO;
import com.suning.svc.ibatis.model.DepositOrder;
import com.suning.svc.ibatis.model.DepositOrderExample;
import com.suning.svc.ibatis.model.TradeItemDealed;
import com.suning.svc.ibatis.model.TradeItemDealedExample;
import com.suning.svc.ibatis.model.VirtualCard;
import com.suning.svc.ibatis.model.VirtualCardExample;

/**
 * 平台商户充值,扒券
 * 
 * @author yanbin
 */
public class ShareDepositProcessor extends BatchTradeProcessTemplate {

    private static final Logger log = LoggerFactory.getLogger(DepositRefundProcessor.class);

    private TradeItemDealedDAO tradeItemDealedDAO;
    private DepositOrderDAO depositOrderDAO;
    private VirtualCardDAO virtualCardDAO;
    private DepositProcessorDAO depositProcessorDAO;
    private InvoiceRequirementService invoiceRequirementService;
    private EntitySystemParameterDAO entitySystemParameterDAO;
    private CardAmountManager cardAmountManager;

    @Override
    protected long getBatchNO() {
        return commonSupportService.getBatchNO(TradeType.SHARE_DEPOSIT, TradeDirection.OPPOSIT);
    }

    @Override
    protected List<TradeItemTempDto> getTradeList(long batchNO) {
        return commonSupportService.getTradesByBatchNo(batchNO);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected TradeDealResult handleTrade(TradeItemTempDto data, long batchNo) throws BaseException {
        TradeDealResult result = new TradeDealResult();
        String itemOrderNo = data.getItemOrderNo();

        log.debug("0010 交易开始 ：行项号为：{}", itemOrderNo);

        TradeItemDealedExample example = new TradeItemDealedExample();
        example.createCriteria().andItemOrderNoEqualTo(itemOrderNo)
                .andRefOrderTypeEqualTo(TradeOrderType.ORDER_TYPE_DEPOSIT);
        List<TradeItemDealed> tradeItemDealeds = tradeItemDealedDAO.selectByExample(example);
        if (null == tradeItemDealeds || tradeItemDealeds.size() == 0) {
            result.fail("没有找到交易记录，退出不处理");
            return result;
        }
        TradeItemDealed tradeItemDealed = tradeItemDealeds.get(0);

        DepositOrderExample exampleOrder = new DepositOrderExample();
        exampleOrder.createCriteria().andIdEqualTo(tradeItemDealed.getRefOrderId());
        List<DepositOrder> depositOrders = depositOrderDAO.selectByExample(exampleOrder);
        if (null == depositOrders || depositOrders.size() == 0) {
            result.fail("没有找到充值记录，退出不处理");
            return result;
        }
        DepositOrder depositOrder = depositOrders.get(0);
        log.debug("原充值记录，订单号为：{}", depositOrder.getOrderNo());

        VirtualCardExample exampleCard = new VirtualCardExample();
        exampleCard.createCriteria().andCardNoEqualTo(depositOrder.getCardNo());
        List<VirtualCard> virtualCards = virtualCardDAO.selectByExample(exampleCard);
        if (null == virtualCards || virtualCards.size() == 0) {
            result.fail("没有找到对应的卡，退出不处理");
            return result;
        }
        VirtualCard virtualCard = virtualCards.get(0);

        // 获取卡Id
        Long cardId = virtualCard.getId();
        log.debug("找到对应的卡，卡号为： {}", cardId);

        Long balanceAmount = cardAmountManager.addAmount(virtualCard.getCardNo(), data.getAmount());

        DepositOrder depositOrderDb = addDepositOrder(virtualCard, data.getCouponNo(), data.getAmount(), balanceAmount,
                data.getTradeTime());
        // 添加开票需求
        invoiceRequirementService.addInvoiceRequirement(depositOrderDb);
        log.debug("完成内部交易逻辑 ：0010：单号为：{}", itemOrderNo);
        result.setOrderId(depositOrderDb.getId());
        result.setOrderType(TradeOrderType.ORDER_TYPE_DEPOSIT);
        return result;
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
    private DepositOrder addDepositOrder(VirtualCard virtualCard, String couponNo, Long amount, Long balanceAmount,
            String tradeTime) {
        // 记录订单信息
        DepositOrder depositOrder = new DepositOrder();
        depositOrder.setId(SerialNumberUtil.getSequence(SequenceContansts.SEQ_DEPOSIT_ORDER));
        depositOrder.setOrderNo(SerialNumberUtil.getSerialNumberOf16BySeqName(SequenceContansts.SEQ_DEPOSIT_ORDER));
        depositOrder.setCardNo(virtualCard.getCardNo());
        // 平台充值 : 数据字典获取
        EntitySystemParameterExample example = new EntitySystemParameterExample();
        example.createCriteria().andParameterCodeEqualTo(DepositConstants.PARAMETER_CODE)
                .andEntityIdEqualTo(DepositConstants.ENTITY_ID)
                .andFatherEntityIdEqualTo(DepositConstants.FATHER_ENTITY_ID);
        List<EntitySystemParameter> entitySystemParameters = entitySystemParameterDAO.selectByExample(example);
        if (null != entitySystemParameters && entitySystemParameters.size() != 0) {
            EntitySystemParameter entitySystemParameter = entitySystemParameters.get(0);
            depositOrder.setCustomerEntityId(entitySystemParameter.getParameterValue());
            depositOrder.setFatherEntityId(OrgnizationConstants.HXT_ENTITY_ID);
        }
        depositOrder.setCouponNo(couponNo);
        depositOrder.setAmount(amount);
        depositOrder.setTradeTime(tradeTime);
        depositOrder.setRefundableAmount(amount);
        depositOrder.setBalanceAmount(balanceAmount);
        depositOrder.setStatus(DepositConstants.DEPOSIT_ORDER_STATUS_NORMAL);
        depositOrder.setCreatedTime(new Date());
        depositOrder.setUpdatedTime(new Date());
        depositOrderDAO.insert(depositOrder);
        log.debug("充值订单添加完成！订单号为：{}", depositOrder.getOrderNo());
        return depositOrder;
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
     * @return the entitySystemParameterDAO
     */
    public EntitySystemParameterDAO getEntitySystemParameterDAO() {
        return entitySystemParameterDAO;
    }

    /**
     * @param entitySystemParameterDAO the entitySystemParameterDAO to set
     */
    public void setEntitySystemParameterDAO(EntitySystemParameterDAO entitySystemParameterDAO) {
        this.entitySystemParameterDAO = entitySystemParameterDAO;
    }

}
