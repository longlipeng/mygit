package com.huateng.univer.seller.seller.blackList.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.seller.blacklist.dto.BlackListAreaDTO;
import com.allinfinance.univer.seller.blacklist.dto.BlackListBatchDTO;
import com.allinfinance.univer.seller.blacklist.dto.BlackListPerDTO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.univer.seller.seller.blackList.dao.BlackListDAO;

public class BlackListDAOImpl extends SqlMapClientDaoSupport implements BlackListDAO{

	@Override
	public void batchInsertPerson(List personList) {
		getSqlMapClientTemplate().insert("BLACK_LIST.insertBlackListPerson", personList);
		
	}

	@Override
	public void batchInsertArea(List areaList) {
		getSqlMapClientTemplate().insert("BLACK_LIST.insertBlackListArea", areaList);
		
	}

	@Override
	public PageDataDTO inqueryPerson() {
		//TODO
		return null;
	}

	@Override
	public PageDataDTO inqueryArea() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delectAllBlackListPerson() {
		getSqlMapClientTemplate().delete("BLACK_LIST.delectAllBlackListPerson");
		
	}

	@Override
	public void delectAllBlackListArea() {
		getSqlMapClientTemplate().delete("BLACK_LIST.delectAllBlackListArea");
		
	}

	@Override
	public void insertPerson(BlackListPerDTO person) throws BizServiceException {
		//TODO
		
	}

	@Override
	public void insertArea(BlackListAreaDTO area) throws BizServiceException {
		getSqlMapClientTemplate().insert("BLACK_LIST.insertOneBlackListArea", area);
		
		
		
	}

}
