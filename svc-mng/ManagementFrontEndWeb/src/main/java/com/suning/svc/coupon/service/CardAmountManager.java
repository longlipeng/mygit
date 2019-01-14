/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: AccountManager.java
 * Author:   秦伟
 * Date:     2013-11-5 上午9:08:09
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.service;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author 11051612
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface CardAmountManager {

    /**
     * 增加卡金额
     *
     * @param cardNO 卡号
     * @param amount 向卡中增加的金额
     * @return 卡余额
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    long addAmount(String cardNO, long amount);
    
    /**
     * 扣减卡金额
     *
     * @param cardNO 卡号
     * @param amount 向卡中增加的金额
     * @return 卡余额
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    long minusAmount(String cardNO, long amount);
}
