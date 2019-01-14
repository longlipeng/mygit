package com.allinfinance.univer.system.dictinfo.dto;

import com.allinfinance.framework.dto.PageQueryDTO;

/**
 * 实体系统参数
 * 
 * @author xxl
 * 
 */
public class EntityDictInfoQueryDTO extends PageQueryDTO {

	private static final long serialVersionUID = 6325943718019020217L;
	private String dictId;
	private String dictCode;
	private String dictTypeCode;
	private String entityId;
	private String fatherEntityId;
	private String dictName;
	private String dictShortName;
	private String dictEnglishName;
	private String dictState;
	private String fatherDictCode;

	private String dictTypeName;

	private Short updateFlag;

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

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getFatherEntityId() {
		return fatherEntityId;
	}

	public void setFatherEntityId(String fatherEntityId) {
		this.fatherEntityId = fatherEntityId;
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

	public String getDictEnglishName() {
		return dictEnglishName;
	}

	public void setDictEnglishName(String dictEnglishName) {
		this.dictEnglishName = dictEnglishName;
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

	public String getDictTypeName() {
		return dictTypeName;
	}

	public void setDictTypeName(String dictTypeName) {
		this.dictTypeName = dictTypeName;
	}

	public Short getUpdateFlag() {
		return updateFlag;
	}

	public void setUpdateFlag(Short updateFlag) {
		this.updateFlag = updateFlag;
	}
	

}
