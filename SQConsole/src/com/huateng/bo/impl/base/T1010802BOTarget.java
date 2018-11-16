package com.huateng.bo.impl.base;

import java.util.List;


import com.huateng.bo.base.T1010802BO;
import com.huateng.common.Constants;
import com.huateng.dao.iface.base.TblAgencyInfoTrueDAO;
import com.huateng.po.base.AgencyInfoTrue;
import com.huateng.po.base.AgencyInfoTruePK;


public class T1010802BOTarget implements T1010802BO {
	public TblAgencyInfoTrueDAO getTblAgencyInfoTrueDAO() {
		return tblAgencyInfoTrueDAO;
	}

	public void setTblAgencyInfoTrueDAO(TblAgencyInfoTrueDAO tblAgencyInfoTrueDAO) {
		this.tblAgencyInfoTrueDAO = tblAgencyInfoTrueDAO;
	}

	private TblAgencyInfoTrueDAO tblAgencyInfoTrueDAO;
	public String add(AgencyInfoTrue agencyInfoTrue) {
		// TODO Auto-generated method stub
		tblAgencyInfoTrueDAO.save(agencyInfoTrue);
		return Constants.SUCCESS_CODE;
	}

	public String delete(AgencyInfoTrue agencyInfoTrue) {
		// TODO Auto-generated method stub
		tblAgencyInfoTrueDAO.delete(agencyInfoTrue);
		return Constants.SUCCESS_CODE;
	}

	public String delete(AgencyInfoTruePK agencyInfoTruePK) {
		// TODO Auto-generated method stub
		this.tblAgencyInfoTrueDAO.delete(agencyInfoTruePK);;
		return Constants.SUCCESS_CODE;
	}

	public AgencyInfoTrue get(AgencyInfoTruePK agencyInfoTruePK) {
		// TODO Auto-generated method stub
		return this.tblAgencyInfoTrueDAO.get(agencyInfoTruePK);
	}

	public String update(List<AgencyInfoTrue> agencytrueInfoList) {
		// TODO Auto-generated method stub
		for(AgencyInfoTrue agencyInfotrue:agencytrueInfoList){
			tblAgencyInfoTrueDAO.update(agencyInfotrue);
		}
		return Constants.SUCCESS_CODE;
	}

	public void update(AgencyInfoTrue agencyInfoTrue) {
		// TODO Auto-generated method stub
		this.tblAgencyInfoTrueDAO.update(agencyInfoTrue);
	}
	

	

}
