package com.huateng.framework.constant;
/****
 * 订单常量
 * @author dawn
 *
 */
public class OrderConst {
	
	/** --------------------------------------------------订单状态--------------------------------------------------------*/
	/**
	 * 标志：是
	 */
	public final static String FLAG_YES = "1";
	/**
	 * 标志：否
	 */
	public final static String FLAG_NO = "0";
	 /**
     * 订单状态 0：取消
     */
    public final static String ORDER_STATE_CANCEL = "0";

    /**
     * 订单状态 1：草稿
     */
    public final static String ORDER_STATE_DRAFT = "1";

    /**
     * 订单状态 2：待审核
     */
    public final static String ORDER_STATE_UNCERTAIN = "2";
    /**
     * 订单状态 3：制卡文件待生成
     */
    public final static String ORDER_STATE_CARDFILE_DOWNLOAD = "3";
    
    /**
     * 订单状态4：已开户
     */
    public final static String ORDER_STATE_OPEN_ACCOUNT = "4";
    
    /**
     * 订单状态 6：制卡文件待下载
     */
    public final static String ORDER_STATE_CARDGFILE_MAKING = "6";
    
    /**
     * 订单状态15:待充值
     */
    public final static String ORDER_STATE_WAITTING_CREDIT = "15";
    
    /**
     * 订单状态16：充值成功
     */
    public final static String ORDER_STATE_CREDIT_SUCCESS = "16";
    /**
     * 订单状态 21:订单准备
     */
    public final static String ORDER_STATE_ORDER_READY = "21";
    
    /**
     * 订单流程岗位23:上级确认
     */
    public final static String ORDER_STATE_ORDER_CONFIRM_BY_HIGHER="23";   
    /***
     * 订单状态26：订单接收
     */
    public final static String ORDER_STATE_ORDER_ACCEPT = "26";
    /***
     * 订单状态27：订单入库完成
     */
    public final static String ORDER_STATE_ORDER_STOCK = "27";
    /**
     * 订单状态 29:订单分发
     */
    public final static String ORDER_STATE_ORDER_SEND = "29";
    
    /**
     * 订单状态30：下级机构订单接收
     */
    public final static String ORDER_STATE_ORDER_BRANCH_ACCEPT = "30";
    /**
     * 订单状态31：待配送确定
     */
    public final static String ORDER_STATE_ORDER_WAIT_SEND_CONFIRM="31";
    
    /**
     * 订单状态32：处理中
     */
    public final static String ORDER_STATE_ORDER_PROCESSING="33";
    
    /**
     * 订单状态32：处理失败
     */
    public final static String ORDER_STATE_ORDER_PROCESS_FAIL="34";
    /***
     * 订单配送确定
     */
    public final static String ORDER_STATE_ORDER_SEND_CONFIRM = "32";
    
    /**
     * 赎回订单入库
     */
    public final static String ORDER_STATE_ORDER_RANSOM_STOCK = "35";
    /**
     * 营销机构与客户之间的销售记名订单
     */
    public final static String ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN = "10000001";
    
    /**
     * 营销机构与客户之间的销售匿名订单
     */
    public final static String ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_UNSIGN="10000002";
    
    /**
     * 营销机构与个人之间的销售记名订单
     */
    public final static String ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN = "10000011";
    
    /**
     * 营销机构与个人之间的销售匿名订单
     */
    public final static String ORDER_TYPE_ORDER_SELL_SELLER_PERSON_UNSIGN="10000012";
    
    /**
     * 营销机构与客户之间的充值订单
     */
    public final static String ORDER_TYPE_SELL_CUSTOMER_CREDIT_ORDER="10000003";
 
    /***
     * 营销机构与营销机构的采购记名订单
     */
    public final static String ORDER_TYPE_ORDER_BUY_SELLER_SELLER_SIGN = "20000001";
    
    /***
     * 营销机构与营销机构的采购匿名订单
     */
    public final static String ORDER_TYPE_ORDER_BUY_SELLER_SELLER_UNSIGN = "20000002";
    /**
     * 营销机构与发卡机构的记名采购订单
     */
    public final static String ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_SIGN ="30000001";
    
    /**
     * 营销机构与发卡机构的匿名采购订单
     */
    public final static String ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN ="30000002";
    
    /***
     * 发卡机构与发卡机构的记名采购订单
     */
    public final static String ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_SIGN ="40000001";
    
    /***
     * 发卡机构与发卡机构的匿名采购订单
     */
    public final static String ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_UNSIGN ="40000002";
    
    /***
     * 发卡机构制卡订单
     */
    public final static String ORDER_TYPE_ORDER_MAKE_CARD="50000001";
    
    /**审核拒绝*/
    public final static String CARD_INFO_QUERY_CARD="2";
    
    /**已邮寄*/
    public final static String CARD_MAIL_DAIYOUJI="3";
    
    
    /**
     * 赎回订单
     */
    public final static String ORDER_TYPE_ORDER_RANSOM ="70000001";
    
    
    /***
     * 销售记名卡订单（散户）
     */
    public final static String ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_SIGN="10000005";
    
    
    /***
     * 销售不记名卡订单（散户）
     */
    public final static String ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_UNSIGN="10000006";
    /**
     * 卡作废订单
     */
    public final static String ORDER_TYPE_CARD_INVOICE ="10000007";
    
    
    /***
     * 订单来源
     */
    
    public final static String ORDER_SOURCE_SYSTEM_INPUT="1";
    
    /***
     * 订单支付状态:未支付
     */
    
    public final static String ORDER_PAY_STATE_UNPAID="0";
    
    /**
     * 订单支付状态：已支付
     */
    public final static String ORDER_PAY_STATE_PAID="1";
    
    
    
    /** ---------------------------------------------订单流程岗位--------------------------------------------------*/
    /**
     * 订单流程岗位 1：录入
     */
    public final static String ORDER_FLOW_INPUT = "1";
    /**
     * 订单流程岗位 2：确定
     */
    public final static String ORDER_FLOW_CONFIRMATION = "2";
    
    /**
     * 订单流程岗位 3：制卡下载
     */
    public final static String ORDER_FLOW_MAKECARD_DOWN = "3";
    /**
     * 订单流程岗位 4：制卡生成
     */
    public final static String ORDER_FLOW_MAKECARD_MAKE = "4";
    
    /**
     * 订单流程岗位 5：财务
     */
    public final static String ORDER_FLOW_FINANCE = "5";

    /**
     * 订单流程岗位 6：总库接收
     */
    public final static String ORDER_FLOW_STOCK_ACCEPT = "6";

    /**
     * 订单流程岗位 7：分库接收
     */
    public final static String ORDER_FLOW_BRANCH_ACCEPT  = "7";

    /**
     * 订单流程岗位 8：准备
     */
    public final static String ORDER_FLOW_READY = "8";
    
    /**
     * 订单流程岗位 9：分发
     */
    public final static String ORDER_FLOW_SEND = "9";

    /**
     * 订单流程岗位10：停用
     */
    public final static String ORDER_FLOW_CEASE = "10";

    /**
     * 订单流程岗位 11：挂失解挂
     */
    public final static String ORDER_FLOW_BLOCK = "11";
    
    /**
     * 订单流程岗位12:配送确定
     */
    public final static String ORDER_FLOW_SEND_CONFIRM = "12";
    
    /**
     * 订单流程岗位：订单立即充值
     */
    public final static String ORDER_FLOW_ORDER_IMMDIATELY_CREDIT = "13";
    
    
    /***
     * 订单流程岗位:订单付款确认
     */
    public final static String ORDER_FLOW_ORDER_PAYMENT = "14";
    /***
     * 订单流程岗位:订单付款审核
     */
    public final static String ORDER_FLOW_ORDER_SUBMIT = "15";
    
    /**
     * 订单流程岗位：库存调拨
     */
    public final static String ORDER_FLOW_STOCK_ALLOCATE = "16";
    
    /** -------------------------------------------订单操作类型----------------------------------------------------*/
    /**
     * 订单操作类型 0：取消
     */
    public final static String ORDER_FLOW_OPRATION_CANCEL = "0";

    /**
     * 订单操作类型 1：提交
     */
    public final static String ORDER_FLOW_OPRATION_CONFIRMATION = "1";
    
   
    /**
     * 订单操作类型 2：退回
     */
    public final static String ORDER_FLOW_OPRATION_BACK = "2";

    /**
     * 订单操作类型 5：全部丢失
     */
    public final static String ORDER_FLOW_OPRATION_ALL_FAIL = "5";

    /**
     * 订单操作类型 6：部分丢失
     */
    public final static String ORDER_FLOW_OPRATION_PART_FAIL = "6";

    /**
     * 订单操作类型 7：全部退回
     */
    public final static String ORDER_FLOW_OPRATION_SEND_ALL_BACK = "7";

    /**
     * 订单操作类型 8：部分退回
     */
    public final static String ORDER_FLOW_OPRATION_SEND_PART_BACK = "8";

    /**
     * 订单操作类型 9：订单停用
     */
    public final static String ORDER_FLOW_OPRATION_CEASE = "9";

    /**
     * 订单操作类型 10：订单挂失
     */
    public final static String ORDER_FLOW_OPRATION_BLOCK = "10";

    /**
     * 订单操作类型 11：订单解挂
     */
    public final static String ORDER_FLOW_OPRATION_UNBLOCK = "11";
    
    /**
     * 订单操作类型 12：新增
     */
    public final static String ORDER_FLOW_OPRATION_ADD = "12";
    
    /**
     * 订单操作类型 13：删除
     */
    public final static String ORDER_FLOW_OPRATION_DELETE = "13";
    
    /**
     * 订单操作类型 14：退回后出库
     */
    public final static String ORDER_FLOW_OPRATION_BACKTHENOUT = "14";
    /**
     * 订单操作类型20：关闭
     */
    public final static String ORDER_FLOW_OPERATION_CLOSE = "20";
    /**
     * 订单卡片激活15：订单卡片激活
     */
    public final static String ORDER_FLOW_OPRATION_ACTIVATE="15";
    /**
     * 单张卡操作类型：激活后出库
     */
    public final static String SINGLE_CARD_ACTIVE = "16";
    /**
     * 单张卡操作类型：赎回后入库
     */
    public final static String SINGLE_CARD_RETRIEVE = "17";
    /**
     * 单张卡操作类型：换卡后入库
     */
    public final static String SINGLE_CARD_CHANGE = "18";
    /** ------------------------------------------制卡状态---------------------------------------------------------------*/
    /** 制卡状态 1：无卡. */
    public final static String MAKE_CARD_STATE_NOCARD = "1";

    /** 制卡状态 2：制卡中. */
    public final static String MAKE_CARD_STATE_MAKING = "2";

    /** 制卡状态 3：制卡成功. */
    public final static String MAKE_CARD_STATE_SUCCESS = "3";

    /** 制卡状态 4：制卡失败. */
    public final static String MAKE_CARD_STATE_FAILTURE = "4";
    
    /**------------------------------------------制卡文件类型-------------------------------------------------------------------------- */
    
    public final static String MGF = "MGF";
    /** 磁条卡 */
    public final static String MGF_VALUE = "2";
    
    public final static String SGF= "SGF";
    /** IC礼品卡  卡类型+产品类型*/
    public final static String SGF_VALUE = "12";
    
    public final static String SLD = "SLD";
    /** IC充值卡 卡类型+产品类型*/
    public final static String SLD_VALUE = "11";
    
    /**---------------------------------------------卡库存状态----------------------------------------------------------------------------*/

    /**
     * 卡片库存状态:取消
     */
	public final static String CARD_STOCK_CANCEL = "0";
	/**
     * 卡片库存状态:入库
     */
    public final static String CARD_STOCK_IN = "1";
    
    /**
     * 卡片库存状态:待出库
     */
    public final static String CARD_STOCK_READY_OUT = "2";
    
    /**
     * 卡片库存状态:已出库
     */
    public final static String CARD_STOCK_OUT = "3";
    
    /**
     * 卡片库存状态:丢失
     */
    public final static String CARD_STOCK_LOST = "4";
    /**
     * 卡片库存状态：作废
     * */
    public final static String CARD_STOCK_INVALID = "6";

    /**----------------------------------------------合并订单关联------------------------------------------------------------------------- */
    /** 叶子节点：是*/
    public final static String IS_LEAF_NODE = "1";
    /** 叶子节点：否*/
    public final static String IS_NOT_LEAF_NODE = "0";
    
    /**----------------------------------------------面额类型-------------------------------------------------------------------- */
    /** 固定面额 */
    public final static String FACE_VALUE_TYPE_STATIC = "0";
    /** 非固定面额 */
    public final static String FACE_VALUE_TYPE_NOT_STATIC = "1";
    
    /** ---------------------------------------------库存操作类型------------------------------------------------------------------------------*/
    /** 卡片库存状态1: 入库 */
    public final static String CARD_STOCK_OPERATE_TYPE_IN = "1";
    /** 卡片库存状态2: 待出库 */
    public final static String CARD_STOCK_OPERATE_TYPE_READY_OUT = "2";
    /** 卡片库存状态3: 出库 */
    public final static String CARD_STOCK_OPERATE_TYPE_OUT = "3";
    
    
    /** 产品署名状态1：记名（个性化卡） */
    public final static String PRODUCT_ONYMOUS_STAT_MUST = "1";
    
    /** 产品署名状态3：记名(库存卡) */
    public final static String PRODUCT_ONYMOUS_STAT_CAN = "3";
    
    /** 产品署名状态2：不记名 */
    public final static String PRODUCT_ONYMOUS_STAT_NO = "2";

    /***
     * 营销机构换卡订单
     */
    public final static String ORDER_TYPE_ORDER_CHANGE_CARD="60000001";
    
    /**----------------------------------------------换卡订单原有卡状态-------------------------------------------------------------------- */
    /** 原有卡状态0: 待验收 */
    public final static String ORIG_CARD_STAT_UNCHECK = "0";
    /** 原有卡状态1: 已验收 */
    public final static String ORIG_CARD_STAT_CHECK = "1";
    /** 原有卡状态2: 入库 */
    public final static String ORIG_CARD_STAT_ENTSTOCK = "2";
    /** 原有卡状态3: 销户 */
    public final static String ORIG_CARD_STAT_DESTORY = "3";
    
    /** 库存卡状态5: 销户 */
    public final static String CARD_STOCK_DESTORY = "5";
    
    /*订单付款方式*/
	public static final String SELL_ORDER_PAYMENT_INSERT="201202270001";
	public static final String SELL_ORDER_PAYMENT_DELETE="201202270002";
	public static final String SELL_ORDER_INQUERY_PRINT_PAYMENT="201202280001";
	
	/**库存调拨订单类型**/
    public static final String STOCK_TRANSFER_ORDER_TYPE = "60000006";
    
/**----------------------------------------------库存调拨——订单状态-----------------------------------------------------------------------*/
    
    /**
     * 库存调拨订单状态7：录入
     * */
    public final static String STOCK_TRANSFER_ORDER_INPUT = "7";
        
    /**
     * 库存调拨订单状态8：待出库
     * */
    public final static String STOCK_TRANSFER_ORDER_READY = "8";
    /**
     * 库存调拨订单状态9：已出库
     * */
    public final static String STOCK_TRANSFER_ORDER_OUT = "9";
    /**
     * 库存调拨订单状态10：部分入库
     * */
    public final static String STOCK_TRANSFER_ORDER_NOTALL_IN = "10";
    /**
     * 库存调拨订单状态11：订单入库完成
     * */
    public final static String STOCK_TRANSFER_ORDER_ALL_IN = "11";
    /**
     * 库存调拨订单状态12：取消
     * */
    public final static String STOCK_TRANSFER_ORDER_CANCEL = "12";
    /**
     * 起始号码和结束号码比较返回值
     * */ 
    public final static int COMPARE_VALUE = 1;
    /**
     * 卡片查询  作废
     * */
    public final static String INVALID_STATE="2";
    
}
