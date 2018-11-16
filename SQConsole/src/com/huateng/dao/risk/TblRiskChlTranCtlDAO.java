package com.huateng.dao.risk;

import java.util.List;

import com.huateng.dao._RootDAO;
import com.huateng.po.risk.TblRiskChlTranCtl;;


public class TblRiskChlTranCtlDAO extends _RootDAO<com.huateng.po.risk.TblRiskChlTranCtl> implements com.huateng.dao.iface.risk.TblRiskChlTranCltDAO{
public TblRiskChlTranCtlDAO(){}
public List<com.huateng.po.risk.TblRiskChlTranCtl> findAll() {
	return null;
}

@Override
protected Class<com.huateng.po.risk.TblRiskChlTranCtl> getReferenceClass() {
	// TODO Auto-generated method stub
	return com.huateng.po.risk.TblRiskChlTranCtl.class;
}

public TblRiskChlTranCtl cast(Object object) {

	return (com.huateng.po.risk.TblRiskChlTranCtl) object;
}

public com.huateng.po.risk.TblRiskChlTranCtl load(String key) {
	// TODO Auto-generated method stub
	return (com.huateng.po.risk.TblRiskChlTranCtl) load(getReferenceClass(),
			key);
}

public com.huateng.po.risk.TblRiskChlTranCtl get(String key) {
	// TODO Auto-generated method stub
	return (com.huateng.po.risk.TblRiskChlTranCtl) get(getReferenceClass(),
			key);
}

public String save(com.huateng.po.risk.TblRiskChlTranCtl tblRiskChlTranCtl) {
	// TODO Auto-generated method stub
	return (java.lang.String) super.save(tblRiskChlTranCtl);
}

public void saveOrUpdate(com.huateng.po.risk.TblRiskChlTranCtl tblRiskChlTranCtl) {
	// TODO Auto-generated method stub
	super.saveOrUpdate(tblRiskChlTranCtl);
}

public void update(com.huateng.po.risk.TblRiskChlTranCtl tblRiskChlTranCtl) {
	// TODO Auto-generated method stub
	super.update(tblRiskChlTranCtl);
}

public void delete(String id) {
	// TODO Auto-generated method stub
	super.delete((Object) load(id));
}

public void delete(com.huateng.po.risk.TblRiskChlTranCtl tblRiskChlTranCtl) {
	// TODO Auto-generated method stub
	super.delete((Object) tblRiskChlTranCtl);
}

}
