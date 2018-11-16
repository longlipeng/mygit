package com.huateng.dao.impl.base;

import java.util.List;

import com.huateng.dao._RootDAO;
import com.huateng.po.base.TblRespCodeInfo;

public  class TblRespCodeInfoTrueDAO extends _RootDAO<com.huateng.po.base.TblRespCodeInfo>implements com.huateng.dao.iface.base.TblRespCodeInfoDAO {
    public TblRespCodeInfoTrueDAO (){}
	@Override
	public Class<com.huateng.po.base.TblRespCodeInfo> getReferenceClass() {	
		return com.huateng.po.base.TblRespCodeInfo.class;
	}
	/**
	 * Cast the object as a com.huateng.po.base.TblRespCodeInfo
	 */
	public com.huateng.po.base.TblRespCodeInfo cast (Object object) {
		return (com.huateng.po.base.TblRespCodeInfo) object;
	}
	
	public com.huateng.po.base.TblRespCodeInfo load(java.lang.String key){
		return (com.huateng.po.base.TblRespCodeInfo) load(getReferenceClass(), key);
	}
	
	public void delete(String id) {
		
	    super.delete(get(id));
	}

	public void delete(TblRespCodeInfo tblRespCodeInfo) {
		super.delete(tblRespCodeInfo);
		
	}

	@SuppressWarnings("unchecked")
	public List<TblRespCodeInfo> findAll() {
		
		return super.loadAll(getReferenceClass());
	}

	public com.huateng.po.base.TblRespCodeInfo get(java.lang.String key) {
		return (com.huateng.po.base.TblRespCodeInfo)super.get(getReferenceClass(), key);
	}


	public String save(TblRespCodeInfo tblRespCodeInfo) {
		
		return (java.lang.String)super.save(tblRespCodeInfo);
	}

	public void saveOrUpdate(TblRespCodeInfo tblRespCodeInfo) {
	   super.saveOrUpdate(tblRespCodeInfo);
	}

	public void update(List<TblRespCodeInfo> tblRespCodeInfoList) {
		
		super.update(tblRespCodeInfoList);
	}
	@SuppressWarnings("unchecked")
	public List<TblRespCodeInfo> loadAll() {
		
		return super.loadAll(getReferenceClass());
	}
	public void update(TblRespCodeInfo tblRespCodeInfo) {
		 super.update(tblRespCodeInfo);
		
	}

}
