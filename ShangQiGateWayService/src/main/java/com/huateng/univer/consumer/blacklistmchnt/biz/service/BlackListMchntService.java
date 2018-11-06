package com.huateng.univer.consumer.blacklistmchnt.biz.service;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.consumer.blacklistmchnt.dto.BlackListMchntDTO;
import com.allinfinance.univer.consumer.blacklistmchnt.dto.BlackListMchntQueryDTO;
import com.huateng.framework.exception.BizServiceException;

public interface BlackListMchntService {

	public PageDataDTO inquiryBlackListMchnt(BlackListMchntQueryDTO dto)
			throws BizServiceException;

	public void deleteBlackListMchnt(BlackListMchntDTO dto) throws BizServiceException;

	public void insertBlackListMchnt(BlackListMchntDTO dto) throws BizServiceException;
}
