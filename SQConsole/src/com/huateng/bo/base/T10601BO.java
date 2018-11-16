package com.huateng.bo.base;

import com.huateng.po.base.TblSignInf;

public interface T10601BO {
	//查询签到信息
	public TblSignInf get(String termid);
	//更新签到信息
	public String update(TblSignInf tblSignInf);
}
