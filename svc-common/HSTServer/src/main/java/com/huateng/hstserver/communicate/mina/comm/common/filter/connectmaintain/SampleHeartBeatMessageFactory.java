package com.huateng.hstserver.communicate.mina.comm.common.filter.connectmaintain;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;

import com.huateng.hstserver.communicate.mina.comm.common.entity.CommMessage;
import com.huateng.hstserver.communicate.mina.comm.common.filter.protocolcodec.app.sample.SampleMessageObj;


public class SampleHeartBeatMessageFactory implements KeepAliveMessageFactory {

	private static final byte SAMPLE_HEART_BEAT_BODY = 0x30;
	
	public Object getRequest(IoSession iosession) {
		
		CommMessage heartBeatmes = new CommMessage();
		
		SampleMessageObj messageObj = new SampleMessageObj();
		messageObj.setSeqNo((short)SAMPLE_HEART_BEAT_BODY);
		messageObj.setReplyCode((short)SAMPLE_HEART_BEAT_BODY);
		
		heartBeatmes.setMessageObject(messageObj);
//		heartBeatmes.setLength(1);
//		byte[] messagbody = new byte[] {SAMPLE_HEART_BEAT_BODY};
//		heartBeatmes.setMessagbody(messagbody);
		
		return heartBeatmes;
	}

	public Object getResponse(IoSession iosession, Object obj) {
		
		CommMessage heartBeatmes = new CommMessage();
		
		SampleMessageObj messageObj = new SampleMessageObj();
		messageObj.setSeqNo((short)SAMPLE_HEART_BEAT_BODY);
		messageObj.setReplyCode((short)SAMPLE_HEART_BEAT_BODY);
		
		heartBeatmes.setMessageObject(messageObj);
		
		return heartBeatmes;
	}

	public boolean isRequest(IoSession iosession, Object obj) {
		CommMessage heartBeatmes;
		try
		{
			heartBeatmes = (CommMessage) obj;
		}
		catch (Exception e)
		{
			return false;
		}
		
		if (heartBeatmes.getLength() == 1)
		{
			return true;
		}
		
		return false;
	}

	public boolean isResponse(IoSession iosession, Object obj) {
		CommMessage heartBeatmes;
		try
		{
			heartBeatmes = (CommMessage) obj;
		}
		catch (Exception e)
		{
			return false;
		}
		
		if (((SampleMessageObj)heartBeatmes.getMessageObject()).getSeqNo() == (short)SAMPLE_HEART_BEAT_BODY)
		{
			return true;
		}
		
		return false;

	}

}
