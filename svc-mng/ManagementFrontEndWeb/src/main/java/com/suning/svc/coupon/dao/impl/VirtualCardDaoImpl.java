/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: VirtualCardDaoImpl.java
 * Author:   秦伟
 * Date:     2013-11-5 上午9:18:33
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.util.Assert;

import com.suning.svc.core.common.BaseException;
import com.suning.svc.coupon.constants.TradeConstants;
import com.suning.svc.coupon.dao.VirtualCardDao;
import com.suning.svc.ibatis.model.VirtualCard;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author 11051612
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class VirtualCardDaoImpl extends SqlMapClientDaoSupport implements VirtualCardDao{

    private static final String NAMESPACE = VirtualCard.class.getSimpleName();
    /* (non-Jsdoc)
     * @see com.suning.svc.coupon.dao.VirtualCardDao#lockRecord(java.lang.String)
     */
    @SuppressWarnings("rawtypes")
    @Override
    public void lockRecord(String cardNO) {
        Assert.hasLength(cardNO, "卡号不能为空");
        Map<String, String> params = new HashMap<String, String>();
        params.put("cardNO", cardNO);
        List cardNOs = (List) getSqlMapClientTemplate().queryForList(NAMESPACE + ".lockRecord", params);
        if(cardNOs.size() == 0){
            throw new BaseException("卡号[" + cardNO + "]不存在");
        }
    }

    /* (non-Jsdoc)
     * @see com.suning.svc.coupon.dao.VirtualCardDao#addAmount(java.lang.String, long)
     */
    @Override
    public void addAmount(String cardNO, long amount) {
        Assert.isTrue(amount > 0, "卡增加的金额必须>0");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("cardNO", cardNO);
        params.put("amount", amount);
        getSqlMapClientTemplate().update(NAMESPACE + ".addAmount", params);
    }

    /* (non-Jsdoc)
     * @see com.suning.svc.coupon.dao.VirtualCardDao#minusAmount(java.lang.String, long)
     */
    @Override
    public void minusAmount(String cardNO, long amount) {
        Assert.isTrue(amount > 0, "卡扣减的金额必须<0");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("cardNO", cardNO);
        params.put("amount", amount);
        params.put("tolerance", TradeConstants.MONEY_TOLERANCE);
        int count = getSqlMapClientTemplate().update(NAMESPACE + ".minusAmount", params);
        if(count == 0){
            throw new BaseException("余额不足");
        }
    }

    /* (non-Jsdoc)
     * @see com.suning.svc.coupon.dao.VirtualCardDao#getAvailableBalance(java.lang.String)
     */
    @Override
    public Long getAvailableBalance(String cardNO) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("cardNO", cardNO);
        return (Long) getSqlMapClientTemplate().queryForObject(NAMESPACE + ".getBalance", params);
    }

}
