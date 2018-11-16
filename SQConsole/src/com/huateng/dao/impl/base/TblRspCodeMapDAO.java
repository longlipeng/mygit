package com.huateng.dao.impl.base;

import java.util.List;

import com.huateng.dao._RootDAO;
import com.huateng.po.base.TblRspCodeMap;
import com.huateng.po.base.TblRspCodeMapPK;

public class TblRspCodeMapDAO extends _RootDAO implements
		com.huateng.dao.iface.base.TblRspCodeMapDAO {
	public TblRspCodeMapDAO (){}
	@Override
	public Class<com.huateng.po.base.TblRspCodeMap> getReferenceClass() {	
		return com.huateng.po.base.TblRspCodeMap.class;
	}
	/**
	 * Cast the object as a com.huateng.po.base.TblRspCodeMap
	 */
	public com.huateng.po.base.TblRspCodeMap cast (Object object) {
		return (com.huateng.po.base.TblRspCodeMap) object;
	}
	
	public void delete(TblRspCodeMap tblRspCodeMap) {
		super.delete((Object)tblRspCodeMap);

	}
	
	public List<TblRspCodeMap> findAll() {
		
		return super.loadAll(getReferenceClass());
	}

	public TblRspCodeMap get(String id) {
		
		return (TblRspCodeMap)super.get(getReferenceClass(), id);
	}

	public TblRspCodeMap load(String id) {
		
		return (TblRspCodeMap) super.load(getReferenceClass(), id);
	}

	public List<TblRspCodeMap> loadAll() {
		
		return super.loadAll(getReferenceClass());
	}

	public TblRspCodeMapPK save(TblRspCodeMap tblRspCodeMap) {
		
		return (TblRspCodeMapPK)super.save(tblRspCodeMap);
	}

	public void saveOrUpdate(TblRspCodeMap tblRspCodeMap) {
	       super.saveOrUpdate(tblRspCodeMap);
	}

	public void update(List<TblRspCodeMap> tblRspCodeMapList) {
		  // super.update(tblRspCodeMapList);

	}

	public void update(TblRspCodeMap tblRspCodeMap) {
		super.update(tblRspCodeMap);

	}
	public void delete(TblRspCodeMapPK id) {
		super.delete((Object)get(id));
	}
	public TblRspCodeMap get(TblRspCodeMapPK key) {
		
		return (TblRspCodeMap) super.get(getReferenceClass(), key);
	}
	public TblRspCodeMap load(TblRspCodeMapPK key) {
		
		return (TblRspCodeMap) super.load(getReferenceClass(), key);
	}


}
