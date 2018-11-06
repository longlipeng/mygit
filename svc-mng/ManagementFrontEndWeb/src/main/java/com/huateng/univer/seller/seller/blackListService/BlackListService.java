package com.huateng.univer.seller.seller.blackListService;


import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.seller.blacklist.dto.BlackListAreaDTO;
import com.allinfinance.univer.seller.blacklist.dto.BlackListBatchDTO;
import com.allinfinance.univer.seller.blacklist.dto.BlackListPerDTO;
import com.huateng.framework.exception.BizServiceException;


public interface BlackListService {
	
	//批量插入人员黑名单
	public void batchInsertPerson(BlackListBatchDTO personDto)throws BizServiceException;
	//插入人员黑名单
	public void insertPerson(BlackListBatchDTO personDto)throws BizServiceException;
	//批量插入地区黑名单
	public  void batchInsertArea(BlackListBatchDTO personDto)throws BizServiceException;
	//插入地区黑名单
		public  void insertArea(BlackListBatchDTO personDto)throws BizServiceException;
	//查询人员黑名单
	public PageDataDTO inqueryPerson(BlackListPerDTO blackListPerDTO) throws BizServiceException;
	//查询地区黑名单
	public  PageDataDTO inqueryArea(BlackListAreaDTO areaDTO)throws BizServiceException;
	
	public void insert(BlackListBatchDTO blackListDTO) throws BizServiceException;
	
	

}
