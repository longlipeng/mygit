package com.huateng.dao.base;

import java.util.List;

import com.huateng.po.base.AgencyInfoTrue;
import com.huateng.po.base.AgencyInfoTruePK;
import com.huateng.dao._RootDAO;


public class TblAgencyInfoTrueDAO extends _RootDAO<com.huateng.po.base.AgencyInfoTrue> implements com.huateng.dao.iface.base.TblAgencyInfoTrueDAO {
public TblAgencyInfoTrueDAO(){}
public List<com.huateng.po.base.AgencyInfoTrue> findAll() {
	return null;
}

@Override
protected Class<com.huateng.po.base.AgencyInfoTrue> getReferenceClass() {
	// TODO Auto-generated method stub
	return com.huateng.po.base.AgencyInfoTrue.class;
}

public AgencyInfoTrue cast(Object object) {

	return (com.huateng.po.base.AgencyInfoTrue) object;
}

public com.huateng.po.base.AgencyInfoTrue load(String key) {
	// TODO Auto-generated method stub
	return (AgencyInfoTrue) load(getReferenceClass(),
			key);
}

public com.huateng.po.base.AgencyInfoTrue get(String key) {
	// TODO Auto-generated method stub
	return (com.huateng.po.base.AgencyInfoTrue) get(getReferenceClass(),
			key);
}

//public String save(com.huateng.po.base.AgencyInfoTrue agencyInfotrue) {
//	// TODO Auto-generated method stub
//	return (java.lang.String) super.save(agencyInfotrue);
//}

public void saveOrUpdate(com.huateng.po.base.AgencyInfoTrue agencyInfotrue) {
	// TODO Auto-generated method stub
	super.saveOrUpdate(agencyInfotrue);
}

public void update(com.huateng.po.base.AgencyInfoTrue agencyInfotrue) {
	// TODO Auto-generated method stub
	super.update(agencyInfotrue);
}

public void delete(String id) {
	// TODO Auto-generated method stub
	super.delete((Object) load(id));
}

public void delete(com.huateng.po.base.AgencyInfoTrue agencyInfotrue) {
	// TODO Auto-generated method stub
	super.delete((Object) agencyInfotrue);
}
@Override
public AgencyInfoTrue get(AgencyInfoTruePK agencyInfoTruePK) {
	// TODO Auto-generated method stub
	return null;
}
@Override
public AgencyInfoTrue load(AgencyInfoTruePK agencyInfoTruePK) {
	// TODO Auto-generated method stub
	return null;
}
@Override
public AgencyInfoTruePK save(AgencyInfoTrue agencyInfotrue) {
	// TODO Auto-generated method stub
	return null;
}
@Override
public void delete(AgencyInfoTruePK agencyInfoTruePK) {
	// TODO Auto-generated method stub
	
}

}
