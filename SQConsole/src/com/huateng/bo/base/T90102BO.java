package com.huateng.bo.base;

import java.util.List;

import com.huateng.po.base.TblBranchManageTrue;;

public interface T90102BO {
	/**
	 * 查询分公司信息
	 * @param Id    分公司编号
	 * @return
	 */
	public TblBranchManageTrue get(String Id);
	/**
	 * 添加通过的分公司信息
	 * @param tblBranchManage    分公司信息
	 * @return
	 */
	public String add(TblBranchManageTrue tblBranchManageTrue);
	/**
	 * 更新通过的分公司信息
	 * @param tblBranchManage    分公司信息列表
	 * @return
	 */
	public String update(List<TblBranchManageTrue> tblBranchManageTrue);
	/**
	 * 删除分公司信息
	 * @param tblBranchManage    分公司信息
	 * @return
	 */
	public String delete(TblBranchManageTrue tblBranchManageTrue);
	/**
	 * 删除分公司信息
	 * @param brhId    分公司编号
	 * @return
	 */
	public String delete(String Id);
	
    public void update(TblBranchManageTrue tblBranchManageTrue);
		
	
}
