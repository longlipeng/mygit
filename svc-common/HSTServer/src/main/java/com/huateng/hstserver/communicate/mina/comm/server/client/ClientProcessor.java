package com.huateng.hstserver.communicate.mina.comm.server.client;



import org.apache.log4j.Logger;

import java.net.InetSocketAddress;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.ReadFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;

import com.huateng.hstserver.communicate.mina.comm.common.entity.CommMessage;
import com.huateng.hstserver.exception.BizServiceException;



public class ClientProcessor {
	
	private final Logger logger = Logger.getLogger(ClientProcessor.class);
	

	private IoConnector connector = null;
	
	private ConcurrentHashMap<InetSocketAddress, IoSession> sessionMap = new ConcurrentHashMap<InetSocketAddress, IoSession>();
	
	private ConcurrentHashMap<InetSocketAddress, InetSocketAddress> connectInitSynTool = new ConcurrentHashMap<InetSocketAddress, InetSocketAddress>();
	// when it's not short connection, there are two things the user should notice
	// 1.this synclient object should coordinate with a dead session processor which could be a filter or a handler
	// 2.user should better set synProcessData to be true when you are concurrent processing on one session, otherwise it may cause reply package race between threads
	private boolean isShortConnection;
	// may be useful when you are using a long connection, see comment on isShortConnection;
	private boolean synProcessData;
	

	public boolean isShortConnection() {
		return isShortConnection;
	}


	public boolean isSynProcessData() {
		return synProcessData;
	}


	public IoConnector getConnector() {
		return connector;
	}


	public void setConnector(IoConnector connector) throws Exception {
		if (this.connector == null)
		{
			if (connector == null)
			{
				
				throw new Exception("Can not set a null connector");
			}
			connector.getSessionConfig().setUseReadOperation(true);
			this.connector = connector;
		}
		else
			
			throw new Exception("Can not reset the connector");
	}
	
	protected ClientProcessor(boolean isShortConnection) {
	
		this(isShortConnection, true);
	}
	
	protected ClientProcessor(boolean isShortConnection, boolean synProcessDataTrans) {
		this.isShortConnection = isShortConnection;
		this.synProcessData = synProcessDataTrans;
	}

	protected void removeDeadSession(InetSocketAddress remoteAddress)
	{
		if (!isShortConnection && remoteAddress!=null)
		{
			sessionMap.remove(remoteAddress);
			connectInitSynTool.remove(remoteAddress);
		}
	}
	
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
		logger.info("Connected to remote, ip:{"+remoteAddress.getAddress().getHostAddress()+"}, port:{"+remoteAddress.getPort()+"}");
		
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
			if(session != null){
				logger.info("session.isClosing is  [{"+session.isClosing()+"}]");
				logger.info("session.isConnected is  [{"+session.isConnected()+"}]");
				if(!session.isClosing()){
					logger.info("sesssionRemoteAddress is  [{"+session.getRemoteAddress().toString()+"}]");
					logger.info("sesssionLocalAddress is  [{"+session.getLocalAddress().toString()+"}]" );
				}
				if(session.isClosing()){
					removeDeadSession(remoteAddress);
					session = null;
				}
			}			
			if(session == null){
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
	
	/**
	 * 关闭所有客户端连接，一般用于停止应用的时候
	 */
	public void destroy(){
		Enumeration<IoSession> sessions = sessionMap.elements();
		while(sessions.hasMoreElements()){
			IoSession session = sessions.nextElement();
			session.close(true);
		}
	}

	public CommMessage sendMessage(CommMessage sendmesg) throws BizServiceException	{
		return null;
	}
	
}
