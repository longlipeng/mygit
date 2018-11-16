package com.huateng.po.risk;

import java.io.Serializable;

public class TblRiskRefuse implements Serializable {

	private static final long serialVersionUID = 1L;
	private String txnTime;//拒绝时间
	private String param1;
	private String param2;
	private String param3;
	private String param4;
	private String param5;
	private String param6;
	private String brhId;
	private String oprId;
	private String refuseType;
	private String refuseInfo;
	private String flag;
	private String reserve1;
	
	public String getTxnTime() {
		return txnTime;
	}
	public void setTxnTime(String txnTime) {
		this.txnTime = txnTime;
	}
	public String getParam1() {
		return param1;
	}
	public void setParam1(String param1) {
		this.param1 = param1;
	}
	public String getBrhId() {
		return brhId;
	}
	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}
	public String getOprId() {
		return oprId;
	}
	public void setOprId(String oprId) {
		this.oprId = oprId;
	}
	public String getRefuseType() {
		return refuseType;
	}
	public void setRefuseType(String refuseType) {
		this.refuseType = refuseType;
	}
	public String getRefuseInfo() {
		return refuseInfo;
	}
	public void setRefuseInfo(String refuseInfo) {
		this.refuseInfo = refuseInfo;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getParam2() {
		return param2;
	}
	public void setParam2(String param2) {
		this.param2 = param2;
	}
	public String getParam3() {
		return param3;
	}
	public void setParam3(String param3) {
		this.param3 = param3;
	}
	public String getParam4() {
		return param4;
	}
	public void setParam4(String param4) {
		this.param4 = param4;
	}
	public String getParam5() {
		return param5;
	}
	public void setParam5(String param5) {
		this.param5 = param5;
	}
	public String getParam6() {
		return param6;
	}
	public void setParam6(String param6) {
		this.param6 = param6;
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
		result = prime * result + ((brhId == null) ? 0 : brhId.hashCode());
		result = prime * result + ((flag == null) ? 0 : flag.hashCode());
		result = prime * result + ((oprId == null) ? 0 : oprId.hashCode());
		result = prime * result + ((param1 == null) ? 0 : param1.hashCode());
		result = prime * result + ((param2 == null) ? 0 : param2.hashCode());
		result = prime * result + ((param3 == null) ? 0 : param3.hashCode());
		result = prime * result + ((param4 == null) ? 0 : param4.hashCode());
		result = prime * result + ((param5 == null) ? 0 : param5.hashCode());
		result = prime * result + ((param6 == null) ? 0 : param6.hashCode());
		result = prime * result + ((reserve1 == null) ? 0 : reserve1.hashCode());
		result = prime * result
				+ ((refuseInfo == null) ? 0 : refuseInfo.hashCode());
		result = prime * result
				+ ((refuseType == null) ? 0 : refuseType.hashCode());
		result = prime * result + ((txnTime == null) ? 0 : txnTime.hashCode());
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
		TblRiskRefuse other = (TblRiskRefuse) obj;
		if (brhId == null) {
			if (other.brhId != null)
				return false;
		} else if (!brhId.equals(other.brhId))
			return false;
		if (flag == null) {
			if (other.flag != null)
				return false;
		} else if (!flag.equals(other.flag))
			return false;
		if (oprId == null) {
			if (other.oprId != null)
				return false;
		} else if (!oprId.equals(other.oprId))
			return false;
		if (param1 == null) {
			if (other.param1 != null)
				return false;
		} else if (!param1.equals(other.param1))
			return false;
		if (param2 == null) {
			if (other.param2 != null)
				return false;
		} else if (!param2.equals(other.param2))
			return false;
		if (param3 == null) {
			if (other.param3 != null)
				return false;
		} else if (!param3.equals(other.param3))
			return false;
		if (param4 == null) {
			if (other.param4 != null)
				return false;
		} else if (!param4.equals(other.param4))
			return false;
		if (param5 == null) {
			if (other.param5 != null)
				return false;
		} else if (!param5.equals(other.param5))
			return false;
		if (param6 == null) {
			if (other.param6 != null)
				return false;
		} else if (!param6.equals(other.param6))
			return false;
		if (reserve1 == null) {
			if (other.reserve1 != null)
				return false;
		} else if (!reserve1.equals(other.reserve1))
			return false;
		if (refuseInfo == null) {
			if (other.refuseInfo != null)
				return false;
		} else if (!refuseInfo.equals(other.refuseInfo))
			return false;
		if (refuseType == null) {
			if (other.refuseType != null)
				return false;
		} else if (!refuseType.equals(other.refuseType))
			return false;
		if (txnTime == null) {
			if (other.txnTime != null)
				return false;
		} else if (!txnTime.equals(other.txnTime))
			return false;
		return true;
	}
	
}
