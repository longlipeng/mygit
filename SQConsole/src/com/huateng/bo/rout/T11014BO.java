package com.huateng.bo.rout;

import java.util.List;
import com.huateng.po.rout.TblRouteChgInf;

public interface T11014BO {

	public TblRouteChgInf get(String destInstId); //获取路由切换信息
    
	public String add(TblRouteChgInf tblRouteChgInf);//增加路由切换信息
	
	public String update(TblRouteChgInf tblRouteChgInf); // 更新路由切换信息

	public String delete(String destInstId);//删除路由切换信息
	
	public String update(List<TblRouteChgInf> tblRouteChgInfs); // 更新路由切换信息
	
}
