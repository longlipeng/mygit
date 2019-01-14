/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-7-30       first release
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
package com.huateng.struts.base.action;

import java.lang.reflect.InvocationTargetException;

import com.huateng.bo.base.T10401BO;
import com.huateng.common.Constants;
import com.huateng.common.SysParamConstants;
import com.huateng.po.TblOprInfo;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.Encryption;
import com.huateng.system.util.SysParamUtil;

/**
 * Title:操作员信息维护
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-7-30
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author 
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class T10401Action extends BaseAction {
	
	private T10401BO t10401BO = (T10401BO) ContextUtil.getBean("T10401BO");
	
	/* (non-Javadoc)
	 * @see com.huateng.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws Exception {
		try {
			if("add".equals(method)) {
				rspCode = add();
			} else if("delete".equals(method)) {
				rspCode = delete();
			} else if("update".equals(method)) {
				rspCode = update();
			} else if("edit".equals(method)) {
				rspCode = edit();
			} else if("reset".equals(method)) {
				rspCode = reset();
			} else if("accept".equals(method)) {
				rspCode = accept();
			} else if("refuse".equals(method)) {
				rspCode = refuse();
			}
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "，操作员信息维护操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
	
	private String refuse() {
		String sql1 = "select LAST_UPD_OPR_ID from TBL_OPR_INFO_TMP where OPR_ID = '"+oprId+"'";
		String findCountBySQLQuery = CommonFunction.getCommQueryDAO().findCountBySQLQuery(sql1);
		System.out.println("更新人："+findCountBySQLQuery);
		if (operator.getOprId().equals(findCountBySQLQuery.trim())) {
			return "提交人与审核人不能是同一个人";
		}
		String sql2 = "update TBL_OPR_INFO_TMP set AUDIT_STAT = '1' where OPR_ID = '"+oprId+"'";
		CommonFunction.getCommQueryDAO().excute(sql2);
		System.out.println("拒绝oprSta="+oprSta);
		System.out.println("拒绝oprId="+oprId);
		return Constants.SUCCESS_CODE;
	}

	/**
	 * 操作员审核
	 * @return
	 */
	private String accept() {
		String sql1 = "select LAST_UPD_OPR_ID from TBL_OPR_INFO_TMP where OPR_ID = '"+oprId+"'";
		String findCountBySQLQuery = CommonFunction.getCommQueryDAO().findCountBySQLQuery(sql1);
		log("更新人："+findCountBySQLQuery);
		if (operator.getOprId().equals(findCountBySQLQuery.trim())) {
			return "提交人与审核人不能是同一个人";
		}
		try {
			try {
				String delSql = "DELETE FROM TBL_OPR_INFO WHERE OPR_ID = '"+oprId+"' ";
				CommonFunction.getCommQueryDAO().excute(delSql);
			} catch (Exception e) {
				log("正式表中没有该数据");
			}
			String sql2 = "insert into TBL_OPR_INFO (OPR_ID,BRH_ID,OPR_DEGREE,OPR_DEGREE_RSC,OPR_STA,OPR_LOG_STA,OPR_NAME,OPR_GENDER,REGISTER_DT,END_DT,OPR_PWD,OPR_TEL,OPR_MOBILE,OPR_EMAIL,PWD_CYCLE,PWD_WR_TM,PWD_WR_TM_TOTAL,PWD_WR_LAST_DT,PWD_OUT_DATE,SET_OPR_ID,RESV1,LAST_UPD_OPR_ID,LAST_UPD_TXN_ID,LAST_UPD_TS,RESV2) select OPR_ID,BRH_ID,OPR_DEGREE,OPR_DEGREE_RSC,OPR_STA,OPR_LOG_STA,OPR_NAME,OPR_GENDER,REGISTER_DT,END_DT,OPR_PWD,OPR_TEL,OPR_MOBILE,OPR_EMAIL,PWD_CYCLE,PWD_WR_TM,PWD_WR_TM_TOTAL,PWD_WR_LAST_DT,PWD_OUT_DATE,SET_OPR_ID,RESV1,LAST_UPD_OPR_ID,LAST_UPD_TXN_ID,LAST_UPD_TS,RESV2 from TBL_OPR_INFO_TMP where OPR_ID = '"+oprId+"'";
			log("新增到正式表的SQL="+sql2);
			CommonFunction.getCommQueryDAO().excute(sql2);
		} catch (Exception e) {
			e.printStackTrace();
			log("正式表中已有数据");
		}
		String sql3 = "update TBL_OPR_INFO_TMP set AUDIT_STAT = '0' where OPR_ID = '"+oprId+"'";
		System.out.println("修改状态的SQL="+sql3);
		CommonFunction.getCommQueryDAO().excute(sql3);
//		List<Object> TblOprInfoList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql2);
//		System.out.println(TblOprInfoList.get(0));
//		System.out.println("通过oprSta="+oprSta);
//		System.out.println("通过oprId="+oprId);
		return Constants.SUCCESS_CODE;
	}

	/**
	 * 添加操作员
	 * @return
	 * @throws Exception 
	 */
	private String add() throws Exception {
		try{
		String sql1 = "select OPR_ID from TBL_OPR_INFO_TMP WHERE OPR_ID ='"+oprId+"'";
		String oprIdTmp = CommonFunction.getCommQueryDAO().findCountBySQLQuery(sql1);
		if (oprIdTmp == oprId || oprIdTmp.equals(oprId)) {
			return "操作员编号已经被使用";
		}
		
		if(oprPwd.indexOf(oprId)!=-1){
			return "授权码不可包含操作员编号";
		}
		
		//为true时操作员密码过期期限为90天,否则为20991231
		String d = SysParamUtil.getParam(SysParamConstants.OPR_PWD_OUT_DAYS);
		String asd = "";
		if(d.equals("true")){
			asd = CommonFunction.getOffSizeDate(CommonFunction.getCurrentDate(), SysParamUtil.getParam(SysParamConstants.OPR_PWD_OUT_DAY));
		}else{
			asd = "20991231";
		}
		
		//RESV2字段: 用来判断新建用户首次登陆时需强制修改密码   首次登陆为1 否则为0 
		String sql2 = "INSERT INTO TBL_OPR_INFO_TMP "
				+ "(OPR_ID, BRH_ID, OPR_DEGREE, OPR_DEGREE_RSC, OPR_STA, OPR_LOG_STA, OPR_NAME, OPR_GENDER, REGISTER_DT, END_DT, OPR_PWD, OPR_TEL, OPR_MOBILE, OPR_EMAIL, PWD_CYCLE, PWD_WR_TM, PWD_WR_TM_TOTAL, PWD_WR_LAST_DT, PWD_OUT_DATE, SET_OPR_ID, RESV1, LAST_UPD_OPR_ID, LAST_UPD_TXN_ID, LAST_UPD_TS,AUDIT_STAT,ADD_OPR_ID,RESV2) VALUES "
				+ "('"+oprId+"', '"+brhId+"', '"+oprDegree+"', ' ', '0', '0', '"+oprName+"', '"+oprGender+"', '"+CommonFunction.getCurrentDate()+"', null, '"+Encryption.encrypt(oprPwd)+"', '"+oprTel+"', '"+oprMobile+"', '"+oprEmail+"', null, '0', '0', '"+CommonFunction.getCurrentDate()+"', '"+asd+"', '"+operator.getOprId()+"', null, '"+operator.getOprId()+"', '"+getTxnId() + getSubTxnId()+"', '"+CommonFunction.getCurrentDate()+"','2',"+"'"+operator.getOprId()+"','1')";
		System.out.println("添加到临时表的SQL "+sql2);
		log("添加操作员信息成功。操作员编号：" + operator.getOprId()+ "，被添加操作员号：" + oprId);
		CommonFunction.getCommQueryDAO().excute(sql2);
		return Constants.SUCCESS_CODE;
		}catch(Exception e){
			e.printStackTrace();
			return Constants.DATA_OPR_FAIL;
		}
		
		/*
		// 检查操作员编号是否已经存在
		if(t10401BO.get(oprId) != null) {
			return "操作员编号已经被使用";
		}
		TblOprInfo tblOprInfo = new TblOprInfo();
		tblOprInfo.setId(oprId);
		tblOprInfo.setBrhId(brhId);
		tblOprInfo.setOprName(oprName);
		tblOprInfo.setOprGender(oprGender);
		tblOprInfo.setOprDegree(oprDegree);
		tblOprInfo.setOprTel(oprTel);
		tblOprInfo.setOprMobile(oprMobile);
		tblOprInfo.setOprPwd(Encryption.encrypt(oprPwd));
		tblOprInfo.setRegisterDt(CommonFunction.getCurrentDate());
		tblOprInfo.setPwdWrTm("0");
		tblOprInfo.setPwdWrTmTotal("0");
		tblOprInfo.setPwdOutDate(CommonFunction.getOffSizeDate(CommonFunction.getCurrentDate(), SysParamUtil.getParam(SysParamConstants.OPR_PWD_OUT_DAY)));
		tblOprInfo.setOprSta("0");
		tblOprInfo.setLastUpdOprId(operator.getOprId());
		tblOprInfo.setLastUpdTs(CommonFunction.getCurrentDate());
//		tblOprInfo.setLastUpdTxnId(getTxnId() + getSubTxnId());
		tblOprInfo.setPwdWrLastDt(CommonFunction.getCurrentDate());
		tblOprInfo.setOprEmail(oprEmail);
		if(StringUtil.isNull(oprDegreeRsc)){
			tblOprInfo.setOprDegreeRsc(" ");
		}else{
			tblOprInfo.setOprDegreeRsc(oprDegreeRsc);
		}
		tblOprInfo.setOprLogSta("0");
		tblOprInfo.setSetOprId(operator.getOprId());
		t10401BO.add(tblOprInfo);
		log("添加操作员信息成功。操作员编号：" + operator.getOprId()+ "，被添加操作员号：" + oprId);
		return Constants.SUCCESS_CODE;
		*/
	}
//	775516
	/**
	 * 删除操作员
	 * @return
	 */
	private String delete() {
		//20120822
		if (oprId.equals("0000")) {
			return "操作员0000不允许删除！";
		}
		if(operator.getOprId().equals(oprId)){
			return "操作员不能删除自己本身账号！";
		}
		t10401BO.delete(oprId);
		String sql = "delete from TBL_OPR_INFO WHERE OPR_ID = '"+oprId+"'";
		System.out.println("删除操作员SQL："+sql);
		CommonFunction.getCommQueryDAO().excute(sql);
		log("删除操作员信息成功。操作员编号：" + operator.getOprId()+ "，被删除操作员号：" + oprId);
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 同步操作员信息
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private String update() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		System.out.println(oprId);
		System.out.println(oprDegree);
		System.out.println(oprName);
		System.out.println(oprGender);
		System.out.println(oprSta);
		System.out.println(oprTel);
		System.out.println(oprMobile);
		System.out.println(oprEmail);
		System.out.println(auditStat);
		
		/*if (auditStat.equals("0")) {
			return "已审核的不能进行修改";
		}*/
		
		String sql = "UPDATE TBL_OPR_INFO_TMP SET OPR_DEGREE='"+oprDegree+"', OPR_NAME='"+oprName+"', OPR_GENDER='"+oprGender+"', OPR_STA='"+oprSta+"',OPR_TEL='"+oprTel+"', OPR_MOBILE='"+oprMobile+"', OPR_EMAIL='"+oprEmail+"', AUDIT_STAT='3', LAST_UPD_OPR_ID='"+operator.getOprId()+"' WHERE OPR_ID = '"+oprId+"'";
		System.out.println("更新操作员SQL："+sql);
		CommonFunction.getCommQueryDAO().excute(sql);
		log("同步操作员信息成功。操作员编号：" + operator.getOprId()+ "，被同步操作员信息号：" + oprId);
		/*jsonBean.parseJSONArrayData(getOprInfoList());
		int len = jsonBean.getArray().size();
		TblOprInfo tblOprInfo = null;
		List<TblOprInfo> tblOprInfoList = new ArrayList<TblOprInfo>(len);
		for(int i = 0; i < len; i++) {
			oprId = jsonBean.getJSONDataAt(i).getString("oprId");
			tblOprInfo = t10401BO.get(oprId);
			jsonBean.setObject(jsonBean.getJSONDataAt(i));
			BeanUtils.setObjectWithPropertiesValue(tblOprInfo, jsonBean, true);
			tblOprInfoList.add(tblOprInfo);
		}
		t10401BO.update(tblOprInfoList);
		log("同步操作员信息成功。操作员编号：" + operator.getOprId()+ "，被同步操作员信息号：" + oprId);*/
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 编辑操作员机构/角色信息
	 * @return
	 */
	private String edit() {
		
		if (auditStat.equals("0")) {
			return "已审核的不能进行修改";
		}
		//update employee set salary=4000,gender='female' where name='wangwu';
		String sql = "update TBL_OPR_INFO_TMP set BRH_ID='"+brhId+"', OPR_DEGREE='"+oprDegree+"',LAST_UPD_OPR_ID='"+operator.getOprId()+"' where OPR_ID='"+oprId+"'";
		CommonFunction.getCommQueryDAO().excute(sql);
		
		/*TblOprInfo tblOprInfo = t10401BO.get(oprId);
		if(tblOprInfo == null) {
			return "您所选择的操作员信息已不存在";
		}
		tblOprInfo.setBrhId(brhId);
		tblOprInfo.setOprDegree(oprDegree);
		t10401BO.update(tblOprInfo);
		log("编辑操作员机构/角色信息成功。操作员编号：" + operator.getOprId()+ "，被编辑操作员机构/角色信息号：" + oprId);*/
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 重置/解锁操作员信息
	 * 需要将操作员密码重置并将操作员解锁定
	 * @return
	 * @throws Exception 
	 */
	private String reset() throws Exception {
		TblOprInfo tblOprInfo = t10401BO.get(oprId);
		if(tblOprInfo == null) {
			return "您所选择的操作员信息已不存在";
		}
		if (auditStat.equals("0")) {
			tblOprInfo.setOprPwd(Encryption.encrypt("11111111"));
			tblOprInfo.setOprSta("0");
			tblOprInfo.setPwdWrTm("0");
			tblOprInfo.setPwdWrTmTotal("0");
			tblOprInfo.setPwdOutDate(CommonFunction.getOffSizeDate(CommonFunction.getCurrentDate(), SysParamUtil.getParam(SysParamConstants.OPR_PWD_OUT_DAY)));
			t10401BO.update(tblOprInfo);
			String sql = "update TBL_OPR_INFO_TMP set OPR_PWD='"+Encryption.encrypt("11111111")+"', OPR_STA='"+0+"',PWD_WR_TM='"+0+"',PWD_WR_TM_TOTAL='"+0+"',PWD_OUT_DATE='"+CommonFunction.getOffSizeDate(CommonFunction.getCurrentDate(), SysParamUtil.getParam(SysParamConstants.OPR_PWD_OUT_DAY))+"' where OPR_ID='"+oprId+"'";
			System.out.println("重置密码更新临时表："+sql);
			CommonFunction.getCommQueryDAO().excute(sql);
			log("重置/解锁操作员信息成功。操作员编号：" + operator.getOprId()+ "，被重置/解锁操作员信息号：" + oprId);
			return Constants.SUCCESS_CODE;
		}else {
			return "该用户未审核";
		}
	}

	// 操作员编号
	private String oprId;
	// 机构编号
	private String brhId;
	// 操作员级别
	private String oprDegree;
	// 操作员名称
	private String oprName;
	// 操作员性别
	private String oprGender;
	// 操作员密码
	private String oprPwd;
	// 操作员电话
	private String oprTel;
	// 操作员移动电话
	private String oprMobile;
	// 操作员信息集合
	private String oprInfoList;
	// 操作员信息集合
	private String oprEmail;
	// 操作员信息集合
	private String oprDegreeRsc;
	
	private String oprSta;
	private String auditStat;
	
	public String getAuditStat() {
		return auditStat;
	}

	public void setAuditStat(String auditStat) {
		this.auditStat = auditStat;
	}

	public String getOprSta() {
		return oprSta;
	}

	public void setOprSta(String oprSta) {
		this.oprSta = oprSta;
	}

	/**
	 * @return the oprId
	 */
	public String getOprId() {
		return oprId;
	}
	/**
	 * @param oprId the oprId to set
	 */
	public void setOprId(String oprId) {
		this.oprId = oprId;
	}
	/**
	 * @return the brhId
	 */
	public String getBrhId() {
		return brhId;
	}
	/**
	 * @param brhId the brhId to set
	 */
	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}
	/**
	 * @return the oprDegree
	 */
	public String getOprDegree() {
		return oprDegree;
	}
	/**
	 * @param oprDegree the oprDegree to set
	 */
	public void setOprDegree(String oprDegree) {
		this.oprDegree = oprDegree;
	}
	/**
	 * @return the oprName
	 */
	public String getOprName() {
		return oprName;
	}
	/**
	 * @param oprName the oprName to set
	 */
	public void setOprName(String oprName) {
		this.oprName = oprName;
	}
	/**
	 * @return the oprGender
	 */
	public String getOprGender() {
		return oprGender;
	}
	/**
	 * @param oprGender the oprGender to set
	 */
	public void setOprGender(String oprGender) {
		this.oprGender = oprGender;
	}
	/**
	 * @return the oprPwd
	 */
	public String getOprPwd() {
		return oprPwd;
	}
	/**
	 * @param oprPwd the oprPwd to set
	 */
	public void setOprPwd(String oprPwd) {
		this.oprPwd = oprPwd;
	}
	/**
	 * @return the oprTel
	 */
	public String getOprTel() {
		return oprTel;
	}
	/**
	 * @param oprTel the oprTel to set
	 */
	public void setOprTel(String oprTel) {
		this.oprTel = oprTel;
	}
	/**
	 * @return the oprMobile
	 */
	public String getOprMobile() {
		return oprMobile;
	}
	/**
	 * @param oprMobile the oprMobile to set
	 */
	public void setOprMobile(String oprMobile) {
		this.oprMobile = oprMobile;
	}

	/**
	 * @return the oprInfoList
	 */
	public String getOprInfoList() {
		return oprInfoList;
	}

	/**
	 * @param oprInfoList the oprInfoList to set
	 */
	public void setOprInfoList(String oprInfoList) {
		this.oprInfoList = oprInfoList;
	}

	public String getOprEmail() {
		return oprEmail;
	}

	public void setOprEmail(String oprEmail) {
		this.oprEmail = oprEmail;
	}

	public String getOprDegreeRsc() {
		return oprDegreeRsc;
	}

	public void setOprDegreeRsc(String oprDegreeRsc) {
		this.oprDegreeRsc = oprDegreeRsc;
	}
}
