package com.huateng.dao.base;



/**
 * This is an automatically generated DAO class which should not be edited.
 */
public abstract class BaseTblProfessionalOrganDAO extends com.huateng.dao._RootDAO<com.huateng.po.mchtSrv.TblProfessionalOrgan> {



	// query name references




	public Class<com.huateng.po.mchtSrv.TblProfessionalOrgan> getReferenceClass () {
	//	return com.huateng.po.mchtSrv.TblProfessionalOrgan.class;
		return null;
	}


	/**
	 * Cast the object as a com.huateng.po.market.TblProfessionalOrgan
	 */
	public com.huateng.po.mchtSrv.TblProfessionalOrgan cast (Object object) {
	//	return (com.huateng.po.mchtSrv.TblProfessionalOrgan) object;
		return null;
	}


	public com.huateng.po.mchtSrv.TblProfessionalOrgan load(java.lang.String key)
	{
	//	return (com.huateng.po.mchtSrv.TblProfessionalOrgan) load(getReferenceClass(), key);
		return null;
	}

	public com.huateng.po.mchtSrv.TblProfessionalOrgan get(java.lang.String key)
	{
	//	return (com.huateng.po.mchtSrv.TblProfessionalOrgan) get(getReferenceClass(), key);
		return null;
	}

	public java.util.List<com.huateng.po.mchtSrv.TblProfessionalOrgan> loadAll()
	{
	//	return loadAll(getReferenceClass());
		return null;
	}





	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param tblProfessionalOrgan a transient instance of a persistent class
	 * @return the class identifier
	 */
	public java.lang.String save(com.huateng.po.mchtSrv.TblProfessionalOrgan tblProfessionalOrgan)
	{
	//	return (java.lang.String) super.save(tblProfessionalOrgan);
		return null;
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param tblProfessionalOrgan a transient instance containing new or updated state
	 */
	public void saveOrUpdate(com.huateng.po.mchtSrv.TblProfessionalOrgan tblProfessionalOrgan)
	{
	//	super.saveOrUpdate(tblProfessionalOrgan);
	}


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblProfessionalOrgan a transient instance containing updated state
	 */
	public void update(com.huateng.po.mchtSrv.TblProfessionalOrgan tblProfessionalOrgan)
	{
	//	super.update(tblProfessionalOrgan);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param tblProfessionalOrgan the instance to be removed
	 */
	public void delete(com.huateng.po.mchtSrv.TblProfessionalOrgan tblProfessionalOrgan)
	{
	//	super.delete((Object) tblProfessionalOrgan);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.String id)
	{
	//	super.delete((Object) load(id));
	}






}