package com.huateng.univer.order.business.service.impl;

import org.apache.log4j.Logger;

import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderInputDTO;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.univer.order.business.bo.OrderBO;
import com.huateng.univer.order.business.service.CancelOrderService;

/**
 * 取消订单
 * 
 * @author dawn
 * 
 */
public class CancelOrderServiceImpl implements CancelOrderService {

	private Logger logger = Logger.getLogger(CancelOrderServiceImpl.class);

	private OrderBO orderBO;

	/**
	 * 将订单状态至为取消. 同时写入流程节点
	 */
	public void cancelOrderAtInput(SellOrderInputDTO sellOrderInputDTO)
			throws BizServiceException {
		try {
			for (String orderId : sellOrderInputDTO.getEc_choose()) {
				orderBO.orderFlowNexNode(orderId,
						OrderConst.ORDER_STATE_CANCEL, sellOrderInputDTO
								.getLoginUserId(), sellOrderInputDTO
								.getDefaultEntityId(),
						OrderConst.ORDER_FLOW_OPRATION_CANCEL, "",
						OrderConst.ORDER_FLOW_INPUT);
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("取消订单失败!");
		}
	}

	public void cancelOrderAtConfirm(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		try {
			if (OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN
					.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_UNSIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_SIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_UNSIGN
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
					|| OrderConst.ORDER_TYPE_ORDER_CHANGE_CARD
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_RANSOM.equals(sellOrderDTO
							.getOrderType())) {
				orderBO.orderFlowNexNode(sellOrderDTO.getOrderId(),
						OrderConst.ORDER_STATE_CANCEL, sellOrderDTO
								.getLoginUserId(), sellOrderDTO
								.getDefaultEntityId(),
						OrderConst.ORDER_FLOW_OPRATION_CANCEL, sellOrderDTO
								.getOperationMemo(),
						OrderConst.ORDER_FLOW_CONFIRMATION);
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("取消订单失败!");
		}
	}

	public OrderBO getOrderBO() {
		return orderBO;
	}

	public void setOrderBO(OrderBO orderBO) {
		this.orderBO = orderBO;
	}

}
