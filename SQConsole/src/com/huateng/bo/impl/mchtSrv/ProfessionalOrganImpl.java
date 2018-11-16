package com.huateng.bo.impl.mchtSrv;

import com.huateng.common.Constants;
import com.huateng.dao.iface.mchtSrv.TblProfessionalOrganDAO;
import com.huateng.po.mchtSrv.TblProfessionalOrgan;
import com.huateng.struts.mchtSrv.ProfessionalOrganConstants;

public class ProfessionalOrganImpl implements ProfessionalOrgan {

	TblProfessionalOrganDAO tblProfessionalOrganDAO;

	public void setTblProfessionalOrganDAO(
			TblProfessionalOrganDAO tblProfessionalOrganDAO) {
		this.tblProfessionalOrganDAO = tblProfessionalOrganDAO;
	}

	public String save(TblProfessionalOrgan tblProfessionalOrgan) {
		if(tblProfessionalOrgan == null)
			return ProfessionalOrganConstants.T70301_02;
		if(tblProfessionalOrganDAO.get(tblProfessionalOrgan.getOrgId()) != null)
			return ProfessionalOrganConstants.T70301_01;
		tblProfessionalOrganDAO.save(tblProfessionalOrgan);
		return Constants.SUCCESS_CODE;
	}

	public String modify(TblProfessionalOrgan tblProfessionalOrgan) {
		tblProfessionalOrganDAO.update(tblProfessionalOrgan);
		return Constants.SUCCESS_CODE;
	}
}
