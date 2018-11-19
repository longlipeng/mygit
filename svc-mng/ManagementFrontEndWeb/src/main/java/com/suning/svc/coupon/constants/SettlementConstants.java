/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: SettleConstants.java
 * Author:   孙超
 * Date:     2013-10-30 下午06:50:11
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.constants;

/**
 * 结算常量
 * 
 * @author 孙超
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class SettlementConstants {

    /** 结算中 */
    public static final String SETTLEMENT_INING = "1";
    /** 结算结束 */
    public static final String SETTLEMENT_OVER = "2";
    /** 费率的key值 */
    public static final String SETTLEMENT_COMMISSION_KEY = "SETTLEMENT_COMMISSION_KEY";

    /** 有重复的结算正在进行的代码 ,注意有两份,在issueweb中也有 */
    public static final String SETTINGING_CODE = "0000";
    /** 账期内没有可以结算的订单 */
    public static final String SETTING_NO_ORDER = "0001";

    /** 结算单初始状态 */
    public static final String SETTLEMENT_INIT_STATE = "0";

    /** 结算单已匹配状态 */
    public static final String SETTLEMENT_STATUS_ONE = "1";

    /** 结算单已收票状态 */
    public static final String SETTLEMENT_STATUS_TWO = "2";

    /** 结算单已记账状态 */
    public static final String SETTLEMENT_STATUS_THREE = "3";

    /** 发送给sop的结算明细sheet页面的名字 */
    public static final String SEND_SOP_EXCEL_SHEET_SETTLMENT = "settlment";
    /** 发送给sop的结算发票sheet页面的名字 */
    public static final String SEND_SOP_EXCEL_SHEET_INVOICE = "invoice";

    /** 上传的FTP的路径 */
    public static final String SEND_SOP_PATH = "tokenCheck";

}
