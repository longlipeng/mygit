package com.huateng.hstserver.constants;

public class MemberConstant {
	/**
	 * 持卡人网站所属商户号，存于系统参数表中
	 */
	//public final static String WEB_MERCHANT_ID = "WEB_MERCHANT_ID";
	public final static String WEB_MERCHANT_ID = "999999999999999";
	
	/**
	 * 持卡人网站所属终端号，存于系统参数表中
	 */
	//public final static String WEB_TERMINAL_ID = "WEB_TERMINAL_ID";
	public final static String WEB_TERMINAL_ID = "88888888";
	/**
	 * 卡交易查询类型
	 */
	public final static String CARD_CURR_TRADE = "0";
	public final static String CARD_HISTORY_TRADE = "1";
	/**
	 * 审批标识
	 */
	public final static String AUTH_IN_TYPE_NO="0";//不需要审批
	public final static String AUTH_IN_TYPE_YES="1";//需要审批
	
	/**
     * 新会员注册时默认积分，存于系统参数表中
     */
    public final static String DEFAULT_POINT = "DEFAULT_POINT";

    /**
     * 最大允许CVV2错误次数，存于系统参数表中
     */
    public final static String MAX_CVV2_ERR_TIME = "MAX_CVV2_ERR_TIME";
    
	/**
     * 会员辅助信息表中默认值字段的主键
     */
    public final static String DEFAULT_MEMBER_INFO_APPEND = "DEFAULTACCOR";
    /**
     * 
     * 登录渠道
     */
    public final static String CARD_HOLDER_MEMBER_LOGIN_CHNL = "1" ; //会员网站
    public final static String CONSUME_MEMBER_LOGIN_CHNL = "2";
    
    
    /**
     * 数据状态   1 正常
     */
    public final static String DATA_STATE_NORMAL="1";
    /**
     * 数据状态   0 删除
     */
    public final static String DATA_STATE_DELETE="0";
    
	/**
     * 会员卡绑定状态：绑定
     */
    public final static String MEMBER_CARD_BIND_YES = "0";
    
	/**
     * 会员卡绑定状态：解绑
     */
    public final static String MEMBER_CARD_BIND_NO = "1";
    /**
     * 会员卡名称:实体卡片
     */
    public final static String  MEMBER_REAL_CARD = "实体卡片";
    /**
     * 会员卡名称:虚拟账户
     */
    public final static String  MEMBER_VIRTUAL_CARD = "虚拟账户";
	/**
     * 会员卡网上交易开通状态：已开通
     */
    public final static String MEMBER_CARD_WEB_TRANSACTION_YES = "1";

	/**
     * 会员卡网上交易开通状态：未开通
     */
    public final static String MEMBER_CARD_WEB_TRANSACTION_NO = "0";

    /**
     * 会员卡是否开通短信通知
     */
    public final static String MEMBER_CARD_MESSAGE_MENTION_YES = "1";
    public final static String MEMBER_CARD_MESSAGE_MENTION_NO = "0";
    /**
     * 是否为默认账号
     */
    public final static String IS_EPAY_DEFAULT_YES = "1";
    public final static String IS_EPAY_DEFAULT_NO = "0";
    /**
     * 会员卡是否开通月报
     */
    public final static String MEMBER_CARD_MONTH_REPORT_YES = "1";
    public final static String MEMBER_CARD_MONTH_REPORT_NO = "0";
    
    /**
     * 会员卡是否开通email通知
     */
    public final static String MEMBER_CARD_EMAIL_MENTION_YES = "1";
    public final static String MEMBER_CARD_EMAIL_MENTION_NO = "0";
   /**
    * 错误提示信息
    */
    public final static String HAD_ACTIVE="01";
    public final static String MEMBER_DOES_NOT_EXIST="02";
    
    /**
     * 库存卡的库存状态
     */
    public final static String MEMBER_STOCK_STATE_IN = "1";//已入库
    public final static String MEMBER_STOCK_STATE_WAIT = "2";//待出库
    public final static String MEMBER_STOCK_STATE_OUT = "3";//已出库
    
    /**
     * 库存卡的分配状态
     */
    public final static String MEMBER_CARD_STOCK_STATE_USED = "1";
    public final static String MEMBER_CARD_STOCK_STATE_UN_USED = "0";
    /**
     * 会员基本状态
     */
    public final static String MEMBER_STAT_NORMAL = "0";
    public final static String MEMBER_STAT_UNNORMAL = "1";
    
    /**
     * 会员网上操作成功/失败
     */
    public final static String MEMBER_SUCC = "1";
    public final static String MEMBER_FAIL = "0";
    
    /**
     * 会员邮件激活状态
     */
    public final static String MEMBER_EMAIL_ACTIVE_YES = "1";
    public final static String MEMBER_EMAIL_ACTIVE_NO = "0";
    /**
     * 认证状态
     * 
     */
    public final static String CONFIRM_STATE_YES = "1";
    public final static String CONFIRM_STATE_NO = "0";
    /**
     * 业务类型
     */
    public final static String MEMBER_ACTIVE_REG_EMAIL="01";
    public final static String MEMBER_EMAIL_CONFIRM="02";
    public final static String MEMBER_RETRIEVE_PASSWORD="03";
    public final static String MEMBER_MOBILE_BIND="04";
    /**
     * 会员手机验证状态
     */
    public final static String MEMBER_CELL_PHONE_VALID_YES = "1";
    public final static String MEMBER_CELL_PHONE_VALID_NO = "0";
    /**
     * 是否接受促销消息
     * 
     */
    public final static String MEMBER_RCV_NULL = "00";
    public final static String MEMBER_RCV_PHONE = "10";
    public final static String MEMBER_RCV_EMAIL = "01";
    public final static String MEMBER_RCV_ALL = "11";
    /**
     * 注册卡类型
     * 
     */
    public final static String MEMBER_REG_REAL_CARD = "01";
    public final static String MEMBER_REG_VIRTUAL_CARD = "02";
    /**
     * 是否支持互联网支付
     */
    public final static String IS_EPAY_YES = "1";
    public final static String IS_EPAY_NO = "0";
    /**
     * 卡片挂失状态
     */
    public final static String MEMBER_CARD_LOST_YES = "1";
    public final static String MEMBER_CARD_LOST_NO = "0";
    
    /**
     * 后台返回响应码
     * 00—成功 
     * */
    /**
     * 00 交易成功  
     */
    public final static String SUCC_POSP = "00";
    /**
     * 03 无效商户  
     */
    public final static String INVALID_MCHNT = "03";
    /**
     * 06 无效合同
     */
    public final static String INVALID_CONTRACT = "06";
    /**
     * 无效交易 
     */
    public final static String INVALID_TRADE = "12";
    /**
     * 卡未激活  
     */
    public final static String CARD_NOT_ACTIVE = "16";
    /**
     * 与原交易卡号不符
     */
    public final static String CARDNO_DISAGREE = "17";
    /**
     * 21 账户不匹配
     */
    public final static String ACC_MISMATCHING = "21";
    /**
     * 25 未找到原交易 
     */
    public final static String UNFIND_ORIGINAL_TRADE_MISTAKE = "25";
    /**
     * 26 原交易不成功
     */
    public final static String ORIGINAL_TRADE_NOT_SUCCESSFUL = "26";
    /**
     * 31 路由失败-机构不支持 
     */
    public final static String PERMISSION_DENIED ="31";
    /**
     * 36 卡已锁定
     */
    public final static String CARD_LOCKED = "36";
    /**
     * 41 卡已挂失
     */
    public final static String CARD_LOSSED = "41";
    /**
     * 42 无效账户 商户合同下未关联账户 
     */
    public final static String INVALID_ACC = "42";
    /**
     * 44 卡被注销  
     */
    public final static String CARD_OFF = "44";
    /**
     * 45 卡被冻结
     */
    public final static String CARD_FREEZED ="45";
    /**
     * 51 余额不足 
     */
    public final static String BALANCE_LACK = "51";
    /**
     * 54 过期的卡
     */
    public final static String EXPIRE_CARD = "54";
    /**
     * 55 密码错
     */
    public final static String PASSWORD_ERROR = "55";
    /**
     * 56 无此卡记录 
     */
    public final static String UNFIND_CARD_RECORD = "56";
    /**
     * 63 余额不正确
     */
    public final static String BALANCE_ERROR = "63";
    /**
     * 64 与原交易金额不符  
     */
    public final static String ORIGTRADE_AMOUNT_DISCREPANT = "64";
    /**
     * 65 消费次数超限
     */
    public final static String CONSUMER_TIMES_OVER = "65";
    /**
     * 66 充值次数超限
     */
    public final static String RECHARGE_TIMES_OVER = "66";
    /**
     * 67 网上单笔交易金额超限  
     */
    public final static String WEB_SINGE_AMT_OVER = "67";
    /**
     * 68 网上当天累计交易金额超限 
     */
    public final static String WEB_DAY_AMT_OVER ="68";
    /**
     * 74 cvv2不正确
     */
    public final static String ERR_POSP_INVALID_CVV2 = "74";
    /**
     * 75 密码错误次数超限  
     */
    public final static String PASSWORD_ERR_TIMES_OVER ="73";
    /**
     * 90 系统正在批处理日切中 
     */
    public final static String SYSTEM_BATCHING ="90";
    /**
     * 94 重复交易  
     */
    public final static String TRADE_REPEAD ="94";
    /**
     * 96 系统故障
     */
    public final static String SYSTEM_ERROR = "96";
    /**
     * 97 无效终端
     */
    public final static String INVAILD_TERM = "97";
    
    
    public final static String RESP_CODE_IS_NULL="";
    /**
     * IVR返回结果
     */
    public final static String IVR_SUCC = "1";
    public final static String IVR_FAIL = "9";
    public final static String IVR_CHECK_CARDCVV2_ERROR = "0";
    public final static String IVR_HAVE_LOST = "2";
}	
