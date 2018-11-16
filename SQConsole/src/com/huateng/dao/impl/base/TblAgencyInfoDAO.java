package com.huateng.dao.impl.base;

import java.util.List;

import com.huateng.dao._RootDAO;
import com.huateng.po.base.AgencyInfo;
import com.huateng.po.base.AgencyInfoPK;

public class TblAgencyInfoDAO extends _RootDAO<com.huateng.po.base.AgencyInfo> implements com.huateng.dao.iface.base.TblAgencyInfoDAO{
	public TblAgencyInfoDAO() {
	}

	public List<AgencyInfo> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Class getReferenceClass() {
		// TODO Auto-generated method stub
		return com.huateng.po.base.AgencyInfo.class;
	}

	public com.huateng.po.base.AgencyInfo cast(Object object) {
		return (com.huateng.po.base.AgencyInfo) object;
	}

	public AgencyInfo load(AgencyInfoPK agencyInfoPK) {
		// TODO Auto-generated method stub
		return (com.huateng.po.base.AgencyInfo) load(getReferenceClass(),
				agencyInfoPK);
	}

	public AgencyInfo get(AgencyInfoPK agencyInfoPK) {
		// TODO Auto-generated method stub
		return (com.huateng.po.base.AgencyInfo) get(getReferenceClass(),
				agencyInfoPK);
	}

	public AgencyInfoPK save(AgencyInfo agencyInfo) {
		// TODO Auto-generated method stub
		return (AgencyInfoPK) super.save(agencyInfo);
	}

	public void saveOrUpdate(AgencyInfo agencyInfo) {
		// TODO Auto-generated method stub
		super.saveOrUpdate(agencyInfo);
	}

	public void update(AgencyInfo agencyInfo) {
		// TODO Auto-generated method stub
		super.update(agencyInfo);
	}

	public void delete(AgencyInfoPK agencyInfoPK) {
		// TODO Auto-generated method stub
		super.delete((Object) load(agencyInfoPK));
	}

	public void delete(AgencyInfo agencyInfo) {
		// TODO Auto-generated method stub
		super.delete((Object) agencyInfo);
	}
}
