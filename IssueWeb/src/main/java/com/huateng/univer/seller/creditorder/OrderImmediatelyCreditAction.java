package com.huateng.univer.seller.creditorder;

import org.apache.log4j.Logger;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.huateng.univer.seller.sellorder.OrderBaseAction;

public class OrderImmediatelyCreditAction extends OrderBaseAction{

	private Logger logger = Logger.getLogger(OrderImmediatelyCreditAction.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String message;
	
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
		actionName= "orderImmediatelyCreditAction";
		actionMethodName="button";
	}

	@Override
	protected void initOrderType() {
	
		
	}

	
	
	public String list(){
		try{
			 ListPageInit("sellOrder", sellOrderQueryDTO);
			 /***
			  * 按订单ID的倒序排序
			  */
			 if(isEmpty(sellOrderQueryDTO.getSortFieldName())){
				 sellOrderQueryDTO.setSort("desc");
				 sellOrderQueryDTO.setSortFieldName("orderId");
			 }
			 PageDataDTO result = (PageDataDTO) sendService(ConstCode.SELL_ORDER_QUERY_AT_ORDER_IMMDTY_CREDIT,
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
		sellOrderDTO.setOperation(operation);
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
		sellOrderDTO.setOperation(operation);
		button();
		if(hasErrors()){
			list();
			return "list";
		}
		return "button";
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
			sendService(ConstCode.SELL_ORDER_SUBMIT_AT_ORDER_IMMDIATELY_CREDIT,
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
			sendService(ConstCode.SELL_ORDER_SENDBACK_AT_ORDER_IMMDIATELY_CREDIT,
					  sellOrderDTO).getDetailvo();
			if(!this.hasErrors()){
				this.addActionMessage("退回订单成功!");
			}
		}catch(Exception e){
			this.logger.error(e.getMessage());
		}
			return list();
	}
}
