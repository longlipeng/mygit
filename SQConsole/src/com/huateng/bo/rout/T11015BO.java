package com.huateng.bo.rout;

import java.util.List;
import com.huateng.po.rout.TblTermChannelInf;
import com.huateng.po.rout.TblTermChannelInfTmp;

public interface T11015BO {

public TblTermChannelInf get(String id); //获取终端通道配置信息
public TblTermChannelInfTmp getTmp(String id); //获取终端通道配置信息
    
	public String add(TblTermChannelInf tblTermChannelInf);//增加终端通道配置信息
	
	public String update(TblTermChannelInf tblTermChannelInf); // 更新终端通道配置信息
	
//	public String updateTmpOne(TblTermChannelInfTmp tblTermChannelInfTmp); // 更新临时表终端通道配置信息

	public String delete(String id);//删除终端通道配置信息
	
	public String update(List<TblTermChannelInf> tblTermChannelInfs); // 更新终端通道配置信息

	public void deleteall(List<Integer> list);//批量删除

	public String add(TblTermChannelInfTmp tblTermChannelInfTmp);//增加临时表终端通道配置信息
	
	public String update(TblTermChannelInfTmp tblTermChannelInfTmp); // 更新临时表终端通道配置信息
	
	public String updateTmp(List<TblTermChannelInfTmp> tblTermChannelInftmp); // 更新临时表终端通道配置信息
}
