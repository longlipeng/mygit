/* @(#)
 *
 * Project:PFConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2011-6-15       first release
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
package com.huateng.dao.impl.mchnt;

import com.huateng.dao._RootDAO;
import com.huateng.po.mchnt.TblHisDiscAlgo1;


/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-6-15
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class TblHisDiscAlgo1DAO extends _RootDAO<com.huateng.po.mchnt.TblHisDiscAlgo1> implements com.huateng.dao.iface.mchnt.TblHisDiscAlgo1DAO{
	

	// query name references




	public Class<TblHisDiscAlgo1> getReferenceClass () {
		return TblHisDiscAlgo1.class;
	}


	/**
	 * Cast the object as a CstMchtFeeInf
	 */
	public TblHisDiscAlgo1 cast (Object object) {
		return (TblHisDiscAlgo1) object;
	}


	public TblHisDiscAlgo1 load(String key)
		throws org.hibernate.HibernateException {
		return (TblHisDiscAlgo1) load(getReferenceClass(), key);
	}

	public TblHisDiscAlgo1 get(String key)
		throws org.hibernate.HibernateException {
		return (TblHisDiscAlgo1) get(getReferenceClass(), key);
	}



	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param cstMchtFeeInf a transient instance of a persistent class
	 * @return the class identifier
	 */
	public String save(TblHisDiscAlgo1 tblHisDiscAlgo1) {
		return (java.lang.String) super.save(tblHisDiscAlgo1);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param cstMchtFeeInf a transient instance containing new or updated state
	 */
	public void saveOrUpdate(TblHisDiscAlgo1 tblHisDiscAlgo1)
		throws org.hibernate.HibernateException {
		super.saveOrUpdate(tblHisDiscAlgo1);
	}


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param cstMchtFeeInf a transient instance containing updated state
	 */
	public void update(TblHisDiscAlgo1 tblHisDiscAlgo1)
		throws org.hibernate.HibernateException {
		super.update(tblHisDiscAlgo1);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param cstMchtFeeInf the instance to be removed
	 */
	public void delete(TblHisDiscAlgo1 tblHisDiscAlgo1)
		throws org.hibernate.HibernateException {
		super.delete((Object) tblHisDiscAlgo1);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param id the instance ID to be removed
	 */
	public void delete(String id)
		throws org.hibernate.HibernateException {
		super.delete((Object) load(id));
	}


}
