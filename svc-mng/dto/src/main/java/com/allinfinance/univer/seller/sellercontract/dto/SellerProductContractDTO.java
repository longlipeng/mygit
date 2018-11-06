package com.allinfinance.univer.seller.sellercontract.dto;

import java.util.List;

import com.allinfinance.framework.dto.BaseDTO;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;

public class SellerProductContractDTO extends BaseDTO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7568400208778393038L;
	private String id;
	private String sellContractId;
	private String productId;   //产品号
	private String productName;
	private String cardFee;
	private String annualFee;
	private String createUser;
	private String createTime;
	private String modifyUser;
	private String modifyTime;
	private String dataState;

	private ProductDTO productDTO;
	
	private List<SellerAcctypeContractDTO> accDTOs;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSellContractId() {
		return sellContractId;
	}

	public void setSellContractId(String sellContractId) {
		this.sellContractId = sellContractId;
	}

	public ProductDTO getProductDTO() {
		return productDTO;
	}

	public void setProductDTO(ProductDTO productDTO) {
		this.productDTO = productDTO;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getCardFee() {
		return cardFee;
	}

	public void setCardFee(String cardFee) {
		if(cardFee != null)
			cardFee = cardFee.trim();
		this.cardFee = cardFee;
	}

	public String getAnnualFee() {
		return annualFee;
	}

	public void setAnnualFee(String annualFee) {
		if(annualFee != null)
			annualFee = annualFee.trim();
		this.annualFee = annualFee;
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

	public List<SellerAcctypeContractDTO> getAccDTOs() {
		return accDTOs;
	}

	public void setAccDTOs(List<SellerAcctypeContractDTO> accDTOs) {
		this.accDTOs = accDTOs;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	

}