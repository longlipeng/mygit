package com.allinfinance.univer.seller.blacklist.dto;

import java.io.Serializable;

import com.allinfinance.framework.dto.PageQueryDTO;

public class BlackListAreaDTO extends PageQueryDTO {

	private static final long serialVersionUID = 1L;
	private String  areaType;//地区类型  (0:国家，1：省，2：市，3：县)
	private String  areaCode;// 地区代码
	private String  areaName;//地区名称
	public String getAreaType() {
		return areaType;
	}
	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	@Override
	public String toString() {
		return "BlackListAreaDTO [areaType=" + areaType + ", areaCode=" + areaCode + ", areaName=" + areaName + "]";
	}
	
	
	

}
