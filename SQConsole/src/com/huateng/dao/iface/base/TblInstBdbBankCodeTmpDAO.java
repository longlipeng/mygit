package com.huateng.dao.iface.base;

import java.util.List;

import com.huateng.po.base.TblInstBdbBankCodeTmp;



public interface TblInstBdbBankCodeTmpDAO {
	public TblInstBdbBankCodeTmp load(String id);
	public void delete(String id);
	public String  save(TblInstBdbBankCodeTmp tblInstBdbBankCodeTmp);
	public TblInstBdbBankCodeTmp query(String id);
	public void update(TblInstBdbBankCodeTmp tblInstBdbBankCodeTmp);
	public void saveOrUpdate(TblInstBdbBankCodeTmp tblInstBdbBankCodeTmp);

}
