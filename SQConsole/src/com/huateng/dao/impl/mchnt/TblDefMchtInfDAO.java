package com.huateng.dao.impl.mchnt;

import org.hibernate.HibernateException;

import com.huateng.dao._RootDAO;
import com.huateng.po.mchnt.TblDefMchtInf;
import com.huateng.po.mchnt.TblDefTermInf;


public class TblDefMchtInfDAO extends
		_RootDAO<com.huateng.po.mchnt.TblDefMchtInf> implements
		com.huateng.dao.iface.mchnt.TblDefMchtInfDAO {

	public TblDefMchtInf cast(Object object) {
		// TODO Auto-generated method stub
		return (com.huateng.po.mchnt.TblDefMchtInf) object;
	}

	public TblDefMchtInf load(String key) throws HibernateException {
		// TODO Auto-generated method stub
		return (com.huateng.po.mchnt.TblDefMchtInf) load(getReferenceClass(), key);
	}

	public TblDefMchtInf get(String key) throws HibernateException {
		// TODO Auto-generated method stub
		return (com.huateng.po.mchnt.TblDefMchtInf) get(getReferenceClass(), key);
	}

	public String save(TblDefMchtInf tblDefMchtInf) throws HibernateException {
		// TODO Auto-generated method stub
		return (java.lang.String) super.save(tblDefMchtInf);
	}

	public void saveOrUpdate(TblDefMchtInf tblDefMchtInf)
			throws HibernateException {
		super.saveOrUpdate(tblDefMchtInf);
		
	}

	public void update(TblDefMchtInf tblDefMchtInf) throws HibernateException {
		// TODO Auto-generated method stub
		super.update(tblDefMchtInf);
		
	}

	public void delete(TblDefMchtInf tblDefMchtInf) throws HibernateException {
		// TODO Auto-generated method stub
		super.delete((Object) tblDefMchtInf);
		
	}

	public void delete(String id) throws HibernateException {
		// TODO Auto-generated method stub
		super.delete((Object) load(id));
		
	}

	public Class<com.huateng.po.mchnt.TblDefMchtInf> getReferenceClass () {
		return com.huateng.po.mchnt.TblDefMchtInf.class;
	}

	/**
	 * 获取进件终端信息
	 */
	public TblDefTermInf getTermInf(String recId) throws HibernateException {
		// TODO Auto-generated method stub
		return (com.huateng.po.mchnt.TblDefTermInf) get(com.huateng.po.mchnt.TblDefTermInf.class, recId);
	}

	/*
	 * 更新终端信息
	 * @see com.huateng.dao.iface.mchnt.TblDefMchtInfDAO#saveTermInf(com.huateng.po.mchnt.TblDefTermInf)
	 */
	public String saveTermInf(TblDefTermInf defTermInf) throws HibernateException {
		return (java.lang.String) super.save(defTermInf);
		
	}


}
