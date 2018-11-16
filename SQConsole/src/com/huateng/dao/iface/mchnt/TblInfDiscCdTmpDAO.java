package com.huateng.dao.iface.mchnt;

import com.huateng.po.mchnt.TblInfDiscCdTmp;

public interface TblInfDiscCdTmpDAO {
	public abstract Class getReferenceClass();

	public abstract TblInfDiscCdTmp get(java.lang.String key);

	public abstract TblInfDiscCdTmp load(java.lang.String key);

    public abstract java.lang.String save(TblInfDiscCdTmp tblInfDiscCdTmp);

    public abstract void saveOrUpdate(TblInfDiscCdTmp tblInfDiscCdTmp);

    public abstract void update(TblInfDiscCdTmp tblInfDiscCdTmp);

    public abstract void delete(java.lang.String id);

    public abstract void delete(TblInfDiscCdTmp tblInfDiscCdTmp);

    public abstract void refresh (TblInfDiscCdTmp tblInfDiscCdTmp);
}
