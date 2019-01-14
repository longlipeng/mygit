package com.huateng.hstserver.communicate.mina.comm.common.filter.protocolcodec.app.sample;

import com.huateng.hstserver.communicate.mina.comm.common.filter.protocolcodec.app.IAppObjectProcessor;

public class SampleAppObjectProcessor implements IAppObjectProcessor {

	@Override
	public Object msg2obj(byte[] messageBytes) {
		SampleMessageObj message = new SampleMessageObj();
		message.setSeqNo((short)((short)(messageBytes[0]) * 256 + (short)messageBytes[1]));
		return message;
	}

	@Override
	public byte[] obj2msg(Object messageObject) {
		SampleMessageObj message = (SampleMessageObj)messageObject;
		byte[] messgeBuffer = new byte[4];
		messgeBuffer[0] = (byte)(message.getSeqNo() / 256);
		messgeBuffer[1] = (byte)message.getSeqNo();
		messgeBuffer[2] = (byte)(message.getReplyCode() / 256);
		messgeBuffer[3] = (byte)message.getReplyCode();
		
		return messgeBuffer;
	}

}
