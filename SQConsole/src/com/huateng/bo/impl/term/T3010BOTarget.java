package com.huateng.bo.impl.term;

import java.util.List;
import org.apache.struts2.ServletActionContext;
import com.huateng.bo.term.T3010BO;
import com.huateng.common.Constants;
import com.huateng.common.Operator;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.dao.iface.mchnt.TblMchntInfoDAO;
import com.huateng.dao.iface.mchnt.TblMchntInfoTmpDAO;
import com.huateng.dao.iface.term.TblTermInfDAO;
import com.huateng.dao.iface.term.TblTermInfTmpDAO;
import com.huateng.dao.iface.term.TblTermRefuseDAO;
import com.huateng.dao.iface.term.TblTermTmkLogDAO;
import com.huateng.po.TblTermInf;
import com.huateng.po.TblTermInfTmp;
import com.huateng.po.TblTermInfTmpPK;
import com.huateng.po.TblTermRefuse;
import com.huateng.po.TblTermRefusePK;
import com.huateng.po.TblTermTmkLog;
import com.huateng.po.TblTermTmkLogPK;
import com.huateng.po.mchnt.TblMchtBaseInf;
import com.huateng.po.mchnt.TblMchtBaseInfTmp;
import com.huateng.struts.pos.TblTermInfConstants;
import com.huateng.struts.pos.TblTermTmkLogConstants;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.GenerateNextId;
import com.huateng.system.util.StatusUtil;

/**
 * Title:终端信息管理
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-16
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @version 1.0
 */
public class T3010BOTarget implements T3010BO {
	
	private TblTermInfTmpDAO tblTermInfTmpDAO;
	private ICommQueryDAO commQueryDAO;
	private TblTermInfDAO tblTermInfDAO;

	public void setTblMchntInfoDAO(TblMchntInfoDAO tblMchntInfoDAO) {
		this.tblMchntInfoDAO = tblMchntInfoDAO;
	}

	public void setTblMchntInfoTmpDAO(TblMchntInfoTmpDAO tblMchntInfoTmpDAO) {
		this.tblMchntInfoTmpDAO = tblMchntInfoTmpDAO;
	}

	private TblTermTmkLogDAO tblTermTmkLogDAO;
	private TblTermRefuseDAO tblTermRefuseDAO;
	private TblMchntInfoDAO tblMchntInfoDAO;
	private TblMchntInfoTmpDAO tblMchntInfoTmpDAO;
	
	
	public TblMchtBaseInfTmp getMchnt(String mchntId){
		return tblMchntInfoTmpDAO.get(mchntId);
	}
	public TblMchtBaseInf get(String key){
		
		return tblMchntInfoDAO.get(key);
	}
	
	/**
	 * @param tblTermRefuseDAO the tblTermRefuseDAO to set
	 */
	public void setTblTermRefuseDAO(TblTermRefuseDAO tblTermRefuseDAO) {
		this.tblTermRefuseDAO = tblTermRefuseDAO;
	}

	/**
	 * @param tblTermTmkLogDAO the tblTermTmkLogDAO to set
	 */
	public void setTblTermTmkLogDAO(TblTermTmkLogDAO tblTermTmkLogDAO) {
		this.tblTermTmkLogDAO = tblTermTmkLogDAO;
	}

	/**
	 * @param commQueryDAO the commQueryDAO to set
	 */
	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}

	/**
	 * @param tblTermInfDAO the tblTermInfDAO to set
	 */
	public void setTblTermInfDAO(TblTermInfDAO tblTermInfDAO) {
		this.tblTermInfDAO = tblTermInfDAO;
	}

	/**
	 * @param tblTermInfTmpDAO the tblTermInfTmpDAO to set
	 */
	public void setTblTermInfTmpDAO(TblTermInfTmpDAO tblTermInfTmpDAO) {
		this.tblTermInfTmpDAO = tblTermInfTmpDAO;
	}

	public String add(TblTermInfTmp tblTermInfTmp) {
		return tblTermInfTmpDAO.save(tblTermInfTmp).getTermId();
	}

	public TblTermInfTmp get(String termId,String recCrtTs) {
		TblTermInfTmpPK pk= new TblTermInfTmpPK(termId,recCrtTs);
		return tblTermInfTmpDAO.get(pk);
	}
	//得到真实表
	public TblTermInf get1(String termId) {
		return tblTermInfDAO.get(termId);
	}
	public boolean update(TblTermInfTmp tblTermInfTmp,String termSta) {
		if(termSta != null)
			tblTermInfTmp.setTermSta(termSta);
		tblTermInfTmpDAO.update(tblTermInfTmp);
		return true;
	}
	//正式表
    public boolean update(TblTermInf tblTermInf,String termSta) {
		if(termSta != null){
			tblTermInf.setTermSta(termSta);
		}
		tblTermInfDAO.update(tblTermInf);
		return true;
	}
	
	public String getTermId(String brhId) {
		if(brhId == null || brhId.trim().equals(""))
			return Constants.FAILURE_CODE;
		if(brhId.length()>2)
			brhId = brhId.substring(0, 2);
		String sql = "select max(substr(term_Id,3,6)) from TBL_TERM_INF_TMP where substr(term_id,0,2)='"+brhId+"'";
		List list = commQueryDAO.findBySQLQuery(sql);
		if(list == null || list.isEmpty() || list.get(0) == null)
			return brhId+CommonFunction.fillString("1", '0', 6, false);
		int max = Integer.parseInt(list.get(0).toString())+1;
		if(max > 999999)
			max = 1;
		return brhId + CommonFunction.fillString(String.valueOf(max), '0', 6, false);
	}

	public boolean save(TblTermInfTmp tblTermInfTmp, String oprId,String termBatchNm,String termSignSta ) {
		TblTermInf tblTermInf = new TblTermInf();
//		if(termBatchNm != null)
//		{
//			tblTermInfTmp.setTermBatchNm(termBatchNm);
//			String param = tblTermInfTmp.getTermPara();
//			String[] array = param.split("\\|");
//			array[8] = "31"+termBatchNm;
//			param = "";
//			for(String tmp : array)
//			{
//				param += tmp+"|";
//			}
//			tblTermInfTmp.setTermPara(param);
//		}
		tblTermInf = (TblTermInf) tblTermInfTmp.clone();
		if(termSignSta != null)
			tblTermInf.setTermSignSta(termSignSta);
//		tblTermInf.setTermPara(tblTermInfTmp.getTermPara().replaceAll("\\|", ""));
		try{
			TblTermInf old =getTermInfo(tblTermInfTmp.getId().getTermId());
			if(old!=null){
				tblTermInf.setTermBatchNm(old.getTermBatchNm());//由于pos结算时只更新正式表中的批次号，所以在做修改审核是需要特殊处理批次号
				tblTermInf.setTermSignSta(old.getTermSignSta());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		tblTermInf.setRecUpdOpr(oprId);
		tblTermInf.setRecUpdTs(CommonFunction.getCurrentDate());
//		tblTermInfDAO.saveOrUpdate(tblTermInf);
		tblTermInfDAO.clear(tblTermInf);
		return true;
	}

	public List qryTermInfo(String termNo, String state, Operator operator, String mchtCd, String termBranch, String startDate,String endDate) {
		StringBuffer whereSql = new StringBuffer(" WHERE trim(t1.term_id) not in (select t3.term_id_id from tbl_term_tmk_log t3 where t3.state ='0')").append(" and t1.TERM_BRANCH in " + operator.getBrhBelowId()).append(" and t1.term_sta = '1' ");
		if (mchtCd != null && !mchtCd.trim().equals("")) {
			whereSql.append(" AND t1.MCHT_CD='").append(mchtCd).append("'");
		}
		if (termBranch != null && !termBranch.trim().equals("")) {
			whereSql.append(" AND t1.TERM_BRANCH='").append(termBranch).append("'");
		}
		if (termNo != null && !termNo.trim().equals("")) {
			whereSql.append(" AND t1.TERM_ID='")
			.append(CommonFunction.fillString(termNo, ' ', 12, true)).append("'");
		}
		if (state != null && !state.trim().equals("")){
			if(state.equals("1"))
				whereSql.append(" AND t2.STATE='1'");
			else
				whereSql.append(" AND t2.STATE is null");
		}
		if (startDate != null && !startDate.trim().equals("")) {
			whereSql.append(" AND t1.REC_CRT_TS>").append(startDate);
		}
		if (endDate != null && !endDate.trim().equals("")) {
			whereSql.append(" AND t1.REC_CRT_TS<").append(endDate);
		}
		
		String sql = "SELECT t1.term_id,t1.mcht_cd,t1.term_branch FROM tbl_term_inf_tmp t1 left join tbl_term_tmk_log t2 on trim(t1.term_id)=t2.term_id_id and t2.state!='0'"
		+ whereSql.toString() + " ORDER BY t1.term_id";
		
		List list = commQueryDAO.findBySQLQuery(sql);
		return list;
	}
	
	//已申请验证码  批量申请
	public List qryTermInfo2(String termNo, String state, Operator operator, String mchtCd, String termBranch, String startDate,String endDate) {
	//	StringBuffer whereSql = new StringBuffer(" WHERE trim(t1.term_id) not in (select t3.term_id_id from tbl_term_tmk_log t3 where t3.state ='0')and (t1.psam_id is not null  and  trim(t1.psam_id) <> ' '  )")
		StringBuffer whereSql = new StringBuffer(" WHERE trim(t1.term_id) not in (select t3.term_id_id from tbl_term_tmk_log t3 where t3.state ='0') and t2.state is not null ")
				.append(" and t1.TERM_BRANCH in " + operator.getBrhBelowId()).append(" and t1.term_sta = '1' ");
		if (mchtCd != null && !mchtCd.trim().equals("")) {
			whereSql.append(" AND t1.MCHT_CD='").append(mchtCd).append("'");
		}
		if (termBranch != null && !termBranch.trim().equals("")) {
			whereSql.append(" AND t1.TERM_BRANCH='").append(termBranch).append("'");
		}
		if (termNo != null && !termNo.trim().equals("")) {
			whereSql.append(" AND t1.TERM_ID='")
			.append(CommonFunction.fillString(termNo, ' ', 12, true)).append("'");
		}
		if (state != null && !state.trim().equals("")){
			if(state.equals("1"))
				whereSql.append(" AND t2.STATE='1'");
			else
				whereSql.append(" AND t2.STATE is null");
		}
		if (startDate != null && !startDate.trim().equals("")) {
			whereSql.append(" AND t1.REC_CRT_TS>").append(startDate);
		}
		if (endDate != null && !endDate.trim().equals("")) {
			whereSql.append(" AND t1.REC_CRT_TS<").append(endDate);
		}
		
		String sql = "SELECT t1.term_id,t1.mcht_cd,t1.term_branch FROM tbl_term_inf_tmp t1 left join tbl_term_tmk_log t2 on trim(t1.term_id)=t2.term_id_id and t2.state!='0'"
		+ whereSql.toString() + " ORDER BY t1.term_id";
		List list = commQueryDAO.findBySQLQuery(sql);
		return list;
	}
	
	//未申请验证码  批量申请
	public List qryTermInfo3(String termNo, String state, Operator operator, String mchtCd, String termBranch, String startDate,String endDate) {
	//	StringBuffer whereSql = new StringBuffer(" WHERE trim(t1.term_id) not in (select t3.term_id_id from tbl_term_tmk_log t3 where t3.state ='0') and (t1.psam_id is  null  or  trim(t1.psam_id) = ' '  )")
		StringBuffer whereSql = new StringBuffer(" WHERE trim(t1.term_id) not in (select t3.term_id_id from tbl_term_tmk_log t3 where t3.state ='0') and t2.state is  null ")
				.append(" and t1.TERM_BRANCH in " + operator.getBrhBelowId()).append(" and t1.term_sta = '1' ");
		if (mchtCd != null && !mchtCd.trim().equals("")) {
			whereSql.append(" AND t1.MCHT_CD='").append(mchtCd).append("'");
		}
		if (termBranch != null && !termBranch.trim().equals("")) {
			whereSql.append(" AND t1.TERM_BRANCH='").append(termBranch).append("'");
		}
		if (termNo != null && !termNo.trim().equals("")) {
			whereSql.append(" AND t1.TERM_ID='")
			.append(CommonFunction.fillString(termNo, ' ', 12, true)).append("'");
		}
		if (state != null && !state.trim().equals("")){
			if(state.equals("1"))
				whereSql.append(" AND t2.STATE='1'");
			else
				whereSql.append(" AND t2.STATE is null");
		}
		if (startDate != null && !startDate.trim().equals("")) {
			whereSql.append(" AND t1.REC_CRT_TS>").append(startDate);
		}
		if (endDate != null && !endDate.trim().equals("")) {
			whereSql.append(" AND t1.REC_CRT_TS<").append(endDate);
		}
		
		String sql = "SELECT t1.term_id,t1.mcht_cd,t1.term_branch FROM tbl_term_inf_tmp t1 left join tbl_term_tmk_log t2 on trim(t1.term_id)=t2.term_id_id and t2.state!='0'"
		+ whereSql.toString() + " ORDER BY t1.term_id";
		
		List list = commQueryDAO.findBySQLQuery(sql);
		return list;
	}

	public boolean initTmkLog(TblTermTmkLog tblTermTmkLog) {
		tblTermTmkLogDAO.save(tblTermTmkLog);
		return true;
	}

	public String getBatchNo() {
		String sql = "select max(substr(BATCH_NO,9,12)) from TBL_TERM_TMK_LOG where substr(BATCH_NO,0,8)='"+CommonFunction.getCurrentDate()+"'";
		List list = commQueryDAO.findBySQLQuery(sql);
		if(list == null || list.isEmpty() || list.get(0) == null)
			return CommonFunction.getCurrentDate()+CommonFunction.fillString("1", '0', 4, false);
		int max = Integer.parseInt(list.get(0).toString())+1;
		if(max > 999999)
			max = 1;
		return CommonFunction.getCurrentDate() + CommonFunction.fillString(String.valueOf(max), '0', 4, false);
	}

	public String chkTmkLog(String termIdId, String batchNo, String oprId) {
		TblTermTmkLogPK pk = new TblTermTmkLogPK(termIdId,batchNo);
		TblTermTmkLog tblTermTmkLog = tblTermTmkLogDAO.get(pk);
		if(tblTermTmkLog == null){
			System.out.println("TBL_TERM_TMK_LOG为空！");
			return TblTermTmkLogConstants.T30203_01;
		}
		if(tblTermTmkLog.getReqOpr().equals(oprId.trim())){
			System.out.println("申请人员相同！");
			return TblTermTmkLogConstants.T30203_02;
		}
		if(tblTermTmkLog.getState().equals(TblTermTmkLogConstants.STATE_RUN)){
			System.out.println("状态相同！");
			return TblTermTmkLogConstants.T30203_04;
		}
		tblTermTmkLog.setChkOpr(oprId);
		tblTermTmkLog.setChkDate(CommonFunction.getCurrentDate());
		tblTermTmkLog.setState(TblTermTmkLogConstants.STATE_RUN);
		tblTermTmkLog.setRecUpdOpr(oprId);
		tblTermTmkLog.setRecUpdTs(CommonFunction.getCurrentDateTime());
		tblTermTmkLogDAO.update(tblTermTmkLog);
		String randomStr=CommonFunction.getRandomNum(8);
		String chkSqlTmp="update TBL_TERM_INF_TMP set PSAM_ID='"+randomStr+"' where TERM_ID="+termIdId;
		CommonFunction.getCommQueryDAO().excute(chkSqlTmp);
		
		String chkSql="update TBL_TERM_INF set PSAM_ID='"+randomStr+"' where TERM_ID="+termIdId;
		CommonFunction.getCommQueryDAO().excute(chkSql);
		return Constants.SUCCESS_CODE;
	}

	public String chkTmkLog(String batchNo, String oprId) {
		List<TblTermTmkLog> list = commQueryDAO.findBySQLQuery("select req_opr from TBL_TERM_TMK_LOG t where t.batch_no='"+batchNo+"' and t.req_opr!='"+oprId+"'");
		if(list==null||list.isEmpty())
			return TblTermTmkLogConstants.T30203_02;
		StringBuffer sql = new StringBuffer("update tbl_term_tmk_log set state=").append(TblTermTmkLogConstants.STATE_RUN)
		.append(",chk_opr = '").append(oprId).append("',chk_date = ").append(CommonFunction.getCurrentDate())
		.append(",rec_upd_opr = '").append(oprId).append("',rec_upd_ts = ").append(CommonFunction.getCurrentDateTime())
		.append(" where batch_no='").append(batchNo).append("' and state='").append(TblTermTmkLogConstants.STATE_INIT)
		.append("' and req_opr!='").append(oprId.trim()).append("'");
		commQueryDAO.excute(sql.toString());
		
		String sqlId="select TERM_ID_ID from TBL_TERM_TMK_LOG where BATCH_NO='"+batchNo+"'  and CHK_OPR='"+oprId+"'";
		List<String> listTermId=commQueryDAO.findBySQLQuery(sqlId);
		for(int i=0;i<listTermId.size();i++){
			String randomStr=CommonFunction.getRandomNum(8);
			String chkSql1="update TBL_TERM_INF_TMP set PSAM_ID='"+randomStr+"' where TERM_ID="+listTermId.get(i);
			CommonFunction.getCommQueryDAO().excute(chkSql1);
			
			String chkSql2="update TBL_TERM_INF set PSAM_ID='"+randomStr+"' where TERM_ID="+listTermId.get(i);
			CommonFunction.getCommQueryDAO().excute(chkSql2);
		}
		return Constants.SUCCESS_CODE;
	}

	public boolean initTmkLog(List<TblTermTmkLog> list) {
		if(list != null && !list.isEmpty())	{
			tblTermTmkLogDAO.batchSave(list);
			return true;
		}
		return false;
	}

	public TblTermInf getTermInfo(String termId) {
		if(termId!=null&&!"".equals(termId))
			return tblTermInfDAO.get(CommonFunction.fillString(termId, ' ', 12, true)); 
		return null;
	}

	public String delTmkLog(String termIdId, String batchNo, String oprId) {
		TblTermTmkLog tblTermTmkLog = tblTermTmkLogDAO.get(new TblTermTmkLogPK(termIdId,batchNo));
		if(tblTermTmkLog.getReqOpr().equals(oprId))	{
			return TblTermTmkLogConstants.T30203_05;
		}
		tblTermTmkLogDAO.delete(tblTermTmkLog);
		return Constants.SUCCESS_CODE;
	}
	public String delTmkLog(String batchNo, String oprId) {
		List<TblTermTmkLog> list = commQueryDAO.findBySQLQuery("select req_opr from TBL_TERM_TMK_LOG t where t.batch_no='"+batchNo+"' and t.req_opr!='"+oprId+"'");
		if(list==null||list.isEmpty())
			return TblTermTmkLogConstants.T30203_02;
		String strSql = "delete from TBL_TERM_TMK_LOG where batch_no = "+batchNo+"";
		try {
			commQueryDAO.excute(strSql);
		} catch (Exception e) {
			e.printStackTrace();
			return "批量拒绝失败！";
		}
//		String strSql = "select * from TBL_TERM_TMK_LOG where BATCH_NO = "+ batchNo +" ";
//		List<TblTermTmkLog> list = commQueryDAO.find(strSql);
////		TblTermTmkLog tblTermTmkLog = tblTermTmkLogDAO.get(new TblTermTmkLogPK(termIdId,batchNo));
//		if(tblTermTmkLog.getReqOpr().equals(oprId))	{
//			return TblTermTmkLogConstants.T30203_05;
//		}
//		tblTermTmkLogDAO.delete(tblTermTmkLog);
		return Constants.SUCCESS_CODE;
	}
	
	public String refuseLog( String termId,String refuseInfo,String refuseType) {
		TblTermRefusePK tblTermRefusePK=new TblTermRefusePK(CommonFunction.getCurrentDateTime(),termId);
		TblTermRefuse tblTermRefuse=new TblTermRefuse();
		Operator opr = (Operator) ServletActionContext.getRequest().getSession().getAttribute(Constants.OPERATOR_INFO);
		tblTermRefuse.setId(tblTermRefusePK);
		tblTermRefuse.setBrhId(opr.getOprBrhId());
		tblTermRefuse.setOprId(opr.getOprId());
		tblTermRefuse.setRefuseInfo(refuseInfo);
		tblTermRefuse.setRefuseType(StatusUtil.getNextStatus("TM."+refuseType));
		tblTermRefuseDAO.save(tblTermRefuse);
		return Constants.SUCCESS_CODE;
	}

	public boolean chkTmkLog(String termId){
		StringBuffer hql = new StringBuffer("from TblTermTmkLog a")
				.append(" where a.State='").append(TblTermTmkLogConstants.STATE_INIT).append("'")
				.append(" and a.Id.TermIdId='").append(termId).append("'");
		List list = tblTermTmkLogDAO.findByHQLQuery(hql.toString());
		if(list!=null&&!list.isEmpty())
			return false;
		return true;
	}

	public String refuse(TblTermInfTmpPK pk, String state,String oprId) {//终端审核拒绝
		if(state.equals(TblTermInfConstants.TERM_STA_ADD_REFUSE)){//新增审核拒绝
			TblTermInfTmp tblTermInfTmp = tblTermInfTmpDAO.get(pk);
			tblTermInfTmp.setTermSta(TblTermInfConstants.TERM_STA_ADD_REFUSE);
			//20120807审核拒绝时更新审核人和审核时间字段
			tblTermInfTmp.setRecUpdOpr(oprId);
			tblTermInfTmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
			
			tblTermInfTmpDAO.update(tblTermInfTmp);
			//tblTermInfTmpDAO.delete(pk);
			return Constants.SUCCESS_CODE;
		}
		if(state.equals(TblTermInfConstants.TERM_STA_MOD_REFUSE)){
			TblTermInf tblTermInf = tblTermInfDAO.get(pk.getTermId());
			TblTermInfTmp tblTermInfTmp = tblTermInfTmpDAO.get(pk);
			
			/*String param = tblTermInf.getTermPara();
			String param1 = tblTermInfTmp.getTermPara();*/
			
			tblTermInfTmp = (TblTermInfTmp)tblTermInf.clone(tblTermInfTmp);
			//20120807审核拒绝时更新审核人和审核时间字段
			tblTermInfTmp.setRecUpdOpr(oprId);
			tblTermInfTmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
			
			/*String[] array = param1.split("\\|");
			StringBuffer result = new StringBuffer("");
			int current = 0;
			for(int i=0;i<array.length;i++){
				result.append(param.substring(current, current+array[i].length())).append("|");
				current += array[i].length();
			}
			tblTermInfTmp.setTermPara(result.toString());*/
			tblTermInfTmpDAO.update(tblTermInfTmp);	
			return Constants.SUCCESS_CODE;
		}
		return Constants.FAILURE_CODE;
	}
	
	public void delete(TblTermInfTmpPK tblTermInfTmpPK){
		tblTermInfTmpDAO.delete(tblTermInfTmpPK);
	}

	public TblTermInfTmp get2(TblTermInfTmpPK tblTermInfTmpPK) {
		return tblTermInfTmpDAO.get(tblTermInfTmpPK);
	}

	public void update(TblTermInfTmp tblTermInfTmp) {
		 tblTermInfTmpDAO.update(tblTermInfTmp);
	}

	public void clear(TblTermInf tblTermInf) {
		
	}
	
	public String getTermNo() {
		// TODO Auto-generated method stub
/*		String no = "0000001";
		String sql = "SELECT substr(term_id,2,7) FROM tbl_term_inf_tmp where substr(term_id,1,1) ='3' ORDER BY substr(term_id,2,7)";
		List<String> termNos = commQueryDAO.findBySQLQuery(sql);
		if (termNos != null && termNos.size() > 0 && termNos.get(0) != null) {
			no = GenerateNextId.genNo(termNos, "", 7);
		}
		return "3"+no;*/
		//测试修改BY张骏恺20140828
		String no = "10000001";
		String sql = "SELECT substr(term_id,1,8) FROM tbl_term_inf_tmp ORDER BY substr(term_id,1,8)";
		List<String> termNos = commQueryDAO.findBySQLQuery(sql);
		if (termNos != null && termNos.size() > 0 && termNos.get(0) != null) {
			no = GenerateNextId.genTermNo(termNos, "", 8);
		}
		return no;
	}

}
