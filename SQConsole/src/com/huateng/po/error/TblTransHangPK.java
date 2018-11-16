package com.huateng.po.error;

import java.io.Serializable;

public class TblTransHangPK implements Serializable{
	   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String flownum;
	public String getTxnssn() {
		return txnssn;
	}
	public void setTxnssn(String txnssn) {
		this.txnssn = txnssn;
	}
	private String txnssn;
   public String getFlownum() {
		return flownum;
	}
	public void setFlownum(String flownum) {
		this.flownum = flownum;
	}
	

}
