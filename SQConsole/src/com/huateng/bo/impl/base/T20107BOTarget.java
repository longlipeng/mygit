package com.huateng.bo.impl.base;
import java.util.List;
import com.huateng.bo.base.T20107BO;
import com.huateng.common.Constants;
import com.huateng.dao.iface.base.TblRespCodeInfoDAO;
import com.huateng.po.base.TblRespCodeInfo;
public class T20107BOTarget implements T20107BO{
    private TblRespCodeInfoDAO tblRespCodeInfoDao;
    public void setTblRespCodeInfoDao(TblRespCodeInfoDAO tblRespCodeInfoDao) {
		this.tblRespCodeInfoDao = tblRespCodeInfoDao;
	}
    public TblRespCodeInfoDAO getTblRespCodeInfoDao(){
    	    return this.tblRespCodeInfoDao;
    }
	public String add(TblRespCodeInfo tblRespCodeInfo) {
		tblRespCodeInfoDao.save(tblRespCodeInfo);
		return Constants.SUCCESS_CODE;
	}
	
	public String delete(TblRespCodeInfo tblRespCodeInfo) {
		tblRespCodeInfoDao.delete(tblRespCodeInfo);
		return Constants.SUCCESS_CODE;
	}

	public String delete(String respId) {
		tblRespCodeInfoDao.delete(respId);
		return Constants.SUCCESS_CODE;
	}

	public TblRespCodeInfo get(String respId) {
		return tblRespCodeInfoDao.get(respId);
	}

	public String update(List<TblRespCodeInfo> tblRespCodeInfoList) {
		for(TblRespCodeInfo tblRespCodeInfo:tblRespCodeInfoList){
			//System.out.println(tblRespCodeInfo.getDesc());
		   tblRespCodeInfoDao.update(tblRespCodeInfo);
		}
		return Constants.SUCCESS_CODE;
	}

	public String update(TblRespCodeInfo tblRespCodeInfo) {
		tblRespCodeInfoDao.update(tblRespCodeInfo);
		return Constants.SUCCESS_CODE;
	}
   
	public List<TblRespCodeInfo> get() {
	
		return tblRespCodeInfoDao.loadAll();
	}

}
