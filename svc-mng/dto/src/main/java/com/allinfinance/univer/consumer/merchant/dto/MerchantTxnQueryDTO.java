package com.allinfinance.univer.consumer.merchant.dto;

import java.util.List;

import com.allinfinance.framework.dto.PageQueryDTO;
import com.allinfinance.univer.consumer.shop.dto.ShopDTO;

public class MerchantTxnQueryDTO extends PageQueryDTO{
	private String merchantCode;
	private String merchantName;
	private String shopId;
	private String shopName;
	private String posId;
	private String startDate;
	private String endDate;
	private String MinTxn;
	private String MaxTxn;
	private String selectFlag;
	private String cardNo;
	private List<MerchantDTO> merchantDTOList;
	private String entityId;
	private String transType;
	private List<ShopDTO> shopDTOList;
	
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getPosId() {
		if(this.posId ==null ||"".equals(this.posId.trim())){
			return "";
		}
		return posId;
	}
	public void setPosId(String posId) {
		this.posId = posId;
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
	public String getMinTxn() {
		return MinTxn;
	}
	public void setMinTxn(String minTxn) {
		MinTxn = minTxn;
	}
	public String getMaxTxn() {
		return MaxTxn;
	}
	public void setMaxTxn(String maxTxn) {
		MaxTxn = maxTxn;
	}
	public String getSelectFlag() {
		return selectFlag;
	}
	public void setSelectFlag(String selectFlag) {
		this.selectFlag = selectFlag;
	}
	public List<MerchantDTO> getMerchantDTOList() {
		return merchantDTOList;
	}
	public void setMerchantDTOList(List<MerchantDTO> merchantDTOList) {
		this.merchantDTOList = merchantDTOList;
	}
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public List<ShopDTO> getShopDTOList() {
		return shopDTOList;
	}
	public void setShopDTOList(List<ShopDTO> shopDTOList) {
		this.shopDTOList = shopDTOList;
	}
    
}
