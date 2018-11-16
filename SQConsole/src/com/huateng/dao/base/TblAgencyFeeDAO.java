package com.huateng.dao.base;

import java.util.List;
import com.huateng.po.base.AgencyFeeLub;
import com.huateng.po.base.AgencyFeeLubTmp;
import com.huateng.dao._RootDAO;

public class TblAgencyFeeDAO extends _RootDAO<com.huateng.po.base.AgencyInfo> 
				implements com.huateng.dao.iface.base.TblAgencyFeeDAO{
	public TblAgencyFeeDAO(){}
	public List<com.huateng.po.base.AgencyFeeLub> findAll() {
		return null;
	}
	
	@Override
	protected Class<AgencyFeeLub> getReferenceClass() {
		return AgencyFeeLub.class;
	}
	
	public AgencyFeeLub cast(Object object) {
		return (AgencyFeeLub) object;
	}
	
	public AgencyFeeLub load(String key) {
		return (AgencyFeeLub) load(getReferenceClass(),key);
	}
	
	public AgencyFeeLub get(String key) {
		return (com.huateng.po.base.AgencyFeeLub) get(getReferenceClass(),key);
	}
	
	public AgencyFeeLubTmp getTmp(String key) {
		return (AgencyFeeLubTmp) get(getReferenceClass(),key);
	}
	
	public String save(AgencyFeeLub agencyFeeLub) {
		return (java.lang.String) super.save(agencyFeeLub);
	}
	
	public String save(AgencyFeeLubTmp agencyFeeLub) {
		return (java.lang.String) super.save(agencyFeeLub);
	}
	
	public void saveOrUpdate(AgencyFeeLub agencyFeeLub) {
		super.saveOrUpdate(agencyFeeLub);
	}
	
	public void update(AgencyFeeLub agencyFeeLub) {
		super.update(agencyFeeLub);
	}
	
	public void update(AgencyFeeLubTmp agencyFeeLub) {
		super.update(agencyFeeLub);
	}
	
	public void delete(String id) {
		super.delete((Object) load(id));
	}
	
	public void delete(AgencyFeeLub agencyFeeLub) {
		super.delete((Object) agencyFeeLub);
	}
	
	public void saveOrUpdate(AgencyFeeLubTmp agencyFeeLub) {
		super.saveOrUpdate(agencyFeeLub);
		
	}
	public void delete2(String id) {
		super.delete((Object) load2(id));
	}
	public void delete2(AgencyFeeLubTmp agencyFeeLubTmp) {
		super.delete((Object) agencyFeeLubTmp);
	}
	public AgencyFeeLubTmp load2(String key) {
		return (AgencyFeeLubTmp) load(getReferenceClass(),key);
	}
}
