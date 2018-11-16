package com.huateng.dao.impl.mchnt;

import java.util.List;

import org.hibernate.HibernateException;

import com.huateng.dao._RootDAO;
import com.huateng.dao.iface.mchnt.ITblMchtBaseInfTmpDAO;
import com.huateng.po.mchnt.TblMchtBaseBusiRange;
import com.huateng.po.mchnt.TblMchtBeneficiaryInf;
import com.huateng.po.mchnt.TblMchtBeneficiaryInfTmp;

public class TblMchtBaseInfTmpDAO extends _RootDAO<com.huateng.po.mchnt.TblMchtBaseInfTmp> implements ITblMchtBaseInfTmpDAO{

	// query name references
	public Class<com.huateng.po.mchnt.TblMchtBaseInfTmp> getReferenceClass () {
		return com.huateng.po.mchnt.TblMchtBaseInfTmp.class;
	}

	/**
	 * Cast the object as a com.huateng.po.mchnt.TblMchtBaseInfTmp
	 */
	public com.huateng.po.mchnt.TblMchtBaseInfTmp cast (Object object) {
		return (com.huateng.po.mchnt.TblMchtBaseInfTmp) object;
	}

	public com.huateng.po.mchnt.TblMchtBaseInfTmp load(java.lang.String key)
		throws org.hibernate.HibernateException {
		return (com.huateng.po.mchnt.TblMchtBaseInfTmp) load(getReferenceClass(), key);
	}

	public com.huateng.po.mchnt.TblMchtBaseInfTmp get(java.lang.String key)
		throws org.hibernate.HibernateException {
		return (com.huateng.po.mchnt.TblMchtBaseInfTmp) get(getReferenceClass(), key);
	}

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param tblMchtBaseInfTmp a transient instance of a persistent class
	 * @return the class identifier
	 */
	public java.lang.String save(com.huateng.po.mchnt.TblMchtBaseInfTmp tblMchtBaseInfTmp)
		throws org.hibernate.HibernateException {
		return (java.lang.String) super.save(tblMchtBaseInfTmp);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param tblMchtBaseInfTmp a transient instance containing new or updated state
	 */
	public void saveOrUpdate(com.huateng.po.mchnt.TblMchtBaseInfTmp tblMchtBaseInfTmp)
		throws org.hibernate.HibernateException {
		super.saveOrUpdate(tblMchtBaseInfTmp);
	}

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblMchtBaseInfTmp a transient instance containing updated state
	 */
	public void update(com.huateng.po.mchnt.TblMchtBaseInfTmp tblMchtBaseInfTmp)
		throws org.hibernate.HibernateException {
		super.update(tblMchtBaseInfTmp);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param tblMchtBaseInfTmp the instance to be removed
	 */
	public void delete(com.huateng.po.mchnt.TblMchtBaseInfTmp tblMchtBaseInfTmp)
		throws org.hibernate.HibernateException {
		super.delete((Object) tblMchtBaseInfTmp);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.String id)
		throws org.hibernate.HibernateException {
		super.delete((Object) load(id));
	}

	/**
	 * 临时表
	 * 根据商户编号查询受益人信息
	 */
	public List<TblMchtBeneficiaryInfTmp> getBeneficiaryTmp(String mchtNo) 
		throws org.hibernate.HibernateException {
		// TODO Auto-generated method stub
		return super.getHibernateTemplate().find("from TblMchtBeneficiaryInfTmp where MCHT_NO = " + mchtNo + " order by BENEFICIARY_ID");
	}
	
	/**
	 * 正式表
	 * 根据商户编号查询受益人信息
	 */
	public List<TblMchtBeneficiaryInf> getBeneficiary(String mchtNo) 
		throws org.hibernate.HibernateException {
		// TODO Auto-generated method stub
		return super.getHibernateTemplate().find("from TblMchtBeneficiaryInf where MCHT_NO = " + mchtNo + " order by BENEFICIARY_ID");
	}

	/**
	 * 正式表
	 * 新增或修改
	 */
	public void saveOrUpdate(TblMchtBeneficiaryInf tblMchtBeneficiaryInf) {
		// TODO Auto-generated method stub
		//super.getHibernateTemplate().clear();
		//tblMchtBeneficiaryInf = (TblMchtBeneficiaryInf) super.getHibernateTemplate().merge(tblMchtBeneficiaryInf);
		//super.getHibernateTemplate().refresh(tblMchtBeneficiaryInf);
		
		List<TblMchtBeneficiaryInf> list = super.getHibernateTemplate().find("from TblMchtBeneficiaryInf where BENEFICIARY_ID = " + tblMchtBeneficiaryInf.getBeneficiaryId() + " order by BENEFICIARY_ID");
		
		if(list==null || list.size() == 0){
			super.getHibernateTemplate().clear();
			super.save(tblMchtBeneficiaryInf);
		}else{
			super.update(tblMchtBeneficiaryInf);
		}
		//super.saveOrUpdate(tblMchtBeneficiaryInf);
	}
	
	/**
	 * 临时表
	 * 新增或修改
	 */
	public void saveOrUpdate(TblMchtBeneficiaryInfTmp tblMchtBeneficiaryInfTmp) {
		// TODO Auto-generated method stub
		List<TblMchtBeneficiaryInfTmp> list = super.getHibernateTemplate().find("from TblMchtBeneficiaryInfTmp where BENEFICIARY_ID = " + tblMchtBeneficiaryInfTmp.getBeneficiaryId() + " order by BENEFICIARY_ID");
		
		if(list==null || list.size() == 0){
			super.getHibernateTemplate().clear();
			super.save(tblMchtBeneficiaryInfTmp);
		}else{
			super.update(tblMchtBeneficiaryInfTmp);
		}
		//super.saveOrUpdate(tblMchtBeneficiaryInfTmp);
	}
	
	/**
	 * 修改正式表id
	 */
	public void beneficiaryUpdate(TblMchtBeneficiaryInf tblMchtBeneficiaryInf) {
		// TODO Auto-generated method stub
		super.update(tblMchtBeneficiaryInf);
	}
	
	/**
	 * 修改临时表id
	 */
	public void beneficiaryUpdateTmp(TblMchtBeneficiaryInfTmp tblMchtBeneficiaryInfTmp){
		super.update(tblMchtBeneficiaryInfTmp);
	}
	
	/**
	 * 根据id查询数据
	 * @return
	 */
	public TblMchtBeneficiaryInf getBeneficiaryInf(String beneficiaryId){
		return (TblMchtBeneficiaryInf) super.get(TblMchtBeneficiaryInf.class, beneficiaryId);
	}
	
	/**
	 * 删除
	 * @param key
	 * @return
	 */
	public TblMchtBeneficiaryInfTmp loadDel(String key){
		return (TblMchtBeneficiaryInfTmp) super.load(TblMchtBeneficiaryInfTmp.class, key);
	}
	
	/**
	 * 删除
	 * @return
	 */
	public void beneficiaryDel(String beneficiaryId){
		super.delete(loadDel(beneficiaryId));
	}
	
	/**
	 * 新增
	 * @param tblMchtBeneficiaryInfTmp
	 * @return
	 */
	public void beneficiaryAdd(TblMchtBeneficiaryInfTmp tblMchtBeneficiaryInfTmp){
		super.save(tblMchtBeneficiaryInfTmp);
	}
	
	/**
	 * 新增
	 * 临时表
	 * @param tblMchtBeneficiaryInf
	 * @return
	 */
	public void beneficiaryAdd1(TblMchtBeneficiaryInf tblMchtBeneficiaryInf){
		super.save(tblMchtBeneficiaryInf);
	}

	/**
	 * 根据mchtId查询数据
	 */
	public TblMchtBaseBusiRange getBaseBusiRange(String mchtId) {
		// TODO Auto-generated method stub
		return (TblMchtBaseBusiRange) super.get(TblMchtBaseBusiRange.class, mchtId);
	}

	public void addBaseBusiRange(TblMchtBaseBusiRange tblMchtBaseBusiRange) {
		// TODO Auto-generated method stub
		super.save(tblMchtBaseBusiRange);
	}

	public void upBaseBusiRange(TblMchtBaseBusiRange tblMchtBaseBusiRange) {
		// TODO Auto-generated method stub
		super.update(tblMchtBaseBusiRange);
	}
	
	
	

}