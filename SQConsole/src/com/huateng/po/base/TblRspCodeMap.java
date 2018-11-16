package com.huateng.po.base;

import java.io.Serializable;

public class TblRspCodeMap implements Serializable {
	private static final long serialVersionUID = 1L;
	private int hashCode = Integer.MIN_VALUE;
	private TblRspCodeMapPK id;
	public TblRspCodeMap(TblRspCodeMapPK key){
		this.setId(key);
		initialize();
	}
	
	public TblRspCodeMapPK getId() {
		return id;
	}

	public void setId(TblRspCodeMapPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}
	
    private Integer srcRspCodeL;
    private Integer destRspCodeL;
    private String rspCodeDsp;
    private String statusId;
    private String oprId;
    private String checkId;
    private String oprTime;
    private String checkTime;
    private String srcRspCodeLOld;
    private String destRspCodeLOld;
    private String rspCodeDspOld;
    
    public TblRspCodeMap(){
    	initialize();
    }
    
    protected void initialize () {}
	

	public Integer getSrcRspCodeL() {
		return srcRspCodeL;
	}
	public void setSrcRspCodeL(Integer srcRspCodeL) {
		this.srcRspCodeL = srcRspCodeL;
	}
	
	public String getOprId() {
		return oprId;
	}

	public void setOprId(String oprId) {
		this.oprId = oprId;
	}

	public String getCheckId() {
		return checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}

	public String getOprTime() {
		return oprTime;
	}

	public void setOprTime(String oprTime) {
		this.oprTime = oprTime;
	}

	public String getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}

	public String getSrcRspCodeLOld() {
		return srcRspCodeLOld;
	}

	public void setSrcRspCodeLOld(String srcRspCodeLOld) {
		this.srcRspCodeLOld = srcRspCodeLOld;
	}

	public String getDestRspCodeLOld() {
		return destRspCodeLOld;
	}

	public void setDestRspCodeLOld(String destRspCodeLOld) {
		this.destRspCodeLOld = destRspCodeLOld;
	}

	public Integer getDestRspCodeL() {
		return destRspCodeL;
	}
	public void setDestRspCodeL(Integer destRspCodeL) {
		this.destRspCodeL = destRspCodeL;
	}
	public String getRspCodeDsp() {
		return rspCodeDsp;
	}
	public void setRspCodeDsp(String rspCodeDsp) {
		this.rspCodeDsp = rspCodeDsp;
	}
	public String getStatusId() {
		return statusId;
	}
	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}
	
	public String getRspCodeDspOld() {
		return rspCodeDspOld;
	}

	public void setRspCodeDspOld(String rspCodeDspOld) {
		this.rspCodeDspOld = rspCodeDspOld;
	}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.base.TblRspCodeMap)) return false;
		else {
			com.huateng.po.base.TblRspCodeMap tblRspCodeMap = (com.huateng.po.base.TblRspCodeMap) obj;
			if (null == this.getId() || null == tblRspCodeMap.getId()) return false;
			else return (this.getId().equals(tblRspCodeMap.getId()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}

	public String toString () {
		return super.toString();
	}

}
