package com.huateng.dao.iface.base;

import com.huateng.po.mchnt.TblInfMchntTp;
import com.huateng.po.mchnt.TblInfMchntTpPK;



public interface TblInfMchntTpDAO {
	public TblInfMchntTp get(TblInfMchntTpPK tblInfMchntTpPK);

	public TblInfMchntTp load(java.lang.String key);

	public java.util.List<TblInfMchntTp> findAll ();

	public java.lang.String save(TblInfMchntTp tblInfMchntTp);

	public void saveOrUpdate(TblInfMchntTp tblInfMchntTp);

	public void update(TblInfMchntTp tblInfMchntTp);
	
	

}
