/**
 * 
 */
package com.huateng.dao.impl.error;

import java.util.List;

import com.huateng.dao._RootDAO;
import com.huateng.po.error.ManualReturn;
import com.huateng.po.error.TblAlgoDtl;

/**
 * @author zoujunqing
 *
 */
public class TblManualReturnDao extends _RootDAO<TblAlgoDtl> implements 
		com.huateng.dao.iface.error.TblManualReturnDao{

	@Override
	protected Class getReferenceClass() {
		 
		return ManualReturn.class;
	}

	public void saveOrUpdate(ManualReturn manu) {
		super.saveOrUpdate(manu);
	}

	public ManualReturn get(String id) {
		return (ManualReturn)super.get(getReferenceClass(), id);
	}

	public void saveupdate(ManualReturn manualReturn) {
		// TODO Auto-generated method stub
		super.update(manualReturn);
	}

	public void delete(String id) {
		super.delete(load(ManualReturn.class, id));
	}

}
