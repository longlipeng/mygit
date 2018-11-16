package com.huateng.dao.impl.risk;

import java.util.List;

import com.huateng.dao._RootDAO;
import com.huateng.po.risk.TblRiskChlTranLimit;;

public class TblRiskChlTranLimitDAO extends _RootDAO<com.huateng.po.risk.TblRiskChlTranLimit> implements com.huateng.dao.iface.risk.TblRiskChlTranLimitDAO{
	public TblRiskChlTranLimitDAO() {
	}

	public List<TblRiskChlTranLimit> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Class getReferenceClass() {
		// TODO Auto-generated method stub
		return com.huateng.po.risk.TblRiskChlTranLimit.class;
	}

	public TblRiskChlTranLimit cast(Object object) {
		return (com.huateng.po.risk.TblRiskChlTranLimit) object;
	}

	public TblRiskChlTranLimit load(String key) {
		// TODO Auto-generated method stub
		return (com.huateng.po.risk.TblRiskChlTranLimit) load(getReferenceClass(),
				key);
	}

	public TblRiskChlTranLimit get(String key) {
		// TODO Auto-generated method stub
		return (com.huateng.po.risk.TblRiskChlTranLimit) get(getReferenceClass(),
				key);
	}

	public String save(TblRiskChlTranLimit tblRiskChlTranLimit) {
		// TODO Auto-generated method stub
		return (java.lang.String) super.save(tblRiskChlTranLimit);
	}

	public void saveOrUpdate(TblRiskChlTranLimit tblRiskChlTranLimit) {
		// TODO Auto-generated method stub
		super.saveOrUpdate(tblRiskChlTranLimit);
	}

	public void update(TblRiskChlTranLimit tblRiskChlTranLimit) {
		// TODO Auto-generated method stub
		super.update(tblRiskChlTranLimit);
	}

	public void delete(String id) {
		// TODO Auto-generated method stub
		super.delete((Object) load(id));
	}

	public void delete(TblRiskChlTranLimit tblRiskChlTranLimit) {
		// TODO Auto-generated method stub
		super.delete((Object) tblRiskChlTranLimit);
	}
}
