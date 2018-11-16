package com.huateng.bo.impl.rout;

import java.util.List;
import com.huateng.bo.rout.T11018BO;
import com.huateng.common.Constants;
import com.huateng.dao.iface.rout.TblTermChannelInfDAO;
import com.huateng.dao.iface.rout.TblTermChannelInfTmpDAO;
import com.huateng.po.rout.TblTermChannelInf;
import com.huateng.po.rout.TblTermChannelInfTmp;

public class T11018BOTarget implements T11018BO {

	private TblTermChannelInfDAO tblTermChannelInfDAO;
	private TblTermChannelInfTmpDAO tblTermChannelInfTmpDAO;
	
	
	
	public TblTermChannelInfTmpDAO getTblTermChannelInfTmpDAO() {
		return tblTermChannelInfTmpDAO;
	}

	public void setTblTermChannelInfTmpDAO(
			TblTermChannelInfTmpDAO tblTermChannelInfTmpDAO) {
		this.tblTermChannelInfTmpDAO = tblTermChannelInfTmpDAO;
	}

	public TblTermChannelInfDAO getTblTermChannelInfDAO() {
		return tblTermChannelInfDAO;
	}

	public void setTblTermChannelInfDAO(TblTermChannelInfDAO tblTermChannelInfDAO) {
		this.tblTermChannelInfDAO = tblTermChannelInfDAO;
	}

	public String add(TblTermChannelInf tblTermChannelInf) {
		tblTermChannelInfDAO.saveOrUpdate(tblTermChannelInf);
	    return Constants.SUCCESS_CODE;
	}
	
	public String add(TblTermChannelInfTmp tblTermChannelInfTmp) {
		tblTermChannelInfTmpDAO.saveOrUpdate(tblTermChannelInfTmp);
	    return Constants.SUCCESS_CODE;
	}

	public String delete(String id) {
		tblTermChannelInfDAO.delete(id);
		return Constants.SUCCESS_CODE;
	}

	public TblTermChannelInf get(String id) {
		return tblTermChannelInfDAO.get(id);
	}
	
	public TblTermChannelInfTmp getTmp(String id) {
		return tblTermChannelInfTmpDAO.getTmp(id);
	}

	public String update(TblTermChannelInf tblTermChannelInf) {
		tblTermChannelInfDAO.saveOrUpdate(tblTermChannelInf);
		return Constants.SUCCESS_CODE;
	}
	
	public String updateTmpOne(TblTermChannelInfTmp tblTermChannelInfTmp) {
		tblTermChannelInfTmpDAO.saveOrUpdate(tblTermChannelInfTmp);
		return Constants.SUCCESS_CODE;
	}
	
	public String update(TblTermChannelInfTmp tblTermChannelInfTmp) {
		tblTermChannelInfDAO.saveOrUpdate(tblTermChannelInfTmp);
		return Constants.SUCCESS_CODE;
	}

	public String update(List<TblTermChannelInf> tblTermChannelInfs) {
		for(TblTermChannelInf termChannel : tblTermChannelInfs){
			tblTermChannelInfDAO.update(termChannel);
		}
		return Constants.SUCCESS_CODE;
	}
	
	
	public String updateTmp(List<TblTermChannelInfTmp> tblTermChannelInfTmp) {
		for(TblTermChannelInfTmp termChannelTmp : tblTermChannelInfTmp){
			tblTermChannelInfTmpDAO.update(termChannelTmp);
		}
		return Constants.SUCCESS_CODE;
	}

	public void deleteall(List<Integer> list) {
		// TODO Auto-generated method stub
		for(Integer id:list){
			tblTermChannelInfDAO.delete(id.toString());
		}
	}

}
