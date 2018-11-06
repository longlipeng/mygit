/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: SapInfoConstants.java
 * Author:   12073942
 * Date:     2013-4-22 下午4:57:49
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.constants;

/**
 * SAP财务记账常量
 * 
 * @author LEO
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class SapInfoConstants {

    /**
     * 每页记录数（每次处理记录数）
     */
    public static final int PAGE_SIZE = 100;

    /**
     * 再次处理的时间偏差（相对当前时间的分钟数）
     */
    public static final int REDO_TIME_OFFSET = -120;

    /**
     * 标志位：初始
     */
    public static final String FLAG_INIT = "0";

    /**
     * 标志位：已上送（针对需要回执的记录的中间状态）
     */
    public static final String FLAG_SENT = "1";

    /**
     * 标志位：已忽略（金额为0）
     */
    public static final String FLAG_AMOUNT_ZERO = "8";

    /**
     * 标志位：已忽略（商户为联营）
     */
    public static final String FLAG_MERCHANT_JOINT = "7";

    /**
     * 标志位：已完成
     */
    public static final String FLAG_DONE = "9";

    /**
     * 需要回执
     */
    public static final String NEED_RECEIPT = "1";

    /**
     * OneByOne业务类型：SAP财务记账
     */
    public static final String OBO_BIZ_TYPE_ACCOUNT = "sap.finance.account";

    /**
     * OneByOne业务ID：唯一的
     */
    public static final String OBO_BIZ_ID_ONCE = "only.one";

    /**
     * OneByOne方法：正在运行
     */
    public static final String OBO_METHOD_RUNNING = "running";

    /**
     * 金额：0
     */
    public static final String AMOUNT_ZERO = "0";

    /**
     * 交易类型：结算单（07）
     */
    public static final String TRANS_TYPE_SETTLEMENT = "07";

    /**
     * 商户类型：联营（1）
     */
    public static final String MERCHANT_TYPE_JOINT = "1";

}
