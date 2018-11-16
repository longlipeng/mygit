package com.huateng.bo.impl.risk;

import com.huateng.bo.risk.T40206BO;
import com.huateng.common.Constants;
import com.huateng.dao.iface.risk.TblCtlCardInfDAO;
import com.huateng.dao.iface.risk.TblRiskRefuseDAO;
import com.huateng.po.TblCtlCardInf;
import com.huateng.po.risk.TblRiskRefuse;

public class T40206BOTarget implements T40206BO {

	private TblCtlCardInfDAO tblCtlCardInfDAO;
	private TblRiskRefuseDAO tblRiskRefuseDAO;
	
	public TblRiskRefuseDAO getTblRiskRefuseDAO() {
		return tblRiskRefuseDAO;
	}

	public void setTblRiskRefuseDAO(TblRiskRefuseDAO tblRiskRefuseDAO) {
		this.tblRiskRefuseDAO = tblRiskRefuseDAO;
	}

	public String acceptAdd(TblCtlCardInf tblCtlCardInf) throws Exception {//新增待审核的卡黑名单已通过
		tblCtlCardInfDAO.update(tblCtlCardInf);
		return Constants.SUCCESS_CODE;
	}

	public String acceptDelete(TblCtlCardInf tblCtlCardInf) throws Exception {//删除待审核的卡黑名单已通过
		tblCtlCardInfDAO.update(tblCtlCardInf);
		return Constants.SUCCESS_CODE;
	}

	public String refuseAdd(TblCtlCardInf tblCtlCardInf) throws Exception {//新增待审核的卡黑名单已拒绝
		tblCtlCardInfDAO.update(tblCtlCardInf);
		return Constants.SUCCESS_CODE;
	}

	public String refuseDelete(TblCtlCardInf tblCtlCardInf) throws Exception {//删除待审核的卡黑名单已拒绝
		tblCtlCardInfDAO.update(tblCtlCardInf);
		return Constants.SUCCESS_CODE;
	}

	public TblCtlCardInfDAO getTblCtlCardInfDAO() {
		return tblCtlCardInfDAO;
	}

	public void setTblCtlCardInfDAO(TblCtlCardInfDAO tblCtlCardInfDAO) {
		this.tblCtlCardInfDAO = tblCtlCardInfDAO;
	}

	public TblCtlCardInf get(String id) throws Exception{
		return tblCtlCardInfDAO.get(id);
	}

	public String delete(String id) throws Exception {
		this.tblCtlCardInfDAO.delete(id);
		return Constants.SUCCESS_CODE;
	}

	public String saveRefuseInfo(TblRiskRefuse riskRefuse) throws Exception {
		tblRiskRefuseDAO.save(riskRefuse);
		return Constants.SUCCESS_CODE;
	}
}
