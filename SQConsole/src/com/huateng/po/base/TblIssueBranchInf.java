package com.huateng.po.base;

import java.io.Serializable;

public class TblIssueBranchInf implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String branchId;
	private String branchName;
	private String branchAddr;
	private String reserve1;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getBranchAddr() {
		return branchAddr;
	}
	public void setBranchAddr(String branchAddr) {
		this.branchAddr = branchAddr;
	}
	
	public String getReserve1() {
		return reserve1;
	}
	public void setReserve1(String reserve1) {
		this.reserve1 = reserve1;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((branchAddr == null) ? 0 : branchAddr.hashCode());
		result = prime * result + ((branchId == null) ? 0 : branchId.hashCode());
		result = prime * result + ((branchName == null) ? 0 : branchName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((reserve1 == null) ? 0 : reserve1.hashCode());
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
		TblIssueBranchInf other = (TblIssueBranchInf) obj;
		if (branchAddr == null) {
			if (other.branchAddr != null)
				return false;
		} else if (!branchAddr.equals(other.branchAddr))
			return false;
		if (branchId == null) {
			if (other.branchId != null)
				return false;
		} else if (!branchId.equals(other.branchId))
			return false;
		if (branchName == null) {
			if (other.branchName != null)
				return false;
		} else if (!branchName.equals(other.branchName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (reserve1 == null) {
			if (other.reserve1 != null)
				return false;
		} else if (!reserve1.equals(other.reserve1))
			return false;
		return true;
	}
	
}
