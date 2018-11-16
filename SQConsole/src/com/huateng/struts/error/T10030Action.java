package com.huateng.struts.error;

import java.sql.SQLException;
import java.util.List;
import com.huateng.bo.error.T10020BO;
import com.huateng.bo.risk.T40206BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.po.error.TBLTermHang;
import com.huateng.po.error.TBLTermHangPK;
import com.huateng.po.error.TblMchtHang;
import com.huateng.po.error.TblMchtHangPK;
import com.huateng.po.error.TblTransHang;
import com.huateng.po.error.TblTransHangPK;
import com.huateng.po.risk.TblRiskRefuse;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.SysParamUtil;

public class T10030Action extends BaseAction{
	private static final long serialVersionUID = 1L;
	private String mchtno;
	private String flownum;
	private String txnId;
	private String subTxnId;
	private String termid;
	private String refuseInfo;
	private String transdate;
	public String getMchtno() {
		return mchtno;
	}
	public void setMchtno(String mchtno) {
		this.mchtno = mchtno;
	}
	public String getFlownum() {
		return flownum;
	}
	public void setFlownum(String flownum) {
		this.flownum = flownum;
	}
	public String getTxnId() {
		return txnId;
	}
	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}
	public String getSubTxnId() {
		return subTxnId;
	}
	public void setSubTxnId(String subTxnId) {
		this.subTxnId = subTxnId;
	}
	public String getTermid() {
		return termid;
	}
	public void setTermid(String termid) {
		this.termid = termid;
	}
	public String getRefuseInfo() {
		return refuseInfo;
	}
	public void setRefuseInfo(String refuseInfo) {
		this.refuseInfo = refuseInfo;
	}
	public String getTransdate() {
		return transdate;
	}
	public void setTransdate(String transdate) {
		this.transdate = transdate;
	}
	public String getTxnssn() {
		return txnssn;
	}
	public void setTxnssn(String txnssn) {
		this.txnssn = txnssn;
	}
	private String txnssn;
	private T10020BO t10020BO = (T10020BO) ContextUtil.getBean("T10020BO");
	private T40206BO t40206bo = (T40206BO) ContextUtil.getBean("T40206BO");
	
	@Override
	protected String subExecute() throws Exception {
		try {
			 if("accept".equals(method)) {
					rspCode = accept();
				} else if("refuse".equals(method)) {
					rspCode = refuse();
				}
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "，审核" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
	
//通过
	@SuppressWarnings("unchecked")
	private String accept() throws SQLException {
		if(subTxnId.equals("01")){
			String sql = "SELECT OPR_ID FROM TBL_TRANS_HANG WHERE FLOW_NUM= '" + flownum + "' and MCHT_NO='" + mchtno 
				+ "' and TRANS_DATE='" + this.transdate + "'";
			List<String> list = commQueryDAO.findBySQLQuery(sql);
			if (null != list && !list.isEmpty()) {
				if (!StringUtil.isNull(list.get(0))) {
					if(list.get(0).equals(operator.getOprId())){
						return "同一操作员不能审核！";
					}
				}
			}
			
		TblTransHangPK tblTransHangPK=new TblTransHangPK();
		tblTransHangPK.setFlownum(flownum);
		tblTransHangPK.setTxnssn(txnssn);
		TblTransHang tblTransHang=t10020BO.getTran(tblTransHangPK);
		tblTransHang.setHangflag(CommonFunction.HANG_RECEIVE);
		tblTransHang.setRelievedate(CommonFunction.getCurrentDate());
		t10020BO.update(tblTransHang);
		String transdate=tblTransHang.getTransdate();
		
		//先判断该商户需要解账的记录是否在清分清算表中
	 	sql="select count(*) from tbl_algo_dtl where trans_date='"+transdate+"' and TXN_SSN='"+txnssn+"' and (substr(MISC_1,0,12))= '" + flownum + "'";
	 	String total=commQueryDAO.findCountBySQLQuery(sql);
	 	if("0".equals(total)){//如果清分清算表中没记录则去交易流水表中进行解账
	 		sql="update tbl_n_txn set batch_flag='3' where (substr(inst_date,0,8))='"+transdate+"' and SYS_SEQ_NUM='"+txnssn+"' and RETRIVL_REF= '" + flownum + "' and batch_flag='2' ";
	 		sql+=" And  resp_code = '00' and revsal_flag<>'1' And txn_num <>'1021' and txn_num<>'8311' and txn_num not in "+SysParamUtil.getParam("REVSALCODE");//过滤掉失败的交易以及冲正的交易
	 		commQueryDAO.excute(sql);
	 	}
	 	String sqlNew = "update BTH_NEW_GC_TXN_SUCC set batch_flag='3' where inst_date='"+transdate+"' and SYS_SEQ_NUM='"+txnssn+"' and RETRIVL_REF= '" + flownum + "' and batch_flag='2' "
	 					+" And  resp_code = '00' and revsal_flag<>'1' And txn_num <>'1021' and txn_num<>'8311' and txn_num not in "+SysParamUtil.getParam("REVSALCODE");//过滤掉失败的交易以及冲正的交易
 		commQueryDAO.excute(sqlNew);
	 	//20120801:不论清分清算表中能否找到可解挂的记录，都更新清分清算表tbl_algo_dtl中的stml_flg值为3
//	 	else{//如果清分清算表中有记录，则进行解账, 并对月结商户表中的手续费进行解账
	 		String sql2="update tbl_algo_dtl set stlm_flg = '3' where stlm_flg='2' and trans_date='"+transdate+"' and TXN_SSN='"+txnssn+"' and (substr(MISC_1,0,12))= '" + flownum + "'";
	 		commQueryDAO.excute(sql2);
	 		//更新月结商户表
	 		String sql3="update TBL_MCHNT_MONTH_CLEAN set stlm_flag='3' where stlm_flag='2' and trans_date='"+transdate+"' and TRANS_TIME='"+tblTransHang.getTransTime()+"' and TREM_ID= '" + tblTransHang.getTermid() + "'";
	 		commQueryDAO.excute(sql3);
//	 	}
		 /*try {
		//审核通过状态改为stlm_flg2
		 String sql2="update tbl_algo_dtl  set stlm_flg = '3' where stlm_flg='2' and trans_date='"+transdate+"' and TXN_SSN='"+txnssn+"' and (substr(MISC_1,0,12))= '" + flownum + "'";
		 pst=conn.prepareStatement(sql2);
		 pst.executeUpdate();
	   } catch (SQLException e) {
		 e.printStackTrace();
	   }finally{ }*/
		}
//按终端
		if(subTxnId.equals("02")){
			String sql = "SELECT OPR_ID FROM TBL_TERM_HANG WHERE TERM_ID= '" + termid + "' and TRANS_DATE='" + this.transdate +"'";
			List<String> list = commQueryDAO.findBySQLQuery(sql);
			if (null != list && !list.isEmpty()) {
				if (!StringUtil.isNull(list.get(0))) {
					if(list.get(0).equals(operator.getOprId())){
						return "同一操作员不能审核！";
					}
				}
			}
			
			//先判断该商户需要解账的记录是否在清分清算表中
		 	sql="select count(*) from tbl_algo_dtl where trans_date='"+transdate+"' and term_id='"+termid+"'";
		 	String total=commQueryDAO.findCountBySQLQuery(sql);
		 	if("0".equals(total)){//如果清分清算表中没记录则去交易流水表中进行解挂
		 		sql="update tbl_n_txn set batch_flag='3' where card_accp_term_id='"+termid+"' and (substr(inst_date,0,8))='"+ transdate+"' and batch_flag='2' ";
		 		sql+=" And  resp_code = '00' and revsal_flag<>'1' And txn_num <>'1021' and txn_num<>'8311' and txn_num not in "+SysParamUtil.getParam("REVSALCODE");//过滤掉失败的交易以及冲正的交易
		 		commQueryDAO.excute(sql);
		 	}
		 	String sqlNew = "update BTH_NEW_GC_TXN_SUCC set batch_flag='3' where card_accp_term_id='"+termid+"' and inst_date = '"+ transdate+"' and batch_flag='2' "
				+" And  resp_code = '00' and revsal_flag<>'1' And txn_num <>'1021' and txn_num<>'8311' and txn_num not in "+SysParamUtil.getParam("REVSALCODE");//过滤掉失败的交易以及冲正的交易
		 	commQueryDAO.excute(sqlNew);
		 	//20120801:不论清分清算表中能否找到可解挂的记录，都更新清分清算表tbl_algo_dtl中的stml_flg值为3
//		 	else{//如果清分清算表中有记录，则进行解挂
		 		String sql2="update tbl_algo_dtl set stlm_flg = '3' where stlm_flg='2' and trans_date='"+transdate+"' and term_id='"+termid+"'";
		 		commQueryDAO.excute(sql2);
		 		//更新月结商户表
		 		String sql3="update TBL_MCHNT_MONTH_CLEAN set stlm_flag='3' where stlm_flag='2' and trans_date='"+transdate+"' and TREM_ID='"+termid+"'";
		 		commQueryDAO.excute(sql3);
//		 	}
			
			TBLTermHangPK tBLTermHangPK=new TBLTermHangPK();
			tBLTermHangPK.setTermid(termid);
			tBLTermHangPK.setTransdate(transdate);
			TBLTermHang tBLTermHang=t10020BO.getTerm(tBLTermHangPK);
			tBLTermHang.setHangflag(CommonFunction.HANG_RECEIVE);
			tBLTermHang.setRelievedate(CommonFunction.getCurrentDate());
			t10020BO.update(tBLTermHang);
		/*try {
			conn=ConnectionUtils.openConnection();	
			String sql2="update tbl_algo_dtl  set stlm_flg = '3' where stlm_flg='2' and trans_date='"+transdate+"' and TERM_ID='"+termid+"'";
			 pst=conn.prepareStatement(sql2);
			 pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			pst.close();
			conn.close();
		}*/
		}
//商户		
		if(subTxnId.equals("03")){
			String sql = "SELECT OPR_ID FROM TBL_MCHT_HANG WHERE MCHT_NO= '" + mchtno + "' and TRANS_DATE='" + this.transdate + "'";
			List<String> list = commQueryDAO.findBySQLQuery(sql);
			if (null != list && !list.isEmpty()) {
				if (!StringUtil.isNull(list.get(0))) {
					if(list.get(0).equals(operator.getOprId())){
						return "同一操作员不能审核！";
					}
				}
			}
			
			//先判断该商户需要解账的记录是否在清分清算表中
		 	sql="select count(*) from tbl_algo_dtl where trans_date='"+transdate+"' and MCHT_CD='"+mchtno+"'";
		 	String total=commQueryDAO.findCountBySQLQuery(sql);
		 	if("0".equals(total)){//如果清分清算表中没记录则去交易流水表中进行解挂
		 		sql="update tbl_n_txn set batch_flag='3' where card_accp_id='"+mchtno+"' and (substr(inst_date,0,8))='"+ transdate+"' and batch_flag='2' ";
		 		sql+=" And  resp_code = '00' and revsal_flag<>'1' And txn_num <>'1021' and txn_num<>'8311' and txn_num not in "+SysParamUtil.getParam("REVSALCODE");//过滤掉失败的交易以及冲正的交易
		 		commQueryDAO.excute(sql);
		 	}
		 	String sqlNew = "update BTH_NEW_GC_TXN_SUCC set batch_flag='3' where card_accp_id='"+mchtno+"' and inst_date = '"+ transdate+"' and batch_flag='2' "
					+" And  resp_code = '00' and revsal_flag<>'1' And txn_num <>'1021' and txn_num<>'8311' and txn_num not in "+SysParamUtil.getParam("REVSALCODE");//过滤掉失败的交易以及冲正的交易
		 	commQueryDAO.excute(sqlNew);
		 	//20120801:不论清分清算表中能否找到可解挂的记录，都更新清分清算表tbl_algo_dtl中的stml_flg值为3
//		 	else{//如果清分清算表中有记录，则进行解挂
		 		String sql2="update tbl_algo_dtl  set stlm_flg = '3' where stlm_flg='2' and trans_date='"+transdate+"' and MCHT_CD='"+mchtno+"'";
		 		commQueryDAO.excute(sql2);
		 		//更新月结商户表
		 		String sql3="update TBL_MCHNT_MONTH_CLEAN set stlm_flag='3' where stlm_flag='2' and trans_date='"+transdate+"' and MCHT_CD='"+mchtno+"'";
		 		commQueryDAO.excute(sql3);
//		 	}
			TblMchtHangPK tblMchtHangPK=new TblMchtHangPK();
			tblMchtHangPK.setMchtno(mchtno);
			tblMchtHangPK.setTransdate(transdate);
			TblMchtHang tblMchtHang=t10020BO.getMcht(tblMchtHangPK);
			tblMchtHang.setHangflag(CommonFunction.HANG_RECEIVE);
			tblMchtHang.setRelievedate(CommonFunction.getCurrentDate());
			t10020BO.update(tblMchtHang);
			/* try {
			String sql2="update tbl_algo_dtl  set stlm_flg = '3' where stlm_flg='2' and trans_date='"+transdate+"' and MCHT_CD='"+mchtno+"'";
			pst=conn.prepareStatement(sql2);
			 pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{}*/
		}
		return Constants.SUCCESS_CODE;
	}
	
//拒绝
	@SuppressWarnings("unchecked")
	private String refuse() throws Exception {
		TblRiskRefuse tblRiskRefuse=new TblRiskRefuse();
		if(subTxnId.equals("04")){
			String sql = "SELECT OPR_ID FROM TBL_TRANS_HANG WHERE FLOW_NUM= '" + flownum + "' and MCHT_NO='" + mchtno 
				+ "' and TRANS_DATE='" + this.transdate + "'";
			List<String> list = commQueryDAO.findBySQLQuery(sql);
			if (null != list && !list.isEmpty()) {
				if (!StringUtil.isNull(list.get(0))) {
					if(list.get(0).equals(operator.getOprId())){
						return "同一操作员不能审核！";
					}
				}
			}
			TblTransHangPK tblTransHangPK=new TblTransHangPK();
			tblTransHangPK.setFlownum(flownum);
			tblTransHangPK.setTxnssn(txnssn);
			TblTransHang tblTransHang=t10020BO.getTran(tblTransHangPK);
			tblTransHang.setHangflag(CommonFunction.HANG_FLAG);
			tblTransHang.setRelievedate(CommonFunction.getCurrentDate());
			t10020BO.update(tblTransHang);
			//拒绝原因插入
			tblRiskRefuse.setRefuseType(tblTransHang.getHangflag());
			tblRiskRefuse.setRefuseInfo(getRefuseInfo());
			tblRiskRefuse.setOprId(operator.getOprId());
			tblRiskRefuse.setBrhId(operator.getOprBrhId());
			tblRiskRefuse.setParam1(flownum);
			tblRiskRefuse.setParam2(mchtno);
			tblRiskRefuse.setParam3(tblTransHang.getHangdate());
			tblRiskRefuse.setParam4(tblTransHang.getTransdate());
			tblRiskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());
			tblRiskRefuse.setFlag("96");
			t40206bo.saveRefuseInfo(tblRiskRefuse);
	}
	//终端
	if(subTxnId.equals("05")){
		String sql = "SELECT OPR_ID FROM TBL_TERM_HANG WHERE TERM_ID= '" + termid + "' and TRANS_DATE='" + this.transdate +"'";
		List<String> list = commQueryDAO.findBySQLQuery(sql);
		if (null != list && !list.isEmpty()) {
			if (!StringUtil.isNull(list.get(0))) {
				if(list.get(0).equals(operator.getOprId())){
					return "同一操作员不能审核！";
				}
			}
		}
		TBLTermHangPK tBLTermHangPK=new TBLTermHangPK();
		tBLTermHangPK.setTermid(termid);
		tBLTermHangPK.setTransdate(transdate);
		TBLTermHang tBLTermHang=t10020BO.getTerm(tBLTermHangPK);
		tBLTermHang.setHangflag(CommonFunction.HANG_FLAG);
		tBLTermHang.setRelievedate(CommonFunction.getCurrentDate());
		t10020BO.update(tBLTermHang);
		//拒绝原因插入
		tblRiskRefuse.setRefuseType(tBLTermHang.getHangflag());
		tblRiskRefuse.setRefuseInfo(getRefuseInfo());
		tblRiskRefuse.setOprId(operator.getOprId());
		tblRiskRefuse.setBrhId(operator.getOprBrhId());
		tblRiskRefuse.setParam1(termid);
		tblRiskRefuse.setParam2(tBLTermHang.getHangdate());
		tblRiskRefuse.setParam3(transdate);
		tblRiskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());
		tblRiskRefuse.setFlag("95");
		t40206bo.saveRefuseInfo(tblRiskRefuse);
}
	//商户
	if(subTxnId.equals("06")){
		String sql = "SELECT OPR_ID FROM TBL_MCHT_HANG WHERE MCHT_NO= '" + mchtno + "' and TRANS_DATE='"+this.transdate+"'";
		List<String> list = commQueryDAO.findBySQLQuery(sql);
		if (null != list && !list.isEmpty()) {
			if (!StringUtil.isNull(list.get(0))) {
				if(list.get(0).equals(operator.getOprId())){
					return "同一操作员不能审核！";
				}
			}
		}
		TblMchtHangPK tblMchtHangPK=new TblMchtHangPK();
		tblMchtHangPK.setMchtno(mchtno);
		tblMchtHangPK.setTransdate(transdate);
		TblMchtHang tblMchtHang=t10020BO.getMcht(tblMchtHangPK);
		tblMchtHang.setHangflag(CommonFunction.HANG_FLAG);
		tblMchtHang.setRelievedate(CommonFunction.getCurrentDate());
		t10020BO.update(tblMchtHang);
		//拒绝原因插入
		tblRiskRefuse.setRefuseType(tblMchtHang.getHangflag());
		tblRiskRefuse.setRefuseInfo(getRefuseInfo());
		tblRiskRefuse.setOprId(operator.getOprId());
		tblRiskRefuse.setBrhId(operator.getOprBrhId());
		tblRiskRefuse.setParam1(mchtno);
		tblRiskRefuse.setParam2(tblMchtHang.getHangdate());
		tblRiskRefuse.setParam3(transdate);
		tblRiskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());
		tblRiskRefuse.setFlag("94");
		t40206bo.saveRefuseInfo(tblRiskRefuse);
	}
		return  Constants.SUCCESS_CODE;
	}
}
