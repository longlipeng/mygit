package com.huateng.dao.impl.reserve;

import com.huateng.dao._RootDAO;
import com.huateng.po.reserve.TblSettleRosterInf;
import com.huateng.po.reserve.TblSettleRosterInfTmp;

public class TblRosterSettleTmpDao extends _RootDAO<TblSettleRosterInfTmp> implements com.huateng.dao.iface.reserve.TblRosterSettleTmpDao {

	public TblRosterSettleTmpDao(){}
	
	public Class<TblSettleRosterInfTmp> getReferenceClass() {
		// TODO Auto-generated method stub
		return TblSettleRosterInfTmp.class;
	}

	public void addRoster(TblSettleRosterInfTmp tblSettleRosterInfTmp) {
		// TODO Auto-generated method stub
		super.save(tblSettleRosterInfTmp);
	}

	public TblSettleRosterInfTmp getDel(String rosterId){
		return (TblSettleRosterInfTmp) super.get(getReferenceClass(), rosterId);
	}
	
	public void delRoster(String rosterId) {
		// TODO Auto-generated method stub
		super.delete(getDel(rosterId));
	}

	public TblSettleRosterInfTmp getRoster(String rosterId) {
		// TODO Auto-generated method stub
		return (TblSettleRosterInfTmp) super.get(getReferenceClass(), rosterId);
	}

	public void upRoster(TblSettleRosterInfTmp tblSettleRosterInfTmp) {
		// TODO Auto-generated method stub
		super.update(tblSettleRosterInfTmp);
	}

	public TblSettleRosterInf getRosterInf(String rosterId) {
		// TODO Auto-generated method stub
		return (TblSettleRosterInf) super.get(TblSettleRosterInf.class, rosterId);
	}

	public void addRosterInf(TblSettleRosterInf tblSettleRosterInf) {
		// TODO Auto-generated method stub
		super.save(tblSettleRosterInf);
	}
	
	public void delRosterInf(String rosterId) {
		// TODO Auto-generated method stub
		super.delete(getRosterInf(rosterId));
	}

	
	
}
