package com.allinfinance.univer.seller.order.dto;

import java.util.ArrayList;
import java.util.List;

import com.allinfinance.framework.dto.BaseDTO;
import com.allinfinance.univer.issuer.dto.issuerContract.LoyaltyContractDTO;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;
import com.allinfinance.univer.seller.sellercontract.dto.SellerContractDTO;

/**
 * 采购订单DTO
 * 
 * @author xxl
 * 
 */
public class SellBuyOrderDTO extends BaseDTO {

	private static final long serialVersionUID = -4716947461835022291L;
	private String orderType;
	private String productId;
	private String faceValueType;
	private String faceValue;
	private String cardLayoutId;
	private String sellContractId;
	private String contractSeller;
	
	//Added by Yifeng.Shi @2011-05-31
	
	private String invoiceCompanyName;

	private String invoiceAddresses;

	private String invoiceItemId;

	private String invoiceItem;

	private String invoiceDate;
	
	private String onymousStat;
	
	// 要合并的订单编号集合
	private String orderIdArray;

	private ProductDTO productDTO = new ProductDTO();

	private List<SellerContractDTO> contractDTOs = new ArrayList<SellerContractDTO>();

	private List<SellOrderListDTO> sellOrderListDTOs = new ArrayList<SellOrderListDTO>();
	
	private List<LoyaltyContractDTO> loyaltyContractDTO = new ArrayList<LoyaltyContractDTO>();
	
	public List<LoyaltyContractDTO> getLoyaltyContractDTO() {
		return loyaltyContractDTO;
	}

	public void setLoyaltyContractDTO(List<LoyaltyContractDTO> loyaltyContractDTO) {
		this.loyaltyContractDTO = loyaltyContractDTO;
	}

	public List<SellerContractDTO> getContractDTOs() {
		return contractDTOs;
	}

	public void setContractDTOs(List<SellerContractDTO> contractDTOs) {
		this.contractDTOs = contractDTOs;
	}

	public ProductDTO getProductDTO() {
		return productDTO;
	}

	public void setProductDTO(ProductDTO productDTO) {
		this.productDTO = productDTO;
	}

	public List<SellOrderListDTO> getSellOrderListDTOs() {
		return sellOrderListDTOs;
	}

	public void setSellOrderListDTOs(List<SellOrderListDTO> sellOrderListDTOs) {
		this.sellOrderListDTOs = sellOrderListDTOs;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getOnymousStat() {
		return onymousStat;
	}

	public void setOnymousStat(String onymousStat) {
		this.onymousStat = onymousStat;
	}

	public String getSellContractId() {
		return sellContractId;
	}

	public void setSellContractId(String sellContractId) {
		this.sellContractId = sellContractId;
	}

	public String getOrderIdArray() {
		return orderIdArray;
	}

	public void setOrderIdArray(String orderIdArray) {
		this.orderIdArray = orderIdArray;
	}

	public String getContractSeller() {
		return contractSeller;
	}

	public void setContractSeller(String contractSeller) {
		this.contractSeller = contractSeller;
	}

	public String getCardLayoutId() {
		return cardLayoutId;
	}

	public void setCardLayoutId(String cardLayoutId) {
		this.cardLayoutId = cardLayoutId;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getFaceValueType() {
		return faceValueType;
	}

	public void setFaceValueType(String faceValueType) {
		this.faceValueType = faceValueType;
	}

	public String getFaceValue() {
		return faceValue;
	}

	public void setFaceValue(String faceValue) {
		this.faceValue = faceValue;
	}

	public String getInvoiceCompanyName() {
		return invoiceCompanyName;
	}

	public void setInvoiceCompanyName(String invoiceCompanyName) {
		this.invoiceCompanyName = invoiceCompanyName;
	}

	public String getInvoiceAddresses() {
		return invoiceAddresses;
	}

	public void setInvoiceAddresses(String invoiceAddresses) {
		this.invoiceAddresses = invoiceAddresses;
	}

	public String getInvoiceItemId() {
		return invoiceItemId;
	}

	public void setInvoiceItemId(String invoiceItemId) {
		this.invoiceItemId = invoiceItemId;
	}

	public String getInvoiceItem() {
		return invoiceItem;
	}

	public void setInvoiceItem(String invoiceItem) {
		this.invoiceItem = invoiceItem;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	

}
