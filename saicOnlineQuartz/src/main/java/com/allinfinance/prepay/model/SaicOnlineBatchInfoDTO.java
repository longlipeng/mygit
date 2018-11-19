package com.allinfinance.prepay.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
@XStreamAlias("saicOnlineBatchInfoDTO")  
public class SaicOnlineBatchInfoDTO {
	@XStreamAlias("leaguerId")
	protected  String leaguerId;//��ԱID
	@XStreamAlias("name")
	protected  String name;//����
	@XStreamAlias("custidType")
	protected  String custidType;//֤������
	@XStreamAlias("custidNo")
	protected  String custidNo;//֤����
	@XStreamAlias("custMobile")
	protected  String custMobile;//�ֻ���
	@XStreamAlias("custArea")
	protected  String custArea;//�ͻ���������
	@XStreamAlias("productId")
	protected  String productId;//����Ʒ
	@XStreamAlias("cardholderBirthday")
	protected  String cardholderBirthday;//����
	@XStreamAlias("deliveryAddress")
	protected  String deliveryAddress;//�ʼĵ�ַ
	@XStreamAlias("dlateNumber")
	protected  String dlateNumber;//���ƺ�
	@XStreamAlias("driverLicence")
	protected  String driverLicence;//��ʻ֤
	@XStreamAlias("activitySector")
	protected  String activitySector; //��ҵ
	@XStreamAlias("awareness")
	protected  String awareness; //ְҵ
	@XStreamAlias("county")
	protected  String county; //����
	@XStreamAlias("city")
	protected  String city; //����
	@XStreamAlias("validity")
	protected  String validity;//֤��ʧЧ����
	@XStreamAlias("gender")
	protected  String gender;//�Ա�
	@XStreamAlias("remark")
	protected  String remark;//��ע
	
	
	
	public String getCardholderBirthday() {
		return cardholderBirthday;
	}

	public void setCardholderBirthday(String cardholderBirthday) {
		this.cardholderBirthday = cardholderBirthday;
	}

	public String getLeaguerId() {
		return leaguerId;
	}

	public void setLeaguerId(String leaguerId) {
		this.leaguerId = leaguerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCustidType() {
		return custidType;
	}

	public void setCustidType(String custidType) {
		this.custidType = custidType;
	}

	public String getCustidNo() {
		return custidNo;
	}

	public void setCustidNo(String custidNo) {
		this.custidNo = custidNo;
	}

	public String getCustMobile() {
		return custMobile;
	}

	public void setCustMobile(String custMobile) {
		this.custMobile = custMobile;
	}

	public String getCustArea() {
		return custArea;
	}

	public void setCustArea(String custArea) {
		this.custArea = custArea;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getDlateNumber() {
		return dlateNumber;
	}

	public void setDlateNumber(String dlateNumber) {
		this.dlateNumber = dlateNumber;
	}

	public String getDriverLicence() {
		return driverLicence;
	}

	public void setDriverLicence(String driverLicence) {
		this.driverLicence = driverLicence;
	}

	public String getActivitySector() {
		return activitySector;
	}

	public void setActivitySector(String activitySector) {
		this.activitySector = activitySector;
	}

	public String getAwareness() {
		return awareness;
	}

	public void setAwareness(String awareness) {
		this.awareness = awareness;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	

}
