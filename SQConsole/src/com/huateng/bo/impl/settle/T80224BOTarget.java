package com.huateng.bo.impl.settle;

import java.util.List;

import com.huateng.bo.settle.T80224BO;
import com.huateng.common.Constants;
import com.huateng.dao.iface.settle.TblMchtSumrzInfDAO;
import com.huateng.po.settle.TblMchtSumrzInf;

public class T80224BOTarget implements T80224BO {
	
	private TblMchtSumrzInfDAO tblMchtSumrzInfDAO;

	
	public TblMchtSumrzInfDAO getTblMchtSumrzInfDAO() {
		return tblMchtSumrzInfDAO;
	}

	public void setTblMchtSumrzInfDAO(TblMchtSumrzInfDAO tblMchtSumrzInfDAO) {
		this.tblMchtSumrzInfDAO = tblMchtSumrzInfDAO;
	}

	public TblMchtSumrzInf get(Integer seqNo) {
		// TODO Auto-generated method stub
		return tblMchtSumrzInfDAO.get(seqNo);
	}

	public String update(List<TblMchtSumrzInf> list)  throws Exception{
		for(TblMchtSumrzInf tblMchtSumrzInf : list) {
			tblMchtSumrzInfDAO.update(tblMchtSumrzInf);
		}
		return Constants.SUCCESS_CODE;
	}

	public String update(TblMchtSumrzInf tblMchtSumrzInf)  throws Exception{
		tblMchtSumrzInfDAO.update(tblMchtSumrzInf);
		return Constants.SUCCESS_CODE;
	}

	public List<TblMchtSumrzInf> getSumrInf(String sumrzBatch) {
		// TODO Auto-generated method stub
		return tblMchtSumrzInfDAO.getSumrInf(sumrzBatch);
	}

	
}
