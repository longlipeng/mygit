package com.huateng.univer.consumer.blacklist.biz.service;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.consumer.blacklist.dto.BlackListDTO;
import com.allinfinance.univer.consumer.blacklist.dto.BlackListQueryDTO;
import com.huateng.framework.exception.BizServiceException;

public interface BlackListService {

	public PageDataDTO inquiryBlackList(BlackListQueryDTO dto)
			throws BizServiceException;

	public void deleteBlackList(BlackListDTO dto) throws BizServiceException;

	public void insertBlackList(BlackListDTO dto) throws BizServiceException;
}
