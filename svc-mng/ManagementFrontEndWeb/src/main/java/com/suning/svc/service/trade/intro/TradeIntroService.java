/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: TradeIntroService.java
 * Author:   12073942
 * Date:     2013-10-18 下午4:37:38
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.service.trade.intro;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huateng.framework.dao.CommonsDAO;
import com.suning.svc.constants.TradeIntroConstants;
import com.suning.svc.coupon.constants.SequenceContansts;
import com.suning.svc.ibatis.dao.TradeItemTempDAO;
import com.suning.svc.ibatis.model.TradeItemTemp;
import com.suning.svc.service.trade.intro.dto.TradeIntroDto;
import com.suning.svc.service.trade.intro.result.TradeIntroResult;

/**
 * 交易接入处理服务
 * 
 * @author LEO
 */
public class TradeIntroService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TradeIntroService.class);

    /**
     * 交易行项临时表DAO
     */
    private TradeItemTempDAO tradeItemTempDAO;

    /**
     * 通用DAO
     */
    private CommonsDAO commonsDAO;

    /**
     * 
     * 保存记录
     * 
     * @param dto 交易接入DTO
     * @return 业务处理结果
     */
    public TradeIntroResult saveRecord(TradeIntroDto dto) {
        TradeIntroResult rlt = new TradeIntroResult();
        rlt.setTradeIntroDto(dto);
        try {
            TradeItemTemp record = new TradeItemTemp();
            // ID为流水号取自SEQ
            record.setId(Long.valueOf(commonsDAO.getNextValueOfSequenceBySequence(SequenceContansts.SEQ_TRADE_ITEM_SN)));
            // DTO字段共11个
            record.setPartner(dto.getPartner());
            record.setMchtCode(dto.getMchtCode());
            record.setTradeType(dto.getTradeType());
            record.setDirection(dto.getBillType());
            record.setCouponNo(dto.getCouponNo());
            record.setItemOrderNo(dto.getItemOrderNo());
            record.setTradeTime(dto.getTradeTime());
            record.setAmount(Long.valueOf(dto.getAmount()));
            record.setOrderNo(dto.getOrderNo());
            record.setItemDes(dto.getItemDes());
            record.setItemRemark(dto.getItemRemark());
            // 设置标记
            record.setStatus(TradeIntroConstants.STATUS_INIT);
            // 设置初始计数值0
            record.setCounter(0);
            record.setCreatedTime(new Date());
            record.setUpdatedTime(new Date());
            tradeItemTempDAO.insert(record);
            LOGGER.info("交易行项[{}]保存成功", getDtoSummary(dto));
        } catch (Exception e) {
            rlt.fail("", e.getMessage());
            LOGGER.error("交易行项[{}]保存失败：{}", getDtoSummary(dto), e.getMessage());
        }
        return rlt;
    }

    /**
     * 
     * 获取DTO摘要
     * 
     * @param dto 交易接入DTO
     * @return DTO摘要
     */
    private String getDtoSummary(TradeIntroDto dto) {
        StringBuilder sb = new StringBuilder();
        sb.append("Partner:").append(dto.getPartner());
        sb.append("|MchtCode:").append(dto.getMchtCode());
        sb.append("|TradeType:").append(dto.getTradeType());
        sb.append("|BillType:").append(dto.getBillType());
        sb.append("|CouponNo:").append(dto.getCouponNo());
        sb.append("|ItemOrderNo:").append(dto.getItemOrderNo());
        sb.append("|TradeTime:").append(dto.getTradeTime());
        sb.append("|Amount:").append(dto.getAmount());
        return sb.toString();
    }

    /**
     * @param tradeItemTempDAO the tradeItemTempDAO to set
     */
    public void setTradeItemTempDAO(TradeItemTempDAO tradeItemTempDAO) {
        this.tradeItemTempDAO = tradeItemTempDAO;
    }

    /**
     * @param commonsDAO the commonsDAO to set
     */
    public void setCommonsDAO(CommonsDAO commonsDAO) {
        this.commonsDAO = commonsDAO;
    }

}
