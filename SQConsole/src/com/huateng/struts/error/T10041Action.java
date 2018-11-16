package com.huateng.struts.error;

import java.util.ArrayList;
import java.util.List;
import com.huateng.bo.error.T10020BO;
import com.huateng.common.Constants;
import com.huateng.po.error.TBLTermHangPK;
import com.huateng.po.error.TblMchtHangPK;
import com.huateng.po.error.TblTransHang;
import com.huateng.po.error.TBLTermHang;
import com.huateng.po.error.TblMchtHang;
import com.huateng.po.error.TblTransHangPK;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

public class T10041Action extends BaseAction{

	private static final long serialVersionUID = 1L;
	public String getFlownum() {
		return flownum;
	}

	public void setFlownum(String flownum) {
		this.flownum = flownum;
	}

	public String getTransdate() {
		return transdate;
	}

	public void setTransdate(String transdate) {
		this.transdate = transdate;
	}

	public String getMchtno() {
		return mchtno;
	}

	public void setMchtno(String mchtno) {
		this.mchtno = mchtno;
	}

	private String flownum;
	private String transdate;
	private String mchtno;
	private String exMsg;
	private String TranDataList;
	private String refuseInfo;
	private String jieInfo;
	private String txnssn1; 
	private String transTime;
	
	
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

	public String getJieInfo() {
		return jieInfo;
	}

	public void setJieInfo(String jieInfo) {
		this.jieInfo = jieInfo;
	}

	public String getTermid() {
		return termid;
	}

	public void setTermid(String termid) {
		this.termid = termid;
	}

	private String termid;
	public String getRefuseInfo() {
		return refuseInfo;
	}

	public void setRefuseInfo(String refuseInfo) {
		this.refuseInfo = refuseInfo;
	}

	public String getTranDataList() {
		return TranDataList;
	}

	public void setTranDataList(String tranDataList) {
		TranDataList = tranDataList;
	}

	public String getExMsg() {
		return exMsg;
	}

	public void setExMsg(String exMsg) {
		this.exMsg = exMsg;
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

	private String txnId;
	private String subTxnId;
	private T10020BO t10020BO = (T10020BO) ContextUtil.getBean("T10020BO");
	@Override
	protected String subExecute() throws Exception {
		// TODO Auto-generated method stub
		try {
			 if("update".equals(method)) {
					rspCode = update();
				} 
			} catch (Exception e) {
				log("操作员编号：" + operator.getOprId()+ "，解挂" + getMethod() + "失败，失败原因为："+e.getMessage());
			}
			return rspCode;
	}

	private String update() {
		// TODO Auto-generated method stub
		if(subTxnId.equals("01")){
			jsonBean.parseJSONArrayData(getTranDataList());
			int len = jsonBean.getArray().size();
			List<TblTransHang> tblTransHangList = new ArrayList<TblTransHang>();
			for(int i = 0; i < len; i++) {
				jsonBean.setObject(jsonBean.getJSONDataAt(i));
				flownum= jsonBean.getJSONDataAt(i).getString("flownum");
				transdate= jsonBean.getJSONDataAt(i).getString("transdate");
				mchtno=jsonBean.getJSONDataAt(i).getString("mchtno");	
				txnssn1=jsonBean.getJSONDataAt(i).getString("txnssn1");	
				termid=jsonBean.getJSONDataAt(i).getString("termid");
				transTime=jsonBean.getJSONDataAt(i).getString("transTime");
				TblTransHangPK tblTransHangPK=new TblTransHangPK();
				tblTransHangPK.setFlownum(flownum);
				tblTransHangPK.setTxnssn(txnssn1);
				TblTransHang tblTransHang =t10020BO.getTran(tblTransHangPK);
				if(tblTransHang==null){
					tblTransHang=new TblTransHang();
					tblTransHang.setId(tblTransHangPK);
					tblTransHang.setTransdate(transdate);
					tblTransHang.setMchtno(mchtno);
				}
				tblTransHang.setTermid(termid);
				tblTransHang.setTransTime(transTime);
				tblTransHang.setHangflag(CommonFunction.HANG_RECEIVE_CHECK);//解挂未审核
				tblTransHang.setRelievedate(CommonFunction.getCurrentDate());
				tblTransHang.setReliveremark(exMsg);
				tblTransHang.setOprid(operator.getOprId());
				t10020BO.saveOrUpdate(tblTransHang);	
			}
	            log("按交易解挂成功。操作员编号：" + operator.getOprId());
		
	 }
		if(subTxnId.equals("02")){
			TBLTermHangPK tBLTermHangPK=new TBLTermHangPK();
			tBLTermHangPK.setTermid(termid);
			tBLTermHangPK.setTransdate(transdate);
			TBLTermHang tBLTermHang=t10020BO.getTerm(tBLTermHangPK);
			tBLTermHang.setHangflag(CommonFunction.HANG_RECEIVE_CHECK);//解挂未审核
			tBLTermHang.setRelievedate(CommonFunction.getCurrentDate());
			tBLTermHang.setReliveremark(jieInfo);
			tBLTermHang.setOprid(operator.getOprId());
			t10020BO.saveOrUpdate(tBLTermHang);	
		}
		if(subTxnId.equals("03")){
			TblMchtHangPK tblMchtHangPK=new TblMchtHangPK();
			tblMchtHangPK.setMchtno(mchtno);
			tblMchtHangPK.setTransdate(transdate);
			TblMchtHang tblMchtHang=t10020BO.getMcht(tblMchtHangPK);
			tblMchtHang.setHangflag(CommonFunction.HANG_RECEIVE_CHECK);//解挂未审核
			tblMchtHang.setRelievedate(CommonFunction.getCurrentDate());
			tblMchtHang.setReliveremark(jieInfo);
			tblMchtHang.setOprid(operator.getOprId());
			t10020BO.saveOrUpdate(tblMchtHang);	
		}
		  return Constants.SUCCESS_CODE;
      }
	
	}
