package com.huateng.bo.impl.pos;

import java.util.List;

import com.huateng.bo.pos.T30106BO;
import com.huateng.dao.iface.pos.CstTermFeeInDAO;
import com.huateng.po.pos.CstTermFeeIn;
import com.huateng.po.pos.CstTermFeePK;


public class T30106BOTarget implements T30106BO{
	private CstTermFeeInDAO cstTermFeeInDAO;

	public CstTermFeeInDAO getCstTermFeeInDAO() {
		return cstTermFeeInDAO;
	}

	public void setCstTermFeeInDAO(CstTermFeeInDAO cstTermFeeInDAO) {
		this.cstTermFeeInDAO = cstTermFeeInDAO;
	}

	/**
	 * @return the cstMchtFeeInfDAO
	 */
	
	public String add(CstTermFeeIn cstTermFeeIn) {
		cstTermFeeInDAO.save(cstTermFeeIn);
		return "00";
	}

	public CstTermFeeIn get(CstTermFeePK cstTermFeePK) {
		return cstTermFeeInDAO.get(cstTermFeePK);
	}

	public String update(CstTermFeeIn cstTermFeeIn) {
		cstTermFeeInDAO.update(cstTermFeeIn);
		return "00";
	}
	
	

	public void delete(CstTermFeePK id) {
		cstTermFeeInDAO .delete(id);
		
	}
	public String update(List<CstTermFeeIn> cstTermFeeInList) {
		for(CstTermFeeIn tblOprInfo : cstTermFeeInList) {
			update(tblOprInfo);
		}
		return "00";
	}
	
}
