package com.huateng.po.risk;

import java.io.Serializable;

public class CstAfterRuleInf implements Serializable {

	private static final long serialVersionUID = 1L;

	private CstAfterRuleInfPK id;
	
	private String days;
	private String warnLvt;
	private String warnCount;
	private String warnAmt;
	private String saState;
	private String daysOld;
	private String warnLvtOld;
	private String warnCountOld;
	private String warnAmtOld;
	private String createTime;
	private String updateTime;
	private String creator;
	private String updateOpr;
	private String caseid;
	/**
	 * @return the caseid
	 */
	public String getCaseid() {
		return caseid;
	}

	/**
	 * @param caseid the caseid to set
	 */
	public void setCaseid(String caseid) {
		this.caseid = caseid;
	}

	public CstAfterRuleInf(){}
	
	public CstAfterRuleInf(CstAfterRuleInfPK id){
		this.id = id;
	}
	
	public CstAfterRuleInf(CstAfterRuleInfPK id,String days,String warnLvt,String warnCount,
			String warnAmt,String saState,String daysOld,String warnLvtOld,String warnCountOld,
			String warnAmtOld,String createTime,String updateTime,String creator,String updateOpr){
		this.id = id;
		this.days = days;
		this.warnLvt = warnLvt;
		this.warnCount = warnCount;
		this.warnAmt = warnAmt;
		this.saState = saState;
		this.daysOld = daysOld;
		this.warnLvtOld = warnLvtOld;
		this.warnCountOld = warnCountOld;
		this.warnAmtOld = warnAmtOld;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.creator = creator;
		this.updateOpr = updateOpr;
	}
	
	public CstAfterRuleInfPK getId() {
		return id;
	}
	public void setId(CstAfterRuleInfPK id) {
		this.id = id;
	}
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	public String getWarnLvt() {
		return warnLvt;
	}
	public void setWarnLvt(String warnLvt) {
		this.warnLvt = warnLvt;
	}
	public String getWarnCount() {
		return warnCount;
	}
	public void setWarnCount(String warnCount) {
		this.warnCount = warnCount;
	}
	public String getWarnAmt() {
		return warnAmt;
	}
	public void setWarnAmt(String warnAmt) {
		this.warnAmt = warnAmt;
	}
	public String getSaState() {
		return saState;
	}
	public void setSaState(String saState) {
		this.saState = saState;
	}
	public String getDaysOld() {
		return daysOld;
	}
	public void setDaysOld(String daysOld) {
		this.daysOld = daysOld;
	}
	public String getWarnLvtOld() {
		return warnLvtOld;
	}
	public void setWarnLvtOld(String warnLvtOld) {
		this.warnLvtOld = warnLvtOld;
	}
	public String getWarnCountOld() {
		return warnCountOld;
	}
	public void setWarnCountOld(String warnCountOld) {
		this.warnCountOld = warnCountOld;
	}
	public String getWarnAmtOld() {
		return warnAmtOld;
	}
	public void setWarnAmtOld(String warnAmtOld) {
		this.warnAmtOld = warnAmtOld;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getUpdateOpr() {
		return updateOpr;
	}
	public void setUpdateOpr(String updateOpr) {
		this.updateOpr = updateOpr;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((creator == null) ? 0 : creator.hashCode());
		result = prime * result + ((days == null) ? 0 : days.hashCode());
		result = prime * result + ((daysOld == null) ? 0 : daysOld.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((saState == null) ? 0 : saState.hashCode());
		result = prime * result
				+ ((updateOpr == null) ? 0 : updateOpr.hashCode());
		result = prime * result
				+ ((updateTime == null) ? 0 : updateTime.hashCode());
		result = prime * result + ((warnAmt == null) ? 0 : warnAmt.hashCode());
		result = prime * result
				+ ((warnAmtOld == null) ? 0 : warnAmtOld.hashCode());
		result = prime * result
				+ ((warnCount == null) ? 0 : warnCount.hashCode());
		result = prime * result
				+ ((warnCountOld == null) ? 0 : warnCountOld.hashCode());
		result = prime * result + ((warnLvt == null) ? 0 : warnLvt.hashCode());
		result = prime * result
				+ ((warnLvtOld == null) ? 0 : warnLvtOld.hashCode());
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
		CstAfterRuleInf other = (CstAfterRuleInf) obj;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (creator == null) {
			if (other.creator != null)
				return false;
		} else if (!creator.equals(other.creator))
			return false;
		if (days == null) {
			if (other.days != null)
				return false;
		} else if (!days.equals(other.days))
			return false;
		if (daysOld == null) {
			if (other.daysOld != null)
				return false;
		} else if (!daysOld.equals(other.daysOld))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (saState == null) {
			if (other.saState != null)
				return false;
		} else if (!saState.equals(other.saState))
			return false;
		if (updateOpr == null) {
			if (other.updateOpr != null)
				return false;
		} else if (!updateOpr.equals(other.updateOpr))
			return false;
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
			return false;
		if (warnAmt == null) {
			if (other.warnAmt != null)
				return false;
		} else if (!warnAmt.equals(other.warnAmt))
			return false;
		if (warnAmtOld == null) {
			if (other.warnAmtOld != null)
				return false;
		} else if (!warnAmtOld.equals(other.warnAmtOld))
			return false;
		if (warnCount == null) {
			if (other.warnCount != null)
				return false;
		} else if (!warnCount.equals(other.warnCount))
			return false;
		if (warnCountOld == null) {
			if (other.warnCountOld != null)
				return false;
		} else if (!warnCountOld.equals(other.warnCountOld))
			return false;
		if (warnLvt == null) {
			if (other.warnLvt != null)
				return false;
		} else if (!warnLvt.equals(other.warnLvt))
			return false;
		if (warnLvtOld == null) {
			if (other.warnLvtOld != null)
				return false;
		} else if (!warnLvtOld.equals(other.warnLvtOld))
			return false;
		return true;
	}
	
}
