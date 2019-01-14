package com.huateng.univer.order.business.bo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.SellOrderDAO;
import com.huateng.framework.ibatis.dao.SellOrderPaymentDAO;
import com.huateng.framework.ibatis.model.SellOrder;
import com.huateng.framework.ibatis.model.SellOrderList;
import com.huateng.framework.ibatis.model.SellOrderOrigCardList;
import com.huateng.framework.ibatis.model.SellOrderPayment;
import com.huateng.framework.ibatis.model.SellOrderPaymentExample;
import com.huateng.framework.util.MathUtil;
import com.huateng.framework.util.StringUtil;

public class OrderCountTotalPrice {

	private OrderBaseQueryBO orderBaseQueryBO;

	private SellOrderDAO sellOrderDAO;

	private SellOrderPaymentDAO sellOrderPaymentDAO;

	/**
	 * 记名采购订单计算总费用
	 * 
	 * @param orderId
	 * @throws Exception
	 */
	// 有关采购相关订单的计算总费用
	public void countTotalPriceForOrderByOrderType(String orderId)
			throws Exception {
		SellOrder sellOrder = orderBaseQueryBO.getSellOrder(orderId);
		String orderType = sellOrder.getOrderType();
		// 记名采购
		if (OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_SIGN
				.equals(orderType)
				|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_SIGN
						.equals(orderType)
				|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_SIGN
						.equals(orderType)) {
			this.countTotalPriceForBuyOrderSign(sellOrder);
		}
		// 匿名采购
		if (OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN
				.equals(orderType)
				|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_UNSIGN
						.equals(orderType)
				|| OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_UNSIGN
						.equals(orderType)) {
			this.countTotalPriceForSellOrderUnsign(orderId);
		}
		// if(OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_SIGN.equals(orderType)
		// ||OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_UNSIGN.equals(orderType)
		// ){
		// this.countTotalPriceForSellOrderMakeCard(orderId);
		// }

	}

	public void countTotalPriceForSellOrderMakeCard(String orderId)
			throws Exception {

		SellOrder sellOrder = orderBaseQueryBO.getSellOrder(orderId);

		String totalPrice = "0";

		String cardQuantity = "0";

		/**
		 * 面额
		 */
		// if(StringUtil.isNotEmpty(sellOrder.getFaceValue())){
		// totalPrice=MathUtil.add(totalPrice,sellOrder.getFaceValue()).toString();
		// }

		/**
		 * 卡张数
		 */
		if (StringUtil.isNotEmpty(sellOrder.getCardQuantity())) {
			cardQuantity = sellOrder.getCardQuantity();
		}
		String totalList = "0";
		if (StringUtil.isNotEmpty(sellOrder.getFaceValue())) {
			totalList = MathUtil.multiply(
					cardQuantity,
					MathUtil.add(totalPrice, sellOrder.getFaceValue())
							.toString()).toString();
		}
		totalPrice = MathUtil.add(totalPrice, totalList).toString();

		sellOrder.setTotalPrice(totalPrice);

		sellOrder.setCardQuantity(cardQuantity);

		sellOrderDAO.updateByPrimaryKeySelective(sellOrder);
	}

	public void countTotalPriceForSellOrderUnsign(String orderId)
			throws Exception {

		SellOrder sellOrder = orderBaseQueryBO.getSellOrder(orderId);

		String totalPrice = "0";

		String cardQuantity = "0";
		/**
		 * 折扣费
		 */
		if (StringUtil.isNotEmpty(sellOrder.getDiscountFee())) {
			totalPrice = MathUtil.subtract(totalPrice,
					sellOrder.getDiscountFee()).toString();
		}
		/* 暂行，只有销售匿名订单中订单信息加入包装费 */
		if (sellOrder.getOrderType().equals(
				OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_UNSIGN)
				|| sellOrder.getOrderType().equals(
						OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_UNSIGN)
				|| sellOrder.getOrderType().equals(
						OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_UNSIGN)) {
			if (StringUtil.isNotEmpty(sellOrder.getPackageFee())) {
				totalPrice = MathUtil
						.add(totalPrice, sellOrder.getPackageFee()).toString();
			}
		}
		// /**
		// * 包装费
		// */
		// if(StringUtil.isNotEmpty(sellOrder.getPackageFee())){
		// totalPrice=MathUtil.add(totalPrice,sellOrder.getPackageFee()).toString();
		// }
		/***
		 * 送货费
		 */
		if (StringUtil.isNotEmpty(sellOrder.getDeliveryFee())) {
			totalPrice = MathUtil.add(totalPrice, sellOrder.getDeliveryFee())
					.toString();
		}
		/**
		 * 附加费
		 */
		if (StringUtil.isNotEmpty(sellOrder.getAdditionalFee())) {
			totalPrice = MathUtil.add(totalPrice, sellOrder.getAdditionalFee())
					.toString();
		}
		/**
		 * 明细计算
		 */

		List<SellOrderList> sellOrderList_list = orderBaseQueryBO
				.getSellOrderListByOrderId(orderId);
		/* 销售匿名订单 */
		if (sellOrder.getOrderType().equals(
				OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_UNSIGN)
				|| sellOrder.getOrderType().equals(
						OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_UNSIGN)
				|| sellOrder.getOrderType().equals(
						OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_UNSIGN)) {
			for (SellOrderList orderlist : sellOrderList_list) {
				String orderListTotalPrice = MathUtil.multiply(
						MathUtil.add(orderlist.getFaceValue(),
								sellOrder.getCardIssueFee(),
								sellOrder.getAnnualFee()).toString(),
						orderlist.getCardAmount()).toString();
				totalPrice = MathUtil.add(orderListTotalPrice, totalPrice)
						.toString();
				cardQuantity = MathUtil.add(cardQuantity,
						orderlist.getCardAmount()).toString();
			}
		} else {
			for (SellOrderList orderlist : sellOrderList_list) {
				/**
				 * 包装费
				 */
				if (StringUtil.isNotEmpty(orderlist.getPackageFee())) {
					totalPrice = MathUtil.add(totalPrice,
							orderlist.getPackageFee()).toString();
				}
				String orderListTotalPrice = MathUtil.multiply(
						MathUtil.add(orderlist.getFaceValue(),
								sellOrder.getCardIssueFee(),
								sellOrder.getAnnualFee()).toString(),
						orderlist.getCardAmount()).toString();
				totalPrice = MathUtil.add(orderListTotalPrice, totalPrice)
						.toString();

				cardQuantity = MathUtil.add(cardQuantity,
						orderlist.getCardAmount()).toString();
			}
		}
		if(sellOrder.getOrderType().equals(
				OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_UNSIGN)){
			sellOrder.setTotalPrice("0");
		}else{
			sellOrder.setTotalPrice(totalPrice);
		}
		
		sellOrder.setCardQuantity(cardQuantity);
		sellOrderDAO.updateByPrimaryKeySelective(sellOrder);
	}

	public void countTotalPriceForSellOrdersign(String orderId)
			throws Exception {

		SellOrder sellOrder = orderBaseQueryBO.getSellOrder(orderId);

		String totalPrice = "0";

		String cardQuantity = "0";

		/**
		 * 包装费
		 */
		if (StringUtil.isNotEmpty(sellOrder.getPackageFee())) {
			totalPrice = MathUtil.add(totalPrice, sellOrder.getPackageFee())
					.toString();
		}
		//				
		// /**
		// * 第一次充值金额
		// */
		// if(StringUtil.isNotEmpty(sellOrder.getFaceValue())){
		// totalPrice=MathUtil.add(totalPrice,sellOrder.getFaceValue()).toString();
		// }

		/**
		 * 折扣费
		 */
		if (StringUtil.isNotEmpty(sellOrder.getDiscountFee())) {
			totalPrice = MathUtil.subtract(totalPrice,
					sellOrder.getDiscountFee()).toString();
		}
		/***
		 * 送货费
		 */
		if (StringUtil.isNotEmpty(sellOrder.getDeliveryFee())) {
			totalPrice = MathUtil.add(totalPrice, sellOrder.getDeliveryFee())
					.toString();
		}
		/**
		 * 附加费
		 */
		if (StringUtil.isNotEmpty(sellOrder.getAdditionalFee())) {
			totalPrice = MathUtil.add(totalPrice, sellOrder.getAdditionalFee())
					.toString();
		}

		/**
		 * 明细计算,通过明细获取卡费
		 */
		cardQuantity = orderBaseQueryBO.getSellOrderCardListByOrderId(orderId);

		String totalList = MathUtil.multiply(
				cardQuantity,
				MathUtil.add(sellOrder.getCardIssueFee(),
						sellOrder.getFaceValue(), sellOrder.getAnnualFee())
						.toString()).toString();

		totalPrice = MathUtil.add(totalPrice, totalList).toString();

		sellOrder.setTotalPrice(totalPrice);

		sellOrder.setCardQuantity(cardQuantity);

		sellOrderDAO.updateByPrimaryKeySelective(sellOrder);
	}

	public void countTotalPriceForBuyOrderSign(SellOrder sellOrder)
			throws Exception {
		String totalPrice = "0";
		String cardQuantity = "0";
		/**
		 * 折扣费
		 */
		if (StringUtil.isNotEmpty(sellOrder.getDiscountFee())) {
			totalPrice = MathUtil.subtract(totalPrice,
					sellOrder.getDiscountFee()).toString();
		}
		/***
		 * 送货费
		 */
		if (StringUtil.isNotEmpty(sellOrder.getDeliveryFee())) {
			totalPrice = MathUtil.add(totalPrice, sellOrder.getDeliveryFee())
					.toString();
		}
		/**
		 * 附加费
		 */
		if (StringUtil.isNotEmpty(sellOrder.getAdditionalFee())) {
			totalPrice = MathUtil.add(totalPrice, sellOrder.getAdditionalFee())
					.toString();
		}
		/**
		 * 包装费
		 */
		// List<SellOrderList> sellOrderList_list =
		// orderBaseQueryBO.getSellOrderListByOrderId(sellOrder.getOrderId());
		// for (SellOrderList orderlist :sellOrderList_list){
		// if(StringUtil.isNotEmpty(sellOrder.getPackageFee())){
		// totalPrice=MathUtil.add(totalPrice,orderlist.getPackageFee()).toString();
		// }
		// }
		cardQuantity = sellOrder.getCardQuantity();
		// cardQuantity=String.valueOf(2);
		// cardQuantity = orderlist.getCardAmount();
		// cardQuantity=orderBaseQueryBO.getSellOrderCardListByOrderId(sellOrder.getOrderId());

		String totalList = MathUtil.multiply(
				cardQuantity,
				MathUtil.add(sellOrder.getPackageFee(),
						sellOrder.getCardIssueFee(), sellOrder.getFaceValue(),
						sellOrder.getAnnualFee()).toString()).toString();

		totalPrice = MathUtil.add(totalPrice, totalList).toString();
		sellOrder.setTotalPrice(totalPrice);
		sellOrderDAO.updateByPrimaryKeySelective(sellOrder);
	}

	public void countTotalPriceForCreditOrder(String orderId) throws Exception {
		SellOrder sellOrder = orderBaseQueryBO.getSellOrder(orderId);
		String totalPrice = orderBaseQueryBO
				.getCreditFaceValueByOrderCardList(orderId);
		String totalCardQuantity = orderBaseQueryBO
				.getSellOrderCardListByOrderId(orderId);
		/**
		 * 除以100得到百分比
		 */
		String serviceFee = MathUtil.divide(sellOrder.getServiceFee(), "100")
				.toString();
		/**
		 * 1+费率
		 */
		totalPrice = MathUtil.multiply(totalPrice,
				MathUtil.add("1", serviceFee).toString()).toString();
		sellOrder.setTotalPrice(totalPrice);
		sellOrder.setCardQuantity(totalCardQuantity);
		sellOrderDAO.updateByPrimaryKeySelective(sellOrder);
	}

	/**
	 * @author Yifeng.Shi 换卡订单的费用计算包括原卡总金额、新卡总金额、订单总金额
	 * */
	public void countTotalPriceForChangeCardOrder(String orderId)
			throws Exception {
		SellOrder sellOrder = orderBaseQueryBO.getSellOrder(orderId);
		String totalPrice = "0";
		/* 送货费 */
		if (StringUtil.isNotEmpty(sellOrder.getDeliveryFee())) {
			totalPrice = MathUtil.add(totalPrice, sellOrder.getDeliveryFee())
					.toString();
		}
		/* 服务费 */
		if (StringUtil.isNotEmpty(sellOrder.getAdditionalFee())) {
			totalPrice = MathUtil.add(totalPrice, sellOrder.getAdditionalFee())
					.toString();
		}
		/* 包装费 */
		if (StringUtil.isNotEmpty(sellOrder.getPackageFee())) {
			totalPrice = MathUtil.add(totalPrice, sellOrder.getPackageFee())
					.toString();
		}

		/* 原卡总费用计算 总张数计算 */
		List<SellOrderOrigCardList> origCardList = orderBaseQueryBO
				.getSellOrderOrigCardList(orderId);
		String origCountAmount = "0";
		int origCardCount = 0;
		for (SellOrderOrigCardList origCard : origCardList) {
			origCountAmount = MathUtil.add(origCountAmount,
					origCard.getAmount()).toString();
			origCardCount++;
		}

		/* 新卡总费用计算 */
		List<SellOrderList> newCardList = orderBaseQueryBO
				.getSellOrderListByOrderId(orderId);
		String newCardAmount = "0";
		String newCardCount = "0";
		for (SellOrderList orderList : newCardList) {
			newCardAmount = MathUtil.add(
					newCardAmount,
					MathUtil.multiply(orderList.getFaceValue(),
							orderList.getCardAmount()).toString()).toString();
			newCardCount = MathUtil
					.add(newCardCount, orderList.getCardAmount()).toString();
		}
		/* 总费用=包装费+送货费+服务费+(新卡总费用-原卡总费用) */
		totalPrice = MathUtil.add(totalPrice,
				MathUtil.subtract(newCardAmount, origCountAmount).toString())
				.toString();
		sellOrder.setOrigcardTotalamt(origCountAmount);
		sellOrder.setNewcardTotalamt(newCardAmount);
		sellOrder.setTotalPrice(totalPrice);
		sellOrder.setOrigcardQuantity(Integer.toString(origCardCount));
		sellOrder.setCardQuantity(newCardCount);
		sellOrderDAO.updateByPrimaryKeySelective(sellOrder);
	}

	public Map<String,String> countPriceForRansomOrder(String orderId) throws Exception {
		SellOrder sellOrder = orderBaseQueryBO.getSellOrder(orderId);
		String totalPrice = "0";

		/* 原卡总费用计算 总张数计算 */
		List<SellOrderOrigCardList> origCardList = orderBaseQueryBO
				.getSellOrderOrigCardList(orderId);
		String origCountAmount = "0";
		String serviceFeeCount = "0";
		int origCardCount = 0;
		for (SellOrderOrigCardList origCard : origCardList) {
			origCountAmount = MathUtil.add(origCountAmount,
					origCard.getAmount()).toString();
			origCardCount++;
		}

		/**
		 * 除以100得到百分比
		 */
		String serviceFee = MathUtil.divide(sellOrder.getServiceFee() == null ? "0" 
				: sellOrder.getServiceFee(), "100").toString();

		serviceFeeCount = MathUtil.multiply(origCountAmount, serviceFee)
				.toString();

		/* 赎回总费用=服务总费+附加费-卡总额 */
		totalPrice = MathUtil.add(totalPrice, serviceFeeCount).toString();
		totalPrice = MathUtil
				.add(totalPrice, sellOrder.getAdditionalFee()).toString();
		totalPrice = MathUtil.subtract(totalPrice, origCountAmount).toString();

		sellOrder.setCardQuantity(Integer.toString(origCardCount));
		sellOrder.setPackageFee(serviceFeeCount);
		sellOrder.setOrigcardTotalamt(origCountAmount);
		sellOrder.setTotalPrice(new BigDecimal(totalPrice).abs().toString());
		sellOrderDAO.updateByPrimaryKeySelective(sellOrder);
		Map<String, String> map = new HashMap<String, String>();
		map.put("cardQuantity", sellOrder.getCardQuantity());
		map.put("packageFee", sellOrder.getPackageFee());
		map.put("totalPrice", sellOrder.getTotalPrice());
		map.put("origcardTotalamt", sellOrder.getOrigcardTotalamt());
		return map;
	}

	public void countAmountForPayment(String orderId)
			throws BizServiceException {
		SellOrderPaymentExample example = new SellOrderPaymentExample();
		example.createCriteria().andOrderIdEqualTo(orderId)
				.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
		List<SellOrderPayment> orderPaymentList = sellOrderPaymentDAO
				.selectByExample(example);
		BigDecimal totalAmount = new BigDecimal("0");
		BigDecimal tempDecimal = null;
		for (SellOrderPayment tempPayment : orderPaymentList) {
			tempDecimal = new BigDecimal(tempPayment.getPaymentAmount());
			totalAmount = totalAmount.add(tempDecimal);
		}
		SellOrder order = new SellOrder();
		order.setOrderId(orderId);
		order.setPaymentAmount(totalAmount.multiply(new BigDecimal("100"))
				.toString());
		sellOrderDAO.updateByPrimaryKeySelective(order);
	}

	public OrderBaseQueryBO getOrderBaseQueryBO() {
		return orderBaseQueryBO;
	}

	public void setOrderBaseQueryBO(OrderBaseQueryBO orderBaseQueryBO) {
		this.orderBaseQueryBO = orderBaseQueryBO;
	}

	public SellOrderDAO getSellOrderDAO() {
		return sellOrderDAO;
	}

	public void setSellOrderDAO(SellOrderDAO sellOrderDAO) {
		this.sellOrderDAO = sellOrderDAO;
	}

	public SellOrderPaymentDAO getSellOrderPaymentDAO() {
		return sellOrderPaymentDAO;
	}

	public void setSellOrderPaymentDAO(SellOrderPaymentDAO sellOrderPaymentDAO) {
		this.sellOrderPaymentDAO = sellOrderPaymentDAO;
	}

}
