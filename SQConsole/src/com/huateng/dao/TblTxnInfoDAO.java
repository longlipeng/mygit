package com.huateng.dao;



public class TblTxnInfoDAO extends _RootDAO<com.huateng.po.TblTermZmkInf> {

public TblTxnInfoDAO () {}


public Class<com.huateng.po.TblTxnInfo> getReferenceClass () {
	return com.huateng.po.TblTxnInfo.class;
}


/**
 * Cast the object as a com.huateng.po.TblTxnInfo
 */
public com.huateng.po.TblTxnInfo cast (Object object) {
	return (com.huateng.po.TblTxnInfo) object;
}


public com.huateng.po.TblTxnInfo load(com.huateng.po.TblTxnInfoPK key)
{
	return (com.huateng.po.TblTxnInfo) load(getReferenceClass(), key);
}

public com.huateng.po.TblTxnInfo get(com.huateng.po.TblTxnInfoPK key)
{
	return (com.huateng.po.TblTxnInfo) get(getReferenceClass(), key);
}

@SuppressWarnings("unchecked")
public java.util.List<com.huateng.po.TblTxnInfo> loadAll()
{
	return loadAll(getReferenceClass());
}





/**
 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
 * of the identifier property if the assigned generator is used.)
 * @param tblTxnInfo a transient instance of a persistent class
 * @return the class identifier
 */
public com.huateng.po.TblTxnInfoPK save(com.huateng.po.TblTxnInfo tblTxnInfo)
{
	return (com.huateng.po.TblTxnInfoPK) super.save(tblTxnInfo);
}

/**
 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
 * identifier property mapping.
 * @param tblTxnInfo a transient instance containing new or updated state
 */
public void saveOrUpdate(com.huateng.po.TblTxnInfo tblTxnInfo)
{
	super.saveOrUpdate(tblTxnInfo);
}


/**
 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
 * instance with the same identifier in the current session.
 * @param tblTxnInfo a transient instance containing updated state
 */
public void update(com.huateng.po.TblTxnInfo tblTxnInfo)
{
	super.update(tblTxnInfo);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param tblTxnInfo the instance to be removed
 */
public void delete(com.huateng.po.TblTxnInfo tblTxnInfo)
{
	super.delete((Object) tblTxnInfo);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param id the instance ID to be removed
 */
public void delete(com.huateng.po.TblTxnInfoPK id)
{
	super.delete((Object) load(id));
}
}