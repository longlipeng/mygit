package com.huateng.univer.entitybaseinfo.delivarypoint.biz.service;

import java.util.List;

import com.allinfinance.univer.entity.dto.DeliveryPointDTO;
import com.huateng.framework.exception.BizServiceException;

/**
 * 快递点service
 * 
 * @author xxl
 * 
 */
public interface DeliveryPointService {
	public List<DeliveryPointDTO> inquery(String entityId)
			throws BizServiceException;

	public DeliveryPointDTO view(DeliveryPointDTO deliveryPointDTO)
			throws BizServiceException;

	public DeliveryPointDTO insert(DeliveryPointDTO deliveryPointDTO)
			throws BizServiceException;

	public void update(DeliveryPointDTO deliveryPointDTO)
			throws BizServiceException;

	public void delete(DeliveryPointDTO deliveryPointDTO)
			throws BizServiceException;
}
