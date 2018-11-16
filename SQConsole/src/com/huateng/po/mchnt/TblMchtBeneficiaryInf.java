package com.huateng.po.mchnt;

import java.io.Serializable;

/**
 * 受益人正式表
 * @author Administrator
 *
 */
public class TblMchtBeneficiaryInf implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String mchtNo;    //商户编号
	
	private String beneficiaryId;   //受益人id
	private String beneficiaryName;   //受益人姓名
	private String beneficiaryAddress;   //受益人地址
	private String beneficiaryIdType;    //身份证件类型
	private String beneficiaryIdCard;   //身份证号
	private String beneficiaryExpiration;  //有效期限

	
	public String getMchtNo() {
		return mchtNo;
	}
	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}
	public String getBeneficiaryId() {
		return beneficiaryId;
	}
	public void setBeneficiaryId(String beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}
	public String getBeneficiaryName() {
		return beneficiaryName;
	}
	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}
	public String getBeneficiaryAddress() {
		return beneficiaryAddress;
	}
	public void setBeneficiaryAddress(String beneficiaryAddress) {
		this.beneficiaryAddress = beneficiaryAddress;
	}
	public String getBeneficiaryIdType() {
		return beneficiaryIdType;
	}
	public void setBeneficiaryIdType(String beneficiaryIdType) {
		this.beneficiaryIdType = beneficiaryIdType;
	}
	public String getBeneficiaryIdCard() {
		return beneficiaryIdCard;
	}
	public void setBeneficiaryIdCard(String beneficiaryIdCard) {
		this.beneficiaryIdCard = beneficiaryIdCard;
	}
	public String getBeneficiaryExpiration() {
		return beneficiaryExpiration;
	}
	public void setBeneficiaryExpiration(String beneficiaryExpiration) {
		this.beneficiaryExpiration = beneficiaryExpiration;
	}
	
	
}
