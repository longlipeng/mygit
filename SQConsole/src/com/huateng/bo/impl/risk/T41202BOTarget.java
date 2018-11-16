package com.huateng.bo.impl.risk;

import java.util.List;

import com.huateng.bo.risk.T41202BO;
import com.huateng.common.Constants;
import com.huateng.dao.iface.risk.TblWhiteListTmpDAO;
import com.huateng.po.risk.TblWhiteListTmp;

public class T41202BOTarget implements T41202BO {

	private TblWhiteListTmpDAO tblWhiteListTmpDAO;
	
	
	public TblWhiteListTmpDAO getTblWhiteListTmpDAO() {
		return tblWhiteListTmpDAO;
	}

	public void setTblWhiteListTmpDAO(TblWhiteListTmpDAO tblWhiteListTmpDAO) {
		this.tblWhiteListTmpDAO = tblWhiteListTmpDAO;
	}

	/**************************/
	public TblWhiteListTmp get(String key) {
		return tblWhiteListTmpDAO.get(key);
	}

	public String add(TblWhiteListTmp tblWhiteListTmp) {
		 tblWhiteListTmpDAO.save(tblWhiteListTmp);
		 return Constants.SUCCESS_CODE;
	}

	public String delete(String key) {
		 tblWhiteListTmpDAO.delete(key);
		 return Constants.SUCCESS_CODE;
	}

	public String update(TblWhiteListTmp tblWhiteListTmp) {
		tblWhiteListTmpDAO.update(tblWhiteListTmp);
		return Constants.SUCCESS_CODE;
	}

	public String update(List<TblWhiteListTmp> list) {
	    for(TblWhiteListTmp t:list){
	    	tblWhiteListTmpDAO.update(t);
	    }
		
		/*for(int i=0;i<list.size();i++){
	    	tblWhiteListTmpDAO.update(list.get(i));
	    }*/
		return Constants.SUCCESS_CODE;
	}

}
