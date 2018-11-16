package com.huateng.po.base;

import java.io.Serializable;

public class TblRspCodeMapPK implements Serializable{
	private static final long serialVersionUID = 1L;

	protected int hashCode = Integer.MIN_VALUE;

	private String srcId;
    private String destId;
    private String srcRspCode;
    private String destRspCode;

    
	public int getHashCode() {
		return hashCode;
	}

	public void setHashCode(int hashCode) {
		this.hashCode = hashCode;
	}

	public String getSrcId() {
		return srcId;
	}

	public void setSrcId(String srcId) {
		this.srcId = srcId;
	}

	public String getDestId() {
		return destId;
	}

	public void setDestId(String destId) {
		this.destId = destId;
	}

	public String getSrcRspCode() {
		return srcRspCode;
	}

	public void setSrcRspCode(String srcRspCode) {
		this.srcRspCode = srcRspCode;
	}

	public String getDestRspCode() {
		return destRspCode;
	}

	public void setDestRspCode(String destRspCode) {
		this.destRspCode = destRspCode;
	}

	public TblRspCodeMapPK () {}
	
   


	public TblRspCodeMapPK(String srcId, String destId, String srcRspCode,
			String destRspCode) {
		this.srcId = srcId;
		this.destId = destId;
		this.srcRspCode = srcRspCode;
		this.destRspCode = destRspCode;
	}

	/**
	 * Return the value associated with the column: TLR_ID
	 */
	




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.TblBrhTlrInfoPK)) return false;
		else {
			com.huateng.po.base.TblRspCodeMapPK mObj = (com.huateng.po.base.TblRspCodeMapPK) obj;
			if (null != this.getSrcId()&& null != mObj.getSrcId()) {
				if (!this.getSrcId().equals(mObj.getSrcId())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getDestId() && null != mObj.getDestId()) {
				if (!this.getDestId().equals(mObj.getDestId())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getSrcRspCode() && null != mObj.getSrcRspCode()) {
				if (!this.getSrcRspCode().equals(mObj.getSrcRspCode())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getDestRspCode() && null != mObj.getDestRspCode()) {
				if (!this.getDestRspCode().equals(mObj.getDestRspCode())) {
					return false;
				}
			}
			else {
				return false;
			}
			return true;
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			StringBuilder sb = new StringBuilder();
			if (null != this.getSrcId()) {
				sb.append(this.getSrcId().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getDestId()) {
				sb.append(this.getDestId().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getDestRspCode()) {
				sb.append(this.getDestRspCode().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getSrcRspCode()) {
				sb.append(this.getSrcRspCode().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			this.hashCode = sb.toString().hashCode();
		}
		return this.hashCode;
	}

}
