package com.huateng.univer.order.business.action;

import org.apache.log4j.Logger;

import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.huateng.framework.constant.DictInfoConstants;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.SellOrderDAO;
import com.huateng.framework.ibatis.dao.SellOrderPaymentDAO;
import com.huateng.framework.ibatis.model.SellOrder;
import com.huateng.framework.ibatis.model.SellOrderPayment;
import com.huateng.framework.ibatis.model.SellOrderPaymentExample;
import com.huateng.framework.util.DateUtil;
import com.huateng.univer.order.business.bo.OrderBO;
import com.huateng.univer.order.business.service.SubmitOrderService;

public class OrderPaymentActionImpl implements OrderPaymentActionInterface {
	private static Logger logger = Logger.getLogger(OrderMakeCardActionImpl.class);
	private SubmitOrderService submitOrderService;
	/**
	 * 订单录入节点提交
	 * 
	 */
	private OrderBO orderBO;
	private SellOrderDAO sellOrderDAO;
	
	/**
	 * 订单信息支付表DAO
	 * 
	 */
	private SellOrderPaymentDAO sellOrderPaymentDAO;

	@Override
	public void submitOrderForPayment(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
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
	
	public SubmitOrderService getSubmitOrderService() {
		return submitOrderService;
	}

	public void setSubmitOrderService(SubmitOrderService submitOrderService) {
		this.submitOrderService = submitOrderService;
	}

	public OrderBO getOrderBO() {
		return orderBO;
	}

	public void setOrderBO(OrderBO orderBO) {
		this.orderBO = orderBO;
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
