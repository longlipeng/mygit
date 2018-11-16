package com.huateng.dao.iface.mchnt;

import com.huateng.po.mchnt.TblDefTermInf;

public interface TblDefMchtInfDAO {
	
	public Class<com.huateng.po.mchnt.TblDefMchtInf> getReferenceClass ();
	
	/**
	 * Cast the object as a com.huateng.po.mchnt.TblMchtBaseInfTmp
	 */
	public com.huateng.po.mchnt.TblDefMchtInf cast (Object object);
	

	public com.huateng.po.mchnt.TblDefMchtInf load(java.lang.String key)
		throws org.hibernate.HibernateException;

	public com.huateng.po.mchnt.TblDefMchtInf get(java.lang.String key)
		throws org.hibernate.HibernateException;
	
	public java.lang.String save(com.huateng.po.mchnt.TblDefMchtInf tblDefMchtInf)
			throws org.hibernate.HibernateException;
	
	public void saveOrUpdate(com.huateng.po.mchnt.TblDefMchtInf tblDefMchtInf)
			throws org.hibernate.HibernateException;
	
	public void update(com.huateng.po.mchnt.TblDefMchtInf tblDefMchtInf)
			throws org.hibernate.HibernateException;
	
	public void delete(com.huateng.po.mchnt.TblDefMchtInf tblDefMchtInf)
			throws org.hibernate.HibernateException;
	
	public void delete(java.lang.String id)
			throws org.hibernate.HibernateException;

	public TblDefTermInf getTermInf(String recId)throws org.hibernate.HibernateException;

	public String saveTermInf(TblDefTermInf defTermInf) throws org.hibernate.HibernateException;

}
