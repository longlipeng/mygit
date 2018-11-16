package com.huateng.bo.risk;

import java.util.List;

import com.huateng.po.risk.RiskBefore;

public interface T40402BO {
	/**
	 * 查询分公司信息
	 * @param Id    编号
	 * @return
	 */
	public RiskBefore get(String Id);
	/**
	 * 添加是前风险控制信息
	 * @param riskBefore    风险控制信息
	 * @return
	 */
	public String add(RiskBefore riskBefore);
	/**
	 * 更新风险控制信息
	 * @param RiskBefore    风险控制信息
	 * @return
	 */
	public String update(List<RiskBefore> riskBefore);
	/**
	 * 删除风险控制信息
	 * @param tblBranchManage    风险控制信息
	 * @return
	 */
	public String delete(RiskBefore riskBefore);
	/**
	 * 删除风险控制信息
	 * @param riskBefore   风险控制信息
	 * @return
	 */
	public String delete(String Id);
}
