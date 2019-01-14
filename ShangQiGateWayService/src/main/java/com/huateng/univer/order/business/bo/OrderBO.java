package com.huateng.univer.order.business.bo;

import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.univer.seller.order.dto.AcceptOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderCardListDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.huateng.framework.constant.Const;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.dao.BaseDAO;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.SellOrderCardListDAO;
import com.huateng.framework.ibatis.dao.SellOrderDAO;
import com.huateng.framework.ibatis.dao.SellOrderFlowDAO;
import com.huateng.framework.ibatis.dao.SellOrderListDAO;
import com.huateng.framework.ibatis.model.EntityStock;
import com.huateng.framework.ibatis.model.SellOrder;
import com.huateng.framework.ibatis.model.SellOrderCardList;
import com.huateng.framework.ibatis.model.SellOrderCardListExample;
import com.huateng.framework.ibatis.model.SellOrderExample;
import com.huateng.framework.ibatis.model.SellOrderFlow;
import com.huateng.framework.ibatis.model.SellOrderList;
import com.huateng.framework.ibatis.model.SellOrderListExample;
import com.huateng.framework.ibatis.model.UnionOrder;
import com.huateng.framework.util.DateUtil;
import com.huateng.univer.entitystock.service.EntityStockService;

public class OrderBO {
    private Logger logger = Logger.getLogger(OrderBO.class);
	/**
	 * 更新订单状 同时记录流程节点
	 */

	private CommonsDAO commonsDAO;

	private SellOrderDAO sellOrderDAO;

	private SellOrderFlowDAO sellOrderFlowDAO;

	private SellOrderCardListDAO sellOrderCardListDAO;

	private SellOrderListDAO sellOrderListDAO;

	private BaseDAO baseOrderDAO;
	private BaseDAO baseDAO;
	private OrderBaseQueryBO orderBaseQueryBO;
	private EntityStockService entityStockService;

	/**
	 * 
	 * @param orderId
	 *            订单ID
	 * @param nextState
	 *            订单下一状态
	 * @param modifyUser
	 *            修改者
	 * @param defaultEntityId
	 *            修改者实体ID
	 * @param operatorType
	 *            操作类型:提交，退回，取消
	 * @param memo
	 *            操作说明
	 * @param currentNode
	 *            订单当前节点
	 * @throws Exception
	 */
	public void orderFlowNexNode(String orderId, String nextState,
			String modifyUser, String defaultEntityId, String operatorType,
			String memo, String currentNode) throws Exception {

		SellOrder sellOrder = new SellOrder();
		sellOrder.setOrderId(orderId);
		// 订单下一状态
		sellOrder.setOrderState(nextState);

		sellOrder.setModifyTime(DateUtil.getCurrentTime());
		// 订单修改人
		sellOrder.setModifyUser(modifyUser);

		SellOrderFlow sellOrderFlow = new SellOrderFlow();
		/**
		 * 因订单流程记录ID没有实际意义 故取sequenceName
		 */
		sellOrderFlow.setOrderFlowId(commonsDAO
				.getNextValueOfSequence("TB_SELL_ORDER_FLOW"));

		sellOrderFlow.setOrderId(orderId);
		// 订单流程实体ID
		sellOrderFlow.setEntityId(defaultEntityId);
		// 订单操作类型
		sellOrderFlow.setOperateType(operatorType);

		sellOrderFlow.setOrderFlowNode(currentNode);

		// 操作说明
		sellOrderFlow.setMemo(memo);

		sellOrderFlow.setCreateTime(DateUtil.getCurrentTime());
		sellOrderFlow.setCreateUser(modifyUser);

		sellOrderFlow.setModifyTime(DateUtil.getCurrentTime());

		sellOrderFlow.setModifyUser(modifyUser);

		sellOrderFlow.setDataState(DataBaseConstant.DATA_STATE_NORMAL);

		sellOrderDAO.updateByPrimaryKeySelective(sellOrder);

		sellOrderFlowDAO.insert(sellOrderFlow);
	}

	public void updateEntityStockByPrimaryKey(List<EntityStock> entityStockList)
			throws Exception {
		commonsDAO.batchUpdate(
				"TB_ENTITY_STOCK.abatorgenerated_updateByPrimaryKeySelective",
				entityStockList);
	}

	public void deleteSellOrderCardListByOrderId(String orderId)
			throws Exception {

		SellOrderCardListExample example = new SellOrderCardListExample();

		example.createCriteria().andOrderIdEqualTo(orderId);

		sellOrderCardListDAO.deleteByExample(example);
	}

	public void batchInsertSellOrderCardList(
			List<SellOrderCardList> sellOrderCardListList) throws Exception {
		commonsDAO.batchInsert(
				"TB_SELL_ORDER_CARD_LIST.abatorgenerated_insert",
				sellOrderCardListList);
	}

	public void batchUpdateSellOrderCardList(
			List<SellOrderCardList> sellOrderCardListList) throws Exception {
		commonsDAO
				.batchInsert(
						"TB_SELL_ORDER_CARD_LIST.abatorgenerated_updateByPrimaryKeySelective",
						sellOrderCardListList);
	}

	public void updateSellOrderList(SellOrderList sellOrderList)
			throws Exception {
		sellOrderListDAO.updateByPrimaryKeySelective(sellOrderList);
	}

	public void deleteSellOrderCardListByPrimaryKey(String orderCardListId)
			throws Exception {
		sellOrderCardListDAO.deleteByPrimaryKey(orderCardListId);
	}

	public void updateSellOrderListByExample(SellOrderList sellOrderList,
			SellOrderListExample sellOrderListExample) throws Exception {
		sellOrderListDAO.updateByExampleSelective(sellOrderList,
				sellOrderListExample);
	}

	public SellOrderCardList getSellOrderCardListByPrimaryKey(
			String orderCardListId) throws Exception {
		return sellOrderCardListDAO.selectByPrimaryKey(orderCardListId);
	}

	public void updateByPrimaryKeySelective(SellOrder sellOrder)
			throws Exception {
		sellOrderDAO.updateByPrimaryKeySelective(sellOrder);
	}

	@SuppressWarnings("unchecked")
    public void updateEntityStockOutByOrderId(String orderId) throws Exception {
		SellOrder order = orderBaseQueryBO.getSellOrder(orderId);
        SellOrderDTO sellOrderDTO = new SellOrderDTO();
        sellOrderDTO.setOrderId(orderId);
        List<String> oldOrderIdOrderList = (List<String>)baseDAO.queryForList("STOCK_ORDER_CARD_LIST.selectOrigOrderByBuyOrderId", orderId);
        if(oldOrderIdOrderList.size() == 0){
            sellOrderDTO.setOrderIds(null);
        }
        else{
            sellOrderDTO.setOrderIds((String[])oldOrderIdOrderList.toArray(new String[oldOrderIdOrderList.size()])); 
        }
		if (OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_SIGN.equals(order
				.getOrderType())) {
			baseOrderDAO.update("ORDER.updateCardOutBySignOrderId", sellOrderDTO);
			
		} else if (OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_UNSIGN
				.equals(order.getOrderType())
				|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN
						.equals(order.getOrderType())
				|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_UNSIGN
						.equals(order.getOrderType())
				|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN
						.equals(order.getOrderType())
				|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_UNSIGN
						.equals(order.getOrderType())) {
			baseOrderDAO.update("ORDER.updateCardOutByOrderId", orderId);
		}
	}

	/***
	 * 递归(查找订单)更新库存 需要递归的订单有 采名卡采购订单
	 * 
	 * @param orderId
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
    public void updateEntityStockOutByRecursionOrderCardList(String orderId)
			throws Exception {
	    SellOrderDTO sellOrderDTO = new SellOrderDTO();
        sellOrderDTO.setOrderId(orderId);
        List<String> oldOrderIdOrderList = (List<String>)baseDAO.queryForList("STOCK_ORDER_CARD_LIST.selectOrigOrderByBuyOrderId", orderId);
        if(oldOrderIdOrderList.size() == 0){
            sellOrderDTO.setOrderIds(null);
        }
        else{
            sellOrderDTO.setOrderIds((String[])oldOrderIdOrderList.toArray(new String[oldOrderIdOrderList.size()])); 
        }
		baseOrderDAO.update("ORDER.updateCardOutBySignOrderId", sellOrderDTO);
	}

	/**
	 * 通过订单明细更新库存
	 * 
	 * @param orderId
	 * @throws Exception
	 */
	public void updateEntityStockOutByBuyOrderCardList(String orderId)
			throws Exception {
		baseOrderDAO.update("ORDER.updateCardOutByOrderId", orderId);

	}

	/**
	 * 采购订单流程中更新原始订单的采购状态(orderBuyerState)
	 */
	public void updateOldOrderStateForBuyOrder(List<String> oldOrderIdList,
			String orderBuyerState) throws Exception {
		SellOrderExample example = new SellOrderExample();
		example.createCriteria().andDataStateEqualTo(
				DataBaseConstant.DATA_STATE_NORMAL)
				.andOrderIdIn(oldOrderIdList);

	}

	/**
	 * 订单接收入库
	 * 
	 * @param orderId
	 * @throws Exception
	 */
	public void orderAcceptInStock(SellOrderDTO sellOrderDTO) throws Exception {
		String orderId = sellOrderDTO.getOrderId();
		// 分库入库
		List<String> cardNos = orderBaseQueryBO
      .getSuccessCardNoList(sellOrderDTO.getOrderId());
		entityStockService.modifyStockStateAndEntity(cardNos, sellOrderDTO
				.getDefaultEntityId(), OrderConst.CARD_STOCK_OUT,
				OrderConst.CARD_STOCK_IN);

		// 库存操作日志：入库
		entityStockService.addStockOperaterRecord(cardNos, orderId,
				sellOrderDTO.getDefaultEntityId(), Const.FUNCTION_ROLE_SELLER,
				OrderConst.ORDER_STATE_ORDER_ACCEPT,
				OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION,
				OrderConst.CARD_STOCK_OPERATE_TYPE_IN, sellOrderDTO
						.getLoginUserId());

	}
	/**
     * 根据卡号段订单接收入库
     * 
     * @param orderId
	 * @throws Exception 
     * @throws Exception
     */
    public void orderAcceptInStock(AcceptOrderDTO acceptOrderDTO) throws Exception {
        String orderId = acceptOrderDTO.getOrderId();
        //由于本次是可以部分接收，以下逻辑需要修改
//      List<String> cardNos = orderBaseQueryBO
//              .getSuccessCardNoList(sellOrderDTO.getOrderId());
        //根据卡号段进行入库
        // 分库入库
        List<String> cardNos = orderBaseQueryBO
      .getSuccessCardNoList(acceptOrderDTO.getOrderId(),acceptOrderDTO);
        if(cardNos==null){
            throw new BizServiceException("参数传递错误！");
        }
        //更新接收数量
//        SellOrderExample example=new SellOrderExample();
//        example.createCriteria().andOrderIdEqualTo(acceptOrderDTO.getOrderId());
//        SellOrder sellOrder=sellOrderDAO.selectByExample(example);
        SellOrder sellOrder=sellOrderDAO.selectByPrimaryKey(acceptOrderDTO.getOrderId());
        if(sellOrder.getOrigcardQuantity()!=null&&!"".equals(sellOrder.getOrigcardQuantity())){
            sellOrder.setOrigcardQuantity(String.valueOf(Integer.valueOf(sellOrder.getOrigcardQuantity())+cardNos.size()));
        }else{
        sellOrder.setOrigcardQuantity(String.valueOf(cardNos.size()));
        }
        try{
        sellOrderDAO.updateByPrimaryKey(sellOrder);
        }catch(Exception e){
            logger.info(e.getMessage());
        }
        entityStockService.modifyStockStateAndEntity(cardNos, acceptOrderDTO
                .getDefaultEntityId(), OrderConst.CARD_STOCK_OUT,
                OrderConst.CARD_STOCK_IN);

        // 库存操作日志：入库
        entityStockService.addStockOperaterRecord(cardNos, orderId,
                acceptOrderDTO.getDefaultEntityId(), Const.FUNCTION_ROLE_SELLER,
                OrderConst.ORDER_STATE_ORDER_ACCEPT,
                OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION,
                OrderConst.CARD_STOCK_OPERATE_TYPE_IN, acceptOrderDTO
                        .getLoginUserId());

    }
    /**
     * 得到已接手的数量
     * */
    public Integer getAcceptNum(SellOrderDTO sellOrderDTO){
        return orderBaseQueryBO.checkNum(sellOrderDTO);
        
    }
	public void orderPayment(String orderId, String initActStat, String bankId,
			String payChannel, String payDetails, String modifyUser,
			String defaultEntityId, String operatorType, String memo,
			String currentNode) throws Exception {
		SellOrder sellOrder = new SellOrder();
		sellOrder.setOrderId(orderId);

		// 订单设置状态为已支付
//		sellOrder.setPaymentState(DictInfoConstants.PAY_STATE_PAID);
		sellOrder.setInitActStat(initActStat);
		/* 订单支付信息修改 */
		sellOrder.setPayChannel(payChannel);
		sellOrder.setPayDetails(payDetails);

		/* 订单银行信息修改 */
		sellOrder.setIntoBankId(bankId);

		// sellOrder.setPaymentDate(DateUtil.getCurrentDateStr());
		sellOrder.setModifyTime(DateUtil.getCurrentTime());

		// 订单修改人
		sellOrder.setModifyUser(modifyUser);

		SellOrderFlow sellOrderFlow = new SellOrderFlow();
		/**
		 * 因订单流程记录ID没有实际意义 故取sequenceName
		 */
		sellOrderFlow.setOrderFlowId(commonsDAO
				.getNextValueOfSequence("TB_SELL_ORDER_FLOW"));

		sellOrderFlow.setOrderId(orderId);
		// 订单流程实体ID
		sellOrderFlow.setEntityId(defaultEntityId);
		// 订单操作类型
		sellOrderFlow.setOperateType(operatorType);

		sellOrderFlow.setOrderFlowNode(currentNode);

		// 操作说明
		sellOrderFlow.setMemo(memo);

		sellOrderFlow.setCreateTime(DateUtil.getCurrentTime());
		sellOrderFlow.setCreateUser(modifyUser);

		sellOrderFlow.setModifyTime(DateUtil.getCurrentTime());

		sellOrderFlow.setModifyUser(modifyUser);

		sellOrderFlow.setDataState(DataBaseConstant.DATA_STATE_NORMAL);

		sellOrderDAO.updateByPrimaryKeySelective(sellOrder);

		sellOrderFlowDAO.insert(sellOrderFlow);
	}

	public void orderFlow(String orderId, String defaultEntityId,
			String operatorType, String currentNode, String memo,
			String modifyUser) throws BizServiceException {
		SellOrderFlow sellOrderFlow = new SellOrderFlow();
		/**
		 * 因订单流程记录ID没有实际意义 故取sequenceName
		 */
		sellOrderFlow.setOrderFlowId(commonsDAO
				.getNextValueOfSequence("TB_SELL_ORDER_FLOW"));

		sellOrderFlow.setOrderId(orderId);
		// 订单流程实体ID
		sellOrderFlow.setEntityId(defaultEntityId);
		// 订单操作类型
		sellOrderFlow.setOperateType(operatorType);

		sellOrderFlow.setOrderFlowNode(currentNode);

		// 操作说明
		sellOrderFlow.setMemo(memo);

		sellOrderFlow.setCreateTime(DateUtil.getCurrentTime());
		sellOrderFlow.setCreateUser(modifyUser);

		sellOrderFlow.setModifyTime(DateUtil.getCurrentTime());

		sellOrderFlow.setModifyUser(modifyUser);

		sellOrderFlow.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
		sellOrderFlowDAO.insert(sellOrderFlow);
	}

	public void insertCreditOrderCardList(
			SellOrderCardListDTO sellOrderCardListDTO) throws Exception {
		baseOrderDAO.insert("ORDER.insertCreditOrderCardList",
				sellOrderCardListDTO);
	}
	public SellOrderDTO selectForUpdate(String orderId){
		SellOrderDTO dto =(SellOrderDTO)baseOrderDAO.queryForObject("selectForUpdate", orderId);
		return dto;
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

	public SellOrderFlowDAO getSellOrderFlowDAO() {
		return sellOrderFlowDAO;
	}

	public void setSellOrderFlowDAO(SellOrderFlowDAO sellOrderFlowDAO) {
		this.sellOrderFlowDAO = sellOrderFlowDAO;
	}

	public SellOrderCardListDAO getSellOrderCardListDAO() {
		return sellOrderCardListDAO;
	}

	public void setSellOrderCardListDAO(
			SellOrderCardListDAO sellOrderCardListDAO) {
		this.sellOrderCardListDAO = sellOrderCardListDAO;
	}

	public SellOrderListDAO getSellOrderListDAO() {
		return sellOrderListDAO;
	}

	public void setSellOrderListDAO(SellOrderListDAO sellOrderListDAO) {
		this.sellOrderListDAO = sellOrderListDAO;
	}

	public BaseDAO getBaseOrderDAO() {
		return baseOrderDAO;
	}

	public void setBaseOrderDAO(BaseDAO baseOrderDAO) {
		this.baseOrderDAO = baseOrderDAO;
	}

	public OrderBaseQueryBO getOrderBaseQueryBO() {
		return orderBaseQueryBO;
	}

	public void setOrderBaseQueryBO(OrderBaseQueryBO orderBaseQueryBO) {
		this.orderBaseQueryBO = orderBaseQueryBO;
	}

	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public EntityStockService getEntityStockService() {
		return entityStockService;
	}

	public void setEntityStockService(EntityStockService entityStockService) {
		this.entityStockService = entityStockService;
	}

}
