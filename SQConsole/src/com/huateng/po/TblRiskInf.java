package com.huateng.po;

import java.io.Serializable;

public class TblRiskInf implements Serializable {
	private static final long serialVersionUID = 1L;

	protected void initialize() {
	}

	public TblRiskInf() {
		super();
	}

	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private String id;

	// fields
	private String saBeUse;
	private String saAction;
	private String saLimitNum;
	private String saLimitAmount;
	private String modiZoneNo;
	private String modiOprId;
	private String modiTime;
	private String saBranchCode;
	private String saDays;
	private int saWarnlvt;
	private String saBeUseModify;
	private String saActionModify;
	private String saLimitNumModify;
	private String saLimitAmountModify;
	private String saDaysModify;
	private String saState;
	
	private String saModelName;
	
	
	
	



	

	public int getSaWarnlvt() {
		return saWarnlvt;
	}

	public void setSaWarnlvt(int saWarnlvt) {
		this.saWarnlvt = saWarnlvt;
	}

	/**
	 * Return the unique identifier of this class
	 * 
	 * @hibernate.id generator-class="assigned" column="sa_model_kind"
	 */
	public String getId() {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * 
	 * @param id
	 *            the new ID
	 */
	public void setId(String id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}

	
	
	
	public String getSaModelName() {
		return saModelName;
	}

	public void setSaModelName(String saModelName) {
		this.saModelName = saModelName;
	}

	/**
	 * Return the value associated with the column: sa_be_use
	 */
	public String getSaBeUse() {
		return saBeUse;
	}

	/**
	 * Set the value related to the column: sa_be_use
	 * 
	 * @param saBeUse
	 *            the sa_be_use value
	 */
	public void setSaBeUse(String saBeUse) {
		this.saBeUse = saBeUse;
	}

	/**
	 * Return the value associated with the column: sa_action
	 */
	public String getSaAction() {
		return saAction;
	}

	/**
	 * Set the value related to the column: sa_action
	 * 
	 * @param saAction
	 *            the sa_action value
	 */
	public void setSaAction(String saAction) {
		this.saAction = saAction;
	}

	public String getSaDays() {
		return saDays;
	}

	public void setSaDays(String saDays) {
		this.saDays = saDays;
	}

	/**
	 * Return the value associated with the column: sa_limit_num
	 */
	public String getSaLimitNum() {
		return saLimitNum;
	}

	/**
	 * Set the value related to the column: sa_limit_num
	 * 
	 * @param saLimitNum
	 *            the sa_limit_num value
	 */
	public void setSaLimitNum(String saLimitNum) {
		this.saLimitNum = saLimitNum;
	}

	/**
	 * Return the value associated with the column: sa_limit_amount
	 */
	public String getSaLimitAmount() {
		return saLimitAmount;
	}

	/**
	 * Set the value related to the column: sa_limit_amount
	 * 
	 * @param saLimitAmount
	 *            the sa_limit_amount value
	 */
	public void setSaLimitAmount(String saLimitAmount) {
		this.saLimitAmount = saLimitAmount;
	}

	/**
	 * @return the modiZoneNo
	 */
	public String getModiZoneNo() {
		return modiZoneNo;
	}

	/**
	 * @param modiZoneNo
	 *            the modiZoneNo to set
	 */
	public void setModiZoneNo(String modiZoneNo) {
		this.modiZoneNo = modiZoneNo;
	}

	/**
	 * @return the modiOprId
	 */
	public String getModiOprId() {
		return modiOprId;
	}

	/**
	 * @param modiOprId
	 *            the modiOprId to set
	 */
	public void setModiOprId(String modiOprId) {
		this.modiOprId = modiOprId;
	}

	/**
	 * @return the modiTime
	 */
	public String getModiTime() {
		return modiTime;
	}

	/**
	 * @param modiTime
	 *            the modiTime to set
	 */
	public void setModiTime(String modiTime) {
		this.modiTime = modiTime;
	}

	/**
	 * @return the saBranchCode
	 */
	public String getSaBranchCode() {
		return saBranchCode;
	}

	/**
	 * @param saBranchCode
	 *            the saBranchCode to set
	 */
	public void setSaBranchCode(String saBranchCode) {
		this.saBranchCode = saBranchCode;
	}
	
	public String getSaBeUseModify() {
		return saBeUseModify;
	}

	public void setSaBeUseModify(String saBeUseModify) {
		this.saBeUseModify = saBeUseModify;
	}

	public String getSaActionModify() {
		return saActionModify;
	}

	public void setSaActionModify(String saActionModify) {
		this.saActionModify = saActionModify;
	}

	public String getSaLimitNumModify() {
		return saLimitNumModify;
	}

	public void setSaLimitNumModify(String saLimitNumModify) {
		this.saLimitNumModify = saLimitNumModify;
	}

	public String getSaLimitAmountModify() {
		return saLimitAmountModify;
	}

	public void setSaLimitAmountModify(String saLimitAmountModify) {
		this.saLimitAmountModify = saLimitAmountModify;
	}

	public String getSaDaysModify() {
		return saDaysModify;
	}

	public void setSaDaysModify(String saDaysModify) {
		this.saDaysModify = saDaysModify;
	}
	
	public String getSaState() {
		return saState;
	}

	public void setSaState(String saState) {
		this.saState = saState;
	}

	public boolean equals(Object obj) {
		if (null == obj)
			return false;
		if (!(obj instanceof com.huateng.po.TblRiskInf))
			return false;
		else {
			com.huateng.po.TblRiskInf tblRiskInf = (com.huateng.po.TblRiskInf) obj;
			if (null == this.getId() || null == tblRiskInf.getId())
				return false;
			else
				return (this.getId().equals(tblRiskInf.getId()));
		}
	}

	public int hashCode() {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId())
				return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":"
						+ this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}

	public String toString() {
		return super.toString();
	}
}