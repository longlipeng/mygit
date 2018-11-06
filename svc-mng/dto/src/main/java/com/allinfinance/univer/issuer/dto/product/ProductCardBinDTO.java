package com.allinfinance.univer.issuer.dto.product;

import java.util.ArrayList;
import java.util.List;

import com.allinfinance.framework.dto.BaseDTO;

/**
 * 产品卡BIN DTO
 * 
 * @author xxl
 * 
 */
public class ProductCardBinDTO extends BaseDTO {

	private static final long serialVersionUID = 5266742290726306146L;

	private String cardBin;

	private String productId;

	private String serialNumber;
	
	private String effect;
	private String realCardBin;
	private  String binType;
	
	private List<ProductCardBinDTO> productCardBinDTOs = new ArrayList<ProductCardBinDTO>();

	
	public String getBinType() {
		return binType;
	}

	public void setBinType(String binType) {
		this.binType = binType;
	}

	public String getRealCardBin() {
		return realCardBin;
	}

	public void setRealCardBin(String realCardBin) {
		this.realCardBin = realCardBin;
	}

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

	public List<ProductCardBinDTO> getProductCardBinDTOs() {
		return productCardBinDTOs;
	}

	public void setProductCardBinDTOs(List<ProductCardBinDTO> productCardBinDTOs) {
		this.productCardBinDTOs = productCardBinDTOs;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

}
