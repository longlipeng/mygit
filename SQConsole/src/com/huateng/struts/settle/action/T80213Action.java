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

public class T80213Action extends ReportBaseAction {

	private static final long serialVersionUID = 1L;

	protected ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");
	
	private String date;//终止日期
	private String agency;
	private String branchId;
	private String branchName;
	private String settlmtAmt1;//我司长款的清算金额小计
	private String counter1;//我司长款的总笔数小计
	private String settlmtAmt2;//商户挂账的清算金额小计
	private String counter2;//商户挂账的总笔数小计
	private String settlmtAmt3;//商户未计算款的清算金额小计
	private String counter3;//商户未计算款的总笔数小计
	private String settlmtAmt4;//商户追扣的清算金额小计
	private String counter4;//商户追扣的总笔数小计
	private String settlmtAmt5;//商户手工退货的清算金额小计
	private String counter5;//商户手工退货的总笔数小计
	
	@Override
	protected String genSql() {
		//清算交易流水表(要么未结算，要么解挂，目前解挂的勾兑状态计入未结算中)
		sql = "Select t.mcht_cd,t.ips_no,m.MCHT_NM,t.term_id,t.pan,t.trans_date,t.date_settlmt,p.value as TXNNUM,t.trans_amt/100,t.txn_ssn," +
				"to_char(t.fee_c_out*100)/100,to_char(t.fee_d_out*100)/100,(case t.STLM_FLG when '0' then '未结算' when '2' then '挂账' " +
				"when '3' then '未结算' end),t.settl_amt/100,'' As note From TBL_ALGO_DTL t,TBL_MCHT_BASE_INF m," +
				"cst_sys_param p Where t.STLM_FLG In('0','2','3')  And m.MCHT_NO=t.MCHT_CD and p.key=t.txn_num " +
				"and p.OWNER='TXNTYPE' And trim(t.stlm_ins_id_cd)='" + this.getAgency().split("-")[0] + "' ";
		sql +=  " and t.DATE_SETTLMT <='" + this.getDate() + "'";
		
		//差错信息表：手工退货
		sql += " union Select e.MCHT_ID,e.IPS_NO,m.MCHT_NM,e.TERM_ID,e.PAN,e.TRANS_DATE_TIME,e.DATE_SETTLMT,p.value as TXNNUM,e.AMT_TRANS/100" +
				",e.TXN_SSN,e.FEE_CREDIT/100,e.FEE_DEBIT/100,(case e.ERR_FLAG when 'E74' then '手工退货' end),(e.AMT_TRANS/100-e.FEE_CREDIT/100)" +
				",'' As note From BTH_CUP_ERR_TXN e,TBL_MCHT_BASE_INF m,cst_sys_param p Where e.ERR_FLAG='E74' And m.MCHT_NO=e.MCHT_ID" +
				" And p.OWNER='TXNTYPE' and p.key=e.txn_num and trim(e.RCV_INS_ID_CD)='" + this.getAgency().split("-")[0] + "'";
		sql +=  " and e.DATE_SETTLMT <='" + this.getDate() + "'";
		
		//差错信息表：追扣
		sql += " union Select f.MCHT_ID,f.IPS_NO,m.MCHT_NM,f.TERM_ID,f.PAN,f.TRANS_DATE_TIME,f.DATE_SETTLMT,p.value as TXNNUM,f.ORG_TRANS_AMT/100" +
				",f.TXN_SSN,f.FEE_CREDIT/100,f.FEE_DEBIT/100,(case f.ERR_FLAG when 'H01' then '追扣' end),f.AMT_TRANS/100,'' As note " +
				"From BTH_CUP_ERR_TXN f,TBL_MCHT_BASE_INF m,cst_sys_param p Where f.ERR_FLAG='H01' And m.MCHT_NO=f.MCHT_ID " +
				"And p.OWNER='TXNTYPE' and p.key=f.txn_num and trim(f.RCV_INS_ID_CD)='" + this.getAgency().split("-")[0] + "'";
		sql +=  " and f.DATE_SETTLMT <='" + this.getDate() + "'";
		
		//对账不平明细表:长款
		sql += " union Select b.card_accp_id,b.ips_no,b.card_accp_addr,b.card_accp_term_id,b.pan,b.acq_txn_date,b.date_settlmt,p.value as TXNNUM," +
				"b.amt_trans/100,b.acq_txn_ssn, b.fee_credit/100,b.fee_debit/100,(case b.STLM_FLAG when '5' then '长款' when '6' then '长款' end)" +
				",b.amt_settlmt/100,''As note From BTH_DTL_ERR_GC b,cst_sys_param p" +
				" Where b.stlm_flag In('5','6') And p.key=b.acq_txn_num and p.OWNER='TXNTYPE' and trim(b.rcvg_code)='" 
				+ this.getAgency().split("-")[0] + "' ";
		sql +=  " and b.DATE_SETTLMT <='" + this.getDate() + "'";
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
		parameters.put("settlmtAmt1", this.getSettlmtAmt1());
		parameters.put("counter1", this.getCounter1());
		parameters.put("settlmtAmt2", this.getSettlmtAmt2());
		parameters.put("counter2", this.getCounter2());
		parameters.put("settlmtAmt3", this.getSettlmtAmt3());
		parameters.put("counter3", this.getCounter3());
		parameters.put("settlmtAmt4", this.getSettlmtAmt4());
		parameters.put("counter4", this.getCounter4());
		parameters.put("settlmtAmt5", this.getSettlmtAmt5());
		parameters.put("counter5", this.getCounter5());
		Double settlmtSum1 = new Double("0.00");
		if( !this.getSettlmtAmt1().equals("0.00") ){
			settlmtSum1 += Double.parseDouble(getSettlmtAmt1());
		}
		if( !this.getSettlmtAmt2().equals("0.00") ){
			settlmtSum1 += Double.parseDouble(getSettlmtAmt2());
		}
		if( !this.getSettlmtAmt3().equals("0.00") ){
			settlmtSum1 += Double.parseDouble(getSettlmtAmt3());
		}
		if( !this.getSettlmtAmt4().equals("0.00") ){
			settlmtSum1 += Double.parseDouble(getSettlmtAmt4());
		}
		if( !this.getSettlmtAmt5().equals("0.00") ){
			settlmtSum1 += Double.parseDouble(getSettlmtAmt5());
		}
		parameters.put("settlmtSum1", String.valueOf(settlmtSum1));
		parameters.put("counterSum1", String.valueOf(Integer.parseInt(counter1)+Integer.parseInt(counter2)
				+Integer.parseInt(counter3)+Integer.parseInt(counter4)+Integer.parseInt(counter5)));
		
		reportType = "PDF";
		reportCreator.initReportData(getJasperInputSteam("T80213.jasper"), parameters, 
				                     reportModel.wrapReportDataSource(), getReportType());

		fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN80213RN_" +
					operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN80213RN"));
		
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

	public String getSettlmtAmt1() {
		String sql = "Select sum(b.amt_settlmt/100) From BTH_DTL_ERR_GC b Where b.stlm_flag In('5','6') and trim(b.rcvg_code)='"
				+ this.getAgency().split("-")[0] + "' and b.DATE_SETTLMT <='"+ this.getDate() + "'";
		this.settlmtAmt1 = commQueryDAO.findCountBySQLQuery(sql);
		if(settlmtAmt1==null || settlmtAmt1.equals(""))
			return "0.00";
		else
			return this.settlmtAmt1;
	}

	public void setSettlmtAmt1(String settlmtAmt1) {
		this.settlmtAmt1 = settlmtAmt1;
	}

	public String getCounter1() {
		String sql = "Select count(b.card_accp_id) From BTH_DTL_ERR_GC b Where b.stlm_flag In('5','6') and trim(b.rcvg_code)='"
			+ this.getAgency().split("-")[0] + "' and b.DATE_SETTLMT <='"+ this.getDate() + "'";
		this.counter1 = commQueryDAO.findCountBySQLQuery(sql);
		return this.counter1;
	}

	public void setCounter1(String counter1) {
		this.counter1 = counter1;
	}

	public String getSettlmtAmt2() {
		String sql = "Select sum(t.settl_amt/100) From TBL_ALGO_DTL t Where t.STLM_FLG='2' and trim(t.stlm_ins_id_cd)='"
				+ this.getAgency().split("-")[0] + "' " + " and t.DATE_SETTLMT <='" + this.getDate() + "'";
		this.settlmtAmt2 = commQueryDAO.findCountBySQLQuery(sql);
		if(settlmtAmt2==null || settlmtAmt2.equals(""))
			return "0.00";
		else
			return this.settlmtAmt2;
	}

	public void setSettlmtAmt2(String settlmtAmt2) {
		this.settlmtAmt2 = settlmtAmt2;
	}

	public String getCounter2() {
		String sql = "Select count(t.mcht_cd) From TBL_ALGO_DTL t Where t.STLM_FLG='2' and trim(t.stlm_ins_id_cd)='"
				+ this.getAgency().split("-")[0] + "' and t.DATE_SETTLMT <='"+ this.getDate() + "'";
		this.counter2 = commQueryDAO.findCountBySQLQuery(sql);
		return this.counter2;
	}

	public void setCounter2(String counter2) {
		this.counter2 = counter2;
	}

	public String getSettlmtAmt3() {
		String sql = "Select sum(t.settl_amt/100) From TBL_ALGO_DTL t Where t.STLM_FLG in('0','3') and trim(t.stlm_ins_id_cd)='"
				+ this.getAgency().split("-")[0] + "' " + " and t.DATE_SETTLMT <='" + this.getDate() + "'";
		this.settlmtAmt3 = commQueryDAO.findCountBySQLQuery(sql);
		if(settlmtAmt3==null || settlmtAmt3.equals(""))
			return "0.00";
		else
			return this.settlmtAmt3;
	}

	public void setSettlmtAmt3(String settlmtAmt3) {
		this.settlmtAmt3 = settlmtAmt3;
	}

	public String getCounter3() {
		String sql = "Select count(t.mcht_cd) From TBL_ALGO_DTL t Where t.STLM_FLG in('0','3') and trim(t.stlm_ins_id_cd)='"
				+ this.getAgency().split("-")[0] + "' and t.DATE_SETTLMT <='"+ this.getDate() + "'";
		this.counter3 = commQueryDAO.findCountBySQLQuery(sql);
		return this.counter3;
	}

	public void setCounter3(String counter3) {
		this.counter3 = counter3;
	}

	public String getSettlmtAmt4() {
		String sql = "Select sum(f.AMT_TRANS/100) From BTH_CUP_ERR_TXN f Where f.ERR_FLAG='H01' "
				+ " and trim(f.RCV_INS_ID_CD)='" + this.getAgency().split("-")[0] + "' and f.DATE_SETTLMT <='" + this.getDate() + "'";
		this.settlmtAmt4 = commQueryDAO.findCountBySQLQuery(sql);
		if(settlmtAmt4==null || settlmtAmt4.equals(""))
			return "0.00";
		else
			return this.settlmtAmt4;
	}

	public void setSettlmtAmt4(String settlmtAmt4) {
		this.settlmtAmt4 = settlmtAmt4;
	}

	public String getCounter4() {
		String sql = "Select count(f.MCHT_ID) From BTH_CUP_ERR_TXN f Where f.ERR_FLAG='H01' "
			+ "and trim(f.RCV_INS_ID_CD)='" + this.getAgency().split("-")[0] + "' and f.DATE_SETTLMT <='" + this.getDate() + "'";
		this.counter4 = commQueryDAO.findCountBySQLQuery(sql);
		return this.counter4;
	}

	public void setCounter4(String counter4) {
		this.counter4 = counter4;
	}

	public String getSettlmtAmt5() {
		String sql = "Select sum(f.AMT_TRANS/100) From BTH_CUP_ERR_TXN f Where f.ERR_FLAG='E74' "
			+ " and trim(f.RCV_INS_ID_CD)='" + this.getAgency().split("-")[0] + "' and f.DATE_SETTLMT <='" + this.getDate() + "'";
		this.settlmtAmt5 = commQueryDAO.findCountBySQLQuery(sql);
		if(settlmtAmt5==null || settlmtAmt5.equals(""))
			return "0.00";
		else
			return this.settlmtAmt5;
	}

	public void setSettlmtAmt5(String settlmtAmt5) {
		this.settlmtAmt5 = settlmtAmt5;
	}

	public String getCounter5() {
		String sql = "Select count(f.MCHT_ID) From BTH_CUP_ERR_TXN f Where f.ERR_FLAG='E74' "
			+ "and trim(f.RCV_INS_ID_CD)='" + this.getAgency().split("-")[0] + "' and f.DATE_SETTLMT <='" + this.getDate() + "'";
		this.counter5 = commQueryDAO.findCountBySQLQuery(sql);
		return this.counter5;
	}

	public void setCounter5(String counter5) {
		this.counter5 = counter5;
	}
	
}
