package com.huateng.univer.seller.seller.blackList.dao;

import java.util.List;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.seller.blacklist.dto.BlackListAreaDTO;
import com.allinfinance.univer.seller.blacklist.dto.BlackListBatchDTO;
import com.allinfinance.univer.seller.blacklist.dto.BlackListPerDTO;
import com.huateng.framework.exception.BizServiceException;

public interface BlackListDAO {
	  //批量插入人员黑名单
		public void batchInsertPerson(List personList)throws BizServiceException;
		//批量插入地区黑名单
		public  void batchInsertArea(List areaList)throws BizServiceException;
		
		//查询人员黑名单
		public PageDataDTO inqueryPerson()throws BizServiceException;
		//查询地区黑名单
		public  PageDataDTO inqueryArea()throws BizServiceException;	
		//清空持卡人黑名单表
		public void delectAllBlackListPerson()throws BizServiceException;
		//清空地区黑名单表
		public void delectAllBlackListArea()throws BizServiceException;
		
		//插入人员黑名单
		public void insertPerson(BlackListPerDTO person)throws BizServiceException;
				//插入地区黑名单
		public  void insertArea(BlackListAreaDTO area)throws BizServiceException;
				

}
