package com.huateng.univer.stockcard.biz.service;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.issuer.dto.stock.StockAllocateDTO;
import com.allinfinance.univer.seller.order.dto.OrderReadyDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderInputDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderListDTO;
import com.allinfinance.univer.stockcard.dto.StockCardNoDTO;
import com.allinfinance.univer.stockcard.dto.StockCardQueryDTO;
import com.huateng.framework.exception.BizServiceException;

public interface StockCardService {
	public PageDataDTO query(StockCardQueryDTO dto) throws BizServiceException;

	public PageDataDTO allocateList(StockAllocateDTO stockAllocateDTO)
			throws BizServiceException;

	/**
	 * 查询调拨机构 和调拨人员
	 * 
	 * @param issuerDTO
	 * @return
	 * @throws BizServiceException
	 */
	public SellOrderInputDTO selectAllocateOrgan(
			SellOrderInputDTO sellOrderInputDTO) throws Exception;

	public StockAllocateDTO getTableId(StockAllocateDTO stockAllocateDTO)
			throws BizServiceException;

	public StockAllocateDTO getStockAmount(StockAllocateDTO stockAllocateDTO)
			throws BizServiceException;

	public PageDataDTO readyCardNo(OrderReadyDTO orderReadyDTO)
			throws BizServiceException;

	public void cardArrayReady(OrderReadyDTO orderReadyDTO) throws Exception;

	public void stockAllocate(StockAllocateDTO stockAllocateDTO)
			throws Exception;

	public StockAllocateDTO getCardList(StockAllocateDTO stockAllocateDTO)
			throws Exception;

	public void stockAllocateReturn(StockAllocateDTO stockAllocateDTO)
			throws Exception;

	public void deleteCardList(StockAllocateDTO stockAllocateDTO)
			throws Exception;

	public void cardSegementReady(OrderReadyDTO orderReadyDTO)
			throws BizServiceException;
	
	public PageDataDTO view(StockCardNoDTO dto) throws BizServiceException;
	
	public StockCardNoDTO updateCardStockState(StockCardNoDTO stockCardNoDTO);
}