package com.huateng.dao.impl.mchnt;

import org.hibernate.HibernateException;

import com.huateng.dao._RootDAO;
import com.huateng.dao.iface.mchnt.ITblMchtSettleInfTmpDAO;
import com.huateng.po.mchnt.TblMchtBaseBusiRange;
import com.huateng.po.mchnt.TblMchtSettleInfPK;
import com.huateng.po.mchnt.TblMchtSettleInfTmp;
import com.huateng.po.mchnt.TblMchtSupp1;
import com.huateng.po.mchnt.TblMchtSupp1Tmp;

public class TblMchtSettleInfTmpDAO extends _RootDAO<TblMchtSettleInfTmp> implements ITblMchtSettleInfTmpDAO{

	// query name references
	public Class<TblMchtSettleInfTmp> getReferenceClass () {
		return TblMchtSettleInfTmp.class;
	}

	/**
	 * Cast the object as a TblMchtSettleInfTmp
	 */
	public TblMchtSettleInfTmp cast (Object object) {
		return (TblMchtSettleInfTmp) object;
	}

	public TblMchtSettleInfTmp load(TblMchtSettleInfPK key) throws org.hibernate.HibernateException {
		return (TblMchtSettleInfTmp) load(getReferenceClass(), key);
	}

	public TblMchtSettleInfTmp get(TblMchtSettleInfPK key) throws org.hibernate.HibernateException {
		return (TblMchtSettleInfTmp) get(getReferenceClass(), key);
	}

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param tblMchtSettleInfTmp a transient instance of a persistent class
	 * @return the class identifier
	 */
	public TblMchtSettleInfPK save(TblMchtSettleInfTmp tblMchtSettleInfTmp) throws org.hibernate.HibernateException {
		return (TblMchtSettleInfPK) super.save(tblMchtSettleInfTmp);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param tblMchtSettleInfTmp a transient instance containing new or updated state
	 */
	public void saveOrUpdate(TblMchtSettleInfTmp tblMchtSettleInfTmp) throws org.hibernate.HibernateException {
		super.saveOrUpdate(tblMchtSettleInfTmp);
	}
	public void saveOrUpdate(TblMchtSupp1Tmp suppTmp) throws org.hibernate.HibernateException {
		super.saveOrUpdate(suppTmp);
	}
	public void saveOrUpdate(TblMchtSupp1 supp) throws org.hibernate.HibernateException {
		super.saveOrUpdate(supp);
	}	
	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblMchtSettleInfTmp a transient instance containing updated state
	 */
	public void update(TblMchtSettleInfTmp tblMchtSettleInfTmp) throws org.hibernate.HibernateException {
		super.update(tblMchtSettleInfTmp);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param tblMchtSettleInfTmp the instance to be removed
	 */
	public void delete(TblMchtSettleInfTmp tblMchtSettleInfTmp) throws org.hibernate.HibernateException {
		super.delete((Object) tblMchtSettleInfTmp);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param id the instance ID to be removed
	 */
	public void delete(TblMchtSettleInfPK id) throws org.hibernate.HibernateException {
		super.delete((Object) load(id));
	}
	
	public java.lang.String save(TblMchtSupp1Tmp suppTmp) throws org.hibernate.HibernateException {
		return (java.lang.String) super.save(suppTmp);
	}
	
	/**
	 * 经营范围
	 * @param tblMchtBaseBusiRange
	 * @return
	 * @throws org.hibernate.HibernateException
	 */
	public java.lang.String save(TblMchtBaseBusiRange tblMchtBaseBusiRange) throws org.hibernate.HibernateException {
		return (java.lang.String) super.save(tblMchtBaseBusiRange);
	}

	public TblMchtSupp1Tmp getSupp1Tmp(String mchtId) throws org.hibernate.HibernateException{
		return (TblMchtSupp1Tmp) get(TblMchtSupp1Tmp.class, mchtId);
	}
	public TblMchtSupp1 getSupp1(String mchtId) throws org.hibernate.HibernateException{
		return (TblMchtSupp1) get(TblMchtSupp1.class, mchtId);
	}

	public void delete(TblMchtSupp1Tmp supp1Tmp) throws HibernateException {
		super.delete((Object) supp1Tmp);
	}

}