package com.huateng.univer.settle.biz.service;

import java.util.List;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.consumer.merchant.dto.MerchantDTO;
import com.allinfinance.univer.settle.dto.SettleDTO;
import com.allinfinance.univer.settle.dto.SettleQueryDTO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.hstserver.model.StlPackageDTO;

public interface SettleService {
	public PageDataDTO querySettleList(SettleQueryDTO settleQueryDTO)
			throws BizServiceException;

	public PageDataDTO previewSettle(SettleQueryDTO settleQueryDTO)
			throws BizServiceException;

	public void generateSettle(SettleDTO settleDTO) throws BizServiceException;

	public SettleQueryDTO querySettleDetail(SettleQueryDTO settleQueryDTO)
			throws BizServiceException;

	public PageDataDTO settleDetail(SettleQueryDTO settleQueryDTO)
			throws BizServiceException;

	public void confirmSettle(SettleQueryDTO settleQueryDTO)
			throws BizServiceException;

	public void settlePaymentConfirm(SettleDTO settleDTO)
			throws BizServiceException;

	public void changeSettleDate(SettleDTO settleDTO)
			throws BizServiceException;

	public void cancelSettle(SettleDTO settleDTO) throws BizServiceException;

	public void settleChangeFee(SettleDTO settleDTO) throws BizServiceException;

	public List<MerchantDTO> previewSettleInit(String consumerId)
			throws BizServiceException;

	public StlPackageDTO send(SettleQueryDTO settleQueryDTO, String settleState)
			throws BizServiceException;
}