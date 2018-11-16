package com.huateng.bo.base;

import java.util.List;

import com.huateng.po.base.TblAgentDivideTmp;

public interface T11503BO {
    public String save(TblAgentDivideTmp tblAgentDivideTmp);
    public String delete(String uuid);
    public String delete(List<String> list);
    public TblAgentDivideTmp query(String uuid);
    public String update(TblAgentDivideTmp tblAgentDivideTmp);
    public String update(List<TblAgentDivideTmp> list);
    public String saveOrUpdate(List<TblAgentDivideTmp> list);
    public String saveOrUpdate(TblAgentDivideTmp tblAgentDivideTmp);
}
