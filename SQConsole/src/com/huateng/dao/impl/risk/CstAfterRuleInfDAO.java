package com.huateng.dao.impl.risk;

import java.util.List;
import com.huateng.dao._RootDAO;
import com.huateng.po.risk.CstAfterRuleInf;
import com.huateng.po.risk.CstAfterRuleInfPK;

public class CstAfterRuleInfDAO extends _RootDAO<CstAfterRuleInf> implements
		com.huateng.dao.iface.risk.CstAfterRuleInfDAO {

	public CstAfterRuleInfDAO(){}
	
	public void delete(CstAfterRuleInfPK id) {
		super.delete((Object) load(id));
	}

	public void delete(CstAfterRuleInf cstAfterRuleInf) {
		super.delete((Object) cstAfterRuleInf);
	}

	public List<CstAfterRuleInf> findAll() {
		return null;
	}

	public CstAfterRuleInf get(CstAfterRuleInfPK key) {
		return (CstAfterRuleInf) get(getReferenceClass(), key);
	}

	public CstAfterRuleInf load(CstAfterRuleInfPK key) {
		return (CstAfterRuleInf) load(getReferenceClass(), key);
	}

	public String save(CstAfterRuleInf cstAfterRuleInf) {
		return (String) super.save(cstAfterRuleInf);
	}

	public void saveOrUpdate(CstAfterRuleInf cstAfterRuleInf) {
		super.saveOrUpdate(cstAfterRuleInf);
	}

	public void update(CstAfterRuleInf cstAfterRuleInf) {
		super.update(cstAfterRuleInf);
	}

	public CstAfterRuleInf cast (Object object) {
		return (CstAfterRuleInf) object;
	}
	
	@SuppressWarnings("unchecked")
	public List<CstAfterRuleInf> loadAll(){
		return loadAll(getReferenceClass());
	}
	
	@Override
	protected Class<CstAfterRuleInf> getReferenceClass() {
		return CstAfterRuleInf.class;
	}

}
