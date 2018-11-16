package com.huateng.dao.impl.error;

import java.util.List;

import com.huateng.common.Constants;
import com.huateng.dao._RootDAO;
import com.huateng.po.error.BthErrRegistTxn;
import com.huateng.po.mchnt.CstMchtFeeInf;
import com.huateng.po.mchnt.CstMchtFeeInfPK;

public class BthErrRegistTxnDAO extends _RootDAO<com.huateng.po.error.BthErrRegistTxn> implements com.huateng.dao.iface.error.BthErrRegistTxnDAO{

	/* (non-Javadoc)
	 * @see com.huateng.dao._RootDAO#getReferenceClass()
	 */
	@Override
	protected Class getReferenceClass() {
		return BthErrRegistTxn.class;
	}

	/* (non-Javadoc)
	 * @see com.huateng.dao.iface.error.BthErrRegistTxnDAO#delete(java.lang.String)
	 */
	public void delete(String id) {
		super.delete((Object) load(id));
	}

	/* (non-Javadoc)
	 * @see com.huateng.dao.iface.error.BthErrRegistTxnDAO#delete(com.huateng.po.BthErrRegistTxn)
	 */
	public void delete(BthErrRegistTxn bthErrRegistTxn) {
		super.delete((Object) bthErrRegistTxn);
	}

	/* (non-Javadoc)
	 * @see com.huateng.dao.iface.error.BthErrRegistTxnDAO#findAll()
	 */
	public List<BthErrRegistTxn> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.huateng.dao.iface.error.BthErrRegistTxnDAO#get(java.lang.String)
	 */
	public BthErrRegistTxn get(String key) {
		return (BthErrRegistTxn) get(getReferenceClass(), key);
	}

	/* (non-Javadoc)
	 * @see com.huateng.dao.iface.error.BthErrRegistTxnDAO#load(java.lang.String)
	 */
	public BthErrRegistTxn load(String key) {
		return (BthErrRegistTxn) load(getReferenceClass(), key);
	}

	/* (non-Javadoc)
	 * @see com.huateng.dao.iface.error.BthErrRegistTxnDAO#save(com.huateng.po.BthErrRegistTxn)
	 */
	public String save(BthErrRegistTxn bthErrRegistTxn) {
		return  (String) super.save(bthErrRegistTxn);
	}

	/* (non-Javadoc)
	 * @see com.huateng.dao.iface.error.BthErrRegistTxnDAO#saveOrUpdate(com.huateng.po.BthErrRegistTxn)
	 */
	public void saveOrUpdate(BthErrRegistTxn bthErrRegistTxn) {
		super.saveOrUpdate(bthErrRegistTxn);
	}

	/* (non-Javadoc)
	 * @see com.huateng.dao.iface.error.BthErrRegistTxnDAO#update(com.huateng.po.BthErrRegistTxn)
	 */
	public String update(BthErrRegistTxn bthErrRegistTxn) {
		super.update(bthErrRegistTxn);
		return Constants.SUCCESS_CODE;
		
	}
//	public String update(List<BthErrRegistTxn> BthErrRegistTxnList){
//		 super.update(BthErrRegistTxnList);
//		 return  null;
//	}

	


}