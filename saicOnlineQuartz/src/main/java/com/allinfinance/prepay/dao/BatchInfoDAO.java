package com.allinfinance.prepay.dao;

public interface BatchInfoDAO {
	
	public int batchInsertInfo(String method,Object entity);

	public int batchUpdateInfo(String method,Object entity);
	 
}
