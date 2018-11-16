package com.huateng.bo.impl.settle;

import com.huateng.bo.settle.T80601BO;
import com.huateng.dao.iface.settle.TblSettleRedempInfDAO;
import com.huateng.po.settle.TblSettleRedempTionInf;
import com.huateng.po.settle.TblSettleRedempTionInfTmp;

/**
 * 赎回入账
 * @author Administrator
 *
 */
public class T80601BOTarget implements T80601BO {

	private TblSettleRedempInfDAO tblSettleRedempInfDAO;


	public String redempAdd(
			TblSettleRedempTionInfTmp tblSettleRedempTionInfTmp) {
		// TODO Auto-generated method stub
		tblSettleRedempInfDAO.redempAdd(tblSettleRedempTionInfTmp);
		return "00";
	}
	
	public TblSettleRedempTionInfTmp getRedemp(String redempId) {
		// TODO Auto-generated method stub
		return tblSettleRedempInfDAO.getRedemp(redempId);
	}
	
	public String redempDel(String redempTionId) {
		// TODO Auto-generated method stub
		tblSettleRedempInfDAO.redempDel(redempTionId);
		return "00";
	}
	
	public String redempUp(TblSettleRedempTionInfTmp tblSettleRedempTionInfTmp) {
		// TODO Auto-generated method stub
		tblSettleRedempInfDAO.redempUp(tblSettleRedempTionInfTmp);
		return "00";
	}
	
	public TblSettleRedempTionInf getRedempInf(String redempId) {
		// TODO Auto-generated method stub
		return tblSettleRedempInfDAO.getRedempInf(redempId);
	}
	
	public String saveOrUpdate(TblSettleRedempTionInf tblSettleRedempTionInf) {
		// TODO Auto-generated method stub
		tblSettleRedempInfDAO.saveOrUpdate(tblSettleRedempTionInf);
		return "00";
	}
	
	
	
	
	
	
	public TblSettleRedempInfDAO getTblSettleRedempInfDAO() {
		return tblSettleRedempInfDAO;
	}

	public void setTblSettleRedempInfDAO(TblSettleRedempInfDAO tblSettleRedempInfDAO) {
		this.tblSettleRedempInfDAO = tblSettleRedempInfDAO;
	}


}
