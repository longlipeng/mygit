package com.huateng.po.error;

import java.io.Serializable;

public class TblMchtHangPK implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String getMchtno() {
		return mchtno;
	}
	public void setMchtno(String mchtno) {
		this.mchtno = mchtno;
	}
	public String getTransdate() {
		return transdate;
	}
	public void setTransdate(String transdate) {
		this.transdate = transdate;
	}
	private String mchtno;
	private String transdate;
}
