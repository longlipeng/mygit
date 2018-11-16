package com.huateng.po.error;

import java.io.Serializable;

public class TBLTermHangPK implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String termid;
	public String getTermid() {
		return termid;
	}
	public void setTermid(String termid) {
		this.termid = termid;
	}
	public String getTransdate() {
		return transdate;
	}
	public void setTransdate(String transdate) {
		this.transdate = transdate;
	}
	private String transdate;
}
