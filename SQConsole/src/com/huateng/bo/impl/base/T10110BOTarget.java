package com.huateng.bo.impl.base;

import java.util.List;
import com.huateng.bo.base.T10110BO;
import com.huateng.common.Constants;
import com.huateng.dao.iface.base.TblAgencyFeeDAO;
import com.huateng.po.base.AgencyFeeLub;
import com.huateng.po.base.AgencyFeeLubTmp;

public class T10110BOTarget implements T10110BO{
	private TblAgencyFeeDAO tblAgencyFeeDAO;
	public TblAgencyFeeDAO getTblAgencyFeeDAO() {
		return tblAgencyFeeDAO;
	}

	public void setTblAgencyFeeDAO(TblAgencyFeeDAO tblAgencyFeeDAO) {
		this.tblAgencyFeeDAO = tblAgencyFeeDAO;
	}

	public String add(AgencyFeeLub agencyFeeLub) {
		// TODO Auto-generated method stub
		tblAgencyFeeDAO.save(agencyFeeLub);
		return Constants.SUCCESS_CODE;
	}
	
	public String add(AgencyFeeLubTmp agencyFeeLub) {
		// TODO Auto-generated method stub
		tblAgencyFeeDAO.save(agencyFeeLub);
		return Constants.SUCCESS_CODE;
	}
	
	public String delete(AgencyFeeLub agencyFeeLub) {
		// TODO Auto-generated method stub
		tblAgencyFeeDAO.delete(agencyFeeLub);
		return Constants.SUCCESS_CODE;
	}

	public String delete(String Id) {
		// TODO Auto-generated method stub
		this.tblAgencyFeeDAO.delete(Id);;
		return Constants.SUCCESS_CODE;
	}

	public AgencyFeeLub get(String Id) {
		// TODO Auto-generated method stub
		return this.tblAgencyFeeDAO.get(Id);
	}

	public AgencyFeeLubTmp getTmp(String Id) {
		// TODO Auto-generated method stub
		return this.tblAgencyFeeDAO.getTmp(Id);
	}
	
	public String update(List<AgencyFeeLub> agencyFeeLubList) {
		// TODO Auto-generated method stub
		for(AgencyFeeLub agencyFeeLub:agencyFeeLubList){
			tblAgencyFeeDAO.saveOrUpdate(agencyFeeLub);
		}
		return Constants.SUCCESS_CODE;
	}
	public String updateTmp(List<AgencyFeeLubTmp> agencyFeeLubList) {
		for(AgencyFeeLubTmp agencyFeeLub:agencyFeeLubList){
			tblAgencyFeeDAO.saveOrUpdate(agencyFeeLub);
		}
		return Constants.SUCCESS_CODE;
	}

	public String delete2(AgencyFeeLubTmp agencyFeeLubTmp) {
		tblAgencyFeeDAO.delete2(agencyFeeLubTmp);
		return Constants.SUCCESS_CODE;
	}

	public String delete2(String Id) {
		this.tblAgencyFeeDAO.delete2(Id);;
		return Constants.SUCCESS_CODE;
	}
}
