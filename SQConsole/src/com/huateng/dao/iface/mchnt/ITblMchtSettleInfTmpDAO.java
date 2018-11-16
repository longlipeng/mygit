package com.huateng.dao.iface.mchnt;

import com.huateng.po.mchnt.TblMchtBaseBusiRange;
import com.huateng.po.mchnt.TblMchtSettleInfPK;
import com.huateng.po.mchnt.TblMchtSettleInfTmp;
import com.huateng.po.mchnt.TblMchtSupp1;
import com.huateng.po.mchnt.TblMchtSupp1Tmp;

public interface ITblMchtSettleInfTmpDAO {

	// query name references
	public Class<TblMchtSettleInfTmp> getReferenceClass ();

	/**
	 * Cast the object as a TblMchtSettleInfTmp
	 */
	public TblMchtSettleInfTmp cast (Object object);

	public TblMchtSettleInfTmp load(TblMchtSettleInfPK key)
		throws org.hibernate.HibernateException;

	public TblMchtSettleInfTmp get(TblMchtSettleInfPK key)
		throws org.hibernate.HibernateException;

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param tblMchtSettleInfTmp a transient instance of a persistent class
	 * @return the class identifier
	 */
	public TblMchtSettleInfPK save(TblMchtSettleInfTmp tblMchtSettleInfTmp)
		throws org.hibernate.HibernateException;

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param tblMchtSettleInfTmp a transient instance containing new or updated state
	 */
	public void saveOrUpdate(TblMchtSettleInfTmp tblMchtSettleInfTmp)
		throws org.hibernate.HibernateException;

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblMchtSettleInfTmp a transient instance containing updated state
	 */
	public void update(TblMchtSettleInfTmp tblMchtSettleInfTmp)
		throws org.hibernate.HibernateException;

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param tblMchtSettleInfTmp the instance to be removed
	 */
	public void delete(TblMchtSettleInfTmp tblMchtSettleInfTmp)
		throws org.hibernate.HibernateException;

	//20120906 add
	public void delete(TblMchtSupp1Tmp supp1Tmp) throws org.hibernate.HibernateException;
	
	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param id the instance ID to be removed
	 */
	public void delete(TblMchtSettleInfPK id)
		throws org.hibernate.HibernateException;

	public String save(TblMchtSupp1Tmp supp1Tmp);
	
	/**
	 * 经营范围
	 * @param tblMchtBaseBusiRange
	 * @return
	 */
	public String save(TblMchtBaseBusiRange tblMchtBaseBusiRange);
	
	public TblMchtSupp1Tmp getSupp1Tmp(String mchtId);

	public void saveOrUpdate(TblMchtSupp1Tmp supp1Tmp);

	public TblMchtSupp1 getSupp1(String mchntId);

	public void saveOrUpdate(TblMchtSupp1 supp);

}