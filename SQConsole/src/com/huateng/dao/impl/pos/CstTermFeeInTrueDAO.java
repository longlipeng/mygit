package com.huateng.dao.impl.pos;

import com.huateng.dao._RootDAO;

import com.huateng.po.pos.CstTermFeeInTrue;
import com.huateng.po.pos.CstTermFeePK;
public class CstTermFeeInTrueDAO extends _RootDAO<com.huateng.po.pos.CstTermFeeInTrue> implements com.huateng.dao.iface.pos.CstTermFeeInTrueDAO{
	public Class<CstTermFeeInTrue> getReferenceClass () {
		return CstTermFeeInTrue.class;
	}

	/**
	 * Cast the object as a CstMchtFeeInf
	 */
	public CstTermFeeInTrue cast (Object object) {
		return (CstTermFeeInTrue) object;
	}

	public CstTermFeeInTrue load(CstTermFeePK key)
		throws org.hibernate.HibernateException {
		return (CstTermFeeInTrue) load(getReferenceClass(), key);
	}

	public CstTermFeeInTrue get(CstTermFeePK key)
		throws org.hibernate.HibernateException {
		return (CstTermFeeInTrue) get(getReferenceClass(), key);
	}

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param cstMchtFeeInf a transient instance of a persistent class
	 * @return the class identifier
	 */
	public CstTermFeePK save(CstTermFeeInTrue cstTermFeeInTrue)
		throws org.hibernate.HibernateException {
		return (CstTermFeePK) super.save(cstTermFeeInTrue);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param cstMchtFeeInf a transient instance containing new or updated state
	 */
	public void saveOrUpdate(CstTermFeeInTrue cstTermFeeInTrue)
		throws org.hibernate.HibernateException {
		super.saveOrUpdate(cstTermFeeInTrue);
	}

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param cstMchtFeeInf a transient instance containing updated state
	 */
	public void update(CstTermFeeInTrue cstTermFeeInTrue)
		throws org.hibernate.HibernateException {
		super.update(cstTermFeeInTrue);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param cstMchtFeeInf the instance to be removed
	 */
	public void delete(CstTermFeeInTrue cstTermFeeInTrue)
		throws org.hibernate.HibernateException {
		super.delete((Object) cstTermFeeInTrue);
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
