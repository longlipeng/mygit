/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: CardDao.java
 * Author:   sunchao
 * Date:     2013-10-28 下午07:52:48
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.card;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huateng.framework.ibatis.model.EntitySystemParameter;
import com.huateng.framework.util.ApplicationContextUtil;
import com.huateng.framework.util.DateUtil;
import com.suning.svc.coupon.constants.CardConstants;
import com.suning.svc.coupon.service.VirtualCardService;
import com.suning.svc.ibatis.model.VirtualCard;

/**
 * 
 * 生产卡
 * 
 * @author sunchao
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class CardProducer {
    /** 数据存放 */
    private static LinkedList<VirtualCard> cardList = new LinkedList<VirtualCard>();
    private VirtualCardService cardService = (VirtualCardService) ApplicationContextUtil.getBean("virtualCardService");
    private static final Logger log = LoggerFactory.getLogger(CardProducer.class);
    private static CardProducer cardProducer = new CardProducer();

    private CardProducer() {
    }

    public static CardProducer getInstance() {
        return cardProducer;
    }

    /**
     * 获取卡号
     * 
     * @throws Exception
     */
    public VirtualCard getCard() throws Exception {
        log.info("内存中卡张数:{} ", cardList.size());
        // 不够分配
        if (cardList.size() == 0) {
            EntitySystemParameter entitySystemParameter = cardService.queryByCardKey();
            long productId = Long.valueOf(entitySystemParameter.getParameterValue());
            // 拿到的1000张卡放入内存中
            push(cardService.getCardToUse(productId));
        }
        VirtualCard usedCard = null;
        for (VirtualCard card : cardList) {
            // 时间大于24小时抛弃
            if (DateUtil.addHours(new Date(), CardConstants.CARD_MEMORY_LIFETIME).after(card.getUpdatedTime())) {
                cardList.remove(card);
                if (cardList.size() == 0) {
                    EntitySystemParameter entitySystemParameter = cardService.queryByCardKey();
                    long productId = Long.valueOf(entitySystemParameter.getParameterValue());
                    // 拿到的1000张卡放入内存中
                    push(cardService.getCardToUse(productId));
                }
                continue;
            }
            usedCard = cardList.removeFirst();
            break;
        }
        usedCard.setStatus(CardConstants.CARD_USED);
        // 更新状态为已使用
        cardService.updateVirtualCardState(usedCard.getId(), CardConstants.CARD_USED);
        log.info("成功获取了卡号为{}", usedCard.getCardNo());
        return usedCard;
    }

    /**
     * 获取数量为num的卡
     * 
     * @param num
     * @return
     * @throws Exception
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public List<VirtualCard> getVirtualCardByNum(int num) throws Exception {

        log.info("内存中卡张数:{} ", cardList.size());
        if (cardList.size() >= num) {
            for (VirtualCard card : cardList) {
                // 先把超时的都移除
                if (DateUtil.addDay(new Date(), -1).after(card.getUpdatedTime())) {
                    cardList.remove(card);
                    continue;
                }
            }
        }
        if (cardList.size() >= num) {
            return getCards(num);
        }
        EntitySystemParameter entitySystemParameter = cardService.queryByCardKey();
        long productId = Long.valueOf(entitySystemParameter.getParameterValue());
        push(cardService.getCardToUse(productId));
        return getCards(num);
    }

    private List<VirtualCard> getCards(int num) {
        List<VirtualCard> outCards = new ArrayList<VirtualCard>();
        outCards = cardList.subList(0, num);
        cardList.removeAll(outCards);
        return outCards;
    }

    private void push(List<VirtualCard> list) {
        synchronized (cardList) {
            // 放入末尾
            cardList.addAll(list);
        }
    }
}
