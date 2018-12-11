/* @(#)
 *
 * Project:PFConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ --------- ---------------------------------------------------
 *   Gavin        2011-6-27       first release
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
package com.huateng.struts.mchnt.action;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import tools.SFTPClientProvider;

import com.huateng.bo.impl.mchnt.TblMchntService;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.common.TblMchntInfoConstants;
import com.huateng.po.mchnt.TblMchtBaseBusiRange;
import com.huateng.po.mchnt.TblMchtBaseInfTmp;
import com.huateng.po.mchnt.TblMchtSettleInfPK;
import com.huateng.po.mchnt.TblMchtSettleInfTmp;
import com.huateng.po.mchnt.TblMchtSupp1Tmp;
import com.huateng.struts.system.action.BaseSupport;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.BlackListAndRiskGradeConfig;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.FileFilter;
import com.huateng.system.util.SysParamUtil;
import com.huateng.system.util.TarBuilder;
import com.jcraft.jsch.ChannelSftp;
import com.alibaba.fastjson.JSONObject;

/**
 * Title:
 *
 * File: T20100Action.java
 *
 * Description:
 *
 * Copyright: Copyright (c) 2011-6-27
 *
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 *
 * @author Gavin
 *
 * @version 1.0
 */
public class T20100Action extends BaseSupport{

	private static final long serialVersionUID = 1L;
	// 文件集合
	private List<File> files;
	// 文件名称集合
	private List<String> filesFileName;
	private static String blockListURL;
	private static String blackResult;
	
	//受益人编号
	private String beneficiaryId;
	//受益人有效期限
	private String beneficiaryExpiration;

	
	public String getBeneficiaryId() {
		return beneficiaryId;
	}

	public void setBeneficiaryId(String beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}

	public String getBeneficiaryExpiration() {
		return beneficiaryExpiration;
	}

	public void setBeneficiaryExpiration(String beneficiaryExpiration) {
		this.beneficiaryExpiration = beneficiaryExpiration;
	}

	public List<String> getFilesFileName() {
		return filesFileName;
	}

	public void setFilesFileName(List<String> filesFileName) {
		this.filesFileName = filesFileName;
	}
	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

	/**
	 * 添加商户信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String add() {
		try {
			//检查手费续是否已经设置
			if(StringUtil.isEmpty(tmpNo)){
				return returnService("20100.07");
			}
			
			System.out.println("统一社会信用代码证有效期uscCodeDate：" + uscCodeDate);
			System.out.println("营业执照有效日期licenceNoDate：" + licenceNoDate);
			System.out.println("控股股东或实际控制人姓名shareholder：" + shareholder);
			System.out.println("控股股东或实际控制人身份证件种类idshareholderTpmd：" + idshareholderTpmd);
			System.out.println("控股股东或实际控制人身份证件号码idshareholder：" + idshareholder);
			System.out.println("控股股东或实际控制人身份证件有效期shareholderDate：" + shareholderDate);
			System.out.println("法定代表人（负责人）身份证件有效期identityNoDate：" + identityNoDate);
			System.out.println("授权人证件类型contactmd：" + contactmd);
			System.out.println("授权人身份证号有效日期linkTelDate：" + linkTelDate);
			
			
			/*blockListURL=BlackListAndRiskGradeConfig.getBlackListRUL();
			// 黑名单校验----只做黑名单
	        Map<String, String> parameters = new HashMap<String,String>();
			if(!StringUtil.isEmpty(mchtNm)) {
				System.out.println("法定代表人/负责人manager："+mchtNm);
				parameters.put("CName",mchtNm);   //中文或者英文名
			}	
			if(!StringUtil.isEmpty(foreignName)) {
				System.out.println("3位字母国家代码foreignName："+foreignName);
				parameters.put("Country", foreignName);   //证件号码
			}*/
			/*if(!StringUtil.isEmpty(nationality)) {
				System.out.println("法定代表人/负责人国籍nationality"+nationality);
				parameters.put("Country", nationality);  //国籍
			}*/
			/*if(!StringUtil.isEmpty(legalGender)) {
				System.out.println("法定代表人性别legalGender"+legalGender);
				parameters.put("Gender", legalGender);  //性别
			}*/
			//parameters.put("Birthday", "");  //出生日期/注册日期
//			blackResult =sendGet(blockListURL,parameters);
//			String resultNum = analysisBlackListResult(blackResult);    
					
			//	        返回状态	Result	Y	Int	
			//			1匹配上黑名单
			//	        2匹配上灰名单
			//	        3匹配上白名单
			//	        0匹配无结果
			//	        -1匹配校验或计算出错
			
			//???请完善
			/*if(resultNum.equals("1")){
				log("匹配上黑名单");
				return returnService("匹配上黑名单");
			}else if(resultNum.equals("2")) {
				log("匹配上灰名单");
				return returnService("匹配上灰名单");
			}else if(resultNum.equals("3")) {
				log("匹配上白名单");
	//			return returnService("匹配上白名单");
			}else if(resultNum.equals("0")) {
				log("匹配无结果");
	//			return returnService("匹配无结果");
			}else if(resultNum.equals("-1")) {
				log("匹配校验或计算出错");;
				return returnService("匹配校验或计算出错");
			}*/
	
	
			/*String unionBrhId = "";
			try {
				unionBrhId = SysParamUtil.getParam("UNION_BRH_ID").trim();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return returnService("获取系统参数出错！");
			}
			String noBeg = unionBrhId+areaNo.trim()+mcc.trim();	//3位机构号+商户所在地区+mcc
			String no = service.getMchtNo(noBeg);
			if("-1".equals(no)){
				return returnService("15位商户号已达到上限！");
			}
			mchtNoInPut = noBeg + no;
			System.out.println("15位商户号："+ mchtNoInPut);*/
			//检查15商户编号是否已存在
			log("添加商户商户号："+mchtNoInPut);
			String mchtNoSql="select count(*) from TBL_MCHT_BASE_INF_TMP where  mcht_no='"+mchtNoInPut+"' and mcht_status<>'8'";
			String result= CommonFunction.getCommQueryDAO().findCountBySQLQuery(mchtNoSql);
			if(!result.equals("0")){
				return returnService("20100.01");
			}
			/*//检查6位商户号是否已存在
			String mchtNoHxSql="select count(*) from TBL_MCHT_BASE_INF_TMP where  mcht_no_hx='"+mchtNoByInPut.trim()+"' and mcht_status<>'8'";;
			result=CommonFunction.getCommQueryDAO().findCountBySQLQuery(mchtNoHxSql);
			if(!result.equals("0")){
				return returnService("20100.02");
			}*/
			/*//事前风控表中去查询商户风险等级：商户名+营业执照+组织机构代码+法人身份证
			String riskLvlSql = "select grade from RISK_BEFORE where mcht_nm='" + mchtNm.trim() + "'" +
			//20120823修改为法人身份证不区分大小写
				" and LICENSE_NO='" + licenceNo.trim() + "' and upper(IDENTITY)='" + identityNo.trim().toUpperCase()+ "'";
			//20120727修改：组织机构代码改为可选项,新增商户时填写组织机构代码的情况
			if(this.bankLicenceNo!=null && !"".equals(this.bankLicenceNo.trim()))
				riskLvlSql += " and ORG_CODE='" + bankLicenceNo.trim()  + "'";
			//20121102修改：事前风险中填写了组织机构代码但新增商户时并未填写组织机构代码的情况，新增商户不成功给出错误提示
			if(this.bankLicenceNo==null || "".equals(this.bankLicenceNo.trim())){
				riskLvlSql += " and ORG_CODE is NULL ";
			}
			rislLvl = commQueryDAO.findCountBySQLQuery(riskLvlSql);
			if(StringUtil.isEmpty(rislLvl)){//商户事前风险评级不存在则添加失败
				return returnService("20100.06");
			}
			//获取商户事前风险等级相关信息
			String sqlRisk = "select * from cst_sys_param a where a.KEY=" + rislLvl + " and OWNER='RISKLEVEL'";
			List<CstSysParam> CstSysParamList=(List<CstSysParam>)commQueryDAO.findBySQLQuery6(sqlRisk);
			CstSysParam cstSysParam=CstSysParamList.get(0);
			String gradename=cstSysParam.getValue();
	//		String des=cstSysParam.getReserve();
			
			//检验营业执照，税务登记证， 法人身份证，商户账户账号是否已经存在
			String checkSql = "select count(B.MCHT_NO) from TBL_MCHT_BASE_INF_TMP B,TBL_MCHT_SETTLE_INF_TMP S " +
					"where TRIM(S.SETTLE_ACCT)= '" + settleAcct + "' and "+
					//20120823修改为法人身份证不区分大小写
					" upper(IDENTITY_NO)='" + identityNo.trim().toUpperCase() + "' And TRIM(LICENCE_NO)='" + licenceNo + "' AND B.MCHT_NO=S.MCHT_NO " ;
			//20120727税务登记证修改为可选项
			if(this.faxNo!=null && !"".equals(this.faxNo))
				checkSql += " AND TRIM(FAX_NO) = '" + faxNo +"'";
			result = CommonFunction.getCommQueryDAO().findCountBySQLQuery(checkSql);
			if(!result.equals("0")){
				return returnService("20100.03");
			}*/
			//需要检查
			if (!StringUtil.isNull(checkIds) && checkIds.equals("T")) {
				//首先检验营业执照，税务登记证， 法人身份证，商户账户账号是否已经存在licenceNo faxNo  identityNo settleAcct
				//商户账户账号
				String sql = "select B.MCHT_NO,trim(MCHT_NM),TRIM(LICENCE_NO),TRIM(FAX_NO),TRIM(IDENTITY_NO)," +
						"trim(BANK_LICENCE_NO),substr(TRIM(SETTLE_ACCT),2) " +
					"from TBL_MCHT_BASE_INF_TMP B,TBL_MCHT_SETTLE_INF_TMP S where " +
					//"(substr(TRIM(SETTLE_ACCT),2) = '" + settleAcct + "' OR " + 
					"(TRIM(SETTLE_ACCT)= '" + settleAcct + "' OR " +
					//20120823修改为法人身份证不区分大小写
					" upper(IDENTITY_NO) = '" + identityNo.trim().toUpperCase() + "' OR " + 
					" TRIM(LICENCE_NO) = '" + licenceNo + "'";
				//20120727税务登记证修改为可选项
				if(this.faxNo!=null && !"".equals(this.faxNo))
					sql += " OR TRIM(FAX_NO) = '" + faxNo + "'";
				//20121109组织机构代码在新增商户时有填写，则需要加入判断条件中查看是否有重复
				if(this.bankLicenceNo!=null && !"".equals(this.bankLicenceNo))
					sql += " OR TRIM(BANK_LICENCE_NO) = '" + bankLicenceNo + "'";
				sql += ") AND B.MCHT_NO = S.MCHT_NO AND TRIM(ACQ_INST_ID) = '" + acqInstId.trim() + "'";
				
				List list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
				if (null != list && !list.isEmpty() && list.size() > 0) {
					Object[] obj = (Object[]) list.get(0);
					String reMsg = "CZ存在商户[<font color='red'>" + obj[0] + "-" + obj[1] + "</font>]等" 
						+ String.valueOf(list.size()) + "家商户与该商户的";
					if (licenceNo.trim().equals(obj[2])) {
						reMsg += "<font color='green'>营业执照编号</font>";
					} else if (faxNo!=null && faxNo.trim().equals(obj[3])) {//20120727税务登记证号码已修改为可选项
						reMsg += "<font color='green'>税务登记证号码</font>";
					} else if (identityNo.trim().equals(obj[4])) {
						reMsg += "<font color='green'>法人证件编号</font>";
					} else if (bankLicenceNo!=null && bankLicenceNo.trim().equals(obj[5])){//20121109
						reMsg += "<font color='green'>组织机构代码</font>";
					} else if (settleAcct!=null && settleAcct.trim().equals(obj[6])) {
						reMsg += "<font color='green'>商户收入账户号</font>";
					} else {
						reMsg += "<font color='green'>部分关键信息</font>";
					}
					reMsg += "相同,请核实相关商户录入信息.";
					
					return returnService(reMsg);
				}
			}
			
			mchtNo=mchtNoInPut;
			mchtNoHx=mchtNoByInPut.trim();
			TblMchtBaseInfTmp tblMchtBaseInfTmp = buildTmpMchtBaseInfo();
			tblMchtBaseInfTmp.setReserved(idOtherNo);
			
			System.out.println("rislLvl用户级别："+rislLvl);
			tblMchtBaseInfTmp.setOrganizationType(organizationType);
			tblMchtBaseInfTmp.setIsSyncretic(isSyncretic);
			
			tblMchtBaseInfTmp.setRislLvl(rislLvl);//风险级别  //by mike
			tblMchtBaseInfTmp.setMchtLvl("0");//商户类别   (默认值为0)  //by mike
			tblMchtBaseInfTmp.setConnType("0");//商户类型   (默认值为0)  //by mike
			tblMchtBaseInfTmp.setSaAction("0");//受控处理动作
	
			tblMchtBaseInfTmp.setCupMchtFlg("1");//银联卡受控标志
			tblMchtBaseInfTmp.setDebMchtFlg("1");//借记卡受理标志
			tblMchtBaseInfTmp.setCreMchtFlg("1");//贷记卡受理标志
			tblMchtBaseInfTmp.setCdcMchtFlg("1");//一帐通受理标志
			tblMchtBaseInfTmp.setForeignName(foreignName);//外籍名
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
			tblMchtBaseInfTmp.setLegalAddr(legalAddr);
			tblMchtBaseInfTmp.setLegalProfession(legalProfession);
			
			
			//企业规模
			tblMchtBaseInfTmp.setEtpsScale(etpsScale);
			
			
			if(StringUtil.isEmpty(faxNo)||faxNo.equalsIgnoreCase("")){
				tblMchtBaseInfTmp.setFaxNo("               ");  //税务登记证号码
			}else{
				tblMchtBaseInfTmp.setFaxNo(faxNo);  //税务登记证号码
			}
			tblMchtBaseInfTmp.setUscCode(uscCode);//统一社会信用代码
			tblMchtBaseInfTmp.setManagerTel(managerTel);//法人电话
	
			if(StringUtil.isNull(openTime) || "请选择".equals(openTime)) {
				tblMchtBaseInfTmp.setOpenTime("00:00");
			}
			
			if(StringUtil.isNull(closeTime) || "请选择".equals(closeTime)) {
				tblMchtBaseInfTmp.setCloseTime("23:59");
			}
			
			TblMchtSettleInfTmp tblMchtSettleInfTmp = buildTmpMchtSettleInfo();
			if (StringUtil.isNull(openStlno)) {
				tblMchtSettleInfTmp.setOpenStlno(" ");
			}
			/*if (StringUtil.isNull(settleBankNo)) {
				tblMchtSettleInfTmp.setSettleBankNo(" ");
			}*/
			if (StringUtil.isNull(changeStlno)) {
				tblMchtSettleInfTmp.setChangeStlno(" ");
			}
			if (StringUtil.isNull(compAccountBankCode)) {
				tblMchtSettleInfTmp.setCompAccountBankCode("111111");
			}
			if (StringUtil.isNull(dirBankCode)) {
				tblMchtSettleInfTmp.setDirBankCode("111111");
			}
			if (StringUtil.isNull(bankAccountCode)) {
				tblMchtSettleInfTmp.setBankAccountCode("111111");
			}
			tblMchtBaseInfTmp.setOpenTime(openTime);
			tblMchtBaseInfTmp.setCloseTime(closeTime);
			tblMchtSettleInfTmp.setFeeDiv1("1");
			tblMchtSettleInfTmp.setFeeDiv2("2");
			tblMchtSettleInfTmp.setFeeDiv3("3");
			//LHF
			tblMchtSettleInfTmp.setDirOpenBank(dir_open_bank);
			tblMchtSettleInfTmp.setDirBankProvince(dir_bank_province);
			tblMchtSettleInfTmp.setDirBankCity(dir_bank_city);
			tblMchtSettleInfTmp.setCompOpenBank(comp_open_bank);
			tblMchtSettleInfTmp.setCompBankProvince(comp_bank_province);
			tblMchtSettleInfTmp.setCompBankCity(comp_bank_city);
			tblMchtSettleInfTmp.setCorpOpenBank(corp_open_bank);
			tblMchtSettleInfTmp.setCorpBankProvince(corp_bank_province);
			tblMchtSettleInfTmp.setCorpBankCity(corp_bank_city);
			
			//商户补充信息
			TblMchtSupp1Tmp supp1Tmp=buildTmpMchtSupp1Tmp();
			
			//商户经营范围
			TblMchtBaseBusiRange tblMchtBaseBusiRange = new TblMchtBaseBusiRange();
			//生成随机数
			Random random = new Random();
			String mchtNoRandom = mchtNo + random.nextInt(999999);
			
			tblMchtBaseBusiRange.setBusiRangeId(mchtNoRandom);
			tblMchtBaseBusiRange.setMchtNo(mchtNo);
			tblMchtBaseBusiRange.setBusiRanges(busiRanges);
			
			//营业范围
			tblMchtBaseInfTmp.setBusiRange(mchtNoRandom);
	
			rspCode = service.saveTmp(tblMchtBaseInfTmp, tblMchtSettleInfTmp,supp1Tmp ,tblMchtBaseBusiRange);
			
			//文件改名
			if (!StringUtil.isNull(hasUpload) && !"0".equals(hasUpload)) {
				String basePath = SysParamUtil.getParam(SysParamConstants.FILE_UPLOAD_DISK);
				basePath = basePath.replace("\\", "/");
				File fl = new File(basePath);
				FileFilter filter = new FileFilter(imagesId);
				File[] files = fl.listFiles(filter);
				for (File file : files) {
					file.renameTo(new File(basePath + file.getName().replaceAll(imagesId, mchtNo)));
				}
			}
			//更新tbl_his_disc_algo2_tmp表中商户编号
			String sqlAlgo2="update tbl_his_disc_algo2_tmp set mcht_no='"+mchtNo+"' where tmp_No='"+tmpNo+"'";
			commQueryDAO.excute(sqlAlgo2);
	
			if (Constants.SUCCESS_CODE.equals(rspCode)) {
				return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + "新增商户信息成功,商户编号[" + mchtNo + "]");
			} else {
				return returnService(rspCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode,e);
		}
	}
	
	private TblMchtSupp1Tmp buildTmpMchtSupp1Tmp() throws InvocationTargetException, IllegalAccessException{
		TblMchtSupp1Tmp suppTmp=new TblMchtSupp1Tmp();
		BeanUtils.copyProperties(suppTmp, this);
		suppTmp.setPreAuthor(isChecked(preAuthor));
		suppTmp.setReturnFunc(isChecked(returnFunc));
		suppTmp.setIsWineshop(isChecked(isWineshop));
		suppTmp.setIsAppOutSide(isChecked(isAppOutSide));
//		suppTmp.setIsMoreAcq(isChecked(isMoreAcq));   //是否多个收单行
		suppTmp.setHasInnerPosExp(isChecked(hasInnerPosExp));
		suppTmp.setHasOurPosExp(isChecked(hasOurPosExp));
		return suppTmp;
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
		id.setTermId(CommonFunction.fillString("*",' ',8,true));//默认全部终端
		tblMchtSettleInfTmp.setId(id);

		//tblMchtSettleInfTmp.setSettleAcct(clearType + settleAcct);
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
		
		System.out.println("法人账户名称---"+legalNam+"====公司账户名称====="+companyNam);
		
		tblMchtSettleInfTmp.setLegalNam(legalNam);//法人账户名称
		tblMchtSettleInfTmp.setCompanyNam(companyNam);//公司账户名称
		
		
		//是否收支2条线
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
		tblMchtSettleInfTmp.setSettleBankNo(bankAccountCode);
		tblMchtSettleInfTmp.setSettleBankNm(corpBankName);
		
		//公司账户取商户名
		tblMchtSettleInfTmp.setSettleAcctNm(mchtNm);
		
		//法人账户取法人姓名
		tblMchtSettleInfTmp.setFeeAcctNm(manager);

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
	private TblMchtBaseInfTmp buildTmpMchtBaseInfo() throws IllegalAccessException, InvocationTargetException {
		TblMchtBaseInfTmp tblMchntInfoTmp = new TblMchtBaseInfTmp();
		BeanUtils.copyProperties(tblMchntInfoTmp, this);
		//非银行版本，因为有的方法用BANK_NO,有的用ACQ_INS_ID，在添加时两个设为一样
		tblMchntInfoTmp.setBankNo(tblMchntInfoTmp.getAcqInstId());
		
	
		tblMchntInfoTmp.setLicenceEndDate(licenceEndDate);
		tblMchntInfoTmp.setMchtNo(mchtNo);
		tblMchntInfoTmp.setAreaNo(areaNo);
		tblMchntInfoTmp.setMchtNoHx(mchtNoHx);
		tblMchntInfoTmp.setBankNo(acqInstId);
		tblMchntInfoTmp.setMchtStatus(TblMchntInfoConstants.MCHNT_ST_NEW_UNCK);
		//是否集团商户
		if(!StringUtil.isNull(mchtGroupFlag)
				&& TblMchntInfoConstants.EXTJS_CHECKED.equalsIgnoreCase(mchtGroupFlag)){
			tblMchntInfoTmp.setMchtGroupFlag("1");
		}else{
			tblMchntInfoTmp.setMchtGroupFlag("0");
		}
		//是否MIS商户
		if(!StringUtil.isNull(mchtMngMode)
				&& TblMchntInfoConstants.EXTJS_CHECKED.equalsIgnoreCase(mchtMngMode)){
			tblMchntInfoTmp.setMchtMngMode("1");
		}else{
			tblMchntInfoTmp.setMchtMngMode("0");
		}
		//是否支持无磁无密交易
		if (!StringUtil.isNull(passFlag)
				&& TblMchntInfoConstants.EXTJS_CHECKED.equalsIgnoreCase(passFlag)) {
			tblMchntInfoTmp.setPassFlag("1");
		} else {
			tblMchntInfoTmp.setPassFlag("0");
		}
		//是否支持人工授权交易
		if (!StringUtil.isNull(manuAuthFlag)
				&& TblMchntInfoConstants.EXTJS_CHECKED.equalsIgnoreCase(manuAuthFlag)) {
			tblMchntInfoTmp.setManuAuthFlag("1");
		} else {
			tblMchntInfoTmp.setManuAuthFlag("0");
		}
		//是否支持折扣消费
		if (!StringUtil.isNull(discConsFlg)
				&& TblMchntInfoConstants.EXTJS_CHECKED.equalsIgnoreCase(discConsFlg)) {
			tblMchntInfoTmp.setDiscConsFlg("1");
		} else {
			tblMchntInfoTmp.setDiscConsFlg("0");
		}
		
		tblMchntInfoTmp.setRouteFlag("1");//路由默认开通

		//申请日期
		tblMchntInfoTmp.setApplyDate(applyDate);
		tblMchntInfoTmp.setEndDate(endDate);//到期日期

		// 记录修改时间
		tblMchntInfoTmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
		// 记录创建时间
		tblMchntInfoTmp.setRecCrtTs(CommonFunction.getCurrentDateTime());
		// 记录修改人
		tblMchntInfoTmp.setUpdOprId("");
		// 记录创建人
		tblMchntInfoTmp.setCrtOprId(getOperator().getOprId());
		
		//记录商户索引（商户新增审核退回修改时，商户号可能改变，但是该字段不变，可以判断是否为同一个商户，暂时记录商户号，录入后不再改变）
		tblMchntInfoTmp.setMchntInd(mchtNo);
		return tblMchntInfoTmp;

	}

	/**
	 * 受益人信息文件上传
	 * @return
	 */
	public String upload1(){
		//创建ftp连接
		SFTPClientProvider provider = new SFTPClientProvider();
		ChannelSftp sftp = provider.connect(SysParamUtil.getParam("FTPSERVERIP"), Integer.parseInt(SysParamUtil.getParam("FTPPORT")), SysParamUtil.getParam("FTPUSERNAME"), SysParamUtil.getParam("FTPPASSWORD"));
		try {
			String fileName = "";
			int fileNameIndex = 0;
			//获取服务器上传路径/home/apache01/mchntFile/
			//pospManager路径:/home/pospManager/mchntFile/
			String filePath = SysParamUtil.getParam(SysParamConstants.FILE_UPLOAD_DISK);
			//追加文件夹方便进行解压缩
			filePath += beneficiaryId + "_" + beneficiaryExpiration + "/";
			filePath = filePath.replace("\\", "/");
			File filePathFile = new File(filePath);
			//生成随机数
			Random random = new Random();
			for (File file : files) {
				//获取文件名
				fileName = filesFileName.get(fileNameIndex);
				log("路径文件名:" + filePath + fileName);
				//如果文件夹不存在则创建
				if(!filePathFile.exists() && !filePathFile.isDirectory()){
					filePathFile.mkdirs();
				}
				String fileType = "";
				//获取文件后缀名
				if (fileName.indexOf(".") != -1) {
					fileType = fileName.substring(fileName.lastIndexOf("."));
				}
				
				if(fileName.indexOf(".jpg")!=-1 || fileName.indexOf(".png")!=-1 
						|| fileName.indexOf(".jpeg")!=-1){
					boolean flag = provider.uploadNm(filePath, file,imagesId + random.nextInt(999999) + fileType, sftp);
					System.out.println(flag);
				}else{
					boolean flag = provider.uploadNm(filePath, file, fileName, sftp);
					System.out.println(flag);
				}
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return returnService("对不起，本次操作失败!",e);
		} finally {
			provider.disconnect(sftp);
		}
		
		return returnService(Constants.SUCCESS_CODE);
	}
	
	/**
	 * 受益人文件下载
	 * @return
	 */
	public String download(){
		
		try {
			//获取文件上传的路径便于从此下载文件
			String filePath1 = SysParamUtil.getParam(SysParamConstants.FILE_UPLOAD_DISK);
			String filePath = filePath1 + beneficiaryId + "_" + beneficiaryExpiration + "/";
			filePath = filePath.replace("\\", "/");
			
			File basePath2File = new File(filePath);
			if  (!basePath2File.exists()  && !basePath2File.isDirectory()) {     
				return returnService("该包未上传！");
			}
			String tarName = "BENEFICIARY_" + beneficiaryId + "_" + beneficiaryExpiration + ".tar";
			try {
				TarBuilder tarBuilder = new TarBuilder(filePath, filePath1,tarName,false);
				tarBuilder.build();
				System.out.println("打包成功！");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return returnService("打包失败！");
			}
			log("上传服务器tar包路径+名称:" + filePath + tarName);
			
			log("GET FILE:" + filePath);
			//创建ftp连接
			SFTPClientProvider provider = new SFTPClientProvider();
			ChannelSftp sftp = provider.connect(SysParamUtil.getParam("FTPSERVERIP"), Integer.parseInt(SysParamUtil.getParam("FTPPORT")), SysParamUtil.getParam("FTPUSERNAME"), SysParamUtil.getParam("FTPPASSWORD"));
			try {
				boolean flag = provider.download(filePath1, filePath1 + tarName, tarName, sftp);
				if(flag){
					return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + tarName);
				}else{
					return returnService("您所请求的受益人文件不存在!");
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return returnService("SFTP 到报表服务器异常");
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return returnService("对不起，本次操作失败!",e);
		}
		
	}
	
	public String upload(){
		/*String TMSPath = SysParamUtil.getParam(SysParamConstants.FILE_UPLOAD_DISK);
		FileInputStream is = null;
		DataOutputStream out = null;
		DataInputStream dis = null;
		try {
			String fileName = "";
			int fileNameIndex = 0;

			String basePath = TMSPath;
			System.out.println("imgFile："+ imgFile);
			System.out.println("files："+ files);
			System.out.println("imagesId："+ imagesId);
			
			for (File file : imgFile) {
				is = new FileInputStream(file);
				fileName = filesFileName.get(fileNameIndex);
				String fileType = "";
				if (fileName.indexOf(".") != -1) {
					fileType = fileName.substring(fileName.lastIndexOf("."));
				}

				File writeFile = new File(basePath
						+ fileName.substring(0, fileName.lastIndexOf("."))
						+ "_" + imagesId + fileType);

				if (!writeFile.getParentFile().exists()) {
					writeFile.getParentFile().mkdirs();
				}
				if (writeFile.exists()) {
					writeFile.delete();
				}
				dis = new DataInputStream(is);
				out = new DataOutputStream(new FileOutputStream(writeFile));

				int temp;
				while ((temp = dis.read()) != -1) {
					out.write(temp);
				}

				out.flush();
				out.close();
				dis.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
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
		return Constants.SUCCESS_CODE_CUSTOMIZE + filesFileName.get(kFileName.size() - 1);*/
		
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
				//获取文件后缀名
				if (fileName.indexOf(".") != -1) {
					fileType = fileName.substring(fileName.lastIndexOf("."));
				}
				File writeFile = new File(basePath + imagesId + random.nextInt(999999) + fileType);
				if (!writeFile.getParentFile().exists()) {
					writeFile.getParentFile().mkdirs();
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
	
	//分析黑名单返回结果
    private String analysisBlackListResult(String blackResult) throws Exception {
    	if(blackResult==null||blackResult.trim().equals("")) {
    		log("返回为空!");
			throw new Exception("返回为空!");
		}
    	try {
    	    JSONObject object=JSONObject.parseObject(blackResult);
            String result =object.getString("result");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            log(e.getMessage());
            throw new Exception("解析黑名单返回结果失败！");
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
                            java.net.URLEncoder.encode(parameters.get(name),  
                                    "utf-8"));  
                }  
                params = sb.toString();  
            } else {  
                for (String name : parameters.keySet()) { 
                        if(parameters.get(name)!=null) {
                    sb.append(name).append("=").append(  
                            java.net.URLEncoder.encode(parameters.get(name),  
                                    "utf-8")).append("&");  
                        }
                }  
                String temp_params = sb.toString();  
                params = temp_params.substring(0, temp_params.length() - 1);  
            }  
            String full_url = url + "?" + params;  
            log("请求地址&参数："+full_url);  
            // 创建URL对象  
            java.net.URL connURL = new java.net.URL(full_url);  
            // 打开URL连接  
            java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL  
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

	// 文件集合
	private List<File> imgFile;
	// 文件名称集合
	private List<String> imgFileFileName;
	private String imagesId;
	private String hasUpload;

	/**
	 * @return the hasUpload
	 */
	public String getHasUpload() {
		return hasUpload;
	}

	/**
	 * @param hasUpload the hasUpload to set
	 */
	public void setHasUpload(String hasUpload) {
		this.hasUpload = hasUpload;
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

	@Override
	public String getMsg() {
		return msg;
	}

	@Override
	public boolean isSuccess() {
		return success;
	}

	public String isChecked(String param)
	{
		return param==null?"0":"1";
	}
	// primary key
	private java.lang.String mchtNo;
	

	// fields
	private java.lang.String mchtNoHx;
	private java.lang.String mchtNm;
	private java.lang.String rislLvl;
	private java.lang.String organizationType;
	private java.lang.String isSyncretic;
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
	private java.lang.String mchtEnAbbr;
	private java.lang.String areaNo;
	private java.lang.String settleAreaNo;
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
	private java.lang.String uscCode;//统一社会信用代码
	private java.lang.String busAmt;
	private java.lang.String mchtCreLvl;
	private java.lang.String contact;
	private java.lang.String postCode;
	private java.lang.String commEmail;
	private java.lang.String commMobil;
	private java.lang.String commTel;
	private java.lang.String legalNam;
	private java.lang.String companyNam;
	private java.lang.String manager;
	private java.lang.String artifCertifTp;
	private java.lang.String identityNo;
	private java.lang.String managerTel;
	private java.lang.String fax;
	private java.lang.String electrofax;
	private java.lang.String regAddr;
	private java.lang.String applyDate;
	private java.lang.String endDate;
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

	private java.lang.String speSettleTp;
	private java.lang.String speSettleLv;
	private java.lang.String speSettleDs;
	private java.lang.String feeBackFlg;
	
	private String mchtNoByInPut;
	private String mchtNoInPut; 
	private String engNameAbbr; 
	
	
	//ADD 20160304
	private String dir_open_bank;
	private String dir_bank_province;
	private String dir_bank_city;
	private String comp_open_bank;
	private String comp_bank_province;
	private String comp_bank_city;
	private String corp_open_bank;
	private String corp_bank_province;
	private String corp_bank_city;
	
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
	
	private String legalGender;
	private String legalAddr;
	private String legalProfession;
	private String foreignName;
	
	
	//企业规模
	private String etpsScale;
	
	
	public String getEtpsScale() {
		return etpsScale;
	}

	public void setEtpsScale(String etpsScale) {
		this.etpsScale = etpsScale;
	}

	public String getForeignName() {
		return foreignName;
	}

	public void setForeignName(String foreignName) {
		this.foreignName = foreignName;
	}

	//经营地址
	private String operateAddr;
	
	
	public String getOperateAddr() {
		return operateAddr;
	}

	public void setOperateAddr(String operateAddr) {
		this.operateAddr = operateAddr;
	}

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

	public String getComp_open_bank() {
		return comp_open_bank;
	}

	public void setComp_open_bank(String comp_open_bank) {
		this.comp_open_bank = comp_open_bank;
	}

	public String getComp_bank_province() {
		return comp_bank_province;
	}

	public void setComp_bank_province(String comp_bank_province) {
		this.comp_bank_province = comp_bank_province;
	}

	public String getComp_bank_city() {
		return comp_bank_city;
	}

	public void setComp_bank_city(String comp_bank_city) {
		this.comp_bank_city = comp_bank_city;
	}

	public String getCorp_open_bank() {
		return corp_open_bank;
	}

	public void setCorp_open_bank(String corp_open_bank) {
		this.corp_open_bank = corp_open_bank;
	}

	public String getCorp_bank_province() {
		return corp_bank_province;
	}

	public void setCorp_bank_province(String corp_bank_province) {
		this.corp_bank_province = corp_bank_province;
	}

	public String getCorp_bank_city() {
		return corp_bank_city;
	}

	public void setCorp_bank_city(String corp_bank_city) {
		this.corp_bank_city = corp_bank_city;
	}

	public String getDir_open_bank() {
		return dir_open_bank;
	}

	public void setDir_open_bank(String dir_open_bank) {
		this.dir_open_bank = dir_open_bank;
	}

	public String getDir_bank_province() {
		return dir_bank_province;
	}

	public void setDir_bank_province(String dir_bank_province) {
		this.dir_bank_province = dir_bank_province;
	}

	public String getDir_bank_city() {
		return dir_bank_city;
	}

	public void setDir_bank_city(String dir_bank_city) {
		this.dir_bank_city = dir_bank_city;
	}

	/**
	 * @return the engNameAbbr
	 */
	public String getEngNameAbbr() {
		return engNameAbbr;
	}

	/**
	 * @param engNameAbbr the engNameAbbr to set
	 */
	public void setEngNameAbbr(String engNameAbbr) {
		this.engNameAbbr = engNameAbbr;
	}

	public String getMchtNoInPut() {
		return mchtNoInPut;
	}

	public void setMchtNoInPut(String mchtNoInPut) {
		this.mchtNoInPut = mchtNoInPut;
	}

	public String getMchtNoByInPut() {
		return mchtNoByInPut;
	}

	public void setMchtNoByInPut(String mchtNoByInPut) {
		this.mchtNoByInPut = mchtNoByInPut;
	}

	public TblMchntService getService() {
		return service;
	}

	public void setService(TblMchntService service) {
		this.service = service;
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

	public java.lang.String getIsSyncretic() {
		return isSyncretic;
	}

	public void setIsSyncretic(java.lang.String isSyncretic) {
		this.isSyncretic = isSyncretic;
	}

	public java.lang.String getOrganizationType() {
		return organizationType;
	}

	public void setOrganizationType(java.lang.String organizationType) {
		this.organizationType = organizationType;
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

	public java.lang.String getAreaNo() {
		return areaNo;
	}

	public void setAreaNo(java.lang.String areaNo) {
		this.areaNo = areaNo;
	}

	public java.lang.String getSettleAreaNo() {
		return settleAreaNo;
	}

	public void setSettleAreaNo(java.lang.String settleAreaNo) {
		this.settleAreaNo = settleAreaNo;
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

	public java.lang.String getUscCode() {
		return uscCode;
	}

	public void setUscCode(java.lang.String uscCode) {
		this.uscCode = uscCode;
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
	
	public java.lang.String getEndDate() {
		return endDate;
	}

	public void setEndDate(java.lang.String endDate) {
		this.endDate = endDate;
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
	
	 

	//计费代码
	private String discCode;

	public String getDiscCode() {
		return discCode;
	}

	public void setDiscCode(String discCode) {
		this.discCode = discCode;
	}

	private String mchtNoBySelf;
	private String idOtherNo;
	private String feeTypeFlag;
	private String feeSelfGDName;
	private String feeSelfBLName;
	private String feeMost;
	private String feeLeast;
	private String clearType;

	private String tmpNo;
	
	public String getTmpNo() {
		return tmpNo;
	}

	public void setTmpNo(String tmpNo) {
		this.tmpNo = tmpNo;
	}

	public String getClearType() {
		return clearType;
	}

	public void setClearType(String clearType) {
		this.clearType = clearType;
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
	public String getMchtNoBySelf() {
		return mchtNoBySelf;
	}

	public void setMchtNoBySelf(String mchtNoBySelf) {
		this.mchtNoBySelf = mchtNoBySelf;
	}

	public java.lang.String getSpeSettleTp() {
		return speSettleTp;
	}

	public void setSpeSettleTp(java.lang.String speSettleTp) {
		this.speSettleTp = speSettleTp;
	}

	public java.lang.String getSpeSettleLv() {
		return speSettleLv;
	}

	public void setSpeSettleLv(java.lang.String speSettleLv) {
		this.speSettleLv = speSettleLv;
	}

	public java.lang.String getSpeSettleDs() {
		return speSettleDs;
	}

	public void setSpeSettleDs(java.lang.String speSettleDs) {
		this.speSettleDs = speSettleDs;
	}

	public java.lang.String getFeeBackFlg() {
		return feeBackFlg;
	}

	public void setFeeBackFlg(java.lang.String feeBackFlg) {
		this.feeBackFlg = feeBackFlg;
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

	public java.lang.String getMchtNoHx() {
		return mchtNoHx;
	}

	public void setMchtNoHx(java.lang.String mchtNoHx) {
		this.mchtNoHx = mchtNoHx;
	}
	//商户补充信息
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
	
	//商户简称
	private String mchtCNAbbr;
	
	
	//商户英文简称
	private String mchtENAbbr;
	
	//经营范围
	private String busiRanges;
	
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
	
	//代理商ID
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
	
//	//适用MCC
//	private String appMCC;
//	
//	//是否开通路由
//	private String openRoute;
//	
//	//商户单笔刷卡限额
//	private String sinLimit;
//	
//	//商户单日刷卡限额
//	private String dayLimit;
//	
//	//商户单月刷卡限额
//	private String monLimit;
//	
//	//商户开通是否加入黑名单
//	private String openBlackList;
	
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
	
	

	public String getReturnFeeFlag() {
		return returnFeeFlag;
	}

	public void setReturnFeeFlag(String returnFeeFlag) {
		this.returnFeeFlag = returnFeeFlag;
	}

	public String getMchtCNAbbr() {
		return mchtCNAbbr;
	}

	public void setMchtCNAbbr(String mchtCNAbbr) {
		this.mchtCNAbbr = mchtCNAbbr;
	}

	public String getMchtENAbbr() {
		return mchtENAbbr;
	}

	public void setMchtENAbbr(String mchtENAbbr) {
		this.mchtENAbbr = mchtENAbbr;
	}

	public String getBusiRanges() {
		return busiRanges;
	}

	public void setBusiRanges(String busiRanges) {
		this.busiRanges = busiRanges;
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
	
	
}

