package com.huateng.hstserver.communicate.mina.comm.common.quartz;


public class ConnectionTask {
	//链路名称
	private String connectionNm;
	//链路重建申请时间
	private long requestTms;
	
	public String getConnectionNm() {
		return connectionNm;
	}
	public void setConnectionNm(String connectionNm) {
		this.connectionNm = connectionNm;
	}
	
	public long getRequestTms() {
		return requestTms;
	}
	public void setRequestTms(long requestTms) {
		this.requestTms = requestTms;
	}
	
	
}
