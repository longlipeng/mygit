package com.huateng.bo.base;

import java.util.List;

import com.huateng.po.base.TblBranchManage;;

public interface T10108BO {
	/**
	 * 查询分公司信息
	 * @param Id    分公司编号
	 * @return
	 */
	public TblBranchManage get(String Id);
	/**
	 * 添加分公司信息
	 * @param tblBranchManage    分公司信息
	 * @return
	 */
	public String add(TblBranchManage tblBranchManage);
	/**
	 * 更新分公司信息
	 * @param tblBranchManage    分公司信息列表
	 * @return
	 */
	public String update(List<TblBranchManage> tblBranchManage);
	/**
	 * 删除分公司信息
	 * @param tblBranchManage    分公司信息
	 * @return
	 */
	public String delete(TblBranchManage tblBranchManage);
	/**
	 * 删除分公司信息
	 * @param brhId    分公司编号
	 * @return
	 */
	public String delete(String Id);
}
