package com.huateng.struts.error;

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

public class T10051Action extends BaseAction{

	private static final long serialVersionUID = 1L;
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
	private String mchtno;
	private String flownum;
	private String txnId;
	private String subTxnId;
	private String termid;
	private String refuseInfo;
	private String txnssn;
	private String transdate;
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
	private String accept() throws Exception {
		if(subTxnId.equals("01")){
			String sql = "SELECT OPR_ID FROM TBL_TRANS_HANG WHERE FLOW_NUM= '" + flownum + "' and TXN_SSN='" + txnssn 
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
			tblTransHang.setHangdate(CommonFunction.getCurrentDate());
			t10020BO.update(tblTransHang);
			String transdate=tblTransHang.getTransdate();
			
			//先判断该商户需要挂账的记录是否在清分清算表中
		 	sql="select count(*) from tbl_algo_dtl where trans_date='"+transdate+"' and TXN_SSN='"+txnssn+"' and (substr(MISC_1,0,12))= '" + flownum + "'";
		 	String total=commQueryDAO.findCountBySQLQuery(sql);
		 	if("0".equals(total)){//如果清分清算表中没记录则去交易流水表中进行挂账
		 		sql="update tbl_n_txn set batch_flag='2' where (substr(inst_date,0,8))='"+transdate+"' and SYS_SEQ_NUM='"+txnssn+"' and RETRIVL_REF= '" + flownum + "'";
		 		sql+=" And  resp_code = '00' and revsal_flag<>'1' and cancel_flag<>'1' And txn_num in "+SysParamUtil.getParam("HANGCODE");//过滤掉失败的交易以及冲正的交易
		 		commQueryDAO.excute(sql);
		 	}else{//如果清分清算表中有记录则，则进行挂账, 并对月结商户表中的手续费进行挂账
		 		String sql2="update tbl_algo_dtl  set stlm_flg = '2' where stlm_flg<>'1' and trans_date='"+transdate+"' and TXN_SSN='"+txnssn+"' and (substr(MISC_1,0,12))= '" + flownum + "'";
		 		commQueryDAO.excute(sql2);
		 		//更新月结商户表
		 		String sql3="update TBL_MCHNT_MONTH_CLEAN set stlm_flag='2' where stlm_flag<>'1' and trans_date='"+transdate+"' and FLOW_NO='"+tblTransHang.getId().getTxnssn()+"' and TREM_ID= '" + tblTransHang.getTermid() + "'";
		 		commQueryDAO.excute(sql3);
		 	}
			 /*try {
			//审核通过状态改为stlm_flg2
			String sql2="update tbl_algo_dtl  set stlm_flg = '2' where stlm_flg<>'1' and trans_date='"+transdate+"' and TXN_SSN='"+txnssn+"' and (substr(MISC_1,0,12))= '" + flownum + "'";
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{}*/
		}else 
//按终端
		if(subTxnId.equals("02")){
			String sql = "SELECT OPR_ID FROM TBL_TERM_HANG WHERE TERM_ID= '" + termid + "' and TRANS_DATE='" + this.transdate + "'";
			List<String> list = commQueryDAO.findBySQLQuery(sql);
			if (null != list && !list.isEmpty()) {
				if (!StringUtil.isNull(list.get(0))) {
					if(list.get(0).equals(operator.getOprId())){
						return "同一操作员不能审核！";
					}
				}
			}
			
			//先判断该商户需要挂账的记录是否在清分清算表中
		 	sql="select count(*) from tbl_algo_dtl where TERM_ID='"+termid+"' and trans_date='"+transdate+"' ";
		 	String total=commQueryDAO.findCountBySQLQuery(sql);
		 	if("0".equals(total)){//如果清分清算表中没记录则去交易流水表中进行挂账
		 		sql="update tbl_n_txn set batch_flag='2' where card_accp_term_id='"+termid+"' and (substr(inst_date,0,8))='"+ transdate+"'";
		 		sql+=" And resp_code = '00' and revsal_flag<>'1' and cancel_flag<>'1' And txn_num in "+SysParamUtil.getParam("HANGCODE");//过滤掉失败的交易以及冲正的交易
		 		commQueryDAO.excute(sql);
		 	}else{//如果清分清算表中有记录则，则进行挂账, 并对月结商户表中的手续费进行挂账
		 		String sql2="update tbl_algo_dtl  set stlm_flg = '2' where stlm_flg<>'1' and trans_date='"+transdate+"' and TERM_ID='"+termid+"' and txn_num in "+SysParamUtil.getParam("HANGCODE");
		 		commQueryDAO.excute(sql2);
		 		//更新月结商户表
		 		String sql3="update TBL_MCHNT_MONTH_CLEAN set stlm_flag='2' where stlm_flag<>'1' and trans_date='"+transdate+"' and TREM_ID='"+termid+"' and txn_num in "+SysParamUtil.getParam("HANGCODE");
		 		commQueryDAO.excute(sql3);
		 	}
			
			TBLTermHangPK tBLTermHangPK=new TBLTermHangPK();
			tBLTermHangPK.setTermid(termid);
			tBLTermHangPK.setTransdate(transdate);
			TBLTermHang tBLTermHang=t10020BO.getTerm(tBLTermHangPK);
			tBLTermHang.setHangflag(CommonFunction.HANG_FLAG);
			tBLTermHang.setHangdate(CommonFunction.getCurrentDate());
			t10020BO.update(tBLTermHang);
		/*try {
			String sql2="update tbl_algo_dtl  set stlm_flg = '2' where stlm_flg<>'1' and trans_date='"+transdate+"' and TERM_ID='"+termid+"'";
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{	}*/
		}else
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
			//先判断该商户需要挂账的记录是否在清分清算表中
		 	sql="select count(*) from tbl_algo_dtl where MCHT_CD='"+mchtno+"' and trans_date='"+transdate+"' ";
		 	String total=commQueryDAO.findCountBySQLQuery(sql);
		 	if("0".equals(total)){//如果清分清算表中没记录则去交易流水表中进行挂账
		 		sql="update tbl_n_txn set batch_flag='2' where card_accp_id='"+mchtno+"' and (substr(inst_date,0,8))='"+ transdate+"'";
		 		sql+=" And  resp_code = '00' and revsal_flag<>'1' and cancel_flag<>'1' And txn_num in "+SysParamUtil.getParam("HANGCODE");//过滤掉失败的交易以及冲正的交易
		 		commQueryDAO.excute(sql);
		 	}else{//如果清分清算表中有记录则，则进行挂账, 并对月结商户表中的手续费进行挂账
		 		String sql2="update tbl_algo_dtl  set stlm_flg = '2' where stlm_flg<>'1' and trans_date='"+transdate+"' and MCHT_CD='"+mchtno+"' and txn_num in "+SysParamUtil.getParam("HANGCODE");
		 		commQueryDAO.excute(sql2);
		 		//更新月结商户表
		 		String sql3="update TBL_MCHNT_MONTH_CLEAN set stlm_flag='2' where stlm_flag<>'1' and trans_date='"+transdate+"' and MCHT_CD='"+mchtno+"' and txn_num in "+SysParamUtil.getParam("HANGCODE");
		 		commQueryDAO.excute(sql3);
		 	}
			TblMchtHangPK tblMchtHangPK=new TblMchtHangPK();
			tblMchtHangPK.setMchtno(mchtno);
			tblMchtHangPK.setTransdate(transdate);
			TblMchtHang tblMchtHang=t10020BO.getMcht(tblMchtHangPK);
			tblMchtHang.setHangflag(CommonFunction.HANG_FLAG);
			tblMchtHang.setHangdate(CommonFunction.getCurrentDate());
			t10020BO.update(tblMchtHang);
		/*	 try {
			String sql2="update tbl_algo_dtl  set stlm_flg = '2' where stlm_flg<>'1' and trans_date='"+transdate+"' and MCHT_CD='"+mchtno+"'";
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{}*/
		}
		return Constants.SUCCESS_CODE;
	}
	
	//拒绝
	private String refuse() throws Exception {
		TblRiskRefuse tblRiskRefuse=new TblRiskRefuse();
		if(subTxnId.equals("04")){
			String sql = "SELECT OPR_ID FROM TBL_TRANS_HANG WHERE FLOW_NUM= '" + flownum + "' and TXN_SSN='" + txnssn 
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
			tblTransHang.setHangflag(CommonFunction.HANG_FLAG_REFUSE);
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
			tblRiskRefuse.setFlag("99");
			t40206bo.saveRefuseInfo(tblRiskRefuse);
	 }
	//终端
	if(subTxnId.equals("05")){
		String sql = "SELECT OPR_ID FROM TBL_TERM_HANG WHERE TERM_ID= '" + termid + "' and TRANS_DATE='"+this.transdate+"'";
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
		tBLTermHang.setHangflag(CommonFunction.HANG_FLAG_REFUSE);
		tBLTermHang.setHangdate(CommonFunction.getCurrentDate());
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
		tblRiskRefuse.setFlag("98");
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
		tblMchtHang.setHangflag(CommonFunction.HANG_FLAG_REFUSE);
		tblMchtHang.setHangdate(CommonFunction.getCurrentDate());
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
		tblRiskRefuse.setFlag("97");
		t40206bo.saveRefuseInfo(tblRiskRefuse);
}
		return  Constants.SUCCESS_CODE;
	}
}
