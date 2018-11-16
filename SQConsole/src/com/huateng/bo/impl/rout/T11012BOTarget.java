package com.huateng.bo.impl.rout;

import java.util.List;
import com.huateng.bo.rout.T11012BO;
import com.huateng.common.Constants;
import com.huateng.dao.iface.rout.TblRouteInfDAO;
import com.huateng.po.rout.TblRouteInfo;
import com.huateng.po.rout.TblRouteInfoTemp;

public class T11012BOTarget implements T11012BO {

	private TblRouteInfDAO tblRouteInfDAO;
	
	public TblRouteInfDAO getTblRouteInfDAO() {
		return tblRouteInfDAO;
	}

	public void setTblRouteInfDAO(TblRouteInfDAO tblRouteInfDAO) {
		this.tblRouteInfDAO = tblRouteInfDAO;
	}

	public String add(TblRouteInfo tblRouteInfo) {
		tblRouteInfDAO.saveOrUpdate(tblRouteInfo);//save composite
	    return Constants.SUCCESS_CODE;
	}

	public String delete(String reserved) {
		tblRouteInfDAO.deleteTblRouteInfo(reserved);
		return Constants.SUCCESS_CODE;
	}

	public TblRouteInfo get(String reserved) {
		return tblRouteInfDAO.getTblRouteInfo(reserved);
	}

	public String update(TblRouteInfo tblRouteInfo) {
		tblRouteInfDAO.update(tblRouteInfo);
		return Constants.SUCCESS_CODE;
	}

	public String update(List<TblRouteInfo> TblRouteInfos) {
		for(TblRouteInfo rout:TblRouteInfos){
			tblRouteInfDAO.update(rout);
			}
		return Constants.SUCCESS_CODE;
	}

}
