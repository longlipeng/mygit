package com.huateng.bo.impl.base;

import java.util.List;

import com.huateng.bo.base.T10106BO;
import com.huateng.common.Constants;
import com.huateng.dao.iface.base.TblAgencyInfoDAO;
import com.huateng.po.base.AgencyInfo;
import com.huateng.po.base.AgencyInfoPK;

public class T10106BOTarget implements T10106BO {
	private TblAgencyInfoDAO tblAgencyInfoDAO;
	public String add(AgencyInfo agencyInfo) {
		// TODO Auto-generated method stub
		tblAgencyInfoDAO.save(agencyInfo);
		return Constants.SUCCESS_CODE;
	}

	public String delete(AgencyInfo agencyInfo) {
		// TODO Auto-generated method stub
		tblAgencyInfoDAO.delete(agencyInfo);
		return Constants.SUCCESS_CODE;
	}

	public String delete(AgencyInfoPK agencyInfoPK) {
		// TODO Auto-generated method stub
		this.tblAgencyInfoDAO.delete(agencyInfoPK);;
		return Constants.SUCCESS_CODE;
	}

	public AgencyInfo get(AgencyInfoPK agencyInfoPK) {
		// TODO Auto-generated method stub
		return this.tblAgencyInfoDAO.get(agencyInfoPK);
	}

	public String update(List<AgencyInfo> agencyInfoList) {
		// TODO Auto-generated method stub
		for(AgencyInfo agencyInfo:agencyInfoList){
			tblAgencyInfoDAO.update(agencyInfo);
		}
		return Constants.SUCCESS_CODE;
	}
	public TblAgencyInfoDAO getTblAgencyInfoDAO() {
		return tblAgencyInfoDAO;
	}

	public void setTblAgencyInfoDAO(TblAgencyInfoDAO tblAgencyInfoDAO) {
		this.tblAgencyInfoDAO = tblAgencyInfoDAO;
	}

	/**
	 * 更新机构信息
	 * @param tblBranchManage    机构信息列表
	 * @return
	 */
	public void saveOrUpdate(AgencyInfo agencyInfo){
		tblAgencyInfoDAO.saveOrUpdate(agencyInfo);
	}

}
