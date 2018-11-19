package com.huateng.framework.constant;


/**
 * <p>
 * Title: Accor
 * </p>
 * 
 * <p>
 * Description:Accor Project 1nd Edition
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author YY
 * @version 1.0
 */

public class Const {

	/**
	 * 返回成功失败标记 0表示调用后台程序失败 1表示调用后台程序成功
	 */
	public static final String RETURN_FAILED = "0";
	public static final String RETURN_SUCC = "1";

	/**
	 * C后台返回结果 0表示成功 其他表示失败
	 */
	public static final int C_SUCCESS = 0;

	/**
	 * 后台校验成功失败标记 1表示成功 0表示失败
	 */
	public static String CHECK_SUCC = "1";
	public static String CHECK_FAIlED = "0";

	/**
	 * 是否需要校验密码标记 1表示需要校验 0表示不需要校验
	 */
	public static final String NEED = "1";
	public static final String NOTNEED = "0";

	// packet max length
	public static int PACKET_LEN = 1024;

	public static String XML_COMMON = "commonhead.xml";

	// define txn category
	public static String TXNCODE_ACNTMNG = "10";
	public static String TXNCODE_MAKE_MAG_CARD = "11";
	public static String TXNCODE_MAKE_IC_CARD = "12";
	public static String TXNCODE_MAKE_IC_CARD_PIN = "13";
	public static String TXNCODE_MAKE_END = "00";

	// define transfer xml file
	public static String XML_ACNTMNG = "acctMgmt.xml";
	public static String FILE_NAME_MAKECARD = "MakeCard";
	public static String PACKET_NAME_MAKE_END = "end";
	public static String PACKET_NAME_MAKEKEY = "makeKey";
	public static String PACKET_NAME_MAKE_MG_CARD = "makeMagCard";
	public static String PACKET_NAME_MAKE_IC_CARD = "makeICCard";
	public static String PACKET_NAME_MAKE_IC_CARD_PIN = "makeICCardPin";
	public static String PACKET_NAME_DECRYPT = "decrypt";

	public static String FILE_NAME_ISO8583 = "ISO8583";
	public static String PACKET_NAME_ENQUIRY_BALANCE = "enquiryBalance";
	public static String PACKET_NAME_CONSUME = "consume";
	public static String PACKET_NAME_AUTHORIZATION = "authorization";
	public static String PACKET_NAME_AUTHORIZATION1 = "authorization1";
	public static String PACKET_NAME_RESETPASSWORD = "resetPassword";

	public static String FILE_NAME_ACTIVE = "Recharge";
	public static String PACKET_NAME_ACTIVE = "active";
	public static String PACKET_NAME_RECHARGE = "recharge";
	public static String PACKET_NAME_RECHARGE_DIPOSABLE = "diposableRecharge";
	public static String PACKET_NAME_RECHARGE_DIPOSABLE_CANCEL = "diposableRechargeCancel";
	public static String PACKET_NAME_ACCOUNTADJUST = "accountAdjust";
	public static String PACKET_NAME_ACCOUNTADJUST1 = "accountAdjust1";
	public static String PACKET_NAME_CVV = "CVV";
	public static String PACKET_NAME_DEFRAY = "defray";
	public static String PACKET_NAME_RETURNPURCHASE = "returnPurchase";
	public static String PACKET_NAME_TRANSFER = "transfer";
	public static String PACKET_NAME_EXCHANGE = "exchange";

	// 公共事业费
	public static String FILE_NAME_PUBLICSERVICE = "PublicService";
	public static String PACKET_NAME_PUBLICSERVICE = "publicService";

	// get xml config file error
	public static final String ERR_GETXML = "613001";

	// error parse packet
	public static final String ERR_PARSEPACKET = "601003";

	// error build packet
	public static final String ERR_BUILDPACKET = "601004";

	// error build packet
	public static final String ERR_OTHER = "602000";

	// error build packet
	public static final String ERR_TOXML = "602001";

	// error build packet
	public static final String ERR_TOOBJ = "602002";

	// ASCII
	public static final String CODE_ASCII = "ASCII";

	// BCD
	public static final String CODE_BCD = "BCD";

	// BINARY
	public static final String CODE_BINARY = "BINARY";

	// 对账标志
	public static final String RECON_RESULT_ = "0";
	public static final String RECON_RESULT_NORMAL = "1";
	public static final String RECON_RESULT_NOT_EQUAL = "2";
	public static final String RECON_RESULT_ONLY_IN_COMM = "3";
	public static final String RECON_RESULT_ONLY_IN_DAYS = "4";

	// 对账不平原因
	public static final String RECON_REASON_TRANS_TIME = "交易时间不一致";
	public static final String RECON_REASON_COMM_MCHNT_ID = "交行商户号不一致";
	public static final String RECON_REASON_MCHNT_ID = "得仕商户号不一致";
	public static final String RECON_REASON_COMM_POS_ID = "交行终端号不一致";
	public static final String RECON_REASON_AMT = "交易金额不一致";
	public static final String RECON_REASON_TXN_TYPE = "交易类型不一致";
	public static final String RECON_REASON_SUCC = "对账平";
	public static final String RECON_REASON_ONLY_IN_COMM = "只存在交行文件中";
	public static final String RECON_REASON_ONLY_IN_DAYS = "只存在得仕系统中";
	// 返回码
	public static final String RESPONSE_CODE = "00";
	// 报文处理结果
	public static final String TXN_STATE_SUCCESS = "1";
	
	//充值服务名
	public static final String SERVICE_NAME_RECHARGE = "vRecharge";
	
	//充值订单服务名
	public static final String SERVICE_NAME_RECHARGE_ORDER = "vNoActRecharge";
	
	//充值交易代码
	public static final String TXN_TYPE_CODE_RECHARGE = "0002";
	
	//充值订单交易代码
	public static final String TXN_TYPE_CODE_RECHARGE_ORDER = "0004";
	
	//充值订单交易返回码
	public static final String RECHARGE_ORDER_RES_CODE = "52";
	
	
	//激活服务名
	public static final String SERVICE_NAME_ACTIVE = "vAct";
	
	//激活交易代码
	public static final String TXN_TYPE_CODE_ACTIVE	 = "0003";
	// 发送报文
	/**
	 * 普通请求：1 
	 */
	public static final String JAVA2C_NORMAL = "1";
	/**
	 * 大量数据：2
	 */
	public static final String JAVA2C_BIG_AMT = "2";
	
	//渠道号
	public static final String JAVA2C_CHANNEL="888888";
	// 制卡处理状态：1，2，3
	public static final String MAKECARD_STATE_PROCESSING = "1";
	public static final String MAKECARD_STATE_FAILED = "2";
	public static final String MAKECARD_STATE_FINISH = "3";
	
	//实体类型
	/**实体类型 :2 发卡 机构*/
	public static final String FUNCTION_ROLE_ISSUER = "2";
	/**实体类型 :3 营销机构*/
	public static final String FUNCTION_ROLE_SELLER = "3";
	
	//结算单状态
	
	public static final String SETTLE_DRAFT_STATE= "0";
	public static final String SETTLE_STATE_CONFIRM="4";
	public static final String SETTLE_CONFIRM_STATE = "1";
	public static final String SETTLE_CANCEL_STATE = "2";
	public static final String SETTLE_PAYMENT_STATE = "3";
	
	//报文返回
	//每条记录的分割符segmentation 
	public static final String RECORD_SEGMENTATION= "|";
	//每个字段的分割符
	public static final String FIELD_SEGMENTATION= "^";
}
