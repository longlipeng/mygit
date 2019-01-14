package com.huateng.univer.issuer.order.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.univer.issuer.dto.product.ProductDTO;
import com.allinfinance.univer.seller.order.dto.AcceptOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.huateng.framework.constant.Const;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.dao.BaseDAO;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.SellOrderCardListDAO;
import com.huateng.framework.ibatis.dao.SellOrderDAO;
import com.huateng.framework.ibatis.dao.SellOrderListDAO;
import com.huateng.framework.ibatis.dao.UnionOrderDAO;
import com.huateng.framework.ibatis.model.SellOrder;
import com.huateng.framework.ibatis.model.SellOrderCardList;
import com.huateng.framework.ibatis.model.SellOrderCardListExample;
import com.huateng.framework.ibatis.model.SellOrderFlow;
import com.huateng.framework.ibatis.model.SellOrderList;
import com.huateng.framework.ibatis.model.SellOrderListExample;
import com.huateng.framework.ibatis.model.UnionOrder;
import com.huateng.framework.ibatis.model.UnionOrderExample;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.StringUtil;
import com.huateng.univer.entitystock.service.EntityStockService;
import com.huateng.univer.issuer.order.biz.bo.StockOrderCommonService;
import com.huateng.univer.issuer.order.biz.service.StockOrderConfirmService;
import com.huateng.univer.issuer.product.service.ProductService;
import com.huateng.univer.order.business.bo.OrderBO;
import com.huateng.univer.order.business.bo.OrderBaseQueryBO;
import com.huateng.univer.order.business.service.OrderCardListService;

/**
 * 库存订单流程service 用于订单的提交、退回、取消等操作
 * 
 * @author xxl
 * 
 */
public class StockOrderConfirmServiceImpl implements StockOrderConfirmService {

	private Logger logger = Logger.getLogger(this.getClass());
	private CommonsDAO commonsDAO;
	private ProductService productService;
	private SellOrderDAO sellOrderDAO;
	private SellOrderCardListDAO sellOrderCardListDAO;
	private SellOrderListDAO sellOrderListDAO;
	private UnionOrderDAO unionOrderDAO;
	private BaseDAO baseDAO;
	private StockOrderCommonService stockOrderCommonService;
	private OrderCardListService orderCardListService;
	private EntityStockService entityStockService;
	private OrderBaseQueryBO orderBaseQueryBO;
	private OrderBO orderBO;

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

	/**
	 * 批量提交订单至待审核状态
	 */
	public void batchSubmit(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		try {
			String[] orderIds = sellOrderDTO.getOrderIds();
			List<SellOrder> sellOrders = new ArrayList<SellOrder>();
			List<SellOrderFlow> orderFlows = new ArrayList<SellOrderFlow>();
			for (String orderId : orderIds) {
				SellOrder sellOrder = new SellOrder();
				sellOrder.setOrderId(orderId);
				sellOrder.setOrderState(OrderConst.ORDER_STATE_UNCERTAIN);
				sellOrders.add(sellOrder);
				// 添加订单流程信息
				SellOrderFlow orderFlow = new SellOrderFlow();
				orderFlow.setOrderFlowId(commonsDAO
						.getNextValueOfSequence("TB_SELL_ORDER_FLOW"));
				orderFlow.setOrderId(sellOrder.getOrderId());
				orderFlow.setEntityId(sellOrderDTO.getDefaultEntityId());
				orderFlow.setOrderFlowNode(OrderConst.ORDER_FLOW_INPUT);
				orderFlow
						.setOperateType(OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION);
				orderFlow.setCreateTime(DateUtil.getCurrentTime());
				orderFlow.setModifyTime(DateUtil.getCurrentTime());
				orderFlow.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
				orderFlow.setCreateUser(sellOrderDTO.getLoginUserId());
				orderFlow.setModifyUser(sellOrderDTO.getLoginUserId());
				orderFlows.add(orderFlow);
			}
			commonsDAO
					.batchUpdate(
							"TB_SELL_ORDER.abatorgenerated_updateByPrimaryKeySelective",
							sellOrders);

			commonsDAO.batchInsert("TB_SELL_ORDER_FLOW.abatorgenerated_insert",
					orderFlows);

		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("提交制卡订单失败！");
		}
	}

	/**
	 * 批量取消订单的
	 */
	public void batchCancel(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		try {
			String[] orderIds = sellOrderDTO.getOrderIds();
			List<SellOrder> sellOrders = new ArrayList<SellOrder>();
			List<SellOrderFlow> orderFlows = new ArrayList<SellOrderFlow>();
			for (String orderId : orderIds) {
				SellOrder sellOrder = new SellOrder();
				sellOrder.setOrderId(orderId);
				sellOrder.setOrderState(OrderConst.ORDER_STATE_CANCEL);
				sellOrders.add(sellOrder);
				// 添加订单流程信息
				SellOrderFlow orderFlow = new SellOrderFlow();
				orderFlow.setOrderFlowId(commonsDAO
						.getNextValueOfSequence("TB_SELL_ORDER_FLOW"));
				orderFlow.setOrderId(sellOrder.getOrderId());
				orderFlow.setEntityId(sellOrderDTO.getDefaultEntityId());
				orderFlow.setOrderFlowNode(OrderConst.ORDER_FLOW_INPUT);
				orderFlow.setOperateType(OrderConst.ORDER_FLOW_OPRATION_CANCEL);
				orderFlow.setCreateTime(DateUtil.getCurrentTime());
				orderFlow.setModifyTime(DateUtil.getCurrentTime());
				orderFlow.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
				orderFlow.setCreateUser(sellOrderDTO.getLoginUserId());
				orderFlow.setModifyUser(sellOrderDTO.getLoginUserId());
				orderFlows.add(orderFlow);
			}
			commonsDAO
					.batchUpdate(
							"TB_SELL_ORDER.abatorgenerated_updateByPrimaryKeySelective",
							sellOrders);
			commonsDAO.batchInsert("TB_SELL_ORDER_FLOW.abatorgenerated_insert",
					orderFlows);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("取消制卡订单失败！");
		}
	}

	/**
	 * 根据订单状态获取流程节点
	 * 
	 * @param orderState
	 * @return
	 */
	private String getOrderFlowNode(String orderState) {
		// 草稿
		if (OrderConst.ORDER_STATE_DRAFT.equals(orderState)) {
			return OrderConst.ORDER_FLOW_INPUT;
		}
		// 待确定
		if (OrderConst.ORDER_STATE_UNCERTAIN.equals(orderState)
				|| OrderConst.ORDER_STATE_ORDER_CONFIRM_BY_HIGHER
						.equals(orderState)) {
			return OrderConst.ORDER_FLOW_CONFIRMATION;
		}
		// 制卡文件待下载
		if (OrderConst.ORDER_FLOW_MAKECARD_DOWN.equals(orderState)) {
			return OrderConst.ORDER_FLOW_MAKECARD_DOWN;
		}
		// 制卡文件待生成
		if (OrderConst.ORDER_STATE_CARDGFILE_MAKING.equals(orderState)) {
			return OrderConst.ORDER_FLOW_MAKECARD_DOWN;
		}
		// 接收
		if (OrderConst.ORDER_STATE_ORDER_ACCEPT.equals(orderState)) {
			return OrderConst.ORDER_FLOW_STOCK_ACCEPT;
		}
		// 分库接收
		if (OrderConst.ORDER_STATE_ORDER_BRANCH_ACCEPT.equals(orderState)) {
			return OrderConst.ORDER_FLOW_BRANCH_ACCEPT;
		}
		// 准备
		if (OrderConst.ORDER_STATE_ORDER_READY.equals(orderState)) {
			return OrderConst.ORDER_FLOW_READY;
		}
		// 配送
		if (OrderConst.ORDER_STATE_ORDER_SEND.equals(orderState)) {
			return OrderConst.ORDER_FLOW_SEND;
		}

		return null;
	}

	/**
	 * 提交订单
	 */
	public void confirm(SellOrderDTO sellOrderDTO) throws BizServiceException {
		try {
			String loginUserId = sellOrderDTO.getLoginUserId();
			String entityId = sellOrderDTO.getDefaultEntityId();
			SellOrder sellOrder = this.getSellOrder(sellOrderDTO.getOrderId());
			SellOrderList record=new SellOrderList();
			if(sellOrder.getOrderType().equals(OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN)){
			    SellOrderList sellOrderList=(SellOrderList)baseDAO.queryForList("STOCK_ORDER_LIST.selectOrderListByOrderId", sellOrderDTO).get(0);
	            record=(SellOrderList)sellOrderListDAO.selectByPrimaryKey(sellOrderList.getOrderListId());
			}
			String orderState = sellOrder.getOrderState();
			//针对采购订单，增加配送张数。
			String realCardQuantity=sellOrderDTO.getRealCardQuantity();
			// 订单流程信息
			SellOrderFlow orderFlow = new SellOrderFlow();
			orderFlow.setOrderFlowId(commonsDAO
					.getNextValueOfSequence("TB_SELL_ORDER_FLOW"));
			orderFlow.setOrderId(sellOrderDTO.getOrderId());
			orderFlow.setEntityId(sellOrderDTO.getDefaultEntityId());
			orderFlow.setOrderFlowNode(getOrderFlowNode(orderState));
			orderFlow
					.setOperateType(OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION);
			orderFlow.setMemo(sellOrderDTO.getOperationMemo());
			orderFlow.setCreateUser(sellOrderDTO.getLoginUserId());
			orderFlow.setModifyUser(sellOrderDTO.getLoginUserId());

			// 草稿-->待审核状态
			if (OrderConst.ORDER_STATE_DRAFT.equals(orderState)) {
				sellOrder = this.confirmDraft(sellOrder);
			} else if (OrderConst.ORDER_STATE_UNCERTAIN.equals(orderState)
					|| OrderConst.ORDER_STATE_ORDER_CONFIRM_BY_HIGHER
							.equals(orderState)) {
				// 待审核-->制卡文件待下载/准备状态
				sellOrder = this.confirmUnCertain(sellOrder, entityId,realCardQuantity,record);
			} else if (OrderConst.ORDER_STATE_CARDGFILE_MAKING
					.equals(orderState)) {
				// 制卡文件待生成-->接收状态
				sellOrder = this.confirmCardFileMaking(sellOrder);
			} else if (OrderConst.ORDER_STATE_ORDER_ACCEPT.equals(orderState)) {
				// 接收-->配送/入库
				sellOrder = this.confirmOrderAccept(sellOrder, entityId,
						loginUserId);
			} else if (OrderConst.ORDER_STATE_ORDER_READY.equals(orderState)) {
				// 准备-->配送/录入/向上一级采购(代发卡)
				sellOrder = this.confirmOrderReady(sellOrder, entityId);
			} else if (OrderConst.ORDER_STATE_ORDER_SEND.equals(orderState)) {
				// 配送-->下级机构订单接收
				sellOrder = this.confirmOrderBranchAccept(sellOrder, entityId,
						loginUserId);
			} else if (OrderConst.ORDER_STATE_ORDER_BRANCH_ACCEPT
					.equals(orderState)) {
				// 下级机构接收-->配送/入库
				sellOrder = this.confirmOrderAccept(sellOrder, entityId,
						loginUserId);
			}

			sellOrder.setModifyUser(sellOrderDTO.getLoginUserId());
			sellOrder.setModifyTime(DateUtil.getCurrentTime());
			record.setModifyUser(sellOrderDTO.getLoginUserId());
			record.setModifyTime(DateUtil.getCurrentTime());
			record.setRealAmount(realCardQuantity);
			sellOrderDAO.updateByPrimaryKeySelective(sellOrder);
			if(sellOrder.getOrderType().equals(OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN)){
			sellOrderListDAO.updateByPrimaryKeySelective(record);
			}
			// 添加订单流程信息
			stockOrderCommonService.addOrderFlow(orderFlow);

		} catch (BizServiceException be) {
			throw be;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("提交制卡订单失败！");
		}
	}

	/**
	 * 提交草稿状态订单
	 */
	private SellOrder confirmDraft(SellOrder sellOrder) throws Exception {
		sellOrder.setOrderState(OrderConst.ORDER_STATE_UNCERTAIN);
		return sellOrder;
	}

	/**
	 * 提交待审核状态的订单
	 */
	private SellOrder confirmUnCertain(SellOrder sellOrder, String entityId,String realCardQuantity,SellOrderList record)
			throws Exception {
		String orderType = sellOrder.getOrderType();
		if (OrderConst.ORDER_TYPE_ORDER_MAKE_CARD.equals(orderType)) {
			// 库存订单-->制卡文件待下载
			sellOrder.setOrderState(OrderConst.ORDER_STATE_CARDFILE_DOWNLOAD);
		} else if (OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_SIGN
				.equals(orderType)) {
			sellOrder = getOrderNextStateForBuyOrder(sellOrder, entityId);
		} else if (OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN
				.equals(orderType)) {
			// 非记名采购订单-->订单准备
			sellOrder.setOrderState(OrderConst.ORDER_STATE_ORDER_READY);
			//新增配送张数
			sellOrder.setRealCardQuantity(realCardQuantity);
			record.setRealAmount(realCardQuantity);
		} else if (OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_UNSIGN
				.equals(orderType)) {
			if (entityId.equals(sellOrder.getFirstEntityId())) {
				// 下级代发卡订单-->待审批
				sellOrder
						.setOrderState(OrderConst.ORDER_STATE_ORDER_CONFIRM_BY_HIGHER);
			} else if (entityId.equals(sellOrder.getProcessEntityId())) {
				// 非记名采购订单-->订单准备
				sellOrder.setOrderState(OrderConst.ORDER_STATE_ORDER_READY);
			}
		} else if (OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_SIGN
				.equals(orderType)) {
			if (entityId.equals(sellOrder.getFirstEntityId())) {
				// 下级代发卡订单-->待审批
				sellOrder
						.setOrderState(OrderConst.ORDER_STATE_ORDER_CONFIRM_BY_HIGHER);
			} else if (entityId.equals(sellOrder.getProcessEntityId())) {
				// 上级代发卡订单
				sellOrder = getOrderNextStateForBuyOrder(sellOrder, entityId);
			}
		}

		return sellOrder;
	}

	private SellOrder getOrderNextStateForBuyOrder(SellOrder sellOrder,
			String entityId) throws Exception {
		String productId = sellOrder.getProductId();
		ProductDTO productDTO = new ProductDTO();
		productDTO.setProductId(productId);
		productDTO = productService.viewProduct(productDTO);
		// if (entityId.equals(productDTO.getEntityId())
		// && entityId.equals(productDTO.getProductDefineIssuer())) {
		// Modify by Yifeng.Shi 2011-05-06
		if (entityId.equals(productDTO.getProductDefineIssuer())) {
			sellOrder.setOrderState(OrderConst.ORDER_STATE_CARDFILE_DOWNLOAD);
		} else {
			sellOrder.setOrderState(OrderConst.ORDER_STATE_ORDER_READY);		
		}
		return sellOrder;
	}

	/**
	 * 提交制卡文件生成完成的订单
	 */
	private SellOrder confirmCardFileMaking(SellOrder sellOrder)
			throws Exception {
		// 判断是否下载过制卡和卡PIN文件
		// if (StringUtil.isEmpty(sellOrder.getIsCreateCardFile())
		// || OrderConst.FLAG_NO.equals(sellOrder.getIsCreateCardFile())) {
		if (StringUtil.isEmpty(sellOrder.getIsCreateCardFile())) {
			throw new BizServiceException("订单： " + sellOrder.getOrderId()
					+ " 未生成制卡文件不能提交！");
		}
		/*
		 * if (StringUtil.isEmpty(sellOrder.getIsCreatePinFile()) ||
		 * OrderConst.FLAG_NO.equals(sellOrder.getIsCreatePinFile())) { throw
		 * new BizServiceException("订单： " + sellOrder.getOrderId() +
		 * " 未生成卡PIN文件不能提交！"); }
		 */
		sellOrder.setOrderState(OrderConst.ORDER_STATE_ORDER_ACCEPT);
		return sellOrder;

	}

	/**
	 * 提交接收状态的订单
	 */
	private SellOrder confirmOrderAccept(SellOrder sellOrder, String entityId,
			String loginUserId) throws Exception {

		// 如果是库存订单，则应是入库状态
		if (OrderConst.ORDER_TYPE_ORDER_MAKE_CARD.equals(sellOrder
				.getOrderType())) {
			checkStockCardList(sellOrder.getOrderId());
			sellOrder.setOrderState(OrderConst.ORDER_STATE_ORDER_STOCK);
		}
		// 如果是营销机构和发卡机构之间的记名采购订单，则应是配送状态
		if (OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_SIGN.equals(sellOrder
				.getOrderType())) {
			checkBuyCardList(sellOrder.getOrderId());
			sellOrder.setOrderState(OrderConst.ORDER_STATE_ORDER_SEND);
		}
		// 如果是发卡机构和发卡机构之间的记名采购订单，则需根据发起方和处理方来选择是入库或者配送
		if (OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_SIGN.equals(sellOrder
				.getOrderType())) {
			checkBuyCardList(sellOrder.getOrderId());
			// 发起方为本机构，则应是入库
			if (sellOrder.getFirstEntityId().equals(entityId)) {
				sellOrder.setOrderState(OrderConst.ORDER_STATE_ORDER_STOCK);

			}
			// 处理方为本机构，则应是配送
			if (sellOrder.getProcessEntityId().equals(entityId)) {
				sellOrder.setOrderState(OrderConst.ORDER_STATE_ORDER_SEND);
			}
			this.loyaltyOrderSubmitAtAccept(sellOrder, entityId, loginUserId);
			return sellOrder;
		}
		// 发卡机构和发卡机构的匿名采购订单，必定为采购方提交，则必定为更改库存状态，而非将卡入库
		if (OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_UNSIGN
				.equals(sellOrder.getOrderType())) {
			sellOrder.setOrderState(OrderConst.ORDER_STATE_ORDER_STOCK);

			this.loyaltyOrderSubmitAtAccept(sellOrder, entityId, loginUserId);
			return sellOrder;
		}

		// 入库
		// 发卡机构和发卡机构，营销机构和发卡机构，记名采购订单，配送退回后，至接收，接收提交则入库。违反唯一约束。
		this.addEntityStore(sellOrder, entityId, loginUserId);

		return sellOrder;
	}

	/**
	 * 代发卡分库入库
	 */

	private void loyaltyOrderSubmitAtAccept(SellOrder sellOrder,
			String entityId, String operateUser) throws BizServiceException,
			Exception {
		String orderId = sellOrder.getOrderId();
		String productId = sellOrder.getProductId();
		ProductDTO productDTO = new ProductDTO();
		productDTO.setProductId(productId);
		productDTO = productService.viewProduct(productDTO);
		List<String> cardNos = orderCardListService
				.getSuccessCardNoList(orderId);
		// 如果产品定义者为当前发卡机构，则应该添加库存记录，而非更新
		if (productDTO.getProductDefineIssuer().equals(entityId)) {
			addEntityStore(sellOrder, entityId, operateUser);
		} else {
			// 如果产品定义者非当前发卡机构，则应该更改库存记录，而非入库
/*			if (sellOrder.getOrderType().equals(
					OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_UNSIGN)) {
				entityStockService.modifyStockStateAndEntity(cardNos, entityId,
						OrderConst.CARD_STOCK_OUT, OrderConst.CARD_STOCK_IN);
			} else {*/
				entityStockService.modifyStockStateAndEntity(cardNos, entityId,
						OrderConst.CARD_STOCK_OUT, OrderConst.CARD_STOCK_IN);
			//}
			// 库存操作日志：入库
			entityStockService.addStockOperaterRecord(cardNos, orderId,
					entityId, Const.FUNCTION_ROLE_SELLER,
					OrderConst.ORDER_STATE_ORDER_ACCEPT,
					OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION,
					OrderConst.CARD_STOCK_OPERATE_TYPE_IN, operateUser);
		}

	}

	/**
	 * 验证库存订单卡状态
	 */
	private void checkStockCardList(String orderId) throws BizServiceException {
		List<String> stateList = new ArrayList<String>();
		stateList.add(OrderConst.MAKE_CARD_STATE_SUCCESS);
		stateList.add(OrderConst.MAKE_CARD_STATE_FAILTURE);
		SellOrderCardListExample example = new SellOrderCardListExample();
		example.createCriteria().andDataStateEqualTo(
				DataBaseConstant.DATA_STATE_NORMAL).andOrderIdEqualTo(orderId)
				.andCardStateNotIn(stateList);

		if (sellOrderCardListDAO.countByExample(example) > 0) {
			throw new BizServiceException("订单：" + orderId + " 中有制卡未完成的卡,不能提交！");
		}
	}

	/**
	 * 验证采购订单卡状态
	 */
	private void checkBuyCardList(String orderId) throws Exception {
		List<SellOrderCardList> orderCardLists = orderCardListService
				.getOrderCardList(orderId);
		for (SellOrderCardList orderCardList : orderCardLists) {
			String cardSate = orderCardList.getCardState();
			if (OrderConst.MAKE_CARD_STATE_FAILTURE.equals(cardSate)) {
				throw new BizServiceException("订单中卡："
						+ orderCardList.getCardNo() + " 制卡失败，不能提交！");
			}
			if (!OrderConst.MAKE_CARD_STATE_SUCCESS.equals(cardSate)) {
				throw new BizServiceException("订单中卡："
						+ orderCardList.getCardNo() + " 制卡未完成，不能提交！");
			}
		}
	}

	/**
	 * 库存订单卡片入库
	 */
	private void addEntityStore(SellOrder sellOrder, String entityId,
			String loginUserId) throws Exception {

		logger.debug("开始入库。。。。。。。。");
		long time = System.currentTimeMillis();
		String orderId = sellOrder.getOrderId();

		List<String> cardNos = orderCardListService
				.getSuccessCardNoList(orderId);
		if (null == cardNos || cardNos.size() < 1) {
			throw new BizServiceException("订单：" + orderId + " 中没有制卡完成的卡,不能入库！");
		}

		// 添加库存记录
		entityStockService.addStockRecord(cardNos, sellOrder, entityId,
				loginUserId);

		// 添加库存操作日志信息
		entityStockService.addStockOperaterRecord(cardNos, orderId, entityId,
				Const.FUNCTION_ROLE_ISSUER,
				OrderConst.ORDER_STATE_ORDER_ACCEPT,
				OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION,
				OrderConst.CARD_STOCK_OPERATE_TYPE_IN, loginUserId);

		logger
				.debug("入库耗时：" + (System.currentTimeMillis() - time) / 1000
						+ "秒");

	}

	/**
	 * 提交准备状态的订单
	 */
	private SellOrder confirmOrderReady(SellOrder sellOrder, String entityId)
			throws Exception {
		if (sellOrder.getOrderType().equals(
				OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_SIGN)
				|| sellOrder.getOrderType().equals(
						OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_SIGN)) {
			// 发卡机构记名订单判断。
			boolean flag = true;
			String rootOrderIdString = "";
			while (flag) {
				UnionOrderExample unionOrderExample = new UnionOrderExample();
				unionOrderExample.createCriteria().andNewOrderEqualTo(
						sellOrder.getOrderId());
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
			 * 这里需要传递对象，包含根订单Id和当前机构Id。获取根订单的卡片列表 暂定用UnionOrder对象代替。
			 */
			UnionOrder order = new UnionOrder();
			order.setNewOrder(entityId);
			order.setOldOrder(rootOrderIdString);
			String cardQuantity = orderBaseQueryBO
					.selectSellAndSellSignCardQuantity(order);
			if ("0".equals(cardQuantity)) {
				throw new BizServiceException("订单的卡未配送到本机构,提交订单失败!");
			}

		}
		//本次优化准备卡的数量以配送的张数为准，所以这里需要检验配送数量与实际卡明细的数量。
		if (sellOrder.getOrderType().equals(
				OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_UNSIGN)
				|| sellOrder.getOrderType().equals(
						OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN)) {
			this.checkSrockReady(sellOrder.getOrderId());
		}

		sellOrder.setOrderState(OrderConst.ORDER_STATE_ORDER_SEND);
		return sellOrder;
	}

	/**
	 * 查看订单下所有订单明细是否准备完成
	 */
	private void checkSrockReady(String orderId) throws BizServiceException {
		SellOrderListExample example = new SellOrderListExample();
		example.createCriteria().andDataStateEqualTo(
				DataBaseConstant.DATA_STATE_NORMAL).andOrderIdEqualTo(orderId);
		List<SellOrderList> sellOrderLists = stockOrderCommonService
				.getOrderListByExample(example);
		for (SellOrderList orderList : sellOrderLists) {
//			Integer cardAmt = new Integer(orderList.getCardAmount());
			Integer realAmt = new Integer(orderList.getRealAmount());
			SellOrderCardListExample ex=new SellOrderCardListExample();
	        ex.createCriteria().andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL).andOrderIdEqualTo(orderId).andOrderListIdEqualTo(orderList.getOrderListId());
			Integer count=sellOrderCardListDAO.countByExample(ex);
			if (count.intValue() != realAmt.intValue()) {
				throw new BizServiceException("订单：" + orderId
						+ " 中有准备未完成的明细，不能提交!");
			}
		}
	}

	/**
	 * 查看记名采购订单下所有订单明细是否准备完成
	 */
	private void checkStockReady(String orderId, String cardQuantity)
			throws BizServiceException {
		List<String> cardNoList = orderCardListService
				.getSuccessCardNoList(orderId);
		if (null != cardNoList && cardNoList.size() != 0
				&& cardNoList.size() == Integer.parseInt(cardQuantity)) {

		} else {
			throw new BizServiceException("订单：" + orderId + " 中有未完成的卡明细，不能提交!");
		}
	}

	/**
	 * 提交配送状态的订单
	 */
	private SellOrder confirmOrderBranchAccept(SellOrder sellOrder,
			String entityId, String loginUserId) throws Exception {
		String orderId = sellOrder.getOrderId();
		List<String> cardNos = orderCardListService
				.getSuccessCardNoList(orderId);
		// 出库准备中 --> 出库
		if (OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN
				.equals(sellOrder.getOrderType())
				|| OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_UNSIGN
						.equals(sellOrder.getOrderType())) {
			entityStockService.modifyStockState(cardNos,
					OrderConst.CARD_STOCK_READY_OUT, OrderConst.CARD_STOCK_OUT);
		}
		// 入库-->出库
		if (OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_SIGN.equals(sellOrder
				.getOrderType())
				|| OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_SIGN
						.equals(sellOrder.getOrderType())) {
			entityStockService.modifyStockState(cardNos,
					OrderConst.CARD_STOCK_IN, OrderConst.CARD_STOCK_OUT);
		}
		// 判断订单卡是否出库过。存在疑问，部分卡出库，部分卡未出库。
		// EntityStockOperaterExample entityStockOperaterExample = new
		// EntityStockOperaterExample();
		// entityStockOperaterExample.createCriteria().andCardNoIn(cardNos)
		// .andEntityIdEqualTo(entityId).andDataStateEqualTo(
		// DataBaseConstant.DATA_STATE_NORMAL)
		// .andStockStateEqualTo(OrderConst.CARD_STOCK_OUT);
		// 记录库存操作日志
		entityStockService.addStockOperaterRecord(cardNos, orderId, entityId,
				Const.FUNCTION_ROLE_ISSUER, OrderConst.ORDER_STATE_ORDER_SEND,
				OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION,
				OrderConst.CARD_STOCK_OPERATE_TYPE_OUT, loginUserId);

		sellOrder.setOrderState(OrderConst.ORDER_STATE_ORDER_BRANCH_ACCEPT);
		return sellOrder;
	}

	/**
	 * 订单退回到上一节点
	 */
	public void reject(SellOrderDTO sellOrderDTO) throws BizServiceException {
		try {
			SellOrder sellOrder = this.getSellOrder(sellOrderDTO.getOrderId());
			String orderState = sellOrder.getOrderState();
			// 订单流程信息
			SellOrderFlow orderFlow = new SellOrderFlow();
			orderFlow.setOrderFlowId(commonsDAO
					.getNextValueOfSequence("TB_SELL_ORDER_FLOW"));
			orderFlow.setOrderId(sellOrderDTO.getOrderId());
			orderFlow.setEntityId(sellOrderDTO.getDefaultEntityId());
			orderFlow.setOrderFlowNode(getOrderFlowNode(orderState));
			orderFlow.setOperateType(OrderConst.ORDER_FLOW_OPRATION_BACK);
			orderFlow.setCreateUser(sellOrderDTO.getLoginUserId());
			orderFlow.setModifyUser(sellOrderDTO.getLoginUserId());
			orderFlow.setMemo(sellOrderDTO.getOperationMemo());
			// 待审核状态-->草稿状态/下一级采购订单草稿状态
			if (OrderConst.ORDER_STATE_UNCERTAIN.equals(orderState)
					|| OrderConst.ORDER_STATE_ORDER_CONFIRM_BY_HIGHER
							.equals(orderState)) {
				sellOrder = rejectUnCertain(sellOrder, sellOrderDTO
						.getDefaultEntityId());
			} else if (OrderConst.ORDER_STATE_CARDFILE_DOWNLOAD
					.equals(orderState)) {
				// 制卡文件待生成-->待审核状态
				sellOrder = rejectMakeCardDown(sellOrder);
			} else if (OrderConst.ORDER_STATE_CARDGFILE_MAKING.equals(orderState)){
				throw new BizServiceException("制卡文件已生成不能退回该订单");
			} else if (OrderConst.ORDER_STATE_ORDER_ACCEPT.equals(orderState)) {
				// 接收 -->制卡文件待下载
				sellOrder = rejectOrderAccept(sellOrder);
			} else if (OrderConst.ORDER_STATE_ORDER_READY.equals(orderState)) {
				// 准备 --> 待审批
				sellOrder = rejectOrderReady(sellOrder, sellOrderDTO
						.getDefaultEntityId(), sellOrderDTO.getLoginUserId());
			} else if (OrderConst.ORDER_STATE_ORDER_SEND.equals(orderState)) {
				// 配送 --> 准备
				sellOrder = rejectOrderSend(sellOrder, sellOrderDTO
						.getDefaultEntityId());
			}
			sellOrder.setModifyUser(sellOrderDTO.getLoginUserId());
			sellOrder.setModifyTime(DateUtil.getCurrentTime());
			sellOrderDAO.updateByPrimaryKeySelective(sellOrder);
			// 添加订单流程信息
			stockOrderCommonService.addOrderFlow(orderFlow);
		} catch (BizServiceException be) {
			throw be;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("退回制卡订单失败！");
		}
	}

	/**
	 * 退回待审核状态的订单
	 */
	private SellOrder rejectUnCertain(SellOrder sellOrder,
			String currentEntityId) throws Exception {
		// 采购订单退回
		if (OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_SIGN.equals(sellOrder
				.getOrderType())
				|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN
						.equals(sellOrder.getOrderType())) {
			// 待审核
			sellOrder.setOrderState(OrderConst.ORDER_STATE_UNCERTAIN);
		}
		if (OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_SIGN.equals(sellOrder
				.getOrderType())
				|| OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_UNSIGN
						.equals(sellOrder.getOrderType())) {
			if (sellOrder.getProcessEntityId().equals(currentEntityId)) {
				sellOrder.setOrderState(OrderConst.ORDER_STATE_UNCERTAIN);
			} else if (sellOrder.getFirstEntityId().equals(currentEntityId)) {
				sellOrder.setOrderState(OrderConst.ORDER_STATE_DRAFT);
			}
		}

		// 库存订单退回-->草稿
		if (OrderConst.ORDER_TYPE_ORDER_MAKE_CARD.equals(sellOrder
				.getOrderType())) {
			sellOrder.setOrderState(OrderConst.ORDER_STATE_DRAFT);
		}
		return sellOrder;
	}

	/**
	 * 退回制卡文件待下载的订单
	 */
	private SellOrder rejectMakeCardDown(SellOrder sellOrder) throws Exception {
		if (sellOrder.getOrderType().equals(
				OrderConst.ORDER_TYPE_ORDER_MAKE_CARD)) {
			sellOrder.setOrderState(OrderConst.ORDER_STATE_UNCERTAIN);
		} else {
			sellOrder
					.setOrderState(OrderConst.ORDER_STATE_ORDER_CONFIRM_BY_HIGHER);
		}
		return sellOrder;
	}

	/**
	 * 退回接收状态的订单
	 */
	private SellOrder rejectOrderAccept(SellOrder sellOrder) throws Exception {
		String orderId = sellOrder.getOrderId();

		if (orderCardListService.countFailCard(orderId) < 1) {
			throw new BizServiceException("订单：" + orderId + " 中所有卡状态都正常,不能退回！");
		}

		// 有制卡失败的卡 退回到制卡文件下载，只生成制卡失败的卡
		sellOrder.setIsCreateCardFile(OrderConst.FLAG_NO);
		sellOrder.setIsCreatePinFile(OrderConst.FLAG_NO);
		sellOrder.setIsImportCardFile(OrderConst.FLAG_NO);
		sellOrder.setOrderState(OrderConst.ORDER_STATE_CARDFILE_DOWNLOAD);
		return sellOrder;
	}

	public void rejectOrderAcceptToFileMake(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		try {
			SellOrder sellOrder = new SellOrder();
			sellOrder.setOrderId(sellOrderDTO.getOrderId());
			String orderId = sellOrderDTO.getOrderId();
			SellOrder targetSellOrder = stockOrderCommonService
					.getSellOrderById(orderId);
			/**
			 * 
			 * 订单的处理方非本机构，则为下级发行机构退回至上级的订单配送，同时，依据订单类型，修改关联的卡。
			 * */
			if (!targetSellOrder.getProcessEntityId().equals(
					sellOrderDTO.getDefaultEntityId())) {
				sellOrder.setOrderState(OrderConst.ORDER_STATE_ORDER_SEND);
				// 记录流程，修改订单卡所属机构和状态。
				rejectOrderAtAcceptToSend(targetSellOrder, sellOrderDTO
						.getLoginUserId(), sellOrderDTO.getDefaultEntityId());
			} else {
				sellOrder
						.setOrderState(OrderConst.ORDER_STATE_CARDGFILE_MAKING);
			}

			// sellOrder.setIsCreateCardFile(OrderConst.FLAG_NO);
			// sellOrder.setIsCreatePinFile(OrderConst.FLAG_NO);
			sellOrderDAO.updateByPrimaryKeySelective(sellOrder);
			// 记录订单流程信息
			stockOrderCommonService.addNewOrderFlow(sellOrderDTO,
					OrderConst.ORDER_FLOW_STOCK_ACCEPT,
					OrderConst.ORDER_FLOW_OPRATION_BACK);
		} catch (BizServiceException ex) {
			throw ex;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("退回订单失败！");
		}
	}

	private void rejectOrderAtAcceptToSend(SellOrder sellOrder, String userId,
			String entityId) throws BizServiceException {
		try {
			List<String> cardNos = orderCardListService
					.getSuccessCardNoList(sellOrder.getOrderId());
			if (sellOrder.getOrderType().equals(
					OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_SIGN)) {
				entityStockService.modifyStockState(cardNos,
						OrderConst.CARD_STOCK_OUT, OrderConst.CARD_STOCK_IN);
				// 记录库存操作日志
				entityStockService.addStockOperaterRecord(cardNos, sellOrder
						.getOrderId(), sellOrder.getProcessEntityId(),
						Const.FUNCTION_ROLE_ISSUER,
						OrderConst.ORDER_STATE_ORDER_ACCEPT,
						OrderConst.ORDER_FLOW_OPRATION_BACK,
						OrderConst.CARD_STOCK_IN, userId);
			} else if (sellOrder.getOrderType().equals(
					OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_UNSIGN)) {
				entityStockService.modifyStockState(cardNos,
						OrderConst.CARD_STOCK_OUT,
						OrderConst.CARD_STOCK_READY_OUT);
				// 记录库存操作日志
				entityStockService.addStockOperaterRecord(cardNos, sellOrder
						.getOrderId(), sellOrder.getProcessEntityId(),
						Const.FUNCTION_ROLE_ISSUER,
						OrderConst.ORDER_STATE_ORDER_ACCEPT,
						OrderConst.ORDER_FLOW_OPRATION_BACK,
						OrderConst.CARD_STOCK_READY_OUT, userId);
			}

		} catch (Exception e) {
			throw new BizServiceException("退回制卡文件失败!");
		}

	}

	/**
	 * 退回准备状态的订单
	 */
	private SellOrder rejectOrderReady(SellOrder sellOrder,
			String currentEntityId, String loginUserId) throws Exception {
		try {
			String orderId = sellOrder.getOrderId();
			// 删除添加的订单卡明细
			if (sellOrder.getOrderType().equals(
					OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_SIGN)
					|| sellOrder.getOrderType().equals(
							OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_SIGN)
					|| sellOrder.getOrderType().equals(
							OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_SIGN)) {
				String isGenerate = orderBaseQueryBO
						.checkSellOrderSignGenerateBuyerOrder(sellOrder
								.getOrderId());
				if (!isGenerate.equals("0")) {
					throw new BizServiceException("该记名订单已经生成采购订单,暂时不能退回!");
				}
			}
			
			List<String> cardNos = orderCardListService
					.getSuccessCardNoList(orderId);
			
			orderCardListService.deleteCardListByOrderId(orderId);
			SellOrderList sellOrderList = orderBaseQueryBO
					.getSellOrderListByOrderId(sellOrder.getOrderId()).get(0);
			sellOrderList.setRealAmount("0");
			orderBO.updateSellOrderList(sellOrderList);
			// 被准备的库存重新入库
			entityStockService.modifyStockState(cardNos,
					OrderConst.CARD_STOCK_READY_OUT, OrderConst.CARD_STOCK_IN);
			// 记录库存操作
			entityStockService.addStockOperaterRecord(cardNos, sellOrder
					.getOrderId(), currentEntityId, Const.FUNCTION_ROLE_ISSUER,
					OrderConst.ORDER_STATE_ORDER_READY,
					OrderConst.ORDER_FLOW_OPRATION_BACK,
					OrderConst.CARD_STOCK_OPERATE_TYPE_IN, loginUserId);
			// 订单退到待审批状态
			sellOrder.setOrderState(OrderConst.ORDER_STATE_ORDER_CONFIRM_BY_HIGHER);
			return sellOrder;
		} catch (BizServiceException b){
			logger.error(b);
			throw b;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			this.logger.error(e.getMessage());
			throw new BizServiceException("订单准备退回失败");
		}
	}

	/**
	 * 退回配送状态的订单
	 */
	private SellOrder rejectOrderSend(SellOrder sellOrder,
			String currentEntityId) throws Exception {
		String productId = sellOrder.getProductId();
		ProductDTO productDTO = new ProductDTO();
		productDTO.setProductId(productId);
		productDTO = productService.viewProduct(productDTO);
		if (sellOrder.getOrderType().equals(
				OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_UNSIGN)
				|| sellOrder.getOrderType().equals(
						OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN)) {
			sellOrder.setOrderState(OrderConst.ORDER_STATE_ORDER_READY);
		} else if (sellOrder.getOrderType().equals(
				OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_SIGN)) {
			// 营销机构和发卡机构的记名采购订单
			// 若订单中产品的定义方为本机构，则应退回订单接收。
			if (productDTO.getProductDefineIssuer().equals(currentEntityId)) {
				sellOrder.setOrderState(OrderConst.ORDER_STATE_ORDER_ACCEPT);
			} else if (!productDTO.getProductDefineIssuer().equals(
					currentEntityId)) {
				sellOrder.setOrderState(OrderConst.ORDER_STATE_ORDER_READY);
			}
		} else if (sellOrder.getOrderType().equals(
				OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_SIGN)) {
			if (productDTO.getProductDefineIssuer().equals(currentEntityId)) {
				sellOrder.setOrderState(OrderConst.ORDER_STATE_ORDER_ACCEPT);
			} else {
				sellOrder.setOrderState(OrderConst.ORDER_STATE_ORDER_READY);
			}
		}
		return sellOrder;
	}

	/**
	 * 取消订单
	 */
	public void cancel(SellOrderDTO sellOrderDTO) throws BizServiceException {
		try {
			SellOrder sellOrder = new SellOrder();
			sellOrder.setOrderId(sellOrderDTO.getOrderId());
			sellOrder.setOrderState(OrderConst.ORDER_STATE_CANCEL);
			sellOrder.setModifyUser(sellOrderDTO.getLoginUserId());
			sellOrder.setModifyTime(DateUtil.getCurrentTime());

			sellOrderDAO.updateByPrimaryKeySelective(sellOrder);
			// 添加订单流程信息
			SellOrderFlow orderFlow = new SellOrderFlow();
			orderFlow.setOrderFlowId(commonsDAO
					.getNextValueOfSequence("TB_SELL_ORDER_FLOW"));
			orderFlow.setOrderId(sellOrder.getOrderId());
			orderFlow.setEntityId(sellOrderDTO.getDefaultEntityId());
			orderFlow.setOrderFlowNode(OrderConst.ORDER_FLOW_MAKECARD_MAKE);
			orderFlow.setOperateType(OrderConst.ORDER_FLOW_OPRATION_CANCEL);
			orderFlow.setCreateTime(DateUtil.getCurrentTime());
			orderFlow.setModifyTime(DateUtil.getCurrentTime());
			orderFlow.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			orderFlow.setCreateUser(sellOrderDTO.getLoginUserId());
			orderFlow.setModifyUser(sellOrderDTO.getLoginUserId());
			stockOrderCommonService.addOrderFlow(orderFlow);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("取消制卡订单失败！");
		}
	}

	/* (non-Javadoc)
     * @see com.huateng.univer.issuer.order.biz.service.StockOrderConfirmService#acceptOrder()
     */
    @Override
    public void submitAcceptOrder(AcceptOrderDTO acceptOrderDTO) throws Exception {
       
        orderBaseQueryBO.accept(acceptOrderDTO);
    }

    /* (non-Javadoc)
     * @see com.huateng.univer.issuer.order.biz.service.StockOrderConfirmService#delete()
     */
    @Override
    public void delete(AcceptOrderDTO acceptOrderDTO) throws Exception{
        
        orderBaseQueryBO.delete(acceptOrderDTO);
    }

    /* (non-Javadoc)
     * @see com.huateng.univer.issuer.order.biz.service.StockOrderConfirmService#deleteAll()
     */
    @Override
    public void deleteAll(AcceptOrderDTO acceptOrderDTO) throws Exception{
        
        orderBaseQueryBO.deleteAll(acceptOrderDTO);
    }

    private SellOrder getSellOrder(String sellOrderId) throws Exception {
        return sellOrderDAO.selectByPrimaryKey(sellOrderId);
        }

    public CommonsDAO getCommonsDAO() {
        return commonsDAO;
        }

    public void setCommonsDAO(CommonsDAO commonsDAO) {
        this.commonsDAO = commonsDAO;
	    }

    public ProductService getProductService() {
	    return productService;
	    }

    public void setProductService(ProductService productService) {
	    this.productService = productService;
	    }

	public SellOrderDAO getSellOrderDAO() {
		return sellOrderDAO;
	}

	public void setSellOrderDAO(SellOrderDAO sellOrderDAO) {
		this.sellOrderDAO = sellOrderDAO;
	}

	public SellOrderCardListDAO getSellOrderCardListDAO() {
		return sellOrderCardListDAO;
	}

	public void setSellOrderCardListDAO(
			SellOrderCardListDAO sellOrderCardListDAO) {
		this.sellOrderCardListDAO = sellOrderCardListDAO;
	}

	public UnionOrderDAO getUnionOrderDAO() {
		return unionOrderDAO;
	}

	public void setUnionOrderDAO(UnionOrderDAO unionOrderDAO) {
		this.unionOrderDAO = unionOrderDAO;
	}

	public SellOrderListDAO getSellOrderListDAO() {
		return sellOrderListDAO;
	}

	public void setSellOrderListDAO(SellOrderListDAO sellOrderListDAO) {
		this.sellOrderListDAO = sellOrderListDAO;
	}

	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public StockOrderCommonService getStockOrderCommonService() {
		return stockOrderCommonService;
	}

	public void setStockOrderCommonService(
			StockOrderCommonService stockOrderCommonService) {
		this.stockOrderCommonService = stockOrderCommonService;
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

}
