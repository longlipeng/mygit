package com.huateng.univer.issuer.consumer.service;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.issuer.dto.consumer.ConsumerDTO;
import com.allinfinance.univer.issuer.dto.consumer.ConsumerQueryDTO;
import com.huateng.framework.exception.BizServiceException;

public interface ConsumerService {

	public PageDataDTO inqueryConsumer(ConsumerQueryDTO consumerQueryDTO)
			throws BizServiceException;

	public ConsumerDTO insertConsumer(ConsumerDTO consumerDTO)
			throws BizServiceException;

	public ConsumerDTO viewConsumer(ConsumerDTO consumerDTO)
			throws BizServiceException;

	public void editConsumer(ConsumerDTO consumerDTO)
			throws BizServiceException;

	public void deleteConsumer(ConsumerDTO consumerDTO)
			throws BizServiceException;

	public PageDataDTO queryEntityId(ConsumerQueryDTO consumerQueryDTO)
			throws BizServiceException;

	public ConsumerDTO configEntityId(ConsumerQueryDTO consumerQueryDTO)
			throws BizServiceException;

	public PageDataDTO selectIssuer(ConsumerQueryDTO consumerQueryDTO)
			throws BizServiceException;

	public ConsumerDTO loadForModifyWebPassowrd(ConsumerDTO consumerDTO)
			throws BizServiceException;

	public void updatePart(ConsumerDTO consumerDTO) throws BizServiceException;

	public String checkWebName(ConsumerDTO consumerDTO)
			throws BizServiceException;

	public void checkConsumer(ConsumerDTO consumerDTO)
			throws BizServiceException;
}
