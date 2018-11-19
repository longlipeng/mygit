/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: CardConstants.java
 * Author:   孙超
 * Date:     2013-10-28 下午08:37:17
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.constants;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author 孙超
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class CardConstants {

    /** 卡状态 */
    public static final String CARD_INIT = "0";
    public static final String CARD_LOADED = "1";
    public static final String CARD_USED = "2";

    /** 每次的制卡数量 */
    public static final int MAKE_NUM = 1000;

    /** 批次状态 */
    // 制卡中
    public static final String MAKING_CARD = "1";
    // 制卡完成
    public static final String MAKE_CARD_OVER = "2";

    public static final String CARD_BIZ_TYPE = "COUPON";
    public static final String CARD_BIZ_ID = "ELECTRIC.CARD";

    /** 产品key值 */
    public static final String PRODUCT_KEY = "COUPON_CARD_KEY";
    public static final String PRODUCT_NAME = "电子卡";
    /** 卡号长度 */
    public static final int CARD_NO_LENGTH = 19;
    /** 卡在内存有效时间 */
    public static final int CARD_MEMORY_LIFETIME = -24;
    /** 状态为内存中，卡再次从取的时间 ，该时间绝对值要大于卡在内存里的绝对值有效时间 */
    public static final int CARD_REUSE_TIME = -26;
}
