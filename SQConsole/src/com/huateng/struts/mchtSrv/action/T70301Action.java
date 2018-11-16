/* @(#)
 *
 * Project:PFConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2011-10-11       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2011 Huateng Software, Inc. All rights reserved.
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
package com.huateng.struts.mchtSrv.action;

import java.math.BigDecimal;

import org.springframework.beans.BeanUtils;

import com.huateng.bo.impl.mchtSrv.ProfessionalOrgan;
import com.huateng.po.mchtSrv.TblProfessionalOrgan;
import com.huateng.struts.system.action.BaseSupport;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-10-11
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Lysander
 * 
 * @version 1.0
 */
public class T70301Action extends BaseSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6168197261385499519L;

	private String orgId;
	private String branch;
	private String orgName;
	private String rate;
	private String rateType;
	private String remarks;
	private String orgIdUpd;
	private String branchUpd;
	private String orgNameUpd;
	private String rateUpd;
	private String rateTypeUpd;
	private String remarksUpd;
	
	
	public String getOrgId() {
		return orgId;
	}


	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}


	public String getBranch() {
		return branch;
	}


	public void setBranch(String branch) {
		this.branch = branch;
	}


	public String getOrgName() {
		return orgName;
	}


	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}


	public String getRate() {
		return rate;
	}


	public void setRate(String rate) {
		this.rate = rate;
	}


	public String getRateType() {
		return rateType;
	}


	public void setRateType(String rateType) {
		this.rateType = rateType;
	}


	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getOrgIdUpd() {
		return orgIdUpd;
	}


	public void setOrgIdUpd(String orgIdUpd) {
		this.orgIdUpd = orgIdUpd;
	}


	public String getBranchUpd() {
		return branchUpd;
	}


	public void setBranchUpd(String branchUpd) {
		this.branchUpd = branchUpd;
	}


	public String getOrgNameUpd() {
		return orgNameUpd;
	}


	public void setOrgNameUpd(String orgNameUpd) {
		this.orgNameUpd = orgNameUpd;
	}


	public String getRateUpd() {
		return rateUpd;
	}


	public void setRateUpd(String rateUpd) {
		this.rateUpd = rateUpd;
	}


	public String getRateTypeUpd() {
		return rateTypeUpd;
	}


	public void setRateTypeUpd(String rateTypeUpd) {
		this.rateTypeUpd = rateTypeUpd;
	}


	public String getRemarksUpd() {
		return remarksUpd;
	}


	public void setRemarksUpd(String remarksUpd) {
		this.remarksUpd = remarksUpd;
	}

	private ProfessionalOrgan service = (ProfessionalOrgan) ContextUtil.getBean("professionalOrganSrv");

	public String add(){
		try {
			TblProfessionalOrgan tblProfessionalOrgan = new TblProfessionalOrgan();
			
			BeanUtils.copyProperties(this, tblProfessionalOrgan,new String[]{"rate"});
			
			tblProfessionalOrgan.setRate(new BigDecimal(rate));
			tblProfessionalOrgan.setRateType(rateType);
			tblProfessionalOrgan.setCrtOpr(getOperator().getOprId());
			tblProfessionalOrgan.setCrtTs(CommonFunction.getCurrentDateTime());
			tblProfessionalOrgan.setLastUpdOpr(getOperator().getOprId());
			tblProfessionalOrgan.setLastUpdTs(CommonFunction.getCurrentDateTime());
			
			rspCode = service.save(tblProfessionalOrgan);
			return returnService(rspCode);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}
		
	}
	
	
	public String update(){
		try {
			TblProfessionalOrgan tblProfessionalOrgan = new TblProfessionalOrgan(orgIdUpd);
			tblProfessionalOrgan.setBranch(branchUpd);
			tblProfessionalOrgan.setRate(new BigDecimal(rateUpd));
			tblProfessionalOrgan.setRateType(rateTypeUpd);
			tblProfessionalOrgan.setOrgName(orgNameUpd);
			tblProfessionalOrgan.setRemarks(remarksUpd);
			tblProfessionalOrgan.setLastUpdOpr(getOperator().getOprId());
			tblProfessionalOrgan.setLastUpdTs(CommonFunction.getCurrentDateTime());
			
			rspCode = service.modify(tblProfessionalOrgan);
			return returnService(rspCode);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}
	}
	@Override
	public String getMsg() {
		return msg;
	}

	@Override
	public boolean isSuccess() {
		return success;
	}

}
