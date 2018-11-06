package com.allinfinance.framework.dto;

public class JudgeInforDTO extends BaseDTO {
	private String entityID;
	private String CName;// 中文名
	private String EName;// 英文名
	private String birthday;// 生日
	private String Gender;// 性别
	private String Country;//
	// 证件类型
	private String certID;
	/**
	 * 用户类型 1:购卡人 2:持卡人
	 */
	private String userType;
	/**
	 * 校验类型 5:黑名单校验4:风险等级校验
	 */
	private String judgeType;
	private String CNum;
	/*
	 * 1:个人
	 * 2:企业
	 */
	private String customerType;
		


    public String getCustomerType() {
        return customerType;
    }


    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }


    public JudgeInforDTO() {
	}


	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getJudgeType() {
		return judgeType;
	}

	public void setJudgeType(String judgeType) {
		this.judgeType = judgeType;
	}

	public String getCName() {
		return CName;
	}

	public void setCName(String cName) {
		CName = cName;
	}

	public String getEName() {
		return EName;
	}

	public void setEName(String eName) {
		EName = eName;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		Gender = gender;
	}

	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		Country = country;
	}

	public String getCertID() {
		return certID;
	}

	public void setCertID(String certID) {
		this.certID = certID;
	}

	public String getCNum() {
		return CNum;
	}

	public void setCNum(String cNum) {
		CNum = cNum;
	}

	public String getEntityID() {
		return entityID;
	}

	public void setEntityID(String entityID) {
		this.entityID = entityID;
	}

}
