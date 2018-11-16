package com.huateng.po.base;

import java.io.Serializable;

public class TblHolidays implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String holidayStart;
	private String holidayEnd;
	private String name;
	private String createOprId;
	private String createTime;
	private String updOprId;
	private String updTime;
	private String saState;
	private String holidayStartOld;
	private String holidayEndOld;
	private String nameOld;
	private String unionFlag;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHolidayStart() {
		return holidayStart;
	}
	public void setHolidayStart(String holidayStart) {
		this.holidayStart = holidayStart;
	}
	public String getHolidayEnd() {
		return holidayEnd;
	}
	public void setHolidayEnd(String holidayEnd) {
		this.holidayEnd = holidayEnd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreateOprId() {
		return createOprId;
	}
	public void setCreateOprId(String createOprId) {
		this.createOprId = createOprId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdOprId() {
		return updOprId;
	}
	public void setUpdOprId(String updOprId) {
		this.updOprId = updOprId;
	}
	public String getUpdTime() {
		return updTime;
	}
	public void setUpdTime(String updTime) {
		this.updTime = updTime;
	}
	public String getSaState() {
		return saState;
	}
	public void setSaState(String saState) {
		this.saState = saState;
	}
	public String getHolidayStartOld() {
		return holidayStartOld;
	}
	public void setHolidayStartOld(String holidayStartOld) {
		this.holidayStartOld = holidayStartOld;
	}
	public String getHolidayEndOld() {
		return holidayEndOld;
	}
	public void setHolidayEndOld(String holidayEndOld) {
		this.holidayEndOld = holidayEndOld;
	}
	public String getNameOld() {
		return nameOld;
	}
	public void setNameOld(String nameOld) {
		this.nameOld = nameOld;
	}
	
	
	public String getUnionFlag() {
		return unionFlag;
	}
	public void setUnionFlag(String unionFlag) {
		this.unionFlag = unionFlag;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((createOprId == null) ? 0 : createOprId.hashCode());
		result = prime * result
				+ ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result
				+ ((holidayEnd == null) ? 0 : holidayEnd.hashCode());
		result = prime * result
				+ ((holidayEndOld == null) ? 0 : holidayEndOld.hashCode());
		result = prime * result
				+ ((holidayStart == null) ? 0 : holidayStart.hashCode());
		result = prime * result
				+ ((holidayStartOld == null) ? 0 : holidayStartOld.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((unionFlag == null) ? 0 : unionFlag.hashCode());
		result = prime * result + ((nameOld == null) ? 0 : nameOld.hashCode());
		result = prime * result + ((saState == null) ? 0 : saState.hashCode());
		result = prime * result
				+ ((updOprId == null) ? 0 : updOprId.hashCode());
		result = prime * result + ((updTime == null) ? 0 : updTime.hashCode());
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
		TblHolidays other = (TblHolidays) obj;
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
		if (holidayEnd == null) {
			if (other.holidayEnd != null)
				return false;
		} else if (!holidayEnd.equals(other.holidayEnd))
			return false;
		if (holidayEndOld == null) {
			if (other.holidayEndOld != null)
				return false;
		} else if (!holidayEndOld.equals(other.holidayEndOld))
			return false;
		if (holidayStart == null) {
			if (other.holidayStart != null)
				return false;
		} else if (!holidayStart.equals(other.holidayStart))
			return false;
		if (holidayStartOld == null) {
			if (other.holidayStartOld != null)
				return false;
		} else if (!holidayStartOld.equals(other.holidayStartOld))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (unionFlag == null) {
			if (other.unionFlag != null)
				return false;
		} else if (!unionFlag.equals(other.unionFlag))
			return false;
		if (nameOld == null) {
			if (other.nameOld != null)
				return false;
		} else if (!nameOld.equals(other.nameOld))
			return false;
		if (saState == null) {
			if (other.saState != null)
				return false;
		} else if (!saState.equals(other.saState))
			return false;
		if (updOprId == null) {
			if (other.updOprId != null)
				return false;
		} else if (!updOprId.equals(other.updOprId))
			return false;
		if (updTime == null) {
			if (other.updTime != null)
				return false;
		} else if (!updTime.equals(other.updTime))
			return false;
		return true;
	}
	
}
