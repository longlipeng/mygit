package com.huateng.bo.impl.rout;

import java.util.List;
import com.huateng.bo.rout.T11011BO;
import com.huateng.common.Constants;
import com.huateng.dao.iface.rout.TblRoutingDAO;
import com.huateng.po.rout.TblRouteInfoTemp;

public class T11011BOTarget implements T11011BO {
   
	private TblRoutingDAO tblRoutingDAO;
       
	public TblRoutingDAO getTblRoutingDAO() {
		return tblRoutingDAO;
	}

	public void setTblRoutingDAO(TblRoutingDAO tblRoutingDAO) {
		this.tblRoutingDAO = tblRoutingDAO;
	}

	public String add(TblRouteInfoTemp tblRouteInfoTemp) {
		tblRoutingDAO.saveOrUpdate(tblRouteInfoTemp);//save composite
	    return Constants.SUCCESS_CODE;
	}

	public String delete(String reserved) {
	    tblRoutingDAO.delete(reserved);
		return Constants.SUCCESS_CODE;
	}

	public TblRouteInfoTemp get(String reserved) {
		return tblRoutingDAO.get(reserved);
	}

	public String update(TblRouteInfoTemp tblRouting) {
		tblRoutingDAO.update(tblRouting);
		return Constants.SUCCESS_CODE;
	}

	public String update(List<TblRouteInfoTemp> tblRouteInfoTemp) {
		for(TblRouteInfoTemp rout:tblRouteInfoTemp){
		  tblRoutingDAO.update(rout);
		}
		return Constants.SUCCESS_CODE;
	}
}
