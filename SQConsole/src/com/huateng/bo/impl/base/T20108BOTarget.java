package com.huateng.bo.impl.base;
import java.util.List;
import com.huateng.bo.base.T20108BO;
import com.huateng.common.Constants;
import com.huateng.dao.iface.base.TblRespCodeInfoTrueDAO;
import com.huateng.po.base.TblRespCodeInfoTrue;
public class T20108BOTarget implements T20108BO{
    private TblRespCodeInfoTrueDAO tblRespCodeInfoTrueDao;
    public void setTblRespCodeInfoTrueDao(TblRespCodeInfoTrueDAO tblRespCodeInfoTrueDao) {
		this.tblRespCodeInfoTrueDao = tblRespCodeInfoTrueDao;
	}
    public TblRespCodeInfoTrueDAO getTblRespCodeInfoTrueDao(){
    	    return this.tblRespCodeInfoTrueDao;
    }
	public String add(TblRespCodeInfoTrue tblRespCodeInfoTrue) {
		tblRespCodeInfoTrueDao.save(tblRespCodeInfoTrue);
		return Constants.SUCCESS_CODE;
	}
	
	public String delete(TblRespCodeInfoTrue tblRespCodeInfoTrue) {
		tblRespCodeInfoTrueDao.delete(tblRespCodeInfoTrue);
		return Constants.SUCCESS_CODE;
	}

	public String delete(String respId) {
		tblRespCodeInfoTrueDao.delete(respId);
		return Constants.SUCCESS_CODE;
	}

	public TblRespCodeInfoTrue get(String respId) {
		return tblRespCodeInfoTrueDao.get(respId);
	}

	public String update(List<TblRespCodeInfoTrue> tblRespCodeInfoTrueList) {
		for(TblRespCodeInfoTrue tblRespCodeInfoTrue:tblRespCodeInfoTrueList){
		   tblRespCodeInfoTrueDao.update(tblRespCodeInfoTrue);
		}
		return Constants.SUCCESS_CODE;
	}

	public String update(TblRespCodeInfoTrue tblRespCodeInfoTrue) {
		tblRespCodeInfoTrueDao.update(tblRespCodeInfoTrue);
		return Constants.SUCCESS_CODE;
	}
   
	public List<TblRespCodeInfoTrue> get() {
	
		return tblRespCodeInfoTrueDao.loadAll();
	}

}
