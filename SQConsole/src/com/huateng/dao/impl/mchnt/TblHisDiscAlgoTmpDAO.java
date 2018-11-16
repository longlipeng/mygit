package com.huateng.dao.impl.mchnt;

import java.util.List;

import com.huateng.dao._RootDAO;
import com.huateng.po.mchnt.TblHisDiscAlgoTmp;
import com.huateng.po.mchnt.TblHisDiscAlgoPK;

public class TblHisDiscAlgoTmpDAO extends _RootDAO<com.huateng.po.mchnt.TblHisDiscAlgoTmp> implements com.huateng.dao.iface.mchnt.TblHisDiscAlgoTmpDAO{

	public TblHisDiscAlgoTmpDAO () {}

	/* (non-Javadoc)
	 * @see com.huateng.dao.iface.TblDivMchntTmpDAO#findAll()
	 */
	public List<TblHisDiscAlgoTmp> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public Class<TblHisDiscAlgoTmp> getReferenceClass () {
		return TblHisDiscAlgoTmp.class;
	}


	/**
	 * Cast the object as a TblHisDiscAlgo
	 */
	public TblHisDiscAlgoTmp cast (Object object) {
		return (TblHisDiscAlgoTmp) object;
	}


	public TblHisDiscAlgoTmp load(TblHisDiscAlgoPK key)
	{
		return (TblHisDiscAlgoTmp) load(getReferenceClass(), key);
	}

	public TblHisDiscAlgoTmp get(TblHisDiscAlgoPK key)
	{
		return (TblHisDiscAlgoTmp) get(getReferenceClass(), key);
	}

	@SuppressWarnings("unchecked")
	public java.util.List<TblHisDiscAlgoTmp> loadAll()
	{
		return loadAll(getReferenceClass());
	}





	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param tblDivMchntTmp a transient instance of a persistent class
	 * @return the class identifier
	 */
	public TblHisDiscAlgoPK save(TblHisDiscAlgoTmp tblDivMchntTmp)
	{
		return (TblHisDiscAlgoPK) super.save(tblDivMchntTmp);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param tblDivMchntTmp a transient instance containing new or updated state
	 */
	public void saveOrUpdate(TblHisDiscAlgoTmp tblDivMchntTmp)
	{
		super.saveOrUpdate(tblDivMchntTmp);
	}


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblDivMchntTmp a transient instance containing updated state
	 */
	public void update(TblHisDiscAlgoTmp tblDivMchntTmp)
	{
		super.update(tblDivMchntTmp);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param tblDivMchntTmp the instance to be removed
	 */
	public void delete(TblHisDiscAlgoTmp tblDivMchntTmp)
	{
		super.delete((Object) tblDivMchntTmp);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param id the instance ID to be removed
	 */
	public void delete(TblHisDiscAlgoPK id)
	{
		super.delete((Object) load(id));
	}
}