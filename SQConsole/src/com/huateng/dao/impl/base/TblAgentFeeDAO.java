package com.huateng.dao.impl.base;

import com.huateng.dao._RootDAO;
import com.huateng.po.base.TblAgentFee;
import com.huateng.po.base.TblAgentFeeRefuseInfo;
import com.huateng.po.base.TblAgentFeeTmp;
import com.huateng.po.risk.TblWhiteList;

public class TblAgentFeeDAO extends _RootDAO<TblAgentFee> implements
		com.huateng.dao.iface.base.TblAgentFeeDAO {

	public TblAgentFee load(String key) {
		return (TblAgentFee) load(getReferenceClassNor(), key);
	}
	
	public TblAgentFeeTmp loadTmp(String key) {
		return (TblAgentFeeTmp) load(getReferenceClass(), key);
	}

	public TblAgentFeeTmp get(String uuid) {
		return (TblAgentFeeTmp) get(getReferenceClass(), uuid);
	}
	
	public TblAgentFee getTrue(String uuid) {
		return (TblAgentFee) get(getReferenceClassNor(), uuid);
	}

	@Override
	protected Class getReferenceClass() {
		return TblAgentFeeTmp.class;
	}
	
	protected Class getReferenceClassNor() {
		return TblAgentFee.class;
	}

	public void delete(String uuId) {
		super.delete((Object) load(uuId));
		
	}
	
	public void deleteTmp(String idkey) {
		super.delete((Object) loadTmp(idkey));
		
	}


	public String save(TblAgentFee tblAgentFee) {
		return (String) super.save(tblAgentFee);
	}
	
	public String save(TblAgentFeeRefuseInfo tblAgentFeeRefuseInfo) {
		return (String) super.save(tblAgentFeeRefuseInfo);
	}
	public String save(TblAgentFeeTmp tblAgentFeeTmp) {
		return (String) super.save(tblAgentFeeTmp);
	}

	public void update(TblAgentFee tblAgentFee) {
		super.update(tblAgentFee);
	}
	
	public void update(TblAgentFeeTmp tblAgentFeeTmp) {
		super.update(tblAgentFeeTmp);
	}

	public void saveOrUpdate(TblAgentFee tblAgentFee) {
		super.saveOrUpdate(tblAgentFee);
		
	}

	public void saveOrUpdate(TblAgentFeeTmp tblAgentFeeTmp) {
		super.saveOrUpdate(tblAgentFeeTmp);
		
	}

	
}
