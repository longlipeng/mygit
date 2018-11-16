package com.huateng.dao.risk;

import java.util.List;

import com.huateng.dao._RootDAO;
import com.huateng.po.risk.TblRiskChlTranLimit;


public class TblRiskChlTranLimitDAO extends _RootDAO<com.huateng.po.risk.TblRiskChlTranLimit> implements com.huateng.dao.iface.risk.TblRiskChlTranLimitDAO{
public TblRiskChlTranLimitDAO(){}
public List<com.huateng.po.risk.TblRiskChlTranLimit> findAll() {
	return null;
}

@Override
protected Class<com.huateng.po.risk.TblRiskChlTranLimit> getReferenceClass() {
	// TODO Auto-generated method stub
	return com.huateng.po.risk.TblRiskChlTranLimit.class;
}

public TblRiskChlTranLimit cast(Object object) {

	return (com.huateng.po.risk.TblRiskChlTranLimit) object;
}

public com.huateng.po.risk.TblRiskChlTranLimit load(String key) {
	// TODO Auto-generated method stub
	return (com.huateng.po.risk.TblRiskChlTranLimit) load(getReferenceClass(),
			key);
}

public com.huateng.po.risk.TblRiskChlTranLimit get(String key) {
	// TODO Auto-generated method stub
	return (com.huateng.po.risk.TblRiskChlTranLimit) get(getReferenceClass(),
			key);
}

public String save(com.huateng.po.risk.TblRiskChlTranLimit tblRiskChlTranLimit) {
	// TODO Auto-generated method stub
	return (java.lang.String) super.save(tblRiskChlTranLimit);
}

public void saveOrUpdate(com.huateng.po.risk.TblRiskChlTranLimit tblRiskChlTranLimit) {
	// TODO Auto-generated method stub
	super.saveOrUpdate(tblRiskChlTranLimit);
}

public void update(com.huateng.po.risk.TblRiskChlTranLimit tblRiskChlTranLimit) {
	// TODO Auto-generated method stub
	super.update(tblRiskChlTranLimit);
}

public void delete(String id) {
	// TODO Auto-generated method stub
	super.delete((Object) load(id));
}

public void delete(com.huateng.po.risk.TblRiskChlTranLimit tblRiskChlTranLimit) {
	// TODO Auto-generated method stub
	super.delete((Object) tblRiskChlTranLimit);
}

}
