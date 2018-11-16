/**
 * 
 */
package com.huateng.dao.impl.error;

import java.util.List;

import com.huateng.dao._RootDAO;
import com.huateng.po.error.TblChangeAccInf;
import com.huateng.po.error.TblChangeAccInfRefuse;
import com.huateng.po.error.TblChangeAccInfTmp;
import com.huateng.po.error.TblAlgoDtl;
import com.huateng.po.error.TblChangeAccInfTmpId;

/**
 * @author zoujunqing
 *
 */
public class TblChangeAccInfTmpDao extends _RootDAO<TblAlgoDtl> implements 
		com.huateng.dao.iface.error.TblChangeAccInfTmpDao{

	@Override
	protected Class getReferenceClass() {
		 
		return TblChangeAccInfTmp.class;
	}

	public void saveOrUpdate(TblChangeAccInfTmp manu) {
		super.saveOrUpdate(manu);
	}

	public TblChangeAccInfTmp get(String id) {
		return (TblChangeAccInfTmp)super.get(getReferenceClass(), id);
	}

	public void saveupdate(TblChangeAccInfTmp tblChangeAccInfTmp) {
		// TODO Auto-generated method stub
		super.update(tblChangeAccInfTmp);
	}

	public void delete(String id) {
		super.delete(load(TblChangeAccInfTmp.class, id));
	}

	public void deletelist(TblChangeAccInfTmpId tblChangeAccInfTmpId) {
		// TODO Auto-generated method stub
		super.delete(load(getReferenceClass(), tblChangeAccInfTmpId));
	}

	public TblChangeAccInfTmp get(TblChangeAccInfTmpId tblChangeAccInfTmpId) {
		// TODO Auto-generated method stub
		return (TblChangeAccInfTmp)super.get(getReferenceClass(), tblChangeAccInfTmpId);
	}

	public void saveOrUpdate(TblChangeAccInf tblChangeAccInf) {
		// TODO Auto-generated method stub
		super.saveOrUpdate(tblChangeAccInf);
	}

	public void saveOrUpdate(TblChangeAccInfRefuse tblChangeAccInfRefuse) {
		// TODO Auto-generated method stub
		super.saveOrUpdate(tblChangeAccInfRefuse);
	}
	
	
}