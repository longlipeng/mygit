package com.huateng.bo.impl.base;

import com.huateng.bo.base.T11501BO;
import com.huateng.bo.base.T11521BO;
import com.huateng.common.Constants;
import com.huateng.dao.iface.base.TblAgentFeeDAO;
import com.huateng.po.base.TblAgentFee;
import com.huateng.po.base.TblAgentFee;
import com.huateng.po.base.TblAgentFeeRefuseInfo;
import com.huateng.po.base.TblAgentFeeTmp;

public class T11521BOTarget implements T11521BO {
    private TblAgentFeeDAO tblAgentFeeDAO;
    
	public TblAgentFeeDAO getTblAgentFeeDAO() {
		return tblAgentFeeDAO;
	}

	public void setTblAgentFeeDAO(TblAgentFeeDAO tblAgentFeeDAO) {
		this.tblAgentFeeDAO = tblAgentFeeDAO;
	}

	public TblAgentFeeTmp get(String uuid) {
		// TODO Auto-generated method stub
		return tblAgentFeeDAO.get(uuid);
	}
	
	public TblAgentFee getTrue(String uuid){
		return tblAgentFeeDAO.getTrue(uuid);
	}

	public String delete(String uuId) throws Exception{
		// TODO Auto-generated method stub
		tblAgentFeeDAO.delete(uuId);
		return Constants.SUCCESS_CODE;
	}
	
	public String deleteTmp(String idkey) throws Exception{
		// TODO Auto-generated method stub
		tblAgentFeeDAO.deleteTmp(idkey);
		return Constants.SUCCESS_CODE;
	}

	public String add(TblAgentFee tblAgentFee) throws Exception {
		tblAgentFeeDAO.save(tblAgentFee);
		return Constants.SUCCESS_CODE;
	}
	
	public String add(TblAgentFeeRefuseInfo tblAgentFeeRefuseInfo) throws Exception {
		tblAgentFeeDAO.save(tblAgentFeeRefuseInfo);
		return Constants.SUCCESS_CODE;
	}
	
	public String add(TblAgentFeeTmp tblAgentFeeTmp) {
		tblAgentFeeDAO.save(tblAgentFeeTmp);
		return Constants.SUCCESS_CODE;
	}

	public String update(TblAgentFee tblAgentFee) throws Exception {
		tblAgentFeeDAO.update(tblAgentFee);
		return Constants.SUCCESS_CODE;
	}
	
	public String update(TblAgentFeeTmp tblAgentFeeTmp) throws Exception {
		tblAgentFeeDAO.update(tblAgentFeeTmp);
		return Constants.SUCCESS_CODE;
	}

	public String saveOrUpdate(TblAgentFeeTmp tblAgentFeeTmp) throws Exception {
		tblAgentFeeDAO.saveOrUpdate(tblAgentFeeTmp);
		return Constants.SUCCESS_CODE;
	}

	public String saveOrUpdate(TblAgentFee tblAgentFee) throws Exception {
		tblAgentFeeDAO.saveOrUpdate(tblAgentFee);
		return Constants.SUCCESS_CODE;
	}


}
