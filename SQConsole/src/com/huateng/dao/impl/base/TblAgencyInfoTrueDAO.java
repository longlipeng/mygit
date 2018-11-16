package com.huateng.dao.impl.base;

import java.util.List;

import com.huateng.dao._RootDAO;
import com.huateng.po.base.AgencyInfoTrue;
import com.huateng.po.base.AgencyInfoTruePK;

public class TblAgencyInfoTrueDAO extends _RootDAO<com.huateng.po.base.AgencyInfoTrue> implements com.huateng.dao.iface.base.TblAgencyInfoTrueDAO{
	public TblAgencyInfoTrueDAO() {
	}

	public List<AgencyInfoTrue> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Class getReferenceClass() {
		// TODO Auto-generated method stub
		return com.huateng.po.base.AgencyInfoTrue.class;
	}

	public com.huateng.po.base.AgencyInfoTrue cast(Object object) {
		return (com.huateng.po.base.AgencyInfoTrue) object;
	}

	public AgencyInfoTrue load(AgencyInfoTruePK agencyInfoTruePK) {
		// TODO Auto-generated method stub
		return (AgencyInfoTrue) load(getReferenceClass(),
				agencyInfoTruePK);
	}

	public AgencyInfoTrue get(AgencyInfoTruePK agencyInfoTruePK) {
		// TODO Auto-generated method stub
		return (AgencyInfoTrue) get(getReferenceClass(),
				agencyInfoTruePK);
	}

	public AgencyInfoTruePK save(AgencyInfoTrue agencyInfotrue) {
		// TODO Auto-generated method stub
		return (AgencyInfoTruePK) super.save(agencyInfotrue);
	}

	public void saveOrUpdate(AgencyInfoTrue agencyInfotrue) {
		// TODO Auto-generated method stub
		super.saveOrUpdate(agencyInfotrue);
	}

	public void update(AgencyInfoTrue agencyInfotrue) {
		// TODO Auto-generated method stub
		super.update(agencyInfotrue);
	}

	public void delete(AgencyInfoTruePK agencyInfoTruePK) {
		// TODO Auto-generated method stub
		super.delete((Object) load(agencyInfoTruePK));
	}

	public void delete(AgencyInfoTrue agencyInfotrue) {
		// TODO Auto-generated method stub
		super.delete((Object) agencyInfotrue);
	}


}