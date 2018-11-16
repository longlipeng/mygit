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
import com.huateng.po.mchnt.TblMchtBeneficiaryInfTmp;
import com.huateng.po.mchnt.TblHisDiscAlgo1Tmp;
import com.huateng.po.mchnt.TblHisDiscAlgo2Tmp;

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
public class TblHisDiscAlgo1TmpDAO extends _RootDAO<com.huateng.po.mchnt.TblHisDiscAlgo1Tmp> implements com.huateng.dao.iface.mchnt.TblHisDiscAlgo1TmpDAO{

	// query name references
	public Class<TblHisDiscAlgo1Tmp> getReferenceClass () {
		return TblHisDiscAlgo1Tmp.class;
	}

	/**
	 * Cast the object as a CstMchtFeeInf
	 */
	public TblHisDiscAlgo1Tmp cast (Object object) {
		return (TblHisDiscAlgo1Tmp) object;
	}


	public TblHisDiscAlgo1Tmp load(String key)
		throws org.hibernate.HibernateException {
		return (TblHisDiscAlgo1Tmp) load(getReferenceClass(), key);
	}

	public TblHisDiscAlgo2Tmp loadAlgo2(String key)
		throws org.hibernate.HibernateException {
		return (TblHisDiscAlgo2Tmp) load(TblHisDiscAlgo2Tmp.class, key);
	}
	public TblHisDiscAlgo1Tmp get(String key)
		throws org.hibernate.HibernateException {
		return (TblHisDiscAlgo1Tmp) get(getReferenceClass(), key);
	}

	public TblHisDiscAlgo2Tmp getAlgo2(String key)
		throws org.hibernate.HibernateException {
		return (TblHisDiscAlgo2Tmp) get(TblHisDiscAlgo2Tmp.class, key);
	}

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param cstMchtFeeInf a transient instance of a persistent class
	 * @return the class identifier
	 */
	public String save(TblHisDiscAlgo1Tmp tblHisDiscAlgo1Tmp) {
		return (java.lang.String) super.save(tblHisDiscAlgo1Tmp);
	}

	public String saveAlgo2(TblHisDiscAlgo2Tmp tblHisDiscAlgo2Tmp) {
		return (java.lang.String) super.save(tblHisDiscAlgo2Tmp);
	}
	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param cstMchtFeeInf a transient instance containing new or updated state
	 */
	public void saveOrUpdate(TblHisDiscAlgo1Tmp tblHisDiscAlgo1Tmp)
		throws org.hibernate.HibernateException {
		super.saveOrUpdate(tblHisDiscAlgo1Tmp);
	}


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param cstMchtFeeInf a transient instance containing updated state
	 */
	public void update(TblHisDiscAlgo1Tmp tblHisDiscAlgo1Tmp)
		throws org.hibernate.HibernateException {
		super.update(tblHisDiscAlgo1Tmp);
	}
	
	public void updateAlgo2(TblHisDiscAlgo2Tmp tblHisDiscAlgo2Tmp)
		throws org.hibernate.HibernateException {
		super.update(tblHisDiscAlgo2Tmp);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param cstMchtFeeInf the instance to be removed
	 */
	public void delete(TblHisDiscAlgo1Tmp tblHisDiscAlgo1Tmp)
		throws org.hibernate.HibernateException {
		super.delete((Object) tblHisDiscAlgo1Tmp);
	}
	
	public void deleteAlgo2(TblHisDiscAlgo2Tmp tblHisDiscAlgo2Tmp)
	throws org.hibernate.HibernateException {
	super.delete((Object) tblHisDiscAlgo2Tmp);
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
	public void deleteAlgo2(String id)
		throws org.hibernate.HibernateException {
		super.delete((Object) loadAlgo2(id));
	}

	/**
	 * 根据id查询记录
	 */
	public TblMchtBeneficiaryInfTmp getBeneficiary(String key)
		throws org.hibernate.HibernateException {
		return (TblMchtBeneficiaryInfTmp) get(TblMchtBeneficiaryInfTmp.class, key);
	}

	/**
	 * 受益人信息删除
	 */
	public TblMchtBeneficiaryInfTmp loadBeneficiary(String key)
		throws org.hibernate.HibernateException {
		return (TblMchtBeneficiaryInfTmp) load(TblMchtBeneficiaryInfTmp.class, key);
	}
	
	/**
	 * 受益人信息删除
	 */
	public void deleteBeneficiary(String beneficiaryId)
		// TODO Auto-generated method stub
		throws org.hibernate.HibernateException {
		super.delete((Object) loadBeneficiary(beneficiaryId));
	}

	/**
	 * 添加受益人信息
	 */
	public void addBeneficiary(TblMchtBeneficiaryInfTmp amlt) 
		// TODO Auto-generated method stub
		throws org.hibernate.HibernateException {
		super.save(amlt);
	}

	/**
	 * 修改受益人信息
	 */
	public void updateBeneficiary(TblMchtBeneficiaryInfTmp amlt) 
		// TODO Auto-generated method stub
		throws org.hibernate.HibernateException {
		super.update(amlt);
	}
	
}
