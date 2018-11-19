/**
 * Classname OrderSendConfirmActionImpl.java
 *
 * Version information
 *
 * Licensed Property to HuaTeng Data Co., Ltd.
 * 
 * (C) Copyright of HuaTeng Data Co., Ltd. 2010
 *     All Rights Reserved.
 *
 * Project Info: HuaTeng Data Internet Acquiring  Project
 * 
 * Modification History:
 * =============================================================================
 *		Author		Date		Description
 *   ------------ ---------- ---------------------------------------------------
 *		wanglu		2013-1-14
 * =============================================================================
 */

package com.huateng.univer.order.business.action;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.constant.DictInfoConstants;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.SellOrderDAO;
import com.huateng.framework.ibatis.dao.SellOrderPaymentDAO;
import com.huateng.framework.ibatis.model.SellOrder;
import com.huateng.framework.ibatis.model.SellOrderPayment;
import com.huateng.framework.ibatis.model.SellOrderPaymentExample;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.univer.order.business.bo.OrderBO;
import com.huateng.univer.order.business.bo.OrderBaseQueryBO;
import com.huateng.univer.order.business.bo.OrderCountTotalPrice;
import com.huateng.univer.order.business.service.SubmitOrderService;

/**
 * @author wanglu
 *
 */
public class OrderSendConfirmActionImpl implements
		OrderSendConfimActionInterface {
	private OrderBaseQueryBO orderBaseQueryBO; 
	public OrderBaseQueryBO getOrderBaseQueryBO() {
		return orderBaseQueryBO;
	}
	public void setOrderBaseQueryBO(OrderBaseQueryBO orderBaseQueryBO) {
		this.orderBaseQueryBO = orderBaseQueryBO;
	}
	private static Logger logger = Logger.getLogger(OrderMakeCardActionImpl.class);
	private SubmitOrderService submitOrderService;
	private OrderPaymentActionImpl orderPaymentActionImpl;
	public SellOrder getSellOrder() {
		return sellOrder;
	}
	public void setSellOrder(SellOrder sellOrder) {
		this.sellOrder = sellOrder;
	}
	private SellOrder sellOrder;
	public OrderBO getOrderBO() {
		return orderBO;
	}
	public void setOrderBO(OrderBO orderBO) {
		this.orderBO = orderBO;
	}
	private OrderBO orderBO;
	public OrderPaymentActionImpl getOrderPaymentActionImpl() {
		return orderPaymentActionImpl;
	}
	public void setOrderPaymentActionImpl(
			OrderPaymentActionImpl orderPaymentActionImpl) {
		this.orderPaymentActionImpl = orderPaymentActionImpl;
	}
	public CommonsDAO getCommonsDAO() {
		return commonsDAO;
	}
	public void setCommonsDAO(CommonsDAO commonsDAO) {
		this.commonsDAO = commonsDAO;
	}
	public OrderCountTotalPrice getOrderCountTotalPrice() {
		return orderCountTotalPrice;
	}
	public void setOrderCountTotalPrice(OrderCountTotalPrice orderCountTotalPrice) {
		this.orderCountTotalPrice = orderCountTotalPrice;
	}
	public SellOrderPaymentDAO getSellOrderPaymentDAO() {
		return sellOrderPaymentDAO;
	}
	public void setSellOrderPaymentDAO(SellOrderPaymentDAO sellOrderPaymentDAO) {
		this.sellOrderPaymentDAO = sellOrderPaymentDAO;
	}
	public SellOrderDAO getSellOrderDAO() {
		return sellOrderDAO;
	}
	public void setSellOrderDAO(SellOrderDAO sellOrderDAO) {
		this.sellOrderDAO = sellOrderDAO;
	}
	private CommonsDAO commonsDAO;
	private OrderCountTotalPrice orderCountTotalPrice;
	private SellOrderPaymentDAO sellOrderPaymentDAO;
	private SellOrderDAO sellOrderDAO;


	/* (non-Javadoc)
	 * @see com.huateng.univer.order.business.action.OrderSendConfimActionInterface#submitOrderAtSendConfirm(com.huateng.univer.seller.order.dto.SellOrderDTO)
	 */
	@Override
	public void submitOrderAtSendConfirm(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		
		if(null!=sellOrderDTO.getOrderType()&&!"".equals(sellOrderDTO.getOrderType())
				&& (OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_UNSIGN.equalsIgnoreCase(sellOrderDTO.getOrderType())
				|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_UNSIGN
								.equalsIgnoreCase(sellOrderDTO.getOrderType()))) {
			try {
				//不记名卡记录流程
				orderBO.orderFlowNexNode(sellOrderDTO.getOrderId(),
						OrderConst.ORDER_STATE_ORDER_SEND_CONFIRM, sellOrderDTO
								.getLoginUserId(), sellOrderDTO
								.getDefaultEntityId(),
						OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION, sellOrderDTO
								.getOperationMemo(),
						OrderConst.ORDER_FLOW_SEND_CONFIRM);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new BizServiceException("订单配送确定失败");
			}
		}
		else{
			
		
			logger.debug("开始充值激活");
			// 获取订单信息及更新订单状态为处理中
			float s= Float.parseFloat((sellOrderDTO.getTotalPrice()));
			float payment=Float.parseFloat((sellOrderDTO.getPaymentState()));
			//如果支付状态不是已支付，就执行付款确认、付款审核
			if(payment!=1){
				if(s==0)
				{
					    sellOrderDTO.setPaymentAmount("0");
						SellOrderPayment tempOrderPayment = new SellOrderPayment();
						  tempOrderPayment.setPaymentType("1");
						  tempOrderPayment.setDataState(DataBaseConstant.DATA_STATE_NORMAL); 
				        tempOrderPayment.setCreateTime(DateUtil.getCurrentTime());
				        tempOrderPayment.setOrderId(sellOrderDTO.getOrderId());
				        tempOrderPayment.setCreateUser(sellOrderDTO.getLoginUserId());
				        BigDecimal amount = new BigDecimal(sellOrderDTO.getPaymentAmount());
				        BigDecimal tempValue = new BigDecimal("100");
				        SellOrder order = sellOrderDAO.selectByPrimaryKey(sellOrderDTO.getOrderId());
				        String orderType = order.getOrderType();
				        String tempValueStr = amount.multiply(tempValue).toBigInteger().toString();
				        if (OrderConst.ORDER_TYPE_ORDER_RANSOM.equals(orderType)) {
				            tempValueStr = "-" + tempValueStr;
				        }
				        tempOrderPayment.setPaymentAmount(tempValueStr);
				        tempOrderPayment.setModifyTime(tempOrderPayment.getCreateTime());
				        tempOrderPayment.setModifyUser(sellOrderDTO.getLoginUserId());
				        tempOrderPayment.setPaymentId(commonsDAO.getNextValueOfSequence("TB_SELL_ORDER_PAYMENT"));
				        if(payment!=2){
				        sellOrderPaymentDAO.insert(tempOrderPayment);
				        orderCountTotalPrice.countAmountForPayment(sellOrderDTO.getOrderId());
				       /* if(payment==0)
						{
							    orderBaseQueryBO.update(sellOrderDTO);
							   
						}*/
				        //付款确认
				        sellOrderDTO.setPaymentState(DictInfoConstants.PAY_STATE_CONFIRM);
						sellOrderDTO.setModifyTime(DateUtil.getCurrentTime());
						sellOrderDTO.setModifyUser(sellOrderDTO.getLoginUserId());
						sellOrder = new SellOrder();
						ReflectionUtil.copyProperties(sellOrderDTO, sellOrder);
						sellOrder.setOrderState(null);
						sellOrder.setCreateUser(sellOrder.getModifyUser());
						
						sellOrderDAO.updateByPrimaryKeySelective(sellOrder);
		
						orderBO.orderFlow(sellOrderDTO.getOrderId(), sellOrderDTO
								.getDefaultEntityId(),
								OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION,
								OrderConst.ORDER_FLOW_ORDER_PAYMENT, sellOrderDTO.getMemo(),
								sellOrderDTO.getModifyUser());
						
						}
						
						//付款审核
				        try {
							logger.debug("开始付款审核");
							//判断订单是否需要激活
							boolean isAct = submitOrderService.submitOrderForPayment(sellOrderDTO);
							if(isAct){
								submitOrderService.updateOrderActState(sellOrderDTO);
								submitOrderService.sumbitForActive(sellOrderDTO);
								//更新订单支付状态
								updatePaymentState(sellOrderDTO);
								
								//更新订单支付信息
								updateOrderPayment(sellOrderDTO);
							}else{
								//更新订单状态
								updateOrderState(sellOrderDTO);		
							}
							// 记录订单流程
							logger.debug("==>before write orderFlow!");
							orderBO.orderFlow(sellOrderDTO.getOrderId(), sellOrderDTO.getDefaultEntityId(),
									OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION,
									OrderConst.ORDER_FLOW_ORDER_SUBMIT, sellOrderDTO
											.getOperationMemo(), sellOrderDTO.getLoginUserId());
						} catch (BizServiceException b){
							throw b;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							this.logger.error(e.getMessage());
							throw new BizServiceException("订单付款审核失败");
						}
				}
			
			}
	        submitOrderService.submitOrder(sellOrderDTO);
			submitOrderService.submitOrderSendConfirm(sellOrderDTO);
		}
	}
	public SubmitOrderService getSubmitOrderService() {
		return submitOrderService;
	}
	public void setSubmitOrderService(SubmitOrderService submitOrderService) {
		this.submitOrderService = submitOrderService;
	}
	//更新订单状态
	private void updateOrderState(SellOrderDTO sellOrderDTO){
		SellOrder sellOrder = new SellOrder();
		sellOrder.setOrderId(sellOrderDTO.getOrderId());
		sellOrder.setModifyTime(DateUtil.getCurrentTime());
		// 订单修改人
		sellOrder.setModifyUser(sellOrderDTO.getLoginUserId());
		/* 订单支付信息修改 */
		sellOrder.setPayChannel(sellOrderDTO.getPayChannel());
		sellOrder.setPayDetails(sellOrderDTO.getPayDetails());
		/* 订单银行信息修改 */
		sellOrder.setIntoBankId(sellOrderDTO.getIntoBankId());
		sellOrder.setPaymentState(DictInfoConstants.PAY_STATE_PAID);
		sellOrderDAO.updateByPrimaryKeySelective(sellOrder);
	}
	
	private void updatePaymentState(SellOrderDTO sellOrderDTO){
		SellOrder sellOrder = new SellOrder();
		sellOrder.setOrderId(sellOrderDTO.getOrderId());
		sellOrder.setPaymentState(DictInfoConstants.PAY_STATE_PAID);
		sellOrderDAO.updateByPrimaryKeySelective(sellOrder);
	}
	
	/**
	 * 
	 * 更新订单支付信息表 <br>
	 * 在付款审核后，修改订单付款审核日期
	 *
	 * @param sellOrderDTO
	 * @throws BizServiceException
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	private void updateOrderPayment(SellOrderDTO sellOrderDTO) throws BizServiceException {
        try {
            SellOrderPaymentExample example = new SellOrderPaymentExample();
            example.createCriteria().andOrderIdEqualTo(sellOrderDTO.getOrderId());
            SellOrderPayment tempPayment = new SellOrderPayment();
            tempPayment.setModifyTime(DateUtil.getCurrentTime());
            tempPayment.setModifyUser(sellOrderDTO.getLoginUserId());
            sellOrderPaymentDAO.updateByExampleSelective(tempPayment,
                    example);      
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new BizServiceException("更新付款审核时间失败");
        }
    }
	
	
}
