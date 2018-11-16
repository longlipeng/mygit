package com.huateng.bo.error;

import com.huateng.po.error.TblAlgoDtlCheck;

public interface T10033BO {

	/**
	 * 添加追扣已清算交易的审核记录
	 * @param tblAlgoDtlCheck
	 * @return
	 * @throws Exception
	 * 
	 */
	public String add( TblAlgoDtlCheck tblAlgoDtlCheck) throws Exception;
	/**
	 * 更新追扣已清算交易的审核记录
	 * @param tblCtlMchtInf
	 * @return
	 * @throws Exception
	 * 
	 */
	public String update(TblAlgoDtlCheck tblAlgoDtlCheck) throws Exception;
	/**
	 * 查询追扣已清算交易的审核记录
	 * @param key
	 * @return
	 * 
	 */
	public TblAlgoDtlCheck get(String key);
	
	public String get(String dateSettlmt,String txnKey);
	
	/**
	 * 删除追扣已清算交易的审核记录
	 * @param tblCtlMchtInf
	 * @return
	 * @throws Exception
	 * 
	 */
	public void delete(String key) throws Exception;
	
}
