package com.allinfinance.univer.seller.order.dto;

import java.util.List;
import java.util.Map;


/***
 * 
 * @author dawn 订单查询下载DTO
 */
public class SellOrderDownLoadDTO {
	/** 订单号*/
	private String orderId;
	/** 订单号*/
	private Object[] orderIds;
	public Object[] getOrderIds() {
		return orderIds;
	}
	public void setOrderIds(Object[] orderIds) {
		this.orderIds = orderIds;
	}
	/** 卡号 */
	private String cardNo;
	/** 订单类型*/
	private String orderType;
	/** 订单状态*/
	private String orderState;
	/** 是否激活*/
	private String initActStat;
	/** 订单开始日期*/
	private String startDate;
	/** 订单结束日期*/
	private String endDate;
	/** 配送方式：1：自取 2配送*/
	private String deliveryMeans;
	/**下单日期*/
	private String orderDate;
	/**收件人姓名*/
	private String receiver;
	/**收件人地址*/
	private String addr;
	/**收件人电话*/
	private String phone;
	/**收件人邮编*/
	private String postcode;
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	/*下载excel文件的map*/
	private Map<String, List<byte[]>> fileData;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getOrderState() {
		return orderState;
	}
	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}
	public String getInitActStat() {
		return initActStat;
	}
	public void setInitActStat(String initActStat) {
		this.initActStat = initActStat;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Map<String, List<byte[]>> getFileData() {
		return fileData;
	}
	public void setFileData(Map<String, List<byte[]>> fileData) {
		this.fileData = fileData;
	}
	public String getDeliveryMeans() {
		return deliveryMeans;
	}
	public void setDeliveryMeans(String deliveryMeans) {
		this.deliveryMeans = deliveryMeans;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
}
