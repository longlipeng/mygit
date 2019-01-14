/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: TransDetailRecvService.java
 * Author:   12073942
 * Date:     2013-10-18 下午2:54:36
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.datasync.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.suning.svc.constants.PartnerConstants;
import com.suning.svc.coupon.constants.TradeConstants;
import com.suning.svc.datasync.constants.MdmConstants;
import com.suning.svc.datasync.utils.SignUtil;
import com.suning.svc.datasync.xmlbean.trans.detail.Item;
import com.suning.svc.datasync.xmlbean.trans.detail.ReceivedBodyBean;
import com.suning.svc.ibatis.dao.PartnerDAO;
import com.suning.svc.ibatis.model.Partner;
import com.suning.svc.ibatis.model.PartnerExample;
import com.suning.svc.service.trade.intro.TradeIntroService;
import com.suning.svc.service.trade.intro.dto.TradeIntroDto;
import com.suning.svc.service.trade.intro.result.TradeIntroResult;

/**
 * SAC/SAP交易明细接收服务
 * 
 * @author LEO
 */
public class TransDetailRecvService implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransDetailRecvService.class);

    /**
     * 合作伙伴-签名密钥键值对
     */
    private static final Map<String, String> PARTNER_MAP = new HashMap<String, String>();

    /**
     * 合作伙伴表DAO
     */
    private PartnerDAO partnerDAO;

    /**
     * 交易接入服务
     */
    private TradeIntroService tradeIntroService;

    /**
     * 处理报文主体
     * 
     * @param bodyBean 报文主体
     */
    public void processBodyBean(ReceivedBodyBean bodyBean) {
        String partner = bodyBean.getPartner();
        String signKey = PARTNER_MAP.get(partner);
        if (signKey == null) {
            LOGGER.error("无效的合作伙伴：{}", partner);
            return;
        }
        List<Item> items = bodyBean.getDetails().getItemList();
        for (Item item : items) {
            String itemSummary = getItemSummary(partner, item);
            if (checkSign(item, signKey)) {
                LOGGER.info("交易请求[{}]验签成功", itemSummary);
                TradeIntroDto dto = new TradeIntroDto();
                dto.setPartner(partner);
                if (StringUtils.length(item.getMchtCode()) == 4) {
                    // 内部供应商加RE
                    dto.setMchtCode(MdmConstants.SUPPLIER_CODE_PREFIX_INNER + item.getMchtCode());
                } else if (StringUtils.length(item.getMchtCode()) == 8) {
                    // 外部供应商加00
                    dto.setMchtCode(MdmConstants.SUPPLIER_CODE_PREFIX_OUTER + item.getMchtCode());
                } else {
                    dto.setMchtCode(item.getMchtCode());
                }
                dto.setTradeType(item.getTradeType());
                if (StringUtils.equals(item.getTradeType(), TradeConstants.TradeType.SHARE_DEPOSIT)) {
                    dto.setBillType(TradeConstants.TradeDirection.OPPOSIT);
                } else {
                    dto.setBillType(item.getBillType());
                }
                dto.setCouponNo(item.getCouponNo());
                dto.setItemOrderNo(item.getItemOrderNo());
                dto.setTradeTime(item.getTradeTime());
                dto.setAmount(item.getAmount());
                dto.setOrderNo(item.getOrderNo());
                dto.setItemDes(item.getItemDes());
                dto.setItemRemark(item.getItemRemark());
                // 传入交易接入服务处理
                TradeIntroResult rlt = tradeIntroService.saveRecord(dto);
                if (rlt.isSuccess()) {
                    LOGGER.info("交易请求[{}]处理成功", itemSummary);
                } else {
                    LOGGER.error("交易请求[{}]处理失败：{}", itemSummary, rlt.getMessage());
                }
            } else {
                LOGGER.error("交易请求[{}]验签失败", itemSummary);
            }
        }
    }

    /**
     * 
     * 验签
     * 
     * @param item 行项
     * @param signKey 签名密钥
     * @return 验签是否通过
     */
    private static boolean checkSign(Item item, String signKey) {
        // StringBuilder sb = new StringBuilder();
        Map<String, String> paramValues = new HashMap<String, String>();
        if (!StringUtils.isEmpty(item.getMchtCode())) {
            // sb.append(item.getMchtCode());
            paramValues.put("mchtCode", item.getMchtCode());
        }
        if (!StringUtils.isEmpty(item.getTradeType())) {
            // sb.append(item.getTradeType());
            paramValues.put("tradeType", item.getTradeType());
        }
        if (!StringUtils.isEmpty(item.getBillType())) {
            // sb.append(item.getBillType());
            paramValues.put("billType", item.getBillType());
        }
        if (!StringUtils.isEmpty(item.getCouponNo())) {
            // sb.append(item.getCouponNo());
            paramValues.put("couponNo", item.getCouponNo());
        }
        if (!StringUtils.isEmpty(item.getItemOrderNo())) {
            // sb.append(item.getItemOrderNo());
            paramValues.put("itemOrderNo", item.getItemOrderNo());
        }
        if (!StringUtils.isEmpty(item.getTradeTime())) {
            // sb.append(item.getTradeTime());
            paramValues.put("tradeTime", item.getTradeTime());
        }
        if (!StringUtils.isEmpty(item.getAmount())) {
            // sb.append(item.getAmount());
            paramValues.put("amount", item.getAmount());
        }
        if (!StringUtils.isEmpty(item.getOrderNo())) {
            // sb.append(item.getOrderNo());
            paramValues.put("orderNo", item.getOrderNo());
        }
        if (!StringUtils.isEmpty(item.getItemDes())) {
            // sb.append(item.getItemDes());
            paramValues.put("itemDes", item.getItemDes());
        }
        if (!StringUtils.isEmpty(item.getItemRemark())) {
            // sb.append(item.getItemRemark());
            paramValues.put("itemRemark", item.getItemRemark());
        }
        // return StringUtils.equalsIgnoreCase(item.getSign(), SignUtil.sign(sb.toString(), signKey));
        String reSign = SignUtil.sign(paramValues, signKey);
        return StringUtils.equalsIgnoreCase(item.getSign(), reSign);
    }

    /**
     * 
     * 获取行项摘要
     * 
     * @param partner 合作伙伴
     * @param item 行项
     * @return 行项摘要
     */
    private String getItemSummary(String partner, Item item) {
        StringBuilder sb = new StringBuilder();
        sb.append("Partner:").append(partner);
        sb.append("|MchtCode:").append(item.getMchtCode());
        sb.append("|TradeType:").append(item.getTradeType());
        sb.append("|BillType:").append(item.getBillType());
        sb.append("|CouponNo:").append(item.getCouponNo());
        sb.append("|ItemOrderNo:").append(item.getItemOrderNo());
        sb.append("|TradeTime:").append(item.getTradeTime());
        sb.append("|Amount:").append(item.getAmount());
        sb.append("|Sign:").append(item.getSign());
        return sb.toString();
    }

    /**
     * @param partnerDAO the partnerDAO to set
     */
    public void setPartnerDAO(PartnerDAO partnerDAO) {
        this.partnerDAO = partnerDAO;
    }

    /**
     * @param tradeIntroService the tradeIntroService to set
     */
    public void setTradeIntroService(TradeIntroService tradeIntroService) {
        this.tradeIntroService = tradeIntroService;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub
        initParternerMap();
    }

    /**
     * 初始化合作伙伴-密钥键值对
     */
    @SuppressWarnings("unchecked")
    private void initParternerMap() {
        // 查询所有有效的合作伙伴
        PartnerExample example = new PartnerExample();
        example.createCriteria().andStatusEqualTo(PartnerConstants.STATUS_ENABLED);
        List<Partner> allPartners = partnerDAO.selectByExample(example);
        for (Partner partner : allPartners) {
            // 逐个装入内存对象
            PARTNER_MAP.put(partner.getCode(), partner.getKey());
        }
        LOGGER.info("初始化完成，共载入{}个有效的合作伙伴", PARTNER_MAP.size());
    }

}
