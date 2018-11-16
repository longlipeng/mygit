package com.huateng.po.risk;

import java.io.Serializable;

public class CstAfterRuleInfPK implements Serializable {

	private static final long serialVersionUID = 1L;

	public CstAfterRuleInfPK(){}
	
	public CstAfterRuleInfPK(String ruleId,String instId,String mcc){
		this.ruleId = ruleId;
		this.instId = instId;
		this.mcc  = mcc;
	}
	
	private String ruleId;
	private String instId;
	private String mcc;

	public String getRuleId() {
		return ruleId;
	}
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	public String getInstId() {
		return instId;
	}
	public void setInstId(String instId) {
		this.instId = instId;
	}
	public String getMcc() {
		return mcc;
	}
	public void setMcc(String mcc) {
		this.mcc = mcc;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((instId == null) ? 0 : instId.hashCode());
		result = prime * result + ((mcc == null) ? 0 : mcc.hashCode());
		result = prime * result + ((ruleId == null) ? 0 : ruleId.hashCode());
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
		CstAfterRuleInfPK other = (CstAfterRuleInfPK) obj;
		if (instId == null) {
			if (other.instId != null)
				return false;
		} else if (!instId.equals(other.instId))
			return false;
		if (mcc == null) {
			if (other.mcc != null)
				return false;
		} else if (!mcc.equals(other.mcc))
			return false;
		if (ruleId == null) {
			if (other.ruleId != null)
				return false;
		} else if (!ruleId.equals(other.ruleId))
			return false;
		return true;
	}
	
}
