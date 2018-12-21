/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-7-30       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2010 Huateng Software, Inc. All rights reserved.
 *
 *       This software is the confidential and proprietary information of
 *       Shanghai HUATENG Software Co., Ltd. ("Confidential Information").
 *       You shall not disclose such Confidential Information and shall use it
 *       only in accordance with the terms of the license agreement you entered
 *       into with Huateng.
 *
 * Warning:
 * =============================================================================
 *
 */
package com.huateng.common;

/**
 * Title:系统参数宏定义
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-7-30
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class SysParamConstants {
	/**产品模式标识*/
	public static final String PRODUCTION_MODE = "PRODUCTION_MODE";
	/**操作员密码过期期限*/
	public static final String OPR_PWD_OUT_DAY = "OPR_PWD_OUT_DAY";
	/**终端主密钥密钥索引*/
	public static final String ZMK_KEY_INDEX = "ZMK_KEY_INDEX";
	/**终端密钥密钥索引*/
	public static final String KEY_INDEX = "KEY_INDEX";
	/**报表模版信息路径*/
	public static final String REPORT_MODEL_DIR = "REPORT_MODEL_DIR";
	/**临时文件存放磁盘*/
	public static final String TEMP_FILE_DISK = "TEMP_FILE_DISK";
	/**退货连接ip*/
	public static final String SALES_IP = "SALES_IP";
	/**退货连接端口*/
	public static final String SALES_PORT = "SALES_PORT";
	/**上传文件服务器目录*/
	public static final String FILE_UPLOAD_DISK = "FILE_UPLOAD_DISK";
	/**清算代发文件下载*/
	public static final String FILE_PATH_SETTLE_DOWNLOAD = "FILE_PATH_SETTLE_DOWNLOAD";
	/**清算结果下载*/
	public static final String FILE_PATH_SETTLE_RESULT = "FILE_PATH_SETTLE_RESULT";
	/**清算结果上传*/
	public static final String FILE_PATH_SETTLE_UPLOAD = "FILE_PATH_SETTLE_UPLOAD";
	/**清算对账报表*/
	public static final String FILE_PATH_SETTLE_REPORT = "FILE_PATH_SETTLE_REPORT";
	/**商户营销活动代发文件下载*/
	public static final String FILE_PATH_MARKET_DOWNLOAD = "FILE_PATH_MARKET_DOWNLOAD";
	/**系统强制密码**/
	public static final String RESET_PWD = "RESET_PWD";
	/**中行对账文件上传*/
	public static final String FILE_PATH_SETTLE_UPLOADJIGOU = "FILE_PATH_SETTLE_UPLOADJIGOU";
	/**上传银企文件*/
	public static final String FILE_PATH_YINQI_UPLOAD = "FILE_PATH_YINQI_UPLOAD";
	/**需要上传的TXT存放位置*/
	public static final String FILE_PATH_YINQI_UPLOAD1 = "FILE_PATH_YINQI_UPLOAD1";
	/**需要上传的TXT加密文件存放位置*/
	public static final String FILE_PATH_YINQI_UPLOAD2 = "FILE_PATH_YINQI_UPLOAD2";
	/**银企直连用户1*/
	public static final String YINQI_NAME = "YINQI_NAME";
	/**银企直连密码1*/
	public static final String YINQI_PWD = "YINQI_PWD";
	
	/**对账文件上传*/
	public static final String FILE_PATH_SETTLE_UPLOADACCOUNT = "FILE_PATH_SETTLE_UPLOADACCOUNT";
	/**deskey*/
	public static final String DESKEY = "DESKEY";
	/**银企直连文件服务器IP*/
	public static final String YQFTPSERVERIP = "YQFTPSERVERIP";
	public static final String YQFTPNAME = "YQFTPNAME";
	public static final String YQFTPPWD = "YQFTPPWD";
	public static final String YQFTPPATH = "YQFTPPATH";
	
	/** 环讯对应农行的商户号*/
	public static final String IPS_ABC_MCHNO = "IPS_ABC_MCHNO";
	
	/**记录佰付通商户批量上传时的错误*/
	public static final String MCHT_BatchFile_ERRInfo="MCHT_BatchFile_ERRInfo";
	public static final String MCHT_BFZBatch_ErrInfo="MCHT_BFZBatch_ErrInfo";
	public static final String MCHT_BJSBatch_ErrInfo="MCHT_BJSBatch_ErrInfo";
	public static final String MCHT_BFTMemberPointsBatch_ErrInfo="MCHT_BFTMemberPointsBatch_ErrInfo";
	/**中石油对账文件名称*/
	public static final String ZSYTXNNAME="ZSYTXNNAME";
	/**银企直连POST请求地址地址*/
	public static final String YQHTPP="YQHTPP";
	/**银企直连登陆用户名*/
	public static final String LGNNAM="LGNNAM";
	/**银企直业务类别*/
	public static final String BUSCOD="BUSCOD";
	/**银企直用途*/
	public static final String NUSAGE="NUSAGE";
	/**用途*/
	public static final String NUSAGE01="NUSAGE01";
	/**收方编号*/
	public static final String CRTSQN="CRTSQN";
	/**付方帐号*/
	public static final String DBTACC="DBTACC";
	/**付方开户行*/
	public static final String DBTBNK="DBTBNK";
	/**付方开户地区*/
	public static final String C_DBTBBK="C_DBTBBK";
	/**付方帐户名*/
	public static final String DBTNAM="DBTNAM";
	/**付方客户关系号*/
	public static final String DBTREL="DBTREL";
	/**业务类别*/
	public static final String BUSCOD01="BUSCOD01";
	/**CVSFILE地址*/
	public static final String CVSFILE="CVSFILE";
	/**CVSFILE名字*/
	public static final String CVSNAME="CVSNAME";
	/**由银行提供支付机构代码*/
	public static final String COMPANY_CODE = "COMPANY_CODE";
	/**备付金银行账户户名*/
	public static final String PROVISIONS_ACCOUNT = "PROVISIONS_ACCOUNT";
	/**备付金银行账户账号*/
	public static final String PROVISIONS_ID = "PROVISIONS_ID"; 
	/**银行名称*/
	public static final String BANK_NAME = "BANK_NAME"; 
	/**线上退货*/
	public static final String XSIP = "XSIP"; 
	public static final String XSPORT = "XSPORT"; 
	/**生成上传TXT文件名*/
	public static final String PMS_101_CMB100 = "PMS_101_CMB100"; 
	public static final String PMS_102_CMB100 = "PMS_102_CMB100"; 
	public static final String PMS_103_CMB100 = "PMS_103_CMB100"; 
	public static final String PMS_104_CMB100 = "PMS_104_CMB100"; 
	public static final String PMS_105_CMB100 = "PMS_105_CMB100"; 
	public static final String PMS_106_CMB100 = "PMS_106_CMB100"; 
	public static final String PMS_107_CMB100 = "PMS_107_CMB100"; 
	public static final String PMS_108_CMB100 = "PMS_108_CMB100"; 
	public static final String PMS_109_CMB100 = "PMS_109_CMB100"; 
	public static final String PMS_110_CMB100 = "PMS_110_CMB100"; 
	public static final String PMS_111_CMB100 = "PMS_111_CMB100"; 
	public static final String PMS_112_CMB100 = "PMS_112_CMB100"; 
	public static final String PMS_113_CMB100 = "PMS_113_CMB100"; 
	
	public static final String PMS_114_CMB100 = "PMS_114_CMB100"; 
	public static final String PMS_115_CMB100 = "PMS_115_CMB100"; 
	public static final String PMS_116_CMB100 = "PMS_116_CMB100"; 
	public static final String PMS_117_CMB100 = "PMS_117_CMB100"; 
	public static final String PMS_118_CMB100 = "PMS_118_CMB100"; 
	
	public static final String FIXED = "FIXED"; 
	
	//反洗钱 批量上传Excel到服务器，然后从服务器下载excel，导入数据库
	public static final String AML_EXCEL = "AML_EXCEL"; 
	//反洗钱 批量上传Excel到服务器，然后从服务器下载excel，导入数据库
	public static final String AML_EXCEL2 = "AML_EXCEL2"; 
	
	//反洗钱结果文件生成目录
	public static final String BLACKLIST_RESULT_FILE_PATH = "blacklist.resultFile.basePath";
	//反洗钱结果文件生成名称
	public static final String BLACKLIST_RESULT_FILE_NAME = "blacklist.resultFile.fileName";
	
	
	//后台通知地址
	public static final String BACKURL = "BACKURL"; 
	//机构代码
	public static final String ACQINSCODE = "ACQINSCODE";  
	//头寸序号
	public static final String INSSEQ = "INSSEQ";  
	//证书ID
	public static final String CERTID = "CERTID";  
	//签名
	public static final String SIGNATURE = "SIGNATURE";  
	//签名方法
	public static final String SIGNMETHOD = "SIGNMETHOD"; 
	//收款方账号
	public static final String PAYEEACCTNO = "PAYEEACCTNO";  
	//收款方账户名称
	public static final String PAYEEACCTNAME = "PAYEEACCTNAME";
	//付款方账号
	public static final String PAYERACCTNO = "PAYERACCTNO";  
	//付款方账户名称
	public static final String PAYERACCTNAME = "PAYERACCTNAME";
	//虚拟账号
	public static final String ACCTNO = "ACCTNO"; 
	//虚拟账户名称
	public static final String ACCTNAME = "ACCTNAME";
	//ACS备付金存管账号
	public static final String BANKNO = "BANKNO";
	
	
	//人行监管入账代理ip
	public static final String HDIP = "HDIP";
	//人行监管入账代理端口
	public static final String HDPORT = "HDPORT";
		
	
}
