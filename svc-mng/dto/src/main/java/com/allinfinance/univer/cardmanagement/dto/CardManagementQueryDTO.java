package com.allinfinance.univer.cardmanagement.dto;

import java.util.Date;

import com.allinfinance.framework.dto.PageQueryDTO;

public class CardManagementQueryDTO extends PageQueryDTO{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long cardManagementId;
	private String cardNo;
	private String starDate;
	private String enDate;
	private Integer nstarDate;
	private Integer nEnDate;
	private Integer operationType;
	private String check;
	private String idNo;
	private String customerName;
	private String cardholderName;
	private String firstName;
	private String userName;
	private Date txnDate;
	private Integer showFlag;
	private String memEmail;
	private String memName;
	private String dyPwdAuth;
	private String comName;
	private Short monLev;
	private Short cardType;
	private Short prodType;
	private String mobile;
	private Long customerId;
	private Integer cardFlag;
	private String isBo;
	private String txnType;
	private String payChnl;
	
	public String getPayChnl() {
		return payChnl;
	}
	public void setPayChnl(String payChnl) {
		this.payChnl = payChnl;
	}
	public String getTxnType() {
		return txnType;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	public Integer getnEnDate() {
		return nEnDate;
	}
	public void setnEnDate(Integer nEnDate) {
		this.nEnDate = nEnDate;
	}
	
	public Integer getCardFlag() {
		return cardFlag;
	}
	public void setCardFlag(Integer cardFlag) {
		this.cardFlag = cardFlag;
	}
	public String getIsBo() {
		return isBo;
	}
	public void setIsBo(String isBo) {
		this.isBo = isBo;
	}
	public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public Short getCardType() {
		return cardType;
	}
	public void setCardType(Short cardType) {
		this.cardType = cardType;
	}
	public Short getProdType() {
		return prodType;
	}
	public void setProdType(Short prodType) {
		this.prodType = prodType;
	}
	public Short getMonLev() {
		return monLev;
	}
	public void setMonLev(Short monLev) {
		this.monLev = monLev;
	}
	public String getMemEmail() {
		return memEmail;
	}
	public void setMemEmail(String memEmail) {
		this.memEmail = memEmail;
	}
	public String getMemName() {
		return memName;
	}
	public void setMemName(String memName) {
		this.memName = memName;
	}
	

	public String getDyPwdAuth() {
		return dyPwdAuth;
	}
	public void setDyPwdAuth(String dyPwdAuth) {
		this.dyPwdAuth = dyPwdAuth;
	}
	public String getComName() {
		return comName;
	}
	public void setComName(String comName) {
		this.comName = comName;
	}
	public Date getTxnDate() {
		return txnDate;
	}
	public Integer getShowFlag() {
		return showFlag;
	}
	public void setShowFlag(Integer showFlag) {
		this.showFlag = showFlag;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCardholderName() {
		return cardholderName;
	}
	public void setCardholderName(String cardholderName) {
		this.cardholderName = cardholderName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getCheck() {
		return check;
	}
	public void setCheck(String check) {
		this.check = check;
	}
	public Integer getOperationType() {
		return operationType;
	}
	public void setOperationType(Integer operationType) {
		this.operationType = operationType;
	}
	public Long getCardManagementId() {
		return cardManagementId;
	}
	public void setCardManagementId(Long cardManagementId) {
		this.cardManagementId = cardManagementId;
	}
	
	public String getStarDate() {
		return starDate;
	}
	public void setStarDate(String starDate) {
		this.starDate = starDate;
	}
	public String getEnDate() {
		return enDate;
	}
	public void setEnDate(String enDate) {
		this.enDate = enDate;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public Integer getNstarDate() {
		return nstarDate;
	}
	public void setNstarDate(Integer nstarDate) {
		this.nstarDate = nstarDate;
	}
	public Integer getNEnDate() {
		return nEnDate;
	}
	public void setNEnDate(Integer enDate) {
		nEnDate = enDate;
	}
	public void setTxnDate(Date txnDate) {
		this.txnDate = txnDate;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	

}
