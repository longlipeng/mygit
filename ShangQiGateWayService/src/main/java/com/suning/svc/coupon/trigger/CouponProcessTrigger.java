/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: CouponProcessTrigger.java
 * Author:   13040443
 * Date:     2013-10-31 下午05:10:54
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.trigger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.suning.svc.coupon.service.impl.ConsumeProcessor;
import com.suning.svc.coupon.service.impl.ConsumeRefundProcessor;
import com.suning.svc.coupon.service.impl.DepositProcessor;
import com.suning.svc.coupon.service.impl.DepositRefundProcessor;
import com.suning.svc.coupon.service.impl.ShareDepositProcessor;

/**
 * 返券处理器job处理
 * 
 * @author yanbin
 */
public class CouponProcessTrigger {

    private static final Logger log = LoggerFactory.getLogger(CouponProcessTrigger.class);

    private DepositProcessor depositProcessor;
    private ShareDepositProcessor shareDepositProcessor;
    private ConsumeProcessor consumeProcessor;
    private ConsumeRefundProcessor consumeRefundProcessor;
    private DepositRefundProcessor depositRefundProcessor;

    public void execute() throws Exception {
        log.info("促销0元零交易任务开始处理：");
        depositProcessor.batchProcessTrade();
        log.debug("depositProcessor 0007+1处理完成。。");
        shareDepositProcessor.batchProcessTrade();
        log.debug("shareDepositProcessor 0010处理完成。。");
        consumeProcessor.batchProcessTrade();
        log.debug("consumeProcessor 0008+1处理完成。。");
        consumeRefundProcessor.batchProcessTrade();
        log.debug("consumeRefundProcessor 0008-1处理完成。。");
        depositRefundProcessor.batchProcessTrade();
        log.debug("depositRefundProcessor 0007-1处理完成。。");
        log.info("促销0元零交易任务处理结束！");
    }

    /**
     * @return the depositProcessor
     */
    public DepositProcessor getDepositProcessor() {
        return depositProcessor;
    }

    /**
     * @param depositProcessor the depositProcessor to set
     */
    public void setDepositProcessor(DepositProcessor depositProcessor) {
        this.depositProcessor = depositProcessor;
    }

    /**
     * @return the shareDepositProcessor
     */
    public ShareDepositProcessor getShareDepositProcessor() {
        return shareDepositProcessor;
    }

    /**
     * @param shareDepositProcessor the shareDepositProcessor to set
     */
    public void setShareDepositProcessor(ShareDepositProcessor shareDepositProcessor) {
        this.shareDepositProcessor = shareDepositProcessor;
    }

    /**
     * @return the consumeProcessor
     */
    public ConsumeProcessor getConsumeProcessor() {
        return consumeProcessor;
    }

    /**
     * @param consumeProcessor the consumeProcessor to set
     */
    public void setConsumeProcessor(ConsumeProcessor consumeProcessor) {
        this.consumeProcessor = consumeProcessor;
    }

    /**
     * @return the consumeRefundProcessor
     */
    public ConsumeRefundProcessor getConsumeRefundProcessor() {
        return consumeRefundProcessor;
    }

    /**
     * @param consumeRefundProcessor the consumeRefundProcessor to set
     */
    public void setConsumeRefundProcessor(ConsumeRefundProcessor consumeRefundProcessor) {
        this.consumeRefundProcessor = consumeRefundProcessor;
    }

    /**
     * @return the depositRefundProcessor
     */
    public DepositRefundProcessor getDepositRefundProcessor() {
        return depositRefundProcessor;
    }

    /**
     * @param depositRefundProcessor the depositRefundProcessor to set
     */
    public void setDepositRefundProcessor(DepositRefundProcessor depositRefundProcessor) {
        this.depositRefundProcessor = depositRefundProcessor;
    }

}
