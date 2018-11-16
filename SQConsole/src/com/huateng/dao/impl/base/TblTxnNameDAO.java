/* @(#)
 *
 * Project:PFConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2011-6-27       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2011 Huateng Software, Inc. All rights reserved.
 *
 *       This software is the confidential and proprietary information of
 *       Shanghai HUATENG Software Co., Ltd. ("Confidential Information").
 *       You shall not disclose such Confidential Information and shall use it
 *       only in accordance with the terms of the license agreement you entered
 *       into with Huateng.
 *
 * Warning:
 * =============================================================================
 *
 */
package com.huateng.dao.impl.base;

import com.huateng.dao._RootDAO;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-6-27
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author liuxianxian
 * 
 * @version 1.0
 */
public class TblTxnNameDAO extends _RootDAO<com.huateng.po.CstSysParam> implements com.huateng.dao.iface.base.TblTxnNameDAO{

	// query name references




	public Class<com.huateng.po.TblTxnName> getReferenceClass () {
		return com.huateng.po.TblTxnName.class;
	}


	/**
	 * Cast the object as a com.huateng.po.TblTxnName
	 */
	public com.huateng.po.TblTxnName cast (Object object) {
		return (com.huateng.po.TblTxnName) object;
	}


	public com.huateng.po.TblTxnName load(java.lang.String key)
	{
		return (com.huateng.po.TblTxnName) load(getReferenceClass(), key);
	}

	public com.huateng.po.TblTxnName get(java.lang.String key)
	{
		return (com.huateng.po.TblTxnName) get(getReferenceClass(), key);
	}

	public java.util.List<com.huateng.po.TblTxnName> loadAll()
	{
		return loadAll(getReferenceClass());
	}





	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param tblTxnName a transient instance of a persistent class
	 * @return the class identifier
	 */
	public java.lang.String save(com.huateng.po.TblTxnName tblTxnName)
	{
		return (java.lang.String) super.save(tblTxnName);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param tblTxnName a transient instance containing new or updated state
	 */
	public void saveOrUpdate(com.huateng.po.TblTxnName tblTxnName)
	{
		super.saveOrUpdate(tblTxnName);
	}


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblTxnName a transient instance containing updated state
	 */
	public void update(com.huateng.po.TblTxnName tblTxnName)
	{
		super.update(tblTxnName);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param tblTxnName the instance to be removed
	 */
	public void delete(com.huateng.po.TblTxnName tblTxnName)
	{
		super.delete((Object) tblTxnName);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.String id)
	{
		super.delete((Object) load(id));
	}
}
