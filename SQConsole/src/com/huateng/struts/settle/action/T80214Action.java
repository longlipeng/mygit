package com.huateng.struts.settle.action;

import java.io.FileOutputStream;
import java.util.List;

import com.huateng.common.SysParamConstants;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.struts.system.action.ReportBaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.InformationUtil;
import com.huateng.system.util.SysParamUtil;

public class T80214Action extends ReportBaseAction {

	private static final long serialVersionUID = 1L;

	protected ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");
	
	private String date;//终止日期
	private String agency;
	private String branchId;
	private String branchName;
	private String settlmtAmt;//我司短款的清算金额小计
	private String counter;//我司短款的总笔数小计
	
	@Override
	protected String genSql() {
		//对账不平明细表:短款
		sql = "Select b.card_accp_id,b.ips_no,b.card_accp_addr,b.card_accp_term_id,b.pan,b.acq_txn_date,b.date_settlmt,p.value as TXNNUM," +
				"b.amt_trans/100,b.acq_txn_ssn, b.fee_credit/100,b.fee_debit/100,(case b.STLM_FLAG when '4' then '短款' when '6' then '短款' end)" +
				",b.amt_settlmt/100,''As note From BTH_DTL_ERR_GC b,cst_sys_param p Where b.stlm_flag In('4','6') " +
				"And p.key=b.acq_txn_num and p.OWNER='TXNTYPE' and trim(b.rcvg_code)='" + this.getAgency().split("-")[0] + "' ";
		sql +=  " and b.DATE_SETTLMT <='" + this.getDate() + "' order by b.DATE_SETTLMT";
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
			data[0][15]=new String("");
		}
		reportModel.setData(data);
		reportModel.setTitle(title);
		
		parameters.put("date", date);
		parameters.put("agencyId", this.getAgency().split("-")[0]);//收单机构编号
		parameters.put("agencyName", this.getAgency().split("-")[1]);//收单机构名称
		parameters.put("branchId", this.getBranchId());
		parameters.put("branchName", this.getBranchName());
		parameters.put("settlmtAmt", this.getSettlmtAmt());
		parameters.put("counter", this.getCounter());
		
		reportType = "PDF";
		reportCreator.initReportData(getJasperInputSteam("T80214.jasper"), parameters, 
				                     reportModel.wrapReportDataSource(), getReportType());

		fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN80214RN_" +
					operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN80214RN"));
		
		writeUsefullMsg(fileName);
		return;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	@SuppressWarnings("unchecked")
	public String getBranchId() {//获取环讯总公司编号，从分公司信息正式表
		String sql="select t.BRANCH_NO from TBL_BRANCH_MANAGER_TRUE t where BRANCH_LEVEL='0'";
		List list=commQueryDAO.findBySQLQuery(sql);
		this.branchId = (String)list.get(0);
		return this.branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@SuppressWarnings("unchecked")
	public String getBranchName() {//获取环讯总公司名称，从分公司信息正式表
		String sql="select t.BRANCH_NAME from TBL_BRANCH_MANAGER_TRUE t where BRANCH_LEVEL='0'";
		List list=commQueryDAO.findBySQLQuery(sql);
		this.branchName = (String)list.get(0);
		return this.branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getSettlmtAmt() {
		String sql = "Select sum(b.amt_settlmt/100) From BTH_DTL_ERR_GC b Where b.stlm_flag In('4','6') and trim(b.rcvg_code)='"
				+ this.getAgency().split("-")[0] + "' and b.DATE_SETTLMT <='"+ this.getDate() + "'";
		this.settlmtAmt = commQueryDAO.findCountBySQLQuery(sql);
		if(settlmtAmt==null || settlmtAmt.equals(""))
			return "0.00";
		else
			return this.settlmtAmt;
	}

	public void setSettlmtAmt(String settlmtAmt) {
		this.settlmtAmt = settlmtAmt;
	}

	public String getCounter() {
		String sql = "Select count(b.card_accp_id) From BTH_DTL_ERR_GC b Where b.stlm_flag In('4','6') and trim(b.rcvg_code)='"
			+ this.getAgency().split("-")[0] + "' and b.DATE_SETTLMT <='"+ this.getDate() + "'";
		this.counter = commQueryDAO.findCountBySQLQuery(sql);
		return this.counter;
	}

	public void setCounter(String counter) {
		this.counter = counter;
	}
}
