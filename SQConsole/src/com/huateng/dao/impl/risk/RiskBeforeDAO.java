package com.huateng.dao.impl.risk;

import java.util.List;

import com.huateng.dao._RootDAO;
import com.huateng.po.risk.RiskBefore;

public class RiskBeforeDAO extends _RootDAO<com.huateng.po.risk.RiskBefore> implements com.huateng.dao.iface.risk.RiskBeforeDAO{
	public RiskBeforeDAO() {
	}

	public List<RiskBefore> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Class getReferenceClass() {
		// TODO Auto-generated method stub
		return com.huateng.po.risk.RiskBefore.class;
	}

	public com.huateng.po.risk.RiskBefore cast(Object object) {
		return (com.huateng.po.risk.RiskBefore) object;
	}

	public RiskBefore load(String key) {
		// TODO Auto-generated method stub
		return (com.huateng.po.risk.RiskBefore) load(getReferenceClass(),
				key);
	}

	public RiskBefore get(String key) {
		// TODO Auto-generated method stub
		return (com.huateng.po.risk.RiskBefore) get(getReferenceClass(),
				key);
	}

	public String save(RiskBefore riskBefore) {
		// TODO Auto-generated method stub
		return (java.lang.String) super.save(riskBefore);
	}

	public void saveOrUpdate(RiskBefore riskBefore) {
		// TODO Auto-generated method stub
		super.saveOrUpdate(riskBefore);
	}

	public void update(RiskBefore riskBefore) {
		// TODO Auto-generated method stub
		super.update(riskBefore);
	}

	public void delete(String id) {
		// TODO Auto-generated method stub
		super.delete((Object) load(id));
	}

	public void delete(RiskBefore riskBefore) {
		// TODO Auto-generated method stub
		super.delete((Object) riskBefore);
	}
}
