/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: Constants.java
 * Author:   11051612
 * Date:     2013-10-29 上午9:23:40
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.constants;

/**
 * 交易相关常量
 * 
 * @author 11051612
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class TradeConstants {

    /**
     * 交易类型
     * 
     * @author 11051612
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    public static class TradeType {
        /**
         * 充值交易
         */
        public static final String DEPOSIT = "0007";

        /**
         * 消费交易
         */
        public static final String CONSUME = "0008";

        /** 扒券充值 */
        public static final String SHARE_DEPOSIT = "0010";
    }

    /**
     * 交易方向
     * 
     * @author 11051612
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    public static class TradeDirection {
        /**
         * 正向
         */
        public static final String FORWARD = "1";

        /**
         * 反向
         */
        public static final String OPPOSIT = "-1";
    }

    /**
     * 退货类型
     * 
     * @author 11051612
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    public static class RefundStatus {
        /**
         * 1正常
         */
        public static final String NO_REFUND = "1";

        /**
         * 2部分退货
         */
        public static final String PARTLY_REFUND = "2";

        /**
         * 3全额退货
         */
        public static final String COMPLETELY_REFUND = "3";
    }

    /**
     * 订单类型
     * 
     * @author yanbin
     */
    public static class TradeOrderType {
        /** 订单类型：充值订单 */
        public static final String ORDER_TYPE_DEPOSIT = "1";
        
        /** 订单类型：充退订单 */
        public static final String ORDER_TYPE_DEPOSIT_REFUND = "2";
        
        /** 订单类型：消费订单 */
        public static final String ORDER_TYPE_CONSUME = "3";
    }

    /**
     * 容差金额：2毛
     */
    public static final long MONEY_TOLERANCE = 20L;
}
