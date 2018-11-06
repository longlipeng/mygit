package com.huateng.hstserver.communicate.mina.comm.server.client;

import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;



/**
 * The Class HuatengServerHandler.
 */
public class ManagedSynClientCoordinateHandler extends IoHandlerAdapter
{	
	private ManagedSynClient managedSyncClient = null;
	
//	private int reqTimeOutTime = 30;
		
	public ManagedSynClient getManagedSyncClient() {
		return managedSyncClient;
	}

	public void setManagedSyncClient(ManagedSynClient syncClient) {
		this.managedSyncClient = syncClient;
	}	
	
	
	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
//		int i = 1;
//		i = i ++;
	}

	
	
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		
		super.messageReceived(session, message);
	}

	public void sessionClosed(IoSession iosession) throws Exception 
	{
		
		managedSyncClient.removeDeadSession((InetSocketAddress)iosession.getRemoteAddress());
	}
	
//	@Override
//	public void exceptionCaught(IoSession session, Throwable cause)
//			throws Exception {
//		super.exceptionCaught(session, cause);
//	}

}
