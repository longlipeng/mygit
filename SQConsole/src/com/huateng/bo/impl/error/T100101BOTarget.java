/**
 * 
 */
package com.huateng.bo.impl.error;

import java.util.List;

import com.huateng.bo.error.T100101BO;
import com.huateng.dao.iface.error.TblManualReturnDao;
import com.huateng.po.error.ManualReturn;

/**
 * @author zoujunqing
 *
 */
public class T100101BOTarget implements T100101BO{
	private TblManualReturnDao tblManualReturnDAO;
	public void add(List<ManualReturn> manualReturnList) {
		try{
		for(ManualReturn manu:manualReturnList) 
			tblManualReturnDAO.saveOrUpdate(manu);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public TblManualReturnDao getTblManualReturnDAO() {
		return tblManualReturnDAO;
	}
	public void setTblManualReturnDAO(TblManualReturnDao tblManualReturnDAO) {
		this.tblManualReturnDAO = tblManualReturnDAO;
	}
	public ManualReturn get(String id) {
		return tblManualReturnDAO.get(id);
	}
	public void saveOrUpdate(ManualReturn manualReturn) {
		tblManualReturnDAO.saveOrUpdate(manualReturn);
		
	}
	public void saveupdate(List<ManualReturn> manualReturnList) {
		// TODO Auto-generated method stub
		for(ManualReturn manualReturn :manualReturnList){
			tblManualReturnDAO.saveupdate(manualReturn);
		}
		
	}
	public void delete(String id) {
		// TODO Auto-generated method stub
		tblManualReturnDAO.delete(id);
	}

}
