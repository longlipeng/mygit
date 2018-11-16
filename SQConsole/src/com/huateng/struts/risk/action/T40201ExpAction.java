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


public class T40201ExpAction extends ReportBaseAction{


	protected void reportAction() throws Exception {
		reportType = "EXCEL";
		
		title = InformationUtil.createTitles("V_", 10);
		data = reportCreator.process(genSql(), title);
		
		
		for (int i = 0; i < data.length; i++) {
			data[i][3] = this.formatDate((String)data[i][3]);
			
			
		}
		
		for (int i = 0; i < data.length; i++) {
			data[i][6] = this.formatDate((String)data[i][6]);
			
			
		}
		
		for (int i = 0; i < data.length; i++) {
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
		
		
		reportCreator.initReportData(getJasperInputSteam("T40201.jasper"), parameters, 
				reportModel.wrapReportDataSource(), getReportType());
		
		if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN40201RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN40201RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
		
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN40201RN"));
		
		writeUsefullMsg(fileName);
		return;
	}
	
	@Override
	protected String genSql() {
		String whereSql = " WHERE FLAG= '1'  ";
	
		/*if (!StringUtil.isNull(mchtStatus)) {
			whereSql += " AND MCHT_STATUS = '"
					+ mchtStatus + "' ";
		}*/
		
		if(isNotEmpty(idCardNo)){
			whereSql += " and PARAM1 like '%" + idCardNo + "%'";
		}
		
		StringBuffer sb = new StringBuffer();
		
	
		sb.append("SELECT PARAM1,PARAM6,REFUSE_TYPE,PARAM4,PARAM3,PARAM2,TXN_TIME,OPR_ID,REFUSE_INFO,PARAM5 FROM tbl_risk_refuse ")
		  .append(whereSql)
		  .append("ORDER BY PARAM1");
		String sql = sb.toString();		
		
		return sql;
	}
	
	private String idCardNo;

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}


	
}
