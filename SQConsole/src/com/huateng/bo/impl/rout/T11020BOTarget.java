package com.huateng.bo.impl.rout;

import java.util.List;

import com.huateng.bo.rout.T11020BO;
import com.huateng.common.Constants;
import com.huateng.dao.iface.rout.TblTermChannelInf2DAO;
import com.huateng.po.rout.TblTermChannelInf2;

public class T11020BOTarget implements T11020BO {

	private TblTermChannelInf2DAO tblTermChannelInf2DAO;
	
	public TblTermChannelInf2DAO getTblTermChannelInf2DAO() {
		return tblTermChannelInf2DAO;
	}

	public void setTblTermChannelInf2DAO(TblTermChannelInf2DAO tblTermChannelInf2DAO) {
		this.tblTermChannelInf2DAO = tblTermChannelInf2DAO;
	}

	public String add(TblTermChannelInf2 tblTermChannelInf2) {
		tblTermChannelInf2DAO.saveOrUpdate(tblTermChannelInf2);
	    return Constants.SUCCESS_CODE;
	}

	public String delete(String id) {
		tblTermChannelInf2DAO.delete(id);
		return Constants.SUCCESS_CODE;
	}

	public TblTermChannelInf2 get(String id) {
		return tblTermChannelInf2DAO.get(id);
	}

	public String update(TblTermChannelInf2 tblTermChannelInf2) {
		tblTermChannelInf2DAO.saveOrUpdate(tblTermChannelInf2);
		return Constants.SUCCESS_CODE;
	}

	public String update(List<TblTermChannelInf2> tblTermChannelInf2s) {
		for(TblTermChannelInf2 termChannel : tblTermChannelInf2s){
			tblTermChannelInf2DAO.update(termChannel);
		}
		return Constants.SUCCESS_CODE;
	}

}
