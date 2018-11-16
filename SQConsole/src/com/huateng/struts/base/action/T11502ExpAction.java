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
package com.huateng.struts.base.action;

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


public class T11502ExpAction extends ReportBaseAction{


	protected void reportAction() throws Exception {
		reportType = "EXCEL";
		
		title = InformationUtil.createTitles("V_", 7);
		data = reportCreator.process(genSql(), title);
		
		String sqlMcc = "select key,value  from cst_sys_param where owner like 'DIVIDE%' ";
		List<Object[]> value =CommonFunction.getCommQueryDAO().findBySQLQuery(sqlMcc);
		
		for (int i = 0; i < data.length; i++) {
			if(data[i][1] != null && !"".equals(data[i][1].toString().trim())){//MCC
				if(value !=null && value.size() >0){
					for(int j = 0; j < value.size(); j++){
						if(data[i][1].toString().trim().equals(value.get(j)[0].toString().trim())){
							data[i][1] = value.get(j)[1].toString();
						}
					}
				}else{
					data[i][1] = data[i][1].toString().trim();
				}
			}else{
				data[i][1] = "";
			}						
		}
		
		
		String discCd = "select disc_cd,disc_nm from tbl_inf_disc_cd"; 
		List<Object[]> discNm =CommonFunction.getCommQueryDAO().findBySQLQuery(discCd);
		for (int i = 0; i < data.length; i++) {
			if(data[i][2] != null && !"".equals(data[i][2].toString().trim())){//MCC
				if(discNm !=null && discNm.size() >0){
					for(int j = 0; j < discNm.size(); j++){
						if(data[i][2].toString().trim().equals(discNm.get(j)[0].toString().trim())){
							data[i][2] = discNm.get(j)[1].toString();
						}
					}
				}else{
					data[i][2] = data[i][2].toString().trim();
				}
			}else{
				data[i][2] = "";
			}						
		}
		
		
		String agentNo = "select AGENT_NO, trim(AGENT_NO) ||' - '|| trim(AGENT_NM) from TBL_AGENT_INFO"; 
		List<Object[]> agentNm =CommonFunction.getCommQueryDAO().findBySQLQuery(agentNo);
		for (int i = 0; i < data.length; i++) {
			if(data[i][0] != null && !"".equals(data[i][0].toString().trim())){//MCC
				if(agentNm !=null && agentNm.size() >0){
					for(int j = 0; j < agentNm.size(); j++){
						if(data[i][0].toString().trim().equals(agentNm.get(j)[0].toString().trim())){
							data[i][0] = agentNm.get(j)[1].toString();
						}
					}
				}else{
					data[i][0] = data[i][0].toString().trim();
				}
			}else{
				data[i][0] = "";
			}						
		}
		
		for (int i = 0; i < data.length; i++) {
			if(data[i][6].equals("0")){
				data[i][6] = "新增待审核";
			}else if(data[i][6].equals("1")){
				data[i][6] = "已删除";
			}else if(data[i][6].equals("2")){
				data[i][6] = "正常";
			}else if(data[i][6].equals("3")){
				data[i][6] = "修改待审核";
			}else if(data[i][6].equals("4")){
				data[i][6] = "删除待审核";
			}
		}
		
		if(data.length == 0) {
			writeNoDataMsg("没有找到符合条件的记录");
			return;
		}
		
		reportModel.setData(data);
		reportModel.setTitle(title);
		
		
		reportCreator.initReportData(getJasperInputSteam("T11502.jasper"), parameters, 
				reportModel.wrapReportDataSource(), getReportType());
		
		if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN11502RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN11502RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
		
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN11502RN"));
		
		writeUsefullMsg(fileName);
		return;
	}
	
	@Override
	protected String genSql() {
		String whereSql = " WHERE 1=1 ";
	
		/*if (!StringUtil.isNull(mchtStatus)) {
			whereSql += " AND MCHT_STATUS = '"
					+ mchtStatus + "' ";
		}*/
		
		if(isNotEmpty(queryAgentNo)){
			whereSql += " and AGENT_NO like '%" + queryAgentNo + "%'";
		}
		if(isNotEmpty(queryDivideType)){
			whereSql += " and DIVIDE_TYPE like '%" + queryDivideType + "%'";
		}
		if(isNotEmpty(queryState)){
			whereSql += " and state = '" + queryState + "'";
		}

		StringBuffer sb = new StringBuffer();
		/*sb.append("SELECT MCHT_NO,MCHT_NM,ENG_NAME,MCC,ROUTE_FLAG,LICENCE_NO,ADDR,POST_CODE,"
				+ "COMM_EMAIL,MANAGER,CONTACT,COMM_TEL,APPLY_DATE,MCHT_STATUS,nvl(B.TERM_COUNT,0),CRT_OPR_ID,UPD_OPR_ID,"
				+ "SUBSTR(REC_UPD_TS,1,8)||' '||SUBSTR(REC_UPD_TS,9,2)||':'||SUBSTR(REC_UPD_TS,11,2)||':'||SUBSTR(REC_UPD_TS,13,2) FROM "
				+ "(SELECT * FROM TBL_MCHT_BASE_INF_TMP ");
		
		sb.append(whereSql);
		sb.append(") A "
				+ "left outer join "
				+ "(select MCHT_CD,count(1) AS TERM_COUNT from TBL_TERM_INF group by MCHT_CD) B "
				+ "ON (A.MCHT_NO = B.MCHT_CD) ORDER BY MCHT_NO");*/
		
		sb.append("SELECT AGENT_NO,DIVIDE_TYPE,DISC_CD,PROFIT,MIN_VALUE/10000,MAX_VALUE/10000,state FROM TBL_AGENT_DIVIDE_TMP").append(whereSql);
		
		String sql = sb.toString();		
		
		return sql;
	}
	
	private String queryAgentNo;
	private String queryDivideType;
	private String queryState;
	
	public String getQueryAgentNo() {
		return queryAgentNo;
	}

	public void setQueryAgentNo(String queryAgentNo) {
		this.queryAgentNo = queryAgentNo;
	}

	public String getQueryDivideType() {
		return queryDivideType;
	}

	public void setQueryDivideType(String queryDivideType) {
		this.queryDivideType = queryDivideType;
	}

	public String getQueryState() {
		return queryState;
	}

	public void setQueryState(String queryState) {
		this.queryState = queryState;
	}
	
	

	
}
