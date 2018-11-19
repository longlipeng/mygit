package com.allinfinance.prepay.service;

import com.allinfinance.prepay.exception.BizServiceException;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderInputDTO;

public interface SubmitOrderService {
	public void submitOrderAtInput(SellOrderInputDTO sellOrderInputDTO)
			throws BizServiceException;
	public void submitOrderAtConfirm(SellOrderDTO sellOrderDTO)
			throws BizServiceException;
	
	public void submitOrderAtReady(SellOrderDTO sellOrderDTO)
			throws BizServiceException;
	
	public void submitOrderAtHandOut(SellOrderDTO sellOrderDTO)
			throws BizServiceException;
	
	
	public void submitOrderAtSendConfirm(SellOrderDTO sellOrderDTO)
			throws BizServiceException;
	
	public void confirmAtOrderPayment(SellOrderDTO sellOrderDTO)
			throws BizServiceException;
	
	public void submitOrderForPayment(SellOrderDTO sellOrderDTO)
			throws BizServiceException ;
	
	public void insertActive(SellOrderDTO sellOrderDTO) throws BizServiceException;
	
	public void submitOrderAtSendConfirmByRecharge(SellOrderDTO sellOrderDTO)
			throws BizServiceException;

}
