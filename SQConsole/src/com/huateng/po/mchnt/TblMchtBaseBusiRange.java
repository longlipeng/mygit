package com.huateng.po.mchnt;

import java.io.Serializable;

/**
 * 经营范围
 * @author Administrator
 *
 */
public class TblMchtBaseBusiRange implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String busiRangeId;    //商户编号
	private String mchtNo;       //商户编号
	private String busiRanges;  //经营范围
	
	
	public String getBusiRanges() {
		return busiRanges;
	}
	public void setBusiRanges(String busiRanges) {
		this.busiRanges = busiRanges;
	}
	public String getMchtNo() {
		return mchtNo;
	}
	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}
	public String getBusiRangeId() {
		return busiRangeId;
	}
	public void setBusiRangeId(String busiRangeId) {
		this.busiRangeId = busiRangeId;
	}
	
	
	
}
