package com.huateng.dao.base;



/**
 * This is an automatically generated DAO class which should not be edited.
 */
public abstract class BaseTblBankBinInfDAO extends com.huateng.dao._RootDAO<com.huateng.po.TblBankBinInf> {



	// query name references




	public Class<com.huateng.po.TblBankBinInf> getReferenceClass () {
		return com.huateng.po.TblBankBinInf.class;
	}


	/**
	 * Cast the object as a com.huateng.po.TblBankBinInf
	 */
	public com.huateng.po.TblBankBinInf cast (Object object) {
		return (com.huateng.po.TblBankBinInf) object;
	}


	public com.huateng.po.TblBankBinInf load(java.lang.Integer key)
	{
		return (com.huateng.po.TblBankBinInf) load(getReferenceClass(), key);
	}

	public com.huateng.po.TblBankBinInf get(java.lang.Integer key)
	{
		return (com.huateng.po.TblBankBinInf) get(getReferenceClass(), key);
	}

	public java.util.List<com.huateng.po.TblBankBinInf> loadAll()
	{
		return loadAll(getReferenceClass());
	}





	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param tblBankBinInf a transient instance of a persistent class
	 * @return the class identifier
	 */
	public java.lang.Integer save(com.huateng.po.TblBankBinInf tblBankBinInf)
	{
		return (java.lang.Integer) super.save(tblBankBinInf);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param tblBankBinInf a transient instance containing new or updated state
	 */
	public void saveOrUpdate(com.huateng.po.TblBankBinInf tblBankBinInf)
	{
		super.saveOrUpdate(tblBankBinInf);
	}


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblBankBinInf a transient instance containing updated state
	 */
	public void update(com.huateng.po.TblBankBinInf tblBankBinInf)
	{
		super.update(tblBankBinInf);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param tblBankBinInf the instance to be removed
	 */
	public void delete(com.huateng.po.TblBankBinInf tblBankBinInf)
	{
		super.delete((Object) tblBankBinInf);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.Integer id)
	{
		super.delete((Object) load(id));
	}






}