package com.huateng.dao.impl.mchnt;

import java.util.List;

import com.huateng.dao._RootDAO;
import com.huateng.po.TblDivMchntTmp;


public class TblDivMchntTmpDAO extends _RootDAO<com.huateng.po.TblDivMchnt> implements com.huateng.dao.iface.mchnt.TblDivMchntTmpDAO{

public TblDivMchntTmpDAO () {}

/* (non-Javadoc)
 * @see com.huateng.dao.iface.TblDivMchntTmpDAO#findAll()
 */
public List<TblDivMchntTmp> findAll() {
	// TODO Auto-generated method stub
	return null;
}

public Class<com.huateng.po.TblDivMchntTmp> getReferenceClass () {
	return com.huateng.po.TblDivMchntTmp.class;
}


/**
 * Cast the object as a com.huateng.po.TblDivMchntTmp
 */
public com.huateng.po.TblDivMchntTmp cast (Object object) {
	return (com.huateng.po.TblDivMchntTmp) object;
}


public com.huateng.po.TblDivMchntTmp load(com.huateng.po.TblDivMchntTmpPK key)
{
	return (com.huateng.po.TblDivMchntTmp) load(getReferenceClass(), key);
}

public com.huateng.po.TblDivMchntTmp get(com.huateng.po.TblDivMchntTmpPK key)
{
	return (com.huateng.po.TblDivMchntTmp) get(getReferenceClass(), key);
}

@SuppressWarnings("unchecked")
public java.util.List<com.huateng.po.TblDivMchntTmp> loadAll()
{
	return loadAll(getReferenceClass());
}





/**
 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
 * of the identifier property if the assigned generator is used.)
 * @param tblDivMchntTmp a transient instance of a persistent class
 * @return the class identifier
 */
public com.huateng.po.TblDivMchntTmpPK save(com.huateng.po.TblDivMchntTmp tblDivMchntTmp)
{
	return (com.huateng.po.TblDivMchntTmpPK) super.save(tblDivMchntTmp);
}

/**
 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
 * identifier property mapping.
 * @param tblDivMchntTmp a transient instance containing new or updated state
 */
public void saveOrUpdate(com.huateng.po.TblDivMchntTmp tblDivMchntTmp)
{
	super.saveOrUpdate(tblDivMchntTmp);
}


/**
 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
 * instance with the same identifier in the current session.
 * @param tblDivMchntTmp a transient instance containing updated state
 */
public void update(com.huateng.po.TblDivMchntTmp tblDivMchntTmp)
{
	super.update(tblDivMchntTmp);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param tblDivMchntTmp the instance to be removed
 */
public void delete(com.huateng.po.TblDivMchntTmp tblDivMchntTmp)
{
	super.delete((Object) tblDivMchntTmp);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param id the instance ID to be removed
 */
public void delete(com.huateng.po.TblDivMchntTmpPK id)
{
	super.delete((Object) load(id));
}
}