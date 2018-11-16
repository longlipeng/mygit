/**
 * 
 */
package com.huateng.bo.error;

import java.util.List;

import com.huateng.po.error.ManualReturn;

/**
 * title:手工退货
 * @author jinlong
 *
 */
public interface T100101BO {
	/**
	 * 批量插入退货信息
	 * @param manualReturnList
	 */
	void add(List<ManualReturn> manualReturnList);
	/**
	 * 根据ID查询退货信息
	 * @param id
	 * @return
	 */
	ManualReturn get(String id);
	/**
	 * 保存或更新退货信息
	 * @param manualReturn
	 */
	void saveOrUpdate(ManualReturn manualReturn);
	
	/**
	 * 保存或更新退货信息
	 * @param manualReturn
	 */
	void saveupdate(List<ManualReturn> manualReturnList);
	void delete(String id);

}
