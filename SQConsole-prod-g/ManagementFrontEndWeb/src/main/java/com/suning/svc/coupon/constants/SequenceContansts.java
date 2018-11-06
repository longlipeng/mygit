/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: SequenceContansts.java
 * Author:   秦伟
 * Date:     2013-10-30 下午8:03:01
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
 * @author 11051612
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class SequenceContansts {

    /** 消费表主键的生成序列 */
    public static final String CONSUMER_ORDER_ID_SEQ = "SEQ_CONSUMER_ORDER";

    /** 开票需求主键序列 */
    public static final String SEQ_INVOICE_REQUIREMENT = "SEQ_INVOICE_REQUIREMENT";

    /** 充值订单主键序列 */
    public static final String SEQ_DEPOSIT_ORDER = "SEQ_DEPOSIT_ORDER";

    /** 充退订单主键序列 */
    public static final String SEQ_DEPOSIT_REFUND_ORDER = "SEQ_DEPOSIT_REFUND_ORDER";

    /** 处理批次主键序列 */
    public static final String SEQ_DEAL_BATCH = "SEQ_DEAL_BATCH";

    /** 发票临时表主键 */
    public static final String SEQ_INVOICETEMP_SN = "SEQ_INVOICETEMP_SN";

    /** 卡主键序列 */
    public static final String SEQ_CP_VIRTUAL_CARD = "SEQ_CP_VIRTUAL_CARD";

    /** 卡批次主键序列 */
    public static final String SEQ_CARD_BATCH = "SEQ_CARD_BATCH";

    /** 结算批次主键序列 */
    public static final String SEQ_SETTLE_BATCH = "SEQ_SETTLE_BATCH";

    /** 结算单主键序列 */
    public static final String SEQ_SETTLEMENT = "SEQ_SETTLEMENT";

    /** 汇总批次主键序列 */
    public static final String SEQ_SUM_ORDER_BATCH = "SEQ_SUM_ORDER_BATCH";

    /** 汇总批次结果主键序列 */
    public static final String SEQ_SUM_ORDER_RESULT = "SEQ_SUM_ORDER_RESULT";
    
    /** 交易行项流水号序列 */
    public static final String SEQ_TRADE_ITEM_SN = "SEQ_TRADE_ITEM_SN";
    /**记账流水号*/
    public static final String SEQ_TRANS_SSN = "SVC_STL.SEQ_TRANS_SSN";

}
