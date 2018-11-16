package com.huateng.bo.error;

import com.huateng.po.error.TblElecCashInf;

public interface T10082BO {

	/**
	 * 添加
	 * @param tblElecCashInf
	 * @return
	 * @throws Exception
	 * 
	 */
	public String add( TblElecCashInf tblElecCashInf) throws Exception;
	/**
	 * 更新
	 * @param tblCtlMchtInf
	 * @return
	 * @throws Exception
	 * 
	 */
	public String update(TblElecCashInf tblElecCashInf) throws Exception;
	/**
	 * 查询
	 * @param key
	 * @return
	 * 
	 */
	public TblElecCashInf get(String key);
	
	
	/**
	 * 删除
	 * @param tblCtlMchtInf
	 * @return
	 * @throws Exception
	 * 
	 */
	public void delete(String key) throws Exception;
	
}
