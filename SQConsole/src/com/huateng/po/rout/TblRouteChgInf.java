package com.huateng.po.rout;

import java.io.Serializable;

/**
 * 路由切换表
 * @author crystal
 *
 */
public class TblRouteChgInf implements Serializable {

	private static final long serialVersionUID = 1L;

	private String destInstId;//机构代码，PK
	private String dftDestId;//原目的ID
	private String chgFlag;//切换标志
	private String chgDestId;//切换后的目的ID
	//20120727 add
	private String createTime;
	private String createOprId;
	private String modiTime;
	private String modiOprId;
	private String reserved;//保留域
	
	public String getDestInstId() {
		return destInstId;
	}
	public void setDestInstId(String destInstId) {
		this.destInstId = destInstId;
	}
	public String getDftDestId() {
		return dftDestId;
	}
	public void setDftDestId(String dftDestId) {
		this.dftDestId = dftDestId;
	}
	public String getChgFlag() {
		return chgFlag;
	}
	public void setChgFlag(String chgFlag) {
		this.chgFlag = chgFlag;
	}
	public String getChgDestId() {
		return chgDestId;
	}
	public void setChgDestId(String chgDestId) {
		this.chgDestId = chgDestId;
	}
	public String getReserved() {
		return reserved;
	}
	public void setReserved(String reserved) {
		this.reserved = reserved;
	}
	
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCreateOprId() {
		return createOprId;
	}
	public void setCreateOprId(String createOprId) {
		this.createOprId = createOprId;
	}
	public String getModiTime() {
		return modiTime;
	}
	public void setModiTime(String modiTime) {
		this.modiTime = modiTime;
	}
	public String getModiOprId() {
		return modiOprId;
	}
	public void setModiOprId(String modiOprId) {
		this.modiOprId = modiOprId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((chgDestId == null) ? 0 : chgDestId.hashCode());
		result = prime * result + ((chgFlag == null) ? 0 : chgFlag.hashCode());
		result = prime * result
				+ ((createOprId == null) ? 0 : createOprId.hashCode());
		result = prime * result
				+ ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result
				+ ((destInstId == null) ? 0 : destInstId.hashCode());
		result = prime * result
				+ ((dftDestId == null) ? 0 : dftDestId.hashCode());
		result = prime * result
				+ ((modiOprId == null) ? 0 : modiOprId.hashCode());
		result = prime * result
				+ ((modiTime == null) ? 0 : modiTime.hashCode());
		result = prime * result
				+ ((reserved == null) ? 0 : reserved.hashCode());
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
		TblRouteChgInf other = (TblRouteChgInf) obj;
		if (chgDestId == null) {
			if (other.chgDestId != null)
				return false;
		} else if (!chgDestId.equals(other.chgDestId))
			return false;
		if (chgFlag == null) {
			if (other.chgFlag != null)
				return false;
		} else if (!chgFlag.equals(other.chgFlag))
			return false;
		if (createOprId == null) {
			if (other.createOprId != null)
				return false;
		} else if (!createOprId.equals(other.createOprId))
			return false;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (destInstId == null) {
			if (other.destInstId != null)
				return false;
		} else if (!destInstId.equals(other.destInstId))
			return false;
		if (dftDestId == null) {
			if (other.dftDestId != null)
				return false;
		} else if (!dftDestId.equals(other.dftDestId))
			return false;
		if (modiOprId == null) {
			if (other.modiOprId != null)
				return false;
		} else if (!modiOprId.equals(other.modiOprId))
			return false;
		if (modiTime == null) {
			if (other.modiTime != null)
				return false;
		} else if (!modiTime.equals(other.modiTime))
			return false;
		if (reserved == null) {
			if (other.reserved != null)
				return false;
		} else if (!reserved.equals(other.reserved))
			return false;
		return true;
	}
	
}
