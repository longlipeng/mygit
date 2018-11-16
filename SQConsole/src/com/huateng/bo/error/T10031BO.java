package com.huateng.bo.error;

import com.huateng.po.error.BthCupErrTxn;
import com.huateng.po.error.BthCupErrTxnPK;
import com.huateng.po.error.TblAlgoDtl;
import com.huateng.po.error.TblAlgoDtlPK;

/**
 * title:差错信息维护
 * 
 * @author crystal chen
 *
 */
public interface T10031BO {

	/**
	 * 查看差错详细信息(差错流水)
	 * @param key
	 * @return
	 * @throws Exception
	 * 2012-04-26
	 */
	public BthCupErrTxn get(BthCupErrTxnPK key) throws Exception;
	
	/**
	 * 添加差错详细信息(差错流水)
	 * @param tblCtlMchtInf
	 * @return
	 * @throws Exception
	 * 
	 */
	public String add( BthCupErrTxn bthCupErrTxn) throws Exception;
	
	/**
	 * 查看差错详细信息（挂账、解账）
	 * @param key
	 * @return
	 * @throws Exception
	 * 2012-05-02
	 */
	public TblAlgoDtl get(TblAlgoDtlPK key) throws Exception;
	/**
	 * 保存或更新差错流水信息
	 * @param errTxn
	 */
	public void saveOrUpdate(BthCupErrTxn errTxn);
	
}
