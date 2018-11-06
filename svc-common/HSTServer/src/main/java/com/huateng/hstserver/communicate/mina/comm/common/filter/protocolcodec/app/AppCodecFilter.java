package com.huateng.hstserver.communicate.mina.comm.common.filter.protocolcodec.app;



import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.WriteRequest;

import com.huateng.hstserver.communicate.mina.comm.common.entity.CommMessage;




@Deprecated
public class AppCodecFilter extends IoFilterAdapter  {

	private IAppObjectProcessor processor;
	
	public IAppObjectProcessor getProcessor() {
		return processor;
	}

	public void setProcessor(IAppObjectProcessor processor) {
		this.processor = processor;
	}

	@Override
	public void messageReceived(NextFilter nextFilter, IoSession session,
			Object message) throws Exception {
		if (processor != null)
		{
			
			((CommMessage)message).setMessageObject(processor.msg2obj(((CommMessage)message).getMessagbody()));
		}
		else
		{
			;
		}
		
		super.messageReceived(nextFilter, session, message);
	}

	@Override
	public void messageSent(NextFilter nextFilter, IoSession session,
			WriteRequest writeRequest) throws Exception {
		CommMessage message = (CommMessage)writeRequest.getMessage();
		
		if (processor != null)
		{
			
			message.setMessagbody(processor.obj2msg(message.getMessageObject()));
			message.setLength(message.getMessagbody().length);
		}
		else
		{
			;
		}
		super.messageSent(nextFilter, session, writeRequest);
	}
	
	

}
