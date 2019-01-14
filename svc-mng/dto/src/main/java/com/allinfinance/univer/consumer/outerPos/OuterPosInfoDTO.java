package com.allinfinance.univer.consumer.outerPos;

public class OuterPosInfoDTO {
	private String termId;

	private String mchntId;
	private String oldMchntId;
	private String mchntName;

	private String termStat;

	private String termOwner;
	private String shopId;

	private String shopName;

	private String  termTransFlag;

	private String termBrandId;
	private String  termBrandName;
	private String termModel;
	public String getOldMchntId() {
		return oldMchntId;
	}

	public void setOldMchntId(String oldMchntId) {
		this.oldMchntId = oldMchntId;
	}

	public String getTermModel() {
		return termModel;
	}

	public void setTermModel(String termModel) {
		this.termModel = termModel;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getTermTransFlag() {
		return termTransFlag;
	}

	public void setTermTransFlag(String termTransFlag) {
		this.termTransFlag = termTransFlag;
	}

	public String getTermBrandId() {
		return termBrandId;
	}

	public void setTermBrandId(String termBrandId) {
		this.termBrandId = termBrandId;
	}

	public String getTermBrandName() {
		return termBrandName;
	}

	public void setTermBrandName(String termBrandName) {
		this.termBrandName = termBrandName;
	}

	public String getTermId() {
		return termId;
	}

	public void setTermId(String termId) {
		this.termId = termId;
	}

	public String getMchntId() {
		return mchntId;
	}

	public void setMchntId(String mchntId) {
		this.mchntId = mchntId;
	}

	public String getMchntName() {
		return mchntName;
	}

	public void setMchntName(String mchntName) {
		this.mchntName = mchntName;
	}

	public String getTermStat() {
		return termStat;
	}

	public void setTermStat(String termStat) {
		this.termStat = termStat;
	}

	public String getTermOwner() {
		return termOwner;
	}

	public void setTermOwner(String termOwner) {
		this.termOwner = termOwner;
	}
}
