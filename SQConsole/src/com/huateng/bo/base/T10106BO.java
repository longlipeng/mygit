package com.huateng.bo.base;

import java.util.List;

import com.huateng.po.base.AgencyInfo;
import com.huateng.po.base.AgencyInfoPK;

public interface T10106BO {
	/**
	 * 查询机构信息
	 * @param Id    分公司编号
	 * @return
	 */
	public AgencyInfo get(AgencyInfoPK agencyInfoPK);
	/**
	 * 添加机构信息
	 * @param tblBranchManage    机构信息
	 * @return
	 */
	public String add(AgencyInfo agencyInfo);
	/**
	 * 更新机构信息
	 * @param tblBranchManage    机构信息列表
	 * @return
	 */
	public String update(List<AgencyInfo> agencyInfo);
	/**
	 * 删除机构信息
	 * @param tblBranchManage    机构信息
	 * @return
	 */
	public String delete(AgencyInfo agencyInfo);
	/**
	 * 删除机构信息
	 * @param brhId    机构编号
	 * @return
	 */
	public String delete(AgencyInfoPK agencyInfoPK);
	/**
	 * 更新机构信息
	 * @param tblBranchManage    机构信息列表
	 * @return
	 */
	public void saveOrUpdate(AgencyInfo agencyInfo);
}
