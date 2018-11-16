package com.huateng.bo.rout;

import java.util.List;
import com.huateng.po.rout.TblRouteInfo;

public interface T11012BO {

	public TblRouteInfo get(String reserved); //获取路由信息
    
	public String add(TblRouteInfo tblRouteInfo);//增加路由信息
	
	public String update(TblRouteInfo tblRouteInfo); // 更新路由信息

	public String delete(String reserved);//删除路由信息
	
	public String update(List<TblRouteInfo> TblRouteInfos); // 更新路由信息
}
