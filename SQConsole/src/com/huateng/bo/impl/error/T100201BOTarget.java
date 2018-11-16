/**
 * 
 */
package com.huateng.bo.impl.error;

import java.util.List;

import com.huateng.bo.error.T100201BO;
import com.huateng.dao.iface.error.TblChangeAccInfTmpDao;
import com.huateng.po.error.TblChangeAccInfRefuse;
import com.huateng.po.error.TblChangeAccInfTmp;
import com.huateng.po.error.TblChangeAccInfTmpId;
import com.huateng.po.error.TblChangeAccInf;
import com.huateng.po.error.TblChangeAccInfId;

/**
 * @author zoujunqing
 *
 */
public  class T100201BOTarget implements T100201BO{
	private TblChangeAccInfTmpDao tblChangeAccInfTmpDao;
	public void add(List<TblChangeAccInfTmp> tblChangeAccInfTmpList) {
		try{
		for(TblChangeAccInfTmp manu:tblChangeAccInfTmpList) 
			tblChangeAccInfTmpDao.saveOrUpdate(manu);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public TblChangeAccInfTmpDao getTblChangeAccInfTmpDAO() {
		return tblChangeAccInfTmpDao;
	}
	public void setTblChangeAccInfTmpDAO(TblChangeAccInfTmpDao tblChangeAccInfTmpDao) {
		this.tblChangeAccInfTmpDao = tblChangeAccInfTmpDao;
	}
	public TblChangeAccInfTmp get(String id) {
		return tblChangeAccInfTmpDao.get(id);
	}
	public void saveOrUpdate(TblChangeAccInfTmp tblChangeAccInfTmp) {
		tblChangeAccInfTmpDao.saveOrUpdate(tblChangeAccInfTmp);
		
	}
	public void saveupdate(List<TblChangeAccInfTmp> tblChangeAccInfTmpList) {
		// TODO Auto-generated method stub
		for(TblChangeAccInfTmp tblChangeAccInfTmp :tblChangeAccInfTmpList){
			tblChangeAccInfTmpDao.saveupdate(tblChangeAccInfTmp);
		}
		
	}
	public void delete(String id) {
		// TODO Auto-generated method stub
		tblChangeAccInfTmpDao.delete(id);
	}
	public void addone(TblChangeAccInfTmp tblChangeAccInfTmp) {
		// TODO Auto-generated method stub
		tblChangeAccInfTmpDao.saveOrUpdate(tblChangeAccInfTmp);
	}
	//批量审核通过
	public void addAll(List<TblChangeAccInfTmp> list){
		for(int i=0;i<list.size();i++){
			tblChangeAccInfTmpDao.saveOrUpdate(list.get(i));
		}
	}
	public void deletelist(TblChangeAccInfTmpId tblChangeAccInfTmpId) {
		// TODO Auto-generated method stub
		tblChangeAccInfTmpDao.deletelist(tblChangeAccInfTmpId);
	}
	//批量审核拒绝
	public void deletelist(List<TblChangeAccInfTmpId> list) {
		for(int i=0;i<list.size();i++){
			tblChangeAccInfTmpDao.deletelist(list.get(i));
		}
	}
	public TblChangeAccInfTmp get(TblChangeAccInfTmpId tblChangeAccInfTmpId) {
		// TODO Auto-generated method stub
		return tblChangeAccInfTmpDao.get(tblChangeAccInfTmpId);
	}
	public void saveOrUpdate(TblChangeAccInf tblChangeAccInf) {
		// TODO Auto-generated method stub
		tblChangeAccInfTmpDao.saveOrUpdate(tblChangeAccInf);
	}
	//批量审核通过
	public void saveOrUpdate(List<TblChangeAccInf> list) {
		for(int i=0;i<list.size();i++){
			tblChangeAccInfTmpDao.saveOrUpdate(list.get(i));
		}
	}
	public void saveRefuse(TblChangeAccInfRefuse tblChangeAccInfRefuse) {
		tblChangeAccInfTmpDao.saveOrUpdate(tblChangeAccInfRefuse);
	}
	//批量审核拒绝
	public void saveRefuse(List<TblChangeAccInfRefuse> list) {
		for(int i=0;i<list.size();i++){
			tblChangeAccInfTmpDao.saveOrUpdate(list.get(i));
		}
	}
	
	
}
