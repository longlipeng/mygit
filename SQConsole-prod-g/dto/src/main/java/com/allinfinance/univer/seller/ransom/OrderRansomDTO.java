package com.allinfinance.univer.seller.ransom;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.framework.dto.PageQueryDTO;
import com.allinfinance.univer.seller.customer.CustomerDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;

public class OrderRansomDTO extends PageQueryDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String orderId;// 订单号
	private String entityName;// 订单发起者 customer entity_Id
	private String productName;// 产品名称
	private String createUser;// 创建人

	private PageDataDTO orderLists;

	private String entityId;

	private String cardNoStart;
	private String cardNoEnd;
	private String cardNoOnly;

	private SellOrderDTO sellOrderDTO;
	private CustomerDTO customerDTO;
	
	private String orderState;
	
	private String order;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public PageDataDTO getOrderLists() {
		return orderLists;
	}

	public void setOrderLists(PageDataDTO orderLists) {
		this.orderLists = orderLists;
	}

	public String getCardNoStart() {
		return cardNoStart;
	}

	public void setCardNoStart(String cardNoStart) {
		this.cardNoStart = cardNoStart;
	}

	public String getCardNoEnd() {
		return cardNoEnd;
	}

	public void setCardNoEnd(String cardNoEnd) {
		this.cardNoEnd = cardNoEnd;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public CustomerDTO getCustomerDTO() {
		return customerDTO;
	}

	public void setCustomerDTO(CustomerDTO customerDTO) {
		this.customerDTO = customerDTO;
	}

	public SellOrderDTO getSellOrderDTO() {
		return sellOrderDTO;
	}

	public void setSellOrderDTO(SellOrderDTO sellOrderDTO) {
		this.sellOrderDTO = sellOrderDTO;
	}

	public String getCardNoOnly() {
		return cardNoOnly;
	}

	public void setCardNoOnly(String cardNoOnly) {
		this.cardNoOnly = cardNoOnly;
	}

	public String getOrderState() {
		return orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

}
