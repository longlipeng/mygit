package com.huateng.bo.impl.risk;

import java.util.List;

import com.huateng.bo.risk.T40402BO;
import com.huateng.common.Constants;
import com.huateng.po.risk.RiskBefore;
import com.huateng.dao.iface.risk.RiskBeforeDAO;
public class T40402BOTarget implements T40402BO{
	public RiskBeforeDAO getRiskBeforeDAO() {
		return riskBeforeDAO;
	}

	public void setRiskBeforeDAO(RiskBeforeDAO riskBeforeDAO) {
		this.riskBeforeDAO = riskBeforeDAO;
	}

	private RiskBeforeDAO riskBeforeDAO;
	public String add(RiskBefore riskBefore) {
		// TODO Auto-generated method stub
		riskBeforeDAO.save(riskBefore);
		return Constants.SUCCESS_CODE;
	}

	public String delete(RiskBefore riskBefore) {
		// TODO Auto-generated method stub
		riskBeforeDAO.delete(riskBefore);
		return Constants.SUCCESS_CODE;
	}

	public String delete(String Id) {
		// TODO Auto-generated method stub
		this.riskBeforeDAO.delete(Id);;
		return Constants.SUCCESS_CODE;
	}

	public RiskBefore get(String Id) {
		// TODO Auto-generated method stub
		return this.riskBeforeDAO.get(Id);
	}

	public String update(List<RiskBefore> riskBeforeList) {
		// TODO Auto-generated method stub
		for(RiskBefore riskBefore:riskBeforeList){
			riskBeforeDAO.update(riskBefore);
		}
		return Constants.SUCCESS_CODE;
	}
	

}
