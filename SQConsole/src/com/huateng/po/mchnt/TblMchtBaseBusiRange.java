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
	private String busiRange;  //经营范围
	
	
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
	public String getBusiRange() {
		return busiRange;
	}
	public void setBusiRange(String busiRange) {
		this.busiRange = busiRange;
	}
	
	
	
}
