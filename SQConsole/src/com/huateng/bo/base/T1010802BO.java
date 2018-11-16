package com.huateng.bo.base;

import java.util.List;
import com.huateng.po.base.AgencyInfoTrue;
import com.huateng.po.base.AgencyInfoTruePK;

public interface T1010802BO {
	/**
	 * 查询机构信息
	 * @param Id    机构编号
	 * @return
	 */
	public AgencyInfoTrue get(AgencyInfoTruePK agencyInfoTruePK);
	/**
	 * 添加机构信息
	 * @param tblBranchManage    机构信息
	 * @return
	 */
	public String add(AgencyInfoTrue agencyInfotrue);
	/**
	 * 更新机构信息
	 * @param tblBranchManage    机构信息列表
	 * @return
	 */
	public String update(List<AgencyInfoTrue> agencyInfoTrue);
	/**
	 * 删除机构信息
	 * @param tblBranchManage    机构信息
	 * @return
	 */
	public String delete(AgencyInfoTrue agencyInfoTrue);
	/**
	 * 删除机构信息
	 * @param brhId    机构编号
	 * @return
	 */
	public String delete(AgencyInfoTruePK agencyInfoTruePK);
	 public void update(AgencyInfoTrue AgencyInfoTrue);
}
