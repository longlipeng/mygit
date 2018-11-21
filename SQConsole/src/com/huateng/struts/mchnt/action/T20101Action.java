/* @(#)
 *
 * Project:PFConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   Gavin      2011-6-20       first release
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
package com.huateng.struts.mchnt.action;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.alibaba.fastjson.JSONObject;
import com.huateng.bo.impl.mchnt.TblMchntService;
import com.huateng.bo.mchnt.T2070302BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.common.TblMchntInfoConstants;
import com.huateng.po.mchnt.TblHisDiscAlgo2Tmp;
import com.huateng.po.mchnt.TblMchtBaseBusiRange;
import com.huateng.po.mchnt.TblMchtBaseInfTmp;
import com.huateng.po.mchnt.TblMchtSettleInfPK;
import com.huateng.po.mchnt.TblMchtSettleInfTmp;
import com.huateng.po.mchnt.TblMchtSupp1Tmp;
import com.huateng.struts.system.action.BaseSupport;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.BlackListAndRiskGradeConfig;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.FileFilter;
import com.huateng.system.util.SysParamUtil;

/**
 * Title:商户信息维护
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-6-20
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class T20101Action extends BaseSupport{
	
	private java.lang.String rateFlag;
	private java.lang.String settleChn;
	private java.lang.String batTime;
	private java.lang.String autoStlFlg;
	private java.lang.String feeType;
	private java.lang.String feeFixed;
	private java.lang.String feeMaxAmt;
	private java.lang.String feeMinAmt;
	private java.lang.String feeRate;
	private java.lang.String feeDiv1;
	private java.lang.String feeDiv2;
	private java.lang.String feeDiv3;
	
	private java.lang.String legalNam;//法人账户名称legalNam
	private java.lang.String companyNam;//公司账户名称companyNam
	//add
	private java.lang.String dirOpenBank_1;
	private java.lang.String dirBankProvince_1;
	private java.lang.String dirBankCity_1;
	private java.lang.String compOpenBank_1;
	private java.lang.String compBankProvince_1;
	private java.lang.String compBankCity_1;
	private java.lang.String corpOpenBank_1;
	private java.lang.String corpBankProvince_1;
	private java.lang.String corpBankCity_1;
	
	private java.lang.String organizationType;
	private java.lang.String isSyncretic;
	private java.lang.String uscCode;
	private static String riskGradeURL;
	private static String riskResult;
	
	private  String foreignName; // 外籍名
	
	public static String getRiskGradeURL() {
		return riskGradeURL;
	}

	public static void setRiskGradeURL(String riskGradeURL) {
		T20101Action.riskGradeURL = riskGradeURL;
	}

	public static String getRiskResult() {
		return riskResult;
	}

	public static void setRiskResult(String riskResult) {
		T20101Action.riskResult = riskResult;
	}

	public String getForeignName() {
		return foreignName;
	}

	public void setForeignName(String foreignName) {
		this.foreignName = foreignName;
	}

	public java.lang.String getUscCode() {
		return uscCode;
	}

	public void setUscCode(java.lang.String uscCode) {
		this.uscCode = uscCode;
	}

	public java.lang.String getOrganizationType() {
		return organizationType;
	}

	public void setOrganizationType(java.lang.String organizationType) {
		this.organizationType = organizationType;
	}

	public java.lang.String getIsSyncretic() {
		return isSyncretic;
	}

	public void setIsSyncretic(java.lang.String isSyncretic) {
		this.isSyncretic = isSyncretic;
	}

	public java.lang.String getDirOpenBank_1() {
		return dirOpenBank_1;
	}

	public void setDirOpenBank_1(java.lang.String dirOpenBank_1) {
		this.dirOpenBank_1 = dirOpenBank_1;
	}

	public java.lang.String getDirBankProvince_1() {
		return dirBankProvince_1;
	}

	public void setDirBankProvince_1(java.lang.String dirBankProvince_1) {
		this.dirBankProvince_1 = dirBankProvince_1;
	}

	public java.lang.String getDirBankCity_1() {
		return dirBankCity_1;
	}

	public void setDirBankCity_1(java.lang.String dirBankCity_1) {
		this.dirBankCity_1 = dirBankCity_1;
	}

	public java.lang.String getCompOpenBank_1() {
		return compOpenBank_1;
	}

	public void setCompOpenBank_1(java.lang.String compOpenBank_1) {
		this.compOpenBank_1 = compOpenBank_1;
	}

	public java.lang.String getCompBankProvince_1() {
		return compBankProvince_1;
	}

	public void setCompBankProvince_1(java.lang.String compBankProvince_1) {
		this.compBankProvince_1 = compBankProvince_1;
	}

	public java.lang.String getCompBankCity_1() {
		return compBankCity_1;
	}

	public void setCompBankCity_1(java.lang.String compBankCity_1) {
		this.compBankCity_1 = compBankCity_1;
	}

	public java.lang.String getCorpOpenBank_1() {
		return corpOpenBank_1;
	}

	public void setCorpOpenBank_1(java.lang.String corpOpenBank_1) {
		this.corpOpenBank_1 = corpOpenBank_1;
	}

	public java.lang.String getCorpBankProvince_1() {
		return corpBankProvince_1;
	}

	public void setCorpBankProvince_1(java.lang.String corpBankProvince_1) {
		this.corpBankProvince_1 = corpBankProvince_1;
	}

	public java.lang.String getCorpBankCity_1() {
		return corpBankCity_1;
	}

	public void setCorpBankCity_1(java.lang.String corpBankCity_1) {
		this.corpBankCity_1 = corpBankCity_1;
	}

	public java.lang.String getLegalNam() {
		return legalNam;
	}

	public void setLegalNam(java.lang.String legalNam) {
		this.legalNam = legalNam;
	}

	public java.lang.String getCompanyNam() {
		return companyNam;
	}

	public void setCompanyNam(java.lang.String companyNam) {
		this.companyNam = companyNam;
	}
	/**
	 * 查询风险等级
	 * @return
	 */
	public String queryRishGrade() throws Exception {
		System.out.println("参数商户号："+mchtNo);
		//获取商户信息
		TblMchtBaseInfTmp tmp = service.getBaseInfTmp(mchtNo);
		String CustNo = tmp.getCustNo().trim();
		System.out.println("客户号："+CustNo);
		String CName = tmp.getMchtNm().trim();
		System.out.println("客户名称："+CName);
//		String CertID = tmp.getAcqBkName();
		String str2 = null;
		if(CustNo==null||CustNo.equals("")){		
			str2 = "客户号不存在！";
			log("客户号不存在！");
//			return returnService(str2);
		}else if(CName==null||CName.equals("")){
			str2 = "客户名称不存在!";
			log("客户名称不存在！");
			return returnService(str2);
		}
		
		
		/*riskGradeURL=BlackListAndRiskGradeConfig.getRiskGradeURL();
		  Map<String, String> parameters = new HashMap<String,String>();
			if(!StringUtil.isEmpty(CName)) {
//				System.out.println("风险等级riskGrade："+riskGrade);
				parameters.put("CName",CName);   //中文或者英文名
			}
			if(!StringUtil.isEmpty(CertID)) {
//				System.out.println("法定代表人/负责人证件号码identityNo："+identityNo);
				parameters.put("CertID", CertID);   //证件号码
			}*/
		  riskGradeURL=BlackListAndRiskGradeConfig.getRiskGradeURL();
		  Map<String, String> parameters = new HashMap<String,String>();
		  
			if(!StringUtil.isEmpty(CustNo)) {
				parameters.put("CNum",CustNo);   //客户号
			}
			
			if(!StringUtil.isEmpty(CName)) {
				parameters.put("CName", CName);   //商户名
			}

			String str = "尚未确认评级";

		//数据库新增风险等级字段
		//在tmp表跟正式表添加相应的字段（javaBean，hbm）
		//update进相应的表中
		
//		String risk="update tbl_mcht_base_inf_tmp set RISK_GRADE='"+str+"' where MCHT_NO='"+mchtNo+"'";
//		commQueryDAO.excute(risk);
			try {
				riskResult =sendGet(riskGradeURL,parameters);
				String resultNum = analysisRiskGradeResult(riskResult);
				//	        返回状态	Result	Y   String	
				//			H:高级风险
				//	        L:低级风险
				//	        M:中级风险
				//	        P:极高风险
				//	        0:尚未确认评级
				//          -1:无此客户或客户号姓名不匹配
				//TODO 完善响应
				
				if(resultNum.equals("H")){
					str = "高级风险";
					log("匹配上高级风险");
				}else if(resultNum.equals("L")) {
					str = "低级风险";
					log("匹配上低级风险");
				}else if(resultNum.equals("M")) {
					str = "中级风险";
					log("匹配上中级风险");
				}else if(resultNum.equals("P")) {
					str = "极高风险";
					log("匹配上极高风险");
				}else if(resultNum.equals("0")) {
					str = "未确认评级";
					log("匹配尚未确认评级");
				}else if(resultNum.equals("-1")) {
					str = "匹配无此客户或客户号姓名不匹配";
					log("匹配无此客户或客户号姓名不匹配");
				}
				tmp.setRiskGrade(str);
//				TblMchtBaseInf tblMchtBaseInf = new  TblMchtBaseInf();
//				BeanUtils.copyProperties(tblMchtBaseInf, tmp);
				try {
					service.updateBaseInfTmp(tmp);
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					String risk="update tbl_mcht_base_inf set RISK_GRADE='"+str+"' where MCHT_NO='"+mchtNo+"'";
					commQueryDAO.excute(risk);
				} catch (Exception e) {
					e.printStackTrace();
					tmp.setRiskGrade("");
					service.updateBaseInfTmp(tmp);
					return returnService("更新失败请联系管理员", e);
				}
				return returnService(str);
				
			} catch (Exception e) {
				e.printStackTrace();
				return returnService(rspCode, e);
			}

	}
	//分析风险等级返回结果
    private String analysisRiskGradeResult(String riskGrade) throws Exception {
    	if(riskGrade==null||riskGrade.trim().equals("")) {
    		log("返回为空!");
    		return "请求失败,稍后重试!";
		}
    	try {
    	    JSONObject object=JSONObject.parseObject(riskGrade);
            String result =object.getString("result");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            log(e.getMessage());
            return "解析风险等级返回结果失败！";
        }
    }
   
    /** 
     * 发送GET请求 
     *  
     * @param url 
     *            目的地址 
     * @param parameters 
     *            请求参数，Map类型。 
     * @return 远程响应结果 
     */  
    public String sendGet(String url, Map<String, String> parameters) {
        String result = "";// 返回的结果  
        BufferedReader in = null;// 读取响应输入流  
        StringBuffer sb = new StringBuffer();// 存储参数  
        String params = "";// 编码之后的参数  
        try {  
            // 编码请求参数  
            if (parameters.size() == 1) {  
                for (String name : parameters.keySet()) {  
                    sb.append(name).append("=").append(  
                            java.net .URLEncoder.encode(parameters.get(name),  
                                    "utf-8"));  
                }  
                params = sb.toString();  
            } else {  
                for (String name : parameters.keySet()) { 
                
                        if(parameters.get(name)!=null) {
                    sb.append(name).append("=").append(  
                            java.net .URLEncoder.encode(parameters.get(name),  
                                    "utf-8")).append("&");  
                        }
                }  
                String temp_params = sb.toString();
                
                log("temp_params："+temp_params);  
                if(temp_params!=null&&temp_params.length()>0){
                	//params = temp_params.substring(0, temp_params.length() - 1);
                	 String[] temp_paramsArray =temp_params.split("&");
              	   for (int i = 0; i < temp_paramsArray.length; i++) {
              		 log("temp_params："+temp_paramsArray[i]); 
              		   if(i!=temp_paramsArray.length-1){
              			 params += temp_paramsArray[i]+"&";
              		   }else{
              			 params+=temp_paramsArray[i];
              		   }
              		   
          		}
                	  log("params："+params);  
                }else {
                	params="";
                }
                //params = temp_params.substring(0, temp_params.length() - 1);  
            }  
            String full_url = url.trim() + "?" + params;  
            log("请求地址&参数："+full_url);  
            // 创建URL对象  
            java.net .URL connURL = new java.net .URL(full_url);  
            // 打开URL连接  
            java.net .HttpURLConnection httpConn = (java.net .HttpURLConnection) connURL  
                    .openConnection();  
            // 设置通用属性  
            httpConn.setRequestProperty("Accept", "*/*");  
            httpConn.setRequestProperty("Connection", "Keep-Alive");  
            httpConn.setRequestProperty("User-Agent",  
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");  
            // 建立实际的连接  
            httpConn.connect();  
            // 响应头部获取  
            Map<String, List<String>> headers = httpConn.getHeaderFields();  
            // 遍历所有的响应头字段  
            for (String key : headers.keySet()) {  
                System.out.println(key + "\t：\t" + headers.get(key));  
            }  
            // 定义BufferedReader输入流来读取URL的响应,并设置编码方式  
            in = new BufferedReader(new InputStreamReader(httpConn  
                    .getInputStream(), "UTF-8"));  
            String line;  
            // 读取返回的内容  
            while ((line = in.readLine()) != null) {  
                result += line;  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
            log(e.getMessage());
        } finally {  
            try {  
                if (in != null) {  
                    in.close();  
                }  
            } catch (IOException ex) {  
                ex.printStackTrace();  
                log(ex.getMessage());
            }  
        }
        log("返回&参数："+result);
        return result; 
    }  
	/**
	 * 修改商户信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String update() {
		T2070302BO t2070302BO = (T2070302BO) ContextUtil.getBean("T2070302BO");
		//T20201BO t20201BO=(T20201BO) ContextUtil.getBean("T20201BO");
		try {
			TblMchtBaseInfTmp tmp = service.getBaseInfTmp(mchtNo);
			/*TblMchtSettleInfTmp settleTmp = service.getSettleInfTmp(mchtNo);*/
			//商户补充信息
			TblMchtSupp1Tmp supp1Tmp=service.getMchtSupp1Tmp(mchtNo);
			
			/*String sql1 = "select BUSI_RANGE_ID,MCHT_NO,BUSI_RANGE from TBL_MCHT_BASE_BUSI_RANGE where MCHT_NO = '" + mchtNo + "'";
			List<Object[]> lists = CommonFunction.getCommQueryDAO().findBySQLQuery(sql1);
			TblMchtBaseBusiRange tblMchtBaseBusiRange = null;
			if(null == lists || lists.isEmpty() || lists.size() == 0){
				//商户经营范围
				tblMchtBaseBusiRange = new TblMchtBaseBusiRange();
				//生成随机数
				Random random = new Random();
				String mchtNoRandom = mchtNo + random.nextInt(999999);
				
				tblMchtBaseBusiRange.setBusiRangeId(mchtNoRandom);
				tblMchtBaseBusiRange.setMchtNo(mchtNo);
				tblMchtBaseBusiRange.setBusiRange(tmp.getBusiRangeId());
				//将原有的经营范围值赋值TblMchtBaseBusiRange表的busiRange字段后把TblMchtBaseBusiRange表的busiRangeId字段赋值给TblMchtBaseInfTmp表的busiRangeId字段
				tmp.setBusiRangeId(mchtNoRandom);
				
				service.updateBaseInfTmp(tmp);
				service.addBaseBusiRange(tblMchtBaseBusiRange);
			}else{
				tblMchtBaseBusiRange = new TblMchtBaseBusiRange();
				for (Object[] baseList : lists) {
					tblMchtBaseBusiRange.setBusiRangeId(baseList[0].toString());
					tblMchtBaseBusiRange.setMchtNo(baseList[1].toString());
					tblMchtBaseBusiRange.setBusiRange(busiRange);
					tmp.setBusiRangeId(baseList[0].toString());
				}
				
				service.updateBaseInfTmp(tmp);
				service.upBaseBusiRange(tblMchtBaseBusiRange);
			}*/
			
			if (null == tmp /*|| null == settleTmp*/) {
				return returnService("没有找到指定的商户信息，请重试");
			}
			
			//需要检查
			if (!StringUtil.isNull(checkIds) && checkIds.equals("T")) {
				//首先检验营业执照， 商户账户账号，法人身份证是否已经存在licenceNo faxNo settleAcct identityNo
				//商户账户账号
				String sql = "select B.MCHT_NO,trim(MCHT_NM)," +
					"TRIM(LICENCE_NO),TRIM(FAX_NO),TRIM(IDENTITY_NO),substr(TRIM(SETTLE_ACCT),2) " +
					"from TBL_MCHT_BASE_INF_TMP B,TBL_MCHT_SETTLE_INF_TMP S where " +
					//"(substr(TRIM(SETTLE_ACCT),2) = '" + settleAcct + "' OR " + 
					"(TRIM(SETTLE_ACCT)= '" + settleAcct + "' OR " +
					" TRIM(IDENTITY_NO) = '" + identityNo.trim() + "' OR "; 
					//20120730,税务登记证号码改为可选项
					if(faxNo!=null || !"".equals(faxNo))
						sql += " TRIM(FAX_NO) = '" + faxNo.trim() + "' OR "; 
					sql += " TRIM(LICENCE_NO) = '" + licenceNo.trim() + "') AND B.MCHT_NO = S.MCHT_NO " +
						" AND TRIM(B.MCHT_NO) != '" + mchtNo.trim() + "' AND TRIM(ACQ_INST_ID) = '" + acqInstId.trim() + "'";
				List list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
				if (null != list && !list.isEmpty() && list.size() > 0) {
					Object[] obj = (Object[]) list.get(0);
					String reMsg = "CZ存在商户[<font color='red'>" + obj[0] + "-" + obj[1] + "</font>]等" + String.valueOf(list.size()) + "家商户与该商户的";
					if (licenceNo.trim().equals(obj[2])) {
						reMsg += "<font color='green'>营业执照编号</font>";
					} else if (faxNo.trim().equals(obj[3]) && (faxNo!=null || !"".equals(faxNo))) {//20120730,税务登记证号码改为可选项
						reMsg += "<font color='green'>税务登记证号码</font>";
					} else if (identityNo.trim().equals(obj[4])) {
						reMsg += "<font color='green'>法人证件编号</font>";
					} else if (settleAcct!=null&&settleAcct.trim().equals(obj[5])) {
						reMsg += "<font color='green'>商户账户账号</font>";
					} else {
						reMsg += "<font color='green'>部分关键信息</font>";
					}
					reMsg += "相同,请核实相关商户录入信息.";
					
					return returnService(reMsg);
				}
			}
			
			String sta = tmp.getMchtStatus();
//			String applyDateBefore = tmp.getApplyDate();
			TblMchtBaseInfTmp tblMchtBaseInfTmp = updateTmpMchtBaseInfo(tmp);
			
//			tblMchtBaseInfTmp.setApplyDate(applyDateBefore);//20120815
			tblMchtBaseInfTmp.setReserved(idOtherNo);
			//tblMchtBaseInfTmp.setRislLvl("0");
			//tblMchtBaseInfTmp.setMchtLvl("0");
			tblMchtBaseInfTmp.setSaAction("0");
			tblMchtBaseInfTmp.setUscCode(uscCode);
			tblMchtBaseInfTmp.setIsSyncretic(isSyncretic);
			tblMchtBaseInfTmp.setOrganizationType(organizationType);
			tblMchtBaseInfTmp.setCupMchtFlg("0");
			tblMchtBaseInfTmp.setDebMchtFlg("0");
			tblMchtBaseInfTmp.setCreMchtFlg("0");
			tblMchtBaseInfTmp.setCdcMchtFlg("0");
			
			tblMchtBaseInfTmp.setUscCodeDate(uscCodeDate);
			tblMchtBaseInfTmp.setLicenceNoDate(licenceNoDate);
			tblMchtBaseInfTmp.setShareholder(shareholder);
			tblMchtBaseInfTmp.setIdshareholderTpmd(idshareholderTpmd);
			tblMchtBaseInfTmp.setIdshareholder(idshareholder);
			tblMchtBaseInfTmp.setShareholderDate(shareholderDate);
			tblMchtBaseInfTmp.setIdentityNoDate(identityNoDate);
			tblMchtBaseInfTmp.setContactmd(contactmd);
			tblMchtBaseInfTmp.setLinkTelDate(linkTelDate);
			tblMchtBaseInfTmp.setOperateAddr(operateAddr);
			
			tblMchtBaseInfTmp.setLegalGender(legalGender);
			tblMchtBaseInfTmp.setLegalProfession(legalProfession);
			tblMchtBaseInfTmp.setLegalProfession(legalProfession);
			//tblMchtBaseInfTmp.setManagerTel("1");
			
			if(StringUtil.isNull(openTime) || "请选择".equals(openTime)) {
				tblMchtBaseInfTmp.setOpenTime("00:00");
			}
			
			if(StringUtil.isNull(closeTime) || "请选择".equals(closeTime)) {
				tblMchtBaseInfTmp.setCloseTime("23:59");
			}
			
			if (!TblMchntInfoConstants.MCHNT_ST_NEW_UNCK.equals(sta)) {
				tmp.setMchtStatus(TblMchntInfoConstants.MCHNT_ST_MODI_UNCK);
			} else {
				tmp.setMchtStatus(sta);
			}
			if(TblMchntInfoConstants.MCHT_ST_NEW_UNCK_REF.equals(sta)){
				tblMchtBaseInfTmp.setMchtStatus(TblMchntInfoConstants.MCHNT_ST_NEW_UNCK);
				String del = "delete from TBL_MCHNT_REFUSE where MCHNT_ID='"+mchtNo+"' and refuse_type='新增审核拒绝' ";
				commQueryDAO.excute(del);
			}
//			TblMchtSettleInfTmp tblMchtSettleInfTmp =null /*updateTmpMchtSettleInfo(settleTmp)*/;
			
			/*if(StringUtil.isEmpty(openStlno)) {
				tblMchtSettleInfTmp.setOpenStlno(" ");
			}
			if(StringUtil.isEmpty(settleBankNo)) {
				tblMchtSettleInfTmp.setOpenStlno(" ");
			}
			if(StringUtil.isEmpty(changeStlno)) {
				tblMchtSettleInfTmp.setOpenStlno(" ");
			}
			tblMchtSettleInfTmp.setFeeDiv1("1");
			tblMchtSettleInfTmp.setFeeDiv2("2");
			tblMchtSettleInfTmp.setFeeDiv3("3");*/
			//商户补充信息
			supp1Tmp=buildTmpMchtSupp1Tmp(supp1Tmp);
			
			rspCode = service.updateTmp(tblMchtBaseInfTmp,supp1Tmp);
			//更新费率信息
			jsonBean.parseJSONArrayData(getAlgo2List());
			int len = jsonBean.getArray().size();
			for(int i=0;i<len;i++){
				String discId = jsonBean.getJSONDataAt(i).getString("discId");
				String feeType = jsonBean.getJSONDataAt(i).getString("feeType");
				TblHisDiscAlgo2Tmp algo2=t2070302BO.getAlgo2(discId);
				if(algo2!=null){
					algo2.setFeeType(feeType);
					algo2.setSA_SATUTE("3");
					t2070302BO.updateAlgo2(algo2);
				}
			}
			//更新新增的商户费率的商户号
			String upAlgo2Sql="update tbl_his_disc_algo2_tmp set mcht_no='"+mchtNo+"' where tmp_no='"+tmpNo+"'";
			commQueryDAO.excute(upAlgo2Sql);
			if (Constants.SUCCESS_CODE.equals(rspCode)) {
				return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + "修改商户信息成功，商户编号[" + mchtNo + "]");
			} else {
				return rspCode;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode,e);
		}
	}
	
	/**
	 * 修改商户信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String update4Add() {
		T2070302BO t2070302BO = (T2070302BO) ContextUtil.getBean("T2070302BO");
		//T20201BO t20201BO=(T20201BO) ContextUtil.getBean("T20201BO");
		try {
			TblMchtBaseInfTmp tmp = service.getBaseInfTmp(mchtNo);
			TblMchtSettleInfPK id = new TblMchtSettleInfPK();
			id.setMchtNo(mchtNo);
			id.setTermId(CommonFunction.fillString("*",' ',8,true));
			TblMchtSettleInfTmp settleTmp = service.getSettleInfTmp(id);
			//商户补充信息
			TblMchtSupp1Tmp supp1Tmp=service.getMchtSupp1Tmp(mchtNo);
			if (null == tmp || null == settleTmp) {
				return returnService("没有找到指定的商户信息，请重试");
			}
			
			//需要检查
			if (!StringUtil.isNull(checkIds) && checkIds.equals("T")) {
				//首先检验营业执照， 商户账户账号，法人身份证是否已经存在licenceNo faxNo settleAcct identityNo
				//商户账户账号
				String sql = "select B.MCHT_NO,trim(MCHT_NM)," +
					"TRIM(LICENCE_NO),TRIM(FAX_NO),TRIM(IDENTITY_NO),substr(TRIM(SETTLE_ACCT),2) " +
					"from TBL_MCHT_BASE_INF_TMP B,TBL_MCHT_SETTLE_INF_TMP S where " +
					//"(substr(TRIM(SETTLE_ACCT),2) = '" + settleAcct + "' OR " + 
					"(TRIM(SETTLE_ACCT)= '" + settleAcct + "' OR " +
					" TRIM(IDENTITY_NO) = '" + identityNo.trim() + "' OR "; 
					//20120730,税务登记证号码改为可选项
					if(faxNo!=null || !"".equals(faxNo))
						sql += " TRIM(FAX_NO) = '" + faxNo.trim() + "' OR "; 
					sql += " TRIM(LICENCE_NO) = '" + licenceNo.trim() + "') AND B.MCHT_NO = S.MCHT_NO " +
						" AND TRIM(B.MCHT_NO) != '" + mchtNo.trim() + "' AND TRIM(ACQ_INST_ID) = '" + acqInstId.trim() + "'";
				List list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
				if (null != list && !list.isEmpty() && list.size() > 0) {
					Object[] obj = (Object[]) list.get(0);
					String reMsg = "CZ存在商户[<font color='red'>" + obj[0] + "-" + obj[1] + "</font>]等" + String.valueOf(list.size()) + "家商户与该商户的";
					if (licenceNo.trim().equals(obj[2])) {
						reMsg += "<font color='green'>营业执照编号</font>";
					} else if (faxNo.trim().equals(obj[3]) && (faxNo!=null || !"".equals(faxNo))) {//20120730,税务登记证号码改为可选项
						reMsg += "<font color='green'>税务登记证号码</font>";
					} else if (identityNo.trim().equals(obj[4])) {
						reMsg += "<font color='green'>法人证件编号</font>";
					} else if (settleAcct!=null&&settleAcct.trim().equals(obj[5])) {
						reMsg += "<font color='green'>商户账户账号</font>";
					} else {
						reMsg += "<font color='green'>部分关键信息</font>";
					}
					reMsg += "相同,请核实相关商户录入信息.";
					
					return returnService(reMsg);
				}
			}
			
			String sta = tmp.getMchtStatus();
//			String applyDateBefore = tmp.getApplyDate();
			TblMchtBaseInfTmp tblMchtBaseInfTmp = updateTmpMchtBaseInfo(tmp);
			
//			tblMchtBaseInfTmp.setApplyDate(applyDateBefore);//20120815
			tblMchtBaseInfTmp.setReserved(idOtherNo);
			//tblMchtBaseInfTmp.setRislLvl("0");
			//tblMchtBaseInfTmp.setMchtLvl("0");
			tblMchtBaseInfTmp.setSaAction("0");
			
			tblMchtBaseInfTmp.setCupMchtFlg("0");
			tblMchtBaseInfTmp.setDebMchtFlg("0");
			tblMchtBaseInfTmp.setCreMchtFlg("0");
			tblMchtBaseInfTmp.setCdcMchtFlg("0");
			
			//tblMchtBaseInfTmp.setManagerTel("1");
			
			if(StringUtil.isNull(openTime) || "请选择".equals(openTime)) {
				tblMchtBaseInfTmp.setOpenTime("00:00");
			}
			
			if(StringUtil.isNull(closeTime) || "请选择".equals(closeTime)) {
				tblMchtBaseInfTmp.setCloseTime("23:59");
			}
			
			
			
				tblMchtBaseInfTmp.setMchtStatus(TblMchntInfoConstants.MCHNT_ST_NEW_UNCK);
				String del = "delete from TBL_MCHNT_REFUSE where MCHNT_ID='"+mchtNo+"' and refuse_type='新增审核退回' ";
				commQueryDAO.excute(del);
			
			TblMchtSettleInfTmp tblMchtSettleInfTmp =updateTmpMchtSettleInf(settleTmp);
			
			if(StringUtil.isEmpty(openStlno)) {
				tblMchtSettleInfTmp.setOpenStlno(" ");
			}
			if(StringUtil.isEmpty(settleBankNo)) {
				tblMchtSettleInfTmp.setOpenStlno(" ");
			}
			if(StringUtil.isEmpty(changeStlno)) {
				tblMchtSettleInfTmp.setOpenStlno(" ");
			}
			tblMchtSettleInfTmp.setFeeDiv1("1");
			tblMchtSettleInfTmp.setFeeDiv2("2");
			tblMchtSettleInfTmp.setFeeDiv3("3");
			//商户补充信息
			supp1Tmp=buildTmpMchtSupp1Tmp(supp1Tmp);
			if(mchtNoInput.equals(mchtNo)){
				rspCode = service.updateTmp(tblMchtBaseInfTmp,tblMchtSettleInfTmp,supp1Tmp);
			}else{
				rspCode = service.updateTmp(tblMchtBaseInfTmp,tblMchtSettleInfTmp,supp1Tmp,mchtNoInput);
			}
			
			//更新费率信息
			jsonBean.parseJSONArrayData(getAlgo2List());
			int len = jsonBean.getArray().size();
			for(int i=0;i<len;i++){
				String discId = jsonBean.getJSONDataAt(i).getString("discId");
				String feeType = jsonBean.getJSONDataAt(i).getString("feeType");
				TblHisDiscAlgo2Tmp algo2=t2070302BO.getAlgo2(discId);
				if(algo2!=null){
					algo2.setTmpNo(mchtNoInput);
					algo2.setMCHT_NO(mchtNoInput);
					algo2.setFeeType(feeType);
					algo2.setSA_SATUTE("3");
					t2070302BO.updateAlgo2(algo2);
				}
			}
			//更新新增的商户费率的商户号
			String upAlgo2Sql="update tbl_his_disc_algo2_tmp set mcht_no='"+mchtNoInput+"' where tmp_no='"+tmpNo+"'";
			commQueryDAO.excute(upAlgo2Sql);
			//修改文件名

			String basePath = SysParamUtil.getParam(SysParamConstants.FILE_UPLOAD_DISK);
			basePath = basePath.replace("\\", "/");
			File fl = new File(basePath);
			FileFilter filter = new FileFilter(mchtNo);
			File[] files = fl.listFiles(filter);
			for (File file : files) {
				file.renameTo(new File(basePath + file.getName().replaceAll(mchtNo, mchtNoInput)));
			}
		
			if (Constants.SUCCESS_CODE.equals(rspCode)) {
				return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + "修改商户信息成功，商户编号[" + mchtNoInput + "]");
			} else {
				return rspCode;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode,e);
		}
	}
	
	/**
	 * 冻结商户信息
	 * @return
	 */
	public String stop() {
		try {
			TblMchtBaseInfTmp inf = service.getBaseInfTmp(mchtNo);
						
			if(inf == null) {
				return returnService("没有找到指定的商户信息");
			}
			if(!TblMchntInfoConstants.MCHNT_ST_OK.equals(inf.getMchtStatus())) {
				return returnService("您所冻结的商户当前不是可冻结状态");
			}
			inf.setMchtStatus(TblMchntInfoConstants.MCHNT_ST_STOP_UNCK);
			
			// 记录录入人
			inf.setCrtOprId(getOperator().getOprId());
			inf.setPartNum(exMsg);
			rspCode = service.updateBaseInfTmp(inf);
			
			return returnService(rspCode);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}
	}
//	
	
	
	public String delete(){
		try {
//			TblMchtBaseInfTmp inf = service.getBaseInfTmp(mchtNo);
			System.out.println("商户编号:"+mchtNo);
			String delMchtNo="delete from tbl_mcht_base_inf_tmp where mcht_no = '"+mchtNo+"'";
			commQueryDAO.excute(delMchtNo);
			return returnService(Constants.SUCCESS_CODE);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService("删除失败"+mchtNo);
		}
	}
	
	
	
	
	
	
	
	
	/**
	 * 恢复商户信息
	 * @return
	 */
	public String recover() {
		try {
			TblMchtBaseInfTmp inf = service.getBaseInfTmp(mchtNo);
						
			if(inf == null) {
				return returnService("没有找到指定的商户信息");
			}
			if(!TblMchntInfoConstants.MCHNT_ST_STOP.equals(inf.getMchtStatus())) {
				return returnService("您所恢复的商户当前不是可恢复状态");
			}
			inf.setMchtStatus(TblMchntInfoConstants.MCHNT_ST_RCV_UNCK);
			// 记录录入人
			inf.setCrtOprId(getOperator().getOprId());
			inf.setPartNum(exMsg);
			rspCode = service.updateBaseInfTmp(inf);
			
			return returnService(rspCode);
		} catch (Exception e) {
			return returnService(rspCode, e);
		}
	}
	
	
	/**
	 * 商户信息注销
	 * @return
	 */
	public String del() {
		try {
			TblMchtBaseInfTmp inf = service.getBaseInfTmp(mchtNo);
						
			if(inf == null) {
				return returnService("没有找到指定的商户信息");
			}
			if(!TblMchntInfoConstants.MCHNT_ST_OK.equals(inf.getMchtStatus()) && !TblMchntInfoConstants.MCHNT_ST_STOP.equals(inf.getMchtStatus())) {
				return returnService("您所注销的商户当前不是可注销状态");
			}
			inf.setMchtStatus(TblMchntInfoConstants.MCHNT_ST_DEL_UNCK);
			
			// 记录录入人
			inf.setCrtOprId(getOperator().getOprId());
			inf.setPartNum(exMsg);
			rspCode = service.updateBaseInfTmp(inf);
			
			return returnService(rspCode);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}
	}
	
	
	/**
	 * 商户结算信息新增
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String setAdd() {
		
		TblMchtSettleInfPK id = new TblMchtSettleInfPK();
		id.setMchtNo(mchtNo);
		id.setTermId(CommonFunction.fillString(termId,' ',8,true));
		try {
			TblMchtSettleInfTmp settleInf = service.getSettleInfTmp(id);
			
//			settleInf.setLegalNam(legalNam);
//			settleInf.setCompanyNam(companyNam);
			
			if(settleInf==null){
				TblMchtSettleInfTmp settleInfTmp = buildTmpMchtSettleInfo();
				rspCode = service.saveSettleInfTmp(settleInfTmp);
			return returnService(rspCode);
			}
			else{
				return returnService("已经存在相同的结算信息！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}
		
	}
	
	/**
	 * 商户结算信息修改
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String setUpdate() {
		
		TblMchtSettleInfPK id = new TblMchtSettleInfPK();
		id.setMchtNo(mchtNo);
		id.setTermId(CommonFunction.fillString(termId,' ',8,true));
		try {
			System.out.println("法人账户名称legalNam---"+legalNam+"==公司账户名称companyNam=="+companyNam);
			System.out.println("结算账户类型："+settleRpt);
			System.out.println(dirOpenBank_1);
			System.out.println(dirBankProvince_1);
			System.out.println(dirBankCity_1);
			//判断结算账户类型
			if (settleRpt.equals("1")) {
				if (StringUtil.isEmpty(corpBankName) && StringUtil.isEmpty(feeAcct) && StringUtil.isEmpty(legalNam)){
					return returnService("对私信息有空，请查看填写错误！");
				}
			} else if(settleRpt.equals("2")) {
				if (StringUtil.isEmpty(compAccountBankName) && StringUtil.isEmpty(settleAcct) && StringUtil.isEmpty(companyNam)){
					return returnService("对公信息有空，请查看填写错误！");
				}
			} else if(settleRpt.equals("3")) {
				if (StringUtil.isEmpty(dirBankName) && StringUtil.isEmpty(dirAccount) && StringUtil.isEmpty(dirAccountName)){
					return returnService("定向委托信息有空，请查看填写错误！");
				}
			}
			TblMchtSettleInfTmp settleInf = service.getSettleInfTmp(id);
			settleInf.setDirOpenBank(dirOpenBank_1);
			settleInf.setDirBankProvince(dirBankProvince_1);
			settleInf.setDirBankCity(dirBankCity_1);
			settleInf.setCompOpenBank(compOpenBank_1);
			settleInf.setCompBankProvince(compBankProvince_1);
			settleInf.setCompBankCity(compBankCity_1);
			settleInf.setCorpOpenBank(corpOpenBank_1);
			settleInf.setCorpBankProvince(corpBankProvince_1);
			settleInf.setCorpBankCity(corpBankCity_1);
			
			settleInf.setLegalNam(legalNam);
			settleInf.setCompanyNam(companyNam);
			
			if(settleInf==null){
				return returnService("没有找到指定的商户结算信息！");
			}else{
				settleInf = updateTmpMchtSettleInf(settleInf);
				rspCode = service.updateSettleInfTmp(settleInf);
				return returnService(rspCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}
		
	}
	
	/**
	 * 商户结算信息删除
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String setDel() {
		
		TblMchtSettleInfPK id = new TblMchtSettleInfPK();
		id.setMchtNo(mchtNo);
		id.setTermId(CommonFunction.fillString(termId,' ',8,true));
		try {
			TblMchtSettleInfTmp settleInf = service.getSettleInfTmp(id);
			if(settleInf==null){
				return returnService("没有找到指定的商户结算信息！");
			}else{
				rspCode = service.deleteSettleInfTmp(settleInf);
				return returnService(rspCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}
		
	}
	

	/**
	 * 构造临时商户清算信息
	 *
	 * @param request
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	private TblMchtSettleInfTmp updateTmpMchtSettleInf(TblMchtSettleInfTmp tblMchtSettleInfTmp) throws IllegalAccessException, InvocationTargetException {
		BeanUtils.copyProperties(tblMchtSettleInfTmp, this);
		TblMchtSettleInfPK id = new TblMchtSettleInfPK();

		tblMchtSettleInfTmp.setSettleAcct(settleAcct);//商户结算帐户号
		tblMchtSettleInfTmp.setRateFlag("-");//手续费结算类型
		tblMchtSettleInfTmp.setFeeType("3"); //手续费类型 ,清算时取的是费率表中的类型 -- 环讯系统
		tblMchtSettleInfTmp.setFeeFixed("-");//该字段无效-- 环讯系统
		tblMchtSettleInfTmp.setFeeMaxAmt("0");//该字段无效-- 环讯系统
		tblMchtSettleInfTmp.setFeeMinAmt("0");//该字段无效-- 环讯系统

		tblMchtSettleInfTmp.setFeeDiv1("-");//该字段无效-- 环讯系统
		tblMchtSettleInfTmp.setFeeDiv2("-");//该字段无效-- 环讯系统
		tblMchtSettleInfTmp.setFeeDiv3("-");//该字段无效-- 环讯系统
		
		tblMchtSettleInfTmp.setSettleChn(settleChn);//环讯系统--该字段用作商户结算方式 及T+N
		

		tblMchtSettleInfTmp.setAutoStlFlg("0");//是否收支2条线
		//退货是否返还手续费
		tblMchtSettleInfTmp.setReturnFeeFlag(isChecked(returnFeeFlag));
		tblMchtSettleInfTmp.setFeeBackFlg("0"); //退货是否返还手续费


		tblMchtSettleInfTmp.setFeeRate(discCode);
		if("3".equals(tblMchtSettleInfTmp.getSettleRpt())){
			tblMchtSettleInfTmp.setDirFlag("1");
		}else{
			tblMchtSettleInfTmp.setDirFlag("0");
		}
		tblMchtSettleInfTmp.setAutoFlag(isChecked(autoFlag));
		tblMchtSettleInfTmp.setHolidaySetFlag(isChecked(holidaySetFlag));
		tblMchtSettleInfTmp.setCreFlag(isChecked(creFlag));
		
		//公司账户取商户名
		tblMchtSettleInfTmp.setSettleAcctNm(mchtNm);
		//法人账户取法人姓名
		tblMchtSettleInfTmp.setFeeAcctNm(manager);

		// 记录修改时间
		tblMchtSettleInfTmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
		// 记录创建时间

		return tblMchtSettleInfTmp;
	}

	
	
	/**
	 * 构造临时商户清算信息
	 *
	 * @param request
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	private TblMchtSettleInfTmp buildTmpMchtSettleInfo() throws IllegalAccessException, InvocationTargetException {
		TblMchtSettleInfTmp tblMchtSettleInfTmp = new TblMchtSettleInfTmp();
		BeanUtils.copyProperties(tblMchtSettleInfTmp, this);
		TblMchtSettleInfPK id = new TblMchtSettleInfPK();
		id.setMchtNo(mchtNo);
		id.setTermId(CommonFunction.fillString(termId,' ',8,true));
		tblMchtSettleInfTmp.setId(id);

		tblMchtSettleInfTmp.setSettleAcct(settleAcct);//商户结算帐户号
		tblMchtSettleInfTmp.setRateFlag("-");//手续费结算类型
		tblMchtSettleInfTmp.setFeeType("3"); //手续费类型 ,清算时取的是费率表中的类型 -- 环讯系统
		tblMchtSettleInfTmp.setFeeFixed("-");//该字段无效-- 环讯系统
		tblMchtSettleInfTmp.setFeeMaxAmt("0");//该字段无效-- 环讯系统
		tblMchtSettleInfTmp.setFeeMinAmt("0");//该字段无效-- 环讯系统

		tblMchtSettleInfTmp.setFeeDiv1("-");//该字段无效-- 环讯系统
		tblMchtSettleInfTmp.setFeeDiv2("-");//该字段无效-- 环讯系统
		tblMchtSettleInfTmp.setFeeDiv3("-");//该字段无效-- 环讯系统
		
		tblMchtSettleInfTmp.setSettleChn(settleChn);//环讯系统--该字段用作商户结算方式 及T+N
		

		tblMchtSettleInfTmp.setAutoStlFlg("0");//是否收支2条线
		//退货是否返还手续费
		tblMchtSettleInfTmp.setReturnFeeFlag(isChecked(returnFeeFlag));
		tblMchtSettleInfTmp.setFeeBackFlg("0"); //退货是否返还手续费


		tblMchtSettleInfTmp.setFeeRate(discCode);
		//定向委托
		if("3".equals(tblMchtSettleInfTmp.getSettleRpt())){
			tblMchtSettleInfTmp.setDirFlag("1");
			tblMchtSettleInfTmp.setDirOpenBank(dirOpenBank_1);
			tblMchtSettleInfTmp.setDirBankProvince(dirBankProvince_1);
			tblMchtSettleInfTmp.setDirBankCity(dirBankCity_1);
		}else{
			tblMchtSettleInfTmp.setDirFlag("0");
		}
		//对公
		if("2".equals(tblMchtSettleInfTmp.getSettleRpt())){
			tblMchtSettleInfTmp.setCompOpenBank(compOpenBank_1);
			tblMchtSettleInfTmp.setCompBankProvince(compBankProvince_1);
			tblMchtSettleInfTmp.setCompBankCity(compBankCity_1);
		}
		//对私
		if("1".equals(tblMchtSettleInfTmp.getSettleRpt())){
			tblMchtSettleInfTmp.setCorpOpenBank(corpOpenBank_1);
			tblMchtSettleInfTmp.setCorpBankProvince(corpBankProvince_1);
			tblMchtSettleInfTmp.setCorpBankCity(corpBankCity_1);
		}
		tblMchtSettleInfTmp.setAutoFlag(isChecked(autoFlag));
		tblMchtSettleInfTmp.setHolidaySetFlag(isChecked(holidaySetFlag));
		tblMchtSettleInfTmp.setCreFlag(isChecked(creFlag));

		// 记录修改时间
		tblMchtSettleInfTmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
		// 记录创建时间
		tblMchtSettleInfTmp.setRecCrtTs(CommonFunction.getCurrentDateTime());

		return tblMchtSettleInfTmp;
	}

	
	/**
	 * 构造临时商户信息
	 * 
	 * @param request
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private TblMchtSettleInfTmp updateTmpMchtSettleInfo(TblMchtSettleInfTmp settleTmp) throws IllegalAccessException, InvocationTargetException {
		BeanUtils.copyProperties(settleTmp, this);
		
		settleTmp.setSettleAcct(settleAcct);

		settleTmp.setRateFlag("-");
		settleTmp.setFeeType("3"); //分段收费
		settleTmp.setFeeFixed("-");
		settleTmp.setFeeMaxAmt("0");
		settleTmp.setFeeMinAmt("0");
		
		settleTmp.setFeeDiv1("-");
		settleTmp.setFeeDiv2("-");
		settleTmp.setFeeDiv3("-");
		
		//是否自动清算
		if (!StringUtil.isNull(autoStlFlg) && TblMchntInfoConstants.EXTJS_CHECKED.equalsIgnoreCase(autoStlFlg)) {
			settleTmp.setAutoStlFlg("1");
		} else {
			settleTmp.setAutoStlFlg("0");
		}
		//退货是否返还手续费
		if (!StringUtil.isNull(feeBackFlg) && TblMchntInfoConstants.EXTJS_CHECKED.equalsIgnoreCase(feeBackFlg)) {
			settleTmp.setFeeBackFlg("1");
		} else {
			settleTmp.setFeeBackFlg("0");
		}
		
		settleTmp.setFeeRate(discCode);

		// 记录修改时间
		settleTmp.setRecUpdTs(CommonFunction.getCurrentDateTime());

		return settleTmp;
	}
	
	private TblMchtSupp1Tmp buildTmpMchtSupp1Tmp(TblMchtSupp1Tmp suppTmp) throws InvocationTargetException, IllegalAccessException{
		if(suppTmp==null)suppTmp=new TblMchtSupp1Tmp();
		BeanUtils.copyProperties(suppTmp, this);
		suppTmp.setPreAuthor(isChecked(preAuthor));
		suppTmp.setReturnFunc(isChecked(returnFunc));
		suppTmp.setIsWineshop(isChecked(isWineshop));
		suppTmp.setIsAppOutSide(isChecked(isAppOutSide));
		suppTmp.setIsMoreAcq(isChecked(isMoreAcq));
		suppTmp.setHasInnerPosExp(isChecked(hasInnerPosExp));
		suppTmp.setHasOurPosExp(isChecked(hasOurPosExp));
		return suppTmp;
	}
	
	public String isChecked(String param) {
		return param==null?"0":"1";
	}
	
	/**
	 * 构造临时商户信息
	 * 
	 * @param request
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private TblMchtBaseInfTmp updateTmpMchtBaseInfo(TblMchtBaseInfTmp tmp) throws IllegalAccessException, InvocationTargetException {
		BeanUtils.copyProperties(tmp, this);
		
		tmp.setBankNo(acqInstId);
		//是否集团商户
		if(!StringUtil.isNull(mchtGroupFlag) && TblMchntInfoConstants.EXTJS_CHECKED.equalsIgnoreCase(mchtGroupFlag)){
			tmp.setMchtGroupFlag("1");
		}else{
			tmp.setMchtGroupFlag("0");
		}
		//是否MIS商户
		if(!StringUtil.isNull(mchtMngMode) && TblMchntInfoConstants.EXTJS_CHECKED.equalsIgnoreCase(mchtMngMode)){
			tmp.setMchtMngMode("1");
		}else{
			tmp.setMchtMngMode("0");
		}
		//是否支持无磁无密交易
		if (!StringUtil.isNull(passFlag)  && TblMchntInfoConstants.EXTJS_CHECKED.equalsIgnoreCase(passFlag)) {
			tmp.setPassFlag("1");
		} else {
			tmp.setPassFlag("0");
		}
		//是否支持人工授权交易
		if (!StringUtil.isNull(manuAuthFlag)  && TblMchntInfoConstants.EXTJS_CHECKED.equalsIgnoreCase(manuAuthFlag)) {
			tmp.setManuAuthFlag("1");
		} else {
			tmp.setManuAuthFlag("0");
		}
		//是否支持折扣消费
		if (!StringUtil.isNull(discConsFlg) && TblMchntInfoConstants.EXTJS_CHECKED.equalsIgnoreCase(discConsFlg)) {
			tmp.setDiscConsFlg("1");
		} else {
			tmp.setDiscConsFlg("0");
		}
		//申请日期（注册日期）
		//不应修改该字段值20120716
//		tmp.setApplyDate(CommonFunction.getCurrentDate());
		
		// 记录修改时间
		tmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
		// 记录录入人
		tmp.setCrtOprId(getOperator().getOprId());
		
		//tmp.setLicenceEndDate("-");
		
		tmp.setEngName(engName.length()>40?"":engName);
		tmp.setRislLvl(rislLvl);//风险级别  //by mike
		tmp.setMchtLvl("0");//商户类别   (默认值为0)  //by mike
		tmp.setConnType("0");//商户类型   (默认值为0)  //by mike
		tmp.setSaAction("0");//受控处理动作

		tmp.setCupMchtFlg("1");//银联卡受控标志
		tmp.setDebMchtFlg("1");//借记卡受理标志
		tmp.setCreMchtFlg("1");//贷记卡受理标志
		tmp.setCdcMchtFlg("1");//一帐通受理标志
		
		return tmp;
	}
	
	
	public String upload(){
		FileInputStream is = null;
		DataOutputStream out = null;
		DataInputStream dis = null;
		
		try {
			String fileName = "";
			int fileNameIndex = 0;
			
			String basePath = SysParamUtil.getParam(SysParamConstants.FILE_UPLOAD_DISK);
			
			basePath = basePath.replace("\\", "/");
			
			Random random = new Random();
			
			for(File file : imgFile) {
				is = new FileInputStream(file);
				fileName = imgFileFileName.get(fileNameIndex);
				String fileType ="";
				if (fileName.indexOf(".") != -1) {
					fileType = fileName.substring(fileName.lastIndexOf("."));
				}
				
				File writeFile = new File(basePath + imagesId + random.nextInt(999999) + fileType);
				
				if (!writeFile.getParentFile().exists()) {
					writeFile.getParentFile().mkdir();
				}
				if (writeFile.exists()) {
					writeFile.delete();
				}
				dis = new DataInputStream(is);
				out = new DataOutputStream(new FileOutputStream(writeFile));

				int temp;
				while((temp = dis.read()) != -1){
					out.write(temp);
				}

				out.flush();
				out.close();
				dis.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return returnService("上传文件失败！", e);
		} finally {
			try {
				if (null != out) {
					out.close();
				}
				if (null != is) {
					is.close();
				}
				if (null != dis) {
					dis.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return returnService(Constants.SUCCESS_CODE);
	}
	
	/**
	 * @return the imgFile
	 */
	public List<File> getImgFile() {
		return imgFile;
	}

	/**
	 * @param imgFile the imgFile to set
	 */
	public void setImgFile(List<File> imgFile) {
		this.imgFile = imgFile;
	}

	/**
	 * @return the imgFileFileName
	 */
	public List<String> getImgFileFileName() {
		return imgFileFileName;
	}

	/**
	 * @param imgFileFileName the imgFileFileName to set
	 */
	public void setImgFileFileName(List<String> imgFileFileName) {
		this.imgFileFileName = imgFileFileName;
	}

	/**
	 * @return the imagesId
	 */
	public String getImagesId() {
		return imagesId;
	}

	/**
	 * @param imagesId the imagesId to set
	 */
	public void setImagesId(String imagesId) {
		this.imagesId = imagesId;
	}
	
	// 文件集合
	private List<File> imgFile;
	// 文件名称集合
	private List<String> imgFileFileName;
	private String imagesId;
	
	// primary key
	private java.lang.String mchtNo;
	private String tmpNo;
	private java.lang.String mchtNoInput;
	// fields
	private java.lang.String mchtNm;
	private java.lang.String rislLvl;
	private java.lang.String mchtLvl;
	private java.lang.String mchtStatus;
	private java.lang.String manuAuthFlag;
	private java.lang.String partNum;
	private java.lang.String discConsFlg;
	private java.lang.String discConsRebate;
	private java.lang.String passFlag;
	private java.lang.String openDays;
	private java.lang.String sleepDays;
	private java.lang.String mchtCnAbbr;
	private java.lang.String spellName;
	private java.lang.String engName;
	private String engNameAbbr;
	private java.lang.String mchtEnAbbr;
//	因修改时不需修改此字段，故注掉
//	private java.lang.String areaNo;
//	private java.lang.String settleAreaNo;
	private java.lang.String addr;
	private java.lang.String homePage;
	private java.lang.String mcc;
	private java.lang.String tcc;
	private java.lang.String etpsAttr;
	private java.lang.String mngMchtId;
	private java.lang.String mchtGrp;
	private java.lang.String mchtAttr;
	private java.lang.String mchtGroupFlag;
	private java.lang.String mchtGroupId;
	private java.lang.String mchtEngNm;
	private java.lang.String mchtEngAddr;
	private java.lang.String mchtEngCityName;
	private java.lang.String saLimitAmt;
	private java.lang.String saAction;
	private java.lang.String psamNum;
	private java.lang.String cdMacNum;
	private java.lang.String posNum;
	private java.lang.String connType;
	private java.lang.String mchtMngMode;
	private java.lang.String mchtFunction;
	private java.lang.String licenceNo;
	private java.lang.String licenceEndDate;
	private java.lang.String bankLicenceNo;
	private java.lang.String busType;
	private java.lang.String faxNo;
	private java.lang.String busAmt;
	private java.lang.String mchtCreLvl;
	private java.lang.String contact;
	private java.lang.String postCode;
	private java.lang.String commEmail;
	private java.lang.String commMobil;
	private java.lang.String commTel;
	private java.lang.String manager;
	private java.lang.String artifCertifTp;
	private java.lang.String identityNo;
	private java.lang.String managerTel;
	private java.lang.String fax;
	private java.lang.String electrofax;
	private java.lang.String regAddr;
	private java.lang.String applyDate;
	private java.lang.String enableDate;
	private java.lang.String preAudNm;
	private java.lang.String confirmNm;
	private java.lang.String protocalId;
	private java.lang.String signInstId;
	private java.lang.String netNm;
	private java.lang.String agrBr;
	private java.lang.String netTel;
	private java.lang.String prolDate;
	private java.lang.String prolTlr;
	private java.lang.String closeDate;
	private java.lang.String closeTlr;
	private java.lang.String mainTlr;
	private java.lang.String checkTlr;
	private java.lang.String operNo;
	private java.lang.String operNm;
	private java.lang.String procFlag;
	private java.lang.String setCur;
	private java.lang.String printInstId;
	private java.lang.String acqInstId;
	private java.lang.String acqBkName;
	private java.lang.String bankNo;
	private java.lang.String orgnNo;
	private java.lang.String subbrhNo;
	private java.lang.String subbrhNm;
	private java.lang.String openTime;
	private java.lang.String closeTime;
	private java.lang.String visActFlg;
	private java.lang.String visMchtId;
	private java.lang.String mstActFlg;
	private java.lang.String mstMchtId;
	private java.lang.String amxActFlg;
	private java.lang.String amxMchtId;
	private java.lang.String dnrActFlg;
	private java.lang.String dnrMchtId;
	private java.lang.String jcbActFlg;
	private java.lang.String jcbMchtId;
	private java.lang.String cupMchtFlg;
	private java.lang.String debMchtFlg;
	private java.lang.String creMchtFlg;
	private java.lang.String cdcMchtFlg;
	
	private java.lang.String settleType;
	private java.lang.String clearType;
	
	//统一社会信用代码证有效期
	private String uscCodeDate;
	//营业执照有效日期
	private String licenceNoDate;
	//控股股东或实际控制人姓名
	private String shareholder;
	//控股股东或实际控制人身份证件种类
	private String idshareholderTpmd;
	//控股股东或实际控制人身份证件号码
	private String idshareholder;
	//控股股东或实际控制人身份证件有效期
	private String shareholderDate;
	//法定代表人（负责人）身份证件有效期
	private String identityNoDate;
	//授权人证件类型
	private String contactmd;
	//授权人身份证号有效日期
	private String linkTelDate; 
	//经营地址
	private String operateAddr; 

	
	public String getOperateAddr() {
		return operateAddr;
	}

	public void setOperateAddr(String operateAddr) {
		this.operateAddr = operateAddr;
	}

	public String getUscCodeDate() {
		return uscCodeDate;
	}

	public void setUscCodeDate(String uscCodeDate) {
		this.uscCodeDate = uscCodeDate;
	}

	public String getLicenceNoDate() {
		return licenceNoDate;
	}

	public void setLicenceNoDate(String licenceNoDate) {
		this.licenceNoDate = licenceNoDate;
	}

	public String getShareholder() {
		return shareholder;
	}

	public void setShareholder(String shareholder) {
		this.shareholder = shareholder;
	}

	public String getIdshareholderTpmd() {
		return idshareholderTpmd;
	}

	public void setIdshareholderTpmd(String idshareholderTpmd) {
		this.idshareholderTpmd = idshareholderTpmd;
	}

	public String getIdshareholder() {
		return idshareholder;
	}

	public void setIdshareholder(String idshareholder) {
		this.idshareholder = idshareholder;
	}

	public String getShareholderDate() {
		return shareholderDate;
	}

	public void setShareholderDate(String shareholderDate) {
		this.shareholderDate = shareholderDate;
	}

	public String getIdentityNoDate() {
		return identityNoDate;
	}

	public void setIdentityNoDate(String identityNoDate) {
		this.identityNoDate = identityNoDate;
	}

	public String getContactmd() {
		return contactmd;
	}

	public void setContactmd(String contactmd) {
		this.contactmd = contactmd;
	}

	public String getLinkTelDate() {
		return linkTelDate;
	}

	public void setLinkTelDate(String linkTelDate) {
		this.linkTelDate = linkTelDate;
	}

	/**
	 * @return the clearType
	 */
	public java.lang.String getClearType() {
		return clearType;
	}

	/**
	 * @param clearType the clearType to set
	 */
	public void setClearType(java.lang.String clearType) {
		this.clearType = clearType;
	}

	public TblMchntService getService() {
		return service;
	}

	public void setService(TblMchntService service) {
		this.service = service;
	}

	public String getEngNameAbbr() {
		return engNameAbbr;
	}

	public void setEngNameAbbr(String engNameAbbr) {
		this.engNameAbbr = engNameAbbr;
	}

	public java.lang.String getMchtNo() {
		return mchtNo;
	}

	public void setMchtNo(java.lang.String mchtNo) {
		this.mchtNo = mchtNo;
	}

	public java.lang.String getMchtNm() {
		return mchtNm;
	}

	public void setMchtNm(java.lang.String mchtNm) {
		this.mchtNm = mchtNm;
	}

	public java.lang.String getRislLvl() {
		return rislLvl;
	}

	public void setRislLvl(java.lang.String rislLvl) {
		this.rislLvl = rislLvl;
	}

	public java.lang.String getMchtLvl() {
		return mchtLvl;
	}

	public void setMchtLvl(java.lang.String mchtLvl) {
		this.mchtLvl = mchtLvl;
	}

	public java.lang.String getMchtStatus() {
		return mchtStatus;
	}

	public void setMchtStatus(java.lang.String mchtStatus) {
		this.mchtStatus = mchtStatus;
	}

	public java.lang.String getManuAuthFlag() {
		return manuAuthFlag;
	}

	public void setManuAuthFlag(java.lang.String manuAuthFlag) {
		this.manuAuthFlag = manuAuthFlag;
	}

	public java.lang.String getDiscConsFlg() {
		return discConsFlg;
	}

	public void setDiscConsFlg(java.lang.String discConsFlg) {
		this.discConsFlg = discConsFlg;
	}

	public java.lang.String getDiscConsRebate() {
		return discConsRebate;
	}

	public void setDiscConsRebate(java.lang.String discConsRebate) {
		this.discConsRebate = discConsRebate;
	}

	public java.lang.String getPassFlag() {
		return passFlag;
	}

	public void setPassFlag(java.lang.String passFlag) {
		this.passFlag = passFlag;
	}

	public java.lang.String getOpenDays() {
		return openDays;
	}

	public void setOpenDays(java.lang.String openDays) {
		this.openDays = openDays;
	}

	public java.lang.String getSleepDays() {
		return sleepDays;
	}

	public void setSleepDays(java.lang.String sleepDays) {
		this.sleepDays = sleepDays;
	}

	public java.lang.String getMchtCnAbbr() {
		return mchtCnAbbr;
	}

	public void setMchtCnAbbr(java.lang.String mchtCnAbbr) {
		this.mchtCnAbbr = mchtCnAbbr;
	}

	public java.lang.String getSpellName() {
		return spellName;
	}

	public void setSpellName(java.lang.String spellName) {
		this.spellName = spellName;
	}

	public java.lang.String getEngName() {
		return engName;
	}

	public void setEngName(java.lang.String engName) {
		this.engName = engName;
	}

	public java.lang.String getMchtEnAbbr() {
		return mchtEnAbbr;
	}

	public void setMchtEnAbbr(java.lang.String mchtEnAbbr) {
		this.mchtEnAbbr = mchtEnAbbr;
	}

	public java.lang.String getAddr() {
		return addr;
	}

	public void setAddr(java.lang.String addr) {
		this.addr = addr;
	}

	public java.lang.String getHomePage() {
		return homePage;
	}

	public void setHomePage(java.lang.String homePage) {
		this.homePage = homePage;
	}

	public java.lang.String getMcc() {
		return mcc;
	}

	public void setMcc(java.lang.String mcc) {
		this.mcc = mcc;
	}

	public java.lang.String getTcc() {
		return tcc;
	}

	public void setTcc(java.lang.String tcc) {
		this.tcc = tcc;
	}

	public java.lang.String getEtpsAttr() {
		return etpsAttr;
	}

	public void setEtpsAttr(java.lang.String etpsAttr) {
		this.etpsAttr = etpsAttr;
	}

	public java.lang.String getMngMchtId() {
		return mngMchtId;
	}

	public void setMngMchtId(java.lang.String mngMchtId) {
		this.mngMchtId = mngMchtId;
	}

	public java.lang.String getMchtGrp() {
		return mchtGrp;
	}

	public void setMchtGrp(java.lang.String mchtGrp) {
		this.mchtGrp = mchtGrp;
	}

	public java.lang.String getMchtAttr() {
		return mchtAttr;
	}

	public void setMchtAttr(java.lang.String mchtAttr) {
		this.mchtAttr = mchtAttr;
	}

	public java.lang.String getMchtGroupFlag() {
		return mchtGroupFlag;
	}

	public void setMchtGroupFlag(java.lang.String mchtGroupFlag) {
		this.mchtGroupFlag = mchtGroupFlag;
	}

	public java.lang.String getMchtGroupId() {
		return mchtGroupId;
	}

	public void setMchtGroupId(java.lang.String mchtGroupId) {
		this.mchtGroupId = mchtGroupId;
	}

	public java.lang.String getMchtEngNm() {
		return mchtEngNm;
	}

	public void setMchtEngNm(java.lang.String mchtEngNm) {
		this.mchtEngNm = mchtEngNm;
	}

	public java.lang.String getMchtEngAddr() {
		return mchtEngAddr;
	}

	public void setMchtEngAddr(java.lang.String mchtEngAddr) {
		this.mchtEngAddr = mchtEngAddr;
	}

	public java.lang.String getMchtEngCityName() {
		return mchtEngCityName;
	}

	public void setMchtEngCityName(java.lang.String mchtEngCityName) {
		this.mchtEngCityName = mchtEngCityName;
	}

	public java.lang.String getSaLimitAmt() {
		return saLimitAmt;
	}

	public void setSaLimitAmt(java.lang.String saLimitAmt) {
		this.saLimitAmt = saLimitAmt;
	}

	public java.lang.String getSaAction() {
		return saAction;
	}

	public void setSaAction(java.lang.String saAction) {
		this.saAction = saAction;
	}

	public java.lang.String getPsamNum() {
		return psamNum;
	}

	public void setPsamNum(java.lang.String psamNum) {
		this.psamNum = psamNum;
	}

	public java.lang.String getCdMacNum() {
		return cdMacNum;
	}

	public void setCdMacNum(java.lang.String cdMacNum) {
		this.cdMacNum = cdMacNum;
	}

	public java.lang.String getPosNum() {
		return posNum;
	}

	public void setPosNum(java.lang.String posNum) {
		this.posNum = posNum;
	}

	public java.lang.String getConnType() {
		return connType;
	}

	public void setConnType(java.lang.String connType) {
		this.connType = connType;
	}

	public java.lang.String getMchtMngMode() {
		return mchtMngMode;
	}

	public void setMchtMngMode(java.lang.String mchtMngMode) {
		this.mchtMngMode = mchtMngMode;
	}

	public java.lang.String getMchtFunction() {
		return mchtFunction;
	}

	public void setMchtFunction(java.lang.String mchtFunction) {
		this.mchtFunction = mchtFunction;
	}

	public java.lang.String getLicenceNo() {
		return licenceNo;
	}

	public void setLicenceNo(java.lang.String licenceNo) {
		this.licenceNo = licenceNo;
	}

	public java.lang.String getLicenceEndDate() {
		return licenceEndDate;
	}

	public void setLicenceEndDate(java.lang.String licenceEndDate) {
		this.licenceEndDate = licenceEndDate;
	}

	public java.lang.String getBankLicenceNo() {
		return bankLicenceNo;
	}

	public void setBankLicenceNo(java.lang.String bankLicenceNo) {
		this.bankLicenceNo = bankLicenceNo;
	}

	public java.lang.String getBusType() {
		return busType;
	}

	public void setBusType(java.lang.String busType) {
		this.busType = busType;
	}

	public java.lang.String getFaxNo() {
		return faxNo;
	}

	public void setFaxNo(java.lang.String faxNo) {
		this.faxNo = faxNo;
	}

	public java.lang.String getBusAmt() {
		return busAmt;
	}

	public void setBusAmt(java.lang.String busAmt) {
		this.busAmt = busAmt;
	}

	public java.lang.String getMchtCreLvl() {
		return mchtCreLvl;
	}

	public void setMchtCreLvl(java.lang.String mchtCreLvl) {
		this.mchtCreLvl = mchtCreLvl;
	}

	public java.lang.String getContact() {
		return contact;
	}

	public void setContact(java.lang.String contact) {
		this.contact = contact;
	}

	public java.lang.String getPostCode() {
		return postCode;
	}

	public void setPostCode(java.lang.String postCode) {
		this.postCode = postCode;
	}

	public java.lang.String getCommEmail() {
		return commEmail;
	}

	public void setCommEmail(java.lang.String commEmail) {
		this.commEmail = commEmail;
	}

	public java.lang.String getCommMobil() {
		return commMobil;
	}

	public void setCommMobil(java.lang.String commMobil) {
		this.commMobil = commMobil;
	}

	public java.lang.String getCommTel() {
		return commTel;
	}

	public void setCommTel(java.lang.String commTel) {
		this.commTel = commTel;
	}

	public java.lang.String getManager() {
		return manager;
	}

	public void setManager(java.lang.String manager) {
		this.manager = manager;
	}

	public java.lang.String getArtifCertifTp() {
		return artifCertifTp;
	}

	public void setArtifCertifTp(java.lang.String artifCertifTp) {
		this.artifCertifTp = artifCertifTp;
	}

	public java.lang.String getIdentityNo() {
		return identityNo;
	}

	public void setIdentityNo(java.lang.String identityNo) {
		this.identityNo = identityNo;
	}

	public java.lang.String getManagerTel() {
		return managerTel;
	}

	public void setManagerTel(java.lang.String managerTel) {
		this.managerTel = managerTel;
	}

	public java.lang.String getFax() {
		return fax;
	}

	public void setFax(java.lang.String fax) {
		this.fax = fax;
	}

	public java.lang.String getElectrofax() {
		return electrofax;
	}

	public void setElectrofax(java.lang.String electrofax) {
		this.electrofax = electrofax;
	}

	public java.lang.String getRegAddr() {
		return regAddr;
	}

	public void setRegAddr(java.lang.String regAddr) {
		this.regAddr = regAddr;
	}

	public java.lang.String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(java.lang.String applyDate) {
		this.applyDate = applyDate;
	}

	public java.lang.String getEnableDate() {
		return enableDate;
	}

	public void setEnableDate(java.lang.String enableDate) {
		this.enableDate = enableDate;
	}

	public java.lang.String getPreAudNm() {
		return preAudNm;
	}

	public void setPreAudNm(java.lang.String preAudNm) {
		this.preAudNm = preAudNm;
	}

	public java.lang.String getConfirmNm() {
		return confirmNm;
	}

	public void setConfirmNm(java.lang.String confirmNm) {
		this.confirmNm = confirmNm;
	}

	public java.lang.String getProtocalId() {
		return protocalId;
	}

	public void setProtocalId(java.lang.String protocalId) {
		this.protocalId = protocalId;
	}

	public java.lang.String getSignInstId() {
		return signInstId;
	}

	public void setSignInstId(java.lang.String signInstId) {
		this.signInstId = signInstId;
	}

	public java.lang.String getNetNm() {
		return netNm;
	}

	public void setNetNm(java.lang.String netNm) {
		this.netNm = netNm;
	}

	public java.lang.String getAgrBr() {
		return agrBr;
	}

	public void setAgrBr(java.lang.String agrBr) {
		this.agrBr = agrBr;
	}

	public java.lang.String getNetTel() {
		return netTel;
	}

	public void setNetTel(java.lang.String netTel) {
		this.netTel = netTel;
	}

	public java.lang.String getProlDate() {
		return prolDate;
	}

	public void setProlDate(java.lang.String prolDate) {
		this.prolDate = prolDate;
	}

	public java.lang.String getProlTlr() {
		return prolTlr;
	}

	public void setProlTlr(java.lang.String prolTlr) {
		this.prolTlr = prolTlr;
	}

	public java.lang.String getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(java.lang.String closeDate) {
		this.closeDate = closeDate;
	}

	public java.lang.String getCloseTlr() {
		return closeTlr;
	}

	public void setCloseTlr(java.lang.String closeTlr) {
		this.closeTlr = closeTlr;
	}

	public java.lang.String getMainTlr() {
		return mainTlr;
	}

	public void setMainTlr(java.lang.String mainTlr) {
		this.mainTlr = mainTlr;
	}

	public java.lang.String getCheckTlr() {
		return checkTlr;
	}

	public void setCheckTlr(java.lang.String checkTlr) {
		this.checkTlr = checkTlr;
	}

	public java.lang.String getOperNo() {
		return operNo;
	}

	public void setOperNo(java.lang.String operNo) {
		this.operNo = operNo;
	}

	public java.lang.String getOperNm() {
		return operNm;
	}

	public void setOperNm(java.lang.String operNm) {
		this.operNm = operNm;
	}

	public java.lang.String getProcFlag() {
		return procFlag;
	}

	public void setProcFlag(java.lang.String procFlag) {
		this.procFlag = procFlag;
	}

	public java.lang.String getSetCur() {
		return setCur;
	}

	public void setSetCur(java.lang.String setCur) {
		this.setCur = setCur;
	}

	public java.lang.String getPrintInstId() {
		return printInstId;
	}

	public void setPrintInstId(java.lang.String printInstId) {
		this.printInstId = printInstId;
	}

	public java.lang.String getAcqInstId() {
		return acqInstId;
	}

	public void setAcqInstId(java.lang.String acqInstId) {
		this.acqInstId = acqInstId;
	}

	public java.lang.String getAcqBkName() {
		return acqBkName;
	}

	public void setAcqBkName(java.lang.String acqBkName) {
		this.acqBkName = acqBkName;
	}

	public java.lang.String getBankNo() {
		return bankNo;
	}

	public void setBankNo(java.lang.String bankNo) {
		this.bankNo = bankNo;
	}

	public java.lang.String getOrgnNo() {
		return orgnNo;
	}

	public void setOrgnNo(java.lang.String orgnNo) {
		this.orgnNo = orgnNo;
	}

	public java.lang.String getSubbrhNo() {
		return subbrhNo;
	}

	public void setSubbrhNo(java.lang.String subbrhNo) {
		this.subbrhNo = subbrhNo;
	}

	public java.lang.String getSubbrhNm() {
		return subbrhNm;
	}

	public void setSubbrhNm(java.lang.String subbrhNm) {
		this.subbrhNm = subbrhNm;
	}

	public java.lang.String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(java.lang.String openTime) {
		this.openTime = openTime;
	}

	public java.lang.String getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(java.lang.String closeTime) {
		this.closeTime = closeTime;
	}

	public java.lang.String getVisActFlg() {
		return visActFlg;
	}

	public void setVisActFlg(java.lang.String visActFlg) {
		this.visActFlg = visActFlg;
	}

	public java.lang.String getVisMchtId() {
		return visMchtId;
	}

	public void setVisMchtId(java.lang.String visMchtId) {
		this.visMchtId = visMchtId;
	}

	public java.lang.String getMstActFlg() {
		return mstActFlg;
	}

	public void setMstActFlg(java.lang.String mstActFlg) {
		this.mstActFlg = mstActFlg;
	}

	public java.lang.String getMstMchtId() {
		return mstMchtId;
	}

	public void setMstMchtId(java.lang.String mstMchtId) {
		this.mstMchtId = mstMchtId;
	}

	public java.lang.String getAmxActFlg() {
		return amxActFlg;
	}

	public void setAmxActFlg(java.lang.String amxActFlg) {
		this.amxActFlg = amxActFlg;
	}

	public java.lang.String getAmxMchtId() {
		return amxMchtId;
	}

	public void setAmxMchtId(java.lang.String amxMchtId) {
		this.amxMchtId = amxMchtId;
	}

	public java.lang.String getDnrActFlg() {
		return dnrActFlg;
	}

	public void setDnrActFlg(java.lang.String dnrActFlg) {
		this.dnrActFlg = dnrActFlg;
	}

	public java.lang.String getDnrMchtId() {
		return dnrMchtId;
	}

	public void setDnrMchtId(java.lang.String dnrMchtId) {
		this.dnrMchtId = dnrMchtId;
	}

	public java.lang.String getJcbActFlg() {
		return jcbActFlg;
	}

	public void setJcbActFlg(java.lang.String jcbActFlg) {
		this.jcbActFlg = jcbActFlg;
	}

	public java.lang.String getJcbMchtId() {
		return jcbMchtId;
	}

	public void setJcbMchtId(java.lang.String jcbMchtId) {
		this.jcbMchtId = jcbMchtId;
	}

	public java.lang.String getCupMchtFlg() {
		return cupMchtFlg;
	}

	public void setCupMchtFlg(java.lang.String cupMchtFlg) {
		this.cupMchtFlg = cupMchtFlg;
	}

	public java.lang.String getDebMchtFlg() {
		return debMchtFlg;
	}

	public void setDebMchtFlg(java.lang.String debMchtFlg) {
		this.debMchtFlg = debMchtFlg;
	}

	public java.lang.String getCreMchtFlg() {
		return creMchtFlg;
	}

	public void setCreMchtFlg(java.lang.String creMchtFlg) {
		this.creMchtFlg = creMchtFlg;
	}

	public java.lang.String getCdcMchtFlg() {
		return cdcMchtFlg;
	}

	public void setCdcMchtFlg(java.lang.String cdcMchtFlg) {
		this.cdcMchtFlg = cdcMchtFlg;
	}

	public java.lang.String getSettleType() {
		return settleType;
	}

	public void setSettleType(java.lang.String settleType) {
		this.settleType = settleType;
	}

	public java.lang.String getRateFlag() {
		return rateFlag;
	}

	public void setRateFlag(java.lang.String rateFlag) {
		this.rateFlag = rateFlag;
	}

	public java.lang.String getSettleChn() {
		return settleChn;
	}

	public void setSettleChn(java.lang.String settleChn) {
		this.settleChn = settleChn;
	}

	public java.lang.String getBatTime() {
		return batTime;
	}

	public void setBatTime(java.lang.String batTime) {
		this.batTime = batTime;
	}

	public java.lang.String getAutoStlFlg() {
		return autoStlFlg;
	}

	public void setAutoStlFlg(java.lang.String autoStlFlg) {
		this.autoStlFlg = autoStlFlg;
	}

	public java.lang.String getPartNum() {
		return partNum;
	}

	public void setPartNum(java.lang.String partNum) {
		this.partNum = partNum;
	}

	public java.lang.String getFeeType() {
		return feeType;
	}

	public void setFeeType(java.lang.String feeType) {
		this.feeType = feeType;
	}

	public java.lang.String getFeeFixed() {
		return feeFixed;
	}

	public void setFeeFixed(java.lang.String feeFixed) {
		this.feeFixed = feeFixed;
	}

	public java.lang.String getFeeMaxAmt() {
		return feeMaxAmt;
	}

	public void setFeeMaxAmt(java.lang.String feeMaxAmt) {
		this.feeMaxAmt = feeMaxAmt;
	}

	public java.lang.String getFeeMinAmt() {
		return feeMinAmt;
	}

	public void setFeeMinAmt(java.lang.String feeMinAmt) {
		this.feeMinAmt = feeMinAmt;
	}

	public java.lang.String getFeeRate() {
		return feeRate;
	}

	public void setFeeRate(java.lang.String feeRate) {
		this.feeRate = feeRate;
	}

	public java.lang.String getFeeDiv1() {
		return feeDiv1;
	}

	public void setFeeDiv1(java.lang.String feeDiv1) {
		this.feeDiv1 = feeDiv1;
	}

	public java.lang.String getFeeDiv2() {
		return feeDiv2;
	}

	public void setFeeDiv2(java.lang.String feeDiv2) {
		this.feeDiv2 = feeDiv2;
	}

	public java.lang.String getFeeDiv3() {
		return feeDiv3;
	}

	public void setFeeDiv3(java.lang.String feeDiv3) {
		this.feeDiv3 = feeDiv3;
	}

	public java.lang.String getSettleMode() {
		return settleMode;
	}

	public void setSettleMode(java.lang.String settleMode) {
		this.settleMode = settleMode;
	}

	public java.lang.String getFeeCycle() {
		return feeCycle;
	}

	public void setFeeCycle(java.lang.String feeCycle) {
		this.feeCycle = feeCycle;
	}

	public java.lang.String getSettleRpt() {
		return settleRpt;
	}

	public void setSettleRpt(java.lang.String settleRpt) {
		this.settleRpt = settleRpt;
	}

	public java.lang.String getSettleBankNo() {
		return settleBankNo;
	}

	public void setSettleBankNo(java.lang.String settleBankNo) {
		this.settleBankNo = settleBankNo;
	}

	public java.lang.String getSettleBankNm() {
		return settleBankNm;
	}

	public void setSettleBankNm(java.lang.String settleBankNm) {
		this.settleBankNm = settleBankNm;
	}

	public java.lang.String getSettleAcctNm() {
		return settleAcctNm;
	}

	public void setSettleAcctNm(java.lang.String settleAcctNm) {
		this.settleAcctNm = settleAcctNm;
	}

	public java.lang.String getSettleAcct() {
		return settleAcct;
	}

	public void setSettleAcct(java.lang.String settleAcct) {
		
		this.settleAcct = settleAcct;
	}

	public java.lang.String getFeeAcctNm() {
		return feeAcctNm;
	}

	public void setFeeAcctNm(java.lang.String feeAcctNm) {
		this.feeAcctNm = feeAcctNm;
	}

	public java.lang.String getFeeAcct() {
		return feeAcct;
	}

	public void setFeeAcct(java.lang.String feeAcct) {
		this.feeAcct = feeAcct;
	}

	public java.lang.String getGroupFlag() {
		return groupFlag;
	}

	public void setGroupFlag(java.lang.String groupFlag) {
		this.groupFlag = groupFlag;
	}

	public java.lang.String getOpenStlno() {
		return openStlno;
	}

	public void setOpenStlno(java.lang.String openStlno) {
		this.openStlno = openStlno;
	}

	public java.lang.String getChangeStlno() {
		return changeStlno;
	}

	public void setChangeStlno(java.lang.String changeStlno) {
		this.changeStlno = changeStlno;
	}

	public java.lang.String getReserved() {
		return reserved;
	}

	public void setReserved(java.lang.String reserved) {
		this.reserved = reserved;
	}

	private java.lang.String settleMode;
	private java.lang.String feeCycle;
	private java.lang.String settleRpt;
	private java.lang.String settleBankNo;
	private java.lang.String settleBankNm;
	private java.lang.String settleAcctNm;
	private java.lang.String settleAcct;
	private java.lang.String feeAcctNm;
	private java.lang.String feeAcct;
	private java.lang.String groupFlag;
	private java.lang.String openStlno;
	private java.lang.String changeStlno;
	private java.lang.String reserved;
	private java.lang.String exMsg;
	
	private String discCode;
	
	
	public String getDiscCode() {
		return discCode;
	}
	
	public void setDiscCode(String discCode) {
		this.discCode = discCode;
	}

	@Override
	public String getMsg() {
		return msg;
	}

	@Override
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @return the exMsg
	 */
	public java.lang.String getExMsg() {
		return exMsg;
	}

	/**
	 * @param exMsg the exMsg to set
	 */
	public void setExMsg(java.lang.String exMsg) {
		this.exMsg = exMsg;
	}
	private java.lang.String speSettleTp;
	private java.lang.String speSettleLv;
	private java.lang.String speSettleDs;
	private java.lang.String feeBackFlg;

	/**
	 * @return the speSettleTp
	 */
	public java.lang.String getSpeSettleTp() {
		return speSettleTp;
	}

	/**
	 * @param speSettleTp the speSettleTp to set
	 */
	public void setSpeSettleTp(java.lang.String speSettleTp) {
		this.speSettleTp = speSettleTp;
	}

	/**
	 * @return the speSettleLv
	 */
	public java.lang.String getSpeSettleLv() {
		return speSettleLv;
	}

	/**
	 * @param speSettleLv the speSettleLv to set
	 */
	public void setSpeSettleLv(java.lang.String speSettleLv) {
		this.speSettleLv = speSettleLv;
	}

	/**
	 * @return the speSettleDs
	 */
	public java.lang.String getSpeSettleDs() {
		return speSettleDs;
	}

	/**
	 * @param speSettleDs the speSettleDs to set
	 */
	public void setSpeSettleDs(java.lang.String speSettleDs) {
		this.speSettleDs = speSettleDs;
	}

	/**
	 * @return the feeBackFlg
	 */
	public java.lang.String getFeeBackFlg() {
		return feeBackFlg;
	}

	/**
	 * @param feeBackFlg the feeBackFlg to set
	 */
	public void setFeeBackFlg(java.lang.String feeBackFlg) {
		this.feeBackFlg = feeBackFlg;
	}
	
	private String idOtherNo;
	private String feeTypeFlag;
	private String feeSelfGDName;
	private String feeSelfBLName;
	private String feeMost;
	private String feeLeast;

	private String algo2List;
	
	public String getAlgo2List() {
		return algo2List;
	}

	public void setAlgo2List(String algo2List) {
		this.algo2List = algo2List;
	}

	public String getFeeSelfBLName() {
		return feeSelfBLName;
	}

	public void setFeeSelfBLName(String feeSelfBLName) {
		this.feeSelfBLName = feeSelfBLName;
	}

	public String getFeeMost() {
		return feeMost;
	}

	public void setFeeMost(String feeMost) {
		this.feeMost = feeMost;
	}

	public String getFeeLeast() {
		return feeLeast;
	}

	public void setFeeLeast(String feeLeast) {
		this.feeLeast = feeLeast;
	}

	public String getFeeSelfGDName() {
		return feeSelfGDName;
	}

	public void setFeeSelfGDName(String feeSelfGDName) {
		this.feeSelfGDName = feeSelfGDName;
	}

	public String getFeeTypeFlag() {
		return feeTypeFlag;
	}

	public void setFeeTypeFlag(String feeTypeFlag) {
		this.feeTypeFlag = feeTypeFlag;
	}

	public void setIdOtherNo(String idOtherNo) {
		this.idOtherNo = idOtherNo;
	}
	
	public String getIdOtherNo() {
		return idOtherNo;
	}
	public String checkIds;
	/**
	 * @return the checkIds
	 */
	public String getCheckIds() {
		return checkIds;
	}

	/**
	 * @param checkIds the checkIds to set
	 */
	public void setCheckIds(String checkIds) {
		this.checkIds = checkIds;
	}

	/**
	 * @return the tmpNo
	 */
	public String getTmpNo() {
		return tmpNo;
	}

	/**
	 * @param tmpNo the tmpNo to set
	 */
	public void setTmpNo(String tmpNo) {
		this.tmpNo = tmpNo;
	}
	
	/**
	 * 省代码
	 */
	private String province;
	/**
	 * 市代码
	 */
	private String city;
	/**
	 * 所属部门
	 */
	private String depart;
	/**
	 * 套用商户类型原因
	 */
	private String mchtTypeCause;
	/**
	 * 国家代码
	 */
	private String countryCode;
	/**
	 * 经营范围
	 */
	 private String busScope;
	 /**
	  * 主营业务
	  */
	 private String busMain;
	 /**
	  * 联系人座机号
	  */
	 private String linkTel;
	 /**
	  * 财务负责人
	  */
	 private String finManager;
	 /**
	  * 财务人手机号
	  */
	 private String finPhone;
	 /**
	  * 财务座机号
	  */
	 private String finTel;
	 /**
	  * 财务传真
	  */
	 private String finFax;
	 /**
	  * 财务电子邮箱
	  */
	 private String finEmail;
	 /**
	  * 直营连锁店数量
	  */
	 private String shopNum;
	 /**
	  * 经营地段
	  */
	 private String busArea;
	 /**
	  * 经营区域
	  */
	 private String busZone;
	 /**
	  * 营业面积
	  */
	 private String acreage;
	 /**
	  * 营业用地性质
	  */
	 private String areaType;
	 /**
	  * 员工人数
	  */
	 private String empNum;
	 /**
	  * 收银员数量
	  */
	 private String cashierNum;
	 /**
	  * 收银台数量
	  */
	 private String cashierDeskNum;
	 /**
	  * 前一年营业额
	  */
	 private String turnoverBefore;
	 /**
	  * 年营业额
	  */
	 private String turnover;
	 /**
	  * 开业日期
	  */
	 private String openDate;
	 /**
	  * 是否为洒店行业
	  */
	 private String isWineshop; 
	 /**
	  * 酒店等级
	  */
	 private String wineshopLvl;
	 /**
	  * 是否多个收单行
	  */
	 private String isMoreAcq;
	 /**
	  * 是否申请外卡
	  */
	 private String isAppOutSide; 
	 /**
	  * 是否有内卡受理经验
	  */
	 private String hasInnerPosExp;
	 /**
	  * 服务模式
	  */
	 private String serType;
	 /**
	  * 服务级别
	  */
	 private String serLvl;
	 /**
	 * @return the serLvl
	 */
	public String getSerLvl() {
		return serLvl;
	}
	/**
	 * @param serLvl the serLvl to set
	 */
	public void setSerLvl(String serLvl) {
		this.serLvl = serLvl;
	}
	/**
	  * 商户拓展渠道
	  */
	 private String src;
	 /**
	  * 推荐人
	  */
	 private String referrer; 
	 /**
	  * 原商户号
	  */
	 private String mchtNoOld;
	 /**
	  * mcc2
	  */
	 private String mcc2;
	 /**
	  * 代理方名称
	  */
	 private String proxy;
	 /**
	  * 代理方联系方式
	  */
	 private String proxyTel;
	 
	 /**
	  * 拓展人
	  */
	 private String expander;
	 /**
	  * 区/县
	  */
	 private String area;
	 /**
	  * 是否有POS外卡受理经验
	  */
	 private String hasOurPosExp;	 
	 
	 private String nationality;
	 
	 
	/**
	 * @return the nationality
	 */
	public String getNationality() {
		return nationality;
	}

	/**
	 * @param nationality the nationality to set
	 */
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	/**
	 * @return the hasOurPosExp
	 */
	public String getHasOurPosExp() {
		return hasOurPosExp;
	}
	/**
	 * @param hasOurPosExp the hasOurPosExp to set
	 */
	public void setHasOurPosExp(String hasOurPosExp) {
		this.hasOurPosExp = hasOurPosExp;
	}
	/**
	 * @return the expander
	 */
	public String getExpander() {
		return expander;
	}
	/**
	 * @param expander the expander to set
	 */
	public void setExpander(String expander) {
		this.expander = expander;
	}
	/**
	 * @return the area
	 */
	public String getArea() {
		return area;
	}
	/**
	 * @param area the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}
	/**
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}
	/**
	 * @param province the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the depart
	 */
	public String getDepart() {
		return depart;
	}
	/**
	 * @param depart the depart to set
	 */
	public void setDepart(String depart) {
		this.depart = depart;
	}
	/**
	 * @return the mchtTypeCause
	 */
	public String getMchtTypeCause() {
		return mchtTypeCause;
	}
	/**
	 * @param mchtTypeCause the mchtTypeCause to set
	 */
	public void setMchtTypeCause(String mchtTypeCause) {
		this.mchtTypeCause = mchtTypeCause;
	}
	/**
	 * @return the countryCode
	 */
	public String getCountryCode() {
		return countryCode;
	}
	/**
	 * @param countryCode the countryCode to set
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	/**
	 * @return the busScope
	 */
	public String getBusScope() {
		return busScope;
	}
	/**
	 * @param busScope the busScope to set
	 */
	public void setBusScope(String busScope) {
		this.busScope = busScope;
	}
	/**
	 * @return the busMain
	 */
	public String getBusMain() {
		return busMain;
	}
	/**
	 * @param busMain the busMain to set
	 */
	public void setBusMain(String busMain) {
		this.busMain = busMain;
	}
	/**
	 * @return the linkTel
	 */
	public String getLinkTel() {
		return linkTel;
	}
	/**
	 * @param linkTel the linkTel to set
	 */
	public void setLinkTel(String linkTel) {
		this.linkTel = linkTel;
	}
	/**
	 * @return the finManager
	 */
	public String getFinManager() {
		return finManager;
	}
	/**
	 * @param finManager the finManager to set
	 */
	public void setFinManager(String finManager) {
		this.finManager = finManager;
	}
	/**
	 * @return the finPhone
	 */
	public String getFinPhone() {
		return finPhone;
	}
	/**
	 * @param finPhone the finPhone to set
	 */
	public void setFinPhone(String finPhone) {
		this.finPhone = finPhone;
	}
	/**
	 * @return the finTel
	 */
	public String getFinTel() {
		return finTel;
	}
	/**
	 * @param finTel the finTel to set
	 */
	public void setFinTel(String finTel) {
		this.finTel = finTel;
	}
	/**
	 * @return the finFax
	 */
	public String getFinFax() {
		return finFax;
	}
	/**
	 * @param finFax the finFax to set
	 */
	public void setFinFax(String finFax) {
		this.finFax = finFax;
	}
	/**
	 * @return the finEmail
	 */
	public String getFinEmail() {
		return finEmail;
	}
	/**
	 * @param finEmail the finEmail to set
	 */
	public void setFinEmail(String finEmail) {
		this.finEmail = finEmail;
	}
	/**
	 * @return the shopNum
	 */
	public String getShopNum() {
		return shopNum;
	}
	/**
	 * @param shopNum the shopNum to set
	 */
	public void setShopNum(String shopNum) {
		this.shopNum = shopNum;
	}
	/**
	 * @return the busArea
	 */
	public String getBusArea() {
		return busArea;
	}
	/**
	 * @param busArea the busArea to set
	 */
	public void setBusArea(String busArea) {
		this.busArea = busArea;
	}
	/**
	 * @return the busZone
	 */
	public String getBusZone() {
		return busZone;
	}
	/**
	 * @param busZone the busZone to set
	 */
	public void setBusZone(String busZone) {
		this.busZone = busZone;
	}
	/**
	 * @return the acreage
	 */
	public String getAcreage() {
		return acreage;
	}
	/**
	 * @param acreage the acreage to set
	 */
	public void setAcreage(String acreage) {
		this.acreage = acreage;
	}
	/**
	 * @return the areaType
	 */
	public String getAreaType() {
		return areaType;
	}
	/**
	 * @param areaType the areaType to set
	 */
	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}
	/**
	 * @return the empNum
	 */
	public String getEmpNum() {
		return empNum;
	}
	/**
	 * @param empNum the empNum to set
	 */
	public void setEmpNum(String empNum) {
		this.empNum = empNum;
	}
	/**
	 * @return the cashierNum
	 */
	public String getCashierNum() {
		return cashierNum;
	}
	/**
	 * @param cashierNum the cashierNum to set
	 */
	public void setCashierNum(String cashierNum) {
		this.cashierNum = cashierNum;
	}
	/**
	 * @return the cashierDeskNum
	 */
	public String getCashierDeskNum() {
		return cashierDeskNum;
	}
	/**
	 * @param cashierDeskNum the cashierDeskNum to set
	 */
	public void setCashierDeskNum(String cashierDeskNum) {
		this.cashierDeskNum = cashierDeskNum;
	}
	/**
	 * @return the turnoverBefore
	 */
	public String getTurnoverBefore() {
		return turnoverBefore;
	}
	/**
	 * @param turnoverBefore the turnoverBefore to set
	 */
	public void setTurnoverBefore(String turnoverBefore) {
		this.turnoverBefore = turnoverBefore;
	}
	/**
	 * @return the turnover
	 */
	public String getTurnover() {
		return turnover;
	}
	/**
	 * @param turnover the turnover to set
	 */
	public void setTurnover(String turnover) {
		this.turnover = turnover;
	}
	/**
	 * @return the openDate
	 */
	public String getOpenDate() {
		return openDate;
	}
	/**
	 * @param openDate the openDate to set
	 */
	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}
	/**
	 * @return the isWineshop
	 */
	public String getIsWineshop() {
		return isWineshop;
	}
	/**
	 * @param isWineshop the isWineshop to set
	 */
	public void setIsWineshop(String isWineshop) {
		this.isWineshop = isWineshop;
	}
	/**
	 * @return the wineshopLvl
	 */
	public String getWineshopLvl() {
		return wineshopLvl;
	}
	/**
	 * @param wineshopLvl the wineshopLvl to set
	 */
	public void setWineshopLvl(String wineshopLvl) {
		this.wineshopLvl = wineshopLvl;
	}
	/**
	 * @return the isMoreAcq
	 */
	public String getIsMoreAcq() {
		return isMoreAcq;
	}
	/**
	 * @param isMoreAcq the isMoreAcq to set
	 */
	public void setIsMoreAcq(String isMoreAcq) {
		this.isMoreAcq = isMoreAcq;
	}
	/**
	 * @return the isAppOutSide
	 */
	public String getIsAppOutSide() {
		return isAppOutSide;
	}
	/**
	 * @param isAppOutSide the isAppOutSide to set
	 */
	public void setIsAppOutSide(String isAppOutSide) {
		this.isAppOutSide = isAppOutSide;
	}
	/**
	 * @return the hasInnerPosExp
	 */
	public String getHasInnerPosExp() {
		return hasInnerPosExp;
	}
	/**
	 * @param hasInnerPosExp the hasInnerPosExp to set
	 */
	public void setHasInnerPosExp(String hasInnerPosExp) {
		this.hasInnerPosExp = hasInnerPosExp;
	}
	/**
	 * @return the serType
	 */
	public String getSerType() {
		return serType;
	}
	/**
	 * @param serType the serType to set
	 */
	public void setSerType(String serType) {
		this.serType = serType;
	}
	/**
	 * @return the src
	 */
	public String getSrc() {
		return src;
	}
	/**
	 * @param src the src to set
	 */
	public void setSrc(String src) {
		this.src = src;
	}
	/**
	 * @return the referrer
	 */
	public String getReferrer() {
		return referrer;
	}
	/**
	 * @param referrer the referrer to set
	 */
	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}
	/**
	 * @return the mchtNoOld
	 */
	public String getMchtNoOld() {
		return mchtNoOld;
	}
	/**
	 * @param mchtNoOld the mchtNoOld to set
	 */
	public void setMchtNoOld(String mchtNoOld) {
		this.mchtNoOld = mchtNoOld;
	}
	/**
	 * @return the mcc2
	 */
	public String getMcc2() {
		return mcc2;
	}
	/**
	 * @param mcc2 the mcc2 to set
	 */
	public void setMcc2(String mcc2) {
		this.mcc2 = mcc2;
	}
	/**
	 * @return the proxy
	 */
	public String getProxy() {
		return proxy;
	}
	/**
	 * @param proxy the proxy to set
	 */
	public void setProxy(String proxy) {
		this.proxy = proxy;
	}
	/**
	 * @return the proxyTel
	 */
	public String getProxyTel() {
		return proxyTel;
	}
	/**
	 * @param proxyTel the proxyTel to set
	 */
	public void setProxyTel(String proxyTel) {
		this.proxyTel = proxyTel;
	}
	
	
	/*********************************************************************************************************/
	//20140729 商户改造 BY 张骏恺

	
	//经营范围
	private String busiRange;
	
	//邮政编号
	private String postalCode;
	
	//装机地址
	private String insAddr;
	
	//所属行业
	private String belInd;
	
	//经营场所权属
	private String ownerBusi;
	
	//ICP备案号
	private String ICPrecordNo;
	
	//ICP备案公司名称
	private String ICPcompName;
	
	//合同邮寄地址
	private String mailAddr;
	
	//商户签约费率
	private String contRate;
	
	//客户申请单笔借记卡交易金额
	private String sinDebAmount;
	
	//客户申请单日借记卡交易金额
	private String dayDebAmount;
	
	//客户申请单月借记卡交易金额
	private String monDebAmount;
	
	//客户申请单笔贷记卡交易金额
	private String sinCreAmount;
	
	//客户申请单日贷记卡交易金额
	private String dayCreAmount;
	
	//客户申请单月贷记卡交易金额
	private String monCreAmount;
	
	//商户调单联系人名称
	private String contName;
	
	//商户调单联系人电话
	private String contTel;
	
	//商户电话
	private String busiTel;
	
	//申请人留言
	private String appComm;
	
	//代理商名称
	private String agentName;
	
	private String agentNo;
	
	//代理商类型
	private String agentType;
	
	//所属大区
	private String regionBel;
	
	//**********************************************************************
	
	//预授权功能
	private String preAuthor;
	
	//退货功能
	private String returnFunc;
	
	
	//************************************************************************************
	//商户当前使用账号
	private String currAccount;
	
	//商户法人账号开户行机构代码
	private String bankAccountCode;
	
	//法人账号开户行名称
	private String corpBankName;
	
	//商户法人账号开户名
	private String legalAccountName;
	
	//商户法人账号
	private String corpAccount;
	
	//商户公司帐号开户行机构代码
	private String compAccountBankCode;
	
	//商户公司帐号开户行名称
	private String compAccountBankName;
	
	//商户公司账号开户名
	private String compAccountName;
	
	//商户公司账号
	private String compAccount;
	
	//定向委托帐号帐号开户行机构代码
	private String dirBankCode;
	
	//定向委托帐号开户行名称
	private String dirBankName;
	
	//定向委托帐号开户名
	private String dirAccountName;
	
	//定向委托账号
	private String dirAccount;
	
	//定向委托付款标志
	private String dirFlag;
	
	//自动提现标志
	private String autoFlag;
	
	//节假日提现标志
	private String holidaySetFlag;
	
	//信用卡刷卡功能标志
	private String creFlag;
	
	//退货返还手续费标志
	private String returnFeeFlag;
	
	private String legalGender;
	private String legalAddr;
	private String legalProfession;
	

	public String getLegalGender() {
		return legalGender;
	}

	public void setLegalGender(String legalGender) {
		this.legalGender = legalGender;
	}

	public String getLegalAddr() {
		return legalAddr;
	}

	public void setLegalAddr(String legalAddr) {
		this.legalAddr = legalAddr;
	}

	public String getLegalProfession() {
		return legalProfession;
	}

	public void setLegalProfession(String legalProfession) {
		this.legalProfession = legalProfession;
	}

	public String getReturnFeeFlag() {
		return returnFeeFlag;
	}

	public void setReturnFeeFlag(String returnFeeFlag) {
		this.returnFeeFlag = returnFeeFlag;
	}

	public String getBusiRange() {
		return busiRange;
	}

	public void setBusiRange(String busiRange) {
		this.busiRange = busiRange;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getInsAddr() {
		return insAddr;
	}

	public void setInsAddr(String insAddr) {
		this.insAddr = insAddr;
	}

	public String getBelInd() {
		return belInd;
	}

	public void setBelInd(String belInd) {
		this.belInd = belInd;
	}

	public String getOwnerBusi() {
		return ownerBusi;
	}

	public void setOwnerBusi(String ownerBusi) {
		this.ownerBusi = ownerBusi;
	}

	public String getICPrecordNo() {
		return ICPrecordNo;
	}

	public void setICPrecordNo(String iCPrecordNo) {
		ICPrecordNo = iCPrecordNo;
	}

	public String getICPcompName() {
		return ICPcompName;
	}

	public void setICPcompName(String iCPcompName) {
		ICPcompName = iCPcompName;
	}

	public String getMailAddr() {
		return mailAddr;
	}

	public void setMailAddr(String mailAddr) {
		this.mailAddr = mailAddr;
	}

	public String getContRate() {
		return contRate;
	}

	public void setContRate(String contRate) {
		this.contRate = contRate;
	}

	public String getSinDebAmount() {
		return sinDebAmount;
	}

	public void setSinDebAmount(String sinDebAmount) {
		this.sinDebAmount = sinDebAmount;
	}

	public String getDayDebAmount() {
		return dayDebAmount;
	}

	public void setDayDebAmount(String dayDebAmount) {
		this.dayDebAmount = dayDebAmount;
	}

	public String getMonDebAmount() {
		return monDebAmount;
	}

	public void setMonDebAmount(String monDebAmount) {
		this.monDebAmount = monDebAmount;
	}

	public String getSinCreAmount() {
		return sinCreAmount;
	}

	public void setSinCreAmount(String sinCreAmount) {
		this.sinCreAmount = sinCreAmount;
	}

	public String getDayCreAmount() {
		return dayCreAmount;
	}

	public void setDayCreAmount(String dayCreAmount) {
		this.dayCreAmount = dayCreAmount;
	}

	public String getMonCreAmount() {
		return monCreAmount;
	}

	public void setMonCreAmount(String monCreAmount) {
		this.monCreAmount = monCreAmount;
	}

	public String getContName() {
		return contName;
	}

	public void setContName(String contName) {
		this.contName = contName;
	}

	public String getContTel() {
		return contTel;
	}

	public void setContTel(String contTel) {
		this.contTel = contTel;
	}

	public String getBusiTel() {
		return busiTel;
	}

	public void setBusiTel(String busiTel) {
		this.busiTel = busiTel;
	}

	public String getAppComm() {
		return appComm;
	}

	public void setAppComm(String appComm) {
		this.appComm = appComm;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getAgentType() {
		return agentType;
	}

	public void setAgentType(String agentType) {
		this.agentType = agentType;
	}

	public String getRegionBel() {
		return regionBel;
	}

	public void setRegionBel(String regionBel) {
		this.regionBel = regionBel;
	}

	public String getPreAuthor() {
		return preAuthor;
	}

	public void setPreAuthor(String preAuthor) {
		this.preAuthor = preAuthor;
	}

	public String getReturnFunc() {
		return returnFunc;
	}

	public void setReturnFunc(String returnFunc) {
		this.returnFunc = returnFunc;
	}

	public String getCurrAccount() {
		return currAccount;
	}

	public void setCurrAccount(String currAccount) {
		this.currAccount = currAccount;
	}

	public String getBankAccountCode() {
		return bankAccountCode;
	}

	public void setBankAccountCode(String bankAccountCode) {
		this.bankAccountCode = bankAccountCode;
	}

	public String getCorpBankName() {
		return corpBankName;
	}

	public void setCorpBankName(String corpBankName) {
		this.corpBankName = corpBankName;
	}

	public String getLegalAccountName() {
		return legalAccountName;
	}

	public void setLegalAccountName(String legalAccountName) {
		this.legalAccountName = legalAccountName;
	}

	public String getCorpAccount() {
		return corpAccount;
	}

	public void setCorpAccount(String corpAccount) {
		this.corpAccount = corpAccount;
	}

	public String getCompAccountBankCode() {
		return compAccountBankCode;
	}

	public void setCompAccountBankCode(String compAccountBankCode) {
		this.compAccountBankCode = compAccountBankCode;
	}

	public String getCompAccountBankName() {
		return compAccountBankName;
	}

	public void setCompAccountBankName(String compAccountBankName) {
		this.compAccountBankName = compAccountBankName;
	}

	public String getCompAccountName() {
		return compAccountName;
	}

	public void setCompAccountName(String compAccountName) {
		this.compAccountName = compAccountName;
	}

	public String getCompAccount() {
		return compAccount;
	}

	public void setCompAccount(String compAccount) {
		this.compAccount = compAccount;
	}

	public String getDirBankCode() {
		return dirBankCode;
	}

	public void setDirBankCode(String dirBankCode) {
		this.dirBankCode = dirBankCode;
	}

	public String getDirBankName() {
		return dirBankName;
	}

	public void setDirBankName(String dirBankName) {
		this.dirBankName = dirBankName;
	}

	public String getDirAccountName() {
		return dirAccountName;
	}

	public void setDirAccountName(String dirAccountName) {
		this.dirAccountName = dirAccountName;
	}

	public String getDirAccount() {
		return dirAccount;
	}

	public void setDirAccount(String dirAccount) {
		this.dirAccount = dirAccount;
	}

	public String getDirFlag() {
		return dirFlag;
	}

	public void setDirFlag(String dirFlag) {
		this.dirFlag = dirFlag;
	}

	public String getAutoFlag() {
		return autoFlag;
	}

	public void setAutoFlag(String autoFlag) {
		this.autoFlag = autoFlag;
	}

	public String getHolidaySetFlag() {
		return holidaySetFlag;
	}

	public void setHolidaySetFlag(String holidaySetFlag) {
		this.holidaySetFlag = holidaySetFlag;
	}

	public String getCreFlag() {
		return creFlag;
	}

	public void setCreFlag(String creFlag) {
		this.creFlag = creFlag;
	}
	
	
	
	private java.lang.String termId;
	public java.lang.String getTermId() {
		return termId;
	}

	public void setTermId(java.lang.String termId) {
		this.termId = termId;
	}
	
	
	private String singleAmt; //单笔消费金额
	private String monthTotalAmt;    //月消费总额
	public String getSingleAmt() {
		return singleAmt;
	}

	public void setSingleAmt(String singleAmt) {
		this.singleAmt = singleAmt;
	}

	public String getMonthTotalAmt() {
		return monthTotalAmt;
	}

	public void setMonthTotalAmt(String monthTotalAmt) {
		this.monthTotalAmt = monthTotalAmt;
	}

	public String getAgentNo() {
		return agentNo;
	}

	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}

	public java.lang.String getMchtNoInput() {
		return mchtNoInput;
	}

	public void setMchtNoInput(java.lang.String mchtNoInput) {
		this.mchtNoInput = mchtNoInput;
	}
	
	
	
}
