package com.allinfinance.univer.system.dictinfo.dto;

import com.allinfinance.framework.dto.PageQueryDTO;

public class DictInfoQueryDTO extends PageQueryDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String dictId;

	private String dictCode;

	private String dictTypeCode;

	private String dictName;

	private String dictShortName;

	private String dictEnglishName;

	private String dictTypeName;

	private Short updateFlag;

	public String getDictId() {
		return dictId;
	}

	public void setDictId(String dictId) {
		this.dictId = dictId;
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

	public Short getUpdateFlag() {
		return updateFlag;
	}

	public void setUpdateFlag(Short updateFlag) {
		this.updateFlag = updateFlag;
	}

	public String getDictEnglishName() {
		return dictEnglishName;
	}

	public void setDictEnglishName(String dictEnglishName) {
		this.dictEnglishName = dictEnglishName;
	}

	public String getDictTypeName() {
		return dictTypeName;
	}

	public void setDictTypeName(String dictTypeName) {
		this.dictTypeName = dictTypeName;
	}

	public String getDictCode() {
		return dictCode;
	}

	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}

}
