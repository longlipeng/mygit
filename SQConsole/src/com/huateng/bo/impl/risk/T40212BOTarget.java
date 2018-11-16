package com.huateng.bo.impl.risk;

import java.util.List;


import com.huateng.bo.risk.T40212BO;
import com.huateng.common.Constants;
import com.huateng.po.risk.TblRiskChlTranLimit;
import com.huateng.dao.iface.risk.TblRiskChlTranLimitDAO;;
public class T40212BOTarget implements T40212BO{
	public TblRiskChlTranLimitDAO tblRiskChlTranLimitDAO;
	

	public TblRiskChlTranLimitDAO getTblRiskChlTranLimitDAO() {
		return tblRiskChlTranLimitDAO;
	}

	public void setTblRiskChlTranLimitDAO(
			TblRiskChlTranLimitDAO tblRiskChlTranLimitDAO) {
		this.tblRiskChlTranLimitDAO = tblRiskChlTranLimitDAO;
	}

	public String add(TblRiskChlTranLimit tblRiskChlTranLimit) {
		// TODO Auto-generated method stub
		tblRiskChlTranLimitDAO.save(tblRiskChlTranLimit);
		return Constants.SUCCESS_CODE;
	}

	public String delete(TblRiskChlTranLimit tblRiskChlTranLimit) {
		// TODO Auto-generated method stub
		tblRiskChlTranLimitDAO.delete(tblRiskChlTranLimit);
		return Constants.SUCCESS_CODE;
	}

	public String delete(String Id) {
		// TODO Auto-generated method stub
		this.tblRiskChlTranLimitDAO.delete(Id);;
		return Constants.SUCCESS_CODE;
	}

	public TblRiskChlTranLimit get(String Id) {
		// TODO Auto-generated method stub
		return this.tblRiskChlTranLimitDAO.get(Id);
	}

	public String update(List<TblRiskChlTranLimit> tblRiskChlTranLimitList) {
		// TODO Auto-generated method stub
		for(TblRiskChlTranLimit tblRiskChlTranLimit:tblRiskChlTranLimitList){
			tblRiskChlTranLimitDAO.update(tblRiskChlTranLimit);
		}
		return Constants.SUCCESS_CODE;
	}

	public String update(TblRiskChlTranLimit tblRiskChlTranLimit) throws Exception {
		// TODO Auto-generated method stub
		 this.tblRiskChlTranLimitDAO.update(tblRiskChlTranLimit);
		 return Constants.SUCCESS_CODE;
	}
	

}
