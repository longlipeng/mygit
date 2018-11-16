package com.huateng.bo.error;

import com.huateng.po.error.TblElecCashInfTmp;

public interface T10081BO {

	/**
	 * 添加
	 * @param tblElecCashInfTmp
	 * @return
	 * @throws Exception
	 * 
	 */
	public String add( TblElecCashInfTmp tblElecCashInfTmp) throws Exception;
	/**
	 * 更新
	 * @param tblCtlMchtInf
	 * @return
	 * @throws Exception
	 * 
	 */
	public String update(TblElecCashInfTmp tblElecCashInfTmp) throws Exception;
	/**
	 * 查询
	 * @param key
	 * @return
	 * 
	 */
	public TblElecCashInfTmp get(String key);
	
	
	/**
	 * 删除
	 * @param tblCtlMchtInf
	 * @return
	 * @throws Exception
	 * 
	 */
	public void delete(String key) throws Exception;
	
}
