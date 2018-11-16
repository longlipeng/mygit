package com.huateng.struts.error;

import java.io.FileOutputStream;
import com.huateng.common.SysParamConstants;
import com.huateng.struts.system.action.ReportBaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.InformationUtil;
import com.huateng.system.util.SysParamUtil;

public class T10036Action extends ReportBaseAction {

	private static final long serialVersionUID = 1L;

	//商户号码
	private String mchtNo;
	//清算开始日期
	private String stlmStartDate;
	//清算结束日期
	private String stlmEndDate;
	
	@Override
	protected String genSql() {
		StringBuffer whereSql = new StringBuffer();
		sql = "select e.TRANS_DATE_TIME,(case e.ERR_FLAG when 'E05' then '调单回复' when 'E22' then '请款' " +
				"when 'E23' then '退单' when 'E24' then '再请款' when 'E25' then '二次退单' " +
				"when 'E32' then '贷记调整' when 'E33' then '一般转帐转入贷记调整' when 'E34' then '一般转帐转出贷记调整' " +
				"when 'E35' then '一般转帐转入对贷记调整的请款' when 'E36' then '一般转帐转出请款' when 'E73' then '差错例外' " +
				"when 'E74' then '能查找到原始交易的手工退货' when 'E80' then '贷记调整（存入类）' when 'E84' then '不能查找到原始交易的手工退货' " +
				"when '2' then '交易挂账' when '3' then '交易解挂' when 'H01' then '追扣' end),ltrim(e.AMT_TRANS,'0')/100,e.ERR_CODE," +
				"e.ORG_DATE_TIME,ltrim(e.ORG_TRANS_AMT,'0')/100,e.FEE_CREDIT/100,e.ORG_TXN_SSN," +
				"(case e.STLM_FLG when '0' then '未清分' when '1' then '已清分' when '2' then '手工处理' end),e.MCHT_ID,m.MCHT_NM,e.DATE_SETTLMT " +
				" from BTH_CUP_ERR_TXN e,TBL_MCHT_BASE_INF m where m.MCHT_NO=e.MCHT_ID ";
		
		if(isNotEmpty(mchtNo)) {
			whereSql.append(" and e.MCHT_ID ='").append(mchtNo).append("'") ;
		}
		if(isNotEmpty(stlmStartDate)) {
			whereSql.append(" and e.DATE_SETTLMT >='").append(stlmStartDate).append("'") ;
		}
		if(isNotEmpty(stlmEndDate)) {
			whereSql.append(" and e.DATE_SETTLMT <='").append(stlmEndDate).append("'") ;
		}
		sql += whereSql.toString();
		sql += " order by e.DATE_SETTLMT ";
		return sql;
	}

	@Override
	protected void reportAction() throws Exception {
		title = InformationUtil.createTitles("V_", 12);
		data = reportCreator.process(genSql(), title);
		
		if(data.length == 0) {
			writeNoDataMsg("没有找到符合条件的记录");
			return;
		}
		
//		for(int i=0; i<title.length; i++) {
//			data[0][i] = "1";
//		}
		reportModel.setData(data);
		reportModel.setTitle(title);
		//统计日期
		parameters.put("stlmStartDate", stlmStartDate);
		parameters.put("stlmEndDate", stlmEndDate);
		//商户编号
		parameters.put("mchtNo", mchtNo);
		reportType = "EXCEL";
		reportCreator.initReportData(getJasperInputSteam("T10036.jasper"), parameters, 
				                     reportModel.wrapReportDataSource(), getReportType());

//		if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN10036RN_" +
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN10036RN"));
		
//		callDownload(fileName,"shanghuchacuojiaoyimingxibiao.xls");
		writeUsefullMsg(fileName);
		return;
	}

	public String getMchtNo() {
		return mchtNo;
	}

	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}

	public String getStlmStartDate() {
		return stlmStartDate;
	}

	public void setStlmStartDate(String stlmStartDate) {
		this.stlmStartDate = stlmStartDate;
	}

	public String getStlmEndDate() {
		return stlmEndDate;
	}

	public void setStlmEndDate(String stlmEndDate) {
		this.stlmEndDate = stlmEndDate;
	}

}
