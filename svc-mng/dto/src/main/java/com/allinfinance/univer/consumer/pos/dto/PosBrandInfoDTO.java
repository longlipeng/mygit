package com.allinfinance.univer.consumer.pos.dto;

import com.allinfinance.framework.dto.BaseDTO;

public class PosBrandInfoDTO extends BaseDTO{
	 private Integer termBrandId;
	 private String brandName;
	 private String tmkIndex;
	 private String tmkKey;
	 private String insIdCd;
	public Integer getTermBrandId() {
		return termBrandId;
	}
	public void setTermBrandId(Integer termBrandId) {
		this.termBrandId = termBrandId;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getTmkIndex() {
		return tmkIndex;
	}
	public void setTmkIndex(String tmkIndex) {
		this.tmkIndex = tmkIndex;
	}
	public String getTmkKey() {
		return tmkKey;
	}
	public void setTmkKey(String tmkKey) {
		this.tmkKey = tmkKey;
	}
	public String getInsIdCd() {
		return insIdCd;
	}
	public void setInsIdCd(String insIdCd) {
		this.insIdCd = insIdCd;
	}
	 
	 
	 
}
