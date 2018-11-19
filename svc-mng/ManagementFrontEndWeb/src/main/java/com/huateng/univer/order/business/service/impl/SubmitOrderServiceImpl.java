package com.huateng.univer.order.business.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.allinfinance.univer.seller.order.dto.SellOrderCardListDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderInputDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderListDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderOrigCardListDTO;
import com.huateng.framework.constant.Const;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.constant.DictInfoConstants;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.ProductDAO;
import com.huateng.framework.ibatis.dao.SellOrderCardListDAO;
import com.huateng.framework.ibatis.dao.SellOrderDAO;
import com.huateng.framework.ibatis.dao.SellOrderOrigCardListDAO;
import com.huateng.framework.ibatis.dao.SellOrderPaymentDAO;
import com.huateng.framework.ibatis.dao.UnionOrderDAO;
import com.huateng.framework.ibatis.model.EntityStock;
import com.huateng.framework.ibatis.model.Product;
import com.huateng.framework.ibatis.model.SellOrder;
import com.huateng.framework.ibatis.model.SellOrderCardList;
import com.huateng.framework.ibatis.model.SellOrderCardListExample;
import com.huateng.framework.ibatis.model.SellOrderExample;
import com.huateng.framework.ibatis.model.SellOrderList;
import com.huateng.framework.ibatis.model.SellOrderOrigCardList;
import com.huateng.framework.ibatis.model.SellOrderOrigCardListExample;
import com.huateng.framework.ibatis.model.SellOrderPaymentExample;
import com.huateng.framework.ibatis.model.UnionOrder;
import com.huateng.framework.ibatis.model.UnionOrderExample;
import com.huateng.framework.util.ConfigMakeCard;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.RechargeConfig;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.framework.util.StringUtil;
import com.huateng.hstserver.config.HSTProperties;
import com.huateng.hstserver.constants.RspCodeMap;
import com.huateng.hstserver.gatewayService.Java2ACCBusinessServiceImpl;
import com.huateng.hstserver.model.AccPackageDTO;
import com.huateng.univer.batchfile.action.BatchFileActionInterface;
import com.huateng.univer.entitybaseinfo.contact.biz.service.ContactService;
import com.huateng.univer.entitystock.service.EntityStockService;
import com.huateng.univer.order.business.bo.OrderBO;
import com.huateng.univer.order.business.bo.OrderBaseQueryBO;
import com.huateng.univer.order.business.service.OrderBatchFileService;
import com.huateng.univer.order.business.service.OrderCardListService;
import com.huateng.univer.order.business.service.RansomOrderService;
import com.huateng.univer.order.business.service.SubmitOrderService;
import com.huateng.univer.seller.customer.biz.service.CustomerService;

public class SubmitOrderServiceImpl implements SubmitOrderService {
	/**
	 * 订单录入节点提交
	 * 
	 */
	private OrderBO orderBO;
	Logger logger = Logger.getLogger(this.getClass());
	private OrderBaseQueryBO orderBaseQueryBO;
	private SellOrderDAO sellOrderDAO;
	private Product product;
	private CommonsDAO commonsDAO;
	private ProductDAO productDAO;
	private SellOrder sellOrder;
	private OrderBatchFileService orderBatchFileService;
	private UnionOrderDAO unionOrderDAO;
	private OrderCardListService orderCardListService;
	private EntityStockService entityStockService;
	private CustomerService customerService;
	private SellOrderOrigCardListDAO sellOrderOrigCardListDAO;
	private ContactService contactService;
	private SellOrderPaymentDAO sellOrderPaymentDAO;
	private Java2ACCBusinessServiceImpl java2ACCBusinessService;
	private RansomOrderService ransomOrderService;
	private SellOrderCardListDAO sellOrderCardListDAO;
	// private BatchFileService rechargeBatchFileService;
	// private BatchFileService rechargeActBatchFileService;
	private BatchFileActionInterface fileBatchService;

	public BatchFileActionInterface getFileBatchService() {
		return fileBatchService;
	}

	public void setFileBatchService(BatchFileActionInterface fileBatchService) {
		this.fileBatchService = fileBatchService;
	}

	public Java2ACCBusinessServiceImpl getJava2ACCBusinessService() {
		return java2ACCBusinessService;
	}

	public void setJava2ACCBusinessService(
			Java2ACCBusinessServiceImpl java2accBusinessService) {
		java2ACCBusinessService = java2accBusinessService;
	}

	public ContactService getContactService() {
		return contactService;
	}

	public void setContactService(ContactService contactService) {
		this.contactService = contactService;
	}

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

	public OrderCardListService getOrderCardListService() {
		return orderCardListService;
	}

	public void setOrderCardListService(
			OrderCardListService orderCardListService) {
		this.orderCardListService = orderCardListService;
	}

	public UnionOrderDAO getUnionOrderDAO() {
		return unionOrderDAO;
	}

	public void setUnionOrderDAO(UnionOrderDAO unionOrderDAO) {
		this.unionOrderDAO = unionOrderDAO;
	}

	public ProductDAO getProductDAO() {
		return productDAO;
	}

	public void setProductDAO(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}

	public SellOrderDAO getSellOrderDAO() {
		return sellOrderDAO;
	}

	public void setSellOrderDAO(SellOrderDAO sellOrderDAO) {
		this.sellOrderDAO = sellOrderDAO;
	}

	@Override
	public void submitOrderAtInput(SellOrderInputDTO sellOrderInputDTO)
			throws BizServiceException {
		try {
			for (String orderId : sellOrderInputDTO.getEc_choose()) {
				SellOrder currentOrder = orderBaseQueryBO.getSellOrder(orderId);
				
				if (StringUtil.isEmpty(currentOrder.getCardQuantity())
						|| Integer.parseInt(currentOrder.getCardQuantity()) <= 0) {
					throw new BizServiceException("提交订单失败,订单：" + orderId
							+ "卡数量为零,请添加卡明细");
				}

				if (currentOrder.getOrderType().equals(
						OrderConst.ORDER_TYPE_ORDER_CHANGE_CARD)) {
					if (StringUtil.isEmpty(currentOrder.getOrigcardQuantity())
							|| Integer.parseInt(currentOrder
									.getOrigcardQuantity()) <= 0) {
						throw new BizServiceException("提交订单失败,订单：" + orderId
								+ "原有卡数量为零,请添加原有卡");
					}
					
					//新老卡数量是否相同
					if(!currentOrder.getCardQuantity().equals(currentOrder.getOrigcardQuantity())){
						throw new BizServiceException("提交订单失败,订单：" + orderId
								+ "原卡张数与新卡张数不相等，请重新编辑订单");
					}
					
					//新老卡金额是否相同
					if (!Float.valueOf(currentOrder.getOrigcardTotalamt())
							.equals(Float.valueOf(currentOrder.getNewcardTotalamt()))) {
						throw new BizServiceException("提交订单失败,订单：" + orderId
								+ "原卡金额与新卡金额不相等，请重新编辑订单");
					}
					
				}
				
				//销售订单、换卡订单、赎回订单、充值订单录入，订单总金额不允许为负金额
				checkTotalPrice(currentOrder);
				
				if (OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN
						.equals(currentOrder.getOrderType())
						|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_UNSIGN
								.equals(currentOrder.getOrderType())
						|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN
								.equals(currentOrder.getOrderType())
						|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_UNSIGN
								.equals(currentOrder.getOrderType())
						|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_SIGN
								.equals(currentOrder.getOrderType())
						|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_UNSIGN
								.equals(currentOrder.getOrderType())
						|| OrderConst.ORDER_TYPE_SELL_CUSTOMER_CREDIT_ORDER
								.equals(currentOrder.getOrderType())) {
					SellOrderDTO sellOrderDTO = null;
					// 充值总额高于5000
					if (currentOrder.getOrderType().equals(
							OrderConst.ORDER_TYPE_SELL_CUSTOMER_CREDIT_ORDER)) {
						// 根据orderId获取SellOrderCardList
						sellOrderDTO = orderBaseQueryBO
								.getSellOrderCardList(orderId);
						Float creditAmount = Float.valueOf(sellOrderDTO
								.getFaceValue());
						if ("3".equals(sellOrderDTO.getCustomerType())) {
							if (creditAmount > 500000) {
								throw new BizServiceException("散户充值金额不能超过5000！");
							}
						}
						/*if (creditAmount > 500000) {
							if (currentOrder.getPayChannel().equals("1")) {
								throw new BizServiceException(
										"充值金额超过5000，请您选择银行转账等非现金付款方式支付，谢谢！");
							}
//							if (null ==currentOrder.getPayDetails()
//									|| "".equals(currentOrder.getPayDetails())) {
//								throw new BizServiceException(
//										"充值金额超过5000，请输入支付明细！");
//							}
//							if (null ==currentOrder.getFromBankId()
//									|| "".equals(currentOrder.getFromBankId())) {
//								throw new BizServiceException(
//										"充值金额超过5000，付款开户银行不能为空！");
//							}
//							if (null ==currentOrder.getIntoBankId()
//									|| "".equals(currentOrder.getIntoBankId())) {
//								throw new BizServiceException(
//										"充值金额超过5000，收款开户银行不能为空！");
//							}
						}*/
					}
					if (currentOrder
							.getOrderType()
							.equals(
									OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN)
							|| currentOrder
									.getOrderType()
									.equals(
											OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_UNSIGN)) {
						// 根据orderId获取SellOrderCardList
						sellOrderDTO = orderBaseQueryBO
								.getSellOrderCardList(orderId);
						Float creditAmount = Float.valueOf(sellOrderDTO
								.getFaceValue());
						/*if (creditAmount > 500000) {
							if (currentOrder.getPayChannel().equals("1")) {
								throw new BizServiceException(
										"您购买金额已超5000 元，请您选择银行转账等非现金付款方式购买，谢谢！");
							}
						}*/
					}
					if (currentOrder
							.getOrderType()
							.equals(
									OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN)
							|| currentOrder
									.getOrderType()
									.equals(
											OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_UNSIGN)) {
						sellOrderDTO = orderBaseQueryBO
								.getSellOrderCardList(orderId);
						Float creditAmount = Float.valueOf(sellOrderDTO
								.getFaceValue());
						/*if (creditAmount > 5000000) {
							if (currentOrder.getPayChannel().equals("1")) {
								throw new BizServiceException(
										"您购买金额已超50000 元，请您选择银行转账等非现金付款方式购买，谢谢！");
							}
						}*/
						/*if (creditAmount > 1000000) {
							ContactDTO cotactDTO = new ContactDTO();
							cotactDTO.setContactId(currentOrder.getContactId());
							cotactDTO = contactService.viewContact(cotactDTO);
							if (null == cotactDTO.getContactName()
									|| null == cotactDTO.getPapersNo()
									|| "".equals(cotactDTO.getContactName())
									|| "".equals(cotactDTO.getPapersNo())) {
								throw new BizServiceException(
										"订单金额超过10000，请至客户信息中完善客户经办人（代办人）信息！");
							}

						}*/
					}
					// 散户（不记名卡）购卡金额不能超过5000
					/*if (currentOrder
							.getOrderType()
							.equals(
									OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_UNSIGN)
							|| currentOrder
									.getOrderType()
									.equals(
											OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_SIGN)) {
						sellOrderDTO = orderBaseQueryBO
								.getSellOrderCardList(orderId);
						Float creditAmount = Float.valueOf(sellOrderDTO
								.getFaceValue());
						if (creditAmount > 500000) {
							throw new BizServiceException("散户购卡金额不能超过5000！");
						}
					}*/
				}
				orderBO.orderFlowNexNode(orderId,
						OrderConst.ORDER_STATE_UNCERTAIN, sellOrderInputDTO
								.getLoginUserId(), sellOrderInputDTO
								.getDefaultEntityId(),
						OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION, "",
						OrderConst.ORDER_FLOW_INPUT);
			}
		} catch (BizServiceException e) {
			throw e;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("提交订单失败");
		}
	}
	
	//销售订单、换卡订单、赎回订单、充值订单录入，订单总金额不允许为负金额
	public void checkTotalPrice(SellOrder currentOrder) throws BizServiceException {
		String orderType = currentOrder.getOrderType();
		if (OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN
				.equals(orderType)
				|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_UNSIGN
						.equals(orderType)
				|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN
						.equals(orderType)
				|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_UNSIGN
						.equals(orderType)
				|| OrderConst.ORDER_TYPE_SELL_CUSTOMER_CREDIT_ORDER
						.equals(orderType)
				|| OrderConst.ORDER_TYPE_ORDER_CHANGE_CARD.equals(orderType)
				|| OrderConst.ORDER_TYPE_ORDER_RANSOM.equals(orderType)) {
			
			BigDecimal totalPrice = new BigDecimal(currentOrder.getTotalPrice());
			if(totalPrice.compareTo(new BigDecimal("0")) == -1){
				throw new BizServiceException("订单总金额不能为负！");
			}	
		}
	}

	@Override
	public void submitOrderAtConfirm(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		
		//赎回订单
		if (OrderConst.ORDER_TYPE_ORDER_RANSOM.equals(sellOrderDTO
				.getOrderType())) {
			//校验赎回订单是否同时包含原售卡订单中所有的卡
			//售卡订单全部卡
			List<SellOrderCardList> sellOrderCardLists = sellOrderCardListDAO.getSellOrderCardListByRansomOrderId(sellOrderDTO);
			
			
			//赎回订单中全部的卡
			List<SellOrderOrigCardListDTO> ransomOrderCardLists = ransomOrderService.ready(sellOrderDTO);
			Map<String,String> ransomOrderCardListMap = new HashMap<String,String>();
			for(SellOrderOrigCardListDTO sellOrderOrigCardListDTO : ransomOrderCardLists){
				ransomOrderCardListMap.put(sellOrderOrigCardListDTO.getCardNo(), "");
			}
			//如果赎回订单所有卡没有包含原售卡订单中所有的卡
			for(SellOrderCardList sellOrderCardList : sellOrderCardLists){
				if(!ransomOrderCardListMap.containsKey(sellOrderCardList.getCardNo())){
					SellOrder sellOrder = new SellOrder();
					sellOrder.setOrderId(sellOrderDTO.getOrderId());
					sellOrder.setOrderState(OrderConst.ORDER_STATE_ORDER_PROCESS_FAIL);
					sellOrderDAO.updateByPrimaryKeySelective(sellOrder);
					logger.info("审核失败：赎回订单必须同时包含原售卡订单中所有的卡 ");
					throw new BizServiceException("审核失败：赎回订单必须同时包含原售卡订单中所有的卡 ");
				}
			}
		}
		try {
			/**
			 * 对于销售订单/下级营销机构采购订单，订单流入准备阶段
			 */
			if (OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_UNSIGN
					.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_SIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_UNSIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_UNSIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_CHANGE_CARD
							.equals(sellOrderDTO.getOrderType())) {
				orderBO.orderFlowNexNode(sellOrderDTO.getOrderId(),
						OrderConst.ORDER_STATE_ORDER_READY, sellOrderDTO
								.getLoginUserId(), sellOrderDTO
								.getDefaultEntityId(),
						OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION,
						sellOrderDTO.getOperationMemo(),
						OrderConst.ORDER_FLOW_CONFIRMATION);
			}
			/**
			 * 对于采购订单，订单流入上级
			 */
			else if (OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN
					.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_SIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_SIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_UNSIGN
							.equals(sellOrderDTO.getOrderType())) {
				if (OrderConst.ORDER_STATE_UNCERTAIN.equals(sellOrderDTO
						.getOrderState())) {
					orderBO.orderFlowNexNode(sellOrderDTO.getOrderId(),
							OrderConst.ORDER_STATE_ORDER_CONFIRM_BY_HIGHER,
							sellOrderDTO.getLoginUserId(), sellOrderDTO
									.getDefaultEntityId(),
							OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION,
							sellOrderDTO.getOperationMemo(),
							OrderConst.ORDER_FLOW_CONFIRMATION);
				} else if (OrderConst.ORDER_STATE_ORDER_CONFIRM_BY_HIGHER
						.equals(sellOrderDTO.getOrderState())) {
					orderBO.orderFlowNexNode(sellOrderDTO.getOrderId(),
							OrderConst.ORDER_STATE_ORDER_READY, sellOrderDTO
									.getLoginUserId(), sellOrderDTO
									.getDefaultEntityId(),
							OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION,
							sellOrderDTO.getOperationMemo(),
							OrderConst.ORDER_FLOW_CONFIRMATION);
				}
				/***
				 * 如果是充值直接流入订单立即充值
				 */
			} else if (OrderConst.ORDER_TYPE_SELL_CUSTOMER_CREDIT_ORDER
					.equals(sellOrderDTO.getOrderType())) {
				orderBO.orderFlowNexNode(sellOrderDTO.getOrderId(),
						OrderConst.ORDER_STATE_WAITTING_CREDIT, sellOrderDTO
								.getLoginUserId(), sellOrderDTO
								.getDefaultEntityId(),
						OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION,
						sellOrderDTO.getOperationMemo(),
						OrderConst.ORDER_FLOW_CONFIRMATION);

			} else if (OrderConst.ORDER_TYPE_ORDER_RANSOM.equals(sellOrderDTO
					.getOrderType())) {
				
				/* 赎回订单审核提交,接收状态 */
				orderBO.orderFlowNexNode(sellOrderDTO.getOrderId(),
						OrderConst.ORDER_STATE_ORDER_ACCEPT, sellOrderDTO
								.getLoginUserId(), sellOrderDTO
								.getDefaultEntityId(),
						OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION,
						sellOrderDTO.getOperationMemo(),
						OrderConst.ORDER_FLOW_CONFIRMATION);
			}

		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("提交订单失败!");
		}
	}

	@Override
	public void submitOrderAtReady(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		try {
			/**
			 * 对于销售记名卡订单 必须等该记名卡 全部入营销机构库才能提交
			 */
			if ((OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN
					.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_SIGN
							.equals(sellOrderDTO.getOrderType()) || OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN
					.equals(sellOrderDTO.getOrderType()))
					&& "1".equals(sellOrderDTO.getOnymousStat())) {
				String cardQuantity = orderBaseQueryBO
						.selectSignCardQuantity(sellOrderDTO.getOrderId());
				if ("0".equals(cardQuantity)) {
					throw new BizServiceException("订单的卡未配送到本机构,提交订单失败!");
				}
			} else if (OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_SIGN
					.equals(sellOrderDTO.getOrderType())) {
				boolean flag = true;
				String rootOrderIdString = "";
				while (flag) {
					UnionOrderExample unionOrderExample = new UnionOrderExample();
					unionOrderExample.createCriteria().andNewOrderEqualTo(
							sellOrderDTO.getOrderId());
					List<UnionOrder> lstResultList = unionOrderDAO
							.selectByExample(unionOrderExample);
					if (null != lstResultList && lstResultList.size() > 0) {
						for (UnionOrder unionOrder : lstResultList) {
							if (unionOrder.getLeafNode().equals("1")) {
								flag = false;
								rootOrderIdString = unionOrder.getOldOrder();
								break;
							}
						}
					}
				}
				/*
				 * 这里需要传递对象，包含根订单Id和当前机构Id。获取根订单的卡片列表
				 * 暂定用UnionOrder对象代替。
				 */
				UnionOrder order = new UnionOrder();
				order.setNewOrder(sellOrderDTO.getDefaultEntityId());
				order.setOldOrder(rootOrderIdString);
				String cardQuantity = orderBaseQueryBO
						.selectSellAndSellSignCardQuantity(order);
				if ("0".equals(cardQuantity)) {
					throw new BizServiceException("订单的卡未配送到本机构,提交订单失败!");
				}
			} else if (OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_UNSIGN
					.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_UNSIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_UNSIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_UNSIGN
							.equals(sellOrderDTO.getOrderType())) {
				List<SellOrderList> sellOrderLists = orderBaseQueryBO
						.getSellOrderListByOrderId(sellOrderDTO.getOrderId());
				for (SellOrderList orderList : sellOrderLists) {
					Integer cardAmt = new Integer(orderList.getCardAmount());
					Integer realAmt = new Integer(orderList.getRealAmount());
					if (cardAmt > realAmt) {
						throw new BizServiceException("订单："
								+ sellOrderDTO.getOrderId()
								+ " 中有准备未完成的明细，不能提交!");
					}
				}
			} else if ((OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN
					.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_SIGN
							.equals(sellOrderDTO.getOrderType()) || OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN
					.equals(sellOrderDTO.getOrderType()))
					&& "3".equals(sellOrderDTO.getOnymousStat())) {
				if (orderBaseQueryBO.selectSignCardCount(sellOrderDTO
						.getOrderId()) > 0) {
					throw new BizServiceException("订单："
							+ sellOrderDTO.getOrderId() + " 中有准备未完成的明细，不能提交!");
				}
			} else if (OrderConst.ORDER_TYPE_ORDER_CHANGE_CARD
					.equals(sellOrderDTO.getOrderType())) {
				SellOrderOrigCardListExample example = new SellOrderOrigCardListExample();
				example.createCriteria().andOrderIdEqualTo(
						sellOrderDTO.getOrderId()).andDataStateEqualTo(
						DataBaseConstant.DATA_STATE_NORMAL);
				List<SellOrderOrigCardList> origCardLists = sellOrderOrigCardListDAO
						.selectByExample(example);
				for (SellOrderOrigCardList origCard : origCardLists) {
					if (!origCard.getCardSate().equals(
							OrderConst.ORIG_CARD_STAT_ENTSTOCK)
							&& !origCard.getCardSate().equals(
									OrderConst.ORIG_CARD_STAT_DESTORY)) {
						throw new BizServiceException("订单："
								+ sellOrderDTO.getOrderId()
								+ " 中有未处理的原有卡信息,不能提交!");
					}
				}
				List<SellOrderList> sellOrderLists = orderBaseQueryBO
						.getSellOrderListByOrderId(sellOrderDTO.getOrderId());
				for (SellOrderList orderList : sellOrderLists) {
					Integer cardAmt = new Integer(orderList.getCardAmount());
					Integer realAmt = new Integer(
							orderList.getRealAmount() == null
									|| orderList.getRealAmount().equals("") ? "0"
									: orderList.getRealAmount());
					if (cardAmt > realAmt) {
						throw new BizServiceException("订单："
								+ sellOrderDTO.getOrderId()
								+ " 中有准备未完成的明细，不能提交!");
					}
				}
				/* 验证通过后，处理换卡订单原有卡信息 */
				List<String> entStockOrigCards = new ArrayList<String>();
				List<String> destoryOrigCardsList = new ArrayList<String>();
				for (SellOrderOrigCardList origCardList : origCardLists) {
					if (origCardList.getCardSate().equals(
							OrderConst.ORIG_CARD_STAT_DESTORY)) {
						destoryOrigCardsList.add(origCardList.getCardNo());
					} else if (origCardList.getCardSate().equals(
							OrderConst.ORIG_CARD_STAT_ENTSTOCK)) {
						entStockOrigCards.add(origCardList.getCardNo());
					}
				}

				// 更新库存
				List<EntityStock> entityStockList = new ArrayList<EntityStock>();

				for (SellOrderOrigCardList origCard : origCardLists) {
					EntityStock entityStcok = new EntityStock();
					entityStcok.setCardNo(origCard.getCardNo());
					entityStcok.setEntityId(sellOrderDTO.getProcessEntityId());
					if (origCard.getCardSate().equals(
							OrderConst.ORIG_CARD_STAT_ENTSTOCK)) {
						entityStcok.setStockState(OrderConst.CARD_STOCK_IN);
					} else if (origCard.getCardSate().equals(
							OrderConst.ORIG_CARD_STAT_DESTORY)) {
						entityStcok
								.setStockState(OrderConst.CARD_STOCK_DESTORY);
					}
					entityStcok.setCardValidDate(origCard.getValidityPeriod());
					entityStcok.setModifyTime(DateUtil.getCurrentTime24());
					entityStcok.setModifyUser(sellOrderDTO.getLoginUserId());
					entityStockList.add(entityStcok);
				}

				/* 报文将销户的卡销户 :3 */
				if (null != destoryOrigCardsList
						&& destoryOrigCardsList.size() > 0) {
					OprateOrigCard(destoryOrigCardsList, "3");
				}

				/* 报文将入库的卡清零并撤销激活 :0 */
				if (null != entStockOrigCards && entStockOrigCards.size() > 0) {
					OprateOrigCard(entStockOrigCards, "0");
				}
				orderBO.updateEntityStockByPrimaryKey(entityStockList);
				// 记录入库原卡库存操作日志
				entityStockService
						.addStockOperaterRecord(entStockOrigCards, sellOrderDTO
								.getOrderId(), sellOrderDTO
								.getDefaultEntityId(),
								Const.FUNCTION_ROLE_SELLER,
								OrderConst.ORDER_STATE_ORDER_READY,
								OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION,
								OrderConst.CARD_STOCK_IN, sellOrderDTO
										.getLoginUserId());

				entityStockService.addStockOperaterRecord(destoryOrigCardsList,
						sellOrderDTO.getOrderId(), sellOrderDTO
								.getDefaultEntityId(),
						Const.FUNCTION_ROLE_SELLER,
						OrderConst.ORDER_STATE_ORDER_READY,
						OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION,
						OrderConst.CARD_STOCK_DESTORY, sellOrderDTO
								.getLoginUserId());
			}
			orderBO.orderFlowNexNode(sellOrderDTO.getOrderId(),
					OrderConst.ORDER_STATE_ORDER_SEND, sellOrderDTO
							.getLoginUserId(), sellOrderDTO
							.getDefaultEntityId(),
					OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION, sellOrderDTO
							.getOperationMemo(), OrderConst.ORDER_FLOW_READY);
		} catch (BizServiceException e) {
			throw e;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("提交订单失败!");
		}
	}

	/**
	 * 对于配送节点 提交订单将卡 至为已出库状态
	 */
	@Override
	public void submitOrderAtHandOut(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		try {

			// orderBO.updateEntityStockOutByOrderId(sellOrderDTO.getOrderId());

			List<String> cardNoList = orderCardListService
					.getSuccessCardNoList(sellOrderDTO.getOrderId());
			if (OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_SIGN
					.equals(sellOrderDTO.getOrderType())) {
				orderBO
						.updateEntityStockOutByRecursionOrderCardList(sellOrderDTO
								.getOrderId());

				orderBO.orderFlowNexNode(sellOrderDTO.getOrderId(),
						OrderConst.ORDER_STATE_ORDER_BRANCH_ACCEPT,
						sellOrderDTO.getLoginUserId(), sellOrderDTO
								.getDefaultEntityId(),
						OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION,
						sellOrderDTO.getOperationMemo(),
						OrderConst.ORDER_FLOW_SEND);

				/***
				 * 如果是销售订单流入待配送确定 如果是采购订单则流入下一级
				 * 
				 */
			} else if (OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_UNSIGN
					.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN
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
					|| OrderConst.ORDER_TYPE_ORDER_CHANGE_CARD
							.equals(sellOrderDTO.getOrderType())) {
				if (!OrderConst.ORDER_PAY_STATE_PAID.equals(sellOrderDTO
						.getPaymentState())
						&& "3".equals(sellOrderDTO.getPaymentTerm())) {
					throw new BizServiceException("订单未支付，不能配送成功");
				}
				if (OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_UNSIGN
						.equals(sellOrderDTO.getOrderType())) {
					orderBO.orderFlowNexNode(sellOrderDTO.getOrderId(),
							OrderConst.ORDER_STATE_ORDER_BRANCH_ACCEPT,
							sellOrderDTO.getLoginUserId(), sellOrderDTO
									.getDefaultEntityId(),
							OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION,
							sellOrderDTO.getOperationMemo(),
							OrderConst.ORDER_FLOW_SEND);

				} else {
					orderBO.orderFlowNexNode(sellOrderDTO.getOrderId(),
							OrderConst.ORDER_STATE_ORDER_WAIT_SEND_CONFIRM,
							sellOrderDTO.getLoginUserId(), sellOrderDTO
									.getDefaultEntityId(),
							OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION,
							sellOrderDTO.getOperationMemo(),
							OrderConst.ORDER_FLOW_SEND);
				}
				// List<String> cardNoList = orderCardListService
				// .getSuccessCardNoList(sellOrderDTO
				// .getOrderId());
				orderBO.updateEntityStockOutByBuyOrderCardList(sellOrderDTO
						.getOrderId());

			}
			SellOrder sellOrder = new SellOrder();
			ReflectionUtil.copyProperties(sellOrderDTO, sellOrder);
			sellOrder.setModifyTime(DateUtil.getCurrentTime());
			sellOrder.setModifyUser(sellOrderDTO.getLoginUserId());
			sellOrderDAO.updateByPrimaryKeySelective(sellOrder);
			// 记录库存操作日志
			entityStockService.addStockOperaterRecord(cardNoList, sellOrderDTO
					.getOrderId(), sellOrderDTO.getDefaultEntityId(),
					Const.FUNCTION_ROLE_SELLER,
					OrderConst.ORDER_STATE_ORDER_SEND,
					OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION,
					OrderConst.CARD_STOCK_OUT, sellOrderDTO.getLoginUserId());

		} catch (BizServiceException b) {
			throw b;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("提交订单失败");
		}
	}

	/**
	 * 订单配送确定
	 */
	@Override
	public void submitOrderSendConfirm(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		try {
			// 获取订单详细信息
			List<SellOrderListDTO> list = getOrderDetail(sellOrderDTO);
			String batchNo = null;
			try {
				batchNo = fileBatchService.fileBatch("businessRechargeAct",
						sellOrderDTO.getOrderId(), list);
			} catch (Exception e) {
				this.logger.error(e.getMessage());
			}
			logger.debug("rechargeAct BatchNo :" + batchNo);
			SellOrderDTO sellOrder = new SellOrderDTO();
			sellOrder.setOrderId(sellOrderDTO.getOrderId());
			if (null != batchNo && !"".equals(batchNo)) {
				sellOrder.setBatchNo(batchNo);
				// 更新订单状态
				updateOrderState(sellOrder);
			} else {
				sellOrder
						.setOrderState(OrderConst.ORDER_STATE_ORDER_WAIT_SEND_CONFIRM);
				// 回滚订单状态
				updateOrderState(sellOrder);
			}
		} catch (BizServiceException ex) {
			throw ex;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("提交订单失败 ");
		}

	}

	// 获取订单详细信息
	private List<SellOrderListDTO> getOrderDetail(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		List<SellOrderListDTO> list;
		try {
			SellOrderExample sellOrderExample = new SellOrderExample();
			sellOrderExample.createCriteria().andOrderIdEqualTo(
					sellOrderDTO.getOrderId()).andDataStateEqualTo("1");

			logger.debug("==>befor Recharge!");
			if (sellOrderDTO.getInitActStat() == null
					|| sellOrderDTO.getInitActStat().equals("")) {
				sellOrderDTO.setInitActStat("0");
			}
			SellOrder sellOrderById=sellOrderDAO.selectByPrimaryKey(sellOrderDTO.getOrderId());
			if(sellOrderById.getOrderType().equals(OrderConst.ORDER_TYPE_ORDER_CHANGE_CARD)){
				Product product=productDAO.selectByPrimaryKey(sellOrderById.getProductId());
				if(product.getOnymousStat().equals("2")){
					sellOrderDTO.setInitActStat("1");
				}
			}

			List<SellOrderListDTO> sellOrderList = ready(sellOrderDTO);
			list = new ArrayList<SellOrderListDTO>();
			for (SellOrderListDTO sellOrderListdto : sellOrderList) {
				sellOrderListdto.setActFlag(sellOrderDTO.getInitActStat());
				list.add(sellOrderListdto);
			}
		} catch (BizServiceException b) {
			throw b;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("获取订单详细信息失败！");
		}
		return list;
	}

	/**
	 * 修改订单状态为处理中
	 * 
	 * @param sellOrderDTO
	 * @return
	 * @throws BizServiceException
	 */
	@Override
	public void submitOrder(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		try {
			SellOrderDTO dto = orderBO.selectForUpdate(sellOrderDTO
					.getOrderId());
			if (!"31".equals(dto.getOrderState()) && !"34".equals(dto.getOrderState())) {
				throw new BizServiceException("订单已提交");
			}

			// 记录订单流程
			logger.debug("==>before write orderFlow!");
			orderBO.orderFlowNexNode(sellOrderDTO.getOrderId(),
					OrderConst.ORDER_STATE_ORDER_PROCESSING, sellOrderDTO
							.getLoginUserId(), sellOrderDTO
							.getDefaultEntityId(),
					OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION, sellOrderDTO
							.getOperationMemo(),
					OrderConst.ORDER_FLOW_SEND_CONFIRM);
		} catch (BizServiceException ex) {
			throw ex;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("提交订单失败 ");
		}
	}

	/**
	 * 对于待配送确定的订单 应该对订单进行充值
	 * 
	 * @throws BizServiceException
	 */
	@Override
	public void submitOrderAtSendConfirm(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		try {
			logger
					.debug("==>enter into Class:SubmitOrderServiceImpl method:submitOrderAtSendConfirm.");
			String batchNo = "";
			// 获取订单详细信息
			SellOrderExample sellOrderExample = new SellOrderExample();
			sellOrderExample.createCriteria().andOrderIdEqualTo(
					sellOrderDTO.getOrderId()).andDataStateEqualTo("1");

			logger.debug("==>befor Recharge!");
			// 充值 订单状态修改为已派送
			if (sellOrderDTO.getInitActStat() == null
					|| sellOrderDTO.getInitActStat().equals("")) {
				sellOrderDTO.setInitActStat("0");
			}
			try {
				List<SellOrderListDTO> sellOrderList = ready(sellOrderDTO);
				List<SellOrderListDTO> list = new ArrayList<SellOrderListDTO>();
				for (SellOrderListDTO sellOrderListdto : sellOrderList) {
					sellOrderListdto.setActFlag(sellOrderDTO.getInitActStat());
					list.add(sellOrderListdto);
				}
				// 批量充值框架
				// batchNo =
				// rechargeActBatchFileService.fileBatch(sellOrderDTO
				// .getOrderId(), list);
				batchNo = fileBatchService.fileBatch("businessRechargeAct",
						sellOrderDTO.getOrderId(), list);
				// sendRechargeMessage(generateRechargeFile(sellOrderDTO),
				// sellOrderDTO.getInitActStat(),sellOrderDTO.getOrderId());
			} catch (BizServiceException b) {
				logger.error(b.getMessage());
			}
			logger.debug("end Recharge!");

			// 记录订单流程
			logger.debug("==>before write orderFlow!");
			orderBO.orderFlowNexNode(sellOrderDTO.getOrderId(),
					OrderConst.ORDER_STATE_ORDER_PROCESSING, sellOrderDTO
							.getLoginUserId(), sellOrderDTO
							.getDefaultEntityId(),
					OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION, sellOrderDTO
							.getOperationMemo(),
					OrderConst.ORDER_FLOW_SEND_CONFIRM);
			logger.debug("end write orderFlow!");
			/* 此处更新订单的激活状态 */
			SellOrder tempOrder = new SellOrder();
			tempOrder.setOrderId(sellOrderDTO.getOrderId());
			tempOrder.setInitActStat(sellOrderDTO.getInitActStat());
			tempOrder.setBatchNo(batchNo);
			sellOrderDAO.updateByPrimaryKeySelective(tempOrder);
			logger.debug("out of Class:SubmitOrderServiceImpl method:submitOrderAtSendConfirm.");
		} catch (BizServiceException b) {
			logger.error(b.getMessage());
			throw b;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("提交订单失败");
		}
	}

	/**
	 * 订单接收提交 分库订单入库
	 */
	@Override
	public void submitOrderAtAccept(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
	    if(sellOrderDTO.getOrderType().equals(OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN)){
	    //判断下接收数量和配送数量
	    Integer num=orderBO.getAcceptNum(sellOrderDTO);
        if(!num.toString().equals(sellOrderDTO.getRealCardQuantity())){
            throw new BizServiceException("该订单还未接收完毕，请接收完后在提交！");
        }
	   
		try {
		    //接收卡片在页面接收按钮完成，不在确认完成。确认时只需要判断接收数量和配送数量，来决定是否可以提交。
//			orderBO.orderAcceptInStock(sellOrderDTO);
			orderBO.orderFlowNexNode(sellOrderDTO.getOrderId(),
					OrderConst.ORDER_STATE_ORDER_STOCK, sellOrderDTO
							.getLoginUserId(), sellOrderDTO
							.getDefaultEntityId(),
					OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION, sellOrderDTO
							.getOperationMemo(),
					OrderConst.ORDER_FLOW_BRANCH_ACCEPT);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			e.printStackTrace();
			throw new BizServiceException("提交订单失败");
		}
	    }else{
	        try {
	            //如果不是30000002，按照老的方式接收。
	            orderBO.orderAcceptInStock(sellOrderDTO);
	            orderBO.orderFlowNexNode(sellOrderDTO.getOrderId(),
	                    OrderConst.ORDER_STATE_ORDER_STOCK, sellOrderDTO
	                            .getLoginUserId(), sellOrderDTO
	                            .getDefaultEntityId(),
	                    OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION, sellOrderDTO
	                            .getOperationMemo(),
	                    OrderConst.ORDER_FLOW_BRANCH_ACCEPT);
	        } catch (Exception e) {
	            this.logger.error(e.getMessage());
	            e.printStackTrace();
	            throw new BizServiceException("提交订单失败");
	        }
	    }
	}

	/**
	 * 订单接收退回 订单退回至发行机构配送
	 */
	@Override
	public void sendBackOrderAtAccept(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
	    Integer num=orderBO.getAcceptNum(sellOrderDTO);
        if(num.intValue()>0){
            throw new BizServiceException("该订单已经部分接收，不能退回！");
        }
		try {
			orderBO.orderFlowNexNode(sellOrderDTO.getOrderId(),
					OrderConst.ORDER_STATE_ORDER_SEND, sellOrderDTO
							.getLoginUserId(), sellOrderDTO
							.getDefaultEntityId(),
					OrderConst.ORDER_FLOW_OPRATION_BACK, sellOrderDTO
							.getOperationMemo(),
					OrderConst.ORDER_FLOW_BRANCH_ACCEPT);
			// 退回订单，订单关联的卡退回原机构。
			List<String> cardNos = orderCardListService
					.getSuccessCardNoList(sellOrderDTO.getOrderId());
			if (sellOrderDTO.getOrderType().equals(
					OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_SIGN)
					|| sellOrderDTO.getOrderType().equals(
							OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_SIGN)) {
				entityStockService.modifyStockState(cardNos,
						OrderConst.CARD_STOCK_OUT, OrderConst.CARD_STOCK_IN);
				// 记录库存操作日志
				entityStockService
						.addStockOperaterRecord(cardNos, sellOrderDTO
								.getOrderId(), sellOrderDTO
								.getProcessEntityId(),
								Const.FUNCTION_ROLE_ISSUER,
								OrderConst.ORDER_STATE_ORDER_ACCEPT,
								OrderConst.ORDER_FLOW_OPRATION_BACK,
								OrderConst.CARD_STOCK_IN, sellOrderDTO
										.getLoginUserId());
			} else if (sellOrderDTO.getOrderType().equals(
					OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN)
					|| sellOrderDTO
							.getOrderType()
							.equals(
									OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_UNSIGN)) {
				entityStockService.modifyStockState(cardNos,
						OrderConst.CARD_STOCK_OUT,
						OrderConst.CARD_STOCK_READY_OUT);
				// 记录库存操作日志
				entityStockService.addStockOperaterRecord(cardNos, sellOrderDTO
						.getOrderId(), sellOrderDTO.getProcessEntityId(),
						Const.FUNCTION_ROLE_ISSUER,
						OrderConst.ORDER_STATE_ORDER_ACCEPT,
						OrderConst.ORDER_FLOW_OPRATION_BACK,
						OrderConst.CARD_STOCK_READY_OUT, sellOrderDTO
								.getLoginUserId());
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("提交订单失败");
		}
	}

	public String generateRechargeFile(SellOrderDTO sellOrderDTO)
			throws Exception {
		try {
			List<SellOrderListDTO> sellOrderList = new ArrayList<SellOrderListDTO>();
			if (OrderConst.ORDER_TYPE_SELL_CUSTOMER_CREDIT_ORDER
					.equals(sellOrderDTO.getOrderType())) {

				sellOrderList = orderBaseQueryBO
						.getCreditCardListByOrderId(sellOrderDTO.getOrderId());

			} else if (OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_UNSIGN
					.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_UNSIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_UNSIGN
							.equals(sellOrderDTO.getOrderType())) {

				sellOrderList = orderBaseQueryBO
						.getSellUnSignCardListByOrderId(sellOrderDTO
								.getOrderId());

			} else if (OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN
					.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_SIGN
							.equals(sellOrderDTO.getOrderType())) {

				sellOrderList = orderBaseQueryBO
						.getSellSignCardListByOrderId(sellOrderDTO.getOrderId());

			} else if (OrderConst.ORDER_TYPE_ORDER_CHANGE_CARD
					.equals(sellOrderDTO.getOrderType())) {
				sellOrderList = orderBaseQueryBO
						.getChangeCardOrderCardListByOrderId(sellOrderDTO
								.getOrderId());
			}
			String fileName = "Recharge"
					+
					/**
					 * 不足5位补零
					 */
					StringUtil.getFormatStr(ConfigMakeCard.getPlatfromNo(),
							"0", 5, false)
					/***
					 * 不足10位补零
					 */
					+ StringUtil.getFormatStr(commonsDAO
							.getNextValueOfSequence("TB_ORDER_BATCH_FILE"),
							"0", 10, true) + "Active";

			if (DictInfoConstants.PAY_MENT_IS_NOT_NEED_PAY_IMMEDIATELY
					.equals(sellOrderDTO.getPaymentTerm())) {
				fileName += "1";
			} else {
				fileName += "0";
			}
			StringBuffer strBuf = new StringBuffer("");
			for (SellOrderListDTO sellOrderListDTO : sellOrderList) {
				/**
				 * 卡号不足19位右补空格
				 */
				strBuf.append(
						StringUtil.getFormatStr(sellOrderListDTO.getCardNo(),
								" ", 19, false)).append(
						StringUtil.getFormatStr(sellOrderListDTO
								.getValidityPeriod(), " ", 8, false))
						.append(
								StringUtil.getFormatStr(sellOrderListDTO
										.getServiceId(), " ", 8, false))
						.append(
								StringUtil.getFormatStr(sellOrderListDTO
										.getFaceValue(), " ", 12, false))
						.append("00000000").append("\n");
			}

			// FtpUtil.ftpPutFile(strBuf.toString(), fileName,
			// RechargeConfig.getIp(),
			// Integer.parseInt(RechargeConfig.getPort()),
			// RechargeConfig.getUserName(),
			// RechargeConfig.getPassword(),
			// RechargeConfig.getUploadFilePath());

			BufferedWriter bw = null;
			File tempFile = new File(RechargeConfig.getUploadFilePath()
					+ fileName);

			// 生成文件
			try {
				tempFile.createNewFile();
				bw = new BufferedWriter(new FileWriter(tempFile));
				bw.write(strBuf.toString());
				bw.flush();
			} catch (Exception e) {
				this.logger.error(e.getMessage());
				throw new BizServiceException("写入制卡文件失败！");
			} finally {
				if (null != bw) {
					bw.close();
				}
				java.lang.Runtime rt = java.lang.Runtime.getRuntime();
				// 当前系统不是WINDOWS.修改文件权限
				if (System.getProperty("os.name") != null
						&& System.getProperty("os.name").indexOf("Windows") == -1) {
					rt.exec("chmod 754 " + tempFile.toString());
				}
			}
			/***
			 * 记录订单与文件的关联
			 */
			orderBatchFileService.insertOrderFile(fileName, sellOrderDTO);
			return fileName.toString();
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw e;
		}

	}

	// 充值
	@Override
	public List<SellOrderListDTO> ready(SellOrderDTO sellOrderDTO)
			throws Exception {
		// Map<String, String> rspCodeMap =
		// RspCodeMap.getRspCodeMap();
		// AccPackageDTO accPackageDTO = new AccPackageDTO();
		try {
			List<SellOrderListDTO> sellOrderList = new ArrayList<SellOrderListDTO>();
			if (OrderConst.ORDER_TYPE_SELL_CUSTOMER_CREDIT_ORDER
					.equals(sellOrderDTO.getOrderType())) {

				sellOrderList = orderBaseQueryBO
						.getCreditCardListByOrderId(sellOrderDTO.getOrderId());

			} else if (OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_UNSIGN
					.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_UNSIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_UNSIGN
							.equals(sellOrderDTO.getOrderType())) {

				sellOrderList = orderBaseQueryBO
						.getSellUnSignCardListByOrderId(sellOrderDTO
								.getOrderId());

			} else if (OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN
					.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_SIGN
							.equals(sellOrderDTO.getOrderType())) {

				sellOrderList = orderBaseQueryBO
						.getSellSignCardListByOrderId(sellOrderDTO.getOrderId());

			} else if (OrderConst.ORDER_TYPE_ORDER_CHANGE_CARD
					.equals(sellOrderDTO.getOrderType())) {
				sellOrderList = orderBaseQueryBO
						.getChangeCardOrderCardListByOrderId(sellOrderDTO
								.getOrderId());
			}

			// 0002
			// accPackageDTO.setTxnCode(Const.TXN_TYPE_CODE_RECHARGE);
			// accPackageDTO.setTxnDate(DateUtil.getCurrentDateStr());
			// accPackageDTO.setTxnTime(DateUtil.getCurrenTime24());
			// accPackageDTO.setFilePath(fileName);
			// accPackageDTO = java2ACCBusinessService
			// .sendRechargeMessage(accPackageDTO);
			//
			// if ("00".equals(accPackageDTO.getRespCode())) {
			// } else {
			// if
			// (rspCodeMap.containsKey(accPackageDTO.getRespCode())) {
			// throw new
			// BizServiceException(rspCodeMap.get(accPackageDTO
			// .getRespCode()));
			// }
			// }
			return sellOrderList;
		} catch (BizServiceException b) {
			throw b;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("充值失败！！");
		}

		// 以前的代码
		/*
		 * Map<Integer, String> parameter = new HashMap<Integer,
		 * String>();
		 * 
		 * parameter.put(CFunctionConstant.TXN_TYPE,
		 * Const.TXN_TYPE_CODE_RECHARGE);
		 * 
		 * parameter.put(CFunctionConstant.SWT_TXN_DATE,
		 * DateUtil.getCurrentDateStr());
		 * 
		 * parameter.put(CFunctionConstant.SWT_TXN_TIME,
		 * DateUtil.getCurrenTime24());
		 * 
		 * parameter.put(CFunctionConstant.FILE_PATH, fileName);
		 * 
		 * Map map = (Map)
		 * java2C.sendTpService(Const.SERVICE_NAME_RECHARGE,parameter,
		 * Const.JAVA2C_NORMAL, true).getDetailvo();
		 * 
		 * if ("00".equals(map.get(CFunctionConstant.RESP_CODE))) { }
		 * else { throw new BizServiceException("充值失败!"); }
		 */
	}

	// 充值
	public void sendRechargeMessage(String fileName, String actState,
			String orderId) throws Exception {
		Map<String, String> rspCodeMap = RspCodeMap.getRspCodeMap();
		AccPackageDTO accPackageDTO = new AccPackageDTO();
		try {
			// 0002
			accPackageDTO.setTxnCode(Const.TXN_TYPE_CODE_RECHARGE);
			accPackageDTO.setTxnDate(DateUtil.getCurrentDateStr());
			accPackageDTO.setTxnTime(DateUtil.getCurrenTime24());
			accPackageDTO.setFilePath(fileName);
			accPackageDTO.setActiveState(actState);
			accPackageDTO.setRechargeNo(orderId);
			accPackageDTO = java2ACCBusinessService
					.sendRechargeMessage(accPackageDTO);

			if (!"00".equals(accPackageDTO.getRespCode())) {
				if (rspCodeMap.containsKey(accPackageDTO.getRespCode())) {
					throw new BizServiceException(rspCodeMap.get(accPackageDTO
							.getRespCode()));
				}
			}
		} catch (BizServiceException b) {
			throw b;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("充值失败！！");
		}

		/*
		 * Map<Integer, String> parameter = new HashMap<Integer,
		 * String>();
		 * 
		 * parameter.put(CFunctionConstant.TXN_TYPE,
		 * Const.TXN_TYPE_CODE_RECHARGE);
		 * 
		 * parameter.put(CFunctionConstant.SWT_TXN_DATE, DateUtil
		 * .getCurrentDateStr());
		 * 
		 * parameter.put(CFunctionConstant.SWT_TXN_TIME, DateUtil
		 * .getCurrenTime24());
		 * 
		 * parameter.put(CFunctionConstant.FILE_PATH, fileName);
		 * 
		 * parameter.put(CFunctionConstant.PIN_QUIRY_NEW, actState);
		 * 
		 * Map map = (Map)
		 * java2C.sendTpService(Const.SERVICE_NAME_RECHARGE,parameter,
		 * Const.JAVA2C_NORMAL, true).getDetailvo();
		 * 
		 * if ("00".equals(map.get(CFunctionConstant.RESP_CODE))) { }
		 * else { throw new BizServiceException("充值失败!"); }
		 */

	}

	@Override
	public void submitOrderImmdiatelyCredit(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		try {
			List<SellOrderListDTO> list = ready(sellOrderDTO);
			String batchNo = null;
			try {
				batchNo = fileBatchService.fileBatch("businessRecharge",
						sellOrderDTO.getOrderId(), list);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				this.logger.error(e.getMessage());
			}
			logger.debug("Activate BatchNo :" + batchNo);
			SellOrderDTO sellOrder = new SellOrderDTO();
			sellOrder.setOrderId(sellOrderDTO.getOrderId());
			if (null != batchNo && !"".equals(batchNo)) {
				sellOrder.setBatchNo(batchNo);
				// 更新订单状态
				updateOrderState(sellOrder);
			} else {
				sellOrder.setOrderState(OrderConst.ORDER_STATE_WAITTING_CREDIT);
				// 回滚订单状态
				updateOrderState(sellOrder);
			}
		} catch (BizServiceException ex) {
			throw ex;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("提交订单失败 ");
		}
	}

	// 获取订单信息及更新订单状态为处理中
	@Override
	public void submitOrderAtCredit(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		try {
			// 获取订单详细信息
			// SellOrderExample sellOrderExample = new
			// SellOrderExample();
			// sellOrderExample.createCriteria().andOrderIdEqualTo(
			// sellOrderDTO.getOrderId()).andDataStateEqualTo("1");
			// List<SellOrder> sellOrders = sellOrderDAO
			// .selectByExample(sellOrderExample);
			// SellOrder currentOrder = new SellOrder();
			// if (null != sellOrders && sellOrders.size() > 0) {
			// currentOrder = sellOrders.get(0);
			// }
			// ReflectionUtil.copyProperties(currentOrder,
			// sellOrderDTO);

			// 锁定订单
			SellOrderDTO dto = orderBO.selectForUpdate(sellOrderDTO
					.getOrderId());
			if (!"15".equals(dto.getOrderState()) && !"34".equals(dto.getOrderState())) {
				throw new BizServiceException("订单已提交");
			}

			orderBO.orderFlowNexNode(sellOrderDTO.getOrderId(),
					OrderConst.ORDER_STATE_ORDER_PROCESSING, sellOrderDTO
							.getLoginUserId(), sellOrderDTO
							.getDefaultEntityId(),
					OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION, sellOrderDTO
							.getOperationMemo(),
					OrderConst.ORDER_FLOW_ORDER_IMMDIATELY_CREDIT);
		} catch (BizServiceException ex) {
			throw ex;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("提交订单失败 ");
		}
	}

	/**
	 * 更新订单批次号
	 * 
	 * @param sellOrderDTO
	 */
	public void updateOrderState(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		try {
			SellOrder sellOrder = new SellOrder();
			sellOrder.setOrderId(sellOrderDTO.getOrderId());
			sellOrder.setBatchNo(sellOrderDTO.getBatchNo());
			sellOrder.setInitActStat(sellOrderDTO.getInitActStat());
			if (null != sellOrderDTO.getOrderState()
					&& !"".equals(sellOrderDTO.getOrderState())) {
				sellOrder.setOrderState(sellOrderDTO.getOrderState());
			}
			sellOrderDAO.updateByPrimaryKeySelective(sellOrder);
		} catch (Exception e) {

			this.logger.error(e.getMessage());
			throw new BizServiceException("更新订单状态失败");
		}
	}

	/***
	 * 订单提交 充值订单立即充值
	 */
	@Override
	public void submitOrderAtOrderImmdiatelyCredit(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		try {
			String batchNo = "";
			// 获取订单详细信息
			SellOrderExample sellOrderExample = new SellOrderExample();
			sellOrderExample.createCriteria().andOrderIdEqualTo(
					sellOrderDTO.getOrderId()).andDataStateEqualTo("1");
			// List<SellOrder> sellOrders = sellOrderDAO
			// .selectByExample(sellOrderExample);
			// SellOrder currentOrder = new SellOrder();
			// if (null != sellOrders && sellOrders.size() > 0) {
			// currentOrder = sellOrders.get(0);
			// }
			// // 先判断订单的支付方式
			// // if充值前&&订单未支付
			// if ("3".equals(currentOrder.getPaymentTerm())
			// && !OrderConst.ORDER_PAY_STATE_PAID.equals(currentOrder
			// .getPaymentState())) {
			// // 返回，充值失败，定单未支付
			// throw new BizServiceException("充值失败，订单未支付");
			// } else {
			// sendRechargeMessage(generateRechargeFile(sellOrderDTO));
			try {
				List<SellOrderListDTO> list = ready(sellOrderDTO);
				// batchNo = rechargeBatchFileService.fileBatch(
				// sellOrderDTO.getOrderId(), list);
				batchNo = fileBatchService.fileBatch("businessRecharge",
						sellOrderDTO.getOrderId(), list);
				// vNoActRecharge(generateRechargeFile(sellOrderDTO),sellOrderDTO.getOrderId());
			} catch (BizServiceException b) {

				logger.error(b.getMessage());
			}

			orderBO.orderFlowNexNode(sellOrderDTO.getOrderId(),
					OrderConst.ORDER_STATE_ORDER_PROCESSING, sellOrderDTO
							.getLoginUserId(), sellOrderDTO
							.getDefaultEntityId(),
					OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION, sellOrderDTO
							.getOperationMemo(),
					OrderConst.ORDER_FLOW_ORDER_IMMDIATELY_CREDIT);

			/* 此处更新订单的激活状态 */
			SellOrder tempOrder = new SellOrder();
			tempOrder.setOrderId(sellOrderDTO.getOrderId());
			tempOrder.setInitActStat(sellOrderDTO.getInitActStat());
			tempOrder.setBatchNo(batchNo);
			sellOrderDAO.updateByPrimaryKeySelective(tempOrder);
			// }
		} catch (BizServiceException ex) {
			throw ex;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("提交订单失败 ");
		}
	}

	public String generateActiveFile(SellOrderDTO sellOrderDTO)
			throws Exception {
		try {
			List<SellOrderListDTO> sellOrderList = new ArrayList<SellOrderListDTO>();
			if (OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_UNSIGN
					.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_UNSIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_UNSIGN
							.equals(sellOrderDTO.getOrderType())) {

				sellOrderList = orderBaseQueryBO
						.getSellUnSignCardListByOrderId(sellOrderDTO
								.getOrderId());

			} else if (OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN
					.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_SIGN
							.equals(sellOrderDTO.getOrderType())) {

				sellOrderList = orderBaseQueryBO
						.getSellSignCardListByOrderId(sellOrderDTO.getOrderId());

			}
			String fileName = "Active"
					+
					/**
					 * 不足5位补零
					 */
					StringUtil.getFormatStr(ConfigMakeCard.getPlatfromNo(),
							"0", 5, false)
					/***
					 * 不足10位补零
					 */
					+ StringUtil.getFormatStr(commonsDAO
							.getNextValueOfSequence("TB_ORDER_BATCH_FILE"),
							"0", 10, true);

			StringBuffer strBuf = new StringBuffer("");
			for (SellOrderListDTO sellOrderListDTO : sellOrderList) {
				/**
				 * 卡号不足19位右补空格
				 */
				strBuf.append(
						StringUtil.getFormatStr(sellOrderListDTO.getCardNo(),
								" ", 19, false)).append("1").append("00000000")
						.append("\n");
			}

			// FtpUtil.ftpPutFile(strBuf.toString(), fileName,
			// RechargeConfig.getIp(),
			// Integer.parseInt(RechargeConfig.getPort()),
			// RechargeConfig.getUserName(),
			// RechargeConfig.getPassword(),
			// RechargeConfig.getUploadFilePath());
			//

			BufferedWriter bw = null;
			File tempFile = new File(RechargeConfig.getUploadFilePath()
					+ fileName);

			// 生成文件
			try {
				tempFile.createNewFile();
				bw = new BufferedWriter(new FileWriter(tempFile));
				bw.write(strBuf.toString());
				bw.flush();
			} catch (Exception e) {
				this.logger.error(e.getMessage());
				throw new BizServiceException("写入制卡文件失败！");
			} finally {
				if (null != bw) {
					bw.close();
				}
			}
			orderBatchFileService.insertOrderFile(fileName, sellOrderDTO);

			return fileName.toString();
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw e;
		}
	}

	/**
	 * 如果是订单是激活前支付 且是未支付 则激活订单中的卡片 同时记录流程日志
	 * 
	 * @param sellOrderDTO
	 * @throws BizServiceException
	 */
	@Override
	public void submitOrderAtOrderPayment(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		try {
			if (OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_UNSIGN
					.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN
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
				if ("1".equals(sellOrderDTO.getInitActStat())
						&& DictInfoConstants.PAY_MENT_IS_ACTIVE_BEFORE
								.equals(sellOrderDTO.getPaymentTerm())
						&& OrderConst.ORDER_STATE_ORDER_SEND_CONFIRM
								.equals(sellOrderDTO.getOrderState())
						&& DictInfoConstants.PAY_STATE_CONFIRM
								.equals(sellOrderDTO.getPaymentState())) {
					sellOrderDTO.setInitActStat(DataBaseConstant.DATA_TYPE_YES);
					sendActiveMessage(generateActiveFile(sellOrderDTO));
				}
			}
			orderBO.orderPayment(sellOrderDTO.getOrderId(), sellOrderDTO
					.getInitActStat(), sellOrderDTO.getIntoBankId(),
					sellOrderDTO.getPayChannel(), sellOrderDTO.getPayDetails(),
					sellOrderDTO.getLoginUserId(), sellOrderDTO
							.getDefaultEntityId(),
					OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION, sellOrderDTO
							.getOperationMemo(),
					OrderConst.ORDER_FLOW_ORDER_SUBMIT);
		} catch (BizServiceException bse) {
			logger.error(bse.getMessage());
			throw bse;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("提交订单失败 ");
		}
	}

	/**
	 * 如果是订单是激活前支付 且是未支付 则激活订单中的卡片 同时记录流程日志
	 * 
	 * @param sellOrderDTO
	 * @throws BizServiceException
	 */
	@Override
	public boolean submitOrderForPayment(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		try {
			if (OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_UNSIGN
					.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN
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
				if ("1".equals(sellOrderDTO.getInitActStat())
						&& DictInfoConstants.PAY_MENT_IS_ACTIVE_BEFORE
								.equals(sellOrderDTO.getPaymentTerm())
						&& OrderConst.ORDER_STATE_ORDER_SEND_CONFIRM
								.equals(sellOrderDTO.getOrderState())
						&& DictInfoConstants.PAY_STATE_CONFIRM
								.equals(sellOrderDTO.getPaymentState())) {
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("提交订单失败 ");
		}
	}

	/**
	 * 
	 * 
	 * @param sellOrderDTO
	 * @throws BizServiceException
	 */
	@Override
	public void confirmAtOrderPayment(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		sellOrderDTO.setPaymentState(DictInfoConstants.PAY_STATE_CONFIRM);
		sellOrderDTO.setModifyTime(DateUtil.getCurrentTime());
		sellOrderDTO.setModifyUser(sellOrderDTO.getLoginUserId());
		sellOrder = new SellOrder();
		ReflectionUtil.copyProperties(sellOrderDTO, sellOrder);
		sellOrder.setOrderState(null);
		sellOrderDAO.updateByPrimaryKeySelective(sellOrder);

		orderBO.orderFlow(sellOrderDTO.getOrderId(), sellOrderDTO
				.getDefaultEntityId(),
				OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION,
				OrderConst.ORDER_FLOW_ORDER_PAYMENT, sellOrderDTO.getMemo(),
				sellOrderDTO.getModifyUser());
	}

	/**
	 * 
	 * 跳转到合并付款页面
	 * 
	 * @param sellOrderDTO
	 * @return
	 * @throws BizServiceException
	 */
	@Override
	public SellOrderDTO combineAtOrderPayment(SellOrderDTO sellOrderDTO)
			throws Exception {
		try {
			sellOrderDTO.setOrderIds(sellOrderDTO.getOrderIds()[0].split(","));
			String[] ids = sellOrderDTO.getOrderIds();
			List<SellOrderDTO> dtos = orderBaseQueryBO
					.getFirstEntityId(sellOrderDTO);
			String entityId;
			if (null != dtos && dtos.size() == 1) {
				entityId = dtos.get(0).getFirstEntityId();
			} else {
				throw new BizServiceException("必须是同一个客户下的订单才能合并付款！！");
			}
			List<String> list = Arrays.asList(ids);
			SellOrderPaymentExample example = new SellOrderPaymentExample();
			example.createCriteria().andOrderIdIn(list)
					.andDataStateEqualTo("1");
			int count = sellOrderPaymentDAO.countByExample(example);
			if (count > 0) {
				throw new BizServiceException("选中的订单已有付款信息！！");
			}
			sellOrderDTO = orderBaseQueryBO.getOrderTotalPrice(sellOrderDTO);
			sellOrderDTO.setOrderIds(ids);
			sellOrderDTO.setFirstEntityId(entityId);
		} catch (BizServiceException b) {
			throw b;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return sellOrderDTO;
	}

	// 激活文件
	public void sendActiveMessage(String fileName) throws Exception {
		Map<String, String> rspCodeMap = RspCodeMap.getRspCodeMap();
		AccPackageDTO accPackageDTO = new AccPackageDTO();
		try {
			// 0002
			accPackageDTO.setTxnCode(Const.TXN_TYPE_CODE_ACTIVE);
			accPackageDTO.setTxnDate(DateUtil.getCurrentDateStr());
			accPackageDTO.setTxnTime(DateUtil.getCurrenTime24());
			accPackageDTO.setFilePath(fileName);

			accPackageDTO = java2ACCBusinessService
					.sendActiveMessage(accPackageDTO);

			if (!"00".equals(accPackageDTO.getRespCode())) {
				if (rspCodeMap.containsKey(accPackageDTO.getRespCode())) {
					throw new BizServiceException(rspCodeMap.get(accPackageDTO
							.getRespCode()));
				}
			}
		} catch (BizServiceException b) {
			throw b;
		} catch (Exception e) {

			this.logger.error(e.getMessage());
			throw new BizServiceException("激活失败！！");
		}

		// 之前的代码
		/*
		 * Map<Integer, String> parameter = new HashMap<Integer,
		 * String>();
		 * 
		 * parameter.put(CFunctionConstant.TXN_TYPE,
		 * Const.TXN_TYPE_CODE_ACTIVE);
		 * 
		 * parameter.put(CFunctionConstant.SWT_TXN_DATE,
		 * DateUtil.getCurrentDateStr());
		 * 
		 * parameter.put(CFunctionConstant.SWT_TXN_TIME,
		 * DateUtil.getCurrenTime24());
		 * 
		 * parameter.put(CFunctionConstant.FILE_PATH, fileName);
		 * 
		 * Map map = (Map)
		 * java2C.sendTpService(Const.SERVICE_NAME_ACTIVE, parameter,
		 * Const.JAVA2C_NORMAL, true).getDetailvo();
		 * 
		 * if ("00".equals(map.get(CFunctionConstant.RESP_CODE))) { }
		 * else { throw new BizServiceException("激活失败!"); }
		 */

	}

	// 充值（文件）
	public void vNoActRecharge(String fileName, String orderId)
			throws Exception {
		Map<String, String> rspCodeMap = RspCodeMap.getRspCodeMap();
		AccPackageDTO accPackageDTO = new AccPackageDTO();
		try {
			accPackageDTO.setTxnCode(Const.TXN_TYPE_CODE_RECHARGE_ORDER);
			accPackageDTO.setTxnDate(DateUtil.getCurrentDateStr());
			accPackageDTO.setTxnTime(DateUtil.getCurrenTime24());
			accPackageDTO.setFilePath(fileName);
			accPackageDTO.setRechargeNo(orderId);

			accPackageDTO = java2ACCBusinessService
					.vNoActRecharge(accPackageDTO);

			/*
			 * Map<Integer, String> parameter = new HashMap<Integer,
			 * String>();
			 * 
			 * parameter.put(CFunctionConstant.TXN_TYPE,Const.
			 * TXN_TYPE_CODE_RECHARGE_ORDER);
			 * 
			 * parameter.put(CFunctionConstant.SWT_TXN_DATE,
			 * DateUtil.getCurrentDateStr());
			 * 
			 * parameter.put(CFunctionConstant.SWT_TXN_TIME,
			 * DateUtil.getCurrenTime24());
			 * 
			 * parameter.put(CFunctionConstant.FILE_PATH, fileName);
			 * 
			 * Map map = (Map)
			 * java2C.sendTpService(Const.SERVICE_NAME_RECHARGE_ORDER,
			 * parameter, Const.JAVA2C_NORMAL, true).getDetailvo();
			 */

			if (!"00".equals(accPackageDTO.getRespCode())) {
				if (rspCodeMap.containsKey(accPackageDTO.getRespCode())) {
					throw new BizServiceException(rspCodeMap.get(accPackageDTO
							.getRespCode()));
				}
			}
		} catch (BizServiceException b) {
			throw b;
		} catch (Exception e) {

			this.logger.error(e.getMessage());
			throw new BizServiceException("充值失败！！");
		}

	}

	// 卡片操作
	public void OprateOrigCard(List<String> cardNoList, String oprateType)
			throws BizServiceException {
		Map<String, String> rspCodeMap = RspCodeMap.getRspCodeMap();
		try {
			String cardNoString = cardNoList.toString().replace("[", "")
					.replace("]", "").replace("{", "").replace("}", "")
					.replace(" ", "");

			AccPackageDTO accPackageDTO = new AccPackageDTO();
			accPackageDTO.setTxnCode("M100");
			accPackageDTO.setChannel(HSTProperties.getString("CHNL_ID"));
			accPackageDTO.setCardNo(cardNoString);
			accPackageDTO.setActiveState(oprateType);
			accPackageDTO = java2ACCBusinessService
					.OprateOrigCard(accPackageDTO);

			if (!"00".equals(accPackageDTO.getRespCode())) {
				if (rspCodeMap.containsKey(accPackageDTO.getRespCode())) {
					throw new BizServiceException(rspCodeMap.get(accPackageDTO
							.getRespCode()));
				}
			}
		} catch (BizServiceException bse) {
			logger.error(bse);
			throw bse;
		} catch (Exception e) {
			logger.error(e);
			throw new BizServiceException("原有卡操作异常!");
		}
		/*
		 * paramMap.put(CFunctionConstant.TXN_TYPE, "M100");
		 * paramMap.put(CFunctionConstant.CHANNEL, "888888");
		 * paramMap.put(CFunctionConstant.RESV2, cardNoString);
		 * paramMap.put(CFunctionConstant.PIN_QUIRY, oprateType);
		 * 
		 * Map map = (Map) java2C.sendTpService("Switch", paramMap,
		 * Const.JAVA2C_NORMAL, true).getDetailvo();
		 * 
		 * if ("00".equals(map.get(CFunctionConstant.RESP_CODE))) { //
		 * 解析返回码 } else { throw new BizServiceException("原有卡操作失败!错误码:"
		 * + map.get(CFunctionConstant.RESP_CODE)); } } catch
		 * (BizServiceException bse) { logger.error(bse); throw bse; }
		 * catch (Exception e) { logger.error(e); throw new
		 * BizServiceException("原有卡操作异常!"); }
		 */
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

	public CommonsDAO getCommonsDAO() {
		return commonsDAO;
	}

	public void setCommonsDAO(CommonsDAO commonsDAO) {
		this.commonsDAO = commonsDAO;
	}

	public OrderBatchFileService getOrderBatchFileService() {
		return orderBatchFileService;
	}

	public void setOrderBatchFileService(
			OrderBatchFileService orderBatchFileService) {
		this.orderBatchFileService = orderBatchFileService;
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	@Override
	public void activateConfirm(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		try {

			try {
				sendActiveMessage(generateActiveFile(sellOrderDTO));
			} catch (BizServiceException b) {

				logger.error(b.getMessage());
				throw b;
			}

			// 修改订单激活状态
			sellOrder = sellOrderDAO.selectByPrimaryKey(sellOrderDTO
					.getOrderId());
			sellOrder.setInitActStat("1");
			sellOrder.setModifyTime(DateUtil.getCurrentTime());
			orderBO.updateByPrimaryKeySelective(sellOrder);
			// List<String> sellOrderCardLists = orderCardListService
			// .getSuccessCardNoList(sellOrderDTO.getOrderId());
			// if (null == sellOrderCardLists ||
			// sellOrderCardLists.size() == 0)
			// {
			// throw new BizServiceException("订单卡列表为空");
			// }
			// StringBuffer cardList = new StringBuffer();
			// for (String sellOrderCardList : sellOrderCardLists) {
			// cardList.append(sellOrderCardList + ",");
			// }

			// 激活
			// String cardLists = cardList.toString();
			// cardLists = cardLists.substring(0, cardLists.length() -
			// 1);
			// HashMap map = new HashMap();
			// map.put(CFunctionConstant.TXN_TYPE, "M100");
			// map.put(CFunctionConstant.CHANNEL,
			// Const.JAVA2C_CHANNEL);
			// map.put(CFunctionConstant.RESV2, cardLists);
			// map.put(CFunctionConstant.PIN_QUIRY, "1");

			// if (null != dto.getServiceFee()
			// || !"".equals(dto.getServiceFee().trim())) {
			// map.put(CFunctionConstant.TXN_AMOUNT, getFormatStr(dto
			// .getServiceFee(), " ", 12, false));
			// }
			// OperationResult or = java2C.sendTpService("Switch",
			// map,
			// Const.JAVA2C_NORMAL, false);
			// HashMap map1 = (HashMap) or.getDetailvo();
			// String rspCode = (String)
			// map1.get(CFunctionConstant.RESP_CODE);
			// logger
			// .debug("++++++++++++++" + rspCode
			// + "+++++++++++++++++++++++");
			// Map<String, String> rspCodeMap =
			// RspCodeMap.getRspCodeMap();
			// if ("00".equals(rspCode)) {
			// sellOrder =
			// sellOrderDAO.selectByPrimaryKey(sellOrderDTO
			// .getOrderId());
			// sellOrder.setInitActStat("1");
			// sellOrder.setModifyTime(DateUtil.getCurrentTime());
			// orderBO.updateByPrimaryKeySelective(sellOrder);
			// } else {
			// throw new BizServiceException("订单激活失败");
			// }

		} catch (Exception e) {
			throw new BizServiceException("订单激活失败");
		}

	}

	/**
	 * 修改订单激活状态
	 * 
	 * @param sellOrderDTO
	 * @return
	 * @throws BizServiceException
	 */
	@Override
	public void updateOrderActState(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		try {
			SellOrderDTO dto = orderBO.selectForUpdate(sellOrderDTO
					.getOrderId());
			logger.debug("order act state :" + dto.getInitActStat());
			if (null != dto.getInitActStat()
					&& ("2".equals(dto.getInitActStat().trim())
						 || "1"
							.equals(dto.getInitActStat()))) {
				throw new BizServiceException("订单已提交");
			}
			SellOrder sellOrder = new SellOrder();
			sellOrder.setOrderId(sellOrderDTO.getOrderId());
			sellOrder.setModifyTime(DateUtil.getCurrentTime());
			// 订单修改人
			sellOrder.setModifyUser(sellOrderDTO.getLoginUserId());
			sellOrder.setInitActStat(DataBaseConstant.ORDER_ACT_STATE_ACTING);
			/* 订单支付信息修改 */
			sellOrder.setPayChannel(sellOrderDTO.getPayChannel());
			sellOrder.setPayDetails(sellOrderDTO.getPayDetails());

			/* 订单银行信息修改 */
			sellOrder.setIntoBankId(sellOrderDTO.getIntoBankId());
			sellOrderDAO.updateByPrimaryKeySelective(sellOrder);			
		} catch (BizServiceException ex) {
			throw ex;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("提交订单失败 ");
		}
	}

	@Override
	public void sumbitForActive(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		try {
			// 获取订单详细信息
			List<SellOrderListDTO> list = ready(sellOrderDTO);
			logger.debug("Activate List size:" + list.size());
			String batchNo = null;
			try {
				batchNo = fileBatchService.fileBatch("businessAct",
						sellOrderDTO.getOrderId(), list);				
			} catch (Exception e) {
				this.logger.error(e.getMessage());
			}
			logger.debug("Activate BatchNo :" + batchNo);
			SellOrderDTO sellOrder = new SellOrderDTO();
			sellOrder.setOrderId(sellOrderDTO.getOrderId());
			if (null != batchNo && !"".equals(batchNo)) {
				sellOrder.setBatchNo(batchNo);
				// 更新订单状态
				updateOrderState(sellOrder);
			} else {
				sellOrder
						.setInitActStat(DataBaseConstant.ORDER_ACT_STATE_INACT);
				// 回滚订单状态
				updateOrderState(sellOrder);
			}
		} catch (BizServiceException b) {
			throw b;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("激活失败");
		}
	}
	
    /**
     * 
     * 功能描述: <br>
     * 订单自动激活
     *
     * @param sellOrderDTO
     * @throws BizServiceException
     */
	 @Override
	public void orderAutoActive(SellOrderDTO sellOrderDTO)
           throws BizServiceException {
       try {
           // 获取订单详细信息
           List<SellOrderListDTO> list = ready(sellOrderDTO);
           logger.debug("Activate List size:" + list.size());
           String batchNo = null;
           try {
               batchNo = fileBatchService.fileBatchTask("businessAct",
                       sellOrderDTO.getOrderId(), list);               
           } catch (Exception e) {
               this.logger.error(e.getMessage());
           }
           logger.debug("Activate BatchNo :" + batchNo);
           SellOrderDTO sellOrder = new SellOrderDTO();
           sellOrder.setOrderId(sellOrderDTO.getOrderId());
           if (null != batchNo && !"".equals(batchNo)) {
               sellOrder.setBatchNo(batchNo);
               // 更新订单状态
               updateOrderState(sellOrder);
           } else {
               sellOrder
                       .setInitActStat(DataBaseConstant.ORDER_ACT_STATE_INACT);
               // 回滚订单状态
               updateOrderState(sellOrder);
           }
       } catch (BizServiceException b) {
           throw b;
       } catch (Exception e) {
           this.logger.error(e.getMessage());
           throw new BizServiceException("激活失败");
       }
    }
	 /**
	  * 校验赎回订单是否已包含原售卡订单中所有的卡
	  */
	 public boolean checkAllCardNo(SellOrderCardListDTO dto){
	 	
	 	//赎回订单
	 	SellOrderCardListExample example = new SellOrderCardListExample();
	 	example.createCriteria().andDataStateEqualTo(
	 			DataBaseConstant.DATA_STATE_NORMAL).andOrderIdEqualTo(dto.getOrderId());
	 	List<SellOrderCardList> ransomOrderCardList = sellOrderCardListDAO.selectByExample(example);
	 	
	 	//售卡订单
	 	List<SellOrderCardList> sellOrderCardList = new ArrayList<SellOrderCardList>();
	 	String sellOrderId = null;
	 	for(SellOrderCardList ransomOrderCard : ransomOrderCardList){
	 		//根据卡号找所有订单
	 		SellOrderCardListExample example2 = new SellOrderCardListExample();
	 		example2.createCriteria().andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL)
	 								 .andCardNoEqualTo(ransomOrderCard.getCardNo());
	 		List<SellOrderCardList> OrderCardLists = sellOrderCardListDAO.selectByExample(example);
	 		
	 		//
	 		for(SellOrderCardList s : OrderCardLists){
	 			if(!dto.getOrderId().equals(s.getOrderId())){
	 				sellOrderId = s.getOrderId();
	 				break;
	 			}
	 		}
	 		
	 	}
	 	SellOrderCardListExample sellExample = new SellOrderCardListExample();
	 	sellExample.createCriteria().andDataStateEqualTo(
	 			DataBaseConstant.DATA_STATE_NORMAL).andOrderIdEqualTo(sellOrderId);
	 	List<SellOrderCardList> OrderCardLists = sellOrderCardListDAO.selectByExample(example);
	 	
	 	
	 	return false;
	 }
	 
	 
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public SellOrder getSellOrder() {
		return sellOrder;
	}

	public void setSellOrder(SellOrder sellOrder) {
		this.sellOrder = sellOrder;
	}

	public SellOrderPaymentDAO getSellOrderPaymentDAO() {
		return sellOrderPaymentDAO;
	}

	public void setSellOrderPaymentDAO(SellOrderPaymentDAO sellOrderPaymentDAO) {
		this.sellOrderPaymentDAO = sellOrderPaymentDAO;
	}

	public RansomOrderService getRansomOrderService() {
		return ransomOrderService;
	}

	public void setRansomOrderService(RansomOrderService ransomOrderService) {
		this.ransomOrderService = ransomOrderService;
	}

	public SellOrderCardListDAO getSellOrderCardListDAO() {
		return sellOrderCardListDAO;
	}

	public void setSellOrderCardListDAO(SellOrderCardListDAO sellOrderCardListDAO) {
		this.sellOrderCardListDAO = sellOrderCardListDAO;
	}
	

	// public BatchFileService getRechargeBatchFileService() {
	// return rechargeBatchFileService;
	// }
	//
	// public void setRechargeBatchFileService(
	// BatchFileService rechargeBatchFileService) {
	// this.rechargeBatchFileService = rechargeBatchFileService;
	// }
	//
	// public BatchFileService getRechargeActBatchFileService() {
	// return rechargeActBatchFileService;
	// }
	//
	// public void setRechargeActBatchFileService(
	// BatchFileService rechargeActBatchFileService) {
	// this.rechargeActBatchFileService = rechargeActBatchFileService;
	// }

}
