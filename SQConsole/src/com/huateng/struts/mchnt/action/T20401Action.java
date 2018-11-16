/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-8-2       first release
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

import java.lang.reflect.InvocationTargetException;
import com.huateng.bo.mchnt.TblMchtNetTmpBO;
import com.huateng.common.Constants;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.ContextUtil;

/**
 * Title:入网商户信息维护
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-2
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class T20401Action extends BaseAction {
	
	//商户编号
	String mchtI;
	//商户名称
	String mchtNm;	
	//法人证件号
	String legalNo;	
	//法人姓名
	String legalNm;	
	//客户经理工号
	String operNo;	
	//客户经理姓名
	String operNm;
	//客户经理手机
	String operPh;
	//法定代表人证件复印件
	String legalCopies;
	//营业执照复印件
	String certCopies;
	//组织机构代码证复印件
	String orgCopies;
	//税务登记证复印件
	String taxCopies;
	
	private TblMchtNetTmpBO tblMchtNetTmpBO = (TblMchtNetTmpBO) ContextUtil.getBean("TblMchtNetTmpBO");
	/* (non-Javadoc)
	 * @see com.huateng.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws Exception {
		if("add".equals(method)) {
			rspCode = add();
		}else if("recover".equals(method)) {
			rspCode = recover();
		} else if("update".equals(method)) {
			rspCode = update();
		}
		
//		log("入网商户编号：" + mchntId);
		
		return rspCode;
	}
	
	/**
	 * 添加商户审核信息
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private String add() throws IllegalAccessException, InvocationTargetException {
//		TblMchtNetTmp tblMchtNetTmp = this.creatTblMchtNetTmp();
		//入网商户BO
//		TblMchtNetTmpBO tblMchtNetTmpBO = (TblMchtNetTmpBO) ContextUtil.getBean("TblMchtNetTmpBO");
		
		//保存入网商户信息
//		try {
//			tblMchtNetTmpBO.add(tblMchtNetTmp);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
		return "sucess";
	}
	
	/**
	 * 冻结商户信息
	 * @return
	 */
//	private String stop() {
//		return Constants.SUCCESS_CODE;
//	}
	
	/**
	 * 恢复商户信息
	 * @return
	 */
	private String recover() {		
		
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 更新商户信息
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private String update() throws IllegalAccessException, InvocationTargetException {
//		TblMchtNetTmp tblMchtNetTmp = this.creatTblMchtNetTmp();
		//入网商户BO
//		TblMchtNetTmpBO tblMchtNetTmpBO = (TblMchtNetTmpBO) ContextUtil.getBean("TblMchtNetTmpBO");
		return null;
	}
	
	/*private TblMchtNetTmp creatTblMchtNetTmp(){
		//入网商户PO
		TblMchtNetTmp tblMchtNetTmp = new TblMchtNetTmp();
		tblMchtNetTmp.setMchtNm(mchtNm);
		tblMchtNetTmp.setLegalNo(legalNo);	
		tblMchtNetTmp.setLegalNm(legalNm);
		tblMchtNetTmp.setOperNo(operNo);
		tblMchtNetTmp.setOperNm(operNm);
		tblMchtNetTmp.setOperPh(operPh);
		tblMchtNetTmp.setCertUrl(legalNo+ "_" + CommonFunction.getCurrentDate() + "_CERT.jpg");
		tblMchtNetTmp.setLegalUrl(legalNo+ "_" + CommonFunction.getCurrentDate() + "_LEGAL.jpg");
		tblMchtNetTmp.setOrgUrl(legalNo+ "_" + CommonFunction.getCurrentDate() + "_ORG.jpg");
		tblMchtNetTmp.setTaxUrl(legalNo+ "_" + CommonFunction.getCurrentDate() + "_TAX.jpg");
		tblMchtNetTmp.setMchtId("-");
		tblMchtNetTmp.setCertNo("-");
		tblMchtNetTmp.setManagerNo("-");
		tblMchtNetTmp.setBrhId(operator.getOprBrhId());//操作员所属机构 
		tblMchtNetTmp.setStatus(TblMchtNetTmpConstants.MCHT_ADD_CHECK);		//添加待审核状态
		tblMchtNetTmp.setCreateTime(CommonFunction.getCurrentDateTime());	//创建时间
		tblMchtNetTmp.setLastUpdTime("-");									//更新时间
		tblMchtNetTmp.setLastOprId(operator.getOprId());//更新操作员		
		return tblMchtNetTmp;
	}*/

	public String getMchtNm() {
		return mchtNm;
	}

	public void setMchtNm(String mchtNm) {
		this.mchtNm = mchtNm;
	}

	public String getLegalNo() {
		return legalNo;
	}

	public void setLegalNo(String legalNo) {
		this.legalNo = legalNo;
	}

	public String getLegalNm() {
		return legalNm;
	}

	public void setLegalNm(String legalNm) {
		this.legalNm = legalNm;
	}

	public String getOperNo() {
		return operNo;
	}

	public void setOperNo(String operNo) {
		this.operNo = operNo;
	}

	public String getOperNm() {
		return operNm;
	}

	public void setOperNm(String operNm) {
		this.operNm = operNm;
	}

	public String getOperPh() {
		return operPh;
	}

	public void setOperPh(String operPh) {
		this.operPh = operPh;
	}

	public String getLegalCopies() {
		return legalCopies;
	}

	public void setLegalCopies(String legalCopies) {
		this.legalCopies = legalCopies;
	}

	public String getCertCopies() {
		return certCopies;
	}

	public void setCertCopies(String certCopies) {
		this.certCopies = certCopies;
	}

	public String getOrgCopies() {
		return orgCopies;
	}

	public void setOrgCopies(String orgCopies) {
		this.orgCopies = orgCopies;
	}

	public String getTaxCopies() {
		return taxCopies;
	}

	public void setTaxCopies(String taxCopies) {
		this.taxCopies = taxCopies;
	}

	public String getMchtI() {
		return mchtI;
	}

	public void setMchtI(String mchtI) {
		this.mchtI = mchtI;
	}

	public TblMchtNetTmpBO getTblMchtNetTmpBO() {
		return tblMchtNetTmpBO;
	}

	public void setTblMchtNetTmpBO(TblMchtNetTmpBO tblMchtNetTmpBO) {
		this.tblMchtNetTmpBO = tblMchtNetTmpBO;
	}


	
	
	
}
