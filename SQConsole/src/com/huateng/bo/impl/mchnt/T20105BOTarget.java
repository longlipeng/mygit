package com.huateng.bo.impl.mchnt;

import java.util.List;

import com.huateng.bo.mchnt.T20105BO;
import com.huateng.commquery.dao.ICommQueryDAO;

import com.huateng.dao.iface.mchnt.ITblMchtUpLoadAddBatchDAO;
import com.huateng.po.TblTermInfTmp;

import com.huateng.po.base.TblSignInf;
import com.huateng.po.mchnt.TblHisDiscAlgo2Tmp;
import com.huateng.po.mchnt.TblMchtBaseInf;
import com.huateng.po.mchnt.TblMchtBaseInfTmp;
import com.huateng.po.mchnt.TblMchtSettleInfTmp;
import com.huateng.po.mchnt.TblMchtSupp1Tmp;


public class T20105BOTarget implements T20105BO {
	private ICommQueryDAO commQueryDAO;

	private ITblMchtUpLoadAddBatchDAO tblMchtUpLoadAddBatchDAO;

	
	
	public int saveUploadForAddBatch(
			List<TblMchtBaseInfTmp> MchtBaseInfListAdd,
			List<TblMchtSupp1Tmp> MchtSupp1ListAdd,
			List<TblHisDiscAlgo2Tmp> HisDiscAlgo2ListAdd,
			List<TblMchtSettleInfTmp> MchtSettleInfTmpListAdd) throws Exception {
		return (Integer)tblMchtUpLoadAddBatchDAO.upLoadAddBatch(MchtBaseInfListAdd, MchtSupp1ListAdd, HisDiscAlgo2ListAdd, MchtSettleInfTmpListAdd);
	}
	
	public int saveForBFZBatch(List<TblTermInfTmp> TblTermInfTmpList)throws Exception{
		return (Integer)tblMchtUpLoadAddBatchDAO.upLoadBFZBatch(TblTermInfTmpList);
	}

	

	public ICommQueryDAO getCommQueryDAO() {
		return commQueryDAO;
	}

	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}

	


	public ITblMchtUpLoadAddBatchDAO getTblMchtUpLoadAddBatchDAO() {
		return tblMchtUpLoadAddBatchDAO;
	}



	public void setTblMchtUpLoadAddBatchDAO(
			ITblMchtUpLoadAddBatchDAO tblMchtUpLoadAddBatchDAO) {
		this.tblMchtUpLoadAddBatchDAO = tblMchtUpLoadAddBatchDAO;
	}
}
