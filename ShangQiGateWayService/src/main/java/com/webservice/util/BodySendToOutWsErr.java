/**   
 * @Title: BodySendToOutWsErr.java
 *
 * @Package: cn.whty.service.xml.sendToOut 
 *
 * @Description: TODO
 *
 * @date: 2014年7月23日 下午11:16:33 
 *
 * @Company: Copyright © WuHan TianYu Information Industry CO., LTD
 *
 * @author: zhangyudong  
 *
 * @version: V1.0   
 */
package com.webservice.util;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @Title: BodySendToOut1000.java
 * 
 * @Description: TODO
 * 
 * @date: 2014年7月23日 下午11:16:33
 * 
 * @Company: Copyright © WuHan TianYu Information Industry CO., LTD
 * 
 * @author: zhangyudong
 * 
 * @version: V1.0
 */
@XmlType(propOrder = { "sCode", "termCode", "mechtCode", "txnTlr",
		"chanlTxnNum", "chanlTime", "frontRspCode", "replyMsg", "retrivlRef" })
public class BodySendToOutWsErr {

	public BodySendToOutWsErr() {

	}

	// 公共参数
	private java.lang.String sCode; // 服务代码
	private java.lang.String termCode;// 终端代码
	private java.lang.String mechtCode;// 商户代码
	private java.lang.String txnTlr;// 交易操作员号
	// 通用参数
	private java.lang.String chanlTxnNum;// 收单流水
	private java.lang.String chanlTime;// 收单时间
	private java.lang.String frontRspCode;// 交易响应代码
	private java.lang.String replyMsg;// 拒绝原因说明
	private java.lang.String retrivlRef;// RetrivlRef

	@XmlElement(name = "SCode")
	public java.lang.String getsCode() {
		return sCode;
	}

	@XmlElement(name = "TermCode")
	public java.lang.String getTermCode() {
		return termCode;
	}

	@XmlElement(name = "MechtCode")
	public java.lang.String getMechtCode() {
		return mechtCode;
	}

	@XmlElement(name = "TxnTlr")
	public java.lang.String getTxnTlr() {
		return txnTlr;
	}

	@XmlElement(name = "ChanlTxnNum")
	public java.lang.String getChanlTxnNum() {
		return chanlTxnNum;
	}

	@XmlElement(name = "ChanlTime")
	public java.lang.String getChanlTime() {
		return chanlTime;
	}

	@XmlElement(name = "FrontRspCode")
	public java.lang.String getFrontRspCode() {
		return frontRspCode;
	}

	@XmlElement(name = "ReplyMsg")
	public java.lang.String getReplyMsg() {
		return replyMsg;
	}

	@XmlElement(name = "RetrivlRef")
	public java.lang.String getRetrivlRef() {
		return retrivlRef;
	}

	

	/**
	 * @param sCode
	 *            the sCode to set
	 */
	public void setsCode(java.lang.String sCode) {
		this.sCode = sCode;
	}

	/**
	 * @param termCode
	 *            the termCode to set
	 */
	public void setTermCode(java.lang.String termCode) {
		this.termCode = termCode;
	}

	/**
	 * @param mechtCode
	 *            the mechtCode to set
	 */
	public void setMechtCode(java.lang.String mechtCode) {
		this.mechtCode = mechtCode;
	}

	/**
	 * @param txnTlr
	 *            the txnTlr to set
	 */
	public void setTxnTlr(java.lang.String txnTlr) {
		this.txnTlr = txnTlr;
	}

	/**
	 * @param chanlTxnNum
	 *            the chanlTxnNum to set
	 */
	public void setChanlTxnNum(java.lang.String chanlTxnNum) {
		this.chanlTxnNum = chanlTxnNum;
	}

	/**
	 * @param chanlTime
	 *            the chanlTime to set
	 */
	public void setChanlTime(java.lang.String chanlTime) {
		this.chanlTime = chanlTime;
	}

	/**
	 * @param frontRspCode
	 *            the frontRspCode to set
	 */
	public void setFrontRspCode(java.lang.String frontRspCode) {
		this.frontRspCode = frontRspCode;
	}

	/**
	 * @param replyMsg
	 *            the replyMsg to set
	 */
	public void setReplyMsg(java.lang.String replyMsg) {
		this.replyMsg = replyMsg;
	}

	/**
	 * @param retrivlRef
	 *            the retrivlRef to set
	 */
	public void setRetrivlRef(java.lang.String retrivlRef) {
		this.retrivlRef = retrivlRef;
	}


}
