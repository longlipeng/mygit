package com.huateng.struts.pos;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-6-20
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @version 1.0
 */
public class TblTermInfConstants {
	/**
	 * 	新增未审核:0;//直接删除
	 *	启用:1;//注销未审核
	 *	修改未审核:2;//不注销
	 *	停用未审核:3;//真实表有
	 *	停用:4;//真实表有
	 *	恢复未审核:5;//真实表有
	 *	注销未审核:6;
	 *	注销:7;
	 *	新增审核拒绝:8;//不注销
	 *	修改审核拒绝:9;//不注销
	 *	停用审核拒绝:A;//不注销
	 *	恢复审核拒绝:B;//不注销
	 *	注销审核拒绝:C;//不注销
	 *	复制未修改:D;//注释掉了
	 */
	public static final String TERM_STA_INIT = "0";
	public static final String TERM_STA_RUN = "1";
	public static final String TERM_STA_MOD_UNCHK = "2";
	public static final String TERM_STA_STOP_UNCHK = "3";
	public static final String TERM_STA_STOP = "4";
	public static final String TERM_STA_REC_UNCHK = "5"; 
	public static final String TERM_STA_CANCEL_UNCHK = "6";
	public static final String TERM_STA_CANCEL = "7";
	public static final String TERM_STA_ADD_REFUSE = "8";
	public static final String TERM_STA_MOD_REFUSE = "9";
	public static final String TERM_STA_STOP_REFUSE = "A";
	public static final String TERM_STA_REC_REFUSE = "B";
	public static final String TERM_STA_CANCEL_REFUSE = "C";
	public static final String TERM_STA_COPY = "D";
	
	
	public static final String DEFUALT_CHECKBOX = "1";
	/**
	 * 	启用:0
	 *	不可用:1
	 */
	public static final String TERM_SIGN_STA_OK = "0";
	public static final String TERM_SIGN_STA_ERR = "1";
	/**
	 * 签到默认状态
	 */
	public static final String TERM_SIGN_DEFAULT = "0";
	/**
	 * 0-我行产权 1-我行租赁 2-第三方投入
	 */
	public static final String PROP_TP_OWN = "0";
	public static final String PROP_TP_RENT = "1";
	public static final String PROP_TP_OTHERS = "2";
	
	public static final String OK = "0";
	public static final String REFUSE = "1";
	public static final String T30101_01 = "30101.01";
	public static final String T30101_02 = "30101.02";
	public static final String T30101_03 = "30101.03";
	public static final String T30101_04 = "30101.04";
	public static final String T30101_05 = "30101.05";
	public static final String T30101_06 = "30101.06";
	public static final String T30201_01 = "30201.01";
	public static final String T30201_02 = "30201.02";
}
