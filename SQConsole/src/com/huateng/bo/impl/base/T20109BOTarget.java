package com.huateng.bo.impl.base;
import java.util.List;

import com.huateng.bo.base.T20109BO;
import com.huateng.common.Constants;
import com.huateng.dao.iface.base.TblRspCodeMapDAO;
import com.huateng.po.base.TblRspCodeMap;
import com.huateng.po.base.TblRspCodeMapPK;
public class T20109BOTarget implements T20109BO{
    private TblRspCodeMapDAO tblRspCodeMapDAO;
	public TblRspCodeMapDAO getTblRspCodeMapDAO() {
		return tblRspCodeMapDAO;
	}

	public void setTblRspCodeMapDAO(TblRspCodeMapDAO tblRspCodeMapDAO) {
		this.tblRspCodeMapDAO = tblRspCodeMapDAO;
	}

	public String add(TblRspCodeMap tblRspCodeMap) {
		tblRspCodeMapDAO.save(tblRspCodeMap);
		return Constants.SUCCESS_CODE;
	}

	public String delete(TblRspCodeMapPK id) {
		tblRspCodeMapDAO.delete(id);
		return Constants.SUCCESS_CODE;
	}

	public String delete(TblRspCodeMap tblRspCodeMap) {
		tblRspCodeMapDAO.delete(tblRspCodeMap);
		return Constants.SUCCESS_CODE;
	}

	public TblRspCodeMap get(TblRspCodeMapPK id) {
		
		return tblRspCodeMapDAO.get(id);
	}

	public String update(TblRspCodeMap tblRspCodeMap) {
	          tblRspCodeMapDAO.update(tblRspCodeMap);
		return Constants.SUCCESS_CODE;
	}

	public String update(List<TblRspCodeMap> tblRspCodeMap) {
		for(TblRspCodeMap rspc:tblRspCodeMap)
			tblRspCodeMapDAO.update(rspc);
		return Constants.SUCCESS_CODE;
	}

}
