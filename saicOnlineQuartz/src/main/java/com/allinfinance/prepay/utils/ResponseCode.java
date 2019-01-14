package com.allinfinance.prepay.utils;

public class ResponseCode {
	
	/**
	 * 没有找到卡
	 */
	public final static String CARDNO_ISNULL="0015";
	
	/**
	 * 必填参数为空
	 */
	public final static String ISNULL="0102";
	/**
	 * 机构号有误
	 */
	public final static String ISSUEID_ERROR="0103";
	/**
	 * 手机号有误
	 */
	public final static String MOBILE_ERROR="0104";
	/**
	 * 证件类型有误
	 */
	public final static String ID_TYPE_ERROR="0105";
	/**
	 * 证件号有误
	 */
	public final static String ID_NO_ERROR="0106";
	/**
	 * 性别有误
	 */
	public final static String GENDER_ERROR="0107";
	/**
	 * 暂不支持企业订单
	 */
	public final static String ORDER_TYPE_ERROR="0108";
	/**
	 * 重复提交
	 */
	public final static String SUBMIT_DUPLICATE="0109";
	/**
	 * 无效交易
	 */
	public final static String INVALID_TRANSACTION="0012";
	
	/**
	 * 密码错
	 */
	public final static String PWD_ERROR="0055";
	/**
	 * 报文格式有误
	 */
	public final static String MESSAGE_FORMAT_ERROR="9902";
	/**
	 * 成功
	 */
	public final static String SUCCESS="00";
	
	/**
	 * 成功
	 */
	public final static String SUCCESS_IPOS="0000";
	/**
	 * 系统错误
	 */
	public final static String SYSTEM_ERROR="9999";
	
	/*-------------------------------------换卡订单应答码-----------------------------------------------*/
	
	/**
	 * 老卡与新卡的卡产品不匹配
	 */
	public final static String PRODUCT_ID_ERROR="0110";
	/**
	 * 未找到客户或持卡人信息
	 */
	public final static String CARDHOLDER_INFO_ISNULL="0111";
	/**
	 * 证件号、证件类型与持卡人不匹配
	 */
	public final static String ID_NO_INCONFORMITY="0112";
	/**
	 * 卡状态错
	 */
	public final static String CARD_STATE_ERROR="0113";
	/*-------------------------------------充值应答码-----------------------------------------------*/
	/**
	 * 充值失败
	 */
	public final static String RECHARGE_FAILED="0014";
	/**
	 * 充值金额有误
	 */
	public final static String RECHARGE_AMT_ERROR="0018";
	
	
	/**
	 * 非法字符
	 */
	public final static String ILLEGAL_CHARACTER="0019";
	/**
	 * 日期格式错
	 */
	public final static String VALIDITY_ERROR="0020";
	/**
	 * 卡产品错
	 */
	public final static String PRODUCT_ERROR="0021";
	/**
	 * 面额错
	 */
	public final static String FACE_VALUE_ERROR="0022";
}
