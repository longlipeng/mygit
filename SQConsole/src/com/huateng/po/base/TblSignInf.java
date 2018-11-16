package com.huateng.po.base;

import java.io.Serializable;

public class TblSignInf implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    /**
	 * @return the instid
	 */
	public String getInstid() {
		return instid;
	}
	/**
	 * @param instid the instid to set
	 */
	public void setInstid(String instid) {
		this.instid = instid;
	}
	/**
	 * @return the instname
	 */
	public String getInstname() {
		return instname;
	}
	/**
	 * @param instname the instname to set
	 */
	public void setInstname(String instname) {
		this.instname = instname;
	}
	/**
	 * @return the mchtid
	 */
	public String getMchtid() {
		return mchtid;
	}
	/**
	 * @param mchtid the mchtid to set
	 */
	public void setMchtid(String mchtid) {
		this.mchtid = mchtid;
	}
	/**
	 * @return the termid
	 */
	public String getTermid() {
		return termid;
	}
	/**
	 * @param termid the termid to set
	 */
	public void setTermid(String termid) {
		this.termid = termid;
	}
	/**
	 * @return the signedflag
	 */
	public String getSignedflag() {
		return signedflag;
	}
	/**
	 * @param signedflag the signedflag to set
	 */
	public void setSignedflag(String signedflag) {
		this.signedflag = signedflag;
	}
	/**
	 * @return the signtime
	 */
	public String getSigntime() {
		return signtime;
	}
	/**
	 * @param signtime the signtime to set
	 */
	public void setSigntime(String signtime) {
		this.signtime = signtime;
	}
	private String instid;
    private String instname;
    private String mchtid;
    private String termid;
    private String signedflag;
    private String signtime;
}
