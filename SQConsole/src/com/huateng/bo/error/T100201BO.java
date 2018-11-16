/**
 * 
 */
package com.huateng.bo.error;

import java.util.List;

import com.huateng.po.error.TblChangeAccInf;
import com.huateng.po.error.TblChangeAccInfRefuse;
import com.huateng.po.error.TblChangeAccInfTmp;
import com.huateng.po.error.TblChangeAccInfTmpId;

/**
 * title:商户调账
 * @author jinlong
 *
 */
public interface T100201BO {
	/**
	 * 批量插入信息
	 * @param TblChangeAccInfTmpDao
	 */
	void add(List<TblChangeAccInfTmp> tblChangeAccInfTmp);
	/**
	 * 根据ID查询退货信息
	 * @param tblChangeAccInfTmpId
	 * @return
	 */
	TblChangeAccInfTmp get(TblChangeAccInfTmpId tblChangeAccInfTmpId);
	/**
	 * 保存或更新信息
	 * @param manualReturn
	 */
	void saveOrUpdate(TblChangeAccInfTmp tblChangeAccInfTmp);
	
	/**
	 * 保存或更新信息
	 * @param manualReturn
	 */
	void saveupdate(List<TblChangeAccInfTmp> tblChangeAccInfTmp);
	void delete(String id);
	void addone(TblChangeAccInfTmp tblChangeAccInfTmp);
	//批量审核通过
	void addAll(List<TblChangeAccInfTmp> list);
	void deletelist(TblChangeAccInfTmpId tblChangeAccInfTmpId);
	//批量审核拒绝
	void deletelist(List<TblChangeAccInfTmpId> list);
	void saveOrUpdate(TblChangeAccInf tblChangeAccInf);
	//批量审核通过
	void saveOrUpdate(List<TblChangeAccInf> list);
	/**
	 * 保存拒绝原因
	 * @param tblChangeAccInfRefuse
	 */
	void saveRefuse(TblChangeAccInfRefuse tblChangeAccInfRefuse);
	//批量审核拒绝
	void saveRefuse(List<TblChangeAccInfRefuse> list);

}
