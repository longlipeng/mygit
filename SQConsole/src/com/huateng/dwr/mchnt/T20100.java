/* @(#)
 *
 * Project:PFConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ --------- ---------------------------------------------------
 *   Gavin        2011-7-5       first release
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
package com.huateng.dwr.mchnt;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import com.huateng.bo.mchnt.T2070302BO;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.common.XmlObject.DuXMLDoc;
import com.huateng.common.XmlObject.XmlObject;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.po.mchnt.TblHisDiscAlgo2Tmp;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.GenerateNextId;
import com.huateng.system.util.SocketConnect;
import com.huateng.system.util.SysParamUtil;

/**
 * Title: 
 * 
 * File: T20100.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-7-5
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class T20100 {
	private static Logger log = Logger.getLogger(T20100.class);
	private static ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");
	
	public String deleteImgFile(String fileName){
		
		try {
			String basePath = SysParamUtil
					.getParam(SysParamConstants.FILE_UPLOAD_DISK);
			basePath = basePath.replace("\\", "/");
			File file = new File(basePath + fileName);
			if (file.exists()) {
				file.delete();
			}
			return "S";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "E";
	}
	
	public String deleteImgFile1(String fileName, String beneficiaryId, String beneficiaryExpiration){
		
		try {
			String basePath = SysParamUtil
					.getParam(SysParamConstants.FILE_UPLOAD_DISK);
			
			//追加文件夹
			basePath += beneficiaryId + "_" + beneficiaryExpiration + "/";
			
			basePath = basePath.replace("\\", "/");
			File file = new File(basePath + fileName);
			if (file.exists()) {
				file.delete();
			}
			return "S";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "E";
	}
	
	
	/**
	 * 验证商户结算账号 环讯没有验该证
	 * @param txnCode
	 * @param account
	 * @param type
	 * @return
	 * 2011-7-19上午11:29:27
	 * @throws IOException 
	 */
	public HashMap<String, String> verifyAccount(String flag,String account,String type) throws IOException {
		
		HashMap<String, String> map = new LinkedHashMap<String, String>();
		String txnCode = "";
		if ("A".equals(flag)) {
			txnCode = "1521062"; //本行对公账户
		} else {
			txnCode = "1511062"; //本行对私账户或单位卡
		}
		
		
		Random r = new Random();
//		交易码
		StringBuffer req = new StringBuffer(txnCode);
//		时间
		req.append(CommonFunction.getCurrentDateTime());
//		流水号
		int c = r.nextInt(9999);
		req.append(CommonFunction.fillString(String.valueOf(c), '0', 4, false));
		
//		账号类型
		if ("A".equals(flag)) {
			type = "01"; //本行对公账户'
		} else if ("P".equals(flag)) {
			type = "02"; //本行对私账户及单位卡
		}
		req.append(type);
		
//		账号长度
		if(!StringUtil.isEmpty(account))
			req.append(account.length());
//		账号
		req.append(CommonFunction.fillString(String.valueOf(account), ' ', 80, true));
		String len = Integer.toHexString(req.toString().length());
		len = new String(CommonFunction.hexStringToByte(len));
		len = new String(CommonFunction.hexStringToByte("00"))+len;
		SocketConnect socket = null;
		try {
			new XmlObject("zifuchuanxml");
			log.info("报文:["+len+req.toString()+"]");
			socket = new SocketConnect(len+req.toString());
			
			if (null != socket) {
				socket.run(Charset.defaultCharset().toString());
				String resp = socket.getRsp();
				
				if(resp.substring(4,6).equals("00")){
					map.put("result", "S");
					map.put("msg", "验证完成");
				} else {
					map.put("result", "F");
					map.put("msg", "无该账户信息");
				}
				if(txnCode.equals("1511062"))
				{
					map.put("accountNo", resp.substring(31, 70).trim());
					map.put("accountNm", resp.substring(71,83).trim());
					String certType = resp.substring(91,92).trim();
					if(certType.equals("2"))
					{
						map.put("licenceNo", resp.substring(92,107).trim());
						
					}
				}
				else{
					map.put("accountNm", resp.substring(71,102).trim());
				}
				
				
				
				return map;
			}
		} catch (UnknownHostException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		} catch (Exception e) {
			log.error(e);
		} finally {
			try {
				if (null != socket) {
					socket.close();
				}
			} catch (Exception e) {}
		}
		
		map.put("result", "E");
		map.put("msg", "请求账号验证服务异常");
		
		return map;
	}
	/**
	 * 验证商户号
	 * @param txnCode
	 * @param mchtNo
	 * @param cusKey
	 * @return
	 * 2011-7-19上午11:29:27
	 * @throws IOException 
	 */
	public HashMap<String, String> verifyMchtNo(String flag,String mchtNo,String cusKey) throws IOException {
		
		HashMap<String, String> map = new LinkedHashMap<String, String>();
		String reqXmlStr ="";//请求xml字符串
		@SuppressWarnings("unused")
		String txnCode = "";//交易码
		if ("A".equals(flag)) {
			txnCode = "1521062"; //本行对公账户
		} else {
			txnCode = "1511062"; //本行对私账户或单位卡
		}
				
		Random r = new Random();
//		时间
		String DateTime = CommonFunction.getCurrentDateTimeForShow();
		
		System.out.print(DateTime.substring(0,10)+"...."+DateTime.substring(11,19));
		
//		流水号
		int c = r.nextInt(9999);
		String cstr = CommonFunction.fillString(String.valueOf(c), '0', 4, false);
//		商户号
		String mchno = mchtNo.trim();
//      验证码
		String cuskey = cusKey.trim();
//		机器名称
        String   terminal = CommonFunction.getAddress();

		String body =
		   "<body>"+
		        "<cusid>"+mchno+"</cusid>"+
		        "<cuskey>"+cuskey+"</cuskey>"+
		        "<bankcd></bankcd>"+
		        "<banknm></banknm>"+
		        "<inacctno></inacctno>"+
		        "<inacctnm></inacctnm>"+
		        "<outacctno></outacctno>"+
		        "<outacctnm></outacctnm>"+
		        "<cusnm></cusnm>"+
           "</body>";
//      MD5
		String  miyao = "01NulpxpmmJVTn7C2sAftIwsGo2goKBjYiLSoBmZ1DTAM41v58fE1eHHe4EXMrb1AAXK2FxRgADFOL5lNgAGEXHT1dNJXonkC1am6GTQko8Q1bStZ2kFGvNCSetySDTR";
		
		String md5 = CommonFunction.Md5(body+miyao);
		
		log.info("加密字符串+++++++++:["+body+miyao+"]");
		reqXmlStr = 
	      "<tcp>"+
	        "<head>"+
	           "<trancode>POS001</trancode>"+
	           "<terminal>"+terminal+"</terminal>"+
	           "<serseq>"+cstr+"</serseq>"+
	           "<senddate>"+DateTime.substring(0,10)+"</senddate>"+
	           "<sendtime>"+DateTime.substring(11,19)+"</sendtime>"+
	           "<md5>"+md5+"</md5>"+
	           "<msgcode></msgcode>"+
	           "<msg></msg>"+
	        "</head>"+body+
	     "</tcp>#END";
		SocketConnect socket = null;
		
		try {
			
			DuXMLDoc doc = new DuXMLDoc();
			log.info("请求报文:["+reqXmlStr+"]");
//			map = doc.xmlElements(reqXmlStr);
	
			socket = new SocketConnect(reqXmlStr.toString());
			
			if (null != socket) {
				socket.run();
				log.info("OS Encode："+Charset.defaultCharset().toString());
				String resp = socket.getRsp();
				log.info("原文返回报文:"+resp+"");
				resp = CommonFunction.changeCharsetOld(resp,Charset.defaultCharset().toString(),"UTF-8");
				String aa[] = resp.split("<msg>");
				String bb[] = aa[1].split("</msg>");
				String cc[] = bb[0].split("120039");
				
				
				log.info("商户号 :"+cc[0]+"");
				//resp = CommonFunction.changeCharsetOld(resp,"GBK","UTF-8");
				log.info("转码后返回报文:"+resp+"");
				/*String str="";
				for (int i=0;i<cc[0].length();i++) 
				{ 
				int ch = (int)cc[0].charAt(i); 
				String s4 = Integer.toHexString(ch); 
				str = str + s4; 
				} 
				log.info("返回16进制字符串:"+str+"");
				
			
				byte[] baKeyword = new byte[str.length()/2]; 
				for(int i = 0; i < baKeyword.length; i++) 
				{ 
				
				baKeyword[i] = (byte)(0xff & Integer.parseInt(str.substring(i*2, i*2+2),16)); 
				}
				
				str = new String(baKeyword, "utf-8");//UTF-16le:Not 
			
				log.info("返回字符串utf-8:"+str+"");*/
				
				map = doc.xmlElements(resp);
				return map;
				
			}
		} catch (UnknownHostException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		} catch (Exception e) {
			log.error(e);
		} finally {
			try {
				if (null != socket) {
					socket.close();
				}
			} catch (Exception e) {}
		}
		map.put("result", "E");
		map.put("msg", "请求账号验证服务异常");
		
		return map;
	}
	//tmpNo,discCode,TERMID,CITY_CODE,TO_BRCH_NO,FK_BRCH_NO,CARD_TYPE,CHANNEL_NO,BUSINESS_TYPE,TXN_NUM
	public HashMap<String, String> addDiscAlgo2(String tmpNo,String feeType,String termId,
			String cityCode,String toBrchNo,String fkBrchNo,String cardType,String channelNo,String busType,String txnNum){
		HashMap<String, String> map = new LinkedHashMap<String, String>();
		if(!StringUtil.isEmpty(tmpNo)){
			String sql = "select count(*) from TBL_HIS_DISC_ALGO2_TMP where TMP_NO='"+tmpNo+"' and TERM_ID='"+termId+"' " +
					" and CITY_CODE='"+cityCode+"' and FK_BRCH_NO='"+fkBrchNo.trim().replace("'", "''")+"' and TO_BRCH_NO='"+toBrchNo+"' " +
					" and CARD_TYPE='"+cardType+"' and CHANNEL_NO='"+channelNo+"' and BUSINESS_TYPE='"+busType+"' and TXN_NUM='"+txnNum+"'";
			String count=commQueryDAO.findCountBySQLQuery(sql);
			if(!"0".equals(count)){
				map.put("result", "E");
				map.put("msg", "已存在相同的计费信息");
				return map;
			}
		}
		TblHisDiscAlgo2Tmp tblHisDiscAlgo2Tmp=new TblHisDiscAlgo2Tmp(); 
		T2070302BO t2070302BO=(T2070302BO) ContextUtil.getBean("T2070302BO");
		String DISC_ID=GenerateNextId.getAlgo2Id();
		if(StringUtil.isEmpty(tmpNo))tmpNo=DISC_ID;
		tblHisDiscAlgo2Tmp.setTmpNo(tmpNo);
		tblHisDiscAlgo2Tmp.setDISC_ID(DISC_ID);
		tblHisDiscAlgo2Tmp.setTERM_ID(termId);
		tblHisDiscAlgo2Tmp.setCITY_CODE(cityCode);
		tblHisDiscAlgo2Tmp.setTO_BRCH_NO(toBrchNo);
		tblHisDiscAlgo2Tmp.setFK_BRCH_NO(fkBrchNo);
		tblHisDiscAlgo2Tmp.setCARD_TYPE(cardType);
		tblHisDiscAlgo2Tmp.setCHANNEL_NO(channelNo);
		tblHisDiscAlgo2Tmp.setBUSINESS_TYPE(busType);
		tblHisDiscAlgo2Tmp.setTXN_NUM(txnNum);
		tblHisDiscAlgo2Tmp.setFLAG("0");
		tblHisDiscAlgo2Tmp.setFeeType(feeType);
		tblHisDiscAlgo2Tmp.setFeeTypeOld(feeType);
		//tblHisDiscAlgo2Tmp.setREC_UPD_USR_ID(operator.getOprId());
		tblHisDiscAlgo2Tmp.setREC_UPD_TS(CommonFunction.getCurrentDateTime());
		tblHisDiscAlgo2Tmp.setREC_CRT_TS(CommonFunction.getCurrentDateTime());
		tblHisDiscAlgo2Tmp.setSA_SATUTE(CommonFunction.ADD_TO_CHECK);
		try{
			t2070302BO.addAlgo2(tblHisDiscAlgo2Tmp);
			map.put("result","N");
			map.put("tmpNo",tmpNo);
		}catch(Exception e){
			e.printStackTrace();
			map.put("result","E");
			map.put("msg", "添加商户手续费信息失败"); 
		}
		
		//map.put("msg", "添加商户");
	//	log("添加商户手续费规则成功。操作员编号："+operator.getOprId()) ;
		return map;
	}
	
	//回退处理
	public HashMap<String, String> rebackDiscAlgo2(String tmpNo,String mchntId){
		HashMap<String, String> map = new LinkedHashMap<String, String>();
		//将本次新增的全部删除
		String sql1="delete from TBL_HIS_DISC_ALGO2_TMP where TMP_NO='"+tmpNo+"' and MCHT_NO<>'"+mchntId+"'";
		commQueryDAO.excute(sql1);
		//将新增未的,并且待删除的状态恢复为新增状态  flag:0 新增，FLAG：1 已启用
		String sql2="update TBL_HIS_DISC_ALGO2_TMP set SA_SATUTE='0' where TMP_NO='"+tmpNo+"' and MCHT_NO='"+mchntId+"' and FLAG='0' and SA_SATUTE='4' ";
		commQueryDAO.excute(sql2);
		//将以启用的，并且待删除的状态恢复为正常状态
		String sql3="update TBL_HIS_DISC_ALGO2_TMP set SA_SATUTE='2' where TMP_NO='"+tmpNo+"' and MCHT_NO='"+mchntId+"' and FLAG='1' and SA_SATUTE='4' ";
		commQueryDAO.excute(sql3);
		//页面控制：待修改状态的不可删除,故回退处理不需考虑这种情况
		return map;
	}
	
	
	//回退处理商户清算信息（待优化）
	public HashMap<String, String> rebackSettleInf(String mchntId){
	
		HashMap<String, String> map = new LinkedHashMap<String, String>();
		
		String settleDelSql = "delete from TBL_MCHT_SETTLE_INF_TMP where MCHT_NO= '"+mchntId+"'";
	
		commQueryDAO.excute(settleDelSql);
	
		String settleInsSql = "insert into TBL_MCHT_SETTLE_INF_TMP(MCHT_NO,TERM_ID,SETTLE_TYPE,RATE_FLAG,SETTLE_CHN,BAT_TIME,AUTO_STL_FLG," +
				"PART_NUM,FEE_TYPE,FEE_FIXED,FEE_MAX_AMT,FEE_MIN_AMT,FEE_RATE,FEE_DIV_1,FEE_DIV_2,FEE_DIV_3,SETTLE_MODE,FEE_CYCLE,SETTLE_RPT," +
				"SETTLE_BANK_NO,SETTLE_BANK_NM,SETTLE_ACCT_NM,SETTLE_ACCT,FEE_ACCT_NM,FEE_ACCT,GROUP_FLAG,OPEN_STLNO,CHANGE_STLNO,SPE_SETTLE_TP," +
				"SPE_SETTLE_LV,SPE_SETTLE_DS,FEE_BACK_FLG,RESERVED,REC_UPD_TS,REC_CRT_TS,CURR_ACCOUNT,BANK_ACCOUNT_CODE,CORP_BANK_NAME," +
				"COMP_ACCOUNT_BANK_CODE,COMP_ACCOUNT_BANK_NAME,DIR_BANK_CODE,DIR_BANK_NAME,DIR_ACCOUNT_NAME,DIR_ACCOUNT,DIR_FLAG,AUTO_FLAG,HOLIDAY_SET_FLAG,CRE_FLAG,RETURN_FEE_FLAG)" +
				" select MCHT_NO,TERM_ID,SETTLE_TYPE,RATE_FLAG,SETTLE_CHN,BAT_TIME,AUTO_STL_FLG,PART_NUM,FEE_TYPE,FEE_FIXED,FEE_MAX_AMT," +
				"FEE_MIN_AMT,FEE_RATE,FEE_DIV_1,FEE_DIV_2,FEE_DIV_3,SETTLE_MODE,FEE_CYCLE,SETTLE_RPT,SETTLE_BANK_NO,SETTLE_BANK_NM,SETTLE_ACCT_NM," +
				"SETTLE_ACCT,FEE_ACCT_NM,FEE_ACCT,GROUP_FLAG,OPEN_STLNO,CHANGE_STLNO,SPE_SETTLE_TP,SPE_SETTLE_LV,SPE_SETTLE_DS,FEE_BACK_FLG,RESERVED," +
				"REC_UPD_TS,REC_CRT_TS,CURR_ACCOUNT,BANK_ACCOUNT_CODE,CORP_BANK_NAME,COMP_ACCOUNT_BANK_CODE,COMP_ACCOUNT_BANK_NAME,DIR_BANK_CODE," +
				"DIR_BANK_NAME,DIR_ACCOUNT_NAME,DIR_ACCOUNT,DIR_FLAG,AUTO_FLAG,HOLIDAY_SET_FLAG,CRE_FLAG,RETURN_FEE_FLAG from TBL_MCHT_SETTLE_INF where MCHT_NO= '"+mchntId+"'";
	
		commQueryDAO.excute(settleInsSql);
		
		return map;
	
	}
	
	/**
	 * 生成商户号
	 * @param txnCode
	 * @param mchtNo
	 * @param cusKey
	 * @return
	 * 2011-7-19上午11:29:27
	 * @throws IOException 
	 */
	public HashMap<String, String> createMchtNo(String mcc,String area) throws IOException {
		
		
		HashMap<String, String> map = new LinkedHashMap<String, String>();
		
		/*String bSql = "SELECT VALUE from cst_sys_param where OWNER = 'BFT_MCC_SEQ' and key = '" + mcc+"'";
		List<String> bList1 = commQueryDAO.findBySQLQuery(bSql);
		String seq = "";
		if( bList1 == null || bList1.size() == 0 || bList1.get(0) == null ){
			map.put("err", "北京银行商户号段未配置");
			return map;
		}else{
			seq = bList1.get(0);
		}*/
		String seq = "96";
		//随行付商户号生成
		String sqlSX = "select  lpad(MCHTSX_NO_SEQUENCE.NEXTVAL,4,0) from dual";
		String mchtNoForSX="";   //随性付商户号
		List<String> listSX = commQueryDAO.findBySQLQuery(sqlSX);
		
		if(listSX.size() == 0){
			map.put("err", "随行付商户号生成失败");
			return map;
		}else{
			String mchtNo =  "836"+area+mcc+listSX.get(0);
			mchtNoForSX=mchtNo;
		}
		
		
		//北京商户号生成：北京商户号的字段是由北京商户号和随行付商户号拼接在一起的，前15个字段是北京银行商户号后15个字段是随行付商户号
		String sqlBeijing = "select to_char(substr(max(MCHT_NO_HX),14,2)+1) from Tbl_Mcht_Base_Inf_Tmp where mcc = '" +mcc+"' and substr(MCHT_NO_HX,12,2) = '"+ seq+"'" ;
		List<String> bList = commQueryDAO.findBySQLQuery(sqlBeijing);
		String lowNum = null;
		if( bList!= null  && bList.size() != 0){
			lowNum = bList.get(0);
		}
		
		if( lowNum == null){
			lowNum = "01";
			String beijingMcht = "4033101"+mcc+seq+lowNum;
			map.put("bResult", "00");
//			map.put("mchtNoByInPut", beijingMcht);
			map.put("mchtNoByInPut", beijingMcht+mchtNoForSX.trim());
		}else if( Integer.parseInt(lowNum) >= 99){
			map.put("err", "该MCC号段已用完，请进行申请");
			return map;
		}else if( Integer.parseInt(lowNum) < 1){
			map.put("err", "商户号生成错误");
			return map;
		}else if( Integer.parseInt(lowNum) < 10 &&  Integer.parseInt(lowNum)>0){
			String beijingMcht = "4033101"+mcc+seq+"0"+lowNum;
			map.put("bResult", "00");
//			map.put("mchtNoByInPut", beijingMcht);
			map.put("mchtNoByInPut", beijingMcht+mchtNoForSX.trim());
		}else if( Integer.parseInt(lowNum) >= 85){
			String beijingMcht = "4033101"+mcc+seq+lowNum;
//			map.put("msg", "该MCC商户号序列所剩不多，请尽快申请新号段");
			map.put("bResult", "01");
//			map.put("mchtNoByInPut", beijingMcht);
			map.put("mchtNoByInPut", beijingMcht+mchtNoForSX.trim());
		}else{
			String beijingMcht = "4033101"+mcc+seq+lowNum;
			map.put("bResult", "00");
//			map.put("mchtNoByInPut", beijingMcht);
			map.put("mchtNoByInPut", beijingMcht+mchtNoForSX.trim());
		}
		
		
		/*String sql = "select  lpad(MCHT_NO_SEQUENCE.NEXTVAL,4,0) from dual";
		List<String> list = commQueryDAO.findBySQLQuery(sql);
		
		if(list.size() == 0){
			map.put("err", "德益富商户号生成失败");
			return map;
		}else{
			String mchtNo =  "108"+area+mcc+list.get(0);
			map.put("result", "00");
			map.put("mchtNoInPut", mchtNo);
		}*/
		
		String mchtNoLast4="0001";
		String sqlDef="select max(substr(mcht_no,12,4)) from tbl_mcht_base_inf_tmp where substr(mcht_no,4,8)='"+area+mcc+"'";
		List<String> mchtNoList=commQueryDAO.findBySQLQuery(sqlDef);
		if(null!=mchtNoList&&null!=mchtNoList.get(0)&&!"".equals(mchtNoList.get(0))){
			String tmpL4=mchtNoList.get(0);
			if("9999".equals(tmpL4)){
				map.put("err", "该地区该MCC的商户商户号已满！");
				return map;
			}else{
				int s=10001;
				s+=Integer.valueOf(tmpL4);
				mchtNoLast4=String.valueOf(s).substring(1);
			}
			
		}
		String bSql = "SELECT VALUE from cst_sys_param where OWNER = 'MCHTNO_START' and key = 'mchtNo'";
		List<String> bList1 = commQueryDAO.findBySQLQuery(bSql);
		String mchtNoStart = "";
		if( bList1 == null || bList1.size() == 0 || bList1.get(0) == null ){
			map.put("err", "商户号起始位未配置");
			return map;
		}else{
			mchtNoStart = bList1.get(0);
			String mchtNo =  mchtNoStart+area+mcc+mchtNoLast4;
			map.put("result", "00");
			map.put("mchtNoInPut", mchtNo);
		}
		return map;
	}
	
	/**
	 * 根据代理商获取所属业务人员
	 * @param str
	 * @return
	 */
	public String getBusiPerson(String str) {
		String sql = "select BUSI_PERSON from TBL_AGENT_INFO where AGENT_NO = '" + str.trim() + "'";
		List list = commQueryDAO.findBySQLQuery(sql);
		if(list != null){
			String result = list.get(0).toString();
			if ( result == null ) {
				return null;
			} else {
				return result;
			}
		}else 
			return null;
	}
	
}
