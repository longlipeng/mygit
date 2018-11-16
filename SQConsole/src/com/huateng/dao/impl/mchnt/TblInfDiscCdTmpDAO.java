package com.huateng.dao.impl.mchnt;

import java.util.List;

import com.huateng.dao._RootDAO;
import com.huateng.po.mchnt.TblInfDiscCdTmp;


public class TblInfDiscCdTmpDAO  extends _RootDAO<com.huateng.po.mchnt.TblInfDiscCdTmp> implements com.huateng.dao.iface.mchnt.TblInfDiscCdTmpDAO {


	/* (non-Javadoc)
	 * @see com.huateng.dao.iface.TblMchntInfoDAO#findAll()
	 */
	public List<TblInfDiscCdTmp> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public Class<TblInfDiscCdTmp> getReferenceClass () {
		return TblInfDiscCdTmp.class;
	}


	/**
	 * Cast the object as a TblInfDiscCd
	 */
	public TblInfDiscCdTmp cast (Object object) {
		return (TblInfDiscCdTmp) object;
	}


	public TblInfDiscCdTmp load(java.lang.String key)
	{
		return (TblInfDiscCdTmp) load(getReferenceClass(), key);
	}

	public TblInfDiscCdTmp get(java.lang.String key)
	{
		return (TblInfDiscCdTmp) get(getReferenceClass(), key);
	}

	@SuppressWarnings("unchecked")
	public java.util.List<TblInfDiscCdTmp> loadAll()
	{
		return loadAll(getReferenceClass());
	}





	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param tblMchntInfo a transient instance of a persistent class
	 * @return the class identifier
	 */
	public java.lang.String save(TblInfDiscCdTmp tblInfDiscCdTmp)
	{
		return (java.lang.String) super.save(tblInfDiscCdTmp);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param tblMchntInfo a transient instance containing new or updated state
	 */
	public void saveOrUpdate(TblInfDiscCdTmp tblMchntInfo)
	{
		super.saveOrUpdate(tblMchntInfo);
	}


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblMchntInfo a transient instance containing updated state
	 */
	public void update(TblInfDiscCdTmp tblMchntInfo)
	{
		super.update(tblMchntInfo);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param tblMchntInfo the instance to be removed
	 */
	public void delete(TblInfDiscCdTmp tblInfDiscCd)
	{
		super.delete((Object) tblInfDiscCd);
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

	/* (non-Javadoc)
	 * @see com.huateng.dao.iface.mchnt.TblInfDiscCdDAO#refresh(com.huateng.po.mchnt.TblInfDiscCd)
	 */
	public void refresh(TblInfDiscCdTmp tblInfDiscCd) {
		super.refresh(tblInfDiscCd);
	}

	
	
}