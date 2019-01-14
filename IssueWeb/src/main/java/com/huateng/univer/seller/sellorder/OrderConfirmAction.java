package com.huateng.univer.seller.sellorder;

import org.apache.log4j.Logger;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.huateng.framework.constant.OrderConst;

/**
 * 
 * @author dawn
 *
 */
public class OrderConfirmAction extends OrderBaseAction{

	/**
	 * 订单确认
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(OrderConfirmAction.class);
	/**
	 * 
	 */
	private String message;
	private String buyOrderFlag;
	private String exFlag;
	
	
	public String getExFlag() {
        return exFlag;
    }

    public void setExFlag(String exFlag) {
        this.exFlag = exFlag;
    }

    @Override
	protected void addCondition() {
	
		
	}

	@Override
	protected void dealWithSellOrderInputDTO() {
	
		
	}

	@Override
	public String edit() {
		
		return null;
	}

	@Override
	protected void init() {	
		actionName= "orderConfirmAction";
		actionMethodName="button";
	}
	@Override
	protected void initOrderType() {	
	}
	@Override
	public String list(){
		try{
			 ListPageInit("sellOrder", sellOrderQueryDTO);
			 sellOrderQueryDTO.setOrderState(OrderConst.ORDER_STATE_UNCERTAIN);
			 /***
			  * 按订单ID的倒序排序
			  */
			 if(isEmpty(sellOrderQueryDTO.getSortFieldName())){
				 sellOrderQueryDTO.setSort("desc");
				 sellOrderQueryDTO.setSortFieldName("orderId");
			 }
			 PageDataDTO result = (PageDataDTO) sendService(ConstCode.SELL_ORDER_QUERY_AT_SELL_CONFIRM,
					  sellOrderQueryDTO).getDetailvo();
			 sellOrders = result.getData();
			 sellOrder_totalRows = result.getTotalRecord();
		}catch(Exception e){
			this.logger.error(e.getMessage());
		}
		return "list";
	}
	
	public String listBuyOrder(){
		try{
			 ListPageInit("sellOrder", sellOrderQueryDTO);
			 sellOrderQueryDTO.setOrderState(OrderConst.ORDER_STATE_UNCERTAIN);
			 /***
			  * 按订单ID的倒序排序
			  */
			 if(isEmpty(sellOrderQueryDTO.getSortFieldName())){
				 sellOrderQueryDTO.setSort("desc");
				 sellOrderQueryDTO.setSortFieldName("orderId");
			 }
			 PageDataDTO result = (PageDataDTO) sendService(ConstCode.SELL_ORDER_QUERY_AT_BUY_CONFIRM,
					  sellOrderQueryDTO).getDetailvo();
			 sellOrders = result.getData();
			 sellOrder_totalRows = result.getTotalRecord();
		}catch(Exception e){
			this.logger.error(e.getMessage());
		}
		return "listBuyOrder";
	}
	
	public String activateList(){
		
		try{
			
			 ListPageInit("sellOrder", sellOrderQueryDTO);
			 /***
			  * 按订单ID的倒序排序
			  */
			 if(isEmpty(sellOrderQueryDTO.getSortFieldName())){
				 sellOrderQueryDTO.setSort("desc");
				 sellOrderQueryDTO.setSortFieldName("orderId");
				 sellOrderQueryDTO.setValidFlag("valid");
			 }
			 sellOrderQueryDTO.setPaymentState("1");
			 sellOrderQueryDTO.setOrderState("32");
			 PageDataDTO result = (PageDataDTO) sendService(ConstCode.ORDER_INQUERY_ACTIVATE,
					  sellOrderQueryDTO).getDetailvo();
			 sellOrders = result.getData();
			 sellOrder_totalRows = result.getTotalRecord();
			 
		}catch(Exception e){
			this.logger.error(e.getMessage());
		}
		
//		try{
//			 ListPageInit("sellOrder", sellOrderQueryDTO);
//			 /***
//			  * 按订单ID的倒序排序
//			  */
//			 if(isEmpty(sellOrderQueryDTO.getSortFieldName())){
//				 sellOrderQueryDTO.setSort("desc");
//				 sellOrderQueryDTO.setSortFieldName("orderId");
//				 sellOrderQueryDTO.setValidFlag("valid");
//			 }
//			 PageDataDTO result = (PageDataDTO) sendService(ConstCode.ORDER_INQUERY_BY_SELLER,
//					  sellOrderQueryDTO).getDetailvo();
//			 sellOrders = result.getData();
//			 sellOrder_totalRows = result.getTotalRecord();
//		}catch(Exception e){
//			this.logger.error(e.getMessage());
//		}
		return "activateList";
	}
	
	public String button(){
		try{
			view();
		}catch(Exception e){
			this.logger.error(e.getMessage());
		}
		if("1".equals(buyOrderFlag)){
			return "buyButton";
		}else if("2".equals(buyOrderFlag)){
		    if("1".equals(exFlag)){
		        return "activate";
		    }else{
		        return "activateEx";
		    }
		}else{
			return "button";
		}
	}
	public String orderSubmit(){
		message="提交";
		operation="submit";
		return button();
	}
	
	public String orderSendBack(){
		message="退回";
		operation="sendBack";
		return button();
	}
	
	public String orderCancel(){
		message="取消";
		operation="cancel";
		return button();
	}
	
	public String submitConfirm(){
		message="提交";
		operation="submit";
		return button();
	}
	
	public String sendBackConfirm(){
		message="退回";
		operation="sendBack";
		return button();
	}
	
	public String cancelConfirm(){
		message="取消";
		operation="cancel";
		return button();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	public String submit(){
		try{
			sendService(ConstCode.SELL_ORDER_SUBMIT_AT_CONFIRM,
					  sellOrderDTO).getDetailvo();
			
			if(!this.hasErrors()){
				this.addActionMessage("提交订单成功!");
			}
		}catch(Exception e){
			this.logger.error(e.getMessage());
		}
		if("1".equals(buyOrderFlag)){
			return listBuyOrder();
		}else {
			return list();
		}
	}
	
	
	public String sendBack(){
		try{
			sendService(ConstCode.SELL_ORDER_SENDBACK_AT_CONFIRM,
					  sellOrderDTO).getDetailvo();
			
			if(!this.hasErrors()){
				this.addActionMessage("退回订单成功!");
			}
		}catch(Exception e){
			this.logger.error(e.getMessage());
		}
		if("1".equals(buyOrderFlag)){
			return listBuyOrder();
		}else {
			return list();
		}
	}
	
	
	public String cancel(){
		try{
			sendService(ConstCode.SELL_ORDER_CANCEL_AT_CONFIRM,
					  sellOrderDTO).getDetailvo();
			
			if(!this.hasErrors()){
				this.addActionMessage("取消订单成功!");
			}
		}catch(Exception e){
			this.logger.error(e.getMessage());
		}
		if("1".equals(buyOrderFlag)){
			return listBuyOrder();
		}else {
			return list();
		}
	}

	public String activate() throws Exception{
		
		sendService(ConstCode.SELL_ORDER_ACTIVATE_CONFIRM,
				  sellOrderDTO).getDetailvo();
		
		if(!this.hasErrors()){
			this.addActionMessage("激活订单成功!");
		}else{
			buyOrderFlag="2";
			button();
		}
	
		return activateList();
	}
	
	public String getBuyOrderFlag() {
		return buyOrderFlag;
	}

	public void setBuyOrderFlag(String buyOrderFlag) {
		this.buyOrderFlag = buyOrderFlag;
	}	
	
	
}
