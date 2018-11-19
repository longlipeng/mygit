package com.huateng.univer.seller.sellorder;


import org.apache.log4j.Logger;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.huateng.framework.constant.OrderConst;


public class OrderSendConfirmAction extends OrderBaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(OrderSendConfirmAction.class);
	private String message;
	private String flag;
	@Override
	protected void addCondition() {
		sellOrderQueryDTO.setOrderState(OrderConst.ORDER_STATE_ORDER_WAIT_SEND_CONFIRM);
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
		actionName= "orderSendConfirmAction";
		actionMethodName="button";
	}

	@Override
	protected void initOrderType() {
		
	}

	@Override
	public String list(){
		init();
		try{
			 ListPageInit("sellOrder", sellOrderQueryDTO);
			 /***
			  * 按订单ID的倒序排序
			  */
			 if(isEmpty(sellOrderQueryDTO.getSortFieldName())){
				 sellOrderQueryDTO.setSort("desc");
				 sellOrderQueryDTO.setSortFieldName("orderId");
			 }
			 addCondition();
			 PageDataDTO result = (PageDataDTO) sendService(ConstCode.SELL_ORDER_QUERY_AT_SEND_CONFIRM,
					  sellOrderQueryDTO).getDetailvo();
			 sellOrders = result.getData();
			 sellOrder_totalRows = result.getTotalRecord();
		}catch(Exception e){
			this.logger.error(e.getMessage());
		}
		return "list";
	}
	
	public String button(){
		try{
			view();
		}catch(Exception e){
			this.logger.error(e.getMessage());
		}
		return "button";
	}
	
	
	public String submitConfirm(){
		message="提交";
		operation="submit";
		sellOrderDTO.setPerFlag("1");
		button();
		if(hasErrors()){
			list();
			return "list";
		}
		return "button";
	}
	
	
	public String sendBackConfirm(){
		message="退回";
		operation="sendback";
		sellOrderDTO.setPerFlag("1");
		button();
		if(hasErrors()){
			list();
			return "list";
		}
		return "back";
	}
	
	
	public String submit(){
		try{
			sendService(ConstCode.SELL_ORDER_SUBMIT_AT_SEND_CONFIRM,
					  sellOrderDTO).getDetailvo();
			if(!this.hasErrors()){
				this.addActionMessage("提交订单成功!");
			}
			
		}catch(Exception e){
			this.logger.error(e.getMessage());
		}
		return list();
	
	}
	
	
	public String sendback(){
		try{
			sendService(ConstCode.SELL_ORDER_SENDBACK_AT_SEND_CONFIRM,
					  sellOrderDTO).getDetailvo();
			if(!this.hasErrors()){
				this.addActionMessage("退回订单成功!");
			}
			
		}catch(Exception e){
			this.logger.error(e.getMessage());
		}
		return list();
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	
}
