package com.huateng.bo.impl.mchnt;


import java.util.List;

import com.huateng.bo.mchnt.T20304BO;
import com.huateng.dao.iface.mchnt.CstMchtFeeInfDAO;
import com.huateng.po.mchnt.CstMchtFeeInf;
import com.huateng.po.mchnt.CstMchtFeeInfPK;
import com.huateng.po.mchnt.CstMchtFeeInfTmp;
import com.huateng.system.util.CommonFunction;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-6-15
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author
 * 
 * @version 1.0
 */
public class T20304BOTarget implements T20304BO {
	public String saveOrUpdate(CstMchtFeeInf cstMchtFeeInf) {
		cstMchtFeeInfDAO.saveOrUpdate(cstMchtFeeInf);
		return "00";
	}
	
	public String saveOrUpdate(CstMchtFeeInfTmp cstMchtFeeInf) {
		cstMchtFeeInfDAO.saveOrUpdate(cstMchtFeeInf);
		return "00";
	}

	public CstMchtFeeInf getMchtLimit(CstMchtFeeInfPK cstMchtFeeInfPK) {
		cstMchtFeeInfPK.setMchtCd(CommonFunction.fillString(cstMchtFeeInfPK.getMchtCd(), ' ', 15, true));
		return cstMchtFeeInfDAO.get(cstMchtFeeInfPK);
	}
	
	public CstMchtFeeInfTmp getMchtLimitTmp(CstMchtFeeInfPK cstMchtFeeInfPK) {
		cstMchtFeeInfPK.setMchtCd(CommonFunction.fillString(cstMchtFeeInfPK.getMchtCd(), ' ', 15, true));
		return cstMchtFeeInfDAO.getTmp(cstMchtFeeInfPK);
	}

	public String updateMchtLimit(CstMchtFeeInf cstMchtFeeInf) {
		cstMchtFeeInfDAO.update(cstMchtFeeInf);
		return "00";
	}
	
	public String updateMchtLimitTmp(CstMchtFeeInfTmp cstMchtFeeInf) {
		cstMchtFeeInfDAO.updateTmp(cstMchtFeeInf);
		return "00";
	}
	
	private CstMchtFeeInfDAO cstMchtFeeInfDAO;

	/**
	 * @return the cstMchtFeeInfDAO
	 */
	public CstMchtFeeInfDAO getCstMchtFeeInfDAO() {
		return cstMchtFeeInfDAO;
	}

	/**
	 * @param cstMchtFeeInfDAO the cstMchtFeeInfDAO to set
	 */
	public void setCstMchtFeeInfDAO(CstMchtFeeInfDAO cstMchtFeeInfDAO) {
		this.cstMchtFeeInfDAO = cstMchtFeeInfDAO;
	}

	public void delete(CstMchtFeeInfPK id) {
		cstMchtFeeInfDAO .delete(id);
		
	}
	public String update(List<CstMchtFeeInf> cstMchtFeeInfList) {
		for(CstMchtFeeInf tblOprInfo : cstMchtFeeInfList) {
			updateMchtLimit(tblOprInfo);
		}
		return "00";
	}
	
	public String updateTmp(List<CstMchtFeeInfTmp> cstMchtFeeInfList) {
		for(CstMchtFeeInfTmp tblOprInfo : cstMchtFeeInfList) {
			updateMchtLimitTmp(tblOprInfo);
		}
		return "00";
	}

}
