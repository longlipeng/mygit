/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: RansomOrderConst.java
 * Author:   13010154
 * Date:     2013-8-9 下午03:07:50
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.huateng.framework.constant;

/**
 * 
 * 赎回订单工具
 *
 * @author 13010154
 */
public class RansomOrderConst {

    /**
     * 销户状态key
     */
    public static final String ACC_CARD_INFO_STATE_KEY = "cardSate";
    /**
     * 销户状态value
     */
    public static final String ACC_CARD_INFO_STATE_VALUE = "3";  
    /**
     * 销户状态异常信息
     */
    public static final String ACC_CARD_INFO_STATE_ERROR= "该卡已经销户！~";
    /**
     * 锁定状态key
     */
    public static final String ACC_CARD_INFO_LOCK_KEY = "lockSate";
    /**
     * 锁定状态value
     */
    public static final String ACC_CARD_INFO_LOCK_VALUE = "1";
    /**
     * 锁定状态异常信息
     */
    public static final String ACC_CARD_INFO_LOCK_ERROR= "该卡已经锁定！~";
    /**
     * 注销状态key
     */
    public static final String ACC_CARD_INFO_CANCEL_KEY = "cancelSate";
    /**
     * 注销状态value
     */
    public static final String ACC_CARD_INFO_CANCEL_VALUE = "1";
    /**
     * 注销状态异常信息
     */
    public static final String ACC_CARD_INFO_CANCEL_ERROR = "该卡已经注销！~";
    /**
     * 挂失状态key
     */
    public static final String ACC_CARD_INFO_LOST_KEY = "lostSate";
    /**
     * 挂失状态value
     */
    public static final String ACC_CARD_INFO_LOST_VALUE = "1";
    /**
     * 挂失状态异常信息
     */
    public static final String ACC_CARD_INFO_LOST_ERROR = "该卡已经挂失！~"; 
    /**
     * 产品ID
     */
    public static final String ACC_CARD_INFO_PRODUCT_ID = "productId";
    /**
     * 产品Name
     */
    public static final String ACC_CARD_INFO_PRODUCT_NAME = "productName";
    /**
     * 持卡人ID
     */
    public static final String ACC_CARD_INFO_CARDHOLDER_ID = "cardholderId";
    /**
     * 持卡人Name
     */
    public static final String ACC_CARD_INFO_CARDHOLDER_NAME = "firstName"; 
    /**
     * 机构异常信息
     */
    public static final String RANSOM_OREDER_ENTITY_ERROR = "不是本机构的卡! ~";
    /**
     * 此卡未售出异常信息
     */
    public static final String RANSOM_OREDER_CARD_NO_SELL_ERROR = "该卡未销售或者该卡已经验卡过或卡是不记名卡~";
    /**
     * 查询失败异常信息
     */
    public static final String RANSOM_OREDER_CARD_NO_SELL_FAIL_ERROR = "根据卡号查询赎回卡的信息失败~！";
    
    
}
