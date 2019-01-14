package com.huateng.hstserver.communicate.mina.comm.common.filter.protocolcodec.app;

public interface IAppObjectProcessor {
	
	public Object msg2obj(byte[] messageBytes) throws Exception;
	
	public byte[] obj2msg(Object messageObject) throws Exception;
}
