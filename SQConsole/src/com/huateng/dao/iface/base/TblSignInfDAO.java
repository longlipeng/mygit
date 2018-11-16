package com.huateng.dao.iface.base;

import com.huateng.po.base.TblSignInf;
public interface TblSignInfDAO {
	public TblSignInf get(java.lang.String key);
	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param tblBrhInfo a transient instance containing new or updated state 
	 */
	public void update(com.huateng.po.base.TblSignInf tblSignInf);
}
