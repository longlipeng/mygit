package com.huateng.bo.base;

import com.huateng.po.base.TblAttendanceBrh;

public interface T10600BO {
	//查询签到信息
	public TblAttendanceBrh get(String brhId);
	//更新签到信息
	public String update(TblAttendanceBrh tblAttendanceBrh);
}
