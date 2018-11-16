/* @(#)
 *
 * Project:PFConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ --------- ---------------------------------------------------
 *   Gavin        2011-7-27       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2011 Huateng Software, Inc. All rights reserved.
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
package com.huateng.struts.settle.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.huateng.bo.settle.T80224BO;
import com.huateng.common.SysParamConstants;
import com.huateng.po.settle.TblMchtSumrzInf;
import com.huateng.struts.system.action.BaseSupport;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.SysParamUtil;

/**
 * Title: 
 * 
 * File: T80224.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-7-27
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author 
 * 
 * @version 1.0
 */
public class T80224Action extends BaseSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(T80224Action.class);
	T80224BO t80224BO = (T80224BO) ContextUtil.getBean("T80224BO");
	
	public String sucAdd(){
		jsonBean.parseJSONArrayData(getInfList());
		int len = jsonBean.getArray().size();
		TblMchtSumrzInf tblMchtSumrzInf = null;
		List<TblMchtSumrzInf> tblMchtSumrzInfList = new ArrayList<TblMchtSumrzInf>(len);
		try {
			for (int i = 0; i < len; i++) {
				tblMchtSumrzInf = t80224BO.get(new Integer(jsonBean.getJSONDataAt(i).getString("seqNo")));
				tblMchtSumrzInf.setSaStatus("1");
				tblMchtSumrzInf.setSumrzDate(sumrzDate);
				tblMchtSumrzInf.setRecUpdOpr(getOperator().getOprId());
				tblMchtSumrzInf.setRecUpdTs(CommonFunction.getCurrentDateTime());
				tblMchtSumrzInfList.add(tblMchtSumrzInf);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			rspCode=t80224BO.update(tblMchtSumrzInfList);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode,e);
		}
		log("商户划款成功回填成功，操作员编号："+getOperator().getOprId());
		return returnService(rspCode);
	}
	public String applyYqZl() {
		jsonBean.parseJSONArrayData(getInfList());
		int len = jsonBean.getArray().size();
		TblMchtSumrzInf tblMchtSumrzInf = null;
		List<TblMchtSumrzInf> tblMchtSumrzInfList = new ArrayList<TblMchtSumrzInf>(len);
		for (int i = 0; i < len; i++) {
			String seqNo = jsonBean.getJSONDataAt(i).getString("seqNo");//主键
			String mchtNm = jsonBean.getJSONDataAt(i).getString("mchtNm");//商户号
			String accFlag = jsonBean.getJSONDataAt(i).getString("accFlag");//账户类型
			String settleAccName = jsonBean.getJSONDataAt(i).getString("settleAccName");//结算账户
			String bankName = jsonBean.getJSONDataAt(i).getString("bankName");//开户行
			String txnAmt = jsonBean.getJSONDataAt(i).getString("txnAmt");//交易金额
			String handAmt = jsonBean.getJSONDataAt(i).getString("handAmt");//手续费
			String sumAmt = jsonBean.getJSONDataAt(i).getString("sumAmt");//结算金额
			String saStatus = jsonBean.getJSONDataAt(i).getString("saStatus");//状态
			String sumrzDate = jsonBean.getJSONDataAt(i).getString("sumrzDate");//划款日期
			String sumrzNote = jsonBean.getJSONDataAt(i).getString("sumrzNote");//备注
			String dirOpenBank = jsonBean.getJSONDataAt(i).getString("dirOpenBank");//定向委托账号开户总行名称
			String dirBankProvince = jsonBean.getJSONDataAt(i).getString("dirBankProvince");//定向委托账号开户行所在省
			String dirBankCity = jsonBean.getJSONDataAt(i).getString("dirBankCity");//定向委托账号开户行所在市
			String compOpenBank = jsonBean.getJSONDataAt(i).getString("compOpenBank");//对公账号开户总行名称
			String compBankProvince = jsonBean.getJSONDataAt(i).getString("compBankProvince");//对公账号开户行所在省
			String compBankCity = jsonBean.getJSONDataAt(i).getString("compBankCity");//对公账号开户行所在市
			String corpOpenBank = jsonBean.getJSONDataAt(i).getString("corpOpenBank");//对私账号开户总行名称
			String corpBankProvince = jsonBean.getJSONDataAt(i).getString("corpBankProvince");//对私账号开户行所在省
			String corpBankCity = jsonBean.getJSONDataAt(i).getString("corpBankCity");//对私账号开户行所在市    settleRpt,companyNam,settleAcct,legalNam,feeAcct,dirAccountName,dirAccount
			String settleRpt = jsonBean.getJSONDataAt(i).getString("settleRpt");//结算账户类型
			String companyNam = jsonBean.getJSONDataAt(i).getString("companyNam");//对公账号名称
			String settleAcct = jsonBean.getJSONDataAt(i).getString("settleAcct");//对公账号
			String legalNam = jsonBean.getJSONDataAt(i).getString("legalNam");//对私账号名称
			String feeAcct = jsonBean.getJSONDataAt(i).getString("feeAcct");//对私账号
			String dirAccountName = jsonBean.getJSONDataAt(i).getString("dirAccountName");//定向委托账号名称
			String dirAccount = jsonBean.getJSONDataAt(i).getString("dirAccount");//定向委托账号
			System.out.println("主键："+seqNo+"商户号："+mchtNm+"账户类型："+accFlag+"结算账户："+settleAccName+"开户行："+bankName+"交易金额："+txnAmt+"手续费："+handAmt);
			System.err.println("结算账户类型："+settleRpt+"对公账号："+settleAcct+"对私账号："+feeAcct+"定向委托账号："+dirAccount);
			System.err.println("对公账号名称："+companyNam+"对私账号名称："+legalNam+"定向委托账号名称："+dirAccountName);
			try {
				tblMchtSumrzInf = t80224BO.get(new Integer(seqNo));
				tblMchtSumrzInf.setAuditStatus("0");
				tblMchtSumrzInf.setAuditId(getOperator().getOprId());
				SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMdd");
				Date date1=new Date();
				tblMchtSumrzInf.setAuditDate(dateFormater.format(date1));
				tblMchtSumrzInfList.add(tblMchtSumrzInf);
			} catch (Exception e) {
				log.error("新增失败："+e);
			}
		}
		try {
			rspCode=t80224BO.update(tblMchtSumrzInfList);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode,e);
		}
		log("商户划款新增，操作员编号："+getOperator().getOprId());
		return returnService(rspCode);
		
	}
	
	
	/**
	 * 二次划款新增
	 * @return
	 */
	public String applyYqZlTwo() {
		jsonBean.parseJSONArrayData(getInfList());
		int len = jsonBean.getArray().size();
		TblMchtSumrzInf tblMchtSumrzInf = null;
		List<TblMchtSumrzInf> tblMchtSumrzInfList = new ArrayList<TblMchtSumrzInf>(len);
		for (int i = 0; i < len; i++) {
			String seqNo = jsonBean.getJSONDataAt(i).getString("seqNo");//主键
			String mchtNm = jsonBean.getJSONDataAt(i).getString("mchtNm");//商户号
			String accFlag = jsonBean.getJSONDataAt(i).getString("accFlag");//账户类型
			String settleAccName = jsonBean.getJSONDataAt(i).getString("settleAccName");//结算账户
			String bankName = jsonBean.getJSONDataAt(i).getString("bankName");//开户行
			String txnAmt = jsonBean.getJSONDataAt(i).getString("txnAmt");//交易金额
			String handAmt = jsonBean.getJSONDataAt(i).getString("handAmt");//手续费
			String sumAmt = jsonBean.getJSONDataAt(i).getString("sumAmt");//结算金额
			String saStatus = jsonBean.getJSONDataAt(i).getString("saStatus");//状态
			String sumrzDate = jsonBean.getJSONDataAt(i).getString("sumrzDate");//划款日期
			String sumrzNote = jsonBean.getJSONDataAt(i).getString("sumrzNote");//备注
			String dirOpenBank = jsonBean.getJSONDataAt(i).getString("dirOpenBank");//定向委托账号开户总行名称
			String dirBankProvince = jsonBean.getJSONDataAt(i).getString("dirBankProvince");//定向委托账号开户行所在省
			String dirBankCity = jsonBean.getJSONDataAt(i).getString("dirBankCity");//定向委托账号开户行所在市
			String compOpenBank = jsonBean.getJSONDataAt(i).getString("compOpenBank");//对公账号开户总行名称
			String compBankProvince = jsonBean.getJSONDataAt(i).getString("compBankProvince");//对公账号开户行所在省
			String compBankCity = jsonBean.getJSONDataAt(i).getString("compBankCity");//对公账号开户行所在市
			String corpOpenBank = jsonBean.getJSONDataAt(i).getString("corpOpenBank");//对私账号开户总行名称
			String corpBankProvince = jsonBean.getJSONDataAt(i).getString("corpBankProvince");//对私账号开户行所在省
			String corpBankCity = jsonBean.getJSONDataAt(i).getString("corpBankCity");//对私账号开户行所在市    settleRpt,companyNam,settleAcct,legalNam,feeAcct,dirAccountName,dirAccount
			String settleRpt = jsonBean.getJSONDataAt(i).getString("settleRpt");//结算账户类型
			String companyNam = jsonBean.getJSONDataAt(i).getString("companyNam");//对公账号名称
			String settleAcct = jsonBean.getJSONDataAt(i).getString("settleAcct");//对公账号
			String legalNam = jsonBean.getJSONDataAt(i).getString("legalNam");//对私账号名称
			String feeAcct = jsonBean.getJSONDataAt(i).getString("feeAcct");//对私账号
			String dirAccountName = jsonBean.getJSONDataAt(i).getString("dirAccountName");//定向委托账号名称
			String dirAccount = jsonBean.getJSONDataAt(i).getString("dirAccount");//定向委托账号
			String auditStatus = jsonBean.getJSONDataAt(i).getString("auditStatus");//定向委托账号
			
			try {
				tblMchtSumrzInf = t80224BO.get(new Integer(seqNo));
				tblMchtSumrzInf.setAuditStatus("3");
				tblMchtSumrzInf.setAuditId(getOperator().getOprId());
				SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMdd");
				Date date1=new Date();
				tblMchtSumrzInf.setAuditDate(dateFormater.format(date1));
				tblMchtSumrzInfList.add(tblMchtSumrzInf);
			} catch (Exception e) {
				log.error("二次新增失败："+e);
			}
		}
		try {
			rspCode=t80224BO.update(tblMchtSumrzInfList);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode,e);
		}
		log("商户划二次款新增，操作员编号："+getOperator().getOprId());
		return returnService(rspCode);
		
	}
	
	
	//审核拒绝
	public String refuseRem() {
		jsonBean.parseJSONArrayData(getInfList());
		int len = jsonBean.getArray().size();
		TblMchtSumrzInf tblMchtSumrzInf = null;
		List<TblMchtSumrzInf> tblMchtSumrzInfList = new ArrayList<TblMchtSumrzInf>(len);
		int l = 0;
		for (int i = 0; i < len; i++) {
			String seqNo = jsonBean.getJSONDataAt(i).getString("seqNo");//主键
			String auditId = jsonBean.getJSONDataAt(i).getString("auditId");//发起划款人
			String auditDate = jsonBean.getJSONDataAt(i).getString("auditDate");//发起划款时间
			
			System.out.println("发起划款人:"+auditId +"		发起划款时间:"+auditDate);
			
			try{
				if (getOperator().getOprId().equals(auditId)) {
					l++ ;
					System.out.println("发起与审核人同一用户！");
					log.error("发起与审核人同一用户！");
					return returnService("发起与审核人同一用户！");
//					continue; 
				}
			}catch (Exception e) {
				log.error("审核判断数据出错"+e.getMessage());
			}
			tblMchtSumrzInf = t80224BO.get(new Integer(seqNo));
			tblMchtSumrzInf.setAuditStatus("2");
			SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMdd");
			Date date1=new Date();
			tblMchtSumrzInf.setRecId(getOperator().getOprId());
			tblMchtSumrzInf.setRecDate(dateFormater.format(date1));
			tblMchtSumrzInfList.add(tblMchtSumrzInf);
		}
		try {
			rspCode=t80224BO.update(tblMchtSumrzInfList);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode,e);
		}
		log("商户划款审核拒绝，操作员编号："+getOperator().getOprId());
		return returnService(rspCode);
	}
	//二次审核拒绝
	public String refuseRemTwo() {
		jsonBean.parseJSONArrayData(getInfList());
		int len = jsonBean.getArray().size();
		TblMchtSumrzInf tblMchtSumrzInf = null;
		List<TblMchtSumrzInf> tblMchtSumrzInfList = new ArrayList<TblMchtSumrzInf>(len);
		int l = 0;
		for (int i = 0; i < len; i++) {
			String seqNo = jsonBean.getJSONDataAt(i).getString("seqNo");//主键
			String auditId = jsonBean.getJSONDataAt(i).getString("auditId");//发起划款人
			String auditDate = jsonBean.getJSONDataAt(i).getString("auditDate");//发起划款时间
			
			System.out.println("发起二次划款人:"+auditId +"		发起二次划款时间:"+auditDate);
			log.info("发起二次划款人:"+auditId +"		发起二次划款时间:"+auditDate);
			
			try{
				if (getOperator().getOprId().equals(auditId)) {
					l++ ;
					log.info("发起与审核人同一用户！");
					
					return returnService("发起与审核人同一用户！");
//					continue; 
				}
			}catch (Exception e) {
				log.error("审核判断数据出错"+e.getMessage());
			}
			tblMchtSumrzInf = t80224BO.get(new Integer(seqNo));
			tblMchtSumrzInf.setAuditStatus("5");
			SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMdd");
			Date date1=new Date();
			tblMchtSumrzInf.setRecId(getOperator().getOprId());
			tblMchtSumrzInf.setRecDate(dateFormater.format(date1));
			tblMchtSumrzInfList.add(tblMchtSumrzInf);
		}
		try {
			rspCode=t80224BO.update(tblMchtSumrzInfList);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode,e);
		}
		log("商户划款审核拒绝，操作员编号："+getOperator().getOprId());
		return returnService(rspCode);
	}
	//银企直连商户划款	审核通过
	public String remittance() {
		String lgnnam = SysParamUtil.getParam(SysParamConstants.LGNNAM);//银企直连登陆用户名
		String buscod = SysParamUtil.getParam(SysParamConstants.BUSCOD);//银企直业务类别
		String nusage = SysParamUtil.getParam(SysParamConstants.NUSAGE);//用途
		String crtsqn = SysParamUtil.getParam(SysParamConstants.CRTSQN);//收方编号
		String dbtacc = SysParamUtil.getParam(SysParamConstants.DBTACC);//付方帐号
		String dbtbnk = SysParamUtil.getParam(SysParamConstants.DBTBNK);//付方开户地区
		String c_dbtbbk = SysParamUtil.getParam(SysParamConstants.C_DBTBBK);//付方开户地区
		String dbtnam = SysParamUtil.getParam(SysParamConstants.DBTNAM);//付方帐户名
		String dbtrel = SysParamUtil.getParam(SysParamConstants.DBTREL);//付方客户关系号
		jsonBean.parseJSONArrayData(getInfList());
		int len = jsonBean.getArray().size();
		TblMchtSumrzInf tblMchtSumrzInf = null;
		List<TblMchtSumrzInf> tblMchtSumrzInfList = new ArrayList<TblMchtSumrzInf>(len);
		int l = 0;
		for (int i = 0; i < len; i++) {
			String seqNo = jsonBean.getJSONDataAt(i).getString("seqNo");//主键
			String mchtNm = jsonBean.getJSONDataAt(i).getString("mchtNm");//商户号
			String accFlag = jsonBean.getJSONDataAt(i).getString("accFlag");//账户类型
			String settleAccName = jsonBean.getJSONDataAt(i).getString("settleAccName");//结算账户
			String bankName = jsonBean.getJSONDataAt(i).getString("bankName");//开户行
			String txnAmt = jsonBean.getJSONDataAt(i).getString("txnAmt");//交易金额
			String handAmt = jsonBean.getJSONDataAt(i).getString("handAmt");//手续费
			String sumAmt = jsonBean.getJSONDataAt(i).getString("sumAmt");//结算金额
			String saStatus = jsonBean.getJSONDataAt(i).getString("saStatus");//状态
			String sumrzDate = jsonBean.getJSONDataAt(i).getString("sumrzDate");//划款日期
			String sumrzNote = jsonBean.getJSONDataAt(i).getString("sumrzNote");//备注
			String dirOpenBank = jsonBean.getJSONDataAt(i).getString("dirOpenBank");//定向委托账号开户总行名称
			String dirBankProvince = jsonBean.getJSONDataAt(i).getString("dirBankProvince");//定向委托账号开户行所在省
			String dirBankCity = jsonBean.getJSONDataAt(i).getString("dirBankCity");//定向委托账号开户行所在市
			String compOpenBank = jsonBean.getJSONDataAt(i).getString("compOpenBank");//对公账号开户总行名称
			String compBankProvince = jsonBean.getJSONDataAt(i).getString("compBankProvince");//对公账号开户行所在省
			String compBankCity = jsonBean.getJSONDataAt(i).getString("compBankCity");//对公账号开户行所在市
			String corpOpenBank = jsonBean.getJSONDataAt(i).getString("corpOpenBank");//对私账号开户总行名称
			String corpBankProvince = jsonBean.getJSONDataAt(i).getString("corpBankProvince");//对私账号开户行所在省
			String corpBankCity = jsonBean.getJSONDataAt(i).getString("corpBankCity");//对私账号开户行所在市    settleRpt,companyNam,settleAcct,legalNam,feeAcct,dirAccountName,dirAccount
			String settleRpt = jsonBean.getJSONDataAt(i).getString("settleRpt");//结算账户类型
			String companyNam = jsonBean.getJSONDataAt(i).getString("companyNam");//对公账号名称
			String settleAcct = jsonBean.getJSONDataAt(i).getString("settleAcct");//对公账号
			String legalNam = jsonBean.getJSONDataAt(i).getString("legalNam");//对私账号名称
			String feeAcct = jsonBean.getJSONDataAt(i).getString("feeAcct");//对私账号
			String dirAccountName = jsonBean.getJSONDataAt(i).getString("dirAccountName");//定向委托账号名称
			String dirAccount = jsonBean.getJSONDataAt(i).getString("dirAccount");//定向委托账号
			
			String auditId = jsonBean.getJSONDataAt(i).getString("auditId");//发起划款人
			String auditDate = jsonBean.getJSONDataAt(i).getString("auditDate");//发起划款时间
			
			System.out.println("发起划款人:"+auditId +"		发起划款时间:"+auditDate);
			log.info("发起划款人:"+auditId +"		发起划款时间:"+auditDate);
			try{
				
				if (getOperator().getOprId().equals(auditId)) {
					l++ ;
					log.info("发起与审核人同一用户！");
					return returnService("发起与审核人同一用户！");
//					continue; 
				}
				
			}catch (Exception e) {
				log.error("审核判断数据出错"+e.getMessage());
			}
			
//			System.out.println("主键："+seqNo+"商户号："+mchtNm+"账户类型："+accFlag+"结算账户："+settleAccName+"开户行："+bankName+"交易金额："+txnAmt+"手续费："+handAmt);
//			System.err.println("结算账户类型："+settleRpt+"对公账号："+settleAcct+"对私账号："+feeAcct+"定向委托账号："+dirAccount);
//			System.err.println("对公账号名称："+companyNam+"对私账号名称："+legalNam+"定向委托账号名称："+dirAccountName);
			try {
				// 构造支付的请求报文
				/*XmlPacket xmlPkt = new XmlPacket("Payment", "USRA01");
				Map mpPodInfo = new Properties();
				mpPodInfo.put("BUSCOD", "N02031");
				xmlPkt.putProperty("SDKPAYRQX", mpPodInfo);
				Map mpPayInfo = new Properties();
				mpPayInfo.put("YURREF", "201009270001");
				mpPayInfo.put("DBTACC", "571905400910411");
				mpPayInfo.put("DBTBBK", "57");
				mpPayInfo.put("DBTBNK", "招商银行杭州分行营业部");
				mpPayInfo.put("DBTNAM", "NEXT TEST");
				mpPayInfo.put("DBTREL", "0000007715");
				mpPayInfo.put("TRSAMT", "1.01");
				mpPayInfo.put("CCYNBR", "10");
				mpPayInfo.put("STLCHN", "N");
				mpPayInfo.put("NUSAGE", "费用报销款");
				mpPayInfo.put("CRTACC", "571905400810812");
				mpPayInfo.put("CRTNAM", "测试收款户");
				mpPayInfo.put("CRTBNK", "招商银行");
				mpPayInfo.put("CTYCOD", "ZJHZ");
				mpPayInfo.put("CRTSQN", "摘要信息:[1.01]");
				xmlPkt.putProperty("SDKPAYDTX", mpPayInfo);*/
				XmlPacket xmlPkt = new XmlPacket("Payment", lgnnam);//登陆用户名
				Map mpPodInfo = new Properties();
				mpPodInfo.put("BUSCOD", buscod);//业务类别
				xmlPkt.putProperty("SDKPAYRQX", mpPodInfo);
				Map mpPayInfo = new Properties();
				//用于标识该笔业务的编号，企业银行编号+业务类型+业务参考号必须唯一。企业可以自定义业务参考号，也可使用银行缺省值（单笔支付），批量支付须由企业提供。直联必须用企业提供
				String yurref;
				//临时注释
				if(settleRpt.equals("1")){//对私账户
					mpPayInfo.put("YURREF", seqNo);//业务参考号
					mpPayInfo.put("DBTACC", dbtacc);//付方帐号
					mpPayInfo.put("C_DBTBBK", c_dbtbbk);//付方开户地区
					mpPayInfo.put("DBTBNK", dbtbnk);//付方开户行
					mpPayInfo.put("DBTNAM", dbtnam);//付方帐户名
					mpPayInfo.put("DBTREL", dbtrel);//付方客户关系号
					mpPayInfo.put("TRSAMT", sumAmt);//交易金额
					mpPayInfo.put("CCYNBR", "10");//币种代码
					mpPayInfo.put("STLCHN", "N");//结算方式代码
					mpPayInfo.put("NUSAGE", nusage);//用途
					mpPayInfo.put("CRTACC", feeAcct);//收方帐号
					mpPayInfo.put("CRTNAM", settleAccName);//收方帐户名
					mpPayInfo.put("CRTBNK", bankName);//收方开户行
					mpPayInfo.put("CRTPVC", corpBankProvince);//省corpBankProvince
					mpPayInfo.put("CRTCTY", corpBankCity);//市corpBankCity
//					mpPayInfo.put("CRTSQN", crtsqn);//收方编号		
				}else if(settleRpt.equals("2")){//对公账户
					mpPayInfo.put("YURREF", seqNo);//业务参考号
					mpPayInfo.put("DBTACC", dbtacc);//付方帐号
					mpPayInfo.put("C_DBTBBK", c_dbtbbk);//付方开户地区
					mpPayInfo.put("DBTBNK", dbtbnk);//付方开户行
					mpPayInfo.put("DBTNAM", dbtnam);//付方帐户名
					mpPayInfo.put("DBTREL", dbtrel);//付方客户关系号
					mpPayInfo.put("TRSAMT", sumAmt);//交易金额
					mpPayInfo.put("CCYNBR", "10");//币种代码
					mpPayInfo.put("STLCHN", "N");//结算方式代码
					mpPayInfo.put("NUSAGE", nusage);//用途
					mpPayInfo.put("CRTACC", settleAcct);//收方帐号
					mpPayInfo.put("CRTNAM", settleAccName);//收方帐户名
					mpPayInfo.put("CRTBNK", bankName);//收方开户行compOpenBank
					mpPayInfo.put("CRTPVC", compBankProvince);//省compBankProvince
					mpPayInfo.put("CRTCTY", compBankCity);//市
					mpPayInfo.put("CRTSQN", crtsqn);//收方编号		
				}else if(settleRpt.equals("3")){//定向委托账户
					mpPayInfo.put("YURREF", seqNo);//业务参考号
					mpPayInfo.put("DBTACC", dbtacc);//付方帐号
					mpPayInfo.put("C_DBTBBK", c_dbtbbk);//付方开户地区
					mpPayInfo.put("DBTBNK", dbtbnk);//付方开户行
					mpPayInfo.put("DBTNAM", dbtnam);//付方帐户名
					mpPayInfo.put("DBTREL", dbtrel);//付方客户关系号
					mpPayInfo.put("TRSAMT", sumAmt);//交易金额
					mpPayInfo.put("CCYNBR", "10");//币种代码
					mpPayInfo.put("STLCHN", "N");//结算方式代码
					mpPayInfo.put("NUSAGE", nusage);//用途
					mpPayInfo.put("CRTACC", dirAccount);//收方帐号dirAccount
					mpPayInfo.put("CRTNAM", settleAccName);//收方帐户名dirAccountName settleAccName
					mpPayInfo.put("CRTBNK", bankName);//收方开户行dirOpenBank
					mpPayInfo.put("CRTPVC", dirBankProvince);//省dirBankProvince
					mpPayInfo.put("CRTCTY", dirBankCity);//市dirBankCity
					mpPayInfo.put("CRTSQN", crtsqn);//收方编号		
				}
				xmlPkt.putProperty("SDKPAYDTX", mpPayInfo);//支付输入明细接口
				// 生成请求报文
				String date = xmlPkt.toXmlString();
				log.info("生成请求报文:"+date);
				// 连接前置机，发送请求报文，获得返回报文
				String result = T80224Action.sendRequest(date);
				result = result.replace("GBK", "UTF-8");
				// 处理返回的结果
				String processResult = T80224Action.processResult(result);
				if (processResult.toUpperCase().equals("NTE")) {
					try {
						tblMchtSumrzInf = t80224BO.get(new Integer(seqNo));
						tblMchtSumrzInf.setSaStatus("2");
						tblMchtSumrzInf.setCauseStat("终审完毕");
						SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMdd");
						Date date1=new Date();
						tblMchtSumrzInf.setSumrzDate(dateFormater.format(date1));
						tblMchtSumrzInf.setRecUpdOpr(getOperator().getOprId());
						tblMchtSumrzInf.setRecUpdTs(CommonFunction.getCurrentDateTime());
						
						tblMchtSumrzInf.setAuditStatus("1");
						tblMchtSumrzInf.setRecId(getOperator().getOprId());
						tblMchtSumrzInf.setRecDate(dateFormater.format(date1));
						
						tblMchtSumrzInfList.add(tblMchtSumrzInf);
					} catch (Exception e) {
						log.error("划款后台处理失败："+e);
						return returnService("划款后台处理失败！请联系管理员！");
					}
				}else if (processResult.toUpperCase().equals("AUT")) {
					try {
						tblMchtSumrzInf = t80224BO.get(new Integer(seqNo));
						tblMchtSumrzInf.setSaStatus("2");
						tblMchtSumrzInf.setCauseStat("等待审批");
						SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMdd");
						Date date1=new Date();
						tblMchtSumrzInf.setSumrzDate(dateFormater.format(date1));
						tblMchtSumrzInf.setRecUpdOpr(getOperator().getOprId());
						tblMchtSumrzInf.setRecUpdTs(CommonFunction.getCurrentDateTime());
						
						tblMchtSumrzInf.setAuditStatus("1");
						tblMchtSumrzInf.setRecId(getOperator().getOprId());
						tblMchtSumrzInf.setRecDate(dateFormater.format(date1));
						
						tblMchtSumrzInfList.add(tblMchtSumrzInf);
					} catch (Exception e) {
						log.error("划款后台处理失败："+e);
						return returnService("划款后台处理失败！请联系管理员！");
					}
				} else if(processResult.toUpperCase().equals("WCF")) {
					try {
						tblMchtSumrzInf = t80224BO.get(new Integer(seqNo));
						tblMchtSumrzInf.setSaStatus("2");
						tblMchtSumrzInf.setCauseStat("订单待确认");
						SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMdd");
						Date date1=new Date();
						tblMchtSumrzInf.setSumrzDate(dateFormater.format(date1));
						tblMchtSumrzInf.setRecUpdOpr(getOperator().getOprId());
						tblMchtSumrzInf.setRecUpdTs(CommonFunction.getCurrentDateTime());
						
						tblMchtSumrzInf.setAuditStatus("1");
						tblMchtSumrzInf.setRecId(getOperator().getOprId());
						tblMchtSumrzInf.setRecDate(dateFormater.format(date1));
						
						tblMchtSumrzInfList.add(tblMchtSumrzInf);
					} catch (Exception e) {
						log.error("划款后台处理失败："+e);
						return returnService("划款后台处理失败！请联系管理员！");
					}
				} else if(processResult.toUpperCase().equals("BNK")) {
					try {
						tblMchtSumrzInf = t80224BO.get(new Integer(seqNo));
						tblMchtSumrzInf.setSaStatus("2");
						tblMchtSumrzInf.setCauseStat("银行处理中");
						SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMdd");
						Date date1=new Date();
						tblMchtSumrzInf.setSumrzDate(dateFormater.format(date1));
						tblMchtSumrzInf.setRecUpdOpr(getOperator().getOprId());
						tblMchtSumrzInf.setRecUpdTs(CommonFunction.getCurrentDateTime());
						
						tblMchtSumrzInf.setAuditStatus("1");
						tblMchtSumrzInf.setRecId(getOperator().getOprId());
						tblMchtSumrzInf.setRecDate(dateFormater.format(date1));
						
						tblMchtSumrzInfList.add(tblMchtSumrzInf);
					} catch (Exception e) {
						log.error("划款后台处理失败："+e);
						return returnService("划款后台处理失败！请联系管理员！");
					}
				} else if(processResult.toUpperCase().equals("ACK")) {
					try {
						tblMchtSumrzInf = t80224BO.get(new Integer(seqNo));
						tblMchtSumrzInf.setSaStatus("2");
						tblMchtSumrzInf.setCauseStat("等待确认");
						SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMdd");
						Date date1=new Date();
						tblMchtSumrzInf.setSumrzDate(dateFormater.format(date1));
						tblMchtSumrzInf.setRecUpdOpr(getOperator().getOprId());
						tblMchtSumrzInf.setRecUpdTs(CommonFunction.getCurrentDateTime());
						
						tblMchtSumrzInf.setAuditStatus("1");
						tblMchtSumrzInf.setRecId(getOperator().getOprId());
						tblMchtSumrzInf.setRecDate(dateFormater.format(date1));
						
						tblMchtSumrzInfList.add(tblMchtSumrzInf);
					} catch (Exception e) {
						log.error("划款后台处理失败："+e);
						return returnService("划款后台处理失败！请联系管理员！");
					}
				} else if(processResult.toUpperCase().equals("APD")) {
					try {
						tblMchtSumrzInf = t80224BO.get(new Integer(seqNo));
						tblMchtSumrzInf.setSaStatus("2");
						tblMchtSumrzInf.setCauseStat("待银行确认");
						SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMdd");
						Date date1=new Date();
						tblMchtSumrzInf.setSumrzDate(dateFormater.format(date1));
						tblMchtSumrzInf.setRecUpdOpr(getOperator().getOprId());
						tblMchtSumrzInf.setRecUpdTs(CommonFunction.getCurrentDateTime());
						
						tblMchtSumrzInf.setAuditStatus("1");
						tblMchtSumrzInf.setRecId(getOperator().getOprId());
						tblMchtSumrzInf.setRecDate(dateFormater.format(date1));
						
						tblMchtSumrzInfList.add(tblMchtSumrzInf);
					} catch (Exception e) {
						log.error("划款后台处理失败："+e);
						return returnService("划款后台处理失败！请联系管理员！");
					}
				} else if(processResult.toUpperCase().equals("OPR")) {
					try {
						tblMchtSumrzInf = t80224BO.get(new Integer(seqNo));
						tblMchtSumrzInf.setSaStatus("2");
						tblMchtSumrzInf.setCauseStat("数据接收中");
						SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMdd");
						Date date1=new Date();
						tblMchtSumrzInf.setSumrzDate(dateFormater.format(date1));
						tblMchtSumrzInf.setRecUpdOpr(getOperator().getOprId());
						tblMchtSumrzInf.setRecUpdTs(CommonFunction.getCurrentDateTime());
						
						tblMchtSumrzInf.setAuditStatus("1");
						tblMchtSumrzInf.setRecId(getOperator().getOprId());
						tblMchtSumrzInf.setRecDate(dateFormater.format(date1));
						
						tblMchtSumrzInfList.add(tblMchtSumrzInf);
					} catch (Exception e) {
						log.error("划款后台处理失败："+e);
						return returnService("划款后台处理失败！请联系管理员！");
					}
				} else{
					tblMchtSumrzInf = t80224BO.get(new Integer(seqNo));
					tblMchtSumrzInf.setSaStatus("0");
					tblMchtSumrzInf.setCauseStat(processResult);
					SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMdd");
					Date date1=new Date();
					tblMchtSumrzInf.setSumrzDate(dateFormater.format(date1));
					tblMchtSumrzInf.setRecUpdOpr(getOperator().getOprId());
					tblMchtSumrzInf.setRecUpdTs(CommonFunction.getCurrentDateTime());
					tblMchtSumrzInf.setAuditStatus("1");
					tblMchtSumrzInf.setRecId(getOperator().getOprId());
					tblMchtSumrzInf.setRecDate(dateFormater.format(date1));
					tblMchtSumrzInfList.add(tblMchtSumrzInf);
//					return returnService("支付失败" + processResult);
					
				}
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
//				return "划款失败";
				return returnService(rspCode,e);
			}
		}
		try {
			rspCode=t80224BO.update(tblMchtSumrzInfList);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode,e);
		}
		log("商户划款成功回填成功，操作员编号："+getOperator().getOprId());
		return returnService(rspCode);
		
	}
	//二次审核 通过
	public String remittanceTwo() {
		String lgnnam = SysParamUtil.getParam(SysParamConstants.LGNNAM);//银企直连登陆用户名
		String buscod = SysParamUtil.getParam(SysParamConstants.BUSCOD);//银企直业务类别
		String nusage = SysParamUtil.getParam(SysParamConstants.NUSAGE);//用途
		String crtsqn = SysParamUtil.getParam(SysParamConstants.CRTSQN);//收方编号
		String dbtacc = SysParamUtil.getParam(SysParamConstants.DBTACC);//付方帐号
		String dbtbnk = SysParamUtil.getParam(SysParamConstants.DBTBNK);//付方开户地区
		String c_dbtbbk = SysParamUtil.getParam(SysParamConstants.C_DBTBBK);//付方开户地区
		String dbtnam = SysParamUtil.getParam(SysParamConstants.DBTNAM);//付方帐户名
		String dbtrel = SysParamUtil.getParam(SysParamConstants.DBTREL);//付方客户关系号
		jsonBean.parseJSONArrayData(getInfList());
		int len = jsonBean.getArray().size();
		TblMchtSumrzInf tblMchtSumrzInf = null;
		List<TblMchtSumrzInf> tblMchtSumrzInfList = new ArrayList<TblMchtSumrzInf>(len);
		int l = 0;
		for (int i = 0; i < len; i++) {
			String seqNo = jsonBean.getJSONDataAt(i).getString("seqNo");//主键
			String mchtNm = jsonBean.getJSONDataAt(i).getString("mchtNm");//商户号
			String accFlag = jsonBean.getJSONDataAt(i).getString("accFlag");//账户类型
			String settleAccName = jsonBean.getJSONDataAt(i).getString("settleAccName");//结算账户
			String bankName = jsonBean.getJSONDataAt(i).getString("bankName");//开户行
			String txnAmt = jsonBean.getJSONDataAt(i).getString("txnAmt");//交易金额
			String handAmt = jsonBean.getJSONDataAt(i).getString("handAmt");//手续费
			String sumAmt = jsonBean.getJSONDataAt(i).getString("sumAmt");//结算金额
			String saStatus = jsonBean.getJSONDataAt(i).getString("saStatus");//状态
			String sumrzDate = jsonBean.getJSONDataAt(i).getString("sumrzDate");//划款日期
			String sumrzNote = jsonBean.getJSONDataAt(i).getString("sumrzNote");//备注
			String dirOpenBank = jsonBean.getJSONDataAt(i).getString("dirOpenBank");//定向委托账号开户总行名称
			String dirBankProvince = jsonBean.getJSONDataAt(i).getString("dirBankProvince");//定向委托账号开户行所在省
			String dirBankCity = jsonBean.getJSONDataAt(i).getString("dirBankCity");//定向委托账号开户行所在市
			String compOpenBank = jsonBean.getJSONDataAt(i).getString("compOpenBank");//对公账号开户总行名称
			String compBankProvince = jsonBean.getJSONDataAt(i).getString("compBankProvince");//对公账号开户行所在省
			String compBankCity = jsonBean.getJSONDataAt(i).getString("compBankCity");//对公账号开户行所在市
			String corpOpenBank = jsonBean.getJSONDataAt(i).getString("corpOpenBank");//对私账号开户总行名称
			String corpBankProvince = jsonBean.getJSONDataAt(i).getString("corpBankProvince");//对私账号开户行所在省
			String corpBankCity = jsonBean.getJSONDataAt(i).getString("corpBankCity");//对私账号开户行所在市    settleRpt,companyNam,settleAcct,legalNam,feeAcct,dirAccountName,dirAccount
			String settleRpt = jsonBean.getJSONDataAt(i).getString("settleRpt");//结算账户类型
			String companyNam = jsonBean.getJSONDataAt(i).getString("companyNam");//对公账号名称
			String settleAcct = jsonBean.getJSONDataAt(i).getString("settleAcct");//对公账号
			String legalNam = jsonBean.getJSONDataAt(i).getString("legalNam");//对私账号名称
			String feeAcct = jsonBean.getJSONDataAt(i).getString("feeAcct");//对私账号
			String dirAccountName = jsonBean.getJSONDataAt(i).getString("dirAccountName");//定向委托账号名称
			String dirAccount = jsonBean.getJSONDataAt(i).getString("dirAccount");//定向委托账号
			
			String auditId = jsonBean.getJSONDataAt(i).getString("auditId");//发起划款人
			String auditDate = jsonBean.getJSONDataAt(i).getString("auditDate");//发起划款时间
			
			System.out.println("发起划款人:"+auditId +"		发起划款时间:"+auditDate);
			log.info("发起划款人:"+auditId +"		发起划款时间:"+auditDate);
			try{
				
				if (getOperator().getOprId().equals(auditId)) {
					l++ ;
//					System.out.println("发起与审核人同一用户！");
					log.error("发起与审核人同一用户！");
					return returnService("发起与审核人同一用户！");
//					continue; 
				}
				
			}catch (Exception e) {
				log.error("审核判断数据出错"+e.getMessage());
			}
			
			try {
				// 构造支付的请求报文
				/*XmlPacket xmlPkt = new XmlPacket("Payment", "USRA01");
				Map mpPodInfo = new Properties();
				mpPodInfo.put("BUSCOD", "N02031");
				xmlPkt.putProperty("SDKPAYRQX", mpPodInfo);
				Map mpPayInfo = new Properties();
				mpPayInfo.put("YURREF", "201009270001");
				mpPayInfo.put("DBTACC", "571905400910411");
				mpPayInfo.put("DBTBBK", "57");
				mpPayInfo.put("DBTBNK", "招商银行杭州分行营业部");
				mpPayInfo.put("DBTNAM", "NEXT TEST");
				mpPayInfo.put("DBTREL", "0000007715");
				mpPayInfo.put("TRSAMT", "1.01");
				mpPayInfo.put("CCYNBR", "10");
				mpPayInfo.put("STLCHN", "N");
				mpPayInfo.put("NUSAGE", "费用报销款");
				mpPayInfo.put("CRTACC", "571905400810812");
				mpPayInfo.put("CRTNAM", "测试收款户");
				mpPayInfo.put("CRTBNK", "招商银行");
				mpPayInfo.put("CTYCOD", "ZJHZ");
				mpPayInfo.put("CRTSQN", "摘要信息:[1.01]");
				xmlPkt.putProperty("SDKPAYDTX", mpPayInfo);*/
				XmlPacket xmlPkt = new XmlPacket("Payment", lgnnam);//登陆用户名
				Map mpPodInfo = new Properties();
				mpPodInfo.put("BUSCOD", buscod);//业务类别
				xmlPkt.putProperty("SDKPAYRQX", mpPodInfo);
				Map mpPayInfo = new Properties();
				//用于标识该笔业务的编号，企业银行编号+业务类型+业务参考号必须唯一。企业可以自定义业务参考号，也可使用银行缺省值（单笔支付），批量支付须由企业提供。直联必须用企业提供
				String yurref;
				//临时注释
				if(settleRpt.equals("1")){//对私账户
					mpPayInfo.put("YURREF", seqNo);//业务参考号
					mpPayInfo.put("DBTACC", dbtacc);//付方帐号
					mpPayInfo.put("C_DBTBBK", c_dbtbbk);//付方开户地区
					mpPayInfo.put("DBTBNK", dbtbnk);//付方开户行
					mpPayInfo.put("DBTNAM", dbtnam);//付方帐户名
					mpPayInfo.put("DBTREL", dbtrel);//付方客户关系号
					mpPayInfo.put("TRSAMT", sumAmt);//交易金额
					mpPayInfo.put("CCYNBR", "10");//币种代码
					mpPayInfo.put("STLCHN", "N");//结算方式代码
					mpPayInfo.put("NUSAGE", nusage);//用途
					mpPayInfo.put("CRTACC", feeAcct);//收方帐号
					mpPayInfo.put("CRTNAM", settleAccName);//收方帐户名settleAccName
					mpPayInfo.put("CRTBNK", corpOpenBank);//收方开户行
					mpPayInfo.put("CRTPVC", corpBankProvince);//省corpBankProvince
					mpPayInfo.put("CRTCTY", corpBankCity);//市corpBankCity
				}else if(settleRpt.equals("2")){//对公账户
					mpPayInfo.put("YURREF", seqNo);//业务参考号
					mpPayInfo.put("DBTACC", dbtacc);//付方帐号
					mpPayInfo.put("C_DBTBBK", c_dbtbbk);//付方开户地区
					mpPayInfo.put("DBTBNK", dbtbnk);//付方开户行
					mpPayInfo.put("DBTNAM", dbtnam);//付方帐户名
					mpPayInfo.put("DBTREL", dbtrel);//付方客户关系号
					mpPayInfo.put("TRSAMT", sumAmt);//交易金额
					mpPayInfo.put("CCYNBR", "10");//币种代码
					mpPayInfo.put("STLCHN", "N");//结算方式代码
					mpPayInfo.put("NUSAGE", nusage);//用途
					mpPayInfo.put("CRTACC", settleAcct);//收方帐号
					mpPayInfo.put("CRTNAM", settleAccName);//收方帐户名
					mpPayInfo.put("CRTBNK", bankName);//收方开户行compOpenBankCRTPVC
					mpPayInfo.put("CRTPVC", compBankProvince);//省compBankProvince
					mpPayInfo.put("CRTCTY", compBankCity);//市
					mpPayInfo.put("CRTSQN", crtsqn);//收方编号		
				}else if(settleRpt.equals("3")){//定向委托账户
					mpPayInfo.put("YURREF", seqNo);//业务参考号
					mpPayInfo.put("DBTACC", dbtacc);//付方帐号
					mpPayInfo.put("C_DBTBBK", c_dbtbbk);//付方开户地区
					mpPayInfo.put("DBTBNK", dbtbnk);//付方开户行
					mpPayInfo.put("DBTNAM", dbtnam);//付方帐户名
					mpPayInfo.put("DBTREL", dbtrel);//付方客户关系号
					mpPayInfo.put("TRSAMT", sumAmt);//交易金额
					mpPayInfo.put("CCYNBR", "10");//币种代码
					mpPayInfo.put("STLCHN", "N");//结算方式代码
					mpPayInfo.put("NUSAGE", nusage);//用途
					mpPayInfo.put("CRTACC", dirAccount);//收方帐号dirAccount
					mpPayInfo.put("CRTNAM", settleAccName);//收方帐户名dirAccountName
					mpPayInfo.put("CRTBNK", bankName);//收方开户行dirOpenBank
					mpPayInfo.put("CRTPVC", dirBankProvince);//省dirBankProvince
					mpPayInfo.put("CRTCTY", dirBankCity);//市dirBankCity
					mpPayInfo.put("CRTSQN", crtsqn);//收方编号		
				}
				xmlPkt.putProperty("SDKPAYDTX", mpPayInfo);//支付输入明细接口
				// 生成请求报文
				String date = xmlPkt.toXmlString();
				log.info("生成请求报文:"+date);
				// 连接前置机，发送请求报文，获得返回报文
				String result = T80224Action.sendRequest(date);
				result = result.replace("GBK", "UTF-8");
				// 处理返回的结果
				String processResult = T80224Action.processResult(result);
				
				if (processResult.toUpperCase().equals("NTE")) {
					try {
						tblMchtSumrzInf = t80224BO.get(new Integer(seqNo));
						tblMchtSumrzInf.setSaStatus("2");
						tblMchtSumrzInf.setCauseStat("终审完毕");
						SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMdd");
						Date date1=new Date();
						tblMchtSumrzInf.setSumrzDate(dateFormater.format(date1));
						tblMchtSumrzInf.setRecUpdOpr(getOperator().getOprId());
						tblMchtSumrzInf.setRecUpdTs(CommonFunction.getCurrentDateTime());
						
						tblMchtSumrzInf.setAuditStatus("1");
						tblMchtSumrzInf.setRecId(getOperator().getOprId());
						tblMchtSumrzInf.setRecDate(dateFormater.format(date1));
						
						tblMchtSumrzInfList.add(tblMchtSumrzInf);
					} catch (Exception e) {
						log.error("划款后台处理失败："+e);
						return returnService("划款后台处理失败！请联系管理员！");
					}
				}else if (processResult.toUpperCase().equals("AUT")) {
					try {
						tblMchtSumrzInf = t80224BO.get(new Integer(seqNo));
						tblMchtSumrzInf.setSaStatus("2");
						tblMchtSumrzInf.setCauseStat("等待审批");
						SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMdd");
						Date date1=new Date();
						tblMchtSumrzInf.setSumrzDate(dateFormater.format(date1));
						tblMchtSumrzInf.setRecUpdOpr(getOperator().getOprId());
						tblMchtSumrzInf.setRecUpdTs(CommonFunction.getCurrentDateTime());
						
						tblMchtSumrzInf.setAuditStatus("1");
						tblMchtSumrzInf.setRecId(getOperator().getOprId());
						tblMchtSumrzInf.setRecDate(dateFormater.format(date1));
						
						tblMchtSumrzInfList.add(tblMchtSumrzInf);
					} catch (Exception e) {
						log.error("划款后台处理失败："+e);
						return returnService("划款后台处理失败！请联系管理员！");
					}
				} else if(processResult.toUpperCase().equals("WCF")) {
					try {
						tblMchtSumrzInf = t80224BO.get(new Integer(seqNo));
						tblMchtSumrzInf.setSaStatus("2");
						tblMchtSumrzInf.setCauseStat("订单待确认");
						SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMdd");
						Date date1=new Date();
						tblMchtSumrzInf.setSumrzDate(dateFormater.format(date1));
						tblMchtSumrzInf.setRecUpdOpr(getOperator().getOprId());
						tblMchtSumrzInf.setRecUpdTs(CommonFunction.getCurrentDateTime());
						
						tblMchtSumrzInf.setAuditStatus("1");
						tblMchtSumrzInf.setRecId(getOperator().getOprId());
						tblMchtSumrzInf.setRecDate(dateFormater.format(date1));
						
						tblMchtSumrzInfList.add(tblMchtSumrzInf);
					} catch (Exception e) {
						log.error("划款后台处理失败："+e);
						return returnService("划款后台处理失败！请联系管理员！");
					}
				} else if(processResult.toUpperCase().equals("BNK")) {
					try {
						tblMchtSumrzInf = t80224BO.get(new Integer(seqNo));
						tblMchtSumrzInf.setSaStatus("2");
						tblMchtSumrzInf.setCauseStat("银行处理中");
						SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMdd");
						Date date1=new Date();
						tblMchtSumrzInf.setSumrzDate(dateFormater.format(date1));
						tblMchtSumrzInf.setRecUpdOpr(getOperator().getOprId());
						tblMchtSumrzInf.setRecUpdTs(CommonFunction.getCurrentDateTime());
						
						tblMchtSumrzInf.setAuditStatus("1");
						tblMchtSumrzInf.setRecId(getOperator().getOprId());
						tblMchtSumrzInf.setRecDate(dateFormater.format(date1));
						
						tblMchtSumrzInfList.add(tblMchtSumrzInf);
					} catch (Exception e) {
						log.error("划款后台处理失败："+e);
						return returnService("划款后台处理失败！请联系管理员！");
					}
				} else if(processResult.toUpperCase().equals("ACK")) {
					try {
						tblMchtSumrzInf = t80224BO.get(new Integer(seqNo));
						tblMchtSumrzInf.setSaStatus("2");
						tblMchtSumrzInf.setCauseStat("等待确认");
						SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMdd");
						Date date1=new Date();
						tblMchtSumrzInf.setSumrzDate(dateFormater.format(date1));
						tblMchtSumrzInf.setRecUpdOpr(getOperator().getOprId());
						tblMchtSumrzInf.setRecUpdTs(CommonFunction.getCurrentDateTime());
						
						tblMchtSumrzInf.setAuditStatus("1");
						tblMchtSumrzInf.setRecId(getOperator().getOprId());
						tblMchtSumrzInf.setRecDate(dateFormater.format(date1));
						
						tblMchtSumrzInfList.add(tblMchtSumrzInf);
					} catch (Exception e) {
						log.error("划款后台处理失败："+e);
						return returnService("划款后台处理失败！请联系管理员！");
					}
				} else if(processResult.toUpperCase().equals("APD")) {
					try {
						tblMchtSumrzInf = t80224BO.get(new Integer(seqNo));
						tblMchtSumrzInf.setSaStatus("2");
						tblMchtSumrzInf.setCauseStat("待银行确认");
						SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMdd");
						Date date1=new Date();
						tblMchtSumrzInf.setSumrzDate(dateFormater.format(date1));
						tblMchtSumrzInf.setRecUpdOpr(getOperator().getOprId());
						tblMchtSumrzInf.setRecUpdTs(CommonFunction.getCurrentDateTime());
						
						tblMchtSumrzInf.setAuditStatus("1");
						tblMchtSumrzInf.setRecId(getOperator().getOprId());
						tblMchtSumrzInf.setRecDate(dateFormater.format(date1));
						
						tblMchtSumrzInfList.add(tblMchtSumrzInf);
					} catch (Exception e) {
						log.error("划款后台处理失败："+e);
						return returnService("划款后台处理失败！请联系管理员！");
					}
				} else if(processResult.toUpperCase().equals("OPR")) {
					try {
						tblMchtSumrzInf = t80224BO.get(new Integer(seqNo));
						tblMchtSumrzInf.setSaStatus("2");
						tblMchtSumrzInf.setCauseStat("数据接收中");
						SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMdd");
						Date date1=new Date();
						tblMchtSumrzInf.setSumrzDate(dateFormater.format(date1));
						tblMchtSumrzInf.setRecUpdOpr(getOperator().getOprId());
						tblMchtSumrzInf.setRecUpdTs(CommonFunction.getCurrentDateTime());
						
						tblMchtSumrzInf.setAuditStatus("1");
						tblMchtSumrzInf.setRecId(getOperator().getOprId());
						tblMchtSumrzInf.setRecDate(dateFormater.format(date1));
						
						tblMchtSumrzInfList.add(tblMchtSumrzInf);
					} catch (Exception e) {
						log.error("划款后台处理失败："+e);
						return returnService("划款后台处理失败！请联系管理员！");
					}
				} else{
					tblMchtSumrzInf = t80224BO.get(new Integer(seqNo));
					tblMchtSumrzInf.setSaStatus("0");
					tblMchtSumrzInf.setCauseStat(processResult);
					SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMdd");
					Date date1=new Date();
					tblMchtSumrzInf.setSumrzDate(dateFormater.format(date1));
					tblMchtSumrzInf.setRecUpdOpr(getOperator().getOprId());
					tblMchtSumrzInf.setRecUpdTs(CommonFunction.getCurrentDateTime());
					tblMchtSumrzInf.setAuditStatus("1");
					tblMchtSumrzInf.setRecId(getOperator().getOprId());
					tblMchtSumrzInf.setRecDate(dateFormater.format(date1));
					tblMchtSumrzInfList.add(tblMchtSumrzInf);
//					return returnService("支付失败" + processResult);
					
				}
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return returnService(rspCode,e);
			}
		}
		try {
			rspCode=t80224BO.update(tblMchtSumrzInfList);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode,e);
		}
		log("商户划款成功回填成功，操作员编号："+getOperator().getOprId());
		return returnService(rspCode);
	}
	public String remittance2() {
		String lgnnam = SysParamUtil.getParam(SysParamConstants.LGNNAM);//银企直连登陆用户名
		String buscod = SysParamUtil.getParam(SysParamConstants.BUSCOD);//银企直业务类别
		String nusage = SysParamUtil.getParam(SysParamConstants.NUSAGE);//用途
		String crtsqn = SysParamUtil.getParam(SysParamConstants.CRTSQN);//收方编号
		String dbtacc = SysParamUtil.getParam(SysParamConstants.DBTACC);//付方帐号
		String dbtbnk = SysParamUtil.getParam(SysParamConstants.DBTBNK);//付方开户地区
		String c_dbtbbk = SysParamUtil.getParam(SysParamConstants.C_DBTBBK);//付方开户地区
		String dbtnam = SysParamUtil.getParam(SysParamConstants.DBTNAM);//付方帐户名
		String dbtrel = SysParamUtil.getParam(SysParamConstants.DBTREL);//付方客户关系号
		jsonBean.parseJSONArrayData(getInfList());
		int len = jsonBean.getArray().size();
		TblMchtSumrzInf tblMchtSumrzInf = null;
		List<TblMchtSumrzInf> tblMchtSumrzInfList = new ArrayList<TblMchtSumrzInf>(len);
		for (int i = 0; i < len; i++) {
			String seqNo = jsonBean.getJSONDataAt(i).getString("seqNo");//主键
			String mchtNm = jsonBean.getJSONDataAt(i).getString("mchtNm");//商户号
			String accFlag = jsonBean.getJSONDataAt(i).getString("accFlag");//账户类型
			String settleAccName = jsonBean.getJSONDataAt(i).getString("settleAccName");//结算账户
			String bankName = jsonBean.getJSONDataAt(i).getString("bankName");//开户行
			String txnAmt = jsonBean.getJSONDataAt(i).getString("txnAmt");//交易金额
			String handAmt = jsonBean.getJSONDataAt(i).getString("handAmt");//手续费
			String sumAmt = jsonBean.getJSONDataAt(i).getString("sumAmt");//结算金额
			String saStatus = jsonBean.getJSONDataAt(i).getString("saStatus");//状态
			String sumrzDate = jsonBean.getJSONDataAt(i).getString("sumrzDate");//划款日期
			String sumrzNote = jsonBean.getJSONDataAt(i).getString("sumrzNote");//备注
			String dirOpenBank = jsonBean.getJSONDataAt(i).getString("dirOpenBank");//定向委托账号开户总行名称
			String dirBankProvince = jsonBean.getJSONDataAt(i).getString("dirBankProvince");//定向委托账号开户行所在省
			String dirBankCity = jsonBean.getJSONDataAt(i).getString("dirBankCity");//定向委托账号开户行所在市
			String compOpenBank = jsonBean.getJSONDataAt(i).getString("compOpenBank");//对公账号开户总行名称
			String compBankProvince = jsonBean.getJSONDataAt(i).getString("compBankProvince");//对公账号开户行所在省
			String compBankCity = jsonBean.getJSONDataAt(i).getString("compBankCity");//对公账号开户行所在市
			String corpOpenBank = jsonBean.getJSONDataAt(i).getString("corpOpenBank");//对私账号开户总行名称
			String corpBankProvince = jsonBean.getJSONDataAt(i).getString("corpBankProvince");//对私账号开户行所在省
			String corpBankCity = jsonBean.getJSONDataAt(i).getString("corpBankCity");//对私账号开户行所在市    settleRpt,companyNam,settleAcct,legalNam,feeAcct,dirAccountName,dirAccount
			String settleRpt = jsonBean.getJSONDataAt(i).getString("settleRpt");//结算账户类型
			String companyNam = jsonBean.getJSONDataAt(i).getString("companyNam");//对公账号名称
			String settleAcct = jsonBean.getJSONDataAt(i).getString("settleAcct");//对公账号
			String legalNam = jsonBean.getJSONDataAt(i).getString("legalNam");//对私账号名称
			String feeAcct = jsonBean.getJSONDataAt(i).getString("feeAcct");//对私账号
			String dirAccountName = jsonBean.getJSONDataAt(i).getString("dirAccountName");//定向委托账号名称
			String dirAccount = jsonBean.getJSONDataAt(i).getString("dirAccount");//定向委托账号
			System.out.println("主键："+seqNo+"商户号："+mchtNm+"账户类型："+accFlag+"结算账户："+settleAccName+"开户行："+bankName+"交易金额："+txnAmt+"手续费："+handAmt);
			System.err.println("结算账户类型："+settleRpt+"对公账号："+settleAcct+"对私账号："+feeAcct+"定向委托账号："+dirAccount);
			System.err.println("对公账号名称："+companyNam+"对私账号名称："+legalNam+"定向委托账号名称："+dirAccountName);
			try {
				XmlPacket xmlPkt = new XmlPacket("Payment", lgnnam);//登陆用户名
				Map mpPodInfo = new Properties();
				mpPodInfo.put("BUSCOD", buscod);//业务类别
				xmlPkt.putProperty("SDKPAYRQX", mpPodInfo);
				Map mpPayInfo = new Properties();
				//用于标识该笔业务的编号，企业银行编号+业务类型+业务参考号必须唯一。企业可以自定义业务参考号，也可使用银行缺省值（单笔支付），批量支付须由企业提供。直联必须用企业提供
				String yurref;
				//临时注释   
				//TBL_MCHT_SUMRZ_INF
				//select COMP_BANK_PROVINCE,COMP_BANK_CITY from TBL_MCHT_SETTLE_INF where SETTLE_ACCT = '1001325129300008397';
				//select TERM_ID,MCHT_NO,SETTLE_TYPE,RATE_FLAG,SETTLE_CHN,BAT_TIME,AUTO_STL_FLG,PART_NUM,FEE_TYPE,FEE_FIXED,FEE_MAX_AMT,FEE_MIN_AMT,FEE_RATE,FEE_DIV_1,FEE_DIV_2,FEE_DIV_3,SETTLE_MODE,FEE_CYCLE,SETTLE_RPT,SETTLE_BANK_NO,SETTLE_BANK_NM,SETTLE_ACCT_NM,SETTLE_ACCT,FEE_ACCT_NM,FEE_ACCT,GROUP_FLAG,OPEN_STLNO,CHANGE_STLNO,SPE_SETTLE_TP,SPE_SETTLE_LV,LEGAL_NAM,COMPANY_NAM,SPE_SETTLE_DS,FEE_BACK_FLG,RESERVED,REC_UPD_TS,REC_CRT_TS,HOLIDAY_SET_FLAG,CURR_ACCOUNT,BANK_ACCOUNT_CODE,CORP_BANK_NAME,COMP_ACCOUNT_BANK_CODE,COMP_ACCOUNT_BANK_NAME,DIR_BANK_CODE,DIR_BANK_NAME,DIR_ACCOUNT_NAME,DIR_ACCOUNT,DIR_FLAG,AUTO_FLAG,CRE_FLAG,RETURN_FEE_FLAG,DIR_OPEN_BANK,DIR_BANK_PROVINCE,DIR_BANK_CITY,COMP_OPEN_BANK,COMP_BANK_PROVINCE,COMP_BANK_CITY,CORP_OPEN_BANK,CORP_BANK_PROVINCE,CORP_BANK_CITY from TBL_MCHT_SETTLE_INF where SETTLE_ACCT = '1001325129300008397';
				if(settleRpt.equals("1")){//对私账户
					mpPayInfo.put("YURREF", seqNo);//业务参考号
					mpPayInfo.put("DBTACC", dbtacc);//付方帐号
					mpPayInfo.put("C_DBTBBK", c_dbtbbk);//付方开户地区
					mpPayInfo.put("DBTBNK", dbtbnk);//付方开户行
					mpPayInfo.put("DBTNAM", dbtnam);//付方帐户名
					mpPayInfo.put("DBTREL", dbtrel);//付方客户关系号
					mpPayInfo.put("TRSAMT", sumAmt);//交易金额
					mpPayInfo.put("CCYNBR", "10");//币种代码
					mpPayInfo.put("STLCHN", "N");//结算方式代码
					mpPayInfo.put("NUSAGE", nusage);//用途
					mpPayInfo.put("CRTACC", feeAcct);//收方帐号
					mpPayInfo.put("CRTNAM", legalNam);//收方帐户名settleAccName
					mpPayInfo.put("CRTBNK", bankName);//收方开户行
					mpPayInfo.put("CRTPVC", corpBankProvince);//省corpBankProvince
					mpPayInfo.put("CRTCTY", corpBankCity);//市corpBankCity
//					mpPayInfo.put("CRTSQN", crtsqn);//收方编号		
				}else if(settleRpt.equals("2")){//对公账户
					mpPayInfo.put("YURREF", seqNo);//业务参考号
					mpPayInfo.put("DBTACC", dbtacc);//付方帐号
					mpPayInfo.put("C_DBTBBK", c_dbtbbk);//付方开户地区
					mpPayInfo.put("DBTBNK", dbtbnk);//付方开户行
					mpPayInfo.put("DBTNAM", dbtnam);//付方帐户名
					mpPayInfo.put("DBTREL", dbtrel);//付方客户关系号
					mpPayInfo.put("TRSAMT", sumAmt);//交易金额
					mpPayInfo.put("CCYNBR", "10");//币种代码
					mpPayInfo.put("STLCHN", "N");//结算方式代码
					mpPayInfo.put("NUSAGE", nusage);//用途
					mpPayInfo.put("CRTACC", settleAcct);//收方帐号
					mpPayInfo.put("CRTNAM", companyNam);//收方帐户名
					mpPayInfo.put("CRTBNK", bankName);//收方开户行compOpenBank
					mpPayInfo.put("CRTPVC", compBankProvince);//省compBankProvince
					mpPayInfo.put("CRTCTY", compBankCity);//市
					mpPayInfo.put("CRTSQN", crtsqn);//收方编号		
				}else if(settleRpt.equals("3")){//定向委托账户
					mpPayInfo.put("YURREF", seqNo);//业务参考号
					mpPayInfo.put("DBTACC", dbtacc);//付方帐号
					mpPayInfo.put("C_DBTBBK", c_dbtbbk);//付方开户地区
					mpPayInfo.put("DBTBNK", dbtbnk);//付方开户行
					mpPayInfo.put("DBTNAM", dbtnam);//付方帐户名
					mpPayInfo.put("DBTREL", dbtrel);//付方客户关系号
					mpPayInfo.put("TRSAMT", sumAmt);//交易金额
					mpPayInfo.put("CCYNBR", "10");//币种代码
					mpPayInfo.put("STLCHN", "N");//结算方式代码
					mpPayInfo.put("NUSAGE", nusage);//用途
					mpPayInfo.put("CRTACC", dirAccount);//收方帐号dirAccount
					mpPayInfo.put("CRTNAM", settleAccName);//收方帐户名dirAccountName
					mpPayInfo.put("CRTBNK", bankName);//收方开户行dirOpenBank
					mpPayInfo.put("CRTPVC", dirBankProvince);//省dirBankProvince
					mpPayInfo.put("CRTCTY", dirBankCity);//市dirBankCity
					mpPayInfo.put("CRTSQN", crtsqn);//收方编号		
				}
				xmlPkt.putProperty("SDKPAYDTX", mpPayInfo);//支付输入明细接口
				// 生成请求报文
				String date = xmlPkt.toXmlString();
				log.info("生成请求报文:"+date);
				// 连接前置机，发送请求报文，获得返回报文
				String result = T80224Action.sendRequest(date);
				result = result.replace("GBK", "UTF-8");
				// 处理返回的结果
				String processResult = T80224Action.processResult(result);
				if (processResult.equals("成功")) {
					try {
						tblMchtSumrzInf = t80224BO.get(new Integer(seqNo));
						tblMchtSumrzInf.setSaStatus("2");
						tblMchtSumrzInf.setCauseStat(processResult);
						SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMdd");
						Date date1=new Date();
						tblMchtSumrzInf.setSumrzDate(dateFormater.format(date1));
						tblMchtSumrzInf.setRecUpdOpr(getOperator().getOprId());
						tblMchtSumrzInf.setRecUpdTs(CommonFunction.getCurrentDateTime());
						tblMchtSumrzInfList.add(tblMchtSumrzInf);
					} catch (Exception e) {
						log.error("划款后台处理失败："+e);
						return returnService("划款后台处理失败！请联系管理员！");
					}
				}else{
					tblMchtSumrzInf = t80224BO.get(new Integer(seqNo));
					tblMchtSumrzInf.setSaStatus("0");
					tblMchtSumrzInf.setCauseStat(processResult);
					SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMdd");
					Date date1=new Date();
					tblMchtSumrzInf.setSumrzDate(dateFormater.format(date1));
					tblMchtSumrzInf.setRecUpdOpr(getOperator().getOprId());
					tblMchtSumrzInf.setRecUpdTs(CommonFunction.getCurrentDateTime());
					tblMchtSumrzInfList.add(tblMchtSumrzInf);
				}
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return returnService(rspCode,e);
			}
		}
		try {
			rspCode=t80224BO.update(tblMchtSumrzInfList);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode,e);
		}
		log("商户二次划款成功，操作员编号："+getOperator().getOprId());
		return returnService(rspCode);
	}
	/**
	 * 手动回填失败
	 * @return
	 */
	public String failureMenu() {
		String lgnnam = SysParamUtil.getParam(SysParamConstants.LGNNAM);//银企直连登陆用户名
		String buscod01 = SysParamUtil.getParam(SysParamConstants.BUSCOD01);//业务类别
		jsonBean.parseJSONArrayData(getInfList());
		int len = jsonBean.getArray().size();
		TblMchtSumrzInf tblMchtSumrzInf = null;
		List<TblMchtSumrzInf> tblMchtSumrzInfList = new ArrayList<TblMchtSumrzInf>(len);
		for (int i = 0; i < len; i++) {
			String seqNo = jsonBean.getJSONDataAt(i).getString("seqNo");//主键
			String sumrzDate = jsonBean.getJSONDataAt(i).getString("sumrzDate");//主键
			tblMchtSumrzInf = t80224BO.get(new Integer(seqNo));
			tblMchtSumrzInf.setSaStatus("0");
			tblMchtSumrzInf.setCauseStat("商户手动回填失败");
			tblMchtSumrzInf.setRecUpdOpr(getOperator().getOprId());
			tblMchtSumrzInf.setRecUpdTs(CommonFunction.getCurrentDateTime());
			tblMchtSumrzInfList.add(tblMchtSumrzInf);
		}	
		try {
			rspCode=t80224BO.update(tblMchtSumrzInfList);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode,e);
		}
		log("商户手动回填失败，操作员编号："+getOperator().getOprId());
		rspCode = "00";
		return returnService(rspCode);
	}
	/**
	 * 手动回填成功
	 * @return
	 */
	public String succeedMenu() {
		String lgnnam = SysParamUtil.getParam(SysParamConstants.LGNNAM);//银企直连登陆用户名
		String buscod01 = SysParamUtil.getParam(SysParamConstants.BUSCOD01);//业务类别
		jsonBean.parseJSONArrayData(getInfList());
		int len = jsonBean.getArray().size();
		TblMchtSumrzInf tblMchtSumrzInf = null;
		List<TblMchtSumrzInf> tblMchtSumrzInfList = new ArrayList<TblMchtSumrzInf>(len);
		for (int i = 0; i < len; i++) {
			String seqNo = jsonBean.getJSONDataAt(i).getString("seqNo");//主键
			String sumrzDate = jsonBean.getJSONDataAt(i).getString("sumrzDate");//主键
			tblMchtSumrzInf = t80224BO.get(new Integer(seqNo));
			tblMchtSumrzInf.setSaStatus("1");
			tblMchtSumrzInf.setCauseStat("商户手动回填成功");
			tblMchtSumrzInf.setRecUpdOpr(getOperator().getOprId());
			tblMchtSumrzInf.setRecUpdTs(CommonFunction.getCurrentDateTime());
			tblMchtSumrzInfList.add(tblMchtSumrzInf);
		}	
		try {
			rspCode=t80224BO.update(tblMchtSumrzInfList);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode,e);
		}
		log("商户手动回填失败，操作员编号："+getOperator().getOprId());
		rspCode = "00";
		return returnService(rspCode);
	}
	/**
	 * 商户回填
	 * @return
	 */
	public String backfill(){
		String lgnnam = SysParamUtil.getParam(SysParamConstants.LGNNAM);//银企直连登陆用户名
		String buscod01 = SysParamUtil.getParam(SysParamConstants.BUSCOD01);//业务类别
		jsonBean.parseJSONArrayData(getInfList());
		int len = jsonBean.getArray().size();
		TblMchtSumrzInf tblMchtSumrzInf = null;
		List<TblMchtSumrzInf> tblMchtSumrzInfList = new ArrayList<TblMchtSumrzInf>(len);
		for (int i = 0; i < len; i++) {
			String seqNo = jsonBean.getJSONDataAt(i).getString("seqNo");//主键
			String sumrzDate = jsonBean.getJSONDataAt(i).getString("sumrzDate");//主键
			XmlPacket xmlPkt = new XmlPacket("GetPaymentInfo", lgnnam);//函数名     登陆用户名
			Map mpPodInfo = new Properties();
			mpPodInfo.put("BUSCOD", buscod01);//业务类别
			mpPodInfo.put("BGNDAT", sumrzDate);//起始时间
			mpPodInfo.put("ENDDAT", sumrzDate);//结束时间
			mpPodInfo.put("DATFLG", "B");//日期类型  A：经办日期；B：期望日期
			mpPodInfo.put("YURREF", seqNo);//业务参考号
//			mpPodInfo.put("RTNFLG", "B");//业务处理结果
			xmlPkt.putProperty("SDKPAYQYX", mpPodInfo);
			String date = xmlPkt.toXmlString();
			log.info("生成查询报文:"+date);
			// 连接前置机，发送请求报文，获得返回报文
			String result = T80224Action.sendRequest(date);
			result = result.replace("GBK", "UTF-8");
			// 处理返回的结果
			String processResult = T80224Action.processResultTS(result);
			if (processResult.toUpperCase().equals("FIN")) {
				try {
					tblMchtSumrzInf = t80224BO.get(new Integer(seqNo));
					tblMchtSumrzInf.setSaStatus("1");
					tblMchtSumrzInf.setCauseStat("完成");
//					SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMdd");
//					Date date1=new Date();
//					tblMchtSumrzInf.setSumrzDate(dateFormater.format(date1));
					tblMchtSumrzInf.setRecUpdOpr(getOperator().getOprId());
					tblMchtSumrzInf.setRecUpdTs(CommonFunction.getCurrentDateTime());
					tblMchtSumrzInfList.add(tblMchtSumrzInf);
				} catch (Exception e) {
					log.error("查询后台处理失败："+e);
					return returnService("回填处理失败！请联系管理员！");
				}
			}else if(processResult.toUpperCase().equals("AUT")){
				tblMchtSumrzInf = t80224BO.get(new Integer(seqNo));
				tblMchtSumrzInf.setSaStatus("2");
				tblMchtSumrzInf.setCauseStat("等待审批");
//				SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMdd");
//				Date date1=new Date();
//				tblMchtSumrzInf.setSumrzDate(dateFormater.format(date1));
				tblMchtSumrzInf.setRecUpdOpr(getOperator().getOprId());
				tblMchtSumrzInf.setRecUpdTs(CommonFunction.getCurrentDateTime());
				tblMchtSumrzInfList.add(tblMchtSumrzInf);
//				return returnService("支付失败" + processResult);
				
			}else if(processResult.toUpperCase().equals("NTE")){
				tblMchtSumrzInf = t80224BO.get(new Integer(seqNo));
				tblMchtSumrzInf.setSaStatus("2");
				tblMchtSumrzInf.setCauseStat("终审完毕");
//				SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMdd");
//				Date date1=new Date();
//				tblMchtSumrzInf.setSumrzDate(dateFormater.format(date1));
				tblMchtSumrzInf.setRecUpdOpr(getOperator().getOprId());
				tblMchtSumrzInf.setRecUpdTs(CommonFunction.getCurrentDateTime());
				tblMchtSumrzInfList.add(tblMchtSumrzInf);
//				return returnService("支付失败" + processResult);
				
			}else if(processResult.toUpperCase().equals("WCF")){
				tblMchtSumrzInf = t80224BO.get(new Integer(seqNo));
				tblMchtSumrzInf.setSaStatus("2");
				tblMchtSumrzInf.setCauseStat("订单待确认");
//				SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMdd");
//				Date date1=new Date();
//				tblMchtSumrzInf.setSumrzDate(dateFormater.format(date1));
				tblMchtSumrzInf.setRecUpdOpr(getOperator().getOprId());
				tblMchtSumrzInf.setRecUpdTs(CommonFunction.getCurrentDateTime());
				tblMchtSumrzInfList.add(tblMchtSumrzInf);
//				return returnService("支付失败" + processResult);
				
			}else if(processResult.toUpperCase().equals("BNK")){
				tblMchtSumrzInf = t80224BO.get(new Integer(seqNo));
				tblMchtSumrzInf.setSaStatus("2");
				tblMchtSumrzInf.setCauseStat("银行处理中");
//				SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMdd");
//				Date date1=new Date();
//				tblMchtSumrzInf.setSumrzDate(dateFormater.format(date1));
				tblMchtSumrzInf.setRecUpdOpr(getOperator().getOprId());
				tblMchtSumrzInf.setRecUpdTs(CommonFunction.getCurrentDateTime());
				tblMchtSumrzInfList.add(tblMchtSumrzInf);
//				return returnService("支付失败" + processResult);
				
			}else if(processResult.toUpperCase().equals("ACK")){
				tblMchtSumrzInf = t80224BO.get(new Integer(seqNo));
				tblMchtSumrzInf.setSaStatus("2");
				tblMchtSumrzInf.setCauseStat("等待确认");
//				SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMdd");
//				Date date1=new Date();
//				tblMchtSumrzInf.setSumrzDate(dateFormater.format(date1));
				tblMchtSumrzInf.setRecUpdOpr(getOperator().getOprId());
				tblMchtSumrzInf.setRecUpdTs(CommonFunction.getCurrentDateTime());
				tblMchtSumrzInfList.add(tblMchtSumrzInf);
//				return returnService("支付失败" + processResult);
				
			}else if(processResult.toUpperCase().equals("APD")){
				tblMchtSumrzInf = t80224BO.get(new Integer(seqNo));
				tblMchtSumrzInf.setSaStatus("2");
				tblMchtSumrzInf.setCauseStat("待银行确认");
//				SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMdd");
//				Date date1=new Date();
//				tblMchtSumrzInf.setSumrzDate(dateFormater.format(date1));
				tblMchtSumrzInf.setRecUpdOpr(getOperator().getOprId());
				tblMchtSumrzInf.setRecUpdTs(CommonFunction.getCurrentDateTime());
				tblMchtSumrzInfList.add(tblMchtSumrzInf);
//				return returnService("支付失败" + processResult);
				
			}else if(processResult.toUpperCase().equals("OPR")){
				tblMchtSumrzInf = t80224BO.get(new Integer(seqNo));
				tblMchtSumrzInf.setSaStatus("2");
				tblMchtSumrzInf.setCauseStat("数据接收中");
//				SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMdd");
//				Date date1=new Date();
//				tblMchtSumrzInf.setSumrzDate(dateFormater.format(date1));
				tblMchtSumrzInf.setRecUpdOpr(getOperator().getOprId());
				tblMchtSumrzInf.setRecUpdTs(CommonFunction.getCurrentDateTime());
				tblMchtSumrzInfList.add(tblMchtSumrzInf);
//				return returnService("支付失败" + processResult);
				
			}else if(processResult.equals("F")){
				tblMchtSumrzInf = t80224BO.get(new Integer(seqNo));
				tblMchtSumrzInf.setSaStatus("0");
				tblMchtSumrzInf.setCauseStat("银行支付失败");
//				SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMdd");
//				Date date1=new Date();
//				tblMchtSumrzInf.setSumrzDate(dateFormater.format(date1));
				tblMchtSumrzInf.setRecUpdOpr(getOperator().getOprId());
				tblMchtSumrzInf.setRecUpdTs(CommonFunction.getCurrentDateTime());
				tblMchtSumrzInfList.add(tblMchtSumrzInf);
//				return returnService("支付失败" + processResult);
				
			}else if(processResult.equals("B")){
				tblMchtSumrzInf = t80224BO.get(new Integer(seqNo));
				tblMchtSumrzInf.setSaStatus("0");
				tblMchtSumrzInf.setCauseStat(processResult);
//				SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMdd");
//				Date date1=new Date();
//				tblMchtSumrzInf.setSumrzDate(dateFormater.format(date1));
				tblMchtSumrzInf.setRecUpdOpr(getOperator().getOprId());
				tblMchtSumrzInf.setRecUpdTs(CommonFunction.getCurrentDateTime());
				tblMchtSumrzInfList.add(tblMchtSumrzInf);
//				return returnService("支付失败" + processResult);
				
			}else {
				tblMchtSumrzInf = t80224BO.get(new Integer(seqNo));
				tblMchtSumrzInf.setSaStatus("0");
				tblMchtSumrzInf.setCauseStat("银行支付被退票");
//				SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMdd");
//				Date date1=new Date();
//				tblMchtSumrzInf.setSumrzDate(dateFormater.format(date1));
				tblMchtSumrzInf.setRecUpdOpr(getOperator().getOprId());
				tblMchtSumrzInf.setRecUpdTs(CommonFunction.getCurrentDateTime());
				tblMchtSumrzInfList.add(tblMchtSumrzInf);
//				return returnService("支付失败" + processResult);
				
			}
		}
		try {
			rspCode=t80224BO.update(tblMchtSumrzInfList);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode,e);
		}
		log("商户回填成功，操作员编号："+getOperator().getOprId());
		return returnService(rspCode);
	}
	private static String processResultTS(String result) {
		String str="";
		if (result != null && result.length() > 0) {
			XmlPacket pktRsp = XmlPacket.valueOf(result);
			if (pktRsp != null) {
				String sRetCod = pktRsp.getRETCOD();
				if (sRetCod.equals("0")) {
					Map propPayResult = pktRsp.getProperty("NTQPAYQYZ", 0);
					String sREQSTS = (String) propPayResult.get("REQSTS");
					String sRTNFLG = (String) propPayResult.get("RTNFLG");
					if (sREQSTS.equals("FIN") && sRTNFLG.equals("F")) {
						String object = (String) propPayResult.get("ERRTXT");
						str = "支付失败："+ object;
						log.error("支付失败："+ object);
					} else {
						if(sREQSTS.equals("S")){
							return "FIN";
						}
						str = sREQSTS;
						log.error("支付已被银行受理（支付状态：" + sREQSTS + "）");
					}
				} else if (sRetCod.equals("-9")) {
					str = "支付未知异常，请查询支付结果确认支付状态，错误信息：" + pktRsp.getERRMSG();
					log.error("支付未知异常，请查询支付结果确认支付状态，错误信息：" + pktRsp.getERRMSG());
				} else {
					str = "支付失败：" + pktRsp.getERRMSG() +" REQSTS:"+sRetCod;
					log.error("支付失败：" + pktRsp.getERRMSG());
				}
			} else {
				str = "响应报文解析失败" ;
				log.error("响应报文解析失败");
			}
		}
		return str;
	}
	private String getRequestQuey(){//查询支付结果
		XmlPacket xmlPkt = new XmlPacket("GetPaymentInfo", "银企直连专用集团1");//函数名     登陆用户名
		Map mpPodInfo = new Properties();
		mpPodInfo.put("BUSCOD", "N01010");//业务类别
		mpPodInfo.put("BGNDAT", "20160518");//起始时间
		mpPodInfo.put("ENDDAT", "20160518");//结束时间
		mpPodInfo.put("DATFLG", "B");//日期类型  A：经办日期；B：期望日期
		mpPodInfo.put("YURREF", "N0725148");//业务参考号
//		mpPodInfo.put("RTNFLG", "B");//业务处理结果
		xmlPkt.putProperty("SDKPAYQYX", mpPodInfo);
		return xmlPkt.toXmlString();
	}
	/**
	 * 连接前置机，发送请求报文，获得返回报文
	 * 
	 * @param data
	 * @return
	 * @throws MalformedURLException
	 */
	public static String sendRequest(String data) {
		String result = "";
		try {
			URL url;
			String yqhtpp = SysParamUtil.getParam(SysParamConstants.YQHTPP);
			url = new URL(yqhtpp);
			HttpURLConnection conn;
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Charset", "GBK");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			OutputStream os;
			os = conn.getOutputStream();
			System.out.println("\n");
			System.out.println("----------------------------------------------------------------------------------------------");
			System.out.println("----------------------------------------------------------------------------------------------");
			System.out.println("\n");
			os.write(data.toString().getBytes("GBK"));
			os.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"gbk"));
			String line;
			while ((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println("\n");
			System.out.println("======================================================================================================");
			log.info("返回数据result："+"\n"+result);
			System.out.println("======================================================================================================");
			System.out.println("\n");
			br.close();
			
		} catch (MalformedURLException e) {
			log.error("MalformedURLException:"+e);
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			log.error("UnsupportedEncodingException:"+e);
			e.printStackTrace();
		} catch (ProtocolException e) {
			log.error("ProtocolException:"+e);
			e.printStackTrace();
		} catch (IOException e) {
			log.error("IOException:"+e);
			e.printStackTrace();
		}
		try {
			return new String(result.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			log.error("UnsupportedEncodingException:"+e);
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 处理返回的结果
	 * 
	 * @param result
	 */
	public static String processResult(String result) {
		String str="";
		if (result != null && result.length() > 0) {
			XmlPacket pktRsp = XmlPacket.valueOf(result);
			if (pktRsp != null) {
				String sRetCod = pktRsp.getRETCOD();
				if (sRetCod.equals("0")) {
					Map propPayResult = pktRsp.getProperty("NTQPAYRQZ", 0);
					String sREQSTS = (String) propPayResult.get("REQSTS");
					String sRTNFLG = (String) propPayResult.get("RTNFLG");
					if (sREQSTS.equals("FIN") && sRTNFLG.equals("F")) {
						String object = (String) propPayResult.get("ERRTXT");
						str = "支付失败："+ object;
						log.error("支付失败："+ object);
					} else {
						if(sREQSTS.equals("S")){
							return "FIN";
						}
						str = sREQSTS;
						log.error("支付已被银行受理（支付状态：" + sREQSTS + "）");
					}
				} else if (sRetCod.equals("-9")) {
					str = "支付未知异常，请查询支付结果确认支付状态，错误信息：" + pktRsp.getERRMSG();
					log.error("支付未知异常，请查询支付结果确认支付状态，错误信息：" + pktRsp.getERRMSG());
				} else {
					str = "支付失败：" + pktRsp.getERRMSG() +" REQSTS:"+sRetCod;
					log.error("支付失败：" + pktRsp.getERRMSG());
				}
			} else {
				str = "响应报文解析失败" ;
				log.error("响应报文解析失败");
			}
		}
		return str;
	}
	
	public String falseAdd(){		
				TblMchtSumrzInf tblMchtSumrzInf = t80224BO.get(new Integer(seqNo));
				if(tblMchtSumrzInf==null){
					return returnService("没有找到对应的信息，请核对！");
				}
				tblMchtSumrzInf.setSaStatus("0");
				tblMchtSumrzInf.setSumrzDate(sumrzDate);
				tblMchtSumrzInf.setRecUpdOpr(getOperator().getOprId());
				tblMchtSumrzInf.setRecUpdTs(CommonFunction.getCurrentDateTime());
				tblMchtSumrzInf.setSumrzNote(sumrzNote);						
		try {
			rspCode=t80224BO.update(tblMchtSumrzInf);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return returnService(rspCode,e);
		}

		log("商户划款失败回填成功，操作员编号："+getOperator().getOprId());
		return returnService(rspCode);
	}
	
	@Override
	public String getMsg() {
		// TODO Auto-generated method stub
		return msg;
	}

	@Override
	public boolean isSuccess() {
		// TODO Auto-generated method stub
		return success;
	}
	
	private String infList;
	private String sumrzDate;
	private String seqNo;
	private String sumrzNote;

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public String getSumrzNote() {
		return sumrzNote;
	}

	public void setSumrzNote(String sumrzNote) {
		this.sumrzNote = sumrzNote;
	}

	public String getInfList() {
		return infList;
	}

	public void setInfList(String infList) {
		this.infList = infList;
	}

	public String getSumrzDate() {
		return sumrzDate;
	}

	public void setSumrzDate(String sumrzDate) {
		this.sumrzDate = sumrzDate;
	}
	
}
