package com.allinfinance.univer.seller.blacklist.dto;

import java.util.List;

import com.allinfinance.framework.dto.PageQueryDTO;

public class BlackListBatchDTO extends PageQueryDTO{
	private String flag;// 操作类型1  人员黑名单 2 地区黑名单
	private  List<BlackListPerDTO>  personList;// 人员黑名单列表
	private List<BlackListAreaDTO>   areaList;//地区黑名单列表
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public List<BlackListPerDTO> getPersonList() {
		return personList;
	}
	public void setPersonList(List<BlackListPerDTO> personList) {
		this.personList = personList;
	}
	public List<BlackListAreaDTO> getAreaList() {
		return areaList;
	}
	public void setAreaList(List<BlackListAreaDTO> areaList) {
		this.areaList = areaList;
	}

}
