/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-7-31       first release
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

import com.huateng.bo.mchnt.T20302BO;
import com.huateng.common.Constants;
import com.huateng.po.mchnt.TblInfMchntTpGrp;
import com.huateng.po.mchnt.TblInfMchntTpGrpPK;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

/**
 * Title:商户组别维护
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-7-31
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author 
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class T20302Action extends BaseAction {
	
	// 商户组别编号
	private String mchntTpGrp;
	private String statusid;
	// 商户组别维护
	private String grpDescr;
	// 商户组别信息集合
	private String mchntTpGrpList;

	
	private T20302BO t20302BO = (T20302BO) ContextUtil.getBean("T20302BO");
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
			}else if("accept".equals(method)) {
				rspCode = accept();
			}else if("refuse".equals(method)) {
				rspCode = refuse();
			}
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "，商户组别维护操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
	
	
	private String accept() {
		String sql1 = "select REC_UPD_USR_ID from TBL_INF_MCHNT_TP_GRP_TMP where MCHNT_TP_GRP = '" + mchntTpGrp + "'";
		System.out.println("更新人SQL："+sql1);
		String findCountBySQLQuery = CommonFunction.getCommQueryDAO().findCountBySQLQuery(sql1);
		System.out.println("更新人："+findCountBySQLQuery);
		if (operator.getOprId().equals(findCountBySQLQuery.trim())) {
			return "提交人与审核人不能是同一个人";
		}
		if (statusid.equals("2")) {
			return "已审核";
		}
		String sql2 = "update TBL_INF_MCHNT_TP_GRP_TMP set STATUSID = '2' where MCHNT_TP_GRP = '" + mchntTpGrp + "'";
		CommonFunction.getCommQueryDAO().excute(sql2);
		String sql3 = "select * from TBL_INF_MCHNT_TP_GRP_TMP where MCHNT_TP_GRP = '" + mchntTpGrp + "'";
		TblInfMchntTpGrpPK id = new TblInfMchntTpGrpPK(mchntTpGrp,"0");
		TblInfMchntTpGrp tblInfMchntTpGrp = t20302BO.get(id);
		String sql4 = "INSERT INTO TBL_INF_MCHNT_TP_GRP "
				+ "(MCHNT_TP_GRP, FRN_IN, DESCR, REC_ST, LAST_OPER_IN, REC_UPD_USR_ID, REC_UPD_TS, REC_CRT_TS) VALUES "
				+ "('"+tblInfMchntTpGrp.getId().getMchntTpGrp()+"', '"+tblInfMchntTpGrp.getId().getFrnIn()+"', '"+tblInfMchntTpGrp.getDescr()+"', '"+Constants.VALID+"', '"+Constants.ADD+"', '"+operator.getOprId()+"', '"+CommonFunction.getCurrentDateTime()+"', '"+CommonFunction.getCurrentDateTime()+"')";
		System.out.println("存储到正式表："+sql4);
		CommonFunction.getCommQueryDAO().excute(sql4);
//		List<Object[]> result = commQueryDAO.findBySQLQuery(sql);
		return Constants.SUCCESS_CODE;
	}


	private String refuse() {
		String sql1 = "select REC_UPD_USR_ID from TBL_INF_MCHNT_TP_GRP_TMP where MCHNT_TP_GRP = '" + mchntTpGrp + "'";
		System.out.println("更新人SQL："+sql1);
		String findCountBySQLQuery = CommonFunction.getCommQueryDAO().findCountBySQLQuery(sql1);
		System.out.println("更新人："+findCountBySQLQuery);
		if (operator.getOprId().equals(findCountBySQLQuery.trim())) {
			return "提交人与审核人不能是同一个人";
		}
		if (statusid.equals("2")) {
			return "已审核";
		}
		String sql2 = "update TBL_INF_MCHNT_TP_GRP_TMP set STATUSID = '5' where MCHNT_TP_GRP = '" + mchntTpGrp + "'";
		CommonFunction.getCommQueryDAO().excute(sql2);
		return Constants.SUCCESS_CODE;
	}


	/**
	 * 添加商户组别信息
	 * @return
	 */
	private String add() {
		/*String sql = "INSERT INTO TBL_INF_MCHNT_TP_GRP_TMP (MCHNT_TP_GRP, FRN_IN, DESCR, REC_ST, LAST_OPER_IN, REC_UPD_USR_ID, REC_UPD_TS, REC_CRT_TS,STATUSID) VALUES ('"+id.getMchntTpGrp()+"', '0', '"+grpDescr+"', '"+Constants.VALID+"', '"+Constants.ADD+"', '"+operator.getOprId()+"', '"+CommonFunction.getCurrentDateTime()+"', '"+CommonFunction.getCurrentDateTime()+"','"+0+"');";
		System.out.println("添加商户组别信息成功。"+sql);
		CommonFunction.getCommQueryDAO().excute(sql);*/
		
		TblInfMchntTpGrp tblInfMchntTpGrp = new TblInfMchntTpGrp();
		TblInfMchntTpGrpPK id = new TblInfMchntTpGrpPK(mchntTpGrp,"0");
		if(t20302BO.get(id) != null) {
			return "您输入的商户类型组别已经存在，请重新输入!";
		}
		tblInfMchntTpGrp.setId(id);
		tblInfMchntTpGrp.setDescr(grpDescr);
		tblInfMchntTpGrp.setRecUpdUsrId(operator.getOprId());	
		tblInfMchntTpGrp.setLastOperIn(Constants.ADD);
		tblInfMchntTpGrp.setRecCrtTs(CommonFunction.getCurrentDateTime());
		tblInfMchntTpGrp.setRecSt(Constants.VALID);
		tblInfMchntTpGrp.setRecUpdTs(CommonFunction.getCurrentDateTime());
		tblInfMchntTpGrp.setStatusid("0");
		log("添加商户组别信息成功。操作员编号：" + operator.getOprId());
		t20302BO.add(tblInfMchntTpGrp);		
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 删除商户组别信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String delete() {
		
		String sql = "select * from tbl_inf_mchnt_tp_tmp where mchnt_tp_grp = '" + mchntTpGrp + "'";
		
		List<Object[]> result = commQueryDAO.findBySQLQuery(sql);
		
		if(result.size() > 0) {
			return "请先清空该组别下的商户MCC,再删除该组别";
		}
		
		TblInfMchntTpGrpPK id = new TblInfMchntTpGrpPK(mchntTpGrp,"0");
		
		t20302BO.delete(id);
		
		String sql1 = "delete from tbl_inf_mchnt_tp where mchnt_tp_grp = '" + mchntTpGrp + "'";
		CommonFunction.getCommQueryDAO().excute(sql1);
		log("删除商户组别信息成功。操作员编号：" + operator.getOprId());
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 同步商户组别信息
	 * @return
	 */
	private String update() {
		
		jsonBean.parseJSONArrayData(mchntTpGrpList);
		
		int len = jsonBean.getArray().size();
		
		TblInfMchntTpGrp tblMchntTpGrp = null;
		
		List<TblInfMchntTpGrp> tblMchntTpGrpList = new ArrayList<TblInfMchntTpGrp>(len);
		for(int i = 0; i < len; i++) {
			TblInfMchntTpGrpPK id =  new TblInfMchntTpGrpPK(jsonBean.getJSONDataAt(i).getString("mchntTpGrp"),"0");
			tblMchntTpGrp = t20302BO.get(id);
//			tblMchntTpGrp.getStatusid();
			if (tblMchntTpGrp.getStatusid().equals("2")) {
				continue;
			}
			tblMchntTpGrp.setDescr(jsonBean.getJSONDataAt(i).getString("descr"));
			tblMchntTpGrpList.add(tblMchntTpGrp);
		}
		t20302BO.update(tblMchntTpGrpList);
		log("同步商户组别信息成功。操作员编号：" + operator.getOprId());
		return Constants.SUCCESS_CODE;
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
	 * @return the grpDescr
	 */
	public String getGrpDescr() {
		return grpDescr;
	}

	/**
	 * @param grpDescr the grpDescr to set
	 */
	public void setGrpDescr(String grpDescr) {
		this.grpDescr = grpDescr;
	}


	/**
	 * @return the mchntTpGrpList
	 */
	public String getMchntTpGrpList() {
		return mchntTpGrpList;
	}


	/**
	 * @param mchntTpGrpList the mchntTpGrpList to set
	 */
	public void setMchntTpGrpList(String mchntTpGrpList) {
		this.mchntTpGrpList = mchntTpGrpList;
	}


	public String getStatusid() {
		return statusid;
	}


	public void setStatusid(String statusid) {
		this.statusid = statusid;
	}
	
}
