package com.huateng.bo.impl.risk;

import java.io.File;
import java.util.List;
import com.huateng.bo.risk.T40214BO;
import com.huateng.common.Constants;
import com.huateng.common.Operator;
import com.huateng.dao.iface.risk.TblRiskMchtTranLimitDAO;
import com.huateng.po.risk.TblRiskMchtTranLimit;

public class T40214BOTarget implements T40214BO {

	private TblRiskMchtTranLimitDAO tblRiskMchtTranLimitDAO;
	
	public TblRiskMchtTranLimitDAO getTblRiskMchtTranLimitDAO() {
		return tblRiskMchtTranLimitDAO;
	}

	public void setTblRiskMchtTranLimitDAO(TblRiskMchtTranLimitDAO tblRiskMchtTranLimitDAO) {
		this.tblRiskMchtTranLimitDAO = tblRiskMchtTranLimitDAO;
	}

	public String add(TblRiskMchtTranLimit tblRiskMchtTranLimit)throws Exception {
		tblRiskMchtTranLimitDAO.save(tblRiskMchtTranLimit);
		return Constants.SUCCESS_CODE;
	}

	public void delete(String key) throws Exception {
		tblRiskMchtTranLimitDAO.delete(key);
	}

	public TblRiskMchtTranLimit get(String key) {
		return tblRiskMchtTranLimitDAO.get(key);
	}

	public String importFile(List<File> fileList, List<String> fileNameList,Operator operator) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String update(TblRiskMchtTranLimit tblRiskMchtTranLimit)throws Exception {
		tblRiskMchtTranLimitDAO.update(tblRiskMchtTranLimit);
		return Constants.SUCCESS_CODE;
	}
	
	public String updateList(List<TblRiskMchtTranLimit> tblRiskMchtTranLimitList)throws Exception {
		for(TblRiskMchtTranLimit temp : tblRiskMchtTranLimitList){
			tblRiskMchtTranLimitDAO.update(temp);
		}
		return Constants.SUCCESS_CODE;
	}

}
