package com.huateng.dao.impl.risk;

import java.util.List;
import com.huateng.dao._RootDAO;
import com.huateng.po.risk.TblRiskTermTranLimit;

public class TblRiskTermTranLimitDAO extends _RootDAO<TblRiskTermTranLimit>
		implements com.huateng.dao.iface.risk.TblRiskTermTranLimitDAO {

	public TblRiskTermTranLimitDAO () {}
	
	public void delete(String id) {
		super.delete((Object) load(id));
	}

	public void delete(TblRiskTermTranLimit tblRiskTermTranLimit) {
		super.delete((Object) tblRiskTermTranLimit);
	}

	public List<TblRiskTermTranLimit> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public TblRiskTermTranLimit get(String key) {
		return (TblRiskTermTranLimit) get(getReferenceClass(), key);
	}

	public TblRiskTermTranLimit cast (Object object) {
		return (TblRiskTermTranLimit) object;
	}

	public TblRiskTermTranLimit load(String key){
		return (TblRiskTermTranLimit) load(getReferenceClass(), key);
	}

	public List<TblRiskTermTranLimit> loadAll(){
		return loadAll(getReferenceClass());
	}
	
	public String save(TblRiskTermTranLimit tblRiskTermTranLimit) {
		return (String) super.save(tblRiskTermTranLimit);
	}

	public void saveOrUpdate(TblRiskTermTranLimit tblRiskTermTranLimit) {
		super.saveOrUpdate(tblRiskTermTranLimit);
	}

	public void update(TblRiskTermTranLimit tblRiskTermTranLimit) {
		super.update(tblRiskTermTranLimit);
	}

	@Override
	protected Class getReferenceClass() {
		return TblRiskTermTranLimit.class;
	}

}
