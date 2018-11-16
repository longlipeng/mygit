package com.huateng.bo.settle;

import com.huateng.po.settle.TblSettleRedempTionInf;
import com.huateng.po.settle.TblSettleRedempTionInfTmp;

public interface T80601BO {

	/**
	 * 新增客户赎回信息
	 * @param tblSettleRedempTionInfTmp
	 * @return
	 */
	public String redempAdd(TblSettleRedempTionInfTmp tblSettleRedempTionInfTmp);
	
	/**
	 * 根据id查询
	 * @param redempId
	 * @return
	 */
	public TblSettleRedempTionInfTmp getRedemp(String redempId);
	
	/**
	 * 删除赎回信息
	 * @param redemp
	 * @return
	 */
	public String redempDel(String redempTionId);
	
	/**
	 * 赎回
	 * @param tblSettleRedempTionInfTmp
	 * @return
	 */
	public String redempUp(TblSettleRedempTionInfTmp tblSettleRedempTionInfTmp);
	
	/**
	 * 正式表
	 * 查询
	 * @param tblSettleRedempTionInf
	 * @return
	 */
	public TblSettleRedempTionInf getRedempInf(String redempId);
	
	/**
	 * 正式表
	 * 更新新增
	 * @param tblSettleRedempTionInf
	 * @return
	 */
	public String saveOrUpdate(TblSettleRedempTionInf tblSettleRedempTionInf);
	
}
