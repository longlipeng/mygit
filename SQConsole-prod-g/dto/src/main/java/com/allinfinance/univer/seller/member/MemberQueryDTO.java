package com.allinfinance.univer.seller.member;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.allinfinance.framework.dto.PageQueryDTO;

public class MemberQueryDTO extends PageQueryDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 336804941234569329L;

	private String userId;

    private String nickNm;

    private String realNm;

    private String loginPwd;

    private String email;

    private String address;

    private String zipCd;

    private String sex;

    private String birthday;

    private String certTp;

    private String certNo;

    private String mobile;
    private String cardNo;
    
	private String corpNm;

    private String corpAddr;

    private String corpZipCd;

    private String corpTel;

    private String rcvIn;

    private String userSt;

    private String emailSt;

    private String mobileSt;

    private String certSt;

    private String regCardTp;
	private String loginChnl;


    private BigDecimal failLoginNum;

    private String failLoginSumDt;

    private String successLoginIp;

    private String lastSuccessLoginTms;

    private String failLoginIp;

    private String lastFailLoginTms;

    private String lastLogoutTms;

    private String fld01ResData;

    private String fld02ResData;

    private String recUpdOperId;

    private String recUpdTransId;

    private String recCrtTs;

    private Date recUpdTs;
    private String entityId;
    
	private String birYear;
	private String birMonth;
	private String birDay;

	private String comPhoneSection;
	private String comPhoneNo;
	private String sexDiffer;
	private String cardValidityPeriod;
	private String productNm;
	private String totalBalance;
	private String balance;
	
	private String bindCardIs;
	
	public String isBindCardIs() {
		return bindCardIs;
	}

	public void setBindCardIs(String bindCardIs) {
		this.bindCardIs = bindCardIs;
	}
	public List<MemberQueryDTO> getMemberQueryDTOs() {
		return memberQueryDTOs;
	}

	public void setMemberQueryDTOs(List<MemberQueryDTO> memberQueryDTOs) {
		this.memberQueryDTOs = memberQueryDTOs;
	}

	private String congeal;
	private List<MemberQueryDTO> memberQueryDTOs;
    public String getCardValidityPeriod() {
		return cardValidityPeriod;
	}

	public void setCardValidityPeriod(String cardValidityPeriod) {
		this.cardValidityPeriod = cardValidityPeriod;
	}

	public String getProductNm() {
		return productNm;
	}

	public void setProductNm(String productNm) {
		this.productNm = productNm;
	}

	public String getTotalBalance() {
		return totalBalance;
	}

	public void setTotalBalance(String totalBalance) {
		this.totalBalance = totalBalance;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getCongeal() {
		return congeal;
	}

	public void setCongeal(String congeal) {
		this.congeal = congeal;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getBirYear() {
		return birYear;
	}

	public void setBirYear(String birYear) {
		this.birYear = birYear;
	}

	public String getBirMonth() {
		return birMonth;
	}

	public void setBirMonth(String birMonth) {
		this.birMonth = birMonth;
	}

	public String getBirDay() {
		return birDay;
	}

	public void setBirDay(String birDay) {
		this.birDay = birDay;
	}

	public String getComPhoneSection() {
		return comPhoneSection;
	}

	public void setComPhoneSection(String comPhoneSection) {
		this.comPhoneSection = comPhoneSection;
	}

	public String getComPhoneNo() {
		return comPhoneNo;
	}

	public void setComPhoneNo(String comPhoneNo) {
		this.comPhoneNo = comPhoneNo;
	}

	public String getSexDiffer() {
		return sexDiffer;
	}

	public void setSexDiffer(String sexDiffer) {
		this.sexDiffer = sexDiffer;
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
   
    public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNickNm() {
		return nickNm;
	}

	public void setNickNm(String nickNm) {
		this.nickNm = nickNm;
	}

	public String getRealNm() {
		return realNm;
	}

	public void setRealNm(String realNm) {
		this.realNm = realNm;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCd() {
		return zipCd;
	}

	public void setZipCd(String zipCd) {
		this.zipCd = zipCd;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getCertTp() {
		return certTp;
	}

	public void setCertTp(String certTp) {
		this.certTp = certTp;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCorpNm() {
		return corpNm;
	}

	public void setCorpNm(String corpNm) {
		this.corpNm = corpNm;
	}

	public String getCorpAddr() {
		return corpAddr;
	}

	public void setCorpAddr(String corpAddr) {
		this.corpAddr = corpAddr;
	}

	public String getCorpZipCd() {
		return corpZipCd;
	}

	public void setCorpZipCd(String corpZipCd) {
		this.corpZipCd = corpZipCd;
	}

	public String getCorpTel() {
		return corpTel;
	}

	public void setCorpTel(String corpTel) {
		this.corpTel = corpTel;
	}

	public String getRcvIn() {
		return rcvIn;
	}

	public void setRcvIn(String rcvIn) {
		this.rcvIn = rcvIn;
	}

	public String getUserSt() {
		return userSt;
	}

	public void setUserSt(String userSt) {
		this.userSt = userSt;
	}

	public String getEmailSt() {
		return emailSt;
	}

	public void setEmailSt(String emailSt) {
		this.emailSt = emailSt;
	}

	public String getMobileSt() {
		return mobileSt;
	}

	public void setMobileSt(String mobileSt) {
		this.mobileSt = mobileSt;
	}

	public String getCertSt() {
		return certSt;
	}

	public void setCertSt(String certSt) {
		this.certSt = certSt;
	}

	public String getRegCardTp() {
		return regCardTp;
	}

	public void setRegCardTp(String regCardTp) {
		this.regCardTp = regCardTp;
	}

	public String getLoginChnl() {
		return loginChnl;
	}

	public void setLoginChnl(String loginChnl) {
		this.loginChnl = loginChnl;
	}

	public BigDecimal getFailLoginNum() {
		return failLoginNum;
	}

	public void setFailLoginNum(BigDecimal failLoginNum) {
		this.failLoginNum = failLoginNum;
	}

	public String getFailLoginSumDt() {
		return failLoginSumDt;
	}

	public void setFailLoginSumDt(String failLoginSumDt) {
		this.failLoginSumDt = failLoginSumDt;
	}

	public String getSuccessLoginIp() {
		return successLoginIp;
	}

	public void setSuccessLoginIp(String successLoginIp) {
		this.successLoginIp = successLoginIp;
	}

	public String getLastSuccessLoginTms() {
		return lastSuccessLoginTms;
	}

	public void setLastSuccessLoginTms(String lastSuccessLoginTms) {
		this.lastSuccessLoginTms = lastSuccessLoginTms;
	}

	public String getFailLoginIp() {
		return failLoginIp;
	}

	public void setFailLoginIp(String failLoginIp) {
		this.failLoginIp = failLoginIp;
	}

	public String getLastFailLoginTms() {
		return lastFailLoginTms;
	}

	public void setLastFailLoginTms(String lastFailLoginTms) {
		this.lastFailLoginTms = lastFailLoginTms;
	}

	public String getLastLogoutTms() {
		return lastLogoutTms;
	}

	public void setLastLogoutTms(String lastLogoutTms) {
		this.lastLogoutTms = lastLogoutTms;
	}

	public String getFld01ResData() {
		return fld01ResData;
	}

	public void setFld01ResData(String fld01ResData) {
		this.fld01ResData = fld01ResData;
	}

	public String getFld02ResData() {
		return fld02ResData;
	}

	public void setFld02ResData(String fld02ResData) {
		this.fld02ResData = fld02ResData;
	}

	public String getRecUpdOperId() {
		return recUpdOperId;
	}

	public void setRecUpdOperId(String recUpdOperId) {
		this.recUpdOperId = recUpdOperId;
	}

	public String getRecUpdTransId() {
		return recUpdTransId;
	}

	public void setRecUpdTransId(String recUpdTransId) {
		this.recUpdTransId = recUpdTransId;
	}

	public String getRecCrtTs() {
		return recCrtTs;
	}

	public void setRecCrtTs(String recCrtTs) {
		this.recCrtTs = recCrtTs;
	}

	public Date getRecUpdTs() {
		return recUpdTs;
	}

	public void setRecUpdTs(Date recUpdTs) {
		this.recUpdTs = recUpdTs;
	}

		

}
