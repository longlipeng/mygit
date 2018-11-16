package com.huateng.bo.impl.mchnt;

import java.util.List;

import com.huateng.bo.mchnt.T20706BO;
import com.huateng.common.Constants;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.dao.iface.mchnt.TblHisDiscAlgo1DAO;
import com.huateng.dao.iface.mchnt.TblHisDiscAlgoDAO;
import com.huateng.dao.iface.mchnt.TblInfDiscCdDAO;
import com.huateng.po.mchnt.TblHisDiscAlgo1;
import com.huateng.po.mchnt.TblHisDiscAlgoTmp;
import com.huateng.po.mchnt.TblInfDiscAlgo;
import com.huateng.po.mchnt.TblInfDiscCd;
import com.huateng.po.mchnt.TblHisDiscAlgoPK;
import com.huateng.po.mchnt.TblHisDiscAlgo;

public class T20706BOTarget  implements T20706BO{
	private TblInfDiscCdDAO tblInfDiscCdDAO;//存在 建临时表
	private TblHisDiscAlgoDAO tblHisDiscAlgoDAO;//存在 建临时表
	
	private TblHisDiscAlgo1DAO tblHisDiscAlgo1DAO;
	
	public TblHisDiscAlgo1DAO getTblHisDiscAlgo1DAO() {
		return tblHisDiscAlgo1DAO;
	}

	public void setTblHisDiscAlgo1DAO(TblHisDiscAlgo1DAO tblHisDiscAlgo1DAO) {
		this.tblHisDiscAlgo1DAO = tblHisDiscAlgo1DAO;
	}
	public TblInfDiscCdDAO getTblInfDiscCdDAO() {
		return tblInfDiscCdDAO;
	}
	public void setTblInfDiscCdDAO(TblInfDiscCdDAO tblInfDiscCdDAO) {
		this.tblInfDiscCdDAO = tblInfDiscCdDAO;
	}
	public TblHisDiscAlgoDAO getTblHisDiscAlgoDAO() {
		return tblHisDiscAlgoDAO;
	}
	public void setTblHisDiscAlgoDAO(TblHisDiscAlgoDAO tblHisDiscAlgoDAO) {
		this.tblHisDiscAlgoDAO = tblHisDiscAlgoDAO;
	}
	/**
	 * @return the cstMchtFeeInfDAO
	 */
	
	public String add(TblInfDiscCd tblInfDiscCd) {
		tblInfDiscCdDAO.save(tblInfDiscCd);
		return "00";
	}
	
	public String add(TblHisDiscAlgo tblHisDiscAlgo) {
		tblHisDiscAlgoDAO.save(tblHisDiscAlgo);
		return "00";
	}

	public TblHisDiscAlgo get(TblHisDiscAlgoPK id) {
		return tblHisDiscAlgoDAO.get(id);
	}
	
	public TblInfDiscCd get(String id) {
		return tblInfDiscCdDAO.get(id);
	}
	
	public String update(TblHisDiscAlgo tblHisDiscAlgo) {
		tblHisDiscAlgoDAO.update(tblHisDiscAlgo);
		return "00";
	}
	public String update(TblInfDiscCd tblInfDiscCd) {
		tblInfDiscCdDAO.update(tblInfDiscCd);
		return "00";
	}

	
	public String saveOrUpdate(TblHisDiscAlgo tblHisDiscAlgo){
		tblHisDiscAlgoDAO.saveOrUpdate(tblHisDiscAlgo);
		return "00";
	}
	public String saveOrUpdate(TblHisDiscAlgo1 algo1){
		tblHisDiscAlgo1DAO.saveOrUpdate(algo1);
		return "00";
	}
	
	public String saveOrUpdate(TblInfDiscCd tblInfDiscCd){
		tblInfDiscCdDAO.saveOrUpdate(tblInfDiscCd);
		return "00";
	}

	
	public void delete(String id) {
		tblInfDiscCdDAO.delete(id);
		
	}
	public void delete(TblHisDiscAlgoPK id) {
		// TODO Auto-generated method stub
		tblHisDiscAlgoDAO.delete(id);
	}
	
	public String update(List<TblHisDiscAlgo> tblHisDiscAlgoList) {
		for(TblHisDiscAlgo tblOprInfo : tblHisDiscAlgoList) {
			update(tblOprInfo);
		}
		return "00";
	}
	
	public String update2(List<TblInfDiscCd> tblInfDiscCdList) {
		for(TblInfDiscCd tblOprInfo : tblInfDiscCdList) {
			update(tblOprInfo);
		}
		return "00";
	}
   public   ICommQueryDAO commQueryDAO ;
	
	public ICommQueryDAO getCommQueryDAO() {
		return commQueryDAO;
	}

	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}

	public String createArith(List<TblInfDiscAlgo> list,
			TblInfDiscCd tblInfDiscCd, List<TblHisDiscAlgo> descList) {
	
		String sql = "delete from tbl_his_disc_algo where trim(DISC_ID) = '"
			+ tblInfDiscCd.getDiscCd().trim() + "'";
		commQueryDAO.excute(sql);
		String wheresql = "delete from tbl_inf_disc_cd where trim(DISC_CD) = '"
			+tblInfDiscCd.getDiscCd().trim() + "'";
		commQueryDAO.excute(wheresql);
		tblInfDiscCdDAO.save(tblInfDiscCd);
		for (TblHisDiscAlgo his : descList) {
			tblHisDiscAlgoDAO.save(his);// 商户手续费配置表
		}
		return Constants.SUCCESS_CODE;
		
	}
}
