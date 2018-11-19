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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.huateng.framework.util.ApplicationContextUtil;
import com.suning.svc.ibatis.model.VirtualCard;

/**
 * 卡选择
 * 
 * @author 孙超
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class CardSelector {

    private static final Logger log = LoggerFactory.getLogger(CardSelector.class);
    private static TransactionTemplate cardManageTransactionTemplate = (TransactionTemplate) ApplicationContextUtil
            .getBean("cardManageTransactionTemplate");

    public static VirtualCard getCard() {
        return (VirtualCard) cardManageTransactionTemplate.execute(new TransactionCallback() {
            @Override
            public Object doInTransaction(TransactionStatus arg0) {
                VirtualCard card = null;
                try {
                    card = CardProducer.getInstance().getCard();
                } catch (Exception e) {
                    log.error("取卡过程中出现错误", e);
                }
                return card;
            }
        });
    }
}
