package com.huateng.bo.impl.mchnt;

import java.util.List;

import com.huateng.bo.mchnt.T20703BO;
import com.huateng.dao.iface.mchnt.TblHisDiscAlgo1DAO;
import com.huateng.po.mchnt.TblHisDiscAlgo1;


public class T20703BOTarget implements T20703BO{
	private TblHisDiscAlgo1DAO tblHisDiscAlgo1DAO;


	public TblHisDiscAlgo1DAO getTblHisDiscAlgo1DAO() {
		return tblHisDiscAlgo1DAO;
	}

	public void setTblHisDiscAlgo1DAO(TblHisDiscAlgo1DAO tblHisDiscAlgo1DAO) {
		this.tblHisDiscAlgo1DAO = tblHisDiscAlgo1DAO;
	}

	/**
	 * @return the cstMchtFeeInfDAO
	 */
	
	public String add(TblHisDiscAlgo1 tblHisDiscAlgo1) {
		tblHisDiscAlgo1DAO.save(tblHisDiscAlgo1);
		return "00";
	}

	public TblHisDiscAlgo1 get(String id) {
		return tblHisDiscAlgo1DAO.get(id);
	}

	public String update(TblHisDiscAlgo1 tblHisDiscAlgo1) {
		tblHisDiscAlgo1DAO.update(tblHisDiscAlgo1);
		return "00";
	}
	
	

	public void delete(String id) {
		tblHisDiscAlgo1DAO .delete(id);
		
	}
	public String update(List<TblHisDiscAlgo1> tblHisDiscAlgo1List) {
		for(TblHisDiscAlgo1 tblHisDiscAlgo1 : tblHisDiscAlgo1List) {
			update(tblHisDiscAlgo1);
		}
		return "00";
	}
}
