package com.allinfinance.univer.issuer.dto.consumer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.allinfinance.framework.dto.BaseDTO;
import com.allinfinance.univer.entity.dto.BankDTO;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;

public class AcqPayDTO extends BaseDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7701284397132047601L;
	private String seqId;
	private String entityId;
	private String consumerName;
	private String productId;
	private List<BankDTO> banks = new ArrayList<BankDTO>();
	private String bank;
	private String mchantCode;
	private String certs;
	private String certsPwd;
	private String sms;
	private String logo;
	private String copyright;
	private String insNm;
	private String domainUrl;
	private List<ProductDTO> productDTOs=new ArrayList<ProductDTO>();
	private String crtTs;
	private String updTs;
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	public String getConsumerName() {
		return consumerName;
	}
	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}
	
	public List<BankDTO> getBanks() {
		return banks;
	}
	public void setBanks(List<BankDTO> banks) {
		this.banks = banks;
	}
	public String getMchantCode() {
		return mchantCode;
	}
	public void setMchantCode(String mchantCode) {
		this.mchantCode = mchantCode;
	}
	public String getCerts() {
		return certs;
	}
	public void setCerts(String certs) {
		this.certs = certs;
	}
	public String getCertsPwd() {
		return certsPwd;
	}
	public void setCertsPwd(String certsPwd) {
		this.certsPwd = certsPwd;
	}
	public String getSms() {
		return sms;
	}
	public void setSms(String sms) {
		this.sms = sms;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getCopyright() {
		return copyright;
	}
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}
	public List<ProductDTO> getProductDTOs() {
		return productDTOs;
	}
	public void setProductDTOs(List<ProductDTO> productDTOs) {
		this.productDTOs = productDTOs;
	}
	
	public String getSeqId() {
		return seqId;
	}
	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getCrtTs() {
		return crtTs;
	}
	public void setCrtTs(String crtTs) {
		this.crtTs = crtTs;
	}
	public String getUpdTs() {
		return updTs;
	}
	public void setUpdTs(String updTs) {
		this.updTs = updTs;
	}
	public String getDomainUrl() {
		return domainUrl;
	}
	public void setDomainUrl(String domainUrl) {
		this.domainUrl = domainUrl;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getInsNm() {
		return insNm;
	}
	public void setInsNm(String insNm) {
		this.insNm = insNm;
	}
	
	

}
