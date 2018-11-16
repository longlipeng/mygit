package com.huateng.bo.impl.risk;

import java.util.List;

import com.huateng.bo.risk.T40210BO;
import com.huateng.common.Constants;
import com.huateng.po.risk.TblRiskChlTranCtl;
import com.huateng.dao.iface.risk.TblRiskChlTranCltDAO;
public class T40210BOTarget implements T40210BO {
	public TblRiskChlTranCltDAO tblRiskChlTranCltDAO;
	

	public TblRiskChlTranCltDAO getTblRiskChlTranCltDAO() {
		return tblRiskChlTranCltDAO;
	}

	public void setTblRiskChlTranCltDAO(TblRiskChlTranCltDAO tblRiskChlTranCltDAO) {
		this.tblRiskChlTranCltDAO = tblRiskChlTranCltDAO;
	}

	public String add(TblRiskChlTranCtl tblRiskChlTranCtl) {
		// TODO Auto-generated method stub
		tblRiskChlTranCltDAO.save(tblRiskChlTranCtl);
		return Constants.SUCCESS_CODE;
	}

	public String delete(TblRiskChlTranCtl tblRiskChlTranCtl) {
		// TODO Auto-generated method stub
		tblRiskChlTranCltDAO.delete(tblRiskChlTranCtl);
		return Constants.SUCCESS_CODE;
	}

	public String delete(String Id) {
		// TODO Auto-generated method stub
		this.tblRiskChlTranCltDAO.delete(Id);;
		return Constants.SUCCESS_CODE;
	}

	public TblRiskChlTranCtl get(String Id) {
		// TODO Auto-generated method stub
		return this.tblRiskChlTranCltDAO.get(Id);
	}

	public String update(List<TblRiskChlTranCtl> tblRiskChlTranCtlList) {
		// TODO Auto-generated method stub
		for(TblRiskChlTranCtl tblRiskChlTranCtl:tblRiskChlTranCtlList){
			tblRiskChlTranCltDAO.update(tblRiskChlTranCtl);
		}
		return Constants.SUCCESS_CODE;
	}

	public String update(TblRiskChlTranCtl tblRiskChlTranCtl) throws Exception {
		// TODO Auto-generated method stub
		 this.tblRiskChlTranCltDAO.update(tblRiskChlTranCtl);
		 return Constants.SUCCESS_CODE;
	}
	

}
