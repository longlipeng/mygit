package com.huateng.dao.impl.pos;

import com.huateng.dao._RootDAO;

import com.huateng.po.pos.CstTermFeeIn;
import com.huateng.po.pos.CstTermFeePK;
public class CstTermFeeInDAO extends _RootDAO<com.huateng.po.pos.CstTermFeeIn> implements com.huateng.dao.iface.pos.CstTermFeeInDAO{
	public Class<CstTermFeeIn> getReferenceClass () {
		return CstTermFeeIn.class;
	}


	/**
	 * Cast the object as a CstMchtFeeInf
	 */
	public CstTermFeeIn cast (Object object) {
		return (CstTermFeeIn) object;
	}


	public CstTermFeeIn load(CstTermFeePK key)
		throws org.hibernate.HibernateException {
		return (CstTermFeeIn) load(getReferenceClass(), key);
	}

	public CstTermFeeIn get(CstTermFeePK key)
		throws org.hibernate.HibernateException {
		return (CstTermFeeIn) get(getReferenceClass(), key);
	}



	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param cstMchtFeeInf a transient instance of a persistent class
	 * @return the class identifier
	 */
	public CstTermFeePK save(CstTermFeeIn cstTermFeeIn)
		throws org.hibernate.HibernateException {
		return (CstTermFeePK) super.save(cstTermFeeIn);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param cstMchtFeeInf a transient instance containing new or updated state
	 */
	public void saveOrUpdate(CstTermFeeIn cstTermFeeIn)
		throws org.hibernate.HibernateException {
		super.saveOrUpdate(cstTermFeeIn);
	}


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param cstMchtFeeInf a transient instance containing updated state
	 */
	public void update(CstTermFeeIn cstTermFeeIn)
		throws org.hibernate.HibernateException {
		super.update(cstTermFeeIn);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param cstMchtFeeInf the instance to be removed
	 */
	public void delete(CstTermFeeIn cstTermFeeIn)
		throws org.hibernate.HibernateException {
		super.delete((Object) cstTermFeeIn);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param id the instance ID to be removed
	 */
	public void delete(CstTermFeePK id)
		throws org.hibernate.HibernateException {
		super.delete((Object) load(id));
	}


}
