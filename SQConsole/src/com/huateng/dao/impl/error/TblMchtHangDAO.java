package com.huateng.dao.impl.error;

import com.huateng.dao._RootDAO;
import com.huateng.po.error.TBLTermHangPK;
import com.huateng.po.error.TblMchtHang;
import com.huateng.po.error.TBLTermHang;
import com.huateng.po.error.TblMchtHangPK;
import com.huateng.po.error.TblTransHang;
import com.huateng.po.error.TblTransHangPK;

public class TblMchtHangDAO extends _RootDAO<com.huateng.po.error.TblTransHang> implements com.huateng.dao.iface.error.TblMchtHangDAO{
	public Class<TblMchtHang> getReferenceClass () {
		return TblMchtHang.class;
	}

	public Class<TBLTermHang> getReferenceClass2 () {
		return TBLTermHang.class;
	}
	
	public Class<TblTransHang> getReferenceClass3 () {
		return TblTransHang.class;
	}
	/**
	 * Cast the object as a CstMchtFeeInf
	 */
	public TblMchtHang cast (Object object) {
		return (TblMchtHang) object;
	}

	public TBLTermHang cast2 (Object object) {
		return (TBLTermHang) object;
	}
	
	public TblTransHang cast3 (Object object) {
		return (TblTransHang) object;
	}
	
	public TblMchtHang load(String key)
		throws org.hibernate.HibernateException {
		return (TblMchtHang) load(getReferenceClass(), key);
	}
	
	public TBLTermHang load2(String key)
	     throws org.hibernate.HibernateException {
	return (TBLTermHang) load(getReferenceClass(), key);
}
	public TblTransHang load3(String key)
	    throws org.hibernate.HibernateException {
	return (TblTransHang) load(getReferenceClass(), key);
}
	
	public TblMchtHang get(TblMchtHangPK id)
		throws org.hibernate.HibernateException {
		return (TblMchtHang) get(getReferenceClass(), id);
	}
	
	public TBLTermHang get2(TBLTermHangPK id)
	    throws org.hibernate.HibernateException {
	return (TBLTermHang) get(getReferenceClass2(), id);
}
	public TblTransHang get3(TblTransHangPK key)
	throws org.hibernate.HibernateException {
	return (TblTransHang) get(getReferenceClass3(), key);
}


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param cstMchtFeeInf a transient instance of a persistent class
	 * @return the class identifier
	 */
	public void save(TBLTermHang tBLTermHang)
		throws org.hibernate.HibernateException {
		 super.save(tBLTermHang);
	}
	public void save(TblMchtHang tblMchtHang)
	  throws org.hibernate.HibernateException {
	 super.save(tblMchtHang);
}
	public void save(TblTransHang tblTransHang)
	    throws org.hibernate.HibernateException {
	 super.save(tblTransHang);
}
	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param cstMchtFeeInf a transient instance containing new or updated state
	 */
	public void saveOrUpdate(TblMchtHang tblMchtHang)
		throws org.hibernate.HibernateException {
		super.saveOrUpdate(tblMchtHang);
	}

	public void saveOrUpdate(TBLTermHang tBLTermHang)
	throws org.hibernate.HibernateException {
	super.saveOrUpdate(tBLTermHang);
}
	public void saveOrUpdate(TblTransHang tblTransHang)
	throws org.hibernate.HibernateException {
	super.saveOrUpdate(tblTransHang);
}
	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param cstMchtFeeInf a transient instance containing updated state
	 */
	public void update(TblMchtHang tblMchtHang)
		throws org.hibernate.HibernateException {
		super.update(tblMchtHang);
	}
	public void update(TBLTermHang tBLTermHang)
	    throws org.hibernate.HibernateException {
	    super.update(tBLTermHang);
}
	public void update(TblTransHang tblTransHang)
	    throws org.hibernate.HibernateException {
	     super.update(tblTransHang);
}
	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param cstMchtFeeInf the instance to be removed
	 */
	public void delete(TblMchtHang tblMchtHang)
		throws org.hibernate.HibernateException {
		super.delete((Object) tblMchtHang);
	}
	public void delete(TBLTermHang tBLTermHang)
	    throws org.hibernate.HibernateException {
	super.delete((Object) tBLTermHang);
}
	public void delete(TblTransHang tblTransHang)
	   throws org.hibernate.HibernateException {
	 super.delete((Object) tblTransHang);
}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param id the instance ID to be removed
	 */
	public void delete(String id)
		throws org.hibernate.HibernateException {
		super.delete((Object) load(id));
	}

}
