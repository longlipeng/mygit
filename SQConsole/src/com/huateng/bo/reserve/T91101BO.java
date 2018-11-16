package com.huateng.bo.reserve;

import com.huateng.po.reserve.TblSettleRosterInf;
import com.huateng.po.reserve.TblSettleRosterInfTmp;

public interface T91101BO {

	/**
	 * 新增
	 * @param tblSettleRosterInfTmp
	 * @return
	 */
	public String addRoster(TblSettleRosterInfTmp tblSettleRosterInfTmp);
	
	/**
	 * 删除
	 * @param rosterId
	 * @return
	 */
	public String delRoster(String rosterId);
	
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
	public String upRoster(TblSettleRosterInfTmp tblSettleRosterInfTmp);
	
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
	public String addRosterInf(TblSettleRosterInf tblSettleRosterInf);
	
	/**
	 * 删除
	 * 正
	 * @param rosterId
	 * @return
	 */
	public String delRosterInf(String rosterId);
}
