package com.allinfinance.prepay.message;


public class ResponseCode {
	public static final String[] EMPTY							= {"", ""};
	public static final String[] SUCCESS						= {"00", "交易成功"};
	public static final String[] ILLEGAL_MESSAGE_FORMAT		= {"0001", "报文格式错误"};
	public static final String[] ILLEGAL_KEY					= {"0002", "秘钥信息错误"};
	public static final String[] TRANSACTION_NOT_SUPPORTED	= {"0003", "交易不支持"};
	public static final String[] ARQC_VERIFY_ERROR			= {"0004", "ARQC验证失败"};
	public static final String[] MAC_CALCULATION_ERROR		= {"0005", "MAC计算错误"};
	public static final String[] HSM_ERROR					= {"9901", "加密机错误"};
	public static final String[] SYSTEM_ERROR					= {"9999", "系统错误"};
}
