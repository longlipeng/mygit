package com.huateng.framework.constant;

/**
 * 字典表使用
 * 
 * @author liming.feng
 *
 */
public class SystemInfoConstants {
	
	/**
     * 订单状态
     */
    public final static String SELL_ORDER_STATE  = "209";
    
    /**
     * 库存调拨订单状态
     */
    public final static String STOCK_TRANSFER_ORDER_STATE  = "313";
    
    /**
     * 营销机构订单类型
     */
    public final static String SELL_ORDER_TYPE   = "205";
    
    /**
     * 制卡原因
     */
    public final static String MAKE_CARD_REASON   = "200";
    
    /**
     * 送货方式
     */
    public final static String DELIVERY_MEANS   = "202";
    
    /**
     * 支付方式
     */
    public final static String PAYMENT_TERM   = "207";
    
    /**
     * 持卡人分类
     */
    public final static String CARD_HOLDER_TYPE   = "103";

    //以下信息为系统参数信息
    
    /**
     * 充值有效期
     */
    public final static String  CREDIT_VALIDITY_PERIOD="CREDIT_VALIDITY_PERIOD";
    /**
     * 结算分组名称
     */
    public final static String SETTLE_GROUP_NAME_MAXIMUM="SETTLE_GROUP_NAME_MAXIMUM";	
    /**
     * 每包包装数量
     */
    public final static String  PACKAGE_SIZE="PACKAGE_SIZE";	
    
    /**
     * 订单卡数量
     */
    public final static String  ORDER_CARD_MAXIMUM="ORDER_CARD_MAXIMUM";	
    /**
     * 每次导出数据最大条数
     */
    public final static String  EXPORT_DATA_MAXIMUM	="EXPORT_DATA_MAXIMUM";
    /**
     * 卡片延期最大月数
     */
    public final static String  EXTENSION_MAXIMUM_MONTH	="EXTENSION_MAXIMUM_MONTH";
    /**
     * 延期操作最小月数
     */
    public final static String  EXTENSION_MINIMUM_MONTH	="EXTENSION_MINIMUM_MONTH";
    /**
     * 卡片延期费用
     */
    public final static String  EXTENSION_FEE	="EXTENSION_FEE";
    /**
     * 验卡
     */
    public final static String  CHECK_CARD	="CHECK_CARD";
    /**
     * 磁道重写费用
     */
    public final static String  REWRITE_MAGNETIC_FEE="REWRITE_MAGNETIC_FEE";
    /**
     * 充值卡挂失费用
     */
    public final static String  CARD_BLOCK_FEE="CARD_BLOCK_FEE";
    /**
     * 充值卡解挂费用
     */
    public final static String CARD_UNBLOCK_FEE	="CARD_UNBLOCK_FEE";
    /**
     * 充值卡锁定费用
     */
    public final static String  CARD_LOCK_FEE="CARD_LOCK_FEE";
    /**
     * 充值卡解锁费用
     */
    public final static String CARD_UNLOCK_FEE	="CARD_UNLOCK_FEE";
    /**
     * 充值卡冻结费用
     */
    public final static String  CARD_FRZZN_FEE="CARD_FRZZN_FEE";
    /**
     * 充值卡解冻费用
     */
    public final static String CARD_UNFRZZN_FEE	="CARD_UNFRZZN_FEE";
    /**
     * 卡安全设置费用
     */
    public final static String CARD_SECURITY_SETTING_FEE="CARD_SECURITY_SETTING_FEE";
    /**
     * 卡交易信息查询费用
     */
    public final static String  QUERY_CARD_TRANSACTION_FEE="QUERY_CARD_TRANSACTION_FEE";	
    /**
     * 卡密重置费用
     */
    public final static String  RESET_CARD_PIN_FEE	="RESET_CARD_PIN_FEE";
    /**
     * 充值卡卡类型（用于流水)
     */
    public final static String CREDIT_CARD_TYPE="CREDIT_CARD_TYPE";
    /**
     * 礼品卡卡类型（用于流水）
     */
    public final static String  GIFT_CARD_TYPE="GIFT_CARD_TYPE";
}
