package com.huateng.bo.impl.reserve;

import com.huateng.bo.reserve.T91101BO;
import com.huateng.dao.iface.reserve.TblRosterSettleTmpDao;
import com.huateng.po.reserve.TblSettleRosterInf;
import com.huateng.po.reserve.TblSettleRosterInfTmp;

public class T91101BOTarget implements T91101BO {

	private TblRosterSettleTmpDao tblRosterSettleTmpDao;

	public TblRosterSettleTmpDao getTblRosterSettleTmpDao() {
		return tblRosterSettleTmpDao;
	}

	public void setTblRosterSettleTmpDao(TblRosterSettleTmpDao tblRosterSettleTmpDao) {
		this.tblRosterSettleTmpDao = tblRosterSettleTmpDao;
	}

	public String addRoster(TblSettleRosterInfTmp tblSettleRosterInfTmp) {
		// TODO Auto-generated method stub
		tblRosterSettleTmpDao.addRoster(tblSettleRosterInfTmp);
		return "00";
	}

	public String delRoster(String rosterId) {
		// TODO Auto-generated method stub
		tblRosterSettleTmpDao.delRoster(rosterId);
		return "00";
	}

	public TblSettleRosterInfTmp getRoster(String rosterId) {
		// TODO Auto-generated method stub
		return tblRosterSettleTmpDao.getRoster(rosterId);
	}

	public String upRoster(TblSettleRosterInfTmp tblSettleRosterInfTmp) {
		// TODO Auto-generated method stub
		tblRosterSettleTmpDao.upRoster(tblSettleRosterInfTmp);
		return "00";
	}

	public TblSettleRosterInf getRosterInf(String rosterId) {
		// TODO Auto-generated method stub
		return tblRosterSettleTmpDao.getRosterInf(rosterId);
	}

	public String addRosterInf(TblSettleRosterInf tblSettleRosterInf) {
		// TODO Auto-generated method stub
		tblRosterSettleTmpDao.addRosterInf(tblSettleRosterInf);
		return "00";
	}

	public String delRosterInf(String rosterId) {
		// TODO Auto-generated method stub
		tblRosterSettleTmpDao.delRosterInf(rosterId);
		return "00";
	}

	
	
}
