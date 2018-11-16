package com.huateng.bo.impl.rout;

import java.util.List;
import com.huateng.bo.rout.T11014BO;
import com.huateng.common.Constants;
import com.huateng.dao.iface.rout.TblRouteChgInfDAO;
import com.huateng.po.rout.TblRouteChgInf;

public class T11014BOTarget implements T11014BO {

	private TblRouteChgInfDAO tblRouteChgInfDAO;
	
	public TblRouteChgInfDAO getTblRouteChgInfDAO() {
		return tblRouteChgInfDAO;
	}

	public void setTblRouteChgInfDAO(TblRouteChgInfDAO tblRouteChgInfDAO) {
		this.tblRouteChgInfDAO = tblRouteChgInfDAO;
	}

	public String add(TblRouteChgInf tblRouteChgInf) {
		tblRouteChgInfDAO.saveOrUpdate(tblRouteChgInf);
	    return Constants.SUCCESS_CODE;
	}

	public String delete(String destInstId) {
		tblRouteChgInfDAO.deleteTblRouteChgInf(destInstId);
		return Constants.SUCCESS_CODE;
	}

	public TblRouteChgInf get(String destInstId) {
		return tblRouteChgInfDAO.getTblRouteChgInf(destInstId);
	}

	public String update(TblRouteChgInf tblRouteChgInf) {
		tblRouteChgInfDAO.saveOrUpdate(tblRouteChgInf);
		return Constants.SUCCESS_CODE;
	}

	public String update(List<TblRouteChgInf> tblRouteChgInfs) {
		for(TblRouteChgInf rout : tblRouteChgInfs){
			tblRouteChgInfDAO.update(rout);
		}
		return Constants.SUCCESS_CODE;
	}

}
