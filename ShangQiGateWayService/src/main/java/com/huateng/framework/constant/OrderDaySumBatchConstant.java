/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: OrderDaySumBatchConstant.java
 * Author:   13010154
 * Date:     2013-11-1 上午08:53:39
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.huateng.framework.constant;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author 13010154
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class OrderDaySumBatchConstant {
    /**
     * 消费标示
     */
    public final static String CONSUME_ORDER = "1";
    /**
     * 退货标识
     */
    public final static String REFOUND_ORDER = "-1";
    /**
     * 12交易类型为充值
     */
    public final static String TXN_TYPE_DEPOSIT = "12";
    /**
     * 13交易类型为充退
     */
    public final static String TXN_TYPE_DEPOSITREFOUND = "13";
    /**
     * 14交易类型为消费
     */
    public final static String TXN_TYPE_CONSUME = "14";
    /**
     * 15交易类型为退货
     */
    public final static String TXN_TYPE_REFOUND = "15";
    /**
     * 1：正在处理  2：处理结束
     */
    public final static String SUM_ORDER_BATCH_DOING_STATUS = "1";

    /**
     * 1：正在处理  2：处理结束
     */
    public final static String SUM_ORDER_BATCH_DONE_STATUS = "2";
    /**
     * 前缀
     */
    public final static String TRADE_TIME_PREFIX = "000000";
    /**
     * 后缀
     */
    public final static String TRADE_TIME_SUFFIX = "235959";
    /**
     * 发行机构
     */
    public static final String ISSUE_ID="5101";
    /**
     * 付款
     */
    public static final String PAYMENT="9902";
    /**
     * 状态
     */
    public static final String FLAG="0";
    /**
     * 是否需要RECEIPT
     */
    public static final String NEED_RECEIPT="1";
    /**
     * 未上传
     */
    public static final String INIT_STATUS="0";
    /**
     * 已上传
     */
    public static final String SUCCESS_STATUS="1";
    /**
     * 上传失败
     */
    public static final String FAIL_STATUE="2";
    
    
    /**
     * OneByOne业务类型：SAP批次
     */
    public static final String OBO_BIZ_TYPE_BATCH = "sap.order.batch.sum";

    /**
     * OneByOne业务ID：唯一的
     */
    public static final String OBO_BIZ_ID_ONCE = "bath.only.one";

    /**
     * OneByOne方法：正在运行
     */
    public static final String OBO_METHOD_RUNNING = "bath.running";
}
