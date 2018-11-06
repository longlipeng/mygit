package com.huateng.univer.order.business.service;

import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.huateng.framework.exception.BizServiceException;

/**
 * @author Yifeng.Shi 2012-3-26 凭证打印Service
 * 
 * */
public interface SellOrderPrintCertService {

	/**
	 * 查询出入库凭证打印信息
	 * */
	public SellOrderDTO printStockCert(SellOrderDTO dto)
			throws BizServiceException;

}
