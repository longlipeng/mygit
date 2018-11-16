package com.huateng.dao.impl.base;

import java.util.List;

import com.huateng.dao._RootDAO;
import com.huateng.po.base.AgencyFeeLub;
import com.huateng.po.base.AgencyFeeLubTmp;

public class TblAgencyFeeDAO extends _RootDAO<com.huateng.po.base.AgencyInfo> implements com.huateng.dao.iface.base.TblAgencyFeeDAO{
	public TblAgencyFeeDAO() {
	}

	public List<AgencyFeeLub> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Class getReferenceClass() {
		// TODO Auto-generated method stub
		return com.huateng.po.base.AgencyFeeLub.class;
	}

	protected Class getReferenceClassTmp(){
		return com.huateng.po.base.AgencyFeeLubTmp.class;
	}
	
	public com.huateng.po.base.AgencyFeeLub cast(Object object) {
		return (com.huateng.po.base.AgencyFeeLub) object;
	}

	public AgencyFeeLub load(String key) {
		// TODO Auto-generated method stub
		return (com.huateng.po.base.AgencyFeeLub) load(getReferenceClass(),key);
	}

	public AgencyFeeLubTmp load2(String key) {
		// TODO Auto-generated method stub
		return (com.huateng.po.base.AgencyFeeLubTmp) load(getReferenceClass(),key);
	}
	
	public AgencyFeeLub get(String key) {
		// TODO Auto-generated method stub
		return (com.huateng.po.base.AgencyFeeLub) get(getReferenceClass(),
				key);
	}
	
	public AgencyFeeLubTmp getTmp(String key) {
		// TODO Auto-generated method stub
		return (com.huateng.po.base.AgencyFeeLubTmp) get(getReferenceClassTmp(),
				key);
	}

	public String save(AgencyFeeLub agencyFeeLub) {
		// TODO Auto-generated method stub
		return (java.lang.String) super.save(agencyFeeLub);
	}
	
	public String save(AgencyFeeLubTmp agencyFeeLub) {
		// TODO Auto-generated method stub
		return (java.lang.String) super.save(agencyFeeLub);
	}

	public void saveOrUpdate(AgencyFeeLub agencyFeeLub) {
		// TODO Auto-generated method stub
		super.saveOrUpdate(agencyFeeLub);
	}

	public void saveOrUpdate(AgencyFeeLubTmp agencyFeeLub) {
		// TODO Auto-generated method stub
		super.saveOrUpdate(agencyFeeLub);
	}
	
	public void update(AgencyFeeLub agencyFeeLub) {
		// TODO Auto-generated method stub
		super.update(agencyFeeLub);
	}
	
	public void update(AgencyFeeLubTmp agencyFeeLub) {
		// TODO Auto-generated method stub
		super.update(agencyFeeLub);
	}

	public void delete(String id) {
		// TODO Auto-generated method stub
		super.delete((Object) load(id));
	}

	public void delete(AgencyFeeLub agencyFeeLub) {
		// TODO Auto-generated method stub
		super.delete((Object) agencyFeeLub);
	}
	
	public void delete2(String id) {
		// TODO Auto-generated method stub
		super.delete((Object) load2(id));
	}

	public void delete2(AgencyFeeLubTmp agencyFeeLubTmp) {
		// TODO Auto-generated method stub
		super.delete((Object) agencyFeeLubTmp);
	}
}
