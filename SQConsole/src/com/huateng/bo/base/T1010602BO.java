package com.huateng.bo.base;

import java.util.List;

import com.huateng.po.base.TblInstBdbBankCode;


public interface T1010602BO {
    public String save(TblInstBdbBankCode tblInstBdbBankCode);
    public String save(List<TblInstBdbBankCode> list);
    public String delete(String id);
    public String delete(List<String> list);
    public TblInstBdbBankCode query(String id);
    public String update(TblInstBdbBankCode tblInstBdbBankCode);
    public String update(List<TblInstBdbBankCode> list);
    public String saveOrUpdate(List<TblInstBdbBankCode> list);
    public String saveOrUpdate(TblInstBdbBankCode tblInstBdbBankCode);
}
