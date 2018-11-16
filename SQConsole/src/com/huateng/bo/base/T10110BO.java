package com.huateng.bo.base;

import java.util.List;

import com.huateng.po.base.AgencyFeeLub;
import com.huateng.po.base.AgencyFeeLubTmp;

public interface T10110BO {
	/**
	 * 查询机构分润信息
	 * @param Id    分润
	 * @return
	 */
	public AgencyFeeLub get(String Id);
	
	public AgencyFeeLubTmp getTmp(String Id);
	/**
	 * 添加分分润信息
	 * @param agencyFeeLub    分润信息
	 * @return
	 */
	public String add(AgencyFeeLub agencyFeeLub);
	
	
	public String add(AgencyFeeLubTmp agencyFeeLub);
	/**
	 * 更新分润信息
	 * @param agencyFeeLub    分润信息列表
	 * @return
	 */
	public String update(List<AgencyFeeLub> agencyFeeLub);
	public String updateTmp(List<AgencyFeeLubTmp> agencyFeeLub);
	
	/**
	 * 删除分润信息
	 * @param agencyFeeLub   分润信息
	 * @return
	 */
	public String delete(AgencyFeeLub agencyFeeLub);
	/**
	 * 删除分润信息
	 * @param id    分润编号
	 * @return
	 */
	public String delete(String Id);
	
	public String delete2(AgencyFeeLubTmp agencyFeeLubTmp);
	public String delete2(String Id);
}
