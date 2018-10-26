package com.allinfinance.model;

public class TbDictInfo {
    private String dictId;

    private String dictCode;

    private String dictTypeCode;

    private String dictName;

    private String dictShortName;

    private String dictEnglishName;

    private String dictState;

    private String fatherDictCode;

    public String getDictId() {
        return dictId;
    }

    public void setDictId(String dictId) {
        this.dictId = dictId == null ? null : dictId.trim();
    }

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode == null ? null : dictCode.trim();
    }

    public String getDictTypeCode() {
        return dictTypeCode;
    }

    public void setDictTypeCode(String dictTypeCode) {
        this.dictTypeCode = dictTypeCode == null ? null : dictTypeCode.trim();
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName == null ? null : dictName.trim();
    }

    public String getDictShortName() {
        return dictShortName;
    }

    public void setDictShortName(String dictShortName) {
        this.dictShortName = dictShortName == null ? null : dictShortName.trim();
    }

    public String getDictEnglishName() {
        return dictEnglishName;
    }

    public void setDictEnglishName(String dictEnglishName) {
        this.dictEnglishName = dictEnglishName == null ? null : dictEnglishName.trim();
    }

    public String getDictState() {
        return dictState;
    }

    public void setDictState(String dictState) {
        this.dictState = dictState == null ? null : dictState.trim();
    }

    public String getFatherDictCode() {
        return fatherDictCode;
    }

    public void setFatherDictCode(String fatherDictCode) {
        this.fatherDictCode = fatherDictCode == null ? null : fatherDictCode.trim();
    }
}