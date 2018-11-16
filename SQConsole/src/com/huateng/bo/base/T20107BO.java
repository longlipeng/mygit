package com.huateng.bo.base;
import java.util.List;


import com.huateng.po.base.TblRespCodeInfo;
public interface T20107BO {
	/**
	 * 查询所有应答码信息
	 * @return
	 */
	public List<TblRespCodeInfo> get();
	/**
	 * 查询应答码信息
	 * @param respId    应答码编号
	 * @return
	 */
	public TblRespCodeInfo get(String respId );
	/**
	 * 新增应答码信息
	 * @param tblRespCodeInfo    应答码信息
	 * @return
	 */
	public String add(TblRespCodeInfo tblRespCodeInfo);
	/*
	 * 批量更新应答码信息
	 * @param tblRespCodeInfoList    应答码信息集合
	 * @return
	 */
	public String update(List<TblRespCodeInfo> tblRespCodeInfoList);
	/**
	 * 更新应答码信息
	 * @param tblRespCodeInfo     应答码信息
	 * @return
	 */
	public String update(TblRespCodeInfo tblRespCodeInfo);
	/**
	 * 删除应答码信息
	 * @param tblRespCodeInfo    应答码信息
	 * @return
	 */
	public String delete(TblRespCodeInfo tblRespCodeInfo);
	/**
	 * 删除应答码信息
	 * @param respId    应答码编号
	 * @return
	 */
	public String delete(String respId);
}
