package com.huateng.po.error;

/**
 * TblChangeAccInfId entity. @author MyEclipse Persistence Tools
 */

public class TblChangeAccInfId implements java.io.Serializable {

	// Fields

	private String changeDate;
	private String mchtNo;
	private String termId;

	// Constructors

	/** default constructor */
	public TblChangeAccInfId() {
	}

	/** full constructor */
	public TblChangeAccInfId(String changeDate, String mchtNo, String termId) {
		this.changeDate = changeDate;
		this.mchtNo = mchtNo;
		this.termId = termId;
	}

	// Property accessors

	public String getChangeDate() {
		return this.changeDate;
	}

	public void setChangeDate(String changeDate) {
		this.changeDate = changeDate;
	}

	public String getMchtNo() {
		return this.mchtNo;
	}

	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}

	public String getTermId() {
		return this.termId;
	}

	public void setTermId(String termId) {
		this.termId = termId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TblChangeAccInfId))
			return false;
		TblChangeAccInfId castOther = (TblChangeAccInfId) other;

		return ((this.getChangeDate() == castOther.getChangeDate()) || (this
				.getChangeDate() != null
				&& castOther.getChangeDate() != null && this.getChangeDate()
				.equals(castOther.getChangeDate())))
				&& ((this.getMchtNo() == castOther.getMchtNo()) || (this
						.getMchtNo() != null
						&& castOther.getMchtNo() != null && this.getMchtNo()
						.equals(castOther.getMchtNo())))
				&& ((this.getTermId() == castOther.getTermId()) || (this
						.getTermId() != null
						&& castOther.getTermId() != null && this.getTermId()
						.equals(castOther.getTermId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getChangeDate() == null ? 0 : this.getChangeDate()
						.hashCode());
		result = 37 * result
				+ (getMchtNo() == null ? 0 : this.getMchtNo().hashCode());
		result = 37 * result
				+ (getTermId() == null ? 0 : this.getTermId().hashCode());
		return result;
	}

}