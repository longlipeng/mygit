package com.huateng.univer.entitybaseinfo.bank.biz.dao.impl;

import com.huateng.framework.ibatis.dao.BankDAOImpl;
import com.huateng.framework.ibatis.model.Bank;
import com.huateng.univer.entitybaseinfo.bank.biz.dao.BankServiceDAO;

public class BankServiceDAOImpl extends BankDAOImpl implements BankServiceDAO {
	public int updateAccountFlag(Bank record) {
		int rows = getSqlMapClientTemplate().update(
				"CUSTOMER.updateAccountFlag", record);
		return rows;
	}
}
