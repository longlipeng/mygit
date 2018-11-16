package com.huateng.bo.impl.risk;

import java.io.File;
import java.util.List;

import com.huateng.bo.risk.T40216BO;
import com.huateng.common.Constants;
import com.huateng.common.Operator;
import com.huateng.dao.iface.risk.TblRiskTermTranLimitDAO;
import com.huateng.po.risk.TblRiskTermTranLimit;

public class T40216BOTarget implements T40216BO {

	private TblRiskTermTranLimitDAO tblRiskTermTranLimitDAO;
	
	public TblRiskTermTranLimitDAO getTblRiskTermTranLimitDAO() {
		return tblRiskTermTranLimitDAO;
	}

	public void setTblRiskTermTranLimitDAO(	TblRiskTermTranLimitDAO tblRiskTermTranLimitDAO) {
		this.tblRiskTermTranLimitDAO = tblRiskTermTranLimitDAO;
	}

	public String add(TblRiskTermTranLimit tblRiskTermTranLimit)throws Exception {
		tblRiskTermTranLimitDAO.save(tblRiskTermTranLimit);
		return Constants.SUCCESS_CODE;
	}

	public void delete(String key) throws Exception {
		tblRiskTermTranLimitDAO.delete(key);
	}

	public TblRiskTermTranLimit get(String key) {
		return tblRiskTermTranLimitDAO.get(key);
	}

	public String update(TblRiskTermTranLimit tblRiskTermTranLimit)throws Exception {
		tblRiskTermTranLimitDAO.update(tblRiskTermTranLimit);
		return Constants.SUCCESS_CODE;
	}
	
	public String updateList(List<TblRiskTermTranLimit> tblRiskTermTranLimitList) {
		for(TblRiskTermTranLimit tblRiskTermTranLimit : tblRiskTermTranLimitList){
			tblRiskTermTranLimitDAO.update(tblRiskTermTranLimit);
		}
		return Constants.SUCCESS_CODE;
	}

	public String importFile(List<File> fileList, List<String> fileNameList,Operator operator) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
