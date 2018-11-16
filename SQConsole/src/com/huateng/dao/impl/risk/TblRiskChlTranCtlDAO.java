package com.huateng.dao.impl.risk;

import java.util.List;

import com.huateng.dao._RootDAO;
import com.huateng.po.risk.TblRiskChlTranCtl;;

public class TblRiskChlTranCtlDAO extends _RootDAO<com.huateng.po.risk.TblRiskChlTranCtl> implements com.huateng.dao.iface.risk.TblRiskChlTranCltDAO{
	public TblRiskChlTranCtlDAO() {
	}

	public List<TblRiskChlTranCtl> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Class getReferenceClass() {
		// TODO Auto-generated method stub
		return com.huateng.po.risk.TblRiskChlTranCtl.class;
	}

	public com.huateng.po.risk.TblRiskChlTranCtl cast(Object object) {
		return (com.huateng.po.risk.TblRiskChlTranCtl) object;
	}

	public TblRiskChlTranCtl load(String key) {
		// TODO Auto-generated method stub
		return (com.huateng.po.risk.TblRiskChlTranCtl) load(getReferenceClass(),
				key);
	}

	public TblRiskChlTranCtl get(String key) {
		// TODO Auto-generated method stub
		return (com.huateng.po.risk.TblRiskChlTranCtl) get(getReferenceClass(),
				key);
	}

	public String save(TblRiskChlTranCtl tblRiskChlTranCtl) {
		// TODO Auto-generated method stub
		return (java.lang.String) super.save(tblRiskChlTranCtl);
	}

	public void saveOrUpdate(TblRiskChlTranCtl tblRiskChlTranCtl) {
		// TODO Auto-generated method stub
		super.saveOrUpdate(tblRiskChlTranCtl);
	}

	public void update(TblRiskChlTranCtl tblRiskChlTranCtl) {
		// TODO Auto-generated method stub
		super.update(tblRiskChlTranCtl);
	}

	public void delete(String id) {
		// TODO Auto-generated method stub
		super.delete((Object) load(id));
	}

	public void delete(TblRiskChlTranCtl tblRiskChlTranCtl) {
		// TODO Auto-generated method stub
		super.delete((Object) tblRiskChlTranCtl);
	}
}
