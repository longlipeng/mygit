package com.huateng.struts.pos.action;

import java.util.ArrayList;
import java.util.List;

import com.huateng.bo.term.T3010BO;
import com.huateng.common.Constants;
import com.huateng.po.TblTermInfTmp;
import com.huateng.po.TblTermTmkLog;
import com.huateng.po.TblTermTmkLogPK;
import com.huateng.struts.pos.TblTermInfConstants;
import com.huateng.struts.pos.TblTermTmkLogConstants;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.SysCodeUtil;

public class T30202Action extends BaseAction {

	private static final long serialVersionUID = 4822110756831919797L;
//	private String termId;
//	private String recCrtTs;
	private String termNo;
	private String startDate;
	private String endDate;
	private String mchtCd;
	private String state;
	private String termBranch;
	private T3010BO t3010BO;
	private String idList;
	public String getIdList() {
		return idList;
	}
	public void setIdList(String idList) {
		this.idList = idList;
	}
	public String getTermNo() {
		return termNo;
	}
	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getMchtCd() {
		return mchtCd;
	}
	public void setMchtCd(String mchtCd) {
		this.mchtCd = mchtCd;
	}
	public String getTermBranch() {
		return termBranch;
	}
	public void setTermBranch(String termBranch) {
		this.termBranch = termBranch;
	}
	/*public String getTermId() {
		return termId;
	}
	public void setTermId(String termId) {
		this.termId = termId;
	}
	public String getRecCrtTs() {
		return recCrtTs;
	}
	public void setRecCrtTs(String recCrtTs) {
		this.recCrtTs = recCrtTs;
	}*/
	public void setT3010BO(T3010BO t3010bo) {
		t3010BO = t3010bo;
	}

	@Override
	protected String subExecute() throws Exception {
		jsonBean.parseJSONArrayData(getIdList());
		int len = jsonBean.getArray().size(); 
		String subTxnId = getSubTxnId();
		String batchNo = t3010BO.getBatchNo();
		for(int k = 0; k < len; k++) {	
			String termId = jsonBean.getJSONDataAt(k).get("termId").toString();
			String recCrtTs = jsonBean.getJSONDataAt(k).get("recCrtTs").toString();
			if(subTxnId.equals("01")) {
				TblTermInfTmp tblTermInfTmp = t3010BO.get(CommonFunction.fillString(termId, ' ', 12,true ), recCrtTs);
				if(tblTermInfTmp == null)
					return TblTermTmkLogConstants.T30202_01;
				if(tblTermInfTmp.getTermSta().equals(TblTermInfConstants.TERM_STA_RUN))
				{
					TblTermTmkLog tblTermTmkLog = new TblTermTmkLog();
					String sql = "select count(*) from tbl_term_tmk_log where term_id_id = '"+tblTermInfTmp.getId().getTermId().trim()+"'";
					String sqlCount = CommonFunction.getCommQueryDAO().findCountBySQLQuery(sql);
					if(sqlCount.equals("0")){
						tblTermTmkLog.setMisc("1");
					}
					tblTermTmkLog.setId(new TblTermTmkLogPK(tblTermInfTmp.getId().getTermId().trim(),batchNo));
					tblTermTmkLog.setMchnNo(tblTermInfTmp.getMchtCd());
					tblTermTmkLog.setTermBranch(tblTermInfTmp.getTermBranch());
					tblTermTmkLog.setState(TblTermTmkLogConstants.STATE_INIT);
					tblTermTmkLog.setReqOpr(operator.getOprId());
					tblTermTmkLog.setReqDate(CommonFunction.getCurrentDate());
					tblTermTmkLog.setRecUpdOpr(operator.getOprId());
					tblTermTmkLog.setRecUpdTs(CommonFunction.getCurrentDateTime());
					t3010BO.initTmkLog(tblTermTmkLog);
					return Constants.SUCCESS_CODE_CUSTOMIZE+"申请批次号："+batchNo;
				}
				return TblTermTmkLogConstants.T30202_02;
			}
		}
		
			if(subTxnId.equals("02")) {
//				List list = t3010BO.qryTermInfo(termNo,state,operator, mchtCd, termBranch, startDate, endDate);
				List<Object> list = new ArrayList<Object>();
				for(int k = 0; k < len; k++) {	
					String termId = jsonBean.getJSONDataAt(k).get("termId").toString();
					String recCrtTs = jsonBean.getJSONDataAt(k).get("recCrtTs").toString();
					StringBuffer whereSql = new StringBuffer(" WHERE trim(t1.term_id) not in (select t3.term_id_id from tbl_term_tmk_log t3 where t3.state ='0')").append(" and t1.TERM_BRANCH in " + operator.getBrhBelowId()).append(" and t1.term_sta = '1' ");
					if (mchtCd != null && !mchtCd.trim().equals("")) {
						whereSql.append(" AND t1.MCHT_CD='").append(mchtCd).append("'");
					}
					if (termBranch != null && !termBranch.trim().equals("")) {
						whereSql.append(" AND t1.TERM_BRANCH='").append(termBranch).append("'");
					}
					if (termId != null && !termId.trim().equals("")) {
						whereSql.append(" AND t1.TERM_ID='")
						.append(termId).append("'");
//						.append(CommonFunction.fillString(termNo, ' ', 12, true)).append("'");
					}
					if (state != null && !state.trim().equals("")){
						if(state.equals("1"))
							whereSql.append(" AND t2.STATE='1'");
						else
							whereSql.append(" AND t2.STATE is null");
					}
					if (recCrtTs != null && !recCrtTs.trim().equals("")) {
						whereSql.append(" AND t1.REC_CRT_TS = ").append(recCrtTs);
					}
					
					String sql = "SELECT t1.term_id,t1.mcht_cd,t1.term_branch FROM tbl_term_inf_tmp t1 left join tbl_term_tmk_log t2 on trim(t1.term_id)=t2.term_id_id and t2.state!='0'"
					+ whereSql.toString() + " ORDER BY t1.term_id";
					List<Object> list1 = commQueryDAO.findBySQLQuery(sql);
					list.add(list1);
				}
				if(list == null || list.isEmpty())
					return TblTermTmkLogConstants.T30202_03;
				List<TblTermTmkLog> result = new ArrayList<TblTermTmkLog>();
				String tmpTermId = null;
				int count = 0;
				for(int i=0;i<list.size();i++) {
//					Object[] object = (Object[])list.get(i);
					List<Object> objs = (List<Object>) list.get(i);
					Object[] object = (Object[]) objs.get(0);
					if(!object[0].toString().trim().equals(tmpTermId)){
						TblTermTmkLog tmp = new TblTermTmkLog();
						tmp.setId(new TblTermTmkLogPK(object[0].toString().trim(),batchNo));
						String sql = "select count(*) from tbl_term_tmk_log where term_id_id = '"+object[0].toString().trim()+"'";
						String sqlCount = CommonFunction.getCommQueryDAO().findCountBySQLQuery(sql);
						if(sqlCount.equals("0")){
							tmp.setMisc("1");
						}
						tmp.setMchnNo(object[1].toString());
						tmp.setTermBranch(object[2].toString());
						tmp.setState(TblTermTmkLogConstants.STATE_INIT);
						tmp.setReqOpr(operator.getOprId());
						tmp.setReqDate(CommonFunction.getCurrentDate());
						tmp.setRecUpdOpr(operator.getOprId());
						tmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
						result.add(tmp);
						count++;
						tmpTermId = object[0].toString().trim();
					}
					if(count > 200)
						break;
				}
				if(t3010BO.initTmkLog(result)) {
					if(count <= 200)
						return Constants.SUCCESS_CODE_CUSTOMIZE+"申请批次号："+batchNo;
					else
						return Constants.SUCCESS_CODE_CUSTOMIZE+"申请批次号："+batchNo+"  "+SysCodeUtil.getErrCode(TblTermTmkLogConstants.T30202_04);
				}
			}
			if(subTxnId.equals("03")) {
				List<Object> list = new ArrayList<Object>();
				//List list = t3010BO.qryTermInfo2(termNo,state,operator, mchtCd, termBranch, startDate, endDate);
				for (int i = 0; i < len; i++) {
					String termId = jsonBean.getJSONDataAt(i).get("termId").toString();
					String recCrtTs = jsonBean.getJSONDataAt(i).get("recCrtTs").toString();
					StringBuffer whereSql = new StringBuffer(" WHERE trim(t1.term_id) not in (select t3.term_id_id from tbl_term_tmk_log t3 where t3.state ='0') and t2.state is not null ")
					.append(" and t1.TERM_BRANCH in " + operator.getBrhBelowId()).append(" and t1.term_sta = '1' ");
					if (mchtCd != null && !mchtCd.trim().equals("")) {
						whereSql.append(" AND t1.MCHT_CD='").append(mchtCd).append("'");
					}
					if (termBranch != null && !termBranch.trim().equals("")) {
						whereSql.append(" AND t1.TERM_BRANCH='").append(termBranch).append("'");
					}
					if (termId != null && !termId.trim().equals("")) {
						whereSql.append(" AND t1.TERM_ID= '")
						.append(termId).append("'");
//						.append(CommonFunction.fillString(termNo, ' ', 12, true)).append("'");
					}
					if (state != null && !state.trim().equals("")){
						if(state.equals("1"))
							whereSql.append(" AND t2.STATE='1'");
						else
							whereSql.append(" AND t2.STATE is null");
					}
					if (recCrtTs != null && !recCrtTs.trim().equals("")) {
						whereSql.append(" AND t1.REC_CRT_TS = ").append(recCrtTs);
					}
					String sql = "SELECT t1.term_id,t1.mcht_cd,t1.term_branch FROM tbl_term_inf_tmp t1 left join tbl_term_tmk_log t2 on trim(t1.term_id)=t2.term_id_id and t2.state!='0'"
					+ whereSql.toString() + " ORDER BY t1.term_id";
					List<Object> list1 = commQueryDAO.findBySQLQuery(sql);
					list.add(list1);
				}
				if(list == null || list.isEmpty())
				return TblTermTmkLogConstants.T30202_03;
				List<TblTermTmkLog> result = new ArrayList<TblTermTmkLog>();
				String tmpTermId = null;
				int count = 0;
				for(int i=0;i<list.size();i++) {
					List<Object> objs = (List<Object>) list.get(i);
					Object[] object = (Object[]) objs.get(0);
					if(!object[0].toString().trim().equals(tmpTermId)){
						TblTermTmkLog tmp = new TblTermTmkLog();
						tmp.setId(new TblTermTmkLogPK(object[0].toString().trim(),batchNo));
						String sql = "select count(*) from tbl_term_tmk_log where term_id_id = '"+object[0].toString().trim()+"'";
						String sqlCount = CommonFunction.getCommQueryDAO().findCountBySQLQuery(sql);
						if(sqlCount.equals("0")){
							tmp.setMisc("1");
						}
						tmp.setMchnNo(object[1].toString());
						tmp.setTermBranch(object[2].toString());
						tmp.setState(TblTermTmkLogConstants.STATE_INIT);
						tmp.setReqOpr(operator.getOprId());
						tmp.setReqDate(CommonFunction.getCurrentDate());
						tmp.setRecUpdOpr(operator.getOprId());
						tmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
						result.add(tmp);
						count++;
						tmpTermId = object[0].toString().trim();
					}
					if(count > 200)
						break;
				}
				if(t3010BO.initTmkLog(result)) {
					if(count <= 200)
						return Constants.SUCCESS_CODE_CUSTOMIZE+"申请批次号："+batchNo;
					else
						return Constants.SUCCESS_CODE_CUSTOMIZE+"申请批次号："+batchNo+"  "+SysCodeUtil.getErrCode(TblTermTmkLogConstants.T30202_04);
				}
			}	
			if(subTxnId.equals("04")) {
//				List list = t3010BO.qryTermInfo3(termNo,state,operator, mchtCd, termBranch, startDate, endDate);
				List<Object> list = new ArrayList<Object>();
				for (int i = 0; i < len; i++) {
					String termId = jsonBean.getJSONDataAt(i).get("termId").toString();
					String recCrtTs = jsonBean.getJSONDataAt(i).get("recCrtTs").toString();
					StringBuffer whereSql = new StringBuffer(" WHERE trim(t1.term_id) not in (select t3.term_id_id from tbl_term_tmk_log t3 where t3.state ='0') and t2.state is  null ")
							.append(" and t1.TERM_BRANCH in " + operator.getBrhBelowId()).append(" and t1.term_sta = '1' ");
					if (mchtCd != null && !mchtCd.trim().equals("")) {
						whereSql.append(" AND t1.MCHT_CD='").append(mchtCd).append("'");
					}
					if (termBranch != null && !termBranch.trim().equals("")) {
						whereSql.append(" AND t1.TERM_BRANCH='").append(termBranch).append("'");
					}
					if (termId != null && !termId.trim().equals("")) {
						whereSql.append(" AND t1.TERM_ID='")
						.append(termId).append("'");
//						.append(CommonFunction.fillString(termNo, ' ', 12, true)).append("'");
					}
					if (state != null && !state.trim().equals("")){
						if(state.equals("1"))
							whereSql.append(" AND t2.STATE='1'");
						else
							whereSql.append(" AND t2.STATE is null");
					}
					if (recCrtTs != null && !recCrtTs.trim().equals("")) {
						whereSql.append(" AND t1.REC_CRT_TS = ").append(recCrtTs);
					}
					
					String sql = "SELECT t1.term_id,t1.mcht_cd,t1.term_branch FROM tbl_term_inf_tmp t1 left join tbl_term_tmk_log t2 on trim(t1.term_id)=t2.term_id_id and t2.state!='0'"
					+ whereSql.toString() + " ORDER BY t1.term_id";
					
					List<Object> list1 = commQueryDAO.findBySQLQuery(sql);
					list.add(list1);
				}
				if(list.size()==0)
					return TblTermTmkLogConstants.T30202_03;
				List<TblTermTmkLog> result = new ArrayList<TblTermTmkLog>();
				String tmpTermId = null;
				int count = 0;
				for(int i=0;i<list.size();i++) {
					List<Object> objs = (List<Object>) list.get(i);
					Object[] object = (Object[]) objs.get(0);
//					Object[] object = (Object[])list.get(i);
					if(!object[0].toString().trim().equals(tmpTermId)){
						TblTermTmkLog tmp = new TblTermTmkLog();
						tmp.setId(new TblTermTmkLogPK(object[0].toString().trim(),batchNo));
						String sql = "select count(*) from tbl_term_tmk_log where term_id_id = '"+object[0].toString().trim()+"'";
						String sqlCount = CommonFunction.getCommQueryDAO().findCountBySQLQuery(sql);
						if(sqlCount.equals("0")){
							tmp.setMisc("1");
						}
						tmp.setMchnNo(object[1].toString());
						tmp.setTermBranch(object[2].toString());
						tmp.setState(TblTermTmkLogConstants.STATE_INIT);
						tmp.setReqOpr(operator.getOprId());
						tmp.setReqDate(CommonFunction.getCurrentDate());
						tmp.setRecUpdOpr(operator.getOprId());
						tmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
						result.add(tmp);
						count++;
						tmpTermId = object[0].toString().trim();
					}
					if(count > 200)
						break;
				}
				if(t3010BO.initTmkLog(result)) {
					if(count <= 200)
						return Constants.SUCCESS_CODE_CUSTOMIZE+"申请批次号："+batchNo;
					else
						return Constants.SUCCESS_CODE_CUSTOMIZE+"申请批次号："+batchNo+"  "+SysCodeUtil.getErrCode(TblTermTmkLogConstants.T30202_04);
				}
			}	
		return Constants.FAILURE_CODE;
	}
}
