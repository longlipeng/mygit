package com.allinfinance.univer.consumer.shop.dto;

import java.util.List;

import com.allinfinance.framework.dto.BaseDTO;
import com.allinfinance.univer.entity.dto.ContactDTO;

public class ShopDTO extends BaseDTO implements java.io.Serializable{
    private String shopId;
    private String entityId;
    private String entityName;
    private String consumerId;
    private String shopCode;
    private String shopName;
    private String shopEnglishName;
    private String shopAddress;
    private String shopEnglishAddress;
    private String shopPostcode;
    private String shopTelephone;
    private String shopFax;
    private String shopWebsite;
    private String shopCity;
    private String businessArea;
    private String shopCategory;
    private String shopType;
    private String shopRank;
    private String paymentType;
    private String shopLongitude;
    private String shopLatitude;
    private String shopNotes;
    private String englishNotes;
    private String defaultFlag;
    private String shopState;
    private String joinDate;
    private String shopComment;
    private String createUser;
    private String createTime;
    private String modifyUser;
    private String modifyTime;
    private String dataState;
    private boolean is;
    private List<ShopDTO> shopDTOList;
    private String merchantName;
    private String merchantCode;
    private List<ContactDTO> contactList; 
    private String creator;
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	public String getShopCode() {
		return shopCode;
	}
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getShopEnglishName() {
		return shopEnglishName;
	}
	public void setShopEnglishName(String shopEnglishName) {
		this.shopEnglishName = shopEnglishName;
	}
	public String getShopAddress() {
		return shopAddress;
	}
	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}
	public String getShopEnglishAddress() {
		return shopEnglishAddress;
	}
	public void setShopEnglishAddress(String shopEnglishAddress) {
		this.shopEnglishAddress = shopEnglishAddress;
	}
	public String getShopPostcode() {
		return shopPostcode;
	}
	public void setShopPostcode(String shopPostcode) {
		this.shopPostcode = shopPostcode;
	}
	public String getShopTelephone() {
		return shopTelephone;
	}
	public void setShopTelephone(String shopTelephone) {
		this.shopTelephone = shopTelephone;
	}
	public String getShopFax() {
		return shopFax;
	}
	public void setShopFax(String shopFax) {
		this.shopFax = shopFax;
	}
	public String getShopWebsite() {
		return shopWebsite;
	}
	public void setShopWebsite(String shopWebsite) {
		this.shopWebsite = shopWebsite;
	}
	public String getShopCity() {
		return shopCity;
	}
	public void setShopCity(String shopCity) {
		this.shopCity = shopCity;
	}
	public String getBusinessArea() {
		return businessArea;
	}
	public void setBusinessArea(String businessArea) {
		this.businessArea = businessArea;
	}
	public String getShopCategory() {
		return shopCategory;
	}
	public void setShopCategory(String shopCategory) {
		this.shopCategory = shopCategory;
	}
	public String getShopType() {
		return shopType;
	}
	public void setShopType(String shopType) {
		this.shopType = shopType;
	}
	public String getShopRank() {
		return shopRank;
	}
	public void setShopRank(String shopRank) {
		this.shopRank = shopRank;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getShopLongitude() {
		return shopLongitude;
	}
	public void setShopLongitude(String shopLongitude) {
		this.shopLongitude = shopLongitude;
	}
	public String getShopLatitude() {
		return shopLatitude;
	}
	public void setShopLatitude(String shopLatitude) {
		this.shopLatitude = shopLatitude;
	}
	public String getShopNotes() {
		return shopNotes;
	}
	public void setShopNotes(String shopNotes) {
		this.shopNotes = shopNotes;
	}
	public String getEnglishNotes() {
		return englishNotes;
	}
	public void setEnglishNotes(String englishNotes) {
		this.englishNotes = englishNotes;
	}
	public String getDefaultFlag() {
		return defaultFlag;
	}
	public void setDefaultFlag(String defaultFlag) {
		this.defaultFlag = defaultFlag;
	}
	public String getShopState() {
		return shopState;
	}
	public void setShopState(String shopState) {
		this.shopState = shopState;
	}
	public String getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}
	public String getShopComment() {
		return shopComment;
	}
	public void setShopComment(String shopComment) {
		this.shopComment = shopComment;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getModifyUser() {
		return modifyUser;
	}
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
	public String getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getDataState() {
		return dataState;
	}
	public void setDataState(String dataState) {
		this.dataState = dataState;
	}
	public List<ShopDTO> getShopDTOList() {
		return shopDTOList;
	}
	public void setShopDTOList(List<ShopDTO> shopDTOList) {
		this.shopDTOList = shopDTOList;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public List<ContactDTO> getContactList() {
		return contactList;
	}
	public void setContactList(List<ContactDTO> contactList) {
		this.contactList = contactList;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public String getConsumerId() {
		return consumerId;
	}
	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}
	public boolean isIs() {
		return is;
	}
	public void setIs(boolean is) {
		this.is = is;
	}
	
    
}
