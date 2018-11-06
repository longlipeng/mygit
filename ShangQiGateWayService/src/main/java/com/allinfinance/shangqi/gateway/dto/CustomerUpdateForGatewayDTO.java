package com.allinfinance.shangqi.gateway.dto;

import java.util.Date;


public class CustomerUpdateForGatewayDTO{
	//姓名
	private String customerName;
	//证件号
	private String idNo;
	//证件类型
	private String idType;
	//地址
	private String customerAddress;
	//国籍
	private String nationality;
	/**
	 * 性别
	 */
	private String gender;
	//电话
	private String customerTelephone;
	
	private String nation;//民族(备用)
	
	private String education;//学历(备用)
	
	private String marriage;//婚姻状况(备用)
	//证件失效日期
	private String endValidity;
	//职业
	private String awareness;
	
	private String cardNo;
	
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getCustomerTelephone() {
		return customerTelephone;
	}
	public void setCustomerTelephone(String customerTelephone) {
		this.customerTelephone = customerTelephone;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getMarriage() {
		return marriage;
	}
	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}
	public String getEndValidity() {
		return endValidity;
	}
	public void setEndValidity(String endValidity) {
		this.endValidity = endValidity;
	}
	public String getAwareness() {
		return awareness;
	}
	public void setAwareness(String awareness) {
		this.awareness = awareness;
	}
	
	
	
	
	
}
