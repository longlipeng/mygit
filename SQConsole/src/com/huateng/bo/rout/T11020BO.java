package com.huateng.bo.rout;

import java.util.List;

import com.huateng.po.rout.TblTermChannelInf2;

public interface T11020BO {

	public TblTermChannelInf2 get(String id); //获取交行终端通道配置信息
    
	public String add(TblTermChannelInf2 tblTermChannelInf2);//增加交行终端通道配置信息
	
	public String update(TblTermChannelInf2 tblTermChannelInf2); // 更新交行终端通道配置信息

	public String delete(String id);//删除交行终端通道配置信息
	
	public String update(List<TblTermChannelInf2> tblTermChannelInf2s); // 更新交行终端通道配置信息
}
