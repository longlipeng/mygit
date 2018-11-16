package com.huateng.struts.settle.action;

import java.io.FileOutputStream;
import com.huateng.common.SysParamConstants;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.struts.system.action.ReportBaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.InformationUtil;
import com.huateng.system.util.SysParamUtil;

public class T80215Action extends ReportBaseAction {

	private static final long serialVersionUID = 1L;

	protected ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");
	
	private String date;//清算起止日期
	
	@Override
	protected String genSql() {
		String dateNew = date;
		dateNew.replace("-", "");
		//对账不平明细表
		sql = "Select ACQ_PAN,ISSUER_CODE,ACQ_TXN_SSN,OUT_SSN,RETRIVL_REF,ACQ_MCHT_CD,CARD_ACCP_TERM_ID," +
				"ACQ_AMT_TRANS/100,INST_DATE,ACQ_TXN_NUM,FEE_CREDIT/100,FEE_DEBIT/100," +
				"(case RCVG_CODE when '01041000' then '中行通道' when '1032900' then '农行通道' " +
				"when '1026100' then '工行通道' when '1032900' then '农行通道' else '未知通道' end)," +
				"(case STLM_FLAG when '1' then 'POSP有，CUPS没有' when '2' then 'POSP无，CUPS有' when '3' then 'POSP与CUPS金额不一致' " +
				"when '4' then 'POSP有，中行没有' when '5' then 'POSP无， 中行有' when '6' then 'POSP与中行金额不一致' " +
				"when 'a' then 'POSP有，工行没有' when 'b' then 'POSP无工行有' when 'c' then 'POSP与工行金额不一致' " +
				"when 'd' then 'POSP有，农行没有' when 'e' then 'POSP无农行有' when 'f' then 'POSP与农行金额不一致' " +
				"else '没有找到不平原因' end),INST_TIME" +
				" From BTH_DTL_ERR_GC where DATE_SETTLMT='"+dateNew+"'";
		return sql;
	}

	@Override
	protected void reportAction() throws Exception {

		title = InformationUtil.createTitles("V_", 15);
		data = reportCreator.process(genSql(), title);
		if(data.length == 0) {//没有符合条件的记录时生成一条空记录
//			writeNoDataMsg("没有找到符合条件的记录");
//			return;
			data = new Object[1][15];
			data[0][0]=new String("");
			data[0][1]=new String("");
			data[0][2]=new String("");
			data[0][3]=new String("");
			data[0][4]=new String("");
			data[0][5]=new String("");
			data[0][6]=new String("");
			data[0][7]=new String("");
			data[0][8]=new String("");
			data[0][9]=new String("");
			data[0][10]=new String("");
			data[0][11]=new String("");
			data[0][12]=new String("");
			data[0][13]=new String("");
			data[0][14]=new String("");
		}
		reportModel.setData(data);
		reportModel.setTitle(title);
		
		parameters.put("date", date);
				
		reportType = "EXCEL";
		reportCreator.initReportData(getJasperInputSteam("T80215.jasper"), parameters, 
				                     reportModel.wrapReportDataSource(), getReportType());

		fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN80215RN_" +
					operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN80215RN"));
		
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
