package com.huateng.struts.settle.action;

import java.io.FileOutputStream;
import com.huateng.common.SysParamConstants;
import com.huateng.struts.system.action.ReportBaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.InformationUtil;
import com.huateng.system.util.SysParamUtil;

public class T80212Action extends ReportBaseAction {

	private static final long serialVersionUID = 1L;
	
	private String date;
	
	@Override
	protected String genSql() {
		StringBuffer whereSql = new StringBuffer();
		sql = "select b.brch_bank_no,b.brch_bank_name,b.SETTLE_DATE,b.batch_no,b.yesterday_amt_disposit,b.day_amt_disposit," +
				"b.day_sum_amt_disposit from tbl_brch_amt_deposit b ";
		
		if(isNotEmpty(date)) {
			whereSql.append(" where b.SETTLE_DATE ='").append(date).append("'") ;
		}
		sql += whereSql.toString();
//		sql += " order by b.SETTLE_DATE ";
		return sql;
	}

	@Override
	protected void reportAction() throws Exception {
		title = InformationUtil.createTitles("V_", 7);
		data = reportCreator.process(genSql(), title);
		if(data.length == 0) {//没有符合条件的记录时生成一条空记录
//			writeNoDataMsg("没有找到符合条件的记录");
//			return;
			data = new Object[1][7];
			data[0][0]=new String("");
			data[0][1]=new String("");
			data[0][2]=new String("");
			data[0][3]=new String("");
			data[0][4]=new String("");
			data[0][5]=new String("");
			data[0][6]=new String("");
		}
		reportModel.setData(data);
		reportModel.setTitle(title);
		//统计日期
		parameters.put("date", date);
		reportType = "EXCEL";
		reportCreator.initReportData(getJasperInputSteam("T80212.jasper"), parameters, 
				                     reportModel.wrapReportDataSource(), getReportType());

//		if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN80212RN_" +
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN80212RN"));
		
		writeUsefullMsg(fileName);
		return;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
