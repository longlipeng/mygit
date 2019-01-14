package com.allinfinance.univer.system.dictinfo.dto;

import com.allinfinance.framework.dto.BaseDTO;

/**
 * 实体系统参数
 * 
 * @author xxl
 * 
 */
public class EntityDictInfoDTO extends BaseDTO {

	private static final long serialVersionUID = 8690883456461310576L;

	/**
	 * 字典ID
	 */
	private String dictId;
	private String dictCode;
	/**
	 * 字典类型
	 */
	private String dictTypeCode;
	/**
	 * 字典名称
	 */
	private String dictName;
	/**
	 * 字典短名称
	 */
	private String dictShortName;
	/**
	 * 字典状态 1表示有效 0表示失效
	 */
	private String dictState;

	private String fatherDictCode;

	private String fatherDictIdStr;

	private String dictEnglishName;

	private Short updateFlag;

	private String[] delStr;

	private String dictTypeName;

	private String shopfatherDictId;

	private String cityfatherDictId;

	private String fatherDictName;

	public String getDictId() {
		return dictId;
	}

	public void setDictId(String dictId) {
		this.dictId = dictId;
	}

	public String getDictCode() {
		return dictCode;
	}

	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}

	public String getDictTypeCode() {
		return dictTypeCode;
	}

	public void setDictTypeCode(String dictTypeCode) {
		this.dictTypeCode = dictTypeCode;
	}

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	public String getDictShortName() {
		return dictShortName;
	}

	public void setDictShortName(String dictShortName) {
		this.dictShortName = dictShortName;
	}

	public String getDictState() {
		return dictState;
	}

	public void setDictState(String dictState) {
		this.dictState = dictState;
	}

	public String getFatherDictCode() {
		return fatherDictCode;
	}

	public void setFatherDictCode(String fatherDictCode) {
		this.fatherDictCode = fatherDictCode;
	}

	public String getFatherDictIdStr() {
		return fatherDictIdStr;
	}

	public void setFatherDictIdStr(String fatherDictIdStr) {
		this.fatherDictIdStr = fatherDictIdStr;
	}

	public String getDictEnglishName() {
		return dictEnglishName;
	}

	public void setDictEnglishName(String dictEnglishName) {
		this.dictEnglishName = dictEnglishName;
	}

	public Short getUpdateFlag() {
		return updateFlag;
	}

	public void setUpdateFlag(Short updateFlag) {
		this.updateFlag = updateFlag;
	}

	public String[] getDelStr() {
		return delStr;
	}

	public void setDelStr(String[] delStr) {
		this.delStr = delStr;
	}

	public String getDictTypeName() {
		return dictTypeName;
	}

	public void setDictTypeName(String dictTypeName) {
		this.dictTypeName = dictTypeName;
	}

	public String getShopfatherDictId() {
		return shopfatherDictId;
	}

	public void setShopfatherDictId(String shopfatherDictId) {
		this.shopfatherDictId = shopfatherDictId;
	}

	public String getCityfatherDictId() {
		return cityfatherDictId;
	}

	public void setCityfatherDictId(String cityfatherDictId) {
		this.cityfatherDictId = cityfatherDictId;
	}

	public String getFatherDictName() {
		return fatherDictName;
	}

	public void setFatherDictName(String fatherDictName) {
		this.fatherDictName = fatherDictName;
	}

}
