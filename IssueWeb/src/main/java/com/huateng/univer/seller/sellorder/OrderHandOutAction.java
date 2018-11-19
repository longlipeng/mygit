package com.huateng.univer.seller.sellorder;

import org.apache.log4j.Logger;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.huateng.framework.constant.OrderConst;

/***
 * 订单配送
 * @author dawn
 *
 */
public class OrderHandOutAction extends OrderBaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(OrderHandOutAction.class);
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
		actionName= "orderHandOutAction";
		actionMethodName="button";

	}

	@Override
	protected void initOrderType() {
		

	}
	
	public String list(){
		try{
			ListPageInit("sellOrder", sellOrderQueryDTO);
			sellOrderQueryDTO.setOrderState(OrderConst.ORDER_STATE_ORDER_SEND);
			 /***
			  * 按订单ID的倒序排序
			  */
			 if(isEmpty(sellOrderQueryDTO.getSortFieldName())){
				 sellOrderQueryDTO.setSort("desc");
				 sellOrderQueryDTO.setSortFieldName("orderId");
			 }
			 PageDataDTO result = (PageDataDTO) sendService(ConstCode.SELL_ORDER_QUERY_AT_READY_OR_HANDOUT,
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
		return button();
	}
	
	
	public String sendBackConfirm(){
		message="退回";
		operation="sendback";
		return button();
	}
	
	public String submit(){
		try{
			sendService(ConstCode.SELL_ORDER_SUBMIT_AT_HAND_OUT,
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
			sendService(ConstCode.SELL_ORDER_SENDBACK_AT_HAND_OUT,
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
	
	
}
