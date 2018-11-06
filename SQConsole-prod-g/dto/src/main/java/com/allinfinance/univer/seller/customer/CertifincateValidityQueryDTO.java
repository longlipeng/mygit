package com.allinfinance.univer.seller.customer;

import com.allinfinance.framework.dto.PageQueryDTO;

public class CertifincateValidityQueryDTO extends PageQueryDTO{
	private String entityId;//id
	private String name;//客户名称
	private String cusType;//客户类别
	private String iDtype;//证件类别
	private String idNo;//证件编号
	private String type;//0 将要过期  1：已过期
	private String validityDate;//日期
	private String phoneNuber;//手机号
	
	

	public String getPhoneNuber() {
		return phoneNuber;
	}

	public void setPhoneNuber(String phoneNuber) {
		this.phoneNuber = phoneNuber;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getValidityDate() {
		return validityDate;
	}

	public void setValidityDate(String validityDate) {
		this.validityDate = validityDate;
	}

	public String getCusType() {
		return cusType;
	}

	public void setCusType(String cusType) {
		this.cusType = cusType;
	}

	public String getiDtype() {
		return iDtype;
	}

	public void setiDtype(String iDtype) {
		this.iDtype = iDtype;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


}
