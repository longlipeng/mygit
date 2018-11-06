package com.allinfinance.svc.coupon.dto;

public class SettlementInvoiceDto {

	/**
     * 结算单ID
     */
    private Long id;
    /**
     * 结算单号
     */
    private String settleNo;
    /**
     * 商户号
     */
    private String mchtEntityId;
    /**
     * 商户名称
     */
    private String merchantName;
    /**
     * 客户号
     */
    private String customerEntityId;
    /**
     * 发票名称
     */
    private String name;
    /**
     * 发票金额
     */
    private String amount;
    /**
     * 开票方
     */
    private String developer;
    /**
     * 收票方
     */
    private String receiver;
    /**
     * 纳税人识别号
     */
    private String taxCode;
    /**
     * 地址
     */
    private String regAddress;
    /**
     * 电话
     */
    private String addressTel;
    /**
     * 开户行
     */
    private String branchBankName;
    /**
     * 账户
     */
    private String bankAcctCode;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSettleNo() {
		return settleNo;
	}
	public void setSettleNo(String settleNo) {
		this.settleNo = settleNo;
	}
	public String getMchtEntityId() {
		return mchtEntityId;
	}
	public void setMchtEntityId(String mchtEntityId) {
		this.mchtEntityId = mchtEntityId;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getCustomerEntityId() {
		return customerEntityId;
	}
	public void setCustomerEntityId(String customerEntityId) {
		this.customerEntityId = customerEntityId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getDeveloper() {
		return developer;
	}
	public void setDeveloper(String developer) {
		this.developer = developer;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getTaxCode() {
		return taxCode;
	}
	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}
	public String getRegAddress() {
		return regAddress;
	}
	public void setRegAddress(String regAddress) {
		this.regAddress = regAddress;
	}
	public String getAddressTel() {
		return addressTel;
	}
	public void setAddressTel(String addressTel) {
		this.addressTel = addressTel;
	}
	public String getBranchBankName() {
		return branchBankName;
	}
	public void setBranchBankName(String branchBankName) {
		this.branchBankName = branchBankName;
	}
	public String getBankAcctCode() {
		return bankAcctCode;
	}
	public void setBankAcctCode(String bankAcctCode) {
		this.bankAcctCode = bankAcctCode;
	}
    
}
