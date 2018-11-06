package com.allinfinance.univer.issuer.dto.product;

import com.allinfinance.framework.dto.PageQueryDTO;

/**
 * 产品卡BIN 查询DTO
 * 
 * @author xxl
 * 
 */
public class ProductCardBinQueryDTO extends PageQueryDTO {

	private static final long serialVersionUID = -626225743477443236L;

	private String cardBin;

	private String productId;

	private String effect;
	private String serialNumber;

	public String getCardBin() {
		return cardBin;
	}

	public void setCardBin(String cardBin) {
		this.cardBin = cardBin;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getEffect() {
		return effect;
	}

	public void setEffect(String effect) {
		this.effect = effect;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

}
