package com.huateng.bo.impl.pos;

import java.util.List;

import com.huateng.bo.pos.T30107BO;
import com.huateng.dao.iface.pos.CstTermFeeInTrueDAO;
import com.huateng.po.pos.CstTermFeeInTrue;
import com.huateng.po.pos.CstTermFeePK;

public class T30107BOTarget implements T30107BO{
	private CstTermFeeInTrueDAO cstTermFeeInTrueDAO;
	public CstTermFeeInTrueDAO getCstTermFeeInTrueDAO() {
		return cstTermFeeInTrueDAO;
	}

	public void setCstTermFeeInTrueDAO(CstTermFeeInTrueDAO cstTermFeeInTrueDAO) {
		this.cstTermFeeInTrueDAO = cstTermFeeInTrueDAO;
	}

	/**
	 * @return the cstMchtFeeInfDAO
	 */
	
	public String add(CstTermFeeInTrue cstTermFeeInTrue) {
		cstTermFeeInTrueDAO.save(cstTermFeeInTrue);
		return "00";
	}

	public CstTermFeeInTrue get(CstTermFeePK cstTermFeePK) {
		return cstTermFeeInTrueDAO.get(cstTermFeePK);
	}

	public String update(CstTermFeeInTrue cstTermFeeInTrue) {
		cstTermFeeInTrueDAO.update(cstTermFeeInTrue);
		return "00";
	}

	public void delete(CstTermFeePK id) {
		cstTermFeeInTrueDAO .delete(id);
		
	}
	public String update(List<CstTermFeeInTrue> cstTermFeeInTrueList) {
		for(CstTermFeeInTrue tblOprInfo : cstTermFeeInTrueList) {
			update(tblOprInfo);
		}
		return "00";
	}
	
}
