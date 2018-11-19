package com.allinfinance.prepay.model;
/**
 * 车享售卡统计dto
 * @author taojiajun
 *
 */
public class SellCardOrder {
	private String orderNo;

	private String cardNo;//卡号
	
	private String faceValue;//面值
	
	private String area;//发卡区域
	
	private String name;//会员姓名
	
	private String phone;//手机号
	
	private String userId;//会员id
	
	private String cardStatus;//卡状态
	
	private String time;//制卡时间
	
	private String cardType;//卡类型:1记名卡,2不记名卡
	
	private String product;//卡产品
	
	private String cardMedium;//卡介质
	
	private String orderType;//订单类型
	
	private String onymousType;//署名类型  1记名卡（个性化卡）2不记名 3 记名（库存卡）

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getFaceValue() {
		return faceValue;
	}

	public void setFaceValue(String faceValue) {
		this.faceValue = faceValue;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getCardMedium() {
		return cardMedium;
	}

	public void setCardMedium(String cardMedium) {
		this.cardMedium = cardMedium;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOnymousType() {
		return onymousType;
	}

	public void setOnymousType(String onymousType) {
		this.onymousType = onymousType;
	}

	
}
