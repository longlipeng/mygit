package com.huateng.dao.iface.base;

import java.util.List;

import com.huateng.po.base.TblInstBdbBankCode;




public interface TblInstBdbBankCodeDAO {
	public TblInstBdbBankCode load(String id);
	public void delete(String id);
	public String  save(TblInstBdbBankCode tblInstBdbBankCode);
	public TblInstBdbBankCode query(String id);
	public void update(TblInstBdbBankCode tblInstBdbBankCode);
	public void saveOrUpdate(TblInstBdbBankCode tblInstBdbBankCode);

}
