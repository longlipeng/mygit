package com.huateng.bo.impl.risk;

import com.huateng.bo.risk.T40501BO;
import com.huateng.common.Constants;
import com.huateng.dao.iface.risk.CstAfterRuleInfDAO;
import com.huateng.po.risk.CstAfterRuleInf;
import com.huateng.po.risk.CstAfterRuleInfPK;

public class T40501BOTarget implements T40501BO {

	private CstAfterRuleInfDAO cstAfterRuleInfDAO;
	
	public CstAfterRuleInfDAO getCstAfterRuleInfDAO() {
		return cstAfterRuleInfDAO;
	}

	public void setCstAfterRuleInfDAO(CstAfterRuleInfDAO cstAfterRuleInfDAO) {
		this.cstAfterRuleInfDAO = cstAfterRuleInfDAO;
	}

	public String add(CstAfterRuleInf cstAfterRuleInf) throws Exception {
		cstAfterRuleInfDAO.saveOrUpdate(cstAfterRuleInf);//save composite
		return Constants.SUCCESS_CODE;
	}

	public void delete(CstAfterRuleInfPK key) throws Exception {
		cstAfterRuleInfDAO.delete(key);
	}

	public CstAfterRuleInf get(CstAfterRuleInfPK key) {
		return cstAfterRuleInfDAO.get(key);
	}

	public String update(CstAfterRuleInf cstAfterRuleInf) throws Exception {
		cstAfterRuleInfDAO.update(cstAfterRuleInf);
		return Constants.SUCCESS_CODE;
	}

}
