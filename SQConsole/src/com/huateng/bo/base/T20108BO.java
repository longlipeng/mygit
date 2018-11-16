package com.huateng.bo.base;
import java.util.List;


import com.huateng.po.base.TblRespCodeInfoTrue;
public interface T20108BO {
	/**
	 * 查询所有应答码信息
	 * @return
	 */
	public List<TblRespCodeInfoTrue> get();
	/**
	 * 查询应答码信息
	 * @param respId    应答码编号
	 * @return
	 */
	public TblRespCodeInfoTrue get(String respId );
	/**
	 * 新增应答码信息
	 * @param TblRespCodeInfoTrue    应答码信息
	 * @return
	 */
	public String add(TblRespCodeInfoTrue tblRespCodeInfoTrue);
	/*
	 * 批量更新应答码信息
	 * @param TblRespCodeInfoTrueList    应答码信息集合
	 * @return
	 */
	public String update(List<TblRespCodeInfoTrue> tblRespCodeInfoTrueList);
	/**
	 * 更新应答码信息
	 * @param TblRespCodeInfoTrue     应答码信息
	 * @return
	 */
	public String update(TblRespCodeInfoTrue tblRespCodeInfoTrue);
	/**
	 * 删除应答码信息
	 * @param TblRespCodeInfoTrue    应答码信息
	 * @return
	 */
	public String delete(TblRespCodeInfoTrue tblRespCodeInfoTrue);
	/**
	 * 删除应答码信息
	 * @param respId    应答码编号
	 * @return
	 */
	public String delete(String respId);
}
