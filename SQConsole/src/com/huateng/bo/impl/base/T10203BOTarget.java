package com.huateng.bo.impl.base;

import com.huateng.bo.base.T10203BO;
import com.huateng.common.Constants;
import com.huateng.dao.iface.base.TblTxnNameDAO;
import com.huateng.po.TblTxnName;

/**
 * Title:交易码BO实现
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-9
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author 
 * 
 * @version 1.0
 */
public class T10203BOTarget implements T10203BO {
	TblTxnNameDAO tblTxnNameDAO;
	
	/* (non-Javadoc)
	 * @see com.huateng.bo.T10203BO#add(com.huateng.po.TblCityCode)
	 */
	public String add(TblTxnName tblTxnName) {
		tblTxnNameDAO.save(tblTxnName);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.huateng.bo.T10203BO#delete(com.huateng.po.TblTxnName)
	 */
	public String delete(TblTxnName tblTxnName) {
		tblTxnNameDAO.delete(tblTxnName);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.huateng.bo.T10203BO#delete(java.lang.String)
	 */
	public String delete(String id) {
		tblTxnNameDAO.delete(id);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.huateng.bo.T10203BO#get(com.huateng.po.TblTxnName)
	 */
	public TblTxnName get(String id) {
		return tblTxnNameDAO.get(id);
	}

	/* (non-Javadoc)
	 * @see com.huateng.bo.T10203BO#update(java.util.List)
	 */
	public String update(TblTxnName TblTxnName) {
//		for(TblTxnName tblTxnName : tblTxnNameList) {
//			tblTxnNameDAO.update(tblTxnName);
//		}
		TblTxnName.setDcFlag(TblTxnName.getDcFlag());
		TblTxnName.setTxnDsp(TblTxnName.getTxnDsp());
		TblTxnName.setTxnName(TblTxnName.getTxnName());
		tblTxnNameDAO.update(TblTxnName);
		return Constants.SUCCESS_CODE;
	}

	public TblTxnNameDAO getTblTxnNameDAO() {
		return tblTxnNameDAO;
	}

	public void setTblTxnNameDAO(TblTxnNameDAO tblTxnNameDAO) {
		this.tblTxnNameDAO = tblTxnNameDAO;
	}

	
	
}
