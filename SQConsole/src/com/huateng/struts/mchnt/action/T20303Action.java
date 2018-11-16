/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-8-1       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2010 Huateng Software, Inc. All rights reserved.
 *
 *       This software is the confidential and proprietary information of
 *       Shanghai HUATENG Software Co., Ltd. ("Confidential Information").
 *       You shall not disclose such Confidential Information and shall use it
 *       only in accordance with the terms of the license agreement you entered
 *       into with Huateng.
 *
 * Warning:
 * =============================================================================
 *
 */
package com.huateng.struts.mchnt.action;

import java.util.ArrayList;
import java.util.List;

import com.huateng.bo.mchnt.T20303BO;
import com.huateng.common.Constants;
import com.huateng.po.mchnt.TblInfMchntTp;
import com.huateng.po.mchnt.TblInfMchntTpPK;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

/**
 * Title:商户MCC维护
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-1
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class T20303Action extends BaseAction {
	
	private T20303BO t20303BO = (T20303BO) ContextUtil.getBean("T20303BO");
	
	/* (non-Javadoc)
	 * @see com.huateng.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute(){
		try {
			if("add".equals(method)) {
				rspCode = add();
			} else if("delete".equals(method)) {
				rspCode = delete();
			} else if("update".equals(method)) {
				rspCode = update();
			} else if("accept".equals(method)) {
				rspCode = accept();
			} else if("refuse".equals(method)) {
				rspCode = refuse();
			}
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "，商户MCC维护操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
	
	private String refuse() {
		String sql1 = "select REC_UPD_USR_ID from TBL_INF_MCHNT_TP_TMP where MCHNT_TP = '" + mchntTp + "'";
		System.out.println("更新人SQL："+sql1);
		String findCountBySQLQuery = CommonFunction.getCommQueryDAO().findCountBySQLQuery(sql1);
		System.out.println("更新人："+findCountBySQLQuery);
		if (operator.getOprId().equals(findCountBySQLQuery.trim())) {
			return "提交人与审核人不能是同一个人";
		}
		if (statusidmcc.equals("2")) {
			return "已审核";
		}
		String sql2 = "update TBL_INF_MCHNT_TP_TMP set STATUSIDMCC = '5' where MCHNT_TP = '" + mchntTp + "'";
		CommonFunction.getCommQueryDAO().excute(sql2);
		return Constants.SUCCESS_CODE;
	}

	private String accept() {
		String sql1 = "select REC_UPD_USR_ID from TBL_INF_MCHNT_TP_TMP where MCHNT_TP = '" + mchntTp + "'";
		System.out.println("更新人SQL："+sql1);
		String rec_upd_usr_id = CommonFunction.getCommQueryDAO().findCountBySQLQuery(sql1);
		System.out.println("更新人："+rec_upd_usr_id);
		if (operator.getOprId().equals(rec_upd_usr_id.trim())) {
			return "提交人与审核人不能是同一个人";
		}
		if (statusidmcc.equals("2")) {
			return "已审核";
		}
		if (statusidmcc.equals("5")) {
			return "已拒绝";
		}
		String sql2 = "update TBL_INF_MCHNT_TP_TMP set STATUSIDMCC = '2' where MCHNT_TP = '" + mchntTp + "'";
		CommonFunction.getCommQueryDAO().excute(sql2);
		TblInfMchntTpPK tblInfMchntTpPK = new TblInfMchntTpPK(mchntTp,"0");
		TblInfMchntTp tblInfMchntTp = t20303BO.get(tblInfMchntTpPK);
		String sql3 = "INSERT INTO TBL_INF_MCHNT_TP "
				+ "(MCHNT_TP, FRN_IN, MCHNT_TP_GRP, DESCR, REC_ST, LAST_OPER_IN, REC_UPD_USR_ID, REC_UPD_TS, REC_CRT_TS) VALUES "
				+ "('"+tblInfMchntTp.getId().getMchntTp()+"', '"+tblInfMchntTp.getId().getFrnIn()+"', '"+tblInfMchntTp.getMchntTpGrp()+"', '"+tblInfMchntTp.getDescr()+"', '"+tblInfMchntTp.getRecSt()+"', '"+tblInfMchntTp.getLastOperIn()+"', '"+tblInfMchntTp.getRecUpdUsrId()+"', '"+tblInfMchntTp.getRecUpdTs()+"', '"+tblInfMchntTp.getRecCrtTs()+"')";
		System.out.println("存储到正式表："+sql3);
		CommonFunction.getCommQueryDAO().excute(sql3);
		return Constants.SUCCESS_CODE;
	}

	/**
	 * 添加商户MCC信息
	 * @return
	 */
	private String add() {
		TblInfMchntTpPK tblInfMchntTpPK = new TblInfMchntTpPK(mchntTp,"0");
		if(t20303BO.get(tblInfMchntTpPK) != null) {
			return "该商户MCC编号已经被使用";
		}
		TblInfMchntTp tblInfMchntTp = new TblInfMchntTp();
		tblInfMchntTp.setId(tblInfMchntTpPK);
		tblInfMchntTp.setDescr(mccDescr);
		tblInfMchntTp.setMchntTpGrp(mchntTpGrp);
		tblInfMchntTp.setRecUpdUsrId(operator.getOprId());
		tblInfMchntTp.setLastOperIn(Constants.ADD);
		tblInfMchntTp.setRecCrtTs(CommonFunction.getCurrentDateTime());
		tblInfMchntTp.setRecUpdTs(CommonFunction.getCurrentDateTime());
		tblInfMchntTp.setRecSt(Constants.VALID);
		tblInfMchntTp.setStatusidmcc("0");
		t20303BO.add(tblInfMchntTp);
		log("添加商户组别信息成功。操作员编号：" + operator.getOprId());
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 删除商户MCC信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String delete() {
		
		String sql = "select * from tbl_mcht_base_inf_tmp where tcc = '" + mchntTp + "'";
		
		List<Object[]> result = commQueryDAO.findBySQLQuery(sql);
		
		if(result.size() > 0) {
			return "该商户MCC编号已经被商户信息使用，无法删除";
		}
		TblInfMchntTpPK tblInfMchntTpPK = new TblInfMchntTpPK(mchntTp,"0");
		t20303BO.delete(tblInfMchntTpPK);
		String sql1 = "delete from TBL_INF_MCHNT_TP where MCHNT_TP = '" + mchntTp + "'";
		System.out.println("删除正式表："+sql1);
		CommonFunction.getCommQueryDAO().excute(sql1);
		log("删除商户组别信息成功。操作员编号：" + operator.getOprId());
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 同步商户MCC信息
	 * @return
	 */
	private String update() {

		jsonBean.parseJSONArrayData(mchntTpList);
		
		int len = jsonBean.getArray().size();
		
		TblInfMchntTp tblMchntTp = null;
		
		List<TblInfMchntTp> tblMchntTpList = new ArrayList<TblInfMchntTp>(len);
		for(int i = 0; i < len; i++) {
			TblInfMchntTpPK pk = new TblInfMchntTpPK(jsonBean.getJSONDataAt(i).getString("mchntTp"),"0");
			tblMchntTp = t20303BO.get(pk);
			tblMchntTp.setMchntTpGrp(jsonBean.getJSONDataAt(i).getString("mchntTpGrp"));
			tblMchntTp.setDescr(jsonBean.getJSONDataAt(i).getString("descr"));
			if (tblMchntTp.getStatusidmcc().equals("2")) {
				continue;
			}
			tblMchntTpList.add(tblMchntTp);
		}
		t20303BO.update(tblMchntTpList);
		log("同步商户组别信息成功。操作员编号：" + operator.getOprId());
		return Constants.SUCCESS_CODE;
	}
	
	// 商户MCC编号
	private String mchntTp;
	// 商户组别编号
	private String mchntTpGrp;
	// 商户MCC描述
	private String mccDescr;
	// 商户组别集合
	private String mchntTpList;
	
	private String statusidmcc;

	public String getStatusidmcc() {
		return statusidmcc;
	}

	public void setStatusidmcc(String statusidmcc) {
		this.statusidmcc = statusidmcc;
	}

	/**
	 * @return the mchntTp
	 */
	public String getMchntTp() {
		return mchntTp;
	}

	/**
	 * @param mchntTp the mchntTp to set
	 */
	public void setMchntTp(String mchntTp) {
		this.mchntTp = mchntTp;
	}

	/**
	 * @return the mchntTpGrp
	 */
	public String getMchntTpGrp() {
		return mchntTpGrp;
	}

	/**
	 * @param mchntTpGrp the mchntTpGrp to set
	 */
	public void setMchntTpGrp(String mchntTpGrp) {
		this.mchntTpGrp = mchntTpGrp;
	}

	/**
	 * @return the mccDescr
	 */
	public String getMccDescr() {
		return mccDescr;
	}

	/**
	 * @param mccDescr the mccDescr to set
	 */
	public void setMccDescr(String mccDescr) {
		this.mccDescr = mccDescr;
	}

	/**
	 * @return the mchntTpList
	 */
	public String getMchntTpList() {
		return mchntTpList;
	}

	/**
	 * @param mchntTpList the mchntTpList to set
	 */
	public void setMchntTpList(String mchntTpList) {
		this.mchntTpList = mchntTpList;
	}
	
	
}
