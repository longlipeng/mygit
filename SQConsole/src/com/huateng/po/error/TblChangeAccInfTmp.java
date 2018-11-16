package com.huateng.po.error;

/**
 * TblChangeAccInfTmp entity. @author MyEclipse Persistence Tools
 */

public class TblChangeAccInfTmp implements java.io.Serializable {

	// Fields

	private TblChangeAccInfTmpId id;
	private Double changeAccount;
	private String changeReason;
	private String changeFlag;
	private Double comfirmAccount;
	private String insOpr;
	private String insTs;
	private String updOpr;
	private String updTs;
	private String aprOpr;
	private String aprTs;
	private String st;
	private String enterTp;
	private String instCode;
	private String channelId;
	private String onlineFlag;

	// Constructors

	/** default constructor */
	public TblChangeAccInfTmp() {
	}

	/** minimal constructor */
	public TblChangeAccInfTmp(TblChangeAccInfTmpId id) {
		this.id = id;
	}

	/** full constructor */
	public TblChangeAccInfTmp(TblChangeAccInfTmpId id, Double changeAccount,
			String changeReason, String changeFlag, Double comfirmAccount,
			String insOpr, String insTs, String updOpr, String updTs,
			String aprOpr, String aprTs, String st,String enterTp,String instCode,String channelId,String onlineFlag) {
		this.id = id;
		this.changeAccount = changeAccount;
		this.changeReason = changeReason;
		this.changeFlag = changeFlag;
		this.comfirmAccount = comfirmAccount;
		this.insOpr = insOpr;
		this.insTs = insTs;
		this.updOpr = updOpr;
		this.updTs = updTs;
		this.aprOpr = aprOpr;
		this.aprTs = aprTs;
		this.st = st;
		this.enterTp = enterTp;
		this.instCode = instCode;
		this.channelId = channelId;
		this.onlineFlag = onlineFlag;
	}

	// Property accessors

	public TblChangeAccInfTmpId getId() {
		return this.id;
	}

	public void setId(TblChangeAccInfTmpId id) {
		this.id = id;
	}

	public Double getChangeAccount() {
		return this.changeAccount;
	}

	public void setChangeAccount(Double changeAccount) {
		this.changeAccount = changeAccount;
	}

	public String getChangeReason() {
		return this.changeReason;
	}

	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}

	public String getChangeFlag() {
		return this.changeFlag;
	}

	public void setChangeFlag(String changeFlag) {
		this.changeFlag = changeFlag;
	}

	public Double getComfirmAccount() {
		return this.comfirmAccount;
	}

	public void setComfirmAccount(Double comfirmAccount) {
		this.comfirmAccount = comfirmAccount;
	}

	public String getInsOpr() {
		return this.insOpr;
	}

	public void setInsOpr(String insOpr) {
		this.insOpr = insOpr;
	}

	public String getInsTs() {
		return this.insTs;
	}

	public void setInsTs(String insTs) {
		this.insTs = insTs;
	}

	public String getUpdOpr() {
		return this.updOpr;
	}

	public void setUpdOpr(String updOpr) {
		this.updOpr = updOpr;
	}

	public String getUpdTs() {
		return this.updTs;
	}

	public void setUpdTs(String updTs) {
		this.updTs = updTs;
	}

	public String getAprOpr() {
		return this.aprOpr;
	}

	public void setAprOpr(String aprOpr) {
		this.aprOpr = aprOpr;
	}

	public String getAprTs() {
		return this.aprTs;
	}

	public void setAprTs(String aprTs) {
		this.aprTs = aprTs;
	}

	public String getSt() {
		return this.st;
	}

	public void setSt(String st) {
		this.st = st;
	}

	public String getEnterTp() {
		return enterTp;
	}

	public void setEnterTp(String enterTp) {
		this.enterTp = enterTp;
	}

	public String getInstCode() {
		return instCode;
	}

	public void setInstCode(String instCode) {
		this.instCode = instCode;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getOnlineFlag() {
		return onlineFlag;
	}

	public void setOnlineFlag(String onlineFlag) {
		this.onlineFlag = onlineFlag;
	}
	
}