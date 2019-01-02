package com.huateng.dao.impl.settle;

import java.util.List;

import com.huateng.dao._RootDAO;
import com.huateng.po.settle.TblMchtSumrzInf;


public class TblMchtSumrzInfDAO extends _RootDAO<com.huateng.po.settle.TblMchtSumrzInf> implements com.huateng.dao.iface.settle.TblMchtSumrzInfDAO {

	public TblMchtSumrzInfDAO () {}

	

	public Class<com.huateng.po.settle.TblMchtSumrzInf> getReferenceClass () {
		return com.huateng.po.settle.TblMchtSumrzInf.class;
	}

	public com.huateng.po.settle.TblMchtSumrzInf cast (Object object) {
		return (com.huateng.po.settle.TblMchtSumrzInf) object;
	}
	



	public TblMchtSumrzInf get(Integer seqNo) {
		// TODO Auto-generated method stub
		return (com.huateng.po.settle.TblMchtSumrzInf) get(getReferenceClass(), seqNo);
	}



	public void update(TblMchtSumrzInf tblMchtSumrzInf) {
		// TODO Auto-generated method stub
		super.update(tblMchtSumrzInf);
	}



	public List<TblMchtSumrzInf> getSumrInf(String sumrzBatch) {
		// TODO Auto-generated method stub
		return super.getHibernateTemplate().find("from TblMchtSumrzInf a where a.sumrzBatch = '" + sumrzBatch + "'");
	}

	
}