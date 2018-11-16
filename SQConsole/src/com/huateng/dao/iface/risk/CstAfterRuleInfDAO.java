package com.huateng.dao.iface.risk;

import java.util.List;

import com.huateng.po.risk.CstAfterRuleInf;
import com.huateng.po.risk.CstAfterRuleInfPK;

public interface CstAfterRuleInfDAO {

	public CstAfterRuleInf get(CstAfterRuleInfPK key);

	public CstAfterRuleInf load(CstAfterRuleInfPK key);

	public List<CstAfterRuleInf> findAll ();

	public String save(CstAfterRuleInf cstAfterRuleInf);

	public void saveOrUpdate(CstAfterRuleInf cstAfterRuleInf);

	public void update(CstAfterRuleInf cstAfterRuleInf);

	public void delete(CstAfterRuleInfPK id);

	public void delete(CstAfterRuleInf cstAfterRuleInf);
	
}
