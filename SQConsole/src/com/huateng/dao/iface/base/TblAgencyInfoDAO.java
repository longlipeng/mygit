package com.huateng.dao.iface.base;

import com.huateng.po.base.AgencyInfoPK;

public interface TblAgencyInfoDAO {
	public com.huateng.po.base.AgencyInfo get(AgencyInfoPK agencyInfoPK);

	public com.huateng.po.base.AgencyInfo load(AgencyInfoPK agencyInfoPK);

	public java.util.List<com.huateng.po.base.AgencyInfo> findAll();

	/**
	 * Persist the given transient instance, first assigning a generated
	 * identifier. (Or using the current value of the identifier property if the
	 * assigned generator is used.)
	 * 
	 * @param tblBrhInfo
	 *            a transient instance of a persistent class
	 * @return the class identifier
	 */
	public AgencyInfoPK save(
			com.huateng.po.base.AgencyInfo agencyInfo);

	/**
	 * Either save() or update() the given instance, depending upon the value of
	 * its identifier property. By default the instance is always saved. This
	 * behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * 
	 * @param tblBrhInfo
	 *            a transient instance containing new or updated state
	 */
	public void saveOrUpdate(com.huateng.po.base.AgencyInfo agencyInfo);

	/**
	 * Update the persistent state associated with the given identifier. An
	 * exception is thrown if there is a persistent instance with the same
	 * identifier in the current session.
	 * 
	 * @param tblBrhInfo
	 *            a transient instance containing updated state
	 */
	public void update(com.huateng.po.base.AgencyInfo agencyInfo);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an
	 * instance associated with the receiving Session or a transient instance
	 * with an identifier associated with existing persistent state.
	 * 
	 * @param id
	 *            the instance ID to be removed
	 */
	public void delete(AgencyInfoPK agencyInfoPK);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an
	 * instance associated with the receiving Session or a transient instance
	 * with an identifier associated with existing persistent state.
	 * 
	 * @param tblBrhInfo
	 *            the instance to be removed
	 */
	public void delete(com.huateng.po.base.AgencyInfo agencyInfo);

}
