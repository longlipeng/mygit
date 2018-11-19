/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: MdmConstants.java
 * Author:   12073942
 * Date:     2013-7-31 上午9:11:23
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.datasync.constants;

/**
 * 与MDM交互相关的常量
 * 
 * @author LEO
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class MdmConstants {

    /**
     * 返回报文中DISTRIBUTE_SYS字段：SVC
     */
    public static final String DISTRIBUTE_SYS_SVC = "SVC";

    /**
     * 返回报文中PROCESS_STAT字段：成功
     */
    public static final String PROCESS_STAT_SUCCESS = "03";

    /**
     * 返回报文中NOTES字段：成功
     */
    public static final String NOTES_SUCCESS = "接收成功!";

    /**
     * 返回报文中PROCESS_STAT字段：失败
     */
    public static final String PROCESS_STAT_FAIL = "04";

    /**
     * 返回报文中NOTES字段：失败
     */
    public static final String NOTES_FAIL = "接收失败!";

    /**
     * 供应商编码前缀：RE（继承原逻辑，为预付卡系统商户下发筛选内部供应商）
     */
    public static final String SUPPLIER_CODE_PREFIX_INNER = "RE";

    /**
     * 供应商编码前缀：00（将8位供应商编码补齐到10位）
     */
    public static final String SUPPLIER_CODE_PREFIX_OUTER = "00";

    /**
     * 供应商账户组：内部供应商
     */
    public static final String ACCT_GRP_INNER = "SN10";

    /**
     * 业务类型代码：华夏通
     */
    public static final String BIZ_TYPE_HXT = "920预付卡";

    /**
     * 业务类型代码：广场卡租赁
     */
    public static final String BIZ_TYPE_GCK_RENT = "930广场租赁";

    /**
     * 业务类型代码：广场卡联营
     */
    public static final String BIZ_TYPE_GCK_JOINT = "931广场联营";

    /**
     * 业务类型代码：C店
     */
    public static final String BIZ_TYPE_C_SHOP = "921C店";

    /**
     * 商户同步DTO中商户类型：默认
     */
    public static final String MERCHANT_TYPE_DEFAULT = "0";

    /**
     * 商户同步DTO中商户类型：联营
     */
    public static final String MERCHANT_TYPE_JOINT = "1";

    /**
     * 商户同步DTO中商户类型：内部
     */
    public static final String MERCHANT_TYPE_INNER = "2";

    /**
     * 商户同步DTO中数据状态：有效
     */
    public static final String DATA_STATE_ENABLED = "1";

    /**
     * 商户同步DTO中数据状态：无效
     */
    public static final String DATA_STATE_DISABLED = "0";

    /**
     * 商户同步DTO中同步标识：同步到临时表
     */
    public static final String SYNCH_FLAG_TEMP = "0";

    /**
     * 商户同步DTO中同步标识：立即为华夏通同步
     */
    public static final String SYNCH_FLAG_HXT = "1";

    /**
     * 商户编码长度
     */
    public static final int MERCHANT_CODE_LENGTH = 15;

}
