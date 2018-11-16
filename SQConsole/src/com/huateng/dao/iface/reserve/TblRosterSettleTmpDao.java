package com.huateng.dao.iface.reserve;

import com.huateng.po.reserve.TblSettleRosterInf;
import com.huateng.po.reserve.TblSettleRosterInfTmp;

public interface TblRosterSettleTmpDao {

	/**
	 * 新增
	 * @param tblSettleRosterInfTmp
	 * @return
	 */
	public void addRoster(TblSettleRosterInfTmp tblSettleRosterInfTmp);
	
	/**
	 * 删除
	 * @param rosterId
	 * @return
	 */
	public void delRoster(String rosterId);
	
	/**
	 * 查询
	 * @param rosterId
	 * @return
	 */
	public TblSettleRosterInfTmp getRoster(String rosterId);
	
	/**
	 * 修改
	 * @param tblSettleRosterInfTmp
	 * @return
	 */
	public void upRoster(TblSettleRosterInfTmp tblSettleRosterInfTmp);
	
	/**
	 * 查询
	 * 正
	 * @param rosterId
	 * @return
	 */
	public TblSettleRosterInf getRosterInf(String rosterId);
	
	/**
	 * 新增
	 * 正
	 * @param tblSettleRosterInfTmp
	 * @return
	 */
	public void addRosterInf(TblSettleRosterInf tblSettleRosterInf);
	
	/**
	 * 删除
	 * 正
	 * @param rosterId
	 * @return
	 */
	public void delRosterInf(String rosterId);
}
