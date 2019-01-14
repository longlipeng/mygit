package com.huateng.framework.common.service;

import com.huateng.framework.dao.DataBaseDAO;
import com.huateng.framework.exception.BizServiceException;

public class CommonServiceImpl implements CommonService {
	
	private DataBaseDAO databaseDao;

	public DataBaseDAO getDatabaseDao() {
		return databaseDao;
	}

	public void setDatabaseDao(DataBaseDAO databaseDao) {
		this.databaseDao = databaseDao;
	}

	@Override
	public String getNextValueOfSequence(String seqName) throws BizServiceException{
		return databaseDao.getNextValueOfSequence(seqName);
	}
}