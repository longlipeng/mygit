package com.huateng.dao.iface.error;

import java.util.List;

import com.huateng.po.error.BthCupErrTxn;
import com.huateng.po.error.BthCupErrTxnPK;

public interface BthCupErrTxnDAO {

	public BthCupErrTxn get(BthCupErrTxnPK key);

	public BthCupErrTxn load(BthCupErrTxnPK key);

	public List<BthCupErrTxn> findAll ();

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param tblCtlCardInf a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public String save(BthCupErrTxn bthCupErrTxn);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param tblCtlCardInf a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(BthCupErrTxn bthCupErrTxn);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblCtlCardInf a transient instance containing updated state
	 */
	public void update(BthCupErrTxn bthCupErrTxn);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(BthCupErrTxnPK id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param tblCtlCardInf the instance to be removed
	 */
	public void delete(BthCupErrTxn bthCupErrTxn);
}
