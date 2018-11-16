package com.huateng.dao.impl.settle;

import com.huateng.dao._RootDAO;
import com.huateng.po.settle.TblSettleRedempTionInf;
import com.huateng.po.settle.TblSettleRedempTionInfTmp;

public class TblSettleRedempInfDAO extends _RootDAO<com.huateng.po.settle.TblSettleRedempTionInfTmp> implements com.huateng.dao.iface.settle.TblSettleRedempInfDAO {

	public TblSettleRedempInfDAO(){}
	
	
	public Class<com.huateng.po.settle.TblSettleRedempTionInfTmp> getReferenceClass() {
		// TODO Auto-generated method stub
		return com.huateng.po.settle.TblSettleRedempTionInfTmp.class;
	}

	/**
	 * 新增客户赎回信息
	 */
	public String redempAdd(
			TblSettleRedempTionInfTmp tblSettleRedempTionInfTmp) {
		// TODO Auto-generated method stub
		return (String) super.save(tblSettleRedempTionInfTmp);
	}

	/**
	 * 根据id查询
	 */
	public TblSettleRedempTionInfTmp getRedemp(String redempId) {
		// TODO Auto-generated method stub
		return (TblSettleRedempTionInfTmp) super.get(getReferenceClass(), redempId);
	}

	/**
	 * 删除赎回信息
	 */
	public void redempDel(String redempTionId) {
		// TODO Auto-generated method stub
		super.delete(getRedemp(redempTionId));
	}

	/**
	 * 赎回
	 */
	public void redempUp(TblSettleRedempTionInfTmp tblSettleRedempTionInfTmp) {
		// TODO Auto-generated method stub
		super.update(tblSettleRedempTionInfTmp);
	}

	/**
	 * 正式表
	 * 查询
	 */
	public TblSettleRedempTionInf getRedempInf(String redempId) {
		// TODO Auto-generated method stub
		return (TblSettleRedempTionInf) super.get(TblSettleRedempTionInf.class, redempId);
	}
	
	/**
	 * 正式表
	 * 更新新增
	 */
	public void saveOrUpdate(TblSettleRedempTionInf tblSettleRedempTionInf) {
		// TODO Auto-generated method stub
		super.saveOrUpdate(tblSettleRedempTionInf);
	}

}
