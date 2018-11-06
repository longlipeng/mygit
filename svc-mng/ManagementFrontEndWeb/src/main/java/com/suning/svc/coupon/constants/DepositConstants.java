/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: DepositConstants.java
 * Author:   13040443
 * Date:     2013-10-30 下午02:02:09
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.constants;

/**
 * 充值，充退常量类
 * 
 * @author yanbin
 */
public class DepositConstants {

    /** 充值订单状态：正常 */
    public static final String DEPOSIT_ORDER_STATUS_NORMAL = "1";

    /** 充值订单状态：完全充退 */
    public static final String DEPOSIT_ORDER_STATUS_REFUND = "2";

    /** 充值订单状态：部分充退 */
    public static final String DEPOSIT_ORDER_STATUS_PART_REFUND = "3";

    /** 发票需求关联的类型：充值 */
    public static final String INVOICE_REF_TYPE_DEPOSIT = "1";
    /** 发票需求关联类型：充退 */
    public static final String INVOICE_REF_TYPE_REFUND = "2";

    /** 发票需求状态：已完成 */
    public static final String INVOICE_REQUIRE_STATUS_YES = "1";
    /** 发票需求状态：未完成 */
    public static final String INVOICE_REQUIRE_STATUS_NO = "0";

    /** 发票方向：正常票 */
    public static final String INVOICE_REQUIRE_ASPECT_NORMAL = "1";
    /** 发票方向：红票 */
    public static final String INVOICE_REQUIRE_ASPECT_RED = "0";

    /** 数据字典获取平台商户号 */
    public static final String PARAMETER_CODE = "PLATFORM_CUSTOMER_ID";
    public static final String ENTITY_ID = "5101";
    public static final String FATHER_ENTITY_ID = "00000000";

}
