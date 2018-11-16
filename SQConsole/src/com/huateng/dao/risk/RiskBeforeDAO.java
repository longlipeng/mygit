package com.huateng.dao.risk;

import java.util.List;

import com.huateng.dao._RootDAO;
import com.huateng.po.risk.RiskBefore;


public class RiskBeforeDAO extends _RootDAO<com.huateng.po.risk.RiskBefore> implements com.huateng.dao.iface.risk.RiskBeforeDAO{
public RiskBeforeDAO(){}
public List<com.huateng.po.risk.RiskBefore> findAll() {
	return null;
}

@Override
protected Class<com.huateng.po.risk.RiskBefore> getReferenceClass() {
	// TODO Auto-generated method stub
	return com.huateng.po.risk.RiskBefore.class;
}

public RiskBefore cast(Object object) {

	return (com.huateng.po.risk.RiskBefore) object;
}

public com.huateng.po.risk.RiskBefore load(String key) {
	// TODO Auto-generated method stub
	return (com.huateng.po.risk.RiskBefore) load(getReferenceClass(),
			key);
}

public com.huateng.po.risk.RiskBefore get(String key) {
	// TODO Auto-generated method stub
	return (com.huateng.po.risk.RiskBefore) get(getReferenceClass(),
			key);
}

public String save(com.huateng.po.risk.RiskBefore riskBefore) {
	// TODO Auto-generated method stub
	return (java.lang.String) super.save(riskBefore);
}

public void saveOrUpdate(com.huateng.po.risk.RiskBefore riskBefore) {
	// TODO Auto-generated method stub
	super.saveOrUpdate(riskBefore);
}

public void update(com.huateng.po.risk.RiskBefore riskBefore) {
	// TODO Auto-generated method stub
	super.update(riskBefore);
}

public void delete(String id) {
	// TODO Auto-generated method stub
	super.delete((Object) load(id));
}

public void delete(com.huateng.po.risk.RiskBefore riskBefore) {
	// TODO Auto-generated method stub
	super.delete((Object) riskBefore);
}

}
