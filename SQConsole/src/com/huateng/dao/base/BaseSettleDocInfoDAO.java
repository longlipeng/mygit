package com.huateng.dao.base;



/**
 * This is an automatically generated DAO class which should not be edited.
 */
public abstract class BaseSettleDocInfoDAO extends com.huateng.dao._RootDAO<com.huateng.po.SettleDocInfo> {



	// query name references




	public Class<com.huateng.po.SettleDocInfo> getReferenceClass () {
		return com.huateng.po.SettleDocInfo.class;
	}


	/**
	 * Cast the object as a com.huateng.po.SettleDocInfo
	 */
	public com.huateng.po.SettleDocInfo cast (Object object) {
		return (com.huateng.po.SettleDocInfo) object;
	}


	public com.huateng.po.SettleDocInfo load(java.lang.String key)
	{
		return (com.huateng.po.SettleDocInfo) load(getReferenceClass(), key);
	}

	public com.huateng.po.SettleDocInfo get(java.lang.String key)
	{
		return (com.huateng.po.SettleDocInfo) get(getReferenceClass(), key);
	}

	public java.util.List<com.huateng.po.SettleDocInfo> loadAll()
	{
		return loadAll(getReferenceClass());
	}





	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param settleDocInfo a transient instance of a persistent class
	 * @return the class identifier
	 */
	public java.lang.String save(com.huateng.po.SettleDocInfo settleDocInfo)
	{
		return (java.lang.String) super.save(settleDocInfo);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param settleDocInfo a transient instance containing new or updated state
	 */
	public void saveOrUpdate(com.huateng.po.SettleDocInfo settleDocInfo)
	{
		super.saveOrUpdate(settleDocInfo);
	}


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param settleDocInfo a transient instance containing updated state
	 */
	public void update(com.huateng.po.SettleDocInfo settleDocInfo)
	{
		super.update(settleDocInfo);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param settleDocInfo the instance to be removed
	 */
	public void delete(com.huateng.po.SettleDocInfo settleDocInfo)
	{
		super.delete((Object) settleDocInfo);
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