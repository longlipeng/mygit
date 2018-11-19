package com.huateng.univer.order.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.huateng.framework.constant.Const;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.SellOrderCardListDAO;
import com.huateng.framework.ibatis.dao.SellOrderDAO;
import com.huateng.framework.ibatis.dao.SellOrderOrigCardListDAO;
import com.huateng.framework.ibatis.model.EntityStock;
import com.huateng.framework.ibatis.model.SellOrder;
import com.huateng.framework.ibatis.model.SellOrderCardList;
import com.huateng.framework.ibatis.model.SellOrderCardListExample;
import com.huateng.framework.ibatis.model.SellOrderExample;
import com.huateng.framework.ibatis.model.SellOrderList;
import com.huateng.framework.ibatis.model.SellOrderListExample;
import com.huateng.framework.ibatis.model.SellOrderOrigCardList;
import com.huateng.framework.ibatis.model.SellOrderOrigCardListExample;
import com.huateng.framework.util.DateUtil;
import com.huateng.univer.entitystock.service.EntityStockService;
import com.huateng.univer.order.business.bo.OrderBO;
import com.huateng.univer.order.business.bo.OrderBaseQueryBO;
import com.huateng.univer.order.business.service.OrderCardListService;
import com.huateng.univer.order.business.service.SendBackOrderService;

public class SendBackOrderServiceImpl implements SendBackOrderService {
	private Logger logger = Logger.getLogger(SendBackOrderServiceImpl.class);
	private OrderBO orderBO;

	private OrderBaseQueryBO orderBaseQueryBO;
	private EntityStockService entityStockService;
	private OrderCardListService orderCardListService;
	private SellOrderCardListDAO sellOrderCardListDAO;

	private SellOrderOrigCardListDAO sellOrderOrigCardListDAO;
	private SellOrderDAO sellOrderDAO;
	public SellOrderOrigCardListDAO getSellOrderOrigCardListDAO() {
		return sellOrderOrigCardListDAO;
	}

	public void setSellOrderOrigCardListDAO(
			SellOrderOrigCardListDAO sellOrderOrigCardListDAO) {
		this.sellOrderOrigCardListDAO = sellOrderOrigCardListDAO;
	}

	public OrderCardListService getOrderCardListService() {
		return orderCardListService;
	}

	public void setOrderCardListService(
			OrderCardListService orderCardListService) {
		this.orderCardListService = orderCardListService;
	}

	public EntityStockService getEntityStockService() {
		return entityStockService;
	}

	public void setEntityStockService(EntityStockService entityStockService) {
		this.entityStockService = entityStockService;
	}

	public void sendBackAtConfirm(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		try {
			if (OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN
					.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_UNSIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_UNSIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_SIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_SELL_CUSTOMER_CREDIT_ORDER
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_SIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_UNSIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_SIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_UNSIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_CHANGE_CARD
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_RANSOM.equals(sellOrderDTO
							.getOrderType())) {
				orderBO.orderFlowNexNode(sellOrderDTO.getOrderId(),
						OrderConst.ORDER_STATE_DRAFT, sellOrderDTO
								.getLoginUserId(), sellOrderDTO
								.getDefaultEntityId(),
						OrderConst.ORDER_FLOW_OPRATION_BACK, sellOrderDTO
								.getOperationMemo(),
						OrderConst.ORDER_FLOW_CONFIRMATION);
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("退回订单失败!");
		}

	}

	public void sendBackAtRansome(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		try {
			orderBO.orderFlowNexNode(sellOrderDTO.getOrderId(),
					OrderConst.ORDER_STATE_UNCERTAIN, sellOrderDTO
							.getLoginUserId(), sellOrderDTO
							.getDefaultEntityId(),
					OrderConst.ORDER_FLOW_OPRATION_BACK, sellOrderDTO
							.getOperationMemo(),
					OrderConst.ORDER_STATE_ORDER_ACCEPT);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("退回订单失败!");
		}
	}
	
	public void sendBackAtReady(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		try {
			/**
			 * 如果是记名订单，判断其是否生成了采购订单.如果生成了采购订单 则不充许退回
			 */
			if (OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN
					.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_SIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_SIGN
							.equals(sellOrderDTO.getOrderType())) {
				String isGenerate = orderBaseQueryBO
						.checkSellOrderSignGenerateBuyerOrder(sellOrderDTO
								.getOrderId());
				if ("1".equals(isGenerate)) {
					throw new BizServiceException("该记名订单已经生成了采购订单，暂时不能退回");
				}
				//记名订单的卡产品的记名库存卡 则删除订单卡明细 并把卡入库
				if(OrderConst.PRODUCT_ONYMOUS_STAT_CAN.equals(sellOrderDTO.getOnymousStat())){
					//删除订单卡明细
					delCardList(sellOrderDTO);
				}
			}
			/**
			 * 如果是若名卡订单的退回 则需要将已经准备过的卡重新至为库存状态：1 同时删除订单中的明细 对于下级机构的采购订单 同样要将明细删除
			 * 同时退回到下级机构确认
			 */
			else if (OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_UNSIGN
					.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_UNSIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_UNSIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_UNSIGN
							.equals(sellOrderDTO.getOrderType())) {
					//删除订单卡明细
				delCardList(sellOrderDTO);
			
				/***
				 * 换卡订单，需要将所有原有卡状态改为待验收:0 同时将所有新卡入库
				 */
			} else if (OrderConst.ORDER_TYPE_ORDER_CHANGE_CARD
					.equals(sellOrderDTO.getOrderType())) {

				SellOrderOrigCardList origCardList = new SellOrderOrigCardList();
				origCardList.setCardSate(OrderConst.ORIG_CARD_STAT_UNCHECK);
				SellOrderOrigCardListExample example = new SellOrderOrigCardListExample();
				example.createCriteria().andOrderIdEqualTo(
						sellOrderDTO.getOrderId()).andDataStateEqualTo(
						DataBaseConstant.DATA_STATE_NORMAL);
				sellOrderOrigCardListDAO.updateByExampleSelective(origCardList,
						example);

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
				// 记录库存操作日志
				entityStockService
						.addStockOperaterRecord(cardNoList, sellOrderDTO
								.getOrderId(), sellOrderDTO
								.getDefaultEntityId(),
								Const.FUNCTION_ROLE_SELLER,
								OrderConst.ORDER_STATE_ORDER_READY,
								OrderConst.ORDER_FLOW_OPRATION_BACK,
								OrderConst.CARD_STOCK_IN, sellOrderDTO
										.getLoginUserId());
				orderBO.deleteSellOrderCardListByOrderId(sellOrderDTO
						.getOrderId());

				SellOrderList sellOrderList = new SellOrderList();

				sellOrderList.setRealAmount("0");

				sellOrderList.setModifyTime(DateUtil.getCurrentTime());

				sellOrderList.setModifyUser(sellOrderDTO.getLoginUserId());

				SellOrderListExample sellOrderListExample = new SellOrderListExample();

				sellOrderListExample.createCriteria().andOrderIdEqualTo(
						sellOrderDTO.getOrderId());

				orderBO.updateSellOrderListByExample(sellOrderList,
						sellOrderListExample);
			}

			orderBO.orderFlowNexNode(sellOrderDTO.getOrderId(),
					OrderConst.ORDER_STATE_UNCERTAIN, sellOrderDTO
							.getLoginUserId(), sellOrderDTO
							.getDefaultEntityId(),
					OrderConst.ORDER_FLOW_OPRATION_BACK, sellOrderDTO
							.getOperationMemo(), OrderConst.ORDER_FLOW_READY);
		} catch (BizServiceException e) {
			throw e;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("退回订单失败");
		}
	}
	
	
	private void delCardList(SellOrderDTO sellOrderDTO) throws BizServiceException{
		try {
			List<SellOrderCardList> orderCardList_list = orderBaseQueryBO
					.getSellOrderCardListDetailByOrderId(sellOrderDTO
							.getOrderId());
			//订单明细不能为空 and 订单明细中卡号不能为空
			if (null != orderCardList_list && orderCardList_list.size() > 0 && null != orderCardList_list.get(0).getCardNo() && !"".equals(orderCardList_list.get(0).getCardNo().trim())) {
				List<EntityStock> entityStockList = new ArrayList<EntityStock>();
				List<String> cardNoList = new ArrayList<String>();
				List<SellOrderCardList> sellOrderCardLists_temp = new ArrayList<SellOrderCardList>();
				for (SellOrderCardList detail : orderCardList_list) {
					EntityStock entityStcok = new EntityStock();
					entityStcok.setCardNo(detail.getCardNo());
					cardNoList.add(detail.getCardNo());
					entityStcok.setEntityId(sellOrderDTO.getProcessEntityId());
					entityStcok.setStockState(OrderConst.CARD_STOCK_IN);
					entityStockList.add(entityStcok);
					//删除持卡人关联的卡号
					SellOrderCardList sellOrderCardList_temp = new SellOrderCardList();
					sellOrderCardList_temp.setCardNo(detail.getCardNo());
					sellOrderCardList_temp.setCardholderId(null);
					sellOrderCardLists_temp.add(sellOrderCardList_temp);
				}
				orderBO.updateEntityStockByPrimaryKey(entityStockList);
				orderBO.batchUpdateAccCardInfo(sellOrderCardLists_temp);
				// 记录库存操作日志
				entityStockService
						.addStockOperaterRecord(cardNoList,
								sellOrderDTO.getOrderId(),
								sellOrderDTO.getDefaultEntityId(),
								Const.FUNCTION_ROLE_SELLER,
								OrderConst.ORDER_STATE_ORDER_READY,
								OrderConst.ORDER_FLOW_OPRATION_BACK,
								OrderConst.CARD_STOCK_IN,
								sellOrderDTO.getLoginUserId());
				//记名订单卡产品为库存卡的 订单明细卡号清空
				if(OrderConst.PRODUCT_ONYMOUS_STAT_CAN.equals(sellOrderDTO.getOnymousStat())){
					SellOrderCardListExample example = new SellOrderCardListExample();
					example.createCriteria().andOrderIdEqualTo(sellOrderDTO.getOrderId());
					SellOrderCardList sellOrderCardList = new SellOrderCardList();
					sellOrderCardList.setCardNo("");
					sellOrderCardListDAO.updateByExampleSelective(sellOrderCardList, example);
				}else{
					orderBO.deleteSellOrderCardListByOrderId(sellOrderDTO
						.getOrderId());
				}
				
				SellOrderList sellOrderList = new SellOrderList();

				sellOrderList.setRealAmount("0");

				sellOrderList.setModifyTime(DateUtil.getCurrentTime());

				sellOrderList.setModifyUser(sellOrderDTO.getLoginUserId());

				SellOrderListExample sellOrderListExample = new SellOrderListExample();

				sellOrderListExample.createCriteria().andOrderIdEqualTo(
						sellOrderDTO.getOrderId());

				orderBO.updateSellOrderListByExample(sellOrderList,
						sellOrderListExample);
				
				SellOrder sellOrder = new SellOrder();
				sellOrder.setRealCardQuantity("0");
				SellOrderExample example = new SellOrderExample();
				example.createCriteria().andOrderIdEqualTo(sellOrderDTO.getOrderId());
				sellOrderDAO.updateByExampleSelective(sellOrder, example);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			this.logger.error(e.getMessage());
			throw new BizServiceException("删除订单卡明细失败");
		}
	}

	public void sendBackAtHandOut(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		try {
			if (sellOrderDTO.getOrderType().equals(
					OrderConst.ORDER_TYPE_ORDER_CHANGE_CARD)) {
				throw new BizServiceException("制卡订单:"
						+ sellOrderDTO.getOrderId() + "中原有卡已经处理,不能退回!");
			}
			orderBO.orderFlowNexNode(sellOrderDTO.getOrderId(),
					OrderConst.ORDER_STATE_ORDER_READY, sellOrderDTO
							.getLoginUserId(), sellOrderDTO
							.getDefaultEntityId(),
					OrderConst.ORDER_FLOW_OPRATION_BACK, sellOrderDTO
							.getOperationMemo(), OrderConst.ORDER_FLOW_SEND);
		} catch (BizServiceException bse) {
			throw bse;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("退回订单失败");
		}
	}

	public OrderBO getOrderBO() {
		return orderBO;
	}

	public void setOrderBO(OrderBO orderBO) {
		this.orderBO = orderBO;
	}

	public OrderBaseQueryBO getOrderBaseQueryBO() {
		return orderBaseQueryBO;
	}

	public void setOrderBaseQueryBO(OrderBaseQueryBO orderBaseQueryBO) {
		this.orderBaseQueryBO = orderBaseQueryBO;
	}

	/***
	 * 如果是订单待配送确定， 依据订单类型决定退到哪一步
	 */
	public void sendBackAtSendConfirm(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		try {
			if (OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN
					.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_UNSIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_UNSIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_SIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_UNSIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_CHANGE_CARD
							.equals(sellOrderDTO.getOrderType())) {

				orderBO.orderFlowNexNode(sellOrderDTO.getOrderId(),
						OrderConst.ORDER_STATE_ORDER_SEND, sellOrderDTO
								.getLoginUserId(), sellOrderDTO
								.getDefaultEntityId(),
						OrderConst.ORDER_FLOW_OPRATION_BACK, sellOrderDTO
								.getMemo(), OrderConst.ORDER_FLOW_SEND_CONFIRM);
			} else if (OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_SIGN
					.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_UNSIGN
							.equals(sellOrderDTO.getOrderType())) {
				orderBO.orderFlowNexNode(sellOrderDTO.getOrderId(),
						OrderConst.ORDER_STATE_ORDER_SEND, sellOrderDTO
								.getLoginUserId(), sellOrderDTO
								.getDefaultEntityId(),
						OrderConst.ORDER_FLOW_OPRATION_BACK, sellOrderDTO
								.getMemo(), OrderConst.ORDER_FLOW_SEND_CONFIRM);
			}
			String stockStateString = "";
			if (OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN
					.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_SIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_SIGN
							.equals(sellOrderDTO.getOrderType())) {
				stockStateString = OrderConst.CARD_STOCK_IN;
			}
			if (OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_UNSIGN
					.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_UNSIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_UNSIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_UNSIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_CHANGE_CARD
							.equals(sellOrderDTO.getOrderType())) {
				stockStateString = OrderConst.CARD_STOCK_READY_OUT;
			}
			List<String> cardNoList = orderCardListService
					.getSuccessCardNoList(sellOrderDTO.getOrderId());
			entityStockService.modifyStockState(cardNoList,
					OrderConst.CARD_STOCK_OUT, stockStateString);

			// 记录库存操作日志
			entityStockService.addStockOperaterRecord(cardNoList, sellOrderDTO
					.getOrderId(), sellOrderDTO.getDefaultEntityId(),
					Const.FUNCTION_ROLE_SELLER,
					OrderConst.ORDER_STATE_ORDER_WAIT_SEND_CONFIRM,
					OrderConst.ORDER_FLOW_OPRATION_BACK, stockStateString,
					sellOrderDTO.getLoginUserId());
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("退回订单失败");
		}

	}

	public void sendBackAtOrderImmdeiatelyCredit(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		try {
			orderBO.orderFlowNexNode(sellOrderDTO.getOrderId(),
					OrderConst.ORDER_STATE_UNCERTAIN, sellOrderDTO
							.getLoginUserId(), sellOrderDTO
							.getDefaultEntityId(),
					OrderConst.ORDER_FLOW_OPRATION_BACK,
					sellOrderDTO.getMemo(),
					OrderConst.ORDER_FLOW_ORDER_IMMDIATELY_CREDIT);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("退回订单失败");
		}

	}

	public SellOrderCardListDAO getSellOrderCardListDAO() {
		return sellOrderCardListDAO;
	}

	public void setSellOrderCardListDAO(SellOrderCardListDAO sellOrderCardListDAO) {
		this.sellOrderCardListDAO = sellOrderCardListDAO;
	}

	public SellOrderDAO getSellOrderDAO() {
		return sellOrderDAO;
	}

	public void setSellOrderDAO(SellOrderDAO sellOrderDAO) {
		this.sellOrderDAO = sellOrderDAO;
	}

	
}
