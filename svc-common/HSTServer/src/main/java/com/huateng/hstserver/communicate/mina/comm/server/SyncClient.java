package com.huateng.hstserver.communicate.mina.comm.server;



import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.ReadFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;

import com.huateng.hstserver.communicate.mina.comm.common.entity.CommMessage;

@Deprecated
public class SyncClient {

	private IoConnector connector;
	
	private ConcurrentHashMap<InetSocketAddress, IoSession> sessionMap = new ConcurrentHashMap<InetSocketAddress, IoSession>();
	
	private ConcurrentHashMap<InetSocketAddress, InetSocketAddress> connectInitSynTool = new ConcurrentHashMap<InetSocketAddress, InetSocketAddress>();
	// when it's not short connection, there are two things the user should notice
	// 1.this synclient object should coordinate with a dead session processor which could be a filter or a handler
	// 2.user should better set synProcessData to be true when you are concurrent processing on one session, otherwise it may cause reply package race between threads
	private boolean isShortConnection;
	// may be useful when you are using a long connection, see comment on isShortConnection;
	private boolean synProcessData;
	

	public SyncClient(IoConnector connector) {
		this(connector, true);
	}
	
	
	public SyncClient(IoConnector connector, boolean isShortConnection) {
	
		this(connector, isShortConnection, true);
	}
	
	
	public SyncClient(IoConnector connector, boolean isShortConnection,
			boolean synProcessDataTrans) {
		connector.getSessionConfig().setUseReadOperation(true);
		this.connector = connector;
		this.isShortConnection = isShortConnection;
		this.synProcessData = synProcessDataTrans;
	}
	
	
	public void removeDeadSession(InetSocketAddress remoteAddress)
	{
		if (!isShortConnection)
		{
			sessionMap.remove(remoteAddress);
			connectInitSynTool.remove(remoteAddress);
		}
	}
	
//	public IoConnector getConnector() {
//		return connector;
//	}
//	
//	public void setConnector(IoConnector connector) {
//		connector.getSessionConfig().setUseReadOperation(true);
//		this.connector = connector;
//	}

	protected IoSession getWorkingSession(InetSocketAddress remoteAddress)
	{
		IoSession session = null;
	
		if (isShortConnection)
		{
			session = getWorkingSessionShortConn(remoteAddress);
		}
		else
		{
			session = getWorkingSessionNotShortConn(remoteAddress);
		}
		
		return session;
	}
	
	protected IoSession getWorkingSessionShortConn(InetSocketAddress remoteAddress)
	{
		ConnectFuture connectfuture;
		IoSession session = null;
	
		connectfuture = connector.connect(remoteAddress);
		connectfuture.awaitUninterruptibly();
		session = connectfuture.getSession();
		
		return session;
	}
	
	protected IoSession getWorkingSessionNotShortConn(InetSocketAddress remoteAddress)
	{
		InetSocketAddress synAddress = null;
		IoSession session = null;
		
		synchronized(connectInitSynTool)
		{
			synAddress = connectInitSynTool.get(remoteAddress);
			if (synAddress == null)
			{
				connectInitSynTool.put(remoteAddress, remoteAddress);
				synAddress = remoteAddress;
			}
		}
		
		synchronized(synAddress)
		{
			session = sessionMap.get(remoteAddress);
			if (session == null)
			{
				session = getWorkingSessionShortConn(remoteAddress);
				sessionMap.put(remoteAddress, session);
			}
		}
		
		return session;
	}
	
	protected CommMessage processMessage(CommMessage sendmesg, IoSession session) 
	{
		
		if(!isShortConnection && synProcessData)
		{
			return processMessageSyn(sendmesg, session);
		}
		else
		{
			return processMessageNotSyn(sendmesg, session);
		}
	}
	
	protected CommMessage processMessageNotSyn(CommMessage sendmesg, IoSession session) 
	{
		
		CommMessage recvMessage = null;
		
		session.write(sendmesg);
		ReadFuture readFuture = session.read();
		
		while(recvMessage == null)
		{
			if (!readFuture.isClosed())
			{
				readFuture.awaitUninterruptibly();
				recvMessage = (CommMessage)readFuture.getMessage();
			}
			else
			{
				session.close(true);
				return null;
			}
		}
		
		return recvMessage;
	}
	
	protected CommMessage processMessageSyn(CommMessage sendmesg, IoSession session) 
	{
		synchronized (session) {
			return processMessageNotSyn(sendmesg, session);
		}
		
	}
	
	public CommMessage sendmessage(CommMessage sendmesg) 
	{
		CommMessage recvMessage = null;	
		InetSocketAddress synAddress = null;
		IoSession session = null;
		
		String ip = sendmesg.getRemoteip();
		int port = sendmesg.getRemoteport();
		InetSocketAddress remoteAddress = new InetSocketAddress(ip, port);
		
		session = getWorkingSession(remoteAddress);
		
		recvMessage = processMessage(sendmesg, session);
		
		if (isShortConnection)
		{
			session.close(true);
		}
		
		return recvMessage;
	}

}
