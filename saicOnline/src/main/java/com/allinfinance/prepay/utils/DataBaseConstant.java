package com.allinfinance.prepay.utils;

/**
 * 数据库常量定义.
 * 
 * @author jiagang
 */
public class DataBaseConstant {

    /**
     * 数据类型
     */
    public final static String DATA_TYPE_YES = "1";

    public final static String DATA_TYPE_NO = "0";
    /** 数据状态类型 0：删除. */
    public final static String DATA_STATE_DELETE = "0";

    /** 数据状态类型 1：正常. */
    public final static String DATA_STATE_NORMAL = "1";
    
    /** 2是营销机构 */
    public final static String SELLER_RESOURCE_RANK = "2";

    /** 运营中（有效）1 */
    public final static String SHOP_DATA_STATE_NORMAL = "1";

    /** 门店切换（门店代码对应） 2 */
    public final static String SHOP_DATA_STATE_CHANGE = "2";

    /** 门店停用（无效）3 */
    public final static String SHOP_DATA_STATE_DISABLE = "3";

    /** 有效状态类型 0：无效. */
    public final static String STATE_INACTIVE = "0";

    /** 有效状态类型 1：有效. */
    public final static String STATE_ACTIVE = "1";

    /** 商户复核状态1：已复核. */
    public final static String CHECK_STATE_CHECK = "1";

    /** 商户复核状态0：未复核. */
    public final static String CHECK_STATE_NONCHECK = "0";

    /** 联系人关联类型 1：客户. */
    public final static String CONTACT_REF_TYPE_CUSTOMER = "1";

    /** 联系人关联类型 2：集团商户. */
    public final static String CONTACT_REF_TYPE_MERCHANTGROUP = "2";

    /** 联系人关联类型 3：商户. */
    public final static String CONTACT_REF_TYPE_MERCHANT = "3";

    /** 联系人关联类型 4：门店. */
    public final static String CONTACT_REF_TYPE_SHOP = "4";

    /** 制卡状态 1：无卡. */
    public final static String MAKE_CARD_STATE_NOCARD = "1";

    /** 制卡状态 2：制卡中. */
    public final static String MAKE_CARD_STATE_MAKING = "2";

    /** 制卡状态 3：制卡成功. */
    public final static String MAKE_CARD_STATE_SUCCESS = "3";

    /** 制卡状态 4：制卡失败. */
    public final static String MAKE_CARD_STATE_FAILTURE = "4";

    /** PIN状态 1：无PIN. */
    public final static String PIN_STATE_NOCARD = "1";

    /** PIN状态 2：制PIN中. */
    public final static String PIN_STATE_MAKING = "2";

    /** PIN状态 3：制PIN成功. */
    public final static String PIN_STATE_SUCCESS = "3";

    /** PIN状态 4：制PIN失败. */
    public final static String PIN_STATE_FAILTURE = "4";

    /** 订单类型 1：充值卡订单. */
    public final static String ORDER_TYPE_RELOADABLECARD = "1";

    /** 订单类型 2：充值订单. */
    public final static String ORDER_TYPE_CREDIT = "2";

    /** 订单类型 3：礼品卡库存订单. */
    public final static String ORDER_TYPE_GIFTCARDSTOCK = "3";

    /** 订单类型 4：礼品卡销售订单. */
    public final static String ORDER_TYPE_GIFTCARDSALE = "4";

    /** 订单类型 5：礼品卡换卡订单. */
    public final static String ORDER_TYPE_GIFTCARDCHANGE = "5";

    /** 订单类型 6：充值卡换卡订单. */
    public final static String ORDER_TYPE_RELOADABLECARDCHANGE = "6";

    /** 订单类型 7：礼品卡销售退单订单. */
    public final static String ORDER_TYPE_GIFTCARDSENDBACK = "7";

    /** 订单批量停用操作类型 1：录入. */
    public final static String ORDER_BATCH_DISABLE_OPERATETYPE_INPUT = "1";

    /** 订单批量停用操作类型 2：确定. */
    public final static String ORDER_BATCH_DISABLE_OPERATETYPE_CONFIRM = "2";

    /** 订单批量挂失解挂操作类型 1：挂失. */
    public final static String ORDER_BATCH_BLOCK_OPERATETYPE_REPORTLOSS = "1";

    /** 订单批量挂失解挂操作类型 2：解挂. */
    public final static String ORDER_BATCH_BLOCK_OPERATETYPE_RELIEVEREPORTLOSS = "2";

    /** 短信通知流水表通知标识 1：已通知. */
    public final static String INFORM_TXN_CALL_FLAG_INFORM = "1";

    /** 短信通知流水表通知标识 2：未通知. */
    public final static String INFORM_TXN_CALL_FLAG_NOT_INFORM = "2";

    /** 短信通知流水表通知类型 1：交易. */
    public final static String INFORM_TXN_CALL_TYPE_TRANS = "1";

    /** 短信通知流水表通知类型 2：批处理. */
    public final static String INFORM_TXN_CALL_TYPE_BANTH = "2";

    /** 短信通知流水表通知类型 3：月报. */
    public final static String INFORM_TXN_CALL_TYPE_MONTHINFORM = "3";

    /** 短信通知流水表通知类型 4：解锁通知'. */
    public final static String INFORM_TXN_CALL_TYPE_RELAX = "4";

    /** 短信通知流水表通知类型 5：重置卡密. */
    public final static String INFORM_TXN_CALL_TYPE_CHANGEPWD = "5";

    /** 销售合同类型1: 发行机构和营销机构合同 */
    public final static String CONTRACT_ISSUER = "1";
    /** 销售合同类型2: 营销机构和营销机构合同 */
    public final static String CONTRACT_SELLER = "2";
    /** 销售合同类型3: 营销机构和客户合同 */
    public final static String CONTRACT_CUSTOMER = "3";
    /** 销售合同类型4: 客户合同模板 */
    public final static String CONTRACT_TEMPLATE = "0";
    //
    // /** 分库库存卡状态20：已分配. */
    // public final static Short ISSURESTOCK_CARD_STOCK_STATE_DISTRIBUTED = 20;
    //
    // /** 分库库存卡状态21：已售出. */
    // public final static Short ISSURESTOCK_CARD_STOCK_STATE_SELDEN = 21;
    //
    // /** 分库库存卡状态22：在库存. */
    // public final static Short ISSURESTOCK_CARD_STOCK_STATE_STOCK = 22;
    //
    // /** 卡片信息卡激活状态1：已激活. */
    // public final static short CARD_ACT_STAT_ACT = 1;
    //
    // /** 卡片信息卡激活状态1：未激活. */
    // public final static short CARD_ACT_STAT_NONACT = 0;
    //
    // /** 卡分库操作信息表状态_101 银箱配卡. */
    // public final static Integer CARD_STOCK_OPERATER_TYPE_101 = 101;
    //
    // /** 卡分库操作信息表状态_102 银箱卡片售出. */
    // public final static Integer CARD_STOCK_OPERATER_TYPE_102 = 102;
    //
    // /** 卡分库操作信息表状态_103 银箱卡片充值. */
    // public final static Integer CARD_STOCK_OPERATER_TYPE_103 = 103;
    //
    // /** 卡分库操作信息表状态_104 银箱卡片激活. */
    // public final static Integer CARD_STOCK_OPERATER_TYPE_104 = 104;
    //
    // /** 卡分库操作信息表状态_105 银箱卡片退卡. */
    // public final static Integer CARD_STOCK_OPERATER_TYPE_105 = 105;
    //
    // /** 卡分库操作信息表状态_106 银箱盘点. */
    // // public final static Integer CARD_STOCK_OPERATER_TYPE_106 = 106;
    //
    // 规则删除
    public final static String RULE_STATE_DELETE = "0";
    // 规则新建
    public final static String RULE_STATE_NEW = "1";
    // 规则启用
    public final static String RULE_STATE_ENABLE = "2";

    // 消费合同 收单机构
    public final static String CONTRACT_CONSUMER = "1";
    // 消费合同 商户
    public final static String CONTRACT_MERCHANT = "2";

    /***
     * 营销合同(与发卡机构)
     */
    public final static String SELL_CONTRACT_ISSUER = "1";
    /**
     * 营销合同(与营销机构)
     */
    public final static String SELL_CONTRACT_SELLER = "2";
    /**
     * 营销合同(与客户)
     */
    public final static String SELL_CONTRACT_CUSTOMER = "3";
    /**
     * 代发卡合同(发卡机构与发卡机构)
     */
    public final static String LOYALTY_CONTRACT_SELLER = "4";

    /** 默认是否默认 */
    public final static String DEFAULT_FLAG_YES = "1";

    public final static String DEFAULT_FLAG_NO = "0";

    /** 发票状态 :0未开票 */
    public final static String INVOICE_NOT_MAKE = "0";
    /** 发票状态 :1已开票 */
    public final static String INVOICE_HAS_MAKE = "1";
    /** 发票状态 :2取消开票 */
    public final static String INVOICE_CANCEL_MAKE = "2";
    /** 普通发票 */
    public final static String INVOICE_TYPE_TWO = "2";
    /** 增值税发票 */
    public final static String INVOICE_TYPE_ONE = "1";

    /** 发票地址订送方式 :送货上门1 */
    public final static String DELIVERY_OPERATION_SEND = "1";
    /** 发票地址订送方式 :上门取货2 */
    public final static String DELIVERY_OPERATION_GET = "2";

    /** 默认失效日期 最大时间 */
    public final static String DEFAULT_EXPIRY_DATE = "29991231";

    /** 订单卡数量限制 最大值 */
    public final static String ORDER_CARD_MAXIMUM = "500";

    /** 订单卡数量限制 参数名称 */
    public final static String ORDER_CARD_MAXIMUM_NAME = "ORDER_CARD_MAXIMUM";

    /** 规则状态 启用 */
    public final static String RULE_STATE_ENABLED = "2";

    /** 营销机构状态 0:无效 1:有效 */
    public final static String SELLER_STATE_INACTIVE = "0";
    public final static String SELLER_STATE_ACTIVE = "1";

    /** 营销机构合同状态 0:无效 1:有效 */
    public final static String CONTRACT_STATE_INACTIVE = "0";
    public final static String CONTRACT_STATE_ACTIVE = "1";

    /** 客户状态 0:无效 1:有效 */
    public final static String CUST_STATE_INACTIVE = "0";
    public final static String CUST_STATE_ACTIVE = "1";

    /** 用户状态 0:无效 1:有效 */
    public final static String USER_STATE_INACTIVE = "0";
    public final static String USER_STATE_ACTIVE = "1";

    /** 激活状态 0:未激活 1：已激活 2：激活中 3：激活失败 */
    public final static String ORDER_ACT_STATE_INACT = "0";
    public final static String ORDER_ACT_STATE_ACT = "1";
    public final static String ORDER_ACT_STATE_ACTING = "2";
    public final static String ORDER_ACT_STATE_ACT_FAILED = "3";
        /**
     * Manager 最高管理员的营销机构号
     */
    public final static String MANAGEMENT_RESOURCE_ID = "00000000";
    /**
     * 功能列表中采购的ID
     */
    public final static String PURCHASE_RESOURCE_ID = "410100";
    /**
     * 系统参数管理
     */
    public final static String SYSTEM_PARAMETER_RESOURCE_ID = "60300";
    /**
     * 数据字典管理
     */
    public final static String DICTINFO_RESOURCE_ID = "60400";
    /**
     * 系统日志查询管理
     */
    public final static String SYSTEM_LOG_RESOURCE_ID = "60500";
    /**
     * 结算周期管理
     */
    public final static String SETTLE_RESOURCE_ID = "60600";
    /**
     * 计算规则管理
     */
    public final static String CAALLINF_RESOURCE_ID = "60700";
    /**批量作废的最大卡片数*/
    public final static int MAX_NUM_INVALID=100;
    /**每次单卡作废数量*/
    public final static String ONE_CARD="1";

    /** 发票收交票状态 0：已分配 1：已收票 2：已交票 */
    public final static String INVOICE_ALREADY_DISTRI = "0";
    public final static String INVOICE_ALREADY_RECEIVE = "1";
    public final static String INVOICE_ALREADY_BAND = "2";

    /** 开票项目：所有类型 */
    public final static String INVOICE_PROJECT_ALL = "0";
    /** 开票项目：代收服务费 */
    public final static String INVOICE_PROJECT_SERVICE = "1";
    /** 字典类型代码 */
    public final static String DICT_TYPE_CODE = "120";
    
}
