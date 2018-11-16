package com.huateng.dao.iface.base;

import java.util.List;
import com.huateng.po.base.TblRspCodeMap;

public interface TblRspCodeMapDAO {
	public Class<com.huateng.po.base.TblRspCodeMap> getReferenceClass ();

	/**
	 * Cast the object as a com.huateng.po.base.TblEmvPara
	 */
	public com.huateng.po.base.TblRspCodeMap cast (Object object) ;


	public com.huateng.po.base.TblRspCodeMap load(com.huateng.po.base.TblRspCodeMapPK key);

	public com.huateng.po.base.TblRspCodeMap get(com.huateng.po.base.TblRspCodeMapPK key);

	public java.util.List<com.huateng.po.base.TblRspCodeMap> loadAll();

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param tblEmvPara a transient instance of a persistent class
	 * @return the class identifier
	 */
	public com.huateng.po.base.TblRspCodeMapPK save(com.huateng.po.base.TblRspCodeMap tblRspCodeMap);
	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param tblEmvPara a transient instance containing new or updated state
	 */
	public void saveOrUpdate(com.huateng.po.base.TblRspCodeMap tblRspCodeMap);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblEmvPara a transient instance containing updated state
	 */
	public void update(com.huateng.po.base.TblRspCodeMap tblRspCodeMap);
	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param tblEmvPara the instance to be removed
	 */
	public void delete(com.huateng.po.base.TblRspCodeMap tblRspCodeMap);
	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param id the instance ID to be removed
	 */
	public void delete(com.huateng.po.base.TblRspCodeMapPK id);

}
