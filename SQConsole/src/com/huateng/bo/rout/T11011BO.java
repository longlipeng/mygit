package com.huateng.bo.rout;

import java.util.List;
import com.huateng.po.rout.TblRouteInfoTemp;

public interface T11011BO {
	
    public TblRouteInfoTemp get(String reserved); //获取路由信息
    
	public String add(TblRouteInfoTemp tblRouteInfoTemp);//增加路由信息
	
	public String update(TblRouteInfoTemp tblRouteInfoTemp); // 更新路由信息

	public String delete(String reserved);//删除路由信息
	
	public String update(List<TblRouteInfoTemp> TblRouteInfoTemps); // 更新路由信息
}
