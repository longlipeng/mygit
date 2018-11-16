package com.huateng.dao.impl.base;

import com.huateng.dao._RootDAO;
import com.huateng.po.base.TblHolidays;

public class TblHolidaysDao extends _RootDAO<com.huateng.po.base.TblHolidays> implements com.huateng.dao.iface.base.TblHolidaysDao{
	public Class<TblHolidays> getReferenceClass () {
		return TblHolidays.class;
	}
	/**
	 * Cast the object as a Tblholidays
	 */
	public TblHolidays cast (Object object) {
		return (TblHolidays) object;
	}


	public TblHolidays load(String key)
		throws org.hibernate.HibernateException {
		return (TblHolidays) load(getReferenceClass(), key);
	}

	public TblHolidays get(String key)
		throws org.hibernate.HibernateException {
		return (TblHolidays) get(getReferenceClass(), key);
	}

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param cstMchtFeeInf a transient instance of a persistent class
	 * @return the class identifier
	 */
	public void save(TblHolidays tblholidays)
		throws org.hibernate.HibernateException {
		 super.save(tblholidays);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param cstMchtFeeInf a transient instance containing new or updated state
	 */
	public void saveOrUpdate(TblHolidays tblholidays)
		throws org.hibernate.HibernateException {
		super.saveOrUpdate(tblholidays);
	}

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param cstMchtFeeInf a transient instance containing updated state
	 */
	public void update(TblHolidays tblholidays)
		throws org.hibernate.HibernateException {
		super.update(tblholidays);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param cstMchtFeeInf the instance to be removed
	 */
	public void delete(TblHolidays tblholidays)
		throws org.hibernate.HibernateException {
		super.delete((Object) tblholidays);
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
