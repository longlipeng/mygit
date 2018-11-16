package com.huateng.struts.settle.action;

import java.io.FileOutputStream;
import com.huateng.common.SysParamConstants;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.struts.system.action.ReportBaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.InformationUtil;
import com.huateng.system.util.SysParamUtil;

public class T80303Action extends ReportBaseAction {

	private static final long serialVersionUID = 1L;

	protected ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");
	
	private String importDate;//导入日期
	private String instId;
	
	@Override
	protected String genSql() {
		//机构导入差错结果报表：按机构和导入日期
		sql = "Select TERM_ID,SUBSTR(XFER_FEE,0,6),PAN,ORG_DATE_TIME,TRANS_DATE_TIME,AMT_TRANS/100,FEE_DEBIT/100,AMT_TRANS/100-FEE_DEBIT/100," +
				"AUTHOR_RSP_CD,PROCCESSING_CODE,MSG_TYPE,SUBSTR(XFER_FEE,6,4),ORG_RETRIVL_REF From BTH_CUP_ERR_TXN " +
				"Where trim(ERR_FLAG)='E22' and trim(RCV_INS_ID_CD)='" + this.getInstId() + "' and DATE_SETTLMT='" + this.getImportDate() + "'";
		return sql;
	}

	@Override
	protected void reportAction() throws Exception {
		title = InformationUtil.createTitles("V_", 13);
		data = reportCreator.process(genSql(), title);
		if(data.length == 0) {//没有符合条件的记录时生成一条空记录
//			writeNoDataMsg("没有找到符合条件的记录");
//			return;
			data = new Object[1][13];
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
//			data[0][13]=new String("");
		}
		reportModel.setData(data);
		reportModel.setTitle(title);
		
		parameters.put("importDate", this.getImportDate());//导入日期
		parameters.put("instId", this.getInstId());//机构编号
				
		reportType = "EXCEL";
		reportCreator.initReportData(getJasperInputSteam("T80303.jasper"), parameters,
				    reportModel.wrapReportDataSource(), getReportType());

		fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN80303RN_" +
					operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN80303RN"));
		
		writeUsefullMsg(fileName);
		return;
	}

	public String getImportDate() {
		return importDate;
	}

	public void setImportDate(String importDate) {
		this.importDate = importDate;
	}

	public String getInstId() {
		return instId;
	}

	public void setInstId(String instId) {
		this.instId = instId;
	}
	
}
