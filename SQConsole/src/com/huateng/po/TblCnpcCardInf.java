package com.huateng.po;

import java.io.Serializable;

public class TblCnpcCardInf implements Serializable {
	private static final long serialVersionUID = 1L;

	public static String REF = "TblCnpcCardInf";
	public static String CUSTOMER_PROFILE = "customerProfile";
	public static String CARD_LEVEL = "cardLevel";
	public static String CHECK_CODE = "checkCode";
	public static String SA_STATE = "saState";
	public static String CARD_BIN = "cardBin";
	public static String CARD_NO = "cardNo";
	public static String INIT_OPR_ID = "initOprId";
	public static String INIT_TIME = "initTime";
	public static String PROP_SA_ACTION_OLD = "upOprId";
	public static String UP_TIME = "upTime";
	public static String VERIFIER_OPR_ID = "verifierOprId";
	public static String VERIFIER_TIME = "verifierTime";
	public static String REMARKS = "remarks";
	
	/**
	 * 
	 */
	public TblCnpcCardInf() {
		super();
	}

	protected void initialize() {
	}

	// primary key
	private TblCnpcCardInfPK id;
	// fields
	private java.lang.String checkCode;
	private java.lang.String saState;
	private java.lang.String cardBin;
	private java.lang.String cardNo;
	private java.lang.String initOprId;
	private java.lang.String initTime;
	private java.lang.String upOprId;
	private java.lang.String upTime;
	private java.lang.String verifierOprId;
	private java.lang.String verifierTime;
	private java.lang.String remarks;


	public TblCnpcCardInfPK getId() {
		return id;
	}

	public void setId(TblCnpcCardInfPK id) {
		this.id = id;
	}

	public java.lang.String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(java.lang.String checkCode) {
		this.checkCode = checkCode;
	}

	public java.lang.String getSaState() {
		return saState;
	}

	public void setSaState(java.lang.String saState) {
		this.saState = saState;
	}

	public java.lang.String getCardBin() {
		return cardBin;
	}

	public void setCardBin(java.lang.String cardBin) {
		this.cardBin = cardBin;
	}

	public java.lang.String getCardNo() {
		return cardNo;
	}

	public void setCardNo(java.lang.String cardNo) {
		this.cardNo = cardNo;
	}

	public java.lang.String getInitOprId() {
		return initOprId;
	}

	public void setInitOprId(java.lang.String initOprId) {
		this.initOprId = initOprId;
	}

	public java.lang.String getInitTime() {
		return initTime;
	}

	public void setInitTime(java.lang.String initTime) {
		this.initTime = initTime;
	}

	public java.lang.String getUpOprId() {
		return upOprId;
	}

	public void setUpOprId(java.lang.String upOprId) {
		this.upOprId = upOprId;
	}

	public java.lang.String getUpTime() {
		return upTime;
	}

	public void setUpTime(java.lang.String upTime) {
		this.upTime = upTime;
	}

	public java.lang.String getVerifierOprId() {
		return verifierOprId;
	}

	public void setVerifierOprId(java.lang.String verifierOprId) {
		this.verifierOprId = verifierOprId;
	}

	public java.lang.String getVerifierTime() {
		return verifierTime;
	}

	public void setVerifierTime(java.lang.String verifierTime) {
		this.verifierTime = verifierTime;
	}

	public java.lang.String getRemarks() {
		return remarks;
	}

	public void setRemarks(java.lang.String remarks) {
		this.remarks = remarks;
	}

	public TblCnpcCardInf(TblCnpcCardInfPK id, String checkCode, String saState, String cardBin, String cardNo,
			String initOprId, String initTime, String upOprId, String upTime, String verifierOprId, String verifierTime,
			String remarks) {
		super();
		this.id = id;
		this.checkCode = checkCode;
		this.saState = saState;
		this.cardBin = cardBin;
		this.cardNo = cardNo;
		this.initOprId = initOprId;
		this.initTime = initTime;
		this.upOprId = upOprId;
		this.upTime = upTime;
		this.verifierOprId = verifierOprId;
		this.verifierTime = verifierTime;
		this.remarks = remarks;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cardBin == null) ? 0 : cardBin.hashCode());
		result = prime * result + ((cardNo == null) ? 0 : cardNo.hashCode());
		result = prime * result + ((checkCode == null) ? 0 : checkCode.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((initOprId == null) ? 0 : initOprId.hashCode());
		result = prime * result + ((initTime == null) ? 0 : initTime.hashCode());
		result = prime * result + ((remarks == null) ? 0 : remarks.hashCode());
		result = prime * result + ((saState == null) ? 0 : saState.hashCode());
		result = prime * result + ((upOprId == null) ? 0 : upOprId.hashCode());
		result = prime * result + ((upTime == null) ? 0 : upTime.hashCode());
		result = prime * result + ((verifierOprId == null) ? 0 : verifierOprId.hashCode());
		result = prime * result + ((verifierTime == null) ? 0 : verifierTime.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TblCnpcCardInf other = (TblCnpcCardInf) obj;
		if (cardBin == null) {
			if (other.cardBin != null)
				return false;
		} else if (!cardBin.equals(other.cardBin))
			return false;
		if (cardNo == null) {
			if (other.cardNo != null)
				return false;
		} else if (!cardNo.equals(other.cardNo))
			return false;
		if (checkCode == null) {
			if (other.checkCode != null)
				return false;
		} else if (!checkCode.equals(other.checkCode))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (initOprId == null) {
			if (other.initOprId != null)
				return false;
		} else if (!initOprId.equals(other.initOprId))
			return false;
		if (initTime == null) {
			if (other.initTime != null)
				return false;
		} else if (!initTime.equals(other.initTime))
			return false;
		if (remarks == null) {
			if (other.remarks != null)
				return false;
		} else if (!remarks.equals(other.remarks))
			return false;
		if (saState == null) {
			if (other.saState != null)
				return false;
		} else if (!saState.equals(other.saState))
			return false;
		if (upOprId == null) {
			if (other.upOprId != null)
				return false;
		} else if (!upOprId.equals(other.upOprId))
			return false;
		if (upTime == null) {
			if (other.upTime != null)
				return false;
		} else if (!upTime.equals(other.upTime))
			return false;
		if (verifierOprId == null) {
			if (other.verifierOprId != null)
				return false;
		} else if (!verifierOprId.equals(other.verifierOprId))
			return false;
		if (verifierTime == null) {
			if (other.verifierTime != null)
				return false;
		} else if (!verifierTime.equals(other.verifierTime))
			return false;
		return true;
	}
	
}