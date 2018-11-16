package com.huateng.bo.base;

import java.util.List;

import com.huateng.po.base.TblInstBdbBankCodeTmp;


public interface T1010601BO {
    public String save(TblInstBdbBankCodeTmp tblInstBdbBankCodeTmp);
    public String save(List<TblInstBdbBankCodeTmp> list);
    public String delete(String id);
    public String delete(List<String> list);
    public TblInstBdbBankCodeTmp query(String id);
    public String update(TblInstBdbBankCodeTmp tblInstBdbBankCodeTmp);
    public String update(List<TblInstBdbBankCodeTmp> list);
    public String saveOrUpdate(List<TblInstBdbBankCodeTmp> list);
    public String saveOrUpdate(TblInstBdbBankCodeTmp tblInstBdbBankCodeTmp);
}
