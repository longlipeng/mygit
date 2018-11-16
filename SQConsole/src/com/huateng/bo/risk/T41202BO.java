package com.huateng.bo.risk;

import java.util.List;

import com.huateng.po.risk.TblWhiteListTmp;

public interface T41202BO {
	//查
    public TblWhiteListTmp get(String key);
    //增
    public String add(TblWhiteListTmp tblWhiteListTmp);
    //删
    public String delete(String key);
    //改
    public String update(TblWhiteListTmp tblWhiteListTmp);
    
    //改All
    public String update(List<TblWhiteListTmp> list);
}
