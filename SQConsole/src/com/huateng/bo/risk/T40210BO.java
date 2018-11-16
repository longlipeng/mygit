package com.huateng.bo.risk;

import java.util.List;

import com.huateng.po.risk.TblRiskChlTranCtl;

public interface T40210BO {
	/**
	 * 查询分公司信息
	 * @param Id    分公司编号
	 * @return
	 */
	public TblRiskChlTranCtl get(String Id);
	/**
	 * 添加分公司信息
	 * @param tblBranchManage    分公司信息
	 * @return
	 */
	public String add(TblRiskChlTranCtl tblRiskChlTranCtl);
	/**
	 * 更新分公司信息
	 * @param tblBranchManage    分公司信息列表
	 * @return
	 */
	public String update(List<TblRiskChlTranCtl> tblRiskChlTranCtl);
	public String update(com.huateng.po.risk.TblRiskChlTranCtl tblRiskChlTranCtl) throws Exception;
	/**
	 * 删除分公司信息
	 * @param tblBranchManage    分公司信息
	 * @return
	 */
	public String delete(TblRiskChlTranCtl tblRiskChlTranCtl);
	/**
	 * 删除分公司信息
	 * @param brhId    分公司编号
	 * @return
	 */
	public String delete(String Id);
}
