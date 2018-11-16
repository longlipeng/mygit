package com.huateng.bo.impl.error;

import java.util.List;

import com.huateng.bo.error.T10020BO;
import com.huateng.dao.iface.error.TblMchtHangDAO;
import com.huateng.po.error.TBLTermHangPK;
import com.huateng.po.error.TblMchtHang;
import com.huateng.po.error.TBLTermHang;
import com.huateng.po.error.TblMchtHangPK;
import com.huateng.po.error.TblTransHang;
import com.huateng.po.error.TblTransHangPK;


public class T10020BOTarget implements T10020BO{
	private TblMchtHangDAO tblMchtHangDAO;

	

	public TblMchtHangDAO getTblMchtHangDAO() {
		return tblMchtHangDAO;
	}

	public void setTblMchtHangDAO(TblMchtHangDAO tblMchtHangDAO) {
		this.tblMchtHangDAO = tblMchtHangDAO;
	}

	/**
	 * @return the cstMchtFeeInfDAO
	 */
	
	public String add(TblMchtHang tblMchtHang) {
		tblMchtHangDAO.save(tblMchtHang);
		return "00";
	}
	
	public String add(TBLTermHang tBLTermHang) {
		tblMchtHangDAO.save(tBLTermHang);
		return "00";
	}
	
	public String add(TblTransHang tblTransHang) {
		tblMchtHangDAO.save(tblTransHang);
		return "00";
	}
	public TblMchtHang getMcht(TblMchtHangPK id) {
		return tblMchtHangDAO.get(id);
	}
	
	public TBLTermHang getTerm(TBLTermHangPK id) {
		return tblMchtHangDAO.get2(id);
	}
	public TblTransHang getTran(TblTransHangPK id) {
		return tblMchtHangDAO.get3(id);
	}
	public String update(TblMchtHang tblMchtHang) {
		tblMchtHangDAO.update(tblMchtHang);
		return "00";
	}
	public String update(TBLTermHang tBLTermHang) {
		tblMchtHangDAO.update(tBLTermHang);
		return "00";
	}
	public String update(TblTransHang tblTransHang) {
		tblMchtHangDAO.update(tblTransHang);
		return "00";
	}
	
	public String saveOrUpdate(TblTransHang tblTransHang){
		tblMchtHangDAO.saveOrUpdate(tblTransHang);
		return "00";
	}
	
	public String saveOrUpdate(TBLTermHang tBLTermHang){
		tblMchtHangDAO.saveOrUpdate(tBLTermHang);
		return "00";
	}
	public String saveOrUpdate(TblMchtHang tblMchtHang){
		tblMchtHangDAO.saveOrUpdate(tblMchtHang);
		return "00";
	}
	
	public void delete(String id) {
		tblMchtHangDAO .delete(id);
		
	}
	public String update(List<TblMchtHang> tblMchtHangList) {
		for(TblMchtHang tblOprInfo : tblMchtHangList) {
			update(tblOprInfo);
		}
		return "00";
	}
}
