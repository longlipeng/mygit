package com.huateng.bo.impl.mchnt;

import java.util.List;

import com.huateng.bo.mchnt.T2070302BO;
import com.huateng.dao.iface.mchnt.TblHisDiscAlgo1TmpDAO;
import com.huateng.po.mchnt.TblMchtBeneficiaryInfTmp;
import com.huateng.po.mchnt.TblHisDiscAlgo1Tmp;
import com.huateng.po.mchnt.TblHisDiscAlgo2Tmp;

public class T2070302BOTarget implements T2070302BO{
	private TblHisDiscAlgo1TmpDAO tblHisDiscAlgo1TmpDAO;


	public TblHisDiscAlgo1TmpDAO getTblHisDiscAlgo1TmpDAO() {
		return tblHisDiscAlgo1TmpDAO;
	}

	public void setTblHisDiscAlgo1TmpDAO(TblHisDiscAlgo1TmpDAO tblHisDiscAlgo1TmpDAO) {
		this.tblHisDiscAlgo1TmpDAO = tblHisDiscAlgo1TmpDAO;
	}

	/**
	 * @return the cstMchtFeeInfDAO
	 */
	
	public String add(TblHisDiscAlgo1Tmp tblHisDiscAlgo1Tmp) {
		tblHisDiscAlgo1TmpDAO.save(tblHisDiscAlgo1Tmp);
		return "00";
	}

	public String addAlgo2(TblHisDiscAlgo2Tmp tblHisDiscAlgo2Tmp) {
		tblHisDiscAlgo1TmpDAO.saveAlgo2(tblHisDiscAlgo2Tmp);
		return "00";
	}
	
	public TblHisDiscAlgo1Tmp get(String id) {
		return tblHisDiscAlgo1TmpDAO.get(id);
	}

	public TblHisDiscAlgo2Tmp getAlgo2(String id) {
		return tblHisDiscAlgo1TmpDAO.getAlgo2(id);
	}
	
	public String update(TblHisDiscAlgo1Tmp tblHisDiscAlgo1Tmp) {
		tblHisDiscAlgo1TmpDAO.update(tblHisDiscAlgo1Tmp);
		return "00";
	}
	
	public String updateAlgo2(TblHisDiscAlgo2Tmp tblHisDiscAlgo2Tmp) {
		tblHisDiscAlgo1TmpDAO.updateAlgo2(tblHisDiscAlgo2Tmp);
		return "00";
	}

	public void delete(String id) {
		tblHisDiscAlgo1TmpDAO .delete(id);
		
	}
	
	public void deleteAlgo2(String id) {
		tblHisDiscAlgo1TmpDAO .deleteAlgo2(id);
		
	}
	public String update(List<TblHisDiscAlgo1Tmp> tblHisDiscAlgo1Tmp1List) {
		for(TblHisDiscAlgo1Tmp tblHisDiscAlgo1Tmp : tblHisDiscAlgo1Tmp1List) {
			update(tblHisDiscAlgo1Tmp);
		}
		return "00";
	}

	/**
	 * 根据id查询记录
	 */
	public TblMchtBeneficiaryInfTmp getBeneficiary(String id) {
		// TODO Auto-generated method stub
		return tblHisDiscAlgo1TmpDAO.getBeneficiary(id);
	}

	/**
	 * 受益人信息删除
	 */
	public void deleteBeneficiary(String beneficiaryId) {
		// TODO Auto-generated method stub
		tblHisDiscAlgo1TmpDAO.deleteBeneficiary(beneficiaryId);
	}

	/**
	 * 添加受益人信息
	 */
	public String addBeneficiary(TblMchtBeneficiaryInfTmp amlt) {
		// TODO Auto-generated method stub
		tblHisDiscAlgo1TmpDAO.addBeneficiary(amlt);
		return "00";
	}

	/**
	 * 修改受益人信息
	 */
	public String updateBeneficiary(TblMchtBeneficiaryInfTmp amlt) {
		// TODO Auto-generated method stub
		tblHisDiscAlgo1TmpDAO.updateBeneficiary(amlt);
		return "00";
	}

}
