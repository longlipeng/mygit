package com.allinfinance.univer.consumer.pos.dto;

import com.allinfinance.framework.dto.BaseDTO;

public class PosParameterDTO extends BaseDTO {
    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    private Integer prmId;
    private Integer prmType;
    private Integer prmVersion;
    private Short prmStat;
    private String prmName;
    private String prmDesc;
    private String prmVal;
    private Short prmLen;
    private String rsvd1;
    private String rsvd2;
    private int prmVersionInt;
    private String consumerId;
    private String cardLength;

    public String getCardLength() {
        return cardLength;
    }

    public void setCardLength(String cardLength) {
        this.cardLength = cardLength;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

    public Short getPrmStat() {
        return prmStat;
    }

    public void setPrmStat(Short prmStat) {
        this.prmStat = prmStat;
    }

    public String getPrmName() {
        return prmName;
    }

    public void setPrmName(String prmName) {
        this.prmName = prmName;
    }

    public String getPrmDesc() {
        return prmDesc;
    }

    public void setPrmDesc(String prmDesc) {
        this.prmDesc = prmDesc;
    }

    public String getPrmVal() {
        return prmVal;
    }

    public void setPrmVal(String prmVal) {
        this.prmVal = prmVal;
    }

    public Short getPrmLen() {
        return prmLen;
    }

    public void setPrmLen(Short prmLen) {
        this.prmLen = prmLen;
    }

    public String getRsvd1() {
        return rsvd1;
    }

    public void setRsvd1(String rsvd1) {
        this.rsvd1 = rsvd1;
    }

    public String getRsvd2() {
        return rsvd2;
    }

    public void setRsvd2(String rsvd2) {
        this.rsvd2 = rsvd2;
    }

    public Integer getPrmId() {
        return prmId;
    }

    public void setPrmId(Integer prmId) {
        this.prmId = prmId;
    }

    public Integer getPrmType() {
        return prmType;
    }

    public void setPrmType(Integer prmType) {
        this.prmType = prmType;
    }

    public Integer getPrmVersion() {
        return prmVersion;
    }

    public void setPrmVersion(Integer prmVersion) {
        this.prmVersion = prmVersion;
    }

    public int getPrmVersionInt() {
        return prmVersionInt;
    }

    public void setPrmVersionInt(int prmVersionInt) {
        this.prmVersionInt = prmVersionInt;
    }

}
