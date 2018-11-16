package com.huateng.bo.risk;

import java.util.List;

import com.huateng.po.risk.TblRiskChlTranLimit;;

public interface T40212BO {
	/**
	 * 查询分公司信息
	 * @param Id    分公司编号
	 * @return
	 */
	public TblRiskChlTranLimit get(String Id);
	/**
	 * 添加分公司信息
	 * @param tblBranchManage    分公司信息
	 * @return
	 */
	public String add(TblRiskChlTranLimit tblRiskChlTranLimit);
	/**
	 * 更新分公司信息
	 * @param tblBranchManage    分公司信息列表
	 * @return
	 */
	public String update(List<TblRiskChlTranLimit> tblRiskChlTranLimit);
	public String update(com.huateng.po.risk.TblRiskChlTranLimit tblRiskChlTranLimit) throws Exception;
	/**
	 * 删除分公司信息
	 * @param tblBranchManage    分公司信息
	 * @return
	 */
	public String delete(TblRiskChlTranLimit tblRiskChlTranLimit);
	/**
	 * 删除分公司信息
	 * @param brhId    分公司编号
	 * @return
	 */
	public String delete(String Id);
}
