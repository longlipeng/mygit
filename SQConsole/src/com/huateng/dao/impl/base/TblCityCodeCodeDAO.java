package com.huateng.dao.impl.base;

import com.huateng.dao._RootDAO;

import com.huateng.po.base.TblCityCodeCode;

public class TblCityCodeCodeDAO extends _RootDAO<com.huateng.po.base.TblCityCodeCode> implements com.huateng.dao.iface.base.TblCityCodeCodeDAO{
	public Class<TblCityCodeCode> getReferenceClass () {
		return TblCityCodeCode.class;
	}


	/**
	 * Cast the object as a CstMchtFeeInf
	 */
	public TblCityCodeCode cast (Object object) {
		return (TblCityCodeCode) object;
	}


	public TblCityCodeCode load(String key)
		throws org.hibernate.HibernateException {
		return (TblCityCodeCode) load(getReferenceClass(), key);
	}

	public TblCityCodeCode get(String key)
		throws org.hibernate.HibernateException {
		return (TblCityCodeCode) get(getReferenceClass(), key);
	}



	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param cstMchtFeeInf a transient instance of a persistent class
	 * @return the class identifier
	 */
	public void save(TblCityCodeCode tblCityCodeCode)
		throws org.hibernate.HibernateException {
		 super.save(tblCityCodeCode);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param cstMchtFeeInf a transient instance containing new or updated state
	 */
	public void saveOrUpdate(TblCityCodeCode tblCityCodeCode)
		throws org.hibernate.HibernateException {
		super.saveOrUpdate(tblCityCodeCode);
	}


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param cstMchtFeeInf a transient instance containing updated state
	 */
	public void update(TblCityCodeCode tblCityCodeCode)
		throws org.hibernate.HibernateException {
		super.update(tblCityCodeCode);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param cstMchtFeeInf the instance to be removed
	 */
	public void delete(TblCityCodeCode tblCityCodeCode)
		throws org.hibernate.HibernateException {
		super.delete((Object) tblCityCodeCode);
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
