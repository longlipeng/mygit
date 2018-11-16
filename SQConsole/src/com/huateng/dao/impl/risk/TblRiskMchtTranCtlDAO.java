package com.huateng.dao.impl.risk;

import java.util.List;
import com.huateng.dao._RootDAO;
import com.huateng.po.risk.TblRiskMchtTranCtl;

public class TblRiskMchtTranCtlDAO extends _RootDAO<TblRiskMchtTranCtl> implements com.huateng.dao.iface.risk.TblRiskMchtTranCtlDAO{

public TblRiskMchtTranCtlDAO () {}

/* (non-Javadoc)
 * @see com.huateng.dao.iface.risk.TblCtlTxnInfDAO#findAll()
 */
public List<TblRiskMchtTranCtl> findAll() {
	// TODO Auto-generated method stub
	return null;
}

public Class<TblRiskMchtTranCtl> getReferenceClass () {
	return TblRiskMchtTranCtl.class;
}

/**
 * Cast the object as a com.huateng.po.TblCtlCardInf
 */
public TblRiskMchtTranCtl cast (Object object) {
	return (TblRiskMchtTranCtl) object;
}

public TblRiskMchtTranCtl load(java.lang.String key)
{
	return (TblRiskMchtTranCtl) load(getReferenceClass(), key);
}

public TblRiskMchtTranCtl get(java.lang.String key)
{
	return (TblRiskMchtTranCtl) get(getReferenceClass(), key);
}

@SuppressWarnings("unchecked")
public java.util.List<TblRiskMchtTranCtl> loadAll()
{
	return loadAll(getReferenceClass());
}

/**
 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
 * of the identifier property if the assigned generator is used.)
 * @param tblCtlCardInf a transient instance of a persistent class
 * @return the class identifier
 */
public String save(TblRiskMchtTranCtl tblRiskMchtTranCtl)
{
	return (String) super.save(tblRiskMchtTranCtl);
}

/**
 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
 * identifier property mapping.
 * @param tblCtlCardInf a transient instance containing new or updated state
 */
public void saveOrUpdate(TblRiskMchtTranCtl tblRiskMchtTranCtl)
{
	super.saveOrUpdate(tblRiskMchtTranCtl);
}

/**
 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
 * instance with the same identifier in the current session.
 * @param tblCtlCardInf a transient instance containing updated state
 */
public void update(TblRiskMchtTranCtl tblRiskMchtTranCtl)
{
	super.update(tblRiskMchtTranCtl);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param tblCtlCardInf the instance to be removed
 */
public void delete(TblRiskMchtTranCtl tblRiskMchtTranCtl)
{
	super.delete((Object) tblRiskMchtTranCtl);
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