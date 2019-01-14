package com.huateng.univer.order.business.service;

import java.util.List;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.entity.stock.dto.EntityStockDTO;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderInputDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderListDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderOrigCardListDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderOrigCardListQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderQueryDTO;
import com.huateng.framework.exception.BizServiceException;

/***
 * 
 * @author dawn
 * 
 */
public interface OrderService {

	public PageDataDTO queryOrder(SellOrderQueryDTO sellOrderQueryDTO)
			throws BizServiceException;

	public SellOrderInputDTO initAdd(SellOrderInputDTO sellOrderInputDTO)
			throws BizServiceException;

	public SellOrderInputDTO insertSerllOrder(SellOrderDTO sellOrderDTO)
			throws BizServiceException;

	public SellOrderInputDTO getCardholderList(
			SellOrderInputDTO sellOrderInputDTO) throws BizServiceException;

	public SellOrderInputDTO insertCardholderList(
			SellOrderInputDTO sellOrderInputDTO) throws BizServiceException;

	public SellOrderInputDTO editSellOrderDTO(
			SellOrderInputDTO sellOrderInputDTO) throws BizServiceException;

	public void deleteOrderList(SellOrderListDTO sellOrderListDTO)
			throws BizServiceException;

	public SellOrderInputDTO getProductStock(SellOrderInputDTO sellOrderInputDTO)
			throws BizServiceException;

	public void insertOrderListForSellOrderUnsign(
			SellOrderListDTO sellOrderListDTO) throws BizServiceException;

	public void deleteOrderListForSellOrderUnsign(
			SellOrderListDTO sellOrderListDTO) throws BizServiceException;

	public void updateSellOrder(SellOrderDTO sellOrderDTO)
			throws BizServiceException;

	public SellOrderListDTO editOrderListForSellOrderUnsign(
			SellOrderListDTO sellOrderListDTO) throws BizServiceException;

	public void updateOrderListForSellOrderUnsign(
			SellOrderListDTO sellOrderListDTO) throws BizServiceException;

	public SellOrderInputDTO editBuyOrderDTO(SellOrderInputDTO sellOrderInputDTO)
			throws BizServiceException;

	public SellOrderInputDTO initBuyOrderAdd(SellOrderInputDTO sellOrderInputDTO)
			throws BizServiceException;

	public List<EntityStockDTO> getCurrentStockDTOByEntity(String entityId)
			throws BizServiceException;

	public PageDataDTO getCustomerCard(SellOrderInputDTO sellOrderInputDTO)
			throws BizServiceException;

	public void insertCreditCardlist(SellOrderInputDTO sellOrderInputDTO)
			throws BizServiceException;

	public void deleteCreditCardlist(SellOrderListDTO sellOrderListDTO)
			throws BizServiceException;

	public List<ProductDTO> getProdByContractForBuyOrderUnsign(String entityId)
			throws BizServiceException;

	public SellOrderDTO getSellOrderByKey(String orderId)
			throws BizServiceException;

	public SellOrderInputDTO editChangeCardOrderDTO(
			SellOrderInputDTO sellOrderInputDTO) throws BizServiceException;

	public void insertChangeCardOrderOrigCard(
			SellOrderDTO sellOrderDTO)
			throws BizServiceException;

	public void deleteChangeCardOrderOrigCard(
			SellOrderOrigCardListDTO sellOrderOrigCardListDTO)
			throws BizServiceException;

	public SellOrderInputDTO getProductStockForChangeCard(
			SellOrderInputDTO sellOrderInputDTO) throws BizServiceException;

	public void deleteOrderListForChangeCard(SellOrderListDTO sellOrderListDTO)
			throws BizServiceException;

	public SellOrderListDTO editOrderListForChangeCardOrder(
			SellOrderListDTO sellOrderListDTO) throws BizServiceException;

	public void insertOrderListForChangeCardOrder(
			SellOrderListDTO sellOrderListDTO) throws BizServiceException;

	public void updateOrderListForChangeCardOrder(
			SellOrderListDTO sellOrderListDTO) throws BizServiceException;

	public SellOrderInputDTO updateRansomOrderDTO(
			SellOrderInputDTO sellOrderInputDTO) throws BizServiceException;

	public void insertRansomOrderOrigCard(
			SellOrderOrigCardListQueryDTO sellOrderOrigCardListQueryDTO)
			throws BizServiceException;

	public void deleteRansomOrderOrigCard(
			SellOrderOrigCardListDTO sellOrderOrigCardListDTO)
			throws BizServiceException;

	public void insertCreditCardholderList(SellOrderInputDTO sellOrderInputDTO)
			throws BizServiceException;

	public String checkCreditCardholderCardList(
			SellOrderInputDTO sellOrderInputDTO) throws BizServiceException;
	
	public SellOrderInputDTO orderInsert(SellOrderDTO sellOrderDTO)
	throws BizServiceException ;
}
