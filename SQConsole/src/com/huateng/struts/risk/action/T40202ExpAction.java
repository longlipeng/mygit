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
package com.huateng.struts.risk.action;

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


public class T40202ExpAction extends ReportBaseAction{


	protected void reportAction() throws Exception {
		reportType = "EXCEL";
		
		title = InformationUtil.createTitles("V_", 10);
		data = reportCreator.process(genSql(), title);
		
		for (int i = 0; i < data.length; i++) {
			if(data[i][3] == null || "".equals(data[i][3])){
				data[i][3] = "";
			}else{
			data[i][3] = this.formatDate((String)data[i][3]);
			}
			
		}
		
		for (int i = 0; i < data.length; i++) {
			if(data[i][1] == null || "".equals(data[i][1])){
				data[i][1] = "";
			}else{
				if(data[i][1].equals("0")){
					data[i][1] = "手动添加";
				}else if(data[i][1].equals("1")){
					data[i][1] = "自动添加";
				}
			}
		}
		
		for (int i = 0; i < data.length; i++) {
			if(data[i][6] == null || "".equals(data[i][6])){
				data[i][6] = "";
			}else{
			data[i][6] = this.formatDate((String)data[i][6]);
			
			}
		}
		
		for (int i = 0; i < data.length; i++) {
			if(data[i][2] == null || "".equals(data[i][2])){
				data[i][2] = "";
			}else{
				if(data[i][2].equals("0")){
					data[i][2] = "新增审核拒绝";
				}else if(data[i][2].equals("1")){
					data[i][2] = "已删除";
				}else if(data[i][2].equals("2")){
					data[i][2] = "正常";
				}else if(data[i][2].equals("3")){
					data[i][2] = "修改审核拒绝";
				}else if(data[i][2].equals("4")){
					data[i][2] = "删除审核拒绝";
				}else if(data[i][2].equals("5")){
					data[i][2] = "新增审核通过";
				}else if(data[i][2].equals("6")){
					data[i][2] = "删除待审核通过";
				}
			}
		}
		
		for (int i = 0; i < data.length; i++) {
			if(data[i][9].equals("1")){
				data[i][9] = "已删除";
			}else if(data[i][9].equals("2")){
				data[i][9] = "正常";
			}
		}
		
		if(data.length == 0) {
			writeNoDataMsg("没有找到符合条件的记录");
			return;
		}
		
		reportModel.setData(data);
		reportModel.setTitle(title);
		
		
		reportCreator.initReportData(getJasperInputSteam("T40202.jasper"), parameters, 
				reportModel.wrapReportDataSource(), getReportType());
		
		if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN40202RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN40202RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
		
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN40202RN"));
		
		writeUsefullMsg(fileName);
		return;
	}
	
	@Override
	protected String genSql() {
		String whereSql = " WHERE FLAG='2' ";
	
		/*if (!StringUtil.isNull(mchtStatus)) {
			whereSql += " AND MCHT_STATUS = '"
					+ mchtStatus + "' ";
		}*/
		
		
		if(isNotEmpty(saMerChName)){
			whereSql += " and PARAM1 like '%" + saMerChName + "%'";
		}

		StringBuffer sb = new StringBuffer();
		
	
		/*sb.append("SELECT mcht_no||'-'||MCHT_NM,SA_STATE,LICENCE_NO,IDENTITY_NO,BANK_LICENCE_NO FROM (")
		  .append("SELECT d.SA_LIMIT_AMT,d.SA_ACTION,d.SA_ZONE_NO,d.SA_ADMI_BRAN_NO,d.SA_CONN_OR,d.SA_CONN_TEL,d.SA_INIT_ZONE_NO,d.SA_INIT_OPR_ID,d.SA_INIT_TIME,d.SA_MODI_ZONE_NO,d.SA_MODI_OPR_ID,d.SA_MODI_TIME,d.SA_LIMIT_AMT_OLD,d.SA_ACTION_OLD,c.MCC,c.RISL_LVL,c.apply_date,c.bank_no,c.LICENCE_NO,c.mcht_no,c.MCHT_NM,c.IDENTITY_NO,c.BANK_LICENCE_NO,d.SA_STATE FROM TBL_MCHT_BASE_INF c,TBL_CTL_MCHT_INF d where c.MCHT_NO=d.SA_MER_NO ) A left outer join (select MCHT_CD,count(term_id) AS TERM_COUNT from TBL_TERM_INF group by MCHT_CD) B ON (A.MCHT_NO = B.MCHT_CD)")
		  .append(whereSql)
		  .append("ORDER BY A.SA_STATE");*/
		sb.append("SELECT PARAM1,PARAM6,REFUSE_TYPE,PARAM4,PARAM3,PARAM2,TXN_TIME,OPR_ID,REFUSE_INFO,PARAM5 FROM tbl_risk_refuse")
		  .append(whereSql);
		String sql = sb.toString();		
		
		return sql;
	}
	
	
	private String saMerChName;

	public String getSaMerChName() {
		return saMerChName;
	}

	public void setSaMerChName(String saMerChName) {
		this.saMerChName = saMerChName;
	}

	
	

	
}
