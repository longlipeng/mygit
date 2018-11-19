package com.allinfinance.univer.consumer.pos.dto;

import java.util.List;

import com.allinfinance.framework.dto.BaseDTO;

public class PosInfoDTO extends BaseDTO implements java.io.Serializable {
    private static final long serialVersionUID = 7066143757985035257L;
    private String id;
    private String termId;
    private String mchntEntityId;
    private String mchntId;
    private String mchntName;
    private String shopId;
    private String termSigninStat;
    private String termPik;
    private String termMak;
    private String termTmk;
    private String termBatchNo;
    private String termTransFlag;
    private Integer prmType1;
    private Integer prmVersion1;
    private String prmChangeFlag1;
    private Integer prmType2;
    private Integer prmVersion2;
    private String prmChangeFlag2;
    private Integer prmType3;
    private Integer prmVersion3;
    private String prmChangeFlag3;
    private Integer termBrandId;
    private String termModel;
    private String termOwner;
    private String consumePerFlag;
    private String reloadPerFlag;
    private String reprintCtrlFlag;
    private String currTxnCnt;
    private String maxTxnCnt;
    private String tmkDownTime;
    private String lastTxnTime;
    private String termStat;
    private String createUser;
    private String createTime;
    private String modifyUser;
    private String modifyTime;
    private String dataState;
    private String rsvd1;
    private String rsvd2;
    private String pointPerFlag;
    private String prmChangeFlag4;
    private Integer maxTxnCntInt;
    private List<String> posInfTermIds;
    private String tmkIndex;
    private String pikIndex;
    private String makIndex;
    private String shopCode;
    private String termBrandName;

    public String getTermBrandName() {
        return termBrandName;
    }

    public void setTermBrandName(String termBrandName) {
        this.termBrandName = termBrandName;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getTmkIndex() {
        return tmkIndex;
    }

    public void setTmkIndex(String tmkIndex) {
        this.tmkIndex = tmkIndex;
    }

    public String getPikIndex() {
        return pikIndex;
    }

    public void setPikIndex(String pikIndex) {
        this.pikIndex = pikIndex;
    }

    public String getMakIndex() {
        return makIndex;
    }

    public void setMakIndex(String makIndex) {
        this.makIndex = makIndex;
    }

    public String getMchntEntityId() {
        return mchntEntityId;
    }

    public void setMchntEntityId(String mchntEntityId) {
        this.mchntEntityId = mchntEntityId;
    }

    public Integer getMaxTxnCntInt() {
        return maxTxnCntInt;
    }

    public void setMaxTxnCntInt(Integer maxTxnCntInt) {
        this.maxTxnCntInt = maxTxnCntInt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getTermSigninStat() {
        return termSigninStat;
    }

    public void setTermSigninStat(String termSigninStat) {
        this.termSigninStat = termSigninStat;
    }

    public String getTermPik() {
        return termPik;
    }

    public void setTermPik(String termPik) {
        this.termPik = termPik;
    }

    public String getTermMak() {
        return termMak;
    }

    public void setTermMak(String termMak) {
        this.termMak = termMak;
    }

    public String getTermTmk() {
        return termTmk;
    }

    public void setTermTmk(String termTmk) {
        this.termTmk = termTmk;
    }

    public String getTermBatchNo() {
        return termBatchNo;
    }

    public void setTermBatchNo(String termBatchNo) {
        this.termBatchNo = termBatchNo;
    }

    public String getTermTransFlag() {
        return termTransFlag;
    }

    public void setTermTransFlag(String termTransFlag) {
        this.termTransFlag = termTransFlag;
    }

    public Integer getPrmType1() {
        return prmType1;
    }

    public void setPrmType1(Integer prmType1) {
        this.prmType1 = prmType1;
    }

    public Integer getPrmVersion1() {
        return prmVersion1;
    }

    public void setPrmVersion1(Integer prmVersion1) {
        this.prmVersion1 = prmVersion1;
    }

    public String getPrmChangeFlag1() {
        return prmChangeFlag1;
    }

    public void setPrmChangeFlag1(String prmChangeFlag1) {
        this.prmChangeFlag1 = prmChangeFlag1;
    }

    public Integer getPrmType2() {
        return prmType2;
    }

    public void setPrmType2(Integer prmType2) {
        this.prmType2 = prmType2;
    }

    public Integer getPrmVersion2() {
        return prmVersion2;
    }

    public void setPrmVersion2(Integer prmVersion2) {
        this.prmVersion2 = prmVersion2;
    }

    public String getPrmChangeFlag2() {
        return prmChangeFlag2;
    }

    public void setPrmChangeFlag2(String prmChangeFlag2) {
        this.prmChangeFlag2 = prmChangeFlag2;
    }

    public Integer getPrmType3() {
        return prmType3;
    }

    public void setPrmType3(Integer prmType3) {
        this.prmType3 = prmType3;
    }

    public Integer getPrmVersion3() {
        return prmVersion3;
    }

    public void setPrmVersion3(Integer prmVersion3) {
        this.prmVersion3 = prmVersion3;
    }

    public String getPrmChangeFlag3() {
        return prmChangeFlag3;
    }

    public void setPrmChangeFlag3(String prmChangeFlag3) {
        this.prmChangeFlag3 = prmChangeFlag3;
    }

    public Integer getTermBrandId() {
        return termBrandId;
    }

    public void setTermBrandId(Integer termBrandId) {
        this.termBrandId = termBrandId;
    }

    public String getTermModel() {
        return termModel;
    }

    public void setTermModel(String termModel) {
        this.termModel = termModel;
    }

    public String getTermOwner() {
        return termOwner;
    }

    public void setTermOwner(String termOwner) {
        this.termOwner = termOwner;
    }

    public String getConsumePerFlag() {
        return consumePerFlag;
    }

    public void setConsumePerFlag(String consumePerFlag) {
        this.consumePerFlag = consumePerFlag;
    }

    public String getReloadPerFlag() {
        return reloadPerFlag;
    }

    public void setReloadPerFlag(String reloadPerFlag) {
        this.reloadPerFlag = reloadPerFlag;
    }

    public String getReprintCtrlFlag() {
        return reprintCtrlFlag;
    }

    public void setReprintCtrlFlag(String reprintCtrlFlag) {
        this.reprintCtrlFlag = reprintCtrlFlag;
    }

    public String getCurrTxnCnt() {
        return currTxnCnt;
    }

    public void setCurrTxnCnt(String currTxnCnt) {
        this.currTxnCnt = currTxnCnt;
    }

    public String getMaxTxnCnt() {
        return maxTxnCnt;
    }

    public void setMaxTxnCnt(String maxTxnCnt) {
        this.maxTxnCnt = maxTxnCnt;
    }

    public String getTmkDownTime() {
        return tmkDownTime;
    }

    public void setTmkDownTime(String tmkDownTime) {
        this.tmkDownTime = tmkDownTime;
    }

    public String getLastTxnTime() {
        return lastTxnTime;
    }

    public void setLastTxnTime(String lastTxnTime) {
        this.lastTxnTime = lastTxnTime;
    }

    public String getTermStat() {
        return termStat;
    }

    public void setTermStat(String termStat) {
        this.termStat = termStat;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getDataState() {
        return dataState;
    }

    public void setDataState(String dataState) {
        this.dataState = dataState;
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

    public String getPointPerFlag() {
        return pointPerFlag;
    }

    public void setPointPerFlag(String pointPerFlag) {
        this.pointPerFlag = pointPerFlag;
    }

    public String getPrmChangeFlag4() {
        return prmChangeFlag4;
    }

    public void setPrmChangeFlag4(String prmChangeFlag4) {
        this.prmChangeFlag4 = prmChangeFlag4;
    }

    public List<String> getPosInfTermIds() {
        return posInfTermIds;
    }

    public void setPosInfTermIds(List<String> posInfTermIds) {
        this.posInfTermIds = posInfTermIds;
    }

}
