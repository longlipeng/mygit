package com.huateng.po.error;

import java.io.Serializable;

public class TBLTermHang implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TBLTermHangPK id;
	public TBLTermHangPK getId() {
		return id;
	}
	public void setId(TBLTermHangPK id) {
		this.id = id;
	}
	private String hangflag;
	private String hangdate;
	private String relievedate;
	private String remark;
	private String oprid;
	private String reliveremark;
	public String getReliveremark() {
		return reliveremark;
	}
	public void setReliveremark(String reliveremark) {
		this.reliveremark = reliveremark;
	}

	public String getHangflag() {
		return hangflag;
	}
	public void setHangflag(String hangflag) {
		this.hangflag = hangflag;
	}
	public String getHangdate() {
		return hangdate;
	}
	public void setHangdate(String hangdate) {
		this.hangdate = hangdate;
	}
	public String getRelievedate() {
		return relievedate;
	}
	public void setRelievedate(String relievedate) {
		this.relievedate = relievedate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOprid() {
		return oprid;
	}
	public void setOprid(String oprid) {
		this.oprid = oprid;
	}

}
