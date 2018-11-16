/* @(#)
 *
 * Project:PFConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ --------- ---------------------------------------------------
 *   Gavin        2011-8-10       first release
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

import java.io.FileOutputStream;
import java.util.List;

import com.huateng.common.Constants;
import com.huateng.common.Operator;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.struts.system.action.ReportBaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.InformationUtil;
import com.huateng.system.util.SysParamUtil;

/**
 * Title: 
 * 
 * File: T20106Action.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-8-10
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author 
 * 
 * @version 1.0
 */
public class T20106Action extends ReportBaseAction{

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	protected void reportAction() throws Exception {
		reportType = "EXCEL";
		
		title = InformationUtil.createTitles("V_", 62);
		data = reportCreator.process(genSql(), title);
		
		
		
		String sqlMcc = "select mchnt_tp,mchnt_tp ||' - '||descr from tbl_inf_mchnt_tp";
		List<Object[]> mccName =CommonFunction.getCommQueryDAO().findBySQLQuery(sqlMcc);
		
		//商户性质
		String sql1 = "select trim(KEY),trim(KEY)||' - '||trim(VALUE) from CST_SYS_PARAM where OWNER='MCHNT_ATTR'";
		List<Object[]> etpsAttr =CommonFunction.getCommQueryDAO().findBySQLQuery(sql1);
		//法人国籍
		String sql2 = "SELECT KEY, KEY ||' - '||VALUE FROM CST_SYS_PARAM WHERE OWNER = 'COUNTRY'";
		List<Object[]> nationality =CommonFunction.getCommQueryDAO().findBySQLQuery(sql2);
		
		//法人/负责人证件类型
		String sql3 = "SELECT KEY, KEY ||' - '||VALUE FROM CST_SYS_PARAM WHERE OWNER = 'COUNTRY'";
		List<Object[]> artifCertifTp =CommonFunction.getCommQueryDAO().findBySQLQuery(sql3);
		
		//商户所属省
		String sql4 = "select trim(CITY_CODE),trim(CITY_CODE) ||' - '||trim(CITY_DES) from TBL_CITY_CODE where  substr(CITY_CODE,3,2)='00'";
		List<Object[]> province =CommonFunction.getCommQueryDAO().findBySQLQuery(sql4);
		
		//商户所属市
		String sql5 = "select trim(CITY_CODE),trim(CITY_CODE) ||' - '||trim(CITY_DES) from TBL_CITY_CODE where  ((substr(CITY_CODE,3,1)<>'0' and substr(CITY_CODE,4,1)='0') or (CITY_CODE IN (1000,1100,2900,6900)))";
		List<Object[]> city =CommonFunction.getCommQueryDAO().findBySQLQuery(sql5);
		
		//商户所属县
		String sql6 = "select trim(CITY_CODE),trim(CITY_CODE) ||' - '||trim(CITY_DES) from TBL_CITY_CODE where  substr(CITY_CODE,4,1)<>'0' ";
		List<Object[]> area =CommonFunction.getCommQueryDAO().findBySQLQuery(sql6);
		
		//经营场所权属
		String sql7 = "Select  KEY,VALUE  from cst_sys_param where OWNER = 'MANAGEOWNER' ";
		List<Object[]> ownerBusi =CommonFunction.getCommQueryDAO().findBySQLQuery(sql7);
		
		//代理商
		String sql8 = "select AGENT_NO, trim(AGENT_NO) ||' - '|| trim(AGENT_NM) from TBL_AGENT_INFO ";
		List<Object[]> agentNo =CommonFunction.getCommQueryDAO().findBySQLQuery(sql8);
		
		
		//商户组别
		String sql9 = "SELECT MCHNT_TP_GRP,MCHNT_TP_GRP ||' - '||DESCR  FROM TBL_INF_MCHNT_TP_GRP";
		List<Object[]> mchntTpGrp = CommonFunction.getCommQueryDAO().findBySQLQuery(sql9);
		
		//Mcc
		String sql10=" select mchnt_tp,mchnt_tp||'-'||descr from tbl_inf_mchnt_tp";
		List<Object[]> mchntTp = CommonFunction.getCommQueryDAO().findBySQLQuery(sql10);
		
		//计费算法
		String sql11 = " select DISC_CD, DISC_CD|| '-' ||DISC_NM from tbl_inf_disc_cd_temp";
		List<Object[]> discCd = CommonFunction.getCommQueryDAO().findBySQLQuery(sql11);
		
		for (int i = 0; i < data.length; i++) {
			//商户MCC
			if(data[i][2] != null && !"".equals(data[i][2].toString().trim())){
				if(mccName !=null && mccName.size() >0){
					for(int j = 0; j < mccName.size(); j++){
						if(data[i][2].toString().trim().equals(mccName.get(j)[0].toString().trim())){
							data[i][2] = mccName.get(j)[1].toString();
						}
					}
				}else{
					data[i][2] = data[i][2].toString().trim();
				}
			}else{
				data[i][2] = "";
			}
			//商户状态
			if(data[i][3] != null){
				String mchntSt = data[i][3].toString().trim();	
				if(mchntSt.equals("0")){
					data[i][3] = "正常";
				}else if(mchntSt.equals("1")){
					data[i][3] = "新增待审核";				
				}else if(mchntSt.equals("3")){
					data[i][3] = "修改待审核";				
				}else if(mchntSt.equals("5")){
					data[i][3] = "冻结待审核";				
				}else if(mchntSt.equals("6")){
					data[i][3] = "冻结";				
				}else if(mchntSt.equals("7")){
					data[i][3] = "恢复待审核";				
				}else if(mchntSt.equals("8")){
					data[i][3] = "注销";				
				}else if(mchntSt.equals("9")){
					data[i][3] = "注销待审核";				
				}else if(mchntSt.equals("H")){
					data[i][3] = "黑名单";				
				}else{
					data[i][3] = mchntSt;
				}
			}else{
				data[i][3] = " ";
			}
			//商户性质
			if(data[i][5] != null && !"".equals(data[i][5].toString().trim())){
				if(etpsAttr !=null && etpsAttr.size() >0){
					for(int j = 0; j < etpsAttr.size(); j++){
						if(data[i][5].toString().trim().equals(etpsAttr.get(j)[0].toString().trim())){
							data[i][5] = etpsAttr.get(j)[1].toString();
						}
					}
				}else{
					data[i][5] = data[i][5].toString().trim();
				}
			}else{
				data[i][5] = "";
			}
			
			//法人国籍
			if(data[i][15] != null && !"".equals(data[i][15].toString().trim())){
				if(nationality !=null && nationality.size() >0){
					for(int j = 0; j < nationality.size(); j++){
						if(data[i][15].toString().trim().equals(nationality.get(j)[0].toString().trim())){
							data[i][15] = nationality.get(j)[1].toString();
						}
					}
				}else{
					data[i][15] = data[i][15].toString().trim();
				}
			}else{
				data[i][15] = "";
			}
			
			//法人/负责人证件类型
			if(data[i][17] != null && !"".equals(data[i][17].toString().trim())){
				if(artifCertifTp !=null && artifCertifTp.size() >0){
					for(int j = 0; j < artifCertifTp.size(); j++){
						if(data[i][17].toString().trim().equals(artifCertifTp.get(j)[0].toString().trim())){
							data[i][17] = artifCertifTp.get(j)[1].toString();
						}
					}
				}else{
					data[i][17] = data[i][17].toString().trim();
				}
			}else{
				data[i][17] = "";
			}
			
			//商户所属省
			if(data[i][21] != null && !"".equals(data[i][21].toString().trim())){
				if(province !=null && province.size() >0){
					for(int j = 0; j < province.size(); j++){
						if(data[i][21].toString().trim().equals(province.get(j)[0].toString().trim())){
							data[i][21] = province.get(j)[1].toString();
						}
					}
				}else{
					data[i][21] = data[i][21].toString().trim();
				}
			}else{
				data[i][21] = "";
			}
			
			//商户所属市
			if(data[i][22] != null && !"".equals(data[i][22].toString().trim())){
				if(city !=null && city.size() >0){
					for(int j = 0; j < city.size(); j++){
						if(data[i][22].toString().trim().equals(city.get(j)[0].toString().trim())){
							data[i][22] = city.get(j)[1].toString();
						}
					}
				}else{
					data[i][22] = data[i][22].toString().trim();
				}
			}else{
				data[i][22] = "";
			}
			
			//商户所属县
			if(data[i][23] != null && !"".equals(data[i][23].toString().trim())){
				if(area !=null && area.size() >0){
					for(int j = 0; j < area.size(); j++){
						if(data[i][23].toString().trim().equals(area.get(j)[0].toString().trim())){
							data[i][23] = area.get(j)[1].toString();
						}
					}
				}else{
					data[i][23] = data[i][23].toString().trim();
				}
			}else{
				data[i][23] = "";
			}
			
			//经营场所权属
			if(data[i][25] != null && !"".equals(data[i][25].toString().trim())){
				if(ownerBusi !=null && ownerBusi.size() >0){
					for(int j = 0; j < ownerBusi.size(); j++){
						if(data[i][25].toString().trim().equals(ownerBusi.get(j)[0].toString().trim())){
							data[i][25] = ownerBusi.get(j)[1].toString();
						}
					}
				}else{
					data[i][25] = data[i][25].toString().trim();
				}
			}else{
				data[i][25] = "";
			}
			
			//预授权
			if(data[i][30] != null){
				
				String param1 = data[i][30].toString().trim();	
				if(param1.equals("0")){
					data[i][30] = "否";
				}else if(param1.equals("1")){
					data[i][30] = "是";				
				}else{
					data[i][30] = param1;
				}
			}else{
				data[i][30] = "否";
			}
			//退货功能
			if(data[i][31] != null){
				
				String param1 = data[i][31].toString().trim();	
				if(param1.equals("0")){
					data[i][31] = "否";
				}else if(param1.equals("1")){
					data[i][31] = "是";				
				}else{
					data[i][31] = param1;
				}
			}else{
				data[i][31] = "否";
			}
			
			//代理商
			if(data[i][37] != null && !"".equals(data[i][37].toString().trim())){
				if(agentNo !=null && agentNo.size() >0){
					for(int j = 0; j < agentNo.size(); j++){
						if(data[i][37].toString().trim().equals(agentNo.get(j)[0].toString().trim())){
							data[i][37] = agentNo.get(j)[1].toString();
						}
					}
				}else{
					data[i][37] = data[i][37].toString().trim();
				}
			}else{
				data[i][37] = "";
			}
			
			//是否有POS内卡受理经验
			if(data[i][38] != null){
				
				String param1 = data[i][38].toString().trim();	
				if(param1.equals("0")){
					data[i][38] = "否";
				}else if(param1.equals("1")){
					data[i][38] = "是";				
				}else{
					data[i][38] = param1;
				}
			}else{
				data[i][38] = "否";
			}
			
			//是否有POS外卡受理经验
			if(data[i][39] != null){
				
				String param1 = data[i][39].toString().trim();	
				if(param1.equals("0")){
					data[i][39] = "否";
				}else if(param1.equals("1")){
					data[i][39] = "是";				
				}else{
					data[i][39] = param1;
				}
			}else{
				data[i][39] = "否";
			}
			
			//是否申请外卡
			if(data[i][40] != null){
				
				String param1 = data[i][40].toString().trim();	
				if(param1.equals("0")){
					data[i][40] = "否";
				}else if(param1.equals("1")){
					data[i][40] = "是";				
				}else{
					data[i][40] = param1;
				}
			}else{
				data[i][40] = "否";
			}
			
			//结算账户类型
			if(data[i][42] != null){
				String param1 = data[i][42].toString().trim();	
				if(param1.equals("1")){
					data[i][42] = "对私账户";
				}else if(param1.equals("2")){
					data[i][42] = "对公账户";				
				}else if(param1.equals("3")){
					data[i][42] = "定向委托账户";				
				}
			}else{
				data[i][42] = " ";
			}
			
			//信用卡刷卡功能标志
			if(data[i][45] != null){
				
				String param1 = data[i][45].toString().trim();	
				if(param1.equals("0")){
					data[i][45] = "否";
				}else if(param1.equals("1")){
					data[i][45] = "是";				
				}else{
					data[i][45] = param1;
				}
			}else{
				data[i][45] = "否";
			}
			
			//节假日提现标志
			if(data[i][46] != null){
				
				String param1 = data[i][46].toString().trim();	
				if(param1.equals("0")){
					data[i][46] = "否";
				}else if(param1.equals("1")){
					data[i][46] = "是";				
				}else{
					data[i][46] = param1;
				}
			}else{
				data[i][46] = "否";
			}
			
			//自动提现标志
			if(data[i][47] != null){
				
				String param1 = data[i][47].toString().trim();	
				if(param1.equals("0")){
					data[i][47] = "否";
				}else if(param1.equals("1")){
					data[i][47] = "是";				
				}else{
					data[i][47] = param1;
				}
			}else{
				data[i][47] = "否";
			}
			
			//退货返还手续费标志
			if(data[i][48] != null){
				
				String param1 = data[i][48].toString().trim();	
				if(param1.equals("0")){
					data[i][48] = "否";
				}else if(param1.equals("1")){
					data[i][48] = "是";				
				}else{
					data[i][48] = param1;
				}
			}else{
				data[i][48] = "否";
			}
			
			
			//商户结算周期
			if(data[i][50] != null){
				String param1 = data[i][50].toString().trim();	
				if(param1.equals("0")){
					data[i][50] = "日结 ";
				}else if(param1.equals("1")){
					data[i][50] = "日结累计";				
				}else if(param1.equals("2")){
					data[i][50] = "月结单笔";				
				}else if(param1.equals("3")){
					data[i][50] = "月结包月";				
				}else if(param1.equals("4")){
					data[i][50] = "月金额结累计计算";				
				}
			}else{
				data[i][50] = " ";
			}
			//商户组别
			if(data[i][51] != null && !"".equals(data[i][51].toString().trim())){
				if(mchntTpGrp !=null && mchntTpGrp.size() >0){
					for(int j = 0; j < mchntTpGrp.size(); j++){
						if(data[i][51].toString().trim().equals(mchntTpGrp.get(j)[0].toString().trim())){
							data[i][51] = mchntTpGrp.get(j)[1].toString();
						}
					}
				}else{
					data[i][51] = data[i][51].toString().trim();
				}
			}else{
				data[i][51] = "";
			}
			
			//商户Mcc
			if(data[i][52] != null && !"".equals(data[i][52].toString().trim())){
				if(mchntTp !=null && mchntTp.size() >0){
					for(int j = 0; j < mchntTp.size(); j++){
						if(data[i][52].toString().trim().equals(mchntTp.get(j)[0].toString().trim())){
							data[i][52] = mchntTp.get(j)[1].toString();
						}
					}
				}else{
					data[i][52] = data[i][52].toString().trim();
				}
			}else{
				data[i][52] = "";
			}
			
			//计费算法
			if(data[i][53] != null && !"".equals(data[i][53].toString().trim())){
				if(discCd !=null && discCd.size() >0){
					for(int j = 0; j < discCd.size(); j++){
						if(data[i][53].toString().trim().equals(discCd.get(j)[0].toString().trim())){
							data[i][53] = discCd.get(j)[1].toString();
						}
					}
				}else{
					data[i][53] = data[i][53].toString().trim();
				}
			}else{
				data[i][53] = "";
			}
			
			//是否开通路由
			/*if(data[i][4] != null){
				String isRoute = data[i][4].toString().trim();	
				if(isRoute.equals("0")){
					data[i][4] = "否";
				}else if(isRoute.equals("1")){
					data[i][4] = "是";				
				}else{
					data[i][4] = isRoute;
				}
			}else{
				data[i][4] = " ";
			}	*/					
		}
		
		if(data.length == 0) {
			writeNoDataMsg("没有找到符合条件的记录");
			return;
		}
		
		reportModel.setData(data);
		reportModel.setTitle(title);
		
//		parameters.put("betdate", startDate + " - " + endDate);
		
		reportCreator.initReportData(getJasperInputSteam("T20106.jasper"), parameters, 
				reportModel.wrapReportDataSource(), getReportType());
		
		if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN20106RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN20106RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
		
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN20106RN"));
		
		writeUsefullMsg(fileName);
		return;
	}
	
	@Override
	protected String genSql() {
	//	String whereSql = " WHERE 1=1 and MCHT_STATUS<>'2' and MCHT_STATUS<>'1' ";  //1-新增待审核    2-新增审核退回
		String whereSql = " WHERE 1=1 ";
		if (!StringUtil.isNull("mchntId")&&!"".equals(mchntId)) {
			whereSql += " AND MCHT_NO = '" + mchntId
					+ "' ";
		}
		if (!StringUtil.isNull(mchtStatus)) {
			whereSql += " AND MCHT_STATUS = '"
					+ mchtStatus + "' ";
		}
		if (!StringUtil.isNull(mchtGrp)) {
			whereSql += " AND MCHT_GRP = '" + mchtGrp
					+ "' ";
		}
		if (!StringUtil.isNull(crtDate)&&!StringUtil.isNull(crtEndDate)) {
			whereSql += " AND SUBSTR(REC_CRT_TS,0,8) between "
					+ crtDate + " and "+ crtEndDate;
		}
		if (!StringUtil.isNull(updDate)&&!StringUtil.isNull(updEndDate)) {
			whereSql += " AND SUBSTR(REC_UPD_TS,0,8) between "
					+ updDate + " and "+updEndDate;
		}
		if (!StringUtil.isNull(brhId)) {
			whereSql += " AND ACQ_INST_ID = '" + brhId
					+ "' ";
		} else {
			whereSql += " AND ACQ_INST_ID IN " + operator.getBrhBelowId() + " ";
		}
		if(!StringUtil.isNull(province)){
			whereSql +=" AND MCHT_NO IN ( SELECT MCHT_NO FROM TBL_MCHT_SUPP1_TMP WHERE PROVINCE= '" 
				+ province+"')";
		}
		if(!StringUtil.isNull(city)){
			whereSql +=" AND MCHT_NO IN ( SELECT MCHT_NO FROM TBL_MCHT_SUPP1_TMP WHERE CITY= '" 
				+ city+"')";
		}
		if(!StringUtil.isNull(area)){
			whereSql +=" ND MCHT_NO IN ( SELECT MCHT_NO FROM TBL_MCHT_SUPP1_TMP WHERE AREA= '" 
				+ area+"')";
		}
		if(!StringUtil.isNull(expander)){
			whereSql +=" AND MCHT_NO IN ( SELECT MCHT_NO FROM TBL_MCHT_SUPP1_TMP WHERE EXPANDER like '%" 
				+ expander+"%')";
		}
		
		if (!StringUtil.isNull(etpsAttr)) {
			whereSql += " AND ETPS_ATTR = '" + etpsAttr
					+ "' ";
		}
		if(!StringUtil.isNull(discId)){
			whereSql +=" AND MCHT_NO IN ( SELECT MCHT_NO FROM TBL_HIS_DISC_ALGO2_TMP WHERE FEE_TYPE ='" 
				+ discId+"')";
		}
		if (!StringUtil.isNull(agentNo)) {
			whereSql += " AND AGENT_NO = '" + agentNo
					+ "' ";
		}
		

		StringBuffer sb = new StringBuffer();
//		sb.append("SELECT MCHT_NO,MCHT_NM,ENG_NAME,MCC,ROUTE_FLAG,LICENCE_NO,ADDR,POST_CODE,"
//				+ "COMM_EMAIL,MANAGER,CONTACT,COMM_TEL,APPLY_DATE,MCHT_STATUS,nvl(B.TERM_COUNT,0),CRT_OPR_ID,UPD_OPR_ID,"
//				+ "SUBSTR(REC_UPD_TS,1,8)||' '||SUBSTR(REC_UPD_TS,9,2)||':'||SUBSTR(REC_UPD_TS,11,2)||':'||SUBSTR(REC_UPD_TS,13,2) FROM "
//				+ "(SELECT * FROM TBL_MCHT_BASE_INF_TMP ");
		sb.append("SELECT A.MCHT_NO,"+   //1商户编号
				  "A.MCHT_NM,"+          //2商户名称
				  "A.MCC,"+               //3商户MCC
				  "A.MCHT_STATUS,"+        //4商户状态
				  "A.MCHT_CN_ABBR,"+        //5商户简称
				  "A.ETPS_ATTR,"+          //6商户性质
				  "A.REG_ADDR,"+          //7注册地址
				  "A.POSTAL_CODE,"+          //8邮政编码
				  "A.COMM_EMAIL,"+          //9电子邮件
				  "A.LICENCE_NO,"+         //10营业执照编号
				  "A.BUSI_RANGE,"+         //11经营范围
				  "C.BUS_MAIN,"+         //12主营业务
				  "A.BEL_IND,"+         //13所属行业
				  "A.LICENCE_END_DATE,"+         //14注册日期
				  "A.BUS_AMT,"+         //15注册日期
				  "C.NATIONALITY,"+         //16法人/负责人国籍
				  "A.MANAGER,"+         //17法人/负责人
				  "A.ARTIF_CERTIF_TP,"+         //18法人/负责人证件类型
				  "A.IDENTITY_NO,"+         //19法人/负责人证件号码
				  "A.CONTACT,"+            //20联系人/授权人姓名
				  "C.LINK_TEL,"+            //21授权人身份证号
				  "C.PROVINCE,"+            //22商户所属省
				  "C.CITY,"+            //23商户所属市
				  "C.AREA,"+            //24商户所属县
				  "C.ACREAGE,"+            //25营业面积/平方米
				  "A.OWNER_BUSI,"+            //26经营场所权属
				  "C.EMP_NUM,"+            //27员工人数
				  "A.CONT_NAME,"+            //28商户调单联系人名称
				  "A.CONT_TEL,"+            //29商户调单联系人电话
				  "A.MANAGER_TEL,"+            //30 法人电话
				  "C.PRE_AUTHOR,"+            //31 预授权功能
				  "C.RETURN_FUNC,"+            //32 退货功能
				  "A.NET_TEL,"+            //33 拓展人电话
				  "C.EXPANDER,"+            //34 拓展人
				  "A.OPEN_TIME,"+            //35 商户营业开始时间
				  "A.CLOSE_TIME,"+            //36 商户营业结束时间
				  "A.OPER_NM,"+            //37 所属业务人员 
				  "A.AGENT_NO,"+            //38 代理商
				  "C.HAS_INNER_POS_EXP,"+            //39 是否有POS内卡受理经验
				  "C.HAS_OUR_POS_EXP,"+            //40 是否有POS外卡受理经验
				  "C.IS_APP_OUTSIDE,"+           //41 是否申请外卡
				  "nvl(B.TERM_COUNT,0),"+           //42 终端数量
				  "D.SETTLE_RPT,"+           //43 结算账户类型
				  "D.COMP_ACCOUNT_BANK_NAME,"+           //44 商户公司账号开户行名称
				  "D.SETTLE_ACCT,"+           //45 商户公司账号
				  "D.CRE_FLAG,"+           //46 信用卡刷卡功能标志
				  "D.HOLIDAY_SET_FLAG,"+           //47 节假日提现标志
				  "D.AUTO_FLAG,"+           //48 自动提现标志
				  "D.RETURN_FEE_FLAG,"+           //49 退货返还手续费标志
				  "D.SETTLE_CHN,"+           //50 商户结算方式T+N
				  "D.SETTLE_TYPE,"+           //51 商户结算周期
				 
				  //10.28新增  by  shiyiwen
				  
                  "A.mcht_grp,"+   //52商户组别
                   "A.mcc,"+   //53商户mcc码
                   "E.fee_type,"+  //54计费代码(终端号为*)
				  
				 "d.comp_Account_Bank_Code,"+  //55商户公司账户开户行机构代码
								  
				 "D.corp_Bank_Name,"+   //56法人帐号开户行名称
				 "D.bank_Account_Code,"+   //57商户法人帐号开户行机构代码
				 "D.d.fee_Acct,"+    //58商户法人帐号
								 
				 "D.dir_Bank_Name,"+   //59定向委托账户开户行名称				  
				 "D.dir_Bank_Code,"+   //60定向委托账户开户行机构代码
				 "D.dir_Account_Name,"+  //61定向委托账户开户名
				 "D.dir_Account"+   //62定向委托帐号
				  
			//	  "A.ROUTE_FLAG,"+        //是否开通路由
				  
			//	  "A.ADDR,"+              //商户地址
			//	  "A.POST_CODE,"+          //邮编
				  
			//	  "A.COMM_TEL,"+           //联系人电话
			//	  "A.APPLY_DATE,"+         //注册日期
				 
		//		  "nvl(B.TERM_COUNT,0),"+   //终端数量
			//	  "A.CRT_OPR_ID,"+            //录入柜员
		//		  "A.UPD_OPR_ID,"+            //审核柜员
			//	  "SUBSTR(REC_UPD_TS,1,8)||' '||SUBSTR(REC_UPD_TS,9,2)||':'||SUBSTR(REC_UPD_TS,11,2)||':'||SUBSTR(REC_UPD_TS,13,2)"+  //最近更新时间
		//	      "A.REC_UPD_TS "+    
			      " FROM (SELECT * FROM TBL_MCHT_BASE_INF_TMP "
				);
		
		
		sb.append(whereSql);
		/*sb.append(") A "
				+ "left outer join "
				+ "(select MCHT_CD,count(1) AS TERM_COUNT from TBL_TERM_INF group by MCHT_CD) B "
				+ "ON (A.MCHT_NO = B.MCHT_CD) ORDER BY MCHT_NO");*/
		sb.append(") A "
				+ "left outer join "
				+ "(select MCHT_CD,count(1) AS TERM_COUNT from TBL_TERM_INF group by MCHT_CD) B ON (A.MCHT_NO = B.MCHT_CD) "
				+ "left join TBL_MCHT_SUPP1_TMP C on A.MCHT_NO = C.MCHT_NO "
				+ "left join TBL_MCHT_SETTLE_INF_TMP D on A.MCHT_NO = D.MCHT_NO AND trim(D.TERM_ID) = '*'"
				+ "left join  tbl_his_disc_algo2_tmp E on E.mcht_no=A.MCHT_NO and E.SA_SATUTE='2' and E.term_id='*'"
		        + " ORDER BY A.MCHT_NO");
		String sql = sb.toString();		
		
		return sql;
	}
	
	private String crtDate;
	private String updDate;
	private String crtEndDate;
	private String updEndDate;
	private String mchntId;
	private String mchtStatus;
	private String mchtGrp;
	private String brhId;
	private String province;
	private String city;
	private String area;
	
	private String expander;
	private String etpsAttr;
	private String discId;
	private String agentNo;

	public String getCrtEndDate() {
		return crtEndDate;
	}

	public void setCrtEndDate(String crtEndDate) {
		this.crtEndDate = crtEndDate;
	}

	public String getUpdEndDate() {
		return updEndDate;
	}

	public void setUpdEndDate(String updEndDate) {
		this.updEndDate = updEndDate;
	}

	public String getCrtDate() {
		return crtDate;
	}

	public void setCrtDate(String crtDate) {
		this.crtDate = crtDate;
	}

	public String getUpdDate() {
		return updDate;
	}

	public void setUpdDate(String updDate) {
		this.updDate = updDate;
	}

	public String getMchntId() {
		return mchntId;
	}

	public void setMchntId(String mchntId) {
		this.mchntId = mchntId;
	}

	public String getMchtStatus() {
		return mchtStatus;
	}

	public void setMchtStatus(String mchtStatus) {
		this.mchtStatus = mchtStatus;
	}

	public String getMchtGrp() {
		return mchtGrp;
	}

	public void setMchtGrp(String mchtGrp) {
		this.mchtGrp = mchtGrp;
	}

	public String getBrhId() {
		return brhId;
	}

	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getExpander() {
		return expander;
	}

	public void setExpander(String expander) {
		this.expander = expander;
	}

	public String getEtpsAttr() {
		return etpsAttr;
	}

	public void setEtpsAttr(String etpsAttr) {
		this.etpsAttr = etpsAttr;
	}

	public String getDiscId() {
		return discId;
	}

	public void setDiscId(String discId) {
		this.discId = discId;
	}

	public String getAgentNo() {
		return agentNo;
	}

	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}

	
}
