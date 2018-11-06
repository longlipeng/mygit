package com.allinfinance.univer.consumer.pos.dto;

import com.allinfinance.framework.dto.PageQueryDTO;

public class PosParameterQueryDTO extends PageQueryDTO{
	 private Integer prmId;
	 private String prmName;
	 private Integer prmVersion;
	 private Integer prmType;
	 private Short prmStat;
	 private String prmVal;
    public Integer getPrmId() {
        return prmId;
    }
    public void setPrmId(Integer prmId) {
        this.prmId = prmId;
    }
    public String getPrmName() {
        return prmName;
    }
    public void setPrmName(String prmName) {
        this.prmName = prmName;
    }
    public Integer getPrmVersion() {
        return prmVersion;
    }
    public void setPrmVersion(Integer prmVersion) {
        this.prmVersion = prmVersion;
    }
    public Integer getPrmType() {
        return prmType;
    }
    public void setPrmType(Integer prmType) {
        this.prmType = prmType;
    }
    public Short getPrmStat() {
        return prmStat;
    }
    public void setPrmStat(Short prmStat) {
        this.prmStat = prmStat;
    }
    public String getPrmVal() {
        return prmVal;
    }
    public void setPrmVal(String prmVal) {
        this.prmVal = prmVal;
    }
	
	
}
