/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: DepositRefundProcessor.java
 * Author:   11051612
 * Date:     2013-10-28 下午7:32:53
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
import com.suning.svc.coupon.constants.DepositConstants;
import com.suning.svc.coupon.constants.OrgnizationConstants;
import com.suning.svc.coupon.constants.SequenceContansts;
import com.suning.svc.coupon.constants.TradeConstants;
import com.suning.svc.coupon.constants.TradeConstants.TradeDirection;
import com.suning.svc.coupon.constants.TradeConstants.TradeType;
import com.suning.svc.coupon.service.BatchTradeProcessTemplate;
import com.suning.svc.coupon.service.CardAmountManager;
import com.suning.svc.coupon.service.InvoiceRequirementService;
import com.suning.svc.coupon.service.TradeDealResult;
import com.suning.svc.coupon.service.VirtualCardService;
import com.suning.svc.coupon.util.SerialNumberUtil;
import com.suning.svc.ibatis.dao.DepositOrderDAO;
import com.suning.svc.ibatis.dao.DepositProcessorDAO;
import com.suning.svc.ibatis.dao.DepositRefundOrderDAO;
import com.suning.svc.ibatis.dao.TradeItemDealedDAO;
import com.suning.svc.ibatis.dao.VirtualCardDAO;
import com.suning.svc.ibatis.model.DepositOrder;
import com.suning.svc.ibatis.model.DepositRefundOrder;
import com.suning.svc.ibatis.model.TradeItemDealed;
import com.suning.svc.ibatis.model.TradeItemDealedExample;
import com.suning.svc.ibatis.model.VirtualCard;

/**
 * 充退<br>
 * 〈功能详细描述〉
 * 
 * @author yanbin
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class DepositRefundProcessor extends BatchTradeProcessTemplate {

    private static final Logger LOG = LoggerFactory.getLogger(DepositRefundProcessor.class);

    private DepositProcessorDAO depositProcessorDAO;
    private VirtualCardDAO virtualCardDAO;
    private VirtualCardService virtualCardService;
    private DepositRefundOrderDAO depositRefundOrderDao;
    private DepositOrderDAO depositOrderDAO;
    private InvoiceRequirementService invoiceRequirementService;
    private CardAmountManager cardAmountManager;
    private TradeItemDealedDAO tradeItemDealedDAO;

    @Override
    protected long getBatchNO() {
        return commonSupportService.getBatchNO(TradeType.DEPOSIT, TradeDirection.OPPOSIT);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected TradeDealResult handleTrade(TradeItemTempDto data, long usedBatchNO) throws BaseException {
        TradeDealResult result = new TradeDealResult();

        String mchtCode = data.getMchtCode();
        String couponNo = data.getCouponNo();
        Long amount = data.getAmount();
        // 获取原交易订单行项号，根据行项号查找原始消费交易
        String itemOrderNO = data.getItemOrderNo();

        LOG.debug("0007 -1 订单开始：商户号：{} ； 券号： {} ； 金额:{} ", new Object[] { mchtCode, couponNo, amount });

        // 获取充值记录订单
        TradeItemDealedExample tradeItemDealedExample = new TradeItemDealedExample();
        TradeItemDealedExample.Criteria criteria = tradeItemDealedExample.createCriteria();
        criteria.andItemOrderNoEqualTo(itemOrderNO);
        criteria.andCouponNoEqualTo(data.getCouponNo());
        criteria.andMchtCodeEqualTo(data.getMchtCode());
        criteria.andTradeTypeEqualTo(TradeConstants.TradeType.DEPOSIT);
        List<TradeItemDealed> tradeItemDealeds = tradeItemDealedDAO.selectByExample(tradeItemDealedExample);
        if (tradeItemDealeds.size() == 0) {
            result.fail("未找到原交易[单号:" + data.getItemOrderNo() + ", 券号:" + data.getCouponNo() + "]");
            return result;
        }

        DepositOrder originalOrder = depositOrderDAO.selectByPrimaryKey(tradeItemDealeds.get(0).getRefOrderId());

        // 如果不够退货，则退出
        if (originalOrder.getRefundableAmount() < data.getAmount()) {
            result.fail("可退金额[" + originalOrder.getRefundableAmount() + "]<充退金额[" + data.getAmount() + "] 充退失败");
            return result;
        }

        VirtualCard virtualCard = virtualCardService.queryHXTCardByCouponNo(couponNo);
        if (null == virtualCard) {
            result.fail("没有找到对应虚拟卡，不处理返回！");
            return result;
        }
        if ((virtualCard.getAvailableBalance() + TradeConstants.MONEY_TOLERANCE) < amount) {
            result.fail("卡内金额不足充退，不处理返回！");
            return result;
        }

        // 扣除卡内的金额
        Long balanceAmount = cardAmountManager.minusAmount(virtualCard.getCardNo(), amount);
        // 增加一条充退记录
        DepositRefundOrder depositRefundOrder = addDepositRefundOrder(mchtCode, couponNo, amount, balanceAmount,
                originalOrder.getOrderNo(), originalOrder.getCardNo(), data.getTradeTime());
        // 减去充值订单中的需要扣除的可退金额
        originalOrder.setRefundableAmount(originalOrder.getRefundableAmount() - amount);
        depositOrderDAO.updateByPrimaryKey(originalOrder);
        LOG.debug("扣除充退金额：{} ;可充退余额：{}", new Object[] { amount, originalOrder.getRefundableAmount() - amount });
        // 登记开票信息
        invoiceRequirementService.addInvoiceRequirement(depositRefundOrder, originalOrder.getId());

        LOG.debug("完成内部充退0007 -1 逻辑");
        result.setOrderId(depositRefundOrder.getId());
        result.setOrderType(TradeConstants.TradeOrderType.ORDER_TYPE_DEPOSIT_REFUND);
        return result;
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
     * 添加充退记录
     * 
     * @param mchtCode
     * @param couponNo
     * @param amount
     * @param orginalOrderNo
     * @param tradeTime
     * @return
     */
    private DepositRefundOrder addDepositRefundOrder(String mchtCode, String couponNo, Long amount, Long balanceAmount,
            String orginalOrderNo, String cardNo, String tradeTime) {
        LOG.debug("增加一条充退记录！原订单号为：" + orginalOrderNo);
        DepositRefundOrder depositRefundOrder = new DepositRefundOrder();
        depositRefundOrder.setId(SerialNumberUtil.getSequence(SequenceContansts.SEQ_DEPOSIT_REFUND_ORDER));
        depositRefundOrder.setRefundOrderNo(SerialNumberUtil
                .getSerialNumberOf16BySeqName(SequenceContansts.SEQ_DEPOSIT_REFUND_ORDER));
        depositRefundOrder.setOriginalOrderNo(orginalOrderNo);
        depositRefundOrder.setCardNo(cardNo);
        depositRefundOrder.setCustomerEntityId(mchtCode);
        depositRefundOrder.setFatherEntityId(OrgnizationConstants.HXT_ENTITY_ID);
        depositRefundOrder.setCouponNo(couponNo);
        depositRefundOrder.setTradeTime(tradeTime);
        depositRefundOrder.setAmount(amount);
        depositRefundOrder.setBalanceAmount(balanceAmount);
        depositRefundOrder.setStatus(DepositConstants.DEPOSIT_ORDER_STATUS_NORMAL);
        depositRefundOrder.setCreatedTime(new Date());
        depositRefundOrder.setUpdatedTime(new Date());
        depositRefundOrderDao.insert(depositRefundOrder);
        LOG.debug("充退记录添加完成！");
        return depositRefundOrder;
    }

    @Override
    protected List<TradeItemTempDto> getTradeList(long batchNO) {
        return commonSupportService.getTradesByBatchNo(batchNO);
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
     * @return the depositRefundOrderDao
     */
    public DepositRefundOrderDAO getDepositRefundOrderDao() {
        return depositRefundOrderDao;
    }

    /**
     * @param depositRefundOrderDao the depositRefundOrderDao to set
     */
    public void setDepositRefundOrderDao(DepositRefundOrderDAO depositRefundOrderDao) {
        this.depositRefundOrderDao = depositRefundOrderDao;
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
