package com.huateng.univer.seller.sellorder;


import org.apache.log4j.Logger;
import org.extremecomponents.table.core.TableConstants;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderPaymentDTO;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.util.StringUtils;

public class OrderPaymentAction extends OrderBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(OrderPaymentAction.class);
	private String message;
	private String[] ids;
	//private SellOrderPaymentDTO orderPaymentDTO = new SellOrderPaymentDTO();
	private Integer totalRows;
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
		actionName = "orderPaymentAction";
		actionMethodName = "button";
	}

	@Override
	protected void initOrderType() {

	}

	public String list() {
		init();
		try {
			ListPageInit("sellOrder", sellOrderQueryDTO);
			/***
			 * 按订单ID的倒序排序
			 */
			if (isEmpty(sellOrderQueryDTO.getSortFieldName())) {
				sellOrderQueryDTO.setSort("desc");
				sellOrderQueryDTO.setSortFieldName("orderId");
			}
			//当订单类型为充值订单 并且 支付方式为1时  支付方式为充值前
			if(OrderConst.ORDER_TYPE_SELL_CUSTOMER_CREDIT_ORDER.equals(sellOrderQueryDTO.getOrderTypeArray()) && "1".equals(sellOrderQueryDTO.getPaymentTerm().trim())){
				sellOrderQueryDTO.setPaymentTerm("3");
			}else if(OrderConst.ORDER_TYPE_SELL_CUSTOMER_CREDIT_ORDER.equals(sellOrderQueryDTO.getOrderTypeArray()) && "3".equals(sellOrderQueryDTO.getPaymentTerm().trim())){
				//当订单类型为充值订单 并且 支付方式为3时  配送前  没有此支付方式 查不出来记录
				sellOrderQueryDTO.setPaymentTerm("1");
			}
			addCondition();
			PageDataDTO result = (PageDataDTO) sendService(
					ConstCode.ORDER_INQUERY_AT_ORDER_PAYMENT, sellOrderQueryDTO)
					.getDetailvo();
			sellOrders = result.getData();
			sellOrder_totalRows = result.getTotalRecord();
			 if(OrderConst.ORDER_TYPE_SELL_CUSTOMER_CREDIT_ORDER.equals(sellOrderQueryDTO.getOrderTypeArray()) && "3".equals(sellOrderQueryDTO.getPaymentTerm().trim())){
					sellOrderQueryDTO.setPaymentTerm("1");
				}else if(OrderConst.ORDER_TYPE_SELL_CUSTOMER_CREDIT_ORDER.equals(sellOrderQueryDTO.getOrderTypeArray()) && "1".equals(sellOrderQueryDTO.getPaymentTerm().trim())){
					//当订单类型为充值订单 并且 支付方式为3时  配送前  没有此支付方式 查不出来记录
					sellOrderQueryDTO.setPaymentTerm("3");
				}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "list";
	}

	public String button() {
		try {
			view();
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "button";
	}

	public String submitConfirm() {
		message = "提交";
		operation = "submit";
		return button();
		
	}

	public String submit() {
		try {
			//checkPaymentSubmit();
			if(hasErrors()){
				return submitConfirm();
			}
			sendService(ConstCode.SELL_ORDER_SUBMIT_AT_ORDER_PAYMENT,
					sellOrderDTO).getDetailvo();

			if (!this.hasErrors()) {
				this.addActionMessage("订单付款成功!");
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return list();
	}
	public String pay(){
		message = "提交";
		operation = "payConfirm";		
		button();
		actionMethodName="pay";
		return "confirm";
	}
	public String payConfirm(){		
		//checkPaymentSubmit();
		sendService(ConstCode.SELL_ORDER_CONFIRM_AT_ORDER_PAYMENT,
				sellOrderDTO).getDetailvo();

		if (!this.hasErrors()) {
			this.addActionMessage("付款确认成功!");
		}
		return list();
	}

	public String combine(){			
		sellOrderDTO=(SellOrderDTO)sendService(ConstCode.SELL_ORDER_CONFIRM_AT_COMBINE,
				sellOrderDTO).getDetailvo();		
		if(hasActionErrors()){
			return list();
		}
		totalRows=0;
		sellOrderDTO.setPaymentId(StringUtils.getStr(sellOrderDTO.getOrderIds()));
		return "combinePay";
	}
	public String combineList() throws Exception{
		ListPageInit("orderPayment", sellOrderPaymentQueryDTO);
		String ids=sellOrderDTO.getPaymentId();
		String entityId=sellOrderDTO.getFirstEntityId();
		sellOrderDTO=(SellOrderDTO)sendService(ConstCode.SELL_ORDER_PAYMENT_COMBINE_LIST,sellOrderDTO).getDetailvo();
		getRequest().setAttribute("orderPaymentList", sellOrderDTO.getPaymentList().getData());
		getRequest().setAttribute("orderPayment_" + TableConstants.TOTAL_ROWS, sellOrderDTO.getPaymentList().getTotalRecord());
		sellOrderDTO.setPaymentId(ids);
		sellOrderDTO.setFirstEntityId(entityId);
		return "combinePay";
	}
	
	public String combinePay(){
		orderPaymentDTO.setTotalPrice(sellOrderDTO.getTotalPrice());
		sendService(ConstCode.SELL_ORDER_PAYMENT_COMBINE,orderPaymentDTO);
		if(!hasActionErrors()){
			 getRequest().setAttribute("sucessMessage", "添加成功!");
		 }else{
			 addOrderPayment();
			 return "addOrderPayment";
		 }
		return "addSuccess";
	}
	
	public String confirm(){
		orderPaymentDTO.setLoginUserId(this.getUser().getLoginUserId());
		sendService(ConstCode.SELL_ORDER_PAYMENT_CONFIRM,orderPaymentDTO);
		return list();
	}
	
	public String deletePayment() throws Exception{
		orderPaymentDTO.setPaymentId(sellOrderDTO.getPaymentId());
		sendService(ConstCode.SELL_ORDER_PAYMENT_COMBINE_DELETE,orderPaymentDTO);
		if(hasActionErrors()){			
			combineList();
			return "combinePay";
		}
		combineList();
		return "combinePay";
		
	}
	public String deletePay(){
		deleteOrderPayment();
		return pay();
	}
	public void checkPaymentSubmit() {
		if (sellOrderDTO.getOrderType().equals(
				OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN)
				|| sellOrderDTO
						.getOrderType()
						.equals(
								OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_UNSIGN)
								|| sellOrderDTO
						.getOrderType()
						.equals(
								OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN)
								|| sellOrderDTO
						.getOrderType()
						.equals(
								OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_UNSIGN)
				|| sellOrderDTO.getOrderType().equals(
						OrderConst.ORDER_TYPE_SELL_CUSTOMER_CREDIT_ORDER)
				|| sellOrderDTO.getOrderType().equals(
						OrderConst.ORDER_TYPE_ORDER_CHANGE_CARD)		
		) {
			if(null == sellOrderDTO.getPayDetails() || sellOrderDTO.getPayDetails().trim().equals("")){
				addFieldError("sellOrderDTO.payDetails", "支付明细必须填写");
			}
			if(null == sellOrderDTO.getFromBankId() || sellOrderDTO.getFromBankId().trim().equals("")){
				addFieldError("sellOrderDTO.fromBankId", "付款银行必须选择");
			}
			if(null == sellOrderDTO.getIntoBankId() || sellOrderDTO.getIntoBankId().trim().equals("")){
				addFieldError("sellOrderDTO.intoBankId", "收款银行必须选择");
			}
		}
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public SellOrderPaymentDTO getOrderPaymentDTO() {
		return orderPaymentDTO;
	}

	public void setOrderPaymentDTO(SellOrderPaymentDTO orderPaymentDTO) {
		this.orderPaymentDTO = orderPaymentDTO;
	}

	public Integer getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}

}
