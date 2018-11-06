package com.huateng.hstserver.communicate.mina.comm.common.filter.logging;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.WriteRequest;
import org.apache.mina.filter.logging.LoggingFilter;

import com.huateng.hstserver.communicate.mina.comm.common.DataTrans.DataTransTools;
import com.huateng.hstserver.communicate.mina.comm.common.entity.CommMessage;





public class PackPrintLoggingFilter extends LoggingFilter {
	private Logger logger = Logger.getLogger(PackPrintLoggingFilter.class);
	protected String getStrFromObj(Object message)
	{
		
		String messageStr = null;
		if (message instanceof CommMessage)
		{
			CommMessage outmessage=(CommMessage) message;
			messageStr = DataTransTools.bytesToHexString(outmessage.getMessagbody());
		}
		else if (message instanceof IoBuffer)
		{
			IoBuffer messBuffer = (IoBuffer) message;
			int messLen = messBuffer.remaining();
			byte[] messBytes = new byte[messLen];
			messBuffer.get(messBytes, 0, messLen);
			messageStr = DataTransTools.bytesToHexString(messBytes);
			messBuffer.position(0);
		}
		else
		{
			messageStr = "Unknown message type";
		}
		
		return messageStr;
	}

	@Override
	public void messageReceived(NextFilter nextFilter, IoSession session,
			Object message) throws Exception {
		
		//String messageStr = getStrFromObj(message);
		logger.info("start recieve log filter");
		super.messageReceived(nextFilter, session, message);
		logger.info("end recieve log filter");
	}

	@Override
	public void messageSent(NextFilter nextFilter, IoSession session,
			WriteRequest writeRequest) throws Exception {
		
		logger.info("start send log filter");
		super.messageSent(nextFilter, session, writeRequest);
		logger.info("end send log filter");
	}

	@Override
	public void sessionClosed(NextFilter nextFilter, IoSession session)
			throws Exception {
		
		super.sessionClosed(nextFilter, session);
	}

	@Override
	public void sessionCreated(NextFilter nextFilter, IoSession session)
			throws Exception {
		
		super.sessionCreated(nextFilter, session);
	}

	@Override
	public void sessionOpened(NextFilter nextFilter, IoSession session)
			throws Exception {
		
		super.sessionOpened(nextFilter, session);
	}
	
	

}
