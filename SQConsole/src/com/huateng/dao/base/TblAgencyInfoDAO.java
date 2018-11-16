package com.huateng.dao.base;

import java.util.List;

import com.huateng.po.base.AgencyInfo;
import com.huateng.po.base.AgencyInfoPK;
import com.huateng.dao._RootDAO;


public class TblAgencyInfoDAO extends _RootDAO<com.huateng.po.base.AgencyInfo> implements com.huateng.dao.iface.base.TblAgencyInfoDAO {
public TblAgencyInfoDAO(){}
public List<com.huateng.po.base.AgencyInfo> findAll() {
	return null;
}

@Override
protected Class<com.huateng.po.base.AgencyInfo> getReferenceClass() {
	// TODO Auto-generated method stub
	return com.huateng.po.base.AgencyInfo.class;
}

public AgencyInfo cast(Object object) {

	return (com.huateng.po.base.AgencyInfo) object;
}

public com.huateng.po.base.AgencyInfo load(String key) {
	// TODO Auto-generated method stub
	return (AgencyInfo) load(getReferenceClass(),
			key);
}

public com.huateng.po.base.AgencyInfo get(String key) {
	// TODO Auto-generated method stub
	return (com.huateng.po.base.AgencyInfo) get(getReferenceClass(),
			key);
}

//public String save(com.huateng.po.base.AgencyInfo agencyInfo) {
//	// TODO Auto-generated method stub
//	return (java.lang.String) super.save(agencyInfo);
//}

public void saveOrUpdate(com.huateng.po.base.AgencyInfo agencyInfo) {
	// TODO Auto-generated method stub
	super.saveOrUpdate(agencyInfo);
}

public void update(com.huateng.po.base.AgencyInfo agencyInfo) {
	// TODO Auto-generated method stub
	super.update(agencyInfo);
}

public void delete(String id) {
	// TODO Auto-generated method stub
	super.delete((Object) load(id));
}

public void delete(com.huateng.po.base.AgencyInfo agencyInfo) {
	// TODO Auto-generated method stub
	super.delete((Object) agencyInfo);
}
@Override
public AgencyInfo get(AgencyInfoPK agencyInfoPK) {
	// TODO Auto-generated method stub
	return null;
}
@Override
public AgencyInfo load(AgencyInfoPK agencyInfoPK) {
	// TODO Auto-generated method stub
	return null;
}
//@Override
//public AgencyInfoPK save(AgencyInfo agencyInfo) {
//	// TODO Auto-generated method stub
//	return null;
//}
@Override
public void delete(AgencyInfoPK agencyInfoPK) {
	// TODO Auto-generated method stub
	
}
@Override
public AgencyInfoPK save(AgencyInfo agencyInfo) {
	// TODO Auto-generated method stub
	return null;
}

}
