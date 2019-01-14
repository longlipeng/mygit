package com.huateng.hstserver.communicate.mina.comm.server.client;

import java.net.InetSocketAddress;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import com.huateng.hstserver.communicate.mina.comm.common.entity.CommMessage;


public class ManagedAsynClientCoordinateHandler extends IoHandlerAdapter {
	
	private ManagedAsynClient managedAsynClient = null;
	
	public ManagedAsynClient getManagedAsynClient() {
		return managedAsynClient;
	}

	public void setManagedAsynClient(ManagedAsynClient managedAsynClient) {
		this.managedAsynClient = managedAsynClient;
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		managedAsynClient.putReplyMessage((CommMessage)message);
		if (managedAsynClient.isShortConnection())
		{
			session.close(true);
		}
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		managedAsynClient.removeDeadSession((InetSocketAddress)session.getRemoteAddress());
	}

//	@Override
//	public void exceptionCaught(IoSession session, Throwable cause)
//			throws Exception {
//		super.exceptionCaught(session, cause);
//	}
	
	

}
