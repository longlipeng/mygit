package com.huateng.bo.impl.base;

import com.huateng.bo.base.T11501BO;
import com.huateng.common.Constants;
import com.huateng.dao.iface.base.TblAgentInfoDAO;
import com.huateng.po.base.TblAgentInfo;

public class T11501BOTarget implements T11501BO {
    private TblAgentInfoDAO tblAgentInfoDAO;
    
	public TblAgentInfoDAO getTblAgentInfoDAO() {
		return tblAgentInfoDAO;
	}

	public void setTblAgentInfoDAO(TblAgentInfoDAO tblAgentInfoDAO) {
		this.tblAgentInfoDAO = tblAgentInfoDAO;
	}

	public TblAgentInfo get(String agentNo) {
		// TODO Auto-generated method stub
		return tblAgentInfoDAO.get(agentNo);
	}

	public String delete(String agentNo) throws Exception{
		// TODO Auto-generated method stub
		tblAgentInfoDAO.delete(agentNo);
		return Constants.SUCCESS_CODE;
	}

	public String add(TblAgentInfo tblAgentInfo) throws Exception {
		tblAgentInfoDAO.save(tblAgentInfo);
		return Constants.SUCCESS_CODE;
	}

	public String update(TblAgentInfo tblAgentInfo) throws Exception {
		tblAgentInfoDAO.update(tblAgentInfo);
		return Constants.SUCCESS_CODE;
	}


}
