package com.huateng.dao.impl.base;

import java.util.List;

import com.huateng.dao._RootDAO;
import com.huateng.po.mchnt.TblInfMchntTp;
import com.huateng.po.mchnt.TblInfMchntTpPK;


public class TblInfMchntTpDAO extends _RootDAO<TblInfMchntTp > implements com.huateng.dao.iface.base.TblInfMchntTpDAO {

	public TblInfMchntTpDAO () {}
	
	/* (non-Javadoc)
	 * @see com.huateng.dao.iface.TblMbfBankInfoDAO#findAll()
	 */
	public List<TblInfMchntTp> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public Class<TblInfMchntTp> getReferenceClass () {
		return TblInfMchntTp.class;
	}


	/**
	 * Cast the object as a com.huateng.po.brh.TblMbfBankInfo
	 */
	public TblInfMchntTp cast (Object object) {
		return (TblInfMchntTp) object;
	}


	public TblInfMchntTp load(java.lang.String key)
	{
		return (TblInfMchntTp) load(getReferenceClass(), key);
	}

	public TblInfMchntTp get(TblInfMchntTpPK tblInfMchntTpPK)
	{
		return (TblInfMchntTp) get(getReferenceClass(), tblInfMchntTpPK);
	}


	public java.lang.String save(TblInfMchntTp tblInfMchntTp)
	{
		return (java.lang.String) super.save(tblInfMchntTp);
	}


	public void saveOrUpdate(TblInfMchntTp tblInfMchntTp)
	{
		super.saveOrUpdate(tblInfMchntTp);
	}

	/* (non-Javadoc)
	 * @see com.huateng.dao.iface.base.TblInfMchntTpDAO#update(com.huateng.po.mchnt.TblInfMchntTp)
	 */
	public void update(TblInfMchntTp tblInfMchntTp) {
		// TODO Auto-generated method stub
		
	}


}
