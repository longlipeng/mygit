package com.huateng.univer.order.business.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.univer.seller.cardholder.dto.CardholderDTO;
import com.allinfinance.univer.seller.order.dto.OrderReadyDTO;
import com.allinfinance.univer.seller.order.dto.SellBuyOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderListDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderOrigCardListDTO;
import com.huateng.framework.constant.Const;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.dao.BaseDAO;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.ProductDAO;
import com.huateng.framework.ibatis.dao.SellOrderDAO;
import com.huateng.framework.ibatis.dao.SellOrderOrigCardListDAO;
import com.huateng.framework.ibatis.model.DeliveryContact;
import com.huateng.framework.ibatis.model.EntityStock;
import com.huateng.framework.ibatis.model.LoyaltyContract;
import com.huateng.framework.ibatis.model.LoyaltyProdContract;
import com.huateng.framework.ibatis.model.Product;
import com.huateng.framework.ibatis.model.ProductExample;
import com.huateng.framework.ibatis.model.SellContract;
import com.huateng.framework.ibatis.model.SellOrder;
import com.huateng.framework.ibatis.model.SellOrderCardList;
import com.huateng.framework.ibatis.model.SellOrderFlow;
import com.huateng.framework.ibatis.model.SellOrderList;
import com.huateng.framework.ibatis.model.SellOrderListExample;
import com.huateng.framework.ibatis.model.SellOrderOrigCardList;
import com.huateng.framework.ibatis.model.SellOrderOrigCardListExample;
import com.huateng.framework.ibatis.model.SellProdContract;
import com.huateng.framework.ibatis.model.Seller;
import com.huateng.framework.ibatis.model.UnionOrder;
import com.huateng.framework.util.Amount;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.univer.entitystock.service.EntityStockService;
import com.huateng.univer.issuer.order.biz.bo.StockOrderCommonService;
import com.huateng.univer.order.business.bo.OrderBO;
import com.huateng.univer.order.business.bo.OrderBaseQueryBO;
import com.huateng.univer.order.business.bo.OrderCountTotalPrice;
import com.huateng.univer.order.business.service.OrderReadyService;

public class OrderReadyServiceImpl implements OrderReadyService {

	private Logger logger = Logger.getLogger(this.getClass());
	private OrderBaseQueryBO orderBaseQueryBO;

	private OrderBO orderBO;
	private StockOrderCommonService stockOrderCommonService;
	private BaseDAO baseOrderDAO;
	private CommonsDAO commonsDAO;
	private SellOrderDAO sellOrderDAO;
	private OrderCountTotalPrice orderCountTotalPrice;
	private EntityStockService entityStockService;
	private SellOrderOrigCardListDAO sellOrderOrigCardListDAO;
	private ProductDAO productDAO;

	public SellOrderOrigCardListDAO getSellOrderOrigCardListDAO() {
		return sellOrderOrigCardListDAO;
	}

	public void setSellOrderOrigCardListDAO(
			SellOrderOrigCardListDAO sellOrderOrigCardListDAO) {
		this.sellOrderOrigCardListDAO = sellOrderOrigCardListDAO;
	}

	public EntityStockService getEntityStockService() {
		return entityStockService;
	}

	public void setEntityStockService(EntityStockService entityStockService) {
		this.entityStockService = entityStockService;
	}

	public StockOrderCommonService getStockOrderCommonService() {
		return stockOrderCommonService;
	}

	public void setStockOrderCommonService(
			StockOrderCommonService stockOrderCommonService) {
		this.stockOrderCommonService = stockOrderCommonService;
	}

	public OrderBaseQueryBO getOrderBaseQueryBO() {
		return orderBaseQueryBO;
	}

	public void setOrderBaseQueryBO(OrderBaseQueryBO orderBaseQueryBO) {
		this.orderBaseQueryBO = orderBaseQueryBO;
	}

	public void cardReady(OrderReadyDTO orderReadyDTO)
			throws BizServiceException {
		try {
			// 记名销售订单卡片准备
			if (orderReadyDTO.getOrderType() != null
					&& (orderReadyDTO
							.getOrderType()
							.equals(
									OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN) || orderReadyDTO
							.getOrderType()
							.equals(
									OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN))
					|| orderReadyDTO
							.getOrderType()
							.equals(
									OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_SIGN)) {
				SellOrder currentOrder = orderBaseQueryBO
						.getSellOrder(orderReadyDTO.getOrderId());

				int difference = Integer.parseInt(currentOrder
						.getCardQuantity())
						- Integer
								.parseInt(currentOrder.getRealCardQuantity() == null
										|| currentOrder.getRealCardQuantity()
												.equals("") ? "0"
										: currentOrder.getRealCardQuantity());
				if (orderReadyDTO.cardNoArray.length > difference) {
					throw new BizServiceException("所选卡片张数不应大于  " + difference
							+ " 张,请重新准备! ");
				}
				List<SellOrderCardList> orderCardList = orderBaseQueryBO
						.getSellOrderCardListsByOrderId(orderReadyDTO
								.getOrderId());
				orderCardListReady(orderCardList, orderReadyDTO);
			} else if (orderReadyDTO.getOrderType() != null
					&& orderReadyDTO.getOrderType().equals(
							OrderConst.ORDER_TYPE_ORDER_CHANGE_CARD)) {
				// 换卡订单卡片准备
				SellOrderList orderList = orderBaseQueryBO
						.getSellOrderListByPrimaryKey(orderReadyDTO
								.getOrderListId());
				if (orderList.getCardAmount() != null
						&& orderList.getRealAmount() != null) {
					Integer reallyAmount = 0;
					reallyAmount = Integer.parseInt(orderList.getCardAmount())
							- Integer.parseInt(orderList.getRealAmount());
					if (orderReadyDTO.cardNoArray.length > reallyAmount) {
						throw new BizServiceException("所选卡片张数不应大于  "
								+ reallyAmount + " 张,请重新准备! ");
					}
				}
				orderReadyDTO.setProductId(orderList.getProductId());
				orderListReadyForChangeOrder(orderList, orderReadyDTO);
			} else {
				// 匿名销售订单卡片准备
				SellOrderList orderList = orderBaseQueryBO
						.getSellOrderListByPrimaryKey(orderReadyDTO
								.getOrderListId());
				if (orderList.getCardAmount() != null
						&& orderList.getRealAmount() != null) {
					Integer reallyAmount = 0;
					reallyAmount = Integer.parseInt(orderList.getCardAmount())
							- Integer.parseInt(orderList.getRealAmount());
					if (orderReadyDTO.cardNoArray.length > reallyAmount) {
						throw new BizServiceException("所选卡片张数不应大于  "
								+ reallyAmount + " 张,请重新准备! ");
					}
				}
				orderListReady(orderList, orderReadyDTO);
			}
			/***
			 * 更新订单的实际数量
			 */
			String cardQuantity = orderBaseQueryBO
					.selectUnsignCardQuantity(orderReadyDTO.getOrderId());

			SellOrder sellOrder = new SellOrder();
			sellOrder.setOrderId(orderReadyDTO.getOrderId());

			sellOrder.setRealCardQuantity(cardQuantity);

			sellOrder.setModifyTime(DateUtil.getCurrentTime());

			sellOrder.setModifyUser(orderReadyDTO.getLoginUserId());

			orderBO.updateByPrimaryKeySelective(sellOrder);
		} catch (BizServiceException e) {
			throw e;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("系统异常");
		}

	}

	/**
	 * @author Yifeng.Shi
	 * @since 2011-09-07
	 * @param OrderReadyDTO
	 * @return null 以开始卡号和结束卡号做卡段号准备
	 * */
	public void cardSegmentReady(OrderReadyDTO orderReadyDTO)
			throws BizServiceException {
		try {
			// 记名销售订单卡片准备
			if (orderReadyDTO.getOrderType() != null
					&& (orderReadyDTO
							.getOrderType()
							.equals(
									OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN)
							|| orderReadyDTO
									.getOrderType()
									.equals(
											OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN) || orderReadyDTO
							.getOrderType()
							.equals(
									OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_SIGN))) {
				SellOrder currentOrder = orderBaseQueryBO
						.getSellOrder(orderReadyDTO.getOrderId());

				int difference = Integer.parseInt(currentOrder
						.getCardQuantity())
						- Integer
								.parseInt(currentOrder.getRealCardQuantity() == null
										|| currentOrder.getRealCardQuantity()
												.equals("") ? "0"
										: currentOrder.getRealCardQuantity());
				if (difference == 0) {
					throw new BizServiceException("该订单已准备完成，请不要重复准备 ");
				}
				List<SellOrderCardList> orderCardList = orderBaseQueryBO
						.getSellOrderCardListsByOrderId(orderReadyDTO
								.getOrderId());
				orderCardListReady(orderCardList, orderReadyDTO);
			} else if (orderReadyDTO.getOrderType() != null
					&& orderReadyDTO.getOrderType().equals(
							OrderConst.ORDER_TYPE_ORDER_CHANGE_CARD)) {
				// 换卡订单卡片准备
				SellOrderList orderList = orderBaseQueryBO
						.getSellOrderListByPrimaryKey(orderReadyDTO
								.getOrderListId());
				if (orderList.getCardAmount() != null
						&& orderList.getRealAmount() != null) {
					Integer reallyAmount = 0;
					reallyAmount = Integer.parseInt(orderList.getCardAmount())
							- Integer.parseInt(orderList.getRealAmount());
					if (reallyAmount == 0) {
						throw new BizServiceException("该明细已准备完成，请不要重复准备 ");
					}
				}
				orderReadyDTO.setProductId(orderList.getProductId());
				orderListReadyForChangeOrder(orderList, orderReadyDTO);
			} else {
				// 匿名销售订单卡片准备
				SellOrderList orderList = orderBaseQueryBO
						.getSellOrderListByPrimaryKey(orderReadyDTO
								.getOrderListId());
				if (orderList.getCardAmount() != null
						&& orderList.getRealAmount() != null) {
					Integer reallyAmount = 0;
					reallyAmount = Integer.parseInt(orderList.getCardAmount())
							- Integer.parseInt(orderList.getRealAmount());
					if (reallyAmount == 0) {
						throw new BizServiceException("该明细已准备完成，请不要重复准备 ");
					}
				}
				orderListReady(orderList, orderReadyDTO);
			}
			/***
			 * 更新订单的实际数量
			 */
			String cardQuantity = orderBaseQueryBO
					.selectUnsignCardQuantity(orderReadyDTO.getOrderId());

			SellOrder sellOrder = new SellOrder();
			sellOrder.setOrderId(orderReadyDTO.getOrderId());

			sellOrder.setRealCardQuantity(cardQuantity);

			sellOrder.setModifyTime(DateUtil.getCurrentTime());

			sellOrder.setModifyUser(orderReadyDTO.getLoginUserId());

			orderBO.updateByPrimaryKeySelective(sellOrder);
		} catch (BizServiceException e) {
			throw e;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("系统异常");
		}

	}

	@SuppressWarnings("unchecked")
	public void orderListReady(SellOrderList sellOrderList,
			OrderReadyDTO orderReadyDTO) throws Exception {
		List<SellOrderCardList> sellOrderCardList = new ArrayList<SellOrderCardList>();
		if (OrderConst.FACE_VALUE_TYPE_NOT_STATIC.equals(sellOrderList
				.getFaceValueType())) {
			orderReadyDTO.setOrderListId(sellOrderList.getOrderListId());
			sellOrderCardList = baseOrderDAO.queryForList(
					"selectCardDetailFororderReadyByNotFixDetail",
					orderReadyDTO);
		} else if (OrderConst.FACE_VALUE_TYPE_STATIC.equals(sellOrderList
				.getFaceValueType())) {
			orderReadyDTO.setOrderListId(sellOrderList.getOrderListId());
			sellOrderCardList = baseOrderDAO.queryForList(
					"selectCardDetailFororderReadyByFixDetail", orderReadyDTO);
		}
		Integer reallyAmount = 0;
		try {
			reallyAmount = Integer.parseInt(sellOrderList.getRealAmount());
		} catch (NumberFormatException e) {
			reallyAmount = 0;
		}
		List<String> cardNoList = new ArrayList<String>();
		List<EntityStock> entityStockList = new ArrayList<EntityStock>();
		for (SellOrderCardList sellOrderCardList_temp : sellOrderCardList) {
			reallyAmount++;
			EntityStock entityStock = new EntityStock();

			entityStock.setCardNo(sellOrderCardList_temp.getCardNo());
			cardNoList.add(sellOrderCardList_temp.getCardNo());
			entityStock.setEntityId(orderReadyDTO.getProcessEntityId());
			entityStock.setStockState(OrderConst.CARD_STOCK_READY_OUT);
			sellOrderCardList_temp.setCreateTime(DateUtil.getCurrentTime());
			sellOrderCardList_temp
					.setCreateUser(orderReadyDTO.getLoginUserId());
			sellOrderCardList_temp.setOrderId(sellOrderList.getOrderId());
			sellOrderCardList_temp.setOrderListId(sellOrderList
					.getOrderListId());
			sellOrderCardList_temp.setOrderListId(sellOrderList
					.getOrderListId());
			sellOrderCardList_temp
					.setCardState(OrderConst.MAKE_CARD_STATE_SUCCESS);

			sellOrderCardList_temp.setModifyTime(DateUtil.getCurrentTime());
			sellOrderCardList_temp
					.setModifyUser(orderReadyDTO.getLoginUserId());
			sellOrderCardList_temp
					.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			entityStockList.add(entityStock);
		}
		orderBO.batchInsertSellOrderCardList(sellOrderCardList);

		orderBO.updateEntityStockByPrimaryKey(entityStockList);

		// 记录库存操作日志
		entityStockService.addStockOperaterRecord(cardNoList, orderReadyDTO
				.getOrderId(), orderReadyDTO.getProcessEntityId(),
				Const.FUNCTION_ROLE_SELLER, OrderConst.ORDER_STATE_ORDER_READY,
				OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION,
				OrderConst.CARD_STOCK_OPERATE_TYPE_READY_OUT, orderReadyDTO
						.getLoginUserId());

		sellOrderList.setRealAmount(reallyAmount.toString());
		sellOrderList.setModifyTime(DateUtil.getCurrentTime());
		sellOrderList.setModifyUser(orderReadyDTO.getLoginUserId());
		orderBO.updateSellOrderList(sellOrderList);
	}

	private void orderListReadyForChangeOrder(SellOrderList sellOrderList,
			OrderReadyDTO orderReadyDTO) throws Exception {
		ProductExample example = new ProductExample();
		example.createCriteria().andProductIdEqualTo(orderReadyDTO.getProductId()).andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
		List<Product> products = productDAO.selectByExample(example);
		CardholderDTO cardholderDTO = new CardholderDTO();
		if(null != products && products.size()>0){
			//如果订单中的产品是记名库存卡 则取原卡的持卡人信息
			if("3".equals(products.get(0).getOnymousStat())){
				cardholderDTO = getOrigCardHolder(orderReadyDTO.getOrderId());
			}			
		}
		
		List<SellOrderCardList> sellOrderCardList = new ArrayList<SellOrderCardList>();
		if (OrderConst.FACE_VALUE_TYPE_NOT_STATIC.equals(sellOrderList
				.getFaceValueType())) {
			orderReadyDTO.setOrderListId(sellOrderList.getOrderListId());
			sellOrderCardList = baseOrderDAO.queryForList(
					"selectCardDetailFororderReadyByNotFixDetail",
					orderReadyDTO);
		} else if (OrderConst.FACE_VALUE_TYPE_STATIC.equals(sellOrderList
				.getFaceValueType())) {
			orderReadyDTO.setOrderListId(sellOrderList.getOrderListId());
			sellOrderCardList = baseOrderDAO.queryForList(
					"selectCardDetailFororderReadyByFixDetail", orderReadyDTO);
		}
		Integer reallyAmount = 0;
		try {
			reallyAmount = Integer.parseInt(sellOrderList.getRealAmount());
		} catch (NumberFormatException e) {
			reallyAmount = 0;
		}
		List<String> cardNoList = new ArrayList<String>();
		List<EntityStock> entityStockList = new ArrayList<EntityStock>();
		for (SellOrderCardList sellOrderCardList_temp : sellOrderCardList) {
			reallyAmount++;
			EntityStock entityStock = new EntityStock();

			entityStock.setCardNo(sellOrderCardList_temp.getCardNo());
			cardNoList.add(sellOrderCardList_temp.getCardNo());
			entityStock.setEntityId(orderReadyDTO.getProcessEntityId());
			entityStock.setStockState(OrderConst.CARD_STOCK_READY_OUT);
			if(null!=cardholderDTO && !"".equals(cardholderDTO.getCardholderId())){
				sellOrderCardList_temp.setCardholderId(cardholderDTO.getCardholderId());
				sellOrderCardList_temp.setFirstName(cardholderDTO.getFirstName());
			}
			sellOrderCardList_temp.setCreateTime(DateUtil.getCurrentTime());
			sellOrderCardList_temp
					.setCreateUser(orderReadyDTO.getLoginUserId());
			sellOrderCardList_temp.setOrderId(sellOrderList.getOrderId());
			sellOrderCardList_temp.setOrderListId(sellOrderList
					.getOrderListId());
			sellOrderCardList_temp.setOrderListId(sellOrderList
					.getOrderListId());
			sellOrderCardList_temp
					.setCardState(OrderConst.MAKE_CARD_STATE_SUCCESS);

			sellOrderCardList_temp.setModifyTime(DateUtil.getCurrentTime());
			sellOrderCardList_temp
					.setModifyUser(orderReadyDTO.getLoginUserId());
			sellOrderCardList_temp
					.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			entityStockList.add(entityStock);
		}
		orderBO.batchInsertSellOrderCardList(sellOrderCardList);

		orderBO.updateEntityStockByPrimaryKey(entityStockList);

		// 记录库存操作日志
		entityStockService.addStockOperaterRecord(cardNoList, orderReadyDTO
				.getOrderId(), orderReadyDTO.getProcessEntityId(),
				Const.FUNCTION_ROLE_SELLER, OrderConst.ORDER_STATE_ORDER_READY,
				OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION,
				OrderConst.CARD_STOCK_OPERATE_TYPE_READY_OUT, orderReadyDTO
						.getLoginUserId());

		sellOrderList.setRealAmount(reallyAmount.toString());
		sellOrderList.setModifyTime(DateUtil.getCurrentTime());
		sellOrderList.setModifyUser(orderReadyDTO.getLoginUserId());
		orderBO.updateSellOrderList(sellOrderList);
	}
	
	private CardholderDTO getOrigCardHolder(String orderId){		
			CardholderDTO cardholder = new CardholderDTO();
			List<CardholderDTO> cardHolders = (List<CardholderDTO>)baseOrderDAO.queryForList("getOrigCardHolderForChangeOrder",orderId);
			if(null!=cardHolders && cardHolders.size()>0){
				cardholder=cardHolders.get(0);
			}
			return cardholder;
	}
	
	public void orderCardListReady(List<SellOrderCardList> sellOrderCardList,
			OrderReadyDTO orderReadyDTO) throws Exception {
		List<String> cardNoList;
		if (orderReadyDTO.getCardNoArray() == null) {
			orderReadyDTO.setQueryAll(true);
			List<HashMap> lists = (List<HashMap>) orderBaseQueryBO
					.getCardForSellOrderReady(orderReadyDTO).getData();
			cardNoList = new ArrayList();
			for (HashMap hashmap : lists) {
				cardNoList.add(hashmap.get("cardNo").toString());
			}
		} else {
			cardNoList = Arrays.asList(orderReadyDTO.getCardNoArray());
		}
		List<EntityStock> entityStockList = new ArrayList<EntityStock>();
		int i = 0;
		List<SellOrderCardList> sellOrderCardLists_temp = new ArrayList<SellOrderCardList>();
		for (SellOrderCardList sellOrderCardList_temp : sellOrderCardList) {
			if (cardNoList.size() > i) {
				EntityStock entityStock = new EntityStock();
				entityStock.setCardNo(cardNoList.get(i));
				entityStock.setEntityId(orderReadyDTO.getProcessEntityId());
				entityStock.setStockState(OrderConst.CARD_STOCK_READY_OUT);
				entityStockList.add(entityStock);
				SellOrderCardList sellOrderCardList_temp2 = new SellOrderCardList();
				sellOrderCardList_temp2
						.setOrderCardListId(sellOrderCardList_temp
								.getOrderCardListId());
				sellOrderCardList_temp2
						.setCardState(OrderConst.MAKE_CARD_STATE_SUCCESS);
				sellOrderCardList_temp2.setCardNo(cardNoList.get(i));
				sellOrderCardList_temp2
						.setModifyTime(DateUtil.getCurrentTime());
				sellOrderCardList_temp2.setModifyUser(orderReadyDTO
						.getLoginUserId());
				sellOrderCardLists_temp.add(sellOrderCardList_temp2);
			}
			i++;
		}
		orderBO.batchUpdateSellOrderCardList(sellOrderCardLists_temp);

		orderBO.updateEntityStockByPrimaryKey(entityStockList);

		// 记录库存操作日志
		entityStockService.addStockOperaterRecord(cardNoList, orderReadyDTO
				.getOrderId(), orderReadyDTO.getProcessEntityId(),
				Const.FUNCTION_ROLE_SELLER, OrderConst.ORDER_STATE_ORDER_READY,
				OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION,
				OrderConst.CARD_STOCK_OPERATE_TYPE_READY_OUT, orderReadyDTO
						.getLoginUserId());

	}

	/**
	 * 删除记录 的同时还应该将卡入库
	 */
	public void deleteRecord(OrderReadyDTO orderReadyDTO)
			throws BizServiceException {
		try {
			SellOrderCardList sellOrderCardList = orderBO
					.getSellOrderCardListByPrimaryKey(orderReadyDTO
							.getOrderCardListId());

			EntityStock entityStock = new EntityStock();

			entityStock.setCardNo(sellOrderCardList.getCardNo());

			entityStock.setEntityId(orderReadyDTO.getProcessEntityId());

			String orderListId = sellOrderCardList.getOrderListId();

			SellOrderList sellOrderList = orderBaseQueryBO
					.getSellOrderListByPrimaryKey(orderListId);

			Integer realAmont = new Integer(sellOrderList.getRealAmount());
			realAmont--;
			sellOrderList.setRealAmount(realAmont.toString());

			entityStock.setStockState(OrderConst.CARD_STOCK_IN);

			List<EntityStock> entityStockList = new ArrayList<EntityStock>();
			entityStockList.add(entityStock);

			orderBO.updateEntityStockByPrimaryKey(entityStockList);
			// 记录库存操作日志
			List<String> cardNoList = new ArrayList<String>();
			cardNoList.add(entityStock.getCardNo());
			entityStockService.addStockOperaterRecord(cardNoList, sellOrderList
					.getOrderId(), orderReadyDTO.getProcessEntityId(),
					Const.FUNCTION_ROLE_SELLER,
					OrderConst.ORDER_STATE_ORDER_READY,
					OrderConst.ORDER_FLOW_OPRATION_DELETE,
					OrderConst.CARD_STOCK_IN, orderReadyDTO.getLoginUserId());
			orderBO.deleteSellOrderCardListByPrimaryKey(orderReadyDTO
					.getOrderCardListId());

			orderBO.updateSellOrderList(sellOrderList);

		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("删除订单明细失败");
		}

	}

	/**
	 *删除准备的卡片
	 */
	public void deleteAllRecord(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		try {
			if (sellOrderDTO.getOrderType() != null
					&& (sellOrderDTO
							.getOrderType()
							.equals(
									OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN)
							|| sellOrderDTO
									.getOrderType()
									.equals(
											OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN) || sellOrderDTO
							.getOrderType()
							.equals(
									OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_SIGN))) {
				deleteAllSign(sellOrderDTO);
			} else {
				deleteAll(sellOrderDTO);
			}

			/***
			 * 更新订单的实际数量
			 */

			SellOrder sellOrder = new SellOrder();
			sellOrder.setOrderId(sellOrderDTO.getOrderId());

			sellOrder.setRealCardQuantity("0");

			sellOrder.setModifyTime(DateUtil.getCurrentTime());

			sellOrder.setModifyUser(sellOrderDTO.getLoginUserId());

			orderBO.updateByPrimaryKeySelective(sellOrder);

			SellOrderList sellOrderList = new SellOrderList();

			sellOrderList.setRealAmount("0");

			sellOrderList.setModifyTime(DateUtil.getCurrentTime());

			sellOrderList.setModifyUser(sellOrderDTO.getLoginUserId());

			SellOrderListExample sellOrderListExample = new SellOrderListExample();

			sellOrderListExample.createCriteria().andOrderIdEqualTo(
					sellOrderDTO.getOrderId());

			orderBO.updateSellOrderListByExample(sellOrderList,
					sellOrderListExample);

		} catch (BizServiceException e) {
			throw e;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("删除全部卡片失败");
		}

	}

	/**
	 * 删除记名准备卡片
	 * 
	 * @param sellOrderDTO
	 * @throws BizServiceException
	 */
	public void deleteAllSign(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		try {
			List<SellOrderCardList> sellOrderList = orderBaseQueryBO
					.getSellOrderCardListByorderId(sellOrderDTO.getOrderId());
			if (sellOrderList.size() == 0) {
				throw new BizServiceException("订单中无准备卡无须删除 ");
			}
			List<EntityStock> entityStockList = new ArrayList<EntityStock>();
			List<SellOrderCardList> sellorderList_tmp = new ArrayList<SellOrderCardList>();
			List<String> cardNoList = new ArrayList<String>();
			for (SellOrderCardList sellOrderCard : sellOrderList) {
				EntityStock entityStock = new EntityStock();
				entityStock.setCardNo(sellOrderCard.getCardNo());
				entityStock.setEntityId(sellOrderDTO.getProcessEntityId());
				entityStock.setStockState(OrderConst.CARD_STOCK_IN);
				entityStockList.add(entityStock);
				cardNoList.add(sellOrderCard.getCardNo());

				sellOrderCard.setCardNo("");
				sellOrderCard.setCardState("");
				sellOrderCard.setModifyTime(DateUtil.getCurrentTime());
				sellOrderCard.setModifyUser(sellOrderDTO.getLoginUserId());
				sellorderList_tmp.add(sellOrderCard);

			}
			orderBO.batchUpdateSellOrderCardList(sellorderList_tmp);

			orderBO.updateEntityStockByPrimaryKey(entityStockList);

			entityStockService.addStockOperaterRecord(cardNoList, sellOrderDTO
					.getOrderId(), sellOrderDTO.getProcessEntityId(),
					Const.FUNCTION_ROLE_SELLER,
					OrderConst.ORDER_STATE_ORDER_READY,
					OrderConst.ORDER_FLOW_OPRATION_DELETE,
					OrderConst.CARD_STOCK_IN, sellOrderDTO.getLoginUserId());
		} catch (BizServiceException e) {
			throw e;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("删除全部卡片失败");
		}

	}

	/**
	 * 删除匿名准备卡片
	 */
	public void deleteAll(SellOrderDTO sellOrderDTO) throws BizServiceException {
		try {

			String cardQuantity = orderBaseQueryBO
					.selectUnsignCardQuantity(sellOrderDTO.getOrderId());
			if ("0".equals(cardQuantity)) {
				throw new BizServiceException("订单中无卡无须删除 ");
			}
			/**
			 * 同时所有的明细中的卡实际数量至为0
			 */
			List<SellOrderCardList> orderCardList_list = orderBaseQueryBO
					.getSellOrderCardListDetailByOrderId(sellOrderDTO
							.getOrderId());

			List<EntityStock> entityStockList = new ArrayList<EntityStock>();
			List<String> cardNoList = new ArrayList<String>();
			for (SellOrderCardList detail : orderCardList_list) {
				EntityStock entityStcok = new EntityStock();
				entityStcok.setCardNo(detail.getCardNo());
				cardNoList.add(detail.getCardNo());
				entityStcok.setEntityId(sellOrderDTO.getProcessEntityId());
				entityStcok.setStockState(OrderConst.CARD_STOCK_IN);
				entityStockList.add(entityStcok);
			}
			orderBO.updateEntityStockByPrimaryKey(entityStockList);
			entityStockService.addStockOperaterRecord(cardNoList, sellOrderDTO
					.getOrderId(), sellOrderDTO.getProcessEntityId(),
					Const.FUNCTION_ROLE_SELLER,
					OrderConst.ORDER_STATE_ORDER_READY,
					OrderConst.ORDER_FLOW_OPRATION_DELETE,
					OrderConst.CARD_STOCK_IN, sellOrderDTO.getLoginUserId());

			orderBO.deleteSellOrderCardListByOrderId(sellOrderDTO.getOrderId());
		} catch (BizServiceException e) {
			throw e;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("删除全部卡片失败");
		}
	}

	/**
	 * 添加采购订单
	 * 
	 * @param sellOrderQueryDTO
	 * @return
	 * @throws BizServiceException
	 */
	public SellBuyOrderDTO insertBuyOrder(SellBuyOrderDTO sellBuyOrderDTO)
			throws BizServiceException {
		try {
			// 原始订单类型
			String orderType = sellBuyOrderDTO.getOrderType();
			if (orderType
					.equals(OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_SIGN)
					|| orderType
							.equals(OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN)
					|| orderType
							.equals(OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_SIGN)
					|| orderType
							.equals(OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN)
					|| orderType
							.equals(OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_SIGN)) {
				String[] orderIdArray = sellBuyOrderDTO.getOrderIdArray()
						.split(",");
				String isGenerate = orderBaseQueryBO
						.checkSellOrderSignGenerateBuyerOrder(orderIdArray[0]);
				if (!isGenerate.equals("0")) {
					throw new BizServiceException("该记名订单已经生成采购订单，请不要重复生成!");
				}
			}
			String sellerId = sellBuyOrderDTO.getDefaultEntityId();
			String productId = sellBuyOrderDTO.getProductId();
			String sellContractId = sellBuyOrderDTO.getSellContractId();
			String orderId = commonsDAO.getNextValueOfSequence("TB_SELL_ORDER");
			Integer cardQuantity = 0;
			// 合同
			SellContract sellContract = orderBaseQueryBO
					.getSellContractById(sellContractId);
			SellProdContract prodContract = orderBaseQueryBO
					.getSellProductContractByExample(sellContractId, productId);
			// 营销机构
			Seller seller = orderBaseQueryBO.getSellerByExample(sellerId);
			DeliveryContact contact = orderBaseQueryBO
					.getDeliveryContactForBuyOrder(sellerId);
			// 订单
			SellOrder sellOrder = new SellOrder();
			sellOrder.setOrderId(orderId);

			if (OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_UNSIGN
					.equals(orderType)
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_UNSIGN
							.equals(orderType)
					|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_UNSIGN
							.equals(orderType)
					|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_UNSIGN
							.equals(orderType)) {
				// 合同类型
				if (DataBaseConstant.SELL_CONTRACT_ISSUER.equals(sellContract
						.getContractType())) {
					sellOrder
							.setOrderType(OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN);
				}
				if (DataBaseConstant.SELL_CONTRACT_SELLER.equals(sellContract
						.getContractType())) {
					sellOrder
							.setOrderType(OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_UNSIGN);
				}
				// // 新订单的失效时间和服务id
				// String[] orderIdArray = sellBuyOrderDTO.getOrderIdArray()
				// .split(",");
				// String sValidityPeriod = "";
				// String sServiceId = "";
				// SellOrder olderOrder = orderBaseQueryBO
				// .getSellOrder(orderIdArray[0]);
				// sValidityPeriod = olderOrder.getValidityPeriod();
				// sServiceId = olderOrder.getServiceId();
				//
				// sellOrder.setValidityPeriod(sValidityPeriod);
				// sellOrder.setServiceId(sServiceId);
				// 订单明细
				List<SellOrderListDTO> orderListDTOs = sellBuyOrderDTO
						.getSellOrderListDTOs();
				// List<SellOrderList> orderLists =
				// orderBaseQueryBO.getOrderListForBuyOrderInput(orderIdArray);
				List<SellOrderList> addOrderLists = new ArrayList<SellOrderList>();
				for (SellOrderListDTO orderListDTO : orderListDTOs) {
					// 新订单的失效时间和服务id
					String[] orderIdArray = sellBuyOrderDTO.getOrderIdArray()
							.split(",");
					String validityPeriodString = "";
					SellOrderList sellOrderList = orderBaseQueryBO
							.getSellOrderListByOrderId(orderIdArray[0]).get(0);
					validityPeriodString = sellOrderList.getValidityPeriod();
					SellOrderList orderList = new SellOrderList();
					ReflectionUtil.copyProperties(orderListDTO, orderList);

					Integer cardAmt = new Integer(orderList.getCardAmount());
					BigDecimal faceValue = new BigDecimal(Amount
							.getDataBaseAmount(orderList.getFaceValue()));
					orderList.setFaceValue(faceValue.toString());
					BigDecimal orderListPrice = faceValue
							.multiply(new BigDecimal(orderList.getCardAmount()));
					cardQuantity += cardAmt;
					orderList.setOrderListId(commonsDAO
							.getNextValueOfSequence("TB_SELL_ORDER_LIST"));
					orderList.setOrderId(orderId);
					orderList.setValidityPeriod(validityPeriodString);
					orderList.setCardIssueFee(prodContract.getCardFee());
					orderList.setPackageId(sellOrderList.getPackageId());
					orderList.setPackageFee(sellOrderList.getPackageFee());
					orderList.setRealAmount("0");
					orderList.setTotalPrice(orderListPrice.toString());

					orderList.setCreateUser(sellBuyOrderDTO.getLoginUserId());
					orderList.setCreateTime(DateUtil.getCurrentTime());
					orderList.setModifyUser(sellBuyOrderDTO.getLoginUserId());
					orderList.setModifyTime(DateUtil.getCurrentTime());
					orderList.setDataState(DataBaseConstant.DATA_STATE_NORMAL);

					addOrderLists.add(orderList);
				}
				commonsDAO.batchInsert(
						"TB_SELL_ORDER_LIST.abatorgenerated_insert",
						addOrderLists);
			} else {
				// 记名订单
				// 合同类型
				if (DataBaseConstant.SELL_CONTRACT_ISSUER.equals(sellContract
						.getContractType())) {
					sellOrder
							.setOrderType(OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_SIGN);
				}
				if (DataBaseConstant.SELL_CONTRACT_SELLER.equals(sellContract
						.getContractType())) {
					sellOrder
							.setOrderType(OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_SIGN);
				}
				sellOrder.setCardLayoutId(sellBuyOrderDTO.getCardLayoutId());
				sellOrder.setFaceValueType(sellBuyOrderDTO.getFaceValueType());
				sellOrder.setFaceValue(Amount.getDataBaseAmount(sellBuyOrderDTO
						.getFaceValue()));

				List<UnionOrder> unionOrders = new ArrayList<UnionOrder>();
				String[] orderIdArray = sellBuyOrderDTO.getOrderIdArray()
						.split(",");
				String sValidityPeriod = "";
				String sServiceId = "";
				for (String oldOrderId : orderIdArray) {
					SellOrder olderOrder = orderBaseQueryBO
							.getSellOrder(oldOrderId);
					sValidityPeriod = olderOrder.getValidityPeriod();
					sServiceId = olderOrder.getServiceId();
					UnionOrder unionOrder = new UnionOrder();
					unionOrder.setNewOrder(orderId);
					unionOrder.setOldOrder(oldOrderId);
					if (OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN
							.equals(olderOrder.getOrderType())
							|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN
									.equals(olderOrder.getOrderType())
							|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_SIGN
									.equals(olderOrder.getOrderType())
							|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN
									.equals(olderOrder.getOrderType())) {
						unionOrder.setLeafNode(OrderConst.IS_LEAF_NODE);
					} else {
						unionOrder.setLeafNode(OrderConst.IS_NOT_LEAF_NODE);
					}
					cardQuantity = cardQuantity
							+ new Integer(olderOrder.getCardQuantity());
					unionOrders.add(unionOrder);
					sellOrder.setPackageId(olderOrder.getPackageId());
					sellOrder.setPackageFee(olderOrder.getPackageFee());
				}
				sellOrder.setValidityPeriod(sValidityPeriod);
				sellOrder.setServiceId(sServiceId);

				commonsDAO.batchInsert("TB_UNION_ORDER.abatorgenerated_insert",
						unionOrders);
			}
			sellOrder.setOrderDate(DateUtil.getCurrentDateStr());
			sellOrder.setFirstEntityId(sellBuyOrderDTO.getDefaultEntityId());
			// 订单处理方(合同卖方)
			sellOrder.setProcessEntityId(sellBuyOrderDTO.getContractSeller());
			sellOrder.setOrderState(OrderConst.ORDER_STATE_DRAFT);
			sellOrder.setProductId(productId);

			// 无需实时支付
			sellOrder.setDeliveryMeans("4");
			sellOrder.setDeliveryFee(sellContract.getDeliveryFee());
			if (null != contact) {
				sellOrder.setDeliveryPoint(contact.getDeliveryPointId());
				sellOrder.setOrderContact(contact.getDeliveryContactId());
			}

			// 发票
			sellOrder
					.setInvoiceAddresses(sellBuyOrderDTO.getInvoiceAddresses());
			sellOrder.setInvoiceCompanyName(sellBuyOrderDTO
					.getInvoiceCompanyName());
			sellOrder.setInvoiceDate(sellBuyOrderDTO.getInvoiceDate());
			sellOrder.setInvoiceItem(sellBuyOrderDTO.getInvoiceItem());
			sellOrder.setInvoiceItemId(sellBuyOrderDTO.getInvoiceItemId());

			// 支付
			sellOrder.setPaymentTerm(seller.getPaymentTerm());
			sellOrder.setPaymentDelay(seller.getPaymentDelay());
//			sellOrder.setPaymentState("0");
			// 费用
			sellOrder.setDiscountFee("0");
			sellOrder.setAdditionalFee("0");
			sellOrder.setCardIssueFee(prodContract.getCardFee());
			sellOrder.setAnnualFee(prodContract.getAnnualFee());

			// totalPrice = Amount.addAmount(totalPrice,
			// getOrderTotalPrice(sellOrder));
			sellOrder.setTotalPrice("0");

			sellOrder.setCardQuantity(cardQuantity.toString());
			// 订单来源2:订单合并
			sellOrder.setOrderSource("2");
			sellOrder.setIsCheckCard("0");  //可选验卡
			sellOrder.setCreateUser(sellBuyOrderDTO.getLoginUserId());
			sellOrder.setCreateTime(DateUtil.getCurrentTime());
			sellOrder.setModifyUser(sellBuyOrderDTO.getLoginUserId());
			sellOrder.setModifyTime(DateUtil.getCurrentTime());
			sellOrder.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			sellOrderDAO.insert(sellOrder);
			// 记录订单流程

			// 添加订单流程信息
			SellOrderFlow orderFlow = new SellOrderFlow();
			orderFlow.setOrderFlowId(commonsDAO
					.getNextValueOfSequence("TB_SELL_ORDER_FLOW"));
			orderFlow.setOrderId(sellOrder.getOrderId());
			orderFlow.setEntityId(sellBuyOrderDTO.getDefaultEntityId());
			orderFlow.setOrderFlowNode(OrderConst.ORDER_FLOW_INPUT);
			orderFlow.setOperateType(OrderConst.ORDER_FLOW_OPRATION_ADD);
			orderFlow.setCreateTime(DateUtil.getCurrentTime());
			orderFlow.setModifyTime(DateUtil.getCurrentTime());
			orderFlow.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			orderFlow.setCreateUser(sellBuyOrderDTO.getLoginUserId());
			orderFlow.setModifyUser(sellBuyOrderDTO.getLoginUserId());
			stockOrderCommonService.addOrderFlow(orderFlow);
			// 计算总金额
			orderCountTotalPrice.countTotalPriceForOrderByOrderType(orderId);

		} catch (BizServiceException ex) {
			throw ex;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("生成采购订单失败！");
		}
		return null;
	}

	/**
	 * 添加代发卡采购订单
	 * 
	 * @param sellOrderQueryDTO
	 * @return
	 * @throws BizServiceException
	 */
	public SellBuyOrderDTO insertLoyaltyBuyOrder(SellBuyOrderDTO sellBuyOrderDTO)
			throws BizServiceException {
		try {
			// 原始订单类型
			String orderType = sellBuyOrderDTO.getOrderType();
			if (orderType
					.equals(OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_SIGN)
					|| orderType
							.equals(OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_SIGN)
					|| orderType
							.equals(OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_SIGN)) {
				String[] orderIdArray = sellBuyOrderDTO.getOrderIdArray()
						.split(",");
				String isGenerate = orderBaseQueryBO
						.checkSellOrderSignGenerateBuyerOrder(orderIdArray[0]);
				if (!isGenerate.equals("0")) {
					throw new BizServiceException("该记名订单已经生成采购订单,请不要重复生成!");
				}
			}
			String sellerId = sellBuyOrderDTO.getDefaultEntityId();
			String productId = sellBuyOrderDTO.getProductId();
			String sellContractId = sellBuyOrderDTO.getSellContractId();
			logger.debug(sellContractId);
			String orderId = commonsDAO.getNextValueOfSequence("TB_SELL_ORDER");
			Integer cardQuantity = 0;
			// 合同
			LoyaltyContract loyaltyContract = orderBaseQueryBO
					.getLoyaltyContractById(sellContractId);
			LoyaltyProdContract prodContract = orderBaseQueryBO
					.getLoyaltyProductContractByExample(sellContractId,
							productId);
			// 发卡机构
			//Issuer issuer = orderBaseQueryBO.getIssuerByExample(sellerId);
			// 送货信息
			DeliveryContact contact = orderBaseQueryBO
					.getDeliveryContactForLoyaltyBuyOrder(sellerId);
			// 订单
			SellOrder sellOrder = new SellOrder();
			sellOrder.setOrderId(orderId);

			if (OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_UNSIGN
					.equals(orderType)
					|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN
							.equals(orderType)) {
				// 合同类型
				sellOrder
						.setOrderType(OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_UNSIGN);
				// 新订单的失效时间
				String[] orderIdArray = sellBuyOrderDTO.getOrderIdArray()
						.split(",");
				String validityPeriodString = "";
				SellOrderList sellOrderList = orderBaseQueryBO
						.getSellOrderListByOrderId(orderIdArray[0]).get(0);
				validityPeriodString = sellOrderList.getValidityPeriod();
				sellOrder.setValidityPeriod(validityPeriodString);

				// 订单明细
				List<SellOrderListDTO> orderListDTOs = sellBuyOrderDTO
						.getSellOrderListDTOs();
				// List<SellOrderList> orderLists =
				// orderBaseQueryBO.getOrderListForBuyOrderInput(orderIdArray);
				List<SellOrderList> addOrderLists = new ArrayList<SellOrderList>();
				for (SellOrderListDTO orderListDTO : orderListDTOs) {

					SellOrderList orderList = new SellOrderList();
					ReflectionUtil.copyProperties(orderListDTO, orderList);

					Integer cardAmt = new Integer(orderList.getCardAmount());
					BigDecimal faceValue = new BigDecimal(Amount
							.getDataBaseAmount(orderList.getFaceValue()));
					orderList.setFaceValue(faceValue.toString());
					sellOrder.setFaceValue(faceValue.toString());
					BigDecimal orderListPrice = faceValue
							.multiply(new BigDecimal(orderList.getCardAmount()));
					cardQuantity += cardAmt;
					orderList.setValidityPeriod(validityPeriodString);
					orderList.setOrderListId(commonsDAO
							.getNextValueOfSequence("TB_SELL_ORDER_LIST"));
					orderList.setOrderId(orderId);
					orderList.setCardIssueFee(prodContract.getCardFee());
					orderList.setPackageFee("0");
					orderList.setRealAmount("0");
					orderList.setTotalPrice(orderListPrice.toString());

					orderList.setCreateUser(sellBuyOrderDTO.getLoginUserId());
					orderList.setCreateTime(DateUtil.getCurrentTime());
					orderList.setModifyUser(sellBuyOrderDTO.getLoginUserId());
					orderList.setModifyTime(DateUtil.getCurrentTime());
					orderList.setDataState(DataBaseConstant.DATA_STATE_NORMAL);

					addOrderLists.add(orderList);
				}

				commonsDAO.batchInsert(
						"TB_SELL_ORDER_LIST.abatorgenerated_insert",
						addOrderLists);
			} else {
				// 记名订单
				// 合同类型

				sellOrder
						.setOrderType(OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_SIGN);

				sellOrder.setCardLayoutId(sellBuyOrderDTO.getCardLayoutId());
				sellOrder.setFaceValueType(sellBuyOrderDTO.getFaceValueType());
				sellOrder.setFaceValue(Amount.getDataBaseAmount(sellBuyOrderDTO
						.getFaceValue()));

				List<UnionOrder> unionOrders = new ArrayList<UnionOrder>();
				String[] orderIdArray = sellBuyOrderDTO.getOrderIdArray()
						.split(",");
				String sValidityPeriod = "";
				String sServiceId = "";
				for (String oldOrderId : orderIdArray) {
					SellOrder olderOrder = orderBaseQueryBO
							.getSellOrder(oldOrderId);
					sValidityPeriod = olderOrder.getValidityPeriod();
					sServiceId = olderOrder.getServiceId();
					UnionOrder unionOrder = new UnionOrder();
					unionOrder.setNewOrder(orderId);
					unionOrder.setOldOrder(oldOrderId);
					// if (OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_SIGN
					// .equals(olderOrder.getOrderType())
					// || OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_SIGN
					// .equals(olderOrder.getOrderType())) {
					// unionOrder.setLeafNode(OrderConst.IS_NOT_LEAF_NODE);
					// } else {
					// unionOrder.setLeafNode(OrderConst.IS_LEAF_NODE);
					// }
					unionOrder.setLeafNode(OrderConst.IS_NOT_LEAF_NODE);
					cardQuantity = cardQuantity
							+ new Integer(olderOrder.getCardQuantity());
					unionOrders.add(unionOrder);
				}
				sellOrder.setValidityPeriod(sValidityPeriod);
				sellOrder.setServiceId(sServiceId);
				commonsDAO.batchInsert("TB_UNION_ORDER.abatorgenerated_insert",
						unionOrders);
			}

			sellOrder.setOrderDate(DateUtil.getCurrentDateStr());
			sellOrder.setFirstEntityId(sellBuyOrderDTO.getDefaultEntityId());
			// 订单处理方(合同卖方)
			sellOrder.setProcessEntityId(sellBuyOrderDTO.getContractSeller());
			sellOrder.setOrderState(OrderConst.ORDER_STATE_DRAFT);
			sellOrder.setProductId(productId);

			// 无需实时支付
			sellOrder.setDeliveryMeans("4");
			sellOrder.setDeliveryFee(loyaltyContract.getDeliveryFee());
			if (null != contact) {
				sellOrder.setDeliveryPoint(contact.getDeliveryPointId());
				sellOrder.setOrderContact(contact.getDeliveryContactId());
			}

			// 发票
			// sellOrder.setinv

			// 支付
			// sellOrder.setPaymentTerm(seller.getPaymentTerm());
			// sellOrder.setPaymentDelay(seller.getPaymentDelay());
			sellOrder.setPaymentState("0");
			// 费用
			sellOrder.setDiscountFee("0");
			sellOrder.setAdditionalFee("0");
			sellOrder.setPackageFee("0");
			sellOrder.setCardIssueFee(prodContract.getCardFee());
			sellOrder.setAnnualFee(prodContract.getAnnualFee());

			// totalPrice = Amount.addAmount(totalPrice,
			// getOrderTotalPrice(sellOrder));
			sellOrder.setTotalPrice("0");

			sellOrder.setCardQuantity(cardQuantity.toString());
			// 订单来源2:订单合并
			sellOrder.setOrderSource("2");

			sellOrder.setCreateUser(sellBuyOrderDTO.getLoginUserId());
			sellOrder.setCreateTime(DateUtil.getCurrentTime());
			sellOrder.setModifyUser(sellBuyOrderDTO.getLoginUserId());
			sellOrder.setModifyTime(DateUtil.getCurrentTime());
			sellOrder.setDataState(DataBaseConstant.DATA_STATE_NORMAL);

			sellOrderDAO.insert(sellOrder);
			// 计算总金额
			orderCountTotalPrice.countTotalPriceForOrderByOrderType(orderId);

		} catch (BizServiceException ex) {
			throw ex;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("生成采购订单失败！");
		}
		return null;
	}

	public SellOrderDTO getSellOrder(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		try {
			SellOrder resultSellOrder = sellOrderDAO
					.selectByPrimaryKey(sellOrderDTO.getOrderId());
			SellOrderDTO resultSellOrderDTO = new SellOrderDTO();
			ReflectionUtil.copyProperties(resultSellOrder, resultSellOrderDTO);
			return resultSellOrderDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询订单信息失败！");
		}

	}

	/**
	 * @author Yifeng.Shi
	 * @serialData 2011-11-04
	 * @param SellOrderReadyDTO
	 * @return null
	 * @throws BizServiceException
	 *             暂用于销售匿名订单一次性全部准备所有明细
	 * */
	public void orderListAllReady(OrderReadyDTO orderReadyDTO)
			throws BizServiceException {
		try {
			SellOrder currentOrder = orderBaseQueryBO
					.getSellOrder(orderReadyDTO.getOrderId());

			/* 通过订单ID获取匿名订单的明细列表 */
			List<SellOrderList> orderLists = orderBaseQueryBO
					.getSellOrderListByOrderId(orderReadyDTO.getOrderId());

			/* 遍历明细列表，循环逐个准备 */
			for (SellOrderList orderList : orderLists) {
				if (Integer.parseInt(orderList.getCardAmount()) > Integer
						.parseInt((orderList.getRealAmount() == null || orderList
								.getRealAmount().equals("")) ? "0" : orderList
								.getRealAmount())) {
					orderListReady(orderList, orderReadyDTO);
				}
			}

		} catch (BizServiceException ex) {
			logger.warn(ex.getMessage());
			throw ex;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BizServiceException("订单全部准备失败");
		}

	}

	public void updateOrigCardState(
			SellOrderOrigCardListDTO sellOrderOrigCardListDTO)
			throws BizServiceException {
		try {
			List<String> origCardListIds = new ArrayList<String>();
			for (String origCardListId : sellOrderOrigCardListDTO
					.getOrigcardListIds()) {
				origCardListIds.add(origCardListId);
			}
			SellOrderOrigCardListExample example = new SellOrderOrigCardListExample();
			List<String> cardStateList;
			if (sellOrderOrigCardListDTO.getCardSate().equals(
					OrderConst.ORIG_CARD_STAT_CHECK)) {
				example
						.createCriteria()
						.andOrderIdEqualTo(
								sellOrderOrigCardListDTO.getOrderId())
						.andOrigcardListIdIn(origCardListIds)
						.andCardSateEqualTo(OrderConst.ORIG_CARD_STAT_UNCHECK)
						.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
			} else if (sellOrderOrigCardListDTO.getCardSate().equals(
					OrderConst.ORIG_CARD_STAT_ENTSTOCK)) {
				cardStateList = new ArrayList<String>();
				cardStateList.add(OrderConst.ORIG_CARD_STAT_CHECK);
				cardStateList.add(OrderConst.ORIG_CARD_STAT_DESTORY);
				example.createCriteria().andOrderIdEqualTo(
						sellOrderOrigCardListDTO.getOrderId())
						.andOrigcardListIdIn(origCardListIds).andCardSateIn(
								cardStateList).andDataStateEqualTo(
								DataBaseConstant.DATA_STATE_NORMAL);
			} else if (sellOrderOrigCardListDTO.getCardSate().equals(
					OrderConst.ORIG_CARD_STAT_DESTORY)) {
				cardStateList = new ArrayList<String>();
				cardStateList.add(OrderConst.ORIG_CARD_STAT_CHECK);
				cardStateList.add(OrderConst.ORIG_CARD_STAT_ENTSTOCK);
				example.createCriteria().andOrderIdEqualTo(
						sellOrderOrigCardListDTO.getOrderId())
						.andOrigcardListIdIn(origCardListIds).andCardSateIn(
								cardStateList).andDataStateEqualTo(
								DataBaseConstant.DATA_STATE_NORMAL);
			}
			SellOrderOrigCardList origCardList = new SellOrderOrigCardList();
			origCardList.setCardSate(sellOrderOrigCardListDTO.getCardSate());
			sellOrderOrigCardListDAO.updateByExampleSelective(origCardList,
					example);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BizServiceException("操作失败");
		}
	}

	/**
	 * 换卡订单全部准备
	 * */
	public void orderReadyForChangeCard(OrderReadyDTO orderReadyDTO)
			throws BizServiceException {
		try {
			SellOrder currentOrder = orderBaseQueryBO
					.getSellOrder(orderReadyDTO.getOrderId());

			/* 通过订单ID获取匿名订单的明细列表 */
			List<SellOrderList> orderLists = orderBaseQueryBO
					.getSellOrderListByOrderId(orderReadyDTO.getOrderId());

			/* 遍历明细列表，循环逐个准备 */
			for (SellOrderList orderList : orderLists) {
				if (Integer.parseInt(orderList.getCardAmount()) > Integer
						.parseInt((orderList.getRealAmount() == null || orderList
								.getRealAmount().equals("")) ? "0" : orderList
								.getRealAmount())) {
					orderReadyDTO.setProductId(orderList.getProductId());
					orderListReady(orderList, orderReadyDTO);
				}
			}

		} catch (BizServiceException ex) {
			logger.warn(ex.getMessage());
			throw ex;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BizServiceException("订单全部准备失败");
		}

	}

	/*
	 * private String getOrderTotalPrice(SellOrder sellOrder){ String
	 * orderTotalPrice = "0"; orderTotalPrice =
	 * Amount.addAmount(orderTotalPrice,
	 * Amount.getDataBaseAmount(sellOrder.getCardIssueFee())); orderTotalPrice =
	 * Amount
	 * .addAmount(orderTotalPrice,Amount.getDataBaseAmount(sellOrder.getAnnualFee
	 * ())); orderTotalPrice =
	 * Amount.addAmount(orderTotalPrice,Amount.getDataBaseAmount
	 * (sellOrder.getDeliveryFee())); orderTotalPrice =
	 * Amount.addAmount(orderTotalPrice
	 * ,Amount.getDataBaseAmount(sellOrder.getAdditionalFee())); //折扣费(-)
	 * //BigDecimal bg orderTotalPrice = orderTotalPrice.subtract(new
	 * BigDecimal(sellOrder.getDiscountFee())); return orderTotalPrice; }
	 */

	public OrderBO getOrderBO() {
		return orderBO;
	}

	public void setOrderBO(OrderBO orderBO) {
		this.orderBO = orderBO;
	}

	public BaseDAO getBaseOrderDAO() {
		return baseOrderDAO;
	}

	public void setBaseOrderDAO(BaseDAO baseOrderDAO) {
		this.baseOrderDAO = baseOrderDAO;
	}

	public CommonsDAO getCommonsDAO() {
		return commonsDAO;
	}

	public void setCommonsDAO(CommonsDAO commonsDAO) {
		this.commonsDAO = commonsDAO;
	}

	public SellOrderDAO getSellOrderDAO() {
		return sellOrderDAO;
	}

	public void setSellOrderDAO(SellOrderDAO sellOrderDAO) {
		this.sellOrderDAO = sellOrderDAO;
	}

	public OrderCountTotalPrice getOrderCountTotalPrice() {
		return orderCountTotalPrice;
	}

	public void setOrderCountTotalPrice(
			OrderCountTotalPrice orderCountTotalPrice) {
		this.orderCountTotalPrice = orderCountTotalPrice;
	}

	public ProductDAO getProductDAO() {
		return productDAO;
	}

	public void setProductDAO(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}

}
