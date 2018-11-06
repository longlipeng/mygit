package com.allinfinance.prepay.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
@XStreamAlias("saicOnlineBatchInfoDTO")  
public class SaicOnlineBatchInfoDTO {
	@XStreamAlias("user_id")
	protected  String user_id;//会员ID
	
	@XStreamAlias("user_name")
	protected  String user_name;//姓名
	
	@XStreamAlias("id_type")
	protected  String id_type;//证件类型
	
	@XStreamAlias("id_no")
	protected  String id_no;//证件号
	
	@XStreamAlias("mobile")
	protected  String mobile;//手机号
	
	
	@XStreamAlias("card_product")
	protected  String card_product;//卡产品
	
	@XStreamAlias("birthday")
	protected  String birthday;//生日
	
	@XStreamAlias("address")
	protected  String address;//邮寄地址
	
	
	
	@XStreamAlias("occupation")
	protected  String occupation; //职业
	
	@XStreamAlias("nationality")
	protected  String nationality; //国籍
	
	
	@XStreamAlias("id_validity")
	protected  String id_validity;//证件失效日期
	
	@XStreamAlias("gender")
	protected  String gender;//性别
	
	@XStreamAlias("email")
	protected  String email;//邮箱
	
	
	
	

	

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getId_type() {
		return id_type;
	}

	public void setId_type(String id_type) {
		this.id_type = id_type;
	}

	public String getId_no() {
		return id_no;
	}

	public void setId_no(String id_no) {
		this.id_no = id_no;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getCard_product() {
		return card_product;
	}

	public void setCard_product(String card_product) {
		this.card_product = card_product;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getId_validity() {
		return id_validity;
	}

	public void setId_validity(String id_validity) {
		this.id_validity = id_validity;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	

}
