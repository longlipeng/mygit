package com.huateng.bo.base;

import java.util.List;
import com.huateng.po.base.TblRspCodeMap;
import com.huateng.po.base.TblRspCodeMapPK;

public interface T20109BO {
	public TblRspCodeMap get(TblRspCodeMapPK id); // 获取应答码信息
    
	public String add(TblRspCodeMap tblRspCodeMap);//增加应答码信息
	
	public String update(TblRspCodeMap tblRspCodeMap); // 更新应答码信息

	public String delete(TblRspCodeMapPK id);// 删除应答码信息
	
	public String delete(TblRspCodeMap tblRspCodeMap);//删除应答码信息
	
	public String update(List<TblRspCodeMap> tblRspCodeMap); // 更新应答码信息
}
