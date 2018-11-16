package com.huateng.dao.iface.mchnt;

import java.util.List;

import com.huateng.po.mchnt.TblMchtBaseBusiRange;
import com.huateng.po.mchnt.TblMchtBeneficiaryInf;
import com.huateng.po.mchnt.TblMchtBeneficiaryInfTmp;

public interface ITblMchtBaseInfTmpDAO {



	// query name references




	public Class<com.huateng.po.mchnt.TblMchtBaseInfTmp> getReferenceClass ();


	/**
	 * Cast the object as a com.huateng.po.mchnt.TblMchtBaseInfTmp
	 */
	public com.huateng.po.mchnt.TblMchtBaseInfTmp cast (Object object);


	public com.huateng.po.mchnt.TblMchtBaseInfTmp load(java.lang.String key)
		throws org.hibernate.HibernateException;

	public com.huateng.po.mchnt.TblMchtBaseInfTmp get(java.lang.String key)
		throws org.hibernate.HibernateException;

	/**
	 * 临时表
	 * 根据商户编号查询受益人信息
	 * @param key
	 * @return
	 * @throws org.hibernate.HibernateException
	 */
	public List<TblMchtBeneficiaryInfTmp> getBeneficiaryTmp(String mchtNo);
	
	/**
	 * 正式表
	 * 根据商户编号查询受益人信息
	 * @param key
	 * @return
	 * @throws org.hibernate.HibernateException
	 */
	public List<TblMchtBeneficiaryInf> getBeneficiary(String mchtNo);
	
	/**
	 * 正式表
	 * 新增或修改
	 */
	public void saveOrUpdate(TblMchtBeneficiaryInf tblMchtBeneficiaryInf);
	
	/**
	 * 临时表
	 * 新增或修改
	 */
	public void saveOrUpdate(TblMchtBeneficiaryInfTmp tblMchtBeneficiaryInfTmp);
	
	/**
	 * 修改正式表id
	 */
	public void beneficiaryUpdate(TblMchtBeneficiaryInf tblMchtBeneficiaryInf);
	
	/**
	 * 修改临时表id
	 */
	public void beneficiaryUpdateTmp(TblMchtBeneficiaryInfTmp tblMchtBeneficiaryInfTmp);
	
	/**
	 * 根据id查询数据
	 * @return
	 */
	public TblMchtBeneficiaryInf getBeneficiaryInf(String beneficiaryId);
	
	/**
	 * 删除
	 * @return
	 */
	public void beneficiaryDel(String beneficiaryId);
	
	/**
	 * 新增
	 * @param tblMchtBeneficiaryInfTmp
	 * @return
	 */
	public void beneficiaryAdd(TblMchtBeneficiaryInfTmp tblMchtBeneficiaryInfTmp);
	
	/**
	 * 新增
	 * 临时表
	 * @param tblMchtBeneficiaryInf
	 * @return
	 */
	public void beneficiaryAdd1(TblMchtBeneficiaryInf tblMchtBeneficiaryInf);
	
	/**
	 * 根据mchtId查询数据
	 * @param mchtId
	 * @return
	 */
	public TblMchtBaseBusiRange getBaseBusiRange(String mchtId);
	
	/**
	 * 新增
	 * @param tblMchtBaseBusiRange
	 * @return
	 */
	public void addBaseBusiRange(TblMchtBaseBusiRange tblMchtBaseBusiRange);
	
	/**
	 * 修改
	 * @param tblMchtBaseBusiRange
	 * @return
	 */
	public void upBaseBusiRange(TblMchtBaseBusiRange tblMchtBaseBusiRange);
	
	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param tblMchtBaseInfTmp a transient instance of a persistent class
	 * @return the class identifier
	 */
	public java.lang.String save(com.huateng.po.mchnt.TblMchtBaseInfTmp tblMchtBaseInfTmp)
		throws org.hibernate.HibernateException;

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param tblMchtBaseInfTmp a transient instance containing new or updated state
	 */
	public void saveOrUpdate(com.huateng.po.mchnt.TblMchtBaseInfTmp tblMchtBaseInfTmp)
		throws org.hibernate.HibernateException;


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblMchtBaseInfTmp a transient instance containing updated state
	 */
	public void update(com.huateng.po.mchnt.TblMchtBaseInfTmp tblMchtBaseInfTmp)
		throws org.hibernate.HibernateException;

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param tblMchtBaseInfTmp the instance to be removed
	 */
	public void delete(com.huateng.po.mchnt.TblMchtBaseInfTmp tblMchtBaseInfTmp)
		throws org.hibernate.HibernateException;

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.String id)
		throws org.hibernate.HibernateException;


}