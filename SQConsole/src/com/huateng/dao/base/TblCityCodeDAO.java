package com.huateng.dao.base;

import java.util.List;

import com.huateng.dao._RootDAO;
import com.huateng.po.TblCityCode;


public class TblCityCodeDAO extends _RootDAO<com.huateng.po.TblCityCode> implements com.huateng.dao.iface.base.TblCityCodeDAO{

public TblCityCodeDAO () {}

/* (non-Javadoc)
 * @see com.huateng.dao.iface.TblCityCodeDAO#findAll()
 */
public List<TblCityCode> findAll() {
	// TODO Auto-generated method stub
	return null;
}

public Class<com.huateng.po.TblCityCode> getReferenceClass () {
	return com.huateng.po.TblCityCode.class;
}


/**
 * Cast the object as a com.huateng.po.TblCityCode
 */
public com.huateng.po.TblCityCode cast (Object object) {
	return (com.huateng.po.TblCityCode) object;
}


public com.huateng.po.TblCityCode load(java.lang.String key)
{
	return (com.huateng.po.TblCityCode) load(getReferenceClass(), key);
}

public com.huateng.po.TblCityCode get(java.lang.String key)
{
	return (com.huateng.po.TblCityCode) get(getReferenceClass(), key);
}

@SuppressWarnings("unchecked")
public java.util.List<com.huateng.po.TblCityCode> loadAll()
{
	return loadAll(getReferenceClass());
}





/**
 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
 * of the identifier property if the assigned generator is used.)
 * @param tblCityCode a transient instance of a persistent class
 * @return the class identifier
 */
public java.lang.String save(com.huateng.po.TblCityCode tblCityCode)
{
	return (java.lang.String) super.save(tblCityCode);
}

/**
 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
 * identifier property mapping.
 * @param tblCityCode a transient instance containing new or updated state
 */
public void saveOrUpdate(com.huateng.po.TblCityCode tblCityCode)
{
	super.saveOrUpdate(tblCityCode);
}


/**
 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
 * instance with the same identifier in the current session.
 * @param tblCityCode a transient instance containing updated state
 */
public void update(com.huateng.po.TblCityCode tblCityCode)
{
	super.update(tblCityCode);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param tblCityCode the instance to be removed
 */
public void delete(com.huateng.po.TblCityCode tblCityCode)
{
	super.delete((Object) tblCityCode);
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