package com.huateng.dao.iface.base;

public interface TblBranchManageTrueDAO {
	public com.huateng.po.base.TblBranchManageTrue get(java.lang.String key);

	public com.huateng.po.base.TblBranchManageTrue load(java.lang.String key);

	public java.util.List<com.huateng.po.base.TblBranchManageTrue> findAll();

	/**
	 * Persist the given transient instance, first assigning a generated
	 * identifier. (Or using the current value of the identifier property if the
	 * assigned generator is used.)
	 * 
	 * @param tblBrhInfo
	 *            a transient instance of a persistent class
	 * @return the class identifier
	 */
	public java.lang.String save(
			com.huateng.po.base.TblBranchManageTrue tblBranchManageTrue);

	/**
	 * Either save() or update() the given instance, depending upon the value of
	 * its identifier property. By default the instance is always saved. This
	 * behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * 
	 * @param tblBrhInfo
	 *            a transient instance containing new or updated state
	 */
	public void saveOrUpdate(com.huateng.po.base.TblBranchManageTrue tblBranchManageTrue);

	/**
	 * Update the persistent state associated with the given identifier. An
	 * exception is thrown if there is a persistent instance with the same
	 * identifier in the current session.
	 * 
	 * @param tblBrhInfo
	 *            a transient instance containing updated state
	 */
	public void update(com.huateng.po.base.TblBranchManageTrue tblBranchManageTrue);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an
	 * instance associated with the receiving Session or a transient instance
	 * with an identifier associated with existing persistent state.
	 * 
	 * @param id
	 *            the instance ID to be removed
	 */
	public void delete(java.lang.String id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an
	 * instance associated with the receiving Session or a transient instance
	 * with an identifier associated with existing persistent state.
	 * 
	 * @param tblBrhInfo
	 *            the instance to be removed
	 */
	public void delete(com.huateng.po.base.TblBranchManageTrue tblBranchManageTrue);

}
