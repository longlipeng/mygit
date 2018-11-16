package com.huateng.dao.impl.risk;

import java.util.List;
import com.huateng.dao._RootDAO;
import com.huateng.po.risk.TblRiskMchtTranLimit;

public class TblRiskMchtTranLimitDAO extends _RootDAO<TblRiskMchtTranLimit> implements
		com.huateng.dao.iface.risk.TblRiskMchtTranLimitDAO {

	public TblRiskMchtTranLimitDAO () {}
	
	public List<TblRiskMchtTranLimit> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public Class<TblRiskMchtTranLimit> getReferenceClass () {
		return TblRiskMchtTranLimit.class;
	}

	/**
	 * Cast the object as a com.huateng.po.TblCtlCardInf
	 */
	public TblRiskMchtTranLimit cast (Object object) {
		return (TblRiskMchtTranLimit) object;
	}

	public TblRiskMchtTranLimit load(java.lang.String key)
	{
		return (TblRiskMchtTranLimit) load(getReferenceClass(), key);
	}

	public TblRiskMchtTranLimit get(java.lang.String key)
	{
		return (TblRiskMchtTranLimit) get(getReferenceClass(), key);
	}

	@SuppressWarnings("unchecked")
	public java.util.List<TblRiskMchtTranLimit> loadAll(){
		return loadAll(getReferenceClass());
	}

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param tblCtlCardInf a transient instance of a persistent class
	 * @return the class identifier
	 */
	public String save(TblRiskMchtTranLimit tblRiskMchtTranLimit)
	{
		return (String) super.save(tblRiskMchtTranLimit);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param tblCtlCardInf a transient instance containing new or updated state
	 */
	public void saveOrUpdate(TblRiskMchtTranLimit tblRiskMchtTranLimit)
	{
		super.saveOrUpdate(tblRiskMchtTranLimit);
	}

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblCtlCardInf a transient instance containing updated state
	 */
	public void update(TblRiskMchtTranLimit tblRiskMchtTranLimit)
	{
		super.update(tblRiskMchtTranLimit);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param tblCtlCardInf the instance to be removed
	 */
	public void delete(TblRiskMchtTranLimit tblRiskMchtTranLimit)
	{
		super.delete((Object) tblRiskMchtTranLimit);
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
