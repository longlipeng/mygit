package com.allinfinance.prepay.model;
/**
 * �����ۿ�ͳ��dto
 * @author taojiajun
 *
 */
public class SellCardOrder {
	private String orderNo;

	private String cardNo;//����
	
	private String faceValue;//��ֵ
	
	private String area;//��������
	
	private String name;//��Ա����
	
	private String phone;//�ֻ���
	
	private String userId;//��Աid
	
	private String cardStatus;//��״̬
	
	private String time;//�ƿ�ʱ��
	
	private String cardType;//������:1������,2��������
	
	private String product;//����Ʒ
	
	private String cardMedium;//������
	
	private String orderType;//��������
	
	private String onymousType;//��������  1�����������Ի�����2������ 3 ��������濨��

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
