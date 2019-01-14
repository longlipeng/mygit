package com.allinfinance.univer.seller.order.dto;

import java.util.List;
import java.util.Map;

import com.allinfinance.framework.dto.BaseDTO;
import com.allinfinance.univer.issuer.dto.cardcompany.CardCompanyDTO;

public class SellOrderMakeCardDTO extends BaseDTO {

	private static final long serialVersionUID = -5832625025525632192L;

	private String orderId;

	private String cardCompanyId;

	private String cardType;

	private Map<String, List<byte[]>> fileData;

	private Short makeCardState;

	private Short pinState;

	private List<CardCompanyDTO> cardCompanyList;

	private String productId;

	private Short productType;

	private List<SellOrderDTO> sellOrderList;

	private String operateType;

	private String memo;

	private String operate;

	private String fileName;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCardCompanyId() {
		return cardCompanyId;
	}

	public void setCardCompanyId(String cardCompanyId) {
		this.cardCompanyId = cardCompanyId;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public Map<String, List<byte[]>> getFileData() {
		return fileData;
	}

	public void setFileData(Map<String, List<byte[]>> fileData) {
		this.fileData = fileData;
	}

	public Short getMakeCardState() {
		return makeCardState;
	}

	public void setMakeCardState(Short makeCardState) {
		this.makeCardState = makeCardState;
	}

	public Short getPinState() {
		return pinState;
	}

	public void setPinState(Short pinState) {
		this.pinState = pinState;
	}

	public List<CardCompanyDTO> getCardCompanyList() {
		return cardCompanyList;
	}

	public void setCardCompanyList(List<CardCompanyDTO> cardCompanyList) {
		this.cardCompanyList = cardCompanyList;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Short getProductType() {
		return productType;
	}

	public void setProductType(Short productType) {
		this.productType = productType;
	}

	public List<SellOrderDTO> getSellOrderList() {
		return sellOrderList;
	}

	public void setSellOrderList(List<SellOrderDTO> sellOrderList) {
		this.sellOrderList = sellOrderList;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}