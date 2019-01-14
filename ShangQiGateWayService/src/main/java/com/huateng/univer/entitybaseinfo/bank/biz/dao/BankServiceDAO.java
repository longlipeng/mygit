package com.huateng.univer.entitybaseinfo.bank.biz.dao;

import com.huateng.framework.ibatis.dao.BankDAO;
import com.huateng.framework.ibatis.model.Bank;

public interface BankServiceDAO extends BankDAO {
	int updateAccountFlag(Bank record);
}
