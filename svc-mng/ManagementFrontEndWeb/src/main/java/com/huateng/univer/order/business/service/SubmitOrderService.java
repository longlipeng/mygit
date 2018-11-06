package com.huateng.univer.order.business.service;

import java.util.List;

import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderInputDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderListDTO;
import com.huateng.framework.exception.BizServiceException;

public interface SubmitOrderService {

	public void submitOrderAtInput(SellOrderInputDTO sellOrderInputDTO)
			throws BizServiceException;

	public void submitOrderAtConfirm(SellOrderDTO sellOrderDTO)
			throws BizServiceException;

	public void submitOrderAtReady(SellOrderDTO sellOrderDTO)
			throws BizServiceException;

	public void submitOrderAtHandOut(SellOrderDTO sellOrderDTO)
			throws BizServiceException;

	public void submitOrderSendConfirm(SellOrderDTO sellOrderDTO)
	throws BizServiceException ;
	
	public void submitOrderAtSendConfirm(SellOrderDTO sellOrderDTO)
			throws BizServiceException;

	public void submitOrderAtAccept(SellOrderDTO sellOrderDTO)
			throws BizServiceException;

	public void submitOrderAtOrderImmdiatelyCredit(SellOrderDTO sellOrderDTO)
			throws BizServiceException;

	public void submitOrderImmdiatelyCredit(SellOrderDTO sellOrderDTO)
	throws BizServiceException; 
	
	public void submitOrderAtOrderPayment(SellOrderDTO sellOrderDTO)
			throws BizServiceException;

	public void sendBackOrderAtAccept(SellOrderDTO sellOrderDTO)
			throws BizServiceException;

	public void activateConfirm(SellOrderDTO sellOrderDTO)
			throws BizServiceException;

	public void confirmAtOrderPayment(SellOrderDTO sellOrderDTO)
			throws BizServiceException;
	public List<SellOrderListDTO> ready(SellOrderDTO sellOrderDTO)
	throws Exception ;
	/**
	 * 
	 * 跳转到合并付款页面
	 * 
	 * @param sellOrderDTO
	 * @return
	 * @throws BizServiceException
	 */
	public SellOrderDTO combineAtOrderPayment(SellOrderDTO sellOrderDTO)
			throws Exception;
	
	/**
	 * 修改订单状态为处理中
	 * 
	 * @param sellOrderDTO
	 * @return
	 * @throws BizServiceException
	 */
	public void submitOrder(SellOrderDTO sellOrderDTO)
			throws BizServiceException ;
	
	// 获取订单信息及更新订单状态为处理中
	public void submitOrderAtCredit(SellOrderDTO sellOrderDTO)
			throws BizServiceException;
	
	public void sumbitForActive(SellOrderDTO sellOrderDTO) throws BizServiceException;
	public void updateOrderActState(SellOrderDTO sellOrderDTO)
	throws BizServiceException ;
	//判断订单是否需要激活
	public boolean submitOrderForPayment(SellOrderDTO sellOrderDTO)
	throws BizServiceException ;
	/**
	 * 
	 * 功能描述: <br>
	 * 订单自动激活
	 *
	 * @param sellOrderDTO
	 * @throws BizServiceException
	 */
	public void orderAutoActive(SellOrderDTO sellOrderDTO) throws BizServiceException;
}
