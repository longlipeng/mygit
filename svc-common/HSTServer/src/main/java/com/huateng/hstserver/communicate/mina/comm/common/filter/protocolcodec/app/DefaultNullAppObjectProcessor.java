package com.huateng.hstserver.communicate.mina.comm.common.filter.protocolcodec.app;

public class DefaultNullAppObjectProcessor implements IAppObjectProcessor {

	@Override
	public Object msg2obj(byte[] messageBytes) {
		String data = new String(messageBytes);
		return data;
	}

	@Override
	public byte[] obj2msg(Object messageObject) {
		String data = (String) messageObject;
		byte[] messageBytes = data.getBytes();
		return messageBytes;
	}

}
