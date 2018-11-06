/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: CardAmountManagerImpl.java
 * Author:   秦伟
 * Date:     2013-11-5 上午9:12:04
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.service.impl;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.suning.svc.coupon.dao.VirtualCardDao;
import com.suning.svc.coupon.service.CardAmountManager;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author 11051612
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class CardAmountManagerImpl implements CardAmountManager {

    TransactionTemplate txTemplate;
    
    VirtualCardDao virtualCardDao;
     
    /*
     * (non-Jsdoc)
     * @see com.suning.svc.coupon.service.CardAmountManager#addAmount(java.lang.String, long)
     */
    @Override
    public long addAmount(final String cardNO, final long amount) {
        // 开启事务
        return (Long) txTemplate.execute(new TransactionCallback() {
            
            @Override
            public Object doInTransaction(TransactionStatus status) {
                // 增加卡余额
                virtualCardDao.addAmount(cardNO, amount);
                // 查询卡余额
                return virtualCardDao.getAvailableBalance(cardNO);
            }
        });
        
    }

    /*
     * (non-Jsdoc)
     * @see com.suning.svc.coupon.service.CardAmountManager#minusAmount(java.lang.String, long)
     */
    @Override
    public long minusAmount(final String cardNO, final long amount) {
        // 开启事务
        return (Long) txTemplate.execute(new TransactionCallback() {
            
            @Override
            public Object doInTransaction(TransactionStatus status) {
                // 扣减卡余额
                virtualCardDao.minusAmount(cardNO, amount);
                // 查询卡余额
                return virtualCardDao.getAvailableBalance(cardNO);
            }
        });
    }

    /**
     * @return the txTemplate
     */
    public TransactionTemplate getTxTemplate() {
        return txTemplate;
    }

    /**
     * @param txTemplate the txTemplate to set
     */
    public void setTxTemplate(TransactionTemplate txTemplate) {
        this.txTemplate = txTemplate;
    }

    /**
     * @return the virtualCardDao
     */
    public VirtualCardDao getVirtualCardDao() {
        return virtualCardDao;
    }

    /**
     * @param virtualCardDao the virtualCardDao to set
     */
    public void setVirtualCardDao(VirtualCardDao virtualCardDao) {
        this.virtualCardDao = virtualCardDao;
    }

}
