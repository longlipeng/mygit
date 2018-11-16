package com.huateng.po.error;

import java.io.Serializable;

public class TblAlgoDtlCheck implements Serializable {

	private static final long serialVersionUID = 1L;

	public TblAlgoDtlCheck(){}
	
	private String id;
	private String oprId;
	private String updateTime;
	private String modifyOprId;
	private String saState;
	private String cutsInfo;
	private String dateSettlmt;
	private String txnKey;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOprId() {
		return oprId;
	}
	public void setOprId(String oprId) {
		this.oprId = oprId;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getModifyOprId() {
		return modifyOprId;
	}
	public void setModifyOprId(String modifyOprId) {
		this.modifyOprId = modifyOprId;
	}
	public String getSaState() {
		return saState;
	}
	public void setSaState(String saState) {
		this.saState = saState;
	}
	public String getCutsInfo() {
		return cutsInfo;
	}
	public void setCutsInfo(String cutsInfo) {
		this.cutsInfo = cutsInfo;
	}
	public String getDateSettlmt() {
		return dateSettlmt;
	}
	public void setDateSettlmt(String dateSettlmt) {
		this.dateSettlmt = dateSettlmt;
	}
	public String getTxnKey() {
		return txnKey;
	}
	public void setTxnKey(String txnKey) {
		this.txnKey = txnKey;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cutsInfo == null) ? 0 : cutsInfo.hashCode());
		result = prime * result
				+ ((dateSettlmt == null) ? 0 : dateSettlmt.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((modifyOprId == null) ? 0 : modifyOprId.hashCode());
		result = prime * result + ((oprId == null) ? 0 : oprId.hashCode());
		result = prime * result + ((saState == null) ? 0 : saState.hashCode());
		result = prime * result + ((txnKey == null) ? 0 : txnKey.hashCode());
		result = prime * result
				+ ((updateTime == null) ? 0 : updateTime.hashCode());
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
		TblAlgoDtlCheck other = (TblAlgoDtlCheck) obj;
		if (cutsInfo == null) {
			if (other.cutsInfo != null)
				return false;
		} else if (!cutsInfo.equals(other.cutsInfo))
			return false;
		if (dateSettlmt == null) {
			if (other.dateSettlmt != null)
				return false;
		} else if (!dateSettlmt.equals(other.dateSettlmt))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (modifyOprId == null) {
			if (other.modifyOprId != null)
				return false;
		} else if (!modifyOprId.equals(other.modifyOprId))
			return false;
		if (oprId == null) {
			if (other.oprId != null)
				return false;
		} else if (!oprId.equals(other.oprId))
			return false;
		if (saState == null) {
			if (other.saState != null)
				return false;
		} else if (!saState.equals(other.saState))
			return false;
		if (txnKey == null) {
			if (other.txnKey != null)
				return false;
		} else if (!txnKey.equals(other.txnKey))
			return false;
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
			return false;
		return true;
	}
	
}
