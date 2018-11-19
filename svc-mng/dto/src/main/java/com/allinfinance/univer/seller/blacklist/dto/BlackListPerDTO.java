package com.allinfinance.univer.seller.blacklist.dto;

import java.io.Serializable;

import com.allinfinance.framework.dto.PageQueryDTO;

public class BlackListPerDTO extends PageQueryDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id; 
	private String name;//姓名
	private String  sex;//性别  1男 2女 3其他
	private String birthday;// 出生/注册时间
	private String country;//国家
	private String idnumbers;//证件
	private String listOfType;//名单类别
	private String listOfSource;//名单来源
	private String entityType;//实体类型 1：个人 2 机构
	private String address;//地址
	private String description;//补充信息
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getIdnumbers() {
		return idnumbers;
	}
	public void setIdnumbers(String idnumbers) {
		this.idnumbers = idnumbers;
	}
	public String getListOfType() {
		return listOfType;
	}
	public void setListOfType(String listOfType) {
		this.listOfType = listOfType;
	}
	public String getListOfSource() {
		return listOfSource;
	}
	public void setListOfSource(String listOfSource) {
		this.listOfSource = listOfSource;
	}
	public String getEntityType() {
		return entityType;
	}
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	/*@Override
	public String toString() {
		return "BlackListPerDTO [id=" + id + ", name=" + name + ", sex=" + sex + ", birthday=" + birthday + ", country="
				+ country + ", idnumbers=" + idnumbers + ", listOfType=" + listOfType + ", listOfSource=" + listOfSource
				+ ", entityType=" + entityType + ", address=" + address + ", description=" + description + "]";
	}*/
	

}
