package com.huateng.struts.error;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import com.huateng.bo.error.T10020BO;
import com.huateng.common.Constants;
import com.huateng.po.error.TBLTermHang;
import com.huateng.po.error.TBLTermHangPK;
import com.huateng.po.error.TblMchtHang;
import com.huateng.po.error.TblMchtHangPK;
import com.huateng.po.error.TblTransHang;
import com.huateng.po.error.TblTransHangPK;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.SysParamUtil;

public class T10020Action extends BaseAction{

	private static final long serialVersionUID = 1L;
	private String mchtno;
	private String transdate;
	private String remark;
	private String subTxnId;
	private String termid;
	private String flownum;
	private String txnssn1;
	private String transTime;
	private String transamt;
	private String txnnum;
	
	/**
	 * @return the transamt
	 */
	public String getTransamt() {
		return transamt;
	}

	/**
	 * @param transamt the transamt to set
	 */
	public void setTransamt(String transamt) {
		this.transamt = transamt;
	}

	/**
	 * @return the txnnum
	 */
	public String getTxnnum() {
		return txnnum;
	}

	/**
	 * @param txnnum the txnnum to set
	 */
	public void setTxnnum(String txnnum) {
		this.txnnum = txnnum;
	}

	/**
	 * @return the transTime
	 */
	public String getTransTime() {
		return transTime;
	}

	/**
	 * @param transTime the transTime to set
	 */
	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}

	public String getTxnssn1() {
		return txnssn1;
	}

	public void setTxnssn1(String txnssn1) {
		this.txnssn1 = txnssn1;
	}

	public String getExMsg() {
		return exMsg;
	}

	public void setExMsg(String exMsg) {
		this.exMsg = exMsg;
	}


	private String exMsg;
	private String TranDataList;
	public String getTranDataList() {
		return TranDataList;
	}

	public void setTranDataList(String tranDataList) {
		TranDataList = tranDataList;
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

	public String getSubTxnId() {
		return subTxnId;
	}

	public void setSubTxnId(String subTxnId) {
		this.subTxnId = subTxnId;
	}

	public String getMchtno() {
		return mchtno;
	}

	public void setMchtno(String mchtno) {
		this.mchtno = mchtno;
	}

	public String getTransdate() {
		return transdate;
	}

	public void setTransdate(String transdate) {
		this.transdate = transdate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	private T10020BO t10020BO = (T10020BO) ContextUtil.getBean("T10020BO");
	@Override
	protected String subExecute() throws Exception {
		try {
			 if("add".equals(method)) {
				rspCode = add();
			 } 
			} catch (Exception e) {
				log("操作员编号：" + operator.getOprId()+ "，挂账" + getMethod() + "失败，失败原因为："+e.getMessage());
			}
		return rspCode;
	}
	
	/**
	 * 
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String add() throws IllegalAccessException, InvocationTargetException, Exception {
		//从入网机构中查出各入网机构的机构代码、日切时间
		StringBuffer agenSql = new StringBuffer();
		agenSql.append(" select AGEN_ID,EXTENSION_FIELD1 from TBL_AGENCY_INFO_TRUE where statue='1'");
		List<Object[]> agenList = CommonFunction.getCommQueryDAO().findBySQLQuery(agenSql.toString());
		if(agenList==null || agenList.size()==0)
			return "获取入网机构 日切时间出错！";
 
		if(subTxnId.equals("01")){//按交易挂账
			jsonBean.parseJSONArrayData(getTranDataList());
			int len = jsonBean.getArray().size();
		//	List<TblTransHang> tblTransHangList = new ArrayList<TblTransHang>();
			for(int i = 0; i < len; i++) {
				jsonBean.setObject(jsonBean.getJSONDataAt(i));
				flownum= jsonBean.getJSONDataAt(i).getString("flownum");
				transdate= jsonBean.getJSONDataAt(i).getString("transdate");
				mchtno=jsonBean.getJSONDataAt(i).getString("mchtno");
				txnssn1=jsonBean.getJSONDataAt(i).getString("txnssn1");
				termid=jsonBean.getJSONDataAt(i).getString("termid");
				transTime=jsonBean.getJSONDataAt(i).getString("transTime");
				transamt=jsonBean.getJSONDataAt(i).getString("transamt");
				txnnum=jsonBean.getJSONDataAt(i).getString("txnnum");
				TblTransHang tblTransHang = new TblTransHang();
				TblTransHangPK tblTransHangPK=new TblTransHangPK();
				tblTransHangPK.setFlownum(flownum);
				tblTransHangPK.setTxnssn(txnssn1);
				tblTransHang.setId(tblTransHangPK);
				tblTransHang.setMchtno(mchtno);
				tblTransHang.setTransdate(transdate);
				tblTransHang.setHangflag(CommonFunction.HANG_FLAG_CHECK);//挂账未审核
				tblTransHang.setHangdate(CommonFunction.getCurrentDate());
				tblTransHang.setRemark(exMsg);
				tblTransHang.setRelievedate("");
				tblTransHang.setOprid(operator.getOprId());
				tblTransHang.setTermid(termid);
				tblTransHang.setTransTime(transTime);
				tblTransHang.setTransAmt(transamt);
				tblTransHang.setTxnNum(txnnum);
				t10020BO.saveOrUpdate(tblTransHang);	
			}
			log("保存按交易挂账成功。操作员编号：" + operator.getOprId());
			}
		if(subTxnId.equals("02")){//按终端挂账
			//判断 是否已经按商户进行过挂账
			String countSql="select count(*) from tbl_mcht_hang a,tbl_term_inf t where a.mcht_no=t.mcht_cd and t.term_id='"
				+termid+"' and a.trans_date='"+transdate+"' and a.hang_flag in('0','1','3') ";
			if(!"0".equals(commQueryDAO.findCountBySQLQuery(countSql))){
				return "该终端已经按商户进行过挂账！";
			}
			//判断  是否已经按终端进行过挂账
			String termSql="select count(*) from tbl_term_hang where trans_date='"+transdate+"' and term_id='"+termid+"' and hang_flag in('0','1','3') ";
			if(!"0".equals(commQueryDAO.findCountBySQLQuery(termSql))){
				return "已经对该终端进行过挂账！";
			}
			
			//先判断该商户需要挂账的记录是否在清分清算表中
			//有时间需要将这里的交易日期判断 改成 按各机构的日切时间点来判断（可参考GridConfigMethod.getAlgoDtl()） 
		 	String sql="select count(*) from tbl_algo_dtl where TERM_ID='"+termid+"' and trans_date='"+transdate+"' ";
		 	String total=commQueryDAO.findCountBySQLQuery(sql);
		 	if("0".equals(total)){//如果清分清算表中没记录则去交易流水表中查询
		 		//只需查询成功的原交易，被冲正的、被撤销的原交易不可以进行挂账
		 		sql="select count(*) from tbl_n_txn t where card_accp_term_id='"+termid+"' and (substr(t.inst_date,0,8))='"+ transdate+"' ";
		 		sql+=" And t.resp_code = '00' and revsal_flag<>'1' and cancel_flag<>'1' And txn_num in "+SysParamUtil.getParam("HANGCODE"); 
		 		total=commQueryDAO.findCountBySQLQuery(sql);
		 	}else{//如果清分清算表中有记录则，则查询是否有可以挂账的记录
		 		sql="select count(*) from tbl_algo_dtl where TERM_ID='"+termid+"' and trans_date='"+transdate+"' and  stlm_flg in ('0','3')  and txn_num in "+SysParamUtil.getParam("HANGCODE");
		 		total=commQueryDAO.findCountBySQLQuery(sql);
		 	}
		 	if("0".equals(total)){
		 		return "终端["+termid+"]"+"在"+transdate+"没有可挂账的记录！";
		 	}
			
			TBLTermHang tBLTermHang=new TBLTermHang();
			TBLTermHangPK tBLTermHangPK=new TBLTermHangPK();
			tBLTermHangPK.setTermid(termid);
			tBLTermHangPK.setTransdate(transdate);
			tBLTermHang.setId(tBLTermHangPK);
			tBLTermHang.setRemark(remark);
			tBLTermHang.setHangdate(CommonFunction.getCurrentDate());
			tBLTermHang.setHangflag(CommonFunction.HANG_FLAG_CHECK);//挂账未审核
			tBLTermHang.setRelievedate("");
			tBLTermHang.setOprid(operator.getOprId());
			t10020BO.saveOrUpdate(tBLTermHang);
			}
		 if(subTxnId.equals("03")){//按商户挂账
			//判断 是否已经按商户进行过挂账
				String countSql="select count(*) from tbl_mcht_hang a where a.mcht_no='"
					+mchtno+"' and a.trans_date='"+transdate+"' and a.hang_flag in('0','1','3') ";
				if(!"0".equals(commQueryDAO.findCountBySQLQuery(countSql))){
					return "该商户已经按商户进行过挂账！";
				}
			 
			 	//先判断该商户需要挂账的记录是否在清分清算表中
				//有时间需要将这里的交易日期判断 改成 按各机构的日切时间点来判断（可参考GridConfigMethod.getAlgoDtl()） 
			 	String sql="select count(*) from tbl_algo_dtl where MCHT_CD='"+mchtno+"' and trans_date='"+transdate+"' ";
			 	String total=commQueryDAO.findCountBySQLQuery(sql);
			 	if("0".equals(total)){//如果清分清算表中没记录则去交易流水表中查询
			 		sql="select count(*) from tbl_n_txn t where card_accp_id='"+mchtno+"' and (substr(t.inst_date,0,8))='"+ transdate+"' ";
			 		sql+=" And  resp_code = '00' and revsal_flag<>'1' And cancel_flag<>'1' And txn_num in "+SysParamUtil.getParam("HANGCODE");//过滤掉失败的交易以及冲正的交易
			 		total=commQueryDAO.findCountBySQLQuery(sql);
			 	}else{//如果清分清算表中有记录则，则查询是否有可以挂账的记录
			 		sql="select count(*) from tbl_algo_dtl where MCHT_CD='"+mchtno+"' and trans_date='"+transdate+"' and stlm_flg in ('0','3')  and txn_num in "+SysParamUtil.getParam("HANGCODE");
			 		total=commQueryDAO.findCountBySQLQuery(sql);
			 	}
			 	if("0".equals(total)){
			 		return "商户["+mchtno+"]"+"在"+transdate+"没有可挂账的记录！";
			 	}
			 	/*//先去清分清算表中查询是否有可以挂账的未清算交易
			 	String countStr="select count(*) from tbl_algo_dtl where MCHT_CD='"+mchtno+"' and stlm_flg<>'1' and trans_date='"+transdate+"' ";
				String count=commQueryDAO.findCountBySQLQuery(countStr);
			 	if("0".equals(count)){//如果清分清算表中没有可以挂账的交易，如果是当日的则去交易流水表中进行查询
			 		countStr="select count(*) from tbl_n_txn where card_accp_id='"+mchtno+"' and (substr(t.inst_date,0,8)='"+ transdate+"' ";
			 		count()
			 	}*/
			 	
				TblMchtHang tblMchtHang=new TblMchtHang();
				TblMchtHangPK tblMchtHangPK=new TblMchtHangPK();
				tblMchtHangPK.setMchtno(mchtno);
				tblMchtHangPK.setTransdate(transdate);
				tblMchtHang.setId(tblMchtHangPK);
				tblMchtHang.setRemark(remark);
				tblMchtHang.setHangdate(CommonFunction.getCurrentDate());
				tblMchtHang.setHangflag(CommonFunction.HANG_FLAG_CHECK);//挂账未审核
				tblMchtHang.setRelievedate("");
				tblMchtHang.setOprid(operator.getOprId());
				t10020BO.saveOrUpdate(tblMchtHang);
		}	
		return Constants.SUCCESS_CODE;
	}
}
