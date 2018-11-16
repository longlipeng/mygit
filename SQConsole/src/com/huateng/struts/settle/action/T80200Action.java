package com.huateng.struts.settle.action;

import java.io.FileOutputStream;
import com.huateng.common.SysParamConstants;
import com.huateng.struts.system.action.ReportBaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.InformationUtil;
import com.huateng.system.util.SysParamUtil;

/**
 * 商户对账明细报表
 * 
 * @author crystal
 *
 */
public class T80200Action extends ReportBaseAction {

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
		if(isNotEmpty(mchtNo)) {
			whereSql.append(" and b.CARD_ACCP_ID ='").append(mchtNo).append("'") ;
		}
		if(isNotEmpty(stlmStartDate)) {
			whereSql.append(" and b.DATE_SETTLMT >='").append(stlmStartDate).append("'") ;
		}
		if(isNotEmpty(stlmEndDate)) {
			whereSql.append(" and b.DATE_SETTLMT <='").append(stlmEndDate).append("'") ;
		}
		//消费类型交易
		sql = "select m.MCHT_NM,b.CARD_ACCP_ID,b.DATE_SETTLMT,b.card_accp_term_id,b.INST_DATE,t.VALUE,b.CUP_SSN,b.PAN," +
				"b.fwd_inst_id_code,ltrim(b.amt_trans,'0')/100,b.mcht_fee/100,'','' " +
				" from BTH_DTL_BDT b,TBL_MCHT_BASE_INF m,cst_sys_param t where m.MCHT_NO=b.CARD_ACCP_ID and b.TXN_NUM=t.key " +
				"and t.owner='TXNTYPE' and b.txn_num='1101' ";
		sql += whereSql.toString();
		//预授权完成交易，原交易为预授权类型交易,20121022修改关联字段
		sql += " union select m.MCHT_NM,b.CARD_ACCP_ID,b.DATE_SETTLMT,b.card_accp_term_id,b.INST_DATE,t.VALUE,b.CUP_SSN,b.PAN," +
				"b.fwd_inst_id_code,ltrim(b.amt_trans,'0')/100,b.mcht_fee/100,n.date_local_trans||' '||n.time_local_trans,n.cup_ssn " +
				" from BTH_DTL_BDT b,TBL_MCHT_BASE_INF m,cst_sys_param t,tbl_n_txn n where m.MCHT_NO=b.CARD_ACCP_ID and b.TXN_NUM=t.key " +
				"and t.owner='TXNTYPE' and n.AUTHR_ID_RESP=b.AUTHR_ID_RESP and b.txn_num='1091' and n.txn_num='1011' ";
		sql += whereSql.toString();
		//退货交易，原交易为消费类型交易
		sql += " union select m.MCHT_NM,b.CARD_ACCP_ID,b.DATE_SETTLMT,b.card_accp_term_id,b.INST_DATE,t.VALUE,b.CUP_SSN,b.PAN," +
				"b.fwd_inst_id_code,ltrim(b.amt_trans,'0')/100,b.mcht_fee/100,n.date_local_trans||' '||n.time_local_trans,n.cup_ssn " +
				" from BTH_DTL_BDT b,TBL_MCHT_BASE_INF m,cst_sys_param t,tbl_n_txn n where m.MCHT_NO=b.CARD_ACCP_ID and b.TXN_NUM=t.key " +
				"and t.owner='TXNTYPE' and n.retrivl_ref=b.retrivl_ref and b.txn_num='5151' and n.txn_num='1101' ";
		sql += whereSql.toString();
		
//		sql += " order by b.DATE_SETTLMT ";
		return sql;
	}

	@Override
	protected void reportAction() throws Exception {
		title = InformationUtil.createTitles("V_", 13);
		data = reportCreator.process(genSql(), title);
		
		/*if(data.length == 0) {
			writeNoDataMsg("没有找到符合条件的记录");
			return;
		}*/
		
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
		reportCreator.initReportData(getJasperInputSteam("T80200.jasper"), parameters, 
				                     reportModel.wrapReportDataSource(), getReportType());

//		if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN80200RN_" +
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN80200RN"));
		
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
