package com.huateng.hstserver.communicate.mina.comm.common.filter.protocolcodec.app.sample;



public class SampleMessageObj {
	
	private short seqNo;
	
	private short replyCode;
	
	public short getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(short seqNo) {
		this.seqNo = seqNo;
	}

	public short getReplyCode() {
		return replyCode;
	}

	public void setReplyCode(short replyCode) {
		this.replyCode = replyCode;
	}

	
}
